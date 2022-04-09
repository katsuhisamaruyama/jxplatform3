/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.CFGTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import org.jtool.srcmodel.JavaProject;
import java.util.List;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;

public class CFGStatementTest {
    
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
    public void testGetDefVariablesTest101_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("x$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest101_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("y$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest101_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("z$2", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest102_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("x$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest102_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest102_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest102_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest102_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!inc", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest102_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "inc( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("n$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest102_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "inc( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("$_", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest102_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "inc( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest103_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest103_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("p$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest103_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest103_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 15);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("r$2", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest103_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 17);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest103_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 18);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!getA", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest103_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("a$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest103_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest105_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test105", "setA( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest105_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test105", "a");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest107_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test107", "m( int[] )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("a$0;i$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest107_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test107", "m( int[] )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("a$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest107_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test107", "m( int[] )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("y$3", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest108_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("y$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest110_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test110", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest111_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test110", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("x$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest111_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test111", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest111_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test111", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest115_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test115", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("i$3", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest115_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test115", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest115_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test115", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 8);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("i$3", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest119_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest119_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 8);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("a$0.x", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest119_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 12);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest119_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 13);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest120_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 8);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest120_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 9);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest125_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("r$2", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest125_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 10);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest125_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 13);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest125_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 14);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!inc1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest125_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 15);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!inc2", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest126_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 17);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("r$4", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest126_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 18);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest126_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 19);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest126_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 20);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest126_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 21);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest126_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 22);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("a$0.!add", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest127_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test127", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 15);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("q$2", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest128_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 11);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("r$2", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest128_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 14);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest128_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 15);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest128_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 16);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest128_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 17);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest128_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 18);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest128_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 19);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest128_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 20);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("a$0.!add", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest128_9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 21);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest128_10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 22);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("a$0.!add.!getY", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest128_11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 23);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!n", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest130_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "n( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("x$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest130_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "n( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("$_", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest130_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "n( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("S130.x", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest130_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "n( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest130_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "n( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("S130.!S130", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest131_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.map$Node.next;this.map.modCount;this.map$Node.value;this.map.size;this.map",
                TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest131_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 8);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest132_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("y$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest132_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!n", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("p4$3", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 9);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 11);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("p5$4", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 12);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 14);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 15);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!getX", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 16);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 17);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!getX.!getA", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 18);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("p6$5", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 19);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 20);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 21);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.x.y.!getA", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 22);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("p7$6", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 23);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_15() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 25);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_16() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 26);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!getX", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_17() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 27);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_18() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 28);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!getX.!getY", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_19() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 29);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("p8$7", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_20() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 30);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_21() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 31);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_22() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 33);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_23() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 34);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!getX", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_24() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 35);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_25() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 36);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!getX.!getY", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_26() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 37);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_27() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 38);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!getX.!getY.!getA", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_28() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 39);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("p9$8", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_29() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 40);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_30() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 42);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_31() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 43);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!getX", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_32() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 44);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_33() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 45);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!getX.y.!getA", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_34() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 46);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("p10$9", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_35() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 47);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_36() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 48);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_37() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 49);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_38() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 50);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.x.!getY", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_39() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 51);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest133_40() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 52);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.x.!getY.!getA", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest135_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest135_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.s1.key;this.s1.value", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest135_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest135_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest135_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.s1.!set2", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest135_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 15);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("v1$2", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest135_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 16);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest135_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 17);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest135_9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 18);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest135_10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 19);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.s1.!get2", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest135_11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.s1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest136_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest136_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest136_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.s1.map;this.s1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest136_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest136_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest136_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest136_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 15);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("v1$2", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest136_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 16);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest136_9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 17);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest136_10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 18);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest136_11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 19);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.s1.!get", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest140_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("xx$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest140_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "S140( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.x", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariablesTest140_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "S140( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("super.x", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest101_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest101_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("x$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest101_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("x$0;this.p", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest102_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!inc", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest102_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest102_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest102_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest102_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest102_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "inc( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest102_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "inc( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("n$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest102_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "inc( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("$_", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest103_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest103_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest103_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest103_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 15);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getA", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest103_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 17);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest103_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 18);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest103_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest103_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this;a$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest105_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test105", "setA( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this;a$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest105_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test105", "a");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest107_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test107", "m( int[] )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("i$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest107_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test107", "m( int[] )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("i$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest107_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test107", "m( int[] )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest108_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("y$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest110_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test110", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("x$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest111_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test110", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("x$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest111_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test111", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("x$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest111_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test111", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest115_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test115", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest115_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test115", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("i$3", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest115_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test115", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 8);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("i$3", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest119_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest119_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 8);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest119_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 12);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest119_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 13);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.x", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest120_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 8);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0;a$0.x", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest120_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 9);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0;a$0.y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest125_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!inc2", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest125_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 10);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!inc1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest125_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 13);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("p$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest125_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 14);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest125_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 15);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest126_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 17);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.!add.!getY;a2$2.!getY", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest126_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 18);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.!add;a2$2", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest126_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 19);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest126_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 20);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest126_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 21);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("p$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest126_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 22);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest127_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test127", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 15);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("A127.z", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest128_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 11);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!n", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest128_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 14);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest128_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 15);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.!add.!getY", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest128_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 16);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.!add", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest128_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 17);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest128_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 18);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest128_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 19);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("p$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest128_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 20);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest128_9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 21);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.!add.y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest128_10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 22);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest128_11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 23);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest130_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "n( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    // TODO
    @Test
    public void testGetUseVariablesTest130_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "n( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("S130.!S130", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest130_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "n( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest130_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "n( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("x$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest130_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "n( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest131_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("$java.lang.String;this.map$Node.next;this.map.modCount;"
                   + "this.map.threshold;this.map$Node.hash;this.map$Node.value;"
                   + "this.map.size;this.map$Node.key;this.map.table;this.map",
                TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest131_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 8);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("$java.lang.String;this.map$Node.next;this.map$Node.hash;"
                   + "this.map$Node.value;this.map$Node.key;this.map.table;this.map",
                TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest132_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!n;this.!n.x", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest132_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX;this.!getX.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 9);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.a;this.x.x;this.x.y;this.x", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 11);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX.!getA", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 12);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 14);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.a;this.x.x;this.x.y;this.x", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 15);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 16);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 17);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 18);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.y.!getA", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 19);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x;this.x.y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 20);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.y.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 21);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 22);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX.!getY;this.!getX.!getY.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 23);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_15() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 25);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.a;this.x.x;this.x.y;this.x", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_16() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 26);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_17() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 27);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX.y.a;this.!getX.y.x;this.!getX.y;this.!getX.y.y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_18() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 28);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_19() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 29);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX.!getY.!getA", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_20() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 30);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX.!getY", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_21() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 31);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_22() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 33);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.a;this.x.x;this.x.y;this.x", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_23() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 34);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_24() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 35);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX.y.a;this.!getX.y.x;this.!getX.y;this.!getX.y.y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_25() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 36);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_26() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 37);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX.!getY.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_27() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 38);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_28() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 39);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX.y.!getA", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_29() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 40);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX;this.!getX.y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_30() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 42);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.a;this.x.x;this.x.y;this.x", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_31() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 43);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_32() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 44);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX.y.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_33() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 45);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_34() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 46);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.!getY.!getA", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_35() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 47);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.!getY", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_36() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 48);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_37() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 49);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.y.a;this.x.y.x;this.x.y;this.x.y.y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_38() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 50);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_39() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 51);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.!getY.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest133_40() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 52);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest135_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.s1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest135_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest135_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("$java.lang.String", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest135_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("$java.lang.String", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest135_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest135_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 15);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.s1.!get2", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest135_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 16);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.s1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest135_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 17);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.s1.key;this.s1.value;this.s1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest135_9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 18);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("$java.lang.String", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest135_10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 19);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest135_11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.s1;S135.!S135", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest136_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.s1.!set", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest136_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.s1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest136_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.s1.map;this.s1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest136_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("$java.lang.String", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest136_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("$java.lang.String", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest136_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.s1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest136_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 15);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.s1.!get", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest136_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 16);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.s1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest136_9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 17);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.s1.map;this.s1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest136_10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 18);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("$java.lang.String", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest136_11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 19);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    @Test
    public void testGetUseVariablesTest140_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("super.x", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest140_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "S140( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariablesTest140_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "S140( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariablesInDeclarationTest133_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariablesInDeclarationTest133_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.x.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariablesInDeclarationTest133_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.x.!getA", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariablesInDeclarationTest133_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.!getX.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariablesInDeclarationTest133_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 11);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.!getX.!getA", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariablesInDeclarationTest133_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 18);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.x.y.!getA", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariablesInDeclarationTest133_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 22);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.!getX.!getY.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariablesInDeclarationTest133_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 29);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.!getX.!getY.!getA", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariablesInDeclarationTest133_9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 39);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.!getX.y.!getA", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindçUseVariablesInDeclarationTest133_10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 46);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.x.!getY.!getA", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariablesInAssignmentTest105_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test105", "setA( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("a$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariablesInAssignmentTest115_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test115", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariablesInAssignmentTest126_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 12);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariablesInReceiverTest133_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.x", TestUtil.asStrOfReference(result));
    }
    
    
    // Also should check Return
    /*
    @Test
    public void testFindPrimaryUseVariablesInReceiverTest133_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 8);
        List<JVariableReference> result = node.findPrimaryUseVariablesInReceiver();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariablesInReceiverTest133_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 12);
        List<JVariableReference> result = node.findPrimaryUseVariablesInReceiver();
        
        assertEquals("this.!getX", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariablesInReceiverTest133_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 19);
        List<JVariableReference> result = node.findPrimaryUseVariablesInReceiver();
        
        assertEquals("this.x.y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariablesInReceiverTest133_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 23);
        List<JVariableReference> result = node.findPrimaryUseVariablesInReceiver();
        
        assertEquals("this.!getX", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariablesInReceiverTest133_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 30);
        List<JVariableReference> result = node.findPrimaryUseVariablesInReceiver();
        
        assertEquals("this.!getX.!getY", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariablesInReceiverTest133_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 31);
        List<JVariableReference> result = node.findPrimaryUseVariablesInReceiver();
        
        assertEquals("this.!getX", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariablesInReceiverTest133_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 40);
        List<JVariableReference> result = node.findPrimaryUseVariablesInReceiver();
        
        assertEquals("this.!getX.y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariablesInReceiverTest133_9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 47);
        List<JVariableReference> result = node.findPrimaryUseVariablesInReceiver();
        
        assertEquals("this.x.!getY", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariablesInReceiverTest133_10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 48);
        List<JVariableReference> result = node.findPrimaryUseVariablesInReceiver();
        
        assertEquals("this.x", TestUtil.asStrOfReference(result));
    }
    */
}
