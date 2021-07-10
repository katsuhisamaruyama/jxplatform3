/*
 *  Copyright 2021
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.bytecode;

import org.jtool.srcmodel.JavaMethod;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGStatement;
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
        if (visitedMethods.contains(this) || count >= maxNumberOfChain) {
            return;
        }
        
        visitedMethods.add(this);
        
        collectDefUseFields();
        collectAccessedMethods();
        collectDefUseFields(this, visitedMethods, visitedFields, count);
    }
    
    private void collectDefUseFields() {
        CFG cfg = bcStore.getJavaProject().getCFGStore().getCFG(jmethod, false);
        if (cfg  == null) {
            return;
        }
        
        for (CFGNode node : cfg.getNodes()) {
            if (node instanceof CFGStatement) {
                CFGStatement stNode = (CFGStatement)node;
                
                stNode.getDefVariables()
                    .stream()
                    .filter(var -> var.isFieldAccess() && var.getReferenceForm().startsWith("this"))
                    .forEach(var -> defFields.add(updateClassName(new DefUseField(var))));
                stNode.getUseVariables()
                    .stream()
                    .filter(var -> var.isFieldAccess() && var.getReferenceForm().startsWith("this"))
                    .forEach(var -> useFields.add(updateClassName(new DefUseField(var))));
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
