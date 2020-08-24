/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import java.util.List;
import java.util.ArrayList;

/**
 * An object storing information on a control flow graph (CFG).
 * 
 * @author Katsuhisa Maruyama
 */
public class CFG extends CommonCFG {
    
    /**
     * The collection of basic blocks existing in this CFG.
     */
    private List<BasicBlock> basicBlocks = new ArrayList<>();
    
    /**
     * Appends all nodes and edges of a given CFG to this CFG.
     * @param cfg the CFG to be appended
     */
    public void append(CFG cfg) {
        cfg.getNodes().forEach(node -> add(node));
        cfg.getEdges().forEach(edge -> add(edge));
    }
    
    /**
     * Adds a basic block to this CFG.
     * @param block the basic block to be added
     */
    public void add(BasicBlock block) {
        basicBlocks.add(block);
    }
    
    /**
     * Returns all basic blocks existing in this CFG.
     * @return the collection of the basic blocks
     */
    public List<BasicBlock> getBasicBlocks() {
        return basicBlocks;
    }
    
    /**
     * Obtains information on this CFG.
     * @return the string representing the information
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("----- CFG of " + getQualifiedName() + "-----\n");
        buf.append(toStringForNodes()); 
        buf.append(toStringForEdges());
        buf.append("-----------------------------------\n");
        return buf.toString();
    }
}
