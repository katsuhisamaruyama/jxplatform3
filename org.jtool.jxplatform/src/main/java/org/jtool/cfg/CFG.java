/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.QualifiedName;
import org.jtool.graph.Graph;
import org.jtool.graph.GraphElement;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * An object storing information on a control flow graph (CFG).
 * 
 * @author Katsuhisa Maruyama
 */
public class CFG extends Graph<CFGNode, ControlFlow> {
    
    /**
     * The entry node of this CFG.
     */
    private CFGEntry entry;
    
    /**
     * The exit node of this CFG.
     */
    private CFGNode exit;
    
    /**
     * Creates a new, empty object for storing a CFG information.
     * This method is not intended to be invoked by clients.
     */
    public CFG() {
        super();
    }
    
    /**
     * The collection of basic blocks existing in this CFG.
     */
    private List<BasicBlock> basicBlocks = new ArrayList<>();
    
    /**
     * Sets the entry node of this CFG.
     * This method is not intended to be invoked by clients.
     * @param node the entry node of this CFG
     */
    public void setEntryNode(CFGEntry node) {
        entry = node;
        entry.setCFG(this);
    }
    
    /**
     * Returns the entry node of this CFG.
     * @return the entry node of this CFG
     */
    public CFGEntry getEntryNode() {
        return entry;
    }
    
    /**
     * Sets the exit node of this CFG.
     * This method is not intended to be invoked by clients.
     * @param node exit end node of this CFG
     */
    public void setExitNode(CFGNode node) {
        exit = node;
    }
    
    /**
     * Returns the exit node of this CFG.
     * @return the exit node of this CFG
     */
    public CFGNode getExitNode() {
        return exit;
    }
    
    /**
     * Returns the identification number of this CFG, which is equals to that of the entry node.
     * @return the identification number of this CFG
     */
    public long getId() {
        return entry.getId();
    }
    
    /**
     * Returns the fully-qualified name of this CFG, which is equals to the fully-qualified
     * of a class, a method, or a field corresponding to this CFG.
     * @return the CFG name.
     */
    public QualifiedName getQualifiedName() {
        return entry.getQualifiedName();
    }
    
    /**
     * Adds a basic block to this CFG.
     * This method is not intended to be invoked by clients.
     * @param block the basic block to be added
     */
    public void add(BasicBlock block) {
        if (!basicBlocks.contains(block)) {
            basicBlocks.add(block);
        }
    }
    
    /**
     * Returns all basic blocks existing in this CFG.
     * @return the collection of the basic blocks
     */
    public List<BasicBlock> getBasicBlocks() {
        return basicBlocks;
    }
    
    /**
     * Clears basic blocks existing in this CFG.
     */
    public void clearBasicBlocks() {
        basicBlocks.clear();
    }
    
    /**
     * Tests if this CFG is created from a method.
     * @return {@code true} if this CFG is created from a method, otherwise {@code false}
     */
    public boolean isMethod() {
        return entry instanceof CFGMethodEntry;
    }
    
    /**
     * Tests if this CFG is created from a field.
     * @return {@code true} if this CFG is created from a field, otherwise {@code false}
     */
    public boolean isField() {
        return entry instanceof CFGFieldEntry;
    }
    
    /**
     * Tests if a given CFG node represents a branch in this CFG.
     * @param node the node to be checked
     * @return {@code true} if the node represents a branch, otherwise {@code false}
     */
    public boolean isBranch(CFGNode node) {
        return node.isBranch();
    }
    
    /**
     * Tests if a given CFG node represents a loop in this CFG.
     * @param node the node to be checked
     * @return {@code true} if the node represents a loop, otherwise {@code false}
     */
    public boolean isLoop(CFGNode node) {
        return node.isLoop();
    }
    
    /**
     * Tests if a given CFG node represents a join in this CFG.
     * @param node the node to be checked
     * @return {@code true} if the node represents a join, otherwise {@code false}
     */
    public boolean isJoinNode(CFGNode node) {
        return node.isJoin();
    }
    
