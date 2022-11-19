/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg.builder;

import org.jtool.pdg.PDG;
import org.jtool.pdg.Dependence;
import org.jtool.pdg.CD;
import org.jtool.pdg.DD;
import org.jtool.pdg.PDGNode;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.JVariableReference;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;
import java.util.HashSet;
import java.util.Collection;

/**
 * Finds data dependences of a PDG from its CFG.
 * This class does not guarantee a loop carried node to be correct if a given PDG contains no control dependence.
 * 
 * @author Katsuhisa Maruyama
 */
public class DDFinder {
    
    public static void find(PDG pdg, CFG cfg) {
        findDDs(pdg, cfg);
        findDefOrderDDs(pdg, cfg);
    }
    
    private static void findDDs(PDG pdg, CFG cfg) {
        for (CFGNode node : cfg.getNodes()) {
            if (node.isStatement() && node.hasDefVariable()) {
                CFGStatement anchor = (CFGStatement)node;
                List<CFGNode> reachableNodes = cfg.forwardReachableNodes(anchor, true, false);
                
                for (JVariableReference jvar : anchor.getDefVariables()) {
                    if (needTraverse(jvar, reachableNodes)) {
                        anchor.getOutgoingFlows().stream().filter(flow -> !flow.isFallThrough())
                                .forEach(flow -> findDD(pdg, cfg, anchor, flow.getDstNode(), jvar));
                    }
                }
            }
        }
    }
    
    private static boolean needTraverse(JVariableReference jvar, List<CFGNode> nodes) {
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
    
    private static void findDD(PDG pdg, CFG cfg, CFGNode anchor, CFGNode startnode, JVariableReference jvar) {
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
                        PDGNode lc = getLoopCarried(pdg, cfg, anchor, candidate);
                        edge = new DD(anchor.getPDGNode(), candidate.getPDGNode(), jvar);
                        
                        if (lc != null) {
                            edge.setLCDD();
                            edge.setLoopCarriedNode(lc);
                        } else {
                            edge.setLIDD();
                        }
                    }
                    pdg.add(edge);
                }
            }
            
            if (node.hasDefVariable()) {
                CFGStatement candidate = (CFGStatement)node;
                if (candidate.defineVariable(jvar)) {
                    if (!candidate.useVariable(jvar)) {
                        DD edge = new DD(anchor.getPDGNode(), candidate.getPDGNode(), jvar);
                        edge.setOutput();
                        pdg.add(edge);
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
    
    private static PDGNode getLoopCarried(PDG pdg, CFG cfg, CFGNode defnode, CFGNode usenode) {
        List<PDGNode> defancestors = new ArrayList<>();
        collectAncestors(pdg, defnode.getPDGNode(), defancestors, new HashSet<>());
        List<PDGNode> useancestors = new ArrayList<>();
        collectAncestors(pdg, usenode.getPDGNode(), useancestors, new HashSet<>());
        
        Set<CFGNode> nodesBetween = null;
        for (PDGNode defancestor : defancestors) {
            for (PDGNode useancestor : useancestors) {
                if (defancestor.equals(useancestor)) {
                    if (nodesBetween == null) {
                        nodesBetween = getNodesBetween(cfg, defnode, usenode);
                    }
                    if (nodesBetween.contains(defancestor.getCFGNode())) {
                        return defancestor;
                    }
                }
            }
        }
        return null;
    }
    
    private static void collectAncestors(PDG pdg, PDGNode node, Collection<PDGNode> ancestors, Set<PDGNode> track) {
        if (node == null || track.contains(node)) {
            return;
        }
        
        track.add(node);
        if (node.isLoop()) {
            ancestors.add(node);
        }
        
        pdg.getIncomingCDEdges(node).stream().filter(edge -> edge.isTrue() || edge.isFalse())
                .forEach(edge -> collectAncestors(pdg, edge.getSrcNode(), ancestors, track));
    }
    
    private static Set<CFGNode> getNodesBetween(CFG cfg, CFGNode defnode, CFGNode usenode) {
        Set<CFGNode> nodes;
        if (defnode.equals(usenode)) {
            nodes = new HashSet<>();
            for (CFGNode succ : defnode.getSuccessors()) {
                nodes.addAll(cfg.reachableNodes(succ, usenode, true, false));
            }
        } else {
            nodes = cfg.reachableNodes(defnode, usenode, true, false);
        }
        return nodes;
    }
    
    private static void findDefOrderDDs(PDG pdg, CFG cfg) {
        for (Dependence edge : pdg.getEdges()) {
            if (edge.isDD()) {
                DD dd = (DD)edge;
                if (dd.isOutput() && isDefOrder(pdg, dd)) {
                    dd.setDefOrder();
                }
            }
        }
    }
    
    private static boolean isDefOrder(PDG pdg, DD dd) {
        PDGNode src = dd.getSrcNode();
        PDGNode dst = dd.getDstNode();
        
//        if (pdg.getQualifiedName().getClassName().equals("Test09")) {
//            System.err.println("SRC = " + pdg.getIncomingCDEdges(src).size());
//            System.err.println("DST = " + pdg.getIncomingCDEdges(dst).size());
//        }
        
        CD srcCD = pdg.getIncomingCDEdges(src).iterator().next();
        CD dstCD = pdg.getIncomingCDEdges(dst).iterator().next();
        if (!srcCD.getSrcNode().equals(dstCD.getSrcNode())) {
            return false;
        }
        
        for (DD srcDD : pdg.getOutgoingDDEdges(src)) {
            if (srcDD.isLCDD() || srcDD.isLIDD()) {
                for (DD dstDD : pdg.getOutgoingDDEdges(dst)) {
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
