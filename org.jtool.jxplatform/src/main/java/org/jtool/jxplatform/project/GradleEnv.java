/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.project;

import static org.jtool.jxplatform.builder.ModelBuilder.br;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.jdt.core.JavaCore;
import org.gradle.api.JavaVersion;
import org.gradle.tooling.ProjectConnection;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.BuildException;
import org.gradle.tooling.model.GradleProject;
import org.gradle.tooling.model.GradleTask;
import org.gradle.tooling.model.DomainObjectSet;
import org.gradle.tooling.model.eclipse.EclipseProject;
import org.gradle.tooling.model.eclipse.EclipseJavaSourceSettings;

/**
 * Obtains path information from the Gradle setting.
 * 
 * @author Katsuhisa Maruyama
 */
class GradleEnv extends ProjectEnv {
    
    private final static GradleConnector connector = GradleConnector.newConnector();
    
    private final static String configName = "build.gradle";
    
    private static String addedTaskName = "copyDependenciesForJxplatform";
    
    GradleEnv(String name, Path basePath, Path topPath) {
        super(name, basePath, topPath);
        configFile = basePath.resolve(Paths.get(GradleEnv.configName));
    }
    
    @Override
    ProjectEnv createProjectEnv(String name, Path basePath, Path topPath) {
        return new GradleEnv(name, basePath, topPath);
    }
    
    @Override
    boolean isApplicable() {
        try {
            configFile = getConfigFile(configFile);
            if (configFile != null && configFile.toFile().exists()) {
                setConfigParameters(configFile);
                return true;
            }
            return false;
        } catch (Exception e) { /* empty */ }
        return false;
    }
    
    private Path getConfigFile(Path path) {
        Path parent = path.getParent();
        while (parent != null) {
            Path pconfigFile = parent.resolve(Paths.get(GradleEnv.configName));
            if (pconfigFile.toFile().exists()) {
                return pconfigFile;
            }
            if (parent.equals(basePath)) {
                return null;
            }
            parent = parent.getParent();
        }
        return null;
    }
    
    private void setConfigParameters(Path configPath) throws Exception {
        ProjectConnection connection = connector.forProjectDirectory(basePath.toFile()).connect();
        try {
            EclipseProject project = connection.model(EclipseProject.class).get();
            
            sourcePaths = project.getSourceDirectories().stream()
                    .map(elem -> basePath.resolve(elem.getPath()).toString()).collect(Collectors.toSet());
            binaryPaths = project.getSourceDirectories().stream()
                    .map(elem -> basePath.resolve(elem.getOutput()).toString()).collect(Collectors.toSet());
            classPaths.add(basePath.resolve(DEFAULT_CLASSPATH).toString());
            classPaths.add(libPath.toString());
            
            EclipseJavaSourceSettings javaSettings = project.getJavaSourceSettings();
            if (javaSettings != null) {
                compilerSourceVersion = compilerVersion(javaSettings.getSourceLanguageLevel());
                compilerTargetVersion = compilerVersion(javaSettings.getTargetBytecodeVersion());
            } else {
                compilerSourceVersion = JavaCore.VERSION_11;
                compilerTargetVersion = JavaCore.VERSION_11;
            }
        } catch (BuildException e) {
            unknownModel();
        } finally {
           connection.close();
        }
    }
    
    private String compilerVersion(JavaVersion v) {
        if (v != null) {
            String version = v.getMajorVersion();
            try {
                double value = Double.valueOf(version);
                if (value <= 10) {
                    return "1." + version;
                } else {
                    return version;
                }
            } catch (NumberFormatException e) { /* empty */ }
        }
        return JavaCore.VERSION_11;
    }
    
    private void unknownModel() {
        String sourceDirectoryCandidate[] = { "src", "main", "java" };
        String sourceDirectory = resolvePath(sourceDirectoryCandidate);
        if (sourceDirectory != null) {
            sourcePaths.add(sourceDirectory);
        }
        String testSourceDirectoryCandidate[] = { "src", "test", "java" };
        String testSourceDirectory = resolvePath(testSourceDirectoryCandidate);
        if (testSourceDirectory != null) {
            sourcePaths.add(testSourceDirectory);
        }
        binaryPaths = new HashSet<>();
        classPaths = new HashSet<>();
        classPaths.add(basePath.resolve(DEFAULT_CLASSPATH).toString());
        classPaths.add(libPath.toString());
        
        compilerSourceVersion = JavaCore.VERSION_11;
        compilerTargetVersion = JavaCore.VERSION_11;
    }
    
    @Override
    void setUpTopProject() throws Exception {
        collectModules();
        super.setUpTopProject();
    }
    
    @Override
    void setUpEachProject() throws Exception {
        if (isProject()) {
            copyDependentLibrariesByCommandExecutor();
        }
        super.setUpEachProject();
    }
    
    private void collectModules() {
        ProjectConnection connection = connector.forProjectDirectory(basePath.toFile()).connect();
        modules = new ArrayList<>();
        try {
            GradleProject project = connection.model(GradleProject.class).get();
            collectSubProjects(project);
        } catch (BuildException e) {
            collectModulesForUnknownModel();
        } finally {
            connection.close();
        }
    }
    
    private void collectSubProjects(GradleProject project) {
        List<GradleProject> children = project.getChildren().stream().collect(Collectors.toList());
        children.stream().map(child -> child.getPath().substring(1).replace(':', File.separatorChar))
            .forEach(name -> modules.add(name));
        children.forEach(child -> collectSubProjects(child));
    }
    
    private void collectModulesForUnknownModel() {
        try (Stream<Path> stream = Files.list(basePath)) {
            stream.filter(path -> isProject(path))
                 .forEach(path -> modules.add(path.toString().substring(basePath.toString().length() + 1)));
        } catch (Exception e) { /* empty */ }
    }
    
    private boolean isProject(Path path) {
        if (path.toString().equals(basePath.toString()) || !path.toFile().isDirectory()) {
            return false;
        }
        try (Stream<Path> stream = Files.list(path)) {
            return stream.filter(p -> !Files.isDirectory(p) && p.toString().endsWith(GradleEnv.configName)).count() > 0;
        } catch (IOException e) {
            return false;
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
        str.append(br);
        str.append("//THESE SETTINGS AND TASK WERE ADDED BY JxPlatform" + br);
        for (int index = 0; index < dependencies.length; index++) {
            str.append("configurations.");
            str.append(dependencies[index]);
            str.append(".setCanBeResolved(true)" + br);
        }
        str.append("task ");
        str.append(taskName);
        str.append("(type: Copy) {" + br);
        for (int index = 0; index < dependencies.length; index++) {
            str.append("  from ");
            str.append("configurations.");
            str.append(dependencies[index]);
            str.append(" into '" + COPIED_CLASSPATH + "'" + br);
        }
        str.append("}" + br);
        return str.toString();
    }
    
    @Override
    public String toString() {
        return "Gradle Env " + basePath.toString();
    }
}
