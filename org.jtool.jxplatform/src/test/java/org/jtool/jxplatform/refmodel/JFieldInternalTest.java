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
        String name = "VideoStore";
        String target = TestUtil.getTarget(name);
        String classpath = target + "/lib/*";
        
        project = RefModelTestUtil.createProjectFromSourceWithoutLibCache(target, classpath);
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
    }
    
    @Test
    public void testInstanceOf() {
        assertTrue(nameField instanceof JFieldInternal);
        
        assertTrue(rentalsField instanceof JFieldInternal);
        
        assertTrue(priceField instanceof JFieldInternal);
        
        assertTrue(titleField instanceof JFieldInternal);
        
        assertTrue(movieField instanceof JFieldInternal);
        
        assertTrue(daysRentedField instanceof JFieldInternal);
        
        assertTrue(priceCodeField instanceof JFieldInternal);
    }
    
    @Test
    public void testGetQualifiedName() {
        assertEquals("org.jtool.videostore.after.Customer#name",
                nameField.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Customer#rentals",
                rentalsField.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Movie#price",
                priceField.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Movie#title",
                titleField.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Rental#movie",
                movieField.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Rental#daysRented",
                daysRentedField.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Price#priceCode",
                priceCodeField.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetDeclaringClass() {
        assertEquals("org.jtool.videostore.after.Customer",
                nameField.getDeclaringClass().getClassName());
        
        assertEquals("org.jtool.videostore.after.Customer",
                rentalsField.getDeclaringClass().getClassName());
        
        assertEquals("org.jtool.videostore.after.Movie",
                priceField.getDeclaringClass().getClassName());
        
        assertEquals("org.jtool.videostore.after.Movie",
                titleField.getDeclaringClass().getClassName());
        
        assertEquals("org.jtool.videostore.after.Rental",
                movieField.getDeclaringClass().getClassName());
        
        assertEquals("org.jtool.videostore.after.Rental",
                daysRentedField.getDeclaringClass().getClassName());
        
        assertEquals("org.jtool.videostore.after.Price",
                priceCodeField.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testIsInProject() {
        assertTrue(nameField.isInProject());
        
        assertTrue(rentalsField.isInProject());
        
        assertTrue(priceField.isInProject());
        
        assertTrue(titleField.isInProject());
        
        assertTrue(movieField.isInProject());
        
        assertTrue(daysRentedField.isInProject());
        
        assertTrue(priceCodeField.isInProject());
    }
}
