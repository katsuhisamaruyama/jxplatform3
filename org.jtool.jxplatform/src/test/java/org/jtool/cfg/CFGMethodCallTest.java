/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.CFGTestUtil;
import org.jtool.jxplatform.util.TestUtil;

import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;

public class CFGMethodCallTest {
    
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
    public void testGetArgumentSizeTest102_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest103_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 6);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest103_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 11);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest103_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest103_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 21);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest103_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 25);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest103_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 29);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest103_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 33);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest105_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test105", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest106_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test106", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest119_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest119_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest119_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 13);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest119_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest119_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 22);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest119_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 27);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest120_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest120_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 7);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest120_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 13);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest120_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 19);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest120_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 25);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest121_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test121", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest124_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test124", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest124_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test124", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 10);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest124_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test124", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 14);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest125_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest125_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest125_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 12);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest126_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest126_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest126_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 15);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest126_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 20);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest126_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 23);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest126_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 27);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest126_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 31);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest126_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 36);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest128_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest128_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest128_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 14);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest128_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 19);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest128_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 22);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest130_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest130_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "n( int )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest131_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest131_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest132_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest133_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest133_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest133_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 14);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest133_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 16);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest133_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 20);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest133_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 25);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest133_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 27);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest133_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 33);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest133_9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 35);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest133_10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 37);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest133_11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 42);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest133_12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 44);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest133_13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 49);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest133_14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 51);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest135_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest135_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 10);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest135_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest135_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 22);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest136_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest136_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 10);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest136_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest136_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 22);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest139_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest139_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest140_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "S140( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest140_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "S140( int )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetArgumentSizeTest140_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        List<CFGParameter> result = node.getActualIns();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest102_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.io.PrintStream", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest103_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 6);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test103", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest103_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 11);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test103", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest103_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test103", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest103_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 21);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test103", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest103_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 25);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test103", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest103_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 29);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test103", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest103_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 33);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test103", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest105_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test105", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test105", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest106_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test106", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test106", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest119_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A119", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest119_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A119", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest119_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 13);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A119", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest119_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A119", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest119_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 22);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A119", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest119_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 27);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test119", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest120_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A120", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest120_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 7);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test120", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest120_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 13);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test120", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest120_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 19);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test120", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest120_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 25);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test120", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest121_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test121", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test121", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest124_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test124", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A124", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest124_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test124", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 10);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A124", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest124_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test124", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 14);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A124", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest125_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test125", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest125_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test125", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest125_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 12);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test125", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest126_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A126", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest126_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A126", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest126_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 15);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A126", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest126_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 20);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A126", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest126_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 23);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A126", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest126_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 27);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A126", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest126_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 31);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A126", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest126_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 36);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A126", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest128_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A128", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest128_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A128", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest128_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 14);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A128", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest128_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 19);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A128", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest128_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 22);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("A128", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest130_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test130", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest130_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "n( int )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S130", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest131_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.util.HashMap", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest131_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.util.HashMap", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest132_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test132", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest133_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest133_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest133_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 14);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest133_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 16);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest133_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 20);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest133_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 25);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest133_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 27);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest133_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 33);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest133_9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 35);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest133_10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 37);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest133_11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 42);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest133_12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 44);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest133_13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 49);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest133_14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 51);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test133", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest135_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S135", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest135_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 10);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S135", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest135_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S135", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest135_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 22);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S135", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest136_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S136", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest136_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 10);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S136", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest136_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S136", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest136_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 22);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S136", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest139_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("PriceCode", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest139_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("java.lang.Enum", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest140_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "S140( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("S140", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest140_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "S140( int )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test140", TestUtil.asSortedStrOfString(result));
    }
    
    @Test
    public void testGetApproximatedTypeNamesTest140_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        Set<String> result = node.getApproximatedTypeNames();
        
        assertEquals("Test140", TestUtil.asSortedStrOfString(result));
    }
}
