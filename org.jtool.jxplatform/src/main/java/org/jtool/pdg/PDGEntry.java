/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.cfg.CFGEntry;
import org.jtool.srcmodel.QualifiedName;

/**
 * The entry node for a PDG.
 * 
 * @author Katsuhisa Maruyama
 */
public class PDGEntry extends PDGNode {
    
    /**
     * The PDG associated with this entry node. 
     */
    private PDG pdg = null;
    
    /**
     * Creates a new object that represents an entry.
     * This method is not intended to be invoked by clients.
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
     * Associates a PDG with this entry node.
     * This method is not intended to be invoked by clients.
     * @param pdg the PDG to be associated
     */
    public void setPDG(PDG pdg) {
        assert pdg != null;
        
        this.pdg = pdg;
    }
    
    /**
     * Returns the PDG that has this entry node
     * @return the PDG
     */
    public PDG getPDG() {
        return pdg;
    }
    
    /**
     * Returns the fully-qualified name of the PDG that has this entry node
     * @return the fully-qualified name of the PDG
     */
    public QualifiedName getQualifiedName() {
        return getCFGEntry().getQualifiedName();
    }
    
    /**
     * Returns the signature of the PDG that has this entry node
     * @return the PDG signature
     */
    public String getSignature() {
        return getCFGEntry().getSignature();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEntry() {
        return true;
    }
}
