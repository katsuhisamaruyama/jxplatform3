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
    
    JMethodExternal(String signature, JClass declaringClass, BytecodeClass bclass) {
        super(new QualifiedName(declaringClass.getClassName(), signature), declaringClass);
        
        defFields.addAll(bclass.getDefFields(signature));
        useFields.addAll(bclass.getUseFields(signature));
        
        collectAccessedMethods(bclass.getCalledMethods(signature));
        collectOverridingMethods(signature);
    }
    
    private void collectAccessedMethods(Collection<QualifiedName> qnames) {
        for (QualifiedName qname : qnames) {
            JMethod method = bcStore.getJMethod(qname.getClassName(), qname.getMemberSignature());
            if (method != null) {
                accessedMethods.add(method);
            }
        }
    }
    
    private void collectOverridingMethods(String signature) {
        if (isConstructor(this)) {
            return;
        }
        
        for (JClass descendant : declaringClass.getDescendants()) {
            JMethod m = descendant.getMethod(signature);
            if (m != null) {
                overridingMethods.add(m);
            }
        }
    }
    
    private boolean isConstructor(JMethod method) {
        String methodName = method.getSignature().substring(0, method.getSignature().indexOf("("));
        return method.declaringClass.getClassName().contentEquals(methodName);
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
        
        collectDefUseFields(visitedMethods, visitedFields, count + 1);
    }
}
