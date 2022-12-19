/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

/**
 * A control dependence edge connecting two nodes in different PDGs.
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
        kind = DependencyGraphEdge.Kind.classMember;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setCall() {
        kind = DependencyGraphEdge.Kind.call;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setExceptionCatch() {
        kind = DependencyGraphEdge.Kind.exceptionCatch;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(super.toString());
        if (kind == DependencyGraphEdge.Kind.classMember) {
            buf.append(" MEMBER");
        } else if (kind == DependencyGraphEdge.Kind.call) {
            buf.append(" CALL");
        } else if (kind == DependencyGraphEdge.Kind.exceptionCatch) {
            buf.append(" EXCP");
        } else if (kind == DependencyGraphEdge.Kind.parameterIn) {
        }
        return buf.toString();
    }
}
