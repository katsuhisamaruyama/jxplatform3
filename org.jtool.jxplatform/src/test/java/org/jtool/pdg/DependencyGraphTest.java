/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.srcmodel.JavaProject;
import java.util.List;
import java.util.Set;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DependencyGraphTest {
    
    private static JavaProject SliceProject;
    
    @BeforeClass
    public static void setUp() {
        BuilderTestUtil.clearProject();
        
        SliceProject = BuilderTestUtil.getProject("Slice");
    }
    
    @AfterClass
    public static void tearDown() {
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testGetNodes_Test103() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test103");
        Set<PDGNode> result = graph.getNodes();
        
        assertEquals(55, result.size());
    }
    
    @Test
    public void testGetNodes_Test122() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test122");
        Set<PDGNode> result = graph.getNodes();
        
        assertEquals(30, result.size());
    }
    
    @Test
    public void testGetCDEdges_Test103() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test103");
        List<DependencyGraphEdge> result = graph.getCDEdges();
        
        assertEquals(61, result.size());
    }
    
    @Test
    public void testGetCDEdges_Test122() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test122");
        List<DependencyGraphEdge> result = graph.getCDEdges();
        
        assertEquals(40, result.size());
    }
    
    @Test
    public void testGetDDEdges_Test103() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test103");
        List<DependencyGraphEdge> result = graph.getDDEdges();
        
        assertEquals(58, result.size());
    }
    
    @Test
    public void testGetDDEdges_Test122() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test122");
        List<DependencyGraphEdge> result = graph.getDDEdges();
        
        assertEquals(17, result.size());
    }
    
    @Test
    public void testGetInterPDGEdges_Test103() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test103");
        List<DependencyGraphEdge> result = graph.getInterPDGEdges();
        
        assertEquals(41, result.size());
    }
    
    @Test
    public void testGetInterPDGEdges_Test122() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test122");
        List<DependencyGraphEdge> result = graph.getInterPDGEdges();
        
        assertEquals(8, result.size());
    }
    
    @Test
    public void testGetIncomingEdges_Test103() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test103");
        ClDG cldg = graph.getClDG("Test103");
        PDG pdg1 = graph.getPDG("Test103#m( )");
        PDG pdg2 = graph.getPDG("Test103#setA( int )");
        List<DependencyGraphEdge> result = DependencyGraphEdge.sortEdges(graph.getIncomingEdges(pdg2.getNode(0)));
        
        assertEquals(3, result.size());
        assertEquals("0", PDGTestUtil.getIdStr(cldg, result.get(0).getSrcNode()));
        assertEquals("6", PDGTestUtil.getIdStr(pdg1, result.get(1).getSrcNode()));
        assertEquals("11", PDGTestUtil.getIdStr(pdg1, result.get(2).getSrcNode()));
    }
    
    @Test
    public void testGetIncomingEdges_Test122() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test122");
        PDG pdg1 = graph.getPDG("Test122#m( )");
        PDG pdg2 = graph.getPDG("Test122#n( int )");
        List<DependencyGraphEdge> result = DependencyGraphEdge.sortEdges(graph.getIncomingEdges(pdg1.getNode(12)));
        
        assertEquals(3, result.size());
        assertEquals("3", PDGTestUtil.getIdStr(pdg1, result.get(0).getSrcNode()));
        assertEquals("7", PDGTestUtil.getIdStr(pdg1, result.get(1).getSrcNode()));
        assertEquals("1", PDGTestUtil.getIdStr(pdg2, result.get(2).getSrcNode()));
    }
    
    @Test
    public void testGetOutgoingEdges_Test103() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test103");
        PDG pdg1 = graph.getPDG("Test103#m( )");
        PDG pdg2 = graph.getPDG("Test103#setA( int )");
        List<DependencyGraphEdge> result = DependencyGraphEdge.sortEdges(graph.getOutgoingEdges(pdg1.getNode(11)));
        
        assertEquals(1, result.size());
        assertEquals("0", PDGTestUtil.getIdStr(pdg2, result.get(0).getDstNode()));
    }
    
    @Test
    public void testGetOutgoingEdges_Test122() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test122");
        PDG pdg1 = graph.getPDG("Test122#m( )");
        PDG pdg2 = graph.getPDG("Test122#n( int )");
        List<DependencyGraphEdge> result = DependencyGraphEdge.sortEdges(graph.getOutgoingEdges(pdg2.getNode(1)));
        
        assertEquals(1, result.size());
        assertEquals("12", PDGTestUtil.getIdStr(pdg1, result.get(0).getDstNode()));
    }
}
