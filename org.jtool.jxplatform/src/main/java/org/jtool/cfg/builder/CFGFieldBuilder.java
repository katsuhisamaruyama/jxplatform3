/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.builder;

import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGExit;
import org.jtool.cfg.CFGFieldEntry;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.ControlFlow;
import org.jtool.cfg.JFieldReference;
import org.jtool.cfg.JVariableReference;
import org.jtool.srcmodel.JavaField;
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
    
    static CFG build(JavaField jfield) {
        if (jfield.getVariableBinding() == null) {
            return null;
        }
        return build(jfield, jfield.getVariableBinding());
    }
    
    private static CFG build(JavaField jfield, IVariableBinding vbinding) {
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
        JVariableReference jvar = new JFieldReference(jfield.getASTNode(), jfield.getASTNode(), jfield.getName(), vbinding);
        declNode.addDefVariable(jvar);
        entry.setDeclarationNode(declNode);
        cfg.add(declNode);
        
        ControlFlow edge = new ControlFlow(entry, declNode);
        edge.setTrue();
        cfg.add(edge);
        
        CFGNode curNode = declNode;
        if (vbinding.isEnumConstant()) {
            EnumConstantDeclaration decl = (EnumConstantDeclaration)jfield.getASTNode();
            if (decl.arguments().size() > 0 && decl.resolveConstructorBinding() != null) {
                ExpressionVisitor visitor = new ExpressionVisitor(jfield.getJavaProject(), cfg, declNode);
                decl.accept(visitor);
                curNode = visitor.getExitNode();
            }
        } else {
            declNode.addUseVariable(jvar);
            
            VariableDeclarationFragment decl = (VariableDeclarationFragment)jfield.getASTNode();
            Expression initializer = decl.getInitializer();
            if (initializer != null) {
                ExpressionVisitor visitor = new ExpressionVisitor(jfield.getJavaProject(), cfg, declNode);
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
        
        return cfg;
    }
}
