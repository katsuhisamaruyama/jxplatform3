/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.graph.GraphEdge;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

/**
 * An edge of CFGs, which represents a control flow between CFG nodes.
 * 
 * @author Katsuhisa Maruyama
 */
public class ControlFlow extends GraphEdge {
    
    /**
     * The node that causes the loop-back when this is a loop-back edge.
     */
    protected CFGNode loopback = null;
    
    /**
     * The kind of this control flow edge.
     */
    protected Kind kind = Kind.undefined;
    
    /**
     * The kind of a control flow edge.
     */
    public enum Kind {
        trueControlFlow,                 // Control flow outgoing to a true-branch
        falseControlFlow,                // Control flow outgoing to a false-branch
        fallThroughFlow,                 // Control flow representing a fall-through
        callFlow,                        // Flow representing the call to a method
        exceptionCatchFlow,              // Flow representing the relationship between an exception occurrence and its catch
        undefined,
    }
    
    /**
     * Creates a new object that represents a control flow edge.
     * This method is not intended to be invoked by clients.
     * @param src the source node of this edge
     * @param dst the destination node of this edge
     */
    public ControlFlow(CFGNode src, CFGNode dst) {
        super(src, dst);
    }
    
    /**
     * Sets the kind of this control flow.
     * This method is not intended to be invoked by clients.
     * @param kind the kind of this edge
     */
    public void setKind(Kind kind) {
        this.kind = kind;
    }
    
    /**
     * Returns the kind of this control flow.
     * @return the kind of the edge
     */
    public ControlFlow.Kind getKind() {
        return kind;
    }
    
    /**
     * Sets as this edge is a true control flow.
     * This method is not intended to be invoked by clients.
     */
    public void setTrue() {
        kind = Kind.trueControlFlow;
    }
    
    /**
     * Tests if this edge represents a true control flow.
     * @return {@code true} if this is a true edge, otherwise {@code false}
     */
    public boolean isTrue() {
        return kind == Kind.trueControlFlow;
    }
    
    /**
     * Sets as this edge is a false control flow.
     * This method is not intended to be invoked by clients.
     */
    public void setFalse() {
        kind = Kind.falseControlFlow;
    }
    
    /**
     * Tests if this edge represents a false control flow.
     * @return {@code true} if this is a false edge, otherwise {@code false}
     */
    public boolean isFalse() {
        return kind == Kind.falseControlFlow;
    }
    
    /**
     * Sets as this edge is a fall-through control flow.
     * This method is not intended to be invoked by clients.
     */
    public void setFallThrough() {
        kind = Kind.fallThroughFlow;
    }
    
    /**
     * Tests if this edge represents a fall-through control flow.
     * @return {@code true} if this is a fall-through edge, otherwise {@code false}
     */
    public boolean isFallThrough() {
        return kind == Kind.fallThroughFlow;
    }
    
    /**
     * Sets as this edge is a method call flow.
     * This method is not intended to be invoked by clients.
     */
    public void setCall() {
        kind = Kind.callFlow;
    }
    
    /**
     * Tests if this edge represents a call flow.
     * @return {@code true} if this is a call edge, otherwise {@code false}
     */
    public boolean isCall() {
        return kind == Kind.callFlow;
    }
    
    /**
     * Sets as this edge is an exception flow.
     * This method is not intended to be invoked by clients.
     */
    public void setExceptionCatch() {
        kind = Kind.exceptionCatchFlow;
    }
    
    /**
     * Tests if this edge represents an exception flow.
     * @return {@code true} if this is an exception edge, otherwise {@code false}
     */
    public boolean isExceptionCatch() {
        return kind == Kind.exceptionCatchFlow;
    }
    
    /**
     * Returns the source node of this control flow edge.
     * @return the source node
     */
    @Override
    public CFGNode getSrcNode() {
        return (CFGNode)src;
    }
    
    /**
     * Returns the destination node of this control flow edge.
     * @return the destination node
     */
    @Override
    public CFGNode getDstNode() {
        return (CFGNode)dst;
    }
    
    /**
     * Sets a node that causes the loop-back.
     * This method is not intended to be invoked by clients.
     * @param node the node for the loop-back edge
     */
    public void setLoopBack(CFGNode node) {
        loopback = node;
    }
    
    /**
     * Returns the node that causes the loop-back.
     * @return the node for the loop-back edge
     */
    public CFGNode getLoopBack() {
        return loopback;
    }
    
    /**
     * Tests if this is a loop-back edge.
     * @return {@code true} if this is a loop-back edge, otherwise {@code false}
     */
    public boolean isLoopBack() {
        return loopback != null;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ControlFlow) ? equals((ControlFlow)obj) : false;
    }
    
    /**
     * Tests if a given control flow edge is equal to this edge.
     * @param flow the flow edge to be checked
     * @return the {@code true} if the given edge is equal to this edge
     */
    public boolean equals(ControlFlow flow) {
        return flow != null && (super.equals((GraphEdge)flow) && kind == flow.kind);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Long.valueOf(src.getId() + dst.getId()).hashCode();
    }
    
    /**
     * Displays information on this control flow.
     */
    public void print() {
        System.out.println(toString());
    }
    
    /**
     * Obtains information on this control flow.
     * @return the string representing the information
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(String.valueOf(src.getId()) + " -> " + String.valueOf(dst.getId()));
        if (getKind() != null) {
            buf.append(" " + getKind().toString());
        }
        if (loopback != null) {
            buf.append(" (L = " + String.valueOf(getLoopBack().getId()) + ")");
        }
        return buf.toString();
    }
    
    /**
     * Sorts the list of control flow edges
     * @param co the list of the control flow edges to be sorted
     * @return the sorted list of the control flow edges
     */
    public static List<ControlFlow> sortEdges(Collection<? extends ControlFlow> collection) {
        List<ControlFlow> edges = new ArrayList<>(collection);
        edges.sort(Comparator.comparingLong((ControlFlow edge) -> edge.getSrcId())
                             .thenComparingLong((ControlFlow edge) -> edge.getDstId())
                             .thenComparing((ControlFlow edge) -> edge.getKind().toString()));
        return edges;
    }
}
