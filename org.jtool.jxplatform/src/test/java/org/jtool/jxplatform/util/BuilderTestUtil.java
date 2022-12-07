/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.util;

import org.jtool.cfg.internal.refmodel.RefModelTestUtil;
import org.jtool.jxplatform.builder.ModelBuilderBatch;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.JavaFile;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

public class BuilderTestUtil {
    
    public final static String testTargetDir = new File(".").getAbsoluteFile().getParent() + "/test_target/";
    
    public static String getTarget(String name) {
        return testTargetDir + name;
    }
    
    public static JavaProject createProject(String name, String lib, String src) {
        String target = getTarget(name);
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        JavaProject project = builder.build(name, target, target + lib, target + src, target);
        return project;
    }
    
    public static boolean removeCache(String target) {
        return RefModelTestUtil.removeCache(target);
    }
    
    public static void createFile(Path path, String content) {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Cannot create file: " + path);
        }
    }
    
    public static void deleteFile(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e){
            System.err.println("Cannot delete file: " + path);
        }
    }
    
    public static String asStrOfNames(Set<String> names) {
        return names.stream().sorted().collect(Collectors.joining(";"));
    }
    
    public static String asStrOfPaths(Set<Path> paths) {
        return paths.stream().map(path -> path.getFileName().toString()).sorted().collect(Collectors.joining(";"));
    }
    
    public static String asStrOfFiles(Set<JavaFile> paths) {
        return paths.stream().map(jfile -> jfile.getName()).sorted().collect(Collectors.joining(";"));
    }
}
