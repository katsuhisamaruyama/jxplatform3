/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.QualifiedName;
import org.jtool.cfg.CCFGEntry;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.PDGTestUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ClDGEntryTest {
    
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
    public void testGetQualifiedNameTest101() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test101");
        ClDGEntry node = cldg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("Test101", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNamePriceCode() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        ClDGEntry node = cldg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("PriceCode", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNameTest134() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "I134");
        ClDGEntry node = cldg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("I134", result.fqn());
    }
    
    @Test
    public void testGetClDGTest101() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test101");
        ClDGEntry node = cldg.getEntryNode();
        ClDG result = node.getClDG();
        
        assertEquals(cldg, result);
    }
    
    
    @Test
    public void testGetClDGPriceCode() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        ClDGEntry node = cldg.getEntryNode();
        ClDG result = node.getClDG();
        
        assertEquals(cldg, result);
    }
    
    @Test
    public void testGetClDGI134() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "I134");
        ClDGEntry node = cldg.getEntryNode();
        ClDG result = node.getClDG();
        
        assertEquals(cldg, result);
    }
    
    @Test
    public void testGetCCFGEntryTest101() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test101");
        ClDGEntry node = cldg.getEntryNode();
        CCFGEntry result = node.getCCFGEntry();
        
        assertEquals("Test101", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetCCFGEntryPriceCode() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        ClDGEntry node = cldg.getEntryNode();
        CCFGEntry result = node.getCCFGEntry();
        
        assertEquals("PriceCode", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetCCFGEntryI134() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "I134");
        ClDGEntry node = cldg.getEntryNode();
        CCFGEntry result = node.getCCFGEntry();
        
        assertEquals("I134", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetCCFGTest101() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test101");
        ClDGEntry node = cldg.getEntryNode();
        ClDG result = node.getClDG();
        
        assertEquals("Test101", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetCCFGPriceCode() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        ClDGEntry node = cldg.getEntryNode();
        ClDG result = node.getClDG();
        
        assertEquals("PriceCode", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetCCFGI134() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "I134");
        ClDGEntry node = cldg.getEntryNode();
        ClDG result = node.getClDG();
        
        assertEquals("I134", result.getQualifiedName().fqn());
    }
}
