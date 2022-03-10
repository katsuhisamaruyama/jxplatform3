/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel;

import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class JavaPackageTest {
    
    private static JavaProject TetrisProject;
    private static JavaProject VideoStoreProject;
    
    @BeforeClass
    public static void setUp() {
        TetrisProject = BuilderTestUtil.createProject("Tetris", "", "");
        VideoStoreProject = BuilderTestUtil.createProject("VideoStore", "/lib/*", "");
    }
    
    @AfterClass
    public static void tearDown() {
        TetrisProject.getModelBuilder().unbuild();
        VideoStoreProject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testGetFiles1() {
        JavaPackage jp = TetrisProject.getPackage("(default)");
        List<String> result = jp.getFiles().stream().map(JavaFile::getName).collect(Collectors.toList());
        
        assertEquals(12, result.size());
        assertEquals("Block.java;"
                   + "BlueBlock.java;"
                   + "CyanBlock.java;"
                   + "GameInfo.java;"
                   + "GreenBlock.java;"
                   + "MagentaBlock.java;"
                   + "OrangeBlock.java;"
                   + "Pit.java;"
                   + "RedBlock.java;"
                   + "Tetris.java;"
                   + "Tile.java;"
                   + "YellowBlock.java",
            TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetFiles2() {
        JavaPackage jp = VideoStoreProject.getPackage("org.jtool.videostore.after");
        List<String> result = jp.getFiles().stream().map(JavaFile::getName).collect(Collectors.toList());
        
        assertEquals(10, result.size());
        assertEquals("ChildrensPrice.java;"
                   + "Customer.java;"
                   + "CustomerTest.java;"
                   + "Movie.java;"
                   + "MovieTest.java;"
                   + "NewReleasePrice.java;"
                   + "Price.java;"
                   + "RegularPrice.java;"
                   + "Rental.java;"
                   + "RentalTest.java",
            TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetName1() {
        JavaPackage jp = TetrisProject.getPackage("(default)");
        String result = jp.getName();
        
        assertEquals("(default)", result);
    }
    
    @Test
    public void testGetName2() {
        JavaPackage jp = VideoStoreProject.getPackage("org.jtool.videostore.after");
        String result = jp.getName();
        
        assertEquals("org.jtool.videostore.after", result);
    }
    
    @Test
    public void testIsDefault1() {
        JavaPackage jp = TetrisProject.getPackage("(default)");
        boolean result = jp.isDefault();
        
        assertTrue(result);
    }
    
    @Test
    public void testIsDefault2() {
        JavaPackage jp = VideoStoreProject.getPackage("org.jtool.videostore.after");
        boolean result = jp.isDefault();
        
        assertFalse(result);
    }
    
    @Test
    public void testGetClasses1() {
        JavaPackage jp = TetrisProject.getPackage("(default)");
        Set<JavaClass> result = jp.getClasses();
        
        assertEquals(12, result.size());
        assertEquals("Block;"
                   + "BlueBlock;"
                   + "CyanBlock;"
                   + "GameInfo;"
                   + "GreenBlock;"
                   + "MagentaBlock;"
                   + "OrangeBlock;"
                   + "Pit;"
                   + "RedBlock;"
                   + "Tetris;"
                   + "Tile;"
                   + "YellowBlock",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetClasses2() {
        JavaPackage jp = VideoStoreProject.getPackage("org.jtool.videostore.after");
        Set<JavaClass> result = jp.getClasses();
        
        assertEquals(11, result.size());
        assertEquals("org.jtool.videostore.after.ChildrensPrice;"
                   + "org.jtool.videostore.after.Customer;"
                   + "org.jtool.videostore.after.CustomerTest;"
                   + "org.jtool.videostore.after.Movie;"
                   + "org.jtool.videostore.after.Movie.PriceCode;"
                   + "org.jtool.videostore.after.MovieTest;"
                   + "org.jtool.videostore.after.NewReleasePrice;"
                   + "org.jtool.videostore.after.Price;"
                   + "org.jtool.videostore.after.RegularPrice;"
                   + "org.jtool.videostore.after.Rental;"
                   + "org.jtool.videostore.after.RentalTest",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetEfferentJavaPackages1() {
        JavaPackage jp = TetrisProject.getPackage("(default)");
        List<String> result = jp.getEfferentJavaPackages().stream().map(JavaPackage::getName).collect(Collectors.toList());
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetEfferentJavaPackages2() {
        JavaPackage jp = VideoStoreProject.getPackage("org.jtool.videostore.after");
        List<String> result = jp.getEfferentJavaPackages().stream().map(JavaPackage::getName).collect(Collectors.toList());
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetEfferentJavaPackages3() {
        JavaPackage jp = VideoStoreProject.getPackage("org.jtool.videostore.use");
        List<String> result = jp.getEfferentJavaPackages().stream().map(JavaPackage::getName).collect(Collectors.toList());
        
        assertEquals(1, result.size());
        assertEquals("org.jtool.videostore.after", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetAfferentJavaPackages1() {
        JavaPackage jp = TetrisProject.getPackage("(default)");
        List<String> result = jp.getAfferentJavaPackages().stream().map(JavaPackage::getName).collect(Collectors.toList());
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAfferentJavaPackages2() {
        JavaPackage jp = VideoStoreProject.getPackage("org.jtool.videostore.after");
        List<String> result = jp.getAfferentJavaPackages().stream().map(JavaPackage::getName).collect(Collectors.toList());
        
        assertEquals(1, result.size());
        assertEquals("org.jtool.videostore.use", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetAfferentJavaPackages3() {
        JavaPackage jp = VideoStoreProject.getPackage("org.jtool.videostore.use");
        List<String> result = jp.getAfferentJavaPackages().stream().map(JavaPackage::getName).collect(Collectors.toList());
        
        assertEquals(0, result.size());
    }
}
