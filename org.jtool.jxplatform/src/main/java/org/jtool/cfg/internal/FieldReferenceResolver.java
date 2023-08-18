/*
 *  Copyright 2022-2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal;

import org.jtool.cfg.CFG;
import org.jtool.cfg.ControlFlow;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGParameter;
import org.jtool.cfg.JVariableReference;
import org.jtool.cfg.JUncoveredFieldReference;
import org.jtool.cfg.internal.refmodel.DefUseField;
import org.jtool.cfg.internal.refmodel.JClass;
import org.jtool.cfg.internal.refmodel.JMethod;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

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
                method.findDefUseFields(receiverName, callNode.getReturnValueName());
                
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
        
        Collection<DefUseField> defs = aggregateUncoveredFields(method.getAllDefFields());
        List<DefUseField> sortedDefs = defs.stream()
                .sorted(Comparator.comparing(DefUseField::getQualifiedName))
                .collect(Collectors.toList());
        
        DominantStatement dominantStatement = null;
        
        for (DefUseField def : sortedDefs) {
            JVariableReference var = new JUncoveredFieldReference(callNode.getASTNode(),
                    def.getClassName(), def.getName(), def.getReferenceForm(),
                    def.getType(), def.isPrimitive(), def.getModifiers(), def.isInProject(),
                    def.getHoldingNodes());
            
            if (def.isInProject()) {
                CFGParameter actualOut = 
                        new CFGParameter(callNode.getASTNode(), CFGNode.Kind.actualOutByFieldAccess, index);
                actualOut.setParent(callNode);
                actualOut.addDefVariable(var);
                cfg.add(actualOut);
                
                if (dominantStatement == null) {
                    dominantStatement = cfg.findDominantStatement(callNode);
                }
                dominantStatement.addImmediatePostDominator(actualOut);
                
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
        Collection<DefUseField> uses = aggregateUncoveredFields(method.getAllUseFields());
        List<DefUseField> sortedUses = uses.stream()
                .sorted(Comparator.comparing(DefUseField::getQualifiedName))
                .collect(Collectors.toList());
        
        for (DefUseField use : sortedUses) {
            JVariableReference var = new JUncoveredFieldReference(callNode.getASTNode(),
                    use.getClassName(), use.getName(), use.getReferenceForm(),
                    use.getType(), use.isPrimitive(), use.getModifiers(), use.isInProject(),
                    use.getHoldingNodes());
            
            callNode.addUseVariable(var);
            callNode.getActualOut().addUseVariable(var);
        }
    }
    
    private Collection<DefUseField> aggregateUncoveredFields(List<DefUseField> fields) {
        Map<String, DefUseField> fmap = new HashMap<>();
        for (DefUseField field : fields) {
            DefUseField f = fmap.get(field.getQualifiedName());
            if (f != null) {
                f.addHoldingNodes(field.getHoldingNodes());
            } else {
                fmap.put(field.getQualifiedName(), field);
            }
        }
        return fmap.values();
    }
}
