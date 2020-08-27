/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.bytecode;

import org.jtool.srcmodel.QualifiedName;
import java.util.Set;
import java.util.Collection;

/**
 * Concise information on a method outside the project.
 * 
 * @author Katsuhisa Maruyama
 */
public class JMethodExternal extends JMethod {
    
    private BytecodeClass bclass;
    
    JMethodExternal(String signature, JClass declaringClass, BytecodeClass bclass) {
        super(new QualifiedName(declaringClass.getClassName(), signature), declaringClass);
        
        this.bclass = bclass;
    }
    
    @Override
    public boolean isInProject() {
        return false;
    }
    
    @Override
    protected void findDefUseFields(Set<JMethod> visitedMethods, Set<JField> visitedFields, int count) {
        if (isDefUseDecided || visitedMethods.contains(this)) {
            return;
        }
        
        if (count > MaxNumberOfChain) {
            return;
        }
        
        isDefUseDecided = true;
        visitedMethods.add(this);
        
        collectDefUseFields();
        collectAccessedMethods();
        collectOverridingMethods();
        
        collectDefUseFields(visitedMethods, visitedFields, count + 1);
    }
    
    private void collectDefUseFields() {
        Collection<DefUseField> defs = bclass.getDefFields(getSignature());
        if (defs != null) {
            defFields.addAll(defs);
        }
        Collection<DefUseField> uses = bclass.getUseFields(getSignature());
        if (uses != null) {
            useFields.addAll(uses);
        }
    }
    
    private void collectAccessedMethods() {
        Collection<QualifiedName> qnames = bclass.getCalledMethods(getSignature());
        if (qnames == null) {
            return;
        }
        
        for (QualifiedName qname : qnames) {
            JMethod method = bcStore.getJMethod(qname.getClassName(), qname.getMemberSignature());
            if (method != null) {
                accessedMethods.add(method);
            }
        }
    }
    
    private void collectOverridingMethods() {
        if (isConstructor(this)) {
            return;
        }
        
        for (JClass descendant : declaringClass.getDescendants()) {
            JMethod method = descendant.getMethod(getSignature());
            if (method != null) {
                overridingMethods.add(method);
            }
        }
    }
    
    private boolean isConstructor(JMethod method) {
        String methodName = method.getSignature().substring(0, method.getSignature().indexOf("("));
        return method.declaringClass.getClassName().contentEquals(methodName);
    }
}
