/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.QualifiedName;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * An object storing information on a class control flow graph (CCFG).
 * 
 * @author Katsuhisa Maruyama
 */
public class CCFG extends CommonCFG {
    
    /**
     * The map between the fully-qualified names and CFGs that have their corresponding names.
     */
    protected Map<String, CFG> cfgs = new HashMap<>();
    
    /**
     * Returns the entry node of this CCFG.
     * @return the entry node
     */
    @Override
    public CFGClassEntry getEntryNode() {
        return (CFGClassEntry)entry;
    }
    
    /**
     * Returns the fully qualified name of this CCFG.
     * @return the fully qualified name
     */
    @Override
    public QualifiedName getQualifiedName() {
        return entry.getQualifiedName();
    }
    
    /**
     * Adds a CFG to this CCFG.
     * @param cfg the CFG to be added
     */
    public void add(CFG cfg) {
        if (!cfgs.values().contains(cfg)) {
            cfgs.put(cfg.getQualifiedName().fqn(), cfg);
        }
    }
    
    /**
     * Returns a copy of this CCFG.
     * @return the copy
     */
    public Set<CFG> getCFGs() {
        return new HashSet<>(cfgs.values());
    }
    
    /**
     * Returns a CFG that has a given name.
     * @param fqn the fully-qualified name of the CFG to be retrieved
     * @return the found CFG, or {@code null} if no CFG is found
     */
    public CFG getCFG(String fqn) {
        return cfgs.get(fqn);
    }
    
    /**
     * Returns all nodes in this CCFG.
     * @return the collection of the nodes
     */
    @Override
    public Set<CFGNode> getNodes() {
        return cfgs.values()
                .stream()
                .flatMap(cfg -> cfg.getNodes().stream()).collect(Collectors.toSet());
    }
    
    /**
     * Returns all edges in this CCFG.
     * @return the collection of the edges
     */
    @Override
    public Set<ControlFlow> getEdges() {
        return cfgs.values()
                .stream()
                .flatMap(cfg -> cfg.getEdges().stream()).collect(Collectors.toSet());
    }
    
    /**
     * Obtains information on this CCFG.
     * @return the string representing the information
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("----- CCFG (from here) -----\n");
        buf.append("Class Name = " + getQualifiedName());
        buf.append("\n");
        cfgs.values().forEach(cfg -> buf.append(cfg.toStringForNodes()));
        cfgs.values().forEach(cfg -> buf.append(cfg.toStringForEdges()));
        buf.append("----- CCFG (to here) -----\n");
        return buf.toString();
    }
}
