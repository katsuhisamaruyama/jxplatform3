/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder;

import org.jtool.cfg.internal.refmodel.RefModelTestUtil;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.internal.ProjectStore;
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
        return commonPath(testTargetDir + name);
    }
    
    public static void clearProject() {
        ProjectStore store = ProjectStore.getInstance();
        for (JavaProject project : store.getProjects()) {
            project.getModelBuilder().unbuild();
        }
        store.clear();
    }
    
    public static JavaProject getProject(String name) {
        switch (name) {
            case "CS-classroom": return getProject(name, "/lib/*", "/src");
            case "DrawTool": return getProject(name, "", "/src");
            case "Lambda": getProject("Lambda", "", "");
            case "Simple": return getProject(name, "", "");
            case "Slice": return getProject(name, "", "");
            case "Tetris": return getProject(name, "", "");
            case "VideoStore": return getProject(name, "/lib/*", "");
        }
        return null;
    }
    
    private static JavaProject getProject(String name, String lib, String src) {
        ProjectStore store = ProjectStore.getInstance();
        JavaProject jproject = store.getProject(BuilderTestUtil.getTarget(name));
        if (jproject == null) {
            jproject = BuilderTestUtil.createProject(name, lib, src);
        }
        return jproject;
    }
    
    public static JavaProject createProject(String name, String lib, String src) {
        String target = getTarget(name);
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        JavaProject project = builder.build(name, target, commonPath(target + lib), commonPath(target + src), target);
        return project;
    }
    
    public static String commonPath(String path) {
        return path.replaceAll("/", File.separator);
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
    
    public static String getContent(String code) {
        return code.replaceAll("\n", ModelBuilder.br);
    }
}
