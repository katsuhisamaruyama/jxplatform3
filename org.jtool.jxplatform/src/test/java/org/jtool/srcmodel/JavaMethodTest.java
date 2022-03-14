/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel;

import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class JavaMethodTest {
    
    private static JavaProject CSclassroomProject;
    private static JavaProject SimpleProject;
    private static JavaProject TetrisProject;
    private static JavaProject VideoStoreProject;
    
    @BeforeClass
    public static void setUp() {
        CSclassroomProject = BuilderTestUtil.createProject("CS-classroom", "/lib/*", "/src");
        SimpleProject = BuilderTestUtil.createProject("Simple", "", "");
        TetrisProject = BuilderTestUtil.createProject("Tetris", "", "");
        VideoStoreProject = BuilderTestUtil.createProject("VideoStore", "/lib/*", "");
    }
    
    @AfterClass
    public static void tearDown() {
        CSclassroomProject.getModelBuilder().unbuild();
        SimpleProject.getModelBuilder().unbuild();
        TetrisProject.getModelBuilder().unbuild();
        VideoStoreProject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testGetJavaProject1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        JavaProject result = jm.getJavaProject(); 
        
        assertEquals("VideoStore", result.getName());
    }
    
    @Test
    public void testGetJavaProject2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("paint( java.awt.Graphics )");
        JavaProject result = jm.getJavaProject(); 
        
        assertEquals("Tetris", result.getName());
    }
    
    @Test
    public void testGetFile1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        JavaFile result = jm.getFile(); 
        
        assertEquals("Customer.java", result.getName());
    }
    
    @Test
    public void testGetFile2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("paint( java.awt.Graphics )");
        JavaFile result = jm.getFile(); 
        
        assertEquals("Block.java", result.getName());
    }
    
    @Test
    public void testGetQualifiedName1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        QualifiedName result = jm.getQualifiedName();
        
        assertEquals("org.jtool.videostore.after.Customer#statement( )", result.fqn());
    }
    
    @Test
    public void testGetQualifiedName2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("paint( java.awt.Graphics )");
        QualifiedName result = jm.getQualifiedName();
        
        assertEquals("Block#paint( java.awt.Graphics )", result.fqn());
    }
    
    @Test
    public void testGetQualifiedName3() {
        JavaMethod jm = VideoStoreProject.getExternalClass("java.util.ArrayList").getMethod("add( int java.lang.Object )");
        QualifiedName result = jm.getQualifiedName();
        
        assertEquals("java.util.ArrayList#add( int java.lang.Object )", result.fqn());
    }
    
    @Test
    public void testGetName1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        String result = jm.getName();
        
        assertEquals("statement", result);
    }
    
    @Test
    public void testGetName2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("paint( java.awt.Graphics )");
        String result = jm.getName();
        
        assertEquals("paint", result);
    }
    
    @Test
    public void testGetClassName1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        String result = jm.getClassName();
        
        assertEquals("org.jtool.videostore.after.Customer", result);
    }
    
    @Test
    public void testGetClassName2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("paint( java.awt.Graphics )");
        String result = jm.getClassName();
        
        assertEquals("Block", result);
    }
    
    @Test
    public void testGetSignature1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        String result = jm.getSignature();
        
        assertEquals("statement( )", result);
    }
    
    @Test
    public void testGetSignature2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("paint( java.awt.Graphics )");
        String result = jm.getSignature();
        
        assertEquals("paint( java.awt.Graphics )", result);
    }
    
    @Test
    public void testGetReturnType1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        String result = jm.getReturnType();
        
        assertEquals("java.lang.String", result);
    }
    
    @Test
    public void testGetReturnType2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("paint( java.awt.Graphics )");
        String result = jm.getReturnType();
        
        assertEquals("void", result);
    }
    
    @Test
    public void testOsPrimitiveReturnType1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        boolean result = jm.isPrimitiveReturnType();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsPrimitiveReturnType2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("paint( java.awt.Graphics )");
        boolean result = jm.isPrimitiveReturnType();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsPrimitiveReturnType3() {
        JavaMethod jm = TetrisProject.getClass("Tile").getMethod("getPosX( )");
        boolean result = jm.isPrimitiveReturnType();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsVoid1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        boolean result = jm.isVoid();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsVoid2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("paint( java.awt.Graphics )");
        boolean result = jm.isVoid();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsInProject1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        boolean result = jm.isInProject();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsInProject2() {
        JavaMethod jm = VideoStoreProject.getExternalClass("java.util.ArrayList").getMethod("add( java.lang.Object )");
        boolean result = jm.isInProject();
        
        assertFalse(result);
    }
    
    @Test
    public void testGetDeclaringClass1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        JavaClass result = jm.getDeclaringClass();
        
        assertEquals("org.jtool.videostore.after.Customer", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetDeclaringClass2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("paint( java.awt.Graphics )");
        JavaClass result = jm.getDeclaringClass();
        
        assertEquals("Block", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testIsMethod1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("Customer( java.lang.String )");
        boolean result = jm.isMethod();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsMethod2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("paint( java.awt.Graphics )");
        boolean result = jm.isMethod();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsConstructor1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("Customer( java.lang.String )");
        boolean result = jm.isConstructor();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsConstructor2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("paint( java.awt.Graphics )");
        boolean result = jm.isConstructor();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsInitializer1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("Customer( java.lang.String )");
        boolean result = jm.isInitializer();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsInitializer2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("paint( java.awt.Graphics )");
        boolean result = jm.isInitializer();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsInitializer3() {
        JavaMethod jm = SimpleProject.getClass("Test26").getMethod(".init( )");
        boolean result = jm.isInitializer();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsLambda1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("Customer( java.lang.String )");
        boolean result = jm.isLambda();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsLambda2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("paint( java.awt.Graphics )");
        boolean result = jm.isLambda();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsLambda3() {
        JavaMethod jm = CSclassroomProject.getClass("Sample110FX$LAMBDA1").getMethod("handle( javafx.event.Event )");
        boolean result = jm.isLambda();
        
        assertTrue(result);
    }
    
    @Test
    public void testGetParameters1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        List<String> result = jm.getParameters().stream().map(JavaLocalVar::getName).collect(Collectors.toList());
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetParameters2() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer")
                .getMethod("addRental( org.jtool.videostore.after.Rental )");
        List<JavaLocalVar> result = jm.getParameters();
        
        assertEquals(1, result.size());
        assertEquals("rental", TestUtil.asStrOf(result));
    }
    
    @Test
    public void testGetParameters3() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("Customer( java.lang.String )");
        List<JavaLocalVar> result = jm.getParameters();
        
        assertEquals(1, result.size());
        assertEquals("name", TestUtil.asStrOf(result));
    }
    
    @Test
    public void testGetParameters4() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Movie").getMethod("getCharge( int )");
        List<JavaLocalVar> result = jm.getParameters();
        
        assertEquals(1, result.size());
        assertEquals("daysRented", TestUtil.asStrOf(result));
    }
    
    @Test
    public void testGetParameters5() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("paint( java.awt.Graphics )");
        List<JavaLocalVar> result = jm.getParameters();
        
        assertEquals(1, result.size());
        assertEquals("g", TestUtil.asStrOf(result));
    }
    
    @Test
    public void testGetParameters6() {
        JavaMethod jm = TetrisProject.getClass("Pit").getMethod("setBox( int int Tile )");
        List<JavaLocalVar> result = jm.getParameters();
        
        assertEquals(3, result.size());
        assertEquals("x;y;t", TestUtil.asStrOf(result));
    }
    
    @Test
    public void testGetParameterSize1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        int result = jm.getParameterSize();
        
        assertEquals(0, result);
    }
    
    @Test
    public void testGetParameterSize2() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer")
                .getMethod("addRental( org.jtool.videostore.after.Rental )");
        int result = jm.getParameterSize();
        
        assertEquals(1, result);
    }
    
    @Test
    public void testGetParameterSize3() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("Customer( java.lang.String )");
        int result = jm.getParameterSize();
        
        assertEquals(1, result);
    }
    
    @Test
    public void testGetParameterSize4() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Movie").getMethod("getCharge( int )");
        int result = jm.getParameterSize();
        
        assertEquals(1, result);
    }
    
    @Test
    public void testGetParameterSize5() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("paint( java.awt.Graphics )");
        int result = jm.getParameterSize();
        
        assertEquals(1, result);
    }
    
    @Test
    public void testGetParameterSize6() {
        JavaMethod jm = TetrisProject.getClass("Pit").getMethod("setBox( int int Tile )");
        int result = jm.getParameterSize();
        
        assertEquals(3, result);
    }
    
    @Test
    public void testGetParameterByIndex1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        JavaLocalVar result = jm.getParameter(0);
        
        assertNull(result);
    }
    
    @Test
    public void testGetParameterByIndex2() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer")
                .getMethod("addRental( org.jtool.videostore.after.Rental )");
        JavaLocalVar result = jm.getParameter(0);
        
        assertEquals("rental", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetParameterByIndex3() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("Customer( java.lang.String )");
        JavaLocalVar result = jm.getParameter(0);
        
        assertEquals("name", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetParameterByIndex4() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Movie").getMethod("getCharge( int )");
        JavaLocalVar result = jm.getParameter(0);
        
        assertEquals("daysRented", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetParameterByIndex5() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("paint( java.awt.Graphics )");
        JavaLocalVar result = jm.getParameter(0);
        
        assertEquals("g", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetParameterByIndex6() {
        JavaMethod jm = TetrisProject.getClass("Pit").getMethod("setBox( int int Tile )");
        JavaLocalVar result = jm.getParameter(1);
        
        assertEquals("y", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetParameterByIndex7() {
        JavaMethod jm = TetrisProject.getClass("Pit").getMethod("setBox( int int Tile )");
        JavaLocalVar result = jm.getParameter(2);
        
        assertEquals("t", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetParameterByName1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        JavaLocalVar result = jm.getParameter("name");
        
        assertNull(result);
    }
    
    @Test
    public void testGetParameterByName2() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer")
                .getMethod("addRental( org.jtool.videostore.after.Rental )");
        JavaLocalVar result = jm.getParameter("rental");
        
        assertEquals("rental", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetParameterByName3() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("Customer( java.lang.String )");
        JavaLocalVar result = jm.getParameter("name");
        
        assertEquals("name", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetParameterByName4() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Movie").getMethod("getCharge( int )");
        JavaLocalVar result = jm.getParameter("daysRented");
        
        assertEquals("daysRented", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetParameterByName5() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("paint( java.awt.Graphics )");
        JavaLocalVar result = jm.getParameter("g");
        
        assertEquals("g", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetParameterByName6() {
        JavaMethod jm = TetrisProject.getClass("Pit").getMethod("setBox( int int Tile )");
        JavaLocalVar result = jm.getParameter("y");
        
        assertEquals("y", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetParameterByName7() {
        JavaMethod jm = TetrisProject.getClass("Pit").getMethod("setBox( int int Tile )");
        JavaLocalVar result = jm.getParameter("t");
        
        assertEquals("t", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetParameterIndex1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        int result = jm.getParameterIndex("name");
        
        assertEquals(-1, result);
    }
    
    @Test
    public void testGetParameterIndex2() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer")
                .getMethod("addRental( org.jtool.videostore.after.Rental )");
        int result = jm.getParameterIndex("rental");
        
        assertEquals(0, result);
    }
    
    @Test
    public void testGetParameterIndex3() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("Customer( java.lang.String )");
        int result = jm.getParameterIndex("name");
        
        assertEquals(0, result);
    }
    
    @Test
    public void testGetParameterIndex4() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Movie").getMethod("getCharge( int )");
        int result = jm.getParameterIndex("daysRented");
        
        assertEquals(0, result);
    }
    
    @Test
    public void testGetParameterIndex5() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("paint( java.awt.Graphics )");
        int result = jm.getParameterIndex("g");
        
        assertEquals(0, result);
    }
    
    @Test
    public void testGetParameterIndex6() {
        JavaMethod jm = TetrisProject.getClass("Pit").getMethod("setBox( int int Tile )");
        int result = jm.getParameterIndex("y");
        
        assertEquals(1, result);
    }
    
    @Test
    public void testGetParameterIndex7() {
        JavaMethod jm = TetrisProject.getClass("Pit").getMethod("setBox( int int Tile )");
        int result = jm.getParameterIndex("t");
        
        assertEquals(2, result);
    }
    
    @Test
    public void testGetLocalVariables1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("getTotalCharge( )");
        List<JavaLocalVar> result = jm.getLocalVariables();
        
        assertEquals(2, result.size());
        assertEquals("each;result", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetLocalVariables2() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("Customer( java.lang.String )");
        List<JavaLocalVar> result = jm.getLocalVariables();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetLocalVariables3() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("getName( )");
        List<JavaLocalVar> result = jm.getLocalVariables();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetLocalVariables4() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("setPosXY( int int )");
        List<JavaLocalVar> result = jm.getLocalVariables();
        
        assertEquals(2, result.size());
        assertEquals("it;tile", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetLocalVariable1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("getTotalCharge( )");
        JavaLocalVar result = jm.getLocalVariable("each", 1);
        
        assertEquals("each", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetLocalVariable2() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("Customer( java.lang.String )");
        JavaLocalVar result = jm.getLocalVariable("name", 0);
        
        assertNull(result);
    }
    
    @Test
    public void testGetLocalVariable3() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("getName( )");
        jm.getLocalVariables().forEach(e -> System.err.println(e.getQualifiedName() + " " + e.getVariableId()));
        JavaLocalVar result = jm.getLocalVariable("name", 0);
        
        assertNull(result);
    }
    
    @Test
    public void testGetLocalVariable4() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("setPosXY( int int )");
        JavaLocalVar result = jm.getLocalVariable("it", 2);
        
        assertEquals("it", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testIsPublic1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        boolean result = jm.isPublic();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsPublic2() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("getTotalCharge( )");
        boolean result = jm.isPublic();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsPublic3() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("Block( int int )");
        boolean result = jm.isPublic();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsPublic4() {
        JavaMethod jm = TetrisProject.getClass("BlueBlock").getMethod("BlueBlock( int int )");
        boolean result = jm.isPublic();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsProtected1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        boolean result = jm.isProtected();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsProtected2() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("getTotalCharge( )");
        boolean result = jm.isProtected();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsProtected3() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("Block( int int )");
        boolean result = jm.isProtected();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsProtected4() {
        JavaMethod jm = TetrisProject.getClass("BlueBlock").getMethod("BlueBlock( int int )");
        boolean result = jm.isProtected();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsPrivate1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        boolean result = jm.isPrivate();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsPrivate2() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("getTotalCharge( )");
        boolean result = jm.isPrivate();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsPrivate3() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("Block( int int )");
        boolean result = jm.isPrivate();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsPrivate4() {
        JavaMethod jm = TetrisProject.getClass("BlueBlock").getMethod("BlueBlock( int int )");
        boolean result = jm.isPrivate();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsDefault1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        boolean result = jm.isDefault();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsDefault2() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("getTotalCharge( )");
        boolean result = jm.isDefault();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsDefault3() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("Block( int int )");
        boolean result = jm.isDefault();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsDefault4() {
        JavaMethod jm = TetrisProject.getClass("BlueBlock").getMethod("BlueBlock( int int )");
        boolean result = jm.isDefault();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsFinal1() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("Block( int int )");
        boolean result = jm.isFinal();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsFinal2() {
        JavaMethod jm = SimpleProject.getClass("Test21").getMethod("m( )");
        boolean result = jm.isFinal();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsAbstract1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        boolean result = jm.isAbstract();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsAbstract2() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Price").getMethod("getCharge( int )");
        boolean result = jm.isAbstract();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsStatic1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        boolean result = jm.isStatic();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsStatic2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("create( int int int )");
        boolean result = jm.isStatic();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsSynchronized1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        boolean result = jm.isSynchronized();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsSynchronized2() {
        JavaMethod jm = TetrisProject.getClass("Pit").getMethod("update( )");
        boolean result = jm.isSynchronized();
        
        assertTrue(result);
    }
    
    @Test
    public void testGetExceptions1() {
        JavaMethod jm = CSclassroomProject.getClass("Storage").getMethod("put( java.lang.String )");
        Set<JavaClass> result = jm.getExceptions();
        
        assertEquals(1, result.size());
//assertEquals("org.jtool.videostore.after.Price#getPriceCode( )", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetExceptions2() {
        JavaMethod jm = CSclassroomProject.getClass("Storage").getMethod("take( )");
        Set<JavaClass> result = jm.getExceptions();
        
        assertEquals(1, result.size());
//assertEquals("org.jtool.videostore.after.Price#getPriceCode( )", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetExceptions3() {
        JavaMethod jm = CSclassroomProject.getClass("Producer").getMethod("run( )");
        Set<JavaClass> result = jm.getExceptions();
        
        assertEquals(0, result.size());
//assertEquals("org.jtool.videostore.after.Price#getPriceCode( )", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetCalledMethods1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        Set<JavaMethod> result = jm.getCalledMethods();
        
        assertEquals(8, result.size());
        assertEquals("java.lang.String#valueOf( double );"
                   + "java.lang.String#valueOf( int );"
                   + "org.jtool.videostore.after.Customer#getName( );"
                   + "org.jtool.videostore.after.Customer#getTotalCharge( );"
                   + "org.jtool.videostore.after.Customer#getTotalFrequentRenterPoints( );"
                   + "org.jtool.videostore.after.Movie#getTitle( );"
                   + "org.jtool.videostore.after.Rental#getCharge( );"
                   + "org.jtool.videostore.after.Rental#getMovie( )",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetCalledMethods2() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Movie").getMethod("getPriceCode( )");
        Set<JavaMethod> result = jm.getCalledMethods();
        
        assertEquals(1, result.size());
        assertEquals("org.jtool.videostore.after.Price#getPriceCode( )", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetCalledMethods3() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Movie").getMethod("getCharge( int )");
        Set<JavaMethod> result = jm.getCalledMethods();
        
        assertEquals(1, result.size());
        assertEquals("org.jtool.videostore.after.Price#getCharge( int )", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetCalledMethods4() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Price").getMethod("getCharge( int )");
        Set<JavaMethod> result = jm.getCalledMethods();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetCalledMethods5() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.RegularPrice").getMethod("getCharge( int )");
        Set<JavaMethod> result = jm.getCalledMethods();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetCalledMethodsInProject1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        Set<JavaMethod> result = jm.getCalledMethodsInProject();
        
        assertEquals(6, result.size());
        assertEquals("org.jtool.videostore.after.Customer#getName( );"
                   + "org.jtool.videostore.after.Customer#getTotalCharge( );"
                   + "org.jtool.videostore.after.Customer#getTotalFrequentRenterPoints( );"
                   + "org.jtool.videostore.after.Movie#getTitle( );"
                   + "org.jtool.videostore.after.Rental#getCharge( );"
                   + "org.jtool.videostore.after.Rental#getMovie( )",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetCalledMethodsInProject2() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Movie").getMethod("getPriceCode( )");
        Set<JavaMethod> result = jm.getCalledMethodsInProject();
        
        assertEquals(1, result.size());
        assertEquals("org.jtool.videostore.after.Price#getPriceCode( )", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetCalledMethodsInProject3() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Movie").getMethod("getCharge( int )");
        Set<JavaMethod> result = jm.getCalledMethodsInProject();
        
        assertEquals(1, result.size());
        assertEquals("org.jtool.videostore.after.Price#getCharge( int )", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetCalledMethodsInProject4() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Price").getMethod("getCharge( int )");
        Set<JavaMethod> result = jm.getCalledMethodsInProject();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetCalledMethodsInProject5() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.RegularPrice").getMethod("getCharge( int )");
        Set<JavaMethod> result = jm.getCalledMethodsInProject();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetCallingMethods1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        Set<JavaMethod> result = jm.getCallingMethods();
        
        assertEquals(7, result.size());
        assertEquals("org.jtool.videostore.after.CustomerTest#testStatementForRentingChildrensMovie( );"
                   + "org.jtool.videostore.after.CustomerTest#testStatementForRentingNewReleaseMovie( );"
                   + "org.jtool.videostore.after.CustomerTest#testStatementForRentingRegularMovie( );"
                   + "org.jtool.videostore.after.CustomerTest#testStatementForRentingTwoMovies( );"
                   + "org.jtool.videostore.after.CustomerTest#testStatementMinusDaysRented( );"
                   + "org.jtool.videostore.after.CustomerTest#testStatementOfThreeMovies1( );"
                   + "org.jtool.videostore.after.CustomerTest#testZeroRental( )",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetCallingMethods2() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Movie").getMethod("getPriceCode( )");
        Set<JavaMethod> result = jm.getCallingMethods();
        
        assertEquals(6, result.size());
        assertEquals("org.jtool.videostore.after.MovieTest#testPriceCodeOfChildrensMovie( );"
                   + "org.jtool.videostore.after.MovieTest#testPriceCodeOfNewReleaseMovie( );"
                   + "org.jtool.videostore.after.MovieTest#testPriceCodeOfRegularMovie( );"
                   + "org.jtool.videostore.after.RentalTest#testChildrensMovie( );"
                   + "org.jtool.videostore.after.RentalTest#testNewReleaseMovie( );"
                   + "org.jtool.videostore.after.RentalTest#testRegularMovie( )",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetCallingMethods3() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Movie").getMethod("getCharge( int )");
        Set<JavaMethod> result = jm.getCallingMethods();
        
        assertEquals(1, result.size());
        assertEquals("org.jtool.videostore.after.Rental#getCharge( )", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetCallingMethods4() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Price").getMethod("getCharge( int )");
        Set<JavaMethod> result = jm.getCallingMethods();
        
        assertEquals(1, result.size());
        assertEquals("org.jtool.videostore.after.Movie#getCharge( int )", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetCallingMethods5() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.RegularPrice").getMethod("getCharge( int )");
        Set<JavaMethod> result = jm.getCallingMethods();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetCallingMethods6() {
        JavaMethod jm = VideoStoreProject.getExternalClass("java.lang.String").getMethod("valueOf( double )");
        Set<JavaMethod> result = jm.getCallingMethods();
        
        assertEquals(6, result.size());
        assertEquals("org.jtool.videostore.after.Customer#statement( );"
                   + "org.jtool.videostore.after.CustomerTest#getEach( java.lang.String double );"
                   + "org.jtool.videostore.after.CustomerTest#getTotal( double int );"
                   + "org.jtool.videostore.before.Customer#statement( );"
                   + "org.jtool.videostore.before.CustomerTest#getEach( java.lang.String double );"
                   + "org.jtool.videostore.before.CustomerTest#getTotal( double int )",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetCallingMethodsInProject1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("getName( )");
        Set<JavaMethod> result = jm.getCallingMethodsInProject();
        
        assertEquals(4, result.size());
        assertEquals("org.jtool.videostore.after.Customer#statement( );"
                   + "org.jtool.videostore.after.CustomerTest#testEmptyName( );"
                   + "org.jtool.videostore.after.CustomerTest#testName( );"
                   + "org.jtool.videostore.use.CustomerUse#makeCustomer( )",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetCallingMethodsInProject2() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Movie").getMethod("getPriceCode( )");
        Set<JavaMethod> result = jm.getCallingMethodsInProject();
        
        assertEquals(6, result.size());
        assertEquals("org.jtool.videostore.after.MovieTest#testPriceCodeOfChildrensMovie( );"
                   + "org.jtool.videostore.after.MovieTest#testPriceCodeOfNewReleaseMovie( );"
                   + "org.jtool.videostore.after.MovieTest#testPriceCodeOfRegularMovie( );"
                   + "org.jtool.videostore.after.RentalTest#testChildrensMovie( );"
                   + "org.jtool.videostore.after.RentalTest#testNewReleaseMovie( );"
                   + "org.jtool.videostore.after.RentalTest#testRegularMovie( )",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetCallingMethodsInProject3() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Movie").getMethod("getCharge( int )");
        Set<JavaMethod> result = jm.getCallingMethodsInProject();
        
        assertEquals(1, result.size());
        assertEquals("org.jtool.videostore.after.Rental#getCharge( )", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetCallingMethodsInProject4() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Price").getMethod("getCharge( int )");
        Set<JavaMethod> result = jm.getCallingMethodsInProject();
        
        assertEquals(1, result.size());
        assertEquals("org.jtool.videostore.after.Movie#getCharge( int )", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetCallingMethodsInProject5() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.RegularPrice").getMethod("getCharge( int )");
        Set<JavaMethod> result = jm.getCallingMethodsInProject();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessedFields1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        Set<JavaField> result = jm.getAccessedFields();
        
        assertEquals(1, result.size());
        assertEquals("org.jtool.videostore.after.Customer#rentals", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAccessedFields2() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Movie")
                .getMethod("setPriceCode( org.jtool.videostore.after.Movie.PriceCode )");
        Set<JavaField> result = jm.getAccessedFields();
        
        assertEquals(4, result.size());
        assertEquals("org.jtool.videostore.after.Movie#price;"
                   + "org.jtool.videostore.after.Movie.PriceCode#CHILDRENS;"
                   + "org.jtool.videostore.after.Movie.PriceCode#NEW_RELEASE;"
                   + "org.jtool.videostore.after.Movie.PriceCode#REGULAR",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAccessedFields3() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Price").getMethod("getPriceCode( )");
        Set<JavaField> result = jm.getAccessedFields();
        
        assertEquals(1, result.size());
        assertEquals("org.jtool.videostore.after.Price#priceCode", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAccessedFields4() {
        JavaMethod jm = TetrisProject.getClass("BlueBlock").getMethod("BlueBlock( int int )");
        Set<JavaField> result = jm.getAccessedFields();
        
        assertEquals(3, result.size());
        assertEquals("Block#offsetY;BlueBlock#COLOR;Tile#SIZE", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAccessedFields5() {
        JavaMethod jm = TetrisProject.getClass("Pit").getMethod("init( )");
        Set<JavaField> result = jm.getAccessedFields();
        
        assertEquals(7, result.size());
        assertEquals("Pit#backgroundColor;"
                   + "Pit#font;"
                   + "Pit#height;"
                   + "Pit#offImage;"
                   + "Pit#offg;"
                   + "Pit#width;"
                   + "java.awt.Font#BOLD",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAccessedFields6() {
        JavaMethod jm = SimpleProject.getClass("Test27").getMethod("init( )");
        Set<JavaField> result = jm.getAccessedFields();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessedFields7() {
        JavaMethod jm = SimpleProject.getClass("MockArrayList").getMethod("MockArrayList( )");
        Set<JavaField> result = jm.getAccessedFields();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessedFields8() {
        JavaMethod jm = SimpleProject.getExternalClass("java.util.ArrayList").getMethod("add( java.lang.Object )");
        Set<JavaField> result = jm.getAccessedFields();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessedFieldsInProject1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        Set<JavaField> result = jm.getAccessedFieldsInProject();
        
        assertEquals(1, result.size());
        assertEquals("org.jtool.videostore.after.Customer#rentals", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAccessedFieldsInProject2() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Movie")
                .getMethod("setPriceCode( org.jtool.videostore.after.Movie.PriceCode )");
        Set<JavaField> result = jm.getAccessedFieldsInProject();
        
        assertEquals(4, result.size());
        assertEquals("org.jtool.videostore.after.Movie#price;"
                   + "org.jtool.videostore.after.Movie.PriceCode#CHILDRENS;"
                   + "org.jtool.videostore.after.Movie.PriceCode#NEW_RELEASE;"
                   + "org.jtool.videostore.after.Movie.PriceCode#REGULAR",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAccessedFieldsInProject3() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Price").getMethod("getPriceCode( )");
        Set<JavaField> result = jm.getAccessedFieldsInProject();
        
        assertEquals(1, result.size());
        assertEquals("org.jtool.videostore.after.Price#priceCode", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAccessedFieldsInProject4() {
        JavaMethod jm = TetrisProject.getClass("BlueBlock").getMethod("BlueBlock( int int )");
        Set<JavaField> result = jm.getAccessedFieldsInProject();
        
        assertEquals(3, result.size());
        assertEquals("Block#offsetY;BlueBlock#COLOR;Tile#SIZE", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAccessedFieldsInProject5() {
        JavaMethod jm = TetrisProject.getClass("Pit").getMethod("init( )");
        Set<JavaField> result = jm.getAccessedFieldsInProject();
        
        assertEquals(6, result.size());
        assertEquals("Pit#backgroundColor;Pit#font;Pit#height;Pit#offImage;Pit#offg;Pit#width",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAccessedFieldsInProject6() {
        JavaMethod jm = SimpleProject.getClass("Test27").getMethod("init( )");
        Set<JavaField> result = jm.getAccessedFieldsInProject();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessedFieldsInProject7() {
        JavaMethod jm = SimpleProject.getClass("MockArrayList").getMethod("MockArrayList( )");
        Set<JavaField> result = jm.getAccessedFieldsInProject();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessingFields1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        Set<JavaField> result = jm.getAccessingFields();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessingFields2() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Movie")
                .getMethod("setPriceCode( org.jtool.videostore.after.Movie.PriceCode )");
        Set<JavaField> result = jm.getAccessingFields();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessingFields3() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Price").getMethod("getPriceCode( )");
        Set<JavaField> result = jm.getAccessingFields();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessingFields4() {
        JavaMethod jm = TetrisProject.getClass("BlueBlock").getMethod("BlueBlock( int int )");
        Set<JavaField> result = jm.getAccessingFields();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessingFields5() {
        JavaMethod jm = TetrisProject.getClass("Pit").getMethod("init( )");
        Set<JavaField> result = jm.getAccessingFields();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessingFields6() {
        JavaMethod jm = SimpleProject.getClass("Test27").getMethod("init( )");
        Set<JavaField> result = jm.getAccessingFields();
        
        assertEquals(1, result.size());
        assertEquals("Test27#x", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAccessingFields7() {
        JavaMethod jm = SimpleProject.getClass("MockArrayList").getMethod("MockArrayList( )");
        Set<JavaField> result = jm.getAccessingFields();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessingFieldsInProject1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        Set<JavaField> result = jm.getAccessingFieldsInProject();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessingFieldsInProject2() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Movie")
                .getMethod("setPriceCode( org.jtool.videostore.after.Movie.PriceCode )");
        Set<JavaField> result = jm.getAccessingFieldsInProject();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessingFieldsInProject3() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Price").getMethod("getPriceCode( )");
        Set<JavaField> result = jm.getAccessingFieldsInProject();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessingFieldsInProject4() {
        JavaMethod jm = TetrisProject.getClass("BlueBlock").getMethod("BlueBlock( int int )");
        Set<JavaField> result = jm.getAccessingFieldsInProject();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessingFieldsInProject5() {
        JavaMethod jm = TetrisProject.getClass("Pit").getMethod("init( )");
        Set<JavaField> result = jm.getAccessingFieldsInProject();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessingFieldsInProject6() {
        JavaMethod jm = SimpleProject.getClass("Test27").getMethod("init( )");
        Set<JavaField> result = jm.getAccessingFieldsInProject();
        
        assertEquals(1, result.size());
        assertEquals("Test27#x", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAccessingFieldsInProject7() {
        JavaMethod jm = SimpleProject.getClass("MockArrayList").getMethod("MockArrayList( )");
        Set<JavaField> result = jm.getAccessingFieldsInProject();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetOverriddenMethods1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        Set<JavaMethod> result = jm.getOverriddenMethods();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetOverriddenMethods2() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Price").getMethod("getCharge( int )");
        Set<JavaMethod> result = jm.getOverriddenMethods();
        
        assertEquals(3, result.size());
        assertEquals("org.jtool.videostore.after.ChildrensPrice#getCharge( int );"
                   + "org.jtool.videostore.after.NewReleasePrice#getCharge( int );"
                   + "org.jtool.videostore.after.RegularPrice#getCharge( int )",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetOverriddenMethods3() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.RegularPrice").getMethod("getCharge( int )");
        Set<JavaMethod> result = jm.getOverriddenMethods();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetOverriddenMethods4() {
        JavaMethod jm = TetrisProject.getClass("Pit").getMethod("paint( java.awt.Graphics )");
        Set<JavaMethod> result = jm.getOverriddenMethods();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetOverriddenMethods5() {
        JavaMethod jm = TetrisProject.getExternalClass("java.awt.Canvas").getMethod("paint( java.awt.Graphics )");
        Set<JavaMethod> result = jm.getOverriddenMethods();
        
        assertEquals(2, result.size());
        assertEquals("GameInfo#paint( java.awt.Graphics );Pit#paint( java.awt.Graphics )",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetOverridingMethods1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        Set<JavaMethod> result = jm.getOverridingMethods();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetOverridingMethods2() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Price").getMethod("getCharge( int )");
        Set<JavaMethod> result = jm.getOverridingMethods();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetOverridingMethods3() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.RegularPrice").getMethod("getCharge( int )");
        Set<JavaMethod> result = jm.getOverridingMethods();
        
        assertEquals(1, result.size());
        assertEquals("org.jtool.videostore.after.Price#getCharge( int )", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetOverridingMethods4() {
        JavaMethod jm = TetrisProject.getClass("Pit").getMethod("paint( java.awt.Graphics )");
        Set<JavaMethod> result = jm.getOverridingMethods();
        
        assertEquals(2, result.size());
        assertEquals("java.awt.Canvas#paint( java.awt.Graphics );java.awt.Component#paint( java.awt.Graphics )", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetOverridingMethods5() {
        JavaMethod jm = TetrisProject.getExternalClass("java.awt.Canvas").getMethod("paint( java.awt.Graphics )");
        Set<JavaMethod> result = jm.getOverridingMethods();
        
        assertEquals(1, result.size());
        assertEquals("java.awt.Component#paint( java.awt.Graphics )", TestUtil.asSortedStrOf(result));
    }
}
