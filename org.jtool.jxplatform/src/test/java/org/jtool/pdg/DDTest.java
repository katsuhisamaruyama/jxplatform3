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

public class DDTest {
    
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
    public void testIsLIDD1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test103", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 2, 3);
        
        assertTrue(result.get(0).isLIDD());
    }
    
    @Test
    public void testIsLIDD2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test103", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 13, 14);
        
        assertTrue(result.get(0).isLIDD());
    }
    
    @Test
    public void testIsLIDD3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test103", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 18, 15);
        
        assertTrue(result.get(0).isLIDD());
    }
    
    @Test
    public void testIssLIDD4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 1, 4);
        
        assertTrue(result.get(0).isLIDD());
    }
    
    @Test
    public void testIsLIDD5() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 2, 5);
        
        assertTrue(result.get(0).isLIDD());
    }
    
    @Test
    public void testIsLIDD6() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 2, 8);
        
        assertTrue(result.get(0).isLIDD());
    }
    
    @Test
    public void testIsLIDD7() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 5, 8);
        
        assertTrue(result.get(0).isLIDD());
    }
    
    @Test
    public void testIsLCDD1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test110", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 4, 3);
        
        assertTrue(result.get(0).isLCDD());
    }
    
    @Test
    public void testIsLCDD2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test115", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 8, 5);
        
        assertTrue(result.get(0).isLCDD());
    }
    
    @Test
    public void testIsDefOrder1() {
        PDG pdg = PDGTestUtil.createPDG(SimpleProject, "Test02", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 7, 7);
        
        assertTrue(result.get(0).isDefOrder());
    }
    
    @Test
    public void testIsOutput1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 3, 6);
        
        assertTrue(result.get(0).isOutput());
    }
    
    @Test
    public void testIsParameterIn1() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test103");
        PDG pdg1 = sdg.findPDG("Test103#m( )");
        PDG pdg2 = sdg.findPDG("Test103#setA( int )");
        PDGNode src = PDGTestUtil.getNode(pdg1, 7);
        PDGNode dst = PDGTestUtil.getNode(pdg2, 1);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isParameterIn());
    }
    
    @Test
    public void testIsParameterIn2() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test118");
        PDG pdg1 = sdg.findPDG("Test118#m( )");
        PDG pdg2 = sdg.findPDG("Test118#m1( int int int )");
        PDGNode src = PDGTestUtil.getNode(pdg1, 11);
        PDGNode dst = PDGTestUtil.getNode(pdg2, 1);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isParameterIn());
    }
    
    @Test
    public void testIsParameterIn3() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test118");
        PDG pdg1 = sdg.findPDG("Test118#m( )");
        PDG pdg2 = sdg.findPDG("Test118#m1( int int int )");
        PDGNode src = PDGTestUtil.getNode(pdg1, 12);
        PDGNode dst = PDGTestUtil.getNode(pdg2, 2);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isParameterIn());
    }
    
    @Test
    public void testIsParameterIn4() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test118");
        PDG pdg1 = sdg.findPDG("Test118#m( )");
        PDG pdg2 = sdg.findPDG("Test118#m1( int int int )");
        PDGNode src = PDGTestUtil.getNode(pdg1, 13);
        PDGNode dst = PDGTestUtil.getNode(pdg2, 3);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isParameterIn());
    }
    
    @Test
    public void testIsParameterIn5() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test127");
        PDG pdg1 = sdg.findPDG("Test127#m( )");
        PDG pdg2 = sdg.findPDG("A127#setY( int )");
        PDGNode src = PDGTestUtil.getNode(pdg1, 8);
        PDGNode dst = PDGTestUtil.getNode(pdg2, 1);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isParameterIn());
    }
    
    @Test
    public void testIsParameterOut1() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test103");
        PDG pdg1 = sdg.findPDG("Test103#m( )");
        PDG pdg2 = sdg.findPDG("Test103#setA( int )");
        PDGNode src = PDGTestUtil.getNode(pdg2, 4);
        PDGNode dst = PDGTestUtil.getNode(pdg1, 8);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isParameterOut());
    }
    
    @Test
    public void testIsParameterOut2() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test118");
        PDG pdg1 = sdg.findPDG("Test118#m( )");
        PDG pdg2 = sdg.findPDG("Test118#m1( int int int )");
        PDGNode src = PDGTestUtil.getNode(pdg2, 6);
        PDGNode dst = PDGTestUtil.getNode(pdg1, 14);
        
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isParameterOut());
    }
    
    @Test
    public void testIsParameterOut3() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test127");
        PDG pdg1 = sdg.findPDG("Test127#m( )");
        PDG pdg2 = sdg.findPDG("A127#A127( )");
        PDGNode src = PDGTestUtil.getNode(pdg2, 2);
        PDGNode dst = PDGTestUtil.getNode(pdg1, 4);
        
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isParameterOut());
    }
    
    @Test
    public void testIsParameterOut4() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test127");
        PDG pdg1 = sdg.findPDG("Test127#m( )");
        PDG pdg2 = sdg.findPDG("A127#setY( int )");
        PDGNode src = PDGTestUtil.getNode(pdg2, 4);
        PDGNode dst = PDGTestUtil.getNode(pdg1, 9);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isParameterOut());
    }
    
    @Test
    public void testIsFieldAccess1() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test103");
        PDG pdg1 = sdg.findPDG("Test103#m( )");
        PDG pdg2 = sdg.findPDG("Test103#a");
        PDGNode src = PDGTestUtil.getNode(pdg1, 1);
        PDGNode dst = PDGTestUtil.getNode(pdg2, 1);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess2() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test103");
        PDG pdg1 = sdg.findPDG("Test103#m( )");
        PDG pdg2 = sdg.findPDG("Test103#a");
        PDGNode src = PDGTestUtil.getNode(pdg2, 1);
        PDGNode dst = PDGTestUtil.getNode(pdg1, 3);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess3() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test124");
        PDG pdg1 = sdg.findPDG("A124#A124( int )");
        PDG pdg2 = sdg.findPDG("A124#x");
        PDGNode src = PDGTestUtil.getNode(pdg2, 1);
        PDGNode dst = PDGTestUtil.getNode(pdg1, 4);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess4() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test126");
        PDG pdg1 = sdg.findPDG("Test126#m( )");
        PDG pdg2 = sdg.findPDG("A126#y");
        PDGNode src = PDGTestUtil.getNode(pdg1, 6);
        PDGNode dst = PDGTestUtil.getNode(pdg2, 1);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess5() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test126");
        PDG pdg1 = sdg.findPDG("A126#setY( int )");
        PDG pdg2 = sdg.findPDG("A126#y");
        PDGNode src = PDGTestUtil.getNode(pdg1, 2);
        PDGNode dst = PDGTestUtil.getNode(pdg2, 1);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess6() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test127");
        PDG pdg1 = sdg.findPDG("Test127#m( )");
        PDG pdg2 = sdg.findPDG("A127#z");
        PDGNode src = PDGTestUtil.getNode(pdg1, 10);
        PDGNode dst = PDGTestUtil.getNode(pdg2, 1);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess7() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test127");
        PDG pdg1 = sdg.findPDG("Test127#m( )");
        PDG pdg2 = sdg.findPDG("A127#z");
        PDGNode src = PDGTestUtil.getNode(pdg2, 1);
        PDGNode dst = PDGTestUtil.getNode(pdg1, 15);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess8() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test139");
        PDG pdg1 = sdg.findPDG("Test139#m( )");
        PDG pdg2 = sdg.findPDG("PriceCode#CHILDRENS");
        PDGNode src = PDGTestUtil.getNode(pdg2, 1);
        PDGNode dst = PDGTestUtil.getNode(pdg1, 1);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsSummary1() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test105");
        PDG pdg = sdg.findPDG("Test105#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 4);
        PDGNode dst = PDGTestUtil.getNode(pdg, 5);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isSummary());
    }
    
    @Test
    public void testIsSummary2() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test118");
        PDG pdg = sdg.findPDG("Test118#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 11);
        PDGNode dst = PDGTestUtil.getNode(pdg, 14);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isSummary());
    }
    
    @Test
    public void testIsSummary3() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test118");
        PDG pdg = sdg.findPDG("Test118#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 19);
        PDGNode dst = PDGTestUtil.getNode(pdg, 21);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isSummary());
    }
    
    @Test
    public void testIsSummary4() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test118");
        PDG pdg = sdg.findPDG("Test118#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 27);
        PDGNode dst = PDGTestUtil.getNode(pdg, 28);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isSummary());
    }
    
    @Test
    public void testIsSummary5() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test118");
        PDG pdg = sdg.findPDG("Test118#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 32);
        PDGNode dst = PDGTestUtil.getNode(pdg, 35);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isSummary());
    }
    
    @Test
    public void testIsSummary6() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test118");
        PDG pdg = sdg.findPDG("Test118#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 33);
        PDGNode dst = PDGTestUtil.getNode(pdg, 35);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isSummary());
    }
    
    @Test
    public void testIsSummary7() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test118");
        PDG pdg = sdg.findPDG("Test118#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 40);
        PDGNode dst = PDGTestUtil.getNode(pdg, 42);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isSummary());
    }
    
    @Test
    public void testIsSummary8() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test118");
        PDG pdg = sdg.findPDG("Test118#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 41);
        PDGNode dst = PDGTestUtil.getNode(pdg, 42);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isSummary());
    }
    
    @Test
    public void testIsSummary9() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test118");
        PDG pdg = sdg.findPDG("Test118#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 46);
        PDGNode dst = PDGTestUtil.getNode(pdg, 49);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isSummary());
    }
    
    @Test
    public void testIsSummary10() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test118");
        PDG pdg = sdg.findPDG("Test118#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 48);
        PDGNode dst = PDGTestUtil.getNode(pdg, 49);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isSummary());
    }
    
    @Test
    public void testIsSummary11() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test118");
        PDG pdg = sdg.findPDG("Test118#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 53);
        PDGNode dst = PDGTestUtil.getNode(pdg, 56);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isSummary());
    }
    
    @Test
    public void testIsSummary12() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test118");
        PDG pdg = sdg.findPDG("Test118#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 54);
        PDGNode dst = PDGTestUtil.getNode(pdg, 56);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isSummary());
    }
    
    @Test
    public void testIsSummary13() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test118");
        PDG pdg = sdg.findPDG("Test118#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 55);
        PDGNode dst = PDGTestUtil.getNode(pdg, 56);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isSummary());
    }
    
    @Test
    public void testIsSummary14() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test121");
        PDG pdg = sdg.findPDG("Test121#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 7);
        PDGNode dst = PDGTestUtil.getNode(pdg, 8);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isSummary());
    }
    
    @Test
    public void testIsSummary15() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test124");
        PDG pdg = sdg.findPDG("Test124#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 6);
        PDGNode dst = PDGTestUtil.getNode(pdg, 7);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isSummary());
    }
    
    @Test
    public void testIsSummary16() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test124");
        PDG pdg = sdg.findPDG("Test124#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 15);
        PDGNode dst = PDGTestUtil.getNode(pdg, 16);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isSummary());
    }
    
    @Test
    public void testIsSummary17() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test126");
        PDG pdg = sdg.findPDG("Test126#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 10);
        PDGNode dst = PDGTestUtil.getNode(pdg, 11);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isSummary());
    }
    
    @Test
    public void testIsSummary18() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test126");
        PDG pdg = sdg.findPDG("Test126#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 21);
        PDGNode dst = PDGTestUtil.getNode(pdg, 22);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isSummary());
    }
    
    @Test
    public void testIsSummary19() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test126");
        PDG pdg = sdg.findPDG("Test126#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 32);
        PDGNode dst = PDGTestUtil.getNode(pdg, 33);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isSummary());
    }
    
    @Test
    public void testIsSummary20() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject, "Test126");
        PDG pdg = sdg.findPDG("Test126#m( )");
        PDGNode src = PDGTestUtil.getNode(pdg, 41);
        PDGNode dst = PDGTestUtil.getNode(pdg, 42);
        List<Dependence> result = PDGTestUtil.getDependence(sdg, src, dst);
        
        assertTrue(result.get(0).isSummary());
    }
}
