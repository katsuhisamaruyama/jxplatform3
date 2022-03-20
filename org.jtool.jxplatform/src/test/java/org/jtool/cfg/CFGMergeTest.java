/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.CFGTestUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CFGMergeTest {
    
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
    public void testGetBranchTest108() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGMerge node = (CFGMerge)CFGTestUtil.getCFGNode(cfg, 8);
        CFGNode branch = CFGTestUtil.getCFGNode(cfg, 5);
        CFGStatement result = node.getBranch();
        
        assertEquals(branch, result);
    }
    
    @Test
    public void testGetBranchTest111() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test111", "m( )");
        CFGMerge node = (CFGMerge)CFGTestUtil.getCFGNode(cfg, 11);
        CFGNode branch = CFGTestUtil.getCFGNode(cfg, 4);
        CFGStatement result = node.getBranch();
        
        assertEquals(branch, result);
    }
    
    @Test
    public void testGetBranchTest113() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test113", "m( )");
        CFGMerge node = (CFGMerge)CFGTestUtil.getCFGNode(cfg, 7);
        CFGNode branch = CFGTestUtil.getCFGNode(cfg, 3);
        CFGStatement result = node.getBranch();
        
        assertEquals(branch, result);
    }
    
    @Test
    public void testGetBranchTest122() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "m( )");
        CFGMerge node = (CFGMerge)CFGTestUtil.getCFGNode(cfg, 12);
        CFGNode branch = CFGTestUtil.getCFGNode(cfg, 4);
        CFGStatement result = node.getBranch();
        
        assertEquals(branch, result);
    }
}
