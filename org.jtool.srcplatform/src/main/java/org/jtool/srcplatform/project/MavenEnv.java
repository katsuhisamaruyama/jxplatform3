/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.project;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.File;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Properties;
import java.io.InputStream;
import org.apache.maven.model.Model;
import org.apache.maven.model.Build;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.codehaus.plexus.util.xml.Xpp3Dom;

/**
 * Obtains path information from the Maven setting.
 * 
 * @author Katsuhisa Maruyama
 */
class MavenEnv extends ProjectEnv {
    
    private final static String configName = "pom.xml";
    
    private boolean sourceDirExists = false;
    
    private static String mvnCommand = findCommandPath("mvn", "-v");
    
    MavenEnv(String name, Path basePath) {
        super(name, basePath);
        configFile = basePath.resolve(Paths.get(MavenEnv.configName));
    }
    
    @Override
    ProjectEnv createProjectEnv(String name, Path basePath) {
        return new MavenEnv(name, basePath);
    }
    
    @Override
    boolean isApplicable() {
        try {
            if (configFile.toFile().exists()) {
                setPaths(configFile.toString());
                return true;
            }
        } catch (Exception e) { /* empty */ }
        return false;
    }
    
    @Override
    boolean isProject() {
        return sourceDirExists;
    }
    
    private void setPaths(String configFile) throws Exception {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(Files.newBufferedReader(Paths.get(configFile)));
        model.setPomFile(Paths.get(configFile).toFile());
        modules = model.getModules();
        
        sourcePaths = new HashSet<>();
        binaryPaths = new HashSet<>();
        classPaths = new HashSet<>();
        
        Build build = model.getBuild();
        if (build == null) {
            return;
        }
        
        String sourceDirectoryCandidates[][] = { { "src", "main", "java" }, { "src" } };
        String testSourceDirectoryCandidates[][] = { { "src", "test", "java" }, { "test" } };
        String sourceDirectory = getSourceDirectory(
                build.getSourceDirectory(), sourceDirectoryCandidates);
        String testSourceDirectory = getSourceDirectory(
                build.getTestSourceDirectory(), testSourceDirectoryCandidates);
        
        if (sourceDirectory == null && testSourceDirectory == null) {
            sourceDirectory = basePath.toString();
            testSourceDirectory = basePath.toString();
            File dir = new File(basePath.toString());
            sourceDirExists = Arrays.asList(dir.listFiles()).stream()
                    .anyMatch(file -> file.isFile() && file.getName().endsWith(".java"));
        }
        if (sourceDirectory != null) {
            sourcePaths.add(sourceDirectory);
        }
        if (testSourceDirectory != null) {
            sourcePaths.add(testSourceDirectory);
        }
        
        String buildDirectory  = build.getDirectory();
        if (buildDirectory == null) {
            buildDirectory = basePath.resolve("target").toString();
        }
        Path buildPath = Paths.get(buildDirectory);
        
        String generatedSourceDirectoryCandidates[] = { "generated-sources", "generated" };
        String generatedSourceDirectory = getGeneratedSourceDirectory(buildPath,
                generatedSourceDirectoryCandidates);
        if (generatedSourceDirectory != null) {
            sourcePaths.add(generatedSourceDirectory);
        }
        
        String generatedTestSourceDirectoryCandidates[] = { "generated-test-sources", "generated" };
        String generatedTestSourceDirectory = getGeneratedSourceDirectory(buildPath,
                generatedTestSourceDirectoryCandidates);
        if (generatedTestSourceDirectory != null) {
            sourcePaths.add(generatedTestSourceDirectory);
        }
        
        String outputDirectory = build.getOutputDirectory();
        if (outputDirectory == null) {
            outputDirectory = buildPath.resolve("classes").toString();
        }
        String testOutputDirectory = build.getTestOutputDirectory();
        if (testOutputDirectory == null) {
            testOutputDirectory = buildPath.resolve("test-classes").toString();
        }
        binaryPaths.add(toAbsolutePath(outputDirectory));
        binaryPaths.add(toAbsolutePath(testOutputDirectory));
        
        classPaths.add(basePath.resolve(DEFAULT_CLASSPATH).toString());
        classPaths.add(libPath.toString());
        
        Set<String> excludes = new HashSet<>();
        model.getProfiles().stream()
            .filter(p -> p.getBuild() != null).map(p -> p.getBuild())
            .flatMap(m -> m.getPlugins().stream())
            .filter(p -> p.getConfiguration() != null)
            .map(p -> (Xpp3Dom)p.getConfiguration())
            .forEach(d -> collectExcludes(d, excludes));
        
        for (String srcPath : sourcePaths) {
            for (String exclude : excludes) {
                Path path = Paths.get(srcPath, exclude);
                if (path.toFile().exists()) {
                    excludedSourceFiles.add(path.toString());
                }
            }
        }
    }
    
