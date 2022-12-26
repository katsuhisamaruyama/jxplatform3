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

public class CDTest {
    
    private static JavaProject SimpleProject;
    private static JavaProject SliceProject;
    
    @BeforeClass
    public static void setUp() {
        SimpleProject = BuilderTestUtil.getProject("Simple");
        SliceProject = BuilderTestUtil.getProject("Slice");
    }
    
    @Test
    public void testIsTrue1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test103", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 0, 1);
        
        assertTrue(result.get(0).isTrue());
    }
    
    @Test
    public void testIsTrue3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test103", "setA( int )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 0, 1);
        
        assertTrue(result.get(0).isTrue());
    }
    
    @Test
    public void testIsTrue4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test103", "setA( int )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 0, 4);
        
        assertTrue(result.get(0).isTrue());
    }
    
    @Test
    public void testIsTrue5() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 4, 5);
        
        assertTrue(result.get(0).isTrue());
    }
    
    @Test
    public void testIsTrue6() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test110", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 3, 4);
        
        assertTrue(result.get(0).isTrue());
    }
    
    @Test
    public void testIsTrue7() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test115", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 5, 6);
        
        assertTrue(result.get(0).isTrue());
    }
    
    @Test
    public void testIsTrue8() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test115", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 5, 7);
        
        assertTrue(result.get(0).isTrue());
    }
    
    @Test
    public void testIsTrue9() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test115", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 5, 8);
        
        assertTrue(result.get(0).isTrue());
    }
    
    @Test
    public void testIsTrue10() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test122", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 3, 12);
        
        assertTrue(result.get(0).isTrue());
    }
    
    @Test
    public void testIsTrue11() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test122", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 3, 14);
        
        assertTrue(result.get(0).isTrue());
    }
    
    @Test
    public void testIsTrue12() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test122", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 7, 5);
        
        assertTrue(result.get(0).isTrue());
    }
    
    @Test
    public void testIsTrue13() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test122", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 7, 9);
        
        assertTrue(result.get(0).isTrue());
    }
    
    @Test
    public void testIsTrue14() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test122", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 7, 10);
        
        assertTrue(result.get(0).isTrue());
    }
    
    @Test
    public void testIsTrue15() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test122", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 12, 13);
        
        assertTrue(result.get(1).isTrue());
    }
    
    public void testIsTrue16() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test122", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 14, 15);
        
        assertTrue(result.get(0).isTrue());
    }
    
    @Test
    public void testIsFalse1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 4, 6);
        
        assertTrue(result.get(0).isFalse());
    }
    
    @Test
    public void testIsFalse2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test109", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 4, 6);
        
        assertTrue(result.get(0).isFalse());
    }
    
    @Test
    public void testIsFallThrough1() {
        PDG pdg = PDGTestUtil.createPDG(SimpleProject, "Test35", "m1( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 4, 2);
        
        assertTrue(result.get(0).isFallThrough());
    }
    
    @Test
    public void testIsFallThrough2() {
        PDG pdg = PDGTestUtil.createPDG(SimpleProject, "Test35", "m1( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 4, 6);
        
        assertTrue(result.get(0).isFallThrough());
    }
    
    @Test
    public void testIsFallThrough3() {
        PDG pdg = PDGTestUtil.createPDG(SimpleProject, "Test35", "m2( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 5, 7);
        
        assertTrue(result.get(0).isFallThrough());
    }
    
    @Test
    public void testIsDeclaration1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 1, 4);
        
        assertTrue(result.get(0).isDeclaration());
    }
    
    @Test
    public void testIsDeclaration2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 3, 6);
        
        assertTrue(result.get(0).isDeclaration());
    }
    
    @Test
    public void testIsExceptionCatch1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test122", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 7, 12);
        
        assertTrue(result.get(0).isExceptionCatch());
    }
}
