/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.graph.GraphEdge;
import org.jtool.graph.GraphElement;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * A dependence edge of PDGs, ClDGs, and SDGs.
 * 
 * @author Katsuhisa Maruyama
 */
public class Dependence extends GraphEdge {
    
    /**
     * The kind of this dependence edge.
     */
    protected Kind kind = Kind.undefined;
    
    /**
     * The kind of a dependence edge.
     */
    public enum Kind {
        trueControlDependence,           // Control dependence with respect to a true-branch flow
        falseControlDependence,          // Control dependence with respect to a false-branch flow
        fallThroughControlDependence,    // Control dependence with respect to a fall-through flow
        exceptionCatch,                  // Control dependence with respect to an exception-catch within a try statement
        declaration,                     // Control dependence between declaration and its references
        
        loopIndependentDefUseDependence, // Data dependence with respect to a loop-independent variable
        loopCarriedDefUseDependence,     // Data dependence with respect to a loop-carried variable
        defOrderDependence,              // Data dependence based on the order of definitions of variables
        outputDependence,                // Data dependence based on the order of outputs of variables
        parameterIn,                     // Data dependence with respect to incoming parameter passing
        parameterOut,                    // Data dependence with respect to outgoing parameter passing
        fieldAccess,                     // Data dependence with respect to a field access
        summary,                         // Data dependence between actual-in and actual-out nodes
        
        classMember,                     // Dependence between a class and its members
        call,                            // Dependence between a caller and its callee
        
        undefined,
    }
    
    /**
     * Creates a new object that represents a dependence edge.
     * This method is not intended to be invoked by clients.
     * @param src the source node
     * @param dst the destination node
     */
    public Dependence(PDGNode src, PDGNode dst) {
        super(src, dst);
    }
    
    /**
     * Sets the kind of this dependence edge.
     * This method is not intended to be invoked by clients.
     * @param kind the kind of the dependence edge
     */
    public void setKind(Kind kind) {
        this.kind = kind;
    }
    
    /**
     * Returns the kind of this dependence edge.
     * @return the kind of the edge
     */
    public Dependence.Kind getKind() {
        return kind;
    }
    
    /**
     * Sets as a call edge.
     */
    public void setClassMember() {
        kind = Kind.classMember;
    }
    
    /**
     * Sets as a call edge.
     */
    public void setCall() {
        kind = Kind.call;
    }
    
    /**
     * Tests if this edge represents a control dependence.
     * @return {@code true} if this is a control dependence edge, otherwise {@code false}
     */
    public boolean isCD() {
        return kind == Kind.trueControlDependence ||
               kind == Kind.falseControlDependence ||
               kind == Kind.fallThroughControlDependence ||
               kind == Kind.declaration ||
               kind == Kind.exceptionCatch;
    }
    
    /**
     * Tests if this edge represents a data dependence.
     * @return {@code true} if this is a data dependence edge, otherwise {@code false}
     */
    public boolean isDD() {
        return kind == Kind.loopIndependentDefUseDependence ||
               kind == Kind.loopCarriedDefUseDependence ||
               kind == Kind.defOrderDependence ||
               kind == Kind.outputDependence ||
               kind == Kind.parameterIn ||
               kind == Kind.parameterOut ||
               kind == Kind.fieldAccess ||
               kind == Kind.summary;
    }
    
    /**
     * Tests if this edge represents a true control dependence.
     * @return {@code true} if this is a true control dependence edge, otherwise {@code false}
     */
    public boolean isTrue() {
        return kind == Kind.trueControlDependence;
    }
    
    /**
     * Tests if this edge represents a false control dependence.
     * @return {@code true} if this is a false control dependence edge, otherwise {@code false}
     */
    public boolean isFalse() {
        return kind == Kind.falseControlDependence;
    }
    
    /**
     * Tests if this edge represents a fall-through control dependence.
     * @return {@code true} if this is a fall-through control dependence edge, otherwise {@code false}
     */
    public boolean isFallThrough() {
        return kind == Kind.fallThroughControlDependence;
    }
    
    /**
     * Tests if this edge represents a declaration dependence.
     * The defined or used variable needs its declaration.
     * @return {@code true} if this is a declaration dependence edge, otherwise {@code false}
     */
    public boolean isDeclaration() {
        return kind == Kind.declaration;
    }
    
    /**
     * Tests if this edge represents an exception dependence.
     * The execution of a statement sometimes depends on the occurrence of an exception.
     * @return {@code true} if this is an exception dependence edge, otherwise {@code false}
     */
    public boolean isExceptionCatch() {
        return kind == Kind.exceptionCatch;
    }
    
    /**
     * Tests if this edge represents a class-member dependence.
     * @return {@code true} if this is a class-member dependence edge, otherwise {@code false}
     */
    public boolean isClassMember() {
        return kind == Kind.classMember;
    }
    
