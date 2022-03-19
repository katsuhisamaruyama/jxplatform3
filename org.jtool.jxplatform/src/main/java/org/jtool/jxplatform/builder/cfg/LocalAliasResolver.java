/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder.cfg;

import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGMethodEntry;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.CFGReceiver;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.CFGParameter;
import org.jtool.cfg.ControlFlow;
import org.jtool.cfg.JReference;
import org.jtool.cfg.JSpecialVarReference;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaMethod;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Collection;
import java.util.HashSet;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * Resolves the alias relations in local variables in the method
 * and fields in a class enclosing the method.
 * 
 * @author Katsuhisa Maruyama
 */
class LocalAliasResolver {
    
    private Multimap<String, JReference> aliasMap = HashMultimap.create();
    
    Collection<JReference> getAliasVariables(String name) {
        return aliasMap.get(name);
    }
    
    void resolve(CFG cfg) {
        if (!cfg.isMethod()) {
            return;
        }
        
        CFGMethodEntry entry = (CFGMethodEntry)cfg.getEntryNode();
        JavaMethod jmethod = entry.getJavaMethod();
        CFGStore cfgStore = jmethod.getJavaProject().getCFGStore();
        
        Set<Alias> aliasesInFieldDeclarations = new HashSet<>();
        for (JavaField jfield : jmethod.getDeclaringClass().getFields()) {
            CFG fcfg = cfgStore.getCFG(jfield, false);
            for (CFGNode node : fcfg.getNodes()) {
                Alias alias = getAliasRelationForStatement(node);
                if (alias != null) {
                    aliasesInFieldDeclarations.add(alias);
                }
            }
        }
        
        for (CFGNode node : cfg.getNodes()) {
            aliasesInFieldDeclarations.forEach(a -> walkForward(node, a));
            Alias alias = getAliasRelationForStatement(node);
            if (alias != null) {
                aliasMap.put(alias.one.getReferenceForm(), alias.another);
                aliasMap.put(alias.another.getReferenceForm(), alias.one);
                walkForward(node, alias);
            }
        }
        
        for (CFGNode node : cfg.getNodes()) {
            if (node.isReceiver()) {
                aliasRelationForReceiver(cfg, node);
            }
        }
    }
    
    private void aliasRelationForReceiver(CFG cfg, CFGNode node) {
        CFGReceiver receiverNode = (CFGReceiver)node;
        JReference var = receiverNode.getUseFirst();
        if (var != null && !var.isExposed()) {
            String refForm = getReferenceForm(cfg, receiverNode, "");
            if (refForm.length() == 0) {
                return;
            }
            
            JReference v = new JSpecialVarReference(var.getASTNode(),
                    refForm, var.getType(), var.isPrimitiveType());
            receiverNode.addUseVariable(v);
            
            getNames(refForm)
                .forEach(name -> aliasMap.get(name)
                .forEach(alias -> {
                    String aliasRefName = refForm.replace(name, alias.getReferenceForm());
                    JReference avar = new JSpecialVarReference(alias.getASTNode(),
                            aliasRefName, alias.getType(), alias.isPrimitiveType());
                    receiverNode.addUseVariable(avar);
                }));
        }
    }
    
    private String getReferenceForm(CFG cfg, CFGReceiver receiverNode, String ref) {
        CFGMethodCall predCallNode = getPredecentMethodCall(cfg, receiverNode);
        if (predCallNode != null) {
            CFGReceiver predReceiverNode = predCallNode.getReceiver();
            if (predReceiverNode != null) {
                JReference predVar = predReceiverNode.getUseFirst();
                if (predVar != null) {
                    String preName = predVar.getReferenceForm() + "." + predCallNode.getSignature();
                    if (predVar.isExposed()) {
                        return preName;
                    } else {
                        return getReferenceForm(cfg, predReceiverNode, preName);
                    }
                }
            }
        }
        return "";
    }
    
    private CFGMethodCall getPredecentMethodCall(CFG cfg, CFGReceiver receiverNode) {
        for (CFGNode node : cfg.getNodes()) {
            if (node.isActualOut()) {
                CFGParameter actualOutNode = (CFGParameter)node;
                if (actualOutNode.getDefVariable().equals(receiverNode.getUseFirst())) {
                    CFGMethodCall callNode = (CFGMethodCall)actualOutNode.getParent();
                    if (!callNode.hasDefVariable()) {
                        return callNode;
                    }
                }
            }
        }
        return null;
    }
    
    private List<String> getNames(String name) {
        List<String> names = new ArrayList<>();
        int index = -1;
        while (true) {
            index = name.indexOf('.', index + 1);
            if (index == -1) {
                break;
            }
            names.add(name.substring(0, index));
        }
        return names;
    }
    
    private Alias getAliasRelationForStatement(CFGNode node) {
        if (!node.isStatement()) {
            return null;
        }
        
        if (!node.isAssignment() && !node.isLocalDeclaration()) {
            return null;
        }
        
        CFGStatement stNode = (CFGStatement)node;
        if (stNode.getDefVariables().size() != 1 || stNode.getUseVariables().size() != 1) {
            return null;
        }
        JReference def = stNode.getDefVariables().get(0);
        if (def.isPrimitiveType() || !def.isExposed()) {
            return null;
        }
        
        JReference use = stNode.getUseVariables().get(0);
        if (use.isMethodCall() || !use.isExposed()) {
            return null;
        }
        
        return new Alias(def, use);
    }
    
    private void walkForward(CFGNode node, Alias alias) {
        Set<CFGNode> track = new HashSet<>();
        track.add(node);
        
        for (ControlFlow flow : node.getOutgoingFlows()) {
            CFGNode succ = flow.getDstNode();
            if (!track.contains(succ)) {
                walkForward(succ, alias.one, alias.another, track);
            }
        }
        
        for (ControlFlow flow : node.getOutgoingFlows()) {
            CFGNode succ = flow.getDstNode();
            if (!track.contains(succ)) {
                walkForward(succ, alias.another, alias.one, track);
            }
        }
    }
    
    private void walkForward(CFGNode node, JReference one, JReference another, Set<CFGNode> track) {
        if (node.isStatement()) {
            CFGStatement stNode = (CFGStatement)node;
            if (stNode.defineVariable(one)) {
                return;
            } else if (stNode.useVariable(one)) {
                stNode.addUseVariable(another);
            }
        }
        
        track.add(node);
        
        for (ControlFlow flow : node.getOutgoingFlows()) {
            CFGNode succ = flow.getDstNode();
            if (!track.contains(succ)) {
                walkForward(succ, one, another, track);
            }
        }
    }
    
    private class Alias {
        
        JReference one;
        JReference another;
        
        Alias(JReference one, JReference another) {
            this.one = one;
            this.another = another;
        }
    }
}
