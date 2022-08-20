/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.eclipse.jdt.core.dom.ASTNode;
import org.jtool.srcmodel.QualifiedName;

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
     * Sets the properties of this variable reference.
     * @param node the AST node for this variable reference
     * @param name the name of this variable reference
     * @param type the type of the referenced variable
     */
    protected void setProperties(ASTNode node, String name, String type) {
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
        this.type = type;
        this.fqn = new QualifiedName(declaringClassName, declaringMethodName + "!" + name);
        this.referenceForm = name;
        this.inProject = true;
    }
    
    /**
     * Returns a prefix reference located prior to this reference.
     * @return the prefix reference
     */
    public JVariableReference getPrefix() {
        return null;
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
