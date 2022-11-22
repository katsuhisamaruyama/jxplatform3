/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.eclipse.jdt.core.dom.ASTNode;

/**
 * A merge node of a CFG.
 * 
 * @author Katsuhisa Maruyama
 */
public class CFGMerge extends CFGNode {
    
    /**
     * The CFG node for the branch that causes the merge
     */
    private CFGStatement branch;
    
    /**
     * Creates a new object that represents a merge.
     * This method is not intended to be invoked by clients.
     * @param node the ASt node corresponding to this node
     * @param branch the node for the branch that causes the merge
     */
    public CFGMerge(ASTNode node, CFGStatement branch) {
        super(node, CFGNode.Kind.merge);
        this.branch = branch;
    }
    
    /**
     * Sets a node for the branch that causes the merge.
     * This method is not intended to be invoked by clients.
     * @param branch the branch node to be set
     */
    public void setBranch(CFGStatement branch) {
        assert branch != null;
        
        this.branch = branch;
    }
    
    /**
     * Returns the node for the branch that causes the merge.
     * @return the branch node
     */
    public CFGStatement getBranch() {
        return branch;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        if (getKind() != null) {
            return super.toString() + " " + branch.getKind().toString() + "(" + branch.getId() + ")";
        } else {
            return super.toString();
        }
    }
}
