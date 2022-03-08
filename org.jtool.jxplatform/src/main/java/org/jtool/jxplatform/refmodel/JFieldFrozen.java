/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.refmodel;

import org.jtool.srcmodel.QualifiedName;

/**
 * Concise information on a field with frozen data extracted from a library file or a cache file.
 * 
 * @author Katsuhisa Maruyama
 */
class JFieldFrozen extends JField {
    
    JFieldFrozen(String signature, JClass declaringClass) {
        super(new QualifiedName(declaringClass.getClassName(), signature), declaringClass);
    }
}