    /**
     * Returns a CFG node with a given identification number.
     * @param id the identification number of the node to be retrieved
     * @return the found node, or {@code null} if no node is found
     */
    public CFGNode getNode(long id) {
        return getNodes().stream()
                         .filter(node -> id == node.getId())
                         .findFirst().orElse(null);
    }
    
    /**
     * Finds an control flow edge specified by given source and destination CFG nodes.
     * @param src the source CFG node for the edge to be retrieved
     * @param dst the destination CFG node for the edge to be retrieved
     * @return the found edge, or {@code null} if no edge is found
     */
    public ControlFlow getFlow(CFGNode src, CFGNode dst) {
        if (src == null || dst == null) {
            return null;
        }
        return getEdges().stream()
                         .filter(edge -> src.equals(edge.getSrcNode()) && dst.equals(edge.getDstNode()))
                         .findFirst().orElse(null);
    }
    
    /**
     * Finds a true control flow edge outgoing from a given node.
     * @param node the CFG node having the outgoing edge
     * @return the found true control flow edge, or {@code null} if no edge is found
     */
    public ControlFlow getTrueFlowFrom(CFGNode node) {
        return getEdges().stream()
                         .filter(edge -> edge.getSrcNode().equals(node) && edge.isTrue())
                         .findFirst().orElse(null);
    }
    
    /**
     * Finds a false control flow edge outgoing from a given node.
     * @param node the CFG node having the outgoing edge
     * @return the found false control flow edge, or {@code null} if no edge is found
     */
    public ControlFlow getFalseFlowFrom(CFGNode node) {
        return getEdges().stream()
                         .filter(edge -> edge.getSrcNode().equals(node) && edge.isFalse())
                         .findFirst().orElse(null);
    }
    
    /**
     * Finds a fall-through control flow edge outgoing from a given node.
     * @param node the CFG node having the outgoing edge
     * @return the found fall-through control flow edge, or {@code null} if no edge is found
     */
    public ControlFlow getFallThroughFlowFrom(CFGNode node) {
        return getEdges().stream()
                         .filter(edge -> edge.getSrcNode().equals(node) && edge.isFallThrough())
                         .findFirst().orElse(null);
    }
    
    /**
     * Finds an exception catch flow edge outgoing from a given node.
     * @param node the CFG node having the outgoing edge
     * @return the found exception catch flow edge, or {@code null} if no edge is found
     */
    public ControlFlow getExceptionCatchFlowFrom(CFGNode node) {
        return getEdges().stream()
                         .filter(edge -> edge.getSrcNode().equals(node) && edge.isExceptionCatch())
                         .findFirst().orElse(null);
    }
    
    /**
     * Returns a successor node of a given CFG node with respect to the true control flow.
     * @param node the source node
     * @return the found successor, or {@code null} if no successor is found
     */
    public CFGNode getTrueSuccessor(CFGNode node) {
        ControlFlow flow = getTrueFlowFrom(node);
        return (flow != null) ? flow.getDstNode() : null;
    }
    
    /**
     * Returns a successor node of a given CFG node with respect to the false control flow.
     * @param node the source node
     * @return the found successor, or {@code null} if no successor is found
     */
    public CFGNode getFalseSuccessor(CFGNode node) {
        ControlFlow flow = getFalseFlowFrom(node);
        return (flow != null) ? flow.getDstNode() : null;
    }
    
    /**
     * Returns a successor node of a given CFG node with respect to the fall-through control flow.
     * @param node the source node
     * @return the found successor, or {@code null} if no successor is found
     */
    public CFGNode getFallThroughSuccessor(CFGNode node) {
        ControlFlow flow = getFallThroughFlowFrom(node);
        return (flow != null) ? flow.getDstNode() : null;
    }
    
