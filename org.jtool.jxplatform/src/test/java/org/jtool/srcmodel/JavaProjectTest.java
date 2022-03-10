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
import static org.junit.Assert.assertNull;

public class JavaProjectTest {
    
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
    public void testGetAllProjects() {
        List<String> result = JavaProject.getAllProjects().stream()
                .map(JavaProject::getName).collect(Collectors.toList());
        
        assertEquals(2, result.size());
        assertEquals("Tetris;VideoStore", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetProject1() {
        JavaProject project = JavaProject.findProject(TetrisProject.getPath());
        
        assertEquals("Tetris", project.getName());
    }
    
    @Test
    public void testGetProject2() {
        JavaProject project = JavaProject.findProject(VideoStoreProject.getPath());
        
        assertEquals("VideoStore", project.getName());
    }
    
    @Test
    public void testGetName1() {
        String result = TetrisProject.getName();
        
        assertEquals("Tetris", result);
    }
    
    @Test
    public void testGetName2() {
        String result = VideoStoreProject.getName();
        
        assertEquals("VideoStore", result);
    }
    
    @Test
    public void testGetPathRelativeToWorkspace1() {
        String result = TetrisProject.getPathRelativeToWorkspace();
        
        assertEquals(TetrisProject.getPath(), result);
    }
    
    @Test
    public void testGetPathRelativeToWorkspace2() {
        String result = VideoStoreProject.getPathRelativeToWorkspace();
        
        assertEquals(VideoStoreProject.getPath(), result);
    }
    
    @Test
    public void testGetPath1() {
        String result = TetrisProject.getPath();
        
        assertEquals(BuilderTestUtil.getTarget("Tetris"), result);
    }
    
    @Test
    public void testGetPath2() {
        String result = VideoStoreProject.getPath();
        
        assertEquals(BuilderTestUtil.getTarget("VideoStore"), result);
    }
    
    @Test
    public void testGetFile1() {
        JavaFile result = TetrisProject.getFile(TetrisProject.getPath() + "/Block.java");
        
        assertEquals("Block.java", result.getName());
    }
    
    @Test
    public void testGetFile2() {
        JavaFile result = VideoStoreProject.getFile(VideoStoreProject.getPath() + "/org/jtool/videostore/after/Customer.java");
        
        assertEquals("Customer.java", result.getName());
    }
    
    @Test
    public void testGetFile3() {
        JavaFile result = VideoStoreProject.getFile(VideoStoreProject.getPath() + "/org/jtool/videostore/after/Customer1.java");
        
        assertNull(result);
    }
    
    @Test
    public void testGetFiles1() {
        List<String> result = TetrisProject.getFiles().stream().map(JavaFile::getName).collect(Collectors.toList());
        
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
        List<String> result = VideoStoreProject.getFiles().stream().map(JavaFile::getName).collect(Collectors.toList());
        
        assertEquals(17, result.size());
        assertEquals("ChildrensPrice.java;"
                   + "Customer.java;"
                   + "Customer.java;"
                   + "CustomerTest.java;"
                   + "CustomerTest.java;"
                   + "CustomerUse.java;"
                   + "Movie.java;"
                   + "Movie.java;"
                   + "MovieTest.java;"
                   + "MovieTest.java;"
                   + "NewReleasePrice.java;"
                   + "Price.java;"
                   + "RegularPrice.java;"
                   + "Rental.java;"
                   + "Rental.java;"
                   + "RentalTest.java;"
                   + "RentalTest.java",
            TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetPackage1() {
        JavaPackage result = TetrisProject.getPackage("(default)");
        
        assertEquals("(default)", result.getName());
    }
    
    @Test
    public void testGetPackage2() {
        JavaPackage result = VideoStoreProject.getPackage("org.jtool.videostore.after");
        
        assertEquals("org.jtool.videostore.after", result.getName());
    }
    
    @Test
    public void testGetPackage3() {
        JavaPackage result = VideoStoreProject.getPackage("org.jtool.videostore.after1");
        
        assertNull(result);
    }
    
    @Test
    public void testGetPackages1() {
        List<String> result = TetrisProject.getPackages().stream().map(JavaPackage::getName).collect(Collectors.toList());
        
        assertEquals(1, result.size());
        assertEquals("(default)", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetPackages2() {
        List<String> result = VideoStoreProject.getPackages().stream().map(JavaPackage::getName).collect(Collectors.toList());
        
        assertEquals(3, result.size());
        assertEquals("org.jtool.videostore.after;org.jtool.videostore.before;org.jtool.videostore.use",
                TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetClasses1() {
        List<JavaClass> result = TetrisProject.getClasses();
        
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
        List<JavaClass> result = VideoStoreProject.getClasses();
        
        assertEquals(19, result.size());
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
                   + "org.jtool.videostore.after.RentalTest;"
                   + "org.jtool.videostore.before.Customer;"
                   + "org.jtool.videostore.before.CustomerTest;"
                   + "org.jtool.videostore.before.Movie;"
                   + "org.jtool.videostore.before.Movie.PriceCode;"
                   + "org.jtool.videostore.before.MovieTest;"
                   + "org.jtool.videostore.before.Rental;"
                   + "org.jtool.videostore.before.RentalTest;"
                   + "org.jtool.videostore.use.CustomerUse",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetClass1() {
        JavaClass result = TetrisProject.getClass("Block");
        
        assertEquals("Block", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetClass2() {
        JavaClass result = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        
        assertEquals("org.jtool.videostore.after.Customer", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetClass3() {
        JavaClass result = VideoStoreProject.getClass("org.jtool.videostore.after.Customer1");
        
        assertNull(result);
    }
    
    @Test
    public void testGetExternalClass1() {
        JavaClass result = TetrisProject.getExternalClass("java.lang.String");
        
        assertEquals("java.lang.String", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetExternalClass2() {
        JavaClass result = VideoStoreProject.getExternalClass("java.util.ArrayList");
        
        assertEquals("java.util.ArrayList", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetExternalClass3() {
        JavaClass result = TetrisProject.getClass("java.util.String");
        
        assertNull(result);
    }
    
    @Test
    public void testCollectDependentClasses1() {
        JavaClass jc = TetrisProject.getClass("Block");
        Set<JavaClass> result = TetrisProject.collectDependentClasses(jc);
        
        assertEquals(16, result.size());
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
                   + "YellowBlock;"
                   + "java.awt.Canvas;"
                   + "java.awt.event.KeyListener;"
                   + "java.lang.Object;"
                   + "java.lang.Runnable;"
                   + "javax.swing.JFrame",
         TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testCollectDependentClasses2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        Set<JavaClass> result = VideoStoreProject.collectDependentClasses(jc);
        
        assertEquals(3, result.size());
        assertEquals("java.lang.Object;org.jtool.videostore.after.CustomerTest;org.jtool.videostore.use.CustomerUse",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetClassPath1() {
        String[] result = TetrisProject.getClassPath();
        
        assertEquals(1, result.length);
        assertEquals(TetrisProject.getPath(), result[0]);
    }
    
    @Test
    public void testGetClassPath2() {
        String[] result = VideoStoreProject.getClassPath();
        
        assertEquals(2, result.length);
        assertEquals(VideoStoreProject.getPath() + "/lib/junit-4.12.jar", result[0]);
        assertEquals(VideoStoreProject.getPath() + "/lib/hamcrest-core-1.3.jar", result[1]);
    }
    
    @Test
    public void testGetSourcePath1() {
        String[] result = TetrisProject.getSourcePath();
        
        assertEquals(1, result.length);
        assertEquals(TetrisProject.getPath(), result[0]);
    }
    
    @Test
    public void testGetSourcePath2() {
        String[] result = VideoStoreProject.getSourcePath();
        
        assertEquals(1, result.length);
        assertEquals(VideoStoreProject.getPath(), result[0]);
    }
    
    @Test
    public void testGetBinaryPath1() {
        String[] result = TetrisProject.getBinaryPath();
        
        assertEquals(1, result.length);
        assertEquals(TetrisProject.getPath(), result[0]);
    }
    
    @Test
    public void testGetBinaryPath2() {
        String[] result = VideoStoreProject.getBinaryPath();
        
        assertEquals(1, result.length);
        assertEquals(VideoStoreProject.getPath(), result[0]);
    }
}
