/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel.builder;

import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import org.eclipse.jdt.core.dom.ITypeBinding;
import java.util.List;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;

public class UncaughtExceptionTypeCollectorTest {
    
    private static JavaProject CSclassroomProject;
    private static JavaProject DrawToolProject;
    private static JavaProject SliceProject;
    
    @BeforeClass
    public static void setUp() {
        CSclassroomProject = BuilderTestUtil.createProject("CS-classroom", "/lib/*", "/src");
        DrawToolProject = BuilderTestUtil.createProject("DrawTool", "", "/src");
        SliceProject = BuilderTestUtil.createProject("Slice", "", "");
    }
    
    @AfterClass
    public static void tearDown() {
        CSclassroomProject.getModelBuilder().unbuild();
        DrawToolProject.getModelBuilder().unbuild();
        SliceProject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testExceptionTypeCollector1() {
        JavaMethod jm = CSclassroomProject.getClass("Sample8").getMethod("main( java.lang.String[] )");
        UncaughtExceptionTypeCollector collector = new UncaughtExceptionTypeCollector();
        List<ITypeBinding> result = collector.getExceptions(jm);
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testExceptionTypeCollector2() {
        JavaMethod jm = CSclassroomProject.getClass("Circle2").getMethod("Circle2( int )");
        UncaughtExceptionTypeCollector collector = new UncaughtExceptionTypeCollector();
        List<ITypeBinding> result = collector.getExceptions(jm);
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testExceptionTypeCollector3() {
        JavaMethod jm = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.FigureMoved").getMethod("undo( )");
        UncaughtExceptionTypeCollector collector = new UncaughtExceptionTypeCollector();
        List<ITypeBinding> result = collector.getExceptions(jm);
        
        assertEquals(1, result.size());
        assertEquals("javax.swing.undo.CannotUndoException", TestUtil.asSortedStrOfTypeBinding(result));
    }
    
    @Test
    public void testExceptionTypeCollector4() {
        JavaMethod jm = SliceProject.getClass("Test122").getMethod("m( )");
        UncaughtExceptionTypeCollector collector = new UncaughtExceptionTypeCollector();
        List<ITypeBinding> result = collector.getExceptions(jm);
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testExceptionTypeCollector5() {
        JavaMethod jm = SliceProject.getClass("Test122").getMethod("n( int )");
        UncaughtExceptionTypeCollector collector = new UncaughtExceptionTypeCollector();
        List<ITypeBinding> result = collector.getExceptions(jm);
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testExceptionTypeCollector6() {
        JavaMethod jm = SliceProject.getClass("Test123").getMethod("m( )");
        UncaughtExceptionTypeCollector collector = new UncaughtExceptionTypeCollector();
        List<ITypeBinding> result = collector.getExceptions(jm);
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testExceptionTypeCollector7() {
        JavaMethod jm = SliceProject.getClass("Test123").getMethod("n( int )");
        UncaughtExceptionTypeCollector collector = new UncaughtExceptionTypeCollector();
        List<ITypeBinding> result = collector.getExceptions(jm);
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testExceptionTypeCollector8() {
        JavaMethod jm = SliceProject.getClass("Test123").getMethod("m2( )");
        UncaughtExceptionTypeCollector collector = new UncaughtExceptionTypeCollector();
        List<ITypeBinding> result = collector.getExceptions(jm);
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testExceptionTypeCollector9() {
        JavaMethod jm = SliceProject.getClass("Test123").getMethod("n2( int )");
        UncaughtExceptionTypeCollector collector = new UncaughtExceptionTypeCollector();
        List<ITypeBinding> result = collector.getExceptions(jm);
        
        assertEquals(1, result.size());
        assertEquals("SubRuntimeException", TestUtil.asSortedStrOfTypeBinding(result));
    }
    
    @Test
    public void testExceptionTypeCollector10() {
        JavaMethod jm = SliceProject.getClass("Test123").getMethod("n3( int )");
        UncaughtExceptionTypeCollector collector = new UncaughtExceptionTypeCollector();
        List<ITypeBinding> result = collector.getExceptions(jm);
        
        assertEquals(1, result.size());
        assertEquals("SubRuntimeException", TestUtil.asSortedStrOfTypeBinding(result));
    }
}
