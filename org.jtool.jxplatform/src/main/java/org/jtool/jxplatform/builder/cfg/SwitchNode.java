/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder.cfg;

import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.ControlFlow;
import org.eclipse.jdt.core.dom.ASTNode;

/**
 * A node for a <code>switch</code> statement of a CFG.
 * This node does not appear in the finally created CFG.
 * 
 * @author Katsuhisa Maruyama
 */
class SwitchNode extends CFGStatement {
    
    private CFGNode defaultStartNode = null;
    private CFGNode defaultEndNode = null;
    
    SwitchNode(ASTNode node, CFGNode.Kind kind) {
        super(node, kind);
    }
    
    void setDefaultStartNode(CFGNode node) {
        defaultStartNode = node;
    }
    
    CFGNode getDefaultStartNode() {
        return defaultStartNode;
    }
    
    void setDefaultEndNode(CFGNode node) {
        defaultEndNode = node;
    }
    
    CFGNode getDefaultEndNode() {
        return defaultEndNode;
    }
    
    CFGNode getPredecessorOfDefault() {
        return defaultStartNode.getIncomingEdges().stream()
                .filter(edge -> ((ControlFlow)edge).isFalse()).map(flow -> (CFGNode)flow.getSrcNode()).findFirst().orElse(null);
    }
    
    CFGNode getSuccessorOfDefault() {
        return defaultStartNode.getOutgoingEdges().stream()
                .filter(edge -> ((ControlFlow)edge).isFalse()).map(flow -> (CFGNode)flow.getDstNode()).findFirst().orElse(null);
    }
    
    boolean hasDefault() {
        return defaultStartNode != null;
    }
}
