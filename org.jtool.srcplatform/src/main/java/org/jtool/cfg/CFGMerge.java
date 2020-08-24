/*
 *  Copyright 2020
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
     * @param the ASt node corresponding to this node
     * @param branch the node for the branch that causes the merge
     */
    public CFGMerge(ASTNode node, CFGStatement branch) {
        super(node, branch.getKind());
        this.branch = branch;
    }
    
    /**
     * Sets the node for the branch that causes the merge.
     * @param branch the branch node to be set
     */
    public void setBranch(CFGStatement branch) {
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
            return super.getIdString()  + " merge-" + branch.getKind().toString() + "(" + branch.getId() + ")";
        } else {
            return super.getIdString();
        }
    }
}
