/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal;

import org.jtool.cfg.JVariableReference;

/**
 * A class that represents a reference to an alias variable.
 * 
 * @author Katsuhisa Maruyama
 */
class JAliasReference extends JVariableReference {
    
    /**
     * A reference to the original of an alias variable.
     */
    private final JVariableReference original;
    
    /**
     * Creates a new object that represents a reference to an alias variable.
     * This constructor is not intended to be invoked by clients.
     * @param jvar the original of this alias variable
     * @param name the name of this alias variable
     * @param original the reference to the original of this alias variable
     */
    public JAliasReference(JVariableReference jvar, String name, JVariableReference original) {
        super(jvar.getASTNode());
        
        assert original != null;
        
        this.original = original;
        this.isPrimitiveType = jvar.isPrimitiveType();
        this.modifiers = jvar.getModifiers();
        
        setProperties(jvar.getASTNode(), name, jvar.getType());
    }
    
    /**
     * Returns a reference to the original of an alias variable.
     * @return the reference to the original variable
     */
    public JVariableReference getOriginal() {
        return original;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAliasReference() {
        return true;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAvailable() {
        return false;
    }
}
