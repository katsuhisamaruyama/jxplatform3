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
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcplatform.bytecode.BytecodeClassStore;
import org.jtool.srcplatform.bytecode.DefUseField;
import org.jtool.srcplatform.bytecode.JMethod;
import org.jtool.srcplatform.bytecode.JField;
import org.eclipse.jdt.core.dom.ASTNode;
import java.util.ArrayList;

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
            findFieldsForCalledMethod(callnode);
        }
        
        for (CFGStatement stnode : cfg.getFieldAccessNodes()) {
            for (JReference jv : stnode.getUseVariables()) {
                if (jv.isFieldAccess()) {
                    findFieldsForAccessedField(stnode, (JFieldReference)jv);
                }
            }
        }
        
        for (CFGStatement stnode : cfg.getReturnNodes()) {
            String type = stnode.getDefVariables().get(0).getType();
            for (JReference jv : new ArrayList<>(stnode.getUseVariables())) {
                if (!jv.isPrimitiveType() && !type.equals("java.lang.String")) {
                    findFieldsForReturn(stnode, jv, type);
                }
            }
        }
    }
    
    private void findFieldsForCalledMethod(CFGMethodCall callnode) {
        if (callnode.getApproximatedTypes() == null) {
            System.err.println("CALL = " + callnode.getMethodCall().getEnclosingClassName() + " " + callnode.getASTNode());
            return;
        }
        
        String receiverName = "";
        if (callnode.hasReceiver()) {
            receiverName = callnode.getReceiver().getName();
        }
        
        for (String className : callnode.getApproximatedTypes()) {
            JMethod method = bcStore.getJMethod(className, callnode.getSignature());
            if (method != null) {
                method.findDefUseFields();
                
                boolean existExternalDefField = false;
                for (DefUseField def : method.getDefFields()) {
                    JReference fvar = createFieldReference(callnode.getASTNode(), def, receiverName);
                    callnode.addDefVariable(fvar);
                    if (!fvar.isInProject()) {
                        existExternalDefField = true;
                    }
                }
                if (existExternalDefField && callnode.hasReceiver()) {
                    callnode.addDefVariables(callnode.getReceiver().getUseVariables());
                }
                
                boolean existExternalUseField = false;
                for (DefUseField use : method.getUseFields()) {
                    JReference fvar = createFieldReference(callnode.getASTNode(), use, receiverName);
                    callnode.addUseVariable(fvar);
                    if (!fvar.isInProject()) {
                        existExternalUseField = true;
                    }
                }
                if (existExternalUseField && callnode.hasReceiver()) {
                    callnode.addUseVariables(callnode.getReceiver().getUseVariables());
                }
            }
        }
    }
    
    private void findFieldsForAccessedField(CFGStatement stnode, JFieldReference jfacc) {
        String className = jfacc.getDeclaringClassName();
        JavaClass jclass = jproject.getClass(className);
        if (jclass != null && jclass.isInProject()) {
            
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
    
    private void findFieldsForReturn(CFGStatement stnode, JReference jv, String type) {
        JavaClass jclass = jproject.getClass(type);
        if (jclass == null) {
            jclass = jproject.getExternalClass(type);
        }
        
        if (jclass != null) {
            for (JavaField jfield : jclass.getFields()) {
                String referenceForm = jv.getReferenceForm() + "." + jfield.getName();
                JReference fvar = new JFieldReference(jv.getASTNode(),
                        jfield.getClassName(), jfield.getName(), referenceForm,
                        jfield.getType(), jfield.isPrimitiveType(), jfield.getModifiers(),
                        jclass.isInProject(), false);
                stnode.addUseVariable(fvar);
            }
        }
    }
    
    private JReference createFieldReference(ASTNode node, DefUseField var, String receiverForm) {
        String referenceForm = var.getReferenceForm();
        if (referenceForm.startsWith("this")) {
            referenceForm = receiverForm + "." + var.getName();
        }
        
        boolean inProject = bcStore.findInternalClass(var.getClassName()) != null;
        return new JFieldReference(node, var.getClassName(), var.getName(), referenceForm,
                var.getType(), var.isPrimitive(), var.getModifier(), inProject, false);
    }
}
