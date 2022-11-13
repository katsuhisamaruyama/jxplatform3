/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import java.util.List;
import java.util.ArrayList;

/**
 * An object storing information about a basic block of a CFG.
 * 
 * @author Katsuhisa Maruyama
 */
public class BasicBlock {
    
    /**
     * The leader node for this basic block.
     */
    private final CFGNode leader;
    
    /**
     * The the ordered list of nodes contained in this basic block.
     */
    private List<CFGNode> nodes = new ArrayList<>();
    
    /**
     * Creates a new, empty object for storing the basic block having a given leader node.
     * @param node the leader node of this basic block
     */
    public BasicBlock(CFGNode node) {
        assert node != null;
        
        leader = node;
    }
    
    /**
     * Returns the leader node for this basic block.
     * @return the leader node
     */
    public CFGNode getLeader() {
        return leader;
    }
    
    /**
     * Returns the identification number for this basic block.
     * @return the identification number
     */
    public long getId() {
        return leader.getId();
    }
    
    /**
     * Adds a CFG node to this basic block.
     * This method is not intended to be invoked by clients.
     * @param node the node to be added
     */
    public void add(CFGNode node) {
        assert node != null;
        
        nodes.add(node);
    }
    
    /**
     * Returns nodes contained in this basic block.
     * @return the ordered list of the contained nodes
     */
    public List<CFGNode> getNodes() {
        return nodes;
    }
    
    /**
     * Tests if this basic block contains a given CFG node.
     * @param node the node to be checked
     * @return {@code true} if this basic block contains the node, otherwise {@code false}
     */
    public boolean contains(CFGNode node) {
        return nodes.contains(node);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof BasicBlock) ? equals((BasicBlock)obj) : false;
    }
    
    /**
     * Tests if a given basic block is equal to this basic block.
     * @param block the basic block to be checked
     * @return the {@code true} if the given basic block is equal to this basic block
     */
    public boolean equals(BasicBlock block) {
        return leader.equals(block.leader);
    }
    
    /**
     * Obtains information on this basic block.
     * @return the string representing the information
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("----- Basic Block (from here) -----\n");
        buf.append(toStringForNodes());
        buf.append("----- Basic Block (to here) -----\n");
        return buf.toString();
    }
    
    /**
     * Obtains information on all the nodes enclosed in this basic block.
     * @return the string representing the information
     */
    private String toStringForNodes() {
        StringBuilder buf = new StringBuilder();
        getNodes().forEach(node -> {
            buf.append(node.toString());
            buf.append("\n");
        });
        return buf.toString();
    }
}
