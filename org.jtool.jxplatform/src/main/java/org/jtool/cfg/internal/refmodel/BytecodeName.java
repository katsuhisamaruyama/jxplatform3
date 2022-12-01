/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal.refmodel;

/**
 * An object that represents the name of a byte-code class.
 * 
 * @author Katsuhisa Maruyama
 */
public class BytecodeName {
    final String className;
    final String cacheName;
    
    BytecodeName(String className, String cacheName) {
        this.className = className;
        this.cacheName = cacheName;
    }
    
    public String getClassName() {
        return className;
    }
    
    public String cacheName() {
        return cacheName;
    }
}
