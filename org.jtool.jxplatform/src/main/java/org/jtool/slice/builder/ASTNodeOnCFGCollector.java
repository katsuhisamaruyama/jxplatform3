/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.slice.builder;

import org.jtool.cfg.CFGNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

/**
 * Collects AST nodes appearing on a CFG.
 * 
 * @author Katsuhisa Maruyama
 */
public class ASTNodeOnCFGCollector extends ASTVisitor {
    
    private Map<String, ASTNode> nodeMap = new HashMap<>();
    
    public ASTNodeOnCFGCollector(ASTNode node) {
        node.accept(this);
    }
    
    public Set<ASTNode> getNodeSet() {
        return new HashSet<ASTNode>(nodeMap.values());
    }
    
    public ASTNode get(ASTNode node) {
        if (node == null) {
            return null;
        }
        return nodeMap.get(key(node));
    }
    
    @Override
    public void preVisit(ASTNode node) {
        if (isCFGNode(node)) {
            nodeMap.put(key(node), node);
        }
    }
    
    private String key(ASTNode node) {
        return String.valueOf(node.getStartPosition() + "-" + node.getLength());
    }
    
    private boolean isCFGNode(ASTNode node) {
        return node instanceof BodyDeclaration ||
               CFGNode.isStatement(node) || CFGNode.isExpression(node) || CFGNode.isLiteral(node);
    }
}
