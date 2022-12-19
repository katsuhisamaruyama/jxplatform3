/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.cfg.internal.CFGTestUtil;
import org.jtool.jxplatform.util.BuilderTestUtil;
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
    public void testGetDefVariables_Test101_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("x$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test101_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("y$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test101_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("z$2", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test102_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("x$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test102_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test102_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test102_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test102_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!inc( int )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test102_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "inc( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("n$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test102_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "inc( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("$_", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test102_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "inc( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test103_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test103_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("p$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test103_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test103_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 15);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("r$2", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test103_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 17);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test103_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 18);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!getA( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test103_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("a$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test103_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test105_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test105", "setA( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test105_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test105", "a");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test107_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test107", "m( int[] )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("a$0;i$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test107_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test107", "m( int[] )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("a$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test107_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test107", "m( int[] )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("y$3", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test108_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("y$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test110_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test110", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test111_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test110", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("x$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test111_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test111", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test111_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test111", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test115_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test115", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("i$3", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test115_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test115", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test115_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test115", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 8);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("i$3", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test119_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test119_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 8);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test119_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 12);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test119_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 13);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test120_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 8);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test120_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 9);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test125_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("r$2", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test125_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 10);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test125_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 13);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test125_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 14);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!inc1( int )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test125_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 15);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!inc2( int )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test126_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 17);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("r$4", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test126_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 18);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test126_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 19);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test126_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 20);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test126_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 21);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test126_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 22);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("a$0.!add( int )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test127_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test127", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 15);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("q$2", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test128_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 11);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("a$0.!setY( int )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test128_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 14);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test128_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 15);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("a$0.!getY( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test128_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 16);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("s$4", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test128_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 17);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test128_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 18);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test128_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 19);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test128_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 20);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test128_9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 21);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("a$0.!add( int )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test128_10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 22);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test128_11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 23);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("a$0.!add( int ).!getY( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test130_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "n( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("x$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test130_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "n( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("$_", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test130_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "n( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test130_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "n( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test130_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "n( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("S130.!S130( int )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test131_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test131_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 8);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test132_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("y$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test132_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!n( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("p4$3", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 9);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 11);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("p5$4", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 12);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 14);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 15);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!getX( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 16);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 17);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!getX( ).!getA( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 18);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("p6$5", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 19);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 20);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 21);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.x.y.!getA( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 22);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("p7$6", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 23);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_15() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 25);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_16() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 26);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!getX( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_17() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 27);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_18() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 28);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!getX( ).!getY( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_19() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 29);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("p8$7", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_20() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 30);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_21() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 31);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_22() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 33);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_23() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 34);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!getX( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_24() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 35);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_25() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 36);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!getX( ).!getY( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_26() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 37);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_27() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 38);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!getX( ).!getY( ).!getA( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_28() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 39);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("p9$8", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_29() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 40);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_30() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 42);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_31() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 43);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!getX( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_32() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 44);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_33() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 45);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.!getX( ).y.!getA( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_34() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 46);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("p10$9", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_35() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 47);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_36() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 48);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_37() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 49);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_38() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 50);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.x.!getY( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_39() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 51);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test133_40() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 52);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.x.!getY( ).!getA( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test135_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test135_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test135_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test135_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test135_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.s1.!set2( java.lang.String java.lang.String )",
                TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test135_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 15);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("v1$2", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test135_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 16);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test135_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 17);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test135_9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 18);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test135_10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 19);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.s1.!get2( java.lang.String )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test135_11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.s1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test136_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test136_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test136_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test136_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test136_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test136_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test136_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 15);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("v1$2", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test136_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 16);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test136_9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 17);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test136_10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 18);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test136_11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 19);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("this.s1.!get( java.lang.String )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test140_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("super.x", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test140_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "S140( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetDefVariables_Test140_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "S140( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getDefVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test101_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test101_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("x$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test101_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("x$0;this.p", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test102_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!inc( int )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test102_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test102_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test102_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test102_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test102_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "inc( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test102_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "inc( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("n$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test102_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "inc( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("$_", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test103_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test103_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test103_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!setA( int )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test103_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test103_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 17);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test103_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 18);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test103_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test103_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test105_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test105", "setA( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test105_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test105", "a");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test107_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test107", "m( int[] )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("i$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test107_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test107", "m( int[] )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("i$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test107_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test107", "m( int[] )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test108_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("y$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test110_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test110", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("x$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test111_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test111", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("x$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test111_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test111", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("$SwitchDef", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test111_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test111", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test115_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test115", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test115_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test115", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("i$3", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test115_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test115", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 8);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("i$3", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test119_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test119_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.!setX( int )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test119_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 12);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test119_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 13);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.x;A119.!A119( ).x", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test120_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 8);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0;a$0.y;a$0.x;A120.!A120( ).y;A120.!A120( ).x", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test120_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 9);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0;a$0.x;a$0.y;A120.!A120( ).x;A120.!A120( ).y",
                TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test125_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!inc2( int )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test125_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 10);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!inc1( int )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test125_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 13);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("p$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test125_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 14);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test125_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test125", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 15);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test126_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 17);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.!add( int ).!getY( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test126_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 18);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.!add( int )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test126_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 19);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test126_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 20);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.y;ret$1.y;A126.!A126( ).y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test126_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 21);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("p$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test126_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 22);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.y;ret$1.y;A126.!A126( ).y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test127_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test127", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 15);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("A127.z", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test128_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 24);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!n( int int )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test128_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 25);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test128_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 26);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test128_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 27);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test128_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 28);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.!add( int ).!getY( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test128_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 29);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.!add( int )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test128_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 30);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test128_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 31);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.y;ret$1.y;A128.!A128( ).y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test128_9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 32);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("p$1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test128_10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 33);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.y;ret$1.y;A128.!A128( ).y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test128_11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 34);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.!add( int ).y;A128.!A128( ).!add( int ).y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test130_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "n( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test130_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "n( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("S130.!S130( int );S130.!S130( int ).x", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test130_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "n( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test130_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "n( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("x$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test130_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test130", "n( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test131_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("$java.lang.String;this.map.!java.util.HashMap$Node.hash;"
                + "this.map.!java.util.HashMap$Node.key;"
                + "this.map.!java.util.HashMap$Node.next;"
                + "this.map.!java.util.HashMap$Node.value;"
                + "this.map.!java.util.HashMap.modCount;"
                + "this.map.!java.util.HashMap.size;"
                + "this.map.!java.util.HashMap.table;"
                + "this.map.!java.util.HashMap.threshold",
                TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test131_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 8);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("$java.lang.String;"
                + "this.map.!java.util.HashMap$Node.hash;"
                + "this.map.!java.util.HashMap$Node.key;"
                + "this.map.!java.util.HashMap$Node.next;"
                + "this.map.!java.util.HashMap$Node.value;"
                + "this.map.!java.util.HashMap.table",
                TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test132_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!n( );this.!n( ).x", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test132_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("p$0.x", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX( );this.!getX( ).a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 9);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.a;this.x;this.x.x;this.x.y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 11);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX( ).!getA( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 12);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 14);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.a;this.x;this.x.x;this.x.y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 15);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.a;this.x;this.x.x;this.x.y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 16);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX( ).a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 17);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX( ).a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 18);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.y.!getA( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 19);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x;this.x.y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 20);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.y.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 21);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.y.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 22);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX( ).!getY( );this.!getX( ).!getY( ).a",
                TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 23);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_15() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 25);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.a;this.x;this.x.x;this.x.y",
                TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_16() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 26);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.a;this.x;this.x.x;this.x.y",
                TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_17() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 27);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX( ).y.a;this.!getX( ).y.x;this.!getX( ).y;this.!getX( ).y.y",
                TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_18() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 28);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX( ).y.a;this.!getX( ).y.x;this.!getX( ).y;this.!getX( ).y.y",
                TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_19() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 29);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX( ).!getY( ).!getA( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_20() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 30);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX( ).!getY( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_21() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 31);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_22() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 33);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.a;this.x;this.x.x;this.x.y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_23() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 34);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.a;this.x;this.x.x;this.x.y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_24() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 35);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX( ).y.a;this.!getX( ).y.x;this.!getX( ).y;this.!getX( ).y.y",
                TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_25() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 36);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX( ).y.a;this.!getX( ).y.x;this.!getX( ).y;this.!getX( ).y.y",
                TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_26() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 37);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX( ).!getY( ).a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_27() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 38);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX( ).!getY( ).a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_28() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 39);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX( ).y.!getA( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_29() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 40);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX( );this.!getX( ).y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_30() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 42);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.a;this.x;this.x.x;this.x.y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_31() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 43);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.a;this.x;this.x.x;this.x.y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_32() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 44);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX( ).y.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_33() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 45);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.!getX( ).y.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_34() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 46);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.!getY( ).!getA( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_35() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 47);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.!getY( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_36() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 48);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_37() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 49);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.y.a;this.x.y.x;this.x.y;this.x.y.y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_38() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 50);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.y.a;this.x.y.x;this.x.y;this.x.y.y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_39() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 51);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.!getY( ).a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test133_40() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 52);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.x.!getY( ).a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test135_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.s1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test135_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test135_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("$java.lang.String", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test135_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("$java.lang.String", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test135_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test135_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 15);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.s1.!get2( java.lang.String )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test135_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 16);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.s1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test135_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 17);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.s1.key;"
                + "this.s1.value;"
                + "this.s1.key$0.!java.lang.String.COMPACT_STRINGS;"
                + "this.s1.key$0.!java.lang.String.coder;"
                + "this.s1.key$0.!java.lang.String.value",
                TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test135_9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 18);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("$java.lang.String", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test135_10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 19);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.s1.key;"
                + "this.s1.value;"
                + "this.s1.key$0.!java.lang.String.COMPACT_STRINGS;"
                + "this.s1.key$0.!java.lang.String.coder;"
                + "this.s1.key$0.!java.lang.String.value",
                TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test135_11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.s1;S135.!S135( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test136_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.s1.!set( java.lang.String java.lang.String )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test136_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.s1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test136_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.s1.map;"
                + "this.s1.map.!java.util.HashMap$Node.hash;"
                + "this.s1.map.!java.util.HashMap$Node.key;"
                + "this.s1.map.!java.util.HashMap$Node.next;"
                + "this.s1.map.!java.util.HashMap$Node.value;"
                + "this.s1.map.!java.util.HashMap.modCount;"
                + "this.s1.map.!java.util.HashMap.size;"
                + "this.s1.map.!java.util.HashMap.table;"
                + "this.s1.map.!java.util.HashMap.threshold",
                TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test136_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("$java.lang.String", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test136_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("$java.lang.String", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test136_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.s1.map;"
                + "this.s1.map.!java.util.HashMap$Node.hash;"
                + "this.s1.map.!java.util.HashMap$Node.key;"
                + "this.s1.map.!java.util.HashMap$Node.next;"
                + "this.s1.map.!java.util.HashMap$Node.value;"
                + "this.s1.map.!java.util.HashMap.modCount;"
                + "this.s1.map.!java.util.HashMap.size;"
                + "this.s1.map.!java.util.HashMap.table;"
                + "this.s1.map.!java.util.HashMap.threshold",
                TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test136_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 15);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.s1.!get( java.lang.String )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test136_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 16);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.s1", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test136_9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 17);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.s1.map;"
                + "this.s1.map.!java.util.HashMap$Node.hash;"
                + "this.s1.map.!java.util.HashMap$Node.key;"
                + "this.s1.map.!java.util.HashMap$Node.next;"
                + "this.s1.map.!java.util.HashMap$Node.value;"
                + "this.s1.map.!java.util.HashMap.table",
                TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test136_10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 18);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("$java.lang.String", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test136_11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test136", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 19);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.s1.map;"
                + "this.s1.map.!java.util.HashMap$Node.hash;"
                + "this.s1.map.!java.util.HashMap$Node.key;"
                + "this.s1.map.!java.util.HashMap$Node.next;"
                + "this.s1.map.!java.util.HashMap$Node.value;"
                + "this.s1.map.!java.util.HashMap.table",
                TestUtil.asStrOfReference(result));
    }
    @Test
    public void testGetUseVariables_Test140_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test140_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "S140( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("super.x", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testGetUseVariables_Test140_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "S140( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariables_InDeclaration_Test133_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariables_InDeclaration_Test133_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.x.a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariables_InDeclaration_Test133_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.x.!getA( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariables_InDeclaration_Test133_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.!getX( ).a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariables_InDeclaration_Test133_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 11);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.!getX( ).!getA( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariables_InDeclaration_Test133_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 18);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.x.y.!getA( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariables_InDeclaration_Test133_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 22);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.!getX( ).!getY( ).a", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariables_InDeclaration_Test133_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 29);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.!getX( ).!getY( ).!getA( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariables_InDeclaration_Test133_9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 39);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.!getX( ).y.!getA( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariables_InDeclaration_Test133_10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 46);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.x.!getY( ).!getA( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariables_InAssignment_Test105_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test105", "setA( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("a$0", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariables_InAssignment_Test115_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test115", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariables_InAssignment_Test126_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 12);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariables_InReceiver_Test133_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.x", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariables_InReceiver_est133_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 8);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariables_InReceiver_Test133_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 12);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.!getX( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariables_InReceiver_Test133_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 19);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.x.y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariables_InReceiver_Test133_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 23);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.!getX( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariables_InReceiver_Test133_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 30);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.!getX( ).!getY( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariables_InReceiver_Test133_7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 31);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.!getX( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariables_InReceiver_Test133_8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 40);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.!getX( ).y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariables_InReceiver_Test133_9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 47);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.x.!getY( )", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testFindPrimaryUseVariables_InReceiver_Test133_10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test133", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 48);
        List<JVariableReference> result = node.findPrimaryUseVariables();
        
        assertEquals("this.x", TestUtil.asStrOfReference(result));
    }
}
