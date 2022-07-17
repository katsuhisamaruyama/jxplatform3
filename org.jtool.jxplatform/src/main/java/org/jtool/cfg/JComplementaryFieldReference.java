/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.eclipse.jdt.core.dom.ASTNode;
import java.util.List;
import java.util.ArrayList;

/**
 * An class that represents a complementary reference to a field.
 * 
 * @author Katsuhisa Maruyama
 */
public class JComplementaryFieldReference extends JFieldReference {
    
    /**
     * CFG nodes that hold this complementary field reference.
     */
    private List<CFGStatement> holdingNodes = new ArrayList<>();
    
    /**
     * Creates a new object that represents a reference to a field.
     * This constructor is not intended to be invoked by clients.
     * @param node the AST node corresponding to this reference
     * @param className the name of a class declaring the referenced field
     * @param name the name of the referenced field
     * @param referenceForm the form of this reference
     * @param type the type of the referenced field
     * @param primitive {@code true} if the type of the referenced field is primitive, otherwise {@code false}
     * @param modifiers the modifier information on the referenced field
     * @param inProject {@code true} if the referenced field exists in the target project, otherwise {@code false}
     * @param holdingNodes CFG nodes that hold this complementary field reference
     */
    public JComplementaryFieldReference(ASTNode node, String className, String name,
            String referenceForm, String type, boolean primitive, int modifiers, boolean inProject,
            List<CFGStatement> holdingNodes) {
        super(node, className, name, referenceForm, type, primitive, modifiers, inProject, false);
        this.holdingNodes = holdingNodes;
    }
    
    /**
     * Returns the CFG nodes that hold this complementary field reference.
     * @return the collection of the nodes
     */
    public List<CFGStatement> getHoldingNodes() {
        return holdingNodes;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isComplementary() {
        return true;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAvailable() {
        return false;
    }
}
