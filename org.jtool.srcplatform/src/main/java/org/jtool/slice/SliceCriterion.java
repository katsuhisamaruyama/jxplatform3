/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.slice;

import org.jtool.pdg.CommonPDG;
import org.jtool.pdg.PDGNode;
import org.jtool.pdg.PDGStatement;
import org.jtool.cfg.JReference;
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
     * The PDG to be sliced.
     */
    private CommonPDG pdg;
    
    /**
     * The node a node for this slicing criterion.
     */
    private PDGNode node;
    
    /**
     * The collection of variables for this slicing criterion.
     */
    private Set<JReference> variables = new HashSet<>();
    
    /**
     * Creates a new object that represents a slicing criterion.
     * @param pdg the the PDG to be sliced
     * @param node a node for the slicing criterion
     * @param var a variables for the slicing criterion
     */
    public SliceCriterion(CommonPDG pdg, PDGNode node, JReference var) {
        this.pdg = pdg;
        this.node = node;
        variables.add(var);
    }
    
    /**
     * Creates a new object that represents a slicing criterion.
     * @param pdg the the PDG to be sliced
     * @param node a node for this slicing criterion
     * @param vars the collection of variables for this slicing criterion
     */
    public SliceCriterion(CommonPDG pdg, PDGNode node, Set<JReference> vars) {
        this.pdg = pdg;
        this.node = node;
        vars.stream().filter(var -> var.isVariableAccess()).forEach(var -> variables.add(var));
    }
    
    /**
     * Returns the PDG to be sliced.
     * @return the PDG
     */
    public CommonPDG getPDG() {
        return pdg;
    }
    
    /**
     * Returns the node for this slicing criterion.
     * @return the node
     */
    public PDGNode getNode() {
        return node;
    }
    
    /**
     * Returns the variable for this slicing criterion.
     * @return the variable
     */
    public Set<JReference> getVariables() {
        return variables;
    }
    
    /**
     * Finds the slicing criterion on a given PDG at a given position at its source code.
     * @param pdg the PDG to be sliced
     * @param code the contents of the source code for the PDG
     * @param lineNumber the line number of the location where a variable of interest 
     * @param columnNumber the column number of the location where a variable of interest 
     * @return the slicing criterion
     */
    public static SliceCriterion find(CommonPDG pdg, String code, int lineNumber, int columnNumber) {
        String[] lines = code.split(System.getProperty("line.separator"));
        int position = 0;
        for (int line = 0; line < lineNumber - 1; line++) {
            position = position + lines[line].length() + 1;
        }
        position = position + columnNumber;
        return find(pdg, position);
    }
    
    /**
     * Finds the slicing criterion on a given PDG at a given position at its source code.
     * @param pdg the PDG to be sliced
     * @param node the AST node for a variable of interest on the source code for the PDG
     * @return the slicing criterion
     */
    public static SliceCriterion find(CommonPDG pdg, ASTNode node) {
        return find(pdg, node.getStartPosition());
    }
    
    /**
     * Finds the slicing criterion on a given PDG at a given position at its source code.
     * @param pdg the PDG to be sliced
     * @param position the position of a variable of interest on the source code for the PDG
     * @return the slicing criterion
     */
    public static SliceCriterion find(CommonPDG pdg, int position) {
        for (PDGNode node : pdg.getNodes()) {
            if (node.isStatement() && !node.getCFGNode().isActualOut()) {
                PDGStatement stnode = (PDGStatement)node;
                for (JReference def : stnode.getDefVariables()) {
                    if (def.isVisible() && position == def.getStartPosition()) {
                        return new SliceCriterion(pdg, stnode, def);
                    }
                }
                for (JReference use : stnode.getUseVariables()) {
                    if (use.isVisible() && position == use.getStartPosition()) {
                        return new SliceCriterion(pdg, stnode, use);
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
        buf.append("\n");
        return buf.toString();
    }
    
    private String getVariableNames(Set<JReference> vars) {
        if (vars.size() == 0) {
            return "Unspecified";
        }
        StringBuilder buf = new StringBuilder();
        vars.forEach(var -> buf.append(" " + var.getName() + "@" + var.getASTNode().getStartPosition()));
        return buf.toString();
    }
}
