/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.eclipse.jdt.core.dom.ASTNode;

/**
 * An exit node of a CFG.
 * 
 * @author Katsuhisa Maruyama
 */
public class CFGExit extends CFGNode {
    
    /**
     * Creates a new object that represents an exit.
     * This method is not intended to be invoked by clients.
     * @param node the AST node corresponding to this node
     * @param kind the kind of this node
     */
    public CFGExit(ASTNode node, CFGNode.Kind kind) {
        super(node, kind);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
