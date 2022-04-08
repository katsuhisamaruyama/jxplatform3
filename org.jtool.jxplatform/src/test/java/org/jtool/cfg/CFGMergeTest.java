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
        CFGTestUtil.writeCFGs(SliceProject);
    }
    
    @AfterClass
    public static void tearDown() {
        SliceProject.getModelBuilder().unbuild();
    }
    
    // TODO
    @Test
    public void testGetBranchTest108() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGMerge node = (CFGMerge)CFGTestUtil.getNode(cfg, 7);
        CFGNode branch = CFGTestUtil.getNode(cfg, 4);
        CFGStatement result = node.getBranch();
        
        assertEquals(branch, result);
    }
    
    @Test
    public void testGetBranchTest111() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test111", "m( )");
        CFGMerge node = (CFGMerge)CFGTestUtil.getNode(cfg, 11);
        CFGNode branch = CFGTestUtil.getNode(cfg, 4);
        CFGStatement result = node.getBranch();
        
        assertEquals(branch, result);
    }
    
    @Test
    public void testGetBranchTest113() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test113", "m( )");
        CFGMerge node = (CFGMerge)CFGTestUtil.getNode(cfg, 7);
        CFGNode branch = CFGTestUtil.getNode(cfg, 3);
        CFGStatement result = node.getBranch();
        
        assertEquals(branch, result);
    }
    
    @Test
    public void testGetBranchTest122() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "m( )");
        CFGMerge node = (CFGMerge)CFGTestUtil.getNode(cfg, 11);
        CFGNode branch = CFGTestUtil.getNode(cfg, 3);
        CFGStatement result = node.getBranch();
        
        assertEquals(branch, result);
    }
}
