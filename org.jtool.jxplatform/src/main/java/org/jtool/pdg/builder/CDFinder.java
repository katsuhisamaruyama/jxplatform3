/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg.builder;

import org.jtool.pdg.PDG;
import org.jtool.pdg.Dependence;
import org.jtool.pdg.CD;
import org.jtool.pdg.PDGNode;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.CFGTry;
import org.jtool.cfg.ControlFlow;
import org.jtool.cfg.JVariableReference;
import org.jtool.cfg.StopConditionOnReachablePath;
import java.util.Set;
import java.util.Stack;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Finds control dependences in a bare PDG.
 * 
 * @author Katsuhisa Maruyama
 */
public class CDFinder {
    
    public static void find(PDG pdg , CFG cfg) {
        findCDs(pdg, cfg);
        findCDsOnTryCatch(pdg, cfg);
        findCDsFromEntry(pdg, cfg);
        findCDsOnDeclarations(pdg, cfg);
        
        removeTransitiveCDs(pdg);
    }
    
    private static void findCDs(PDG pdg, CFG cfg) {
        cfg.getNodes().stream().filter(node -> node.isBranch()).forEach(cfgnode -> {
            findCDs(pdg, cfg, cfgnode);
        });
    }
    
    private static void findCDs(PDG pdg, CFG cfg, CFGNode branchNode) {
        Set<CFGNode> postDominator = cfg.postDominator(branchNode);
        
        for (ControlFlow branch : branchNode.getOutgoingFlows()) {
            CFGNode branchDstNode = branch.getDstNode();
            Set<CFGNode> postDominatorForBranch = cfg.postDominator(branchDstNode);
            postDominatorForBranch.add(branchDstNode);
            
            for (CFGNode cfgnode : postDominatorForBranch) {
                if (cfgnode.isStatement() && !branchNode.equals(cfgnode) && !postDominator.contains(cfgnode)) {
                    CD edge = new CD(branchNode.getPDGNode(), cfgnode.getPDGNode());
                    if (branch.isTrue()) {
                        edge.setTrue();
                    } else if (branch.isFalse()) {
                        edge.setFalse();
                    } else if (branch.isFallThrough()) {
                        edge.setFallThrough();
                    } else if (branch.isExceptionCatch()) {
                        edge.setExceptionCatch();
                    }
                    pdg.add(edge);
                }
            }
        }
    }
    
    private static void findCDsOnTryCatch(PDG bpdg, CFG cfg) {
        for (CFGNode cfgnode : cfg.getNodes()) {
            if (cfgnode.isTry()) {
                CFGTry trynode = (CFGTry)cfgnode;
                trynode.getCatchNodes().forEach(catchnode -> {
                    CD edge = new CD(trynode.getPDGNode(), catchnode.getPDGNode());
                    edge.setTrue();
                    bpdg.add(edge);
                });
                if (trynode.getFinallyNode() != null) {
                    CD edge = new CD(trynode.getPDGNode(), trynode.getFinallyNode().getPDGNode());
                    edge.setTrue();
                    bpdg.add(edge);
                }
            }
        }
    }
    
    private static void findCDsFromEntry(PDG pdg, CFG cfg) {
        CFGNode entryNode = cfg.getEntryNode();
        for (PDGNode pdgnode : pdg.getNodes()) {
            if (!pdgnode.isEntry() && pdg.getIncomingCDEdges(pdgnode).size() == 0) {
                CD edge = new CD(entryNode.getPDGNode(), pdgnode);
                edge.setTrue();
                pdg.add(edge);
            }
        }
    }
    
    private static void findCDsOnDeclarations(PDG pdg, CFG cfg) {
        for (CFGNode cfgnode : cfg.getNodes()) {
            if (cfgnode.isStatement()) {
                findCDsOnDeclarations(pdg, cfg, (CFGStatement)cfgnode);
            }
        }
    }
    
    private static void findCDsOnDeclarations(PDG pdg, CFG cfg, CFGStatement cfgnode) {
        Set<JVariableReference> vars = new HashSet<>();
        vars.addAll(cfgnode.getDefVariables());
        vars.addAll(cfgnode.getUseVariables());
        
        for (JVariableReference jv : vars) {
            cfg.backwardReachableNodes(cfgnode, true, true, new StopConditionOnReachablePath() {
                
                @Override
                public boolean isStop(CFGNode node) {
                    if (node.isLocalDeclaration()) {
                        CFGStatement decnode = (CFGStatement)node;
                        if (decnode.defineVariable(jv)) {
                            if (!decnode.equals(cfgnode)) {
                                CD edge = new CD(decnode.getPDGNode(), cfgnode.getPDGNode());
                                edge.setDeclaration();
                                pdg.add(edge);
                            }
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
    }
    
    private static void removeTransitiveCDs(PDG pdg) {
        for (PDGNode node: pdg.getNodes()) {
            Set<CD> edges = pdg.getIncomingCDEdges(node).stream()
                    .filter(e -> e.isTrue() || e.isFalse()).collect(Collectors.toSet());
            Set<CD> removed = new HashSet<>();
            for (CD edge1 : edges) {
                if (!removed.contains(edge1)) {
                    PDGNode node1 = edge1.getSrcNode();
                    for (CD edge2 : edges) {
                        PDGNode node2 = edge2.getSrcNode();
                        if (!node1.equals(node2) && !removed.contains(edge2)) {
                            if (getCDAncestors(node2).contains(node1)) {
                                removed.add(edge1);
                                break;
                            }
                        }
                    }
                }
            }
            for (CD edge : removed) {
                pdg.remove(edge);
            }
        }
        
    }
    
    public static List<PDGNode> getCDAncestors(PDGNode anchor) {
        List<PDGNode> track = new ArrayList<PDGNode>();
        
        Stack<PDGNode> nodeStack = new Stack<>();
        nodeStack.push(anchor);
        
        while (!nodeStack.isEmpty()) {
            PDGNode node = nodeStack.pop();
            
            if (track.contains(node)) {
                continue;
            }
            track.add(node);
            
            node.getIncomingEdges().stream().map(edge -> (Dependence)edge)
                .filter(edge -> (edge.isTrue() || edge.isFalse()))
                .forEach(edge -> nodeStack.push(edge.getSrcNode()));
        }
        return track;
    }
}
