/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.QualifiedName;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.PDGTestUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Tests a class that builds a CFG.
 * 
 * @author Katsuhisa Maruyama
 */
public class PDGEntryTest {
    
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
    public void testGetQualifiedNameTest101_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test101", "m( )");
        PDGEntry node = pdg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("Test101#m( )", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNameTest101_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test101", "p");
        PDGEntry node = pdg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("Test101#p", result.fqn());
    }
    
    @Test
    public void testGetSignartureTest101_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test101", "m( )");
        PDGEntry node = pdg.getEntryNode();
        String result = node.getSignature();
        
        assertEquals("m( )", result);
    }
    
    @Test
    public void testGetSignartureTest101_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test101", "p");
        PDGEntry node = pdg.getEntryNode();
        String result = node.getSignature();
        
        assertEquals("p", result);
    }
}
