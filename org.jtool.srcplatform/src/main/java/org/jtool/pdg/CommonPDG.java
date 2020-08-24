/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.graph.Graph;
import org.jtool.cfg.CFGEntry;
import org.jtool.cfg.CommonCFG;
import org.jtool.srcmodel.QualifiedName;

/**
 * An object storing information on a program dependence graph (PDG).
 * 
 * @author Katsuhisa Maruyama
 */
public class CommonPDG extends Graph<PDGNode, Dependence> {
    
    /**
     * The entry node of this graph.
     */
    protected PDGEntry entry;
    
    /**
     * Sets the entry node for this graph.
     * @param node the entry node to be set
     */
    public void setEntryNode(PDGEntry node) {
        entry = node;
        entry.setPDG(this);
    }
    
    /**
     * Returns the entry node for this graph
     * @return the entry node
     */
    public PDGEntry getEntryNode() {
        return entry;
    }
    
    /**
     * Returns the identification number of this graph.
     * @return the identification number
     */
    public long getId() {
        return entry.getId();
    }
    
    /**
     * Returns the signature of this graph.
     * @return the graph signature
     */
    public String getSignature() {
        return entry.getSignature();
    }
    
    /**
     * Returns the fully-qualified name of this graph.
     * @return the fully-qualified name
     */
    public QualifiedName getQualifiedName() {
        return entry.getQualifiedName();
    }
    
    /**
     * Returns the CFG corresponding to this graph.
     * @return the corresponding CFG
     */
    public CommonCFG getCFG() {
        CFGEntry node = (CFGEntry)entry.getCFGNode();
        return node.getCFG();
    }
    
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
        return (obj instanceof CommonPDG) ? equals((CommonPDG)obj) : false;
    }
    
    /**
     * Tests if a given PDG is equal to this PDG.
     * @param edge the PDG to be checked
     * @return the {@code true} if the given PDG is equal to this PDG
     */
    public boolean equals(CommonPDG pdg) {
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
     * @return the string representing the information
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
        for (Dependence edge : Dependence.sortDependenceEdges(getEdges())) {
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
        for (Dependence edge : Dependence.sortDependenceEdges(getEdges())) {
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
        for (Dependence edge : Dependence.sortDependenceEdges(getEdges())) {
            if (edge.isDD()) {
                buf.append(edge.toString());
                buf.append("\n");
            }
        }
        return buf.toString();
    }
}
