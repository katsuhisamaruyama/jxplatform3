/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.project;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Properties;
import java.io.InputStream;
import org.apache.maven.model.Model;
import org.apache.maven.model.Build;
import org.apache.maven.model.BuildBase;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.Parent;
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
    
    private static String mvnCommand = findCommandPath("mvn", "-v");
    
    MavenEnv(String name, Path basePath, Path topPath) {
        super(name, basePath, topPath);
        configFile = basePath.resolve(Paths.get(MavenEnv.configName));
    }
    
    @Override
    ProjectEnv createProjectEnv(String name, Path basePath, Path topPath) {
        return new MavenEnv(name, basePath, topPath);
    }
    
    @Override
    boolean isApplicable() {
        try {
            if (configFile != null && configFile.toFile().exists()) {
                setConfigParameters(configFile);
                return true;
            }
        } catch (Exception e) { /* empty */ }
        return false;
    }
    
    private List<Model> getModels(Path configPath) throws Exception {
        List<Model> models = new ArrayList<>();
        Model model = getModel(configPath);
        models.add(model);
        
        Path curpath = basePath;
        while (model != null) {
            Parent parent = model.getParent();
            if (parent == null) {
                break;
            }
            
            try {
                String pomname = File.separatorChar + configName;
                String relpath = parent.getRelativePath();
                String filename = relpath.endsWith(pomname) ? relpath : relpath + pomname;
                Path filepath = curpath.resolve(Paths.get(filename)).normalize();
                curpath = filepath.getParent();
                model = getModel(filepath);
                models.add(model);
            } catch (Exception e) {
                break;
            }
        }
        return models;
    }
    
    private Model getModel(Path path) throws Exception {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(Files.newBufferedReader(path));
        model.setPomFile(path.toFile());
        return model;
    }
    
    private String compilerVersion(List<Model> models, String key) throws Exception {
        for (Model m : models) {
            Properties properties = m.getProperties();
            String version = properties.getProperty(key);
            if (version != null) {
                return version;
            }
        }
        return null;
    }
    
    private void setConfigParameters(Path cpath) throws Exception {
        List<Model> models = getModels(cpath);
        Model model = models.get(0);
        
        modules = model.getModules();
        
        compilerSourceVersion = compilerVersion(models, "maven.compiler.source");
        compilerTargetVersion = compilerVersion(models, "maven.compiler.target");
        
        sourcePaths = new HashSet<>();
        binaryPaths = new HashSet<>();
        classPaths = new HashSet<>();
        
        String sourceDirectory = null;
        String testSourceDirectory = null;
        for (Model m : models) {
            Build build = m.getBuild();
            if (build != null) {
                String sdir = build.getSourceDirectory();
                if (sdir != null && sourceDirectory == null) {
                    sourceDirectory = sdir;
                }
                String tdir = build.getTestSourceDirectory();
                if (sdir != null && testSourceDirectory == null) {
                    testSourceDirectory = tdir;
                }
            }
        }
        
        String sourceDirectoryCandidates[][] = { { "src", "main", "java" }, { "src" } };
        String testSourceDirectoryCandidates[][] = { { "src", "test", "java" }, { "test" } };
        sourceDirectory = getSourceDirectory(sourceDirectory, sourceDirectoryCandidates);
        testSourceDirectory = getSourceDirectory(testSourceDirectory, testSourceDirectoryCandidates);
        if (sourceDirectory == null && testSourceDirectory == null) {
            File dir = new File(basePath.toString());
            boolean sourceDirExists = Arrays.asList(dir.listFiles()).stream()
                    .anyMatch(file -> file.isFile() && file.getName().endsWith(".java"));
            if (sourceDirExists) {
                sourceDirectory = basePath.toString();
                testSourceDirectory = basePath.toString();
            }
        }
        
        if (sourceDirectory != null) {
            sourcePaths.add(sourceDirectory);
        } else {
            return;
        }
        if (testSourceDirectory != null) {
            sourcePaths.add(testSourceDirectory);
        }
        
        Build build = model.getBuild();
        String buildDirectory = null;
        if (build != null) {
            buildDirectory = build.getDirectory();
        } else {
            BuildBase buildBase = model.getProfiles().stream()
                    .filter(p -> p.getBuild() != null)
                    .map(p -> p.getBuild()).findFirst().orElse(null);
            if (buildBase != null) {
                buildDirectory = buildBase.getDirectory();
            }
        }
        
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
        
        String outputDirectory = null;
        String testOutputDirectory = null;
        if (build != null) {
            outputDirectory = build.getOutputDirectory();
            testOutputDirectory = build.getTestOutputDirectory();
        }
        if (outputDirectory == null) {
            outputDirectory = buildPath.resolve("classes").toString();
        }
        if (testOutputDirectory == null) {
            testOutputDirectory = buildPath.resolve("test-classes").toString();
        }
        binaryPaths.add(toAbsolutePath(outputDirectory));
        binaryPaths.add(toAbsolutePath(testOutputDirectory));
        
        classPaths.add(basePath.resolve(DEFAULT_CLASSPATH).toString());
        classPaths.add(libPath.toString());
        
        Stream<Plugin> plugins1;
        if (build != null) {
            plugins1 = build.getPlugins().stream();
        } else {
            plugins1 = new ArrayList<Plugin>().stream();
        }
        Stream<Plugin> plugins2;
        if (model.getProfiles() != null) {
            plugins2 = model.getProfiles().stream()
                    .filter(p -> p.getBuild() != null)
                    .map(p -> p.getBuild()).flatMap(m -> m.getPlugins().stream());
        } else {
            plugins2 = new ArrayList<Plugin>().stream();
        }
        List<Xpp3Dom> configurations = Stream.concat(plugins1, plugins2)
                .filter(p -> p.getArtifactId().equals("maven-compiler-plugin"))
                .filter(p -> p.getConfiguration() != null)
                .map(p -> (Xpp3Dom)p.getConfiguration())
                .collect(Collectors.toList());
        
        Set<String> includes = new HashSet<>();
        configurations.forEach(dom -> collectFileNames(dom, includes, "include", "testInclude"));
        includedSourceFiles = collectFileNames(includes);
        
        Set<String> excludes = new HashSet<>();
        configurations.forEach(dom -> collectFileNames(dom, excludes, "exclude", "testExclude"));
        excludedSourceFiles = collectFileNames(excludes);
        //excludedSourceFiles.forEach(p -> System.err.println("Ex = " + p));
    }
    
    private String getSourceDirectory(String dir, String[][] names) {
        if (dir != null) {
            String sourceDirectory = toAbsolutePath(dir);
            if (new File(sourceDirectory).exists()) {
                return sourceDirectory;
            }
        }
        
        for (int index = 0; index < names.length; index++) {
            String sourceDirectory = resolvePath(names[index]);
            if (sourceDirectory != null) {
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
    
    private void collectFileNames(Xpp3Dom dom, Set<String> filenames, String qname, String testqname) {
        for (Xpp3Dom child : dom.getChildren()) {
            if (child.getName().equals(qname) || child.getName().equals(testqname)) {
                filenames.add(child.getValue());
            }
            collectFileNames(child, filenames, qname, testqname);
        }
    }
    
    private Set<String> collectFileNames(Set<String> filenames) {
        Set<String> names = new HashSet<>();
        for (String srcpath : sourcePaths) {
            for (String filename : filenames) {
                Path path = Paths.get(srcpath, filename);
                if (path.toFile().isFile()) {
                    names.add(path.toString());
                } else if (path.toFile().isDirectory()) {
                    try (Stream<Path> children = Files.list(path)) {
                        children.filter(child -> child.toFile().isFile())
                                .forEach(child -> names.add(child.toString()));
                    } catch (Exception e) { /* empty */ }
                }
            }
        }
        return names;
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
        //request.setOutputHandler(msg -> System.out.println(msg));
        request.setOutputHandler(null);
        request.setErrorHandler(null);
        request.setInputStream(InputStream.nullInputStream());
        invoker.execute(request);
    }
    
    @Override
    public String toString() {
        return "Maven Env " + basePath.toString();
    }
}
