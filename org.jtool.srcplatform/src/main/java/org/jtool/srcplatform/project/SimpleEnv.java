/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.project;

import java.nio.file.Path;
import java.util.HashSet;

/**
 * Obtains path information from the Eclipse setting.
 * 
 * @author Katsuhisa Maruyama
 */
class SimpleEnv extends ProjectEnv {
    
    SimpleEnv(Path basePath) {
        super(basePath);
        sourcePath = new HashSet<String>();
        binaryPath = new HashSet<String>();
        classPath = new HashSet<String>();
        
        sourcePath.add(basePath.resolve("src").toString());
        binaryPath.add(basePath.resolve("bin").toString());
        classPath.add(basePath.resolve("lib").toString());
    }
    
    @Override
    boolean isApplicable() {
        return true;
    }
    
    @Override
    public String toString() {
        return "Simple Env " + basePath.toString();
    }
}
