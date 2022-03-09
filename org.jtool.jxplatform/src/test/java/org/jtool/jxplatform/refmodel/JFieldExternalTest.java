/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.refmodel;

import org.jtool.srcmodel.JavaProject;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

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
    public void testInstanceOf() {
        assertTrue(valueField instanceof JFieldExternal);
        
        assertTrue(sizeField instanceof JFieldExternal);
        
        assertTrue(inField instanceof JFieldExternal);
        
        assertTrue(runnersField instanceof JFieldExternal);
    }
    
    @Test
    public void testGetQualifiedName() {
        assertEquals("java.lang.String#value",
                valueField.getQualifiedName().fqn());
        
        assertEquals("java.util.ArrayList#size",
                sizeField.getQualifiedName().fqn());
        
        assertEquals("java.lang.System#in",
                inField.getQualifiedName().fqn());
        
        assertEquals("org.junit.runners.Suite#runners",
                runnersField.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetDeclaringClass() {
        assertEquals("java.lang.String",
                valueField.getDeclaringClass().getClassName());
        
        assertEquals("java.util.ArrayList",
                sizeField.getDeclaringClass().getClassName());
        
        assertEquals("java.lang.System",
                inField.getDeclaringClass().getClassName());
        
        assertEquals("org.junit.runners.Suite",
                runnersField.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testIsInProject() {
        assertFalse(valueField.isInProject());
        
        assertFalse(sizeField.isInProject());
        
        assertFalse(inField.isInProject());
        
        assertFalse(runnersField.isInProject());
    }
}
