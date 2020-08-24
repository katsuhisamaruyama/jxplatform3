/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.QualifiedName;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Modifier;

/**
 * A class that represents a reference to a variable invisible on source code.
 * 
 * @author Katsuhisa Maruyama
 */
public class JSpecialVarReference extends JReference {
    
    /**
     * Creates a new object that represents a reference to a variable invisible on source code.
     * @param node the AST node for this variable reference
     * @param name the name of the referenced variable
     * @param vbinding the variable binding information on the variable reference
     */
    public JSpecialVarReference(ASTNode node, String name, IVariableBinding vbinding) {
        super(node);
        
        this.fqn = new QualifiedName("", name);
        IVariableBinding binding = vbinding.getVariableDeclaration();
        this.type = binding.getType().getQualifiedName();
        this.isPrimitiveType = binding.getType().isPrimitive();
        this.modifiers = binding.getModifiers();
        
        setProperties(node, name);
    }
    
    /**
     * Creates a new object that represents a reference to a variable invisible on source code.
     * @param node the AST node for this variable reference
     * @param name the name of the referenced variable
     * @param tbinding the type binding information on the variable reference
     */
    public JSpecialVarReference(ASTNode node, String name, ITypeBinding tbinding) {
        super(node);
        
        this.isPrimitiveType = false;
        ITypeBinding binding = tbinding.getTypeDeclaration();
        this.type = binding.getQualifiedName();
        this.modifiers = binding.getModifiers();
        
        setProperties(node, name);
    }
    
    /**
     * Creates a new object that represents a reference to a variable invisible on source code.
     * @param node the AST node for this variable reference
     * @param name the name of the referenced variable
     * @param type the type of the referenced variable
     * @param primitive {@code true} if the type of the referenced variable is primitive, otherwise {@code false}
     */
    public JSpecialVarReference(ASTNode node, String name, String type, boolean primitive) {
        super(node);
        
        this.type = type;
        this.isPrimitiveType = primitive;
        this.modifiers = Modifier.NONE;
        
        setProperties(node, name);
    }
    
    /**
     * Creates a new object that represents a reference to a variable invisible on source code.
     * @param node the AST node for this variable reference
     * @param name the name of the referenced variable
     * @param primitive {@code true} if the type of the referenced variable is primitive, otherwise {@code false}
     */
    public JSpecialVarReference(ASTNode node, String name, boolean primitive) {
        super(node);
        
        ITypeBinding binding = findEnclosingClass(node).getTypeDeclaration();
        this.type = binding.getQualifiedName();
        this.isPrimitiveType = primitive;
        this.modifiers = binding.getModifiers();
        
        setProperties(node, name);
    }
    
    /**
     * Sets the properties of this variable reference.
     * @param node the AST node for this variable reference
     * @param name the name of this variable reference
     */
    private void setProperties(ASTNode node, String name) {
        this.enclosingClassName = findEnclosingClassName(node);
        this.enclosingMethodName = findEnclosingMethodName(node);
        this.declaringClassName = enclosingClassName;
        this.declaringMethodName = enclosingMethodName;
        
        this.fqn = new QualifiedName("", declaringMethodName + "!" + name);
        this.referenceForm = name;
        this.inProject = true;
    }
    
    /**
     * Tests if this variable reference is visible.
     * @return always {@code false} that indicate a reference to an invisible variable.
     */
    @Override
    public boolean isVisible() {
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
