/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg.internal;

import org.jtool.pdg.PDG;
import org.jtool.pdg.Dependence;
import org.jtool.pdg.CD;
import org.jtool.pdg.PDGNode;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.ControlFlow;
import org.jtool.cfg.JVariableReference;
import org.jtool.cfg.StopConditionOnReachablePath;
import org.jtool.cfg.internal.CFGTry;
import org.jtool.cfg.internal.DominantStatement;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Finds control dependences in a bare PDG.
 * 
 * @author Katsuhisa Maruyama
 */
public class CDFinder {
    
    public static void find(PDG pdg , CFG cfg) {
        if (hasNestStructure(cfg)) {
            findCDsOnNestStructure(pdg, cfg);
            cfg.unregisterStructuredStatements();
        } else {
            findCDs(pdg, cfg);
        }
        findCDsOnTryCatch(pdg, cfg);
        findCDsFromEntry(pdg, cfg);
        findCDsOnDeclarations(pdg, cfg);
        
        removeTransitiveCDs(pdg);
    }
    
    private static boolean hasNestStructure(CFG cfg) {
        DominantStatement statement = cfg.getDominantStatement(cfg.getEntryNode().getOutgoingTrueFlow());
        return statement != null && statement.hasNestStructure();
    }
    
    private static void findCDsOnNestStructure(PDG pdg, CFG cfg) {
        for (CFGNode node : cfg.getNodes()) {
            if (node.isBranch()) {
                boolean hasNestStructure = node.getOutgoingFlows().stream()
                        .map(flow -> cfg.getDominantStatement(flow)).allMatch(st -> (st != null && st.hasNestStructure()));
                if (hasNestStructure) {
                    for (ControlFlow flow : node.getOutgoingFlows()) {
                        DominantStatement statement = cfg.getDominantStatement(flow);
                        for (CFGNode postDominator : statement.getImmediatePostDominators()) {
                            CD cd = createCD(node, flow, postDominator);
                            pdg.add(cd);
                        }
                    }
                } else {
                    findCDs(pdg, cfg, node);
                }
            }
        }
    }
    
    private static void findCDs(PDG pdg, CFG cfg) {
        cfg.getNodes().stream().filter(node -> node.isBranch()).forEach(node -> findCDs(pdg, cfg, node));
    }
    
    private static void findCDs(PDG pdg, CFG cfg, CFGNode branchNode) {
        Set<CFGNode> postDominator = cfg.postDominator(branchNode);
        
        for (ControlFlow flow : branchNode.getOutgoingFlows()) {
            CFGNode branchDstNode = flow.getDstNode();
            Set<CFGNode> postDominatorsForDstNode = cfg.postDominator(branchDstNode);
            postDominatorsForDstNode.add(branchDstNode);
            
            for (CFGNode node : postDominatorsForDstNode) {
                if (node.isStatement() && !branchNode.equals(node) && !postDominator.contains(node)) {
                    CD cd = createCD(branchNode, flow, node);
                    pdg.add(cd);
                }
            }
        }
    }
    
    private static CD createCD(CFGNode branchNode, ControlFlow flow, CFGNode node) {
        CD edge = new CD(branchNode.getPDGNode(), node.getPDGNode());
        if (flow.isTrue()) {
            edge.setTrue();
        } else if (flow.isFalse()) {
            edge.setFalse();
        } else if (flow.isFallThrough()) {
            edge.setFallThrough();
        } else if (flow.isExceptionCatch()) {
            edge.setExceptionCatch();
        }
        return edge;
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
                            if (getCDAncestors(pdg, node2).contains(node1)) {
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
    
    public static List<PDGNode> getCDAncestors(PDG pdg, PDGNode anchor) {
        List<PDGNode> track = new ArrayList<PDGNode>();
        
        Stack<PDGNode> nodeStack = new Stack<>();
        nodeStack.push(anchor);
        
        while (!nodeStack.isEmpty()) {
            PDGNode node = nodeStack.pop();
            
            if (track.contains(node)) {
                continue;
            }
            track.add(node);
            
            pdg.getIncomingEdges(node).stream().map(edge -> (Dependence)edge)
                .filter(edge -> (edge.isTrue() || edge.isFalse()))
                .forEach(edge -> nodeStack.push(edge.getSrcNode()));
        }
        return track;
    }
}
