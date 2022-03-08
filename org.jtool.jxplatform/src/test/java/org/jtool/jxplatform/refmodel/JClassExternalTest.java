/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.refmodel;

import org.jtool.jxplatform.util.TestUtil;
import org.jtool.srcmodel.JavaProject;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class JClassExternalTest {
    
    private static JavaProject project;
    private static BytecodeClassStore bcStore;
    
    private static JClass objectClass;
    private static JClass stringClass;
    private static JClass listClass;
    private static JClass arrayListClass;
    private static JClass serializableClass;
    private static JClass testClass;
    private static JClass assertClass;
    private static JClass mapClass;
    private static JClass hashMapClass;
    
    @BeforeClass
    public static void setUp() {
        String name = "VideoStore";
        String target = TestUtil.getTarget(name);
        String classpath = target + "/lib/*";
        
        project = RefModelTestUtil.createProjectFromSourceWithoutLibCache(target, classpath);
        bcStore = project.getCFGStore().getBCStore();
        
        stringClass = bcStore.getJClass("java.lang.String");
        listClass = bcStore.getJClass("java.util.List");
        arrayListClass = bcStore.getJClass("java.util.ArrayList");
        serializableClass = bcStore.getJClass("java.io.Serializable");
        
        testClass = bcStore.getJClass("org.junit.Test");
        assertClass = bcStore.getJClass("org.junit.Assert");
        
        objectClass = bcStore.getJClass("java.lang.Object");
        mapClass = bcStore.getJClass("java.util.Map");
        hashMapClass = bcStore.getJClass("java.util.HashMap");
    }
    
    @AfterClass
    public static void tearDown() {
        project.getModelBuilder().unbuild();
    }
    
    @Test
    public void testInstanceOf() {
        assertTrue(stringClass instanceof JClassExternal);
        
        assertTrue(listClass instanceof JClassExternal);
        
        assertTrue(arrayListClass instanceof JClassExternal);
        
        assertTrue(serializableClass instanceof JClassExternal);
        
        assertTrue(testClass instanceof JClassExternal);
        
        assertTrue(assertClass instanceof JClassExternal);
        
        assertTrue(objectClass instanceof JClassExternal);
        
        assertTrue(mapClass instanceof JClassExternal);
        
        assertTrue(hashMapClass instanceof JClassExternal);
    }
    
    @Test
    public void testGetQualifiedName() {
        assertEquals("java.lang.String", stringClass.getQualifiedName().fqn());
        
        assertEquals("java.util.List", listClass.getQualifiedName().fqn());
        
        assertEquals("java.util.ArrayList", arrayListClass.getQualifiedName().fqn());
        
        assertEquals("java.io.Serializable", serializableClass.getQualifiedName().fqn());
        
        assertEquals("org.junit.Test", testClass.getQualifiedName().fqn());
        
        assertEquals("org.junit.Assert", assertClass.getQualifiedName().fqn());
        
        assertEquals("java.lang.Object", objectClass.getQualifiedName().fqn());
        
        assertEquals("java.util.Map", mapClass.getQualifiedName().fqn());
        
        assertEquals("java.util.HashMap", hashMapClass.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetName() {
        assertEquals("java.lang.String", stringClass.getName());
        
        assertEquals("java.util.List", listClass.getName());
        
        assertEquals("java.util.ArrayList", arrayListClass.getName());
        
        assertEquals("java.io.Serializable", serializableClass.getName());
        
        assertEquals("org.junit.Test", testClass.getName());
        
        assertEquals("org.junit.Assert", assertClass.getName());
        
        assertEquals("java.lang.Object", objectClass.getName());
        
        assertEquals("java.util.Map", mapClass.getName());
        
        assertEquals("java.util.HashMap", hashMapClass.getName());
    }
    
    @Test
    public void testGetSimpleName() {
        assertEquals("String", stringClass.getSimpleName());
        
        assertEquals("List", listClass.getSimpleName());
        
        assertEquals("ArrayList", arrayListClass.getSimpleName());
        
        assertEquals("Serializable", serializableClass.getSimpleName());
        
        assertEquals("Test", testClass.getSimpleName());
        
        assertEquals("Assert", assertClass.getSimpleName());
        
        assertEquals("Object", objectClass.getSimpleName());
        
        assertEquals("Map", mapClass.getSimpleName());
        
        assertEquals("HashMap", hashMapClass.getSimpleName());
    }
    
    @Test
    public void testGetMethods() {
        assertEquals(108, stringClass.getMethods().size());
        
        assertEquals(41, listClass.getMethods().size());
        
        assertEquals(63, arrayListClass.getMethods().size());
        
        assertEquals(0, serializableClass.getMethods().size());
        
        assertEquals(2, testClass.getMethods().size());
        
        assertEquals(67, assertClass.getMethods().size());
        
        assertEquals(13, objectClass.getMethods().size());
        
        assertEquals(39, mapClass.getMethods().size());
        
        assertEquals(51, hashMapClass.getMethods().size());
    }
    
    @Test
    public void testGetMethod() {
        assertEquals("java.lang.String#indexOf( byte[] byte int java.lang.String int )",
                stringClass.getMethod("indexOf( byte[] byte int java.lang.String int )")
                .getQualifiedName().fqn());
        
        assertEquals("java.util.List#get( int )",
                listClass.getMethod("get( int )")
                .getQualifiedName().fqn());
        
        assertEquals("java.util.ArrayList#contains( java.lang.Object )",
                arrayListClass.getMethod("contains( java.lang.Object )")
                .getQualifiedName().fqn());
        
        assertEquals("org.junit.Test#expected( )",
                testClass.getMethod("expected( )")
                .getQualifiedName().fqn());
        
        assertEquals("java.lang.Object#wait( )",
                objectClass.getMethod("wait( )")
                .getQualifiedName().fqn());
        
        assertEquals("java.util.HashMap#HashMap( int float )",
                hashMapClass.getMethod("HashMap( int float )")
                .getQualifiedName().fqn());
    }
    
    @Test
    public void testGetMethodReturningNull() {
        assertNull(arrayListClass.getMethod("indexOf( java.lang.String )"));
    }
    
    @Test
    public void testGetFields() {
        assertEquals(9, stringClass.getFields().size());
        
        assertEquals(0, listClass.getFields().size());
        
        assertEquals(7, arrayListClass.getFields().size());
        
        assertEquals(0, serializableClass.getFields().size());
        
        assertEquals(0, testClass.getFields().size());
        
        assertEquals(0, assertClass.getFields().size());
        
        assertEquals(0, objectClass.getFields().size());
        
        assertEquals(0, mapClass.getFields().size());
        
        assertEquals(13, hashMapClass.getFields().size());
    }
    
    @Test
    public void testGetField() {
        assertEquals("java.lang.String#hash",
                stringClass.getField("hash")
                .getQualifiedName().fqn());
        
        assertEquals("java.util.ArrayList#size",
                arrayListClass.getField("size")
                .getQualifiedName().fqn());
        
        assertEquals("java.util.HashMap#modCount",
                hashMapClass.getField("modCount")
                .getQualifiedName().fqn());
    }
    
    @Test
    public void testGetFieldRetuningNull() {
        assertNull(arrayListClass.getField("length"));
    }
    
    @Test
    public void testGetSuperClass() {
        assertEquals("java.lang.Object", stringClass.getSuperClass());
        
        assertNull(listClass.getSuperClass());
        
        assertEquals("java.util.AbstractList", arrayListClass.getSuperClass());
        
        assertNull(serializableClass.getSuperClass());
        
        assertNull(testClass.getSuperClass());
        
        assertEquals("java.lang.Object", assertClass.getSuperClass());
        
        assertNull(objectClass.getSuperClass());
        
        assertNull(mapClass.getSuperClass());
        
        assertEquals("java.util.AbstractMap", hashMapClass.getSuperClass());
    }
    
    @Test
    public void testGetSuperInterfaces() {
        List<String> sresult = TestUtil.asSortedList(stringClass.getSuperInterfaces());
        assertEquals(3, sresult.size());
        assertEquals("java.io.Serializable", sresult.get(0));
        assertEquals("java.lang.CharSequence", sresult.get(1));
        assertEquals("java.lang.Comparable", sresult.get(2));
        
        List<String> lresult = TestUtil.asSortedList(listClass.getSuperInterfaces());
        assertEquals(1, lresult.size());
        assertEquals("java.util.Collection", lresult.get(0));
        
        List<String> aresult = TestUtil.asSortedList(arrayListClass.getSuperInterfaces());
        assertEquals(4, aresult.size());
        assertEquals("java.io.Serializable", aresult.get(0));
        assertEquals("java.lang.Cloneable", aresult.get(1));
        assertEquals("java.util.List", aresult.get(2));
        assertEquals("java.util.RandomAccess", aresult.get(3));
        
        assertEquals(0, serializableClass.getSuperInterfaces().size());
        
        List<String> tresult = TestUtil.asSortedList(testClass.getSuperInterfaces());
        assertEquals(1, tresult.size());
        assertEquals("java.lang.annotation.Annotation", tresult.get(0));
        
        assertEquals(0, assertClass.getSuperInterfaces().size());
        
        assertEquals(0, objectClass.getSuperInterfaces().size());
        
        assertEquals(0, mapClass.getSuperInterfaces().size());
        
        List<String> hresult = TestUtil.asSortedList(hashMapClass.getSuperInterfaces());
        assertEquals(3, hresult.size());
        assertEquals("java.io.Serializable", hresult.get(0));
        assertEquals("java.lang.Cloneable", hresult.get(1));
        assertEquals("java.util.Map", hresult.get(2));
    }
    
    @Test
    public void testGetSuperClassChain() {
        assertEquals(1, stringClass.getSuperClassChain().size());
        assertEquals("java.lang.Object", stringClass.getSuperClassChain().get(0).getName());
        
        assertEquals(0, listClass.getSuperClassChain().size());
        
        assertEquals(3, arrayListClass.getSuperClassChain().size());
        assertEquals("java.util.AbstractList", arrayListClass.getSuperClassChain().get(0).getName());
        assertEquals("java.util.AbstractCollection", arrayListClass.getSuperClassChain().get(1).getName());
        assertEquals("java.lang.Object", arrayListClass.getSuperClassChain().get(2).getName());
        
        assertEquals(0, serializableClass.getSuperClassChain().size());
        
        assertEquals(0, testClass.getSuperClassChain().size());
        
        assertEquals(1, assertClass.getSuperClassChain().size());
        assertEquals("java.lang.Object", assertClass.getSuperClassChain().get(0).getName());
        
        assertEquals(0, objectClass.getSuperClassChain().size());
        
        assertEquals(0, mapClass.getSuperClassChain().size());
        
        assertEquals(2, hashMapClass.getSuperClassChain().size());
        assertEquals("java.util.AbstractMap", hashMapClass.getSuperClassChain().get(0).getName());
        assertEquals("java.lang.Object", hashMapClass.getSuperClassChain().get(1).getName());
    }
    
    @Test
    public void testIsInterface() {
        assertFalse(stringClass.isInterface());
        
        assertTrue(listClass.isInterface());
        
        assertFalse(arrayListClass.isInterface());
        
        assertTrue(serializableClass.isInterface());
        
        assertTrue(testClass.isInterface());
        
        assertFalse(assertClass.isInterface());
        
        assertFalse(objectClass.isInterface());
        
        assertTrue(mapClass.isInterface());
        
        assertFalse(hashMapClass.isInterface());
    }
    
    @Test
    public void testIsInProject() {
        assertFalse(stringClass.isInProject());
        
        assertFalse(listClass.isInProject());
        
        assertFalse(arrayListClass.isInProject());
        
        assertFalse(serializableClass.isInProject());
        
        assertFalse(testClass.isInProject());
        
        assertFalse(assertClass.isInProject());
        
        assertFalse(objectClass.isInProject());
        
        assertFalse(mapClass.isInProject());
        
        assertFalse(hashMapClass.isInProject());
    }
    
    @Test
    public void testGetAncestors() {
        List<String> sresult = TestUtil.asSortedList(stringClass.getAncestorClasses().stream().map(o -> o.getName()));
        assertEquals(1, sresult.size());
        assertEquals("java.lang.Object", sresult.get(0));
        
        assertEquals(0, listClass.getAncestorClasses().size());
        
        List<String> aresult = TestUtil.asSortedList(arrayListClass.getAncestorClasses().stream().map(o -> o.getName()));
        assertEquals(3, aresult.size());
        assertEquals("java.lang.Object", aresult.get(0));
        assertEquals("java.util.AbstractCollection", aresult.get(1));
        assertEquals("java.util.AbstractList", aresult.get(2));
        
        assertEquals(0, serializableClass.getAncestorClasses().size());
        
        assertEquals(0, testClass.getAncestorClasses().size());
        
        List<String> asresult = TestUtil.asSortedList(assertClass.getAncestorClasses().stream().map(o -> o.getName()));
        assertEquals(1, asresult.size());
        assertEquals("java.lang.Object", asresult.get(0));
        
        assertEquals(0, objectClass.getAncestorClasses().size());
        
        assertEquals(0, mapClass.getAncestorClasses().size());
        
        List<String> hresult = TestUtil.asSortedList(hashMapClass.getAncestorClasses().stream().map(o -> o.getName()));
        assertEquals(2, hresult.size());
        assertEquals("java.lang.Object", hresult.get(0));
        assertEquals("java.util.AbstractMap", hresult.get(1));
    }
    
    @Test
    public void testGetDescendants() {
        assertEquals(0, stringClass.getDescendantClasses().size());
        
        List<String> lresult = TestUtil.asSortedList(listClass.getDescendantClasses().stream().map(o -> o.getName()));
        assertEquals(61, lresult.size());
        assertEquals("com.sun.java.util.jar.pack.ConstantPool.Index", lresult.get(0));
        
        List<String> aresult = TestUtil.asSortedList(arrayListClass.getDescendantClasses().stream().map(o -> o.getName()));
        assertEquals(7, aresult.size());
        assertEquals("com.sun.tools.jdi.EventSetImpl", aresult.get(0));
        
        List<String> sresult = TestUtil.asSortedList(serializableClass.getDescendantClasses().stream().map(o -> o.getName()));
        assertEquals(3133, sresult.size());
        assertEquals("apple.security.AppleProvider", sresult.get(0));
        
        assertEquals(0, testClass.getDescendantClasses().size());
        
        assertEquals(0, assertClass.getDescendantClasses().size());
        
        List<String> oresult = TestUtil.asSortedList(objectClass.getDescendantClasses().stream().map(o -> o.getName()));
        assertEquals(21119, oresult.size());
        assertEquals("apple.laf.JRSUIConstants", oresult.get(0));
        
        List<String> mresult = TestUtil.asSortedList(mapClass.getDescendantClasses().stream().map(o -> o.getName()));
        assertEquals(101, mresult.size());
        assertEquals("apple.security.AppleProvider", mresult.get(0));
        
        List<String> hresult = TestUtil.asSortedList(hashMapClass.getDescendantClasses().stream().map(o -> o.getName()));
        assertEquals(14, hresult.size());
        assertEquals("java.util.LinkedHashMap", hresult.get(4));
    }
}
