/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel.internal;

import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaField;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import java.util.Set;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;

public class MethodCallCollectorTest {
    
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
    public void testMethodCallCollector1() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("addTile( int int java.awt.Color )");
        MethodCallCollector collector = new MethodCallCollector(TetrisProject);
        jm.getASTNode().accept(collector);
        Set<JavaMethod> result = collector.getCalledMethods();
        
        assertEquals(2, result.size());
        assertEquals("Tile#Tile( int int java.awt.Color );java.util.Set#add( java.lang.Object )",
                TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testMethodCallCollector2() {
        JavaMethod jm = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.Line").getMethod("Line( java.awt.Color )");
        MethodCallCollector collector = new MethodCallCollector(DrawToolProject);
        jm.getASTNode().accept(collector);
        Set<JavaMethod> result = collector.getCalledMethods();
        
        assertEquals(1, result.size());
        assertEquals("jp.ac.ritsumei.cs.draw.Figure#Figure( java.awt.Color )", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testMethodCallCollector3() {
        JavaField jf = TetrisProject.getClass("Block").getField("tiles");
        MethodCallCollector collector = new MethodCallCollector(TetrisProject);
        jf.getASTNode().accept(collector);
        Set<JavaMethod> result = collector.getCalledMethods();
        
        assertEquals(1, result.size());
        assertEquals("java.util.HashSet#HashSet( )", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testMethodCallCollector4() {
        JavaField jf = SliceProject.getClass("Order").getField("rentals");
        MethodCallCollector collector = new MethodCallCollector(SliceProject);
        jf.getASTNode().accept(collector);
        Set<JavaMethod> result = collector.getCalledMethods();
        
        assertEquals(1, result.size());
        assertEquals("java.util.ArrayList#ArrayList( )", TestUtil.asSortedStrOf(result));
    }
}
