/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.slice;

import org.jtool.pdg.CD;
import org.jtool.pdg.ClDG;
import org.jtool.pdg.DependenceGraph;
import org.jtool.pdg.DD;
import org.jtool.pdg.Dependence;
import org.jtool.pdg.PDG;
import org.jtool.pdg.PDGNode;
import org.jtool.pdg.PDGStatement;
import org.jtool.pdg.SDG;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGReceiver;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.CFG;
import org.jtool.cfg.JVariableReference;
import org.jtool.cfg.StopConditionOnReachablePath;
import org.jtool.graph.GraphNode;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An object storing information about a program slice.
 * 
 * @author Katsuhisa Maruyama
 */
public class Slice {
    
    protected SliceCriterion criterion;
    
    private PDG pdgForTargetMethod;
    private Set<PDGNode> nodesInSlice = new HashSet<>();
    
    private Set<CFGNode> nodesInTargetMethod;
    private Set<CFGNode> reachableNodesToCriterion;
    
    private Set<PDGNode> reachableMethodCalls = new HashSet<>();
    private Set<PDGNode> pendingNodes = new HashSet<>();
    
    public Slice(SliceCriterion criterion) {
        this.criterion = criterion;
        
        pdgForTargetMethod = getPDGForMethod();
        nodesInTargetMethod = pdgForTargetMethod.getCFG().getNodes();
        reachableNodesToCriterion = criterion.getPDG().getCFG().backwardReachableNodes(criterion.getNode().getCFGNode(), true);
        
        criterion.getVariables().forEach(var -> extract(criterion.getNode(), var));
    }
    
    private PDG getPDGForMethod() {
        DependenceGraph pdg = criterion.getPDG();
        if (pdg.isPDG()) {
            return (PDG)pdg;
        } else if (pdg.isClDG()) {
            for (PDG g : ((ClDG)pdg).getPDGs()) {
                if (g.contains(criterion.getNode())) {
                    return g;
                }
            }
        } else {
            for (PDG g : ((SDG)pdg).getPDGs()) {
                if (g.contains(criterion.getNode())) {
                    return g;
                }
            }
        }
        return null;
    }
    
    public DependenceGraph getPDG() {
        return criterion.getPDG();
    }
    
    public PDGNode getCriterionNode() {
        return criterion.getNode();
    }
    
    public Set<JVariableReference> getCriterionVariables() {
        return criterion.getVariables();
    }
    
    public Set<PDGNode> getNodes() {
        return nodesInSlice;
    }
    
    private void extract(PDGNode node, JVariableReference jv) {
        Set<PDGNode> startnodes = findStartNode(node, jv);
        
        for (PDGNode start : startnodes) {
            traverseBackward(start);
        }
        
        if (node.getCFGNode().isActual()) {
            for (CD edge : getIncomingNormalCD(node)) {
                for (CD edge2 : getIncomingNormalCD(edge.getSrcNode())) {
                    if (isConditional(edge2.getSrcNode())) {
                        traverseBackward(edge2.getSrcNode());
                    }
                }
            }
            
        } else if (node.getCFGNode().isMethodCall()) {
            for (CD edge : getIncomingNormalCD(node)) {
                for (CD edge2 : getIncomingNormalCD(edge.getSrcNode())) {
                    traverseBackward(edge2.getSrcNode());
                }
            }
            
        } else {
            for (CD edge : getIncomingNormalCD(node)) {
                traverseBackward(edge.getSrcNode());
            }
        }
        
        for (PDGNode start : startnodes) {
            for (CD edge : getIncomingNormalCD(start)) {
                traverseBackward(edge.getSrcNode());
            }
        }
    }
    
    private boolean isConditional(PDGNode node) {
        return node.getCFGNode().getOutgoingFlows().stream()
                .filter(edge -> edge.isTrue() || edge.isFalse())
                .collect(Collectors.toList())
                .size() > 1;
    }
    
