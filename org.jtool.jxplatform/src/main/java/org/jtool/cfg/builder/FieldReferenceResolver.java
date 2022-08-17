/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.builder;

import org.jtool.cfg.CFG;
import org.jtool.cfg.ControlFlow;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGParameter;
import org.jtool.cfg.JVariableReference;
import org.jtool.cfg.JComplementaryFieldReference;
import org.jtool.jxplatform.refmodel.DefUseField;
import org.jtool.jxplatform.refmodel.JClass;
import org.jtool.jxplatform.refmodel.JMethod;

/**
 * Finds fields defined and/or used in invoked methods and accessed fields.
 * 
 * @author Katsuhisa Maruyama
 */
class FieldReferenceResolver {
    
    FieldReferenceResolver() {
    }
    
    void findDefUseFields(CFG cfg) {
        for (CFGMethodCall callNode : cfg.getMethodCallNodes()) {
            findFieldsForCalledMethod(cfg, callNode);
        }
    }
    
    private void findFieldsForCalledMethod(CFG cfg, CFGMethodCall callNode) {
        if (callNode.getApproximatedTypes() == null) {
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
                method.findDefUseFields(callNode, receiverName);
                
                insertDefVariables(callNode, method, cfg);
                insertUseVariables(callNode, method);
            }
        }
    }
    
    private void insertDefVariables(CFGMethodCall callNode, JMethod method, CFG cfg) {
        ControlFlow removeFlow = callNode.getOutgoingTrueFlow();
        if (removeFlow != null) {
            cfg.remove(removeFlow);
        }
        
        CFGNode curNode = callNode;
        int index = 1;
        for (DefUseField def : method.getAllDefFields()) {
            JVariableReference var = new JComplementaryFieldReference(callNode.getASTNode(),
                    def.getClassName(), def.getName(), def.getReferenceForm(),
                    def.getType(), def.isPrimitive(), def.getModifiers(), def.isInProject(),
                    def.getHoldingNodes());
            
            if (def.isInProject()) {
                CFGParameter actualOut =
                        new CFGParameter(callNode.getASTNode(), CFGNode.Kind.actualOut, index);
                actualOut.setParent(callNode);
                actualOut.addDefVariable(var);
                cfg.add(actualOut);
                
                ControlFlow flow = new ControlFlow(curNode, actualOut);
                flow.setTrue();
                cfg.add(flow);
                
                curNode = actualOut;
                index++;
                
            } else {
                callNode.getActualOut().addDefVariable(var);
            }
        }
        
        ControlFlow flow = new ControlFlow(curNode, callNode.getActualOut());
        flow.setTrue();
        cfg.add(flow);
    }
    
    private void insertUseVariables(CFGMethodCall callNode, JMethod method) {
        for (DefUseField use : method.getAllUseFields()) {
            JVariableReference var = new JComplementaryFieldReference(callNode.getASTNode(),
                    use.getClassName(), use.getName(), use.getReferenceForm(),
                    use.getType(), use.isPrimitive(), use.getModifiers(), use.isInProject(),
                    use.getHoldingNodes());
            callNode.addUseVariable(var);
            callNode.getActualOut().addUseVariable(var);
        }
    }
}
