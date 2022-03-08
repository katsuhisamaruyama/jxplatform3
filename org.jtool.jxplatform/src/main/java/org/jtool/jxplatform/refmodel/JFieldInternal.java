/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.refmodel;

import org.jtool.srcmodel.JavaField;

/**
 * Concise information on a field inside the project.
 * 
 * @author Katsuhisa Maruyama
 */
class JFieldInternal extends JField {
    
    private JavaField jfield;
    
    JFieldInternal(JavaField jfield, JClass declaringClass) {
        super(jfield.getQualifiedName(), declaringClass);
        
        this.jfield = jfield;
    }
    
    public JavaField getJavaField() {
        return jfield;
    }
}
