/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import static org.jtool.jxplatform.builder.ModelBuilder.br;
import org.jtool.cfg.CFG;
import org.jtool.graph.Graph;
import org.jtool.srcmodel.QualifiedName;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An object storing information on a program dependence graph (PDG).
 * 
 * @author Katsuhisa Maruyama
 */
public class PDG extends Graph<PDGNode, Dependence> {
    
    /**
     * The entry node of this PDG.
     */
    private PDGEntry entry;
    
    /**
     * The CFG corresponding to this PDG.
     */
    private CFG cfg;
    
    /**
     * Creates a new, empty object for storing a PDG information.
     * This method is not intended to be invoked by clients.
     * @param cfg the corresponding CFG
     */
    public PDG(CFG cfg) {
        this.cfg = cfg;
    }
    
    /**
     * Returns the CFG corresponding to this dependency graph.
     * @return the CFG, or {@code null} if the corresponding CFG does not exist
     */
    public CFG getCFG() {
        return cfg;
    }
    
    /**
     * Sets the entry node for this PDG.
     * This method is not intended to be invoked by clients.
     * @param node the entry node to be set
     */
    public void setEntryNode(PDGEntry node) {
        assert node != null;
        
        entry = node;
        entry.setPDG(this);
    }
    
    /**
     * Returns the entry node for this PDG.
     * @return the entry node
     */
    public PDGEntry getEntryNode() {
        return entry;
    }
    
    /**
     * Returns the identification number of this PDG.
     * @return the identification number
     */
    public long getId() {
        return entry.getId();
    }
    
    /**
     * Returns the fully-qualified name of this PDG.
     * @return the fully-qualified name
     */
    public QualifiedName getQualifiedName() {
        return entry.getQualifiedName();
    }
    
    /**
     * Returns all nodes of this PDG.
     * @return the collection of the nodes
     */
    public Set<PDGNode> getNodes() {
        return super.getNodes().stream()
                .map(node -> (PDGNode)node).collect(Collectors.toSet());
    }
    
    /**
     * Returns all edges of this PDG.
     * @return the collection of the edges
     */
    public List<Dependence> getEdges() {
        return super.getEdges().stream()
                .map(edge -> (Dependence)edge).collect(Collectors.toList());
    }
    
    /**
     * Returns all control dependence edges of this PDG.
     * @return the collection of the edges
     */
    public List<CD> getCDEdges() {
        return super.getEdges().stream().filter(edge -> edge.isCD())
            .map(edge -> (CD)edge).collect(Collectors.toList());
    }
    
    /**
     * Returns all data dependence edges of this PDG.
     * @return the collection of the edges
     */
    public List<DD> getDDEdges() {
        return super.getEdges().stream().filter(edge -> edge.isDD())
            .map(edge -> (DD)edge).collect(Collectors.toList());
    }
    
    /**
     * Finds the node having a given identification number.
     * @param id the identification number to the node to be retrieved
     * @return the found node, or {@code null} if the corresponding node is not found
     */
    public PDGNode getNode(long id) {
        return (PDGNode)super.getNodes().stream()
                .filter(node -> node.getId() == id + getEntryNode().getId()).findFirst().orElse(null);
    }
    
    /**
     * Obtains dependence edges incoming to a given node.
     * @param node the node
     * @return the collection of the incoming edges
     */
    public List<Dependence> getIncomingEdges(PDGNode node) {
        return node.getIncomingEdges().stream()
                .map(edge -> (Dependence)edge).collect(Collectors.toList());
    }
    
    /**
     * Obtains control dependence edges incoming to a given node.
     * @param node node the node
     * @return the collection of the incoming control dependence edges
     */
    public List<CD> getIncomingCDEdges(PDGNode node) {
        return getIncomingEdges(node).stream().filter(edge -> edge.isCD())
                .map(edge -> (CD)edge).collect(Collectors.toList());
    }
    
    /**
     * Obtains data dependence edges incoming to a given node.
     * @param node the node
     * @return the collection of the incoming data dependence edges
     */
    public List<DD> getIncomingDDEdges(PDGNode node) {
        return getIncomingEdges(node).stream().filter(edge -> edge.isDD())
                .map(edge -> (DD)edge).collect(Collectors.toList());
    }
    
