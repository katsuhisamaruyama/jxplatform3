/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.refmodel;

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
    public void testInstanceOf() {
        assertTrue(customerClass instanceof JClassInternal);
        
        assertTrue(priceClass instanceof JClassInternal);
        
        assertTrue(regularPriceClass instanceof JClassInternal);
        
        assertTrue(listClass instanceof JClassCache);
        
        assertTrue(arrayListClass instanceof JClassCache);
        
        assertTrue(assertClass instanceof JClassCache);
    }
    
    @Test
    public void testGetQualifiedName() {
        assertEquals("org.jtool.videostore.after.Customer", customerClass.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Price", priceClass.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.RegularPrice", regularPriceClass.getQualifiedName().fqn());
        
        assertEquals("java.util.List", listClass.getQualifiedName().fqn());
        
        assertEquals("java.util.ArrayList", arrayListClass.getQualifiedName().fqn());
        
        assertEquals("org.junit.Assert", assertClass.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetName() {
        assertEquals("org.jtool.videostore.after.Customer", customerClass.getName());
        
        assertEquals("org.jtool.videostore.after.Price", priceClass.getName());
        
        assertEquals("org.jtool.videostore.after.RegularPrice", regularPriceClass.getName());
        
        assertEquals("java.util.List", listClass.getName());
        
        assertEquals("java.util.ArrayList", arrayListClass.getName());
        
        assertEquals("org.junit.Assert", assertClass.getName());
    }
    
    @Test
    public void testGetSimpleName() {
        assertEquals("Customer", customerClass.getSimpleName());
        
        assertEquals("Price", priceClass.getSimpleName());
        
        assertEquals("RegularPrice", regularPriceClass.getSimpleName());
        
        assertEquals("List", listClass.getSimpleName());
        
        assertEquals("ArrayList", arrayListClass.getSimpleName());
        
        assertEquals("Assert", assertClass.getSimpleName());
    }
    
    @Test
    public void testGetMethods() {
        assertEquals(6, customerClass.getMethods().size());
        
        assertEquals(4, priceClass.getMethods().size());
        
        assertEquals(2, regularPriceClass.getMethods().size());
        
        assertEquals(41, listClass.getMethods().size());
        
        assertEquals(63, arrayListClass.getMethods().size());
        
        assertEquals(67, assertClass.getMethods().size());
    }
    
    @Test
    public void testGetMethod() {
        assertEquals("org.jtool.videostore.after.Customer#addRental( org.jtool.videostore.after.Rental )",
                customerClass.getMethod("addRental( org.jtool.videostore.after.Rental )")
                .getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Price#getCharge( int )",
                priceClass.getMethod("getCharge( int )")
                .getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.RegularPrice#getCharge( int )",
                regularPriceClass.getMethod("getCharge( int )")
                .getQualifiedName().fqn());
        
        assertEquals("java.util.List#get( int )",
                listClass.getMethod("get( int )")
                .getQualifiedName().fqn());
        
        assertEquals("java.util.ArrayList#contains( java.lang.Object )",
                arrayListClass.getMethod("contains( java.lang.Object )")
                .getQualifiedName().fqn());
    }
    
    @Test
    public void testGetMethodReturningNull() {
        assertNull(customerClass.getMethod("addRental( org.jtool.videostore.after.Movie )"));
        
        assertNull(arrayListClass.getMethod("indexOf( java.lang.String )"));
    }
    
    @Test
    public void testGetFields() {
        assertEquals(2, customerClass.getFields().size());
        
        assertEquals(1, priceClass.getFields().size());
        
        assertEquals(0, regularPriceClass.getFields().size());
        
        assertEquals(7, arrayListClass.getFields().size());
        
        assertEquals(0, assertClass.getFields().size());
    }
    
    @Test
    public void testGetField() {
        assertEquals("org.jtool.videostore.after.Customer#rentals",
                customerClass.getField("rentals")
                .getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Price#priceCode",
                priceClass.getField("priceCode")
                .getQualifiedName().fqn());
        
        assertEquals("java.util.ArrayList#size",
                arrayListClass.getField("size")
                .getQualifiedName().fqn());
    }
    
    @Test
    public void testGetFieldRetuningNull() {
        assertNull(priceClass.getField("title"));
        
        assertNull(arrayListClass.getField("length"));
    }
    
    @Test
    public void testGetSuperClass() {
        assertEquals("java.lang.Object", customerClass.getSuperClass());
        
        assertEquals("java.lang.Object", priceClass.getSuperClass());
        
        assertEquals("org.jtool.videostore.after.Price", regularPriceClass.getSuperClass());
        
        assertNull(listClass.getSuperClass());
        
        assertEquals("java.util.AbstractList", arrayListClass.getSuperClass());
        
        assertEquals("java.lang.Object", assertClass.getSuperClass());
    }
    
    @Test
    public void testGetSuperInterfaces() {
        assertEquals(0, customerClass.getSuperInterfaces().size());
        
        assertEquals(0, priceClass.getSuperInterfaces().size());
        
        assertEquals(0, regularPriceClass.getSuperInterfaces().size());
        
        List<String> lresult = TestUtil.asSortedList(listClass.getSuperInterfaces());
        assertEquals(1, lresult.size());
        assertEquals("java.util.Collection", lresult.get(0));
        
        List<String> aresult = TestUtil.asSortedList(arrayListClass.getSuperInterfaces());
        assertEquals(4, aresult.size());
        assertEquals("java.io.Serializable", aresult.get(0));
        assertEquals("java.lang.Cloneable", aresult.get(1));
        assertEquals("java.util.List", aresult.get(2));
        assertEquals("java.util.RandomAccess", aresult.get(3));
        
        assertEquals(0, assertClass.getSuperInterfaces().size());
    }
    
    @Test
    public void testGetSuperClassChain() {
        assertEquals(1, customerClass.getSuperClassChain().size());
        assertEquals("java.lang.Object", customerClass.getSuperClassChain().get(0).getName());
        
        assertEquals(2, regularPriceClass.getSuperClassChain().size());
        assertEquals("org.jtool.videostore.after.Price", regularPriceClass.getSuperClassChain().get(0).getName());
        assertEquals("java.lang.Object", regularPriceClass.getSuperClassChain().get(1).getName());
        
        assertEquals(0, listClass.getSuperClassChain().size());
        
        assertEquals(3, arrayListClass.getSuperClassChain().size());
        assertEquals("java.util.AbstractList", arrayListClass.getSuperClassChain().get(0).getName());
        assertEquals("java.util.AbstractCollection", arrayListClass.getSuperClassChain().get(1).getName());
        assertEquals("java.lang.Object", arrayListClass.getSuperClassChain().get(2).getName());
        
        assertEquals(1, assertClass.getSuperClassChain().size());
        assertEquals("java.lang.Object", assertClass.getSuperClassChain().get(0).getName());
    }
    
    @Test
    public void testIsInterface() {
        assertFalse(customerClass.isInterface());
        
        assertFalse(priceClass.isInterface());
        
        assertFalse(regularPriceClass.isInterface());
        
        assertTrue(listClass.isInterface());
        
        assertFalse(arrayListClass.isInterface());
        
        assertFalse(assertClass.isInterface());
    }
    
    @Test
    public void testIsInProject() {
        assertTrue(customerClass.isInProject());
        
        assertTrue(priceClass.isInProject());
        
        assertTrue(regularPriceClass.isInProject());
        
        assertFalse(listClass.isInProject());
        
        assertFalse(arrayListClass.isInProject());
        
        assertFalse(assertClass.isInProject());
    }
    
    @Test
    public void testGetAncestorClasses() {
        List<String> cresult = TestUtil.asSortedList(customerClass.getAncestorClasses().stream().map(o -> o.getName()));
        assertEquals(1, cresult.size());
        assertEquals("java.lang.Object", cresult.get(0));
        
        List<String> presult = TestUtil.asSortedList(priceClass.getAncestorClasses().stream().map(o -> o.getName()));
        assertEquals(1, presult.size());
        assertEquals("java.lang.Object", presult.get(0));
        
        List<String> rresult = TestUtil.asSortedList(regularPriceClass.getAncestorClasses().stream().map(o -> o.getName()));
        assertEquals(2, rresult.size());
        assertEquals("java.lang.Object", rresult.get(0));
        assertEquals("org.jtool.videostore.after.Price", rresult.get(1));
        
        assertEquals(0, listClass.getAncestorClasses().size());
        
        List<String> aresult = TestUtil.asSortedList(arrayListClass.getAncestorClasses().stream().map(o -> o.getName()));
        assertEquals(3, aresult.size());
        assertEquals("java.lang.Object", aresult.get(0));
        assertEquals("java.util.AbstractCollection", aresult.get(1));
        assertEquals("java.util.AbstractList", aresult.get(2));
        
        List<String> asresult = TestUtil.asSortedList(assertClass.getAncestorClasses().stream().map(o -> o.getName()));
        assertEquals(1, asresult.size());
        assertEquals("java.lang.Object", asresult.get(0));
    }
    
    @Test
    public void testGetDescendantClasses() {
        assertEquals(0, customerClass.getDescendantClasses().size());
        
        List<String> presult = TestUtil.asSortedList(priceClass.getDescendantClasses().stream().map(o -> o.getName()));
        assertEquals(3, presult.size());
        assertEquals("org.jtool.videostore.after.ChildrensPrice", presult.get(0));
        assertEquals("org.jtool.videostore.after.NewReleasePrice", presult.get(1));
        assertEquals("org.jtool.videostore.after.RegularPrice", presult.get(2));
        
        List<String> lresult = TestUtil.asSortedList(listClass.getDescendantClasses().stream().map(o -> o.getName()));
        assertEquals(61, lresult.size());
        assertEquals("com.sun.java.util.jar.pack.ConstantPool.Index", lresult.get(0));
        
        List<String> aresult = TestUtil.asSortedList(arrayListClass.getDescendantClasses().stream().map(o -> o.getName()));
        assertEquals(7, aresult.size());
        assertEquals("com.sun.tools.jdi.EventSetImpl", aresult.get(0));
        
        assertEquals(0, assertClass.getDescendantClasses().size());
    }
}