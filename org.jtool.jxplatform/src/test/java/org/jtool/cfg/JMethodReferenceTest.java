/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.jxplatform.util.CFGTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import java.util.List;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;


public class JMethodReferenceTest {
    
    @BeforeClass
    public static void setUp() {
        CFGTestUtil.setUp();
    }
    
    @AfterClass
    public static void tearDown() {
        CFGTestUtil.tearDown();
    }
    
    @Test
    public void testGetEnclosingClassName1() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("Test102", result.get(0).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingClassName2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("Test102", result.get(1).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingClassName3() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("Test102", result.get(2).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingClassName4() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("Test119", result.get(0).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingClassName5() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("Test119", result.get(1).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingClassName6() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("Test119", result.get(2).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingClassName7() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("S140", result.get(0).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingClassName8() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( int )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("S140", result.get(0).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingClassName9() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("S140", result.get(0).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingClassName10() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("Test139", result.get(0).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingClassName11() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("Test139", result.get(1).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingMethodName1() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("m( )", result.get(0).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("m( )", result.get(1).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName3() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("m( )", result.get(2).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName4() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("m( )", result.get(0).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName5() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("m( )", result.get(1).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName6() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("m( )", result.get(2).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName7() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("S140( )", result.get(0).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName8() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( int )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("S140( int )", result.get(0).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName9() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("m( )", result.get(0).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName10() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("m( )", result.get(0).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName11() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("m( )", result.get(1).getEnclosingMethodName());
    }
    
    @Test
    public void testGetDeclaringClassName1() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("Test102", result.get(0).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("Test102", result.get(1).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName3() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("java.io.PrintStream", result.get(2).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName4() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("A119", result.get(0).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName5() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("A119", result.get(1).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName6() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("A119", result.get(2).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName7() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("S140", result.get(0).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName8() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( int )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("Test140", result.get(0).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName9() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("Test140", result.get(0).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName10() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("PriceCode", result.get(0).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName11() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("java.lang.Enum", result.get(1).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringMethodName1() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("", result.get(0).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("", result.get(1).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName3() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("", result.get(2).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName4() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("", result.get(0).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName5() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("", result.get(1).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName6() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("", result.get(2).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName7() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("", result.get(0).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName8() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( int )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("", result.get(0).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName9() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("", result.get(0).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName10() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("", result.get(0).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName11() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("", result.get(1).getDeclaringMethodName());
    }
    
    @Test
    public void testGetName1() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("inc", result.get(0).getName());
    }
    
    @Test
    public void testGetName2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("inc", result.get(1).getName());
    }
    
    @Test
    public void testGetName3() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("println", result.get(2).getName());
    }
    
    @Test
    public void testGetName4() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("A119", result.get(0).getName());
    }
    
    @Test
    public void testGetName5() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("setX", result.get(1).getName());
    }
    
    @Test
    public void testGetName6() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("getX", result.get(2).getName());
    }
    
    @Test
    public void testGetName7() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("S140", result.get(0).getName());
    }
    
    @Test
    public void testGetName8() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( int )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("Test140", result.get(0).getName());
    }
    
    @Test
    public void testGetName9() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("m", result.get(0).getName());
    }
    
    @Test
    public void testGetName10() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("getPriceCode", result.get(0).getName());
    }
    
    @Test
    public void testGetName11() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("name", result.get(1).getName());
    }
    
    @Test
    public void testGetSignature1() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("inc( int )", result.get(0).getSignature());
    }
    
    @Test
    public void testGetSignature2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("inc( int )", result.get(1).getSignature());
    }
    
    @Test
    public void testGetSignature3() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("println( int )", result.get(2).getSignature());
    }
    
    @Test
    public void testGetSignature4() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("A119( )", result.get(0).getSignature());
    }
    
    @Test
    public void testGetSignature5() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("setX( int )", result.get(1).getSignature());
    }
    
    @Test
    public void testGetSignature6() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("getX( )", result.get(2).getSignature());
    }
    
    @Test
    public void testGetSignature7() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("S140( int )", result.get(0).getSignature());
    }
    
    @Test
    public void testGetSignature8() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( int )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("Test140( int )", result.get(0).getSignature());
    }
    
    @Test
    public void testGetSignature9() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("m( )", result.get(0).getSignature());
    }
    
    @Test
    public void testGetSignature10() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("getPriceCode( )", result.get(0).getSignature());
    }
    
    @Test
    public void testGetSignature11() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("name( )", result.get(1).getSignature());
    }
    
    @Test
    public void testGetQualifiedName1() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("Test102#inc( int )", result.get(0).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("Test102#inc( int )", result.get(1).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName3() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("java.io.PrintStream#println( int )", result.get(2).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName4() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("A119#A119( )", result.get(0).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName5() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("A119#setX( int )", result.get(1).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName6() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("A119#getX( )", result.get(2).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName7() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("S140#S140( int )", result.get(0).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName8() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( int )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("Test140#Test140( int )", result.get(0).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName9() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("Test140#m( )", result.get(0).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName10() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("PriceCode#getPriceCode( )", result.get(0).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName11() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("java.lang.Enum#name( )", result.get(1).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetReferenceForm1() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("this#inc( int )", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("this#inc( int )", result.get(1).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm3() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("System.out#println( int )", result.get(2).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm4() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("$InstanceName#A119( )", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm5() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("a$0#setX( int )", result.get(1).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm6() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("a$0#getX( )", result.get(2).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm7() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("S140( int )", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm8() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( int )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("Test140( int )", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm9() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("super#m( )", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm10() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("PriceCode.REGULAR#getPriceCode( )", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm11() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("PriceCode.REGULAR#name( )", result.get(1).getReferenceForm());
    }
    
    @Test
    public void testGetReceiverName1() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("this", result.get(0).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("this", result.get(1).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName3() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("System.out", result.get(2).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName4() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("a$0", result.get(0).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName5() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("a$0", result.get(1).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName6() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("a$0", result.get(2).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName7() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("", result.get(0).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName8() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( int )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("", result.get(0).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName9() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("super", result.get(0).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName10() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("PriceCode.REGULAR", result.get(0).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName11() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("PriceCode.REGULAR", result.get(1).getReceiverName());
    }
    
    @Test
    public void testGetType1() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("int", result.get(0).getType());
    }
    
    @Test
    public void testGetType2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("int", result.get(1).getType());
    }
    
    @Test
    public void testGetType3() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("void", result.get(2).getType());
    }
    
    @Test
    public void testGetType4() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("A119", result.get(0).getType());
    }
    
    @Test
    public void testGetType5() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("void", result.get(1).getType());
    }
    
    @Test
    public void testGetType6() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("int", result.get(2).getType());
    }
    
    @Test
    public void testGetType7() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("S140", result.get(0).getType());
    }
    
    @Test
    public void testGetType8() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( int )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("Test140", result.get(0).getType());
    }
    
    @Test
    public void testGetType9() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("void", result.get(0).getType());
    }
    
    @Test
    public void testGetType10() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("int", result.get(0).getType());
    }
    
    @Test
    public void testGetType11() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("java.lang.String", result.get(1).getType());
    }
    
    @Test
    public void testIsPrimitiveType1() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(0).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(1).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType3() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(2).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType4() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(0).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType5() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(1).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType6() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(2).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType7() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(0).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType8() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( int )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(0).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType9() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(0).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType10() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(0).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType11() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(1).isPrimitiveType());
    }
    
    @Test
    public void testIsFieldAccess1() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(1).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess3() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(2).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess4() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess5() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(1).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess6() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(2).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess7() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess8() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( int )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess9() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess10() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess11() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(1).isFieldAccess());
    }
    
    @Test
    public void testIsLocalAccess1() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(1).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess3() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(2).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess4() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess5() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(1).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess6() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(2).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess7() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess8() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( int )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess9() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess10() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess11() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(1).isLocalAccess());
    }
    
    @Test
    public void testIsVariableAccess1() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isVariableAccess());
    }
    
    @Test
    public void testIsVariableAccess2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(1).isVariableAccess());
    }
    
    @Test
    public void testIsVariableAccess3() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(2).isVariableAccess());
    }
    
    @Test
    public void testIsVariableAccess4() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isVariableAccess());
    }
    
    @Test
    public void testIsVariableAccess5() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(1).isVariableAccess());
    }
    
    @Test
    public void testIsVariableAccess6() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(2).isVariableAccess());
    }
    
    @Test
    public void testIsVariableAccess7() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isVariableAccess());
    }
    
    @Test
    public void testIsVariableAccess8() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( int )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isVariableAccess());
    }
    
    @Test
    public void testIsVariableAccess9() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isVariableAccess());
    }
    
    @Test
    public void testIsVariableAccess10() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isVariableAccess());
    }
    
    @Test
    public void testIsVariableAccess11() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(1).isVariableAccess());
    }
    
    @Test
    public void testIsMethodCall1() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(1).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall3() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(2).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall4() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall5() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(1).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall6() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(2).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall7() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall8() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( int )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall9() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall10() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall11() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(1).isMethodCall());
    }
    
    @Test
    public void testIsMethod1() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(0).isMethod());
    }
    
    @Test
    public void testIsMethod2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(1).isMethod());
    }
    
    @Test
    public void testIsMethod3() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(2).isMethod());
    }
    
