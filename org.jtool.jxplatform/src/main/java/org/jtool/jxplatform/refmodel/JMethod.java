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
    
    protected List<DefUseField> defFields = new ArrayList<>();
    protected List<DefUseField> useFields = new ArrayList<>();
    protected Set<JMethod> accessedMethods = new HashSet<>();
    
    protected List<DefUseField> allDefFields = new ArrayList<>();
    protected List<DefUseField> allUseFields = new ArrayList<>();
    
    static final String OUTSIDE_FIELD_SYMBOL = "!";
    
    protected JMethod(QualifiedName qname, JClass declaringClass) {
        super(qname, declaringClass.bcStore);
        
        this.declaringClass = declaringClass;
    }
    
    @Override
    protected void destroy() {
        super.destroy();
        declaringClass = null;
        defFields = null;
        useFields = null;
        accessedMethods= null;
        allDefFields = null;
        allUseFields = null;
    }
    
    public JClass getDeclaringClass() {
        return declaringClass;
    }
    
    @Override
    public boolean isInProject() {
        return declaringClass.isInProject();
    }
    
    public List<DefUseField> getDefFields() {
        return defFields;
    }
    
    public List<DefUseField> getUseFields() {
        return useFields;
    }
    
    public Set<JMethod> getAccessedMethods() {
        return accessedMethods;
    }
    
    public List<DefUseField> getAllDefFields() {
        return allDefFields;
    }
    
    public List<DefUseField> getAllUseFields() {
        return allUseFields;
    }
    
    public void findDefUseFields(String returnValue, String prefix) {
        collectDefUseFieldsInThisMethod();
        
        allDefFields.clear();
        allUseFields.clear();
        
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
