/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.CFGTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import java.util.List;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests a class that builds a CFG.
 * 
 * @author Katsuhisa Maruyama
 */
public class CFGTryTest {
    
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
    public void testGetCatchNodes1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "m( )");
        CFGTry node = (CFGTry)CFGTestUtil.getNode(cfg, 3);
        List<String> result = CFGTestUtil.getIdList(cfg, node.getCatchNodes());
        
        assertEquals("12", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetCatchNodes2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test123", "m( )");
        CFGTry node = (CFGTry)CFGTestUtil.getNode(cfg, 3);
        List<String> result = CFGTestUtil.getIdList(cfg, node.getCatchNodes());
        
        assertEquals("12", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetCatchNodes3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test123", "m2( )");
        CFGTry node = (CFGTry)CFGTestUtil.getNode(cfg, 1);
        List<String> result = CFGTestUtil.getIdList(cfg, node.getCatchNodes());
        
        assertEquals("8", TestUtil.asStr(result));
        assert node.isTry();
    }
    
    @Test
    public void testGetFinallyNode1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "m( )");
        CFGTry node = (CFGTry)CFGTestUtil.getNode(cfg, 3);
        CFGNode result = node.getFinallyNode();
        
        
        assertEquals("14", String.valueOf(result.getId() - cfg.getId()));
    }
    
    @Test
    public void testGetFinallyNode2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test123", "m( )");
        CFGTry node = (CFGTry)CFGTestUtil.getNode(cfg, 3);
        CFGNode result = node.getFinallyNode();
        
        assertEquals("14", String.valueOf(result.getId() - cfg.getId()));
    }
    
    @Test
    public void testGetFinallyNode3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test123", "m2( )");
        CFGTry node = (CFGTry)CFGTestUtil.getNode(cfg, 1);
        CFGNode result = node.getFinallyNode();
        
        assertNull(result);
    }
}
