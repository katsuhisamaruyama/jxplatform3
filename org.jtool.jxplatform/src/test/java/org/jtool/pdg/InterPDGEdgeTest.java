/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import static org.jtool.pdg.AllPDGTests.SliceProject;
import org.jtool.pdg.internal.PDGTestUtil;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class InterPDGEdgeTest {
    
    @BeforeClass
    public static void setUp() {
        SliceProject = BuilderTestUtil.getProject("Slice");
    }
    
    @Test
    public void testIsDD1() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test126");
        PDG pdg1 = graph.getPDG("A126#add( int )");
        PDG pdg2 = graph.getPDG("A126#setY( int )");
        PDGNode src = pdg2.getNode(2);
        PDGNode dst = pdg1.getNode(14);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD2() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test126");
        PDG pdg1 = graph.getPDG("Test126#m( )");
        PDG pdg2 = graph.getPDG("A126#setY( int )");
        PDGNode src = pdg2.getNode(2);
        PDGNode dst = pdg1.getNode(46);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD3() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test126");
        PDG pdg1 = graph.getPDG("Test126#m( )");
        PDG pdg2 = graph.getPDG("A126#setY( int )");
        PDGNode src = pdg2.getNode(2);
        PDGNode dst = pdg1.getNode(47);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD4() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test126");
        PDG pdg1 = graph.getPDG("Test126#m( )");
        PDG pdg2 = graph.getPDG("A126#setY( int )");
        PDGNode src = pdg2.getNode(2);
        PDGNode dst = pdg1.getNode(48);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsCall1() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test103");
        PDG pdg1 = graph.getPDG("Test103#m( )");
        PDG pdg2 = graph.getPDG("Test103#setA( int )");
        PDGNode src = pdg1.getNode(6);
        PDGNode dst = pdg2.getNode(0);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCall2() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test103");
        PDG pdg1 = graph.getPDG("Test103#m( )");
        PDG pdg2 = graph.getPDG("Test103#getA( )");
        PDGNode src = pdg1.getNode(21);
        PDGNode dst = pdg2.getNode(0);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCall3() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test129");
        PDG pdg1 = graph.getPDG("Test129#m( )");
        PDG pdg2 = graph.getPDG("S129#getP( )");
        PDGNode src = pdg1.getNode(5);
        PDGNode dst = pdg2.getNode(0);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCall4() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test129");
        PDG pdg1 = graph.getPDG("Test129#s2");
        PDG pdg2 = graph.getPDG("S129#S129( )");
        PDGNode src = pdg1.getNode(3);
        PDGNode dst = pdg2.getNode(0);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsClassMember1() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test103");
        ClDG cldg = graph.getClDG("Test103");
        PDG pdg = graph.getPDG("Test103#m( )");
        PDGNode src = cldg.getEntryNode();
        PDGNode dst = pdg.getEntryNode();
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsClassMember2() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test103");
        ClDG cldg = graph.getClDG("Test103");
        PDG pdg = graph.getPDG("Test103#setA( int )");
        PDGNode src = cldg.getEntryNode();
        PDGNode dst = pdg.getEntryNode();
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsClassMember3() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test103");
        ClDG cldg = graph.getClDG("Test103");
        PDG pdg = graph.getPDG("Test103#a");
        PDGNode src = cldg.getEntryNode();
        PDGNode dst = pdg.getEntryNode();
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsClassMember4() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "PriceCode");
        ClDG cldg = graph.getClDG("PriceCode");
        PDG pdg = graph.getPDG("PriceCode#CHILDRENS");
        PDGNode src = cldg.getEntryNode();
        PDGNode dst = pdg.getEntryNode();
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsClassMember5() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "PriceCode");
        ClDG cldg = graph.getClDG("PriceCode");
        PDG pdg = graph.getPDG("PriceCode#getPriceCode( )");
        PDGNode src = cldg.getEntryNode();
        PDGNode dst = pdg.getEntryNode();
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsClassMember6() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "PriceCode");
        ClDG cldg = graph.getClDG("PriceCode");
        PDG pdg = graph.getPDG("PriceCode#priceCode");
        PDGNode src = cldg.getEntryNode();
        PDGNode dst = pdg.getEntryNode();
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isCD());
    }
}
