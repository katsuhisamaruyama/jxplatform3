/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal.refmodel;

/**
 * Concise information on a class outside the project.
 * 
 * @author Katsuhisa Maruyama
 */
class JClassExternal extends JClassFrozen {
    
    JClassExternal(BytecodeClass bclass, BytecodeClassStore bcStore) {
        super(bclass, bcStore);
        
        bclass.getMethods().stream()
                .forEach(sig -> methods.add(new JMethodExternal(sig, this, bclass)));
        
        bclass.getFields().stream()
                .forEach(sig -> fields.add(new JFieldExternal(sig, this)));
    }
}
