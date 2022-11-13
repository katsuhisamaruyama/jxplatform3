/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

/**
 * An interface to introduce a variety of conditions that stop traversing nodes.
 * 
 * @author Katsuhisa Maruyama
 */
@FunctionalInterface
public interface StopConditionOnReachablePath {
    
    /**
     * Tests if a given node satisfies a condition that stops traversing nodes.
     * @param node the node to be checked
     * @return {@code true} if the node satisfies the condition, otherwise {@code false}
     */
    public boolean isStop(CFGNode node);
}
