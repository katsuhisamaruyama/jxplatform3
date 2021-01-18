/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ITypeBinding;

/**
 * A node for a <code>catch</code> clause or <code>throws</code> of method in a CFG.
 * 
 * @author Katsuhisa Maruyama
 */
public class CFGException extends CFGStatement {
    
    /**
     * The type binding information on the type of the exception.
     */
    private ITypeBinding type;
    
    /**
     * The parent node ({@code {CFGMethodEntry} or {@code CFGMethodCall}) that this node directly dangles on.
     */
    private CFGNode parent;
    
    /**
     * Creates a new object that represents an exception.
     * @param node the AST node corresponding to this node
     * @param kind the kind of this node
     * @param type type binding information on the type of the exception
     */
    public CFGException(ASTNode node, CFGNode.Kind kind, ITypeBinding type) {
        super(node, kind);
        this.type = type;
    }
    
    /**
     * Returns the type binding information on the type of the exception.
     * @return the type binding information
     */
    public ITypeBinding getType() {
        return type;
    }
    
    /**
     * Returns the type name of the exception.
     * @return the type name
     */
    public String getTypeName() {
        return type.getQualifiedName();
    }
    
    /**
     * The parent node that this node directly dangles on.
     * @param parent the parent node to be set
     */
    public void setParent(CFGNode parent) {
        this.parent = parent;
    }
    
    /**
     * Returns the parent node that this node directly dangles on.
     * @return the parent node
     */
    public CFGNode getParent() {
        return parent;
    }
}
