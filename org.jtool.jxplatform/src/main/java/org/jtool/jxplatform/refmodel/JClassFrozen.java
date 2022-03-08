/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.refmodel;

import org.jtool.srcmodel.QualifiedName;

/**
 * Concise information on a class with frozen data extracted from a library file or a cache file.
 * 
 * @author Katsuhisa Maruyama
 */
class JClassFrozen extends JClass {
    
    private BytecodeClass bclass;
    
    JClassFrozen(BytecodeClass bclass, BytecodeClassStore bcStore) {
        super(new QualifiedName(bclass.getName(), ""), bcStore);
        
        this.bclass = bclass;
        
        this.modifiers = bclass.getModifiers();
        this.isInterface = bclass.isInterface();
        this.isInProject = bclass.isInProject();
        this.superClass = bclass.getSuperClass();
        this.superInterfaces = bclass.getSuperInterfaces();
    }
    
    @Override
    protected void findSuperClassChain() {
        for (String className : bclass.getSuperClassChain()) {
            JClass clazz = bcStore.getJClass(className);
            if (clazz != null && !clazz.isInterface) {
                superClassChain.add(clazz);
            }
        }
    }
    
    @Override
    protected void findAncestorClasses() {
        for (String className : bclass.getAncestors()) {
            JClass clazz = bcStore.getJClass(className);
            if (clazz != null && !clazz.isInterface && !ancestors.contains(clazz)) {
                ancestors.add(clazz);
            }
        }
    }
    
    @Override
    protected void findDescendantClasses() {
        for (String className : bclass.getDescendants()) {
            JClass clazz = bcStore.getJClass(className);
            if (clazz != null && !clazz.isInterface && !descendants.contains(clazz)) {
                descendants.add(clazz);
            }
        }
    }
}
