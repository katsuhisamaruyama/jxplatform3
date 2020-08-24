/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.bytecode;

import org.jtool.srcmodel.QualifiedName;
import java.util.Set;

/**
 * Concise information on a cached method outside the project.
 * 
 * @author Katsuhisa Maruyama
 */
class JMethodCache extends JMethod {
    
    JMethodCache(String signature, JClass declaringClass, BytecodeClass bclass) {
        super(new QualifiedName(declaringClass.getClassName(), signature), declaringClass);
        
        defFields.addAll(bclass.getDefFields(signature));
        useFields.addAll(bclass.getUseFields(signature));
        
        isDefUseDecided = true;
    }
    
    @Override
    protected boolean isInProject() {
        return false;
    }
    
    @Override
    protected void findDefUseFields(Set<JMethod> visitedMethods, Set<JField> visitedFields, int count) {
    }
}
