/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg.builder;

import org.jtool.pdg.CD;
import org.jtool.pdg.DD;
import org.jtool.pdg.Dependence;
import org.jtool.pdg.PDGNode;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.ControlFlow;
import org.jtool.cfg.JVariableReference;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;
import java.util.HashSet;

/**
 * Finds data dependences of a PDG from its CFG.
 * This class does not guarantee a loop carried node to be correct if a given PDG contains no control dependence.
 * 
 * @author Katsuhisa Maruyama
 */
public class DDFinder {
    
    public static void find(BarePDG bpdg, CFG cfg) {
        findDDs(bpdg, cfg);
        findDefOrderDDs(bpdg, cfg);
    }
    
    private static void findDDs(BarePDG bpdg, CFG cfg) {
        for (CFGNode cfgnode : cfg.getNodes()) {
            if (cfgnode.isStatement() && cfgnode.hasDefVariable()) {
                CFGStatement anchor = (CFGStatement)cfgnode;
                Set<CFGNode> reachableNodes = cfg.forwardReachableNodes(anchor, true, false);
                reachableNodes.remove(anchor);
                
                for (JVariableReference jvar : anchor.getDefVariables()) {
                    if (checkReachableNodes(reachableNodes, jvar)) {
                        findDDs(bpdg, cfg, anchor, jvar);
                    }
                }
            }
        }
    }
    
    private static boolean checkReachableNodes(Set<CFGNode> nodes, JVariableReference jvar) {
        for (CFGNode node : nodes) {
            if (node.isStatement()) {
                CFGStatement candidate = (CFGStatement)node;
                if (candidate.defineVariable(jvar) || candidate.useVariable(jvar)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private static void findDDs(BarePDG bpdg, CFG cfg, CFGStatement anchor, JVariableReference jvar) {
        for (ControlFlow flow : anchor.getOutgoingFlows()) {
            if (!flow.isFallThrough()) {
                CFGNode cfgnode = (CFGNode)flow.getDstNode();
                findDD(bpdg, cfg, anchor, cfgnode, jvar);
            }
        }
    }
    
    private static void findDD(BarePDG bpdg, CFG cfg, CFGNode anchor, CFGNode startnode, JVariableReference jvar) {
        Set<CFGNode> track = new HashSet<>();
        Stack<CFGNode> nodeStack = new Stack<>();
        nodeStack.push(startnode);
        
        while (!nodeStack.isEmpty()) {
            CFGNode node = nodeStack.pop();
            
            if (track.contains(node)) {
                continue;
            }
            track.add(node);
            
            if (node.hasUseVariable()) {
                CFGStatement candidate = (CFGStatement)node;
                if (candidate.useVariable(jvar)) {
                    DD edge;
                    if (anchor.isFormalIn()) {
                        edge = new DD(anchor.getPDGNode(), candidate.getPDGNode(), jvar);
                        edge.setLIDD();
                    
                    } else if (candidate.isFormalOut()) {
                        edge = new DD(anchor.getPDGNode(), candidate.getPDGNode(), jvar);
                        edge.setLIDD();
                    
                    } else {
                        PDGNode lc = getLoopCarried(bpdg, cfg, anchor, candidate);
                        edge = new DD(anchor.getPDGNode(), candidate.getPDGNode(), jvar);
                        
                        if (lc != null) {
                            edge.setLCDD();
                            edge.setLoopCarriedNode(lc);
                        } else {
                            edge.setLIDD();
                        }
                    }
                    bpdg.add(edge);
                }
            }
            
            if (node.hasDefVariable()) {
                CFGStatement candidate = (CFGStatement)node;
                if (candidate.defineVariable(jvar)) {
                    if (!candidate.useVariable(jvar)) {
                        DD edge = new DD(anchor.getPDGNode(), candidate.getPDGNode(), jvar);
                        edge.setOutput();
                        bpdg.add(edge);
                    }
                    continue;
                }
            }
            
            node.getOutgoingFlows().stream()
                .filter(flow -> !flow.isFallThrough())
                .map(flow -> flow.getDstNode())
                .forEach(succ -> nodeStack.push(succ));
        }
    }
    
    private static PDGNode getLoopCarried(BarePDG bpdg, CFG cfg, CFGNode defnode, CFGNode usenode) {
        Set<CFGNode> reachableNodes;
        if (defnode.equals(usenode)) {
            reachableNodes = new HashSet<>();
            for (CFGNode succ : defnode.getSuccessors()) {
                reachableNodes.addAll(cfg.reachableNodes(succ, usenode, true, false));
            }
        } else {
            reachableNodes = cfg.reachableNodes(defnode, usenode, true, false);
        }
        
        Set<PDGNode> track = new HashSet<>();
        track.clear();
        List<PDGNode> loopNodesForDef = new ArrayList<PDGNode>();
        findLoopNodes(bpdg, defnode.getPDGNode(), loopNodesForDef, reachableNodes, track);
        if (loopNodesForDef.isEmpty()) {
            return null;
        }
        
        track.clear();
        List<PDGNode> loopNodesForUse = new ArrayList<PDGNode>();
        findLoopNodes(bpdg, usenode.getPDGNode(), loopNodesForUse, reachableNodes, track);
        if (loopNodesForUse.isEmpty()) {
            return null;
        }
        
        for (PDGNode dlnode : loopNodesForDef) {
            for (PDGNode ulnode : loopNodesForUse) {
                if (dlnode.equals(ulnode)) {
                    return dlnode;
                }
            }
        }
        return null;
    }
    
    private static void findLoopNodes(BarePDG bpdg, PDGNode node, List<PDGNode> loopNodes,
            Set<CFGNode> reachableNodes, Set<PDGNode> track) {
        if (node.isLoop()) {
            loopNodes.add(node);
        }
        
        track.add(node);
        
        for (CD edge : bpdg.getIncomingCDEdges(node)) {
            if (edge.isTrue() || edge.isFalse()) {
                PDGNode src = edge.getSrcNode();
                if (!track.contains(src) && reachableNodes.contains(src.getCFGNode())) {
                    findLoopNodes(bpdg, src, loopNodes, reachableNodes, track);
                }
            }
        }
    }
    
    private static void findDefOrderDDs(BarePDG bpdg, CFG cfg) {
        for (Dependence edge : bpdg.getEdges()) {
            if (edge.isDD()) {
                DD dd = (DD)edge;
                if (dd.isOutput() && isDefOrder(bpdg, dd)) {
                    dd.setDefOrder();
                }
            }
        }
    }
    
    private static boolean isDefOrder(BarePDG bpdg, DD dd) {
        PDGNode src = dd.getSrcNode();
        PDGNode dst = dd.getDstNode();
        CD srcCD = bpdg.getIncomingCDEdges(src).iterator().next();
        CD dstCD = bpdg.getIncomingCDEdges(dst).iterator().next();
        if (!srcCD.getSrcNode().equals(dstCD.getSrcNode())) {
            return false;
        }
        
        for (DD srcDD : bpdg.getOutgoingDDEdges(src)) {
            if (srcDD.isLCDD() || srcDD.isLIDD()) {
                for (DD dstDD : bpdg.getOutgoingDDEdges(dst)) {
                    if (dstDD.isLCDD() || dstDD.isLIDD()) {
                        if (srcDD.getDstNode().equals(dstDD.getDstNode())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
