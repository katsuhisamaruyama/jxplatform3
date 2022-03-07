/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.bytecode;

import org.jtool.srcmodel.QualifiedName;

/**
 * Concise information on a cached class.
 * 
 * @author Katsuhisa Maruyama
 */
class JClassCache extends JClass {
    
    private BytecodeClass bclass;
    
    JClassCache(BytecodeClass bclass, BytecodeClassStore bcStore) {
        super(new QualifiedName(bclass.getName(), ""), bcStore);
        
        this.bclass = bclass;
        
        this.modifiers = bclass.getModifiers();
        this.isInterface = bclass.isInterface();
        this.isInProject = bclass.isInProject();
        this.superClass = bclass.getSuperClass();
        this.superInterfaces = bclass.getSuperInterfaces();
        
        bclass.getMethods().stream()
                .forEach(signature -> this.methods.add(new JMethodCache(signature, this, bclass)));
        
        bclass.getFields().stream()
                .forEach(signature -> this.fields.add(new JFieldCache(signature, this, bclass)));
    }
    
    @Override
    protected void findSuperClassChain() {
        for (String className : bclass.getSuperClassChain()) {
            JClass clazz = bcStore.getJClass(className);
            if (clazz != null) {
                superClassChain.add(clazz);
            }
        }
    }
    
    @Override
    protected void findAncestorClasses() {
        for (String className : bclass.getAncestors()) {
            JClass clazz = bcStore.getJClass(className);
            if (clazz != null) {
                ancestors.add(clazz);
            }
        }
    }
    
    @Override
    protected void findDescendantClasses() {
        for (String className : bclass.getDescendants()) {
            JClass clazz = bcStore.getJClass(className);
            if (clazz != null) {
                descendants.add(clazz);
            }
        }
    }
}
