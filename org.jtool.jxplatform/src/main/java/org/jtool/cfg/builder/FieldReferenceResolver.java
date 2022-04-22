/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.builder;

import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGMethodEntry;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.CFGParameter;
import org.jtool.cfg.JFieldReference;
import org.jtool.cfg.JVariableReference;
import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.refmodel.BytecodeClassStore;
import org.jtool.jxplatform.refmodel.DefUseField;
import org.jtool.jxplatform.refmodel.JClass;
import org.jtool.jxplatform.refmodel.JMethod;
import org.eclipse.jdt.core.dom.ASTNode;
import java.util.List;
import java.util.Set;

/**
 * Finds fields defined and/or used in invoked methods and accessed fields.
 * 
 * @author Katsuhisa Maruyama
 */
class FieldReferenceResolver {
    
    private BytecodeClassStore bcStore;
    
    FieldReferenceResolver(JavaProject jproject) {
        this.bcStore = jproject.getCFGStore().getBCStore();
    }
    
    void findDefUseFields(CFG cfg) {
        for (CFGMethodCall callnode : cfg.getMethodCallNodes()) {
            findFieldsForCalledMethod(callnode);
        }
        
        if (cfg.isMethod()) {
            CFGParameter fout = ((CFGMethodEntry)cfg.getEntryNode()).getFormalOut();
            cfg.getStatementNodes().stream()
                .flatMap(node -> node.getDefVariables().stream())
                .filter(jv -> jv.isFieldAccess())
                .forEach(jv -> fout.addUseVariable(jv));
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
                        callNode.getActualOut().addDefVariable(fvar);
                    }
                    
                    if (!inProject) {
                        existExternalDefField = true;
                    }
                }
                
                if (existExternalDefField && callNode.hasReceiver()) {
                    List<JVariableReference> fvars = callNode.getReceiver().getUseVariables();
                    callNode.addDefVariables(fvars);
                    callNode.getActualOut().addDefVariables(fvars);
                    callNode.getActualOut().addUseVariables(fvars);
                }
                
                boolean existExternalUseField = false;
                for (DefUseField use : method.getUseFields()) {
                    boolean inProject = bcStore.findInternalClass(use.getClassName()) != null;
                    
                    if (!method.isInProject() || method.isInProject() == inProject) {
                        JVariableReference fvar = createExternalFieldReference(callNode.getASTNode(),
                                use, receiverName, callNode.getApproximatedTypeNames(), inProject);
                        callNode.addUseVariable(fvar);
                        callNode.getActualOut().addUseVariable(fvar);
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
