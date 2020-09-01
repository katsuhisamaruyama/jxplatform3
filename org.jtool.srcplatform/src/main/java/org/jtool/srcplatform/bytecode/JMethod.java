/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.bytecode;

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
    protected Set<JMethod> overridingMethods = new HashSet<>();
    
    protected static int MaxNumberOfChain = 0;
    protected static int MaxNumberOfOverriding = 0;
    
    protected JMethod(QualifiedName qname, JClass declaringClass) {
        super(qname, declaringClass.bcStore);
        
        this.declaringClass = declaringClass;
    }
    
    public JClass getDeclaringClass() {
        return declaringClass;
    }
    
    @Override
    protected boolean isInProject() {
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
    
    public Set<JMethod> getOverridingMethods() {
        return overridingMethods;
    }
    
    public void findDefUseFields() {
        findDefUseFields(new HashSet<>(), new HashSet<>(), 0);
    }
    
    abstract protected void findDefUseFields(Set<JMethod> visitedMethods, Set<JField> visitedFields, int count);
    
    protected void collectDefUseFields(Set<JMethod> visitedMethods, Set<JField> visitedFields, int count) {
        Set<JMethod> tmpVisitedMethods = new HashSet<>(visitedMethods);
        
        for (JMethod method : getAccessedMethods()) {
            method.findDefUseFields(visitedMethods, visitedFields, count);
            
            for (JMethod m : tmpVisitedMethods) {
                m.defFields.addAll(method.getDefFields());
                m.useFields.addAll(method.getUseFields());
            }
            
            if (count > 0) {
                for (JMethod omethod : method.getOverridingMethods()) {
                    omethod.findDefUseFields(visitedMethods, visitedFields, count);
                    
                    for (JMethod m : tmpVisitedMethods) {
                        m.defFields.addAll(method.getDefFields());
                        m.useFields.addAll(method.getUseFields());
                    }
                }
            }
        }
    }
    
    public boolean equals(JMethod method) {
        return method != null && (this == method || getQualifiedName().fqn().equals(method.getQualifiedName().fqn()));
    }
}
