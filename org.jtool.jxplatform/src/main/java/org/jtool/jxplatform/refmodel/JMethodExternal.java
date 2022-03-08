/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.refmodel;

/**
 * Concise information on a method outside the project.
 * 
 * @author Katsuhisa Maruyama
 */
class JMethodExternal extends JMethodFrozen {
    
    JMethodExternal(String signature, JClass declaringClass, BytecodeClass bclass) {
        super(signature, declaringClass, bclass);
    }
}
