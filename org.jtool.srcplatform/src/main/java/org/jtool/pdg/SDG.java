/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.cfg.CommonCFG;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.QualifiedName;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * An object storing information on a system dependence graph (SDG).
 * 
 * @author Katsuhisa Maruyama
 */
public class SDG extends CommonPDG {
    
    /**
     * The fully-qualified name of this SDG.
     */
    private static final QualifiedName QName = new QualifiedName("SDG", "SDG");
    
    /**
     * The map between the fully-qualified names and ClDGs that have their corresponding names.
     */
    protected Map<String, ClDG> cldgs = new HashMap<>();
    
    /**
     * The map between the fully-qualified names and PDGs that have their corresponding names.
     */
    protected Map<String, PDG> pdgs = new HashMap<>();
    
    /**
     * Returns the entry node for this SDG.
     */
    @Override
    public PDGEntry getEntryNode() {
        return null;
    }
    
    /**
     * Returns the identification number of this SDG.
     * @return fixed value {@code -1}
     */
    @Override
    public long getId() {
        return -1;
    }
    
    /**
     * Returns the CFG corresponding to this SDG.
     * @return always {@code null} because there is in general no corresponding CFG
     */
    @Override
    public CommonCFG getCFG() {
        return null;
    }
    
    /**
     * Returns the signature of this SDG.
     * @return the SDG signature, which is unique.
     */
    @Override
    public String getSignature() {
        return "SDG";
    }
    
    /**
     * Returns the fully-qualified name of this SDG.
     * @return the SDG name, which is unique.
     */
    @Override
    public QualifiedName getQualifiedName() {
        return QName;
    }
    
    /**
     * Adds a ClDG to this SDG.
     * @param cldg the ClDG to be added
     */
    public void add(ClDG cldg) {
        cldgs.put(cldg.getQualifiedName().fqn(), cldg);
        cldg.getPDGs().forEach(pdg -> pdgs.put(pdg.getQualifiedName().fqn(), pdg));
    }
    
    /**
     * Returns ClDGs contained in this SDG.
     * @return the collection of the contained ClDGs
     */
    public Set<ClDG> getClDGs() {
        return new HashSet<ClDG>(cldgs.values());
    }
    
    /**
     * Finds a ClDG having a given name from ClDGs contained in this SDG.
     * @param fqn the fully-qualified name of the ClDG to be retrieved
     * @return the found ClDG, or {@code null} if the corresponding ClDG is not found
     */
    public ClDG getClDG(String fqn) {
        return cldgs.get(fqn);
    }
    
    /**
     * Finds a ClDG corresponding to a given class from ClDGs contained in this SDG.
     * @param jclass the class for the ClDG to be retrieved
     * @return the found ClDG, or {@code null} if the corresponding ClDG is not found
     */
    public ClDG getClDG(JavaClass jclass) {
        return cldgs.get(jclass.getQualifiedName().fqn());
    }
    
    /**
     * Returns PDGs contained in this SDG.
     * @return the collection of the contained PDGs
     */
    public Set<PDG> getPDGs() {
        return new HashSet<PDG>(pdgs.values());
    }
    
    /**
     * Finds a PDG having a given name from PDGs contained in this SDG.
     * @param fqn the fully-qualified name of the PDG to be retrieved
     * @return the found PDG, or {@code null} if the corresponding PDG is not found
     */
    public PDG getPDG(String fqn) {
        return pdgs.get(fqn);
    }
    
    /**
     * Tests if this graph represents an SDG.
     * @return always {@code true}
     */
    @Override
    public boolean isSDG() {
        return true;
    }
    
    /**
     * Returns nodes contained in this SDG.
     * @return the collection of the contained nodes
     */
    @Override
    public Set<PDGNode> getNodes() {
        return cldgs.values()
                .stream()
                .flatMap(pdg -> pdg.getNodes().stream())
                .collect(Collectors.toSet());
    }
    
    /**
     * Returns dependence edges contained in this SDG.
     * @return the collection of the contained edges
     */
    @Override
    public Set<Dependence> getEdges() {
        return cldgs.values()
                .stream()
                .flatMap(pdg -> pdg.getEdges().stream())
                .collect(Collectors.toSet());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("----- SDG (from here) -----\n");
        buf.append(toStringForNodes());
        buf.append(toStringForEdges());
        
        for (ClDG cldg : cldgs.values()) {
            buf.append(cldg.toString());
        }
        buf.append("----- SDG (to here) -----\n");
        return buf.toString();
    }
} 
