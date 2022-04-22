/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg.builder;

import org.jtool.cfg.CFG;
import org.jtool.graph.Graph;
import org.jtool.pdg.CD;
import org.jtool.pdg.DD;
import org.jtool.pdg.Dependence;
import org.jtool.pdg.PDGNode;
import org.jtool.srcmodel.QualifiedName;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An object storing bare information on a program dependence graph (PDG).
 * 
 * @author Katsuhisa Maruyama
 */
public class BarePDG extends Graph<PDGNode, Dependence> {
    
    private CFG cfg;
    
    public BarePDG(CFG cfg) {
        this.cfg = cfg;
    }
    
    public CFG getCFG() {
        return cfg;
    }
    
    public QualifiedName getQualifiedName() {
        return cfg.getQualifiedName();
    }
    
    Set<Dependence> getIncomingDependenceEdges(PDGNode node) {
        return node.getIncomingEdges().stream()
                   .map(edge -> (Dependence)edge)
                   .collect(Collectors.toSet());
    }
    
    Set<CD> getIncomingCDEdges(PDGNode node) {
        return getIncomingDependenceEdges(node).stream()
                                               .filter(edge -> edge.isCD())
                                               .map(edge -> (CD)edge)
                                               .collect(Collectors.toSet());
    }
    
    Set<DD> getIncomingDDEdges(PDGNode node) {
        return getIncomingDependenceEdges(node).stream()
                                               .filter(edge -> edge.isDD())
                                               .map(edge -> (DD)edge)
                                               .collect(Collectors.toSet());
    }
    
    Set<Dependence> getOutgoingDependenceEdges(PDGNode node) {
        return node.getOutgoingEdges().stream()
                   .map(edge -> (Dependence)edge)
                   .collect(Collectors.toSet());
    }
    
    Set<CD> getOutgoingCDEdges(PDGNode node) {
        return getOutgoingDependenceEdges(node).stream()
                                               .filter(edge -> edge.isCD())
                                               .map(edge -> (CD)edge)
                                               .collect(Collectors.toSet());
    }
    
    public Set<DD> getOutgoingDDEdges(PDGNode node) {
        return getOutgoingDependenceEdges(node).stream()
                                               .filter(edge -> edge.isDD())
                                               .map(edge -> (DD)edge)
                                               .collect(Collectors.toSet());
    }
}
