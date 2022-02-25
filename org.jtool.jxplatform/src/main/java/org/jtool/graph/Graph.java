/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.graph;

import java.util.Set;
import java.util.HashSet;

/**
 * A graph object having nodes and edges.
 * 
 * @author Katsuhsa Maruyama
 */
public class Graph<N extends GraphNode, E extends GraphEdge> {
    
    /**
     * Nodes of this graph.
     */
    private Set<N> nodes = new HashSet<>();
    
    /**
     * Edges of this graph.
     */
    private Set<E> edges = new HashSet<>();
    
    /**
     * Creates a new, empty object.
     */
    public Graph() {
        super();
    }
    
    /**
     * Sets nodes of this graph.
     * @param set a collection of nodes
     */
    public void setNodes(Set<N> set) {
        nodes = set;
    }
    
    /**
     * Returns all nodes of this graph.
     * @return the collection of the nodes
     */
    public Set<N> getNodes() {
        return nodes;
    }
    
    /**
     * Sets edges of this graph.
     * @param set a collection of edges
     */
    public void setEdges(Set<E> set) {
        edges = set;
    }
    
    /**
     * Returns all edges of this graph.
     * @return the collection of the edges
     */
    public Set<E> getEdges() {
        return edges;
    }
    
    /**
     * Clears all nodes and all edges of this graph.
     */
    public void clear() {
        nodes.clear();
        edges.clear();
    }
    
    /**
     * Adds a given node to this graph.
     * @param node the node to be added
     */
    public void add(N node) {
        nodes.add(node);
    }
    
    /**
     * Adds a given edge to this graph.
     * @param edge the edge to be added
     */
    public void add(E edge) {
        edges.add(edge);
    }
    
    /**
     * Removes a given node from this graph.
     * @param node the node to be removed
     */
    public void remove(N node) {
        nodes.remove(node);
        new HashSet<E>(getEdges()).stream()
            .filter(edge -> edge.getSrcNode().equals(node) || edge.getDstNode().equals(node))
            .forEach(edge -> remove(edge));
    }
    
    /**
     * Removes a given edge from this graph.
     * @param edge the edge to be removed
     */
    public void remove(E edge) {
        edges.remove(edge);
        edge.getSrcNode().removeOutgoingEdge(edge);
        edge.getDstNode().removeIncomingEdge(edge);
    }
    
    /**
     * Tests if this graph contains a given node.
     * @param node the node to be checked
     * @return {@code true} if this graph contains the node, otherwise {@code false}
     */
    public boolean contains(N node) {
        return nodes.contains(node);
    }
    
    /**
     * Tests if this graph contains a given edge.
     * @param edge the edge to be checked
     * @return {@code true} if this graph contains the edge, otherwise {@code false}
     */
    public boolean contains(E edge) {
        return edges.contains(edge);
    }
    
    /**
     * Obtains information on this graph.
     * @return the string representing the information
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("----- Graph (from here) -----\n");
        buf.append(toStringForNodes());
        buf.append(toStringForEdges());
        buf.append("----- Graph (to here) -----\n");
        return buf.toString();
    }
    
    /**
     * Obtains information on all the nodes enclosed in this graph.
     * @return the string representing the information
     */
    protected String toStringForNodes() {
        StringBuilder buf = new StringBuilder();
        GraphNode.sortGraphNode(getNodes()).forEach(node -> {
            buf.append(node.toString());
            buf.append("\n");
        });
        return buf.toString();
    }
    
    /**
     * Obtains information on all the edges enclosed in this graph.
     * @return the string representing the information
     */
    protected String toStringForEdges() {
        StringBuilder buf = new StringBuilder();
        GraphEdge.sortGrapgEdge(getEdges()).forEach(edge -> {
            buf.append(edge.toString());
            buf.append("\n");
        });
        return buf.toString();
    }
}
