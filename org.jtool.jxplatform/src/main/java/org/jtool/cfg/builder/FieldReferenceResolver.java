/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.builder;

import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.JFieldReference;
import org.jtool.cfg.JVariableReference;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.refmodel.BytecodeClassStore;
import org.jtool.jxplatform.refmodel.DefUseField;
import org.jtool.jxplatform.refmodel.JClass;
import org.jtool.jxplatform.refmodel.JMethod;
import org.eclipse.jdt.core.dom.ASTNode;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Finds fields defined and/or used in invoked methods and accessed fields.
 * 
 * @author Katsuhisa Maruyama
 */
class FieldReferenceResolver {
    
    private JavaProject jproject;
    private BytecodeClassStore bcStore;
    
    FieldReferenceResolver(JavaProject jproject) {
        this.jproject = jproject;
        this.bcStore = jproject.getCFGStore().getBCStore();
    }
    
    void findDefUseFields(CFG cfg) {
        for (CFGMethodCall callnode : cfg.getMethodCallNodes()) {
            findFieldsForCalledMethod(callnode);
        }
        
        Set<JVariableReference> defFields = cfg.getStatementNodes().stream()
                .flatMap(node -> node.getDefVariables().stream())
                .filter(jv -> jv.isFieldAccess())
                .collect(Collectors.toSet());
        
        for (CFGStatement stnode : cfg.getReturnNodes()) {
            if (stnode.getDefVariables().size() > 0) {
                String type = stnode.getDefFirst().getType();
                for (JVariableReference jv : new ArrayList<>(stnode.getUseVariables())) {
                    if (!jv.isPrimitiveType() && !type.equals("java.lang.String")) {
                        findFieldsForReturn(stnode, jv, type, defFields);
                    }
                }
            }
        }
    }
    
    private void findFieldsForCalledMethod(CFGMethodCall callNode) {
        if (callNode.getApproximatedTypes() == null) {
            System.err.println("CALL = " + callNode.getMethodCall().getEnclosingClassName() + " " + callNode.getASTNode());
            return;
        }
        
        String receiverName = "";
        if (callNode.hasReceiver()) {
            receiverName = callNode.getReceiver().getName();
        } else {
            if (callNode.getMethodCall().isEnumConstant()) {
                receiverName = callNode.getDeclaringClassName();
            }
        }
        
        for (JClass type : callNode.getApproximatedTypes()) {
            String sig = callNode.getSignature();
            
            if (callNode.isConstructorCall()) {
                sig = type.getSimpleName() + sig.substring(sig.lastIndexOf('('));
            }
            
            JMethod method = type.getMethod(sig);
            if (method != null) {
                method.findDefUseFields();
                
                boolean existExternalDefField = false;
                for (DefUseField def : method.getDefFields()) {
                    boolean inProject = bcStore.findInternalClass(def.getClassName()) != null;
                    
                    if (!method.isInProject() || method.isInProject() == inProject) {
                        JVariableReference fvar = createExternalFieldReference(callNode.getASTNode(),
                                def, receiverName, callNode.getApproximatedTypeNames(), inProject);
                        callNode.addDefVariable(fvar);
                    }
                    
                    if (!inProject) {
                        existExternalDefField = true;
                    }
                }
                
                if (existExternalDefField && callNode.hasReceiver()) {
                    List<JVariableReference> fvars = callNode.getReceiver().getUseVariables();
                    callNode.addDefVariables(fvars);
                    callNode.getActualOutForReturn().addDefVariables(fvars);
                    callNode.getActualOutForReturn().addUseVariables(fvars);
                }
                
                boolean existExternalUseField = false;
                for (DefUseField use : method.getUseFields()) {
                    boolean inProject = bcStore.findInternalClass(use.getClassName()) != null;
                    
                    if (!method.isInProject() || method.isInProject() == inProject) {
                        JVariableReference fvar = createExternalFieldReference(callNode.getASTNode(),
                                use, receiverName, callNode.getApproximatedTypeNames(), inProject);
                        callNode.addUseVariable(fvar);
                    }
                    
                    if (!inProject) {
                        existExternalUseField = true;
                    }
                }
                
                if (existExternalUseField && callNode.hasReceiver()) {
                    callNode.addUseVariables(callNode.getReceiver().getUseVariables());
                }
            }
        }
    }
    
    private void findFieldsForReturn(CFGStatement stnode, JVariableReference jv, String type,
            Set<JVariableReference> defFields) {
        JavaClass jclass = jproject.getClass(type);
        if (jclass == null) {
            jclass = jproject.getExternalClass(type);
        }
        
        if (jclass != null) {
            for (JavaField jfield : jclass.getFields()) {
                String referenceForm = jv.getReferenceForm() + "." + jfield.getName();
                if (defFields.stream().anyMatch(v -> v.getReferenceForm().equals(referenceForm))) {
                    JVariableReference fvar = new JFieldReference(jv.getASTNode(),
                            jfield.getClassName(), jfield.getName(), referenceForm,
                            jfield.getType(), jfield.isPrimitiveType(), jfield.getModifiers(),
                            jclass.isInProject(), false);
                    stnode.addUseVariable(fvar);
                }
            }
        }
    }
    
    private JVariableReference createExternalFieldReference(ASTNode node, DefUseField var,
            String receiverName, Set<String> receiverTypes, boolean inProject) {
        String referenceForm = var.getReferenceForm();
        if (inProject) {
            if (referenceForm.startsWith("this")) {
                referenceForm = referenceForm.replace("this", receiverName);
            }
        } else {
            for (String receiverType : receiverTypes) {
                referenceForm = referenceForm.replace(receiverType, receiverName);
            }
        }
        return new JFieldReference(node, var.getClassName(), var.getName(), referenceForm,
                var.getType(), var.isPrimitive(), var.getModifier(), inProject, false);
    }
}
