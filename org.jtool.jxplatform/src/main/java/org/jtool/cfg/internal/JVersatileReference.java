/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Modifier;
import org.jtool.cfg.JVariableReference;

/**
 * A class that represents a versatile reference to either
 *   a variable storing the return value into a formal out node of the method call,
 *   a defined variable in a switch statement mode,
 *   a thrown exception type, or
 *   a literal object.
 * 
 * @author Katsuhisa Maruyama
 */
class JVersatileReference extends JVariableReference {
    
    /**
     * Creates a new object that represents an versatile reference to a variable invisible on source code.
     * This constructor is not intended to be invoked by clients.
     * @param node the AST node for this variable reference
     * @param name the name of the referenced variable
     * @param tbinding the type binding information on the variable reference
     */
    public JVersatileReference(ASTNode node, String name, ITypeBinding tbinding) {
        super(node);
        
        assert tbinding != null;
        
        ITypeBinding binding = tbinding.getTypeDeclaration();
        this.type = binding.getQualifiedName();
        this.isPrimitiveType = binding.isPrimitive();
        this.modifiers = binding.getModifiers();
        
        setProperties(node, name, type);
    }
    
    /**
     * Creates a new object that represents a reference to a variable invisible on source code.
     * This constructor is not intended to be invoked by clients.
     * @param node the AST node for this variable reference
     * @param name the name of the referenced variable
     * @param type the type of the referenced variable
     * @param primitive {@code true} if the type of the referenced variable is primitive, otherwise {@code false}
     */
    public JVersatileReference(ASTNode node, String name, String type, boolean primitive) {
        super(node);
        
        this.isPrimitiveType = primitive;
        this.modifiers = Modifier.NONE;
        
        setProperties(node, name, type);
    }
    
    /**
     * {@inheritDoc}
     */
    public String getReceiverName() {
        return "";
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isVersatileReference() {
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
