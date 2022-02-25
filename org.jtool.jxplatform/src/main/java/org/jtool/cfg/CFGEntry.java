/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.QualifiedName;
import org.eclipse.jdt.core.dom.ASTNode;

/**
 * An entry node for a CFG.
 * 
 * @author Katsuhisa Maruyama
 */
public abstract class CFGEntry extends CFGNode {
    
    /**
     * The fully-qualified name of the CFG that has this entry node.
     */
    protected QualifiedName fqn;
    
    /**
     * The CFG associated with this entry node. 
     */
    private CommonCFG cfg = null;
    
    /**
     * Creates a new object that represents an entry.
     * @param node the ASt node corresponding to this node
     * @param kind the kind of this node
     * @param fqn the fully-qualified name of the CFG that has this entry node
     */
    protected CFGEntry(ASTNode node, CFGNode.Kind kind, QualifiedName fqn) {
        super(node, kind);
        this.fqn = fqn;
    }
    
    /**
     * Associates a CFG with this entry node
     * @param cfg the CFG to be associated
     */
    public void setCFG(CommonCFG cfg) {
        this.cfg = cfg;
    }
    
    /**
     * Returns the CFG that has this entry node
     * @return the CFG
     */
    public CommonCFG getCFG() {
        return cfg;
    }
    
    /**
     * Returns the signature of the CFG that has this entry node
     * @return the signature of the CFG
     */
    public String getSignature() {
        return fqn.getMemberSignature();
    }
    
    /**
     * Returns the fully-qualified name of the CFG that has this entry node
     * @return the fully-qualified name of the CFG
     */
    public QualifiedName getQualifiedName() {
        return fqn;
    }
}
