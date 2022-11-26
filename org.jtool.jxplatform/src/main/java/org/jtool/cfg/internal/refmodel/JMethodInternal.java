/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal.refmodel;

import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.JFieldReference;
import org.jtool.srcmodel.JavaMethod;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

/**
 * Concise information on a method inside the project.
 * 
 * @author Katsuhisa Maruyama
 */
class JMethodInternal extends JMethod {
    
    private JavaMethod jmethod;
    private CFG cfg;
    
    JMethodInternal(JavaMethod jmethod, JClass declaringClass) {
        super(jmethod.getQualifiedName(), declaringClass);
        
        this.jmethod = jmethod;
    }
    
    @Override
    protected void destroy() {
        super.destroy();
        jmethod = null;
        cfg = null;
    }
    
    @Override
    protected void collectDefUseFieldsInThisMethod() {
        if (!isDefUseCollected) {
            collectDefUseFields();
            isDefUseCollected = true;
        }
    }
    
    private void collectDefUseFields() {
        cfg = bcStore.getJavaProject().getCFGStore().getCFG(jmethod, false);
        if (cfg  == null) {
            return;
        }
        
        for (CFGNode node : cfg.getNodes()) {
            if (node.isStatement()) {
                CFGStatement cfgnode = (CFGStatement)node;
                if (cfgnode.isFormal()) {
                    continue;
                }
                
                cfgnode.getDefVariables().stream()
                        .filter(var -> var.isFieldAccess())
                        .map(var -> new DefUseField((JFieldReference)var, cfgnode))
                        .forEach(var -> {
                            defFields.add(updateClassName(var));
                        });
                
                cfgnode.getUseVariables().stream()
                        .filter(var -> var.isFieldAccess())
                        .map(var -> new DefUseField((JFieldReference)var, cfgnode))
                        .forEach(var -> {
                            useFields.add(updateClassName(var));
                        });
            }
        }
    }
    
    @Override
    protected void collectDefUseFieldsInAccessedMethods(JMethod originMethod,
            String prefix, String returnValue, List<CFGMethodCall> callChain) {
        for (DefUseField var : defFields) {
            if (!var.isUncovered()) {
                DefUseField def = new DefUseField(var);
                def.updateReferenceForm(getReferenceForm(var, prefix, returnValue));
                def.addHoldingNodes(callChain);
                
                originMethod.allDefFields.add(def);
            }
        }
        
        for (DefUseField var : useFields) {
            if (!var.isUncovered()) {
                DefUseField use = new DefUseField(var);
                use.updateReferenceForm(getReferenceForm(var, prefix, returnValue));
                use.addHoldingNodes(callChain);
                
                originMethod.allUseFields.add(use);
            }
        }
    }
    
    private String getReferenceForm(DefUseField var, String prefix, String returnValue) {
        if (jmethod.isConstructor()) {
            if (var.getReferenceForm().startsWith("this.") && var.isThis()) {
                if (isThisInvocation(prefix)) {
                    return var.getReferenceForm();
                } else if (isSuperInvocation(prefix)) {
                    return var.getReferenceForm().replace("this.", "super.");
                } else {
                    return var.getReferenceForm().replace("this.", returnValue + ".");
                }
            }
        } else {
            if (var.getReferenceForm().startsWith("this.") && var.isThis()) {
                if (prefix.length() == 0) {
                    return var.getReferenceForm();
                } else {
                    return var.getReferenceForm().replace("this.", prefix + ".");
                }
            }
        }
        return var.getReferenceForm();
    }
    
    private boolean isThisInvocation(String prefix) {
        return prefix.endsWith("this");
    }
    
    private boolean isSuperInvocation(String prefix) {
        return prefix.endsWith("super");
    }
    
    @Override
    protected void traverseAccessedMethods(JMethod originMethod,
            String prefix, String returnValue, List<CFGMethodCall> callChain,
            Set<JMethod> visitedMethods, int count) {
        for (CalledMethod cmethod : getCalledMethods()) {
            JMethod amethod = cmethod.method;
            if (!amethod.isInProject() || !amethod.stopTraverse(visitedMethods, count + 1)) {
                visitedMethods.add(amethod);
                
                amethod.collectDefUseFieldsInThisMethod();
                
                String receiverName = cmethod.node.getReceiver().getName();
                String newPrefix;
                if (receiverName.startsWith("this")) {
                    newPrefix = receiverName.replace("this.", prefix + ".");
                } else {
                    newPrefix = prefix + "." + receiverName;
                }
                
                List<CFGMethodCall> newCallChain = new ArrayList<>(callChain);
                newCallChain.add(cmethod.node);
                
                amethod.collectDefUseFieldsInAccessedMethods(originMethod, newPrefix, returnValue, newCallChain);
                
                int nextCount = amethod.isInProject() ? count + 1 : 0;
                amethod.traverseAccessedMethods(originMethod, newPrefix, returnValue, newCallChain,
                        visitedMethods, nextCount);
            }
        }
    }
    
    @Override
    protected boolean stopTraverse(Set<JMethod> visitedMethods, int count) {
        return (maxNumberOfChainForSourcecode > 0 && count >= maxNumberOfChainForSourcecode)
                || visitedMethods.contains(this);
    }
    
    private List<CalledMethod> getCalledMethods() {
        List<CalledMethod> calledMethods = new ArrayList<>();
        if (cfg  == null) {
            return calledMethods;
        }
        
        for (CFGMethodCall callNode : cfg.getMethodCallNodes()) {
            for (JClass type : callNode.getApproximatedTypes()) {
                String sig = callNode.getSignature();
                if (callNode.isConstructorCall()) {
                    sig = type.getSimpleName() + sig.substring(sig.lastIndexOf('('));
                }
                
                JMethod method = type.getMethod(sig);
                if (method != null && !method.equals(this)) {
                    accessedMethods.add(method);
                    
                    CalledMethod amethod = new CalledMethod(method, callNode);
                    calledMethods.add(amethod);
                }
            }
        }
        return calledMethods;
    }
    
    private class CalledMethod {
        JMethod method;
        CFGMethodCall node;
        
        CalledMethod(JMethod method, CFGMethodCall node) {
            this.method = method;
            this.node = node;
        }
    }
}