    private List<CD> getIncomingNormalCD(PDGNode node) {
        return node.getIncomingCDEdges().stream()
                .filter(edge -> edge.isTrue() || edge.isFalse())
                .collect(Collectors.toList());
    }
    
    private Set<PDGNode> findStartNode(PDGNode node, JVariableReference jv) {
        Set<PDGNode> pdgnodes = new HashSet<>();
        if (node.isStatement()) {
            PDGStatement pdgnode = (PDGStatement)node;
            if (pdgnode.definesVariable(jv)) {
                pdgnodes.add(node);
                return pdgnodes;
            }
        }
        
        for (DD edge : node.getIncomingDDEdges()) {
            if (edge.getVariable().equals(jv)) {
                pdgnodes.add(edge.getSrcNode());
            }
        }
        
        if (pdgnodes.size() > 0) {
            return pdgnodes;
        }
        
        CFG cfg = criterion.getPDG().getCFG();
        cfg.backwardReachableNodes(node.getCFGNode(), true, new StopConditionOnReachablePath() {
            
            @Override
            public boolean isStop(CFGNode node) {
                if (node.hasDefVariable()) {
                    CFGStatement cfgnode = (CFGStatement)node;
                    if (cfgnode.defineVariable(jv)) {
                        pdgnodes.add(cfgnode.getPDGNode());
                        return true;
                    }
                }
                return false;
            }
        });
        
        return pdgnodes;
    }
    
    private void traverseBackward(PDGNode node) {
        if (nodesInSlice.contains(node)) {
            for (DD edge : node.getIncomingDDEdges()) {
                PDGNode src = edge.getSrcNode();
                if (src.getCFGNode().isMethodCall() &&
                    reachableTo(src) &&
                    checkFieldAccessForMethodCall(node, src, edge.getVariable())) {
                    checkPendingNodes(src);
                }
            }
            return;
        }
        
        nodesInSlice.add(node);
        
        if (node.getCFGNode().isMethodCall()) {
            addReachableMethodCall(node);
            
        } else if (node.getCFGNode().isActualOut()) {
            PDGNode callNode = getDominantNode(node);
            addReachableMethodCall(callNode);
            
        } else if (node.getCFGNode().isCatch()) {
            for (Dependence edge : node.getIncomingDependeceEdges()) {
                PDGNode src = edge.getSrcNode();
                if (src.getCFGNode().isMethodCall()) {
                    addReachableMethodCall(src);
                }
            }
        }
        
        for (Dependence edge : Dependence.sortEdges(node.getIncomingDependeceEdges())) {
            PDGNode src = edge.getSrcNode();
            
            if (edge.isCD() && !edge.isFallThrough()) {
                traverseBackward(src);
                
            } else if (edge.isLIDD() || edge.isLCDD()) {
                if (src.getCFGNode().isMethodCall()) {
                    if (reachableTo(src)) {
                        DD dd = (DD)edge;
                        if (dd.getVariable().isInProject()) {
                            traverseBackward(src);
                        }
                    }
                    
                } else {
                    traverseBackward(src);
                }
                
            } else if (edge.isCall()) {
                if (reachableTo(src) && checkTraversable(src)) {
                    for (Dependence edge2 : src.getOutgoingDependeceEdges()) {
                        if (edge2.isExceptionCatch()) {
                            traverseBackward(edge2.getDstNode());
                        }
                    }
                }
                
            } else if (edge.isParameterIn()) {
                PDGNode callNode = getDominantNode(src);
                if (reachableTo(callNode)) {
                    if (checkTraversable(callNode)) {
                        traverseBackward(src);
                    } else {
                        pendingNodes.add(src);
                    }
                }
                
            } else if (edge.isParameterOut()) {
                traverseBackward(src);
                
            } else if (edge.isSummary()) {
                PDGNode ainOn = getDominantNode(src);
                PDGNode aoutOn = getDominantNode(node);
                if (ainOn.equals(aoutOn)) {
                    traverseBackward(src);
                }
                
            } else if (edge.isFieldAccess()) {
                DD dd = (DD)edge;
                
                if (getDominantNode(src).getCFGNode().isFieldEntry()) {
                    traverseBackward(src);
                    
                } else if (src.getCFGNode().isMethodCall()) {
                    addReachableMethodCalls(src, dd.getVariable());
                    
                    if (reachableTo(src)) {
                        if (checkFieldAccessForMethodCall(node, src, dd.getVariable()) ||
                            checkTraversable(src)) {
                            traverseBackward(src);
                        } else {
                            pendingNodes.add(src);
                        }
                    }
                    
                } else {
                    addReachableMethodCalls(src, dd.getVariable());
                    
                    for (PDGNode callNode : getMethodCalls(src)) {
                        if (reachableTo(callNode) && checkTraversable(callNode)) {
                            traverseBackward(src);
                        } else {
                            pendingNodes.add(src);
                        }
                    }
                }
            }
        }
    }
    
