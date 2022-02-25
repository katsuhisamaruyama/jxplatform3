/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

/**
 * An edge that represents a class and its members in ClDGs.
 * 
 * @author Katsuhisa Maruyama
 */
public class ClassMemberEdge extends Dependence {
    
    /**
     * Creates a new object that represents a class-member edge.
     * @param src the source node
     * @param dst the destination node
     */
    public ClassMemberEdge(PDGNode src, PDGNode dst) {
        super(src, dst);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(super.toString());
        buf.append(" MEMBER");
        return buf.toString();
    }
}
