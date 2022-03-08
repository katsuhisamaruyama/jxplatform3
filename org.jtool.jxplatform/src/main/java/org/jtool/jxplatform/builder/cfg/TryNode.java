/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder.cfg;

import org.jtool.cfg.CFGException;
import org.jtool.cfg.CFGStatement;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ITypeBinding;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/**
 * A node for a <code>try</code> statement of a CFG.
 * This node does not appear in the finally created CFG.
 * 
 * @author Katsuhisa Maruyama
 */
class TryNode extends CFGStatement {
    
    private Set<ExceptionOccurrence> exceptionOccurrences = new HashSet<>();
    
    private List<CFGException> catchNodes = new ArrayList<>();
    private CFGStatement finallyNode;
    
    TryNode(ASTNode node, Kind kind) {
        super(node, kind);
    }
    
    void addExceptionOccurrence(CFGStatement node, ITypeBinding type, boolean methidCall) {
        exceptionOccurrences.add(new ExceptionOccurrence(node, type, methidCall));
    }
    
    Set<ExceptionOccurrence> getExceptionOccurrences() {
        return exceptionOccurrences;
    }
    
    void addCatchNode(CFGException node) {
        catchNodes.add(node);
    }
    
    void setCatchNodes(List<CFGException> nodes) {
        for (CFGException node : nodes) {
            addCatchNode(node);
        }
    }
    
    List<CFGException> getCatchNodes() {
        return catchNodes;
    }
    
    void setFinallyNode(CFGStatement node) {
        finallyNode = node;
    }
    
    CFGStatement getFinallyNode() {
        return finallyNode;
    }
    
    class ExceptionOccurrence {
        private CFGStatement node;
        private ITypeBinding type;
        private boolean methodCall;
        
        ExceptionOccurrence(CFGStatement node, ITypeBinding type, boolean methodCall) {
            this.node = node;
            this.type = type;
            this.methodCall = methodCall;
        }
        
        CFGStatement getNode() {
            return node;
        }
        
        ITypeBinding getType() {
          return type;
        }
        
        String getTypeName() {
            return type.getQualifiedName();
        }
        
        boolean isMethodCall() {
            return methodCall;
        }
    }
}
