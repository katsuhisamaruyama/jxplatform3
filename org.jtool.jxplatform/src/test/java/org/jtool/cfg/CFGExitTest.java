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
    public void testGetTypeNameTest122() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGExit node = (CFGExit)CFGTestUtil.getCFGNode(cfg, 4);
        
        assertTrue(node.isExit());
    }
    
    @Test
    public void testGetTypeNameTest123_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "p");
        CFGExit node = (CFGExit)CFGTestUtil.getCFGNode(cfg, 2);
        
        assertTrue(node.isExit());
    }
}
