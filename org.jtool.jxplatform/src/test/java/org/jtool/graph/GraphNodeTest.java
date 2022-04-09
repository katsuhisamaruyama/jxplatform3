/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.graph;

import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class GraphNodeTest {
    
    private static ConcreteGraphNode createNode(long id) {
        return new ConcreteGraphNode(id);
    }
    
    private static ConcreteGraphEdge createEdge(GraphNode src, GraphNode dst) {
        return new ConcreteGraphEdge(src, dst);
    }
    
    @Test
    public void testGetIncomingEdges1() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        createEdge(n1, n2);
        Set<GraphEdge> result = n1.getIncomingEdges();
        
        assertEquals("", ConcreteGraphEdge.asSortedStr(result));
    }
    
    @Test
    public void testGetIncomingEdges2() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        
        createEdge(n1, n2);
        Set<GraphEdge> result = n2.getIncomingEdges();
        
        assertEquals("1/2", ConcreteGraphEdge.asSortedStr(result));
    }
    
    @Test
    public void testGetIncomingEdges3() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphNode n4 = createNode(4);
        ConcreteGraphNode n5 = createNode(5);
        ConcreteGraphNode n6 = createNode(6);
        
        createEdge(n1, n2);
        createEdge(n1, n3);
        createEdge(n2, n4);
        createEdge(n2, n5);
        createEdge(n3, n6);
        createEdge(n6, n2);
        Set<GraphEdge> result = n2.getIncomingEdges();
        
        assertEquals("1/2, 6/2", ConcreteGraphEdge.asSortedStr(result));
    }
    
    @Test
    public void testGetIncomingEdges4() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphNode n4 = createNode(4);
        ConcreteGraphNode n5 = createNode(5);
        ConcreteGraphNode n6 = createNode(6);
        
        createEdge(n1, n2);
        createEdge(n1, n3);
        createEdge(n2, n4);
        createEdge(n2, n5);
        createEdge(n3, n6);
        createEdge(n6, n2);
        Set<GraphEdge> result = n3.getIncomingEdges();
        
        assertEquals("1/3", ConcreteGraphEdge.asSortedStr(result));
    }
    
    @Test
    public void testGetIncomingEdges5() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphNode n4 = createNode(4);
        ConcreteGraphNode n5 = createNode(5);
        ConcreteGraphNode n6 = createNode(6);
        
        createEdge(n1, n2);
        createEdge(n1, n3);
        createEdge(n2, n4);
        createEdge(n2, n5);
        createEdge(n3, n6);
        createEdge(n6, n2);
        Set<GraphEdge> result = n6.getIncomingEdges();
        
        assertEquals("3/6", ConcreteGraphEdge.asSortedStr(result));
    }
    
    @Test
    public void testGetIncomingEdges6() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphNode n4 = createNode(4);
        ConcreteGraphNode n5 = createNode(5);
        ConcreteGraphNode n6 = createNode(6);
        
        createEdge(n1, n2);
        createEdge(n1, n3);
        createEdge(n2, n4);
        createEdge(n2, n5);
        createEdge(n3, n6);
        ConcreteGraphEdge e62 = createEdge(n6, n2);
        
        n2.removeIncomingEdge(e62);
        
        Set<GraphEdge> result = n2.getIncomingEdges();
        
        assertEquals("1/2", ConcreteGraphEdge.asSortedStr(result));
    }
    
    @Test
    public void testGetIncomingEdges7() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphNode n4 = createNode(4);
        ConcreteGraphNode n5 = createNode(5);
        ConcreteGraphNode n6 = createNode(6);
        
        createEdge(n1, n2);
        createEdge(n1, n3);
        createEdge(n2, n4);
        createEdge(n2, n5);
        createEdge(n3, n6);
        ConcreteGraphEdge e62 = createEdge(n6, n2);
        
        n6.removeOutgoingEdge(e62);
        
        Set<GraphEdge> result = n2.getIncomingEdges();
        
        assertEquals("1/2", ConcreteGraphEdge.asSortedStr(result));
    }
    
    @Test
    public void testGetOutgoingEdges1() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        createEdge(n1, n2);
        Set<GraphEdge> result = n1.getOutgoingEdges();
        
        assertEquals("1/2", ConcreteGraphEdge.asSortedStr(result));
    }
    
    @Test
    public void testGetOutgoingEdges2() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        createEdge(n1, n2);
        Set<GraphEdge> result = n2.getOutgoingEdges();
        
        assertEquals("", ConcreteGraphEdge.asSortedStr(result));
    }
    
    @Test
    public void testGetOutgoingEdges3() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphNode n4 = createNode(4);
        ConcreteGraphNode n5 = createNode(5);
        ConcreteGraphNode n6 = createNode(6);
        
        createEdge(n1, n2);
        createEdge(n1, n3);
        createEdge(n2, n4);
        createEdge(n2, n5);
        createEdge(n3, n6);
        createEdge(n6, n2);
        Set<GraphEdge> result = n2.getOutgoingEdges();
        
        assertEquals("2/4, 2/5", ConcreteGraphEdge.asSortedStr(result));
    }
    
    @Test
    public void testGetOutgoingEdges4() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphNode n4 = createNode(4);
        ConcreteGraphNode n5 = createNode(5);
        ConcreteGraphNode n6 = createNode(6);
        createEdge(n1, n2);
        createEdge(n1, n3);
        createEdge(n2, n4);
        createEdge(n2, n5);
        createEdge(n3, n6);
        createEdge(n6, n2);
        Set<GraphEdge> result = n3.getOutgoingEdges();
        
        assertEquals("3/6", ConcreteGraphEdge.asSortedStr(result));
    }
    
    @Test
    public void testGetOutgoingEdges5() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphNode n4 = createNode(4);
        ConcreteGraphNode n5 = createNode(5);
        ConcreteGraphNode n6 = createNode(6);
        createEdge(n1, n2);
        createEdge(n1, n3);
        createEdge(n2, n4);
        createEdge(n2, n5);
        createEdge(n3, n6);
        createEdge(n6, n2);
        Set<GraphEdge> result = n6.getOutgoingEdges();
        
        assertEquals("6/2", ConcreteGraphEdge.asSortedStr(result));
    }
    
    @Test
    public void testGetOutgoingEdges6() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphNode n4 = createNode(4);
        ConcreteGraphNode n5 = createNode(5);
        ConcreteGraphNode n6 = createNode(6);
        
        createEdge(n1, n2);
        createEdge(n1, n3);
        ConcreteGraphEdge e24 = createEdge(n2, n4);
        createEdge(n2, n5);
        createEdge(n3, n6);
        createEdge(n6, n2);
        
        n2.removeOutgoingEdge(e24);
        
        Set<GraphEdge> result = n2.getOutgoingEdges();
        
        assertEquals("2/5", ConcreteGraphEdge.asSortedStr(result));
    }
    
    @Test
    public void testGetOutgoingEdges7() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphNode n4 = createNode(4);
        ConcreteGraphNode n5 = createNode(5);
        ConcreteGraphNode n6 = createNode(6);
        
        createEdge(n1, n2);
        createEdge(n1, n3);
        ConcreteGraphEdge e24 = createEdge(n2, n4);
        createEdge(n2, n5);
        createEdge(n3, n6);
        createEdge(n6, n2);
        
        n4.removeIncomingEdge(e24);
        
        Set<GraphEdge> result = n2.getOutgoingEdges();
        
        assertEquals("2/5", ConcreteGraphEdge.asSortedStr(result));
    }
    
    @Test
    public void testGetSrcNodes1() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        
        createEdge(n1, n2);
        Set<GraphNode> result = n1.getSrcNodes();
        
        assertEquals("", ConcreteGraphNode.asSortedStr(result));
    }
    
    @Test
    public void testGetSrcNodes2() {
        
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        
        createEdge(n1, n2);
        Set<GraphNode> result = n2.getSrcNodes();
        
        assertEquals("1", ConcreteGraphNode.asSortedStr(result));
    }
    
    @Test
    public void testGetSrcNodes3() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphNode n4 = createNode(4);
        ConcreteGraphNode n5 = createNode(5);
        ConcreteGraphNode n6 = createNode(6);
        
        createEdge(n1, n2);
        createEdge(n1, n3);
        createEdge(n2, n4);
        createEdge(n2, n5);
        createEdge(n3, n6);
        createEdge(n6, n2);
        Set<GraphNode> result = n2.getSrcNodes();
        
        assertEquals("1, 6", ConcreteGraphNode.asSortedStr(result));
    }
    
    @Test
    public void testGetSrcNodes4() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphNode n4 = createNode(4);
        ConcreteGraphNode n5 = createNode(5);
        ConcreteGraphNode n6 = createNode(6);
        
        createEdge(n1, n2);
        createEdge(n1, n3);
        createEdge(n2, n4);
        createEdge(n2, n5);
        createEdge(n3, n6);
        createEdge(n6, n2);
        Set<GraphNode> result = n3.getSrcNodes();
        
        assertEquals("1", ConcreteGraphNode.asSortedStr(result));
    }
    
    @Test
    public void testGetSrcNodes5() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphNode n4 = createNode(4);
        ConcreteGraphNode n5 = createNode(5);
        ConcreteGraphNode n6 = createNode(6);
        
        createEdge(n1, n2);
        createEdge(n1, n3);
        createEdge(n2, n4);
        createEdge(n2, n5);
        createEdge(n3, n6);
        createEdge(n6, n2);
        Set<GraphNode> result = n6.getSrcNodes();
        
        assertEquals("3", ConcreteGraphNode.asSortedStr(result));
    }
    
    @Test
    public void testGetDstNodes1() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        
        createEdge(n1, n2);
        Set<GraphNode> result = n1.getDstNodes();
        
        assertEquals("2", ConcreteGraphNode.asSortedStr(result));
    }
    
    @Test
    public void testGetDstNodes2() {
        
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        
        createEdge(n1, n2);
        Set<GraphNode> result = n2.getDstNodes();
        
        assertEquals("", ConcreteGraphNode.asSortedStr(result));
    }
    
    @Test
    public void testGetDstNodes3() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphNode n4 = createNode(4);
        ConcreteGraphNode n5 = createNode(5);
        ConcreteGraphNode n6 = createNode(6);
        
        createEdge(n1, n2);
        createEdge(n1, n3);
        createEdge(n2, n4);
        createEdge(n2, n5);
        createEdge(n3, n6);
        createEdge(n6, n2);
        Set<GraphNode> result = n2.getDstNodes();
        
        assertEquals("4, 5", ConcreteGraphNode.asSortedStr(result));
    }
    
    @Test
    public void testGetDstNodes4() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphNode n4 = createNode(4);
        ConcreteGraphNode n5 = createNode(5);
        ConcreteGraphNode n6 = createNode(6);
        
        createEdge(n1, n2);
        createEdge(n1, n3);
        createEdge(n2, n4);
        createEdge(n2, n5);
        createEdge(n3, n6);
        createEdge(n6, n2);
        Set<GraphNode> result = n3.getDstNodes();
        
        assertEquals("6", ConcreteGraphNode.asSortedStr(result));
    }
    
    @Test
    public void testGetDstNodes5() {
        ConcreteGraphNode n1 = createNode(1);
        ConcreteGraphNode n2 = createNode(2);
        ConcreteGraphNode n3 = createNode(3);
        ConcreteGraphNode n4 = createNode(4);
        ConcreteGraphNode n5 = createNode(5);
        ConcreteGraphNode n6 = createNode(6);
        
        createEdge(n1, n2);
        createEdge(n1, n3);
        createEdge(n2, n4);
        createEdge(n2, n5);
        createEdge(n3, n6);
        createEdge(n6, n2);
        Set<GraphNode> result = n6.getDstNodes();
        
        assertEquals("2", ConcreteGraphNode.asSortedStr(result));
    }
}

class ConcreteGraphNode extends GraphNode {
    
    ConcreteGraphNode(long id) {
        super(id);
    }
    
    @Override
    public String toString() {
        return String.valueOf(id);
    }
    
    public static String asSortedStr(Set<GraphNode> set) {
        return set.stream().map(GraphNode::toString).sorted().collect(Collectors.joining(", "));
    }
}