    /**
     * Obtains dependence edges outgoing from a given node.
     * @param node the node
     * @return the collection of the outgoing dependence edges
     */
    public List<Dependence> getOutgoingEdges(PDGNode node) {
        return node.getOutgoingEdges().stream()
                .map(edge -> (Dependence)edge).collect(Collectors.toList());
    }
    
    /**
     * Obtains control dependence edges outgoing from a given node.
     * @param node the node
     * @return the collection of the outgoing control dependence edges
     */
    public List<CD> getOutgoingCDEdges(PDGNode node) {
        return getOutgoingEdges(node).stream().filter(edge -> edge.isCD())
                .map(edge -> (CD)edge).collect(Collectors.toList());
    }
    
    /**
     * Obtains data dependence edges outgoing from a given node.
     * @param node the node
     * @return the collection of the outgoing data dependence edges
     */
    public List<DD> getOutgoingDDEdges(PDGNode node) {
        return getOutgoingEdges(node).stream().filter(edge -> edge.isDD())
                .map(edge -> (DD)edge).collect(Collectors.toList());
    }
    
    /**
     * Tests if a given node is dominated by any control flow.
     * @param node the node
     * @return {@code true} if this is a dominated node, otherwise {@code false}
     */
    public boolean isDominated(PDGNode node) {
        return getIncomingCDEdges(node).stream()
                .anyMatch(cd -> (cd.isTrue() || cd.isFalse() || cd.isFallThrough()));
    }
    
    /**
     * Tests if a given node is dominated by a true control flow.
     * @param node the node
     * @return {@code true} if this is a true-dominated node, otherwise {@code false}
     */
    public boolean isTrueDominated(PDGNode node) {
        return getIncomingCDEdges(node).stream().anyMatch(cd -> cd.isTrue());
    }
    
    /**
     * Tests if a given node is dominated by a false control flow.
     * @param node the node
     * @return {@code true} if this is a false-dominated node, otherwise {@code false}
     */
    public boolean isFalseDominated(PDGNode node) {
        return getIncomingCDEdges(node).stream().anyMatch(cd -> cd.isFalse());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof PDG) ? equals((PDG)obj) : false;
    }
    
    /**
     * Tests if a given PDG is equal to this PDG.
     * @param pdg the PDG to be checked
     * @return the {@code true} if the given PDG is equal to this PDG
     */
    public boolean equals(PDG pdg) {
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
     * Obtains information on this dependency graph.
     * @param kindName the kind name of this dependency graph
     * @return the string representing the information on this dependency graph
     */
    public String toString(String kindName) {
        StringBuilder buf = new StringBuilder();
        buf.append("----- " + kindName + " (from here) -----" + br);
        buf.append("Name = " + getQualifiedName());
        buf.append(br);
        buf.append(toStringForNodes()); 
        buf.append(toStringForEdges());
        buf.append("----- " + kindName + " (to here) -----" + br);
        return buf.toString();
    }
    
    /**
     * Obtains information on all nodes enclosed in this dependency graph.
     * @return the string representing the information
     */
    protected String toStringForNodes() {
        return super.toStringForNodes();
    }
    
    /**
     * Obtains information on all edges enclosed in this dependency graph.
     * @return the string representing the information
     */
    @Override
    protected String toStringForEdges() {
        StringBuffer buf = new StringBuffer();
        int index = 1;
        for (DependencyGraphEdge edge : DependencyGraphEdge.sortEdges(getEdges())) {
            buf.append(String.valueOf(index));
            buf.append(": ");
            buf.append(edge.toString());
            buf.append(br);
            index++;
        }
        return buf.toString();
    }
    
    /**
     * Obtains information on control dependence edges enclosed in this dependency graph.
     * @return the string representing the information
     */
    protected String toStringForCDEdges() {
        StringBuffer buf = new StringBuffer();
        for (DependencyGraphEdge edge : DependencyGraphEdge.sortEdges(getEdges())) {
            if (edge.isCD()) {
                buf.append(edge.toString());
                buf.append(br);
            }
        }
        return buf.toString();
    }
    
    /**
     * Obtains information on data dependence edges enclosed in this dependency graph.
     * @return the string representing the information
     */
    protected String toStringForDDEdges() {
        StringBuffer buf = new StringBuffer();
        for (DependencyGraphEdge edge : DependencyGraphEdge.sortEdges(getEdges())) {
            if (edge.isDD()) {
                buf.append(edge.toString());
                buf.append(br);
            }
        }
        return buf.toString();
    }
}
