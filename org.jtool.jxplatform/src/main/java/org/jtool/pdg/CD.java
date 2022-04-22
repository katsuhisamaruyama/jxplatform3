/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

/**
 * An edge represents control dependence (CD) between PDG nodes.
 * 
 * @author Katsuhisa Maruyama
 */
public class CD extends Dependence {
    
    /**
     * Creates a new object that represents a control dependence.
     * @param src the source node
     * @param dst destination node
     */
    public CD(PDGNode src, PDGNode dst) {
        super(src, dst);
    }
    
    /**
     * Sets as a true control dependence.
     */
    public void setTrue() {
        kind = Kind.trueControlDependence;
    }
    
    /**
     * Sets as a false control dependence.
     */
    public void setFalse() {
        kind = Kind.falseControlDependence;
    }
    
    /**
     * Sets as a fall-through control dependence.
     */
    public void setFallThrough() {
        kind = Kind.fallThroughControlDependence;
    }
    
    /**
     * Sets as a declaration edge.
     */
    public void setDeclaration() {
        kind = Kind.declaration;
    }
    
    /**
     * Sets as an exception catch edge.
     */
    public void setExceptionCatch() {
        kind = Kind.exceptionCatch;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(super.toString());
        if (kind == Kind.trueControlDependence) {
            buf.append(" T");
        } else if (kind == Kind.falseControlDependence) {
            buf.append(" F");
        } else if (kind == Kind.fallThroughControlDependence) {
            buf.append(" FALL");
        } else if (kind == Kind.declaration) {
            buf.append(" DECL");
        } else if (kind == Kind.exceptionCatch) {
            buf.append(" EXCP");
        }
        return buf.toString();
    }
}
