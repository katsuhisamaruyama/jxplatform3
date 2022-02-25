/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.bytecode;

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
            if (clazz != null) {
                superClassChain.add(clazz);
            }
        }
    }
    
    @Override
    protected void findAncestors() {
        for (String className : bclass.getAncestors()) {
            JClass clazz = bcStore.getJClass(className);
            if (clazz != null) {
                ancestors.add(clazz);
            }
        }
    }
    
    @Override
    protected void findDescendants() {
        for (String className : bclass.getDescendants()) {
            JClass clazz = bcStore.getJClass(className);
            if (clazz != null) {
                descendants.add(clazz);
            }
        }
    }
}
