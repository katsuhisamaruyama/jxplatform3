/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.bytecode;

import org.jtool.srcmodel.QualifiedName;

/**
 * An element common to a class, a method, and a field
 * when following method call and field access chains.
 * 
 * @author Katsuhisa Maruyama
 */
abstract class JCommon {
    
    protected QualifiedName qname;
    
    protected BytecodeClassStore bcStore;
    
    protected JCommon(QualifiedName qname, BytecodeClassStore bcStore) {
        this.qname = qname;
        this.bcStore = bcStore;
    }
    
    public QualifiedName getQualifiedName() {
        return qname;
    }
    
    public String getClassName() {
        return qname.getClassName();
    }
    
    public String getSignature() {
        return qname.getMemberSignature();
    }
    
    abstract protected boolean isInProject();
    
    @Override
    public String toString() {
        return getQualifiedName().fqn();
    }
}
