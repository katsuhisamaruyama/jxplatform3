/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.eclipse.jdt.core.dom.ASTNode;
import java.util.List;
import java.util.ArrayList;

/**
 * A statement node of a CFG, which stores variables defined and used in it.
 * 
 * @author Katsuhisa Maruyama
 */
public class CFGStatement extends CFGNode {
    
    /**
     * The collection of variables defined in this node.
     */
    private List<JReference> defs = new ArrayList<>();
    
    /**
     * The collection of variables used in this node.
     */
    private List<JReference> uses = new ArrayList<>();
    
    /**
     * Creates a new object that represents a statement.
     * @param node the AST node corresponding to this node
     * @param kind the kind of this node
     */
    public CFGStatement(ASTNode node, Kind kind) {
        super(node, kind);
    }
    
    /**
     * Adds a variable to the collection of variables defined this node.
     * This method is not intended to be invoked by clients.
     * @param jv the variable to be added
     */
    public void addDefVariable(JReference jv) {
        if (jv != null && !defineVariable(jv)) {
            defs.add(jv);
        }
    }
    
    /**
     * Adds a variable to the collection of variables used this node.
     * This method is not intended to be invoked by clients.
     * @param jv the variable to be added
     */
    public void addUseVariable(JReference jv) {
        if (jv != null && !useVariable(jv)) {
            uses.add(jv);
        }
    }
    
    /**
     * Adds variables to the collection of variables defined this node.
     * This method is not intended to be invoked by clients.
     * @param jvs the collection of the variables to be added
     */
    public void addDefVariables(List<JReference> jvs) {
        jvs.forEach(jvar -> addDefVariable(jvar));
    }
    
    /**
     * Adds variables to the collection of variables used this node.
     * This method is not intended to be invoked by clients.
     * @param jvs the collection of the variables to be added
     */
    public void addUseVariables(List<JReference> jvs) {
        jvs.forEach(jvar -> addUseVariable(jvar));
    }
    
    /**
     * Removes a variable from the collection of variables defined this node.
     * This method is not intended to be invoked by clients.
     * @param jv the collection of the variables to be removed
     * @return {@code true} if the removal is successfully done, otherwise {@code false}
     */
    public boolean removeDefVariable(JReference jv) {
        return defs.remove(jv);
    }
    
    /**
     * Removes a variable from the collection of variables used this node.
     * This method is not intended to be invoked by clients.
     * @param jv the collection of the variables to be removed
     * @return {@code true} if the removal is successfully done, otherwise {@code false}
     */
    public boolean removeUseVariable(JReference jv) {
        return uses.remove(jv);
    }
    
    /**
     * Clears the collection of variables defined this node.
     * This method is not intended to be invoked by clients.
     */
    public void clearDefVariables() {
        defs.clear();
    }
    
    /**
     * Clears the collection of variables defined this node.
     * This method is not intended to be invoked by clients.
     */
    public void clearUseVariables() {
        uses.clear();
    }
    
    /**
     * Sets the collection of variables defined in this node.
     * This method is not intended to be invoked by clients.
     * @param jvs the collection of the variables to be set
     */
    public void setDefVariables(List<JReference> jvs) {
        defs = jvs;
    }
    
    /**
     * Sets the collection of variables used in this node.
     * This method is not intended to be invoked by clients.
     * @param jvs the collection of the variables to be set
     */
    public void setUseVariables(List<JReference> jvs) {
        uses = jvs;
    }
    
    /**
     * Sets the collection that stores a variable defined in this node.
     * This method is not intended to be invoked by clients.
     * @param jv the variable to be set
     */
    public void setDefVariable(JReference jv) {
        clearDefVariables();
        addDefVariable(jv);
    }
    
    /**
     * Sets the collection that stores a variable used in this node.
     * This method is not intended to be invoked by clients.
     * @param jv the variable to be set
     */
    public void setUseVariable(JReference jv) {
        clearUseVariables();
        addUseVariable(jv);
    }
    
    /**
     * Returns variables defined in this node.
     * @return the collection of the defined variables
     */
    public List<JReference> getDefVariables() {
        return defs;
    }
    
    /**
     * Returns variables used in this node.
     * @return the collection of the used variables
     */
    public List<JReference> getUseVariables() {
        return uses;
    }
    
    /**
     * Tests if this node defines a given variable.
     * @param jv the variable to be checked
     * @return {@code true} if the variable is defined in this node, otherwise {@code false}
     */
    public boolean defineVariable(JReference jv) {
        return defs.stream().anyMatch(v -> v.equals(jv));
    }
    
    /**
     * Tests if this node uses a given variable.
     * @param jv the variable to be checked
     * @return {@code true} if the variable is used in this node, otherwise {@code false}
     */
    public boolean useVariable(JReference jv) {
        return uses.stream().anyMatch(v -> v.equals(jv));
    }
    
    /**
     * Tests if this node defines any variable.
     * @return {@code true} if there is a defined variable found in this node, otherwise {@code false}
     */
    @Override
    public boolean hasDefVariable() {
        return defs.size() != 0;
    }
    
    /**
     * Tests if this node uses any variable.
     * @return {@code true} if there is a used variable found in this node, otherwise {@code false}
     */
    @Override
    public boolean hasUseVariable() {
        return uses.size() != 0;
    }
    
    /**
     * Returns a variable having a given name from the collection of variables defined in this node.
     * @param name the name of the variable
     * @return the found variable, or {@code null} if no variable is found
     */
    public JReference getDefVariable(String name) {
        return defs.stream().filter(jv -> jv.getName().equals(name)).findFirst().orElse(null);
    }
    
    /**
     * Returns a variable having a given name from the collection of variables used in this node.
     * @param name the name of the variable
     * @return the found variable, or {@code null} if no variable is found
     */
    public JReference getUseVariable(String name) {
        return uses.stream().filter(jv -> jv.getName().equals(name)).findFirst().orElse(null);
    }
    
    /**
     * Returns the first one from the collection of variables defined in this node.
     * @return the defined variable at the first position, or {@code null} if this node never defines any variable
     */
    public JReference getDefFirst() {
        if (hasDefVariable()) {
            return defs.get(0);
        }
        return null;
    }
    
    /**
     * Returns the first one from the collection of variables used in this node.
     * @return the used variable at the first position, or {@code null} if this node never uses any variable
     */
    public JReference getUseFirst() {
        if (hasUseVariable()) {
            return uses.get(0);
        }
        return null;
    }
    
    /**
     * Obtains information on this statement node.
     * @return the string representing the information
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(super.toString());
        buf.append(" D = { " + toStringForVariables(defs) + " }");
        buf.append(" U = { " + toStringForVariables(uses) + " }");
        return buf.toString();
    }
    
    /**
     * Obtains information on variables.
     * @param jvars the set of variables
     * @return the string representing the information
     */
    protected String toStringForVariables(List<JReference> jvars) {
        StringBuffer buf = new StringBuffer();
        jvars.forEach(jvar -> {
            buf.append(jvar.getReferenceForm());
            buf.append(", ");
        });
        return buf.length() != 0 ? buf.substring(0, buf.length() - 2) : "";
    }
}
