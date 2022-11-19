/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.cfg.CFGNode;
import org.jtool.graph.GraphNode;
import java.util.List;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * A node of PDGs, ClDGs, and SDG.
 * 
 * @author Katsuhisa Maruyama
 */
public class PDGNode extends GraphNode {
    
    /**
     * The CFG node corresponding to this node.
     */
    protected final CFGNode cfgnode;
    
    /**
     * Creates a new object that represents a PDG node.
     * This method is not intended to be invoked by clients.
     * @param node the CFG node corresponding to this node
     */
    protected PDGNode(CFGNode node) {
        super(node.getId());
        
        cfgnode = node;
        cfgnode.setPDGNode(this);
    }
    
    /**
     * Returns the CFG node corresponding to this node.
     * @return the corresponding CFG node
     */
    public CFGNode getCFGNode() {
        return cfgnode;
    }
    
    /**
     * Tests if this represents a statement.
     * @return {@code true} if this is a statement node, otherwise {@code false}
     */
    public boolean isStatement() {
        return cfgnode.isStatement();
    }
    
    /**
     * Tests if this represents a parameter.
     * @return {@code true} if this is a parameter node, otherwise {@code false}
     */
    public boolean isParameter() {
        return cfgnode.isParameter();
    }
    
    /**
     * Tests if this node causes branching.
     * @return {@code true} if this node causes branching, otherwise {@code false}
     */
    public boolean isBranch() {
        return cfgnode.isBranch();
    }
    
    /**
     * Tests if this node causes looping.
     * @return {@code true} if this node causes looping, otherwise {@code false}
     */
    public boolean isLoop() {
        return cfgnode.isLoop();
    }
    
    /**
     * Tests if this node indicates the entry for a method, a field, or a class.
     * @return {@code true} if this is an entry node, otherwise {@code false}
     */
    public boolean isEntry() {
        return false;
    }
    
    /**
     * Tests if a given PDG node is equal to this node.
     * @param node the node to be checked
     * @return the {@code true} if the given node is equal to this node
     */
    public boolean equals(PDGNode node) {
        return super.equals((GraphNode)node);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    /**
     * Displays information on this node.
     */
    public void print() {
        System.out.println(toString());
    }
    
    /**
     * Obtains information on this node.
     * @return the string representing the information
     */
    @Override
    public String toString() {
        return cfgnode.toString();
    }
    
    /**
     * Sorts the list of PDG nodes
     * @param nodes the collection of the PDG nodes to be sorted
     * @return the sorted list of the PDG nodes
     */
    public static List<PDGNode> sortNodes(Collection<? extends PDGNode> nodes) {
        return nodes.stream()
                    .sorted(Comparator.comparingLong(PDGNode::getId))
                    .collect(Collectors.toList());
    }
}
