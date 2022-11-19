/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.cfg.JVariableReference;

/**
 * An edge object for a graph.
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
        kind = DependencyGraphEdgeKind.parameterIn;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setParameterOut() {
        kind = DependencyGraphEdgeKind.parameterOut;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setFieldAccess() {
        kind = DependencyGraphEdgeKind.fieldAccess;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setUncoveredFieldAccess() {
        kind = DependencyGraphEdgeKind.uncoveredFieldAccess;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setSummary() {
        kind = DependencyGraphEdgeKind.summary;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(super.toString());
        if (kind == DependencyGraphEdgeKind.parameterIn) {
            buf.append(" PIN");
        } else if (kind == DependencyGraphEdgeKind.parameterOut) {
            buf.append(" POUT");
        } else if (kind == DependencyGraphEdgeKind.fieldAccess) {
            buf.append(" FACC");
        } else if (kind == DependencyGraphEdgeKind.uncoveredFieldAccess) {
            buf.append(" CFACC");
        } else if (kind == DependencyGraphEdgeKind.summary) {
            buf.append(" SUMM");
        }
        return buf.toString();
    }
}
