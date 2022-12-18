/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.graph;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

/**
 * An edge object for a graph.
 * 
 * @author Katsuhisa Maruyama
 */
public abstract class GraphEdge extends GraphElement {
    
    /**
     * The source node of this edge.
     */
    protected GraphNode src;
    
    /**
     * The destination node of this edge.
     */
    protected GraphNode dst;
    
    /**
     * Creates a new object that represents an edge connecting two nodes.
     * @param src the source node of this edge
     * @param dst the destination node of this edge
     */
    protected GraphEdge(GraphNode src, GraphNode dst) {
        assert src != null;
        assert dst != null;
        
        this.src = src;
        this.dst = dst;
        src.addOutgoingEdge(this);
        dst.addIncomingEdge(this);
    }
    
    /**
     * Returns the identification number of the source node of this edge.
     * @return the identification number of the source node
     */
    protected long getSrcId() {
        return src.getId();
    }
    
    /**
     * Returns the identification number of the destination node of this edge.
     * @return the identification number of the destination node
     */
    protected long getDstId() {
        return dst.getId();
    }
    
    /**
     * Returns the source node for this edge.
     * @return the source node
     */
    public GraphNode getSrcNode() {
        return src;
    }
    
    /**
     * Returns the destination node for this edge.
     * @return the destination node
     */
    public GraphNode getDstNode() {
        return dst;
    }
    
    /**
     * Sets a source node for this edge.
     * @param node the source node to be set
     */
    public void setSrcNode(GraphNode node) {
        assert node != null;
        
        src.removeOutgoingEdge(this);
        dst.removeIncomingEdge(this);
        
        src = node;
        src.addOutgoingEdge(this);
        dst.addIncomingEdge(this);
    }
    
    /**
     * Sets a destination node for this edge.
     * @param node the destination node to be set
     */
    public void setDstNode(GraphNode node) {
        assert node != null;
        
        src.removeOutgoingEdge(this);
        dst.removeIncomingEdge(this);
        
        dst = node;
        src.addOutgoingEdge(this);
        dst.addIncomingEdge(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(GraphElement elem) {
        return (elem instanceof GraphEdge) ? equals((GraphEdge)elem) : false;
    }
    
    /**
     * Tests if a given edge is equal to this edge.
     * @param edge the edge to be checked
     * @return the {@code true} if the given edge is equal to this edge
     */
    public boolean equals(GraphEdge edge) {
        return edge != null && (this == edge || (src.equals(edge.src) && dst.equals(edge.dst)));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Long.valueOf(src.id + dst.id).hashCode();
    }
    
    /**
     * Obtains information on this edge.
     * @return the string representing the information
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("Edge: " + src.getId() + " -> " + dst.getId());
        return buf.toString();
    }
    
    /**
     * Sorts the list of edges
     * @param collection the list to be sorted
     * @return the sorted list of the edges
     */
    protected static List<GraphEdge> sortGrapgEdge(Collection<? extends GraphEdge> collection) {
        List<GraphEdge> edges = new ArrayList<>(collection);
        edges.sort(Comparator.comparingLong(GraphEdge::getSrcId).thenComparingLong(GraphEdge::getDstId));
        return edges;
    }
}
