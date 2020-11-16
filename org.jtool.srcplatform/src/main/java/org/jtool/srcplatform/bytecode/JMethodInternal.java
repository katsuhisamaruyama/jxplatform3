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
    
    private JavaMethod jmethod;
    
    JMethodInternal(JavaMethod jmethod, JClass declaringClass) {
        super(jmethod.getQualifiedName(), declaringClass);
        
        this.jmethod = jmethod;
    }
    
    public JavaMethod getJavaMethod() {
        return jmethod;
    }
    
    @Override
    protected void findDefUseFields(Set<JMethod> visitedMethods, Set<JField> visitedFields, int count) {
        if (visitedMethods.contains(this) || count > maxNumberOfChain) {
            return;
        }
        
        visitedMethods.add(this);
        
        collectDefUseFields();
        
        collectAccessedMethods();
        
        collectDefUseFields(this, visitedMethods, visitedFields, count);
    }
    
    private void collectDefUseFields() {
        CFG cfg = bcStore.getJavaProject().getCFGStore().getCFGWithoutResolvingMethodCalls(jmethod);
        if (cfg  == null) {
            return;
        }
        
        for (CFGNode node : cfg.getNodes()) {
            if (node instanceof CFGStatement) {
                CFGStatement stNode = (CFGStatement)node;
                for (JReference var : stNode.getDefVariables()) {
                    if (var.isFieldAccess() && var.getReferenceForm().startsWith("this")) {
                        defFields.add(updateClassName(new DefUseField(var)));
                    }
                }
                for (JReference var : stNode.getUseVariables()) {
                    if (var.isFieldAccess() && var.getReferenceForm().startsWith("this")) {
                        useFields.add(updateClassName(new DefUseField(var)));
                    }
                }
            }
        }
    }
    
    private void collectAccessedMethods() {
        for (JavaMethod jm : jmethod.getCalledMethods()) {
            JMethod method = bcStore.getJMethod(jm.getClassName(), jm.getSignature());
            if (method != null) {
                accessedMethods.add(method);
            }
        }
    }
}
