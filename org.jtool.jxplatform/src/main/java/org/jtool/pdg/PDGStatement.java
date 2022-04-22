/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.JVariableReference;
import java.util.List;

/**
 * A statement node of a PDG, which stores defined and used variables.
 * 
 * @author Katsuhisa Maruyama
 */
public class PDGStatement extends PDGNode {
    
    /**
     * Creates a new object that represents a statement.
     * This method is not intended to be invoked by clients.
     * @param node the CFG node corresponding to this node
     */
    public PDGStatement(CFGStatement node) {
        super(node);
    }
    
    /**
     * Returns the CFG node for a statement corresponding to this node.
     * @return the CFG node for the statement
     */
    public CFGStatement getCFGStatement() {
        return (CFGStatement)cfgnode;
    }
    
    /**
     * Returns variables defined in this node.
     * @return the collection of the defined variables
     */
    public List<JVariableReference> getDefVariables() {
        return getCFGStatement().getDefVariables();
    }
    
    /**
     * Returns variables used in this node.
     * @return the collection of the used variables
     */
    public List<JVariableReference> getUseVariables() {
        return getCFGStatement().getUseVariables();
    }
    
    /**
     * Returns the first one from the collection of variables defined in this node.
     * @return the defined variable at the first position, or {@code null} if this node never defines any variable
     */
    public JVariableReference getDefFirst() {
        return getCFGStatement().getDefFirst();
    }
    
    /**
     * Returns the first one from the collection of variables used in this node.
     * @return the used variable at the first position, or {@code null} if this node never uses any variable
     */
    public JVariableReference getUseFirst() {
        return getCFGStatement().getUseFirst();
    }
    
    /**
     * Tests if this node defines a given variable.
     * @param jv the variable to be checked
     * @return {@code true} if the variable is defined in this node, otherwise {@code false}
     */
    public boolean definesVariable(JVariableReference jv) {
        return getCFGStatement().getDefVariables().contains(jv);
    }
    
    /**
     * Tests if this node uses a given variable.
     * @param jv the variable to be checked
     * @return {@code true} if the variable is used in this node, otherwise {@code false}
     */
    public boolean usesVariable(JVariableReference jv) {
        return getCFGStatement().getUseVariables().contains(jv);
    }
    
    /**
     * Obtains information on this statement node.
     * @return the string representing the information
     */
    @Override
    public String toString() {
        return getCFGStatement().toString();
    }
}
