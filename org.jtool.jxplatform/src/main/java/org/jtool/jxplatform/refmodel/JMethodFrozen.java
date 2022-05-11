/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.refmodel;

import org.jtool.srcmodel.QualifiedName;
import java.util.Set;
import java.util.Collection;

/**
 * Concise information on a class with frozen data extracted from a library file or a cache file.
 * 
 * @author Katsuhisa Maruyama
 */
class JMethodFrozen extends JMethod {
    
    private BytecodeClass bclass;
    
    JMethodFrozen(String signature, JClass declaringClass, BytecodeClass bclass) {
        super(new QualifiedName(declaringClass.getClassName(), signature), declaringClass);
        
        this.bclass = bclass;
    }
    
    protected void collectDefUseFields() {
        defFields.addAll(updateClassName(bclass.getDefFields(getSignature())));
        useFields.addAll(updateClassName(bclass.getUseFields(getSignature())));
    }
    
    protected void collectAccessedMethods() {
        Collection<QualifiedName> qnames = bclass.getCalledMethods(getSignature());
        if (qnames == null) {
            return;
        }
        
        for (QualifiedName qname : qnames) {
            JMethod amethod = bcStore.getJMethod(qname.getClassName(), qname.getMemberSignature());
            if (amethod != null && !amethod.equals(this)) {
                accessedMethods.add(amethod);
            }
        }
    }
    
    protected boolean stopTraverse(Set<JMethod> visitedMethods, int count) {
        return count >= maxNumberOfChainForBytecode || visitedMethods.contains(this);
    }
}
