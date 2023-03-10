/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.JavaField;
import java.util.List;

/**
 * An entry node for a CFG for a field or an enum constant.
 * 
 * @author Katsuhisa Maruyama
 */
public class CFGFieldEntry extends CFGEntry {
    
    /**
     * The field associated with this entry node.
     */
    private final JavaField jfield;
    
    /**
     * The CFG node for the declaration of the field associated with this entry node.
     */
    private CFGStatement declNode;
    
    /**
     * Creates a new object that represents an entry for a field.
     * This method is not intended to be invoked by clients.
     * @param jfield the field associated with this entry node
     * @param kind the kind of this node
     */
    public CFGFieldEntry(JavaField jfield, CFGNode.Kind kind) {
        super(jfield.getASTNode(), kind, jfield.getQualifiedName());
        this.jfield = jfield;
    }
    
    /**
     * Returns the filed associated with this entry node.
     * @return the associated field
     */
    public JavaField getJavaField() {
        return jfield;
    }
    
    /**
     * Sets the CFG node for the declaration of the field associated with this entry node.
     * This method is not intended to be invoked by clients.
     * @param node the CFG node of the field declaration to be set
     */
    public void setDeclarationNode(CFGStatement node) {
        assert node != null;
        
        declNode = node;
    }
    
    /**
     * Returns the CFG node for the declaration of the field associated with this entry node.
     * @return the CFG node of the field declaration
     */
    public CFGStatement getDeclarationNode() {
        return declNode;
    }
    
    /**
     * Returns a variable corresponding to the field associated with this entry node.
     * @return the reference to this field
     */
    public JVariableReference getDefField() {
        return declNode.getDefFirst();
    }
    
    /**
     * Returns variables used in the declaration of the field associated with this entry node.
     * @return the collection of the used references
     */
    public List<JVariableReference> getUseFields() {
        return declNode.getUseVariables();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return super.toString() + " [ " + fqn + " ]";
    }
}
