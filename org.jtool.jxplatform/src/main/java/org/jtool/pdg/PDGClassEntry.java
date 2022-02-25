/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.cfg.CFGClassEntry;
import org.jtool.srcmodel.QualifiedName;

/**
 * The entry node for a ClDG for a class or an interface.
 * 
 * @author Katsuhisa Maruyama
 */
public class PDGClassEntry extends PDGEntry {
    
    /**
     * Creates a new object that represents an entry.
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
     * Returns the signature of the ClDG that has this entry node
     * @return the PDG signature
     */
    @Override
    public String getSignature() {
        return getCFGClassEntry().getSignature();
    }
    
    /**
     * Returns the fully-qualified name of the ClDG that has this entry node
     * @return the fully-qualified name of the ClDG
     */
    @Override
    public QualifiedName getQualifiedName() {
        return getCFGClassEntry().getQualifiedName();
    }
}
