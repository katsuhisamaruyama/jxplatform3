/*
 *  Copyright 2022-2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder;

import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaFile;
import org.jtool.srcmodel.JavaProject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Set;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

public class IncrementalModelBuilderTest {
    
    private void createFile1(String target) {
        String code = 
                "class New1 {\n" +
                "    private int x = 1;\n" +
                "    public void m() {\n" +
                "        int y = 1;\n" +
                "    }\n" +
                "}";
        BuilderTestUtil.createFile(Paths.get(target + File.separator + "New1.java"),
                BuilderTestUtil.getContent(code));
    }
    
    private void createFile2(String target) {
        String code = 
                "class New2 {\n" +
                "    private New1 n = new New1();\n" +
                "}";
        BuilderTestUtil.createFile(Paths.get(target + File.separator + "New2.java"),
                BuilderTestUtil.getContent(code));
    }
    
    private void deleteFile1(String target) {
        BuilderTestUtil.deleteFile(Paths.get(target + File.separator + "New1.java"));
    }
    
    private void deleteFile2(String target) {
        BuilderTestUtil.deleteFile(Paths.get(target + File.separator + "New2.java"));
    }
    
    private void modifyFile1(String target) {
        String code = 
                "class New1 {\n" +
                "    private int a = 1;\n" +
                "    public void b() {\n" +
                "        int c = 1;\n" +
                "    }\n" +
                "}";
        deleteFile1(target);
        BuilderTestUtil.createFile(Paths.get(target + File.separator + "New1.java"),
                BuilderTestUtil.getContent(code));
    }
    
    private void  modifyFile2(String target) {
        String code = 
                "class New2 {\n" +
                "    private New1 m = new New1();\n" +
                "}";
        deleteFile2(target);
        BuilderTestUtil.createFile(Paths.get(target + File.separator + "New2.java"),
                BuilderTestUtil.getContent(code));
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
    
    @Test
    public void testAddFile1() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        BuilderTestUtil.clearProject();
        IncrementalModelBuilder builder = new IncrementalModelBuilder();
        JavaProject jproject = builder.build(name, target, target);
        
        createFile1(target);
        
        builder.addFile(jproject, "New1.java");
        
        try {
            Set<String> result1 = builder.getAddedFileNames();
            assertEquals(target + File.separator + "New1.java", BuilderTestUtil.asStrOfNames(result1));
            
            Set<String> result2 = builder.getRemovedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result2));
            
            Set<String> result3 = builder.getObsoleteFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result3));
        } finally {
            deleteFile1(target);
            deleteFile2(target);
        }
        
        builder.unbuild();
    }
    
    @Test
    public void testAddFile2() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        BuilderTestUtil.clearProject();
        IncrementalModelBuilder builder = new IncrementalModelBuilder();
        JavaProject jproject = builder.build(name, target, target);
        
        builder.addFile(jproject, "New1.java");
        
        try {
            Set<String> result1 = builder.getAddedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result1));
            
            Set<String> result2 = builder.getRemovedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result2));
            
            Set<String> result3 = builder.getObsoleteFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result3));
            
        } finally {
            deleteFile1(target);
            deleteFile2(target);
        }
        builder.unbuild();
    }
    
    @Test
    public void testAddFile3() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        createFile1(target);
        createFile2(target);
        
        BuilderTestUtil.clearProject();
        IncrementalModelBuilder builder = new IncrementalModelBuilder();
        JavaProject jproject = builder.build(name, target, target);
        
        builder.addFile(jproject, "New1.java");
        
        try {
            Set<String> result1 = builder.getAddedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result1));
            
            Set<String> result2 = builder.getRemovedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result2));
            
            Set<String> result3 = builder.getObsoleteFileNames();
            assertEquals(target + File.separator + "New1.java;" +
                         target + File.separator + "New2.java", BuilderTestUtil.asStrOfNames(result3));
        } finally {
            deleteFile1(target);
            deleteFile2(target);
        }
        builder.unbuild();
    }
    
    @Ignore("Not implemented yet")
    @Test
    public void testAddFile4() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        createFile2(target);
        
        BuilderTestUtil.clearProject();
        IncrementalModelBuilder builder = new IncrementalModelBuilder();
        JavaProject jproject = builder.build(name, target, target);
        
        createFile1(target);
        
        builder.addFile(jproject, "New1.java");
        
        try {
            Set<String> result1 = builder.getAddedFileNames();
            assertEquals(target + File.separator + "New1.java", BuilderTestUtil.asStrOfNames(result1));
            
            Set<String> result2 = builder.getRemovedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result2));
            
            Set<String> result3 = builder.getObsoleteFileNames();
            assertEquals(target + File.separator + "New2.java", BuilderTestUtil.asStrOfNames(result3));
        } finally {
            deleteFile1(target);
            deleteFile2(target);
        }
        builder.unbuild();
    }
    
    @Test
    public void testRemoveFile1() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        createFile1(target);
        
        BuilderTestUtil.clearProject();
        IncrementalModelBuilder builder = new IncrementalModelBuilder();
        JavaProject jproject = builder.build(name, target, target);
        
        deleteFile1(target);
        
        builder.removeFile(jproject, "New1.java");
        
        try {
            Set<String> result1 = builder.getAddedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result1));
            
            Set<String> result2 = builder.getRemovedFileNames();
            assertEquals(target + File.separator + "New1.java", BuilderTestUtil.asStrOfNames(result2));
            
            Set<String> result3 = builder.getObsoleteFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result3));
        } finally {
            deleteFile1(target);
            deleteFile2(target);
        }
        builder.unbuild();
    }
    
    @Test
    public void testRemoveFile2() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        createFile1(target);
        
        BuilderTestUtil.clearProject();
        IncrementalModelBuilder builder = new IncrementalModelBuilder();
        JavaProject jproject = builder.build(name, target, target);
        
        builder.removeFile(jproject, "New1.java");
        
        try {
            Set<String> result1 = builder.getAddedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result1));
            
            Set<String> result2 = builder.getRemovedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result2));
            
            Set<String> result3 = builder.getObsoleteFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result3));
        } finally {
            deleteFile1(target);
            deleteFile2(target);
        }
        
        builder.unbuild();
    }
    
    @Test
    public void testRemoveFile3() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        createFile1(target);
        createFile2(target);
        
        BuilderTestUtil.clearProject();
        IncrementalModelBuilder builder = new IncrementalModelBuilder();
        JavaProject jproject = builder.build(name, target, target);
        
        deleteFile1(target);
        
        builder.removeFile(jproject, "New1.java");
        
        try {
            Set<String> result1 = builder.getAddedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result1));
            
            Set<String> result2 = builder.getRemovedFileNames();
            assertEquals(target + File.separator + "New1.java", BuilderTestUtil.asStrOfNames(result2));
            
            Set<String> result3 = builder.getObsoleteFileNames();
            assertEquals(target + File.separator + "New2.java", BuilderTestUtil.asStrOfNames(result3));
        } finally {
            deleteFile1(target);
            deleteFile2(target);
        }
        builder.unbuild();
    }
    
    @Test
    public void testRemoveFile4() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        createFile1(target);
        createFile2(target);
        
        BuilderTestUtil.clearProject();
        IncrementalModelBuilder builder = new IncrementalModelBuilder();
        JavaProject jproject = builder.build(name, target, target);
        
        deleteFile2(target);
        
        builder.removeFile(jproject, "New2.java");
        
        try {
            Set<String> result1 = builder.getAddedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result1));
            
            Set<String> result2 = builder.getRemovedFileNames();
            assertEquals(target + File.separator + "New2.java", BuilderTestUtil.asStrOfNames(result2));
            
            Set<String> result3 = builder.getObsoleteFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result3));
        } finally {
            deleteFile1(target);
            deleteFile2(target);
        }
        builder.unbuild();
    }
    
    @Test
    public void testUpdateFile1() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        createFile1(target);
        
        BuilderTestUtil.clearProject();
        IncrementalModelBuilder builder = new IncrementalModelBuilder();
        JavaProject jproject = builder.build(name, target, target);
        
        modifyFile1(target);
        
        builder.updateFile(jproject, "New1.java");
        
        try {
            Set<String> result1 = builder.getAddedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result1));
            
            Set<String> result2 = builder.getRemovedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result2));
            
            Set<String> result3 = builder.getObsoleteFileNames();
            assertEquals(target + File.separator + "New1.java", BuilderTestUtil.asStrOfNames(result3));
        } finally {
            deleteFile1(target);
            deleteFile2(target);
        }
        builder.unbuild();
    }
    
    @Test
    public void testUpdateFile2() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        BuilderTestUtil.clearProject();
        IncrementalModelBuilder builder = new IncrementalModelBuilder();
        JavaProject jproject = builder.build(name, target, target);
        
        modifyFile1(target);
        
        builder.updateFile(jproject, "New1.java");
        
        try {
            Set<String> result1 = builder.getAddedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result1));
            
            Set<String> result2 = builder.getRemovedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result2));
            
            Set<String> result3 = builder.getObsoleteFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result3));
        } finally {
            deleteFile1(target);
            deleteFile2(target);
        }
        builder.unbuild();
    }
    
    @Test
    public void testUpdateFile3() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        createFile1(target);
        createFile2(target);
        
        BuilderTestUtil.clearProject();
        IncrementalModelBuilder builder = new IncrementalModelBuilder();
        JavaProject jproject = builder.build(name, target, target);
        
        modifyFile1(target);
        
        builder.updateFile(jproject, "New1.java");
        
        try {
            Set<String> result1 = builder.getAddedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result1));
            
            Set<String> result2 = builder.getRemovedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result2));
            
            Set<String> result3 = builder.getObsoleteFileNames();
            assertEquals(target + File.separator + "New1.java;" +
                         target + File.separator + "New2.java", BuilderTestUtil.asStrOfNames(result3));
        } finally {
            deleteFile1(target);
            deleteFile2(target);
        }
        builder.unbuild();
    }
    
    @Test
    public void testUpdateFile4() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        createFile1(target);
        createFile2(target);
        
        BuilderTestUtil.clearProject();
        IncrementalModelBuilder builder = new IncrementalModelBuilder();
        JavaProject jproject = builder.build(name, target, target);
        
        modifyFile2(target);
        
        builder.updateFile(jproject, "New2.java");
        
        try {
            Set<String> result1 = builder.getAddedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result1));
            
            Set<String> result2 = builder.getRemovedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result2));
            
            Set<String> result3 = builder.getObsoleteFileNames();
            assertEquals(target + File.separator + "New2.java", BuilderTestUtil.asStrOfNames(result3));
        } finally {
            deleteFile1(target);
            deleteFile2(target);
        }
        builder.unbuild();
    }
    
    @Test
    public void testCollectDependentFiles1() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        BuilderTestUtil.clearProject();
        IncrementalModelBuilder builder = new IncrementalModelBuilder();
        JavaProject jproject = builder.build(name, target, target);
        
        JavaFile jfile = jproject.getFile(target + File.separator + "Customer.java");
        Set<JavaFile> result = builder.collectDependentFiles(jfile);
        
        assertEquals(target + File.separator + "Customer.java;" +
                     target + File.separator + "CustomerTest.java", BuilderTestUtil.asStrOfFiles(result));
        
        builder.unbuild();
    }
    
    @Test
    public void testCollectDependentFiles2() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        BuilderTestUtil.clearProject();
        IncrementalModelBuilder builder = new IncrementalModelBuilder();
        JavaProject jproject = builder.build(name, target, target);
        
        JavaFile jfile = jproject.getFile(target + File.separator + "Rental.java");
        Set<JavaFile> result = builder.collectDependentFiles(jfile);
        
        assertEquals(target + File.separator + "Customer.java;" +
                     target + File.separator + "CustomerTest.java;" +
                     target + File.separator + "Order.java;" +
                     target + File.separator + "Rental.java", BuilderTestUtil.asStrOfFiles(result));
        
        builder.unbuild();
    }
    
    @Test
    public void testCollectDependentFiles3() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        BuilderTestUtil.clearProject();
        IncrementalModelBuilder builder = new IncrementalModelBuilder();
        JavaProject jproject = builder.build(name, target, target);
        
        JavaFile jfile = jproject.getFile(target + File.separator + "Order.java");
        Set<JavaFile> result = builder.collectDependentFiles(jfile);
        
        assertEquals(target + File.separator + "Customer.java;" +
                     target + File.separator + "CustomerTest.java;" +
                     target + File.separator + "Order.java", BuilderTestUtil.asStrOfFiles(result));
        
        builder.unbuild();
    }
    
    @Test
    public void testCollectDependentFiles4() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        BuilderTestUtil.clearProject();
        IncrementalModelBuilder builder = new IncrementalModelBuilder();
        JavaProject jproject = builder.build(name, target, target);
        
        JavaFile jfile = jproject.getFile(target + File.separator + "CustomerTest.java");
        Set<JavaFile> result = builder.collectDependentFiles(jfile);
        
        assertEquals(target + File.separator + "CustomerTest.java", BuilderTestUtil.asStrOfFiles(result));
        
        builder.unbuild();
    }
    
    @Test
    public void testCollectFilesByFileTime1() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        createFile1(target);
        
        BuilderTestUtil.clearProject();
        IncrementalModelBuilder builder = new IncrementalModelBuilder();
        builder.build(name, target, target);
        
        builder.collectFilesByFileTime();
        
        try {
            Set<String> result1 = builder.getAddedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result1));
            
            Set<String> result2 = builder.getAddedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result2));
            
            Set<String> result3 = builder.getObsoleteFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result3));
        } finally {
            deleteFile1(target);
            deleteFile2(target);
        }
        builder.unbuild();
    }
    
    @Test
    public void testCollectFilesByFileTime2() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        BuilderTestUtil.clearProject();
        IncrementalModelBuilder builder = new IncrementalModelBuilder();
        builder.build(name, target, target);
        
        createFile1(target);
        
        builder.collectFilesByFileTime();
        
        try {
            Set<String> result1 = builder.getAddedFileNames();
            assertEquals(target + File.separator + "New1.java", BuilderTestUtil.asStrOfNames(result1));
            
            Set<String> result2 = builder.getRemovedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result2));
            
            Set<String> result3 = builder.getObsoleteFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result3));
        } finally {
            deleteFile1(target);
            deleteFile2(target);
        }
        builder.unbuild();
    }
    
    @Test
    public void testCollectFilesByFileTime3() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        createFile1(target);
        
        BuilderTestUtil.clearProject();
        IncrementalModelBuilder builder = new IncrementalModelBuilder();
        builder.build(name, target, target);
        
        touchFile(target, "New1.java");
        
        builder.collectFilesByFileTime();
        
        try {
            Set<String> result1 = builder.getAddedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result1));
            
            Set<String> result2 = builder.getRemovedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result2));
            
            Set<String> result3 = builder.getObsoleteFileNames();
            assertEquals(target + File.separator + "New1.java", BuilderTestUtil.asStrOfNames(result3));
        } finally {
            deleteFile1(target);
            deleteFile2(target);
        }
        builder.unbuild();
    }
    
    @Test
    public void testCollectFilesByFileTime4() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        createFile1(target);
        
        BuilderTestUtil.clearProject();
        IncrementalModelBuilder builder = new IncrementalModelBuilder();
        builder.build(name, target, target);
        
        deleteFile1(target);
        
        builder.collectFilesByFileTime();
        
        try {
            Set<String> result1 = builder.getAddedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result1));
            
            Set<String> result2 = builder.getRemovedFileNames();
            assertEquals(target + File.separator + "New1.java", BuilderTestUtil.asStrOfNames(result2));
            
            Set<String> result3 = builder.getObsoleteFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result3));
        } finally {
            deleteFile1(target);
            deleteFile2(target);
        }
        builder.unbuild();
    }
    
    @Test
    public void testCollectFilesByFileTime5() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        createFile1(target);
        createFile2(target);
        
        BuilderTestUtil.clearProject();
        IncrementalModelBuilder builder = new IncrementalModelBuilder();
        builder.build(name, target, target);
        
        touchFile(target, "New1.java");
        
        builder.collectFilesByFileTime();
        
        try {
            Set<String> result1 = builder.getAddedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result1));
            
            Set<String> result2 = builder.getRemovedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result2));
            
            Set<String> result3 = builder.getObsoleteFileNames();
            assertEquals(target + File.separator + "New1.java;" +
                         target + File.separator + "New2.java", BuilderTestUtil.asStrOfNames(result3));
        } finally {
            deleteFile1(target);
            deleteFile2(target);
        }
        builder.unbuild();
    }
    
    @Test
    public void testCollectFilesByFileTime6() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        createFile1(target);
        createFile2(target);
        
        BuilderTestUtil.clearProject();
        IncrementalModelBuilder builder = new IncrementalModelBuilder();
        builder.build(name, target, target);
        
        touchFile(target, "New2.java");
        
        builder.collectFilesByFileTime();
        
        try {
            Set<String> result1 = builder.getAddedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result1));
            
            Set<String> result2 = builder.getRemovedFileNames();
            assertEquals("", BuilderTestUtil.asStrOfNames(result2));
            
            Set<String> result3 = builder.getObsoleteFileNames();
            assertEquals(target + File.separator + "New2.java", BuilderTestUtil.asStrOfNames(result3));
        } finally {
            deleteFile1(target);
            deleteFile2(target);
        }
        builder.unbuild();
    }
    
    @Test
    public void testRebuild() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        createFile1(target);
        
        BuilderTestUtil.clearProject();
        IncrementalModelBuilder builder = new IncrementalModelBuilder();
        JavaProject jproject = builder.build(name, target, target);
        
        JavaFile jfileBefore = jproject.getFile(target + File.separator + "New1.java");
        JavaClass jclassBefore = jfileBefore.getClasses().iterator().next();
        
        modifyFile1(target);
        
        builder.updateFile(jproject, target + File.separator + "New1.java");
        
        builder.rebuild();
        
        JavaFile jfileAfter = jproject.getFile(target + File.separator + "New1.java");
        JavaClass jclassAfter = jfileAfter.getClasses().iterator().next();
        
        try {
            assertFalse(jfileBefore.getSource().equals(jfileAfter.getSource()));
            
            assertEquals("New1", jclassBefore.getQualifiedName().toString());
            assertEquals("New1", jclassAfter.getQualifiedName().toString());
            
            assertNotNull(jclassBefore.getMethod("m( )"));
            assertNotNull(jclassAfter.getMethod("b( )"));
            
            assertNotNull(jclassBefore.getField("x"));
            assertNotNull(jclassAfter.getField("a"));
        } finally {
            deleteFile1(target);
            deleteFile2(target);
        }
        builder.unbuild();
    }
    
    @Test
    public void testIncrementalBuild() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        createFile1(target);
        
        BuilderTestUtil.clearProject();
        IncrementalModelBuilder builder = new IncrementalModelBuilder();
        JavaProject jproject = builder.build(name, target, target);
        
        JavaFile jfileBefore = jproject.getFile(target + File.separator + "New1.java");
        JavaClass jclassBefore = jfileBefore.getClasses().iterator().next();
        
        modifyFile1(target);
        
        builder.updateFile(jproject, target + File.separator + "New1.java");
        
        builder.incrementalBuild();
        
        JavaFile jfileAfter = jproject.getFile(target + File.separator + "New1.java");
        JavaClass jclassAfter = jfileAfter.getClasses().iterator().next();
        
        try {
            assertFalse(jfileBefore.getSource().equals(jfileAfter.getSource()));
            
            assertEquals("New1", jclassBefore.getQualifiedName().toString());
            assertEquals("New1", jclassAfter.getQualifiedName().toString());
            
            assertNotNull(jclassBefore.getMethod("m( )"));
            assertNotNull(jclassAfter.getMethod("b( )"));
            
            assertNotNull(jclassBefore.getField("x"));
            assertNotNull(jclassAfter.getField("a"));
        } finally {
            deleteFile1(target);
            deleteFile2(target);
        }
        builder.unbuild();
    }
    
    @Test
    public void testBuildByFileTime() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        createFile1(target);
        
        BuilderTestUtil.clearProject();
        IncrementalModelBuilder builder = new IncrementalModelBuilder();
        JavaProject jproject = builder.build(name, target, target);
        
        JavaFile jfileBefore = jproject.getFile(target + File.separator + "New1.java");
        JavaClass jclassBefore = jfileBefore.getClasses().iterator().next();
        
        modifyFile1(target);
        
        builder.buildByFileTime();
        
        JavaFile jfileAfter = jproject.getFile(target + File.separator + "New1.java");
        JavaClass jclassAfter = jfileAfter.getClasses().iterator().next();
        
        try {
            assertFalse(jfileBefore.getSource().equals(jfileAfter.getSource()));
            
            assertEquals("New1", jclassBefore.getQualifiedName().toString());
            assertEquals("New1", jclassAfter.getQualifiedName().toString());
            
            assertNotNull(jclassBefore.getMethod("m( )"));
            assertNotNull(jclassAfter.getMethod("b( )"));
            
            assertNotNull(jclassBefore.getField("x"));
            assertNotNull(jclassAfter.getField("a"));
        } finally {
            deleteFile1(target);
            deleteFile2(target);
        }
        builder.unbuild();
    }
}
