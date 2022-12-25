/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel;

import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import org.jtool.jxplatform.util.FlakyByExternalLib;
import java.util.List;
import java.util.Set;
import org.junit.experimental.categories.Category;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class JavaClassTest {
    
    private static JavaProject CSclassroomProject;
    private static JavaProject SimpleProject;
    private static JavaProject SliceProject;
    private static JavaProject TetrisProject;
    private static JavaProject VideoStoreProject;
    
    @BeforeClass
    public static void setUp() {
        CSclassroomProject = BuilderTestUtil.createProject("CS-classroom", "/lib/*", "/src");
        SimpleProject = BuilderTestUtil.createProject("Simple", "", "");
        SliceProject = BuilderTestUtil.createProject("Slice", "", "");
        TetrisProject = BuilderTestUtil.createProject("Tetris", "", "");
        VideoStoreProject = BuilderTestUtil.createProject("VideoStore", "/lib/*", "");
    }
    
    @AfterClass
    public static void tearDown() {
        CSclassroomProject.getModelBuilder().unbuild();
        SimpleProject.getModelBuilder().unbuild();
        SliceProject.getModelBuilder().unbuild();
        TetrisProject.getModelBuilder().unbuild();
        VideoStoreProject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testGetJavaProject1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        JavaProject result = jc.getJavaProject(); 
        
        assertEquals("VideoStore", result.getName());
    }
    
    @Test
    public void testGetJavaProject2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Rental");
        JavaProject result = jc.getJavaProject(); 
        
        assertEquals("VideoStore", result.getName());
    }
    
    @Test
    public void testGetJavaProject3() {
        JavaClass jc = SliceProject.getClass("R134");
        JavaProject result = jc.getJavaProject(); 
        
        assertEquals("Slice", result.getName());
    }
    
    @Test
    public void testGetFile1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        JavaFile result = jc.getFile(); 
        
        assertEquals("Customer.java", result.getName());
    }
    
    @Test
    public void testGetFile2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Rental");
        JavaFile result = jc.getFile(); 
        
        assertEquals("Rental.java", result.getName());
    }
    
    @Test
    public void testGetFile3() {
        JavaClass jc = SliceProject.getClass("R134");
        JavaFile result = jc.getFile(); 
        
        assertEquals("Test134.java", result.getName());
    }
    
    @Test
    public void testGetQualifiedName1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        QualifiedName result = jc.getQualifiedName();
        
        assertEquals("org.jtool.videostore.after.Customer", result.fqn());
    }
    
    @Test
    public void testGetQualifiedName2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Rental");
        QualifiedName result = jc.getQualifiedName();
        
        assertEquals("org.jtool.videostore.after.Rental", result.fqn());
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetQualifiedName3() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.ArrayList");
        QualifiedName result = jc.getQualifiedName();
        
        assertEquals("java.util.ArrayList", result.fqn());
    }
    
    @Test
    public void testGetName1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        String result = jc.getName();
        
        assertEquals("Customer", result);
    }
    
    @Test
    public void testGetName2() {
        JavaClass jc = SliceProject.getClass("R134");
        String result = jc.getName();
        
        assertEquals("R134", result);
    }
    
    @Test
    public void testGetName3() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.ArrayList");
        String result = jc.getName();
        
        assertEquals("ArrayList", result);
    }
    
    @Test
    public void testGetClassName1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        String result = jc.getClassName();
        
        assertEquals("org.jtool.videostore.after.Customer", result);
    }
    
    @Test
    public void testGetClassName2() {
        JavaClass jc = SliceProject.getClass("R134");
        String result = jc.getClassName();
        
        assertEquals("R134", result);
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetClassName3() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.ArrayList");
        String result = jc.getClassName();
        
        assertEquals("java.util.ArrayList", result);
    }
    
    @Test
    public void testIsInProject1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        boolean result = jc.isInProject();
        
        assertTrue(result);
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testIsInProject2() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.ArrayList");
        boolean result = jc.isInProject();
        
        assertFalse(result);
    }
    
    @Test
    public void testGetDeclaringClass1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        JavaClass result = jc.getDeclaringClass();
        
        assertNull(result);
    }
    
    @Test
    public void testGetDeclaringClass2() {
        JavaClass jc = CSclassroomProject.getClass("Sample110FX$LAMBDA1");
        JavaClass result = jc.getDeclaringClass();
        
        assertEquals("Sample110FX", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetDeclaringClass3() {
        JavaClass jc = CSclassroomProject.getClass("PanelB$1");
        JavaClass result = jc.getDeclaringClass();
        
        assertEquals("PanelB", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetDeclaringMethod1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        JavaMethod result = jc.getDeclaringMethod();
        
        assertNull(result);
    }
    
    @Test
    public void testGetDeclaringMethod2() {
        JavaClass jc = CSclassroomProject.getClass("Sample110FX$LAMBDA1");
        JavaMethod result = jc.getDeclaringMethod();
        
        assertEquals("Sample110FX#start( javafx.stage.Stage )", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetDeclaringMethod3() {
        JavaClass jc = CSclassroomProject.getClass("PanelB$1");
        JavaMethod result = jc.getDeclaringMethod();
        
        assertEquals("PanelB#PanelB( )", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetPackage1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        JavaPackage result = jc.getPackage();
        
        assertEquals("org.jtool.videostore.after", result.getName());
    }
    
    @Test
    public void testGetPackage2() {
        JavaClass jc = SliceProject.getClass("R134");
        JavaPackage result = jc.getPackage();
        
        assertEquals("(default)", result.getName());
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetPackage3() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.ArrayList");
        JavaPackage result = jc.getPackage();
        
        assertEquals("java.util", result.getName());
    }
    
    @Test
    public void testIsClass1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        boolean result = jc.isClass();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsClass2() {
        JavaClass jc = SliceProject.getClass("I134");
        boolean result = jc.isClass();
        
        assertFalse(result);
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testIsClass3() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.ArrayList");
        boolean result = jc.isClass();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsInterface1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        boolean result = jc.isInterface();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsInterface2() {
        JavaClass jc = SliceProject.getClass("I134");
        boolean result = jc.isInterface();
        
        assertTrue(result);
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testIsInterface3() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.List");
        boolean result = jc.isInterface();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsEnum1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        boolean result = jc.isEnum();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsEnum2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Movie.PriceCode");
        boolean result = jc.isEnum();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsAnonymous() {
        JavaClass jc = CSclassroomProject.getClass("PanelB$1");
        boolean result = jc.isAnonymous();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsLambda1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        boolean result = jc.isLambda();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsLambda2() {
        JavaClass jc = CSclassroomProject.getClass("Sample110FX$LAMBDA1");
        boolean result = jc.isLambda();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsPublic1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Rental");
        boolean result = jc.isPublic();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsPublic2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Price");
        boolean result = jc.isPublic();
        
        assertTrue(result);
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testIsPublic3() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.ArrayList");
        boolean result = jc.isPublic();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsProtected1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Rental");
        boolean result = jc.isProtected();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsProtected2() {
        JavaClass jc = SimpleProject.getClass("Test24.P24");
        boolean result = jc.isProtected();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsPrivate1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Rental");
        boolean result = jc.isPrivate();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsPrivate2() {
        JavaClass jc = CSclassroomProject.getClass("Sample12.ButtonListener");
        boolean result = jc.isPrivate();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsPrivate3() {
        JavaClass jc = SimpleProject.getClass("Test24.Q24");
        boolean result = jc.isPrivate();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsDefault1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Rental");
        boolean result = jc.isDefault();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsDefault2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Price");
        boolean result = jc.isDefault();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsFinal1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        boolean result = jc.isFinal();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsFinal2() {
        JavaClass jc = SimpleProject.getClass("Test24.Q24");
        boolean result = jc.isFinal();
        
        assertTrue(result);
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testIsFinal3() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.lang.String");
        boolean result = jc.isFinal();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsStatic1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        boolean result = jc.isStatic();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsStatic2() {
        JavaClass jc = SimpleProject.getClass("Test24.Q24");
        boolean result = jc.isStatic();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsStatic3() {
        JavaClass jc = CSclassroomProject.getClass("Sample12.ButtonListener");
        boolean result = jc.isStatic();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsAbstract1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        boolean result = jc.isAbstract();
        
        assertFalse(result);
    }
    
    @Test
    public void testIsAbstract2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Price");
        boolean result = jc.isAbstract();
        
        assertTrue(result);
    }
    
    @Test
    public void testGetInnerClasses1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Movie");
        List<JavaClass> result = jc.getInnerClasses();
        
        assertEquals(1, result.size());
        assertEquals("org.jtool.videostore.after.Movie.PriceCode", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetInnerClasses2() {
        JavaClass jc = CSclassroomProject.getClass("Sample12");
        List<JavaClass> result = jc.getInnerClasses();
        
        assertEquals(1, result.size());
        assertEquals("Sample12.ButtonListener", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetInnerClasses3() {
        JavaClass jc = SimpleProject.getClass("Test24");
        List<JavaClass> result = jc.getInnerClasses();
        
        assertEquals(2, result.size());
        assertEquals("Test24.P24;Test24.Q24", TestUtil.asSortedStrOf(result));
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetInnerClasses4() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.ArrayList");
        List<JavaClass> result = jc.getInnerClasses();
        
        assertEquals(4, result.size());
        assertEquals("java.util.ArrayList.ArrayListSpliterator;"
                   + "java.util.ArrayList.Itr;"
                   + "java.util.ArrayList.ListItr;"
                   + "java.util.ArrayList.SubList",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetSuperClassName1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        String result = jc.getSuperClassName();
        
        assertEquals("java.lang.Object", result);
    }
    
    @Test
    public void testGetSuperClassName2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Price");
        String result = jc.getSuperClassName();
        
        assertEquals("java.lang.Object", result);
    }
    
    @Test
    public void testGetSuperClassName3() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.RegularPrice");
        String result = jc.getSuperClassName();
        
        assertEquals("org.jtool.videostore.after.Price", result);
    }
    
    @Test
    public void testGetSuperClassName4() {
        JavaClass jc = SliceProject.getClass("R134");
        String result = jc.getSuperClassName();
        
        assertEquals("Q134", result);
    }
    
    @Test
    public void testGetSuperClassName5() {
        JavaClass jc = SliceProject.getClass("I134");
        String result = jc.getSuperClassName();
        
        assertNull(result);
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetSuperClassName6() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.ArrayList");
        String result = jc.getSuperClassName();
        
        assertEquals("java.util.AbstractList", result);
    }
    
    @Test
    public void testGetSuperClass1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        JavaClass result = jc.getSuperClass();
        
        assertEquals("java.lang.Object", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetSuperClass2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Price");
        JavaClass result = jc.getSuperClass();
        
        assertEquals("java.lang.Object", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetSuperClass3() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.RegularPrice");
        JavaClass result = jc.getSuperClass();
        
        assertEquals("org.jtool.videostore.after.Price", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetSuperClass4() {
        JavaClass jc = SliceProject.getClass("R134");
        JavaClass result = jc.getSuperClass();
        
        assertEquals("Q134", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetSuperClass5() {
        JavaClass jc = SliceProject.getClass("I134");
        JavaClass result = jc.getSuperClass();
        
        assertNull(result);
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetSuperClass6() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.ArrayList");
        JavaClass result = jc.getSuperClass();
        
        assertEquals("java.util.AbstractList", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetSuperInterfaceNames1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        Set<String> result = jc.getSuperInterfaceNames();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetSuperInterfaceNames2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Movie");
        Set<String> result = jc.getSuperInterfaceNames();
        
        assertEquals(1, result.size());
        assertEquals("java.io.Serializable", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetSuperInterfaceNames3() {
        JavaClass jc = SliceProject.getClass("R134");
        Set<String> result = jc.getSuperInterfaceNames();
        
        assertEquals(1, result.size());
        assertEquals("I134", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetSuperInterfaceNames4() {
        JavaClass jc = TetrisProject.getClass("Tetris");
        Set<String> result = jc.getSuperInterfaceNames();
        
        assertEquals(2, result.size());
        assertEquals("java.awt.event.KeyListener;java.lang.Runnable", TestUtil.asSortedStr(result));
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetSuperInterfaceNames5() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.ArrayList");
        Set<String> result = jc.getSuperInterfaceNames();
        
        assertEquals(4, result.size());
        assertEquals("java.io.Serializable;"
                   + "java.lang.Cloneable;"
                   + "java.util.List;"
                   + "java.util.RandomAccess",
            TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetSuperInterfaces1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        Set<JavaClass> result = jc.getSuperInterfaces();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetSuperInterfaces2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Movie");
        Set<JavaClass> result = jc.getSuperInterfaces();
        
        assertEquals(1, result.size());
        assertEquals("java.io.Serializable", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetSuperInterfaces3() {
        JavaClass jc = SliceProject.getClass("R134");
        Set<JavaClass> result = jc.getSuperInterfaces();
        
        assertEquals(1, result.size());
        assertEquals("I134", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetSuperInterfaces4() {
        JavaClass jc = TetrisProject.getClass("Tetris");
        Set<JavaClass> result = jc.getSuperInterfaces();
        
        assertEquals(2, result.size());
        assertEquals("java.awt.event.KeyListener;java.lang.Runnable", TestUtil.asSortedStrOf(result));
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetSuperInterfaces5() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.ArrayList");
        Set<JavaClass> result = jc.getSuperInterfaces();
        
        assertEquals(4, result.size());
        assertEquals("java.io.Serializable;"
                   + "java.lang.Cloneable;"
                   + "java.util.List;"
                   + "java.util.RandomAccess",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetFields1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        List<JavaField> result = jc.getFields();
        
        assertEquals(2, result.size());
        assertEquals("org.jtool.videostore.after.Customer#name;org.jtool.videostore.after.Customer#rentals",
                TestUtil.asSortedStrOf(jc.getFields()));
    }
    
    @Test
    public void testGetFields2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.RegularPrice");
        List<JavaField> result = jc.getFields();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetFields3() {
        JavaClass jc = SliceProject.getClass("I134");
        List<JavaField> result = jc.getFields();
        
        assertEquals(0, result.size());
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetFields4() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.ArrayList");
        List<JavaField> result = jc.getFields();
        
        assertEquals(7, result.size());
        assertEquals("java.util.ArrayList#DEFAULTCAPACITY_EMPTY_ELEMENTDATA;"
                   + "java.util.ArrayList#DEFAULT_CAPACITY;"
                   + "java.util.ArrayList#EMPTY_ELEMENTDATA;"
                   + "java.util.ArrayList#MAX_ARRAY_SIZE;"
                   + "java.util.ArrayList#elementData;"
                   + "java.util.ArrayList#serialVersionUID;"
                   + "java.util.ArrayList#size",
            TestUtil.asSortedStrOf(jc.getFields()));
    }
    
    @Test
    public void testGetField1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        JavaField result = jc.getField("rentals");
        
        assertEquals("org.jtool.videostore.after.Customer#rentals", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetField2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Price");
        JavaField result = jc.getField("priceCode");
        
        assertEquals("org.jtool.videostore.after.Price#priceCode", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetField3() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        JavaField result = jc.getField("title");
        
        assertNull(result);
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetField4() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.ArrayList");
        JavaField result = jc.getField("size");
        
        assertEquals("java.util.ArrayList#size", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetMethods1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        List<JavaMethod> result = jc.getMethods();
        
        assertEquals(6, result.size());
        assertEquals("org.jtool.videostore.after.Customer#Customer( java.lang.String );"
                   + "org.jtool.videostore.after.Customer#addRental( org.jtool.videostore.after.Rental );"
                   + "org.jtool.videostore.after.Customer#getName( );"
                   + "org.jtool.videostore.after.Customer#getTotalCharge( );"
                   + "org.jtool.videostore.after.Customer#getTotalFrequentRenterPoints( );"
                   + "org.jtool.videostore.after.Customer#statement( )",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetMethods2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.RegularPrice");
        List<JavaMethod> result = jc.getMethods();
        
        assertEquals(2, result.size());
        assertEquals("org.jtool.videostore.after.RegularPrice#RegularPrice( org.jtool.videostore.after.Movie.PriceCode );"
                   + "org.jtool.videostore.after.RegularPrice#getCharge( int )",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetMethods3() {
        JavaClass jc = SliceProject.getClass("I134");
        List<JavaMethod> result = jc.getMethods();
        
        assertEquals(1, result.size());
        assertEquals("I134#f( int )", TestUtil.asSortedStrOf(result));
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetMethods4() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.ArrayList");
        List<JavaMethod> result = jc.getMethods();
        
        assertEquals(63, result.size());
        String resultStr = TestUtil.asSortedStrOf(result);
        assertTrue(resultStr.startsWith("java.util.ArrayList#ArrayList( );"));
        assertTrue(resultStr.endsWith(";java.util.ArrayList#writeObject( java.io.ObjectOutputStream )"));
    }
    
    @Test
    public void testInitializer1() {
        JavaClass jc = SimpleProject.getClass("Test26");
        JavaMethod result = jc.getInitializer();
        
        assertEquals("Test26#.init( )", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetMethod1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        JavaMethod result = jc.getMethod("addRental( org.jtool.videostore.after.Rental )");
        
        assertEquals("org.jtool.videostore.after.Customer#addRental( org.jtool.videostore.after.Rental )",
                result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetMethod2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.RegularPrice");
        JavaMethod result = jc.getMethod("getCharge( int )");
        
        assertEquals("org.jtool.videostore.after.RegularPrice#getCharge( int )", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetMethod3() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        JavaMethod result = jc.getMethod("getCharge( int )");
        
        assertNull(result);
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetMethod4() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.ArrayList");
        JavaMethod result = jc.getMethod("add( java.lang.Object )");
        
        assertEquals("java.util.ArrayList#add( java.lang.Object )", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetUsedClasses1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        Set<JavaClass> result = jc.getUsedClasses();
        
        assertEquals(4, result.size());
        assertEquals("java.lang.String;"
                   + "java.util.ArrayList;"
                   + "java.util.List;"
                   + "org.jtool.videostore.after.Rental",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetUsedClasses2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Rental");
        Set<JavaClass> result = jc.getUsedClasses();
        
        assertEquals(1, result.size());
        assertEquals("org.jtool.videostore.after.Movie", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetUsedClasses3() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Movie");
        Set<JavaClass> result = jc.getUsedClasses();
        
        assertEquals(7, result.size());
        assertEquals("java.io.Serializable;"
                   + "java.lang.String;"
                   + "org.jtool.videostore.after.ChildrensPrice;"
                   + "org.jtool.videostore.after.Movie.PriceCode;"
                   + "org.jtool.videostore.after.NewReleasePrice;"
                   + "org.jtool.videostore.after.Price;"
                   + "org.jtool.videostore.after.RegularPrice",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetUsedClasses4() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Price");
        Set<JavaClass> result = jc.getUsedClasses();
        
        assertEquals(1, result.size());
        assertEquals("org.jtool.videostore.after.Movie.PriceCode", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetUsedClasses5() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.use.CustomerUse");
        Set<JavaClass> result = jc.getUsedClasses();
        
        assertEquals(1, result.size());
        assertEquals("org.jtool.videostore.after.Customer", TestUtil.asSortedStrOf(result));
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetUsedClasses6() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.ArrayList");
        Set<JavaClass> result = jc.getUsedClasses();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetChildren1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        Set<JavaClass> result = jc.getChildren();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetChildren2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Price");
        Set<JavaClass> result = jc.getChildren();
        
        assertEquals(3, result.size());
        assertEquals("org.jtool.videostore.after.ChildrensPrice;"
                   + "org.jtool.videostore.after.NewReleasePrice;"
                   + "org.jtool.videostore.after.RegularPrice",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetChildren3() {
        JavaClass jc = SliceProject.getClass("P134");
        Set<JavaClass> result = jc.getChildren();
        
        assertEquals(1, result.size());
        assertEquals("Q134", TestUtil.asSortedStrOf(result));
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetChildren4() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.ArrayList");
        Set<JavaClass> result = jc.getChildren();
        
        assertEquals(0, result.size());
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetChildren5() {
        JavaClass jc = TetrisProject.getExternalClass("java.awt.Canvas");
        Set<JavaClass> result = jc.getChildren();
        
        assertEquals(2, result.size());
        assertEquals("GameInfo;Pit", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testIsChildOf1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        JavaClass sc = VideoStoreProject.getClass("org.jtool.videostore.after.Rental");
        boolean result = jc.isChildOf(sc);
        
        assertFalse(result);
    }
    
    @Test
    public void testIsChildOf2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Price");
        JavaClass sc = VideoStoreProject.getClass("org.jtool.videostore.after.RegularPrice");
        boolean result = jc.isChildOf(sc);
        
        assertFalse(result);
    }
    
    @Test
    public void testIsChildOf3() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.RegularPrice");
        JavaClass sc = VideoStoreProject.getClass("org.jtool.videostore.after.Price");
        boolean result = jc.isChildOf(sc);
        
        assertTrue(result);
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testIsChildOf4() {
        JavaClass jc = TetrisProject.getClass("Pit");
        JavaClass sc = TetrisProject.getExternalClass("java.awt.Canvas");
        boolean result = jc.isChildOf(sc);
        
        assertTrue(result);
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testIsChildOf5() {
        JavaClass jc = TetrisProject.getExternalClass("java.awt.Canvas");
        JavaClass cc = TetrisProject.getClass("Pit");
        boolean result = jc.isChildOf(cc);
        
        assertFalse(result);
    }
    
    @Test
    public void testGetAllSuperClasses1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        List<JavaClass> result = jc.getAllSuperClasses();
        
        assertEquals(1, result.size());
        assertEquals("java.lang.Object", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAllSuperClasses2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Price");
        List<JavaClass> result = jc.getAllSuperClasses();
        
        assertEquals(1, result.size());
        assertEquals("java.lang.Object", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAllSuperClasses3() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.RegularPrice");
        List<JavaClass> result = jc.getAllSuperClasses();
        
        assertEquals(2, result.size());
        assertEquals("java.lang.Object;org.jtool.videostore.after.Price", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAllSuperClasses4() {
        JavaClass jc = SliceProject.getClass("R134");
        List<JavaClass> result = jc.getAllSuperClasses();
        
        assertEquals(3, result.size());
        assertEquals("P134;Q134;java.lang.Object", TestUtil.asSortedStrOf(result));
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetAllSuperClasses5() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.ArrayList");
        List<JavaClass> result = jc.getAllSuperClasses();
        
        assertEquals(3, result.size());
        assertEquals("java.lang.Object;"
                   + "java.util.AbstractCollection;"
                   + "java.util.AbstractList",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAllSuperInterfaces1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        Set<JavaClass> result = jc.getAllSuperInterfaces();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAllSuperInterfaces2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Movie");
        Set<JavaClass> result = jc.getAllSuperInterfaces();
        
        assertEquals(1, result.size());
        assertEquals("java.io.Serializable", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAllSuperInterfaces3() {
        JavaClass jc = SliceProject.getClass("R134");
        Set<JavaClass> result = jc.getAllSuperInterfaces();
        
        assertEquals(1, result.size());
        assertEquals("I134", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAllSuperInterfaces4() {
        JavaClass jc = TetrisProject.getClass("Tetris");
        Set<JavaClass> result = jc.getAllSuperInterfaces();
        
        assertEquals(3, result.size());
        assertEquals("java.awt.event.KeyListener;"
                   + "java.lang.Runnable;"
                   + "java.util.EventListener",
            TestUtil.asSortedStrOf(result));
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetAllSuperInterfaces5() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.ArrayList");
        Set<JavaClass> result = jc.getAllSuperInterfaces();
        
        assertEquals(6, result.size());
        assertEquals("java.io.Serializable;"
                   + "java.lang.Cloneable;"
                   + "java.lang.Iterable;"
                   + "java.util.Collection;"
                   + "java.util.List;"
                   + "java.util.RandomAccess",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAncestors1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        Set<JavaClass> result = jc.getAncestors();
        
        assertEquals(1, result.size());
        assertEquals("java.lang.Object", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAncestors2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Price");
        Set<JavaClass> result = jc.getAncestors();
        
        assertEquals(1, result.size());
        assertEquals("java.lang.Object", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAncestors3() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.RegularPrice");
        Set<JavaClass> result = jc.getAncestors();
        
        assertEquals(2, result.size());
        assertEquals("java.lang.Object;org.jtool.videostore.after.Price", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAncestors4() {
        JavaClass jc = SliceProject.getClass("R134");
        Set<JavaClass> result = jc.getAncestors();
        
        assertEquals(4, result.size());
        assertEquals("I134;P134;Q134;java.lang.Object", TestUtil.asSortedStrOf(result));
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetAncestors5() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.ArrayList");
        Set<JavaClass> result = jc.getAncestors();
        
        assertEquals(9, result.size());
        assertEquals("java.io.Serializable;"
                   + "java.lang.Cloneable;"
                   + "java.lang.Iterable;"
                   + "java.lang.Object;"
                   + "java.util.AbstractCollection;"
                   + "java.util.AbstractList;"
                   + "java.util.Collection;"
                   + "java.util.List;"
                   + "java.util.RandomAccess",
            TestUtil.asSortedStrOf(result));
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetAncestors6() {
        JavaClass jc = TetrisProject.getExternalClass("java.awt.Canvas");
        Set<JavaClass> result = jc.getAncestors();
        
        assertEquals(3, result.size());
        assertEquals("java.awt.Component;"
                   + "java.lang.Object;"
                   + "javax.accessibility.Accessible",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetDescendants1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        Set<JavaClass> result = jc.getDescendants();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetDescendants2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Price");
        Set<JavaClass> result = jc.getDescendants();
        
        assertEquals(3, result.size());
        assertEquals("org.jtool.videostore.after.ChildrensPrice;"
                   + "org.jtool.videostore.after.NewReleasePrice;"
                   + "org.jtool.videostore.after.RegularPrice",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetDescendants3() {
        JavaClass jc = SliceProject.getClass("P134");
        Set<JavaClass> result = jc.getDescendants();
        
        assertEquals(2, result.size());
        assertEquals("Q134;R134", TestUtil.asSortedStrOf(result));
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetDescendants4() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.ArrayList");
        Set<JavaClass> result = jc.getDescendants();
        
        assertEquals(0, result.size());
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetDescendants5() {
        JavaClass jc = TetrisProject.getExternalClass("java.awt.Canvas");
        Set<JavaClass> result = jc.getDescendants();
        
        assertEquals(2, result.size());
        assertEquals("GameInfo;Pit", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetEfferentClasses1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        Set<JavaClass> result = jc.getEfferentClasses();
        
        assertEquals(5, result.size());
        assertEquals("java.lang.String;"
                   + "java.util.ArrayList;"
                   + "java.util.List;"
                   + "org.jtool.videostore.after.Movie;"
                   + "org.jtool.videostore.after.Rental",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetEfferentClasses2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Rental");
        Set<JavaClass> result = jc.getEfferentClasses();
        
        assertEquals(1, result.size());
        assertEquals("org.jtool.videostore.after.Movie", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetEfferentClasses3() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Movie");
        Set<JavaClass> result = jc.getEfferentClasses();
        
        assertEquals(7, result.size());
        assertEquals("java.io.Serializable;"
                   + "java.lang.String;"
                   + "org.jtool.videostore.after.ChildrensPrice;"
                   + "org.jtool.videostore.after.Movie.PriceCode;"
                   + "org.jtool.videostore.after.NewReleasePrice;"
                   + "org.jtool.videostore.after.Price;"
                   + "org.jtool.videostore.after.RegularPrice",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetEfferentClasses4() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Price");
        Set<JavaClass> result = jc.getEfferentClasses();
        
        assertEquals(1, result.size());
        assertEquals("org.jtool.videostore.after.Movie.PriceCode", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetEfferentClasses5() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.use.CustomerUse");
        Set<JavaClass> result = jc.getEfferentClasses();
        
        assertEquals(3, result.size());
        assertEquals("java.io.PrintStream;"
                   + "java.lang.System;"
                   + "org.jtool.videostore.after.Customer",
            TestUtil.asSortedStrOf(result));
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetEfferentClasses6() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.ArrayList");
        Set<JavaClass> result = jc.getEfferentClasses();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetEfferentClassesInProject1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        Set<JavaClass> result = jc.getEfferentClassesInProject();
        
        assertEquals(2, result.size());
        assertEquals("org.jtool.videostore.after.Movie;"
                   + "org.jtool.videostore.after.Rental",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetEfferentClassesInProject2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Rental");
        Set<JavaClass> result = jc.getEfferentClassesInProject();
        
        assertEquals(1, result.size());
        assertEquals("org.jtool.videostore.after.Movie", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetEfferentClassesInProject3() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Movie");
        Set<JavaClass> result = jc.getEfferentClassesInProject();
        
        assertEquals(5, result.size());
        assertEquals("org.jtool.videostore.after.ChildrensPrice;"
                   + "org.jtool.videostore.after.Movie.PriceCode;"
                   + "org.jtool.videostore.after.NewReleasePrice;"
                   + "org.jtool.videostore.after.Price;"
                   + "org.jtool.videostore.after.RegularPrice",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetEfferentClassesInProject4() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Price");
        Set<JavaClass> result = jc.getEfferentClassesInProject();
        
        assertEquals(1, result.size());
        assertEquals("org.jtool.videostore.after.Movie.PriceCode", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetEfferentClassesInProject5() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.use.CustomerUse");
        Set<JavaClass> result = jc.getEfferentClassesInProject();
        
        assertEquals(1, result.size());
        assertEquals("org.jtool.videostore.after.Customer", TestUtil.asSortedStrOf(result));
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetEfferentClassesInProject6() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.ArrayList");
        Set<JavaClass> result = jc.getEfferentClassesInProject();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAfferentClasses1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        Set<JavaClass> result = jc.getAfferentClasses();
        
        assertEquals(2, result.size());
        assertEquals("org.jtool.videostore.after.CustomerTest;"
                   + "org.jtool.videostore.use.CustomerUse",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAfferentClasses2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Rental");
        Set<JavaClass> result = jc.getAfferentClasses();
        
        assertEquals(3, result.size());
        assertEquals("org.jtool.videostore.after.Customer;"
                   + "org.jtool.videostore.after.CustomerTest;"
                   + "org.jtool.videostore.after.RentalTest",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAfferentClasses3() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Movie");
        Set<JavaClass> result = jc.getAfferentClasses();
        
        assertEquals(5, result.size());
        assertEquals("org.jtool.videostore.after.Customer;"
                   + "org.jtool.videostore.after.CustomerTest;"
                   + "org.jtool.videostore.after.MovieTest;"
                   + "org.jtool.videostore.after.Rental;"
                   + "org.jtool.videostore.after.RentalTest",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAfferentClasses4() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Price");
        Set<JavaClass> result = jc.getAfferentClasses();
        
        assertEquals(4, result.size());
        assertEquals("org.jtool.videostore.after.ChildrensPrice;"
                   + "org.jtool.videostore.after.Movie;"
                   + "org.jtool.videostore.after.NewReleasePrice;"
                   + "org.jtool.videostore.after.RegularPrice",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAfferentClasses5() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.use.CustomerUse");
        Set<JavaClass> result = jc.getAfferentClasses();
        
        assertEquals(0, result.size());
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetAfferentClasses6() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.ArrayList");
        Set<JavaClass> result = jc.getAfferentClasses();
        
        assertEquals(2, result.size());
        assertEquals("org.jtool.videostore.after.Customer;"
                   + "org.jtool.videostore.before.Customer",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAfferentClassesInProject1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        Set<JavaClass> result = jc.getAfferentClassesInProject();
        
        assertEquals(2, result.size());
        assertEquals("org.jtool.videostore.after.CustomerTest;"
                   + "org.jtool.videostore.use.CustomerUse",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAfferentClassesInProject2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Rental");
        Set<JavaClass> result = jc.getAfferentClassesInProject();
        
        assertEquals(3, result.size());
        assertEquals("org.jtool.videostore.after.Customer;"
                   + "org.jtool.videostore.after.CustomerTest;"
                   + "org.jtool.videostore.after.RentalTest",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAfferentClassesInProject3() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Movie");
        Set<JavaClass> result = jc.getAfferentClassesInProject();
        
        assertEquals(5, result.size());
        assertEquals("org.jtool.videostore.after.Customer;"
                   + "org.jtool.videostore.after.CustomerTest;"
                   + "org.jtool.videostore.after.MovieTest;"
                   + "org.jtool.videostore.after.Rental;"
                   + "org.jtool.videostore.after.RentalTest",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAfferentClassesInProject4() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Price");
        Set<JavaClass> result = jc.getAfferentClassesInProject();
        
        assertEquals(4, result.size());
        assertEquals("org.jtool.videostore.after.ChildrensPrice;"
                   + "org.jtool.videostore.after.Movie;"
                   + "org.jtool.videostore.after.NewReleasePrice;"
                   + "org.jtool.videostore.after.RegularPrice",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAfferentClassesInProject5() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.use.CustomerUse");
        Set<JavaClass> result = jc.getAfferentClassesInProject();
        
        assertEquals(0, result.size());
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetAfferentClassesInProject6() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.ArrayList");
        Set<JavaClass> result = jc.getAfferentClassesInProject();
        
        assertEquals(2, result.size());
        assertEquals("org.jtool.videostore.after.Customer;"
                   + "org.jtool.videostore.before.Customer",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetObsoleteClasses1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        Set<JavaClass> result = jc.getObsoleteClasses();
        
        assertEquals(3, result.size());
        assertEquals("org.jtool.videostore.after.Customer;"
                   + "org.jtool.videostore.after.CustomerTest;"
                   + "org.jtool.videostore.use.CustomerUse",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetObsoleteClasses2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Rental");
        Set<JavaClass> result = jc.getObsoleteClasses();
        
        assertEquals(5, result.size());
        assertEquals("org.jtool.videostore.after.Customer;"
                   + "org.jtool.videostore.after.CustomerTest;"
                   + "org.jtool.videostore.after.Rental;"
                   + "org.jtool.videostore.after.RentalTest;"
                   + "org.jtool.videostore.use.CustomerUse",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetObsoleteClasses3() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Movie");
        Set<JavaClass> result = jc.getObsoleteClasses();
        
        assertEquals(7, result.size());
        assertEquals("org.jtool.videostore.after.Customer;"
                   + "org.jtool.videostore.after.CustomerTest;"
                   + "org.jtool.videostore.after.Movie;"
                   + "org.jtool.videostore.after.MovieTest;"
                   + "org.jtool.videostore.after.Rental;"
                   + "org.jtool.videostore.after.RentalTest;"
                   + "org.jtool.videostore.use.CustomerUse",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetObsoleteClasses4() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Price");
        Set<JavaClass> result = jc.getObsoleteClasses();
        
        assertEquals(11, result.size());
        assertEquals("org.jtool.videostore.after.ChildrensPrice;"
                   + "org.jtool.videostore.after.Customer;"
                   + "org.jtool.videostore.after.CustomerTest;"
                   + "org.jtool.videostore.after.Movie;"
                   + "org.jtool.videostore.after.MovieTest;"
                   + "org.jtool.videostore.after.NewReleasePrice;"
                   + "org.jtool.videostore.after.Price;"
                   + "org.jtool.videostore.after.RegularPrice;"
                   + "org.jtool.videostore.after.Rental;"
                   + "org.jtool.videostore.after.RentalTest;"
                   + "org.jtool.videostore.use.CustomerUse",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetObsoleteClasses5() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.use.CustomerUse");
        Set<JavaClass> result = jc.getObsoleteClasses();
        
        assertEquals(1, result.size());
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetObsoleteClasses6() {
        JavaClass jc = VideoStoreProject.getExternalClass("java.util.ArrayList");
        Set<JavaClass> result = jc.getObsoleteClasses();
        
        assertEquals(6, result.size());
        assertEquals("java.util.ArrayList;"
                   + "org.jtool.videostore.after.Customer;"
                   + "org.jtool.videostore.after.CustomerTest;"
                   + "org.jtool.videostore.before.Customer;"
                   + "org.jtool.videostore.before.CustomerTest;"
                   + "org.jtool.videostore.use.CustomerUse",
            TestUtil.asSortedStrOf(result));
    }
}
