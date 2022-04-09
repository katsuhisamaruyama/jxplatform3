/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.graph.Graph;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CCFG;
import org.jtool.srcmodel.QualifiedName;

/**
 * An object storing information on a dependence graph.
 * 
 * @author Katsuhisa Maruyama
 */
public abstract class DependenceGraph extends Graph<PDGNode, Dependence> {
    
    /**
     * Returns the identification number of this PDG.
     * @return the identification number
     */
    public abstract long getId();
    
    /**
     * Returns the fully-qualified name of this PDG.
     * @return the fully-qualified name
     */
    public abstract QualifiedName getQualifiedName();
    
    /**
     * Returns the signature of this graph.
     * @return the graph signature
     */
    public abstract String getSignature();
    
    /**
     * Returns the CFG corresponding to this graph.
     * @return the corresponding CFG
     */
    public abstract CFG getCFG();
    
    /**
     * Returns the CFG corresponding to this graph.
     * @return the corresponding CCFG
     */
    public abstract CCFG getCCFG();
    
    /**
     * Adds a node to this graph.
     * @param node the node to be added
     */
    @Override
    public void add(PDGNode node) {
        super.add(node);
    }
    
    /**
     * Adds a dependence edge to this graph.
     * @param edge the edge to be added
     */
    @Override
    public void add(Dependence edge) {
        super.add(edge);
    }
    
    /**
     * Finds the node having a given identification number.
     * @param id the identification number to the node to be retrieved
     * @return the found node, or {@code null} if the corresponding node is not found
     */
    public PDGNode getNode(int id) {
        return getNodes().stream().filter(node -> node.getId() == id).findFirst().orElse(null);
    }
    
    /**
     * Tests if this graph represents a PDG.
     * @return {@code true} if this is a PDG, otherwise {@code false}
     */
    public boolean isPDG() {
        return false;
    }
    
    /**
     * Tests if this graph represents a ClDG.
     * @return {@code true} if this is a ClDG, otherwise {@code false}
     */
    public boolean isClDG() {
        return false;
    }
    
    /**
     * Tests if this graph represents an SDG.
     * @return {@code true} if this is an SDG, otherwise {@code false}
     */
    public boolean isSDG() {
        return false;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof DependenceGraph) ? equals((DependenceGraph)obj) : false;
    }
    
    /**
     * Tests if a given PDG is equal to this PDG.
     * @param pdg the PDG to be checked
     * @return the {@code true} if the given PDG is equal to this PDG
     */
    public boolean equals(DependenceGraph pdg) {
        return pdg != null && (this == pdg || getQualifiedName().equals(pdg.getQualifiedName()));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return getQualifiedName().hashCode();
    }
    
    /**
     * Displays information on this graph.
     */
    public void print() {
        System.out.println(toString());
    }
    
    /**
     * Obtains information on this graph.
     * @return the string representing the information on this PDG
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("----- PDG (from here) -----\n");
        buf.append("Name = " + getQualifiedName());
        buf.append("\n");
        buf.append(toStringForNodes()); 
        buf.append(toStringForEdges());
        buf.append("----- PDG (to here) -----\n");
        return buf.toString();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected String toStringForEdges() {
        StringBuffer buf = new StringBuffer();
        int index = 1;
        for (Dependence edge : Dependence.sortEdges(getEdges())) {
            buf.append(String.valueOf(index));
            buf.append(": ");
            buf.append(edge.toString());
            buf.append("\n");
            index++;
        }
        return buf.toString();
    }
    
    /**
     * Displays information on a graph to show control dependence edges.
     * @return the string representing the information on control dependence
     */
    public String printCDG() {
        StringBuilder buf = new StringBuilder();
        buf.append("----- CDG (from here) -----\n");
        buf.append("Name = " + getQualifiedName());
        buf.append("\n");
        buf.append(toStringForNodes()); 
        buf.append(toStringForCDEdges());
        buf.append("----- CDG (to here) -----\n");
        return buf.toString();
    }
    
    /**
     * Displays information on a graph to show data dependence edges.
     * @return the string representing the information on data dependence
     */
    public String printDDG() {
        StringBuilder buf = new StringBuilder();
        buf.append("----- DDG (from here) -----\n");
        buf.append("Name = " + getQualifiedName());
        buf.append("\n");
        buf.append(toStringForNodes()); 
        buf.append(toStringForDDEdges());
        buf.append("----- DDG (to here) -----\n");
        return buf.toString();
    }
    
    /**
     * Obtains information on control dependence edges enclosed in this graph.
     * @return the string representing the information
     */
    protected String toStringForCDEdges() {
        StringBuffer buf = new StringBuffer();
        for (Dependence edge : Dependence.sortEdges(getEdges())) {
            if (edge.isCD()) {
                buf.append(edge.toString());
                buf.append("\n");
            }
        }
        return buf.toString();
    }
    
    /**
     * Obtains information on data dependence edges enclosed in this graph.
     * @return the string representing the information
     */
    protected String toStringForDDEdges() {
        StringBuffer buf = new StringBuffer();
        for (Dependence edge : Dependence.sortEdges(getEdges())) {
            if (edge.isDD()) {
                buf.append(edge.toString());
                buf.append("\n");
            }
        }
        return buf.toString();
    }
}
