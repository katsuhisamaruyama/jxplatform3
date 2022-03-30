/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.eclipse.jdt.core.dom.ASTNode;

/**
 * A class that represents a reference to a variable.
 * 
 * @author Katsuhisa Maruyama
 */
public class JVariableReference extends JReference {
    
    /**
     * Creates a new object that represents a reference to a variable.
     * This constructor is not intended to be invoked by clients.
     * @param node AST node corresponding to this reference
     */
    protected JVariableReference(ASTNode node) {
        super(node);
    }
    
    /**
     * Returns a prefix reference located prior to this reference.
     * @return the prefix reference
     */
    public JVariableReference getPrefix() {
        return null;
    }
}
