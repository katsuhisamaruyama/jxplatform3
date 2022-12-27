/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel.internal;

import static org.jtool.srcmodel.AllSrcTests.DrawToolProject;
import static org.jtool.srcmodel.AllSrcTests.SliceProject;
import static org.jtool.srcmodel.AllSrcTests.TetrisProject;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.srcmodel.JavaMethod;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;

public class StatementCollectorTest {
    
    @BeforeClass
    public static void setUp() {
        DrawToolProject = BuilderTestUtil.getProject("DrawTool");
        SliceProject = BuilderTestUtil.getProject("Slice");
        TetrisProject = BuilderTestUtil.getProject("Tetris");
    }
    
    @Test
    public void testStatementCollector1() {
        JavaMethod jm = TetrisProject.getClass("Tetris").getMethod("keyPressed( java.awt.event.KeyEvent )");
        StatementCollector collector = new StatementCollector();
        jm.getASTNode().accept(collector);
        
        assertEquals(27, collector.getNumberOfStatements());
    }
    
    @Test
    public void testStatementCollector2() {
        JavaMethod jm = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.DrawCanvas").getMethod("autoSave( )");
        StatementCollector collector = new StatementCollector();
        jm.getASTNode().accept(collector);
        
        assertEquals(6, collector.getNumberOfStatements());
    }
    
    @Test
    public void testStatementCollector3() {
        JavaMethod jm = SliceProject.getClass("Customer").getMethod("statement( Order )");
        StatementCollector collector = new StatementCollector();
        jm.getASTNode().accept(collector);
        
        assertEquals(6, collector.getNumberOfStatements());
    }
}
