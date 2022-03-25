/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.CFGTestUtil;
import org.jtool.srcmodel.JavaProject;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CFGReceiverTest {
    
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
    public void testGetNameTest102_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("System.out", result);
    }
    
    @Test
    public void testGetNameTest119_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a$0", result);
    }
    
    @Test
    public void testGetNameTest119_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a$0", result);
    }
    
    @Test
    public void testGetNameTest119_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 13);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a$0", result);
    }
    
    @Test
    public void testGetNameTest119_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a$0", result);
    }
    
    @Test
    public void testGetNameTest119_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 22);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a$0", result);
    }
    
    @Test
    public void testGetNameTest119_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 27);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this", result);
    }
    
    @Test
    public void testGetNameTest124_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test124", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a$2", result);
    }
    
    @Test
    public void testGetNameTest124_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test124", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 10);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a$2", result);
    }
    
    @Test
    public void testGetNameTest124_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test124", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 14);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a$2", result);
    }
    
    @Test
    public void testGetNameTest126_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a$0", result);
    }
    
    @Test
    public void testGetNameTest126_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a$0", result);
    }
    
    @Test
    public void testGetNameTest126_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 15);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a2$2", result);
    }
    
    @Test
    public void testGetNameTest126_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 20);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a$0", result);
    }
    
    @Test
    public void testGetNameTest126_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 23);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("$105", result);
    }
    
    @Test
    public void testGetNameTest126_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 27);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a3$5", result);
    }
    
    @Test
    public void testGetNameTest126_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 31);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a3$5", result);
    }
    
    @Test
    public void testGetNameTest126_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 36);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a3$5", result);
    }
    
    @Test
    public void testGetNameTest128_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a$0", result);
    }
    
    @Test
    public void testGetNameTest128_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a$0", result);
    }
    
    @Test
    public void testGetNameTest128_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 13);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this", result);
    }
    
    @Test
    public void testGetNameTest128_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 18);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("a$0", result);
    }
    
    @Test
    public void testGetNameTest128_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 21);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("$106", result);
    }
    
    @Test
    public void testGetNameTest130_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this", result);
    }
    
    @Test
    public void testGetNameTest130_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "n( int )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("$102", result);
    }
    
    @Test
    public void testGetNameTest131_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.map", result);
    }
    
    @Test
    public void testGetNameTest131_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.map", result);
    }
    
    @Test
    public void testGetNameTest132_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this", result);
    }
    
    @Test
    public void testGetNameTest133_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.x", result);
    }
    
    @Test
    public void testGetNameTest133_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this", result);
    }
    
    @Test
    public void testGetNameTest133_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 14);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this", result);
    }
    
    @Test
    public void testGetNameTest133_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 16);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("$102", result);
    }
    
    @Test
    public void testGetNameTest133_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 20);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.x.y", result);
    }
    
    @Test
    public void testGetNameTest133_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 25);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this", result);
    }
    
    @Test
    public void testGetNameTest133_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 27);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("$105", result);
    }
    
    @Test
    public void testGetNameTest133_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 33);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this", result);
    }
    
    @Test
    public void testGetNameTest133_9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 35);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("$107", result);
    }
    
    @Test
    public void testGetNameTest133_10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 37);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("$108", result);
    }
    
    @Test
    public void testGetNameTest133_11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 42);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this", result);
    }
    
    @Test
    public void testGetNameTest133_12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 44);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("$110.y", result);
    }
    
    @Test
    public void testGetNameTest133_13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 49);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.x", result);
    }
    
    @Test
    public void testGetNameTest133_14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 51);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("$112", result);
    }
    
    @Test
    public void testGetNameTest134_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test134", "m3( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("p$1", result);
    }
    
    @Test
    public void testGetNameTest134_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test134", "m3( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("p$1", result);
    }
    
    @Test
    public void testGetNameTest134_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test134", "m3( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 13);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("p$1", result);
    }
    
    @Test
    public void testGetNameTest135_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.s1", result);
    }
    
    @Test
    public void testGetNameTest135_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 10);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.s2", result);
    }
    
    @Test
    public void testGetNameTest135_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.s1", result);
    }
    
    @Test
    public void testGetNameTest135_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 22);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.s2", result);
    }
    
    @Test
    public void testGetNameTest136_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.s1", result);
    }
    
    @Test
    public void testGetNameTest136_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 10);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.s2", result);
    }
    
    @Test
    public void testGetNameTest136_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.s1", result);
    }
    
    @Test
    public void testGetNameTest136_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 22);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this.s2", result);
    }
    
    @Test
    public void testGetNameTest139_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("PriceCode.REGULAR", result);
    }
    
    @Test
    public void testGetNameTest139_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("PriceCode.REGULAR", result);
    }
    
    @Test
    public void testGetUseVariablesTest140_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "S140( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        CFGReceiver rec = node.getReceiver();
        String result = rec.getName();
        
        assertEquals("this", result);
    }
    
    @Test
    public void testGetUseVariablesTest140_2() {
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
    public void testExplicitTypeTest134_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test134", "m3( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        CFGReceiver rec = node.getReceiver();
        String result = rec.explicitType();
        
        assertNull(result);
    }
    
    @Test
    public void testExplicitTypeTest134_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test134", "m3( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 13);
        CFGReceiver rec = node.getReceiver();
        String result = rec.explicitType();
        
        assertEquals("Q134", result);
    }
}
