/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import static org.jtool.pdg.AllPDGTests.SimpleProject;
import static org.jtool.pdg.AllPDGTests.SliceProject;
import org.jtool.pdg.internal.PDGTestUtil;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class DDTest {
    
    @BeforeClass
    public static void setUp() {
        SimpleProject = BuilderTestUtil.getProject("Simple");
        SliceProject = BuilderTestUtil.getProject("Slice");
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
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 13, 9);
        
        assertTrue(result.get(0).isLIDD());
    }
    
    @Test
    public void testIsLIDD3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test103", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 18, 15);
        
        assertTrue(result.get(0).isLIDD());
    }
    
    @Test
    public void testIsLIDD4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 1, 4);
        
        assertTrue(result.get(1).isLIDD());
    }
    
    @Test
    public void testIsLIDD5() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 2, 5);
        
        assertTrue(result.get(1).isLIDD());
    }
    
    @Test
    public void testIsLIDD6() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 2, 8);
        
        assertTrue(result.get(1).isLIDD());
    }
    
    @Test
    public void testIsLIDD7() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 5, 8);
        
        assertTrue(result.get(0).isLIDD());
    }
    
    @Test
    public void testIsLIDD8() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "S140", "S140( int )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 6, 2);
        
        assertTrue(result.get(0).isLIDD());
    }
    
    @Test
    public void testIsLIDD9() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "S140", "S140( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 21, 6);
        
        assertTrue(result.get(0).isLIDD());
    }
    
    @Test
    public void testIsLIDD10() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "S140", "S140( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 22, 5);
        
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
    public void testIsLCDD3() {
        PDG pdg = PDGTestUtil.createPDG(SimpleProject, "Test02", "m( )");
        List<Dependence> result = PDGTestUtil.getDependence(pdg, 8, 8);
        
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
        
        assertTrue(result.get(1).isOutput());
    }
}
