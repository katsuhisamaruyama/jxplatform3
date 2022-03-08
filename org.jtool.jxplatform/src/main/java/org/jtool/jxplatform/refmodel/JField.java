/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.refmodel;

import org.jtool.srcmodel.QualifiedName;

/**
 * Concise information on a field.
 * 
 * @author Katsuhisa Maruyama
 */
abstract public class JField extends JCommon {
    
    protected JClass declaringClass;
    
    protected JField(QualifiedName qname, JClass declaringClass) {
        super(qname, declaringClass.bcStore);
        
        this.declaringClass = declaringClass;
    }
    
    public JClass getDeclaringClass() {
        return declaringClass;
    }
    
    @Override
    public boolean isInProject() {
        return declaringClass.isInProject();
    }
    
    public boolean equals(JField field) {
        return field != null && (this == field || getQualifiedName().fqn().equals(field.getQualifiedName().fqn()));
    }
}
