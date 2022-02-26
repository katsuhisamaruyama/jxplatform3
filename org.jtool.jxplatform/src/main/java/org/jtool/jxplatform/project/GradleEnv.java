/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.project;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.gradle.tooling.ProjectConnection;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.model.GradleProject;
import org.gradle.tooling.model.GradleTask;
import org.gradle.tooling.model.DomainObjectSet;
import org.gradle.tooling.model.eclipse.EclipseProject;

/**
 * Obtains path information from the Ant setting.
 * 
 * @author Katsuhisa Maruyama
 */
class GradleEnv extends ProjectEnv {
    
    private final static GradleConnector connector = GradleConnector.newConnector();
    
    private final static String configName = ".gradle";
    
    private static String addedTaskName = "copyDependenciesForJxplatform";
    
    GradleEnv(String name, Path basePath) {
        super(name, basePath);
        configFile = basePath.resolve(getFileName(basePath, GradleEnv.configName));
    }
    
    @Override
    ProjectEnv createProjectEnv(String name, Path basePath) {
        return new GradleEnv(name, basePath);
    }
    
    private String getFileName(Path path, String sufix) {
        File[] files = path.toFile().listFiles((file, name) -> name.endsWith(sufix));
        if (files.length > 0) {
            return files[0].getAbsolutePath();
        }
        return GradleEnv.configName;
    }
    
    @Override
    boolean isApplicable() {
        try {
            if (configFile.toFile().exists()) {
                setPaths(configFile.toString());
                return true;
            }
            return false;
        } catch (Exception e) { /* empty */ }
        return false;
    }
    
    @Override
    boolean isProject() {
        return sourcePaths.size() > 0;
    }
    
    private void setPaths(String configFile) throws Exception {
        ProjectConnection connection = connector.forProjectDirectory(basePath.toFile()).connect();
        try {
            EclipseProject project = connection.model(EclipseProject.class).get();
            if (project == null) {
                return;
            }
            
            sourcePaths = project.getSourceDirectories().stream()
                    .map(elem -> basePath.resolve(elem.getPath()).toString()).collect(Collectors.toSet());
            
            binaryPaths = project.getSourceDirectories().stream()
                    .map(elem -> basePath.resolve(elem.getOutput()).toString()).collect(Collectors.toSet());
            
            classPaths.add(basePath.resolve(DEFAULT_CLASSPATH).toString());
            classPaths.add(libPath.toString());
        } finally {
           connection.close();
        }
    }
    
    @Override
    void setUpTopProject() throws Exception {
        collectModules();
        super.setUpTopProject();
    }
    
    @Override
    void setUpEachProject() throws Exception {
        copyDependentLibrariesByCommandExecutor();
        super.setUpEachProject();
    }
    
    private void collectModules() {
        ProjectConnection connection = connector.forProjectDirectory(basePath.toFile()).connect();
        modules = new ArrayList<>();
        try {
            GradleProject gproject = connection.model(GradleProject.class).get();
            modules = gproject.getChildren().stream()
                    .map(elem -> elem.getName()).collect(Collectors.toList());
        } finally {
            connection.close();
        }
    }
    
    private void copyDependentLibrariesByCommandExecutor() throws Exception {
        if (libPath.toFile().exists()) {
            return;
        }
        
        Files.createDirectory(libPath);
        
        System.out.println("Resolving dependencies");
        
        Path movedFile = Paths.get(configFile.toString() + ".original-moved");
        if (movedFile.toFile().exists()) {
            return;
        }
        
        Files.move(configFile, movedFile);
        Files.copy(movedFile, configFile);
        
        ProjectConnection connection = GradleConnector.newConnector().forProjectDirectory(basePath.toFile()).connect();
        try {
            String taskName = getTaskName(connection, addedTaskName);
            addTask(taskName);
            
            BuildLauncher build = connection.newBuild();
            build.forTasks(taskName);
            build.run();
        } catch (Exception e) {
            Files.move(movedFile, configFile);
            throw e;
        } finally {
            connection.close();
        }
    }
    
    private String getTaskName(ProjectConnection connection, String taskName) {
        GradleProject project = connection.model(GradleProject.class).get();
        StringBuilder str = new StringBuilder(taskName);
        DomainObjectSet<? extends GradleTask> tasks = project.getTasks();
        while (tasks.stream().anyMatch(task -> task.getName().equals(str.toString()))) {
            str.append("Z");
        }
        return str.toString();
    }
    
    private void addTask(String taskName) throws IOException {
        String[] dependencies = new String[] {
                "implementation",
                "testImplementation",
                "compileOnly",
                "testCompileOnly"
        };
        String content = getConfiguration(taskName, dependencies);
        Files.write(configFile, content.getBytes(), StandardOpenOption.APPEND);
    }
    
    private String getConfiguration(String taskName, String[] dependencies) throws IOException {
        StringBuilder str = new StringBuilder();
        str.append("\n");
        str.append("//THESE SETTINGS AND TASK WERE ADDED BY JxPlatform\n");
        for (int index = 0; index < dependencies.length; index++) {
            str.append("configurations.");
            str.append(dependencies[index]);
            str.append(".setCanBeResolved(true)\n");
        }
        str.append("task ");
        str.append(taskName);
        str.append("(type: Copy) {\n");
        for (int index = 0; index < dependencies.length; index++) {
            str.append("  from ");
            str.append("configurations.");
            str.append(dependencies[index]);
            str.append(" into '" + COPIED_CLASSPATH + "'\n");
        }
        str.append("}\n");
        return str.toString();
    }
    
    @Override
    public String toString() {
        return "Gradle Env " + basePath.toString();
    }
}
