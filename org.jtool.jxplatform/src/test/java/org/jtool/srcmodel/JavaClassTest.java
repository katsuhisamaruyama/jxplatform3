/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel;

import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import java.util.List;
import java.util.Set;
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
    
    @Test
    public void testIsInProject1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        boolean result = jc.isInProject();
        
        assertTrue(result);
    }
    
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
        JavaClass jc = CSclassroomProject.getClass("Sample110FX#start( javafx.stage.Stage )$1");
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
        JavaClass jc = CSclassroomProject.getClass("Sample110FX#start( javafx.stage.Stage )$1");
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
        JavaClass jc = CSclassroomProject.getClass("Sample110FX#start( javafx.stage.Stage )$1");
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
    
    @Test
    public void testInitializer1() {
        JavaClass jc = SimpleProject.getClass("Test26");
        JavaMethod result = jc.getInitializer();
        
        assertEquals("Test26#.init( )", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testMethods1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        JavaMethod result = jc.getMethod("addRental( org.jtool.videostore.after.Rental )");
        
        assertEquals("org.jtool.videostore.after.Customer#addRental( org.jtool.videostore.after.Rental )",
                result.getQualifiedName().fqn());
    }
    
    @Test
    public void testMethods2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.RegularPrice");
        JavaMethod result = jc.getMethod("getCharge( int )");
        
        assertEquals("org.jtool.videostore.after.RegularPrice#getCharge( int )", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testMethods3() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        JavaMethod result = jc.getMethod("getCharge( int )");
        
        assertNull(result);
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
    
    @Test
    public void testGetChildren1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        List<JavaClass> result = jc.getChildren();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetChildren2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Price");
        List<JavaClass> result = jc.getChildren();
        
        assertEquals(3, result.size());
        assertEquals("org.jtool.videostore.after.ChildrensPrice;"
                   + "org.jtool.videostore.after.NewReleasePrice;"
                   + "org.jtool.videostore.after.RegularPrice",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetChildren3() {
        JavaClass jc = SliceProject.getClass("P134");
        List<JavaClass> result = jc.getChildren();
        
        assertEquals(1, result.size());
        assertEquals("Q134", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testIsChildOf1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        JavaClass cc = VideoStoreProject.getClass("org.jtool.videostore.after.Rental");
        boolean result = jc.isChildOf(cc);
        
        assertFalse(result);
    }
    
    @Test
    public void testIsChildOf2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Price");
        JavaClass cc = VideoStoreProject.getClass("org.jtool.videostore.after.RegularPrice");
        boolean result = jc.isChildOf(cc);
        
        assertFalse(result);
    }
    
    @Test
    public void testIsChildOf3() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.RegularPrice");
        JavaClass cc = VideoStoreProject.getClass("org.jtool.videostore.after.Price");
        boolean result = jc.isChildOf(cc);
        
        assertTrue(result);
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
    
    @Test
    public void testGetAllSuperInterfaces1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        List<JavaClass> result = jc.getAllSuperInterfaces();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAllSuperInterfaces2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Movie");
        List<JavaClass> result = jc.getAllSuperInterfaces();
        
        assertEquals(1, result.size());
        assertEquals("java.io.Serializable", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAllSuperInterfaces3() {
        JavaClass jc = SliceProject.getClass("R134");
        List<JavaClass> result = jc.getAllSuperInterfaces();
        
        assertEquals(1, result.size());
        assertEquals("I134", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAllSuperInterfaces4() {
        JavaClass jc = TetrisProject.getClass("Tetris");
        List<JavaClass> result = jc.getAllSuperInterfaces();
        
        assertEquals(2, result.size());
        assertEquals("java.awt.event.KeyListener;java.lang.Runnable", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAncestors1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        List<JavaClass> result = jc.getAncestors();
        
        assertEquals(1, result.size());
        assertEquals("java.lang.Object", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAncestors2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Price");
        List<JavaClass> result = jc.getAncestors();
        
        assertEquals(1, result.size());
        assertEquals("java.lang.Object", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAncestors3() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.RegularPrice");
        List<JavaClass> result = jc.getAncestors();
        
        assertEquals(2, result.size());
        assertEquals("java.lang.Object;org.jtool.videostore.after.Price", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetAncestors4() {
        JavaClass jc = SliceProject.getClass("R134");
        List<JavaClass> result = jc.getAncestors();
        
        assertEquals(4, result.size());
        assertEquals("I134;P134;Q134;java.lang.Object", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetDescendants1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        List<JavaClass> result = jc.getChildren();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetDescendants2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Price");
        List<JavaClass> result = jc.getChildren();
        
        assertEquals(3, result.size());
        assertEquals("org.jtool.videostore.after.ChildrensPrice;"
                   + "org.jtool.videostore.after.NewReleasePrice;"
                   + "org.jtool.videostore.after.RegularPrice",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetDescendants3() {
        JavaClass jc = SliceProject.getClass("P134");
        List<JavaClass> result = jc.getChildren();
        
        assertEquals(1, result.size());
        assertEquals("Q134", TestUtil.asSortedStrOf(result));
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
}