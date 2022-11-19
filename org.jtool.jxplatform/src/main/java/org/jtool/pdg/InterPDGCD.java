/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

/**
 * An edge object for a graph.
 * 
 * @author Katsuhisa Maruyama
 */
public class InterPDGCD extends InterPDGEdge {
    
    /**
     * Creates a new object that represents an edge with respect to control flow.
     * @param src the source node of this edge
     * @param dst the destination node of this edge
     */
    public InterPDGCD(PDGNode src, PDGNode dst) {
        super(src, dst);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setClassMember() {
        kind = DependencyGraphEdgeKind.classMember;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setCall() {
        kind = DependencyGraphEdgeKind.call;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setExceptionCatch() {
        kind = DependencyGraphEdgeKind.exceptionCatch;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(super.toString());
        if (kind == DependencyGraphEdgeKind.classMember) {
            buf.append(" MEMBER");
        } else if (kind == DependencyGraphEdgeKind.call) {
            buf.append(" CALL");
        } else if (kind == DependencyGraphEdgeKind.exceptionCatch) {
            buf.append(" EXCP");
        } else if (kind == DependencyGraphEdgeKind.parameterIn) {
        }
        return buf.toString();
    }
}
