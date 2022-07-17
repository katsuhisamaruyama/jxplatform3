/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.refmodel;

import org.jtool.cfg.CFGMethodCall;
import org.jtool.srcmodel.QualifiedName;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;

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
    
    static final String OUTSIDE_FIELD_SYMBOL = "!";
    
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
    
    public void findDefUseFields(CFGMethodCall callNode, String prefix) {
        collectDefUseFieldsInThisMethod();
        
        allDefFields.clear();
        allUseFields.clear();
        
        String returnValue = callNode.getReturnValueName();
        List<CFGMethodCall> callChain = new ArrayList<>();
        Set<JMethod> visitedMethods = new HashSet<>();
        
        collectDefUseFieldsInAccessedMethods(this, prefix, returnValue, callChain);
        
        traverseAccessedMethods(this, prefix, returnValue, callChain, visitedMethods, 0);
    }
    
    abstract protected void collectDefUseFieldsInThisMethod();
    
    abstract protected void collectDefUseFieldsInAccessedMethods(JMethod originMethod,
            String prefix, String returnValue, List<CFGMethodCall> callChain);
    
    abstract protected void traverseAccessedMethods(JMethod originMethod,
            String prefix, String returnValue, List<CFGMethodCall> callChain,
            Set<JMethod> visitedMethods, int count);
    
    abstract boolean stopTraverse(Set<JMethod> visitedMethods, int count);
    
    protected DefUseField updateClassName(DefUseField var) {
        JField field = bcStore.getJField(var.getClassName(), var.getName());
        if (field != null) {
            var.updateClassName(field.getClassName());
        }
        return var;
    }
    
    protected Collection<DefUseField> updateClassName(Collection<DefUseField> vars) {
        vars.forEach(var -> updateClassName(var));
        return vars;
    }
    
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof JMethod) ? equals((JMethod)obj) : false;
    }
    
    public boolean equals(JMethod method) {
        return method != null &&
               (this == method || getQualifiedName().fqn().equals(method.getQualifiedName().fqn()));
    }
}