    /**
     * Returns a successor node of a given CFG node with respect to the exception catch flow.
     * @param node the source node
     * @return the found successor, or {@code null} if no successor is found
     */
    public CFGNode getExceptionCatchSuccessor(CFGNode node) {
        ControlFlow flow = getExceptionCatchFlowFrom(node);
        return (flow != null) ? flow.getDstNode() : null;
    }
    
    /**
     * Tests if the method of this CFG contains a try statement.
     * @return {@code true} if the method contains a try statement, otherwise {@code false}
     */
    public boolean hasTryStatement(){
        return getNodes().stream().anyMatch(node -> node.isTry());
    }
    
    /**
     * Obtains CFG nodes for method calls.
     * @return the collection of the method call nodes
     */
    public Set<CFGMethodCall> getMethodCallNodes() {
        return getNodes().stream()
                         .filter(node -> node.isMethodCall())
                         .map(node -> (CFGMethodCall)node)
                         .collect(Collectors.toSet());
    }
    
    /**
     * Obtains CFG nodes for field accesses.
     * @return the collection of the field access nodes
     */
    public Set<CFGStatement> getFieldAccessNodes() {
        return getNodes().stream()
                         .filter(node -> hasFieldAccess(node))
                         .map(node -> (CFGStatement)node)
                         .collect(Collectors.toSet());
    }
    
