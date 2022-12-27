/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel.internal;

import static org.jtool.srcmodel.AllSrcTests.DrawToolProject;
import static org.jtool.srcmodel.AllSrcTests.LambdaProject;
import static org.jtool.srcmodel.AllSrcTests.SliceProject;
import static org.jtool.srcmodel.AllSrcTests.TetrisProject;
import org.jtool.srcmodel.JavaClass;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import java.util.Set;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;

public class TypeCollectorTest {
    
    @BeforeClass
    public static void setUp() {
        DrawToolProject = BuilderTestUtil.getProject("DrawTool");
        LambdaProject = BuilderTestUtil.getProject("Lambda");
        SliceProject = BuilderTestUtil.getProject("Slice");
        TetrisProject = BuilderTestUtil.getProject("Tetris");
    }
    
    @Test
    public void testTypeCollector1() {
        JavaClass jc = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.Figure");
        TypeCollector collector = new TypeCollector(jc);
        jc.getASTNode().accept(collector);
        Set<JavaClass> result = collector.getTypes();
        
        assertEquals(7, result.size());
        assertEquals("java.awt.BasicStroke;"
                   + "java.awt.Color;"
                   + "java.awt.Graphics;"
                   + "java.awt.Stroke;"
                   + "java.lang.Object;"
                   + "java.lang.String;"
                   + "jp.ac.ritsumei.cs.draw.Figure",
                TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testTypeCollector2() {
        JavaClass jc = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.TabbedCanvas");
        TypeCollector collector = new TypeCollector(jc);
        jc.getASTNode().accept(collector);
        Set<JavaClass> result = collector.getTypes();
        
        assertEquals(11, result.size());
        assertEquals("java.lang.String;"
                   + "java.util.ArrayList;"
                   + "java.util.List;"
                   + "javax.swing.JTabbedPane;"
                   + "javax.swing.event.ChangeEvent;"
                   + "javax.swing.event.ChangeListener;"
                   + "javax.swing.undo.UndoManager;"
                   + "jp.ac.ritsumei.cs.draw.DrawCanvas;"
                   + "jp.ac.ritsumei.cs.draw.DrawMenu;"
                   + "jp.ac.ritsumei.cs.draw.FigureManager;"
                   + "jp.ac.ritsumei.cs.draw.FigureSelector",
                TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testTypeCollector3() {
        JavaClass jc = TetrisProject.getClass("Pit");
        TypeCollector collector = new TypeCollector(jc);
        jc.getASTNode().accept(collector);
        Set<JavaClass> result = collector.getTypes();
        
        assertEquals(9, result.size());
        assertEquals("Block;GameInfo;Tile;"
                   + "java.awt.Canvas;java.awt.Color;java.awt.Font;java.awt.Graphics;java.awt.Image;java.util.Random",
                TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testTypeCollector4() {
        JavaClass jc = LambdaProject.getClass("Test");
        TypeCollector collector = new TypeCollector(jc);
        jc.getASTNode().accept(collector);
        Set<JavaClass> result = collector.getTypes();
        
        assertEquals(5, result.size());
        assertEquals("Test;"
                   + "java.lang.String;"
                   + "java.util.function.BinaryOperator;"
                   + "java.util.function.Function;"
                   + "java.util.function.UnaryOperator",
                TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testTypeCollector5() {
        JavaClass jc = SliceProject.getClass("S136");
        TypeCollector collector = new TypeCollector(jc);
        jc.getASTNode().accept(collector);
        Set<JavaClass> result = collector.getTypes();
        
        assertEquals(3, result.size());
        assertEquals("java.lang.String;java.util.HashMap;java.util.Map",
                TestUtil.asSortedStrOf(result));
    }
}
