/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.refmodel;

import org.jtool.srcmodel.QualifiedName;

/**
 * Concise information on a class outside the project.
 * 
 * @author Katsuhisa Maruyama
 */
public class JClassExternal extends JClass {
    
    private BytecodeClass bclass;
    
    JClassExternal(BytecodeClass bclass, BytecodeClassStore bcStore) {
        super(new QualifiedName(bclass.getName(), ""), bcStore);
        
        this.bclass = bclass;
        
        this.modifiers = bclass.getModifiers();
        this.isInterface = bclass.isInterface();
        this.isInProject = false;
        this.superClass = bclass.getSuperClass();
        this.superInterfaces = bclass.getSuperInterfaces();
        
        bclass.getMethods().stream()
                .forEach(sig -> this.methods.add(new JMethodExternal(sig, this, bclass)));
        
        bclass.getFields().stream()
            .forEach(sig -> this.fields.add(new JFieldExternal(sig, this)));
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
