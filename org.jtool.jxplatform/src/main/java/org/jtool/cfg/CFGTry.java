/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.eclipse.jdt.core.dom.ASTNode;
import java.util.List;
import java.util.ArrayList;

/**
 * A try node of a CFG.
 * 
 * @author Katsuhisa Maruyama
 */
public class CFGTry extends CFGStatement {
    
    /**
     * The collection of catch nodes corresponding to this try node.
     */
    private List<CFGStatement> catchNodes = new ArrayList<>();
    
    /**
     * A finally node corresponding to this try node.
     */
    private CFGStatement finallyNode = null;
    
    /**
     * Creates a new object that represents a try statement.
     * This method is not intended to be invoked by clients.
     * @param node the ASt node corresponding to this node
     */
    public CFGTry(ASTNode node) {
        super(node, CFGNode.Kind.trySt);
    }
    
    /**
     * Adds a node of a catch clause corresponding to this try node.
     * @param node the catch node
     */
    public void addCatchNode(CFGStatement node) {
        catchNodes.add(node);
    }
    
    /**
     * Return the catch nodes corresponding to this try node.
     * @return the collection of the catch nodes
     */
    public List<CFGStatement> getCatchNodes() {
        return catchNodes;
    }
    
    /**
     * Sets a node of a finally clause corresponding to this try node.
     * @param node the catch node
     */
    public void setFinallyNode(CFGStatement node) {
        finallyNode = node;
    }
    
    /**
     * Return the finally node corresponding to this try node.
     * @return the finally node, or {@code null} if the try statement has no finally clause
     */
    public CFGStatement getFinallyNode() {
        return finallyNode;
    }
    
}