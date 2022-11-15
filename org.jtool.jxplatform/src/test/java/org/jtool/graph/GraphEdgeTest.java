/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.graph;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class GraphEdgeTest {
    
    private static ConcreteGraphNode createNode(long id) {
        return new ConcreteGraphNode(id);
    }
    
    private static ConcreteGraphEdge createEdge(GraphNode src, GraphNode dst) {
        return new ConcreteGraphEdge(src, dst);
    }
    
    @Test
    public void testGetSrcNode1() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphEdge e12 = createEdge(n1, n2);
        GraphNode result = e12.getSrcNode();
        
        assertEquals("1", result.toString());
    }
    
    @Test
    public void testGetDstNode1() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphEdge e12 = createEdge(n1, n2);
        GraphNode result = e12.getDstNode();
        
        assertEquals("2", result.toString());
    }
    
    @Test
    public void testsetSrcNode1() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphEdge e12 = createEdge(n1, n2);
        e12.setSrcNode(n3);
        GraphNode result = e12.getSrcNode();
        
        assertEquals("3", result.toString());
    }
    
    @Test
    public void testSetDstNode1() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n4 = createNode(4);
        ConcreteGraphEdge e12 = createEdge(n1, n2);
        e12.setDstNode(n4);
        GraphNode result = e12.getDstNode();
        
        assertEquals("4", result.toString());
    }
}

class ConcreteGraphEdge extends GraphEdge {
    
    ConcreteGraphEdge(GraphNode src, GraphNode dst) {
        super(src, dst);
    }
    
    @Override
    public String toString() {
        return String.valueOf(src.id + "/" + dst.id);
    }
    
    public static String asSortedStr(List<GraphEdge> edges) {
        return edges.stream().map(GraphEdge::toString).sorted().collect(Collectors.joining(", "));
    }
}
