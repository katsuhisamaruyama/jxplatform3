/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal.refmodel;

import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.srcmodel.JavaProject;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JFieldInternalTest {
    
    private static JavaProject project;
    private static BytecodeClassStore bcStore;
    
    private static JField nameField;
    private static JField rentalsField;
    private static JField priceField;
    private static JField titleField;
    private static JField movieField;
    private static JField daysRentedField;
    private static JField priceCodeField;
    
    @BeforeClass
    public static void setUp() {
        BuilderTestUtil.clearProject();
        
        project = RefModelTestUtil.createProjectFromSourceWithoutLibCache("VideoStore", "/lib/*", "");
        bcStore = project.getCFGStore().getBCStore();
        
        JClass customerClass = bcStore.getJClass("org.jtool.videostore.after.Customer");
        nameField = customerClass.getField("name");
        
        rentalsField = customerClass.getField("rentals");
        
        JClass movieClass = bcStore.getJClass("org.jtool.videostore.after.Movie");
        priceField = movieClass.getField("price");
        titleField = movieClass.getField("title");
        
        JClass rentalClass = bcStore.getJClass("org.jtool.videostore.after.Rental");
        movieField = rentalClass.getField("movie");
        daysRentedField = rentalClass.getField("daysRented");
        
        JClass priceClass = bcStore.getJClass("org.jtool.videostore.after.Price");
        priceCodeField = priceClass.getField("priceCode");
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
    
    @Test
    public void testInstanceOf3() {
        assertTrue(priceField instanceof JFieldInternal);
    }
    
    @Test
    public void testInstanceOf4() {
        assertTrue(titleField instanceof JFieldInternal);
    }
    
    @Test
    public void testInstanceOf5() {
        assertTrue(movieField instanceof JFieldInternal);
    }
    
    @Test
    public void testInstanceOf6() {
        assertTrue(daysRentedField instanceof JFieldInternal);
    }
    
    @Test
    public void testInstanceOf7() {
        assertTrue(priceCodeField instanceof JFieldInternal);
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
        assertEquals("org.jtool.videostore.after.Movie#price", priceField.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName4() {
        assertEquals("org.jtool.videostore.after.Movie#title", titleField.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName5() {
        assertEquals("org.jtool.videostore.after.Rental#movie", movieField.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName6() {
        assertEquals("org.jtool.videostore.after.Rental#daysRented", daysRentedField.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName7() {
        assertEquals("org.jtool.videostore.after.Price#priceCode", priceCodeField.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetDeclaringClass1() {
        assertEquals("org.jtool.videostore.after.Customer", nameField.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testGetDeclaringClass2() {
        assertEquals("org.jtool.videostore.after.Customer", rentalsField.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testGetDeclaringClass3() {
        assertEquals("org.jtool.videostore.after.Movie", priceField.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testGetDeclaringClass4() {
        assertEquals("org.jtool.videostore.after.Movie", titleField.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testGetDeclaringClass5() {
        assertEquals("org.jtool.videostore.after.Rental", movieField.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testGetDeclaringClass6() {
        assertEquals("org.jtool.videostore.after.Rental", daysRentedField.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testGetDeclaringClass7() {
        assertEquals("org.jtool.videostore.after.Price", priceCodeField.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testIsInProject1() {
        assertTrue(nameField.isInProject());
    }
    
    @Test
    public void testIsInProject2() {
        assertTrue(rentalsField.isInProject());
    }
    
    @Test
    public void testIsInProject3() {
        assertTrue(priceField.isInProject());
    }
    
    @Test
    public void testIsInProject4() {
        assertTrue(titleField.isInProject());
    }
    
    @Test
    public void testIsInProject5() {
        assertTrue(movieField.isInProject());
    }
    
    @Test
    public void testIsInProject6() {
        assertTrue(daysRentedField.isInProject());
    }
    
    @Test
    public void testIsInProject7() {
        assertTrue(priceCodeField.isInProject());
    }
}
