/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel.internal;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.srcmodel.JavaMethod;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;

public class StatementCollectorTest {
    
    private static JavaProject DrawToolProject;
    private static JavaProject SliceProject;
    private static JavaProject TetrisProject;
    
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