    /**
     * Tests if this edge represents a method call dependence.
     * The caller is needed to execute a callee.
     * @return {@code true} if this is a method call dependence edge, otherwise {@code false}
     */
    public boolean isCall() {
        return kind == Kind.call;
    }
    
    /**
     * Tests if this edge represents a define-use dependence.
     * @return {@code true} if this is a define-use dependence, otherwise {@code false}
     */
    public boolean isDefUse() {
        return isLIDD() || isLCDD();
    }
    
    /**
     * Tests if this edge represents a loop-independent data dependence.
     * The data dependence occurs when not passing a loop-back control flow.
     * @return {@code true} if this is a loop-independent data dependence edge, otherwise {@code false}
     */
    public boolean isLIDD() {
        return kind == Kind.loopIndependentDefUseDependence;
    }
    
    /**
     * Tests if this edge represents a loop-carried data dependence.
     * The data dependence occurs when passing a loop-back control flow.
     * @return {@code true} if this is a loop-carried data dependence edge, otherwise {@code false}
     */
    public boolean isLCDD() {
        return kind == Kind.loopCarriedDefUseDependence;
    }
    
    /**
     * Tests if this edge represents a define-order dependence when the two nodes are dominated the same node.
     * @return {@code true} if this is a define-order dependence edge, otherwise {@code false}
     */
    public boolean isDefOrder() {
        return kind == Kind.defOrderDependence;
    }
    
    /**
     * Tests if this edge represents an output dependence.
     * @return {@code true} if this is an output dependence edge, otherwise {@code false}
     */
    public boolean isOutput() {
        return kind == Kind.outputDependence;
    }
    
    /**
     * Tests if this edge represents a parameter-in dependence.
     * The value of an argument is passed to a parameter of a method.
     * @return {@code true} if this is a parameter-in dependence edge, otherwise {@code false}
     */
    public boolean isParameterIn() {
        return kind == Kind.parameterIn;
    }
    
    /**
     * Tests if this edge represents a parameter-out dependence.
     * The resulting value of a method is return to a caller.
     * @return {@code true} if this is a parameter-out dependence edge, otherwise {@code false}
     */
    public boolean isParameterOut() {
        return kind == Kind.parameterOut;
    }
    
    /**
     * Tests if this edge represents a field access dependence.
     * @return {@code true} if this is a field access dependence edge, otherwise {@code false}
     */
    public boolean isFieldAccess() {
        return kind == Kind.fieldAccess;
    }
    
    /**
     * Tests if this edge represents a summary data dependence.
     * The summary dependence edges are created as needed.
     * @return {@code true} if this is a summary data dependence edge, otherwise {@code false}
     */
    public boolean isSummary() {
        return kind == Kind.summary;
    }
    
    /**
     * Returns the source node of this dependence edge.
     * @return the source node
     */
    @Override
    public PDGNode getSrcNode() {
        return (PDGNode)src;
    }
    
    /**
     * Returns the source node of this dependence edge.
     * @return the source node
     */
    @Override
    public PDGNode getDstNode() {
        return (PDGNode)dst;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(GraphElement elem) {
        return (elem instanceof Dependence) ? equals((Dependence)elem) : false;
    }
    
    /**
     * Tests if a given dependence edge is equal to this edge.
     * @param dependence the dependence edge to be checked
     * @return the {@code true} if the given edge is equal to this edge
     */
    public boolean equals(Dependence dependence) {
        return dependence != null && (super.equals((GraphEdge)dependence) && kind == dependence.kind);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Long.valueOf(src.getId() + dst.getId()).hashCode();
    }
    
    /**
     * Displays information on this dependence.
     */
    public void print() {
        System.out.println(toString());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(src.getId());
        buf.append(" -> ");
        buf.append(dst.getId());
        if (kind == Kind.classMember) {
            buf.append(" MEMBER");
        } else if (kind == Kind.call) {
            buf.append(" CALL");
        }
        return buf.toString();
    }
    
    /**
     * Sorts the list of dependence edges
     * @param co the list of the dependence edges to be sorted
     * @return the sorted list of the dependence edges
     */
    public static List<Dependence> sortEdges(Collection<? extends Dependence> co) {
        List<Dependence> edges = new ArrayList<>(co);
        Collections.sort(edges, new Comparator<>() {
            
            @Override
            public int compare(Dependence edge1, Dependence edge2) {
                if (edge2.src.getId() == edge1.src.getId()) {
                    return edge2.dst.getId() == edge1.dst.getId() ?
                           edge2.kind.toString().compareTo(edge1.kind.toString()) :
                           edge1.dst.getId() > edge2.dst.getId() ? 1 : -1;
                } else if (edge1.src.getId() > edge2.src.getId()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        return edges;
    }
}
