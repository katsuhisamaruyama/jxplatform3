/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.srcmodel.JavaProject;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class DependenceTest {
    
    private static JavaProject SimpleProject;
    private static JavaProject SliceProject;
    
    @BeforeClass
    public static void setUp() {
        SimpleProject = BuilderTestUtil.createProject("Simple", "", "");
        SliceProject = BuilderTestUtil.createProject("Slice", "", "");
    }
    
    @AfterClass
    public static void tearDown() {
        SimpleProject.getModelBuilder().unbuild();
        SliceProject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testIsCD_Test103_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test103", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 0, 1);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCD_Test103_3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test103", "setA( int )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 0, 1);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCD_Test103_4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test103", "setA( int )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 0, 4);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCD_Test108_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 4, 5);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCD_Test108_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 4, 6);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCD_Test108_3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 1, 4);
        
        assertTrue(result.get(0).isCD());
        assertTrue(result.get(1).isDD());
    }
    
    @Test
    public void testIsCD_Test108_4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 2, 5);
        
        assertTrue(result.get(0).isCD());
        assertTrue(result.get(1).isDD());
    }
    
    @Test
    public void testIsCD_Test109_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test109", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 4, 6);
        
        assertTrue(result.get(0).isFalse());
    }
    
    @Test
    public void testIsCD_Test110_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test110", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 3, 4);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCD_Test115_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test115", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 5, 7);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCD_Test115_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test115", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 5, 8);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCD_Test115_3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test115", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 5, 6);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCD_Test115_4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test115", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 5, 7);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCD_Test115_5() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test115", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 5, 8);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCD_Test122_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test122", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 3, 12);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCD_Test122_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test122", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 3, 14);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCD_Test122_3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test122", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 7, 5);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCD_Test122_4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test122", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 7, 12);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCD_Test35_1() {
        PDG pdg = PDGTestUtil.createPDG(SimpleProject, "Test35", "m1( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 4, 2);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCD_Test35_2() {
        PDG pdg = PDGTestUtil.createPDG(SimpleProject, "Test35", "m1( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 4, 6);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCD_Test35_3() {
        PDG pdg = PDGTestUtil.createPDG(SimpleProject, "Test35", "m2( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 5, 7);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsDD_Test103_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test103", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 2, 3);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD_Test103_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test103", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 13, 9);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD_Test103_3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test103", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 18, 15);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD_Test108_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 1, 4);
        
        assertTrue(result.get(0).isCD());
        assertTrue(result.get(1).isDD());
    }
    
    @Test
    public void testIsDD_Test108_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 2, 5);
        
        assertTrue(result.get(0).isCD());
        assertTrue(result.get(1).isDD());
    }
    
    @Test
    public void testIsDD_Test108_3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 3, 6);
        
        assertTrue(result.get(0).isCD());
        assertTrue(result.get(1).isDD());
    }
    
    @Test
    public void testIsDD_Test108_4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 2, 8);
        
        assertTrue(result.get(0).isCD());
        assertTrue(result.get(1).isDD());
    }
    
    @Test
    public void testIsDD_Test108_5() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 5, 8);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD_Test110_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test110", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 4, 3);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD_Test115_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test115", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 8, 5);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD_Customer_1() {
        PDG pdg = PDGTestUtil.createPDG(SimpleProject, "Test02", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 7, 7);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD_Test105_1() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test105");
        PDG pdg = graph.getPDG("Test105#m( )");
        PDGNode src = pdg.getNode(1);
        PDGNode dst = pdg.getNode(6);
        List<Dependence> result = PDGTestUtil.getDependence(pdg, src, dst);
        
        assertTrue(result.get(0).isCD());
        assertTrue(result.get(1).isDD());
    }
    
    @Test
    public void testIsDD_Test105_2() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test105");
        PDG pdg = graph.getPDG("Test105#m( )");
        PDGNode src = pdg.getNode(5);
        PDGNode dst = pdg.getNode(1);
        List<Dependence> result = PDGTestUtil.getDependence(pdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD_Test105_3() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test105");
        PDG pdg = graph.getPDG("Test105#m( )");
        PDGNode src = pdg.getNode(16);
        PDGNode dst = pdg.getNode(7);
        List<Dependence> result = PDGTestUtil.getDependence(pdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD_Test118_1() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test118");
        PDG pdg = graph.getPDG("Test118#m( )");
        PDGNode src = pdg.getNode(7);
        PDGNode dst = pdg.getNode(1);
        List<Dependence> result = PDGTestUtil.getDependence(pdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD_Test118_2() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test118");
        PDG pdg = graph.getPDG("Test118#m( )");
        PDGNode src = pdg.getNode(14);
        PDGNode dst = pdg.getNode(8);
        List<Dependence> result = PDGTestUtil.getDependence(pdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD_Test118_3() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test118");
        PDG pdg = graph.getPDG("Test118#m( )");
        PDGNode src = pdg.getNode(21);
        PDGNode dst = pdg.getNode(15);
        List<Dependence> result = PDGTestUtil.getDependence(pdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD_Test118_4() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test118");
        PDG pdg = graph.getPDG("Test118#m( )");
        PDGNode src = pdg.getNode(28);
        PDGNode dst = pdg.getNode(22);
        List<Dependence> result = PDGTestUtil.getDependence(pdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD_Test118_5() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test118");
        PDG pdg = graph.getPDG("Test118#m( )");
        PDGNode src = pdg.getNode(35);
        PDGNode dst = pdg.getNode(29);
        List<Dependence> result = PDGTestUtil.getDependence(pdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD_Test118_6() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test118");
        PDG pdg = graph.getPDG("Test118#m( )");
        PDGNode src = pdg.getNode(42);
        PDGNode dst = pdg.getNode(36);
        List<Dependence> result = PDGTestUtil.getDependence(pdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD_Test118_7() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test118");
        PDG pdg = graph.getPDG("Test118#m( )");
        PDGNode src = pdg.getNode(49);
        PDGNode dst = pdg.getNode(43);
        List<Dependence> result = PDGTestUtil.getDependence(pdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD_Test118_8() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test118");
        PDG pdg = graph.getPDG("Test118#m( )");
        PDGNode src = pdg.getNode(56);
        PDGNode dst = pdg.getNode(50);
        List<Dependence> result = PDGTestUtil.getDependence(pdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD_Test118_9() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test118");
        PDG pdg = graph.getPDG("Test118#m( )");
        PDGNode src = pdg.getNode(63);
        PDGNode dst = pdg.getNode(57);
        List<Dependence> result = PDGTestUtil.getDependence(pdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD_Test121_1() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test121");
        PDG pdg = graph.getPDG("Test121#m( )");
        PDGNode src = pdg.getNode(1);
        PDGNode dst = pdg.getNode(6);
        List<Dependence> result = PDGTestUtil.getDependence(pdg, src, dst);
        
        assertTrue(result.get(0).isCD());
        assertTrue(result.get(1).isDD());
    }
    
    @Test
    public void testIsDD_Test121_2() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test121");
        PDG pdg = graph.getPDG("Test121#m( )");
        PDGNode src = pdg.getNode(2);
        PDGNode dst = pdg.getNode(7);
        List<Dependence> result = PDGTestUtil.getDependence(pdg, src, dst);
        
        assertTrue(result.get(0).isCD());
        assertTrue(result.get(1).isDD());
    }
    
    @Test
    public void testIsDD_Test121_3() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test121");
        PDG pdg = graph.getPDG("Test121#m( )");
        PDGNode src = pdg.getNode(8);
        PDGNode dst = pdg.getNode(3);
        List<Dependence> result = PDGTestUtil.getDependence(pdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD_Test124_1() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test124");
        PDG pdg = graph.getPDG("Test124#m( )");
        PDGNode src = pdg.getNode(1);
        PDGNode dst = pdg.getNode(6);
        List<Dependence> result = PDGTestUtil.getDependence(pdg, src, dst);
        
        assertTrue(result.get(0).isCD());
        assertTrue(result.get(1).isDD());
    }
    
    @Test
    public void testIsDD_Test124_2() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test124");
        PDG pdg = graph.getPDG("Test124#m( )");
        PDGNode src = pdg.getNode(2);
        PDGNode dst = pdg.getNode(15);
        List<Dependence> result = PDGTestUtil.getDependence(pdg, src, dst);
        
        assertTrue(result.get(0).isCD());
        assertTrue(result.get(1).isDD());
    }
    
    @Test
    public void testIsDD_Test124_3() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test124");
        PDG pdg = graph.getPDG("Test124#m( )");
        PDGNode src = pdg.getNode(3);
        PDGNode dst = pdg.getNode(9);
        List<Dependence> result = PDGTestUtil.getDependence(pdg, src, dst);
        
        assertTrue(result.get(0).isCD());
        assertTrue(result.get(1).isDD());
    }
    
    @Test
    public void testIsDD_Test124_4() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test124");
        PDG pdg = graph.getPDG("Test124#m( )");
        PDGNode src = pdg.getNode(3);
        PDGNode dst = pdg.getNode(13);
        List<Dependence> result = PDGTestUtil.getDependence(pdg, src, dst);
        
        assertTrue(result.get(0).isCD());
        assertTrue(result.get(1).isDD());
    }
    
    @Test
    public void testIsDD_Test124_5() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test124");
        PDG pdg = graph.getPDG("Test124#m( )");
        PDGNode src = pdg.getNode(7);
        PDGNode dst = pdg.getNode(3);
        List<Dependence> result = PDGTestUtil.getDependence(pdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD_Test124_6() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test124");
        PDG pdg = graph.getPDG("Test124#m( )");
        PDGNode src = pdg.getNode(11);
        PDGNode dst = pdg.getNode(8);
        List<Dependence> result = PDGTestUtil.getDependence(pdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD_Test124_7() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test124");
        PDG pdg = graph.getPDG("Test124#m( )");
        PDGNode src = pdg.getNode(16);
        PDGNode dst = pdg.getNode(12);
        List<Dependence> result = PDGTestUtil.getDependence(pdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD_Test124_8() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test124");
        PDG pdg = graph.getPDG("Test124#m( )");
        PDGNode src = pdg.getNode(19);
        PDGNode dst = pdg.getNode(10);
        List<Dependence> result = PDGTestUtil.getDependence(pdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD_Test124_9() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test124");
        PDG pdg = graph.getPDG("Test124#m( )");
        PDGNode src = pdg.getNode(19);
        PDGNode dst = pdg.getNode(11);
        List<Dependence> result = PDGTestUtil.getDependence(pdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD_Test124_10() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test124");
        PDG pdg = graph.getPDG("Test124#m( )");
        PDGNode src = pdg.getNode(19);
        PDGNode dst = pdg.getNode(14);
        List<Dependence> result = PDGTestUtil.getDependence(pdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD_Test124_11() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test124");
        PDG pdg = graph.getPDG("Test124#m( )");
        PDGNode src = pdg.getNode(19);
        PDGNode dst = pdg.getNode(16);
        List<Dependence> result = PDGTestUtil.getDependence(pdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDD_Test126_1() {
        DependencyGraph graph = PDGTestUtil.createDependencyGraph(SliceProject, "Test126");
        PDG pdg = graph.getPDG("Test126#m( )");
        PDGNode src = pdg.getNode(46);
        PDGNode dst = pdg.getNode(47);
        List<Dependence> result = PDGTestUtil.getDependence(pdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
}
