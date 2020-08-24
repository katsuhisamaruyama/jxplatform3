/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.bytecode;

import org.jtool.srcmodel.QualifiedName;

/**
 * Concise information on a cached class outside the project.
 * 
 * @author Katsuhisa Maruyama
 */
class JClassCache extends JClass {
    
    JClassCache(BytecodeClass bclass, BytecodeClassStore bcStore) {
        super(new QualifiedName(bclass.getName()), bcStore);
        
        this.modifiers = bclass.getModifiers();
        this.isInterface = bclass.isInterface();
        
        for (String signature : bclass.getMethods()) {
            methods.add(new JMethodCache(signature, this, bclass));
        }
    }
    
    @Override
    protected boolean isInProject() {
        return false;
    }
    
    @Override
    protected void findSuperClassChain() {
    }
    
    @Override
    protected void findAncestors() {
    }
    
    @Override
    protected void findDescendants() {
    }
}
