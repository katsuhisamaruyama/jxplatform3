/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGEntry;
import org.jtool.pdg.builder.BarePDG;
import org.jtool.srcmodel.QualifiedName;
import java.util.Set;
import java.util.HashSet;

/**
 * An object storing information on a program dependence graph (PDG) for a method or a field.
 * 
 * @author Katsuhisa Maruyama
 */
public class PDG extends DependencyGraph {
    
    /**
     * A bare PDG corresponding to this PDG.
     */
    private BarePDG bpdg;
    
    /**
     * The entry node of this PDG.
     */
    private PDGEntry entry;
    
    /**
     * Creates a new, empty object for storing a PDG information.
     * This method is not intended to be invoked by clients.
     */
    public PDG() {
    }
    
    /**
     * Creates and returns a copy of this PDG.
     * @return the copy.
     */
    @Override
    public PDG clone() {
        PDG clone = new PDG();
        clone.bpdg = bpdg;
        clone.entry = entry;
        return clone;
    }
    
    /**
     * Sets a bare PDG for this PDG.
     * This method is not intended to be invoked by clients.
     * @param bpdg the bare PDG
     */
    public void setBarePDG(BarePDG bpdg) {
        assert bpdg != null;
        
        this.bpdg = bpdg;
        addNodes(bpdg.getNodes());
        addEdges(bpdg.getEdges());
    }
    
    /**
     * Returns a bare PDG corresponding to this PDG.
     * @return the corresponding bare PDG
     */
    public BarePDG getBarePDG() {
        return bpdg;
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
        return entry.getSignature();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CFG getCFG() {
        CFGEntry node = (CFGEntry)entry.getCFGNode();
        return node.getCFG();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPDG() {
        return true;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PDG> getPDGs() {
        Set<PDG> pdgs = new HashSet<>();
        pdgs.add(this);
        return pdgs;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public PDG findPDG(String fqn) {
        return getQualifiedName().fqn().equals(fqn) ? this : null;
    }
    
    /**
     * Obtains information on this PDG.
     * @return the string representing the information on this PDG
     */
    @Override
    public String toString() {
        return toString("PDG");
    }
}
