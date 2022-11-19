/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.graph.GraphNode;
import org.jtool.jxplatform.builder.TimeInfo;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * An object storing information on a dependency graph consisting of ClDGs.
 * 
 * @author Katsuhisa Maruyama
 */
public class DependencyGraph {
    
    /**
     * The qualified name of this dependency graph.
     */
    private final String name;
    
    /**
     * The map between the fully-qualified names and ClDGs that have their corresponding names.
     */
    private Map<String, ClDG> cldgs = new HashMap<>();
    
    /**
     * The map between the fully-qualified names and PDGs that have their corresponding names.
     */
    private Map<String, PDG> pdgs = new HashMap<>();
    
    /**
     * All nodes that are included in this dependency graph.
     */
    private Set<PDGNode> nodes = new HashSet<>();
    
    /**
     * All edges that are included in PDSs of this dependency graph.
     */
    private List<DependencyGraphEdge> edges = new ArrayList<>();
    
    /**
     * The map between source node and its its edges that connect nodes in different PDGs.
     */
    private Multimap<PDGNode, DependencyGraphEdge> interPDGEdgeMapWithSrcKey = HashMultimap.create();
    
    /**
     * The map between destination node and its edges that connect nodes in different PDGs.
     */
    private Multimap<PDGNode, DependencyGraphEdge> interPDGEdgeMapWithDstKey = HashMultimap.create();
    
    /**
     * Map for edges (connecting two nodes) that uncover field accesses in this dependency graph.
     */
    private Multimap<PDGNode, PDGNode> uncoveredFieldAccessEdgeMap = HashMultimap.create();
    
    /**
     * Creates a dependency graph.
     * This method is not intended to be invoked by clients.
     * @param name the name to be set
     */
    public DependencyGraph(String name) {
        this.name = name + "-" + TimeInfo.getTimeAsISOString(TimeInfo.getCurrentTime());
    }
    
    /**
     * Returns the name of this dependency graph.
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * Adds a ClDG to this dependency graph.
     * @param cldg the ClDG to be added
     */
    public void add(ClDG cldg) {
        if (!cldgs.values().contains(cldg)) {
            cldg.getNodes().stream().forEach(node -> add(node));
            cldg.getEdges().stream().forEach(edge -> add(edge));
            cldgs.put(cldg.getQualifiedName().fqn(), cldg);
            
            cldg.getPDGs().forEach(pdg -> pdgs.put(pdg.getQualifiedName().fqn(), pdg));
        }
    }
    
    /**
     * Adds a PDG to to this dependency graph.
     * @param pdg the PDG to be added
     */
    public void add(PDG pdg) {
        if (!pdgs.values().contains(pdg)) {
            pdg.getNodes().stream().forEach(node -> add(node));
            pdg.getEdges().stream().forEach(edge -> add(edge));
            pdgs.put(pdg.getQualifiedName().fqn(), pdg);
        }
    }
    
    /**
     * Returns ClDGs contained in this dependency graph.
     * @return the collection of all the contained ClDGs
     */
    public Set<ClDG> getClDGs() {
        return new HashSet<ClDG>(cldgs.values());
    }
    
    /**
     * Returns PDGs contained in this dependency graph.
     * @return the collection of all the contained PDGs
     */
    public Set<PDG> getPDGs() {
        return new HashSet<PDG>(pdgs.values());
    }
    
    /**
     * Finds a ClDG having a given name from ClDGs contained in this dependency graph.
     * @param fqn the fully-qualified name of the ClDG to be retrieved
     * @return the found ClDG, or {@code null} if the corresponding ClDG is not found
     */
    public ClDG getClDG(String fqn) {
        return cldgs.get(fqn);
    }
    
    /**
     * Finds a PDG having a given name from PDGs contained in this dependency graph.
     * @param fqn the fully-qualified name of the PDG to be retrieved
     * @return the found PDG, or {@code null} if the corresponding PDG is not found
     */
    public PDG getPDG(String fqn) {
        return pdgs.get(fqn);
    }
    
    /**
     * Finds a ClDG corresponding to a given class from ClDGs contained in this dependency graph.
     * @param jclass the class for the ClDG to be retrieved
     * @return the found ClDG, or {@code null} if the corresponding ClDG is not found
     */
    public ClDG getClDG(JavaClass jclass) {
        assert jclass != null;
        
        return getClDG(jclass.getQualifiedName().fqn());
    }
    
    /**
     * Finds a PDG corresponding to a given method from PDGs contained in this dependency graph.
     * @param jmethod the method for the PDG to be retrieved
     * @return the found PDG, or {@code null} if the corresponding PDG is not found
     */
    public PDG getPDG(JavaMethod jmethod) {
            assert jmethod != null;
            
            return getPDG(jmethod.getQualifiedName().fqn());
    }
    
    /**
     * Finds a PDG corresponding to a given field from PDGs contained in this dependency graph.
     * @param jmethod the field for the PDG to be retrieved
     * @return the found PDG, or {@code null} if the corresponding PDG is not found
     */
    public PDG getPDG(JavaField jfield) {
        assert jfield!= null;
        
        return getPDG(jfield.getQualifiedName().fqn());
    }
    
    /**
     * Adds a node to this dependency graph.
     * This method is not intended to be invoked by clients.
     * @param node the node to be added
     */
    public void add(PDGNode node) {
        assert node != null;
        
        nodes.add(node);
    }
    
