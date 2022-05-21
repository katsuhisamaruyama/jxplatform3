/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.refmodel;

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
    protected void collectDefUseFields() {
        CFG cfg = bcStore.getJavaProject().getCFGStore().getCFG(jmethod, false);
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
                    .filter(var -> var.isFieldAccess() && var.getReferenceForm().startsWith("this"))
                    .forEach(var -> defFields.add(updateClassName(new DefUseField(var))));
                stNode.getUseVariables()
                    .stream()
                    .filter(var -> var.isFieldAccess() && var.getReferenceForm().startsWith("this"))
                    .forEach(var -> useFields.add(updateClassName(new DefUseField(var))));
            }
        }
    }
    
    @Override
    protected void collectAccessedMethods() {
        for (JavaMethod jm : jmethod.getCalledMethods()) {
            JMethod amethod = bcStore.getJMethod(jm.getClassName(), jm.getSignature());
            if (amethod != null && !amethod.equals(this)) {
                accessedMethods.add(amethod);
            }
        }
    }
    
    @Override
    protected boolean stopTraverse(Set<JMethod> visitedMethods, int count) {
        return (maxNumberOfChainForSourcecode > 0 && count >= maxNumberOfChainForSourcecode)
                || visitedMethods.contains(this);
    }
}
