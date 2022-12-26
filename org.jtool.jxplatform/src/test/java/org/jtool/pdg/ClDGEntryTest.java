/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.QualifiedName;
import org.jtool.cfg.CCFGEntry;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ClDGEntryTest {
    
    private static JavaProject SliceProject;
    
    @BeforeClass
    public static void setUp() {
        SliceProject = BuilderTestUtil.getProject("Slice");
    }
    
    @Test
    public void testGetQualifiedName_Test101() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test101");
        ClDGEntry node = cldg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("Test101", result.fqn());
    }
    
    @Test
    public void testGetQualifiedName_PriceCode() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        ClDGEntry node = cldg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("PriceCode", result.fqn());
    }
    
    @Test
    public void testGetQualifiedName_Test134() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "I134");
        ClDGEntry node = cldg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("I134", result.fqn());
    }
    
    @Test
    public void testGetClDG_Test101() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test101");
        ClDGEntry node = cldg.getEntryNode();
        ClDG result = node.getClDG();
        
        assertEquals(cldg, result);
    }
    
    
    @Test
    public void testGetClDG_PriceCode() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        ClDGEntry node = cldg.getEntryNode();
        ClDG result = node.getClDG();
        
        assertEquals(cldg, result);
    }
    
    @Test
    public void testGetClDG_I134() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "I134");
        ClDGEntry node = cldg.getEntryNode();
        ClDG result = node.getClDG();
        
        assertEquals(cldg, result);
    }
    
    @Test
    public void testGetCCFGEntry_Test101() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test101");
        ClDGEntry node = cldg.getEntryNode();
        CCFGEntry result = node.getCCFGEntry();
        
        assertEquals("Test101", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetCCFGEntry_PriceCode() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        ClDGEntry node = cldg.getEntryNode();
        CCFGEntry result = node.getCCFGEntry();
        
        assertEquals("PriceCode", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetCCFGEntry_I134() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "I134");
        ClDGEntry node = cldg.getEntryNode();
        CCFGEntry result = node.getCCFGEntry();
        
        assertEquals("I134", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetCCFG_Test101() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test101");
        ClDGEntry node = cldg.getEntryNode();
        ClDG result = node.getClDG();
        
        assertEquals("Test101", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetCCFG_PriceCode() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        ClDGEntry node = cldg.getEntryNode();
        ClDG result = node.getClDG();
        
        assertEquals("PriceCode", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetCCFG_I134() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "I134");
        ClDGEntry node = cldg.getEntryNode();
        ClDG result = node.getClDG();
        
        assertEquals("I134", result.getQualifiedName().fqn());
    }
}
