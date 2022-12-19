/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.cfg.internal.CFGTestUtil;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import org.jtool.srcmodel.JavaProject;
import java.util.List;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CFGParameterTest {
    
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
    public void testGetDefVariable1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 11);
        JReference result = node.getActualIns().get(0).getDefVariable();
        
        assertNull(result);
    }
    
    @Test
    public void testGetDefVariable2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 11);
        JReference result = node.getActualOut().getDefVariable();
        
        assertEquals("this.!setA( int )", result.getReferenceForm());
    }
    
    @Test
    public void testGetDefVariable3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test121", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        JReference result = node.getActualIns().get(0).getDefVariable();
        
        assertNull(result);
    }
    
    @Test
    public void testGetDefVariable4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test121", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        JReference result = node.getActualIns().get(1).getDefVariable();
        
        assertNull(result);
    }
    
    @Test
    public void testGetDefVariable5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test121", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        JReference result = node.getActualOut().getDefVariable();
        
        assertEquals("this.!m( int int )", result.getReferenceForm());
    }
    
    @Test
    public void testGetIndex1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 11);
        int result = node.getActualIns().get(0).getIndex();
        
        assertEquals(0, result);
    }
    
    @Test
    public void testGetIndex2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 11);
        int result = node.getActualOut().getIndex();
        
        assertEquals(0, result);
    }
    
    @Test
    public void testGetIndex3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test121", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        int result = node.getActualIns().get(0).getIndex();
        
        assertEquals(0, result);
    }
    
    @Test
    public void testGetIndex4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test121", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        int result = node.getActualIns().get(1).getIndex();
        
        assertEquals(1, result);
    }
    
    @Test
    public void testGetIndex5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test121", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        int result = node.getActualOut().getIndex();
        
        assertEquals(0, result);
    }
    
    @Test
    public void testGetParent1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 11);
        CFGNode result = node.getActualIns().get(0).getParent();
        
        assertEquals(node, result);
    }
    
    @Test
    public void testGetParent2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 11);
        CFGNode result = node.getActualOut().getParent();
        
        assertEquals(node, result);
    }
    
    @Test
    public void testGetParent3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test121", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        CFGNode result = node.getActualIns().get(0).getParent();
        
        assertEquals(node, result);
    }
    
    @Test
    public void testGetParent4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test121", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        CFGNode result = node.getActualIns().get(1).getParent();
        
        assertEquals(node, result);
    }
    
    @Test
    public void testGetParent5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test121", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        CFGNode result = node.getActualOut().getParent();
        
        assertEquals(node, result);
    }
    
    @Test
    public void testGetUseVariables_Test103_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 11);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test105_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test105", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test106_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test106", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test119_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test119_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test119_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test120_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 7);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("a$0;a$0.y;a$0.x;A120.!A120( ).y;A120.!A120( ).x",
                TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test120_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 7);
        List<JVariableReference> result = node.getActualIns().get(1).getUseVariables();
        
        assertEquals("a$0;a$0.x;a$0.y;A120.!A120( ).x;A120.!A120( ).y",
                TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test120_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 13);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("a$0;a$0.y;a$0.x;A120.!A120( ).y;A120.!A120( ).x",
                TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test120_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 13);
        List<JVariableReference> result = node.getActualIns().get(1).getUseVariables();
        
        assertEquals("a$0;a$0.x;a$0.y;A120.!A120( ).x;A120.!A120( ).y",
                TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test120_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 19);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("a$0;a$0.y;a$0.x;A120.!A120( ).y;A120.!A120( ).x",
                TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test120_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 19);
        List<JVariableReference> result = node.getActualIns().get(1).getUseVariables();
        
        assertEquals("a$0;a$0.x;a$0.y;A120.!A120( ).x;A120.!A120( ).y",
                TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test120_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 25);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("a$0;a$0.y;a$0.x;A120.!A120( ).y;A120.!A120( ).x",
                TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test120_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 25);
        List<JVariableReference> result = node.getActualIns().get(1).getUseVariables();
        
        assertEquals("a$0;a$0.x;a$0.y;A120.!A120( ).x;A120.!A120( ).y",
                TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test121_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test121", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("a$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test121_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test121", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getActualIns().get(1).getUseVariables();
        
        assertEquals("b$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test124_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test124", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("p$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test124_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test124", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 14);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("q$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test125_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("p$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test125_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("this.!inc1( int )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test125_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 12);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("p$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test126_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("p$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test126_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 20);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("p$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test126_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 31);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test128_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("q$2", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test128_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 14);
        
        assertEquals(0, node.getActualIns().size());
    }
    
    @Test
    public void testGetUseVariables_Test128_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 19);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("p$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test128_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 22);
        
        assertEquals(0, node.getActualIns().size());
    }
    
    @Test
    public void testGetUseVariables_Test130_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test130_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "n( int )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("x$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test135_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("$java.lang.String", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test135_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getActualIns().get(1).getUseVariables();
        
        assertEquals("$java.lang.String", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test135_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 10);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("$java.lang.String", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test135_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 10);
        List<JVariableReference> result = node.getActualIns().get(1).getUseVariables();
        
        assertEquals("$java.lang.String", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test135_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("$java.lang.String", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test135_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 22);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("$java.lang.String", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test136_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("$java.lang.String", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test136_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getActualIns().get(1).getUseVariables();
        
        assertEquals("$java.lang.String", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test136_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 10);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("$java.lang.String", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test136_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 10);
        List<JVariableReference> result = node.getActualIns().get(1).getUseVariables();
        
        assertEquals("$java.lang.String", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test136_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 17);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("$java.lang.String", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test136_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 22);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("$java.lang.String", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test140_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "S140( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test140_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "S140( int )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getActualIns().get(0).getUseVariables();
        
        assertEquals("x$0", TestUtil.asStrOfReference(result));
    }
}
