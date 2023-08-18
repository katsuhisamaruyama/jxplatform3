/*
 *  Copyright 2022-2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.eclipse.jdt.core.dom.ASTNode;
import java.util.Set;
import java.util.HashSet;

/**
 * An class that represents a reference to a field, which is uncovered in a called method.
 * 
 * @author Katsuhisa Maruyama
 */
public class JUncoveredFieldReference extends JFieldReference {
    
    /**
     * CFG nodes that hold this uncovered field reference.
     */
    private Set<CFGStatement> holdingNodes = new HashSet<>();
    
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
     * @param holdingNodes CFG nodes that hold this uncovered field reference
     */
    public JUncoveredFieldReference(ASTNode node, String className, String name, String referenceForm,
            String type, boolean primitive, int modifiers, boolean inProject, Set<CFGStatement> holdingNodes) {
        super(node, className, name, referenceForm, type, primitive, modifiers, inProject, false);
        this.holdingNodes = holdingNodes;
    }
    
    /**
     * Returns the CFG nodes that hold this uncovered field reference.
     * @return the collection of the nodes
     */
    public Set<CFGStatement> getHoldingNodes() {
        return holdingNodes;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUncoveredFieldReference() {
        return true;
    }
}
