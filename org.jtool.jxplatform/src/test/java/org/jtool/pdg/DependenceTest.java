/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.PDGTestUtil;
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
    public void testIsCDTest103_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test103", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 0, 1);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCDTest103_3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test103", "setA( int )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 0, 1);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCDTest103_4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test103", "setA( int )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 0, 4);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCDTest108_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 4, 5);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCDTest108_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 4, 6);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCDTest108_3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 1, 4);
        
        assertTrue(result.get(0).isCD());
        assertTrue(result.get(1).isDD());
    }
    
    @Test
    public void testIsCDTest108_4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 2, 5);
        
        assertTrue(result.get(0).isCD());
        assertTrue(result.get(1).isDD());
    }
    
    @Test
    public void testIsCDTest109_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test109", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 4, 6);
        
        assertTrue(result.get(0).isFalse());
    }
    
    @Test
    public void testIsCDTest110_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test110", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 3, 4);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCDTest115_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test115", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 5, 7);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCDTest115_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test115", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 5, 8);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCDTest115_3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test115", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 5, 6);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCDTest115_4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test115", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 5, 7);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCDTest115_5() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test115", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 5, 8);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCDTest122_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test122", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 3, 12);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCDTest122_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test122", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 3, 14);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCDTest122_3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test122", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 7, 5);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCDTest122_4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test122", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 7, 12);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCDTest35_1() {
        PDG pdg = PDGTestUtil.createPDG(SimpleProject, "Test35", "m1( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 4, 2);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCDTest35_2() {
        PDG pdg = PDGTestUtil.createPDG(SimpleProject, "Test35", "m1( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 4, 6);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsCDTest35_3() {
        PDG pdg = PDGTestUtil.createPDG(SimpleProject, "Test35", "m2( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 5, 7);
        
        assertTrue(result.get(0).isCD());
    }
    
    @Test
    public void testIsDDTest103_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test103", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 2, 3);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest103_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test103", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 13, 9);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest103_3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test103", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 18, 15);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest108_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 1, 4);
        
        assertTrue(result.get(0).isCD());
        assertTrue(result.get(1).isDD());
    }
    
    @Test
    public void testIsDDTest108_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 2, 5);
        
        assertTrue(result.get(0).isCD());
        assertTrue(result.get(1).isDD());
    }
    
    @Test
    public void testIsDDTest108_3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 3, 6);
        
        assertTrue(result.get(0).isCD());
        assertTrue(result.get(1).isDD());
    }
    
    @Test
    public void testIsDDTest108_4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 2, 8);
        
        assertTrue(result.get(0).isCD());
        assertTrue(result.get(1).isDD());
    }
    
    @Test
    public void testIsDDTest108_5() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 5, 8);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest110_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test110", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 4, 3);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest115_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test115", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 8, 5);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDCustomer_1() {
        PDG pdg = PDGTestUtil.createPDG(SimpleProject, "Test02", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 7, 7);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest105_1() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test105");
        PDG pdg = sdg.findPDG("Test105#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 1);
        PDGNode dst = PDGTestUtil.getNode(pdg, 6);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isCD());
        assertTrue(result.get(1).isDD());
    }
    
    @Test
    public void testIsDDTest105_2() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test105");
        PDG pdg = sdg.findPDG("Test105#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 5);
        PDGNode dst = PDGTestUtil.getNode(pdg, 1);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest105_3() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test105");
        PDG pdg = sdg.findPDG("Test105#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 16);
        PDGNode dst = PDGTestUtil.getNode(pdg, 7);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest118_1() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test118");
        PDG pdg = sdg.findPDG("Test118#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 7);
        PDGNode dst = PDGTestUtil.getNode(pdg, 1);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest118_2() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test118");
        PDG pdg = sdg.findPDG("Test118#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 14);
        PDGNode dst = PDGTestUtil.getNode(pdg, 8);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest118_3() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test118");
        PDG pdg = sdg.findPDG("Test118#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 21);
        PDGNode dst = PDGTestUtil.getNode(pdg, 15);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest118_4() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test118");
        PDG pdg = sdg.findPDG("Test118#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 28);
        PDGNode dst = PDGTestUtil.getNode(pdg, 22);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest118_5() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test118");
        PDG pdg = sdg.findPDG("Test118#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 35);
        PDGNode dst = PDGTestUtil.getNode(pdg, 29);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest118_6() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test118");
        PDG pdg = sdg.findPDG("Test118#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 42);
        PDGNode dst = PDGTestUtil.getNode(pdg, 36);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest118_7() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test118");
        PDG pdg = sdg.findPDG("Test118#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 49);
        PDGNode dst = PDGTestUtil.getNode(pdg, 43);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest118_8() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test118");
        PDG pdg = sdg.findPDG("Test118#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 56);
        PDGNode dst = PDGTestUtil.getNode(pdg, 50);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest118_9() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test118");
        PDG pdg = sdg.findPDG("Test118#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 63);
        PDGNode dst = PDGTestUtil.getNode(pdg, 57);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest121_1() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test121");
        PDG pdg = sdg.findPDG("Test121#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 1);
        PDGNode dst = PDGTestUtil.getNode(pdg, 6);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isCD());
        assertTrue(result.get(1).isDD());
    }
    
    @Test
    public void testIsDDTest121_2() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test121");
        PDG pdg = sdg.findPDG("Test121#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 2);
        PDGNode dst = PDGTestUtil.getNode(pdg, 7);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isCD());
        assertTrue(result.get(1).isDD());
    }
    
    @Test
    public void testIsDDTest121_3() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test121");
        PDG pdg = sdg.findPDG("Test121#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 8);
        PDGNode dst = PDGTestUtil.getNode(pdg, 3);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest124_1() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test124");
        PDG pdg = sdg.findPDG("Test124#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 1);
        PDGNode dst = PDGTestUtil.getNode(pdg, 6);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isCD());
        assertTrue(result.get(1).isDD());
    }
    
    @Test
    public void testIsDDTest124_2() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test124");
        PDG pdg = sdg.findPDG("Test124#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 2);
        PDGNode dst = PDGTestUtil.getNode(pdg, 15);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isCD());
        assertTrue(result.get(1).isDD());
    }
    
    @Test
    public void testIsDDTest124_3() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test124");
        PDG pdg = sdg.findPDG("Test124#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 3);
        PDGNode dst = PDGTestUtil.getNode(pdg, 9);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isCD());
        assertTrue(result.get(1).isDD());
    }
    
    @Test
    public void testIsDDTest124_4() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test124");
        PDG pdg = sdg.findPDG("Test124#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 3);
        PDGNode dst = PDGTestUtil.getNode(pdg, 13);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isCD());
        assertTrue(result.get(1).isDD());
    }
    
    @Test
    public void testIsDDTest124_5() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test124");
        PDG pdg = sdg.findPDG("Test124#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 7);
        PDGNode dst = PDGTestUtil.getNode(pdg, 3);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest124_6() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test124");
        PDG pdg = sdg.findPDG("Test124#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 11);
        PDGNode dst = PDGTestUtil.getNode(pdg, 8);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest124_7() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test124");
        PDG pdg = sdg.findPDG("Test124#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 16);
        PDGNode dst = PDGTestUtil.getNode(pdg, 12);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest124_8() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test124");
        PDG pdg = sdg.findPDG("Test124#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 19);
        PDGNode dst = PDGTestUtil.getNode(pdg, 10);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest124_9() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test124");
        PDG pdg = sdg.findPDG("Test124#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 19);
        PDGNode dst = PDGTestUtil.getNode(pdg, 11);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest124_10() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test124");
        PDG pdg = sdg.findPDG("Test124#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 19);
        PDGNode dst = PDGTestUtil.getNode(pdg, 14);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest124_11() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test124");
        PDG pdg = sdg.findPDG("Test124#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 19);
        PDGNode dst = PDGTestUtil.getNode(pdg, 16);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest126_1() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test126");
        PDG pdg = sdg.findPDG("Test126#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 46);
        PDGNode dst = PDGTestUtil.getNode(pdg, 47);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest126_2() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test126");
        PDG pdg1 = sdg.findPDG("A126#add( int )");
        PDG pdg2 = sdg.findPDG("A126#setY( int )");
        PDGNode src = PDGTestUtil.getNode(pdg2, 2);
        PDGNode dst = PDGTestUtil.getNode(pdg1, 14);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest126_3() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test126");
        PDG pdg1 = sdg.findPDG("Test126#m( )");
        PDG pdg2 = sdg.findPDG("A126#setY( int )");
        PDGNode src = PDGTestUtil.getNode(pdg2, 2);
        PDGNode dst = PDGTestUtil.getNode(pdg1, 46);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest126_4() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test126");
        PDG pdg1 = sdg.findPDG("Test126#m( )");
        PDG pdg2 = sdg.findPDG("A126#setY( int )");
        PDGNode src = PDGTestUtil.getNode(pdg2, 2);
        PDGNode dst = PDGTestUtil.getNode(pdg1, 47);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsDDTest126_5() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test126");
        PDG pdg1 = sdg.findPDG("Test126#m( )");
        PDG pdg2 = sdg.findPDG("A126#setY( int )");
        PDGNode src = PDGTestUtil.getNode(pdg2, 2);
        PDGNode dst = PDGTestUtil.getNode(pdg1, 48);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isDD());
    }
    
    @Test
    public void testIsClassMemberTest103_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDGNode entry = cldg.getEntryNode();
        PDGNode member = cldg.findPDG("Test103#m( )").getEntryNode();
        List<Dependence> result = PDGTestUtil.getDependence(cldg, entry, member);
        
        assertTrue(result.get(0).isClassMember());
    }
    
    @Test
    public void testIsClassMemberTest103_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDGNode entry = cldg.getEntryNode();
        PDGNode member = cldg.findPDG("Test103#setA( int )").getEntryNode();
        List<Dependence> result = PDGTestUtil.getDependence(cldg, entry, member);
        
        assertTrue(result.get(0).isClassMember());
    }
    
    @Test
    public void testIsClassMemberTest103_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDGNode entry = cldg.getEntryNode();
        PDGNode member = cldg.findPDG("Test103#a").getEntryNode();
        List<Dependence> result = PDGTestUtil.getDependence(cldg, entry, member);
        
        assertTrue(result.get(0).isClassMember());
    }
    
    @Test
    public void testIsClassMemberPriceCode_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDGNode entry = cldg.getEntryNode();
        PDGNode member = cldg.findPDG("PriceCode#CHILDRENS").getEntryNode();
        List<Dependence> result = PDGTestUtil.getDependence(cldg, entry, member);
        
        assertTrue(result.get(0).isClassMember());
    }
    
    @Test
    public void testIsClassMemberPriceCode_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDGNode entry = cldg.getEntryNode();
        PDGNode member = cldg.findPDG("PriceCode#getPriceCode( )").getEntryNode();
        List<Dependence> result = PDGTestUtil.getDependence(cldg, entry, member);
        
        assertTrue(result.get(0).isClassMember());
    }
    
    @Test
    public void testIsClassMemberPriceCode_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDGNode entry = cldg.getEntryNode();
        PDGNode member = cldg.findPDG("PriceCode#priceCode").getEntryNode();
        List<Dependence> result = PDGTestUtil.getDependence(cldg, entry, member);
        
        assertTrue(result.get(0).isClassMember());
    }
    
    @Test
    public void testIsCallTest103_1() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test103");
        PDG pdg1 = sdg.findPDG("Test103#m( )");
        PDG pdg2 = sdg.findPDG("Test103#setA( int )");
        PDGNode caller = PDGTestUtil.getNode(pdg1, 11);
        PDGNode callee = PDGTestUtil.getNode(pdg2, 0);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, caller, callee);
        
        assertTrue(result.get(0).isCall());
    }
    
    @Test
    public void testIsCallTest132_1() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test132");
        PDG pdg1 = sdg.findPDG("Test132#m( )");
        PDG pdg2 = sdg.findPDG("Test132#n( )");
        PDGNode caller = PDGTestUtil.getNode(pdg1, 3);
        PDGNode callee = PDGTestUtil.getNode(pdg2, 0);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, caller , callee);
        
        assertTrue(result.get(0).isCall());
    }
    
    @Test
    public void testIsCallTest132_2() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test132");
        PDG pdg1 = sdg.findPDG("Test132#n( )");
        PDG pdg2 = sdg.findPDG("P132#P132( )");
        PDGNode caller = PDGTestUtil.getNode(pdg1, 3);
        PDGNode callee = PDGTestUtil.getNode(pdg2, 0);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, caller, callee);
        
        assertTrue(result.get(0).isCall());
    }
    
    @Test
    public void testIsCallTest134_1() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test134");
        PDG pdg1 = sdg.findPDG("Test134#m3( )");
        PDG pdg2 = sdg.findPDG("R134#f( int )");
        PDGNode caller = PDGTestUtil.getNode(pdg1, 8);
        PDGNode callee = PDGTestUtil.getNode(pdg2, 0);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, caller, callee);
        
        assertTrue(result.get(0).isCall());
    }
    
    @Test
    public void testIsCallTest139_1() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test139");
        PDG pdg1 = sdg.findPDG("Test139#m( )");
        PDG pdg2 = sdg.findPDG("PriceCode#getPriceCode( )");
        PDGNode caller = PDGTestUtil.getNode(pdg1, 5);
        PDGNode callee = PDGTestUtil.getNode(pdg2, 0);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, caller, callee);
        
        assertTrue(result.get(0).isCall());
    }
}
