/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.srcmodel.JavaProject;
import org.jtool.cfg.JVariableReference;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.PDGTestUtil;
import org.jtool.jxplatform.util.TestUtil;
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
    public void testGetgetUseVariables102_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test102", "m( )");
        PDGStatement node = (PDGStatement)PDGTestUtil.getNode(pdg, 1);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("x$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest102_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test102", "m( )");
        PDGStatement node = (PDGStatement)PDGTestUtil.getNode(pdg, 2);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest102_3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test102", "m( )");
        PDGStatement node = (PDGStatement)PDGTestUtil.getNode(pdg, 3);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest102_4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test102", "m( )");
        PDGStatement node = (PDGStatement)PDGTestUtil.getNode(pdg, 4);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest102_5() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test102", "m( )");
        PDGStatement node = (PDGStatement)PDGTestUtil.getNode(pdg, 5);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!inc( int )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest102_6() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test102", "m( )");
        PDGStatement node = (PDGStatement)PDGTestUtil.getNode(pdg, 6);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("y$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest108_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGStatement node = (PDGStatement)PDGTestUtil.getNode(pdg, 1);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("x$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest108_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGStatement node = (PDGStatement)PDGTestUtil.getNode(pdg, 2);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("y$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest108_3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGStatement node = (PDGStatement)PDGTestUtil.getNode(pdg, 3);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("z$2", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest108_4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGStatement node = (PDGStatement)PDGTestUtil.getNode(pdg, 4);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest108_5() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGStatement node = (PDGStatement)PDGTestUtil.getNode(pdg, 5);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("y$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest108_6() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGStatement node = (PDGStatement)PDGTestUtil.getNode(pdg, 11);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest102_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test102", "m( )");
        PDGStatement node = (PDGStatement)PDGTestUtil.getNode(pdg, 1);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!inc( int )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest102_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test102", "m( )");
        PDGStatement node = (PDGStatement)PDGTestUtil.getNode(pdg, 2);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest102_3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test102", "m( )");
        PDGStatement node = (PDGStatement)PDGTestUtil.getNode(pdg, 3);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest102_4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test102", "m( )");
        PDGStatement node = (PDGStatement)PDGTestUtil.getNode(pdg, 4);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest102_5() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test102", "m( )");
        PDGStatement node = (PDGStatement)PDGTestUtil.getNode(pdg, 5);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest102_6() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test102", "m( )");
        PDGStatement node = (PDGStatement)PDGTestUtil.getNode(pdg, 6);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest108_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGStatement node = (PDGStatement)PDGTestUtil.getNode(pdg, 1);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest108_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGStatement node = (PDGStatement)PDGTestUtil.getNode(pdg, 2);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest108_3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGStatement node = (PDGStatement)PDGTestUtil.getNode(pdg, 3);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest108_4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGStatement node = (PDGStatement)PDGTestUtil.getNode(pdg, 4);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("x$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest108_5() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGStatement node = (PDGStatement)PDGTestUtil.getNode(pdg, 5);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("y$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest108_6() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGStatement node = (PDGStatement)PDGTestUtil.getNode(pdg, 11);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("$_", TestUtil.asStrOfReference(result));
    }
}
