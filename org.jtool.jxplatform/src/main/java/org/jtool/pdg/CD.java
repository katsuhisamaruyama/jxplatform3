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
        kind = DependencyGraphEdgeKind.trueControlDependence;
    }
    
    /**
     * Sets as a false control dependence.
     */
    public void setFalse() {
        kind = DependencyGraphEdgeKind.falseControlDependence;
    }
    
    /**
     * Sets as a fall-through control dependence.
     */
    public void setFallThrough() {
        kind = DependencyGraphEdgeKind.fallThroughControlDependence;
    }
    
    /**
     * Sets as a declaration edge.
     */
    public void setDeclaration() {
        kind = DependencyGraphEdgeKind.declaration;
    }
    
    /**
     * Sets as an exception catch edge.
     */
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
        if (kind == DependencyGraphEdgeKind.trueControlDependence) {
            buf.append(" T");
        } else if (kind == DependencyGraphEdgeKind.falseControlDependence) {
            buf.append(" F");
        } else if (kind == DependencyGraphEdgeKind.fallThroughControlDependence) {
            buf.append(" FALL");
        } else if (kind == DependencyGraphEdgeKind.declaration) {
            buf.append(" DECL");
        } else if (kind == DependencyGraphEdgeKind.exceptionCatch) {
            buf.append(" EXCP");
        }
        return buf.toString();
    }
}
