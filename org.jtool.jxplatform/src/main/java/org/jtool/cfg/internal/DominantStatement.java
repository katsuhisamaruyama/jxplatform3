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
    
    private boolean breakNestedStructure = false;
    
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
    
    void breakNestedStructure(boolean bool) {
        this.breakNestedStructure = bool;
    }
    
    public boolean breakNestedStructure() {
        return breakNestedStructure;
    }
}
