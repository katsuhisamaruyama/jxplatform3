/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal.refmodel;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.util.FlakyByExternalLib;
import org.junit.experimental.categories.Category;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

@Category(FlakyByExternalLib.class)
public class JFieldExternalTest {
    
    private static JavaProject project;
    private static BytecodeClassStore bcStore;
    
    private static JField valueField;
    private static JField sizeField;
    private static JField inField;
    private static JField runnersField;
    
    @BeforeClass
    public static void setUp() {
        project = RefModelTestUtil.createProjectFromSourceWithoutLibCache("VideoStore", "/lib/*", "");
        bcStore = project.getCFGStore().getBCStore();
        
        JClass stringClass = bcStore.getJClass("java.lang.String");
        valueField = stringClass.getField("value");
        
        JClass arrayListClass = bcStore.getJClass("java.util.ArrayList");
        sizeField = arrayListClass.getField("size");
        
        JClass systemClass = bcStore.getJClass("java.lang.System");
        inField = systemClass.getField("in");
        
        JClass suiteClass = bcStore.getJClass("org.junit.runners.Suite");
        runnersField = suiteClass.getField("runners");
    }
    
    @AfterClass
    public static void tearDown() {
        project.getModelBuilder().unbuild();
    }
    
    @Test
    public void testInstanceOf1() {
        assertTrue(valueField instanceof JFieldExternal);
    }
    
    @Test
    public void testInstanceOf2() {
        assertTrue(sizeField instanceof JFieldExternal);
    }
    
    @Test
    public void testInstanceOf3() {
        assertTrue(inField instanceof JFieldExternal);
    }
    
    @Test
    public void testInstanceOf4() {
        assertTrue(runnersField instanceof JFieldExternal);
    }
    
    @Test
    public void testGetQualifiedName1() {
        assertEquals("java.lang.String#value", valueField.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName2() {
        assertEquals("java.util.ArrayList#size", sizeField.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName3() {
        assertEquals("java.lang.System#in", inField.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName4() {
        assertEquals("org.junit.runners.Suite#runners", runnersField.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetDeclaringClass1() {
        assertEquals("java.lang.String", valueField.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testGetDeclaringClass2() {
        assertEquals("java.util.ArrayList", sizeField.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testGetDeclaringClass3() {
        assertEquals("java.lang.System", inField.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testGetDeclaringClass4() {
        assertEquals("org.junit.runners.Suite", runnersField.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testIsInProject1() {
        assertFalse(valueField.isInProject());
    }
    
    @Test
    public void testIsInProject2() {
        assertFalse(sizeField.isInProject());
    }
    
    @Test
    public void testIsInProject3() {
        assertFalse(inField.isInProject());
    }
    
    @Test
    public void testIsInProject4() {
        assertFalse(runnersField.isInProject());
    }
}
