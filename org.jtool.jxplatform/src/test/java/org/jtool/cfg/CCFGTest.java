/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.CFGTestUtil;
import java.util.Set;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CCFGTest {
    
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
    public void testGetCFGsTest103() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test103");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetCFGsTest119() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test119");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetCFGsA119() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "A119");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetCFGsTest129() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test129");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetCFGsP129() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "P129");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(8, result.size());
    }
    
    @Test
    public void testGetCFGsS129() {
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
    public void testGetCFGsU129() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "U129");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetCFGsTest134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test134");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(13, result.size());
    }
    
    @Test
    public void testGetCFGsI134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "I134");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetCFGsP134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "P134");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetCFGsQ134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Q134");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetCFGsR134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "R134");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetCFGsTest139() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test139");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetCFGsPriceCode() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "PriceCode");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetCFGsCustomer() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Customer");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetCFGsCustomerTest() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "CustomerTest");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetCFGsOrder() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Order");
        Set<CFG> result = ccfg.getCFGs();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetCFGsRental() {
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
