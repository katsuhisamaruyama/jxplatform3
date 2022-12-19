/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal;

import org.jtool.cfg.CFG;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.srcmodel.JavaProject;
import java.util.List;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class JReturnValueReferenceTest {
    
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
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getDefReturnValueReference(cfg);
        
        assertEquals("Test103", result.get(0).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingClassName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getUseReturnValueReference(cfg);
        
        assertEquals("Test103", result.get(0).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingMethodName1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getDefReturnValueReference(cfg);
        
        assertEquals("m( )", result.get(0).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getUseReturnValueReference(cfg);
        
        assertEquals("m( )", result.get(0).getEnclosingMethodName());
    }
    
    @Test
    public void testGetDeclaringClassName1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getDefReturnValueReference(cfg);
        
        assertEquals("Test103", result.get(0).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getUseReturnValueReference(cfg);
        
        assertEquals("Test103", result.get(0).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringMethodName1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getDefReturnValueReference(cfg);
        
        assertEquals("m( )", result.get(0).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getUseReturnValueReference(cfg);
        
        assertEquals("m( )", result.get(0).getDeclaringMethodName());
    }
    
    @Test
    public void testGetName1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getDefReturnValueReference(cfg);
        
        assertEquals("this.!setA( int )", result.get(0).getName());
    }
    
    @Test
    public void testGetName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getUseReturnValueReference(cfg);
        
        assertEquals("this.!setA( int )", result.get(0).getName());
    }
    
    @Test
    public void testGetSignature1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getDefReturnValueReference(cfg);
        
        assertEquals("m( )!this.!setA( int )", result.get(0).getSignature());
    }
    
    @Test
    public void testGetSignature2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getUseReturnValueReference(cfg);
        
        assertEquals("m( )!this.!setA( int )", result.get(0).getSignature());
    }
    
    @Test
    public void testGetQualifiedName1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getDefReturnValueReference(cfg);
        
        assertEquals("Test103#m( )!this.!setA( int )", result.get(0).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getUseReturnValueReference(cfg);
        
        assertEquals("Test103#m( )!this.!setA( int )", result.get(0).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetReferenceForm1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getDefReturnValueReference(cfg);
        
        assertEquals("this.!setA( int )", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getUseReturnValueReference(cfg);
        
        assertEquals("this.!setA( int )", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getDefReturnValueReference(cfg);
        
        assertEquals("a$0.!setX( int )", result.get(1).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getUseReturnValueReference(cfg);
        
        assertEquals("a$0.!setX( int )", result.get(1).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getDefReturnValueReference(cfg);
        
        assertEquals("a$0.!getX( )", result.get(2).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getUseReturnValueReference(cfg);
        
        assertEquals("a$0.!getX( )", result.get(2).getReferenceForm());
    }
    
    @Test
    public void testGetReceiverName1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getDefReturnValueReference(cfg);
        
        assertEquals("this", result.get(0).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getUseReturnValueReference(cfg);
        
        assertEquals("this", result.get(0).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getDefReturnValueReference(cfg);
        
        assertEquals("a$0", result.get(1).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getUseReturnValueReference(cfg);
        
        assertEquals("a$0", result.get(1).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getDefReturnValueReference(cfg);
        
        assertEquals("a$0", result.get(2).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getUseReturnValueReference(cfg);
        
        assertEquals("a$0", result.get(2).getReceiverName());
    }
    
    @Test
    public void testGetType1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getDefReturnValueReference(cfg);
        
        assertEquals("void", result.get(0).getType());
    }
    
    @Test
    public void testGetType2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getUseReturnValueReference(cfg);
        
        assertEquals("void", result.get(0).getType());
    }
    
    @Test
    public void testGetType3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getDefReturnValueReference(cfg);
        
        assertEquals("void", result.get(1).getType());
    }
    
    @Test
    public void testGetType4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getUseReturnValueReference(cfg);
        
        assertEquals("void", result.get(1).getType());
    }
    
    @Test
    public void testGetType5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getDefReturnValueReference(cfg);
        
        assertEquals("int", result.get(2).getType());
    }
    
    @Test
    public void testGetType6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getUseReturnValueReference(cfg);
        
        assertEquals("int", result.get(2).getType());
    }
    
    @Test
    public void testIsPrimitiveType1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getDefReturnValueReference(cfg);
        
        assertTrue(result.get(0).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getUseReturnValueReference(cfg);
        
        assertTrue(result.get(0).isPrimitiveType());
    }
    
    @Test
    public void testIsFieldAccess1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getDefReturnValueReference(cfg);
        
        assertFalse(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getUseReturnValueReference(cfg);
        
        assertFalse(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsLocalAccess1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getDefReturnValueReference(cfg);
        
        assertFalse(result.get(0).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getUseReturnValueReference(cfg);
        
        assertFalse(result.get(0).isLocalAccess());
    }
    
    @Test
    public void testIsVariableAccess1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getDefReturnValueReference(cfg);
        
        assertFalse(result.get(0).isVariableAccess());
    }
    
    @Test
    public void testIsVariableAccess2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getUseReturnValueReference(cfg);
        
        assertFalse(result.get(0).isVariableAccess());
    }
    
    @Test
    public void testIsMethodCall1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getDefReturnValueReference(cfg);
        
        assertFalse(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getUseReturnValueReference(cfg);
        
        assertFalse(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsAvailable1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getDefReturnValueReference(cfg);
        
        assertFalse(result.get(0).isAvailable());
    }
    
    @Test
    public void testIsAvailable2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getUseReturnValueReference(cfg);
        
        assertFalse(result.get(0).isAvailable());
    }
    
    @Test
    public void testGetPrefix1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getDefReturnValueReference(cfg);
        
        assertNull(result.get(0).getPrefix());
    }
    
    @Test
    public void testGetPrefix2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getUseReturnValueReference(cfg);
        
        assertNull(result.get(0).getPrefix());
    }
    
    @Test
    public void testGetPrefix3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getDefReturnValueReference(cfg);
        
        assertEquals("a$0", result.get(1).getPrefix().getReferenceForm());
    }
    
    @Test
    public void testGetPrefix4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getUseReturnValueReference(cfg);
        
        assertEquals("a$0", result.get(1).getPrefix().getReferenceForm());
    }
    
    @Test
    public void testGetPrefix5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getDefReturnValueReference(cfg);
        
        assertEquals("a$0", result.get(2).getPrefix().getReferenceForm());
    }
    
    @Test
    public void testGetPrefix6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        List<JReturnValueReference> result = CFGTestUtil.getUseReturnValueReference(cfg);
        
        assertEquals("a$0", result.get(2).getPrefix().getReferenceForm());
    }
}
