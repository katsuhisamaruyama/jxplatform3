/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.srcmodel.JavaProject;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class InterPDGDDTest {
    
    private static JavaProject SliceProject;
    
    @BeforeClass
    public static void setUp() {
        SliceProject = BuilderTestUtil.getProject("Slice");
    }
    
    @Test
    public void testIsParameterIn1() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test103");
        PDG pdg1 = graph.getPDG("Test103#m( )");
        PDG pdg2 = graph.getPDG("Test103#setA( int )");
        PDGNode src = pdg1.getNode(7);
        PDGNode dst = pdg2.getNode(1);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isParameterIn());
    }
    
    @Test
    public void testIsParameterIn2() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test118");
        PDG pdg1 = graph.getPDG("Test118#m( )");
        PDG pdg2 = graph.getPDG("Test118#m1( int int int )");
        PDGNode src = pdg1.getNode(11);
        PDGNode dst = pdg2.getNode(1);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isParameterIn());
    }
    
    @Test
    public void testIsParameterIn3() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test118");
        PDG pdg1 = graph.getPDG("Test118#m( )");
        PDG pdg2 = graph.getPDG("Test118#m1( int int int )");
        PDGNode src = pdg1.getNode(12);
        PDGNode dst = pdg2.getNode(2);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isParameterIn());
    }
    
    @Test
    public void testIsParameterIn4() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test118");
        PDG pdg1 = graph.getPDG("Test118#m( )");
        PDG pdg2 = graph.getPDG("Test118#m1( int int int )");
        PDGNode src = pdg1.getNode(13);
        PDGNode dst = pdg2.getNode(3);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isParameterIn());
    }
    
    @Test
    public void testIsParameterIn5() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test127");
        PDG pdg1 = graph.getPDG("Test127#m( )");
        PDG pdg2 = graph.getPDG("A127#setY( int )");
        PDGNode src = pdg1.getNode(8);
        PDGNode dst = pdg2.getNode(1);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isParameterIn());
    }
    
    @Test
    public void testIsParameterOut1() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test103");
        PDG pdg1 = graph.getPDG("Test103#m( )");
        PDG pdg2 = graph.getPDG("Test103#setA( int )");
        PDGNode src = pdg2.getNode(4);
        PDGNode dst = pdg1.getNode(8);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isParameterOut());
    }
    
    @Test
    public void testIsParameterOut2() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test118");
        PDG pdg1 = graph.getPDG("Test118#m( )");
        PDG pdg2 = graph.getPDG("Test118#m1( int int int )");
        PDGNode src = pdg2.getNode(6);
        PDGNode dst = pdg1.getNode(14);
        
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isParameterOut());
    }
    
    @Test
    public void testIsParameterOut3() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test127");
        PDG pdg1 = graph.getPDG("Test127#m( )");
        PDG pdg2 = graph.getPDG("A127#A127( )");
        PDGNode src = pdg2.getNode(2);
        PDGNode dst = pdg1.getNode(4);
        
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isParameterOut());
    }
    
    @Test
    public void testIsParameterOut4() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test127");
        PDG pdg1 = graph.getPDG("Test127#m( )");
        PDG pdg2 = graph.getPDG("A127#setY( int )");
        PDGNode src = pdg2.getNode(4);
        PDGNode dst = pdg1.getNode(9);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isParameterOut());
    }
    
    @Test
    public void testIsFieldAccess1() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test103");
        PDG pdg1 = graph.getPDG("Test103#m( )");
        PDG pdg2 = graph.getPDG("Test103#a");
        PDGNode src = pdg2.getNode(1);
        PDGNode dst = pdg1.getNode(14);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess2() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test103");
        PDG pdg1 = graph.getPDG("Test103#m( )");
        PDG pdg2 = graph.getPDG("Test103#a");
        PDGNode src = pdg2.getNode(1);
        PDGNode dst = pdg1.getNode(3);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess3() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test124");
        PDG pdg1 = graph.getPDG("A124#inc( int )");
        PDG pdg2 = graph.getPDG("A124#x");
        PDGNode src = pdg2.getNode(1);
        PDGNode dst = pdg1.getNode(2);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess4() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test126");
        PDG pdg1 = graph.getPDG("Test126#m( )");
        PDG pdg2 = graph.getPDG("A126#y");
        PDGNode src = pdg2.getNode(1);
        PDGNode dst = pdg1.getNode(43);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess5() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test126");
        PDG pdg1 = graph.getPDG("A126#getY( )");
        PDG pdg2 = graph.getPDG("A126#y");
        PDGNode src = pdg2.getNode(1);
        PDGNode dst = pdg1.getNode(1);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess6() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test127");
        PDG pdg1 = graph.getPDG("Test127#m( )");
        PDG pdg2 = graph.getPDG("A127#z");
        PDGNode src = pdg2.getNode(1);
        PDGNode dst = pdg1.getNode(15);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess7() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test127");
        PDG pdg1 = graph.getPDG("Test127#m( )");
        PDG pdg2 = graph.getPDG("A127#z");
        PDGNode src = pdg2.getNode(1);
        PDGNode dst = pdg1.getNode(15);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess8() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test132");
        PDG pdg1 = graph.getPDG("Test132#m( )");
        PDG pdg2 = graph.getPDG("P132#x");
        PDGNode src = pdg2.getNode(1);
        PDGNode dst = pdg1.getNode(1);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess9() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test139");
        PDG pdg1 = graph.getPDG("Test139#m( )");
        PDG pdg2 = graph.getPDG("PriceCode#CHILDRENS");
        PDGNode src = pdg2.getNode(1);
        PDGNode dst = pdg1.getNode(1);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsUncoveredFieldAccess1() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test126");
        PDG pdg1 = graph.getPDG("A126#add( int )");
        PDG pdg2 = graph.getPDG("A126#setY( int )");
        PDGNode src = pdg2.getNode(2);
        PDGNode dst = pdg1.getNode(14);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isUncoveredFieldAccess());
    }
    
    @Test
    public void testIsUncoveredFieldAccess2() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test126");
        PDG pdg1 = graph.getPDG("Test126#m( )");
        PDG pdg2 = graph.getPDG("A126#setY( int )");
        PDGNode src = pdg2.getNode(2);
        PDGNode dst = pdg1.getNode(46);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isUncoveredFieldAccess());
    }
    
    @Test
    public void testIsUncoveredFieldAccess3() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test126");
        PDG pdg1 = graph.getPDG("Test126#m( )");
        PDG pdg2 = graph.getPDG("A126#setY( int )");
        PDGNode src = pdg2.getNode(2);
        PDGNode dst = pdg1.getNode(47);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isUncoveredFieldAccess());
    }
    
    @Test
    public void testIsUncoveredFieldAccess4() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test126");
        PDG pdg1 = graph.getPDG("Test126#m( )");
        PDG pdg2 = graph.getPDG("A126#setY( int )");
        PDGNode src = pdg2.getNode(2);
        PDGNode dst = pdg1.getNode(48);
        List<DependencyGraphEdge> result = PDGTestUtil.getDependence(graph, src, dst);
        
        assertTrue(result.get(0).isUncoveredFieldAccess());
    }
}
