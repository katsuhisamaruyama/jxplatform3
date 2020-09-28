/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg.builder;

import org.jtool.pdg.CD;
import org.jtool.pdg.DD;
import org.jtool.pdg.Dependence;
import org.jtool.pdg.PDG;
import org.jtool.pdg.PDGNode;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.ControlFlow;
import org.jtool.cfg.JReference;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/**
 * Finds data dependences of a PDG from its CFG.
 * This class does not guarantee a loop carried node to be correct if a given PDG contains no control dependence.
 * All methods of this class are not intended to be directly called by clients.
 * 
 * @author Katsuhisa Maruyama
 */
public class DDFinder {
    
    public static void find(PDG pdg, CFG cfg) {
        findDDs(pdg, cfg);
        findDefOrderDDs(pdg, cfg);
    }
    
    private static void findDDs(PDG pdg, CFG cfg) {
        for (CFGNode cfgnode : cfg.getNodes()) {
            if (cfgnode.isStatement() && cfgnode.hasDefVariable()) {
                CFGStatement anchor = (CFGStatement)cfgnode;
                
                Set<CFGNode> reachableNodes = new HashSet<>();
                findReachableNodes(cfg, anchor, anchor, reachableNodes);
                reachableNodes.remove(anchor);
                
                for (JReference jvar : anchor.getDefVariables()) {
                    if (checkReachableNodes(reachableNodes, jvar)) {
                        findDDs(pdg, cfg, anchor, jvar);
                    }
                }
            }
        }
    }
    
    private static void findReachableNodes(CFG cfg, CFGNode anchor, CFGNode node, Set<CFGNode> nodes) {
        if (nodes.contains(node)) {
            return;
        }
        
        nodes.add(node);
        
        for (ControlFlow flow : node.getOutgoingFlows()) {
            if (!flow.isFallThrough()) {
                CFGNode succ = (CFGNode)flow.getDstNode();
                findReachableNodes(cfg, anchor, succ, nodes);
            }
        }
    }
    
    private static boolean checkReachableNodes(Set<CFGNode> nodes, JReference jvar) {
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
    
    private static void findDDs(PDG pdg, CFG cfg, CFGStatement anchor, JReference jvar) {
        for (ControlFlow flow : anchor.getOutgoingFlows()) {
            if (!flow.isFallThrough()) {
                Set<CFGNode> track = new HashSet<>();
                CFGNode cfgnode = (CFGNode)flow.getDstNode();
                findDD(pdg, cfg, anchor, cfgnode, jvar, track);
            }
        }
    }
    
    private static void findDD(PDG pdg, CFG cfg, CFGNode anchor, CFGNode node, JReference jvar, Set<CFGNode> track) {
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
                    if (lc != null && track.contains(lc.getCFGNode())) {
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
                return;
            }
        }
        
        for (ControlFlow flow : node.getOutgoingFlows()) {
            if (!flow.isFallThrough()) {
                CFGNode succ = (CFGNode)flow.getDstNode();
                if (!track.contains(succ)) {
                    findDD(pdg, cfg, anchor, succ, jvar, track);
                }
            }
        }
    }
    
    private static PDGNode getLoopCarried(PDG pdg, CFG cfg, CFGNode def, CFGNode use) {
        Set<PDGNode> track = new HashSet<>();
        
        track.clear();
        List<PDGNode> deftrack = new ArrayList<PDGNode>();
        findReachableNodes(def.getPDGNode(), deftrack, track);
        if (deftrack.isEmpty()) {
            return null;
        }
        
        track.clear();
        List<PDGNode> usetrack = new ArrayList<PDGNode>();
        findReachableNodes(use.getPDGNode(), usetrack, track);
        if (usetrack.isEmpty()) {
            return null;
        }
        
        Set<CFGNode> reachanbleNodes = cfg.constrainedReachableNodes(def, use);
        for (PDGNode pdgnode : deftrack) {
            if (usetrack.contains(pdgnode) && reachanbleNodes.contains(pdgnode.getCFGNode())) {
                return pdgnode;
            }
        }
        return null;
    }
    
    private static void findReachableNodes(PDGNode node, List<PDGNode> nodes, Set<PDGNode> track) {
        track.add(node);
        if (node.isLoop()) {
            nodes.add(node);
        }
        
        for (CD edge : node.getIncomingCDEdges()) {
            PDGNode src = edge.getSrcNode();
            if (!track.contains(src)) {
                findReachableNodes(src, nodes, track);
            }
        }
    }
    
    private static void findDefOrderDDs(PDG pdg, CFG cfg) {
        for (Dependence edge : pdg.getEdges()) {
            if (edge.isDD()) {
                DD dd = (DD)edge;
                if (dd.isOutput() && isDefOrder(dd)) {
                    dd.setDefOrder();
                }
            }
        }
    }
    
    private static boolean isDefOrder(DD dd) {
        PDGNode src = dd.getSrcNode();
        PDGNode dst = dd.getDstNode();
        CD[] srcCDs = src.getIncomingCDEdges().toArray(new CD[0]);
        CD[] dstCDs = dst.getIncomingCDEdges().toArray(new CD[0]);
        if (!srcCDs[0].getSrcNode().equals(dstCDs[0].getSrcNode())) {
            return false;
        }
        
        for (DD srcDD : src.getOutgoingDDEdges()) {
            if (srcDD.isLCDD() || srcDD.isLIDD()) {
                for (DD dstDD : dst.getOutgoingDDEdges()) {
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
