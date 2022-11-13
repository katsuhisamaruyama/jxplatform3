/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.QualifiedName;
import org.jtool.jxplatform.builder.TimeInfo;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

/**
 * An object storing information on a system dependence graph (SDG).
 * 
 * @author Katsuhisa Maruyama
 */
public class SDG extends DependencyGraph {
    
    /**
     * The map between the fully-qualified names and ClDGs that have their corresponding names.
     */
    private Map<String, ClDG> cldgs = new HashMap<>();
    
    /**
     * The map between the fully-qualified names and PDGs that have their corresponding names.
     */
    private Map<String, PDG> pdgs = new HashMap<>();
    
    /**
     * The qualified name of this SDG.
     */
    private final QualifiedName qname;
    
    /**
     * Creates a ClDG.
     * This method is not intended to be invoked by clients.
     * @param node the entry node of this ClDG
     */
    public SDG() {
        qname = new QualifiedName("%SDG", TimeInfo.getTimeAsISOString(TimeInfo.getCurrentTime()));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public long getId() {
        return 0;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public QualifiedName getQualifiedName() {
        return qname;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getSignature() {
        return qname.fqn();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSDG() {
        return true;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PDG> getPDGs() {
        return new HashSet<>(pdgs.values());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public PDG findPDG(String fqn) {
        return pdgs.get(fqn);
    }
    
    /**
     * Adds a ClDG to this SDG.
     * @param cldg the ClDG to be added
     */
    public void add(ClDG cldg) {
        if (!cldgs.values().contains(cldg)) {
            cldgs.put(cldg.getQualifiedName().fqn(), cldg);
            addNodes(cldg.getNodes());
            addEdges(cldg.getEdges());
            addInterEdges(cldg.getInterEdges());
            
            cldg.getPDGs().forEach(pdg -> pdgs.put(pdg.getQualifiedName().fqn(), pdg));
        }
    }
    
    /**
     * Returns ClDGs contained in this SDG.
     * @return the collection of all the contained ClDGs
     */
    public Set<ClDG> getClDGs() {
        return new HashSet<>(cldgs.values());
    }
    
    /**
     * Finds a ClDG having a given name from ClDGs contained in this SDG.
     * @param fqn the fully-qualified name of the ClDG to be retrieved
     * @return the found ClDG, or {@code null} if the corresponding ClDG is not found
     */
    public ClDG findClDG(String fqn) {
        return cldgs.get(fqn);
    }
    
    /**
     * Finds a ClDG corresponding to a given class from ClDGs contained in this SDG.
     * @param jclass the class for the ClDG to be retrieved
     * @return the found ClDG, or {@code null} if the corresponding ClDG is not found
     */
    public ClDG findClDG(JavaClass jclass) {
        assert jclass != null;
        
        return cldgs.get(jclass.getQualifiedName().fqn());
    }
    
    /**
     * Finds a PDG corresponding to a given method from PDGs contained in this ClDG.
     * @param jmethod the method for the PDG to be retrieved
     * @return the found PDG, or {@code null} if the corresponding PDG is not found
     */
    public PDG findPDG(JavaMethod jmethod) {
        assert jmethod != null;
        
        return pdgs.get(jmethod.getQualifiedName().fqn());
    }
    
    /**
     * Finds a PDG corresponding to a given field from PDGs contained in this ClDG.
     * @param jmethod the field for the PDG to be retrieved
     * @return the found PDG, or {@code null} if the corresponding PDG is not found
     */
    public PDG findPDG(JavaField jfield) {
        assert jfield!= null;
        
        return pdgs.get(jfield.getQualifiedName().fqn());
    }
    
    /**
     * Obtains information on this graph.
     * @return the string representing the information on this PDG
     */
    @Override
    public String toString() {
        return qname.fqn();
    }
} 
