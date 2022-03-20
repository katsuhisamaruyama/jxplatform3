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

/**
 * Tests a class that builds a CFG.
 * 
 * @author Katsuhisa Maruyama
 */
public class ClDGEntryTest {
    
    private static JavaProject SliceProject;
    private static JavaProject VideoStoreProject;
    
    @BeforeClass
    public static void setUp() {
        SliceProject = BuilderTestUtil.createProject("Slice", "", "");
        VideoStoreProject = BuilderTestUtil.createProject("VideoStore", "/lib/*", "");
    }
    
    @AfterClass
    public static void tearDown() {
        SliceProject.getModelBuilder().unbuild();
        VideoStoreProject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testGetQualifiedNameTest101() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test101");
        ClDGEntry node = cldg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("Test101", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNameTest134() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test134");
        ClDGEntry node = cldg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("Test134", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNameP134() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P134");
        ClDGEntry node = cldg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("P134", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNameQ134() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Q134");
        ClDGEntry node = cldg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("Q134", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNameR134() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "R134");
        ClDGEntry node = cldg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("R134", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNameI134() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "I134");
        ClDGEntry node = cldg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("I134", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNameTest139() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test139");
        ClDGEntry node = cldg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("Test139", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNamePriceCode() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        ClDGEntry node = cldg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("PriceCode", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNameCustomer() {
        ClDG cldg = PDGTestUtil.createClDG(VideoStoreProject, "org.jtool.videostore.after.Customer");
        ClDGEntry node = cldg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("org.jtool.videostore.after.Customer", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNameRental() {
        ClDG cldg = PDGTestUtil.createClDG(VideoStoreProject, "org.jtool.videostore.after.Rental");
        ClDGEntry node = cldg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("org.jtool.videostore.after.Rental", result.fqn());
    }
    
    @Test
    public void testGetClDGTest101() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test101");
        ClDGEntry node = cldg.getEntryNode();
        ClDG result = node.getClDG();
        
        assertEquals(cldg, result);
    }
    
    @Test
    public void testGetClDGTest134() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test134");
        ClDGEntry node = cldg.getEntryNode();
        ClDG result = node.getClDG();
        
        assertEquals(cldg, result);
    }
    
    @Test
    public void testGetClDGP134() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P134");
        ClDGEntry node = cldg.getEntryNode();
        ClDG result = node.getClDG();
        
        assertEquals(cldg, result);
    }
    
    @Test
    public void testGetClDGQ134() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Q134");
        ClDGEntry node = cldg.getEntryNode();
        ClDG result = node.getClDG();
        
        assertEquals(cldg, result);
    }
    
    @Test
    public void testGetClDGR134() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "R134");
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
    public void testGetClDGTest135() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test135");
        ClDGEntry node = cldg.getEntryNode();
        ClDG result = node.getClDG();
        
        assertEquals(cldg, result);
    }
    
    @Test
    public void testGetClDGS135() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "S135");
        ClDGEntry node = cldg.getEntryNode();
        ClDG result = node.getClDG();
        
        assertEquals(cldg, result);
    }
    
    @Test
    public void testGetClDGTest139() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test139");
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
    public void testGetClDGCustomer() {
        ClDG cldg = PDGTestUtil.createClDG(VideoStoreProject, "org.jtool.videostore.after.Customer");
        ClDGEntry node = cldg.getEntryNode();
        ClDG result = node.getClDG();
        
        assertEquals(cldg, result);
    }
    
    @Test
    public void testGetClDGRental() {
        ClDG cldg = PDGTestUtil.createClDG(VideoStoreProject, "org.jtool.videostore.after.Rental");
        ClDGEntry node = cldg.getEntryNode();
        ClDG result = node.getClDG();
        
        assertEquals(cldg, result);
    }
    
    @Test
    public void testGetCFGClassEntryTest101() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test101");
        ClDGEntry node = cldg.getEntryNode();
        CCFGEntry result = node.getCCFGEntry();
        
        assertEquals("Test101", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetCFGClassEntryTest134() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test134");
        ClDGEntry node = cldg.getEntryNode();
        CCFGEntry result = node.getCCFGEntry();
        
        assertEquals("Test134", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetCFGP134() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P134");
        ClDGEntry node = cldg.getEntryNode();
        CCFGEntry result = node.getCCFGEntry();
        
        assertEquals("P134", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetCFGQ134() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Q134");
        ClDGEntry node = cldg.getEntryNode();
        CCFGEntry result = node.getCCFGEntry();
        
        assertEquals("Q134", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetCFGR134() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "R134");
        ClDGEntry node = cldg.getEntryNode();
        CCFGEntry result = node.getCCFGEntry();
        
        assertEquals("R134", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetCFGI134() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "I134");
        ClDGEntry node = cldg.getEntryNode();
        CCFGEntry result = node.getCCFGEntry();
        
        assertEquals("I134", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetCFGTest135() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test135");
        ClDGEntry node = cldg.getEntryNode();
        CCFGEntry result = node.getCCFGEntry();
        
        assertEquals("Test135", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetCFGS135() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "S135");
        ClDGEntry node = cldg.getEntryNode();
        CCFGEntry result = node.getCCFGEntry();
        
        assertEquals("S135", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetCFGTest139() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test139");
        ClDGEntry node = cldg.getEntryNode();
        CCFGEntry result = node.getCCFGEntry();
        
        assertEquals("Test139", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetCFGPriceCode() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        ClDGEntry node = cldg.getEntryNode();
        CCFGEntry result = node.getCCFGEntry();
        
        assertEquals("PriceCode", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetCFGCustomer() {
        ClDG cldg = PDGTestUtil.createClDG(VideoStoreProject, "org.jtool.videostore.after.Customer");
        ClDGEntry node = cldg.getEntryNode();
        CCFGEntry result = node.getCCFGEntry();
        
        assertEquals("org.jtool.videostore.after.Customer", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetCFGRental() {
        ClDG cldg = PDGTestUtil.createClDG(VideoStoreProject, "org.jtool.videostore.after.Rental");
        ClDGEntry node = cldg.getEntryNode();
        CCFGEntry result = node.getCCFGEntry();
        
        assertEquals("org.jtool.videostore.after.Rental", result.getQualifiedName().fqn());
    }
}
