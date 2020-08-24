/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.QualifiedName;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.IVariableBinding;

/**
 * An class that represents a reference to a local variable.
 * 
 * @author Katsuhisa Maruyama
 */
public class JLocalVarReference extends JReference {
    
    /**
     * A flag that indicates whether this is a reference to a parameter.
     */
    private boolean isParameter;
    
    /**
     * The identification number of a referenced variable.
     */
    private int variableId;
    
    /**
     * Creates a new object that represents a reference to a local variable.
     * @param node the AST node corresponding to this reference
     * @param vbinding the variable binding information on this reference
     */
    public JLocalVarReference(ASTNode node, IVariableBinding vbinding) {
        super(node);
        
        IVariableBinding binding = vbinding.getVariableDeclaration();
        this.enclosingClassName = findEnclosingClassName(node);
        this.enclosingMethodName = findEnclosingMethodName(node);
        this.declaringClassName = enclosingClassName;
        this.declaringMethodName = enclosingMethodName;
        
        String name = declaringMethodName + "!" + binding.getName() + "$" + String.valueOf(binding.getVariableId());
        this.fqn = new QualifiedName("", name);
        this.referenceForm = binding.getName() + "$" + String.valueOf(binding.getVariableId());
        this.type = binding.getType().getErasure().getQualifiedName();
        this.isPrimitiveType = binding.getType().isPrimitive();
        this.modifiers = binding.getModifiers();
        this.inProject = true;
        this.isParameter = binding.isParameter();
        this.variableId = binding.getVariableId();
    }
    
    /**
     * Tests if this is a reference to a local variable.
     * @return always {@code true} that indicates a reference to a local variable
     */
    @Override
    public boolean isLocalAccess() {
        return true;
    }
    
    /**
     * Tests if this is a reference to a parameter.
     * @return {@code true} if this is a reference to a parameter, otherwise {@code false}
     */
    public boolean isParameter() {
        return isParameter;
    }
    
    /**
     * Returns the identification number of a referenced variable.
     * @return the identification number
     */
    public int getVariableId() {
        return variableId;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getStartPosition() {
        return astNode.getStartPosition();
    }
    
    /**
     * Obtains information on this local variable reference.
     * @return the string representing the information
     */
    @Override
    public String toString() {
        return fqn.getMemberSignature() + "@" + type;
    }
}
