/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.cfg.CFG;
import org.jtool.cfg.CCFG;
import org.jtool.graph.GraphNode;
import org.jtool.srcmodel.QualifiedName;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * An object storing information on a dependency graph.
 * 
 * @author Katsuhisa Maruyama
 */
public abstract class DependencyGraph {
    
    /**
     * All nodes that are included in this dependency graph.
     */
    protected Set<PDGNode> allNodes = new HashSet<>();
    
    /**
     * All edges that are included in this dependency graph.
     */
    protected List<CD> cdEdges = new ArrayList<>();
    
    /**
     * All edges that are included in this dependency graph.
     */
    protected List<DD> ddEdges = new ArrayList<>();
    
    /**
     * Edges that link nodes in different PDGs.
     */
    protected List<Dependence> interEdges = new ArrayList<>();
    
    /**
     * Map for edges (connecting two nodes) that uncover field accesses in this dependency graph.
     */
    private Multimap<PDGNode, PDGNode> uncoveredFieldAccessEdgeMap = HashMultimap.create();
    
    /**
     * Creates a dependency graph.
     */
    protected DependencyGraph() {
    }
    
    /**
     * Returns the identification number of this dependency graph.
     * @return the identification number
     */
    public abstract long getId();
    
    /**
     * Returns the fully-qualified name of this dependency graph.
     * @return the fully-qualified name
     */
    public abstract QualifiedName getQualifiedName();
    
    /**
     * Returns the signature of this dependency graph.
     * @return the graph signature
     */
    public abstract String getSignature();
    
    /**
     * Returns PDGs contained in this dependency graph.
     * @return the collection of all the contained PDGs
     */
    public abstract Set<PDG> getPDGs();
    
    /**
     * Finds a PDG having a given name from PDGs contained in this dependency graph.
     * @param fqn the fully-qualified name of the PDG to be retrieved
     * @return the found PDG, or {@code null} if the corresponding PDG is not found
     */
    public abstract PDG findPDG(String fqn);
    
    /**
     * Adds nodes to this dependency graph.
     * This method is not intended to be invoked by clients.
     * @param nodes the collection of nodes to be added
     */
    public void addNodes(Set<PDGNode> nodes) {
        assert nodes != null;
        
        allNodes.addAll(nodes);
    }
    
    /**
     * Adds a node specific to this dependency graph.
     * This method is not intended to be invoked by clients.
     * @param node the node to be added
     */
    public void add(PDGNode node) {
        assert node != null;
        
        allNodes.add(node);
    }
    
    /**
     * Adds edges to this dependency graph.
     * This method is not intended to be invoked by clients.
     * @param edges the collection of the edges to be added
     */
    public void addEdges(List<Dependence> edges) {
        assert edges != null;
        
        edges.forEach(edge -> add(edge));
    }
    
    /**
     * Adds an edge that links nodes within different PDGs.
     * This method is not intended to be invoked by clients.
     * @param edge the dependence edge to be added
     */
    public void add(Dependence edge) {
        assert edge != null;
        
        if (edge.isCD()) {
            cdEdges.add((CD)edge);
        } else if (edge.isDD()) {
            ddEdges.add((DD)edge);
        } else {
            interEdges.add(edge);
        }
    }
    
    /**
     * Adds edges that link nodes within different PDGs.
     * This method is not intended to be invoked by clients.
     * @param edges the collection of the edges to be added
     */
    public void addInterEdges(List<Dependence> edges) {
        assert edges != null;
        
        edges.forEach(edge -> addInterEdge(edge));
    }
    
    /**
     * Adds an edge that links nodes within different PDGs.
     * This method is not intended to be invoked by clients.
     * @param edge the edge to be added
     */
    public void addInterEdge(Dependence edge) {
        assert edge != null;
        
        interEdges.add(edge);
        
        if (edge.isCD()) {
            cdEdges.add((CD)edge);
            
        } else if (edge.isDD()) {
            ddEdges.add((DD)edge);
        }
    }
    
    /**
     * Tests if this dependency graph represents a PDG.
     * @return {@code true} if this is a PDG, otherwise {@code false}
     */
    public boolean isPDG() {
        return false;
    }
    
    /**
     * Tests if this dependency graph represents a ClDG.
     * @return {@code true} if this is a ClDG, otherwise {@code false}
     */
    public boolean isClDG() {
        return false;
    }
    
    /**
     * Tests if this dependency graph represents an SDG.
     * @return {@code true} if this is an SDG, otherwise {@code false}
     */
    public boolean isSDG() {
        return false;
    }
    
    /**
     * Returns the CFG corresponding to this dependency graph.
     * @return the CFG, or {@code null} if the corresponding CFG does not exist
     */
    public CFG getCFG() {
        return null;
    }
    
    /**
     * Returns the CFG corresponding to this dependency graph.
     * @return the CCFG, or {@code null} if the corresponding CCFG does not exist
     */
    public CCFG getCCFG() {
        return null;
    }
    
    /**
     * Returns all nodes of this dependency graph.
     * @return the collection of the nodes
     */
    public Set<PDGNode> getNodes() {
        return allNodes;
    }
    
