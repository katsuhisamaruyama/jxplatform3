/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.cfg.JVariableReference;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

/**
 * A dependence edge of PDGs, ClDGs, and SDGs.
 * 
 * @author Katsuhisa Maruyama
 */
public interface DependencyGraphEdge {
    
    /**
     * Returns the identification number of the source node of this edge.
     * @return the identification number of the source node
     */
    public long getSrcId();
    
    /**
     * Returns the identification number of the destination node of this edge.
     * @return the identification number of the destination node
     */
    public long getDstId();
    
    /**
     * Returns the source node for this edge.
     * @return the source node
     */
    public PDGNode getSrcNode();
    
    /**
     * Returns the destination node for this edge.
     * @return the destination node
     */
    public PDGNode getDstNode();
    
    /**
     * Returns the variable related to this data dependence.
     * The value of a variable defined in the source node reaches that used in the destination node.
     * @return the variable storing the passed value
     */
    default public JVariableReference getVariable() {
        return null;
    }
    
    /**
     * The kind of a dependency graph edge.
     * 
     * @author Katsuhisa Maruyama
     */
    public enum Kind {
        
        /**
         * Representing the control dependence in {@code PDG}, regarding a true-branch flow in {@code CFG}.
         */
        trueControlDependence,
        
        /**
         * Representing the control dependence in {@code PDG}, regarding a false-branch flow in {@code CFG}.
         */
        falseControlDependence,
        
        /**
         * Representing the control dependence in {@code PDG}, regarding a fall-through flow in {@code CFG}.
         */
        fallThroughControlDependence,
        
        /**
         * Representing the control dependence between a declaration and its reference in {@code PDG}.
         */
        declaration,
        
        /**
         * Representing the control dependence regarding an exception-catch within a try statement in {@code PDG}.
         */
        exceptionCatch,
        
        /**
         * Representing the control dependence between a class and its member in {@code PDG}.
         */
        classMember,
        
        /**
         * Representing the control dependence between between a caller and its callee in {@code PDG}.
         */
        call,
        
        /**
         * Representing the data dependence regarding a loop-independent variable in {@code PDG}.
         */
        loopIndependentDefUseDependence,
        
        /**
         * Representing the data dependence regarding a loop-carried variable in {@code PDG}.
         */
        loopCarriedDefUseDependence,
        
        /**
         * Representing the data dependence based on the order of definitions of variables in {@code PDG}.
         */
        defOrderDependence,
        
        /**
         * Representing the data dependence based on the order of outputs of variables in {@code PDG}.
         */
        outputDependence,
        
        /**
         * Representing the data dependence regarding the incoming parameter passing in {@code PDG}.
         */
        parameterIn,
        
        /**
         * Representing the data dependence regarding the outgoing parameter passing in {@code PDG}.
         */
        parameterOut,
        
        /**
         * Representing the data dependence between an actual-in node and an actual-out node in {@code PDG}.
         */
        summary,
        
        /**
         * Representing the data dependence regarding the the field access in {@code PDG}.
         */
        fieldAccess,
        
        /**
         * Representing the data dependence regarding the uncovered field accesses in {@code PDG}.
         */
        uncoveredFieldAccess,
        
        /**
         * Representing the uncategorized dependence.
         */
        undefined,
    }
    
    /**
     * Sets the kind of this dependence edge.
     * This method is not intended to be invoked by clients.
     * @param kind the kind of the dependence edge
     */
    public void setKind(DependencyGraphEdge.Kind kind);
    
    /**
     * Returns the kind of this dependence edge.
     * @return the kind of the edge
     */
    public DependencyGraphEdge.Kind getKind();
    
    /**
     * Tests if this edge represents a control dependence.
     * @return {@code true} if this is a control dependence edge, otherwise {@code false}
     */
    default public boolean isCD() {
        DependencyGraphEdge.Kind kind = getKind();
        return kind == DependencyGraphEdge.Kind.trueControlDependence ||
               kind == DependencyGraphEdge.Kind.falseControlDependence ||
               kind == DependencyGraphEdge.Kind.fallThroughControlDependence ||
               kind == DependencyGraphEdge.Kind.declaration ||
               kind == DependencyGraphEdge.Kind.exceptionCatch ||
               kind == DependencyGraphEdge.Kind.classMember ||
               kind == DependencyGraphEdge.Kind.call;
    }
    
