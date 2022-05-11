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
    
    public void findDefUseFields() {
        collectDefUseFieldsInThisMethod();
        
        allDefFields.clear();
        allUseFields.clear();
        allDefFields.addAll(defFields);
        allUseFields.addAll(useFields);
        
        collectDefUseFieldsInAccessedMethods(this, new HashSet<>(), 0);
    }
    
    protected void collectDefUseFieldsInThisMethod() {
        if (!isDefUseCollected) {
            collectDefUseFields();
            collectAccessedMethods();
            isDefUseCollected = true;
        }
    }
    
    abstract protected void collectDefUseFields();
    
    abstract protected void collectAccessedMethods();
    
    abstract boolean stopTraverse(Set<JMethod> visitedMethods, int count);
    
    protected void collectDefUseFieldsInAccessedMethods(JMethod method, Set<JMethod> visitedMethods, int count) {
        for (JMethod amethod : method.accessedMethods) {
            
            if (!amethod.stopTraverse(visitedMethods, count + 1)) {
                visitedMethods.add(amethod);
                
                amethod.collectDefUseFieldsInThisMethod();
                amethod.defFields.stream()
                       .filter(var -> !var.getReferenceForm().startsWith("this"))
                       .forEach(var -> method.allDefFields.add(var));
                amethod.useFields.stream()
                       .filter(var -> !var.getReferenceForm().startsWith("this"))
                       .forEach(var -> method.allUseFields.add(var));
                
                collectDefUseFieldsInAccessedMethods(amethod, visitedMethods, count + 1);
            }
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof JMethod) ? equals((JMethod)obj) : false;
    }
    
    public boolean equals(JMethod method) {
        return method != null && (this == method || getQualifiedName().fqn().equals(method.getQualifiedName().fqn()));
    }
}
