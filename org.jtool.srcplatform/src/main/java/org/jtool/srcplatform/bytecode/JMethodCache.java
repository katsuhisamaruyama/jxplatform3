/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.bytecode;

import org.jtool.srcmodel.QualifiedName;

/**
 * Concise information on a cached method.
 * 
 * @author Katsuhisa Maruyama
 */
class JMethodCache extends JMethod {
    
    JMethodCache(String signature, JClass declaringClass, BytecodeClass bclass) {
        super(new QualifiedName(declaringClass.getClassName(), signature), declaringClass);
        
        defFields.addAll(bclass.getDefFields(signature));
        useFields.addAll(bclass.getUseFields(signature));
        
        isDefUseDecided = true;
    }
}
