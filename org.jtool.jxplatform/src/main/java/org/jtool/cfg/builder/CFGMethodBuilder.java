/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.builder;

import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGException;
import org.jtool.cfg.CFGExit;
import org.jtool.cfg.CFGMethodEntry;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGParameter;
import org.jtool.cfg.ControlFlow;
import org.jtool.cfg.JLocalVarReference;
import org.jtool.cfg.JVariableReference;
import org.jtool.cfg.JExpedientialReference;
import org.jtool.graph.GraphEdge;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.builder.UncaughtExceptionTypeCollector;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/**
 * Builds a CFG that corresponds to a method.
 * 
 * @author Katsuhisa Maruyama
 */
class CFGMethodBuilder {
    
    @SuppressWarnings("unchecked")
    static CFG build(JavaMethod jmethod, boolean toBeResolved) {
        List<VariableDeclaration> params;
        if (!jmethod.isInitializer()) {
            if (jmethod.isLambda()) {
                LambdaExpression node = (LambdaExpression)jmethod.getASTNode();
                params = node.parameters();
            } else {
                if (jmethod.isSynthetic()) {
                    params = new ArrayList<>();
                } else {
                    MethodDeclaration node = (MethodDeclaration)jmethod.getASTNode();
                    params = node.parameters();
                }
            }
        } else {
            params = new ArrayList<>();
        }
        return build(jmethod, jmethod.getMethodBinding(), params, toBeResolved);
    }
    
    private static CFG build(JavaMethod jmethod, IMethodBinding mbinding,
            List<VariableDeclaration> params, boolean toBeResolved) {
        CFG cfg = new CFG();
        
        CFGMethodEntry entry;
        if (mbinding == null) {
            entry = new CFGMethodEntry(jmethod, CFGNode.Kind.initializerEntry);
        } else {
            if (mbinding.isConstructor()) {
                entry = new CFGMethodEntry(jmethod, CFGNode.Kind.constructorEntry);
            } else {
                entry = new CFGMethodEntry(jmethod, CFGNode.Kind.methodEntry);
            }
        }
        
        cfg.setEntryNode(entry);
        cfg.add(entry);
        
        if (jmethod.isSynthetic()) {
            CFGExit exit = getExitNode(jmethod, mbinding);
            cfg.setExitNode(exit);
            cfg.add(exit);
            
            ControlFlow exitEdge = new ControlFlow(entry, exit);
            exitEdge.setTrue();
            cfg.add(exitEdge);
            return cfg;
        }
        
        CFGNode tmpExit = new CFGNode();
        cfg.setExitNode(tmpExit);
        
        Set<CFGException> exceptionNodes = createExceptionNodes(jmethod, entry, cfg);
        
        CFGNode finalFormalInNode = createFormalIn(params, cfg, entry, entry);
        CFGNode nextNode = new CFGNode();
        
        ControlFlow entryEdge = new ControlFlow(finalFormalInNode, nextNode);
        entryEdge.setTrue();
        cfg.add(entryEdge);
        
        StatementVisitor visitor = new StatementVisitor(cfg, finalFormalInNode, nextNode);
        jmethod.getASTNode().accept(visitor);
        nextNode = visitor.getNextCFGNode();
        
        CFGExit exit = getExitNode(jmethod, mbinding);
        cfg.setExitNode(exit);
        cfg.add(exit);
        
        CFGNode formalOutNodeForReturn = createFormalOutForReturn(jmethod.getASTNode(), cfg, entry);
        replace(cfg, nextNode, formalOutNodeForReturn);
        replace(cfg, tmpExit, formalOutNodeForReturn);
        
        ControlFlow exitEdge = new ControlFlow(formalOutNodeForReturn, exit);
        exitEdge.setTrue();
        cfg.add(exitEdge);
        
        for (CFGException n : exceptionNodes) {
            ControlFlow exceptionExitEdge = new ControlFlow(n, exit);
            exceptionExitEdge.setTrue();
            cfg.add(exceptionExitEdge);
        }
        
        if (toBeResolved) {
            Resolver.resolveReferences(jmethod.getJavaProject(), cfg);
        }
        
        Resolver.resolveLocalAlias(cfg);
        
        return cfg;
    }
    
