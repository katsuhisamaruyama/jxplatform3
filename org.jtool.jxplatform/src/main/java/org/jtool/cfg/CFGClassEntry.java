/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.JavaClass;
import java.util.Set;
import java.util.HashSet;

/**
 * The entry node for a CCFG for a class or an interface.
 * 
 * @author Katsuhisa Maruyama
 */
public class CFGClassEntry extends CFGEntry {
    
    /**
     * The class associated with this entry node.
     */
    private JavaClass jclass;
    
    /**
     * The collection of CFGs for the methods within the class associated with this entry node.
     */
    private Set<CFG> methods = new HashSet<>();
    
    /**
     * The collection of CFGs for the fields within the class associated with this entry node.
     */
    private Set<CFG> fields = new HashSet<>();
    
    /**
     * The collection of CCFGs for the inner classes within the class associated with this entry node.
     */
    private Set<CCFG> classes = new HashSet<>();
    
    /**
     * Creates a new object that represents an entry for a class.
     * @param jclass the class associated with this entry node
     * @param kind the kind of this node
     */
    public CFGClassEntry(JavaClass jclass, CFGNode.Kind kind) {
        super(jclass.getASTNode(), kind, jclass.getQualifiedName());
        this.jclass = jclass;
    }
    
    /**
     * Returns the class associated with this entry node.
     * @return the associated class
     */
    public JavaClass getJavaClass() {
        return jclass;
    }
    
    /**
     * Adds a CFG for a method to the class associated with this entry node.
     * @param cfg the CFG to be added 
     */
    public void addMethod(CFG cfg) {
        methods.add(cfg);
    }
    
    /**
     * Returns CFGs for the methods within the class associated with this entry node.
     * @return the collection of the CFGs for the methods
     */
    public Set<CFG> getMethods() {
        return methods;
    }
    
    /**
     * Adds a CFG for a field to the class associated with this entry node.
     * @param cfg the CFG to be added
     */
    public void addField(CFG cfg) {
        fields.add(cfg);
    }
    
    /**
     * Returns CFGs for the fields within the class associated with this entry node.
     * @return the collection of the CFGs for the fields
     */
    public Set<CFG> getFields() {
        return fields;
    }
    
    /**
     * Adds a CCFG for an inner class to the class associated with this entry node.
     * @param ccfg the CCFG to be added 
     */
    public void addClass(CCFG ccfg) {
        classes.add(ccfg);
    }
    
    /**
     * Returns CCFGs for the inner classes within the class associated with this entry node.
     * @return the collection of the CCFGs for the inner classes
     */
    public Set<CCFG> getClasses() {
        return classes;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return super.toString() + " [ " + fqn.getClassName() + " ]";
    }
}
