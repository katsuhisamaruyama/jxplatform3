/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal.refmodel;

/**
 * Concise information on a field with data extracted from a cache file.
 * 
 * @author Katsuhisa Maruyama
 */
class JFieldCache extends JFieldFrozen {
    
    JFieldCache(String signature, JClass declaringClass) {
        super(signature, declaringClass);
    }
}
