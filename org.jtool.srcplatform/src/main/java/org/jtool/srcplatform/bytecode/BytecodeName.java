/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.bytecode;

/**
 * An object that represents the name of a byte-code class.
 * 
 * @author Katsuhisa Maruyama
 */
public class BytecodeName {
    public final String className;
    public final String cacheName;
    
    BytecodeName(String className, String cacheName) {
        this.className = className;
        this.cacheName = cacheName;
    }
}
