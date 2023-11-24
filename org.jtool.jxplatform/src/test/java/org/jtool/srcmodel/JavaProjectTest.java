/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel;

import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class JavaProjectTest {
    
    private static JavaProject TetrisProject;
    private static JavaProject VideoStoreProject;
    
    @BeforeClass
    public static void setUp() {
        BuilderTestUtil.clearProject();
        
        TetrisProject = BuilderTestUtil.getProject("Tetris");
        VideoStoreProject = BuilderTestUtil.getProject("VideoStore");
    }
    
    @AfterClass
    public static void tearDown() {
        BuilderTestUtil.clearProject();
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
        String path = TetrisProject.getPath() + "/Block.java";
        String filepath = path.replace('/', File.separatorChar);
        JavaFile result = TetrisProject.getFile(filepath);
        
        assertEquals("Block.java", result.getName());
    }
    
    @Test
    public void testGetFile2() {
        String path = VideoStoreProject.getPath() + "/org/jtool/videostore/after/Customer.java";
        String filepath = path.replace('/', File.separatorChar);
        JavaFile result = VideoStoreProject.getFile(filepath);
        
        assertEquals("Customer.java", result.getName());
    }
    
    @Test
    public void testGetFile3() {
        String path = VideoStoreProject.getPath() + "/org/jtool/videostore/after/Customer1.java";
        String filepath = path.replace('/', File.separatorChar);
        JavaFile result = VideoStoreProject.getFile(filepath);
        
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
    public void testGetPackage4() {
        JavaPackage result = VideoStoreProject.getPackage("java.util");
        
        assertEquals("java.util", result.getName());
    }
    
    @Test
    public void testGetPackages1() {
        List<String> result = TetrisProject.getPackages().stream()
                .map(JavaPackage::getName).collect(Collectors.toList());
        
        assertEquals(6, result.size());
        assertEquals("(default);"
                   + "java.awt;"
                   + "java.awt.event;"
                   + "java.lang;"
                   + "java.util;"
                   + "javax.swing",
            TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetPackages2() {
        List<String> result = VideoStoreProject.getPackages().stream()
                .map(JavaPackage::getName).collect(Collectors.toList());
        
        assertEquals(7, result.size());
        assertEquals("java.io;java.lang;java.util;"
                   + "org.jtool.videostore.after;org.jtool.videostore.before;org.jtool.videostore.use;"
                   + "org.junit",
            TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetPackagesInProject1() {
        List<String> result = TetrisProject.getPackagesInProject().stream()
                .map(JavaPackage::getName).collect(Collectors.toList());
        
        assertEquals(1, result.size());
        assertEquals("(default)", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetPackagesInProject2() {
        List<String> result = VideoStoreProject.getPackagesInProject().stream()
                .map(JavaPackage::getName).collect(Collectors.toList());
        
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
    public void testGetExternalClasses1() {
        List<JavaClass> result = TetrisProject.getExternalClasses();
        
        assertEquals(23, result.size());
        assertEquals("java.awt.Canvas;"
                   + "java.awt.Color;"
                   + "java.awt.Component;"
                   + "java.awt.Container;"
                   + "java.awt.FlowLayout;"
                   + "java.awt.Font;"
                   + "java.awt.Graphics;"
                   + "java.awt.Image;"
                   + "java.awt.Window;"
                   + "java.awt.event.KeyEvent;"
                   + "java.awt.event.KeyListener;"
                   + "java.lang.InterruptedException;"
                   + "java.lang.Object;"
                   + "java.lang.Runnable;"
                   + "java.lang.Runtime;"
                   + "java.lang.String;"
                   + "java.lang.System;"
                   + "java.lang.Thread;"
                   + "java.util.HashSet;"
                   + "java.util.Iterator;"
                   + "java.util.Random;"
                   + "java.util.Set;"
                   + "javax.swing.JFrame",
            TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetExternalClasses2() {
        List<JavaClass> result = VideoStoreProject.getExternalClasses();
        
        assertEquals(10, result.size());
        assertEquals("java.io.PrintStream;"
                   + "java.io.Serializable;"
                   + "java.lang.AssertionError;"
                   + "java.lang.Enum;"
                   + "java.lang.Object;"
                   + "java.lang.String;"
                   + "java.lang.System;"
                   + "java.util.ArrayList;"
                   + "java.util.List;"
                   + "org.junit.Assert",
            TestUtil.asSortedStrOf(result));
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
    public void testCollectClassesDependingOn1() {
        JavaClass jc = TetrisProject.getClass("Block");
        Set<JavaClass> result = TetrisProject.collectClassesDependingOn(jc);
        
        assertEquals(10, result.size());
        assertEquals("BlueBlock;"
                   + "CyanBlock;"
                   + "GameInfo;"
                   + "GreenBlock;"
                   + "MagentaBlock;"
                   + "OrangeBlock;"
                   + "Pit;"
                   + "RedBlock;"
                   + "Tetris;"
                   + "YellowBlock",
         TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testCollectClassesDependingOn2() {
        JavaClass jc = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
        Set<JavaClass> result = VideoStoreProject.collectClassesDependingOn(jc);
        
        assertEquals(2, result.size());
        assertEquals("org.jtool.videostore.after.CustomerTest;"
                   + "org.jtool.videostore.use.CustomerUse",
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
