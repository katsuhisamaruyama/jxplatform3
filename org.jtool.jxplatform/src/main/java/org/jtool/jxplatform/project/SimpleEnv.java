/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.project;

import java.nio.file.Path;

/**
 * Obtains path information from the Eclipse setting.
 * 
 * @author Katsuhisa Maruyama
 */
class SimpleEnv extends ProjectEnv {
    
    SimpleEnv(String name, Path basePath) {
        super(name, basePath);
        configFile = null;
        
        setPaths();
    }
    
    @Override
    ProjectEnv createProjectEnv(String name, Path basePath) {
        return new SimpleEnv(name, basePath);
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
    }
    
    @Override
    public String toString() {
        return "Simple Env " + basePath.toString();
    }
}
