/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.QualifiedName;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Modifier;

/**
 * A class that represents an expedient reference to a variable invisible on source code.
 * 
 * @author Katsuhisa Maruyama
 */
public class JExpedientReference extends JVariableReference {
    
    /**
     * Creates a new object that represents an expedient reference to a variable invisible on source code.
     * This constructor is not intended to be invoked by clients.
     * @param node the AST node for this variable reference
     * @param name the name of the referenced variable
     * @param tbinding the type binding information on the variable reference
     */
    public JExpedientReference(ASTNode node, String name, ITypeBinding tbinding) {
        super(node);
        
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
    public JExpedientReference(ASTNode node, String name, String type, boolean primitive) {
        super(node);
        
        this.type = type;
        this.isPrimitiveType = primitive;
        this.modifiers = Modifier.NONE;
        
        setProperties(node, name, type);
    }
    
    /**
     * Sets the properties of this variable reference.
     * @param node the AST node for this variable reference
     * @param name the name of this variable reference
     * @param type the type of the referenced variable
     */
    private void setProperties(ASTNode node, String name, String type) {
        if (node != null) {
            this.enclosingClassName = findEnclosingClassName(node);
            this.enclosingMethodName = findEnclosingMethodName(node);
        } else {
            this.enclosingClassName = type;
            this.enclosingMethodName = type + "( )";
        }
        this.declaringClassName = enclosingClassName;
        this.declaringMethodName = enclosingMethodName;
        
        this.name = name;
        this.fqn = new QualifiedName(declaringClassName, declaringMethodName + "!" + name);
        this.referenceForm = name;
        this.inProject = true;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAvailable() {
        return false;
    }
    
    /**
     * Obtains information on this variable reference.
     * @return the string representing the information
     */
    @Override
    public String toString() {
        return fqn.getMemberSignature() + "@" + type;
    }
}
