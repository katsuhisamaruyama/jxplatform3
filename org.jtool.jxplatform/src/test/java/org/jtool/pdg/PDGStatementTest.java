/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.srcmodel.JavaProject;
import org.jtool.cfg.JVariableReference;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import org.jtool.jxplatform.util.PDGTestUtil;
import java.util.List;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;

public class PDGStatementTest {
    
    private static JavaProject SliceProject;
    
    @BeforeClass
    public static void setUp() {
        SliceProject = BuilderTestUtil.createProject("Slice", "", "");
    }
    
    @AfterClass
    public static void tearDown() {
        SliceProject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testGetDefVariables_Test102_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test102", "m( )");
        PDGStatement node = (PDGStatement)pdg.getNode(1);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("x$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test102_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test102", "m( )");
        PDGStatement node = (PDGStatement)pdg.getNode(2);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test102_3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test102", "m( )");
        PDGStatement node = (PDGStatement)pdg.getNode(3);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test102_4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test102", "m( )");
        PDGStatement node = (PDGStatement)pdg.getNode(4);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test102_5() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test102", "m( )");
        PDGStatement node = (PDGStatement)pdg.getNode(5);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!inc( int )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test102_6() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test102", "m( )");
        PDGStatement node = (PDGStatement)pdg.getNode(6);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("y$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test108_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGStatement node = (PDGStatement)pdg.getNode(1);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("x$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test108_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGStatement node = (PDGStatement)pdg.getNode(2);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("y$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test108_3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGStatement node = (PDGStatement)pdg.getNode(3);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("z$2", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test108_4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGStatement node = (PDGStatement)pdg.getNode(4);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test108_5() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGStatement node = (PDGStatement)pdg.getNode(5);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("y$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test108_6() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGStatement node = (PDGStatement)pdg.getNode(11);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test102_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test102", "m( )");
        PDGStatement node = (PDGStatement)pdg.getNode(1);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!inc( int )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test102_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test102", "m( )");
        PDGStatement node = (PDGStatement)pdg.getNode(2);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test102_3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test102", "m( )");
        PDGStatement node = (PDGStatement)pdg.getNode(3);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test102_4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test102", "m( )");
        PDGStatement node = (PDGStatement)pdg.getNode(4);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test102_5() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test102", "m( )");
        PDGStatement node = (PDGStatement)pdg.getNode(5);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test102_6() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test102", "m( )");
        PDGStatement node = (PDGStatement)pdg.getNode(6);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test108_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGStatement node = (PDGStatement)pdg.getNode(1);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test108_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGStatement node = (PDGStatement)pdg.getNode(2);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test108_3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGStatement node = (PDGStatement)pdg.getNode(3);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test108_4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGStatement node = (PDGStatement)pdg.getNode(4);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("x$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test108_5() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGStatement node = (PDGStatement)pdg.getNode(5);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("y$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test108_6() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGStatement node = (PDGStatement)pdg.getNode(11);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("$_", TestUtil.asStrOfReference(result));
    }
}
