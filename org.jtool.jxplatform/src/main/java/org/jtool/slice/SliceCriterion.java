/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.slice;

import org.jtool.pdg.DependencyGraph;
import org.jtool.pdg.ClDG;
import org.jtool.pdg.PDG;
import org.jtool.pdg.PDGNode;
import org.jtool.pdg.PDGStatement;
import org.jtool.srcmodel.JavaClass;
import org.jtool.cfg.JVariableReference;
import java.util.Set;
import java.util.HashSet;

/**
 * An object that represents a slicing criterion.
 * 
 * @author Katsuhisa Maruyama
 */
public class SliceCriterion {
    
    /**
     * The whole dependency graph to be sliced, which is either PDG, ClDG, or SDG.
     */
    private DependencyGraph graph;
    
    /**
     * A node defining and/or using variables of interest.
     */
    private PDGNode node;
    
    /**
     * The collection of variables of interest.
     */
    private Set<JVariableReference> variables = new HashSet<>();
    
    /**
     * Creates a new object that represents a slicing criterion.
     * @param graph the whole dependency graph to be sliced
     * @param node a target node for the slicing criterion
     * @param var a target variable for the slicing criterion
     */
    public SliceCriterion(DependencyGraph graph, PDGNode node, JVariableReference var) {
        this.graph = graph;
        this.node = node;
        if (var.isVariableAccess()) {
            variables.add(var);
        }
    }
    
    /**
     * Creates a new object that represents a slicing criterion.
     * @param graph the whole dependency graph to be sliced
     * @param node a target node for the slicing criterion
     * @param vars the collection of target variables for this slicing criterion
     */
    public SliceCriterion(DependencyGraph graph, PDGNode node, Set<JVariableReference> vars) {
        this.graph = graph;
        this.node = node;
        vars.stream().filter(var -> var.isVariableAccess()).forEach(var -> variables.add(var));
    }
    
    /**
     * Returns the whole dependency graph to be sliced.
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
     * Returns the collection of target variables for this slicing criterion.
     * @return the variables
     */
    public Set<JVariableReference> getVariables() {
        return variables;
    }
    
    /**
     * Finds a slicing criterion on a target variable at a given position on the source code.
     * @param graph the whole dependency graph to be sliced
     * @param jclass a class containing a target variable
     * @param lineNumber the line number of the location where a target variable exists
     * @param columnNumber the column number of the location where a target variable exists
     * @return the slicing criterion, or {@code null} if a valid slice criterion is not found
     */
    public static SliceCriterion find(DependencyGraph graph, JavaClass jclass, int lineNumber, int columnNumber) {
        Set<PDGNode> nodes = new HashSet<>();
        graph.getPDGs().stream()
                .filter(g -> g.getQualifiedName().getClassName().equals(jclass.getQualifiedName().getClassName()))
                .forEach(g -> nodes.addAll(g.getNodes()));
        
        String code = jclass.getFile().getSource();
        String[] lines = code.split(System.getProperty("line.separator"));
        int position = 0;
        for (int line = 0; line < lineNumber - 1; line++) {
            position = position + lines[line].length() + 1;
        }
        position = position + columnNumber;
        
        return find(graph, nodes, position);
    }
    
    /**
     * Finds a slicing criterion on a target variable at a given position on the source code.
     * @param graph the whole dependency graph to be sliced
     * @param pdg a PDG containing the target variable
     * @param position the offset value indicating the position where the target variable exists
     * @return the slicing criterion, or {@code null} if a valid slice criterion is not found
     */
    public static SliceCriterion find(DependencyGraph graph, PDG pdg, int position) {
        return find(graph, pdg.getNodes(), position);
    }
    
    /**
     * Finds a slicing criterion on a target variable at a given position on the source code.
     * @param graph the whole dependency graph to be sliced
     * @param cldg a ClDG containing the target variable
     * @param position the offset value indicating the position where the target variable exists
     * @return the slicing criterion, or {@code null} if a valid slice criterion is not found
     */
    public static SliceCriterion find(DependencyGraph graph, ClDG cldg, int position) {
        return find(graph, cldg.getNodes(), position);
    }
    
    /**
     * Finds a slicing criterion on a target variable at a given position on the source code.
     * @param graph the whole dependency graph to be sliced
     * @param nodes the collection of nodes that may define and/or use the target variable
     * @param position the offset value indicating the position where the target variable exists
     * @return the slicing criterion, or {@code null} if a valid slice criterion is not found
     */
    public static SliceCriterion find(DependencyGraph graph, Set<PDGNode> nodes, int position) {
        for (PDGNode node : nodes) {
            if (node.getCFGNode().isActualOut()) {
                continue;
            }
            
            if (node.isStatement()) {
                PDGStatement stnode = (PDGStatement)node;
                if (node.getCFGNode().isMethodCall() && position == node.getCFGNode().getASTNode().getStartPosition()) {
                    Set<JVariableReference> vars = new HashSet<>();
                    vars.addAll(stnode.getDefVariables());
                    vars.addAll(stnode.getUseVariables());
                    return new SliceCriterion(graph, node, vars);
                } else {
                    for (JVariableReference def : stnode.getDefVariables()) {
                        if (def.isAvailable() && position == def.getStartPosition()) {
                            return new SliceCriterion(graph, node, def);
                        }
                    }
                    for (JVariableReference use : stnode.getUseVariables()) {
                        if (use.isAvailable() && position == use.getStartPosition()) {
                            return new SliceCriterion(graph, node, use);
                        }
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
