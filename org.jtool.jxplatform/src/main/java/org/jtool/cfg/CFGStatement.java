/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.ReturnStatement;
import java.util.List;
import java.util.stream.Collectors;
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
    protected List<JVariableReference> defs = new ArrayList<>();
    
    /**
     * The collection of variables used in this node.
     */
    protected List<JVariableReference> uses = new ArrayList<>();
    
    /**
     * Creates a new object that represents a statement.
     * This method is not intended to be invoked by clients.
     * @param node the AST node corresponding to this node
     * @param kind the kind of this node
     */
    public CFGStatement(ASTNode node, Kind kind) {
        super(node, kind);
    }
    
    /**
     * Adds a variable to the collection of variables defined in this node.
     * This method is not intended to be invoked by clients.
     * @param jv the variable to be added
     */
    public void addDefVariable(JVariableReference jv) {
        if (jv != null && !defineVariable(jv)) {
            defs.add(jv);
        }
    }
    
    /**
     * Adds a variable to the collection of variables used in this node.
     * This method is not intended to be invoked by clients.
     * @param jv the variable to be added
     */
    public void addUseVariable(JVariableReference jv) {
        if (jv != null && !useVariable(jv)) {
            uses.add(jv);
        }
    }
    
    /**
     * Adds variables to the collection of variables defined in this node.
     * This method is not intended to be invoked by clients.
     * @param jvs the collection of the variables to be added
     */
    public void addDefVariables(List<JVariableReference> jvs) {
        jvs.forEach(jv -> addDefVariable(jv));
    }
    
    /**
     * Adds variables to the collection of variables used in this node.
     * This method is not intended to be invoked by clients.
     * @param jvs the collection of the variables to be added
     */
    public void addUseVariables(List<JVariableReference> jvs) {
        jvs.forEach(jv -> addUseVariable(jv));
    }
    
    /**
     * Removes a variable from the collection of variables defined in this node.
     * This method is not intended to be invoked by clients.
     * @param jv the collection of the variables to be removed
     * @return {@code true} if the removal is successfully done, otherwise {@code false}
     */
    public boolean removeDefVariable(JReference jv) {
        return defs.remove(jv);
    }
    
    /**
     * Removes a variable from the collection of variables used in this node.
     * This method is not intended to be invoked by clients.
     * @param jv the collection of the variables to be removed
     * @return {@code true} if the removal is successfully done, otherwise {@code false}
     */
    public boolean removeUseVariable(JReference jv) {
        return uses.remove(jv);
    }
    
    /**
     * Clears the collection of variables defined in this node.
     * This method is not intended to be invoked by clients.
     */
    public void clearDefVariables() {
        defs.clear();
    }
    
    /**
     * Clears the collection of variables defined in this node.
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
    public void setDefVariables(List<JVariableReference> jvs) {
        defs = jvs;
    }
    
    /**
     * Sets the collection of variables used in this node.
     * This method is not intended to be invoked by clients.
     * @param jvs the collection of the variables to be set
     */
    public void setUseVariables(List<JVariableReference> jvs) {
        uses = jvs;
    }
    
    /**
     * Sets the collection that stores a variable defined in this node.
     * This method is not intended to be invoked by clients.
     * @param jv the variable to be set
     */
    public void setDefVariable(JVariableReference jv) {
        clearDefVariables();
        addDefVariable(jv);
    }
    
    /**
     * Sets the collection that stores a variable used in this node.
     * This method is not intended to be invoked by clients.
     * @param jv the variable to be set
     */
    public void setUseVariable(JVariableReference jv) {
        clearUseVariables();
        addUseVariable(jv);
    }
    
    /**
     * Returns variables defined in this node.
     * @return the collection of the defined variables
     */
    public List<JVariableReference> getDefVariables() {
        return defs;
    }
    
    /**
     * Returns variables used in this node.
     * @return the collection of the used variables
     */
    public List<JVariableReference> getUseVariables() {
        return uses;
    }
    
    /**
     * Tests if this node defines a given variable.
     * @param jv the variable to be checked
     * @return {@code true} if the variable is defined in this node, otherwise {@code false}
     */
    public boolean defineVariable(JVariableReference jv) {
        return defs.contains(jv);
    }
    
    /**
     * Tests if this node uses a given variable.
     * @param jv the variable to be checked
     * @return {@code true} if the variable is used in this node, otherwise {@code false}
     */
    public boolean useVariable(JVariableReference jv) {
        return uses.contains(jv);
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
    public JVariableReference getDefVariable(String name) {
        return defs.stream().filter(jv -> jv.getName().equals(name)).findFirst().orElse(null);
    }
    
    /**
     * Returns a variable having a given name from the collection of variables used in this node.
     * @param name the name of the variable
     * @return the found variable, or {@code null} if no variable is found
     */
    public JVariableReference getUseVariable(String name) {
        return uses.stream().filter(jv -> jv.getName().equals(name)).findFirst().orElse(null);
    }
    
    /**
     * Returns the first one from the collection of variables defined in this node.
     * @return the defined variable at the first position, or {@code null} if this node never defines any variable
     */
    public JVariableReference getDefFirst() {
        if (hasDefVariable()) {
            return defs.get(0);
        }
        return null;
    }
    
    /**
     * Returns the first one from the collection of variables used in this node.
     * @return the used variable at the first position, or {@code null} if this node never uses any variable
     */
    public JVariableReference getUseFirst() {
        if (hasUseVariable()) {
            return uses.get(0);
        }
        return null;
    }
    
    /**
     * Returns the last one from the collection of variables defined in this node.
     * @return the defined variable at the last position, or {@code null} if this node never defines any variable
     */
    public JVariableReference getDefLast() {
        if (hasDefVariable()) {
            return defs.get(defs.size() - 1);
        }
        return null;
    }
    
    /**
     * Returns the last one from the collection of variables used in this node.
     * @return the used variable at the last position, or {@code null} if this node never uses any variable
     */
    public JVariableReference getUseLast() {
        if (hasUseVariable()) {
            return uses.get(uses.size() - 1);
        }
        return null;
    }
    
    /**
     * Obtains primary variables used in this assignment node.
     * @return the collection of the primary variables
     */
    public List<JVariableReference> findPrimaryUseVariables() {
        Expression expr = null;
        if (astNode instanceof VariableDeclarationFragment) {
            expr = ((VariableDeclarationFragment)astNode).getInitializer();
        } else if (astNode instanceof Assignment) {
            expr = ((Assignment)astNode).getRightHandSide();
        } else if (astNode instanceof ReturnStatement) {
            expr = ((ReturnStatement)astNode).getExpression();
        } else if (astNode.getParent() instanceof MethodInvocation) {
            expr = ((MethodInvocation)astNode.getParent()).getExpression();
        } else if (astNode.getParent() instanceof ClassInstanceCreation) {
            expr = ((ClassInstanceCreation)astNode.getParent()).getExpression();
        }
        return findPrimaryUseVariables(expr);
    }
    
    /**
     * Obtains primary variables used in this node.
     * @param expr the expression that contains used variables
     * @return the collection of the primary variables
     */
    public List<JVariableReference> findPrimaryUseVariables(Expression expr) {
        List <JVariableReference> vars = new ArrayList<>();
        if (expr == null) {
            return vars;
        }
        
        List<Expression> exprs = new ArrayList<>();
        if (expr instanceof ArrayAccess) {
            exprs.add(((ArrayAccess)expr).getArray());
        } else if (expr instanceof FieldAccess || expr instanceof Name) {
            exprs.add(expr);
        } else if (expr instanceof MethodInvocation ||
                   expr instanceof SuperMethodInvocation ||
                   expr instanceof ClassInstanceCreation) {
            exprs.add(expr);
        } else if (expr instanceof ConditionalExpression) {
            exprs.add(((ConditionalExpression)expr).getThenExpression());
            exprs.add(((ConditionalExpression)expr).getElseExpression());
        } else {
            return vars;
        }
        
        for (Expression e : exprs) {
            uses.stream()
                .filter(v -> v.getASTNode().equals(e))
                .findFirst().ifPresent(v -> vars.add(v));
        }
        return vars;
    }
    
    /**
     * {@inheritDoc}
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
    protected String toStringForVariables(List<JVariableReference> jvars) {
        return jvars.stream().map(e -> e.getReferenceForm()).collect(Collectors.joining(", "));
    }
}
