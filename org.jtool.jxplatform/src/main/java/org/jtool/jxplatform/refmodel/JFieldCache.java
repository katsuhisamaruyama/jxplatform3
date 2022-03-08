/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.refmodel;

import org.jtool.srcmodel.QualifiedName;

/**
 * Concise information on a cached field.
 * 
 * @author Katsuhisa Maruyama
 */
class JFieldCache extends JField {
    
    JFieldCache(String signature, JClass declaringClass, BytecodeClass bclass) {
        super(new QualifiedName(declaringClass.getClassName(), signature), declaringClass);
        
        isDefUseDecided = true;
    }
}
