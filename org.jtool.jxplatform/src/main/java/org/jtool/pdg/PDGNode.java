/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.cfg.CFGNode;
import org.jtool.graph.GraphNode;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
    protected CFGNode cfgnode;
    
    /**
     * Creates a new object that represents a PDG node.
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
     * Tests if this node causes joining.
     * @return {@code true} if this node causes joining, otherwise {@code false}
     */
    public boolean isJoin() {
        return cfgnode.isJoin();
    }
    
    /**
     * Obtains dependence edges incoming to this node.
     * @return the collection of the incoming edges
     */
    public Set<Dependence> getIncomingDependeceEdges() {
        return getIncomingEdges().stream()
                                 .map(edge -> (Dependence)edge)
                                 .collect(Collectors.toSet());
    }
    
    /**
     * Obtains dependence edges outgoing from this node.
     * @return the collection of the outgoing edges
     */
    public Set<Dependence> getOutgoingDependeceEdges() {
        return getOutgoingEdges().stream()
                                 .map(edge -> (Dependence)edge)
                                 .collect(Collectors.toSet());
    }
    
    /**
     * Obtains control dependence edges incoming to this node.
     * @return the collection of the incoming edges
     */
    public Set<CD> getIncomingCDEdges() {
        return getIncomingEdges().stream()
                                 .map(edge -> (Dependence)edge)
                                 .filter(edge -> edge.isCD())
                                 .map(edge -> (CD)edge)
                                 .collect(Collectors.toSet());
    }
    
    /**
     * Obtains control dependence edges outgoing from this node.
     * @return the collection of the outgoing edges
     */
    public Set<CD> getOutgoingCDEdges() {
        return getOutgoingEdges().stream()
                                 .map(edge -> (Dependence)edge)
                                 .filter(edge -> edge.isCD())
                                 .map(edge -> (CD)edge)
                                 .collect(Collectors.toSet());
    }
    
    /**
     * Obtains data dependence edges incoming to this node.
     * @return the collection of the incoming edges
     */
    public Set<DD> getIncomingDDEdges() {
        return getIncomingEdges().stream()
                                 .map(edge -> (Dependence)edge)
                                 .filter(edge -> edge.isDD())
                                 .map(edge -> (DD)edge)
                                 .collect(Collectors.toSet());
    }
    
    /**
     * Obtains data dependence edges outgoing from this node.
     * @return the collection of the outgoing edges
     */
    public Set<DD> getOutgoingDDEdges() {
        return getOutgoingEdges().stream()
                                 .map(edge -> (Dependence)edge)
                                 .filter(edge -> edge.isDD())
                                 .map(edge -> (DD)edge)
                                 .collect(Collectors.toSet());
    }
    
    /**
     * Tests if this node is dominated by any control flow.
     * @return {@code true} if this is a dominated node, otherwise {@code false}
     */
    public boolean isDominated() {
        return !getIncomingCDEdges().isEmpty();
    }
    
    /**
     * Tests if this node is dominated by a true control flow.
     * @return {@code true} if this is a true-dominated node, otherwise {@code false}
     */
    public boolean isTrueDominated() {
        return getIncomingCDEdges().stream().anyMatch(cd -> cd.isTrue());
    }
    
    /**
     * Tests if this node is dominated by a false control flow.
     * @return {@code true} if this is a false-dominated node, otherwise {@code false}
     */
    public boolean isFalseDominated() {
        return getIncomingCDEdges().stream().anyMatch(cd -> cd.isFalse());
    }
    
    /**
     * Returns the number of dependence edges incoming to this node.
     * @return the number of the incoming edges
     */
    public int getNumOfIncomingTrueFalseCDs() {
        return (int)getIncomingCDEdges().stream().filter(cd -> cd.isTrue() || cd.isFalse()).count();
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
        return getCFGNode().toString();
    }
    
    /**
     * Sorts the list of PDG nodes
     * @param co the list of the PDG nodes to be sorted
     * @return the sorted list of the PDG nodes
     */
    public static List<PDGNode> sortPDGNodes(Collection<? extends PDGNode> co) {
        List<PDGNode> nodes = new ArrayList<>(co);
        Collections.sort(nodes, new Comparator<>() {
            
            @Override
            public int compare(PDGNode node1, PDGNode node2) {
                return (node2.id == node1.id) ? 0 : (node1.id > node2.id) ? 1 : -1;
            }
        });
        return nodes;
    }
}
