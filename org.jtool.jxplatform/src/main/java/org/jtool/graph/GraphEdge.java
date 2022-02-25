/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.graph;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * An edge object for agraph.
 * 
 * @author Katsuhsa Maruyama
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
        this.src = src;
        this.dst = dst;
        src.addOutgoingEdge(this);
        dst.addIncomingEdge(this);
    }
    
    /**
     * Sets a source node for this edge.
     * @param node the source node to be set
     */
    public void setSrcNode(GraphNode node) {
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
        src.removeOutgoingEdge(this);
        dst.removeIncomingEdge(this);
        dst = node;
        src.addOutgoingEdge(this);
        dst.addIncomingEdge(this);
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
     * Copies information on this edge into a given clone.
     * @param cloneEdge the clone of this edge
     */
    protected void setClone(GraphEdge cloneEdge) {
        cloneEdge.setSrcNode(src);
        cloneEdge.setDstNode(dst);
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
     * @param co the list to be sorted
     * @return the sorted list of the edges
     */
    protected static List<GraphEdge> sortGrapgEdge(Collection<? extends GraphEdge> co) {
        List<GraphEdge> edges = new ArrayList<>(co);
        Collections.sort(edges, new Comparator<>() {
            
            @Override
            public int compare(GraphEdge edge1, GraphEdge edge2) {
                if (edge2.src.id == edge1.src.id) {
                    return edge2.dst.id == edge1.dst.id ? 0 : edge1.dst.id > edge2.dst.id ? 1 : -1;
                } else if (edge1.src.id > edge2.src.id) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        return edges;
    }
}