    /**
     * Returns all edges of this dependency graph.
     * @return the collection of the edges
     */
    public List<Dependence> getEdges() {
        List<Dependence> allEdges = new ArrayList<>();
        allEdges.addAll(cdEdges);
        allEdges.addAll(ddEdges);
        allEdges.addAll(interEdges);
        return allEdges;
    }
    
    /**
     * Returns all control dependence edges of this dependency graph.
     * @return the collection of the edges
     */
    public List<CD> getCDEdges() {
        return cdEdges;
    }
    
    /**
     * Returns all data dependence edges of this dependency graph.
     * @return the collection of the edges
     */
    public List<DD> getDDEdges() {
        return ddEdges;
    }
    
    /**
     * Obtains edges that link nodes within different PDGs.
     * @return the collection of the edges
     */
    public List<Dependence> getInterEdges() {
        return interEdges;
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
     * Obtains dependence edges incoming to a given node.
     * @param node the node
     * @return the collection of the incoming edges
     */
    public Set<Dependence> getIncomingDependenceEdges(PDGNode node) {
        return getEdges().stream()
                .filter(edge -> edge.getDstNode().equals(node)).collect(Collectors.toSet());
    }
    
    /**
     * Obtains control dependence edges incoming to a given node.
     * @param node the node
     * @return the collection of the incoming edges
     */
    public Set<CD> getIncomingCDEdges(PDGNode node) {
        return getCDEdges().stream()
                .filter(edge -> edge.getDstNode().equals(node)).collect(Collectors.toSet());
    }
    
    /**
     * Obtains data dependence edges incoming to this node.
     * @return the collection of the incoming edges
     */
    public Set<DD> getIncomingDDEdges(PDGNode node) {
        return getDDEdges().stream()
                .filter(edge -> edge.getDstNode().equals(node)).collect(Collectors.toSet());
    }
    
    /**
     * Obtains dependence edges outgoing from a given node.
     * @param node the node
     * @return the collection of the outgoing edges
     */
    public Set<Dependence> getOutgoingDependenceEdges(PDGNode node) {
        return getEdges().stream()
                .filter(edge -> edge.getSrcNode().equals(node)).collect(Collectors.toSet());
    }
    
    /**
     * Obtains control dependence edges outgoing from a given node.
     * @param node the node
     * @return the collection of the outgoing edges
     */
    public Set<CD> getOutgoingCDEdges(PDGNode node) {
        return getCDEdges().stream()
                .filter(edge -> edge.getSrcNode().equals(node)).collect(Collectors.toSet());
    }
    
    /**
     * Obtains data dependence edges outgoing from a given node.
     * @param node the node
     * @return the collection of the outgoing edges
     */
    public Set<DD> getOutgoingDDEdges(PDGNode node) {
        return getDDEdges().stream()
                .filter(edge -> edge.getSrcNode().equals(node)).collect(Collectors.toSet());
    }
    
    /**
     * Finds dependence edges between two nodes.
     * @param src the source node
     * @param dst the destination node
     * @return the collection of the dependence edges
     */
    public List<Dependence> findDependence(PDGNode src, PDGNode dst) {
        return getEdges().stream()
                .filter(e -> e.getSrcNode().equals(src) && e.getDstNode().equals(dst))
                .collect(Collectors.toList());
    }
    
    /**
     * Adds an edge that uncovers an field access.
     * This method is not intended to be invoked by clients.
     * @param edge the edge to be added
     */
    public void addUncoveredFieldAccessEdge(DD edge) {
        assert edge != null;
        
        uncoveredFieldAccessEdgeMap.put(edge.getSrcNode(), edge.getDstNode());
    }
    
    /**
     * Tests there is a dependence edge that uncovers field accesses, connecting two nodes.
     * @param src the source node
     * @param dst the destination node
     * @return {@code true} if a dependence edge was found, otherwise {@code false}
     */
    public boolean existsUncoveredFieldAccessEdge(PDGNode src, PDGNode dst) {
        return uncoveredFieldAccessEdgeMap.get(src).contains(dst);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof DependencyGraph) ? equals((DependencyGraph)obj) : false;
    }
    
    /**
     * Tests if a given dependency graph is equal to this dependency graph.
     * @param graph the dependency graph to be checked
     * @return {@code true} if the given dependency graph is equal to this dependency graph
     */
    public boolean equals(DependencyGraph graph) {
        return graph != null && (this == graph || getQualifiedName().equals(graph.getQualifiedName()));
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
        buf.append("----- " + kindName + " (from here) -----\n");
        buf.append("Name = " + getQualifiedName());
        buf.append("\n");
        buf.append(toStringForNodes()); 
        buf.append(toStringForEdges());
        buf.append("----- " + kindName + " (to here) -----\n");
        return buf.toString();
    }
    
    /**
     * Obtains information on all nodes enclosed in this dependency graph.
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
     * Obtains information on all edges enclosed in this dependency graph.
     * @return the string representing the information
     */
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
     * Obtains information on control dependence edges enclosed in this dependency graph.
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
     * Obtains information on data dependence edges enclosed in this dependency graph.
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