    /**
     * Tests if this edge represents a data dependence.
     * @return {@code true} if this is a data dependence edge, otherwise {@code false}
     */
    default public boolean isDD() {
        DependencyGraphEdge.Kind kind = getKind();
        return kind == DependencyGraphEdge.Kind.loopIndependentDefUseDependence ||
               kind == DependencyGraphEdge.Kind.loopCarriedDefUseDependence ||
               kind == DependencyGraphEdge.Kind.defOrderDependence ||
               kind == DependencyGraphEdge.Kind.outputDependence ||
               kind == DependencyGraphEdge.Kind.parameterIn ||
               kind == DependencyGraphEdge.Kind.parameterOut ||
               kind == DependencyGraphEdge.Kind.fieldAccess ||
               kind == DependencyGraphEdge.Kind.uncoveredFieldAccess ||
               kind == DependencyGraphEdge.Kind.summary;
    }
    
    /**
     * Tests if this edge represents a dependence.
     * @return {@code true} if this is a dependence edge, otherwise {@code false}
     */
    default public boolean isDependence() {
        return false;
    }
    
    /**
     * Tests if this edge represents an edge connecting two nodes in different PDGs.
     * @return {@code true} if this is a connecting edge, otherwise {@code false}
     */
    default public boolean isInterPDGEdge() {
        return false;
    }
    
    /**
     * Tests if this edge represents a true control dependence.
     * @return {@code true} if this is a true control dependence edge, otherwise {@code false}
     */
    default public boolean isTrue() {
        DependencyGraphEdge.Kind kind = getKind();
        return kind == DependencyGraphEdge.Kind.trueControlDependence;
    }
    
    /**
     * Tests if this edge represents a false control dependence.
     * @return {@code true} if this is a false control dependence edge, otherwise {@code false}
     */
    default public boolean isFalse() {
        DependencyGraphEdge.Kind kind = getKind();
        return kind == DependencyGraphEdge.Kind.falseControlDependence;
    }
    
    /**
     * Tests if this edge represents a fall-through control dependence.
     * @return {@code true} if this is a fall-through control dependence edge, otherwise {@code false}
     */
    default public boolean isFallThrough() {
        DependencyGraphEdge.Kind kind = getKind();
        return kind == DependencyGraphEdge.Kind.fallThroughControlDependence;
    }
    
    /**
     * Tests if this edge represents a declaration dependence.
     * The defined or used variable needs its declaration.
     * @return {@code true} if this is a declaration dependence edge, otherwise {@code false}
     */
    default public boolean isDeclaration() {
        DependencyGraphEdge.Kind kind = getKind();
        return kind == DependencyGraphEdge.Kind.declaration;
    }
    
    /**
     * Tests if this edge represents an exception dependence.
     * The execution of a statement sometimes depends on the occurrence of an exception.
     * @return {@code true} if this is an exception dependence edge, otherwise {@code false}
     */
    default public boolean isExceptionCatch() {
        DependencyGraphEdge.Kind kind = getKind();
        return kind == DependencyGraphEdge.Kind.exceptionCatch;
    }
    
    /**
     * Tests if this edge represents a class-member dependence.
     * @return {@code true} if this is a class-member dependence edge, otherwise {@code false}
     */
    default public boolean isClassMember() {
        DependencyGraphEdge.Kind kind = getKind();
        return kind == DependencyGraphEdge.Kind.classMember;
    }
    
    /**
     * Tests if this edge represents a method call dependence.
     * The caller is needed to execute a callee.
     * @return {@code true} if this is a method call dependence edge, otherwise {@code false}
     */
    default public boolean isCall() {
        DependencyGraphEdge.Kind kind = getKind();
        return kind == DependencyGraphEdge.Kind.call;
    }
    
    /**
     * Tests if this edge represents a define-use dependence.
     * @return {@code true} if this is a define-use dependence, otherwise {@code false}
     */
    default public boolean isDefUse() {
        return isLIDD() || isLCDD();
    }
    
    /**
     * Tests if this edge represents a loop-independent data dependence.
     * The data dependence occurs when not passing a loop-back control flow.
     * @return {@code true} if this is a loop-independent data dependence edge, otherwise {@code false}
     */
    default public boolean isLIDD() {
        DependencyGraphEdge.Kind kind = getKind();
        return kind == DependencyGraphEdge.Kind.loopIndependentDefUseDependence;
    }
    
