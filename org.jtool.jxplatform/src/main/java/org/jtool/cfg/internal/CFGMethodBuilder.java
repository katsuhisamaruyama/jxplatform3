/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal;

import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGException;
import org.jtool.cfg.CFGExit;
import org.jtool.cfg.CFGMethodEntry;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGParameter;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.ControlFlow;
import org.jtool.cfg.JLocalVarReference;
import org.jtool.cfg.JVariableReference;
import org.jtool.graph.GraphEdge;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.internal.UncaughtExceptionTypeCollector;
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
import java.util.stream.Collectors;

/**
 * Builds a CFG that corresponds to a method.
 * 
 * @author Katsuhisa Maruyama
 */
class CFGMethodBuilder {
    
    @SuppressWarnings("unchecked")
    static CFG build(JavaMethod jmethod) {
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
        return build(jmethod, jmethod.getMethodBinding(), params);
    }
    
    private static CFG build(JavaMethod jmethod, IMethodBinding mbinding, List<VariableDeclaration> params) {
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
        
        DominantStatement entryStatement = new DominantStatement();
        
        if (jmethod.isSynthetic()) {
            CFGExit exit = getExitNode(jmethod, mbinding);
            cfg.setExitNode(exit);
            cfg.add(exit);
            
            CFGNode formalOut = createFormalOut(jmethod.getASTNode(), cfg, entry, entryStatement);
            ControlFlow edge = new ControlFlow(entry, formalOut);
            edge.setTrue();
            cfg.add(edge);
            
            ControlFlow exitEdge = new ControlFlow(formalOut, exit);
            exitEdge.setTrue();
            cfg.add(exitEdge);
            
            cfg.registerDominantStatement(cfg.getEntryNode().getOutgoingTrueFlow(), entryStatement);
            
            return cfg;
        }
        
        CFGNode tmpExit = new CFGNode();
        cfg.setExitNode(tmpExit);
        
        Set<CFGException> exceptionNodes = createExceptionNodes(jmethod, entry, cfg, entryStatement);
        
        CFGNode formalIn = createFormalIn(params, cfg, entry, entry, entryStatement);
        CFGNode nextNode = new CFGNode();
        
        ControlFlow edge = new ControlFlow(formalIn, nextNode);
        edge.setTrue();
        cfg.add(edge);
        
        StatementVisitor visitor = new StatementVisitor(jmethod.getJavaProject(), cfg,
                formalIn, nextNode, entryStatement);
        jmethod.getASTNode().accept(visitor);
        
        nextNode = visitor.getNextCFGNode();
        
        CFGExit exit = getExitNode(jmethod, mbinding);
        cfg.setExitNode(exit);
        cfg.add(exit);
        
        CFGNode formalOut = createFormalOut(jmethod.getASTNode(), cfg, entry, entryStatement);
        replace(cfg, nextNode, formalOut);
        replace(cfg, tmpExit, formalOut);
        
        ControlFlow exitEdge = new ControlFlow(formalOut, exit);
        exitEdge.setTrue();
        cfg.add(exitEdge);
        
        for (CFGException n : exceptionNodes) {
            ControlFlow exceptionExitEdge = new ControlFlow(n, exit);
            exceptionExitEdge.setTrue();
            cfg.add(exceptionExitEdge);
        }
        
        cfg.registerDominantStatement(cfg.getEntryNode().getOutgoingTrueFlow(), entryStatement);
        
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
    
    private static Set<CFGException> createExceptionNodes(JavaMethod jmethod, CFGMethodEntry entry, CFG cfg,
            DominantStatement entryStatement) {
        Set<CFGException> nodes = new HashSet<>();
        for (Type type : jmethod.getExceptionTypeNodes()) {
            CFGException exceptionNode = createExceptionNode(entry, cfg, type.resolveBinding().getTypeDeclaration());
            nodes.add(exceptionNode);
            
            entryStatement.addImmediatePostDominator(exceptionNode);
            entryStatement.setNestStructure(false);
        }
        
        UncaughtExceptionTypeCollector collector = new UncaughtExceptionTypeCollector();
        for (ITypeBinding tbinding : collector.getExceptions(jmethod)) {
            boolean included = nodes.stream()
                                    .anyMatch(n -> n.getTypeName().equals(tbinding.getQualifiedName()));
            if (!included) {
                CFGException exceptionNode = createExceptionNode(entry, cfg, tbinding);
                nodes.add(exceptionNode);
                
                entryStatement.addImmediatePostDominator(exceptionNode);
                entryStatement.setNestStructure(false);
            }
        }
        
        return nodes;
    }
    
    private static CFGException createExceptionNode(CFGMethodEntry entry, CFG cfg, ITypeBinding tbinding) {
        CFGException exceptNode = new CFGException(entry.getASTNode(), CFGNode.Kind.throwsClause, tbinding);
        entry.addExceptionNode(exceptNode);
        cfg.add(exceptNode);
        
        JVariableReference jvar = new JVersatileReference(entry.getASTNode(),
                "$" + tbinding.getErasure().getQualifiedName(), tbinding);
        exceptNode.setUseVariable(jvar);
        return exceptNode;
    }
    
    private static CFGNode createFormalIn(List<VariableDeclaration> params,
            CFG cfg, CFGMethodEntry entry, CFGNode prevNode,
            DominantStatement entryStatement) {
        for (int ordinal = 0; ordinal < params.size(); ordinal++) {
            VariableDeclaration param = params.get(ordinal);
            CFGParameter formalIn = new CFGParameter(param, CFGNode.Kind.formalIn, ordinal);
            formalIn.setParent(entry);
            entry.addFormalIn(formalIn);
            cfg.add(formalIn);
            
            entryStatement.addImmediatePostDominator(formalIn);
            
            JVariableReference def = new JLocalVarReference(param.getName(), param.resolveBinding());
            formalIn.setDefVariable(def);
            
            ControlFlow edge = new ControlFlow(prevNode, formalIn);
            edge.setTrue();
            cfg.add(edge);
            
            prevNode = formalIn;
        }
        return prevNode;
    }
    
    private static CFGNode createFormalOut(ASTNode node, CFG cfg, CFGMethodEntry entry,
            DominantStatement entryStatement) {
        CFGParameter formalOut = new CFGParameter(node, CFGNode.Kind.formalOut, 0);
        formalOut.setParent(entry);
        entry.setFormalOut(formalOut);
        cfg.add(formalOut);
        
        entryStatement.addImmediatePostDominator(formalOut);
        
        String returnType = entry.getJavaMethod().getReturnType();
        boolean isPrimitiveType = entry.getJavaMethod().isPrimitiveReturnType();
        
        JVariableReference use = new JVersatileReference(node,
                StatementVisitor.RETURN_VALUE_SYMBOL, returnType, isPrimitiveType);
        formalOut.addUseVariable(use);
        
        return formalOut;
    }
    
    static void addUseVariablesForReturn(CFG cfg) {
        List<CFGStatement> returnNodes = cfg.getStatementNodes().stream()
                                            .filter(node -> node.isReturn())
                                            .collect(Collectors.toList());
        for (CFGStatement returnNode : returnNodes) {
            Set<JVariableReference> defs = cfg.backwardReachableNodes(returnNode, true, false).stream()
                                              .filter(node -> node.isStatement())
                                              .flatMap(node -> ((CFGStatement)node).getDefVariables().stream())
                                              .filter(def -> def.isFieldAccess())
                                              .collect(Collectors.toSet());
            
            Set<JVariableReference> uses = returnNode.getUseVariables().stream()
                                                     .filter(var -> !var.isPrimitiveType())
                                                     .collect(Collectors.toSet());
            
            for (JVariableReference use : uses) {
                for (JVariableReference def : defs) {
                    if (def.getReferenceForm().startsWith(use.getReferenceForm() + ".")) {
                        returnNode.addUseVariable(def);
                    }
                }
            }
        }
    }
}
