/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import java.util.Set;
import java.util.HashSet;

/**
 * An object storing information about a basic block of a CFG.
 * 
 * @author Katsuhisa Maruyama
 */
public class BasicBlock {
    
    /**
     * The identification number for this basic block.
     */
    private int id;
    
    /**
     * The leader node for this basic block.
     */
    private CFGNode leader;
    
    /**
     * the collection of nodes contained in this basic block.
     */
    private Set<CFGNode> nodes = new HashSet<CFGNode>();
    
    /**
     * The number prepared for generating the identification number when creating a new basic block.
     */
    private static int num = 0;
    
    /**
     * Creates a new, empty object for storing the basic block having a given leader node.
     * @param node the leader node of this basic block
     */
    public BasicBlock(CFGNode node) {
        num++;
        id = num;
        leader = node;
    }
    
    /**
     * Returns the identification number for this basic block.
     * @return the identification number
     */
    public int getId() {
        return id;
    }
    
    /**
     * Returns the leader node for this basic block.
     * @return the leader node
     */
    public CFGNode getLeader() {
        return leader;
    }
    
    /**
     * Adds a CFG node to this basic block.
     * This method is not intended to be invoked by clients.
     * @param node the node to be added
     */
    public void add(CFGNode node) {
        nodes.add(node);
        node.setBasicBlock(this);
    }
    
    /**
     * Returns nodes contained in this basic block.
     * @return the collection of the contained nodes
     */
    public Set<CFGNode> getNodes() {
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
     * Tests if this basic block has any node.
     * @return {@code true} if this basic block has any node, otherwise {@code false}
     */
    public boolean isEmpty() {
        return nodes.isEmpty();
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
