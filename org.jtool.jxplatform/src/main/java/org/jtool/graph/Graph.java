/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.graph;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

/**
 * A graph object having nodes and edges.
 * 
 * @author Katsuhisa Maruyama
 * 
 * @param <N> The type of nodes in this graph
 * @param <E> The type of edges in this graph
 */
public class Graph<N extends GraphNode, E extends GraphEdge> {
    
    /**
     * Nodes of this graph.
     */
    protected Set<N> nodes = new HashSet<>();
    
    /**
     * Edges of this graph.
     */
    protected List<E> edges = new ArrayList<>();
    
    /**
     * Creates a new, empty object for storing a graph information.
     */
    public Graph() {
    }
    
    /**
     * Clears all nodes and all edges of this graph.
     */
    public void clear() {
        nodes.clear();
        edges.clear();
    }
    
    /**
     * Returns all nodes of this graph.
     * @return the collection of the nodes
     */
    public Set<N> getNodes() {
        return nodes;
    }
    
    /**
     * Returns all edges of this graph.
     * @return the collection of the edges
     */
    public List<E> getEdges() {
        return edges;
    }
    
    /**
     * Adds nodes of this graph.
     * @param nodes a collection of nodes to be added
     */
    public void addNodes(Set<N> nodes) {
        assert nodes != null;
        
        this.nodes = nodes;
    }
    
    /**
     * Sets edges of this graph.
     * @param edges a collection of edges to be added
     */
    public void addEdges(List<E> edges) {
        assert edges != null;
        
        this.edges = edges;
    }
    
    /**
     * Adds a given node to this graph.
     * @param node the node to be added
     */
    public void add(N node) {
        assert node != null;
        
        nodes.add(node);
    }
    
    /**
     * Adds a given edge to this graph.
     * @param edge the edge to be added
     */
    public void add(E edge) {
        assert edge != null;
        
        edges.add(edge);
    }
    
    /**
     * Removes a given node from this graph.
     * @param node the node to be removed
     */
    public void remove(N node) {
        assert node != null;
        
        nodes.remove(node);
        Set<E> temp = new HashSet<E>(getEdges());
        temp.stream()
            .filter(edge -> edge.getSrcNode().equals(node) || edge.getDstNode().equals(node))
            .forEach(edge -> remove(edge));
    }
    
    /**
     * Removes a given edge from this graph.
     * @param edge the edge to be removed
     */
    public void remove(E edge) {
        assert edge != null;
        
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
        assert node != null;
        
        return nodes.contains(node);
    }
    
    /**
     * Tests if this graph contains a given edge.
     * @param edge the edge to be checked
     * @return {@code true} if this graph contains the edge, otherwise {@code false}
     */
    public boolean contains(E edge) {
        assert edge != null;
        
        return edges.contains(edge);
    }
    
    /**
     * Obtains information on this graph.
     * @return the string representing the information
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("----- Graph (from here) -----" + System.getProperty("line.separator"));
        buf.append(toStringForNodes());
        buf.append(toStringForEdges());
        buf.append("----- Graph (to here) -----" + System.getProperty("line.separator"));
        return buf.toString();
    }
    
    /**
     * Obtains information on all nodes enclosed in this graph.
     * @return the string representing the information
     */
    protected String toStringForNodes() {
        StringBuilder buf = new StringBuilder();
        GraphNode.sortGraphNode(getNodes()).forEach(node -> {
            buf.append(node.toString());
            buf.append(System.getProperty("line.separator"));
        });
        return buf.toString();
    }
    
    /**
     * Obtains information on all edges enclosed in this graph.
     * @return the string representing the information
     */
    protected String toStringForEdges() {
        StringBuilder buf = new StringBuilder();
        GraphEdge.sortGrapgEdge(getEdges()).forEach(edge -> {
            buf.append(edge.toString());
            buf.append(System.getProperty("line.separator"));
        });
        return buf.toString();
    }
}
