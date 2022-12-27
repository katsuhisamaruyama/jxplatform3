/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel.internal;

import static org.jtool.srcmodel.AllSrcTests.DrawToolProject;
import static org.jtool.srcmodel.AllSrcTests.SimpleProject;
import static org.jtool.srcmodel.AllSrcTests.SliceProject;
import static org.jtool.srcmodel.AllSrcTests.TetrisProject;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaField;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import java.util.Set;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;

public class FieldAccessCollectorTest {
    
    @BeforeClass
    public static void setUp() {
        DrawToolProject = BuilderTestUtil.getProject("DrawTool");
        SimpleProject = BuilderTestUtil.getProject("Simple");
        SliceProject = BuilderTestUtil.getProject("Slice");
        TetrisProject = BuilderTestUtil.getProject("Tetris");
    }
    
    @Test
    public void testFieldAccessCollector1() {
        JavaMethod jm = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.Figure").getMethod("setStart( int int )");
        FieldAccessCollector collector = new FieldAccessCollector(DrawToolProject);
        jm.getASTNode().accept(collector);
        Set<JavaField> result = collector.getAccessedFields();
        
        assertEquals(2, result.size());
        assertEquals("jp.ac.ritsumei.cs.draw.Figure#startX;jp.ac.ritsumei.cs.draw.Figure#startY",
                   TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testFieldAccessCollector2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("setPosXY( int int )");
        FieldAccessCollector collector = new FieldAccessCollector(TetrisProject);
        jm.getASTNode().accept(collector);
        Set<JavaField> result = collector.getAccessedFields();
        
        assertEquals(3, result.size());
        assertEquals("Block#posX;Block#posY;Block#tiles", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testFieldAccessCollector3() {
        JavaMethod jm = SliceProject.getClass("Test101").getMethod("m( )");
        FieldAccessCollector collector = new FieldAccessCollector(SliceProject);
        jm.getASTNode().accept(collector);
        Set<JavaField> result = collector.getAccessedFields();
        
        assertEquals(1, result.size());
        assertEquals("Test101#p", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testFieldAccessCollector4() {
        JavaMethod jm = SimpleProject.getClass("Test26").getMethod(".init( )");
        FieldAccessCollector collector = new FieldAccessCollector(SimpleProject);
        jm.getASTNode().accept(collector);
        Set<JavaField> result = collector.getAccessedFields();
        
        assertEquals(1, result.size());
        assertEquals("Test26#xxx", TestUtil.asSortedStrOf(result));
    }
}
