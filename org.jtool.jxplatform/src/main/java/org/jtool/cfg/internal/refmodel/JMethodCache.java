/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal.refmodel;

/**
 * Concise information on a cached method.
 * 
 * @author Katsuhisa Maruyama
 */
class JMethodCache extends JMethodFrozen {
    
    JMethodCache(String signature, JClass declaringClass, BytecodeClass bclass) {
        super(signature, declaringClass, bclass);
    }
}