    /**
     * tests if a given node employs any field access.
     * @param node the node to be checked
     * @return {@code true} if a given node employs any field access, otherwise {@code false}
     */
    private boolean hasFieldAccess(CFGNode node) {
        if (node.isStatementNotParameter()) {
            CFGStatement stnode = (CFGStatement)node;
            for (JReference jv : stnode.getDefVariables()) {
                if (jv.isFieldAccess()) {
                    return true;
                }
            }
            for (JReference jv : stnode.getUseVariables()) {
                if (jv.isFieldAccess()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Obtains CFG nodes for return statements.
     * @return the collection of the return statement nodes
     */
    public Set<CFGStatement> getReturnNodes() {
        return getNodes().stream()
                         .filter(node -> node.isReturn())
                         .map(node -> (CFGStatement)node)
                         .collect(Collectors.toSet());
    }
    
    /**
     * Calculates nodes traversed forward from a given node on this CFG.
     * @param from the source node
     * @param loopbackOk {@code true} if loop-back edges can be traversed, otherwise {@code false}
     * @param condition the condition that stops traversing
     * @return the collection of the traversed nodes
     */
    public Set<CFGNode> forwardReachableNodes(CFGNode from, boolean loopbackOk, StopConditionOnReachablePath condition) {
        Set<CFGNode> track = new HashSet<>();
        if (from != null) {
            walkForward(from, condition, loopbackOk, track);
        }
        return track;
    }
    
    /**
     * Calculates nodes traversed backward from a given node on this CFG.
     * @param from the source node
     * @param loopbackOk {@code true} if loop-back edges can be traversed, otherwise {@code false}
     * @param condition the condition that stops traversing
     * @return the collection of the traversed nodes
     */
    public Set<CFGNode> backwardReachableNodes(CFGNode from, boolean loopbackOk, StopConditionOnReachablePath condition) {
        Set<CFGNode> track = new HashSet<>();
        if (from != null) {
            walkBackward(from, condition, loopbackOk, track);
        }
        return track;
    }
    
    /**
     * Calculates nodes traversed forward from a given node on this CFG with no condition.
     * @param from the source node
     * @param loopbackOk {@code true} if loop-back edges can be traversed, otherwise {@code false}
     * @return the collection of the traversed nodes
     */
    public Set<CFGNode> forwardReachableNodes(CFGNode from, boolean loopbackOk) {
        return forwardReachableNodes(from, loopbackOk, node -> { return false; });
    }
    
    /**
     * Calculates nodes traversed backward from a given node on this CFG with no condition.
     * @param from the source node
     * @param loopbackOk {@code true} if loop-back edges can be traversed, otherwise {@code false}
     * @return the collection of the traversed nodes
     */
    public Set<CFGNode> backwardReachableNodes(CFGNode from, boolean loopbackOk) {
        return backwardReachableNodes(from, loopbackOk, node -> { return false; });
    }
    
    /**
     * Calculates nodes traversed forward between two nodes on this CFG.
     * @param from the source node
     * @param to the destination node
     * @param loopbackOk {@code true} if loop-back edges can be traversed, otherwise {@code false}
     * @return the collection of the traversed nodes
     */
    public Set<CFGNode> forwardReachableNodes(CFGNode from, CFGNode to, boolean loopbackOk) {
        Set<CFGNode> track = new HashSet<CFGNode>();
        if (from.equals(to)) {
            track.add(from);
            return track;
        }
        
        Set<CFGNode> ftrack = forwardReachableNodes(from, loopbackOk, node -> node.equals(to));
        track.addAll(ftrack);
        track.add(to);
        return track;
    }
    
    /**
     * Calculates nodes traversed backward between two nodes on this CFG.
     * @param from the source node
     * @param to the destination node
     * @param loopbackOk {@code true} if loop-back edges can be traversed, otherwise {@code false}
     * @return the collection of the traversed nodes
     */
    public Set<CFGNode> backwardReachableNodes(CFGNode from, CFGNode to, boolean loopbackOk) {
        Set<CFGNode> track = new HashSet<>();
        if (from.equals(to)) {
            track.add(from);
            return track;
        }
        
        Set<CFGNode> btrack = backwardReachableNodes(from, loopbackOk, node -> node.equals(to));
        track.addAll(btrack);
        track.add(to);
        return track;
    }
    
    /**
     * Calculates nodes traversed between two nodes on this CFG, which are reachable from the both nodes.
     * @param from the source node
     * @param to the destination node
     * @param loopbackOk {@code true} if loop-back edges can be traversed, otherwise {@code false}
     * @return the collection of the traversed nodes
     */
    public Set<CFGNode> reachableNodes(CFGNode from, CFGNode to, boolean loopbackOk) {
        Set<CFGNode> ftrack = forwardReachableNodes(from, to, loopbackOk);
        Set<CFGNode> btrack = backwardReachableNodes(to, from, loopbackOk);
        Set<CFGNode> track = GraphElement.intersection(ftrack, btrack);
        return track;
    }
    
    /**
     * Calculates a constrained reachable nodes between two nodes.
     * @param from the source node
     * @param to the destination node
     * @return the collection of the reachable nodes
     */
    public Set<CFGNode> constrainedReachableNodes(CFGNode from, CFGNode to) {
        Set<CFGNode> btrackf = backwardReachableNodes(to, from, true);
        Set<CFGNode> ftrackf = forwardReachableNodes(from, getExitNode(), true);
        Set<CFGNode> fCRP = GraphElement.intersection(btrackf, ftrackf);
        
        Set<CFGNode> ftrackb = forwardReachableNodes(from, to, true);
        Set<CFGNode> btrackb = backwardReachableNodes(to, getExitNode(), true);
        Set<CFGNode> bCRP = GraphElement.intersection(ftrackb, btrackb);
        
        Set<CFGNode> CRP = GraphElement.union(fCRP, bCRP);
        return CRP;
    }
    
    /**
     * Walks forward and collects the traversed nodes.
     * @param startnode the starting node to be traversed
     * @param condition the condition that stops traversing
     * @param loopbackOk {@code true} if loop-back edges can be traversed, otherwise {@code false}
     * @param track the collection of already traversed nodes
     */
    private void walkForward(CFGNode startnode, StopConditionOnReachablePath condition, boolean loopbackOk, Set<CFGNode> track) {
        Stack<CFGNode> nodeStack = new Stack<>();
        nodeStack.push(startnode);
        
        while (!nodeStack.isEmpty()) {
            CFGNode node = nodeStack.pop();
            
            if (condition.isStop(node) || track.contains(node)) {
                continue;
            }
            track.add(node);
            
            node.getOutgoingFlows().stream() 
                .filter(flow -> loopbackOk || !flow.isLoopBack())
                .map(flow -> flow.getDstNode())
                .forEach(succ -> nodeStack.push(succ));
        }
    }
    
    /**
     * Walks backward and collects the traversed nodes.
     * @param startnode the starting node to be traversed
     * @param condition the condition that stops traversing
     * @param loopbackOk {@code true} if loop-back edges can be traversed, otherwise {@code false}
     * @param track the collection of already traversed nodes
     */
    private void walkBackward(CFGNode startnode, StopConditionOnReachablePath condition, boolean loopbackOk, Set<CFGNode> track) {
        Stack<CFGNode> nodeStack = new Stack<>();
        nodeStack.push(startnode);
        
        while (!nodeStack.isEmpty()) {
            CFGNode node = nodeStack.pop();
            
            if (condition.isStop(node) || track.contains(node)) {
                continue;
            }
            track.add(node);
            
            node.getIncomingFlows().stream()
                .filter(flow -> loopbackOk || !flow.isLoopBack())
                .map(flow -> flow.getSrcNode())
                .forEach(pred -> nodeStack.push(pred));
        }
    }
    
    /**
     * Calculates post-dominant nodes for a given CFG.
     * This is a naive implementation.
     * Use Lengauer-Tarjan Dominator Tree Algorithm to make the calculation efficient.
     * @param anchor the anchor node that might dominate other nodes
     * @return the collection of the dominated nodes
     */
    public Set<CFGNode> postDominator(CFGNode anchor) {
        Set<CFGNode> postDominator = new HashSet<>();
        for (CFGNode node : CFGNode.sortCFGNodesInverse(getNodes())) {
            if (!anchor.equals(node)) {
                if (node.getOutgoingFlows().size() == 1) {
                    CFGNode succ = node.getOutgoingFlows().iterator().next().getDstNode();
                    if (succ.getIncomingFlows().size() == 1 && postDominator.contains(succ)) {
                        postDominator.add(node);
                        continue;
                    }
                }
                
                Set<CFGNode> track = forwardReachableNodes(anchor, node, true);
                if (track.contains(node) && !track.contains(getExitNode())) {
                    postDominator.add(node);
                }
            }
        }
        return postDominator;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof CFG) ? equals((CFG)obj) : false;
    }
    
    /**
     * Tests if a given CFG is equal to this CFG.
     * @param cfg the CFG to be checked
     * @return the {@code true} if the given CFG is equal to this CFG
     */
    public boolean equals(CFG cfg) {
        return cfg != null && (this == cfg || getQualifiedName().equals(cfg.getQualifiedName()));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return getQualifiedName().hashCode();
    }
    
    /**
     * Displays information on this graph.
     */
    public void print() {
        System.out.println(toString());
    }
    
    /**
     * Obtains information on this CFG.
     * @return the string representing the information
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("----- CFG of " + getQualifiedName() + "-----\n");
        buf.append(toStringForNodes()); 
        buf.append(toStringForEdges());
        buf.append("-----------------------------------\n");
        return buf.toString();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected String toStringForNodes() {
        StringBuilder buf = new StringBuilder();
        CFGNode.sortCFGNodes(getNodes()).forEach(node -> {
            buf.append(node.toString());
            buf.append("\n");
        });
        return buf.toString();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected String toStringForEdges() {
        StringBuilder buf = new StringBuilder();
        buf.append(GraphElement.getIdString(0));
        buf.append(": ");
        buf.append(entry.getSignature());
        buf.append("\n");
        
        long index = 1;
        for (ControlFlow edge : ControlFlow.sortControlFlowEdges(getEdges())) {
            buf.append(GraphElement.getIdString(index));
            buf.append(": ");
            buf.append(edge.toString());
            buf.append("\n");
            index++;
        }
        return buf.toString();
    }
}
