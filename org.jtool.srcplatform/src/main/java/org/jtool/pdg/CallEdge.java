/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

/**
 * An edge that represents a relationship between caller and callee in ClDGs and SDGs.
 * 
 * @author Katsuhisa Maruyama
 */
public class CallEdge extends Dependence {
    
    /**
     * Creates a new object that represents a call relation.
     * @param src the caller node
     * @param dst the callee node
     */
    public CallEdge(PDGNode src, PDGNode dst) {
        super(src, dst);
    }
    
    /**
     * Sets as a call edge.
     */
    public void setCall() {
        kind = Kind.call;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(super.toString());
        buf.append(" CALL");
        return buf.toString();
    }
}
