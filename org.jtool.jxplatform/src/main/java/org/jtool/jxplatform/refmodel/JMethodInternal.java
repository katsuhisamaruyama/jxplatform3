/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.refmodel;

import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.QualifiedName;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.CFGMethodCall;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

/**
 * Concise information on a method inside the project.
 * 
 * @author Katsuhisa Maruyama
 */
class JMethodInternal extends JMethod {
    
    private JavaMethod jmethod;
    private CFG cfg;
    
    JMethodInternal(JavaMethod jmethod, JClass declaringClass) {
        super(jmethod.getQualifiedName(), declaringClass);
        
        this.jmethod = jmethod;
    }
    
    public JavaMethod getJavaMethod() {
        return jmethod;
    }
    
    protected void collectDefUseFieldsInThisMethod() {
        if (!isDefUseCollected) {
            collectDefUseFields();
            isDefUseCollected = true;
        }
    }
    
    private void collectDefUseFields() {
        cfg = bcStore.getJavaProject().getCFGStore().getCFG(jmethod, false);
        if (cfg  == null) {
            return;
        }
        
        for (CFGNode node : cfg.getNodes()) {
            if (node.isStatement()) {
                CFGStatement stNode = (CFGStatement)node;
                if (stNode.isFormal()) {
                    continue;
                }
                
                stNode.getDefVariables()
                    .stream()
                    .filter(var -> var.isFieldAccess())
                    .forEach(var -> defFields.add(updateClassName(new DefUseField(var))));
                stNode.getUseVariables()
                    .stream()
                    .filter(var -> var.isFieldAccess())
                    .forEach(var -> useFields.add(updateClassName(new DefUseField(var))));
            }
        }
    }
    
    private List<CalledMethod> getCalledMethods() {
        List<CalledMethod> calledMethods = new ArrayList<>();
        if (cfg  == null) {
            return calledMethods;
        }
        
        for (CFGMethodCall callNode : cfg.getMethodCallNodes()) {
            for (JClass type : callNode.getApproximatedTypes()) {
                String sig = callNode.getSignature();
                if (callNode.isConstructorCall()) {
                    sig = type.getSimpleName() + sig.substring(sig.lastIndexOf('('));
                }
                
                JMethod method = type.getMethod(sig);
                if (method != null && !method.equals(this)) {
                    CalledMethod amethod = new CalledMethod(method, callNode.getReceiver().getName());
                    calledMethods.add(amethod);
                }
            }
        }
        return calledMethods;
    }
    
    protected void collectDefUseFieldsInAccessedMethods(JMethod originMethod,
            JMethod accessedMethod, String receiverName) {
        for (DefUseField var : accessedMethod.defFields) {
            if (var.getReferenceForm().startsWith("this.")) {
                String referenceForm = var.getReferenceForm().replace("this", receiverName);
                DefUseField def = new DefUseField(var.getClassName(), var.getName(), referenceForm,
                        var.getType(), var.isPrimitive(), var.getModifiers());
                originMethod.allDefFields.add(def);
            } else if (var.isStatic()) {
                originMethod.allDefFields.add(var);
            }
        }
        
        for (DefUseField var : accessedMethod.useFields) {
            if (var.getReferenceForm().startsWith("this.")) {
                String referenceForm = var.getReferenceForm().replace("this", receiverName);
                DefUseField use = new DefUseField(var.getClassName(), var.getName(), referenceForm,
                        var.getType(), var.isPrimitive(), var.getModifiers());
                originMethod.allUseFields.add(use);
            } else if (var.isStatic()) {
                originMethod.allUseFields.add(var);
            }
        }
    }
    
    protected void collectDefUseFieldsInAccessedMethods(JMethod originMethod,
            String prefix, Set<JMethod> visitedMethods, int count) {
        for (CalledMethod cmethod : getCalledMethods()) {
            JMethod amethod = cmethod.method;
            if (!amethod.stopTraverse(visitedMethods, count + 1)) {
                visitedMethods.add(amethod);
                
                amethod.collectDefUseFieldsInThisMethod();
                String newPrefix = prefix + "." + 
                        getSignature() + QualifiedName.QualifiedNameSeparator + cmethod.receiverName;
                collectDefUseFieldsInAccessedMethods(originMethod, amethod, newPrefix);
                
                if (this.isInProject() && !amethod.isInProject()) {
                    amethod.collectDefUseFieldsInAccessedMethods(originMethod,
                            newPrefix, visitedMethods, 0);
                } else {
                    amethod.collectDefUseFieldsInAccessedMethods(originMethod,
                            newPrefix, visitedMethods, count + 1);
                }
            }
        }
    }
    
    @Override
    protected boolean stopTraverse(Set<JMethod> visitedMethods, int count) {
        return (maxNumberOfChainForSourcecode > 0 && count >= maxNumberOfChainForSourcecode)
                || visitedMethods.contains(this);
    }
    
    private class CalledMethod {
        JMethod method;
        String receiverName;
        
        CalledMethod(JMethod method, String receiverName) {
            this.method = method;
            this.receiverName = receiverName;
        }
    }
}
