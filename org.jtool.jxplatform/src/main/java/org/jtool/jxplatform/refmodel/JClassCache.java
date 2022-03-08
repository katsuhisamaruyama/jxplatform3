/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.refmodel;

/**
 * Concise information on a class with data extracted from a cache file.
 * 
 * @author Katsuhisa Maruyama
 */
class JClassCache extends JClassFrozen {
    
    JClassCache(BytecodeClass bclass, BytecodeClassStore bcStore) {
        super(bclass, bcStore);
        
        bclass.getMethods().stream()
                .forEach(sig -> methods.add(new JMethodCache(sig, this, bclass)));
        
        bclass.getFields().stream()
                .forEach(sig -> fields.add(new JFieldCache(sig, this)));
    }
}
