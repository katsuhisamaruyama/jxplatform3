/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.srcmodel.QualifiedName;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CCFG;
import org.jtool.cfg.CCFGEntry;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * An object storing information on a class dependence graph (ClDG).
 * 
 * @author Katsuhisa Maruyama
 */
public class ClDG extends DependenceGraph {
    
    /**
     * The entry node of this ClDG.
     */
    private ClDGEntry entry;
    
    /**
     * The map between the fully-qualified names and PDGs that have their corresponding names.
     */
    private Map<String, PDG> pdgs = new HashMap<>();
    
    /**
     * Sets the entry node for this ClDG.
     * This method is not intended to be invoked by clients.
     * @param node the entry node to be set
     */
    public void setEntryNode(ClDGEntry node) {
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
     * {@inheritDoc}
     */
    @Override
    public long getId() {
        return entry.getId();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public QualifiedName getQualifiedName() {
        return entry.getQualifiedName();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getSignature() {
        return entry.getQualifiedName().fqn();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CCFG getCCFG() {
        CCFGEntry node = entry.getCCFGEntry();
        return node.getCCFG();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CFG getCFG() {
        return null;
    }
    
    /**
     * Tests if this graph represents a ClDG.
     * @return always {@code true}
     */
    @Override
    public boolean isClDG() {
        return true;
    }
    
    /**
     * Adds a PDG to this ClDG.
     * This method is not intended to be invoked by clients.
     * @param pdg the PDG to be added
     */
    public void add(PDG pdg) {
        if (!pdgs.values().contains(pdg)) {
            pdgs.put(pdg.getQualifiedName().fqn(), pdg);
        }
    }
    
    /**
     * Returns PDGs contained in this ClDG.
     * @return the collection of the contained PDGs
     */
    public Set<PDG> getPDGs() {
        return new HashSet<>(pdgs.values());
    }
    
    /**
     * Finds a PDG having a given name from PDGs contained in this ClDG.
     * @param fqn the fully-qualified name of the PDG to be retrieved
     * @return the found PDG, or {@code null} if the corresponding PDG is not found
     */
    public PDG getPDG(String fqn) {
        return pdgs.get(fqn);
    }
    
    /**
     * Returns nodes contained in this ClDG.
     * @return the collection of the contained nodes
     */
    public Set<PDGNode> getNodes() {
        Set<PDGNode> nodes = pdgs.values().stream()
                                          .flatMap(pdg -> pdg.getNodes().stream())
                                          .collect(Collectors.toSet());
        nodes.add(entry);
        return nodes;
    }
    
    /**
     * Returns dependence edges contained in this ClDG.
     * @return the collection of the contained edges
     */
    public Set<Dependence> getEdges() {
        Set<Dependence> edges = pdgs.values().stream()
                                             .flatMap(pdg -> pdg.getEdges().stream())
                                             .collect(Collectors.toSet());
        edges.addAll(entry.getOutgoingDependeceEdges());
        return edges;
    }
    
    /**
     * Displays information on this graph.
     */
    public void print() {
        System.out.println(toString());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("----- ClDG (from here) -----\n");
        buf.append("Class Name = " + getQualifiedName());
        buf.append("\n");
        buf.append(toStringForNodes());
        buf.append(toStringForEdges());
        buf.append("----- ClDG (to here) -----\n");
        return buf.toString();
    }
}
