/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel;

import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import org.eclipse.jdt.core.dom.CompilationUnit;
import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JavaFileTest {
    
    private static JavaProject CSclassroomProject;
    //private static JavaProject DrawToolProject;
    //private static JavaProject LambdaProject;
    //private static JavaProject SimpleProject;
    //private static JavaProject SliceProject;
    private static JavaProject TetrisProject;
    private static JavaProject VideoStoreProject;
    
    @BeforeClass
    public static void setUp() {
        CSclassroomProject = BuilderTestUtil.createProject("CS-classroom", "/lib/*", "/src");
        //DrawToolProject = BuilderTestUtil.createProject("DrawTool", "", "/src");
        //LambdaProject = BuilderTestUtil.createProject("Lambda", "", "");
        //SimpleProject = BuilderTestUtil.createProject("Simple", "", "");
        //SliceProject = BuilderTestUtil.createProject("Slice", "", "");
        TetrisProject = BuilderTestUtil.createProject("Tetris", "", "");
        VideoStoreProject = BuilderTestUtil.createProject("VideoStore", "/lib/*", "");
    }
    
    @AfterClass
    public static void tearDown() {
        CSclassroomProject.getModelBuilder().unbuild();
        //DrawToolProject.getModelBuilder().unbuild();
        //LambdaProject.getModelBuilder().unbuild();
        //SimpleProject.getModelBuilder().unbuild();
        //SliceProject.getModelBuilder().unbuild();
        TetrisProject.getModelBuilder().unbuild();
        VideoStoreProject.getModelBuilder().unbuild();
    }
    
    private JavaFile getFile(JavaProject project, String fpath) {
        String fullPath = project.getPath() + "/" + fpath;
        String filepath = fullPath.replace('/', File.separatorChar);
        return project.getFile(filepath);
    }
    
    @Test
    public void testGetCompilationUnit1() {
        JavaFile jfile = getFile(VideoStoreProject, "org/jtool/videostore/after/Customer.java");
        CompilationUnit result = jfile.getCompilationUnit();
        
        assertNotNull(result);
    }
    
    @Test
    public void testGetCompilationUnit2() {
        JavaFile jfile = getFile(TetrisProject, "Block.java");
        CompilationUnit result = jfile.getCompilationUnit();
        
        assertNotNull(result);
    }
    
    @Test
    public void testGetProject1() {
        JavaFile jfile = getFile(VideoStoreProject, "org/jtool/videostore/after/Customer.java");
        JavaProject result = jfile.getProject();
        
        assertEquals("VideoStore", result.getName());
    }
    
    @Test
    public void testGetProject2() {
        JavaFile jfile = getFile(TetrisProject, "Block.java");
        JavaProject result = jfile.getProject();
        
        assertEquals("Tetris", result.getName());
    }
    
    @Test
    public void testGetPath1() {
        JavaFile jfile = getFile(VideoStoreProject, "org/jtool/videostore/after/Customer.java");
        String result = jfile.getPath();
        
        assertEquals(VideoStoreProject.getPath() + "/org/jtool/videostore/after/Customer.java", result);
    }
    
    @Test
    public void testGetPath2() {
        JavaFile jfile = getFile(TetrisProject, "Block.java");
        String result = jfile.getPath();
        
        assertEquals(TetrisProject.getPath()+ "/Block.java", result);
    }
    
    @Test
    public void testGetRelativePath1() {
        JavaFile jfile = getFile(VideoStoreProject, "org/jtool/videostore/after/Customer.java");
        String result = jfile.getRelativePath();
        
        assertEquals("org/jtool/videostore/after/Customer.java", result);
    }
    
    @Test
    public void testGetRelativePath2() {
        JavaFile jfile = getFile(TetrisProject, "Block.java");
        String result = jfile.getRelativePath();
        
        assertEquals("Block.java", result);
    }
    
    @Test
    public void testGetName1() {
        JavaFile jfile = getFile(VideoStoreProject, "org/jtool/videostore/after/Customer.java");
        String result = jfile.getName();
        
        assertEquals("Customer.java", result);
    }
    
    @Test
    public void testGetName2() {
        JavaFile jfile = getFile(TetrisProject, "Block.java");
        String result = jfile.getName();
        
        assertEquals("Block.java", result);
    }
    
    @Test
    public void testGetSource1() {
        JavaFile jfile = getFile(VideoStoreProject, "org/jtool/videostore/after/Customer.java");
        String result = jfile.getSource();
        
        assertEquals(1334, result.length());
        assertEquals(1655168699, result.hashCode());
    }
    
    @Test
    public void testGetSource2() {
        JavaFile jfile = getFile(TetrisProject, "Block.java");
        String result = jfile.getSource();
        
        assertEquals(4791, result.length());
        assertEquals(-657453646, result.hashCode());
    }
    
    @Test
    public void testGetCharset1() {
        JavaFile jfile = getFile(VideoStoreProject, "org/jtool/videostore/after/Customer.java");
        String result = jfile.getCharset();
        
        assertEquals("UTF-8", result);
    }
    
    @Test
    public void testGetCharset2() {
        JavaFile jfile = getFile(TetrisProject, "Block.java");
        String result = jfile.getCharset();
        
        assertEquals("UTF-8", result);
    }
    
    @Test
    public void testGetPackage1() {
        JavaFile jfile = getFile(VideoStoreProject, "org/jtool/videostore/after/Customer.java");
        JavaPackage result = jfile.getPackage();
        
        assertEquals("org.jtool.videostore.after", result.getName());
    }
    
    @Test
    public void testGetPackage2() {
        JavaFile jfile = getFile(TetrisProject, "Block.java");
        JavaPackage result = jfile.getPackage();
        
        assertEquals("(default)", result.getName());
    }
    
    @Test
    public void testGetClass1() {
        JavaFile jfile = getFile(VideoStoreProject, "org/jtool/videostore/after/Customer.java");
        Set<JavaClass> result = jfile.getClasses();
        
        assertEquals(1, result.size());
        assertEquals("org.jtool.videostore.after.Customer", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetClass2() {
        JavaFile jfile = getFile(TetrisProject, "Block.java");
        Set<JavaClass> result = jfile.getClasses();
        
        assertEquals(1, result.size());
        assertEquals("Block", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetClass3() {
        JavaFile jfile = getFile(CSclassroomProject, "src/Sample13.java");
        Set<JavaClass> result = jfile.getClasses();
        
        assertEquals(2, result.size());
        assertEquals("PanelA;Sample13", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testGetImports1() {
        JavaFile jfile = getFile(VideoStoreProject, "org/jtool/videostore/after/Customer.java");
        List<String> result = jfile.getImports().stream()
                .map(Object::toString).map(s -> s.replace("\n", "")).collect(Collectors.toList());
        
        assertEquals(2, result.size());
        assertEquals("import java.util.ArrayList;;import java.util.List;", TestUtil.asSortedStr(result));
    }
    
    @Test
    public void testGetImports2() {
        JavaFile jfile = getFile(TetrisProject, "Block.java");
        List<String> result = jfile.getImports().stream()
                .map(Object::toString).map(s -> s.replace("\n", "")).collect(Collectors.toList());
        
        assertEquals(2, result.size());
        assertEquals("import java.awt.*;;import java.util.*;", TestUtil.asSortedStr(result));
    }
}