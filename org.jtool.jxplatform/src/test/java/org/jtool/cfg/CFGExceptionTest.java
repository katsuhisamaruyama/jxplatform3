/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.JavaProject;
import org.jtool.cfg.internal.CFGTestUtil;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;

public class CFGExceptionTest {
    
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
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "n( int )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        CFGException exception = node.getExceptionNodes().get(0);
        String result = exception.getTypeName();
        
        assertEquals("java.lang.Exception", result);
    }
    
    @Test
    public void testGetTypeName_Test123_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test123", "n( int )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        CFGException exception = node.getExceptionNodes().get(0);
        String result = exception.getTypeName();
        
        assertEquals("SubException", result);
    }
    
    @Test
    public void testGetTypeName_Test123_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test123", "n2( int )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        CFGException exception = node.getExceptionNodes().get(0);
        String result = exception.getTypeName();
        
        assertEquals("SubRuntimeException", result);
    }
    
    @Test
    public void testGetTypeName_Test123_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test123", "n3( int )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        CFGException exception = node.getExceptionNodes().get(0);
        String result = exception.getTypeName();
        
        assertEquals("SubRuntimeException", result);
    }
}
