/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.cfg.CCFG;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGEntry;
import org.jtool.srcmodel.QualifiedName;

/**
 * An object storing information on a program dependence graph (PDG).
 * 
 * @author Katsuhisa Maruyama
 */
public class PDG extends DependenceGraph {
    
    /**
     * The entry node of this PDG.
     */
    protected PDGEntry entry;
    
    /**
     * Sets the entry node for this PDG.
     * This method is not intended to be invoked by clients.
     * @param node the entry node to be set
     */
    public void setEntryNode(PDGEntry node) {
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
    public CCFG getCCFG() {
        return null;
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
     * Tests if this graph represents a PDG.
     * @return always {@code true}
     */
    @Override
    public boolean isPDG() {
        return true;
    }
}
