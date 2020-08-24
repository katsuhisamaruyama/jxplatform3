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
import org.jtool.cfg.JReference;
import java.util.Set;

/**
 * Concise information on a field inside the project.
 * 
 * @author Katsuhisa Maruyama
 */
class JFieldInternal extends JField {
    
    JFieldInternal(JavaField jfield, JClass declaringClass) {
        super(jfield.getQualifiedName(), declaringClass);
        
        collectDefUseFields(jfield);
        collectAccessedMethods(jfield);
    }
    
    private void collectDefUseFields(JavaField jfield) {
        CFG cfg = bcStore.getJavaProject().getCFGStore().getCFG(jfield, false);
        
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
    
    private void collectAccessedMethods(JavaField jfield) {
        for (JavaMethod jm : jfield.getCalledMethods()) {
            JMethod method = bcStore.getJMethod(jm.getClassName(), jm.getSignature());
            if (method != null) {
                accessedMethods.add(method);
            }
        }
    }
    
    @Override
    public boolean isInProject() {
        return true;
    }
    
    @Override
    protected void findDefUseFields(Set<JMethod> visitedMethods, Set<JField> visitedFields, int count) {
        if (isDefUseDecided || visitedFields.contains(this)) {
            return;
        }
        
        isDefUseDecided = true;
        visitedFields.add(this);
        
        collectDefUseFields(visitedMethods, visitedFields, count);
    }
}
