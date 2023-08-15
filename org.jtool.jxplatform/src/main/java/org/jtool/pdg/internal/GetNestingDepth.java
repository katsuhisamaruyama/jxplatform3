/*
 *  Copyright 2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg.internal;

import org.jtool.cfg.CFGNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SwitchCase;
import java.util.Stack;

/**
 * Obtains the number of nesting depth.
 * 
 * @author Katsuhisa Maruyama
 */
public class GetNestingDepth extends ASTVisitor {
    
    private int numberOfNesting;
    private int maxNumberOfNesting;
    private Stack<Integer> switchNesting = new Stack<>();
    
    public GetNestingDepth() {
        numberOfNesting = 0;
        maxNumberOfNesting = 0;
    }
    
    public int getMaximumNuberOfNestingDepth(CFGNode cfgnode) {
        ASTNode node = cfgnode.getASTNode();
        if (node != null) {
            node.accept(this);
        }
        return maxNumberOfNesting;
    }
    
    @Override
    public boolean visit(IfStatement node) {
        startNesting();
        return true;
    }
    
    @Override
    public void endVisit(IfStatement node) {
        endNesting();
    }
    
    @Override
    public boolean visit(WhileStatement node) {
        startNesting();
        return true;
    }
    
    @Override
    public void endVisit(WhileStatement node) {
        endNesting();
    }
    
    @Override
    public boolean visit(DoStatement node) {
        startNesting();
        return true;
    }
    
    @Override
    public void endVisit(DoStatement node) {
        endNesting();
    }
    
    @Override
    public boolean visit(ForStatement node) {
        startNesting();
        return true;
    }
    
    @Override
    public void endVisit(ForStatement node) {
        endNesting();
    }
    
    @Override
    public boolean visit(EnhancedForStatement node) {
        startNesting();
        return true;
    }
    
    @Override
    public void endVisit(EnhancedForStatement node) {
        endNesting();
    }
    
    public boolean visit(SwitchStatement node) {
        switchNesting.push(numberOfNesting);
        return true;
    }
    
    @Override
    public void endVisit(SwitchStatement node) {
        numberOfNesting = switchNesting.pop();
    }
    
    @Override
    public boolean visit(SwitchCase node) {
        startNesting();
        return true;
    }
    
    @Override
    public void endVisit(SwitchCase node) {
        /* empty */
    }
    
    private void startNesting() {
        numberOfNesting++;
        
        if (maxNumberOfNesting < numberOfNesting) {
            maxNumberOfNesting = numberOfNesting;
        }
    }
    
    private void endNesting() {
        numberOfNesting--;
    }
}
