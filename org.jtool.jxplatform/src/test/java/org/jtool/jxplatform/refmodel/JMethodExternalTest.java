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
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class JMethodExternalTest {
    
    private static JavaProject project;
    private static BytecodeClassStore bcStore;
    
    private static JMethod indexOfMethod;
    private static JMethod addMethodInList;
    private static JMethod addMethodInArrayList;
    private static JMethod removeMethod;
    private static JMethod expectedMethod;
    
    @BeforeClass
    public static void setUp() {
        project = RefModelTestUtil.createProjectFromSourceWithoutLibCache("VideoStore", "/lib/*", "");
        bcStore = project.getCFGStore().getBCStore();
        
        JClass stringClass = bcStore.getJClass("java.lang.String");
        indexOfMethod = stringClass.getMethod("indexOf( byte[] byte int java.lang.String int )");
        indexOfMethod.findDefUseFields();
        
        JClass listClass = bcStore.getJClass("java.util.List");
        addMethodInList = listClass.getMethod("add( java.lang.Object )");
        addMethodInList.findDefUseFields();
        
        JClass arrayListClass = bcStore.getJClass("java.util.ArrayList");
        addMethodInArrayList = arrayListClass.getMethod("add( java.lang.Object )");
        addMethodInArrayList.findDefUseFields();
        removeMethod = arrayListClass.getMethod("remove( java.lang.Object )");
        removeMethod.findDefUseFields();
        
        JClass testClass = bcStore.getJClass("org.junit.Test");
        expectedMethod = testClass.getMethod("expected( )");
        expectedMethod.findDefUseFields();
    }
    
    @AfterClass
    public static void tearDown() {
        project.getModelBuilder().unbuild();
    }
    
    @Test
    public void testInstanceOf() {
        assertTrue(indexOfMethod instanceof JMethodExternal);
        
        assertTrue(addMethodInList instanceof JMethodExternal);
        
        assertTrue(addMethodInArrayList instanceof JMethodExternal);
        
        assertTrue(removeMethod instanceof JMethodExternal);
        
        assertTrue(expectedMethod instanceof JMethodExternal);
    }
    
    @Test
    public void testGetQualifiedName() {
        assertEquals("java.lang.String#indexOf( byte[] byte int java.lang.String int )",
                indexOfMethod.getQualifiedName().fqn());
        
        assertEquals("java.util.List#add( java.lang.Object )",
                addMethodInList.getQualifiedName().fqn());
        
        assertEquals("java.util.ArrayList#add( java.lang.Object )",
                addMethodInArrayList.getQualifiedName().fqn());
        
        assertEquals("java.util.ArrayList#remove( java.lang.Object )",
                removeMethod.getQualifiedName().fqn());
        
        assertEquals("org.junit.Test#expected( )",
                expectedMethod.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetDeclaringClass() {
        assertEquals("java.lang.String",
                indexOfMethod.getDeclaringClass().getClassName());
        
        assertEquals("java.util.List",
                addMethodInList.getDeclaringClass().getClassName());
        
        assertEquals("java.util.ArrayList",
                addMethodInArrayList.getDeclaringClass().getClassName());
        
        assertEquals("java.util.ArrayList",
                removeMethod.getDeclaringClass().getClassName());
        
        assertEquals("org.junit.Test",
                expectedMethod.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testIsInProject() {
        assertFalse(indexOfMethod.isInProject());
        
        assertFalse(addMethodInList.isInProject());
        
        assertFalse(addMethodInArrayList.isInProject());
        
        assertFalse(removeMethod.isInProject());
        
        assertFalse(expectedMethod.isInProject());
    }
    
    @Test
    public void testGetDefFields() {
        assertEquals(0, indexOfMethod.getDefFields().size());
        
        assertEquals(0, addMethodInList.getDefFields().size());
        
        List<String> aresult = TestUtil.asSortedList(addMethodInArrayList.getDefFields().stream().map(o -> o.getQualifiedName()));
        assertEquals(2, aresult.size());
        assertEquals("java.util.AbstractList%modCount%java.util.AbstractList.modCount", aresult.get(0));
        assertEquals("java.util.ArrayList%size%java.util.ArrayList.size", aresult.get(1));
        
        List<String> rresult = TestUtil.asSortedList(removeMethod.getDefFields().stream().map(o -> o.getQualifiedName()));
        assertEquals(2, rresult.size());
        assertEquals("java.util.AbstractList%modCount%java.util.AbstractList.modCount", rresult.get(0));
        assertEquals("java.util.ArrayList%size%java.util.ArrayList.size", rresult.get(1));
        
        assertEquals(0, expectedMethod.getDefFields().size());
    }
    
    @Test
    public void testGetUseFields() {
        List<String> iresult = TestUtil.asSortedList(indexOfMethod.getUseFields().stream().map(o -> o.getQualifiedName()));
        assertEquals(3, iresult.size());
        assertEquals("java.lang.String%COMPACT_STRINGS%java.lang.String.COMPACT_STRINGS", iresult.get(0));
        assertEquals("java.lang.String%coder%java.lang.String.coder", iresult.get(1));
        assertEquals("java.lang.String%value%java.lang.String.value", iresult.get(2));
        
        assertEquals(0, addMethodInList.getUseFields().size());
        
        List<String> aresult = TestUtil.asSortedList(addMethodInArrayList.getUseFields().stream().map(o -> o.getQualifiedName()));
        assertEquals(3, aresult.size());
        assertEquals("java.util.AbstractList%modCount%java.util.AbstractList.modCount", aresult.get(0));
        assertEquals("java.util.ArrayList%elementData%java.util.ArrayList.elementData", aresult.get(1));
        assertEquals("java.util.ArrayList%size%java.util.ArrayList.size", aresult.get(2));
        //assertEquals(3, addMethodInArrayList.getUseFields().size());
        
        List<String> rresult = TestUtil.asSortedList(removeMethod.getUseFields().stream().map(o -> o.getQualifiedName()));
        assertEquals(3, rresult.size());
        assertEquals("java.util.AbstractList%modCount%java.util.AbstractList.modCount", rresult.get(0));
        assertEquals("java.util.ArrayList%elementData%java.util.ArrayList.elementData", rresult.get(1));
        assertEquals("java.util.ArrayList%size%java.util.ArrayList.size", rresult.get(2));
        
        assertEquals(0, expectedMethod.getUseFields().size());
    }
    
    @Test
    public void testGetAccessedMethods() {
        List<String> iresult = TestUtil.asSortedList(indexOfMethod.getAccessedMethods().stream().map(o -> o.getQualifiedName()));
        assertEquals(5, iresult.size());
        assertEquals("java.lang.String#coder( )", iresult.get(0));
        assertEquals("java.lang.String#length( )", iresult.get(1));
        assertEquals("java.lang.StringLatin1#indexOf( byte[] int byte[] int int )", iresult.get(2));
        assertEquals("java.lang.StringUTF16#indexOf( byte[] int byte[] int int )", iresult.get(3));
        assertEquals("java.lang.StringUTF16#indexOfLatin1( byte[] int byte[] int int )", iresult.get(4));
        
        assertEquals(0, addMethodInList.getAccessedMethods().size());
        
        List<String> aresult = TestUtil.asSortedList(addMethodInArrayList.getAccessedMethods().stream().map(o -> o.getQualifiedName()));
        assertEquals(1, aresult.size());
        assertEquals("java.util.ArrayList#add( java.lang.Object java.lang.Object[] int )", aresult.get(0));
        
        List<String> rresult = TestUtil.asSortedList(removeMethod.getAccessedMethods().stream().map(o -> o.getQualifiedName()));
        assertEquals(2, rresult.size());
        assertEquals("java.lang.Object#equals( java.lang.Object )", rresult.get(0));
        assertEquals("java.util.ArrayList#fastRemove( java.lang.Object[] int )", rresult.get(1));
        
        assertEquals(0, expectedMethod.getAccessedMethods().size());
    }
}
