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
        findDefUseFields(new HashSet<>(), new HashSet<>(), 0);
    }
    
    abstract protected void findDefUseFields(Set<JMethod> visitedMethods, Set<JField> visitedFields, int count);
    
    protected void collectDefUseFields(Set<JMethod> visitedMethods, Set<JField> visitedFields, int count) {
        Set<JMethod> tmpVisitedMethods = new HashSet<>(visitedMethods);
        
        for (JMethod method : getAccessedMethods()) {
            method.findDefUseFields(visitedMethods, visitedFields, count + 1);
            
            for (JMethod m : tmpVisitedMethods) {
                m.defFields.addAll(method.getDefFields());
                m.useFields.addAll(method.getUseFields());
            }
        }
    }
    
    public boolean equals(JField field) {
        return field != null && (this == field || getQualifiedName().fqn().equals(field.getQualifiedName().fqn()));
    }
}
