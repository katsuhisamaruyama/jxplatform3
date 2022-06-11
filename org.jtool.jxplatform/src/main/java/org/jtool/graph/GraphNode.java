/*
 *  Copyright 2022
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
    protected List<GraphEdge> incomingEdges = new ArrayList<>();
    
    /**
     * The collection of edges outgoing from this node. 
     */
    protected List<GraphEdge> outgoingEdges = new ArrayList<>();
    
    /**
     * The collection of source nodes of this node. 
     */
    protected Set<GraphNode> srcNodes = new HashSet<>();
    
    /**
     * The collection of destination nodes of this node. 
     */
    protected Set<GraphNode> dstNodes = new HashSet<>();
    
    /**
     * Creates a new, empty object that represents a node.
     * @param id the identification number this node independently has
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
     * Sets the identification number.
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
    public void addIncomingEdges(List<GraphEdge> edges) {
        edges.forEach(edge -> addIncomingEdge(edge));
    }
    
    /**
     * Adds edges outgoing to this node.
     * @param edges the collection of the outgoing edges to be added
     */
    public void addOutgoingEdges(List<GraphEdge> edges) {
        edges.forEach(edge -> addOutgoingEdge(edge));
    }
    
    /**
     * Removes an edge incoming to this node.
     * @param edge the incoming edge to be removed
     */
    public void removeIncomingEdge(GraphEdge edge) {
        GraphNode src = edge.getSrcNode();
        
        incomingEdges.remove(edge);
        if (!incomingEdges.stream().anyMatch(e -> e.getSrcNode().equals(src))) {
            srcNodes.remove(src);
        }
        
        src.outgoingEdges.remove(edge);
        if (!src.outgoingEdges.stream().anyMatch(e -> e.getDstNode().equals(this))) {
            src.dstNodes.remove(this);
        }
    }
    
    /**
     * Removes an edge outgoing from this node. 
     * @param edge the outgoing edge to be removed
     */
    public void removeOutgoingEdge(GraphEdge edge) {
        GraphNode dst = edge.getDstNode();
        
        outgoingEdges.remove(edge);
        if (!outgoingEdges.stream().anyMatch(e -> e.getDstNode().equals(dst))) {
            dstNodes.remove(dst);
        }
        dst.incomingEdges.remove(edge);
        if (!incomingEdges.stream().anyMatch(e -> e.getSrcNode().equals(this))) {
            dst.srcNodes.remove(this);
        }
    }
    
    /**
     * Returns edges incoming to this node.
     * @return the collection of the incoming edges
     */
    public List<GraphEdge> getIncomingEdges() {
        return incomingEdges;
    }
    
    /**
     * Returns edges outgoing from this node.
     * @return the collection of the outgoing edges
     */
    public List<GraphEdge> getOutgoingEdges() {
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
     * Obtains information on this node.
     * @return the string representing the information
     */
    public String toString() {
        StringBuilder buf = new StringBuilder(); 
        buf.append("Node: " + GraphElement.getIdString(getId()) + "\n");
        List<GraphEdge> outgoing = getOutgoingEdges();
        buf.append("  Outgoing :");
        outgoing.forEach(edge -> buf.append("  " + edge.getDstNode().getId()));
        buf.append("\n");
        List<GraphEdge> incoming = getIncomingEdges();
        buf.append("  Incoming :");
        incoming.forEach(edge -> buf.append("  " + edge.getSrcNode().getId()));
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
