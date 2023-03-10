/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal.refmodel;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.jxplatform.util.FlakyByExternalLib;
import org.junit.experimental.categories.Category;
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
        BuilderTestUtil.clearProject();
        
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
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testInstanceOf1() {
        assertTrue(nameField instanceof JFieldInternal);
    }
    
    @Test
    public void testInstanceOf2() {
        assertTrue(rentalsField instanceof JFieldInternal);
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testInstanceOf3() {
        assertTrue(valueField instanceof JFieldCache);
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testInstanceOf4() {
        assertTrue(runnersField instanceof JFieldCache);
    }
    
    @Test
    public void testGetQualifiedName1() {
        assertEquals("org.jtool.videostore.after.Customer#name", nameField.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName2() {
        assertEquals("org.jtool.videostore.after.Customer#rentals", rentalsField.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName3() {
        assertEquals("java.lang.String#value", valueField.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName4() {
        assertEquals("org.junit.runners.Suite#runners", runnersField.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetDeclaringClass1() {
        assertEquals("org.jtool.videostore.after.Customer", nameField.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testGetDeclaringClass2() {
        assertEquals("org.jtool.videostore.after.Customer", rentalsField.getDeclaringClass().getClassName());
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetDeclaringClass3() {
        assertEquals("java.lang.String", valueField.getDeclaringClass().getClassName());
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testGetDeclaringClass4() {
        assertEquals("org.junit.runners.Suite", runnersField.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testIsInProject1() {
        assertTrue(nameField.isInProject());
    }
    
    @Test
    public void testIsInProject2() {
        assertTrue(rentalsField.isInProject());
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testIsInProject3() {
        assertFalse(valueField.isInProject());
    }
    
    @Category(FlakyByExternalLib.class)
    @Test
    public void testIsInProject4() {
        assertFalse(runnersField.isInProject());
    }
}