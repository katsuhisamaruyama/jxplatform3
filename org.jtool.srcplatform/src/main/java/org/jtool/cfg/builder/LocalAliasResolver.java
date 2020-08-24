/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.builder;

import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGMethodEntry;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.ControlFlow;
import org.jtool.cfg.JReference;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaMethod;
import java.util.Set;
import java.util.HashSet;

/**
 * Resolves the alias relations in local variables in the method
 * and fields in a class enclosing the method.
 * 
 * @author Katsuhisa Maruyama
 */
class LocalAliasResolver {
    
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
                Alias alias = getAliasRelation(node);
                if (alias != null) {
                    aliasesInFieldDeclarations.add(alias);
                }
            }
        }
        
        for (CFGNode node : cfg.getNodes()) {
            aliasesInFieldDeclarations.forEach(a -> walkForward(node, a));
            Alias alias = getAliasRelation(node);
            if (alias != null) {
                walkForward(node, alias);
            }
        }
    }
    
    private Alias getAliasRelation(CFGNode node) {
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
        if (def.isPrimitiveType() || !def.isVisible()) {
            return null;
        }
        
        JReference use = stNode.getUseVariables().get(0);
        if (use.isMethodCall() || !use.isVisible()) {
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
