/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.bytecode;

import org.jtool.srcmodel.QualifiedName;
import java.util.Set;
import java.util.HashSet;

/**
 * Concise information on a field.
 * 
 * @author Katsuhisa Maruyama
 */
abstract public class JField extends JCommon {
    
    protected JClass declaringClass;
    
    protected boolean isDefUseDecided = true;
    
    protected Set<DefUseField> defFields = new HashSet<>();
    protected Set<DefUseField> useFields = new HashSet<>();
    protected Set<JMethod> accessedMethods = new HashSet<>();
    
    protected JField(QualifiedName qname, JClass declaringClass) {
        super(qname, declaringClass.bcStore);
    }
    
    public JClass getDeclaringClass() {
        return declaringClass;
    }
    
    @Override
    public boolean isInProject() {
        return declaringClass.isInProject();
    }
    
    public Set<DefUseField> getDefFields() {
        return defFields;
    }
    
    public Set<DefUseField> getUseFields() {
        return useFields;
    }
    
    public Set<JMethod> getAccessedMethods() {
        return accessedMethods;
    }
    
    public void findDefUseFields() {
        if (isDefUseDecided) {
            return;
        }
        isDefUseDecided = true;
        
        findDefUseFields(new HashSet<>(), new HashSet<>(), 0);
    }
    
    protected void findDefUseFields(Set<JMethod> visitedMethods, Set<JField> visitedFields, int count) {
    }
    
    protected void collectDefUseFields(JField field, Set<JMethod> visitedMethods, Set<JField> visitedFields, int count) {
        for (JMethod m : accessedMethods) {
            m.findDefUseFields(visitedMethods, visitedFields, count);
            
            field.defFields.addAll(m.getDefFields());
            field.useFields.addAll(m.getUseFields());
        }
    }
    
    public boolean equals(JField field) {
        return field != null && (this == field || getQualifiedName().fqn().equals(field.getQualifiedName().fqn()));
    }
}
