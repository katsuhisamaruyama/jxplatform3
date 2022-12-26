/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal.refmodel;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.util.TestUtil;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.jxplatform.util.FlakyByExternalLib;
import java.util.List;
import org.junit.experimental.categories.Category;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

@Category(FlakyByExternalLib.class)
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
        BuilderTestUtil.clearProject();
        
        project = RefModelTestUtil.createProjectFromSourceWithoutLibCache("VideoStore", "/lib/*", "");
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
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testInstanceOf1() {
        assertTrue(stringClass instanceof JClassExternal);
    }
    
    @Test
    public void testInstanceOf2() {
        assertTrue(listClass instanceof JClassExternal);
    }
    
    @Test
    public void testInstanceOf3() {
        assertTrue(arrayListClass instanceof JClassExternal);
    }
    
    @Test
    public void testInstanceOf4() {
        assertTrue(serializableClass instanceof JClassExternal);
    }
    
    @Test
    public void testInstanceOf5() {
        assertTrue(testClass instanceof JClassExternal);
    }
    
    @Test
    public void testInstanceOf6() {
        assertTrue(assertClass instanceof JClassExternal);
    }
    
    @Test
    public void testInstanceOf7() {
        assertTrue(objectClass instanceof JClassExternal);
    }
    
    @Test
    public void testInstanceOf8() {
        assertTrue(mapClass instanceof JClassExternal);
    }
    
    @Test
    public void testInstanceOf9() {
        assertTrue(hashMapClass instanceof JClassExternal);
    }
    
    @Test
    public void testGetQualifiedName1() {
        assertEquals("java.lang.String", stringClass.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName2() {
        assertEquals("java.util.List", listClass.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName3() {
        assertEquals("java.util.ArrayList", arrayListClass.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName4() {
        assertEquals("java.io.Serializable", serializableClass.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName5() {
        assertEquals("org.junit.Test", testClass.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName6() {
        assertEquals("org.junit.Assert", assertClass.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName7() {
        assertEquals("java.lang.Object", objectClass.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName8() {
        assertEquals("java.util.Map", mapClass.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName9() {
        assertEquals("java.util.HashMap", hashMapClass.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetName1() {
        assertEquals("java.lang.String", stringClass.getName());
    }
    
    @Test
    public void testGetName2() {
        assertEquals("java.util.List", listClass.getName());
    }
    
    @Test
    public void testGetName3() {
        assertEquals("java.util.ArrayList", arrayListClass.getName());
    }
    
    @Test
    public void testGetName4() {
        assertEquals("java.io.Serializable", serializableClass.getName());
    }
    
    @Test
    public void testGetName5() {
        assertEquals("org.junit.Test", testClass.getName());
    }
    
    @Test
    public void testGetName6() {
        assertEquals("org.junit.Assert", assertClass.getName());
    }
    
    @Test
    public void testGetName7() {
        assertEquals("java.lang.Object", objectClass.getName());
    }
    
    @Test
    public void testGetName8() {
        assertEquals("java.util.Map", mapClass.getName());
    }
    
    @Test
    public void testGetName9() {
        assertEquals("java.util.HashMap", hashMapClass.getName());
    }
    
    @Test
    public void testGetSimpleName1() {
        assertEquals("String", stringClass.getSimpleName());
    }
    
    @Test
    public void testGetSimpleName2() {
        assertEquals("List", listClass.getSimpleName());
    }
    
    @Test
    public void testGetSimpleName3() {
        assertEquals("ArrayList", arrayListClass.getSimpleName());
    }
    
    @Test
    public void testGetSimpleName4() {
        assertEquals("Serializable", serializableClass.getSimpleName());
    }
    
    @Test
    public void testGetSimpleName5() {
        assertEquals("Test", testClass.getSimpleName());
    }
    
    @Test
    public void testGetSimpleName6() {
        assertEquals("Assert", assertClass.getSimpleName());
    }
    
    @Test
    public void testGetSimpleName7() {
        assertEquals("Object", objectClass.getSimpleName());
    }
    
    @Test
    public void testGetSimpleName8() {
        assertEquals("Map", mapClass.getSimpleName());
    }
    
    @Test
    public void testGetSimpleName9() {
        assertEquals("HashMap", hashMapClass.getSimpleName());
    }
    
    @Test
    public void testGetMethods1() {
        assertEquals(108, stringClass.getMethods().size());
    }
    
    @Test
    public void testGetMethods2() {
        assertEquals(41, listClass.getMethods().size());
    }
    
    @Test
    public void testGetMethods3() {
        assertEquals(63, arrayListClass.getMethods().size());
    }
    
    @Test
    public void testGetMethods4() {
        assertEquals(0, serializableClass.getMethods().size());
    }
    
    @Test
    public void testGetMethods5() {
        assertEquals(2, testClass.getMethods().size());
    }
    
    @Test
    public void testGetMethods6() {
        assertEquals(67, assertClass.getMethods().size());
    }
    
    @Test
    public void testGetMethods7() {
        assertEquals(13, objectClass.getMethods().size());
    }
    
    @Test
    public void testGetMethods8() {
        assertEquals(39, mapClass.getMethods().size());
    }
    
    @Test
    public void testGetMethods9() {
        assertEquals(51, hashMapClass.getMethods().size());
    }
    
    @Test
    public void testGetMethod1() {
        assertEquals("java.lang.String#indexOf( byte[] byte int java.lang.String int )",
                stringClass.getMethod("indexOf( byte[] byte int java.lang.String int )")
                .getQualifiedName().fqn());
    }
    
    @Test
    public void testGetMethod2() {
        assertEquals("java.util.List#get( int )",
                listClass.getMethod("get( int )")
                .getQualifiedName().fqn());
    }
    
    @Test
    public void testGetMethod3() {
        assertEquals("java.util.ArrayList#contains( java.lang.Object )",
                arrayListClass.getMethod("contains( java.lang.Object )")
                .getQualifiedName().fqn());
    }
    
    @Test
    public void testGetMethod4() {
        assertEquals("org.junit.Test#expected( )",
                testClass.getMethod("expected( )")
                .getQualifiedName().fqn());
    }
    
    @Test
    public void testGetMethod5() {
        assertEquals("java.lang.Object#wait( )",
                objectClass.getMethod("wait( )")
                .getQualifiedName().fqn());
    }
    
    @Test
    public void testGetMethod6() {
        assertEquals("java.util.HashMap#HashMap( int float )",
                hashMapClass.getMethod("HashMap( int float )")
                .getQualifiedName().fqn());
    }
    
    @Test
    public void testGetMethodReturningNull() {
        assertNull(arrayListClass.getMethod("indexOf( java.lang.String )"));
    }
    
    @Test
    public void testGetFields1() {
        assertEquals(9, stringClass.getFields().size());
    }
    
    @Test
    public void testGetFields2() {
        assertEquals(0, listClass.getFields().size());
    }
    
    @Test
    public void testGetFields3() {
        assertEquals(7, arrayListClass.getFields().size());
    }
    
    @Test
    public void testGetFields4() {
        assertEquals(0, serializableClass.getFields().size());
    }
    
    @Test
    public void testGetFields5() {
        assertEquals(0, testClass.getFields().size());
    }
    
    @Test
    public void testGetFields6() {
        assertEquals(0, assertClass.getFields().size());
    }
    
    @Test
    public void testGetFields7() {
        assertEquals(0, objectClass.getFields().size());
    }
    
    @Test
    public void testGetFields8() {
        assertEquals(0, mapClass.getFields().size());
    }
    
    @Test
    public void testGetFields9() {
        assertEquals(13, hashMapClass.getFields().size());
    }
    
    @Test
    public void testGetField1() {
        assertEquals("java.lang.String#hash",
                stringClass.getField("hash").getQualifiedName().fqn());
    }
    
    @Test
    public void testGetField2() {
        assertEquals("java.util.ArrayList#size",
                arrayListClass.getField("size").getQualifiedName().fqn());
    }
    
    @Test
    public void testGetField3() {
        assertEquals("java.util.HashMap#modCount",
                hashMapClass.getField("modCount").getQualifiedName().fqn());
    }
    
    @Test
    public void testGetFieldRetuningNull() {
        assertNull(arrayListClass.getField("length"));
    }
    
    @Test
    public void testGetSuperClass1() {
        assertEquals("java.lang.Object", stringClass.getSuperClass());
    }
    
    @Test
    public void testGetSuperClass2() {
        assertNull(listClass.getSuperClass());
    }
    
    @Test
    public void testGetSuperClass3() {
        assertEquals("java.util.AbstractList", arrayListClass.getSuperClass());
    }
    
    @Test
    public void testGetSuperClass4() {
        assertNull(serializableClass.getSuperClass());
    }
    
    @Test
    public void testGetSuperClass5() {
        assertNull(testClass.getSuperClass());
    }
    
    @Test
    public void testGetSuperClass6() {
        assertEquals("java.lang.Object", assertClass.getSuperClass());
    }
    
    @Test
    public void testGetSuperClass7() {
        assertNull(objectClass.getSuperClass());
    }
    
    @Test
    public void testGetSuperClass8() {
        assertNull(mapClass.getSuperClass());
    }
    
    @Test
    public void testGetSuperClass9() {
        assertEquals("java.util.AbstractMap", hashMapClass.getSuperClass());
    }
    
    @Test
    public void testGetSuperInterfaces1() {
        List<String> result = TestUtil.asSortedList(stringClass.getSuperInterfaces());
        
        assertEquals(3, result.size());
        assertEquals("java.io.Serializable", result.get(0));
        assertEquals("java.lang.CharSequence", result.get(1));
        assertEquals("java.lang.Comparable", result.get(2));
    }
    
    @Test
    public void testGetSuperInterfaces2() {
        List<String> result = TestUtil.asSortedList(listClass.getSuperInterfaces());
        
        assertEquals(1, result.size());
        assertEquals("java.util.Collection", result.get(0));
    }
    
    @Test
    public void testGetSuperInterfaces3() {
        List<String> result = TestUtil.asSortedList(arrayListClass.getSuperInterfaces());
        
        assertEquals(4, result.size());
        assertEquals("java.io.Serializable", result.get(0));
        assertEquals("java.lang.Cloneable", result.get(1));
        assertEquals("java.util.List", result.get(2));
        assertEquals("java.util.RandomAccess", result.get(3));
    }
    
    @Test
    public void testGetSuperInterfaces4() {
        assertEquals(0, serializableClass.getSuperInterfaces().size());
    }
    
    @Test
    public void testGetSuperInterfaces5() {
        List<String> result = TestUtil.asSortedList(testClass.getSuperInterfaces());
        
        assertEquals(1, result.size());
        assertEquals("java.lang.annotation.Annotation", result.get(0));
    }
    
    @Test
    public void testGetSuperInterfaces6() {
        assertEquals(0, assertClass.getSuperInterfaces().size());
    }
    
    @Test
    public void testGetSuperInterfaces7() {
        assertEquals(0, objectClass.getSuperInterfaces().size());
    }
    
    @Test
    public void testGetSuperInterfaces() {
        assertEquals(0, mapClass.getSuperInterfaces().size());
    }
    
    @Test
    public void testGetSuperInterfaces8() {
        List<String> result = TestUtil.asSortedList(hashMapClass.getSuperInterfaces());
        
        assertEquals(3, result.size());
        assertEquals("java.io.Serializable", result.get(0));
        assertEquals("java.lang.Cloneable", result.get(1));
        assertEquals("java.util.Map", result.get(2));
    }
    
    @Test
    public void testGetSuperClassChain1() {
        assertEquals(1, stringClass.getSuperClassChain().size());
        assertEquals("java.lang.Object", stringClass.getSuperClassChain().get(0).getName());
    }
    
    @Test
    public void testGetSuperClassChain2() {
        assertEquals(0, listClass.getSuperClassChain().size());
    }
    
    @Test
    public void testGetSuperClassChain3() {
        assertEquals(3, arrayListClass.getSuperClassChain().size());
        assertEquals("java.util.AbstractList", arrayListClass.getSuperClassChain().get(0).getName());
        assertEquals("java.util.AbstractCollection", arrayListClass.getSuperClassChain().get(1).getName());
        assertEquals("java.lang.Object", arrayListClass.getSuperClassChain().get(2).getName());
    }
    
    @Test
    public void testGetSuperClassChain4() {
        assertEquals(0, serializableClass.getSuperClassChain().size());
    }
    
    @Test
    public void testGetSuperClassChain5() {
        assertEquals(0, testClass.getSuperClassChain().size());
    }
    
    @Test
    public void testGetSuperClassChain6() {
        assertEquals(1, assertClass.getSuperClassChain().size());
        assertEquals("java.lang.Object", assertClass.getSuperClassChain().get(0).getName());
    }
    
    @Test
    public void testGetSuperClassChain7() {
        assertEquals(0, objectClass.getSuperClassChain().size());
    }
    
    @Test
    public void testGetSuperClassChain8() {
        assertEquals(0, mapClass.getSuperClassChain().size());
    }
    
    @Test
    public void testGetSuperClassChain9() {
        assertEquals(2, hashMapClass.getSuperClassChain().size());
        assertEquals("java.util.AbstractMap", hashMapClass.getSuperClassChain().get(0).getName());
        assertEquals("java.lang.Object", hashMapClass.getSuperClassChain().get(1).getName());
    }
    
    @Test
    public void testIsInterface1() {
        assertFalse(stringClass.isInterface());
    }
    
    @Test
    public void testIsInterface2() {
        assertTrue(listClass.isInterface());
    }
    
    @Test
    public void testIsInterface3() {
        assertFalse(arrayListClass.isInterface());
    }
    
    @Test
    public void testIsInterface4() {
        assertTrue(serializableClass.isInterface());
    }
    
    @Test
    public void testIsInterface5() {
        assertTrue(testClass.isInterface());
    }
    
    @Test
    public void testIsInterface6() {
        assertFalse(assertClass.isInterface());
    }
    
    @Test
    public void testIsInterface7() {
        assertFalse(objectClass.isInterface());
    }
    
    @Test
    public void testIsInterface8() {
        assertTrue(mapClass.isInterface());
    }
    
    @Test
    public void testIsInterface9() {
        assertFalse(hashMapClass.isInterface());
    }
    
    @Test
    public void testIsInProject1() {
        assertFalse(stringClass.isInProject());
    }
    
    @Test
    public void testIsInProject2() {
        assertFalse(listClass.isInProject());
    }
    
    @Test
    public void testIsInProject3() {
        assertFalse(arrayListClass.isInProject());
    }
    
    @Test
    public void testIsInProject4() {
        assertFalse(serializableClass.isInProject());
    }
    
    @Test
    public void testIsInProject5() {
        assertFalse(testClass.isInProject());
    }
    
    @Test
    public void testIsInProject6() {
        assertFalse(assertClass.isInProject());
    }
    
    @Test
    public void testIsInProject7() {
        assertFalse(objectClass.isInProject());
    }
    
    @Test
    public void testIsInProject8() {
        assertFalse(mapClass.isInProject());
    }
    
    @Test
    public void testIsInProject9() {
        assertFalse(hashMapClass.isInProject());
    }
    
    @Test
    public void testGetAncestors1() {
        List<String> result = TestUtil.asSortedList(stringClass.getAncestorClasses().stream()
                .map(o -> o.getName()));
        
        assertEquals(1, result.size());
        assertEquals("java.lang.Object", result.get(0));
    }
    
    @Test
    public void testGetAncestors2() {
        assertEquals(0, listClass.getAncestorClasses().size());
    }
    
    @Test
    public void testGetAncestors3() {
        List<String> result = TestUtil.asSortedList(arrayListClass.getAncestorClasses().stream()
                .map(o -> o.getName()));
        
        assertEquals(3, result.size());
        assertEquals("java.lang.Object", result.get(0));
        assertEquals("java.util.AbstractCollection", result.get(1));
        assertEquals("java.util.AbstractList", result.get(2));
    }
    
    @Test
    public void testGetAncestors4() {
        assertEquals(0, serializableClass.getAncestorClasses().size());
    }
    
    @Test
    public void testGetAncestors5() {
        assertEquals(0, testClass.getAncestorClasses().size());
    }
    
    @Test
    public void testGetAncestors6() {
        List<String> result = TestUtil.asSortedList(assertClass.getAncestorClasses().stream()
                .map(o -> o.getName()));
        
        assertEquals(1, result.size());
        assertEquals("java.lang.Object", result.get(0));
    }
    
    @Test
    public void testGetAncestors7() {
        assertEquals(0, objectClass.getAncestorClasses().size());
    }
    
    @Test
    public void testGetAncestors8() {
        assertEquals(0, mapClass.getAncestorClasses().size());
    }
    
    @Test
    public void testGetAncestors9() {
        List<String> result = TestUtil.asSortedList(hashMapClass.getAncestorClasses().stream()
                .map(o -> o.getName()));
        
        assertEquals(2, result.size());
        assertEquals("java.lang.Object", result.get(0));
        assertEquals("java.util.AbstractMap", result.get(1));
    }
    
    @Test
    public void testGetDescendants1() {
        assertEquals(0, stringClass.getDescendantClasses().size());
    }
    
    @Test
    public void testGetDescendants2() {
        List<String> result = TestUtil.asSortedList(listClass.getDescendantClasses().stream()
                .map(o -> o.getName()));
        
        assertEquals(61, result.size());
        assertEquals("com.sun.java.util.jar.pack.ConstantPool.Index", result.get(0));
    }
    
    @Test
    public void testGetDescendants3() {
        List<String> result = TestUtil.asSortedList(arrayListClass.getDescendantClasses().stream()
                .map(o -> o.getName()));
        
        assertEquals(7, result.size());
        assertEquals("com.sun.tools.jdi.EventSetImpl", result.get(0));
    }
    
    @Test
    public void testGetDescendants4() {
        List<String> result = TestUtil.asSortedList(serializableClass.getDescendantClasses().stream()
                .map(o -> o.getName()));
        
        assertEquals(3106, result.size());
        assertEquals("apple.security.AppleProvider", result.get(0));
    }
    
    @Test
    public void testGetDescendants5() {
        assertEquals(0, testClass.getDescendantClasses().size());
    }
    
    @Test
    public void testGetDescendants6() {
        assertEquals(0, assertClass.getDescendantClasses().size());
    }
    
    @Test
    public void testGetDescendants7() {
        List<String> result = TestUtil.asSortedList(objectClass.getDescendantClasses().stream()
                .map(o -> o.getName()));
        
        assertEquals(20841, result.size());
        assertEquals("apple.laf.JRSUIConstants", result.get(0));
    }
    
    @Test
    public void testGetDescendants8() {
        List<String> result = TestUtil.asSortedList(mapClass.getDescendantClasses().stream()
                .map(o -> o.getName()));
        
        assertEquals(100, result.size());
        assertEquals("apple.security.AppleProvider", result.get(0));
    }
    
    @Test
    public void testGetDescendants9() {
        List<String> result = TestUtil.asSortedList(hashMapClass.getDescendantClasses().stream()
                .map(o -> o.getName()));
        
        assertEquals(13, result.size());
        assertEquals("java.util.LinkedHashMap", result.get(4));
    }
}
