/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal;

import org.jtool.cfg.CFGNode;
import java.util.List;
import java.util.ArrayList;

/**
 * A statement (e.g, if, while, do, or for) that may dominate other nodes.
 * 
 * @author Katsuhisa Maruyama
 */
public class DominantStatement {
    
    private List<CFGNode> immediatePostDominators = new ArrayList<>();
    
    private boolean hasNest = true;
    
    public DominantStatement() {
    }
    
    public void clear() {
        immediatePostDominators.clear();
        immediatePostDominators = null;
    }
    
    void addImmediatePostDominator(CFGNode node) {
        assert node != null;
        
        immediatePostDominators.add(node);
    }
    
    public List<CFGNode> getImmediatePostDominators() {
        return immediatePostDominators;
    }
    
    void setNestStructure(boolean bool) {
        this.hasNest = bool;
    }
    
    public boolean hasNestStructure() {
        return hasNest;
    }
}
