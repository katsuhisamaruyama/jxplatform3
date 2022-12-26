/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.cfg.internal.CFGTestUtil;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.srcmodel.JavaProject;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CFGReceiverTest {
    
    private static JavaProject SliceProject;
    
    @BeforeClass
    public static void setUp() {
        SliceProject = BuilderTestUtil.getProject("Slice");
    }
    
    @Test
    public void testGetName_Test102_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("System.out", result);
    }
    
    @Test
    public void testGetName_Test119_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("A119", result);
    }
    
    @Test
    public void testGetName_Test119_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a$0", result);
    }
    
    @Test
    public void testGetName_Test119_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 13);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a$0", result);
    }
    
    @Test
    public void testGetName_Test119_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a$0", result);
    }
    
    @Test
    public void testGetName_Test119_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 22);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a$0", result);
    }
    
    @Test
    public void testGetName_Test119_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 27);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this", result);
    }
    
    @Test
    public void testGetName_Test124_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test124", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("A124", result);
    }
    
    @Test
    public void testGetName_Test124_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test124", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 10);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a$2", result);
    }
    
    @Test
    public void testGetName_Test124_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test124", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 14);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a$2", result);
    }
    
    @Test
    public void testGetName_Test126_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("A126", result);
    }
    
    @Test
    public void testGetName_Test126_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a$0", result);
    }
    
    @Test
    public void testGetName_Test126_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 15);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a2$2", result);
    }
    
    @Test
    public void testGetName_Test126_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 20);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a$0", result);
    }
    
    @Test
    public void testGetName_Test126_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 23);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a$0.!add( int )", result);
    }
    
    @Test
    public void testGetName_Test126_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 27);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("A126", result);
    }
    
    @Test
    public void testGetName_Test126_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 31);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a3$5", result);
    }
    
    @Test
    public void testGetName_Test126_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 36);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a3$5", result);
    }
    
    @Test
    public void testGetName_Test128_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("A128", result);
    }
    
    @Test
    public void testGetName_Test128_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a$0", result);
    }
    
    @Test
    public void testGetName_Test128_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 14);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a$0", result);
    }
    
    @Test
    public void testGetName_Test128_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 19);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a$0", result);
    }
    
    @Test
    public void testGetName_Test128_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 22);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a$0.!add( int )", result);
    }
    
    @Test
    public void testGetName_Test130_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this", result);
    }
    
    @Test
    public void testGetName_Test130_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "n( int )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("S130", result);
    }
    
    @Test
    public void testGetName_Test131_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.map", result);
    }
    
    @Test
    public void testGetName_Test131_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.map", result);
    }
    
    @Test
    public void testGetName_Test132_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this", result);
    }
    
    @Test
    public void testGetName_Test133_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.x", result);
    }
    
    @Test
    public void testGetName_Test133_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this", result);
    }
    
    @Test
    public void testGetName_Test133_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 14);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this", result);
    }
    
    @Test
    public void testGetName_Test133_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 16);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.!getX( )", result);
    }
    
    @Test
    public void testGetName_Test133_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 20);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.x.y", result);
    }
    
    @Test
    public void testGetName_Test133_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 25);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this", result);
    }
    
    @Test
    public void testGetName_Test133_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 27);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.!getX( )", result);
    }
    
    @Test
    public void testGetName_Test133_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 33);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this", result);
    }
    
    @Test
    public void testGetName_Test133_9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 35);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.!getX( )", result);
    }
    
    @Test
    public void testGetName_Test133_10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 37);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.!getX( ).!getY( )", result);
    }
    
    @Test
    public void testGetName_Test133_11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 42);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this", result);
    }
    
    @Test
    public void testGetName_Test133_12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 44);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.!getX( ).y", result);
    }
    
    @Test
    public void testGetName_Test133_13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 49);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.x", result);
    }
    
    @Test
    public void testGetName_Test133_14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 51);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.x.!getY( )", result);
    }
    
    @Test
    public void testGetName_Test134_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test134", "m3( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("R134", result);
    }
    
    @Test
    public void testGetName_Test134_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test134", "m3( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("p$1", result);
    }
    
    @Test
    public void testGetName_Test134_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test134", "m3( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 13);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("p$1", result);
    }
    
    @Test
    public void testGetName_Test135_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.s1", result);
    }
    
    @Test
    public void testGetName_Test135_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 10);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.s2", result);
    }
    
    @Test
    public void testGetName_Test135_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.s1", result);
    }
    
    @Test
    public void testGetName_Test135_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 22);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.s2", result);
    }
    
    @Test
    public void testGetName_Test136_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.s1", result);
    }
    
    @Test
    public void testGetName_Test136_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 10);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.s2", result);
    }
    
    @Test
    public void testGetName_Test136_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.s1", result);
    }
    
    @Test
    public void testGetName_Test136_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 22);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.s2", result);
    }
    
    @Test
    public void testGetName_Test139_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("PriceCode.REGULAR", result);
    }
    
    @Test
    public void testGetName_Test139_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("PriceCode.REGULAR", result);
    }
    
    @Test
    public void testGetUseVariables_Test140_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "S140( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this", result);
    }
    
    @Test
    public void testGetUseVariables_Test140_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "S140( int )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("super", result);
    }
    
    @Test
    public void testExplicitTypeTest134_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test134", "m3( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        CFGReceiver rec = node.getReceiver();
        String result = rec.explicitType();
        
        assertNull(result);
    }
    
    @Test
    public void testExplicitType_Test134_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test134", "m3( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        CFGReceiver rec = node.getReceiver();
        String result = rec.explicitType();
        
        assertNull(result);
    }
    
    @Test
    public void testExplicitType_Test134_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test134", "m3( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 13);
        CFGReceiver rec = node.getReceiver();
        String result = rec.explicitType();
        
        assertEquals("Q134", result);
    }
}
