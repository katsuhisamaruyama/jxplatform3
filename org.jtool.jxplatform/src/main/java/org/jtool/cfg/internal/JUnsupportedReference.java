/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Modifier;
import org.jtool.cfg.JVariableReference;

/**
 * A class that represents an unsupported reference to a node of 
 * LambdaExpression,
 * CreationReference, SuperMethodReference, TypeMethodReference, ExpressionMethodReference, and
 * SwitchExpression.
 * 
 * @author Katsuhisa Maruyama
 */
class JUnsupportedReference extends JVariableReference {
    
    /**
     * Creates a new object that represents a reference to an unsupported program element.
     * This constructor is not intended to be invoked by clients.
     * @param node the AST node for this variable reference
     * @param name the name of the referenced variable
     * @param type the type of the referenced variable
     * @param primitive {@code true} if the type of the referenced variable is primitive, otherwise {@code false}
     */
    public JUnsupportedReference(ASTNode node, String name, String type, boolean primitive) {
        super(node);
        
        this.isPrimitiveType = primitive;
        this.modifiers = Modifier.NONE;
        
        setProperties(node, name, type);
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean isUnsupportedReference() {
        return true;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAvailable() {
        return false;
    }
}
