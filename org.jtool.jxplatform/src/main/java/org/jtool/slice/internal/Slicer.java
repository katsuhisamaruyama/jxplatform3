/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.slice.internal;

import org.jtool.slice.SliceCriterion;
import org.jtool.pdg.PDG;
import org.jtool.pdg.DependencyGraph;
import org.jtool.pdg.DependencyGraphEdge;
import org.jtool.pdg.PDGNode;
import org.jtool.pdg.PDGStatement;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.CFGParameter;
import org.jtool.cfg.CFG;
import org.jtool.cfg.JVariableReference;
import org.jtool.cfg.StopConditionOnReachablePath;
import org.jtool.graph.GraphNode;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

/**
 * Collects nodes in a program slice.
 * 
 * @author Katsuhisa Maruyama
 */
public class Slicer {
    
    private DependencyGraph graph;
    
    private Set<PDGNode> nodesInSlice = new HashSet<>();
    
    private Set<CFGNode> nodesInTargetMethod;
    private List<CFGNode> reachableNodesToCriterion;
    
    private Set<PDGNode> reachedMethodCalls = new HashSet<>();
    private Set<PDGNode> pendingNodes = new HashSet<>();
    
    public Slicer(SliceCriterion criterion) {
        assert criterion != null;
        
        graph = criterion.getDependencyGraph();
        PDG pdg = graph.getPDGs().stream()
                .filter(g -> g.getNodes().contains(criterion.getNode())).findFirst().orElse(null);
        
        if (pdg != null) {
            nodesInTargetMethod = pdg.getCFG().getNodes();
            reachableNodesToCriterion = pdg.getCFG()
                    .backwardReachableNodes(criterion.getNode().getCFGNode(), true, true);
            criterion.getVariables().forEach(var -> extract(pdg, criterion.getNode(), var));
        }
    }
    
    public Set<PDGNode> getNodes() {
        return nodesInSlice;
    }
    
    private void extract(PDG pdg, PDGNode node, JVariableReference var) {
        Set<PDGNode> startnodes = findStartNodes(pdg, node, var);
        
        startnodes.stream()
                  .filter(start -> start.getCFGNode().isMethodCall())
                  .forEach(start -> {
                      CFGMethodCall callNode = (CFGMethodCall)start.getCFGNode();
                      reachedMethodCalls.add(callNode.getPDGNode());
                      traverseBackward(callNode.getPDGNode());
                      
                      PDGNode receiverNode = getReceiverNode(callNode);
                      traverseBackward(receiverNode);
                  });
        
        startnodes.forEach(start -> traverseBackward(start));
        
        graph.getIncomingCDEdges(node).stream()
            .filter(edge -> edge.isTrue() || edge.isFalse())
            .forEach(edge -> traverseBackward(edge.getSrcNode()));
    }
    
    private Set<PDGNode> findStartNodes(PDG pdg, PDGNode node, JVariableReference jv) {
        Set<PDGNode> pdgnodes = new HashSet<>();
        if (node.isStatement()) {
            PDGStatement pdgnode = (PDGStatement)node;
            if (pdgnode.definesVariable(jv)) {
                pdgnodes.add(pdgnode);
                if (!jv.isPrimitiveType()) {
                    findStartNodesForNonPrimitiveVariable(pdgnodes, pdg, pdgnode, jv);
                }
                return pdgnodes;
            }
        }
        
        findStartNodesForUseVariable(pdgnodes, pdg, node, jv);
        if (!jv.isPrimitiveType()) {
            findStartNodesForUseFieldReference(pdgnodes, pdg, node, jv);
        }
        return pdgnodes;
    }
    
    private void findStartNodesForUseVariable(Set<PDGNode> pdgnodes,
            PDG pdg, PDGNode node, JVariableReference jv) {
        graph.getIncomingDDEdges(node).stream()
            .filter(edge -> edge.getVariable().equals(jv))
            .forEach(edge -> pdgnodes.add(edge.getSrcNode()));
        
        if (pdgnodes.size() > 0) {
            return;
        }
        
        traverseBackaward(pdgnodes, pdg, node, jv);
    }
    
    private void findStartNodesForNonPrimitiveVariable(Set<PDGNode> pdgnodes,
            PDG pdg, PDGStatement node, JVariableReference jv) {
        JVariableReference use = node.getUseVariables().stream()
                .filter(v -> !v.isUncoveredFieldReference()).findFirst().orElse(null);
        if (use == null) {
            return;
        }
        
        if (use.isReturnValueReference()) {
            Set<PDGNode> actualOuts = new HashSet<>();
            traverseBackaward(actualOuts, pdg, node, use);
            
            if (actualOuts.size() > 0) {
                PDGStatement actualOut = (PDGStatement)actualOuts.iterator().next();
                actualOut.getUseVariables()
                    .forEach(v -> findStartNodesForUseFieldReference(pdgnodes, pdg, actualOut, v));
            }
        } else {
            findStartNodesForUseFieldReference(pdgnodes, pdg, node, use);
        }
    }
    
