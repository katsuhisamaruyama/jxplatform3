/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel;

import org.eclipse.jdt.core.dom.ASTNode;

/**
 * A root object for a Java model element.
 * 
 * @author Katsuhisa Maruyama
 */
public abstract class JavaElement {
    
    /**
     * An AST node corresponding to this model element.
     */
    protected ASTNode astNode;
    
    /**
     * Information on a code fragment for this model element.
     */
    protected CodeRange codeRange;
    
    /**
     * Creates a new object of this model element.
     * This constructor is intended to be invoked by subclasses when creation their objects.
     * @param node an AST node corresponding to this model element
     * @param jfile a file that declares this model element
     */
    protected JavaElement(ASTNode node) {
        this.astNode = node;
        if (node != null) {
            this.codeRange = new CodeRange(node);
        } else {
            this.codeRange = null;
        }
    }
    
    /**
     * Returns the fully qualified name of this model element.
     * @return the fully qualified name
     */
    public abstract QualifiedName getQualifiedName();
    
    /**
     * Returns the project which this model element exists in.
     * @return the project of this model element
     */
    public abstract JavaProject getJavaProject();
    
    /**
     * Returns the file that declares this model element.
     * @return the declaring file
     */
    public abstract JavaFile getFile();
    
    /**
     * Returns the AST node corresponding to this model element.
     * @return the corresponding AST node
     */
    public ASTNode getASTNode() {
        return astNode;
    }
    
    /**
     * Returns the range information on a code fragment of this model element.
     * @return the code range information on this model element
     */
    public CodeRange getCodeRange() {
        return codeRange;
    }
    
    /**
     * Obtains the source code corresponding to a code fragment of this model element.
     * @return the source code of this model element, or the empty string if there is no source code
     */
    public String getSource() {
        if (codeRange != null) {
            StringBuilder buf = new StringBuilder(getFile().getSource());
            return buf.substring(codeRange.getStartPosition(), codeRange.getEndPosition() + 1);
        }
        return "";
    }
}
