/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import static org.jtool.cfg.AllCFGTests.SliceProject;
import org.jtool.cfg.internal.CFGTestUtil;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import java.util.Set;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CCFGTest {
    
    @BeforeClass
    public static void setUp() {
        SliceProject = BuilderTestUtil.getProject("Slice");
    }
    
    @Test
    public void testGetCFGs_Test103() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test103");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetCFGs_Test119() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test119");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetCFGs_A119() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "A119");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetCFGs_Test129() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test129");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetCFGs_P129() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "P129");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(8, result.size());
    }
    
    @Test
    public void testGetCFGs_S129() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "S129");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetCFGsT129() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "T129");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetCFGs_U129() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "U129");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetCFGs_Test134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test134");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(13, result.size());
    }
    
    @Test
    public void testGetCFGs_I134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "I134");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetCFGs_P134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "P134");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetCFGs_Q134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Q134");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetCFGs_R134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "R134");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetCFGs_Test139() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test139");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetCFGs_PriceCode() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "PriceCode");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetCFGs_Customer() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Customer");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetCFGs_CustomerTest() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "CustomerTest");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetCFGs_Order() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Order");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetCFGs_Rental() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Rental");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testIsClass() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test134");
        
        assertTrue(ccfg.isClass());
    }
    
    @Test
    public void testIsInterface() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "I134");
        
        assertTrue(ccfg.isInterface());
    }
    
    @Test
    public void testIsEnum() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "PriceCode");
        
        assertTrue(ccfg.isEnum());
    }
}
