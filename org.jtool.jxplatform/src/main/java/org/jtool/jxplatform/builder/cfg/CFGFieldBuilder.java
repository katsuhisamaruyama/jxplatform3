/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder.cfg;

import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGExit;
import org.jtool.cfg.CFGFieldEntry;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.ControlFlow;
import org.jtool.cfg.JFieldReference;
import org.jtool.cfg.JReference;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaProject;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.IVariableBinding;

/**
 * Builds a CFG that corresponds to a field.
 * 
 * @author Katsuhisa Maruyama
 */
class CFGFieldBuilder {
    
    static CFG build(JavaField jfield, boolean toBeResolved) {
        if (jfield.getVariableBinding() == null) {
            return null;
        }
        return build(jfield, jfield.getVariableBinding(), toBeResolved);
    }
    
    private static CFG build(JavaField jfield, IVariableBinding vbinding, boolean toBeResolved) {
        CFG cfg = new CFG();
        
        CFGFieldEntry entry;
        if (vbinding.isEnumConstant()) {
            entry = new CFGFieldEntry(jfield, CFGNode.Kind.enumConstantEntry);
        } else {
            entry = new CFGFieldEntry(jfield, CFGNode.Kind.fieldEntry);
        }
        cfg.setEntryNode(entry);
        cfg.add(entry);
        
        CFGStatement declNode = new CFGStatement(jfield.getASTNode(), CFGNode.Kind.fieldDeclaration);
        JReference jvar = new JFieldReference(jfield.getASTNode(), jfield.getASTNode(), jfield.getName(), vbinding);
        declNode.addDefVariable(jvar);
        declNode.addUseVariable(jvar);
        entry.setDeclarationNode(declNode);
        cfg.add(declNode);
        
        ControlFlow edge = new ControlFlow(entry, declNode);
        edge.setTrue();
        cfg.add(edge);
        
        CFGNode curNode = declNode;
        if (vbinding.isEnumConstant()) {
            EnumConstantDeclaration decl = (EnumConstantDeclaration)jfield.getASTNode();
            if (decl.resolveConstructorBinding() != null) {
                ExpressionVisitor visitor = new ExpressionVisitor(cfg, declNode);
                decl.accept(visitor);
                curNode = visitor.getExitNode();
            }
        } else {
            VariableDeclarationFragment decl = (VariableDeclarationFragment)jfield.getASTNode();
            Expression initializer = decl.getInitializer();
            if (initializer != null) {
                ExpressionVisitor visitor = new ExpressionVisitor(cfg, declNode);
                initializer.accept(visitor);
                curNode = visitor.getExitNode();
            }
        }
        
        CFGExit exit;
        if (vbinding.isEnumConstant()) {
            exit = new CFGExit(jfield.getASTNode(), CFGNode.Kind.enumConstantExit);
        } else {
            exit = new CFGExit(jfield.getASTNode(), CFGNode.Kind.fieldExit);
        }
        cfg.setExitNode(exit);
        cfg.add(exit);
        
        edge = new ControlFlow(curNode, exit);
        edge.setTrue();
        cfg.add(edge);
        
        if (toBeResolved) {
            resolveReferences(jfield.getJavaProject(), cfg);
        }
        
        return cfg;
    }
    
    static void resolveReferences(JavaProject jproject, CFG cfg) {
        ReceiverTypeResolver receiverTypeResolver = new ReceiverTypeResolver(jproject);
        receiverTypeResolver.findReceiverTypes(cfg);
        
        ReferenceResolver referenceResolver = new ReferenceResolver(jproject);
        referenceResolver.findDefUseFields(cfg);
    }
}