    @Test
    public void testIsMethod4() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isMethod());
    }
    
    @Test
    public void testIsMethod5() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(1).isMethod());
    }
    
    @Test
    public void testIsMethod6() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(2).isMethod());
    }
    
    @Test
    public void testIsMethod7() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isMethod());
    }
    
    @Test
    public void testIsMethod8() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( int )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isMethod());
    }
    
    @Test
    public void testIsMethod9() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(0).isMethod());
    }
    
    @Test
    public void testIsMethod10() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(0).isMethod());
    }
    
    @Test
    public void testIsMethod11() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(1).isMethod());
    }
    
    @Test
    public void testIsConstructor1() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isConstructor());
    }
    
    @Test
    public void testIsConstructor2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(1).isConstructor());
    }
    
    @Test
    public void testIsConstructor3() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(2).isConstructor());
    }
    
    @Test
    public void testIsConstructor4() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(0).isConstructor());
    }
    
    @Test
    public void testIsConstructor5() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(1).isConstructor());
    }
    
    @Test
    public void testIsConstructor6() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(2).isConstructor());
    }
    
    @Test
    public void testIsConstructor7() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(0).isConstructor());
    }
    
    @Test
    public void testIsConstructor8() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( int )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(0).isConstructor());
    }
    
    @Test
    public void testIsConstructor9() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isConstructor());
    }
    
    @Test
    public void testIsConstructor10() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isConstructor());
    }
    
    @Test
    public void testIsConstructor11() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(1).isConstructor());
    }
    
    @Test
    public void testIsEnum1() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isEnum());
    }
    
    @Test
    public void testIsEnum2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(1).isEnum());
    }
    
    @Test
    public void testIsEnum3() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(2).isEnum());
    }
    
    @Test
    public void testIsEnum4() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isEnum());
    }
    
    @Test
    public void testIsEnum5() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(1).isEnum());
    }
    
    @Test
    public void testIsEnum6() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(2).isEnum());
    }
    
    @Test
    public void testIsEnum7() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isEnum());
    }
    
    @Test
    public void testIsEnum8() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( int )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isEnum());
    }
    
    @Test
    public void testIsEnum9() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isEnum());
    }
    
    @Test
    public void testIsEnum10() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(0).isEnum());
    }
    
    @Test
    public void testIsEnum11() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(1).isEnum());
    }
    
    @Test
    public void testIsSuper1() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isSuper());
    }
    
    @Test
    public void testIsSuper2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(1).isSuper());
    }
    
    @Test
    public void testIsSuper3() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(2).isSuper());
    }
    
    @Test
    public void testIsSuper4() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isSuper());
    }
    
    @Test
    public void testIsSuper5() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(1).isSuper());
    }
    
    @Test
    public void testIsSuper6() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(2).isSuper());
    }
    
    @Test
    public void testIsSuper7() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isSuper());
    }
    
    @Test
    public void testIsSuper8() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( int )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(0).isSuper());
    }
    
    @Test
    public void testIsSuper9() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(0).isSuper());
    }
    
    @Test
    public void testIsSuper10() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isSuper());
    }
    
    @Test
    public void testIsSuper11() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(1).isSuper());
    }
    
    @Test
    public void testIsLocal1() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(0).isLocal());
    }
    
    @Test
    public void testIsLocal2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(1).isLocal());
    }
    
    @Test
    public void testIsLocal3() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(2).isLocal());
    }
    
    @Test
    public void testIsLocal4() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isLocal());
    }
    
    @Test
    public void testIsLocal5() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(1).isLocal());
    }
    
    @Test
    public void testIsLocal6() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(2).isLocal());
    }
    
    @Test
    public void testIsLocal7() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(0).isLocal());
    }
    
    @Test
    public void testIsLocal8() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( int )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isLocal());
    }
    
    @Test
    public void testIsLocal9() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isLocal());
    }
    
    @Test
    public void testIsLocal10() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).isLocal());
    }
    
    @Test
    public void testIsLocal11() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(1).isLocal());
    }
    
    @Test
    public void testArguments1() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("10", TestUtil.asStr(result.get(0).getArguments()));
    }
    
    @Test
    public void testArguments2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("y", TestUtil.asStr(result.get(1).getArguments()));
    }
    
    @Test
    public void testGetArguments3() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("z", TestUtil.asStr(result.get(2).getArguments()));
    }
    
    @Test
    public void testGetArguments4() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("", TestUtil.asStr(result.get(0).getArguments()));
    }
    
    @Test
    public void testGetArguments5() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("1", TestUtil.asStr(result.get(1).getArguments()));
    }
    
    @Test
    public void testGetArguments6() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("", TestUtil.asStr(result.get(2).getArguments()));
    }
    
    @Test
    public void testGetArguments7() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("100", TestUtil.asStr(result.get(0).getArguments()));
    }
    
    @Test
    public void testGetArguments8() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( int )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("x", TestUtil.asStr(result.get(0).getArguments()));
    }
    
    @Test
    public void testGetArguments9() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("", TestUtil.asStr(result.get(0).getArguments()));
    }
    
    @Test
    public void testGetArguments10() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("", TestUtil.asStr(result.get(0).getArguments()));
    }
    
    @Test
    public void testGetArguments11() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("", TestUtil.asStr(result.get(1).getArguments()));
    }
    
    @Test
    public void testGetArgumentSize1() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("inc", result.get(0).getName());
    }
    
    @Test
    public void testGetArgumentSize2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("inc", result.get(1).getName());
    }
    
    @Test
    public void testGetArgumentSize3() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("println", result.get(2).getName());
    }
    
    @Test
    public void testGetArgumentSize4() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("A119", result.get(0).getName());
    }
    
    @Test
    public void testGetArgumentSize5() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals(1, result.get(1).getArgumentSize());
    }
    
    @Test
    public void testGetArgumentSize6() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals(0, result.get(2).getArgumentSize());
    }
    
    @Test
    public void testGetArgumentSize7() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals(1, result.get(0).getArgumentSize());
    }
    
    @Test
    public void testGetArgumentSize8() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( int )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals(1, result.get(0).getArgumentSize());
    }
    
    @Test
    public void testGetArgumentSize9() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals(0, result.get(0).getArgumentSize());
    }
    
    @Test
    public void testGetArgumentSize10() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals(0, result.get(0).getArgumentSize());
    }
    
    @Test
    public void testGetArgumentSize11() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals(0, result.get(1).getArgumentSize());
    }
    
    @Test
    public void testArgument1() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("10", result.get(0).getArgument(0).toString());
    }
    
    @Test
    public void testArgument2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("y", result.get(1).getArgument(0).toString());
    }
    
    @Test
    public void testArgument3() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("z", result.get(2).getArgument(0).toString());
    }
    
    @Test
    public void testArgument4() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertNull(result.get(0).getArgument(0));
    }
    
    @Test
    public void testArgument5() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("1", result.get(1).getArgument(0).toString());
    }
    
    @Test
    public void testArgument6() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertNull(result.get(2).getArgument(0));
    }
    
    @Test
    public void testArgument7() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("100", result.get(0).getArgument(0).toString());
    }
    
    @Test
    public void testArgument8() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( int )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("x", result.get(0).getArgument(0).toString());
    }
    
    @Test
    public void testArgument9() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertNull(result.get(0).getArgument(0));
    }
    
    @Test
    public void testArgument10() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertNull(result.get(0).getArgument(0));
    }
    
    @Test
    public void testArgument11() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertNull(result.get(1).getArgument(0));
    }
    
    @Test
    public void testHasReceiver1() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(0).hasReceiver());
    }
    
    @Test
    public void testHasReceiver2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(1).hasReceiver());
    }
    
    @Test
    public void testHasReceiver3() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(2).hasReceiver());
    }
    
    @Test
    public void testHasReceiver4() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(0).hasReceiver());
    }
    
    @Test
    public void testHasReceiver5() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(1).hasReceiver());
    }
    
    @Test
    public void testHasReceiver6() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(2).hasReceiver());
    }
    
    @Test
    public void testHasReceiver7() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).hasReceiver());
    }
    
    @Test
    public void testHasReceiver8() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( int )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).hasReceiver());
    }
    
    @Test
    public void testHasReceiver9() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(0).hasReceiver());
    }
    
    @Test
    public void testHasReceiver10() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(0).hasReceiver());
    }
    
    @Test
    public void testHasReceiver11() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertTrue(result.get(1).hasReceiver());
    }
    
    @Test
    public void testGetReceiver1() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("this", result.get(0).getReceiver().getName());
    }
    
    @Test
    public void testGetReceiver2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("this", result.get(1).getReceiver().getName());
    }
    
    @Test
    public void testGetReceiver3() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("System.out", result.get(2).getReceiver().getName());
    }
    
    @Test
    public void testGetReceiver4() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("a$0", result.get(0).getReceiver().getName());
    }
    
    @Test
    public void testGetReceiver5() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("a$0", result.get(1).getReceiver().getName());
    }
    
    @Test
    public void testGetReceiver6() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("a$0", result.get(2).getReceiver().getName());
    }
    
    @Test
    public void testGetReceiver7() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertNull(result.get(0).getReceiver());
    }
    
    @Test
    public void testGetReceiver8() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( int )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertNull(result.get(0).getReceiver());
    }
    
    @Test
    public void testGetReceiver9() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("super", result.get(0).getReceiver().getName());
    }
    
    @Test
    public void testGetReceiver10() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("PriceCode.REGULAR", result.get(0).getReceiver().getName());
    }
    
    @Test
    public void testGetReceiver11() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("PriceCode.REGULAR", result.get(1).getReceiver().getName());
    }
    
    @Test
    public void testGetApproximatedTypeNames1() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("Test102", TestUtil.asSortedStr(result.get(0).getApproximatedTypeNames()));
    }
    
    @Test
    public void testGetApproximatedTypeNames2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("Test102", TestUtil.asSortedStr(result.get(1).getApproximatedTypeNames()));
    }
    
    @Test
    public void testGetApproximatedTypeNames3() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("java.io.PrintStream", TestUtil.asSortedStr(result.get(2).getApproximatedTypeNames()));
    }
    
    @Test
    public void testGetApproximatedTypeNames4() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("A119", TestUtil.asSortedStr(result.get(0).getApproximatedTypeNames()));
    }
    
    @Test
    public void testGetApproximatedTypeNames5() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("A119", TestUtil.asSortedStr(result.get(1).getApproximatedTypeNames()));
    }
    
    @Test
    public void testGetApproximatedTypeNames6() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("A119", TestUtil.asSortedStr(result.get(2).getApproximatedTypeNames()));
    }
    
    @Test
    public void testGetApproximatedTypeNames7() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("S140", TestUtil.asSortedStr(result.get(0).getApproximatedTypeNames()));
    }
    
    @Test
    public void testGetApproximatedTypeNames8() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( int )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("Test140", TestUtil.asSortedStr(result.get(0).getApproximatedTypeNames()));
    }
    
    @Test
    public void testGetApproximatedTypeNames9() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("Test140", TestUtil.asSortedStr(result.get(0).getApproximatedTypeNames()));
    }
    
    @Test
    public void testGetApproximatedTypeNames10() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("PriceCode", TestUtil.asSortedStr(result.get(0).getApproximatedTypeNames()));
    }
    
    @Test
    public void testGetApproximatedTypeNames11() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("PriceCode", TestUtil.asSortedStr(result.get(1).getApproximatedTypeNames()));
    }
    
    @Test
    public void testCallSelfDirectly() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).callSelfDirectly());
    }
    
    @Test
    public void testCallSelfDirectly2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(1).callSelfDirectly());
    }
    
    @Test
    public void testCallSelfDirectly3() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test102", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(2).callSelfDirectly());
    }
    
    @Test
    public void testCallSelfDirectly4() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).callSelfDirectly());
    }
    
    @Test
    public void testCallSelfDirectly5() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(1).callSelfDirectly());
    }
    
    @Test
    public void testCallSelfDirectly6() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test119", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(2).callSelfDirectly());
    }
    
    @Test
    public void testCallSelfDirectly7() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).callSelfDirectly());
    }
    
    @Test
    public void testCallSelfDirectly8() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "S140( int )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).callSelfDirectly());
    }
    
    @Test
    public void testCallSelfDirectly9() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("S140", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).callSelfDirectly());
    }
    
    @Test
    public void testCallSelfDirectly10() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(0).callSelfDirectly());
    }
    
    @Test
    public void testCallSelfDirectly11() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test139", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertFalse(result.get(1).callSelfDirectly());
    }
    
    @Test
    public void testGetExceptionTypes1() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test122", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("java.lang.Exception", TestUtil.asSortedStrOfTypeBinding(result.get(0).getExceptionTypes()));
    }
    
    @Test
    public void testGetExceptionTypes2() {
        CFG cfg = CFGTestUtil.createCFGFromSliceProject("Test123", "m( )");
        List<JMethodReference> result = CFGTestUtil.getMethodReference(cfg);
        
        assertEquals("SubException", TestUtil.asSortedStrOfTypeBinding(result.get(0).getExceptionTypes()));
    }
}
