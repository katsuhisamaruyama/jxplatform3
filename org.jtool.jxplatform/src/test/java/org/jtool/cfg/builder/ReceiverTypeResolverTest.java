/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.builder;

import org.jtool.srcmodel.JavaProject;

import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.CFGTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGMethodCall;
import java.util.Set;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;

public class ReceiverTypeResolverTest {
    
    private static JavaProject SimpleProject;
    
    @BeforeClass
    public static void setUp() {
        SimpleProject = BuilderTestUtil.createProject("Simple", "", "");
    }
    
    @AfterClass
    public static void tearDown() {
        SimpleProject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testgetApproximatedTypeNamesForStringLiteralReceiver1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m1( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.String", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForStringLiteralReceiver2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m1( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.Object", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForStringLiteralReceiver3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m1( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 15);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.String", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForTypeLiteralReceiver1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m2( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.Class", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForTypeLiteralReceiver2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m2( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.Object", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForTypeLiteralReceiver3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m2( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 15);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.Class", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForCastReceiver() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m3( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 7);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("P47", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForClassInstanceCreation1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m4( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        assert node.isConstructorCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("P47", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForClassInstanceCreation2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m4( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 7);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("P47", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForStaticMethodCall() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m5( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.Math", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForLocalMethodCall() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m6( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test47", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForConstructorInvocation() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "P31", "P31( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        assert node.isConstructorCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("P31", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForSuperConstructorInvocation() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "P31", "P31( int )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        assert node.isConstructorCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test31", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForSuperMethodInvocation() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "P31", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test31", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForEnumConstantCall1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "PriceCode", "REGULAR");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 2);
        assert node.isConstructorCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("PriceCode", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForEnumConstantCall2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m7( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Direction", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForEnumConstantCall3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m7( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.Enum", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForFieldReceiver1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m1( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 7);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForFieldReceiver2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m1( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 11);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForFieldReceiver3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m1( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 15);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.String", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForFieldReceiver4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m2( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 7);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("T48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForFieldReceiver5() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m2( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 11);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForFieldReceiver6() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m2( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 15);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("T48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForFieldReceiver7() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m2( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 20);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.Object", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForReturnReceiver1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m3( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 10);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForReturnReceiver2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m3( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForReturnReceiver3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m3( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 24);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.String", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForReturnReceiver4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m4( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 10);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("T48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForReturnReceiver5() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m4( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForReturnReceiver6() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m4( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 24);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("T48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForReturnReceiver7() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m5( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 11);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForReturnReceiver8() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m5( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 15);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForReturnReceiver9() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m14( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 11);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.util.ArrayList", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForReturnReceiver10() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m15( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 7);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.String", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForReturnReceiver11() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m15( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 15);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.Object", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForLocalReceiver1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m6( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 7);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForLocalReceiver2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m6( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 11);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForLocalReceiver3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m6( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 15);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.Object", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForLocalReceiver4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m7( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 7);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("T48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForLocalReceiver5() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m7( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 11);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForLocalReceiver6() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m7( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 15);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("T48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForLocalReceiver7() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m7( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 19);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.Object", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForLocalReceiver8() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m8( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 7);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("T48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForLocalReceiver9() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m8( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 11);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForLocalReceiver10() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m8( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 15);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.Object", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForFormalReceiver1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m9( S48 )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForFormalReceiver2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m10( S48 )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForFormalReceiver3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m11( S48 )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForFormalReceiver4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m12( S48 )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForFormalReceiver5() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m13( S48 )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("T48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForFormalReceiver6() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m13( S48 )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForFormalReceiver7() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m16( S48 )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testgetApproximatedTypeNamesForFormalReceiver8() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m16( S48 )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
}
