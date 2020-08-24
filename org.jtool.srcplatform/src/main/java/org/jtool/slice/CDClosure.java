/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.slice;

import org.jtool.pdg.PDGNode;
import java.util.List;
import java.util.ArrayList;

/**
 * An object storing information about a closure created by traversing only control dependence edges.
 * 
 * @author Katsuhisa Maruyama
 */
public class CDClosure {
    
    /**
     * Obtains a closure created by forward traversing only control dependence edges from a given node.
     * @param anchor the starting node
     * @return the collection of PDG nodes contained in the closure
     */
    public static List<PDGNode> getForwardCDClosure(PDGNode anchor) {
        List<PDGNode> nodes = new ArrayList<>();
        traverseForwardCD(anchor, nodes);
        return nodes;
    }
    
    private static void traverseForwardCD(PDGNode node, List<PDGNode> nodes) {
        if (nodes.contains(node)) {
            return;
        }
        nodes.add(node);
        
        node.getOutgoingCDEdges().forEach(edge -> {
            PDGNode next = edge.getSrcNode();
            traverseForwardCD(next, nodes);
        });
    }
    
    /**
     * Obtains a closure created by backward traversing only control dependence edges from a given node.
     * @param anchor the starting node
     * @return the collection of PDG nodes contained in the closure
     */
    public static List<PDGNode> getBackwardCDClosure(PDGNode anchor) {
        List<PDGNode> nodes = new ArrayList<>();
        traverseBackwardCD(anchor, nodes);
        return nodes;
    }
    
    private static void traverseBackwardCD(PDGNode node, List<PDGNode> nodes) {
        if (nodes.contains(node)) {
            return;
        }
        nodes.add(node);
        
        node.getIncomingCDEdges().forEach(edge -> {
            PDGNode next = edge.getSrcNode();
            traverseBackwardCD(next, nodes);
        });
    }
}
