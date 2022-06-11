/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.graph;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class GraphTest {
    
    private static Graph<GraphNode, GraphEdge> createGraph() {
        return new Graph<GraphNode, GraphEdge>();
    }
    
    private static ConcreteGraphNode createNode(long id) {
        return new ConcreteGraphNode(id);
    }
    
    private static ConcreteGraphEdge createEdge(GraphNode src, GraphNode dst) {
        return new ConcreteGraphEdge(src, dst);
    }
    
    @Test
    public void testGetNodes1() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        
        Set<GraphNode> nodes = new HashSet<>();
        nodes.add(n1);
        nodes.add(n2);
        
        Graph<GraphNode, GraphEdge> g = createGraph();
        g.nodes = nodes;
        Set<GraphNode> result = g.getNodes();
        
        assertEquals("1, 2", ConcreteGraphNode.asSortedStr(result));
    }
    
    @Test
    public void testGetEdges1() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphEdge e12 = createEdge(n1, n2);
        ConcreteGraphEdge e13 = createEdge(n1, n3);
        
        List<GraphEdge> edges = new ArrayList<>();
        edges.add(e12);
        edges.add(e13);
        
        Graph<GraphNode, GraphEdge> g = createGraph();
        g.edges = edges;
        List<GraphEdge> result = g.getEdges();
        
        assertEquals("1/2, 1/3", ConcreteGraphEdge.asSortedStr(result));
    }
    
    @Test
    public void testAddNodes1() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        
        Set<GraphNode> nodes = new HashSet<>();
        nodes.add(n1);
        nodes.add(n2);
        
        Graph<GraphNode, GraphEdge> g = createGraph();
        g.addNodes(nodes);
        
        assertEquals("1, 2", ConcreteGraphNode.asSortedStr(g.getNodes()));
    }
    
    @Test
    public void testAddEdges1() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphEdge e12 = createEdge(n1, n2);
        ConcreteGraphEdge e13 = createEdge(n1, n3);
        
        List<GraphEdge> edges = new ArrayList<>();
        edges.add(e12);
        edges.add(e13);
        
        Graph<GraphNode, GraphEdge> g = createGraph();
        g.addEdges(edges);
        
        assertEquals("1/2, 1/3", ConcreteGraphEdge.asSortedStr(g.getEdges()));
    }
    
    @Test
    public void testAddNode1() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        
        Graph<GraphNode, GraphEdge> g = createGraph();
        g.add(n1);
        g.add(n2);
        
        assertEquals("1, 2", ConcreteGraphNode.asSortedStr(g.getNodes()));
    }
    
    @Test
    public void testAddNode2() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n2a = createNode(2);
        
        Graph<GraphNode, GraphEdge> g = createGraph();
        g.add(n1);
        g.add(n2);
        g.add(n2a);
        
        assertEquals("1, 2", ConcreteGraphNode.asSortedStr(g.getNodes()));
    }
    
    @Test
    public void testAddNode3() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        
        Graph<GraphNode, GraphEdge> g = createGraph();
        g.add(n1);
        g.add(n2);
        g.add(n2);
        
        assertEquals("1, 2", ConcreteGraphNode.asSortedStr(g.getNodes()));
    }
    
    @Test
    public void testGetEdge1() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphEdge e12 = createEdge(n1, n2);
        ConcreteGraphEdge e13 = createEdge(n1, n3);
        
        Graph<GraphNode, GraphEdge> g = createGraph();
        g.add(e12);
        g.add(e13);
        
        assertEquals("1/2, 1/3", ConcreteGraphEdge.asSortedStr(g.getEdges()));
    }
    
    @Test
    public void testAddEdge2() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphEdge e12 = createEdge(n1, n2);
        ConcreteGraphEdge e13 = createEdge(n1, n3);
        ConcreteGraphEdge e13a = createEdge(n1, n3);
        
        Graph<GraphNode, GraphEdge> g = createGraph();
        g.add(e12);
        g.add(e13);
        g.add(e13a);
        
        assertEquals("1/2, 1/3, 1/3", ConcreteGraphEdge.asSortedStr(g.getEdges()));
    }
    
    @Test
    public void testAddEdge3() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphEdge e12 = createEdge(n1, n2);
        ConcreteGraphEdge e13 = createEdge(n1, n3);
        
        Graph<GraphNode, GraphEdge> g = createGraph();
        g.add(e12);
        g.add(e13);
        g.add(e13);
        
        assertEquals("1/2, 1/3, 1/3", ConcreteGraphEdge.asSortedStr(g.getEdges()));
    }
    
    @Test
    public void testremoveNode1() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphEdge e12 = createEdge(n1, n2);
        ConcreteGraphEdge e13 = createEdge(n1, n3);
        
        Graph<GraphNode, GraphEdge> g = createGraph();
        g.add(n1);
        g.add(n2);
        g.add(n3);
        g.add(e12);
        g.add(e13);
        g.remove(n1);
        
        assertEquals("2, 3", ConcreteGraphNode.asSortedStr(g.getNodes()));
        assertEquals("", ConcreteGraphEdge.asSortedStr(g.getEdges()));
    }
    
    @Test
    public void testremoveNode2() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphEdge e12 = createEdge(n1, n2);
        ConcreteGraphEdge e13 = createEdge(n1, n3);
        
        Graph<GraphNode, GraphEdge> g = createGraph();
        g.add(n1);
        g.add(n2);
        g.add(n3);
        g.add(e12);
        g.add(e13);
        g.remove(n2);
        
        assertEquals("1, 3", ConcreteGraphNode.asSortedStr(g.getNodes()));
        assertEquals("1/3", ConcreteGraphEdge.asSortedStr(g.getEdges()));
    }
    
    @Test
    public void testremoveNode3() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphNode n4 = createNode(4);
        ConcreteGraphEdge e12 = createEdge(n1, n2);
        ConcreteGraphEdge e13 = createEdge(n1, n3);
        
        Graph<GraphNode, GraphEdge> g = createGraph();
        g.add(n1);
        g.add(n2);
        g.add(n3);
        g.add(e12);
        g.add(e13);
        g.remove(n4);
        
        assertEquals("1, 2, 3", ConcreteGraphNode.asSortedStr(g.getNodes()));
        assertEquals("1/2, 1/3", ConcreteGraphEdge.asSortedStr(g.getEdges()));
    }
    
    @Test
    public void testremoveEdge1() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphEdge e12 = createEdge(n1, n2);
        ConcreteGraphEdge e13 = createEdge(n1, n3);
        
        Graph<GraphNode, GraphEdge> g = createGraph();
        g.add(n1);
        g.add(n2);
        g.add(n3);
        g.add(e12);
        g.add(e13);
        g.remove(e13);
        
        assertEquals("1, 2, 3", ConcreteGraphNode.asSortedStr(g.getNodes()));
        assertEquals("1/2", ConcreteGraphEdge.asSortedStr(g.getEdges()));
    }
    
    @Test
    public void testremoveEdge2() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphEdge e12 = createEdge(n1, n2);
        ConcreteGraphEdge e13 = createEdge(n1, n3);
        ConcreteGraphEdge e32 = createEdge(n3, n2);
        
        Graph<GraphNode, GraphEdge> g = createGraph();
        g.add(n1);
        g.add(n2);
        g.add(n3);
        g.add(e12);
        g.add(e13);
        g.add(e32);
        g.remove(e13);
        
        assertEquals("1, 2, 3", ConcreteGraphNode.asSortedStr(g.getNodes()));
        assertEquals("1/2, 3/2", ConcreteGraphEdge.asSortedStr(g.getEdges()));
    }
    
    @Test
    public void testremoveEdge3() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphEdge e12 = createEdge(n1, n2);
        ConcreteGraphEdge e13 = createEdge(n1, n3);
        ConcreteGraphEdge e32 = createEdge(n3, n2);
        
        Graph<GraphNode, GraphEdge> g = createGraph();
        g.add(n1);
        g.add(n2);
        g.add(n3);
        g.add(e12);
        g.add(e13);
        g.remove(e32);
        
        assertEquals("1, 2, 3", ConcreteGraphNode.asSortedStr(g.getNodes()));
        assertEquals("1/2, 1/3", ConcreteGraphEdge.asSortedStr(g.getEdges()));
    }
    
    @Test
    public void testContainsNode1() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphEdge e12 = createEdge(n1, n2);
        ConcreteGraphEdge e13 = createEdge(n1, n3);
        
        Graph<GraphNode, GraphEdge> g = createGraph();
        g.add(n1);
        g.add(n2);
        g.add(n3);
        g.add(e12);
        g.add(e13);
        boolean result = g.contains(n1);
        
        assertTrue(result);
    }
    
    @Test
    public void testContainsNode2() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphNode n4 = createNode(4);
        ConcreteGraphEdge e12 = createEdge(n1, n2);
        ConcreteGraphEdge e13 = createEdge(n1, n3);
        
        Graph<GraphNode, GraphEdge> g = createGraph();
        g.add(n1);
        g.add(n2);
        g.add(n3);
        g.add(e12);
        g.add(e13);
        boolean result = g.contains(n4);
        
        assertFalse(result);
    }
    
    @Test
    public void testContainsEdge1() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphEdge e12 = createEdge(n1, n2);
        ConcreteGraphEdge e13 = createEdge(n1, n3);
        
        Graph<GraphNode, GraphEdge> g = createGraph();
        g.add(n1);
        g.add(n2);
        g.add(n3);
        g.add(e12);
        g.add(e13);
        boolean result = g.contains(e12);
        
        assertTrue(result);
    }
    @Test
    public void testContainsEdge2() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphEdge e12 = createEdge(n1, n2);
        ConcreteGraphEdge e13 = createEdge(n1, n3);
        ConcreteGraphEdge e32 = createEdge(n3, n2);
        
        Graph<GraphNode, GraphEdge> g = createGraph();
        g.add(n1);
        g.add(n2);
        g.add(n3);
        g.add(e12);
        g.add(e13);
        boolean result = g.contains(e32);
        
        assertFalse(result);
    }
}
