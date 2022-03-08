/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.refmodel;

import org.jtool.srcmodel.QualifiedName;
import java.util.Collection;

/**
 * An element common to classes, methods, and fields with concise information.
 * 
 * @author Katsuhisa Maruyama
 */
abstract class JCommon {
    
    protected QualifiedName qname;
    
    protected BytecodeClassStore bcStore;
    
    protected static int maxNumberOfChainForSourcecode;
    protected static int maxNumberOfChainForBytecode;
    
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
    
    abstract public boolean isInProject();
    
    protected DefUseField updateClassName(DefUseField var) {
        JField field = bcStore.getJField(var.getClassName(), var.getName());
        if (field != null) {
            var.updateClassName(field.getClassName());
        }
        return var;
    }
    
    protected Collection<DefUseField> updateClassName(Collection<DefUseField> vars) {
        vars.forEach(var -> updateClassName(var));
        return vars;
    }
    
    @Override
    public String toString() {
        return getQualifiedName().fqn();
    }
}
