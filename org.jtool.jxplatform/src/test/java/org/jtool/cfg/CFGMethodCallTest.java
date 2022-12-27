/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import static org.jtool.cfg.AllCFGTests.SliceProject;
import org.jtool.cfg.internal.CFGTestUtil;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;

public class CFGMethodCallTest {
    
    @BeforeClass
    public static void setUp() {
        SliceProject = BuilderTestUtil.getProject("Slice");
    }
    
    @Test
    public void testGetArgumentSize_Test102_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test103_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 6);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test103_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 11);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test103_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test103_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 21);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test103_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 25);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test103_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 29);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test103_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 33);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test105_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test105", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test106_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test106", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test119_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test119_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test119_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 13);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test119_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test119_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 22);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test119_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 27);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test120_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test120_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 7);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test120_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 13);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test120_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 19);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test120_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 25);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test121_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test121", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test124_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test124", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test124_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test124", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 10);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test124_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test124", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 14);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test125_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test125_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test125_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 12);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test126_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test126_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test126_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 15);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test126_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 20);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test126_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 23);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test126_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 27);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test126_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 31);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test126_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 36);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test128_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test128_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test128_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 14);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test128_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 19);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test128_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 22);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test130_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test130_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "n( int )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test131_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test131_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test132_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test133_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test133_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test133_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 14);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test133_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 16);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test133_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 20);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test133_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 25);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test133_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 27);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test133_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 33);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test133_9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 35);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test133_10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 37);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test133_11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 42);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test133_12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 44);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test133_13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 49);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test133_14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 51);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test135_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test135_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 10);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test135_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test135_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 22);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test136_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test136_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 10);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test136_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test136_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 22);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test139_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test139_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test140_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "S140( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test140_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "S140( int )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSize_Test140_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test102_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.io.PrintStream", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test103_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 6);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test103", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test103_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 11);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test103", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test103_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test103", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test103_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 21);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test103", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test103_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 25);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test103", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test103_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 29);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test103", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test103_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 33);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test103", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test105_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test105", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test105", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test106_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test106", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test106", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test119_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A119", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test119_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A119", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test119_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 13);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A119", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test119_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A119", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test119_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 22);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A119", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test119_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 27);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test119", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test120_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A120", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test120_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 7);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test120", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test120_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 13);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test120", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test120_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 19);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test120", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test120_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 25);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test120", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test121_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test121", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test121", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test124_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test124", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A124", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test124_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test124", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 10);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A124", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test124_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test124", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 14);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A124", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test125_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test125", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test125_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test125", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test125_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 12);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test125", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test126_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A126", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test126_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A126", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test126_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 15);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A126", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test126_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 20);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A126", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test126_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 23);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A126", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test126_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 27);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A126", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test126_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 31);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A126", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test126_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 36);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A126", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test128_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A128", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test128_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A128", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test128_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 14);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A128", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test128_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 19);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A128", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test128_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 22);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A128", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test130_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test130", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test130_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "n( int )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S130", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test131_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.util.HashMap", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test131_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.util.HashMap", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test132_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test132", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test133_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test133_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test133_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 14);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test133_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 16);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test133_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 20);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test133_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 25);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test133_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 27);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test133_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 33);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test133_9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 35);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test133_10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 37);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test133_11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 42);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test133_12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 44);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test133_13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 49);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test133_14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 51);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test135_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S135", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test135_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 10);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S135", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test135_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S135", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test135_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 22);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S135", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test136_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S136", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test136_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 10);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S136", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test136_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S136", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test136_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 22);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S136", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test139_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("PriceCode", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test139_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.Enum", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test140_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "S140( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S140", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test140_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "S140( int )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test140", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNames_Test140_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test140", TestUtil.asSortedStrOfString(result));
    }
}
