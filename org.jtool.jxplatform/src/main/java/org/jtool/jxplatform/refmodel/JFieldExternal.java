/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.refmodel;

import org.jtool.srcmodel.QualifiedName;

/**
 * Concise information on a field outside the project.
 * 
 * @author Katsuhisa Maruyama
 */
public class JFieldExternal extends JField {
    
    JFieldExternal(String signature, JClass declaringClass) {
        super(new QualifiedName(declaringClass.getClassName(), signature), declaringClass);
    }
}