    /**
     * Tests if this edge represents a loop-carried data dependence.
     * The data dependence occurs when passing a loop-back control flow.
     * @return {@code true} if this is a loop-carried data dependence edge, otherwise {@code false}
     */
    default public boolean isLCDD() {
        DependencyGraphEdge.Kind kind = getKind();
        return kind == DependencyGraphEdge.Kind.loopCarriedDefUseDependence;
    }
    
    /**
     * Tests if this edge represents a define-order dependence when the two nodes are dominated the same node.
     * @return {@code true} if this is a define-order dependence edge, otherwise {@code false}
     */
    default public boolean isDefOrder() {
        DependencyGraphEdge.Kind kind = getKind();
        return kind == DependencyGraphEdge.Kind.defOrderDependence;
    }
    
    /**
     * Tests if this edge represents an output dependence.
     * @return {@code true} if this is an output dependence edge, otherwise {@code false}
     */
    default public boolean isOutput() {
        DependencyGraphEdge.Kind kind = getKind();
        return kind == DependencyGraphEdge.Kind.outputDependence;
    }
    
    /**
     * Tests if this edge represents a parameter-in dependence.
     * The value of an argument is passed to a parameter of a method.
     * @return {@code true} if this is a parameter-in dependence edge, otherwise {@code false}
     */
    default public boolean isParameterIn() {
        DependencyGraphEdge.Kind kind = getKind();
        return kind == DependencyGraphEdge.Kind.parameterIn;
    }
    
    /**
     * Tests if this edge represents a parameter-out dependence.
     * The resulting value of a method is return to a caller.
     * @return {@code true} if this is a parameter-out dependence edge, otherwise {@code false}
     */
    default public boolean isParameterOut() {
        DependencyGraphEdge.Kind kind = getKind();
        return kind == DependencyGraphEdge.Kind.parameterOut;
    }
    
    /**
     * Tests if this edge represents a field access dependence.
     * @return {@code true} if this is a field access dependence edge, otherwise {@code false}
     */
    default public boolean isFieldAccess() {
        DependencyGraphEdge.Kind kind = getKind();
        return kind == DependencyGraphEdge.Kind.fieldAccess;
    }
    
    /**
     * Tests if this edge represents a summary data dependence.
     * The summary dependence edges are created as needed.
     * @return {@code true} if this is a summary data dependence edge, otherwise {@code false}
     */
    default public boolean isSummary() {
        DependencyGraphEdge.Kind kind = getKind();
        return kind == DependencyGraphEdge.Kind.summary;
    }
    
    /**
     * Tests if this edge represents an uncovered field access dependence.
     * @return {@code true} if this is an uncovered field access dependence edge, otherwise {@code false}
     */
    default public boolean isUncoveredFieldAccess() {
        DependencyGraphEdge.Kind kind = getKind();
        return kind == DependencyGraphEdge.Kind.uncoveredFieldAccess;
    }
    
    /**
     * Sorts the list of edges.
     * @param collection the list of the edges to be sorted
     * @return the sorted list of the edges
     */
    public static List<DependencyGraphEdge> sortEdges(Collection<? extends DependencyGraphEdge> collection) {
        List<DependencyGraphEdge> edges = new ArrayList<>(collection);
        edges.sort(Comparator.comparingLong((DependencyGraphEdge edge) -> edge.getSrcId())
                             .thenComparingLong((DependencyGraphEdge edge) -> edge.getDstId())
                             .thenComparing((DependencyGraphEdge edge) -> edge.getKind().toString()));
        return edges;
    }
    
    /**
     * Sorts the list of dependence edges in reverse order.
     * @param collection the list of the dependence edges to be sorted
     * @return the sorted list of the dependence edges
     */
    public static List<DependencyGraphEdge> sortEdgesReverse(Collection<? extends DependencyGraphEdge> collection) {
        List<DependencyGraphEdge> edges = new ArrayList<>(collection);
        edges.sort(Comparator.comparingLong((DependencyGraphEdge edge) -> edge.getSrcId())
                             .thenComparingLong((DependencyGraphEdge edge) -> edge.getDstId())
                             .thenComparing((DependencyGraphEdge edge) -> edge.getKind().toString()).reversed());
        return edges;
    }
}
