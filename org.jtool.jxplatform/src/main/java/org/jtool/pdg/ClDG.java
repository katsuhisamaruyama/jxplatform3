/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.cfg.CCFG;
import org.jtool.cfg.CFGEntry;
import org.jtool.cfg.CommonCFG;
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
public class ClDG extends CommonPDG {
    
    /**
     * The map between the fully-qualified names and PDGs that have their corresponding names.
     */
    protected Map<String, PDG> pdgs = new HashMap<>();
    
    /**
     * Returns the entry node for this ClDG.
     * @return the entry node
     */
    @Override
    public PDGClassEntry getEntryNode() {
        return (PDGClassEntry)entry;
    }
    
    /**
     * Returns the CCFG corresponding to this ClDG.
     * @return the CCFG
     */
    @Override
    public CCFG getCFG() {
        CFGEntry node = (CFGEntry)entry.getCFGNode();
        CommonCFG cfg = node.getCFG();
        return cfg instanceof CCFG ? (CCFG)cfg : null;
    }
    
    /**
     * Adds a PDG to this ClDG.
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
     * Tests if this graph represents a ClDG.
     * @return always {@code true}
     */
    @Override
    public boolean isClDG() {
        return true;
    }
    
    /**
     * Returns nodes contained in this ClDG.
     * @return the collection of the contained nodes
     */
    @Override
    public Set<PDGNode> getNodes() {
        Set<PDGNode> nodes = pdgs.values()
                .stream()
                .flatMap(pdg -> pdg.getNodes().stream())
                .collect(Collectors.toSet());
        nodes.add(entry);
        return nodes;
    }
    
    /**
     * Returns dependence edges contained in this ClDG.
     * @return the collection of the contained edges
     */
    @Override
    public Set<Dependence> getEdges() {
        Set<Dependence> edges = pdgs.values()
                .stream()
                .flatMap(pdg -> pdg.getEdges().stream())
                .collect(Collectors.toSet());
        edges.addAll(entry.getOutgoingDependeceEdges());
        return edges;
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
