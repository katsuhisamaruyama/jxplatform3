/*
 *  Copyright 2022
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
    
    protected boolean isDefUseCollected = false;
    
    protected Set<DefUseField> defFields = new HashSet<>();
    protected Set<DefUseField> useFields = new HashSet<>();
    protected Set<JMethod> accessedMethods = new HashSet<>();
    
    protected Set<DefUseField> allDefFields = new HashSet<>();
    protected Set<DefUseField> allUseFields = new HashSet<>();
    
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
    
    public Set<DefUseField> getAllDefFields() {
        return allDefFields;
    }
    
    public Set<DefUseField> getAllUseFields() {
        return allUseFields;
    }
    
    public void findDefUseFields(String receiverName) {
        collectDefUseFieldsInThisMethod();
        
        allDefFields.clear();
        allUseFields.clear();
        collectDefUseFieldsInAccessedMethods(this, this, receiverName);
        
        collectDefUseFieldsInAccessedMethods(this, receiverName, new HashSet<>(), 0);
    }
    
    abstract protected void collectDefUseFieldsInThisMethod();
    
    abstract protected void collectDefUseFieldsInAccessedMethods(JMethod originMethod,
            JMethod accessedMethod, String receiverName);
    
    abstract protected void collectDefUseFieldsInAccessedMethods(JMethod originMethod,
            String prefix, Set<JMethod> visitedMethods, int count);
    
    abstract boolean stopTraverse(Set<JMethod> visitedMethods, int count);
    
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof JMethod) ? equals((JMethod)obj) : false;
    }
    
    public boolean equals(JMethod method) {
        return method != null &&
               (this == method || getQualifiedName().fqn().equals(method.getQualifiedName().fqn()));
    }
}