    private void findStartNodesForUseFieldReference(Set<PDGNode> pdgnodes,
            PDG pdg, PDGNode node, JVariableReference jv) {
        CFG cfg = pdg.getCFG();
        
        for (GraphNode pred : node.getCFGNode().getSrcNodes()) {
            cfg.backwardReachableNodes((CFGNode)pred, true, true, new StopConditionOnReachablePath() {
                
                @Override
                public boolean isStop(CFGNode node) {
                    if (node.hasDefVariable()) {
                        CFGStatement cfgnode = (CFGStatement)node;
                        boolean match = cfgnode.getDefVariables().stream()
                            .filter(v -> !v.isReturnValueReference())
                            .anyMatch(v -> v.getReferenceForm().startsWith(jv.getReferenceForm() + "."));
                        if (match) {
                            pdgnodes.add(cfgnode.getPDGNode());
                        }
                        if (cfgnode.defineVariable(jv)) {
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
    }
    
    private void traverseBackaward(Set<PDGNode> pdgnodes, PDG pdg, PDGNode node, JVariableReference jv) {
        CFG cfg = pdg.getCFG();
        cfg.backwardReachableNodes(node.getCFGNode(), true, true, new StopConditionOnReachablePath() {
            
            @Override
            public boolean isStop(CFGNode node) {
                if (node.isStatement()) {
                    CFGStatement cfgnode = (CFGStatement)node;
                    if (cfgnode.defineVariable(jv)) {
                        pdgnodes.add(cfgnode.getPDGNode());
                        return true;
                    }
                }
                return false;
            }
        });
    }
    
    private PDGNode getCallNode(CFGParameter node) {
        return node.getParent().getPDGNode();
    }
    
    private PDGNode getReceiverNode(CFGMethodCall node) {
        return node.getReceiver().getPDGNode();
    }
    
    private void traverseBackward(PDGNode node) {
        if (nodesInSlice.contains(node)) {
            return;
        }
        
        nodesInSlice.add(node);
        
        if (node.getCFGNode().isActualOut()) {
            PDGNode callNode = getCallNode((CFGParameter)node.getCFGNode());
            reachedMethodCalls.add(callNode);
            checkPendingNodes();
            
            PDGNode receiverNode = getReceiverNode((CFGMethodCall)callNode.getCFGNode());
            traverseBackward(receiverNode);
            
            traverseBackward(callNode);
        }
        
        for (DependencyGraphEdge edge : DependencyGraphEdge.sortEdges(graph.getIncomingEdges(node))) {
            PDGNode src = edge.getSrcNode();
            
            if (edge.isFallThrough() || edge.isClassMember() || edge.isCall()) {
                continue;
            }
            
            if (edge.isOutput() || edge.isDefOrder()) {
                continue;
            }
            
            if (edge.isSummary()) {
                PDGNode callNode = getCallNode((CFGParameter)node.getCFGNode());
                if (!hasCallee(callNode)) {
                    traverseBackward(src);
                }
                continue;
            }
            
            if (src.getCFGNode().isMethodCall()) {
                if (!reachedMethodCalls.contains(src) && reachableTo(src)) {
                    reachedMethodCalls.add(src);
                    checkPendingNodes();
                }
                
                if (reachedMethodCalls.contains(src)) {
                    PDGNode receiverNode = getReceiverNode((CFGMethodCall)src.getCFGNode());
                    traverseBackward(receiverNode);
                    
                    traverseBackward(src);
                }
                continue;
            }
            
            if (edge.isDD()) {
                if (src.getCFGNode().isActualIn()) {
                    if (!traverseActualIn(src)) {
                        pendingNodes.add(src);
                    }
                    continue;
                }
                
                if (edge.isFieldAccess()) {
                    if (getDominantNode(src).getCFGNode().isFieldEntry()) {
                        traverseBackward(src);
                        
                    } else {
                        //for (PDGNode callNode : getCallsToMethodOf(src)) {
                        //    collectReachedMethodCalls(src);
                        //    if (reachedMethodCalls.contains(callNode)) {
                        //        traverseBackward(src);
                        //    }
                        //}
                    }
                    continue;
                }
            }
            
            traverseBackward(src);
        }
        
        graph.getOutgoingEdges(node).stream()
            .filter(edge -> edge.isExceptionCatch())
            .forEach(edge -> traverseBackward(edge.getDstNode()));
    }
    
    private void checkPendingNodes() {
        for (PDGNode pendingNode : new HashSet<>(pendingNodes)) {
            if (traverseActualIn(pendingNode)) {
                pendingNodes.remove(pendingNode);
            }
        }
    }
    
    private boolean traverseActualIn(PDGNode src) {
        PDGNode callNode = getCallNode((CFGParameter)src.getCFGNode());
        if (reachedMethodCalls.contains(callNode)) {
            traverseBackward(src);
            
            PDGNode receiverNode = getReceiverNode((CFGMethodCall)callNode.getCFGNode());
            traverseBackward(receiverNode);
            traverseBackward(callNode);
            return true;
        }
        return false;
    }
    
    private boolean hasCallee(PDGNode node) {
        return graph.getOutgoingEdges(node).stream().anyMatch(edge -> edge.isCall());
    }
    
    private PDGNode getDominantNode(PDGNode node) {
        for (DependencyGraphEdge edge : graph.getIncomingCDEdges(node)) {
            if (edge.isTrue() || edge.isFalse()) {
                return edge.getSrcNode();
            }
        }
        return null;
    }
    
    @SuppressWarnings("unused")
    private PDGNode getDominatedNode(PDGNode node) {
        for (DependencyGraphEdge edge : graph.getOutgoingCDEdges(node)) {
            if (edge.isTrue() || edge.isFalse()) {
                return edge.getDstNode();
            }
        }
        return null;
    }
    
    private boolean reachableTo(PDGNode node) {
        return !nodesInTargetMethod.contains(node.getCFGNode()) ||
               reachableNodesToCriterion.contains(node.getCFGNode());
    }
}
