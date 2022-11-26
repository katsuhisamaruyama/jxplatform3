/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal.refmodel;

/**
 * Concise information on a field outside the project.
 * 
 * @author Katsuhisa Maruyama
 */
class JFieldExternal extends JFieldFrozen {
    
    JFieldExternal(String signature, JClass declaringClass) {
        super(signature, declaringClass);
    }
}
