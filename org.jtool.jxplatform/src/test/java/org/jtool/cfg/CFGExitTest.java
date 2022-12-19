/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.JavaProject;
import org.jtool.cfg.internal.CFGTestUtil;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class CFGExitTest {
    
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
