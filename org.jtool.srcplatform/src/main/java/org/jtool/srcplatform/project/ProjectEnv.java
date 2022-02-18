/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.project;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.io.File;
import java.io.IOException;

/**
 * Obtains path information from the project setting.
 * 
 * @author Katsuhisa Maruyama
 */
abstract class ProjectEnv {
    
    protected static final String DEFAULT_SOURCEPATH = "src";
    protected static final String DEFAULT_BINARYPATH = "bin";
    protected static final String DEFAULT_CLASSPATH = "lib";
    protected static final String COPIED_CLASSPATH = "lib-copied";
    
    protected static final String userHome = System.getProperty("user.home");
    
    protected String name;
    protected Path basePath;
    protected Path libPath;
    protected Path configFile;
    
    protected List<String> modules;
    
    protected Set<String> classPath;
    protected Set<String> sourcePath;
    protected Set<String> binaryPath;
    
    ProjectEnv(String name, Path basePath) {
        this.name = name;
        this.basePath = basePath;
        
        this.libPath = basePath.resolve(COPIED_CLASSPATH);
        this.configFile = null;
        this.modules = new ArrayList<>();
    }
    
    static ProjectEnv getProjectEnv(String name, Path basePath, ProjectEnv parent) {
        if (parent != null) {
            ProjectEnv env = parent.createProjectEnv(name, basePath);
            if (env.isApplicable()) {
                return env;
            }
        }
        
        List<ProjectEnv> envs = new ArrayList<ProjectEnv>();
        envs.add(new MavenEnv(name, basePath));
        envs.add(new AntEnv(name, basePath));
        envs.add(new GradleEnv(name, basePath));
        envs.add(new EclipseEnv(name, basePath));
        
        for (ProjectEnv env : envs) {
            if (env.isApplicable()) {
                return env;
            }
        }
        return new SimpleEnv(name, basePath);
    }
    
    abstract ProjectEnv createProjectEnv(String name, Path basePath);
    
    boolean isApplicable() {
        return false;
    }
    
    boolean isProject() {
        return true;
    }
    
    String getName() {
        return name;
    }
    
    Path getBasePath() {
        return basePath;
    }
    
    List<String> getModules() {
        return modules;
    }
    
    Set<String> getSourcePath() {
        if (sourcePath.size() == 0) {
            sourcePath.add(basePath.resolve(DEFAULT_SOURCEPATH).toString());
        }
        return sourcePath;
    }
    
    Set<String> getBinaryPath() {
        if (binaryPath.size() == 0) {
            binaryPath.add(basePath.resolve(DEFAULT_BINARYPATH).toString());
        }
        return binaryPath;
    }
    
    Set<String> getClassPath() {
        if (classPath.size() == 0) {
            classPath.add(basePath.resolve(DEFAULT_CLASSPATH).toString());
            classPath.add(basePath.resolve(COPIED_CLASSPATH).toString());
        }
        return classPath;
    }
    
    protected String resolvePath(String names[]) {
        Path path = basePath;
        for (int index = 0; index < names.length; index++) {
            path = path.resolve(names[index]);
        }
        
        String resolvedPath = toAbsolutePath(path.toString());
        if (new File(resolvedPath).exists()) {
            return resolvedPath;
        }
        return null;
    }
    
    protected String toAbsolutePath(String path) {
        if (path.charAt(0) == '/') {
            return path;
        } else {
             return basePath.toAbsolutePath() + File.separator + path;
        }
    }
    
    void setUpTopProject() throws Exception {
    }
    
    void setUpEachProject() throws Exception {
    }
    
    protected static String findCommandPath(String command, String option) {
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
            System.err.println("This method is not available on Windows.");
            return null;
        }
        
        String[] paths = { userHome, "/usr/local/bin", "/usr/bin" };
        for (int index = 0; index < paths.length; index++) {
            Path commandPath = Paths.get(paths[index] + File.separatorChar + command);
            if (checkCommand(commandPath, option)) {
                return commandPath.toString();
            }
        }
        return "";
    }
    
    protected static boolean checkCommand(Path commandPath, String option) {
        if (!commandPath.toFile().isFile()) {
            return false;
        }
        
        try {
            new ProcessBuilder(commandPath.toString(), option).start();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
