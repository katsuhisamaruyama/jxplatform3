/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.refmodel;

import org.jtool.srcmodel.JavaProject;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class JFieldCacheLibTest {
    
    private static JavaProject project;
    private static BytecodeClassStore bcStore;
    
    private static JField nameField;
    private static JField rentalsField;
    
    private static JField valueField;
    private static JField runnersField;
    
    @BeforeClass
    public static void setUp() {
        project = RefModelTestUtil.createProjectFromSourceWithLibCache("VideoStore", "/lib/*", "");
        bcStore = project.getCFGStore().getBCStore();
        
        JClass customerClass = bcStore.getJClass("org.jtool.videostore.after.Customer");
        nameField = customerClass.getField("name");
        rentalsField = customerClass.getField("rentals");
        
        JClass stringClass = bcStore.getJClass("java.lang.String");
        valueField = stringClass.getField("value");
        
        JClass suiteClass = bcStore.getJClass("org.junit.runners.Suite");
        runnersField = suiteClass.getField("runners");
    }
    
    @AfterClass
    public static void tearDown() {
        project.getModelBuilder().unbuild();
    }
    
    @Test
    public void testInstanceOf() {
        assertTrue(nameField instanceof JFieldInternal);
        
        assertTrue(rentalsField instanceof JFieldInternal);
        
        assertTrue(valueField instanceof JFieldCache);
        
        assertTrue(runnersField instanceof JFieldCache);
    }
    
    @Test
    public void testGetQualifiedName() {
        assertEquals("org.jtool.videostore.after.Customer#name",
                nameField.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Customer#rentals",
                rentalsField.getQualifiedName().fqn());
        
        assertEquals("java.lang.String#value",
                valueField.getQualifiedName().fqn());
        
        assertEquals("org.junit.runners.Suite#runners",
                runnersField.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetDeclaringClass() {
        assertEquals("org.jtool.videostore.after.Customer",
                nameField.getDeclaringClass().getClassName());
        
        assertEquals("org.jtool.videostore.after.Customer",
                rentalsField.getDeclaringClass().getClassName());
        
        assertEquals("java.lang.String",
                valueField.getDeclaringClass().getClassName());
        
        assertEquals("org.junit.runners.Suite",
                runnersField.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testIsInProject() {
        assertTrue(nameField.isInProject());
        
        assertTrue(rentalsField.isInProject());
        
        assertFalse(valueField.isInProject());
        
        assertFalse(runnersField.isInProject());
    }
}