    /**
     * Adds an edge to this dependency graph.
     * This method is not intended to be invoked by clients.
     * @param edge the dependence edge to be added
     */
    public void add(DependencyGraphEdge edge) {
        assert edge != null;
        
        if (edge.isInterPDGEdge()) {
            interPDGEdgeMapWithSrcKey.put(edge.getSrcNode(), edge);
            interPDGEdgeMapWithDstKey.put(edge.getDstNode(), edge);
        } else {
            edges.add(edge);
        }
    }
    
    /**
     * Returns all nodes of this dependency graph.
     * @return the collection of the nodes
     */
    public Set<PDGNode> getNodes() {
        return nodes;
    }
    
    /**
     * Returns all edges of this dependency graph.
     * @return the collection of the edges
     */
    public List<DependencyGraphEdge> getEdges() {
        List<DependencyGraphEdge> allEdges = new ArrayList<>();
        allEdges.addAll(edges);
        allEdges.addAll(interPDGEdgeMapWithSrcKey.values());
        return allEdges;
    }
    
    /**
     * Returns all control dependence edges of this dependency graph.
     * @return the collection of the edges
     */
    public List<DependencyGraphEdge> getCDEdges() {
        return getEdges().stream().filter(e -> e.isCD()).collect(Collectors.toList());
    }
    
    /**
     * Returns all data dependence edges of this dependency graph.
     * @return the collection of the edges
     */
    public List<DependencyGraphEdge> getDDEdges() {
        return getEdges().stream().filter(e -> e.isDD()).collect(Collectors.toList());
    }
    
    /**
     * Obtains edges that connect nodes in different PDGs.
     * @return the collection of the edges
     */
    List<DependencyGraphEdge> getInterPDGEdges() {
        return new ArrayList<DependencyGraphEdge>(interPDGEdgeMapWithSrcKey.values());
    }
    
    /**
     * Obtains dependence edges incoming to a given node.
     * @param node the node
     * @return the collection of the incoming edges
     */
    public List<DependencyGraphEdge> getIncomingEdges(PDGNode node) {
        assert node != null;
        
        List<DependencyGraphEdge> allEdges = new ArrayList<>();
        allEdges.addAll(node.getIncomingEdges().stream()
                .map(e -> (DependencyGraphEdge)e).collect(Collectors.toList()));
        allEdges.addAll(interPDGEdgeMapWithDstKey.get(node));
        return allEdges;
    }
    
    /**
     * Obtains control dependence edges incoming to a given node.
     * @param node the node
     * @return the collection of the incoming edges
     */
    public List<DependencyGraphEdge> getIncomingCDEdges(PDGNode node) {
        assert node != null;
        
        return getIncomingEdges(node).stream().filter(e -> e.isCD()).collect(Collectors.toList());
    }
    
    /**
     * Obtains data dependence edges incoming to this node.
     * @return the collection of the incoming edges
     */
    public List<DependencyGraphEdge> getIncomingDDEdges(PDGNode node) {
        assert node != null;
        
        return getIncomingEdges(node).stream().filter(e -> e.isDD()).collect(Collectors.toList());
    }
    
    /**
     * Obtains dependence edges outgoing from a given node.
     * @param node the node
     * @return the collection of the outgoing edges
     */
    public List<DependencyGraphEdge> getOutgoingEdges(PDGNode node) {
        assert node != null;
        
        List<DependencyGraphEdge> allEdges = new ArrayList<>();
        allEdges.addAll(node.getOutgoingEdges().stream()
                .map(e -> (DependencyGraphEdge)e).collect(Collectors.toList()));
        allEdges.addAll(interPDGEdgeMapWithSrcKey.get(node));
        return allEdges;
    }
    
    /**
     * Obtains control dependence edges outgoing from a given node.
     * @param node the node
     * @return the collection of the outgoing edges
     */
    public List<DependencyGraphEdge> getOutgoingCDEdges(PDGNode node) {
        assert node != null;
        
        return getOutgoingEdges(node).stream().filter(e -> e.isCD()).collect(Collectors.toList());
    }
    
    /**
     * Obtains data dependence edges outgoing from a given node.
     * @param node the node
     * @return the collection of the outgoing edges
     */
    public List<DependencyGraphEdge> getOutgoingDDEdges(PDGNode node) {
        assert node != null;
        
        return getOutgoingEdges(node).stream().filter(e -> e.isDD()).collect(Collectors.toList());
    }
    
    /**
     * Adds an edge that uncovers an field access.
     * This method is not intended to be invoked by clients.
     * @param edge the edge to be added
     */
    public void addUncoveredFieldAccessEdge(InterPDGDD edge) {
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
        assert src != null;
        assert dst != null;
        
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
        return graph != null && (this == graph || getName().equals(graph.getName()));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return getName().hashCode();
    }
    
    /**
     * Displays information on this graph.
     */
    public void print() {
        System.out.println(toString());
    }
    
    /**
     * Obtains information on this dependency graph.
     * @return the string representing the information on this dependency graph
     */
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("----- (from here) -----\n");
        buf.append("Name = " + getName());
        buf.append("\n");
        buf.append(toStringForNodes()); 
        buf.append(toStringForEdges());
        buf.append("----- (to here) -----\n");
        return buf.toString();
    }
    
    /**
     * Obtains information on all nodes enclosed in this dependency graph.
     * @return the string representing the information
     */
    private String toStringForNodes() {
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
    private String toStringForEdges() {
        StringBuffer buf = new StringBuffer();
        int index = 1;
        for (DependencyGraphEdge edge : DependencyGraphEdge.sortEdges(getEdges())) {
            buf.append(String.valueOf(index));
            buf.append(": ");
            buf.append(edge.toString());
            buf.append("\n");
            index++;
        }
        return buf.toString();
    }
}
