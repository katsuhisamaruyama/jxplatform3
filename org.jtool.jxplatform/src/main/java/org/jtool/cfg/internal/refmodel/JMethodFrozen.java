/*
 *  Copyright 2022-2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal.refmodel;

import org.jtool.cfg.CFGMethodCall;
import org.jtool.srcmodel.QualifiedName;
import java.util.List;
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
    
    @Override
    protected void destroy() {
        super.destroy();
        bclass = null;
    }
    
    @Override
    protected void collectDefUseFieldsInThisMethod() {
        if (!hasDefUseCollected) {
            collectDefUseFields();
            collectAccessedMethods();
            hasDefUseCollected = true;
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
    
    @Override
    protected void collectDefUseFieldsInAccessedMethods(JMethod originMethod,
            InvocationForm prefix, String returnValue, List<CFGMethodCall> callChain) {
        for (DefUseField var : defFields) {
            if (!var.isUncovered()) {
                DefUseField def = new DefUseField(var);
                def.updateReferenceForm(getReferenceForm(var, prefix.placeholder));
                def.addHoldingNodes(callChain);
                
                originMethod.defFieldsCache.add(def);
            }
        }
        
        for (DefUseField var : useFields) {
            if (!var.isUncovered()) {
                DefUseField use = new DefUseField(var);
                use.updateReferenceForm(getReferenceForm(var, prefix.placeholder));
                use.addHoldingNodes(callChain);
                
                originMethod.useFieldsCache.add(use);
            }
        }
    }
    
    private String getReferenceForm(DefUseField var, String prefix) {
        if (var.isThis()) {
            if (prefix.length() == 0) {
                return var.getReferenceForm();
            } else {
                return prefix + "." + var.getReferenceForm();
            }
        } else {
            return var.getReferenceForm();
        }
    }
    
    @Override
    protected void traverseAccessedMethods(JMethod originMethod,
            InvocationForm prefix, String returnValue, List<CFGMethodCall> callChain,
            Set<JMethod> visitedMethods, int count) {
        for (JMethod amethod : accessedMethods) {
            if (amethod.stopTraverse(visitedMethods, count + 1)) {
                prefix.reusable = false;
            } else {
                visitedMethods.add(amethod);
                
                amethod.collectDefUseFieldsInThisMethod();
                
                amethod.collectDefUseFieldsInAccessedMethods(originMethod, prefix, returnValue, callChain);
                
                amethod.traverseAccessedMethods(originMethod, prefix, returnValue, callChain,
                        visitedMethods, count + 1);
            }
        }
    }
    
    @Override
    protected boolean stopTraverse(Set<JMethod> visitedMethods, int count) {
        return count >= maxNumberOfChainForBytecode || visitedMethods.contains(this);
    }
}
