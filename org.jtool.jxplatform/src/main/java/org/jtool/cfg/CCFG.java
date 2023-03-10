/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import static org.jtool.jxplatform.builder.ModelBuilder.br;
import org.jtool.srcmodel.QualifiedName;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

/**
 * An object storing information on a class control flow graph (CCFG).
 * 
 * @author Katsuhisa Maruyama
 */
public class CCFG {
    
    /**
     * The entry node of this CCFG.
     */
    private CCFGEntry entry;
    
    /**
     * The map between the fully-qualified names and CFGs that have their corresponding names.
     */
    private Map<String, CFG> cfgs = new HashMap<>();
    
    /**
     * Creates a new, empty object for storing a CCFG information.
     * This method is not intended to be invoked by clients.
     */
    public CCFG() {
    }
    
    /**
     * Sets the entry node of this CFG.
     * This method is not intended to be invoked by clients.
     * @param node the entry node of this CFG
     */
    public void setEntryNode(CCFGEntry node) {
        assert node != null;
        
        entry = node;
        entry.setCCFG(this);
    }
    
    /**
     * Returns the entry node of this CCFG.
     * @return the entry node
     */
    public CCFGEntry getEntryNode() {
        return (CCFGEntry)entry;
    }
    
    /**
     * Returns the fully qualified name of this CCFG.
     * @return the fully qualified name
     */
    public QualifiedName getQualifiedName() {
        return entry.getQualifiedName();
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
     * Adds a CFG to this CCFG.
     * This method is not intended to be invoked by clients.
     * @param cfg the CFG to be added
     */
    public void add(CFG cfg) {
        assert cfg != null;
        
        if (!cfgs.values().contains(cfg)) {
            cfgs.put(cfg.getQualifiedName().fqn(), cfg);
        }
    }
    
    /**
     * Return CFGs contained in this CCFG.
     * @return the collection of the contained CFGs
     */
    public Set<CFG> getCFGs() {
        return new HashSet<>(cfgs.values());
    }
    
    /**
     * Tests if this CCFG is created from a class.
     * @return {@code true} if this CCFG corresponds to a class, otherwise {@code false}
     */
    public boolean isClass() {
        return entry.getJavaClass().isClass();
    }
    
    /**
     * Tests if this CCFG is created from an interface.
     * @return {@code true} if this CCFG corresponds to an interface, otherwise {@code false}
     */
    public boolean isInterface() {
        return entry.getJavaClass().isInterface();
    }
    
    /**
     * Tests if this CCFG is created from an enum class.
     * @return {@code true} if this CCFG corresponds to an enum, otherwise {@code false}
     */
    public boolean isEnum() {
        return entry.getJavaClass().isEnum();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof CCFG) ? equals((CCFG)obj) : false;
    }
    
    /**
     * Tests if a given CCFG is equal to this CCFG.
     * @param ccfg the CCFG to be checked
     * @return the {@code true} if the given CCFG is equal to this CCFG
     */
    public boolean equals(CCFG ccfg) {
        return ccfg != null && (this == ccfg || getQualifiedName().equals(ccfg.getQualifiedName()));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return getQualifiedName().hashCode();
    }
    
    /**
     * Displays information on this graph.
     */
    public void print() {
        System.out.println(toString());
    }
    
    /**
     * Obtains information on this CCFG.
     * @return the string representing the information
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("----- CCFG (from here) -----" +br);
        buf.append("Class Name = " + getQualifiedName());
        buf.append(br);
        cfgs.values().forEach(cfg -> buf.append(cfg.toStringForNodes() + "--" + br));
        cfgs.values().forEach(cfg -> buf.append(cfg.toStringForEdges() + "--" + br));
        buf.append("----- CCFG (to here) -----" + br);
        return buf.toString();
    }
}
