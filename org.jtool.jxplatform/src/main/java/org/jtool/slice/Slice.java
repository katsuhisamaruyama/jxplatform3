/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.slice;

import org.jtool.slice.builder.Slicer;
import org.jtool.pdg.DependencyGraph;
import org.jtool.pdg.PDGNode;
import org.jtool.cfg.JVariableReference;
import org.jtool.graph.GraphNode;
import java.util.Set;
import java.util.HashSet;

/**
 * An object storing information about a program slice.
 * 
 * @author Katsuhisa Maruyama
 */
public class Slice {
    
    private SliceCriterion criterion;
    
    private Set<PDGNode> nodes = new HashSet<>();
    
    public Slice(SliceCriterion criterion) {
        this.criterion = criterion;
        
        Slicer slicer = new Slicer(criterion);
        nodes = slicer.getNodes();
    }
    
    /**
     * Returns a dependency graph containing the target PDG.
     * @return the dependency graph
     */
    public DependencyGraph getDependencyGraph() {
        return criterion.getDependencyGraph();
    }
    
    public PDGNode getCriterionNode() {
        return criterion.getNode();
    }
    
    public Set<JVariableReference> getCriterionVariables() {
        return criterion.getVariables();
    }
    
    public Set<PDGNode> getNodes() {
        return nodes;
    }
    
    public void print() {
        System.out.println(toString());
    }
    
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("----- Slice (from here) -----\n");
        buf.append(criterion.toString());
        buf.append(getNodeInfo());
        buf.append("----- Slice (to here) -----\n");
        return buf.toString();
    }
    
    private String getNodeInfo() {
        StringBuilder buf = new StringBuilder();
        GraphNode.sortGraphNode(nodes).forEach(node -> {
            PDGNode pdgnode = (PDGNode)node;
            buf.append(node.toString());
            if (pdgnode.getCFGNode().getASTNode() != null) {
                buf.append(" offset=" + pdgnode.getCFGNode().getASTNode().getStartPosition());
            }
            buf.append("\n");
        });
        return buf.toString();
    }
}
