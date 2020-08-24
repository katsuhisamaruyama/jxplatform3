/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.slice;

import org.jtool.pdg.CD;
import org.jtool.pdg.ClDG;
import org.jtool.pdg.CommonPDG;
import org.jtool.pdg.DD;
import org.jtool.pdg.Dependence;
import org.jtool.pdg.PDG;
import org.jtool.pdg.PDGNode;
import org.jtool.pdg.PDGStatement;
import org.jtool.pdg.SDG;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.CommonCFG;
import org.jtool.cfg.JReference;
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
    private Set<CFGNode> nodesInTargetMethod;
    private Set<CFGNode> reachableNodesToCriterion;
    private Set<PDGNode> reachableMethodCalls = new HashSet<>();
    private Set<PDGNode> nodesInSlice = new HashSet<>();
    
    public Slice(SliceCriterion criterion) {
        this.criterion = criterion;
        
        pdgForTargetMethod = getPDGForMethod();
        nodesInTargetMethod = pdgForTargetMethod.getCFG().getNodes();
        reachableNodesToCriterion = criterion.getPDG().getCFG().backwardReachableNodes(criterion.getNode().getCFGNode(), true);
        
        criterion.getVariables().forEach(var -> extract(criterion.getNode(), var));
    }
    
    private PDG getPDGForMethod() {
        CommonPDG pdg = criterion.getPDG();
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
    
    public CommonPDG getPDG() {
        return criterion.getPDG();
    }
    
    public PDGNode getCriterionNode() {
        return criterion.getNode();
    }
    
    public Set<JReference> getCriterionVariables() {
        return criterion.getVariables();
    }
    
    public Set<PDGNode> getNodes() {
        return nodesInSlice;
    }
    
    private void extract(PDGNode node, JReference jv) {
        Set<PDGNode> startnodes = findStartNode(node, jv);
        startnodes.forEach(n -> System.err.println("START = " + n));
        
        for (PDGNode start : startnodes) {
            traverseBackward(start);
        }
        
        if (node.getCFGNode().isActual()) {
            for (CD edge : getIncomingNormalCD(node.getIncomingCDEdges())) {
                for (CD edge2 : getIncomingNormalCD(edge.getSrcNode().getIncomingCDEdges())) {
                    for (CD edge3 : getIncomingNormalCD(edge2.getSrcNode().getIncomingCDEdges())) {
                        traverseBackward(edge3.getSrcNode());
                    }
                }
            }
            
        } else if (node.getCFGNode().isMethodCall()) {
            for (CD edge : getIncomingNormalCD(node.getIncomingCDEdges())) {
                for (CD edge2 : getIncomingNormalCD(edge.getSrcNode().getIncomingCDEdges())) {
                    traverseBackward(edge2.getSrcNode());
                }
            }
            
        } else {
            for (CD edge : getIncomingNormalCD(node.getIncomingCDEdges())) {
                traverseBackward(edge.getSrcNode());
            }
        }
        
        for (PDGNode start : startnodes) {
            for (CD edge : start.getIncomingCDEdges()) {
                traverseBackward(edge.getSrcNode());
            }
        }
    }
    
    private List<CD> getIncomingNormalCD(Set<CD> edges) {
        return edges.stream()
                .filter(edge -> edge.isTrue() || edge.isFalse() || edge.isFallThrough())
                .collect(Collectors.toList());
    }
    
    private Set<PDGNode> findStartNode(PDGNode node, JReference jv) {
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
        
        CommonCFG cfg = criterion.getPDG().getCFG();
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
            return;
        }
        
        node.getSrcNodes().forEach(src -> System.err.println("TRAVERSE = " + src + " -> " + node));
        
        nodesInSlice.add(node);
        
        if (node.getCFGNode().isActualOut()) {
            PDGNode callNode = getDominantNode(node);
            reachableMethodCalls.add(callNode);
            
        } /*else if (node.getCFGNode().isMethodCall()) {
            
            CFGMethodCall call = (CFGMethodCall)node.getCFGNode();
            
            System.err.println("CALL = " + call);
            
            if (!mayChangeState(call)) {
                Set<JReference> receivers = traversableMethods.get(call.getQualifiedName());
                if (receivers != null) {
                    receivers.clear();
                }
            }
            
            System.err.println("CLEAR[");
            printMethods();
            System.err.println("]");
        }
        */
        
        else if (node.getCFGNode().isCatch()) {
            for (Dependence edge : node.getIncomingDependeceEdges()) {
                PDGNode src = edge.getSrcNode();
                if (src.getCFGNode().isMethodCall()) {
                    reachableMethodCalls.add(src);
                }
            }
        }
        
        /*
        for (Dependence edge : node.getIncomingDependeceEdges()) {
            PDGNode src = edge.getSrcNode();
            
            if (edge.isFieldAccess() && src.getCFGNode().isMethodCall()) {
                DD dd = (DD)edge;
                CFGMethodCall callNode = (CFGMethodCall)src.getCFGNode();
                if (!callNode.getMethodCall().getQualifiedName().equals(pdgForTargetMethod.getQualifiedName()) &&
                    canTraverseMethodCall(src, dd.getVariable())) {
                    callNodes.add(src);
                }
            }
        }
        */
        
        for (Dependence edge : Dependence.sortDependenceEdges(node.getIncomingDependeceEdges())) {
            PDGNode src = edge.getSrcNode();
            
            if (edge.isCD()) {
                traverseBackward(src);
                
            } else if (edge.isLIDD() || edge.isLCDD()) {
                if (src.getCFGNode().isMethodCall()) {
                    if (reachableTo(src)) {
                        traverseBackward(src);
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
                if (reachableTo(callNode) && checkTraversable(callNode)) {
                    traverseBackward(src);
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
                if (src.getCFGNode().isMethodCall()) {
                    reachableMethodCalls.addAll(getReachableMethodCalls(src, (DD)edge));
                    reachableMethodCalls.forEach(m -> System.err.println("M= " + m));
                    if (reachableTo(src) && checkTraversable(src)) {
                        traverseBackward(src);
                    }
                    
                } else if (getDominantNode(src).getCFGNode().isFieldEntry()) {
                    traverseBackward(src);
                    
                } else {
                    System.err.println("FACC = " + src + "->" + node);
                    
                    reachableMethodCalls.addAll(getReachableMethodCalls(src, (DD)edge));
                    reachableMethodCalls.forEach(m -> System.err.println("M= " + m));
                    
                    System.err.println("CALLS = " + getMethodCalls(src).size());
                    
                    for (PDGNode callNode : getMethodCalls(src)) {
                        if (reachableTo(callNode) && checkTraversable(callNode)) {
                            traverseBackward(src);
                            break;
                        }
                    }
                }
            }
        }
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
    
    private Set<PDGNode> getReachableMethodCalls(PDGNode node, DD dd) {
        Set<PDGNode> nodes = new HashSet<>();
        for (PDGNode callNode : collectMethodCalls(node)) {
            System.err.println("CHECK = " + callNode);
            
            if (reachableMethodCall(callNode, dd.getVariable())) {
                System.err.println("REGISTER = " + callNode);
                nodes.add(callNode);
            }
        }
        return nodes;
    }
    
    private boolean reachableMethodCall(PDGNode callNode, JReference jv) {
        if (!reachableTo(callNode)) {
            return false;
        }
        
        PDGNode receiver = ((CFGMethodCall)callNode.getCFGNode()).getReceiver().getPDGNode();
        System.err.println("RECEIVER= " + receiver);
        for (DD edge : receiver.getOutgoingDDEdges()) {
            
            System.err.println("EDGE = " + edge);
            
            if (edge.isOutput() &&
                jv.getQualifiedName().equals(edge.getVariable().getQualifiedName()) &&
                reachableTo(getDominantNode(edge.getDstNode()))) {
                return false;
            }
        }
        return true;
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
    
    private String getVariableNames(Set<JReference> jvs) {
        StringBuilder buf = new StringBuilder();
        jvs.forEach(jv -> buf.append(" " + jv.getName() + "@" + jv.getASTNode().getStartPosition()));
        return buf.toString();
    }
}
