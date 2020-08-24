/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.slice.builder;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodInvocation;
import java.util.List;
import java.util.ArrayList;

/**
 * Collect AST nodes corresponding to method invocations.
 * 
 * @author Katsuhisa Maruyama
 */
public class MethodInvocationCollector extends ASTVisitor {
    
    private List<MethodInvocation> nodes = new ArrayList<>();
    
    public MethodInvocationCollector(ASTNode node) {
        node.accept(this);
    }
    
    public List<MethodInvocation> getNodes() {
        return nodes;
    }
    
    @Override
    public boolean visit(MethodInvocation node) {
        nodes.add(node);
        return true;
    }
}
