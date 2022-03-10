/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder.srcmodel;

import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaLocalVar;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import java.util.Set;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;

public class SrcModelBuilderTest {
    
    private static JavaProject CSclassroomProject;
    private static JavaProject DrawToolProject;
    private static JavaProject LambdaProject;
    private static JavaProject SimpleProject;
    private static JavaProject SliceProject;
    private static JavaProject TetrisProject;
    
    @BeforeClass
    public static void setUp() {
        CSclassroomProject = BuilderTestUtil.createProject("CS-classroom", "/lib/*", "/src");
        DrawToolProject = BuilderTestUtil.createProject("DrawTool", "", "/src");
        LambdaProject = BuilderTestUtil.createProject("Lambda", "", "");
        SimpleProject = BuilderTestUtil.createProject("Simple", "", "");
        SliceProject = BuilderTestUtil.createProject("Slice", "", "");
        TetrisProject = BuilderTestUtil.createProject("Tetris", "", "");
    }
    
    @AfterClass
    public static void tearDown() {
        CSclassroomProject.getModelBuilder().unbuild();
        DrawToolProject.getModelBuilder().unbuild();
        LambdaProject.getModelBuilder().unbuild();
        SimpleProject.getModelBuilder().unbuild();
        SliceProject.getModelBuilder().unbuild();
        TetrisProject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testExceptionTypeCollector1() {
        JavaMethod jm = CSclassroomProject.getClass("Sample8").getMethod("main( java.lang.String[] )");
        ExceptionTypeCollector collector = new ExceptionTypeCollector();
        Set<ITypeBinding> result = collector.getExceptions(jm);
        
        assertEquals(1, result.size());
        assertEquals("ZeroRadiusException", TestUtil.asSortedStrOfTypeBinding(result));
    }
    
    @Test
    public void testExceptionTypeCollector2() {
        JavaMethod jm = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.FigureMoved").getMethod("undo( )");
        ExceptionTypeCollector collector = new ExceptionTypeCollector();
        Set<ITypeBinding> result = collector.getExceptions(jm);
        
        assertEquals(1, result.size());
        assertEquals("javax.swing.undo.CannotUndoException", TestUtil.asSortedStrOfTypeBinding(result));
    }
    
    @Test
    public void testExceptionTypeCollector3() {
        JavaMethod jm = SliceProject.getClass("Test122").getMethod("m( )");
        ExceptionTypeCollector collector = new ExceptionTypeCollector();
        Set<ITypeBinding> result = collector.getExceptions(jm);
        
        assertEquals(1, result.size());
        assertEquals("java.lang.Exception", TestUtil.asSortedStrOfTypeBinding(result));
    }
    
    @Test
    public void testExceptionTypeCollector4() {
        JavaMethod jm = SliceProject.getClass("Test123").getMethod("m( )");
        ExceptionTypeCollector collector = new ExceptionTypeCollector();
        Set<ITypeBinding> result = collector.getExceptions(jm);
        
        assertEquals(2, result.size());
        assertEquals("SubException;SubSubException", TestUtil.asSortedStrOfTypeBinding(result));
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
    
    @Test
    public void testFieldInitializerCollector1() {
        JavaField jf = TetrisProject.getClass("BlueBlock").getField("COLOR");
        FieldInitializerCollector collector = new FieldInitializerCollector(TetrisProject);
        jf.getASTNode().accept(collector);
        Set<JavaField> result = collector.getAccessedFields();
        
        assertEquals(1, result.size());
        assertEquals("java.awt.Color#blue", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testFieldInitializerCollector2() {
        JavaField jf = TetrisProject.getClass("Block").getField("tiles");
        FieldInitializerCollector collector = new FieldInitializerCollector(TetrisProject);
        jf.getASTNode().accept(collector);
        Set<JavaField> result = collector.getAccessedFields();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testFieldInitializerCollector3() {
        JavaField jf = SliceProject.getClass("Order").getField("rentals");
        FieldInitializerCollector collector = new FieldInitializerCollector(SliceProject);
        jf.getASTNode().accept(collector);
        Set<JavaField> result = collector.getAccessedFields();
        
        assertEquals(0, result.size());
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
    
    @Test
    public void testLocalDeclarationCollector1() {
        JavaMethod jm = TetrisProject.getClass("Tetris").getMethod("keyPressed( java.awt.event.KeyEvent )");
        LocalDeclarationCollector collector = new LocalDeclarationCollector(jm);
        MethodDeclaration node = (MethodDeclaration)jm.getASTNode();
        node.getBody().accept(collector);
        Set<JavaLocalVar> result = collector.getLocalDeclarations();
        
        assertEquals(2, result.size());
        assertEquals("block;keyCode", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testLocalDeclarationCollector2() {
        JavaMethod jm = TetrisProject.getClass("Pit").getMethod("Pit( GameInfo )");
        LocalDeclarationCollector collector = new LocalDeclarationCollector(jm);
        MethodDeclaration node = (MethodDeclaration)jm.getASTNode();
        node.getBody().accept(collector);
        Set<JavaLocalVar> result = collector.getLocalDeclarations();
        
        assertEquals(1, result.size());
        assertEquals("seed", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testLocalDeclarationCollector3() {
        JavaMethod jm = SliceProject.getClass("Customer").getMethod("statement( Order )");
        LocalDeclarationCollector collector = new LocalDeclarationCollector(jm);
        MethodDeclaration node = (MethodDeclaration)jm.getASTNode();
        node.getBody().accept(collector);
        Set<JavaLocalVar> result = collector.getLocalDeclarations();
        
        assertEquals(1, result.size());
        assertEquals("amount", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testLocalDeclarationCollector4() {
        JavaMethod jm = SimpleProject.getClass("Test26").getMethod(".init( )");
        LocalDeclarationCollector collector = new LocalDeclarationCollector(jm);
        Initializer node = (Initializer)jm.getASTNode();
        node.getBody().accept(collector);
        Set<JavaLocalVar> result = collector.getLocalDeclarations();
        
        assertEquals(1, result.size());
        assertEquals("c", TestUtil.asSortedStrOf(result));
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
    
    @Test
    public void testLambdaCollector1() {
        JavaMethod jm = CSclassroomProject.getClass("Sample110FX").getMethod("start( javafx.stage.Stage )");
        LambdaCollector collector = new LambdaCollector(jm);
        MethodDeclaration node = (MethodDeclaration)jm.getASTNode();
        node.getBody().accept(collector);
        Set<JavaClass> result = collector.getLambdas();
        
        assertEquals(1, result.size());
        assertEquals("Sample110FX#start( javafx.stage.Stage )$1", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testLambdaCollector2() {
        JavaMethod jm = LambdaProject.getClass("Test").getMethod("run1( )");
        LambdaCollector collector = new LambdaCollector(jm);
        MethodDeclaration node = (MethodDeclaration)jm.getASTNode();
        node.getBody().accept(collector);
        Set<JavaClass> result = collector.getLambdas();
        
        assertEquals(1, result.size());
        assertEquals("Test#run1( )$1", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testLambdaCollector3() {
        JavaMethod jm = LambdaProject.getClass("Test").getMethod("run2( )");
        LambdaCollector collector = new LambdaCollector(jm);
        MethodDeclaration node = (MethodDeclaration)jm.getASTNode();
        node.getBody().accept(collector);
        Set<JavaClass> result = collector.getLambdas();
        
        assertEquals(0, result.size());
    }
}
