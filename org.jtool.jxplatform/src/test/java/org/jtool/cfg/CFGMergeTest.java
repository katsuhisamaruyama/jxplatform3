/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.JavaProject;
import org.jtool.cfg.internal.CFGTestUtil;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CFGMergeTest {
    
    private static JavaProject SliceProject;
    
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
