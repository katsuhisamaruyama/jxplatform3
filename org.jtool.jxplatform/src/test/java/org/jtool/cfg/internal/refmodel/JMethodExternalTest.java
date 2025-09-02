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

@Category(FlakyByExternalLib.class)
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
        BuilderTestUtil.clearProject();
        
        project = RefModelTestUtil.createProjectFromSourceWithoutLibCache("VideoStore", "/lib/*", "");
        bcStore = project.getCFGStore().getBCStore();
        
        JClass stringClass = bcStore.getJClass("java.lang.String");
        indexOfMethod = stringClass.getMethod("indexOf( byte[] byte int java.lang.String int )");
        indexOfMethod.findDefUseFields("s$0.!indexOf( byte[] byte int java.lang.String int )", "");
        
        JClass listClass = bcStore.getJClass("java.util.List");
        addMethodInList = listClass.getMethod("add( java.lang.Object )");
        addMethodInList.findDefUseFields("l$0.!add( java.lang.Object )", "");
        
        JClass arrayListClass = bcStore.getJClass("java.util.ArrayList");
        addMethodInArrayList = arrayListClass.getMethod("add( java.lang.Object )");
        addMethodInArrayList.findDefUseFields("a$0.!add( java.lang.Object )", "");
        removeMethod = arrayListClass.getMethod("remove( java.lang.Object )");
        removeMethod.findDefUseFields("a$0.!remove( java.lang.Object )", "");
        
        JClass testClass = bcStore.getJClass("org.junit.Test");
        expectedMethod = testClass.getMethod("expected( )");
        expectedMethod.findDefUseFields("t$0.!expected( )", "");
    }
    
    @AfterClass
    public static void tearDown() {
        project.getModelBuilder().unbuild();
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testInstanceOf1() {
        assertTrue(indexOfMethod instanceof JMethodExternal);
    }
    
    @Test
    public void testInstanceOf2() {
        assertTrue(addMethodInList instanceof JMethodExternal);
    }
    
    @Test
    public void testInstanceOf3() {
        assertTrue(addMethodInArrayList instanceof JMethodExternal);
    }
    
    @Test
    public void testInstanceOf4() {
        assertTrue(removeMethod instanceof JMethodExternal);
    }
    
    @Test
    public void testInstanceOf5() {
        assertTrue(expectedMethod instanceof JMethodExternal);
    }
    
    @Test
    public void testGetQualifiedName1() {
        assertEquals("java.lang.String#indexOf( byte[] byte int java.lang.String int )",
                indexOfMethod.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName2() {
        assertEquals("java.util.List#add( java.lang.Object )",
                addMethodInList.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName3() {
        assertEquals("java.util.ArrayList#add( java.lang.Object )",
                addMethodInArrayList.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName4() {
        assertEquals("java.util.ArrayList#remove( java.lang.Object )",
                removeMethod.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName5() {
        assertEquals("org.junit.Test#expected( )",
                expectedMethod.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetDeclaringClass1() {
        assertEquals("java.lang.String",
                indexOfMethod.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testGetDeclaringClass2() {
        assertEquals("java.util.List",
                addMethodInList.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testGetDeclaringClass3() {
        assertEquals("java.util.ArrayList",
                addMethodInArrayList.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testGetDeclaringClass4() {
        assertEquals("java.util.ArrayList",
                removeMethod.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testGetDeclaringClass5() {
        assertEquals("org.junit.Test",
                expectedMethod.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testIsInProject1() {
        assertFalse(indexOfMethod.isInProject());
    }
    
    @Test
    public void testIsInProject2() {
        assertFalse(addMethodInList.isInProject());
    }
    
    @Test
    public void testIsInProject3() {
        assertFalse(addMethodInArrayList.isInProject());
    }
    
    @Test
    public void testIsInProject4() {
        assertFalse(removeMethod.isInProject());
    }
    
    @Test
    public void testIsInProject5() {
        assertFalse(expectedMethod.isInProject());
    }
    
    @Test
    public void testGetDefFields1() {
        assertEquals(0, indexOfMethod.getDefFields().size());
    }
    
    @Test
    public void testGetDefFields2() {
        assertEquals(0, addMethodInList.getDefFields().size());
    }
    
    @Test
    public void testGetDefFields3() {
        List<String> result = TestUtil.asSortedList(addMethodInArrayList.getDefFields().stream()
                .map(o -> o.getQualifiedName()));
        
        assertEquals(1, result.size());
        assertEquals("java.util.AbstractList%modCount%!java.util.ArrayList.modCount", result.get(0));
    }
    
    @Test
    public void testGetDefFields4() {
        assertEquals(0, addMethodInList.getDefFields().size());
    }
    
    @Test
    public void testGetDefFields5() {
        assertEquals(0, expectedMethod.getDefFields().size());
    }
    
    @Test
    public void testGetUseFields1() {
        List<String> result = TestUtil.asSortedList(indexOfMethod.getUseFields().stream()
                .map(o -> o.getQualifiedName()));
        
        assertEquals(1, result.size());
        assertEquals("java.lang.String%value%!java.lang.String.value", result.get(0));
    }
    
    @Test
    public void testGetUseFields2() {
        assertEquals(0, addMethodInList.getUseFields().size());
    }
    
    @Test
    public void testGetUseFields3() {
        List<String> result = TestUtil.asSortedList(addMethodInArrayList.getUseFields().stream()
                .map(o -> o.getQualifiedName()));
        
        assertEquals(3, result.size());
        assertEquals("java.util.AbstractList%modCount%!java.util.ArrayList.modCount", result.get(0));
        assertEquals("java.util.ArrayList%elementData%!java.util.ArrayList.elementData", result.get(1));
        assertEquals("java.util.ArrayList%size%!java.util.ArrayList.size", result.get(2));
    }
    
    @Test
    public void testGetUseFields4() {
        List<String> result = TestUtil.asSortedList(removeMethod.getUseFields().stream()
                .map(o -> o.getQualifiedName()));
        
        assertEquals(2, result.size());
        assertEquals("java.util.ArrayList%elementData%!java.util.ArrayList.elementData", result.get(0));
        assertEquals("java.util.ArrayList%size%!java.util.ArrayList.size", result.get(1));
    }
    
    @Test
    public void testGetUseFields5() {
        assertEquals(0, expectedMethod.getUseFields().size());
    }
    
    @Test
    public void testGetAccessedMethods1() {
        List<String> result = TestUtil.asSortedList(indexOfMethod.getAccessedMethods().stream()
                .map(o -> o.getQualifiedName()));
        
        assertEquals(6, result.size());
        assertEquals("java.lang.Math#clamp( long int int )", result.get(0));
        assertEquals("java.lang.String#coder( )", result.get(1));
        assertEquals("java.lang.String#length( )", result.get(2));
        assertEquals("java.lang.StringLatin1#indexOf( byte[] int byte[] int int )", result.get(3));
        assertEquals("java.lang.StringUTF16#indexOf( byte[] int byte[] int int )", result.get(4));
        assertEquals("java.lang.StringUTF16#indexOfLatin1( byte[] int byte[] int int )", result.get(5));
    }
    
    @Test
    public void testGetAccessedMethods2() {
        assertEquals(0, addMethodInList.getAccessedMethods().size());
    }
    
    @Test
    public void testGetAccessedMethods3() {
        List<String> result = TestUtil.asSortedList(addMethodInArrayList.getAccessedMethods().stream()
                .map(o -> o.getQualifiedName()));
        
        assertEquals(1, result.size());
        assertEquals("java.util.ArrayList#add( java.lang.Object java.lang.Object[] int )", result.get(0));
    }
    
    @Test
    public void testGetAccessedMethods4() {
        List<String> result = TestUtil.asSortedList(removeMethod.getAccessedMethods().stream()
                .map(o -> o.getQualifiedName()));
        
        assertEquals(2, result.size());
        assertEquals("java.lang.Object#equals( java.lang.Object )", result.get(0));
        assertEquals("java.util.ArrayList#fastRemove( java.lang.Object[] int )", result.get(1));
    }
    
    @Test
    public void testGetAccessedMethods5() {
        assertEquals(0, expectedMethod.getAccessedMethods().size());
    }
}
