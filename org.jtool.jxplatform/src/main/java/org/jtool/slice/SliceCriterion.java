/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.slice;

import org.jtool.pdg.DependencyGraph;
import org.jtool.pdg.ClDG;
import org.jtool.pdg.PDGNode;
import org.jtool.pdg.PDGStatement;
import org.jtool.cfg.JVariableReference;
import org.eclipse.jdt.core.dom.ASTNode;
import java.util.Set;
import java.util.HashSet;

/**
 * An object that represents a slicing criterion.
 * 
 * @author Katsuhisa Maruyama
 */
public class SliceCriterion {
    
    /**
     * A dependency graph to be sliced.
     */
    private DependencyGraph graph;
    
    /**
     * A target node for this slicing criterion.
     */
    private PDGNode node;
    
    /**
     * The collection of target variables for this slicing criterion.
     */
    private Set<JVariableReference> variables = new HashSet<>();
    
    /**
     * Creates a new object that represents a slicing criterion.
     * @param graph a dependency graph to be sliced
     * @param node a target node for the slicing criterion
     * @param var a target variables for the slicing criterion
     */
    public SliceCriterion(DependencyGraph graph, PDGNode node, JVariableReference var) {
        this.graph = graph;
        this.node = node;
        variables.add(var);
    }
    
    /**
     * Creates a new object that represents a slicing criterion.
     * @param graph a dependency graph to be sliced
     * @param node a target node for this slicing criterion
     * @param vars the collection of target variables for this slicing criterion
     */
    public SliceCriterion(DependencyGraph graph, PDGNode node, Set<JVariableReference> vars) {
        this.graph = graph;
        this.node = node;
        vars.stream().filter(var -> var.isVariableAccess()).forEach(var -> variables.add(var));
    }
    
    /**
     * Returns a dependency graph to be sliced.
     * @return the dependency graph
     */
    public DependencyGraph getDependencyGraph() {
        return graph;
    }
    
    /**
     * Returns a target node for this slicing criterion.
     * @return the node
     */
    public PDGNode getNode() {
        return node;
    }
    
    /**
     * Returns a target variable for this slicing criterion.
     * @return the variable
     */
    public Set<JVariableReference> getVariables() {
        return variables;
    }
    
    /**
     * Finds the slicing criterion on a given PDG at a given position at its source code.
     * @param cldg a ClDG to be sliced
     * @param code the contents of the source code for the target PDG
     * @param lineNumber the line number of the location where the target variable of interest
     * @param columnNumber the column number of the location where the target variable of interest
     * @return the slicing criterion
     */
    public static SliceCriterion find(ClDG cldg, 
            String code, int lineNumber, int columnNumber) {
        String[] lines = code.split(System.getProperty("line.separator"));
        int position = 0;
        for (int line = 0; line < lineNumber - 1; line++) {
            position = position + lines[line].length() + 1;
        }
        position = position + columnNumber;
        return find(cldg, position);
    }
    
    /**
     * Finds the slicing criterion on a given PDG at a given position at its source code.
     * @param cldg a ClDG to be sliced
     * @param node the AST node for a variable of interest on the source code
     * @return the slicing criterion
     */
    public static SliceCriterion find(ClDG cldg, ASTNode node) {
        return find(cldg, node.getStartPosition());
    }
    
    /**
     * Finds the slicing criterion on a given PDG at a given position at its source code.
     * @param cldg a ClDG to be sliced
     * @param position the position of the target variable of interest on the source code
     * @param graph a dependency graph containing the sliced PDG
     * @return the slicing criterion
     */
    public static SliceCriterion find(ClDG cldg, int position) {
        for (PDGNode node : cldg.getNodes()) {
            if (node.isStatement() && !node.getCFGNode().isActualOut()) {
                PDGStatement stnode = (PDGStatement)node;
                for (JVariableReference def : stnode.getDefVariables()) {
                    if (def.isTouchable() && position == def.getStartPosition()) {
                        return new SliceCriterion(cldg, stnode, def);
                    }
                }
                for (JVariableReference use : stnode.getUseVariables()) {
                    if (use.isTouchable() && position == use.getStartPosition()) {
                        return new SliceCriterion(cldg, stnode, use);
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * Obtains information on this slicing criterion.
     * @return the string representing the information
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("Node = " + node.getId());
        buf.append(";");
        buf.append(" Variable =" + getVariableNames(variables));
        buf.append(" on " + graph.getQualifiedName().fqn());
        buf.append("\n");
        return buf.toString();
    }
    
    private String getVariableNames(Set<JVariableReference> vars) {
        if (vars.size() == 0) {
            return "Unspecified";
        }
        StringBuilder buf = new StringBuilder();
        vars.forEach(var -> buf.append(" " + var.getName() + "@" + var.getASTNode().getStartPosition()));
        return buf.toString();
    }
}
