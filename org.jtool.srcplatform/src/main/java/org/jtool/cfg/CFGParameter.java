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
     * @return the defined ariable
     */
    public JReference getDefVariable() {
        return getDefVariables().get(0);
    }
    
    /**
     * Returns the variable used when a method is called.
     * @return the used variable
     */
    public JReference getUseVariable() {
        return getUseVariables().get(0);
    }
}
