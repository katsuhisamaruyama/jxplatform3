/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.project;

import java.nio.file.Path;

import org.eclipse.jdt.core.JavaCore;

/**
 * Obtains path information from the Eclipse setting.
 * 
 * @author Katsuhisa Maruyama
 */
class SimpleEnv extends ProjectEnv {
    
    SimpleEnv(String name, Path basePath, Path topPath) {
        super(name, basePath, topPath);
        configFile = null;
        
        setPaths();
    }
    
    @Override
    ProjectEnv createProjectEnv(String name, Path basePath, Path topPath) {
        return new SimpleEnv(name, basePath, topPath);
    }
    
    @Override
    boolean isApplicable() {
        return true;
    }
    
    private void setPaths() {
        sourcePaths.add(basePath.resolve("src").toString());
        sourcePaths.add(basePath.toString());
        binaryPaths.add(basePath.resolve("bin").toString());
        classPaths.add(basePath.resolve("lib").toString());
        classPaths.add(basePath.resolve("lib-copied").toString());
        
        compilerSourceVersion = JavaCore.VERSION_11;
        compilerTargetVersion = JavaCore.VERSION_11;
    }
    
    @Override
    public String toString() {
        return "Simple Env " + basePath.toString();
    }
}
