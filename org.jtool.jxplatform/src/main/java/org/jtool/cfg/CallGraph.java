/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.graph.GraphElement;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An object storing information about a call graph.
 * 
 * @author Katsuhisa Maruyama
 */
public class CallGraph {
    
    /**
     * The collection of nodes of this call graph.
     */
    private Set<CFGEntry> nodes = new HashSet<>();
    
    /**
     * The collection of edges of this call graph.
     */
    private Set<ControlFlow> edges = new HashSet<>();
    
    /**
     * The name of this call graph.
     */
    private final String name;
    
    /**
     * Creates a call graph having a given name.
     * @param name the name of this call graph
     */
    public CallGraph(String name) {
        this.name = name;
    }
    
    /**
     * Returns the name of this call graph
     * @return the call graph name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns all nodes of this call graph.
     * @return the collection of the nodes
     */
    public Set<CFGEntry> getNodes() {
        return nodes;
    }
    
    /**
     * Returns all edges of this call graph.
     * @return the collection of the edges
     */
    public Set<ControlFlow> getEdges() {
        return edges;
    }
    
    /**
     * Appends a given call graph to this call graph.
     * This method is not intended to be invoked by clients.
     * @param graph the call graph to be appended
     */
    public void append(CallGraph graph) {
        assert graph != null;
        
        graph.getEdges().forEach(e -> setCall((CFGEntry)e.getSrcNode(), (CFGEntry)e.getDstNode()));
    }
    
    /**
     * Sets a call flow edge in this call graph.
     * This method is not intended to be invoked by clients.
     * @param caller a node for the caller site
     * @param callee a node for the callee site
     */
    public void setCall(CFGEntry caller, CFGEntry callee) {
        assert caller != null;
        assert callee != null;
        
        if (!nodes.contains(caller)) {
            nodes.add(caller);
        }
        if (!nodes.contains(callee)) {
            nodes.add(callee);
        }
        
        ControlFlow edge = new ControlFlow(caller, callee);
        edge.setCall();
        if (!edges.contains(edge)) {
            edges.add(edge);
        }
    }
    
    /**
     * Returns nodes corresponding to methods that a method for a given node calls.
     * @param caller the node for the caller site 
     * @return the collection of callee sites
     */
    public Set<CFGEntry> getCalleeNodes(CFGEntry caller) {
        return edges.stream()
                    .filter(e -> e.getSrcNode().equals(caller))
                    .map(e -> (CFGEntry)e.getDstNode()).collect(Collectors.toSet());
    }
    
    /**
     * Returns nodes corresponding to methods that calls a method for a given node.
     * @param callee the node for the callee site 
     * @return the collection of caller sites
     */
    public Set<CFGEntry> getCallerNodes(CFGNode callee) {
        return edges.stream()
                    .filter(e -> e.getDstNode().equals(callee))
                    .map(e -> (CFGEntry)e.getSrcNode()).collect(Collectors.toSet());
    }
    
    /**
     * Finds a call flow edge outgoing from a given node.
     * @param node the CFG node having the outgoing edge
     * @return the collection of the call flow edges
     */
    public Set<ControlFlow> getCallFlowsFrom(CFGNode node) {
        return edges.stream()
                    .filter(edge -> edge.getSrcNode().equals(node))
                    .collect(Collectors.toSet());
    }
    
    /**
     * Displays information on this call graph.
     */
    public void print() {
        System.out.println(toString());
    }
    
    /**
     * Obtains information on this call graph.
     * @return the string representing the information
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("----- CallGraph of " + getName() + "-----\n");
        buf.append(toStringForEdges());
        buf.append("-----------------------------------\n");
        return buf.toString();
    }
    
    /**
     * Obtains information on all edges enclosed in this call graph.
     * @return the string representing the information
     */
    private String toStringForEdges() {
        StringBuilder buf = new StringBuilder();
        int index = 1;
        for (ControlFlow edge : ControlFlow.sortEdges(getEdges())) {
            buf.append(GraphElement.getIdString(index));
            buf.append(": ");
            buf.append(edge.toString());
            buf.append("\n");
            index++;
        }
        return buf.toString();
    }
}
