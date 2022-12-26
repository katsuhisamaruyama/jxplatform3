/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel;

import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import java.util.Set;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class JavaFieldTest {
    
    private static JavaProject SimpleProject;
    private static JavaProject TetrisProject;
    private static JavaProject VideoStoreProject;
    
    @BeforeClass
    public static void setUp() {
        SimpleProject = BuilderTestUtil.getProject("Simple");
        TetrisProject = BuilderTestUtil.getProject("Tetris");
        VideoStoreProject = BuilderTestUtil.getProject("VideoStore");
    }
    
    @Test
    public void testGetJavaProject1() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        JavaProject result = jf.getJavaProject();
        
        assertEquals("VideoStore", result.getName());
    }
    
    @Test
    public void testGetJavaProject2() {
        JavaField jf = TetrisProject.getClass("Block").getField("pit");
        JavaProject result = jf.getJavaProject(); 
        
        assertEquals("Tetris", result.getName());
    }
    
    @Test
    public void testGetFile1() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        JavaFile result = jf.getFile();
        
        assertEquals("Customer.java", result.getName());
    }
    
    @Test
    public void testGetFile2() {
        JavaField jf = TetrisProject.getClass("Block").getField("pit");
        JavaFile result = jf.getFile();
        
        assertEquals("Block.java", result.getName());
    }
    
    @Test
    public void testGetQualifiedName1() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        QualifiedName result = jf.getQualifiedName();
        
        assertEquals("org.jtool.videostore.after.Customer#rentals", result.fqn());
    }
    
    @Test
    public void testGetQualifiedName2() {
        JavaField jf = TetrisProject.getClass("Block").getField("pit");
        QualifiedName result = jf.getQualifiedName();
        
        assertEquals("Block#pit", result.fqn());
    }
    
    @Test
    public void testGetName1() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        String result = jf.getName();
        
        assertEquals("rentals", result);
    }
    
    @Test
    public void testGetName2() {
        JavaField jf = TetrisProject.getClass("Block").getField("pit");
        String result = jf.getName();
        
        assertEquals("pit", result);
    }
    
    @Test
    public void testGetClassName1() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        String result = jf.getClassName();
        
        assertEquals("org.jtool.videostore.after.Customer", result);
    }
    
    @Test
    public void testGetClassName2() {
        JavaField jf = TetrisProject.getClass("Block").getField("pit");
        String result = jf.getClassName();
        
        assertEquals("Block", result);
    }
    
    @Test
    public void testGetType1() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        String result = jf.getType();
        
        assertEquals("java.util.List<org.jtool.videostore.after.Rental>", result);
    }
    
    @Test
    public void testGetType2() {
        JavaField jf = TetrisProject.getClass("Block").getField("posX");
        String result = jf.getType();
        
        assertEquals("int", result);
    }
    
    @Test
    public void testIsPrimitiveType1() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        boolean result = jf.isPrimitiveType();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsPrimitiveType2() {
        JavaField jf = TetrisProject.getClass("Block").getField("pit");
        boolean result = jf.isPrimitiveType();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsPrimitiveType3() {
        JavaField jf = TetrisProject.getClass("Block").getField("posX");
        boolean result = jf.isPrimitiveType();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsInProject1() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        boolean result = jf.isInProject();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsInProject2() {
        JavaField jf = VideoStoreProject.getExternalClass("java.util.ArrayList").getField("size");
        boolean result = jf.isInProject();
        
        assertFalse(result);
    }
    
    @Test
    public void testGetDeclaringClass1() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        JavaClass result = jf.getDeclaringClass();
        
        assertEquals("org.jtool.videostore.after.Customer", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetDeclaringClass2() {
        JavaField jf = TetrisProject.getClass("Block").getField("pit");
        JavaClass result = jf.getDeclaringClass();
        
        assertEquals("Block", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testIsField1() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        boolean result = jf.isField();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsField2() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Movie.PriceCode").getField("CHILDRENS");
        boolean result = jf.isField();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsEnumConstant1() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        boolean result = jf.isEnumConstant();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsEnumConstant2() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Movie.PriceCode").getField("CHILDRENS");
        boolean result = jf.isEnumConstant();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsLocal1() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        boolean result = jf.isLocal();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsLocal2() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Movie.PriceCode").getField("CHILDRENS");
        boolean result = jf.isLocal();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsParameter1() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        boolean result = jf.isParameter();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsParameter2() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Movie.PriceCode").getField("CHILDRENS");
        boolean result = jf.isParameter();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsPublic1() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        boolean result = jf.isPublic();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsPublic2() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("name");
        boolean result = jf.isPublic();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsPublic3() {
        JavaField jf = TetrisProject.getClass("Block").getField("NUMBER_OF");
        boolean result = jf.isPublic();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsPublic4() {
        JavaField jf = TetrisProject.getClass("Block").getField("offsetX");
        boolean result = jf.isPublic();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsProtected1() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        boolean result = jf.isProtected();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsProtected2() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("name");
        boolean result = jf.isProtected();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsProtected3() {
        JavaField jf = TetrisProject.getClass("Block").getField("NUMBER_OF");
        boolean result = jf.isProtected();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsProtected4() {
        JavaField jf = TetrisProject.getClass("Block").getField("offsetX");
        boolean result = jf.isProtected();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsPrivate1() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        boolean result = jf.isPrivate();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsPrivate2() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("name");
        boolean result = jf.isPublic();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsPrivate3() {
        JavaField jf = TetrisProject.getClass("Block").getField("NUMBER_OF");
        boolean result = jf.isPrivate();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsPrivate4() {
        JavaField jf = TetrisProject.getClass("Block").getField("offsetX");
        boolean result = jf.isPrivate();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsDefault1() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        boolean result = jf.isDefault();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsDefault2() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("name");
        boolean result = jf.isDefault();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsDefault3() {
        JavaField jf = TetrisProject.getClass("Block").getField("NUMBER_OF");
        boolean result = jf.isDefault();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsDefault4() {
        JavaField jf = TetrisProject.getClass("Block").getField("offsetX");
        boolean result = jf.isDefault();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsFinal1() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        boolean result = jf.isFinal();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsFinal2() {
        JavaField jf = TetrisProject.getClass("Block").getField("NUMBER_OF");
        boolean result = jf.isFinal();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsStatic1() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        boolean result = jf.isStatic();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsStatic2() {
        JavaField jf = TetrisProject.getClass("Block").getField("NUMBER_OF");
        boolean result = jf.isStatic();
        
        assertTrue(result);
    }
    
    @Test
    public void testGetAccessedFields1() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("name");
        Set<JavaField> result = jf.getAccessedFields();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessedFields2() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        Set<JavaField> result = jf.getAccessedFields();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessedFields3() {
        JavaField jf = TetrisProject.getClass("Pit").getField("backgroundColor");
        Set<JavaField> result = jf.getAccessedFields();
        
        assertEquals(1, result.size());
        assertEquals("java.awt.Color#black", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAccessedFields4() {
        JavaField jf = TetrisProject.getClass("Pit").getField("columns");
        Set<JavaField> result = jf.getAccessedFields();
        
        assertEquals(1, result.size());
        assertEquals("Tetris#COLUMNS", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAccessedFields5() {
        JavaField jf = TetrisProject.getClass("Tetris").getField("COLUMNS");
        Set<JavaField> result = jf.getAccessedFields();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessedFieldsInProject1() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("name");
        Set<JavaField> result = jf.getAccessedFieldsInProject();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessedFieldsInProject2() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        Set<JavaField> result = jf.getAccessedFieldsInProject();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessedFieldsInProject3() {
        JavaField jf = TetrisProject.getClass("Pit").getField("backgroundColor");
        Set<JavaField> result = jf.getAccessedFieldsInProject();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessedFieldsInProject4() {
        JavaField jf = TetrisProject.getClass("Pit").getField("columns");
        Set<JavaField> result = jf.getAccessedFieldsInProject();
        
        assertEquals(1, result.size());
        assertEquals("Tetris#COLUMNS", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAccessedFieldsInProject5() {
        JavaField jf = TetrisProject.getClass("Tetris").getField("COLUMNS");
        Set<JavaField> result = jf.getAccessedFieldsInProject();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessingFields1() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("name");
        Set<JavaField> result = jf.getAccessingFields();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessingFields2() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        Set<JavaField> result = jf.getAccessingFields();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessingFields3() {
        JavaField jf = TetrisProject.getClass("Pit").getField("backgroundColor");
        Set<JavaField> result = jf.getAccessingFields();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessingFields4() {
        JavaField jf = TetrisProject.getClass("Pit").getField("columns");
        Set<JavaField> result = jf.getAccessingFields();
        
        assertEquals(1, result.size());
        assertEquals("Pit#initPosX", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAccessingFields5() {
        JavaField jf = TetrisProject.getClass("Tetris").getField("COLUMNS");
        Set<JavaField> result = jf.getAccessingFields();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetAccessingFieldsInProject1() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("name");
        Set<JavaField> result = jf.getAccessingFieldsInProject();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessingFieldsInProject2() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        Set<JavaField> result = jf.getAccessingFieldsInProject();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessingFieldsInProject3() {
        JavaField jf = TetrisProject.getClass("Pit").getField("backgroundColor");
        Set<JavaField> result = jf.getAccessingFieldsInProject();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessingFieldsInProject4() {
        JavaField jf = TetrisProject.getClass("Pit").getField("columns");
        Set<JavaField> result = jf.getAccessingFieldsInProject();
        
        assertEquals(1, result.size());
        assertEquals("Pit#initPosX", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAccessingFieldsInProject5() {
        JavaField jf = TetrisProject.getClass("Tetris").getField("COLUMNS");
        Set<JavaField> result = jf.getAccessingFieldsInProject();
        
        assertEquals(1, result.size());
        assertEquals("Pit#columns", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetCalledMethods1() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("name");
        Set<JavaMethod> result = jf.getCalledMethods();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetCalledMethods2() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        Set<JavaMethod> result = jf.getCalledMethods();
        
        assertEquals(1, result.size());
        assertEquals("java.util.ArrayList#ArrayList( )", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetCalledMethods3() {
        JavaField jf = TetrisProject.getClass("Pit").getField("backgroundColor");
        Set<JavaMethod> result = jf.getCalledMethods();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetCalledMethods4() {
        JavaField jf = SimpleProject.getClass("Test27").getField("list");
        Set<JavaMethod> result = jf.getCalledMethods();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetCalledMethods5() {
        JavaField jf = TetrisProject.getClass("Tile").getField("posX");
        Set<JavaMethod> result = jf.getCalledMethods();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetCalledMethodsInProject1() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("name");
        Set<JavaMethod> result = jf.getCalledMethodsInProject();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetCalledMethodsInProject2() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        Set<JavaMethod> result = jf.getCalledMethodsInProject();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetCalledMethodsInProject3() {
        JavaField jf = TetrisProject.getClass("Pit").getField("backgroundColor");
        Set<JavaMethod> result = jf.getCalledMethodsInProject();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetCalledMethodsInProject4() {
        JavaField jf = SimpleProject.getClass("Test27").getField("list");
        Set<JavaMethod> result = jf.getCalledMethodsInProject();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetCalledMethodsInProject5() {
        JavaField jf = TetrisProject.getClass("Tile").getField("posX");
        Set<JavaMethod> result = jf.getCalledMethodsInProject();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessingMethods1() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("name");
        Set<JavaMethod> result = jf.getAccessingMethods();
        
        assertEquals(2, result.size());
        assertEquals("org.jtool.videostore.after.Customer#Customer( java.lang.String );"
                   + "org.jtool.videostore.after.Customer#getName( )",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAccessingMethods2() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        Set<JavaMethod> result = jf.getAccessingMethods();
        
        assertEquals(4, result.size());
        assertEquals("org.jtool.videostore.after.Customer#addRental( org.jtool.videostore.after.Rental );"
                   + "org.jtool.videostore.after.Customer#getTotalCharge( );"
                   + "org.jtool.videostore.after.Customer#getTotalFrequentRenterPoints( );"
                   + "org.jtool.videostore.after.Customer#statement( )",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAccessingMethods3() {
        JavaField jf = TetrisProject.getClass("Pit").getField("backgroundColor");
        Set<JavaMethod> result = jf.getAccessingMethods();
        
        assertEquals(3, result.size());
        assertEquals("Pit#gameOver( );Pit#init( );Pit#update( )", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAccessingMethods4() {
        JavaField jf = SimpleProject.getClass("Test27").getField("list");
        Set<JavaMethod> result = jf.getAccessingMethods();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessingMethods5() {
        JavaField jf = TetrisProject.getClass("Tile").getField("posX");
        Set<JavaMethod> result = jf.getAccessingMethods();
        
        assertEquals(5, result.size());
        assertEquals("Tile#Tile( int int java.awt.Color );"
                   + "Tile#getPosX( );"
                   + "Tile#paint( java.awt.Graphics int int );"
                   + "Tile#setPosX( int );"
                   + "Tile#setPosXY( int int )",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAccessingMethodsInProject1() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("name");
        Set<JavaMethod> result = jf.getAccessingMethodsInProject();
        
        assertEquals(2, result.size());
        assertEquals("org.jtool.videostore.after.Customer#Customer( java.lang.String );"
                   + "org.jtool.videostore.after.Customer#getName( )",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAccessingMethodsInProject2() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        Set<JavaMethod> result = jf.getAccessingMethodsInProject();
        
        assertEquals(4, result.size());
        assertEquals("org.jtool.videostore.after.Customer#addRental( org.jtool.videostore.after.Rental );"
                   + "org.jtool.videostore.after.Customer#getTotalCharge( );"
                   + "org.jtool.videostore.after.Customer#getTotalFrequentRenterPoints( );"
                   + "org.jtool.videostore.after.Customer#statement( )",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAccessingMethodsInProject3() {
        JavaField jf = TetrisProject.getClass("Pit").getField("backgroundColor");
        Set<JavaMethod> result = jf.getAccessingMethodsInProject();
        
        assertEquals(3, result.size());
        assertEquals("Pit#gameOver( );Pit#init( );Pit#update( )", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAccessingMethodsInProject4() {
        JavaField jf = SimpleProject.getClass("Test27").getField("list");
        Set<JavaMethod> result = jf.getAccessingMethodsInProject();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAccessingMethodsInProject5() {
        JavaField jf = TetrisProject.getClass("Tile").getField("posX");
        Set<JavaMethod> result = jf.getAccessingMethodsInProject();
        
        assertEquals(5, result.size());
        assertEquals("Tile#Tile( int int java.awt.Color );"
                   + "Tile#getPosX( );"
                   + "Tile#paint( java.awt.Graphics int int );"
                   + "Tile#setPosX( int );"
                   + "Tile#setPosXY( int int )",
            TestUtil.asSortedStrOf(result));
    }
}
