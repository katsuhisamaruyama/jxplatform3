/*
 *  Copyright 2022-2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal.refmodel;

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
    
    protected boolean hasDefUseCollected = false;
    protected boolean hasDefUseCached = false;
    
    protected List<DefUseField> defFields = new ArrayList<>();
    protected List<DefUseField> useFields = new ArrayList<>();
    protected Set<JMethod> accessedMethods = new HashSet<>();
    
    protected List<DefUseField> defFieldsCache = new ArrayList<>();
    protected List<DefUseField> useFieldsCache = new ArrayList<>();
    
    protected List<DefUseField> allDefFields = new ArrayList<>();
    protected List<DefUseField> allUseFields = new ArrayList<>();
    
    static final String RETURN_VALUE_PLACEHOLDER = "$$RV";
    static final String RECEIVER_NAME_PLACEHOLDER = "$$RN";
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
        defFieldsCache = null;
        useFieldsCache = null;
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
    
    public void findDefUseFields(String receiverName, String returnValue) {
        collectDefUseFieldsInThisMethod();
        
        InvocationForm prefix = new InvocationForm(receiverName, RECEIVER_NAME_PLACEHOLDER);
        String cachedReturnValue = RETURN_VALUE_PLACEHOLDER;
        
        if (!hasDefUseCached) {
            
            defFieldsCache.clear();
            useFieldsCache.clear();
            
            List<CFGMethodCall> callChain = new ArrayList<>();
            Set<JMethod> visitedMethods = new HashSet<>();
            
            collectDefUseFieldsInAccessedMethods(this, prefix, cachedReturnValue, callChain);
            
            traverseAccessedMethods(this, prefix, cachedReturnValue, callChain, visitedMethods, 0);
            
            //hasDefUseCached = prefix.reusable;
        }
        
        String placeholder = prefix.placeholder;
        if (receiverName.length() == 0) {
            placeholder = placeholder + ".";
        }
        
        allDefFields.clear();
        for (DefUseField def : defFieldsCache) {
            DefUseField newDef = new DefUseField(def);
            newDef.updateReferenceForm(newDef.getReferenceForm().replace(cachedReturnValue, returnValue));
            newDef.updateReferenceForm(newDef.getReferenceForm().replace(placeholder, receiverName));
            newDef.updateReferenceForm(replace(receiverName, newDef.getReferenceForm()));
            
            allDefFields.add(newDef);
        }
        allUseFields.clear();
        for (DefUseField use : useFieldsCache) {
            DefUseField newUse = new DefUseField(use);
            newUse.updateReferenceForm(newUse.getReferenceForm().replace(cachedReturnValue, returnValue));
            newUse.updateReferenceForm(newUse.getReferenceForm().replace(placeholder, receiverName));
            newUse.updateReferenceForm(replace(receiverName, newUse.getReferenceForm()));
            
            allUseFields.add(newUse);
        }
    }
    
    private String replace(String receiverName, String form) {
        if (receiverName.endsWith("this")) {
            return form.replaceAll("^this\\.!" + declaringClass.getSimpleName() + "(.+)\\.", "this.");
        } else if (receiverName.endsWith("super")) {
            return form.replaceAll("^super\\.!" + declaringClass.getSimpleName() + "(.+)\\.", "super.");
        }
        return form;
    }
    
    abstract protected void collectDefUseFieldsInThisMethod();
    
    abstract protected void collectDefUseFieldsInAccessedMethods(JMethod originMethod,
            InvocationForm prefix, String returnValue, List<CFGMethodCall> callChain);
    
    abstract protected void traverseAccessedMethods(JMethod originMethod,
            InvocationForm prefix, String returnValue, List<CFGMethodCall> callChain,
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

class InvocationForm {
    String original = "";
    String placeholder = "";
    boolean reusable = true;
    
    InvocationForm() {
    }
    
    InvocationForm(String original, String placeholder) {
        this.original = original;
        this.placeholder = placeholder;
    }
}
