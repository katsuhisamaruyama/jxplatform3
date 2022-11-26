/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal.refmodel;

import org.jtool.jxplatform.util.TestUtil;
import org.jtool.srcmodel.JavaProject;
import java.util.List;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class JClassCacheLibTest {
    
    private static JavaProject project;
    private static BytecodeClassStore bcStore;
    
    private static JClass customerClass;
    private static JClass priceClass;
    private static JClass regularPriceClass;
    
    private static JClass listClass;
    private static JClass arrayListClass;
    private static JClass assertClass;
    
    
    @BeforeClass
    public static void setUp() {
        project = RefModelTestUtil.createProjectFromSourceWithLibCache("VideoStore", "/lib/*", "");
        bcStore = project.getCFGStore().getBCStore();
        
        customerClass = bcStore.getJClass("org.jtool.videostore.after.Customer");
        priceClass = bcStore.getJClass("org.jtool.videostore.after.Price");
        regularPriceClass = bcStore.getJClass("org.jtool.videostore.after.RegularPrice");
        
        listClass = bcStore.getJClass("java.util.List");
        arrayListClass = bcStore.getJClass("java.util.ArrayList");
        assertClass = bcStore.getJClass("org.junit.Assert");
    }
    
    @AfterClass
    public static void tearDown() {
        project.getModelBuilder().unbuild();
    }
    
    @Test
    public void testInstanceOf1() {
        assertTrue(customerClass instanceof JClassInternal);
    }
    
    @Test
    public void testInstanceOf2() {
        assertTrue(priceClass instanceof JClassInternal);
    }
    
    @Test
    public void testInstanceOf3() {
        assertTrue(regularPriceClass instanceof JClassInternal);
    }
    
    @Test
    public void testInstanceOf4() {
        assertTrue(listClass instanceof JClassCache);
    }
    
    @Test
    public void testInstanceOf5() {
        assertTrue(arrayListClass instanceof JClassCache);
    }
    
    @Test
    public void testInstanceOf6() {
        assertTrue(assertClass instanceof JClassCache);
    }
    
    @Test
    public void testGetQualifiedName1() {
        assertEquals("org.jtool.videostore.after.Customer", customerClass.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName2() {
        assertEquals("org.jtool.videostore.after.Price", priceClass.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName3() {
        assertEquals("org.jtool.videostore.after.RegularPrice", regularPriceClass.getQualifiedName().fqn());
    }
    @Test
    public void testGetQualifiedName4() {
        assertEquals("java.util.List", listClass.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName5() {
        assertEquals("java.util.ArrayList", arrayListClass.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName6() {
        assertEquals("org.junit.Assert", assertClass.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetName1() {
        assertEquals("org.jtool.videostore.after.Customer", customerClass.getName());
    }
    
    @Test
    public void testGetName2() {
        assertEquals("org.jtool.videostore.after.Price", priceClass.getName());
    }
    
    @Test
    public void testGetName3() {
        assertEquals("org.jtool.videostore.after.RegularPrice", regularPriceClass.getName());
    }
    
    @Test
    public void testGetName4() {
        
        assertEquals("java.util.List", listClass.getName());
    }
    @Test
    public void testGetName5() {
        assertEquals("java.util.ArrayList", arrayListClass.getName());
    }
    
    @Test
    public void testGetName6() {
        assertEquals("org.junit.Assert", assertClass.getName());
    }
    
    @Test
    public void testGetSimpleName1() {
        assertEquals("Customer", customerClass.getSimpleName());
    }
    
    @Test
    public void testGetSimpleName2() {
        assertEquals("Price", priceClass.getSimpleName());
    }
    
    @Test
    public void testGetSimpleName3() {
        assertEquals("RegularPrice", regularPriceClass.getSimpleName());
    }
    
    @Test
    public void testGetSimpleName4() {
        assertEquals("List", listClass.getSimpleName());
    }
    
    @Test
    public void testGetSimpleName5() {
        assertEquals("ArrayList", arrayListClass.getSimpleName());
    }
    
    @Test
    public void testGetSimpleName6() {
        assertEquals("Assert", assertClass.getSimpleName());
    }
    
    @Test
    public void testGetMethods1() {
        assertEquals(6, customerClass.getMethods().size());
    }
    
    @Test
        public void testGetMethods2() {
        assertEquals(4, priceClass.getMethods().size());
    }
    
    @Test
    public void testGetMethods3() {
        assertEquals(2, regularPriceClass.getMethods().size());
    }
    
    @Test
    public void testGetMethods4() {
        assertEquals(41, listClass.getMethods().size());
    }
    
    @Test
    public void testGetMethods5() {
        assertEquals(63, arrayListClass.getMethods().size());
    }
    
    @Test
    public void testGetMethods6() {
        assertEquals(67, assertClass.getMethods().size());
    }
    
    @Test
    public void testGetMethod1() {
        assertEquals("org.jtool.videostore.after.Customer#addRental( org.jtool.videostore.after.Rental )",
                customerClass.getMethod("addRental( org.jtool.videostore.after.Rental )")
                .getQualifiedName().fqn());
    }
    
    @Test
    public void testGetMethod2() {
        assertEquals("org.jtool.videostore.after.Price#getCharge( int )",
                priceClass.getMethod("getCharge( int )")
                .getQualifiedName().fqn());
    }
    
    @Test
    public void testGetMethod3() {
        assertEquals("org.jtool.videostore.after.RegularPrice#getCharge( int )",
                regularPriceClass.getMethod("getCharge( int )")
                .getQualifiedName().fqn());
    }
    
    @Test
    public void testGetMethod4() {
        assertEquals("java.util.List#get( int )",
                listClass.getMethod("get( int )")
                .getQualifiedName().fqn());
    }
    
    @Test
    public void testGetMethod5() {
        assertEquals("java.util.ArrayList#contains( java.lang.Object )",
                arrayListClass.getMethod("contains( java.lang.Object )")
                .getQualifiedName().fqn());
    }
    
    @Test
    public void testGetMethodReturningNull1() {
        assertNull(customerClass.getMethod("addRental( org.jtool.videostore.after.Movie )"));
    }
    
    @Test
    public void testGetMethodReturningNull2() {
        assertNull(arrayListClass.getMethod("indexOf( java.lang.String )"));
    }
    
    @Test
    public void testGetFields1() {
        assertEquals(2, customerClass.getFields().size());
    }
    
    @Test
    public void testGetFields2() {
        assertEquals(1, priceClass.getFields().size());
    }
    
    @Test
    public void testGetFields3() {
        assertEquals(0, regularPriceClass.getFields().size());
    }
    
    @Test
    public void testGetFields4() {
        assertEquals(7, arrayListClass.getFields().size());
    }
    
    @Test
    public void testGetFields5() {
        assertEquals(0, assertClass.getFields().size());
    }
    
    @Test
    public void testGetField1() {
        assertEquals("org.jtool.videostore.after.Customer#rentals",
                customerClass.getField("rentals").getQualifiedName().fqn());
    }
    
    @Test
    public void testGetField2() {
        assertEquals("org.jtool.videostore.after.Price#priceCode",
                priceClass.getField("priceCode").getQualifiedName().fqn());
    }
    
    @Test
    public void testGetField3() {
        assertEquals("java.util.ArrayList#size",
                arrayListClass.getField("size").getQualifiedName().fqn());
    }
    
    @Test
    public void testGetFieldRetuningNull1() {
        assertNull(priceClass.getField("title"));
    }
    
    @Test
    public void testGetFieldRetuningNull2() {
        assertNull(arrayListClass.getField("length"));
    }
    
    @Test
    public void testGetSuperClass1() {
        assertEquals("java.lang.Object", customerClass.getSuperClass());
    }
    
    @Test
    public void testGetSuperClass2() {
        assertEquals("java.lang.Object", priceClass.getSuperClass());
    }
    
    @Test
    public void testGetSuperClass3() {
        assertEquals("org.jtool.videostore.after.Price", regularPriceClass.getSuperClass());
    }
    
    @Test
    public void testGetSuperClass4() {
        assertNull(listClass.getSuperClass());
    }
    
    @Test
    public void testGetSuperClass5() {
        assertEquals("java.util.AbstractList", arrayListClass.getSuperClass());
    }
    
    @Test
    public void testGetSuperClass6() {
        assertEquals("java.lang.Object", assertClass.getSuperClass());
    }
    
    @Test
    public void testGetSuperInterfaces1() {
        assertEquals(0, customerClass.getSuperInterfaces().size());
    }
    
    @Test
    public void testGetSuperInterfaces2() {
        assertEquals(0, priceClass.getSuperInterfaces().size());
    }
    
    @Test
    public void testGetSuperInterfaces3() {
        assertEquals(0, regularPriceClass.getSuperInterfaces().size());
    }
    
    @Test
    public void testGetSuperInterfaces4() {
        List<String> result = TestUtil.asSortedList(listClass.getSuperInterfaces());
        
        assertEquals(1, result.size());
        assertEquals("java.util.Collection", result.get(0));
    }
    
    @Test
    public void testGetSuperInterfaces5() {
        List<String> result = TestUtil.asSortedList(arrayListClass.getSuperInterfaces());
        
        assertEquals(4, result.size());
        assertEquals("java.io.Serializable", result.get(0));
        assertEquals("java.lang.Cloneable", result.get(1));
        assertEquals("java.util.List", result.get(2));
        assertEquals("java.util.RandomAccess", result.get(3));
    }
    @Test
    public void testGetSuperInterfaces6() {
        assertEquals(0, assertClass.getSuperInterfaces().size());
    }
    
    @Test
    public void testGetSuperClassChain1() {
        assertEquals(1, customerClass.getSuperClassChain().size());
        assertEquals("java.lang.Object", customerClass.getSuperClassChain().get(0).getName());
    }
    
    @Test
    public void testGetSuperClassChain2() {
        assertEquals(2, regularPriceClass.getSuperClassChain().size());
        assertEquals("org.jtool.videostore.after.Price", regularPriceClass.getSuperClassChain().get(0).getName());
        assertEquals("java.lang.Object", regularPriceClass.getSuperClassChain().get(1).getName());
    }
    
    @Test
    public void testGetSuperClassChain3() {
        assertEquals(0, listClass.getSuperClassChain().size());
    }
    
    @Test
    public void testGetSuperClassChain4() {
        assertEquals(3, arrayListClass.getSuperClassChain().size());
        assertEquals("java.util.AbstractList", arrayListClass.getSuperClassChain().get(0).getName());
        assertEquals("java.util.AbstractCollection", arrayListClass.getSuperClassChain().get(1).getName());
        assertEquals("java.lang.Object", arrayListClass.getSuperClassChain().get(2).getName());
    }
    
    @Test
    public void testGetSuperClassChain5() {
        assertEquals(1, assertClass.getSuperClassChain().size());
        assertEquals("java.lang.Object", assertClass.getSuperClassChain().get(0).getName());
    }
    
    @Test
    public void testIsInterface1() {
        assertFalse(customerClass.isInterface());
    }
    
    @Test
    public void testIsInterface2() {
        assertFalse(priceClass.isInterface());
    }
    
    @Test
    public void testIsInterface3() {
        assertFalse(regularPriceClass.isInterface());
    }
    
    @Test
    public void testIsInterface4() {
        assertTrue(listClass.isInterface());
    }
    
    @Test
    public void testIsInterface5() {
        assertFalse(arrayListClass.isInterface());
    }
    
    @Test
    public void testIsInterface6() {
        assertFalse(assertClass.isInterface());
    }
    
    @Test
    public void testIsInProject1() {
        assertTrue(customerClass.isInProject());
    }
    
    @Test
    public void testIsInProject2() {
        assertTrue(priceClass.isInProject());
    }
    
    @Test
    public void testIsInProject3() {
        assertTrue(regularPriceClass.isInProject());
    }
    
    @Test
    public void testIsInProject4() {
        assertFalse(listClass.isInProject());
    }
    
    @Test
    public void testIsInProject5() {
        assertFalse(arrayListClass.isInProject());
    }
    
    @Test
    public void testIsInProject6() {
        assertFalse(assertClass.isInProject());
    }
    
    @Test
    public void testGetAncestorClasses1() {
        List<String> result = TestUtil.asSortedList(customerClass.getAncestorClasses().stream()
                .map(o -> o.getName()));
        
        assertEquals(1, result.size());
        assertEquals("java.lang.Object", result.get(0));
    }
    
    @Test
    public void testGetAncestorClasses2() {
        List<String> result = TestUtil.asSortedList(priceClass.getAncestorClasses().stream()
                .map(o -> o.getName()));
        
        assertEquals(1, result.size());
        assertEquals("java.lang.Object", result.get(0));
    }
    
    @Test
    public void testGetAncestorClasses3() {
        List<String> result = TestUtil.asSortedList(regularPriceClass.getAncestorClasses().stream()
                .map(o -> o.getName()));
        
        assertEquals(2, result.size());
        assertEquals("java.lang.Object", result.get(0));
        assertEquals("org.jtool.videostore.after.Price", result.get(1));
    }
    
    @Test
    public void testGetAncestorClasses4() {
        assertEquals(0, listClass.getAncestorClasses().size());
    }
    
    @Test
    public void testGetAncestorClasses5() {
        List<String> result = TestUtil.asSortedList(arrayListClass.getAncestorClasses().stream()
                .map(o -> o.getName()));
        
        assertEquals(3, result.size());
        assertEquals("java.lang.Object", result.get(0));
        assertEquals("java.util.AbstractCollection", result.get(1));
        assertEquals("java.util.AbstractList", result.get(2));
    }
    
    @Test
    public void testGetAncestorClasses6() {
        List<String> result = TestUtil.asSortedList(assertClass.getAncestorClasses().stream()
                .map(o -> o.getName()));
        assertEquals(1, result.size());
        assertEquals("java.lang.Object", result.get(0));
    }
    
    @Test
    public void testGetDescendantClasses1() {
        assertEquals(0, customerClass.getDescendantClasses().size());
    }
    
    @Test
    public void testGetDescendantClasses2() {
        List<String> result = TestUtil.asSortedList(priceClass.getDescendantClasses().stream()
                .map(o -> o.getName()));
        
        assertEquals(3, result.size());
        assertEquals("org.jtool.videostore.after.ChildrensPrice", result.get(0));
        assertEquals("org.jtool.videostore.after.NewReleasePrice", result.get(1));
        assertEquals("org.jtool.videostore.after.RegularPrice", result.get(2));
    }
    
    @Test
    public void testGetDescendantClasses3() {
        List<String> result = TestUtil.asSortedList(listClass.getDescendantClasses().stream()
                .map(o -> o.getName()));
        
        assertEquals(61, result.size());
        assertEquals("com.sun.java.util.jar.pack.ConstantPool.Index", result.get(0));
    }
    
    @Test
    public void testGetDescendantClasses4() {
        List<String> result = TestUtil.asSortedList(arrayListClass.getDescendantClasses().stream()
                .map(o -> o.getName()));
        
        assertEquals(7, result.size());
        assertEquals("com.sun.tools.jdi.EventSetImpl", result.get(0));
    }
    
    @Test
    public void testGetDescendantClasses5() {
        assertEquals(0, assertClass.getDescendantClasses().size());
    }
}