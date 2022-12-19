/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Modifier;
import org.jtool.cfg.JVariableReference;

/**
 * A class that represents a reference to a variable that stores the return value.
 * 
 * @author Katsuhisa Maruyama
 */
class JReturnValueReference extends JVariableReference {
    
    /**
     * The symbol indicating that a reference represents a variable that stores the return value.
     */
    public static final String METHOD_RETURN_SYMBOL = "!";
    
    /**
     * A prefix reference located prior to this reference.
     */
    private JVariableReference prefix = null;
    
    /**
     * Creates a new object that represents a reference to a variable that stores the return value.
     * This constructor is not intended to be invoked by clients.
     * @param node the AST node for this variable reference
     * @param name the name of the referenced variable
     * @param type the type of the referenced variable
     * @param primitive {@code true} if the type of the referenced variable is primitive, otherwise {@code false}
     */
    public JReturnValueReference(ASTNode node, String name, String type, boolean primitive) {
        super(node);
        
        this.isPrimitiveType = primitive;
        this.modifiers = Modifier.NONE;
        
        setProperties(node, name, type);
    }
    
    /**
     * Returns a prefix reference located prior to this reference.
     * @param prefix the prefix reference
     */
    public void setPrefix(JVariableReference prefix) {
        this.prefix = prefix;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public JVariableReference getPrefix() {
        return prefix;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isReturnValueReference() {
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
