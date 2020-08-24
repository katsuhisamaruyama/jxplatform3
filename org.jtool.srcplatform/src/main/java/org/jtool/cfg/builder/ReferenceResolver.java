/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.builder;

import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.JFieldReference;
import org.jtool.cfg.JReference;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcplatform.bytecode.BytecodeClassStore;
import org.jtool.srcplatform.bytecode.DefUseField;
import org.jtool.srcplatform.bytecode.JField;
import org.jtool.srcplatform.bytecode.JMethod;
import org.eclipse.jdt.core.dom.ASTNode;

/**
 * Finds fields defined and/or used in invoked methods and accessed fields.
 * 
 * @author Katsuhisa Maruyama
 */
class ReferenceResolver {
    
    private JavaProject jproject;
    private BytecodeClassStore bcStore;
    
    ReferenceResolver(JavaProject jproject) {
        this.jproject = jproject;
        this.bcStore = jproject.getCFGStore().getBCStore();
    }
    
    void findDefUseFields(CFG cfg) {
        for (CFGMethodCall callnode : cfg.getMethodCallNodes()) {
            findDefUseFieldsInCalledMethod(callnode);
        }
        
        for (CFGStatement stnode : cfg.getFieldAccessNodes()) {
            for (JReference jv : stnode.getUseVariables()) {
                if (jv.isFieldAccess()) {
                    findDefUseFieldsInAccessedField(stnode, (JFieldReference)jv);
                }
            }
        }
    }
    
    private void findDefUseFieldsInCalledMethod(CFGMethodCall callnode) {
        for (String className : callnode.getReceiver().getApproximatedTypes()) {
            JavaClass jclass = jproject.getClass(className);
            if (jclass == null || !jclass.isInProject()) {
                
                JMethod method = bcStore.getJMethod(className, callnode.getSignature());
                if (method != null) {
                    method.findDefUseFields();
                    
                    for (DefUseField def : method.getDefFields()) {
                        JReference fvar = createFieldReference(callnode.getASTNode(),
                                def, callnode.getReceiver().getName());
                        callnode.addDefVariable(fvar);
                    }
                    for (DefUseField use : method.getUseFields()) {
                        JReference fvar = createFieldReference(callnode.getASTNode(),
                                use, callnode.getReceiver().getName());
                        callnode.addUseVariable(fvar);
                    }
                }
            }
        }
    }
    
    private void findDefUseFieldsInAccessedField(CFGStatement stnode, JFieldReference jfacc) {
        String className = jfacc.getDeclaringClassName();
        JavaClass jclass = jproject.getClass(className);
        if (jclass == null || !jclass.isInProject()) {
            
            JField field = bcStore.getJField(className, jfacc.getName());
            if (field != null) {
                field.findDefUseFields();
                
                for (DefUseField use : field.getUseFields()) {
                    JReference fvar = createFieldReference(jfacc.getASTNode(),
                            use, jfacc.getReceiverName());
                    stnode.addUseVariable(fvar);
                }
            }
        }
    }
    
    private JReference createFieldReference(ASTNode node, DefUseField var, String receiverForm) {
        String referenceForm = var.getReferenceForm();
        if (referenceForm.startsWith("this")) {
            referenceForm = receiverForm + "." + var.getName();
        }
        
        boolean inProject;
        if (bcStore.findInternalClass(var.getClassName()) != null) {
            inProject = true;
        } else {
            inProject = false;
        }
        
        return new JFieldReference(node, var.getClassName(), var.getName(), referenceForm,
                var.getType(), var.isPrimitive(), var.getModifier(), inProject, false);
    }
}
