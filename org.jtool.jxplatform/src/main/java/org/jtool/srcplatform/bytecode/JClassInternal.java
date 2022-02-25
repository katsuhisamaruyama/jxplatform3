/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.bytecode;

import org.jtool.srcmodel.JavaClass;
import java.util.ArrayList;

/**
 * Concise information on a class inside the project.
 * 
 * @author Katsuhisa Maruyama
 */
public class JClassInternal extends JClass {
    
    private JavaClass jclass;
    
    JClassInternal(JavaClass jclass, BytecodeClassStore bcStore) {
        super(jclass.getQualifiedName(), bcStore);
        
        this.jclass = jclass;
        
        this.modifiers = jclass.getModifiers();
        this.isInterface = jclass.isInterface();
        this.isInProject = true;
        this.superClass = jclass.getSuperClassName();
        this.superInterfaces = new ArrayList<>(jclass.getSuperInterfaceNames());
        
        jclass.getMethods().stream()
                .filter(jm -> jm.getQualifiedName().isResolve())
                .forEach(jm -> this.methods.add(new JMethodInternal(jm, this)));
        
        jclass.getFields().stream()
                .filter(jf -> jf.getQualifiedName().isResolve())
                .forEach(jf -> this.fields.add(new JFieldInternal(jf, this)));
    }
    
    public JavaClass getJavaClass() {
        return jclass;
    }
    
    @Override
    protected void findSuperClassChain() {
        if (jclass.isInterface() || jclass.isEnum()) {
            return;
        }
        JavaClass parent = jclass.getSuperClass();
        if (parent == null) {
            return;
        }
        
        JClass clazz = bcStore.getJClass(parent.getQualifiedName().fqn());
        while (clazz != null) {
            superClassChain.add(clazz);
            clazz = bcStore.getJClass(clazz.getSuperClass());
        }
    }
    
    @Override
    protected void findAncestors() {
        for (JavaClass jc : jclass.getAncestors()) {
            if (jc.isClass()) {
                JClass clazz = bcStore.getJClass(jc.getQualifiedName().fqn());
                if (clazz != null) {
                    ancestors.add(clazz);
                }
            }
        }
    }
    
    @Override
    protected void findDescendants() {
        for (JavaClass jc : jclass.getDescendants()) {
            if (jc.isClass()) {
                JClass clazz = bcStore.getJClass(jc.getQualifiedName().fqn());
                if (clazz != null) {
                    descendants.add(clazz);
                }
            }
        }
    }
}
