/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder;

import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaFile;
import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.project.ModelBuilderBatchImpl;
import org.jtool.jxplatform.util.BuilderTestUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

public class IncrementalModelBuilderTest {
    
    private void createFile(String target) {
        String content =
                "class New {\n" +
                "    private int x = 1;\n" +
                "    public void m() {\n" +
                "        int y = 1;\n" +
                "  }\n" +
                "}";
        BuilderTestUtil.createFile(Paths.get(target + File.separator + "New.java"), content);
    }
    
    private void modifyFile(String target) {
        String content =
                "class New {\n" +
                "    private int a = 1;\n" +
                "    public void b() {\n" +
                "        int c = 1;\n" +
                "  }\n" +
                "}";
        deleteFile(target);
        BuilderTestUtil.createFile(Paths.get(target + File.separator + "New.java"), content);
    }
    
    private void deleteFile(String target) {
        BuilderTestUtil.deleteFile(Paths.get(target + File.separator + "New.java"));
    }
    
    private FileTime touchFile(String target, String name) {
        try {
            Path path = Paths.get(target + File.separator + name);
            FileTime originalTime = Files.getLastModifiedTime(path);
            
            FileTime nowTime = FileTime.from(Instant.now());
            path = Files.setLastModifiedTime(path, nowTime);
            return originalTime;
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }
    
    private void revertFile(String target, String name, FileTime originalTime) {
        try {
            Path path = Paths.get(target + File.separator + name);
            path = Files.setLastModifiedTime(path, originalTime);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    @Test
    public void testAddFile1() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        IncrementalModelBuilder builder = new IncrementalModelBuilder(new ModelBuilderBatchImpl());
        builder.build(name, target, target);
        
        createFile(target);
        
        builder.addFile(target + File.separator + "New.java");
        
        Set<Path> result1 = builder.getNewFiles();
        assertEquals("New.java", BuilderTestUtil.asStrOfPaths(result1));
        
        Set<JavaFile> result2 = builder.getObsoleteFiles();
        assertEquals("", BuilderTestUtil.asStrOfFiles(result2));
        
        deleteFile(target);
        
        builder.unbuild();
    }
    
    @Test
    public void testAddFile2() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        IncrementalModelBuilder builder = new IncrementalModelBuilder(new ModelBuilderBatchImpl());
        builder.build(name, target, target);
        
        builder.addFile(target + File.separator + "New1.java");
        
        Set<Path> result1 = builder.getNewFiles();
        assertEquals("", BuilderTestUtil.asStrOfPaths(result1));
        
        Set<JavaFile> result2 = builder.getObsoleteFiles();
        assertEquals("", BuilderTestUtil.asStrOfFiles(result2));
        
        builder.unbuild();
    }
    
    @Test
    public void testAddFile3() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        IncrementalModelBuilder builder = new IncrementalModelBuilder(new ModelBuilderBatchImpl());
        builder.build(name, target, target);
        
        builder.addFile(target + File.separator + "Customer.java");
        
        Set<Path> result1 = builder.getNewFiles();
        assertEquals("Customer.java", BuilderTestUtil.asStrOfPaths(result1));
        
        Set<JavaFile> result2 = builder.getObsoleteFiles();
        assertEquals("Customer.java;CustomerTest.java", BuilderTestUtil.asStrOfFiles(result2));
        
        builder.unbuild();
    }
    
    @Test
    public void testRemoveFile1() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        createFile(target);
        
        IncrementalModelBuilder builder = new IncrementalModelBuilder(new ModelBuilderBatchImpl());
        builder.build(name, target, target);
        
        deleteFile(target);
        
        builder.removeFile(target + File.separator + "New.java");
        
        Set<Path> result1 = builder.getNewFiles();
        assertEquals("", BuilderTestUtil.asStrOfPaths(result1));
        
        Set<JavaFile> result2 = builder.getObsoleteFiles();
        assertEquals("New.java", BuilderTestUtil.asStrOfFiles(result2));
        
