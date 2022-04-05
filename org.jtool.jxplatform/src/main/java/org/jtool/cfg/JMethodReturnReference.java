/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.eclipse.jdt.core.dom.ASTNode;

/**
 * A class that represents a reference to a variable invisible on source code.
 * 
 * @author Katsuhisa Maruyama
 */
public class JMethodReturnReference extends JExpedientialReference {
    
    /**
     * The symbol indicating that a reference represents a method return.
     */
    public static final String METHOD_RETURN_SYMBOL = "!";
    
    /**
     * A prefix reference located prior to this reference.
     */
    private JVariableReference prefix = null;
    
    /**
     * Creates a new object that represents a reference to a variable invisible on source code.
     * This constructor is not intended to be invoked by clients.
     * @param node the AST node for this variable reference
     * @param name the name of the referenced variable
     * @param type the type of the referenced variable
     * @param primitive {@code true} if the type of the referenced variable is primitive, otherwise {@code false}
     */
    public JMethodReturnReference(ASTNode node, String name, String type, boolean primitive) {
        super(node, name, type, primitive);
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
}
