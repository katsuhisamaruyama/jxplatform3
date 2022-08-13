/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.slice;

import org.jtool.pdg.DependencyGraph;
import org.jtool.pdg.PDGNode;
import java.util.Set;
import java.util.HashSet;

/**
 * An object storing information about a closure created by traversing only control dependence edges.
 * 
 * @author Katsuhisa Maruyama
 */
public class CDClosure {
    
    /**
     * Obtains a closure created by forward traversing only control dependence edges from a given node.
     * @param graph a graph containing the starting node
     * @param anchor the starting node
     * @return the collection of PDG nodes contained in the closure
     */
    public static Set<PDGNode> getForwardCDClosure(DependencyGraph graph, PDGNode anchor) {
        Set<PDGNode> nodes = new HashSet<>();
        traverseForwardCD(graph, anchor, nodes);
        return nodes;
    }
    
    private static void traverseForwardCD(DependencyGraph graph, PDGNode node, Set<PDGNode> nodes) {
        if (nodes.contains(node)) {
            return;
        }
        nodes.add(node);
        
        graph.getOutgoingCDEdges(node).forEach(edge -> {
            PDGNode next = edge.getSrcNode();
            traverseForwardCD(graph, next, nodes);
        });
    }
    
    /**
     * Obtains a closure created by backward traversing only control dependence edges from a given node.
     * @param graph a graph containing the starting node
     * @param anchor the starting node
     * @return the collection of PDG nodes contained in the closure
     */
    public static Set<PDGNode> getBackwardCDClosure(DependencyGraph graph, PDGNode anchor) {
        Set<PDGNode> nodes = new HashSet<>();
        traverseBackwardCD(graph, anchor, nodes);
        return nodes;
    }
    
    private static void traverseBackwardCD(DependencyGraph graph, PDGNode node, Set<PDGNode> nodes) {
        if (nodes.contains(node)) {
            return;
        }
        nodes.add(node);
        
        graph.getIncomingCDEdges(node).forEach(edge -> {
            PDGNode next = edge.getSrcNode();
            traverseBackwardCD(graph, next, nodes);
        });
    }
}
