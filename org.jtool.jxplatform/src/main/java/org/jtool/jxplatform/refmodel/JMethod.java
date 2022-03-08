/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.refmodel;

import org.jtool.srcmodel.QualifiedName;
import java.util.Set;
import java.util.HashSet;

/**
 * Concise information on a method.
 * 
 * @author Katsuhisa Maruyama
 */
abstract public class JMethod extends JCommon {
    
    protected JClass declaringClass;
    
    protected boolean isDefUseDecided = false;
    
    protected Set<DefUseField> defFields = new HashSet<>();
    protected Set<DefUseField> useFields = new HashSet<>();
    protected Set<JMethod> accessedMethods = new HashSet<>();
    
    protected JMethod(QualifiedName qname, JClass declaringClass) {
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
    
    protected void collectDefUseFields(JMethod method, Set<JMethod> visitedMethods, Set<JField> visitedFields, int count) {
        for (JMethod m : accessedMethods) {
            m.findDefUseFields(visitedMethods, visitedFields, count);
            
            m.getDefFields().stream()
                .filter(var -> !var.getReferenceForm().startsWith("this"))
                .forEach(var -> method.defFields.add(var));
            m.getUseFields().stream()
                .filter(var -> !var.getReferenceForm().startsWith("this"))
                .forEach(var -> method.useFields.add(var));
        }
    }
    
    public boolean equals(JMethod method) {
        return method != null && (this == method || getQualifiedName().fqn().equals(method.getQualifiedName().fqn()));
    }
}
