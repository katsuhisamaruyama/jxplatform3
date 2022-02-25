/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.slice;

import org.jtool.pdg.DD;
import org.jtool.pdg.PDGNode;
import org.jtool.cfg.JReference;
import java.util.List;
import java.util.ArrayList;

/**
 * An object storing information about a closure created by traversing only data dependence edges.
 * 
 * @author Katsuhisa Maruyama
 */
public class DDClosure {
    
    /**
     * Obtains a closure created by forward traversing only data dependence edges from a given node.
     * @param anchor the starting node
     * @param jv a variable of interest appearing in the starting node
     * @return the collection of PDG nodes contained in the closure
     */
    public static List<PDGNode> getForwardCDClosure(PDGNode anchor, JReference jv) {
        List<PDGNode> nodes = new ArrayList<>();
        for (DD edge : anchor.getOutgoingDDEdges()) {
            if (edge.getVariable().equals(jv)) {
                PDGNode next = edge.getSrcNode();
                traverseForwardDD(next, nodes);
            }
        }
        return nodes;
    }
    
    private static void traverseForwardDD(PDGNode node, List<PDGNode> nodes) {
        if (nodes.contains(node)) {
            return;
        }
        nodes.add(node);
        
        node.getOutgoingDDEdges().forEach(edge -> {
            PDGNode next = edge.getSrcNode();
            traverseForwardDD(next, nodes);
        });
    }
    
    /**
     * Obtains a closure created by backward traversing only data dependence edges from a given node.
     * @param anchor the starting node
     * @param jv a variable of interest appearing in the starting node
     * @return the collection of PDG nodes contained in the closure
     */
    public static List<PDGNode> getBackwardCDClosure(PDGNode anchor, JReference jv) {
        List<PDGNode> nodes = new ArrayList<>();
        for (DD edge : anchor.getIncomingDDEdges()) {
            if (edge.getVariable().equals(jv)) {
                PDGNode next = edge.getSrcNode();
                traverseBackwardDD(next, nodes);
            }
        }
        return nodes;
    }
    
    private static void traverseBackwardDD(PDGNode node, List<PDGNode> nodes) {
        if (nodes.contains(node)) {
            return;
        }
        nodes.add(node);
        
        node.getIncomingDDEdges().forEach(edge -> {
            PDGNode next = edge.getSrcNode();
            traverseBackwardDD(next, nodes);
        });
    }
}
