/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.graph;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * A node object for a graph.
 * 
 * @author Katsuhsa Maruyama
 */
public abstract class GraphNode extends GraphElement {
    
    /**
     * The identification number of this node.
     */
    protected long id = 0;
    
    /**
     * The collection of edges incoming to this node. 
     */
    private Set<GraphEdge> incomingEdges = new HashSet<>();
    
    /**
     * The collection of edges outgoing from this node. 
     */
    private Set<GraphEdge> outgoingEdges = new HashSet<>();
    
    /**
     * The collection of source nodes of this node. 
     */
    private Set<GraphNode> srcNodes = new HashSet<>();
    
    /**
     * The collection of destination nodes of this node. 
     */
    private Set<GraphNode> dstNodes = new HashSet<>();
    
    /**
     * Creates a new, empty object that represents a node.
     */
    protected GraphNode(long id) {
        this.id = id;
    }
    
    /**
     * Clears information on this node. 
     */
    public void clear() {
        incomingEdges.clear();
        outgoingEdges.clear();
        srcNodes.clear();
        dstNodes.clear();
    }
    
    /**
     * Sets the identification number
     * @param id the identification number to be assigned
     */
    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * Returns the identification number of this node.
     * @return the identification number
     */
    public long getId() {
        return id;
    }
    
    /**
     * Adds an edge incoming to this node. 
     * @param edge the incoming edge to be added
     */
    public void addIncomingEdge(GraphEdge edge) {
        if (incomingEdges.add(edge)) {
            srcNodes.add(edge.getSrcNode());
        }
    }
    
    /**
     * Adds an edge outgoing from this node.
     * @param edge the outgoing edge to be added
     */
    public void addOutgoingEdge(GraphEdge edge) {
        if (outgoingEdges.add(edge)) {
            dstNodes.add(edge.getDstNode());
        }
    }
    
    /**
     * Adds edges incoming to this node. 
     * @param edges the collection of the incoming edges to be added
     */
    public void addIncomingEdges(Set<GraphEdge> edges) {
        edges.forEach(edge -> addIncomingEdge(edge));
    }
    
    /**
     * Adds edges outgoing to this node. 
     * @param edges the collection of the outgoing edges to be added
     */
    public void addOutgoingEdges(Set<GraphEdge> edges) {
        edges.forEach(edge -> addOutgoingEdge(edge));
    }
    
    /**
     * Removes an edge incoming to this node.
     * @param edge the incoming edge to be removed
     */
    public void removeIncomingEdge(GraphEdge edge) {
        incomingEdges.remove(edge);
        srcNodes.remove(edge.getSrcNode());
    }
    
    /**
     * Removes an edge outgoing from this node. 
     * @param edge the outgoing edge to be removed
     */
    public void removeOutgoingEdge(GraphEdge edge) {
        outgoingEdges.remove(edge);
        dstNodes.remove(edge.getDstNode());
    }
    
    /**
     * Clears information on incoming edges stored in this node.
     */
    public void clearIncomingEdges() {
        incomingEdges.clear();
    }
    
    /**
     * Clears information on outgoing edges stored in this node.
     */
    public void clearOutgoingEdges() {
        outgoingEdges.clear();
    }
    
    /**
     * Sets edges incoming to this node.
     * @param edges the collection of the incoming edges to be set
     */
    public void setIncomingEdges(Set<GraphEdge> edges) {
        incomingEdges = edges;
    }
    
    /**
     * Sets edges outgoing from this node.
     * @param edges the collection of the outgoing edges to be set
     */
    public void setOutgoingEdges(Set<GraphEdge> edges) {
        outgoingEdges = edges;
    }
    
    /**
     * Returns edges incoming to this node.
     * @return the collection of the incoming edges
     */
    public Set<GraphEdge> getIncomingEdges() {
        return incomingEdges;
    }
    
    /**
     * Returns edges outgoing from this node.
     * @return the collection of the outgoing edges
     */
    public Set<GraphEdge> getOutgoingEdges() {
        return outgoingEdges;
    }
    
    /**
     * Returns source nodes for this node.
     * @return the collection of the source nodes
     */
    public Set<GraphNode> getSrcNodes() {
        return srcNodes;
    }
    
    /**
     * Returns destination nodes for this node.
     * @return The collection of destination nodes
     */
    public Set<GraphNode> getDstNodes() {
        return dstNodes;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(GraphElement elem) {
        return (elem instanceof GraphNode) ? equals((GraphNode)elem) : false;
    }
    
    /**
     * Tests if a given node is equal to this node.
     * @param node the node to be checked
     * @return the {@code true} if the given node is equal to this node
     */
    public boolean equals(GraphNode node) {
        return node != null && (this == node || id == node.id);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Long.valueOf(id).hashCode();
    }
    
    /**
     * Copies information on this node into a given clone.
     * @param cloneNode the clone of this node
     */
    protected void setClone(GraphNode cloneNode) {
        cloneNode.addIncomingEdges(incomingEdges);
        cloneNode.addOutgoingEdges(outgoingEdges);
    }
    
    /**
     * Obtains information on this node.
     * @return the string representing the information
     */
    public String toString() {
        StringBuilder buf = new StringBuilder(); 
        buf.append("Node: " + getIdString() + "\n");
        Set<GraphEdge> outgoing = getOutgoingEdges();
        buf.append("  Outgoing :");
        outgoing.forEach(edge -> buf.append("  " + edge.getDstNode().getId()));
        buf.append("\n");
        Set<GraphEdge> incoming = getIncomingEdges();
        buf.append("  Incoming :");
        incoming.forEach(edge -> buf.append("  " + edge.getSrcNode().getId()));
        return buf.toString();
    }
    
    /**
     * Obtains the printed identification number.
     * @return the printed string of the identification number
     */
    public String getIdString() {
        StringBuilder buf = new StringBuilder();
        long id = getId();
        if (id < 10) {
            buf.append("   ");
        } else if (id < 100) {
            buf.append("  ");
        } else if (id < 1000) {
            buf.append(" ");
        }
        buf.append(String.valueOf(id));
        return buf.toString();
    }
    
    /**
     * Sorts the list of nodes
     * @param co the list to be sorted
     * @return the sorted list of the nodes
     */
    public static List<GraphNode> sortGraphNode(Collection<? extends GraphNode> co) {
        List<GraphNode> nodes = new ArrayList<>(co);
        Collections.sort(nodes, new Comparator<>() {
            
            @Override
            public int compare(GraphNode node1, GraphNode node2) {
                return node2.id == node1.id ? 0 : node1.id > node2.id ? 1 : -1;
            }
        });
        return nodes;
    }
}
