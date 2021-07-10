/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.bytecode;

import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGStatement;
import java.util.Set;

/**
 * Concise information on a field inside the project.
 * 
 * @author Katsuhisa Maruyama
 */
class JFieldInternal extends JField {
    
    private JavaField jfield;
    
    JFieldInternal(JavaField jfield, JClass declaringClass) {
        super(jfield.getQualifiedName(), declaringClass);
        
        this.jfield = jfield;
    }
    
    public JavaField getJavaField() {
        return jfield;
    }
    
    @Override
    protected void findDefUseFields(Set<JMethod> visitedMethods, Set<JField> visitedFields, int count) {
        if (visitedFields.contains(this) || count >= maxNumberOfChain) {
            return;
        }
        
        visitedFields.add(this);
        
        collectDefUseFields();
        collectAccessedMethods();
        collectDefUseFields(this, visitedMethods, visitedFields, count);
    }
    
    private void collectDefUseFields() {
        CFG cfg = bcStore.getJavaProject().getCFGStore().getCFGWithoutResolvingMethodCalls(jfield);
        if (cfg == null) {
            return;
        }
        
        for (CFGNode node : cfg.getNodes()) {
            if (node instanceof CFGStatement) {
                CFGStatement stNode = (CFGStatement)node;
                
                stNode.getDefVariables().stream()
                    .filter(var -> var.isFieldAccess())
                    .forEach(var -> defFields.add(new DefUseField(var)));
                stNode.getUseVariables().stream()
                    .filter(var -> var.isFieldAccess())
                    .forEach(var -> useFields.add(new DefUseField(var)));
            }
        }
    }
    
    private void collectAccessedMethods() {
        for (JavaMethod jm : jfield.getCalledMethods()) {
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
}
