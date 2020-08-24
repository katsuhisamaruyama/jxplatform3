/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.cfg.CFGEntry;
import org.jtool.srcmodel.QualifiedName;

/**
 * The entry node for a PDG or a ClDG.
 * 
 * @author Katsuhisa Maruyama
 */
public class PDGEntry extends PDGNode {
    
    /**
     * The PDG associated with this entry node. 
     */
    private CommonPDG pdg = null;
    
    /**
     * Creates a new object that represents an entry.
     * @param node the entry node for the CFG corresponding to the PDG that has this entry node.
     */
    public PDGEntry(CFGEntry node) {
        super(node);
    }
    
    /**
     * Returns the entry node for the CFG corresponding to the PDG that has this entry node.
     * @return the entry node for the CFG
     */
    public CFGEntry getCFGEntry() {
        return (CFGEntry)getCFGNode();
    }
    
    /**
     * Associates a PFG with this entry node
     * @param cfg the CFG to be associated
     */
    public void setPDG(CommonPDG pdg) {
        this.pdg = pdg;
    }
    
    /**
     * Returns the PFG that has this entry node
     * @return the PFG
     */
    public CommonPDG getPDG() {
        return pdg;
    }
    
    /**
     * Returns the signature of the PDG that has this entry node
     * @return the PDG signature
     */
    public String getSignature() {
        return getCFGEntry().getSignature();
    }
    
    /**
     * Returns the fully-qualified name of the PDG that has this entry node
     * @return the fully-qualified name of the PDG
     */
    public QualifiedName getQualifiedName() {
        return getCFGEntry().getQualifiedName();
    }
}
