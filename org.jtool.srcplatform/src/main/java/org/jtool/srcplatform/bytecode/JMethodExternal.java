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
    protected void findDefUseFields(Set<JMethod> visitedMethods, Set<JField> visitedFields, int count) {
        if (visitedMethods.contains(this) || count >= maxNumberOfChain) {
            return;
        }
        
        visitedMethods.add(this);
        
        collectDefUseFields();
        collectAccessedMethods();
        collectDefUseFields(this, visitedMethods, visitedFields, count + 1);
    }
    
    private void collectDefUseFields() {
        defFields.addAll(updateClassName(bclass.getDefFields(getSignature())));
        useFields.addAll(updateClassName(bclass.getUseFields(getSignature())));
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
}
