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
import static org.junit.Assert.assertTrue;

public class CFGExitTest {
    
    @BeforeClass
    public static void setUp() {
        SliceProject = BuilderTestUtil.getProject("Slice");
    }
    
    @Test
    public void testGetTypeName_Test122() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGExit node = (CFGExit)CFGTestUtil.getNode(cfg, 4);
        
        assertTrue(node.isExit());
    }
    
    @Test
    public void testGetTypeName_Test123_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "p");
        CFGExit node = (CFGExit)CFGTestUtil.getNode(cfg, 2);
        
        assertTrue(node.isExit());
    }
}
