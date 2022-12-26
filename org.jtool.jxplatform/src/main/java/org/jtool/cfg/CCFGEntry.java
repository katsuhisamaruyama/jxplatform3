/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.QualifiedName;
import java.util.Set;
import java.util.HashSet;

/**
 * The entry node for a CCFG for a class or an interface.
 * 
 * @author Katsuhisa Maruyama
 */
public class CCFGEntry extends CFGNode {
    
    /**
     * The CCFG associated with this entry node. 
     */
    private CCFG ccfg = null;
    
    /**
     * The class associated with this entry node.
     */
    private final JavaClass jclass;
    
    /**
     * The collection of CFGs for the methods within the class associated with this entry node.
     */
    private final Set<CFG> methods = new HashSet<>();
    
    /**
     * The collection of CFGs for the fields within the class associated with this entry node.
     */
    private final Set<CFG> fields = new HashSet<>();
    
    /**
     * The collection of CCFGs for the inner classes within the class associated with this entry node.
     */
    private final Set<CCFG> classes = new HashSet<>();
    
    /**
     * Creates a new object that represents an entry for a class.
     * This method is not intended to be invoked by clients.
     * @param jclass the class associated with this entry node
     * @param kind the kind of this node
     */
    public CCFGEntry(JavaClass jclass, CFGNode.Kind kind) {
        super(jclass.getASTNode(), kind);
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
     * Returns the fully-qualified name of the CCFG that has this entry node
     * @return the fully-qualified name of the CCFG
     */
    public QualifiedName getQualifiedName() {
        return jclass.getQualifiedName();
    }
    
    /**
     * Associates a CCFG with this entry node.
     * This method is not intended to be invoked by clients.
     * @param ccfg the CCFG to be associated
     */
    public void setCCFG(CCFG ccfg) {
        assert ccfg != null;
        
        this.ccfg = ccfg;
    }
    
    /**
     * Returns the CCFG that has this entry node
     * @return the CCFG
     */
    public CCFG getCCFG() {
        return ccfg;
    }
    
    /**
     * Adds a CFG for a method to the class associated with this entry node.
     * This method is not intended to be invoked by clients.
     * @param cfg the CFG to be added 
     */
    public void addMethod(CFG cfg) {
        assert cfg != null;
        
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
     * This method is not intended to be invoked by clients.
     * @param cfg the CFG to be added
     */
    public void addField(CFG cfg) {
        assert cfg != null;
        
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
     * This method is not intended to be invoked by clients.
     * @param ccfg the CCFG to be added 
     */
    public void addClass(CCFG ccfg) {
        assert ccfg != null;
        
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
        return super.toString() + " [ " + getQualifiedName().getClassName() + " ]";
    }
}