        builder.unbuild();
    }
    
    @Test
    public void testRemoveFile2() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        IncrementalModelBuilder builder = new IncrementalModelBuilder(new ModelBuilderBatchImpl());
        builder.build(name, target, target);
        
        builder.removeFile(target + File.separator + "New1.java");
        
        Set<Path> result1 = builder.getNewFiles();
        assertEquals("", BuilderTestUtil.asStrOfPaths(result1));
        
        Set<JavaFile> result2 = builder.getObsoleteFiles();
        assertEquals("", BuilderTestUtil.asStrOfFiles(result2));
        
        builder.unbuild();
    }
    
    @Test
    public void testRemoveFile3() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        IncrementalModelBuilder builder = new IncrementalModelBuilder(new ModelBuilderBatchImpl());
        builder.build(name, target, target);
        
        builder.removeFile(target + File.separator + "Customer.java");
        
        Set<Path> result1 = builder.getNewFiles();
        assertEquals("", BuilderTestUtil.asStrOfPaths(result1));
        
        Set<JavaFile> result2 = builder.getObsoleteFiles();
        assertEquals("", BuilderTestUtil.asStrOfFiles(result2));
        
        builder.unbuild();
    }
    
    @Test
    public void testUpdateFile1() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        createFile(target);
        
        IncrementalModelBuilder builder = new IncrementalModelBuilder(new ModelBuilderBatchImpl());
        builder.build(name, target, target);
        
        modifyFile(target);
        
        builder.updateFile(target + File.separator + "New.java");
        
        Set<Path> result1 = builder.getNewFiles();
        assertEquals("New.java", BuilderTestUtil.asStrOfPaths(result1));
        
        Set<JavaFile> result2 = builder.getObsoleteFiles();
        assertEquals("New.java", BuilderTestUtil.asStrOfFiles(result2));
        
        deleteFile(target);
        
        builder.unbuild();
    }
    
    @Test
    public void testUpdateFile2() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        IncrementalModelBuilder builder = new IncrementalModelBuilder(new ModelBuilderBatchImpl());
        builder.build(name, target, target);
        
        builder.updateFile(target + File.separator + "New1.java");
        
        Set<Path> result1 = builder.getNewFiles();
        assertEquals("", BuilderTestUtil.asStrOfPaths(result1));
        
        Set<JavaFile> result2 = builder.getObsoleteFiles();
        assertEquals("", BuilderTestUtil.asStrOfFiles(result2));
        
        builder.unbuild();
    }
    
    @Test
    public void testUpdateFile3() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        IncrementalModelBuilder builder = new IncrementalModelBuilder(new ModelBuilderBatchImpl());
        builder.build(name, target, target);
        
        builder.updateFile(target + File.separator + "Customer.java");
        
        Set<Path> result1 = builder.getNewFiles();
        assertEquals("Customer.java", BuilderTestUtil.asStrOfPaths(result1));
        
        Set<JavaFile> result2 = builder.getObsoleteFiles();
        assertEquals("Customer.java;CustomerTest.java", BuilderTestUtil.asStrOfFiles(result2));
        
        builder.unbuild();
    }
    
    @Test
    public void testCollectDependentFiles1() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        IncrementalModelBuilder builder = new IncrementalModelBuilder(new ModelBuilderBatchImpl());
        builder.build(name, target, target);
        
        Set<JavaFile> result = builder.collectDependentFiles(target + File.separator + "Customer.java");
        
        assertEquals("Customer.java;CustomerTest.java", BuilderTestUtil.asStrOfFiles(result));
        
        builder.unbuild();
    }
    
    @Test
    public void testCollectDependentFiles2() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        IncrementalModelBuilder builder = new IncrementalModelBuilder(new ModelBuilderBatchImpl());
        builder.build(name, target, target);
        
        Set<JavaFile> result = builder.collectDependentFiles(target + File.separator + "Rental.java");
        
        assertEquals("Customer.java;CustomerTest.java;Order.java;Rental.java", BuilderTestUtil.asStrOfFiles(result));
        
        builder.unbuild();
    }
    
    @Test
    public void testCollectDependentFiles3() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        IncrementalModelBuilder builder = new IncrementalModelBuilder(new ModelBuilderBatchImpl());
        builder.build(name, target, target);
        
        Set<JavaFile> result = builder.collectDependentFiles(target + File.separator + "Order.java");
        
        assertEquals("Customer.java;CustomerTest.java;Order.java", BuilderTestUtil.asStrOfFiles(result));
        
        builder.unbuild();
    }
    
    @Test
    public void testCollectDependentFiles4() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        IncrementalModelBuilder builder = new IncrementalModelBuilder(new ModelBuilderBatchImpl());
        builder.build(name, target, target);
        
        Set<JavaFile> result = builder.collectDependentFiles(target + File.separator + "CustomerTest.java");
        
        assertEquals("CustomerTest.java", BuilderTestUtil.asStrOfFiles(result));
        
        builder.unbuild();
    }
    
    @Test
    public void testCollectFilesByFileTime1() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        createFile(target);
        
        IncrementalModelBuilder builder = new IncrementalModelBuilder(new ModelBuilderBatchImpl());
        builder.build(name, target, target);
        
        builder.collectFilesByFileTime();
        
        Set<Path> result1 = builder.getNewFiles();
        assertEquals("", BuilderTestUtil.asStrOfPaths(result1));
        
        Set<JavaFile> result2 = builder.getObsoleteFiles();
        assertEquals("", BuilderTestUtil.asStrOfFiles(result2));
        
        deleteFile(target);
        
        builder.unbuild();
    }
    
    @Test
    public void testCollectFilesByFileTime2() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        IncrementalModelBuilder builder = new IncrementalModelBuilder(new ModelBuilderBatchImpl());
        builder.build(name, target, target);
        
        createFile(target);
        
        builder.collectFilesByFileTime();
        
        Set<Path> result1 = builder.getNewFiles();
        assertEquals("New.java", BuilderTestUtil.asStrOfPaths(result1));
        
        Set<JavaFile> result2 = builder.getObsoleteFiles();
        assertEquals("", BuilderTestUtil.asStrOfFiles(result2));
        
        deleteFile(target);
        
        builder.unbuild();
    }
    
    @Test
    public void testCollectFilesByFileTime3() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        createFile(target);
        
        IncrementalModelBuilder builder = new IncrementalModelBuilder(new ModelBuilderBatchImpl());
        builder.build(name, target, target);
        
        touchFile(target, "New.java");
        
        builder.collectFilesByFileTime();
        
        Set<Path> result1 = builder.getNewFiles();
        assertEquals("New.java", BuilderTestUtil.asStrOfPaths(result1));
        
        Set<JavaFile> result2 = builder.getObsoleteFiles();
        assertEquals("New.java", BuilderTestUtil.asStrOfFiles(result2));
        
        deleteFile(target);
        
        builder.unbuild();
    }
    
    @Test
    public void testCollectFilesByFileTime4() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        createFile(target);
        
        IncrementalModelBuilder builder = new IncrementalModelBuilder(new ModelBuilderBatchImpl());
        builder.build(name, target, target);
        
        deleteFile(target);
        
        builder.collectFilesByFileTime();
        
        Set<Path> result1 = builder.getNewFiles();
        assertEquals("", BuilderTestUtil.asStrOfPaths(result1));
        
        Set<JavaFile> result2 = builder.getObsoleteFiles();
        assertEquals("New.java", BuilderTestUtil.asStrOfFiles(result2));
        
        builder.unbuild();
    }
    
    @Test
    public void testCollectFilesByFileTime5() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        IncrementalModelBuilder builder = new IncrementalModelBuilder(new ModelBuilderBatchImpl());
        builder.build(name, target, target);
        
        FileTime originalTime = touchFile(target, "Customer.java");
        
        builder.collectFilesByFileTime();
        
        Set<Path> result1 = builder.getNewFiles();
        assertEquals("Customer.java", BuilderTestUtil.asStrOfPaths(result1));
        
        Set<JavaFile> result2 = builder.getObsoleteFiles();
        assertEquals("Customer.java;CustomerTest.java", BuilderTestUtil.asStrOfFiles(result2));
        
        revertFile(target, "Customer.java", originalTime);
        
        builder.unbuild();
    }
    
    @Test
    public void testRebuild() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        createFile(target);
        
        IncrementalModelBuilder builder = new IncrementalModelBuilder(new ModelBuilderBatchImpl());
        JavaProject jproject = builder.build(name, target, target);
        
        JavaFile jfileBefore = jproject.getFile(target + File.separator + "New.java");
        JavaClass jclassBefore = jfileBefore.getClasses().iterator().next();
        
        modifyFile(target);
        
        builder.updateFile(target + File.separator + "New.java");
        builder.rebuild();
        
        JavaFile jfileAfter = jproject.getFile(target + File.separator + "New.java");
        JavaClass jclassAfter = jfileAfter.getClasses().iterator().next();
        
        System.out.println(jfileBefore);
        System.out.println(jfileAfter);
        
        assertFalse(jfileBefore.getSource().equals(jfileAfter.getSource()));
        
        assertEquals("New", jclassBefore.getQualifiedName().toString());
        assertEquals("New", jclassAfter.getQualifiedName().toString());
        
        assertNotNull(jclassBefore.getMethod("m( )"));
        assertNotNull(jclassAfter.getMethod("b( )"));
        
        assertNotNull(jclassBefore.getField("x"));
        assertNotNull(jclassAfter.getField("a"));
        
        deleteFile(target);
        
        builder.unbuild();
    }
    
    @Test
    public void testIncrementalBuild() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        createFile(target);
        
        IncrementalModelBuilder builder = new IncrementalModelBuilder(new ModelBuilderBatchImpl());
        JavaProject jproject = builder.build(name, target, target);
        
        JavaFile jfileBefore = jproject.getFile(target + File.separator + "New.java");
        JavaClass jclassBefore = jfileBefore.getClasses().iterator().next();
        
        modifyFile(target);
        
        builder.updateFile(target + File.separator + "New.java");
        builder.incrementalBuild();
        
        JavaFile jfileAfter = jproject.getFile(target + File.separator + "New.java");
        JavaClass jclassAfter = jfileAfter.getClasses().iterator().next();
        
        System.out.println(jfileBefore);
        System.out.println(jfileAfter);
        
        assertFalse(jfileBefore.getSource().equals(jfileAfter.getSource()));
        
        assertEquals("New", jclassBefore.getQualifiedName().toString());
        assertEquals("New", jclassAfter.getQualifiedName().toString());
        
        assertNotNull(jclassBefore.getMethod("m( )"));
        assertNotNull(jclassAfter.getMethod("b( )"));
        
        assertNotNull(jclassBefore.getField("x"));
        assertNotNull(jclassAfter.getField("a"));
        
        deleteFile(target);
        
        builder.unbuild();
    }
    
    @Test
    public void testBuildByFileTime() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        createFile(target);
        
        IncrementalModelBuilder builder = new IncrementalModelBuilder(new ModelBuilderBatchImpl());
        JavaProject jproject = builder.build(name, target, target);
        
        JavaFile jfileBefore = jproject.getFile(target + File.separator + "New.java");
        JavaClass jclassBefore = jfileBefore.getClasses().iterator().next();
        
        modifyFile(target);
        
        builder.buildByFileTime();
        
        JavaFile jfileAfter = jproject.getFile(target + File.separator + "New.java");
        JavaClass jclassAfter = jfileAfter.getClasses().iterator().next();
        
        assertFalse(jfileBefore.getSource().equals(jfileAfter.getSource()));
        
        assertEquals("New", jclassBefore.getQualifiedName().toString());
        assertEquals("New", jclassAfter.getQualifiedName().toString());
        
        assertNotNull(jclassBefore.getMethod("m( )"));
        assertNotNull(jclassAfter.getMethod("b( )"));
        
        assertNotNull(jclassBefore.getField("x"));
        assertNotNull(jclassAfter.getField("a"));
        
        deleteFile(target);
        
        builder.unbuild();
    }
}
