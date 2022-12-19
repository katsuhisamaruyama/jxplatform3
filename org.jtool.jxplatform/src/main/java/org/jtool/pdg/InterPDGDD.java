/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.cfg.JVariableReference;

/**
 * A data dependence edge connecting two nodes in different PDGs.
 * 
 * @author Katsuhisa Maruyama
 */
public class InterPDGDD extends InterPDGEdge {
    
    /**
     * The variable related to this data dependence.
     */
    private JVariableReference jvar = null;
    
    /**
     * Creates a new object that represents an edge with respect to data flow.
     * @param src the source node of this edge
     * @param dst the destination node of this edge
     * @param jv the variable related to this data dependence
     */
    public InterPDGDD(PDGNode src, PDGNode dst, JVariableReference jv) {
        super(src, dst);
        this.jvar = jv;
    }
    
    /**
     * Creates a new object that represents an edge with respect to data flow.
     * @param src the source node of this edge
     * @param dst the destination node of this edge
     */
    public InterPDGDD(PDGNode src, PDGNode dst) {
        this(src, dst, null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public JVariableReference getVariable() {
        return jvar;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setParameterIn() {
        kind = DependencyGraphEdge.Kind.parameterIn;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setParameterOut() {
        kind = DependencyGraphEdge.Kind.parameterOut;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setFieldAccess() {
        kind = DependencyGraphEdge.Kind.fieldAccess;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setUncoveredFieldAccess() {
        kind = DependencyGraphEdge.Kind.uncoveredFieldAccess;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setSummary() {
        kind = DependencyGraphEdge.Kind.summary;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(super.toString());
        if (kind == DependencyGraphEdge.Kind.parameterIn) {
            buf.append(" PIN");
        } else if (kind == DependencyGraphEdge.Kind.parameterOut) {
            buf.append(" POUT");
        } else if (kind == DependencyGraphEdge.Kind.fieldAccess) {
            buf.append(" FACC");
        } else if (kind == DependencyGraphEdge.Kind.uncoveredFieldAccess) {
            buf.append(" CFACC");
        } else if (kind == DependencyGraphEdge.Kind.summary) {
            buf.append(" SUMM");
        }
        return buf.toString();
    }
}