    private boolean checkFieldAccessForMethodCall(PDGNode node, PDGNode src, JVariableReference jv) {
        for (DD edge : src.getOutgoingDDEdges()) {
            if (edge.isOutput() &&
                jv.getQualifiedName().equals(edge.getVariable().getQualifiedName()) &&
                reachableTo(getDominantNode(edge.getDstNode()))) {
                return false;
            }
        }
        
        for (DD dd : node.getOutgoingDDEdges()) {
            PDGNode dst = dd.getDstNode();
            if (!dst.equals(node) && !dst.equals(src)) {
                CFGStatement srcSt = (CFGStatement)src.getCFGNode();
                CFGStatement dstSt = (CFGStatement)dst.getCFGNode();
                for (JVariableReference def : srcSt.getDefVariables()) {
                    if (def.isInProject() && dstSt.getUseVariables().contains(def)) {
                        if (nodesInSlice.contains(dst)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private void addReachableMethodCalls(PDGNode node, JVariableReference jv) {
        for (PDGNode callNode : getReachableMethodCalls(node, jv)) {
            addReachableMethodCall(callNode);
        }
    }
    
    private void addReachableMethodCall(PDGNode callNode) {
        reachableMethodCalls.add(callNode);
        checkPendingNodes(callNode);
    }
    
    private void checkPendingNodes(PDGNode callNode) {
        for (PDGNode pendingNode : new HashSet<PDGNode>(pendingNodes)) {
            for (PDGNode node : getDominantCallNodes(pendingNode)) {
                if (callNode.equals(node)) {
                    pendingNodes.remove(pendingNode);
                    traverseBackward(pendingNode);
                }
            }
        }
    }
    
    private Set<PDGNode> getDominantCallNodes(PDGNode node) {
        Set<PDGNode> nodes = new HashSet<>();
        if (node.getCFGNode().isReceiver()) {
            nodes.add(getDominatedNode(node));
        } else if (node.getCFGNode().isActual()) {
            nodes.add(getDominantNode(node));
        } else {
            nodes.addAll(getMethodCalls(node));
        }
        return nodes;
    }
    
    private boolean reachableTo(PDGNode node) {
        return !nodesInTargetMethod.contains(node.getCFGNode()) ||
                reachableNodesToCriterion.contains(node.getCFGNode());
    }
    
    private boolean checkTraversable(PDGNode node) {
        if (reachableMethodCalls.contains(node)) {
            return true;
        }
        
        for (PDGNode callNode : collectMethodCalls(node)) {
            if (reachableMethodCalls.contains(callNode)) {
                return true;
            }
        }
        return false;
    }
    
    private PDGNode getDominantNode(PDGNode node) {
        for (CD edge : node.getIncomingCDEdges()) {
            if (edge.isTrue() || edge.isFalse()) {
                return edge.getSrcNode();
            }
        }
        return null;
    }
    
    private PDGNode getDominatedNode(PDGNode node) {
        for (CD edge : node.getOutgoingCDEdges()) {
            if (edge.isTrue() || edge.isFalse()) {
                return edge.getDstNode();
            }
        }
        return null;
    }
    
    private Set<PDGNode> getReachableMethodCalls(PDGNode node, JVariableReference jv) {
        Set<PDGNode> nodes = new HashSet<>();
        for (PDGNode callNode : collectMethodCalls(node)) {
            if (reachableMethodCall(callNode, jv)) {
                nodes.add(callNode);
            }
        }
        return nodes;
    }
    
    private boolean reachableMethodCall(PDGNode callNode, JVariableReference jv) {
        if (!reachableTo(callNode)) {
            return false;
        }
        
        for (DD edge : callNode.getOutgoingDDEdges()) {
            if (edge.isOutput() &&
                jv.getQualifiedName().equals(edge.getVariable().getQualifiedName()) &&
                reachableTo(getDominantNode(edge.getDstNode()))) {
                return false;
            }
        }
        
        CFGMethodCall call = (CFGMethodCall)callNode.getCFGNode();
        CFGReceiver receiverNode = call.getReceiver();
        if (receiverNode != null) {
            if (nodesInSlice.contains(receiverNode.getPDGNode())) {
                return true;
            }
        }
        return false;
    }
    
    private Set<PDGNode> collectMethodCalls(PDGNode node) {
        Set<PDGNode> nodes = new HashSet<>();
        for (PDGNode callNode : getMethodCalls(node)) {
            collectMethodCalls(callNode, nodes);
        }
        return nodes;
    }
    
    private void collectMethodCalls(PDGNode callNode, Set<PDGNode> visited) {
        if (visited.contains(callNode)) {
            return;
        }
        
        visited.add(callNode);
        for (PDGNode callNode2 : getMethodCalls(callNode)) {
            collectMethodCalls(callNode2, visited);
        }
    }
    
    private Set<PDGNode> getMethodCalls(PDGNode node) {
        Set<PDGNode> nodes = new HashSet<>();
        if (node.getCFGNode().isMethodCall()) {
            nodes.add(node);
        } else {
            PDGNode entry = getMethodEntry(node);
            if (entry != null) {
                for (Dependence edge : entry.getIncomingDependeceEdges()) {
                    if (edge.isCall()) {
                        nodes.add(edge.getSrcNode());
                    }
                }
            }
        }
        return nodes;
    }
    
    private PDGNode getMethodEntry(PDGNode node) {
        while (node != null &&
                !node.getCFGNode().isMethodEntry() &&
                !node.getCFGNode().isConstructorEntry() &&
                !node.getCFGNode().isInitializerEntry()) {
            node = getDominantNode(node);
        }
        return node;
    }
    
    public void print() {
        System.out.println(toString());
    }
    
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("----- Slice (from here) -----\n");
        buf.append("Node = " + getCriterionNode().getId() +
                   "; Variable = " + getVariableNames(getCriterionVariables()));
        buf.append("\n");
        buf.append(getNodeInfo());
        buf.append("----- Slice (to here) -----\n");
        return buf.toString();
    }
    
    private String getNodeInfo() {
        StringBuilder buf = new StringBuilder();
        GraphNode.sortGraphNode(nodesInSlice).forEach(node -> {
            PDGNode pdgnode = (PDGNode)node;
            buf.append(node.toString() + "@" + pdgnode.getCFGNode().getASTNode().getStartPosition());
            buf.append("\n");
        });
        return buf.toString();
    }
    
    private String getVariableNames(Set<JVariableReference> jvs) {
        StringBuilder buf = new StringBuilder();
        jvs.forEach(jv -> buf.append(" " + jv.getName() + "@" + jv.getASTNode().getStartPosition()));
        return buf.toString();
    }
}
