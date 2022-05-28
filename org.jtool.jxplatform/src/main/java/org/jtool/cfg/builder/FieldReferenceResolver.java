/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.builder;

import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.JFieldReference;
import org.jtool.cfg.JVariableReference;
import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.refmodel.BytecodeClassStore;
import org.jtool.jxplatform.refmodel.DefUseField;
import org.jtool.jxplatform.refmodel.JClass;
import org.jtool.jxplatform.refmodel.JMethod;
import java.util.List;

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
            findFieldsForCalledMethod(cfg, callnode);
        }
    }
    
    private void findFieldsForCalledMethod(CFG cfg, CFGMethodCall callNode) {
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
            if (method != null && !method.getQualifiedName().equals(cfg.getQualifiedName())) {
                method.findDefUseFields(receiverName);
                
                boolean existExternalDefField = false;
                for (DefUseField def : method.getAllDefFields()) {
                    boolean isInProject = bcStore.findInternalClass(def.getClassName()) != null;
                    JVariableReference var = new JFieldReference(callNode.getASTNode(),
                            def.getClassName(), def.getName(), def.getReferenceForm(),
                            def.getType(), def.isPrimitive(), def.getModifiers(), isInProject, false);
                    callNode.addDefVariable(var);
                            
                    if (!isInProject) {
                        existExternalDefField = true;
                    }
                }
                
                if (existExternalDefField && callNode.hasReceiver()) {
                    List<JVariableReference> vars = callNode.getReceiver().getUseVariables();
                    callNode.addDefVariables(vars);
                    callNode.addUseVariables(vars);
                }
                
                boolean existExternalUseField = false;
                for (DefUseField use : method.getAllUseFields()) {
                    boolean isInProject = bcStore.findInternalClass(use.getClassName()) != null;
                    JVariableReference fvar = new JFieldReference(callNode.getASTNode(),
                            use.getClassName(), use.getName(), use.getReferenceForm(),
                            use.getType(), use.isPrimitive(), use.getModifiers(), isInProject, false);
                    callNode.addUseVariable(fvar);
                            
                    if (!isInProject) {
                        existExternalUseField = true;
                    }
                }
                
                if (existExternalUseField && callNode.hasReceiver()) {
                    callNode.addUseVariables(callNode.getReceiver().getUseVariables());
                }
            }
        }
    }
}
