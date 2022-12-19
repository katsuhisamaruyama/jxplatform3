/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel;

import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class JavaLocalVarTest {
    
    private static JavaProject TetrisProject;
    private static JavaProject VideoStoreProject;
    
    @BeforeClass
    public static void setUp() {
        TetrisProject = BuilderTestUtil.createProject("Tetris", "", "");
        VideoStoreProject = BuilderTestUtil.createProject("VideoStore", "/lib/*", "");
    }
    
    @AfterClass
    public static void tearDown() {
        TetrisProject.getModelBuilder().unbuild();
        VideoStoreProject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testGetJavaProject1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        JavaLocalVar jv = jm.getLocalVariable("each", 1);
        JavaProject result = jv.getJavaProject(); 
        
        assertEquals("VideoStore", result.getName());
    }
    
    @Test
    public void testGetJavaProject2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("setPosXY( int int )");
        JavaLocalVar jv = jm.getParameter("x");
        JavaProject result = jv.getJavaProject(); 
        
        assertEquals("Tetris", result.getName());
    }
    
    @Test
    public void testGetFile1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        JavaLocalVar jv = jm.getLocalVariable("each", 1);
        JavaFile result = jv.getFile(); 
        
        assertEquals("Customer.java", result.getName());
    }
    
    @Test
    public void testGetFile2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("setPosXY( int int )");
        JavaLocalVar jv = jm.getParameter("x");
        JavaFile result = jv.getFile(); 
        
        assertEquals("Block.java", result.getName());
    }
    
    @Test
    public void testGetQualifiedName1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        JavaLocalVar jv = jm.getLocalVariable("each", 1);
        QualifiedName result = jv.getQualifiedName();
        
        assertEquals("each", result.fqn());
    }
    
    @Test
    public void testGetQualifiedName2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("setPosXY( int int )");
        JavaLocalVar jv = jm.getParameter("x");
        QualifiedName result = jv.getQualifiedName();
        
        assertEquals("x", result.fqn());
    }
    
    @Test
    public void testGetName1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        JavaLocalVar jv = jm.getLocalVariable("each", 1);
        String result = jv.getName();
        
        assertEquals("each", result);
    }
    
    @Test
    public void testGetName2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("setPosXY( int int )");
        JavaLocalVar jv = jm.getParameter("x");
        String result = jv.getName();
        
        assertEquals("x", result);
    }
    
    @Test
    public void testGetClassName1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        JavaLocalVar jv = jm.getLocalVariable("each", 1);
        String result = jv.getClassName();
        
        assertEquals("", result);
    }
    
    @Test
    public void testGetClassName2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("setPosXY( int int )");
        JavaLocalVar jv = jm.getParameter("x");
        String result = jv.getClassName();
        
        assertEquals("", result);
    }
    
    @Test
    public void testGetType1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        JavaLocalVar jv = jm.getLocalVariable("each", 1);
        String result = jv.getType();
        
        assertEquals("org.jtool.videostore.after.Rental", result);
    }
    
    @Test
    public void testGetType2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("setPosXY( int int )");
        JavaLocalVar jv = jm.getParameter("x");
        String result = jv.getType();
        
        assertEquals("int", result);
    }
    
    @Test
    public void testIsPrimitiveType1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        JavaLocalVar jv = jm.getLocalVariable("each", 1);
        boolean result = jv.isPrimitiveType();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsPrimitiveType2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("setPosXY( int int )");
        JavaLocalVar jv = jm.getParameter("x");
        boolean result = jv.isPrimitiveType();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsField1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        JavaLocalVar jv = jm.getLocalVariable("each", 1);
        boolean result = jv.isField();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsField2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("setPosXY( int int )");
        JavaLocalVar jv = jm.getParameter("x");
        boolean result = jv.isField();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsEnumConstant1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        JavaLocalVar jv = jm.getLocalVariable("each", 1);
        boolean result = jv.isEnumConstant();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsEnumConstant2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("setPosXY( int int )");
        JavaLocalVar jv = jm.getParameter("x");
        boolean result = jv.isEnumConstant();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsLocal1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        JavaLocalVar jv = jm.getLocalVariable("each", 1);
        boolean result = jv.isLocal();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsLocal2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("setPosXY( int int )");
        JavaLocalVar jv = jm.getParameter("x");
        boolean result = jv.isLocal();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsParameter1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        JavaLocalVar jv = jm.getLocalVariable("each", 1);
        boolean result = jv.isParameter();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsParameter2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("setPosXY( int int )");
        JavaLocalVar jv = jm.getParameter("x");
        boolean result = jv.isParameter();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsFinal1() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        JavaLocalVar jv = jm.getLocalVariable("each", 1);
        boolean result = jv.isFinal();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsFinal2() {
        JavaMethod jm = TetrisProject.getClass("Block").getMethod("setPosXY( int int )");
        JavaLocalVar jv = jm.getLocalVariable("it", 2);
        boolean result = jv.isFinal();
        
        assertTrue(result);
    }
}
