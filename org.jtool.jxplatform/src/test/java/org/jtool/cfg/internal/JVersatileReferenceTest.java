/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal;

import org.jtool.cfg.CFG;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.srcmodel.JavaProject;
import java.util.List;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class JVersatileReferenceTest {
    
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
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getDefVersatileReference(cfg);
        
        assertEquals("Test103", result.get(0).getEnclosingClassName());
        assertEquals("$_", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetEnclosingClassName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("Test103", result.get(0).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingClassName3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "n( int )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("Test122", result.get(0).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingClassName4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test129", "m( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("Test129", result.get(0).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingMethodName1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getDefVersatileReference(cfg);
        
        assertEquals("getA( )", result.get(0).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("getA( )", result.get(0).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "n( int )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("n( int )", result.get(0).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test129", "m( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("m( )", result.get(0).getEnclosingMethodName());
    }
    
    @Test
    public void testGetDeclaringClassName1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getDefVersatileReference(cfg);
        
        assertEquals("Test103", result.get(0).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("Test103", result.get(0).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "n( int )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("Test122", result.get(0).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test129", "m( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("Test129", result.get(0).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringMethodName1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getDefVersatileReference(cfg);
        
        assertEquals("getA( )", result.get(0).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("getA( )", result.get(0).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "n( int )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("n( int )", result.get(0).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test129", "m( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("m( )", result.get(0).getDeclaringMethodName());
    }
    
    @Test
    public void testGetName1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getDefVersatileReference(cfg);
        
        assertEquals("$_", result.get(0).getName());
    }
    
    @Test
    public void testGetName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("$_", result.get(0).getName());
    }
    
    @Test
    public void testGetName3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "n( int )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("$java.lang.Exception", result.get(0).getName());
    }
    
    @Test
    public void testGetName4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test129", "m( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("$java.lang.String", result.get(0).getName());
    }
    
    @Test
    public void testGetSignature1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getDefVersatileReference(cfg);
        
        assertEquals("getA( )!$_", result.get(0).getSignature());
    }
    
    @Test
    public void testGetSignature2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("getA( )!$_", result.get(0).getSignature());
    }
    
    @Test
    public void testGetSignature3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "n( int )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("n( int )!$java.lang.Exception", result.get(0).getSignature());
    }
    
    @Test
    public void testGetSignature4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test129", "m( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("m( )!$java.lang.String", result.get(0).getSignature());
    }
    
    @Test
    public void testGetQualifiedName1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getDefVersatileReference(cfg);
        
        assertEquals("Test103#getA( )!$_", result.get(0).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("Test103#getA( )!$_", result.get(0).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "n( int )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("Test122#n( int )!$java.lang.Exception", result.get(0).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test129", "m( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("Test129#m( )!$java.lang.String", result.get(0).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetReferenceForm1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getDefVersatileReference(cfg);
        
        assertEquals("$_", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("$_", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "n( int )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("$java.lang.Exception", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test129", "m( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("$java.lang.String", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetReceiverName1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getDefVersatileReference(cfg);
        
        assertEquals("", result.get(0).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("", result.get(0).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "n( int )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("", result.get(0).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test129", "m( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("", result.get(0).getReceiverName());
    }
    
    @Test
    public void testGetType1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getDefVersatileReference(cfg);
        
        assertEquals("int", result.get(0).getType());
    }
    
    @Test
    public void testGetType2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("int", result.get(0).getType());
    }
    
    @Test
    public void testGetType3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "n( int )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("java.lang.Exception", result.get(0).getType());
    }
    
    @Test
    public void testGetType4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test129", "m( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertEquals("java.lang.String", result.get(0).getType());
    }
    
    @Test
    public void testIsPrimitiveType1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getDefVersatileReference(cfg);
        
        assertTrue(result.get(0).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertTrue(result.get(0).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "n( int )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertFalse(result.get(0).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test129", "m( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertFalse(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getDefVersatileReference(cfg);
        
        assertFalse(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertFalse(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "n( int )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertFalse(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test129", "m( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertFalse(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsLocalAccess1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getDefVersatileReference(cfg);
        
        assertFalse(result.get(0).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertFalse(result.get(0).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "n( int )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertFalse(result.get(0).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test129", "m( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertFalse(result.get(0).isLocalAccess());
    }
    
    @Test
    public void testIsVariableAccess1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getDefVersatileReference(cfg);
        
        assertFalse(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsVariableAccess2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertFalse(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsVariableAccess3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "n( int )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertFalse(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsVariableAccess4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test129", "m( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertFalse(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getDefVersatileReference(cfg);
        
        assertFalse(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertFalse(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "n( int )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertFalse(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test129", "m( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertFalse(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsAvailable1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertFalse(result.get(0).isAvailable());
    }
    
    @Test
    public void testIsAvailable2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertFalse(result.get(0).isAvailable());
    }
    
    @Test
    public void testIsAvailable3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "n( int )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertFalse(result.get(0).isAvailable());
    }
    
    @Test
    public void testIsAvailable4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test129", "m( )");
        List<JVersatileReference> result = CFGTestUtil.getUseVersatileReference(cfg);
        
        assertFalse(result.get(0).isAvailable());
    }
}

