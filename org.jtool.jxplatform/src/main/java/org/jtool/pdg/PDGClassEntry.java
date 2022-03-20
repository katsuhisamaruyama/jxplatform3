/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.srcmodel.QualifiedName;
import org.jtool.cfg.CFGClassEntry;

/**
 * The entry node for a ClDG for a class or an interface.
 * 
 * @author Katsuhisa Maruyama
 */
public class PDGClassEntry extends PDGNode {
    
    /**
     * The ClDG associated with this entry node. 
     */
    private ClDG cldg = null;
    
    /**
     * Creates a new object that represents an entry.
     * This method is not intended to be invoked by clients.
     * @param node the entry node for the CCFG corresponding to the ClDG that has this entry node.
     */
    public PDGClassEntry(CFGClassEntry node) {
        super(node);
    }
    
    /**
     * Returns the entry node for the CCFG corresponding to the ClDG that has this entry node.
     * @return the entry node for the CCFG
     */
    public CFGClassEntry getCFGClassEntry() {
        return (CFGClassEntry)getCFGNode();
    }
    
    /**
     * Returns the fully-qualified name of the ClDG that has this entry node
     * @return the fully-qualified name of the ClDG
     */
    public QualifiedName getQualifiedName() {
        return getCFGClassEntry().getQualifiedName();
    }
    
    /**
     * Associates a ClDG with this entry node.
     * This method is not intended to be invoked by clients.
     * @param cldg the ClDG to be associated
     */
    public void setClDG(ClDG cldg) {
        this.cldg = cldg;
    }
    
    /**
     * Returns the ClDG that has this entry node
     * @return the ClDG
     */
    public ClDG getClDG() {
        return cldg;
    }
}
