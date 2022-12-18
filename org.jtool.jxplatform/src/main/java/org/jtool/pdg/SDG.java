/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

/**
 * An object storing information on a system dependence graph (SDG).
 * 
 * @author Katsuhisa Maruyama
 */
public class SDG extends DependencyGraph {
    
    /**
     * Creates a a system dependence graph.
     * This method is not intended to be invoked by clients.
     */
    public SDG() {
        super("SDG");
    }
}
