/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.QualifiedName;
import org.jtool.cfg.CCFG;
import org.jtool.cfg.CCFGEntry;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

/**
 * An object storing information on a class dependence graph (ClDG) for a class.
 * 
 * @author Katsuhisa Maruyama
 */
public class ClDG extends DependencyGraph {
    
    /**
     * The map between the fully-qualified names and PDGs that have their corresponding names.
     */
    private Map<String, PDG> pdgs = new HashMap<>();
    
    /**
     * The entry node of this ClDG.
     */
    private ClDGEntry entry;
    
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
    public boolean isClDG() {
        return true;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PDG> getPDGs() {
        return new HashSet<>(pdgs.values());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public PDG findPDG(String fqn) {
        return pdgs.get(fqn);
    }
    
    /**
     * Adds a PDG to this ClDG.
     * This method is not intended to be invoked by clients.
     * @param pdg the PDG to be added
     */
    public void add(PDG pdg) {
        if (!pdgs.values().contains(pdg)) {
            pdgs.put(pdg.getQualifiedName().fqn(), pdg);
            addNodes(pdg.getNodes());
            addEdges(pdg.getEdges());
            pdg.getSpecificEdges().forEach(e -> add(e));
        }
    }
    
    /**
     * Finds a PDG corresponding to a given method from PDGs contained in this ClDG.
     * @param jmethod the method for the PDG to be retrieved
     * @return the found PDG, or {@code null} if the corresponding PDG is not found
     */
    public PDG findPDG(JavaMethod jmethod) {
        return pdgs.get(jmethod.getQualifiedName().fqn());
    }
    
    /**
     * Finds a PDG corresponding to a given field from PDGs contained in this ClDG.
     * @param jmethod the field for the PDG to be retrieved
     * @return the found PDG, or {@code null} if the corresponding PDG is not found
     */
    public PDG findPDG(JavaField jfield) {
        return pdgs.get(jfield.getQualifiedName().fqn());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return toString("ClDG");
    }
}