    private static CFGExit getExitNode(JavaMethod jmethod, IMethodBinding mbinding) {
        if (mbinding == null) {
            return new CFGExit(jmethod.getASTNode(), CFGNode.Kind.initializerExit);
        } else {
            if (mbinding.isConstructor()) {
                return new CFGExit(jmethod.getASTNode(), CFGNode.Kind.constructorExit);
            } else {
                return new CFGExit(jmethod.getASTNode(), CFGNode.Kind.methodExit);
            }
        }
    }
    
    private static void replace(CFG cfg, CFGNode tmpNode, CFGNode node) {
        Set<GraphEdge> edges = new HashSet<>(tmpNode.getIncomingEdges());
        for (GraphEdge edge : edges) {
            edge.setDstNode(node);
        }
    }
    
    private static Set<CFGException> createExceptionNodes(JavaMethod jmethod, CFGMethodEntry entry, CFG cfg) {
        Set<CFGException> nodes = new HashSet<>();
        for (Type type : jmethod.getExceptionTypeNodes()) {
            CFGException exceptionNode = createExceptionNode(entry, cfg, type.resolveBinding().getTypeDeclaration());
            nodes.add(exceptionNode);
        }
        
        UncaughtExceptionTypeCollector collector = new UncaughtExceptionTypeCollector();
        for (ITypeBinding tbinding : collector.getExceptions(jmethod)) {
            boolean included = nodes.stream()
                                    .anyMatch(n -> n.getTypeName().equals(tbinding.getQualifiedName()));
            if (!included) {
                CFGException exceptionNode = createExceptionNode(entry, cfg, tbinding);
                nodes.add(exceptionNode);
            }
        }
        
        return nodes;
    }
    
    private static CFGException createExceptionNode(CFGMethodEntry entry, CFG cfg, ITypeBinding tbinding) {
        CFGException node = new CFGException(entry.getASTNode(), CFGNode.Kind.throwsClause, tbinding);
        entry.addExceptionNode(node);
        cfg.add(node);
        
        JVariableReference jvar = new JExpedientialReference(entry.getASTNode(),
                "$" + tbinding.getErasure().getQualifiedName(), tbinding);
        node.setUseVariable(jvar);
        return node;
    }
    
    private static CFGNode createFormalIn(List<VariableDeclaration> params,
            CFG cfg, CFGMethodEntry entry, CFGNode prevNode) {
        for (int ordinal = 0; ordinal < params.size(); ordinal++) {
            VariableDeclaration param = params.get(ordinal);
            CFGParameter formalInNode = new CFGParameter(param, CFGNode.Kind.formalIn, ordinal);
            formalInNode.setParent(entry);
            entry.addFormalIn(formalInNode);
            cfg.add(formalInNode);
            
            JVariableReference def = new JLocalVarReference(param.getName(), param.resolveBinding());
            formalInNode.setDefVariable(def);
            
            ControlFlow edge = new ControlFlow(prevNode, formalInNode);
            edge.setTrue();
            cfg.add(edge);
            
            prevNode = formalInNode;
        }
        return prevNode;
    }
    
    private static CFGNode createFormalOutForReturn(ASTNode node, CFG cfg, CFGMethodEntry entry) {
        CFGParameter formalOutNode = new CFGParameter(node, CFGNode.Kind.formalOut, 0);
        formalOutNode.setParent(entry);
        entry.setFormalOutForReturn(formalOutNode);
        cfg.add(formalOutNode);
        
        String returnType = entry.getJavaMethod().getReturnType();
        boolean isPrimitiveType = entry.getJavaMethod().isPrimitiveReturnType();
        
        JVariableReference use = new JExpedientialReference(node, "$_", returnType, isPrimitiveType);
        formalOutNode.addUseVariable(use);
        
        return formalOutNode;
    }
}
