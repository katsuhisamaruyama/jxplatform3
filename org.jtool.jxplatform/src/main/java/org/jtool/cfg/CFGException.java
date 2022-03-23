/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ITypeBinding;

/**
 * A node for a thrown or caught exception.
 * 
 * @author Katsuhisa Maruyama
 */
public class CFGException extends CFGStatement {
    
    /**
     * The type binding information on the type of the exception.
     */
    private ITypeBinding tbinding;
    
    /**
     * Creates a new object that represents an exception.
     * This method is not intended to be invoked by clients.
     * @param node the AST node corresponding to this node
     * @param kind the kind of this node
     * @param type type binding information on the type of the exception
     */
    public CFGException(ASTNode node, CFGNode.Kind kind, ITypeBinding tbinding) {
        super(node, kind);
        this.tbinding = tbinding;
    }
    
    /**
     * Returns the type binding information on the type of the exception.
     * @return the type binding information
     */
    public ITypeBinding getTypeBinding() {
        return tbinding;
    }
    
    /**
     * Returns the type name of the exception.
     * @return the type name
     */
    public String getTypeName() {
        return tbinding.getQualifiedName();
    }
}
