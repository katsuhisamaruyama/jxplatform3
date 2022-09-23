/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.slice;

import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaField;
import org.jtool.pdg.DependencyGraph;
import org.jtool.pdg.PDGNode;
import org.jtool.cfg.JVariableReference;
import org.jtool.codemanipulation.CodeExtractor;
import org.jtool.graph.GraphNode;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;

/**
 * An object storing information about a program slice.
 * 
 * @author Katsuhisa Maruyama
 */
public class Slice {
    
    /**
     * A slicing criterion that derives this slice.
     */
    private SliceCriterion criterion;
    
    /**
     * The collection of PDG nodes that contained in this slice.
     */
    private Set<PDGNode> nodes = new HashSet<>();
    
    /**
     * Creates a new object that represents a slice.
     * @param criterion a slicing criterion that derives this slice.
     */
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
    
    /**
     * Returns a target node for this slicing criterion.
     * @return the node
     */
    public PDGNode getCriterionNode() {
        return criterion.getNode();
    }
    
    /**
     * Returns the collection of target variables for this slicing criterion.
     * @return the variables
     */
    public Set<JVariableReference> getCriterionVariables() {
        return criterion.getVariables();
    }
    
    /**
     * Obtains PDG nodes that contained in this slice.
     * @return the collection of the PDG nodes
     */
    public Set<PDGNode> getNodes() {
        return nodes;
    }
    
    /**
     * Obtains source code that constitutes this slice.
     * @param jclass a program element that encloses the source code
     * @return the contents of the source code
     */
    public String getCode(JavaClass jclass) {
        CodeExtractor extractor = new CodeExtractor(jclass, nodes);
        return extractor.extract();
    }
    
    /**
     * Obtains source code that constitutes this slice.
     * @param jclass a program element that encloses the source code
     * @param options the options for formatting the source code
     * @return the contents of the source code
     */
    public String getCode(JavaClass jclass, Map<String, String> options) {
        CodeExtractor extractor = new CodeExtractor(jclass, nodes);
        return extractor.extract(options);
    }
    
    /**
     * Obtains source code that constitutes this slice.
     * @param jmethod a program element that encloses the source code
     * @return the contents of the source code
     */
    public String getCode(JavaMethod jmethod) {
        CodeExtractor extractor = new CodeExtractor(jmethod, nodes);
        return extractor.extract();
    }
    
    /**
     * Obtains source code that constitutes this slice.
     * @param jmethod a program element that encloses the source code
     * @param options the options for formatting the source code
     * @return the contents of the source code
     */
    public String getCode(JavaMethod jmethod, Map<String, String> options) {
        CodeExtractor extractor = new CodeExtractor(jmethod, nodes);
        return extractor.extract(options);
    }
    
    /**
     * Obtains source code that constitutes this slice.
     * @param jfield a program element that encloses the source code
     * @return the contents of the source code
     */
    public String getCode(JavaField jfield) {
        CodeExtractor extractor = new CodeExtractor(jfield, nodes);
        return extractor.extract();
    }
    
    /**
     * Obtains source code that constitutes this slice.
     * @param jfield a program element that encloses the source code
     * @param options the options for formatting the source code
     * @return the contents of the source code
     */
    public String getCode(JavaField jfield, Map<String, String> options) {
        CodeExtractor extractor = new CodeExtractor(jfield, nodes);
        return extractor.extract(options);
    }
    /**
     * Displays information on this slice.
     */
    public void print() {
        System.out.println(toString());
    }
    
    /**
     * Obtains information on this slice.
     * @return the string representing the information on this slice
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("----- Slice (from here) -----\n");
        buf.append(criterion.toString());
        buf.append(getNodeInfo());
        buf.append("----- Slice (to here) -----\n");
        return buf.toString();
    }
    
    /**
     * Obtains information on all nodes contained in this slice.
     * @return the string representing the information
     */
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