    private String getSourceDirectory(String dir, String[][] names) {
        if (dir != null) {
            String sourceDirectory = toAbsolutePath(dir);
            sourceDirExists = sourceDirExists | new File(sourceDirectory).exists();
            return sourceDirectory;
        }
        
        for (int index = 0; index < names.length; index++) {
            String sourceDirectory = resolvePath(names[index]);
            if (sourceDirectory != null) {
                sourceDirExists = true;
                return sourceDirectory;
            }
        }
        return null;
    }
    
    private String getGeneratedSourceDirectory(Path buildPath, String names[]) {
        for (int index = 0; index < names.length; index++) {
            Path path = buildPath.resolve(names[index]);
            String resolvedPath = toAbsolutePath(path.toString());
            if (new File(resolvedPath).exists()) {
                return resolvedPath;
            }
        }
        return null;
    }
    
    private void collectExcludes(Xpp3Dom dom, Set<String> qnames) {
        for (Xpp3Dom child : dom.getChildren()) {
            if (child.getName().equals("exclude") || child.getName().equals("testExclude")) {
                qnames.add(child.getValue());
            }
            collectExcludes(child, qnames);
        }
    }
    
    @Override
    void setUpTopProject() throws Exception {
        copyDependentLibrariesByCommandExecutor();
        super.setUpTopProject();
    }
    
    private void copyDependentLibrariesByCommandExecutor() throws Exception {
        if (libPath.toFile().exists()) {
            return;
        }
        
        Files.createDirectory(libPath);
        
        if (mvnCommand == null || mvnCommand.length() == 0) {
            mvnCommand = null;
            System.err.println("*******************************************************************");
            System.err.println("Cannot find the Maven command. Please execute it manually:");
            System.err.println("  -- mvn generate-sources");
            System.err.println("  -- mvn generate-test-sources");
            System.err.println("  -- mvn dependency:copy-dependencies -DoutputDirectory=" + COPIED_CLASSPATH);
            System.err.println("*******************************************************************");
            return;
        }
        
        System.out.println("Resolving dependencies");
        
        File pomFile = configFile.toFile();
        File mvnCommandFile = new File(mvnCommand);
        try {
            executeCommand(pomFile, mvnCommandFile, "generate-sources", "-Denforcer.skip=true");
            executeCommand(pomFile, mvnCommandFile, "generate-test-sources", "-Denforcer.skip=true");
            executeCommand(pomFile, mvnCommandFile,
                    "dependency:copy-dependencies", "-DoutputDirectory=" + COPIED_CLASSPATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void executeCommand(File pomFile, File mvnCommandFile, String goals, String option) throws Exception {
        Properties properties = new Properties();
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(pomFile);
        request.setQuiet(true);
        request.setProperties(properties);
        request.setGoals(Arrays.asList(goals));
        request.setOutputHandler(null);
        if (option != null) {
            request.setMavenOpts(option);
        }
        
        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(Paths.get(userHome).toFile());
        invoker.setMavenExecutable(mvnCommandFile);
        //invoker.setOutputHandler(msg -> System.out.println(msg));
        invoker.setOutputHandler(null);
        invoker.setErrorHandler(null);
        invoker.setInputStream(InputStream.nullInputStream());
        invoker.execute(request);
    }
    
    @Override
    public String toString() {
        return "Maven Env " + basePath.toString();
    }
}
