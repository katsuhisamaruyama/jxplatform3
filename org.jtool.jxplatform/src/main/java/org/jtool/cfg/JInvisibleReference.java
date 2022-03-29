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
 * A class that represents a reference to a variable invisible on source code.
 * 
 * @author Katsuhisa Maruyama
 */
public class JInvisibleReference extends JVariableReference {
    
    /**
     * Creates a new object that represents a reference to a variable invisible on source code.
     * This constructor is not intended to be invoked by clients.
     * @param node the AST node for this variable reference
     * @param name the name of the referenced variable
     * @param tbinding the type binding information on the variable reference
     */
    public JInvisibleReference(ASTNode node, String name, ITypeBinding tbinding) {
        super(node);
        
        ITypeBinding binding = tbinding.getTypeDeclaration();
        this.type = binding.getQualifiedName();
        this.isPrimitiveType = binding.isPrimitive();
        this.modifiers = binding.getModifiers();
        
        setProperties(node, name);
    }
    
    /**
     * Creates a new object that represents a reference to a variable invisible on source code.
     * This constructor is not intended to be invoked by clients.
     * @param node the AST node for this variable reference
     * @param name the name of the referenced variable
     * @param type the type of the referenced variable
     * @param primitive {@code true} if the type of the referenced variable is primitive, otherwise {@code false}
     */
    public JInvisibleReference(ASTNode node, String name, String type, boolean primitive) {
        super(node);
        
        this.type = type;
        this.isPrimitiveType = primitive;
        this.modifiers = Modifier.NONE;
        
        setProperties(node, name);
    }
    
    /**
     * Creates a new object that represents a reference to a variable invisible on source code.
     * This constructor is not intended to be invoked by clients.
     * @param node the AST node for this variable reference
     * @param name the name of the referenced variable
     * @param primitive {@code true} if the type of the referenced variable is primitive, otherwise {@code false}
     */
    public JInvisibleReference(ASTNode node, String name, boolean primitive) {
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
        
        this.name = name;
        this.fqn = new QualifiedName("", declaringMethodName + "!" + name);
        this.referenceForm = name;
        this.inProject = true;
    }
    
    /**
     * Tests if an element for this reference is exposed.
     * @return {@code false} that indicate a reference to an exposed element.
     */
    @Override
    public boolean isAnalyzable() {
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
