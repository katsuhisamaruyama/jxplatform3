/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.slice;

import org.jtool.pdg.DependencyGraph;
import org.jtool.pdg.DD;
import org.jtool.pdg.PDGNode;
import org.jtool.cfg.JReference;
import java.util.Set;
import java.util.HashSet;

/**
 * An object storing information about a closure created by traversing only data dependence edges.
 * 
 * @author Katsuhisa Maruyama
 */
public class DDClosure {
    
    /**
     * Obtains a closure created by forward traversing only data dependence edges from a given node.
     * @param graph a graph containing the starting node
     * @param anchor the starting node
     * @param jv a variable of interest appearing in the starting node
     * @return the collection of PDG nodes contained in the closure
     */
    public static Set<PDGNode> getForwardDDClosure(DependencyGraph graph, PDGNode anchor, JReference jv) {
        Set<PDGNode> nodes = new HashSet<>();
        for (DD edge : graph.getOutgoingDDEdges(anchor)) {
            if (edge.getVariable().equals(jv)) {
                PDGNode next = edge.getSrcNode();
                traverseForwardDD(graph, next, nodes);
            }
        }
        return nodes;
    }
    
    private static void traverseForwardDD(DependencyGraph graph, PDGNode node, Set<PDGNode> nodes) {
        if (nodes.contains(node)) {
            return;
        }
        nodes.add(node);
        
        graph.getOutgoingDDEdges(node).forEach(edge -> {
            PDGNode next = edge.getSrcNode();
            traverseForwardDD(graph, next, nodes);
        });
    }
    
    /**
     * Obtains a closure created by backward traversing only data dependence edges from a given node.
     * @param graph a graph containing the starting node
     * @param anchor the starting node
     * @param jv a variable of interest appearing in the starting node
     * @return the collection of PDG nodes contained in the closure
     */
    public static Set<PDGNode> getBackwardDDClosure(DependencyGraph graph, PDGNode anchor, JReference jv) {
        Set<PDGNode> nodes = new HashSet<>();
        for (DD edge : graph.getIncomingDDEdges(anchor)) {
            if (edge.getVariable().equals(jv)) {
                PDGNode next = edge.getSrcNode();
                traverseBackwardDD(graph, next, nodes);
            }
        }
        return nodes;
    }
    
    private static void traverseBackwardDD(DependencyGraph graph, PDGNode node, Set<PDGNode> nodes) {
        if (nodes.contains(node)) {
            return;
        }
        nodes.add(node);
        
        graph.getIncomingDDEdges(node).forEach(edge -> {
            PDGNode next = edge.getSrcNode();
            traverseBackwardDD(graph, next, nodes);
        });
    }
}
