/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.CFGTestUtil;
import org.jtool.srcmodel.JavaProject;
import java.util.List;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class JExpedientialReferenceTest {
    
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
    public void testGetEnclosingClassName1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertEquals("Test103", result.get(0).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingClassName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertEquals("Test103", result.get(1).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingMethodName1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertEquals("setA( int )", result.get(0).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertEquals("setA( int )", result.get(1).getEnclosingMethodName());
    }
    
    @Test
    public void testGetDeclaringClassName1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertEquals("Test103", result.get(0).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertEquals("Test103", result.get(1).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringMethodName1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertEquals("setA( int )", result.get(0).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertEquals("setA( int )", result.get(1).getDeclaringMethodName());
    }
    
    @Test
    public void testGetName1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertEquals("this", result.get(0).getName());
    }
    
    @Test
    public void testGetName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertEquals("$_", result.get(1).getName());
    }
    
    @Test
    public void testGetSignature1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertEquals("setA( int )!this", result.get(0).getSignature());
    }
    
    @Test
    public void testGetSignature2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertEquals("setA( int )!$_", result.get(1).getSignature());
    }
    
    @Test
    public void testGetQualifiedName1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertEquals("setA( int )!this", result.get(0).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertEquals("setA( int )!$_", result.get(1).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetReferenceForm1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertEquals("this", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertEquals("$_", result.get(1).getReferenceForm());
    }
    
    @Test
    public void testGetReceiverName1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertEquals("", result.get(0).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertEquals("", result.get(1).getReceiverName());
    }
    
    @Test
    public void testGetType1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertEquals("Test103", result.get(0).getType());
    }
    
    @Test
    public void testGetType2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertEquals("void", result.get(1).getType());
    }
    
    @Test
    public void testIsPrimitiveType1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertFalse(result.get(0).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertFalse(result.get(1).isPrimitiveType());
    }
    
    @Test
    public void testIsFieldAccess1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertFalse(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertFalse(result.get(1).isFieldAccess());
    }
    
    @Test
    public void testIsLocalAccess1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertFalse(result.get(0).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertFalse(result.get(1).isLocalAccess());
    }
    
    @Test
    public void testIsVariableAccess1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertFalse(result.get(0).isVariableAccess());
    }
    
    @Test
    public void testIsVariableAccess2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertFalse(result.get(1).isVariableAccess());
    }
    
    @Test
    public void testIsMethodCall1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertFalse(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertFalse(result.get(1).isMethodCall());
    }
    
    @Test
    public void testIsExposed1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertFalse(result.get(0).isTouchable());
    }
    
    @Test
    public void testIsExposed2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JExpedientialReference> result = CFGTestUtil.getUseExpedientialReference(cfg);
        
        assertFalse(result.get(1).isTouchable());
    }
}
