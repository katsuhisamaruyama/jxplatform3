/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel;

import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JavaElementTest {
    
    private static JavaProject DrawToolProject;
    private static JavaProject SliceProject;
    private static JavaProject VideoStoreProject;
    
    @BeforeClass
    public static void setUp() {
        DrawToolProject = BuilderTestUtil.createProject("DrawTool", "", "/src");
        SliceProject = BuilderTestUtil.createProject("Slice", "", "");
        VideoStoreProject = BuilderTestUtil.createProject("VideoStore", "/lib/*", "");
    }
    
    @AfterClass
    public static void tearDown() {
        DrawToolProject.getModelBuilder().unbuild();
        SliceProject.getModelBuilder().unbuild();
        VideoStoreProject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testGetSource1() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        String result = jc.getSource();
        
        assertEquals(1244, result.length());
        assertTrue(result.startsWith("public class Customer {"));
    }
    
    @Test
    public void testGetSource2() {
        JavaClass jc = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.Figure");
        String result = jc.getSource();
        
        assertEquals(8497, result.length());
        assertTrue(result.startsWith("/**\n * Represents a figure\n */"));
    }
    
    @Test
    public void testGetSource3() {
        JavaClass jc = SliceProject.getClass("Test200");
        String result = jc.getSource();
        
        assertEquals(298, result.length());
        assertTrue(result.startsWith("class Test200 {"));
    }
    
    @Test
    public void testGetSource4() {
        JavaMethod jm = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getMethod("statement( )");
        String result = jm.getSource();
        
        assertEquals(510, result.length());
        assertTrue(result.startsWith("public String statement() {"));
    }
    
    @Test
    public void testGetSource5() {
        JavaMethod jm = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.Figure").getMethod("setStart( int int )");
        String result = jm.getSource();
        
        assertEquals(246, result.length());
        assertTrue(result.startsWith("/**\n     * Sets the start point of this figure."));
    }
    
    @Test
    public void testGetSource6() {
        JavaField jf = VideoStoreProject.getClass("org.jtool.videostore.after.Customer").getField("rentals");
        String result = jf.getSource();
        
        assertEquals(27, result.length());
        assertTrue(result.startsWith("rentals = new ArrayList<>()"));
    }
    
    @Test
    public void testGetSource7() {
        JavaField jf = DrawToolProject.getClass("jp.ac.ritsumei.cs.draw.Figure").getField("color");
        String result = jf.getSource();
        
        assertEquals(5, result.length());
        assertTrue(result.startsWith("color"));
    }
}
