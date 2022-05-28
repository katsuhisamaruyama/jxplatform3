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
    
    protected void collectDefUseFieldsInThisMethod() {
        if (!isDefUseCollected) {
            collectDefUseFields();
            collectAccessedMethods();
            isDefUseCollected = true;
        }
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
            JMethod amethod = bcStore.getJMethod(qname.getClassName(), qname.getMemberSignature());
            if (amethod != null && !amethod.equals(this)) {
                accessedMethods.add(amethod);
            }
        }
    }
    
    protected void collectDefUseFieldsInAccessedMethods(JMethod originMethod,
            JMethod accessedMethod, String receiverName) {
        for (DefUseField var : accessedMethod.defFields) {
            DefUseField def;
            if (var.isStatic()) {
                def = var;
            } else {
                String referenceForm;
                if (receiverName.length() == 0) {
                    referenceForm = var.getReferenceForm();
                } else {
                    referenceForm = receiverName + "." + var.getReferenceForm();
                }
                def = new DefUseField(var.getClassName(), var.getName(), referenceForm,
                                      var.getType(), var.isPrimitive(), var.getModifiers());
            }
            originMethod.allDefFields.add(def);
        }
        
        for (DefUseField var : accessedMethod.useFields) {
            DefUseField use;
            if (var.isStatic()) {
                use = var;
            } else {
                String referenceForm;
                if (receiverName.length() == 0) {
                    referenceForm = var.getReferenceForm();
                } else {
                    referenceForm = receiverName + "." + var.getReferenceForm();
                }
                use = new DefUseField(var.getClassName(), var.getName(), referenceForm,
                                      var.getType(), var.isPrimitive(), var.getModifiers());
            }
            originMethod.allUseFields.add(use);
        }
    }
    
    protected void collectDefUseFieldsInAccessedMethods(JMethod originMethod,
            String prefix, Set<JMethod> visitedMethods, int count) {
        for (JMethod amethod : accessedMethods) {
            if (!amethod.stopTraverse(visitedMethods, count + 1)) {
                visitedMethods.add(amethod);
                
                amethod.collectDefUseFieldsInThisMethod();
                collectDefUseFieldsInAccessedMethods(originMethod, amethod, "");
                
                amethod.collectDefUseFieldsInAccessedMethods(originMethod, "", visitedMethods, count + 1);
            }
        }
    }
    
    protected boolean stopTraverse(Set<JMethod> visitedMethods, int count) {
        return count >= maxNumberOfChainForBytecode || visitedMethods.contains(this);
    }
}
