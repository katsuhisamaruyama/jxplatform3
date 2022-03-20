/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.eclipse.jdt.core.dom.ASTNode;

/**
 * A node for a parameter of a method declaration or an argument of a method call.
 * 
 * @author Katsuhisa Maruyama
 */
public class CFGParameter extends CFGStatement {
    
    /**
     * The index number of the parameter list for a method declaration or the argument lists for a method call.
     */
    private int index;
    
    /**
     * The parent node ({@code {CFGMethodEntry} or {@code CFGMethodCall}) that this node directly dangles on.
     */
    private CFGNode parent;
    
    /**
     * Creates a new object that represents a parameter.
     * This method is not intended to be invoked by clients.
     * @param node the AST node correspond to this node
     * @param kind the kind of this node
     * @param index the index number of the parameter list or the argument lists.
     */
    public CFGParameter(ASTNode node, CFGNode.Kind kind, int index) {
        super(node, kind);
        this.index = index;
    }
    
    /**
     * Sets the index number of the parameter list or the argument lists.
     * This method is not intended to be invoked by clients.
     * @param index the index number to be set
     */
    public void setIndex(int index) {
        this.index = index;
    }
    
    /**
     * Returns the index number of the parameter list or the argument lists.
     * @return the index number
     */
    public int getIndex() {
        return index;
    }
    
    /**
     * The parent node that this node directly dangles on.
     * This method is not intended to be invoked by clients.
     * @param node the parent node to be set
     */
    public void setParent(CFGNode node) {
        parent = node;
    }
    
    /**
     * Returns the parent node that this node directly dangles on.
     * @return the parent node
     */
    public CFGNode getParent() {
        return parent;
    }
    
    /**
     * Returns the variable defined when a method is called.
     * @return the defined variable, or {@code null} if there is no defined variable
     */
    public JReference getDefVariable() {
        if (getDefVariables().size() > 0) {
            return getDefVariables().get(0);
        }
        return null;
    }
    
    /**
     * Returns the variable used when a method is called.
     * @return the used variable, or {@code null} if there is no used variable
     */
    public JReference getUseVariable() {
        if (getUseVariables().size() > 0) {
            return getUseVariables().get(0);
        }
        return null;
    }
}
