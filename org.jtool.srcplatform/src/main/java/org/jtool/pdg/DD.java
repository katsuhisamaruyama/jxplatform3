/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.cfg.JReference;
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
    protected JReference jvar;
    
    /**
     * The loop-carried node for this data dependence.
     */
    private PDGNode loopCarriedNode = null;
    
    /**
     * Creates a new object that represents a data dependence.
     * @param src the source node
     * @param dst destination node
     */
    public DD(PDGNode src, PDGNode dst) {
        super(src, dst);
    }
    
    /**
     * Creates a new object that represents a data dependence.
     * @param src the source node
     * @param dst destination node
     * @param jv the variable related to this data dependence
     */
    public DD(PDGNode src, PDGNode dst, JReference jv) {
        super(src, dst);
        this.jvar = jv;
    }
    
    /**
     * Sets the variable related to this data dependence.
     * @param jv the variable to be set
     */
    public void setVariable(JReference jv) {
        this.jvar = jv;
    }
    
    /**
     * Returns the variable related to this data dependence.
     * The value of a variable defined in the source node reaches that used in the destination node.
     * @return the variable storing the passed value
     */
    public JReference getVariable() {
        return jvar;
    }
    
    /**
     * Sets the loop-carried node for this data dependence.
     * @param node loop-carried node
     */
    public void setLoopCarriedNode(PDGNode node) {
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
     * {@code true} if the edge is a loop-carried dependence, otherwise {@code false}
     */
    public boolean isLoopCarried() {
        return loopCarriedNode != null;
    }
    
    /**
     * Tests if this dependence edge is independent to a loop.
     * {@code true} if the edge is a loop-independent dependence, otherwise {@code false}
     */
    public boolean isLoopIndependent() {
        return loopCarriedNode == null;
    }
    
    /**
     * Sets as a loop-independent data dependence.
     */
    public void setLIDD() {
        kind = Kind.loopIndependentDefUseDependence;
    }
    
    /**
     * Sets as a loop-carried data dependence.
     */
    public void setLCDD() {
        kind = Kind.loopCarriedDefUseDependence;
    }
    
    /**
     * Sets as a define-order dependence.
     */
    public void setDefOrder() {
        kind = Kind.defOrderDependence;
    }
    
    /**
     * Sets as an output dependence.
     */
    public void setOutput() {
        kind = Kind.outputDependence;
    }
    
    /**
     * Sets as a parameter-in dependence.
     */
    public void setParameterIn() {
        kind = Kind.parameterIn;
    }
    
    /**
     * Sets as a parameter-out dependence.
     */
    public void setParameterOut() {
        kind = Kind.parameterOut;
    }
    
    /**
     * Sets as a field access dependence.
     */
    public void setFieldAccess() {
        kind = Kind.fieldAccess;
    }
    
    /**
     * Sets as a summary data dependence.
     */
    public void setSummary() {
        kind = Kind.summary;
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
     * Creates a clone of this object.
     * @return the created clone
     */
    @Override
    public DD clone() {
        DD cloneEdge = new DD(getSrcNode(), getDstNode());
        super.setClone(cloneEdge);
        setClone(cloneEdge);
        return cloneEdge;
    }
    
    /**
     * Copies information on this edge into a given clone.
     * @param cloneEdge the clone of this edge
     */
    protected void setClone(DD cloneEdge) {
        cloneEdge.setVariable(getVariable());
        cloneEdge.setLoopCarriedNode(getLoopCarriedNode());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(super.toString());
        buf.append(" [ ");
        buf.append(jvar.getReferenceForm());
        if (isLoopCarried()) {
            buf.append(" LC = ");
            buf.append(getLoopCarriedNode().getId());
        }
        buf.append(" ]");
        if (kind == Kind.loopIndependentDefUseDependence) {
            buf.append(" LIDD");
        } else if (kind == Kind.loopCarriedDefUseDependence) {
            buf.append(" LCDD");
        } else if (kind == Kind.defOrderDependence) {
            buf.append(" DO");
        } else if (kind == Kind.outputDependence) {
            buf.append(" OD");
        } else if (kind == Kind.parameterIn) {
            buf.append(" PIN");
        } else if (kind == Kind.parameterOut) {
            buf.append(" POUT");
        } else if (kind == Kind.fieldAccess) {
            buf.append(" FACC");
        } else if (kind == Kind.summary) {
            buf.append(" SUMM");
        }
        return buf.toString();
    }
}
