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

public class CFGExceptionTest {
    
    @BeforeClass
    public static void setUp() {
        SliceProject = BuilderTestUtil.getProject("Slice");
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
