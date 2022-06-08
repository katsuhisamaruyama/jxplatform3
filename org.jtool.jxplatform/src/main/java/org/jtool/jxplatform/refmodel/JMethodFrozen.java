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
        bclass.getDefFields(getSignature()).stream()
            .forEach(var -> {
                var.updateReferenceForm(OUTSIDE_FIELD_SYMBOL + var.getReferenceForm());
                defFields.add(updateClassName(var));
            });
        bclass.getUseFields(getSignature()).stream()
            .forEach(var -> {
                var.updateReferenceForm(OUTSIDE_FIELD_SYMBOL + var.getReferenceForm());
                useFields.add(updateClassName(var));
            });
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
            JMethod accessedMethod, String prefix) {
        for (DefUseField var : accessedMethod.defFields) {
            if (var.isInThis()) {
                String referenceForm;
                if (prefix.length() == 0) {
                    referenceForm = var.getReferenceForm();
                } else {
                    referenceForm = prefix + "." + var.getReferenceForm();
                }
                
                DefUseField def = new DefUseField(var);
                def.updateReferenceForm(referenceForm);
                originMethod.allDefFields.add(def);
            } else if (var.isStatic()) {
                originMethod.allDefFields.add(var);
            }
        }
        
        for (DefUseField var : accessedMethod.useFields) {
            if (var.isInThis()) {
                String referenceForm;
                if (prefix.length() == 0) {
                    referenceForm = var.getReferenceForm();
                } else {
                    referenceForm = prefix + "." + var.getReferenceForm();
                }
                
                DefUseField use = new DefUseField(var);
                use.updateReferenceForm(referenceForm);
                originMethod.allUseFields.add(use);
            } else if (var.isStatic()) {
                originMethod.allUseFields.add(var);
            }
        }
    }
    
    protected void collectDefUseFieldsInAccessedMethods(JMethod originMethod,
            String prefix, Set<JMethod> visitedMethods, int count) {
        for (JMethod amethod : accessedMethods) {
            if (!amethod.stopTraverse(visitedMethods, count + 1)) {
                visitedMethods.add(amethod);
                
                amethod.collectDefUseFieldsInThisMethod();
                collectDefUseFieldsInAccessedMethods(originMethod, amethod, prefix);
                
                amethod.collectDefUseFieldsInAccessedMethods(originMethod, prefix, visitedMethods, count + 1);
            }
        }
    }
    
    protected boolean stopTraverse(Set<JMethod> visitedMethods, int count) {
        return count >= maxNumberOfChainForBytecode || visitedMethods.contains(this);
    }
}
