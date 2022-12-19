/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.builder.BuilderTestUtil;
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
    public void testGetApproximatedTypeNames_ForStringLiteralReceiver1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m1( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.String", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForStringLiteralReceiver2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m1( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.Object", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForStringLiteralReceiver3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m1( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 15);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.String", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForTypeLiteralReceiver1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m2( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.Class", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForTypeLiteralReceiver2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m2( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.Object", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForTypeLiteralReceiver3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m2( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 15);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.Class", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForCastReceiver() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m3( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 7);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("P47", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForClassInstanceCreation1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m4( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        assert node.isConstructorCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("P47", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForClassInstanceCreation2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m4( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 7);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("P47", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForStaticMethodCall() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m5( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.Math", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForLocalMethodCall() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m6( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test47", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForConstructorInvocation() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "P31", "P31( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        assert node.isConstructorCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("P31", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForSuperConstructorInvocation() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "P31", "P31( int int )");
        
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        assert node.isConstructorCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test31", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForSuperMethodInvocation() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "P31", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test31", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForEnumConstantCall1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "PriceCode", "REGULAR");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 2);
        assert node.isConstructorCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("PriceCode", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForEnumConstantCall2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m7( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Direction", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForEnumConstantCall3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m7( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.Enum", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForFieldReceiver1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m1( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 7);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForFieldReceiver2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m1( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 11);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForFieldReceiver3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m1( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 15);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.String", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForFieldReceiver4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m2( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 7);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("T48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForFieldReceiver5() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m2( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 11);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForFieldReceiver6() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m2( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 15);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("T48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForFieldReceiver7() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m2( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 20);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.Object", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForReturnReceiver1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m3( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 10);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForReturnReceiver2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m3( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForReturnReceiver3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m3( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 24);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.String", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForReturnReceiver4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m4( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 10);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("T48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForReturnReceiver5() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m4( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForReturnReceiver6() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m4( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 24);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("T48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForReturnReceiver7() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m5( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 11);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForReturnReceiver8() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m5( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 15);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForReturnReceiver9() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m14( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 11);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.util.ArrayList", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForReturnReceiver10() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m15( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 7);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.String", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForReturnReceiver11() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m15( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 15);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.Object", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForLocalReceiver1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m6( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 7);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForLocalReceiver2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m6( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 11);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForLocalReceiver3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m6( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 15);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.Object", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForLocalReceiver4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m7( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 7);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("T48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForLocalReceiver5() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m7( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 11);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForLocalReceiver6() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m7( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 15);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("T48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForLocalReceiver7() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m7( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 19);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.Object", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForLocalReceiver8() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m8( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 7);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("T48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForLocalReceiver9() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m8( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 11);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForLocalReceiver10() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m8( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 15);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.Object", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForFormalReceiver1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m9( S48 )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForFormalReceiver2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m10( S48 )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForFormalReceiver3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m11( S48 )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForFormalReceiver4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m12( S48 )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForFormalReceiver5() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m13( S48 )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("T48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForFormalReceiver6() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m13( S48 )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForFormalReceiver7() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m16( S48 )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForFormalReceiver8() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test48", "m16( S48 )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S48", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_ForEnhancedFor1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test50", "m( )");
        
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        assert node.isMethodCall();
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.String", TestUtil.asSortedStr(result));
    }
}
