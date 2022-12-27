/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import static org.jtool.cfg.AllCFGTests.SliceProject;
import org.jtool.cfg.internal.CFGTestUtil;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;

public class CFGMergeTest {
    
    @BeforeClass
    public static void setUp() {
        SliceProject = BuilderTestUtil.getProject("Slice");
    }
    
    @Test
    public void testGetBranch_Test108() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGMerge node = (CFGMerge)CFGTestUtil.getNode(cfg, 7);
        CFGNode branch = CFGTestUtil.getNode(cfg, 4);
        CFGStatement result = node.getBranch();
        
        assertEquals(branch, result);
    }
    
    @Test
    public void testGetBranch_Test111() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test111", "m( )");
        CFGMerge node = (CFGMerge)CFGTestUtil.getNode(cfg, 11);
        CFGNode branch = CFGTestUtil.getNode(cfg, 4);
        CFGStatement result = node.getBranch();
        
        assertEquals(branch, result);
    }
    
    @Test
    public void testGetBranch_Test113() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test113", "m( )");
        CFGMerge node = (CFGMerge)CFGTestUtil.getNode(cfg, 7);
        CFGNode branch = CFGTestUtil.getNode(cfg, 3);
        CFGStatement result = node.getBranch();
        
        assertEquals(branch, result);
    }
    
    @Test
    public void testGetBranch_Test122() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "m( )");
        CFGMerge node = (CFGMerge)CFGTestUtil.getNode(cfg, 11);
        CFGNode branch = CFGTestUtil.getNode(cfg, 3);
        CFGStatement result = node.getBranch();
        
        assertEquals(branch, result);
    }
}
