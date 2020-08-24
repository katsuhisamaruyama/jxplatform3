/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGEntry;
import org.jtool.cfg.CommonCFG;

/**
 * An object storing information on a program dependence graph (PDG).
 * 
 * @author Katsuhisa Maruyama
 */
public class PDG extends CommonPDG {
    
    /**
     * Returns the CFG corresponding to this PDG.
     * @return the CFG
     */
    @Override
    public CFG getCFG() {
        CFGEntry node = (CFGEntry)entry.getCFGNode();
        CommonCFG cfg = node.getCFG();
        if (cfg instanceof CFG) {
            return (CFG)cfg;
        }
        return null;
    }
    
    /**
     * Appends all nodes and edges of a given PDG to this PDG.
     * @param pdg the PDG to be appended
     */
    public void append(PDG pdg) {
        pdg.getNodes().forEach(node -> add(node));
        pdg.getEdges().forEach(edge -> add(edge));
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
