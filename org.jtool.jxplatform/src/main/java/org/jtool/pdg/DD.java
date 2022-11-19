/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.cfg.JVariableReference;
import org.jtool.graph.GraphElement;

/**
 * An edge that represents data dependence (DD) between PDG nodes.
 * 
 * @author Katsuhisa Maruyama
 */
public class DD extends Dependence {
    
    /**
     * The variable related to this data dependence.
     */
    private JVariableReference jvar = null;
    
    /**
     * The loop-carried node for this data dependence.
     */
    private PDGNode loopCarriedNode = null;
    
    /**
     * Creates a new object that represents a data dependence.
     * @param src the source node
     * @param dst destination node
     * @param jv the variable related to this data dependence
     */
    public DD(PDGNode src, PDGNode dst, JVariableReference jv) {
        super(src, dst);
        
        assert jv != null;
        
        this.jvar = jv;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public JVariableReference getVariable() {
        return jvar;
    }
    
    /**
     * Sets the loop-carried node for this data dependence.
     * @param node loop-carried node
     */
    public void setLoopCarriedNode(PDGNode node) {
        assert node != null;
        
        loopCarriedNode = node;
    }
    
    /**
     * Returns the loop-carried node for this data dependence.
     * @return the loop-carried node, or {@code null} if this edge does not have a loop-carried node
     */
    public PDGNode getLoopCarriedNode() {
        return loopCarriedNode;
    }
    
    /**
     * Tests if this dependence edge is carried by a loop.
     * @return {@code true} if the edge is a loop-carried dependence, otherwise {@code false}
     */
    public boolean isLoopCarried() {
        return loopCarriedNode != null;
    }
    
    /**
     * Tests if this dependence edge is independent to a loop.
     * @return {@code true} if the edge is a loop-independent dependence, otherwise {@code false}
     */
    public boolean isLoopIndependent() {
        return loopCarriedNode == null;
    }
    
    /**
     * Sets as a loop-independent data dependence.
     */
    public void setLIDD() {
        kind = DependencyGraphEdgeKind.loopIndependentDefUseDependence;
    }
    
    /**
     * Sets as a loop-carried data dependence.
     */
    public void setLCDD() {
        kind = DependencyGraphEdgeKind.loopCarriedDefUseDependence;
    }
    
    /**
     * Sets as a define-order dependence.
     */
    public void setDefOrder() {
        kind = DependencyGraphEdgeKind.defOrderDependence;
    }
    
    /**
     * Sets as an output dependence.
     */
    public void setOutput() {
        kind = DependencyGraphEdgeKind.outputDependence;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(GraphElement elem) {
        return (elem instanceof DD) ? equals((DD)elem) : false;
    }
    
    /**
     * Tests if a given dependence edge is equal to this edge.
     * @param edge the edge to be checked
     * @return the {@code true} if the given edge is equal to this edge
     */
    public boolean equals(DD edge) {
        if (jvar == null) {
            return edge != null && super.equals((Dependence)edge);
        }
        return edge != null && (super.equals((Dependence)edge) && jvar.equals(edge.jvar));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(super.toString());
        if (jvar != null) {
            buf.append(" [ ");
            buf.append(jvar.getReferenceForm());
            if (isLoopCarried()) {
                buf.append(" LC = ");
                buf.append(getLoopCarriedNode().getId());
            }
            buf.append(" ]");
        }
        if (kind == DependencyGraphEdgeKind.loopIndependentDefUseDependence) {
            buf.append(" LIDD");
        } else if (kind == DependencyGraphEdgeKind.loopCarriedDefUseDependence) {
            buf.append(" LCDD");
        } else if (kind == DependencyGraphEdgeKind.defOrderDependence) {
            buf.append(" DO");
        } else if (kind == DependencyGraphEdgeKind.outputDependence) {
            buf.append(" OD");
        } else if (kind == DependencyGraphEdgeKind.parameterIn) {
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
