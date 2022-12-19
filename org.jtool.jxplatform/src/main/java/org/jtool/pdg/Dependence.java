/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.graph.GraphEdge;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

/**
 * A dependence edge of PDGs.
 * 
 * @author Katsuhisa Maruyama
 */
public class Dependence extends GraphEdge implements DependencyGraphEdge {
    
    /**
     * The kind of this dependence edge.
     */
    protected DependencyGraphEdge.Kind kind = DependencyGraphEdge.Kind.undefined;
    
    /**
     * Creates a new object that represents a dependence edge.
     * This method is not intended to be invoked by clients.
     * @param src the source node
     * @param dst the destination node
     */
    public Dependence(PDGNode src, PDGNode dst) {
        super(src, dst);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDependence() {
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
     * Returns the source node of this dependence edge.
     * @return the source node
     */
    @Override
    public PDGNode getSrcNode() {
        return (PDGNode)src;
    }
    
    /**
     * Returns the source node of this dependence edge.
     * @return the source node
     */
    @Override
    public PDGNode getDstNode() {
        return (PDGNode)dst;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setKind(DependencyGraphEdge.Kind kind) {
        this.kind = kind;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public DependencyGraphEdge.Kind getKind() {
        return kind;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Dependence) ? equals((Dependence)obj) : false;
    }
    
    /**
     * Tests if a given dependence edge is equal to this edge.
     * @param dependence the dependence edge to be checked
     * @return the {@code true} if the given edge is equal to this edge
     */
    public boolean equals(Dependence dependence) {
        return dependence != null && (super.equals((GraphEdge)dependence) && kind == dependence.kind);
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
    
    /**
     * Sorts the list of dependence edges.
     * @param collection the list of the dependence edges to be sorted
     * @return the sorted list of the dependence edges
     */
    public static List<Dependence> sortEdges(Collection<? extends Dependence> collection) {
        List<Dependence> edges = new ArrayList<>(collection);
        edges.sort(Comparator.comparingLong((Dependence edge) -> edge.getSrcId())
                             .thenComparingLong((Dependence edge) -> edge.getDstId())
                             .thenComparing((Dependence edge) -> edge.getKind().toString()));
        return edges;
    }
}
