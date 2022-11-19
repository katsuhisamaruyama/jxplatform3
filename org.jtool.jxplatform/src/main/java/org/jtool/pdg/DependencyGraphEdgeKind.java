/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

/**
 * The kind of a dependency graph edge.
 * 
 * @author Katsuhisa Maruyama
 */
public enum DependencyGraphEdgeKind {
    trueControlDependence,           // Control dependence with respect to a true-branch flow
    falseControlDependence,          // Control dependence with respect to a false-branch flow
    fallThroughControlDependence,    // Control dependence with respect to a fall-through flow
    declaration,                     // Control dependence between declaration and its references
    exceptionCatch,                  // Control dependence with respect to an exception-catch within a try statement
    classMember,                     // Dependence between a class and its members
    call,                            // Dependence between a caller and its callee
    
    loopIndependentDefUseDependence, // Data dependence with respect to a loop-independent variable
    loopCarriedDefUseDependence,     // Data dependence with respect to a loop-carried variable
    defOrderDependence,              // Data dependence based on the order of definitions of variables
    outputDependence,                // Data dependence based on the order of outputs of variables
    parameterIn,                     // Data dependence with respect to incoming parameter passing
    parameterOut,                    // Data dependence with respect to outgoing parameter passing
    summary,                         // Data dependence between actual-in and actual-out nodes
    fieldAccess,                     // Data dependence with respect to a field access
    uncoveredFieldAccess,            // Data dependence with respect to an uncovered field accesses
    
    undefined,
}
