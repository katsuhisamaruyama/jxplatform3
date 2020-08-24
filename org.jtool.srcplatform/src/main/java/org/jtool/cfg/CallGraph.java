/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.graph.Graph;
import java.util.Set;

/**
 * An object storing information about a call graph.
 * 
 * @author Katsuhisa Maruyama
 */
public class CallGraph extends Graph<CFGNode, ControlFlow> {
    
    /**
     * The name of this call graph.
     */
    protected String name;
    
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
     * Adds a control flow edge to this call graph.
     * @param the control flow edge to be added
     */
    @Override
    public void add(ControlFlow flow) {
        if (!contains(flow.getSrcNode())) {
            super.add(flow.getSrcNode());
        }
        if (!contains(flow.getDstNode())) {
            super.add(flow.getDstNode());
        }
        if (!contains(flow)) {
            super.add(flow);
        }
    }
    
    /**
     * Appends a given call graph to this call graph.
     * @param graph the call graph to be appended
     */
    public void append(CallGraph graph) {
        if (graph != null) {
            graph.getEdges().forEach(edge -> add(edge));
        }
    }
    
    /**
     * Returns nodes corresponding to methods that a method for a given node calls.
     * @param src the node for the caller site 
     * @return the collection of callee sites
     */
    public Set<CFGNode> getCalleeNodes(CFGNode src) {
        return src.getSuccessors();
    }
    
    /**
     * Returns nodes corresponding to methods that calls a method for a given node.
     * @param dst the node for the callee site 
     * @return the collection of caller sites
     */
    public Set<CFGNode> getCallerNodes(CFGNode dst) {
        return dst.getPredecessors();
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
     * {@inheritDoc}
     */
    @Override
    protected String toStringForEdges() {
        StringBuilder buf = new StringBuilder();
        int index = 1;
        for (ControlFlow edge : ControlFlow.sortControlFlowEdges(getEdges())) {
            buf.append(String.valueOf(index));
            buf.append(": ");
            buf.append(edge.toString());
            buf.append("\n");
            index++;
        }
        return buf.toString();
    }
}
