/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import static org.jtool.jxplatform.builder.ModelBuilder.br;
import org.jtool.srcmodel.QualifiedName;
import org.jtool.cfg.CCFG;
import org.jtool.cfg.CCFGEntry;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

/**
 * An object storing information on a class dependence graph (ClDG) for a class.
 * 
 * @author Katsuhisa Maruyama
 */
public class ClDG {
    
    /**
     * The entry node of this ClDG.
     */
    private ClDGEntry entry;
    
    /**
     * All edges between a class and its members.
     */
    private List<InterPDGCD> classMemberEdges = new ArrayList<>();
    
    /**
     * The map between the fully-qualified names and PDGs that have their corresponding names.
     */
    private Map<String, PDG> pdgs = new HashMap<>();
    
    /**
     * Creates a new, empty object for storing a ClDG information.
     * This method is not intended to be invoked by clients.
     */
    public ClDG() {
    }
    
    /**
     * Sets the entry node for this ClDG.
     * This method is not intended to be invoked by clients.
     * @param node the entry node to be set
     */
    public void setEntryNode(ClDGEntry node) {
        assert node != null;
        
        entry = node;
        entry.setClDG(this);
    }
    
    /**
     * Returns the entry node for this ClDG.
     * @return the entry node
     */
    public ClDGEntry getEntryNode() {
        return entry;
    }
    
    /**
     * Adds an edge between a class and its member.
     * @param edge an edge to be added
     */
    public void addClassMemberEdge(InterPDGCD edge) {
        assert edge != null;
        
        classMemberEdges.add(edge);
    }
    
    /**
     * Obtains edges between a class and its members.
     * @return the collection of the edges
     */
    public List<InterPDGCD> getInterPDGCDs() {
        return classMemberEdges;
    }
    
    /**
     * Returns all nodes of this ClDG.
     * @return the collection of the nodes
     */
    public Set<PDGNode> getNodes() {
        Set<PDGNode> nodes = new HashSet<>();
        pdgs.values().forEach(pdg -> nodes.addAll(pdg.getNodes()));
        nodes.add(entry);
        return nodes;
    }
    
    /**
     * Returns all edges of this ClDG.
     * @return the collection of the edges
     */
    public List<DependencyGraphEdge> getEdges() {
        List<DependencyGraphEdge> edges = new ArrayList<>();
        pdgs.values().forEach(pdg -> edges.addAll(pdg.getEdges()));
        edges.addAll(classMemberEdges);
        return edges;
    }
    
    /**
     * Returns the fully qualified name of this ClDG.
     * @return the fully qualified name
     */
    public QualifiedName getQualifiedName() {
        return entry.getQualifiedName();
    }
    
    /**
     * Returns a PDG that has a given name.
     * @param fqn the fully-qualified name of the PDG to be retrieved
     * @return the found PDG, or {@code null} if no PDGis found
     */
    public PDG getPDG(String fqn) {
        return pdgs.get(fqn);
    }
    
    /**
     * Adds a PDG to this ClDG.
     * This method is not intended to be invoked by clients.
     * @param pdg the PDG to be added
     */
    public void add(PDG pdg) {
        assert pdg != null;
        
        if (!pdgs.values().contains(pdg)) {
            pdgs.put(pdg.getQualifiedName().fqn(), pdg);
        }
    }
    
    /**
     * Return PDGs contained in this ClDG.
     * @return the collection of the contained PDGs
     */
    public Set<PDG> getPDGs() {
        return new HashSet<PDG>(pdgs.values());
    }
    
    /**
     * Returns the CCFG corresponding to this ClDG.
     * @return the CCFG, or {@code null} if the corresponding CCFG does not exist
     */
    public CCFG getCCFG() {
        CCFGEntry node = entry.getCCFGEntry();
        return node.getCCFG();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ClDG) ? equals((ClDG)obj) : false;
    }
    
    /**
     * Tests if a given ClDG is equal to this ClDG.
     * @param cldg the ClDG to be checked
     * @return the {@code true} if the given ClDG is equal to this ClDG
     */
    public boolean equals(ClDG cldg) {
        return cldg != null && (this == cldg || getQualifiedName().equals(cldg.getQualifiedName()));
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
     * Obtains information on this CCFG.
     * @return the string representing the information
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("----- ClDG (from here) -----" + br);
        buf.append("Class Name = " + getQualifiedName());
        buf.append(br);
        pdgs.values().forEach(cfg -> buf.append(cfg.toStringForNodes() + "--" + br));
        pdgs.values().forEach(cfg -> buf.append(cfg.toStringForEdges() + "--" + br));
        buf.append("----- ClDG (to here) -----" + br);
        return buf.toString();
    }
}
