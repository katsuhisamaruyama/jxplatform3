/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

/**
 * An edge object that represents an edge connecting two nodes in different PDGs
 * 
 * @author Katsuhisa Maruyama
 */
public abstract class InterPDGEdge implements DependencyGraphEdge {
    
    /**
     * The kind of this edge.
     */
    protected DependencyGraphEdgeKind kind = DependencyGraphEdgeKind.undefined;
    
    /**
     * The source node of this edge.
     */
    protected PDGNode src;
    
    /**
     * The destination node of this edge.
     */
    protected PDGNode dst;
    
    /**
     * Creates a new object that represents an edge connecting two nodes in different PDGs.
     * @param src the source node of this edge
     * @param dst the destination node of this edge
     */
    public InterPDGEdge(PDGNode src, PDGNode dst) {
        assert src != null;
        assert dst != null;
        
        this.src = src;
        this.dst = dst;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInterPDGEdge() {
        return true;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public long getSrcId() {
        return src.getId();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public long getDstId() {
        return dst.getId();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public PDGNode getSrcNode() {
        return src;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public PDGNode getDstNode() {
        return dst;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setKind(DependencyGraphEdgeKind kind) {
        this.kind = kind;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public DependencyGraphEdgeKind getKind() {
        return kind;
    }
    
    /**
     * Sets as an edge between class and its member.
     */
    public void setClassMember() {
        kind = DependencyGraphEdgeKind.classMember;
    }
    
    /**
     * Sets as a call edge.
     */
    public void setCall() {
        kind = DependencyGraphEdgeKind.call;
    }
    
    /**
     * Sets as an exception catch edge.
     */
    public void setExceptionCatch() {
        kind = DependencyGraphEdgeKind.exceptionCatch;
    }
    
    /**
     * Sets as a parameter-in dependence.
     */
    public void setParameterIn() {
        kind = DependencyGraphEdgeKind.parameterIn;
    }
    
    /**
     * Sets as a parameter-out dependence.
     */
    public void setParameterOut() {
        kind = DependencyGraphEdgeKind.parameterOut;
    }
    
    /**
     * Sets as a field access dependence.
     */
    public void setFieldAccess() {
        kind = DependencyGraphEdgeKind.fieldAccess;
    }
    
    /**
     * Sets as an uncovered field access dependence.
     */
    public void setUncoveredFieldAccess() {
        kind = DependencyGraphEdgeKind.uncoveredFieldAccess;
    }
    
    /**
     * Sets as a summary data dependence.
     */
    public void setSummary() {
        kind = DependencyGraphEdgeKind.summary;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof InterPDGEdge) ? equals((InterPDGEdge)obj) : false;
    }
    
    /**
     * Tests if a given edge is equal to this edge.
     * @param edge the edge to be checked
     * @return the {@code true} if the given edge is equal to this edge
     */
    public boolean equals(InterPDGEdge edge) {
        return edge != null && (this == edge || (src.equals(edge.src) && dst.equals(edge.dst)));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Long.valueOf(src.getId() + dst.getId()).hashCode();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(src.getId());
        buf.append(" -> ");
        buf.append(dst.getId());
        return buf.toString();
    }
}
