/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.bytecode;

import org.jtool.srcmodel.JavaMethod;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.JReference;
import java.util.Set;

/**
 * Concise information on a method inside the project.
 * 
 * @author Katsuhisa Maruyama
 */
class JMethodInternal extends JMethod {
    
    JMethodInternal(JavaMethod jmethod, JClass declaringClass) {
        super(jmethod.getQualifiedName(), declaringClass);
        
        collectDefUseFields(jmethod);
        collectAccessedMethods(jmethod);
        collectOverridingMethods(jmethod);
    }
    
    private void collectDefUseFields(JavaMethod jmethod) {
        CFG cfg = bcStore.getJavaProject().getCFGStore().getCFGWithoutResolvingMethodCalls(jmethod);
        
        for (CFGNode node : cfg.getNodes()) {
            if (node instanceof CFGStatement) {
                CFGStatement stNode = (CFGStatement)node;
                for (JReference var : stNode.getDefVariables()) {
                    if (var.isFieldAccess()) {
                        defFields.add(new DefUseField(var));
                    }
                }
                for (JReference var : stNode.getUseVariables()) {
                    if (var.isFieldAccess()) {
                        useFields.add(new DefUseField(var));
                    }
                }
            }
        }
    }
    
    private void collectAccessedMethods(JavaMethod jmethod) {
        for (JavaMethod jm : jmethod.getCalledMethods()) {
            JMethod method;
            if (jm.getDeclaringClass().getClassName().equals(declaringClass.getClassName())) {
                method = declaringClass.getMethod(jm.getSignature());
            } else {
                method = bcStore.getJMethod(jm.getClassName(), jm.getSignature());
            }
            if (method != null) {
                accessedMethods.add(method);
            }
        }
    }
    
    private void collectOverridingMethods(JavaMethod jmethod) {
        if (jmethod.isConstructor()) {
            return;
        }
        
        JClass clazz;
        if (jmethod.getDeclaringClass().getClassName().equals(declaringClass.getClassName())) {
            clazz = declaringClass;
        } else {
            clazz = bcStore.getJClass(jmethod.getClassName());
        }
        for (JClass descendant : clazz.getDescendants()) {
            JMethod method = descendant.getMethod(jmethod.getSignature());
            if (method != null) {
                overridingMethods.add(method);
            }
        }
    }
    
    @Override
    public boolean isInProject() {
        return true;
    }
    
    @Override
    protected void findDefUseFields(Set<JMethod> visitedMethods, Set<JField> visitedFields, int count) {
        if (isDefUseDecided || visitedMethods.contains(this)) {
            return;
        }
        
        isDefUseDecided = true;
        visitedMethods.add(this);
        
        collectDefUseFields(visitedMethods, visitedFields, count);
    }
}
