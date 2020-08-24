/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * Stores information on the range of a code fragment for an AST node.
 * 
 * @author Katsuhisa Maruyama
 */
public class CodeRange {
    
    /**
     * The position that indicates where the code fragment for an AST node begins.
     */
    private int startPosition;
    
    /**
     * The position that indicates where the code fragment for an AST node ends.
     */
    private int endPosition;
    
    /**
     * The upper line number of the position that indicates the code fragment for an AST node begins.
     */
    private int upperLineNumber;
    
    /**
     * The bottom line number of the position that indicates the code fragment for an AST node ends.
     */
    private int bottomLineNumber;
    
    /**
     * The starting position of the code fragment for an AST node,
     * including comments and whitespace immediately exist before or after.
     */
    private int extendedStartPosition;
    
    /**
     * The ending position of the code fragment for an AST node,
     * including comments and whitespace immediately exist before or after.
     */
    private int extendedEndPosition;
    
    /**
     * The upper line number of the code fragment for an AST node,
     * including comments and whitespace immediately exist before or after.
     */
    private int extendedUpperLineNumber;
    
    /**
     * The bottom line number of the code fragment for an AST node,
     * including comments and whitespace immediately exist before or after.
     */
    private int extendedBottomLineNumber;
    
    /**
     * Creates code range information on a given AST node.
     * @param node the AST node
     */
    public CodeRange(ASTNode node) {
        assert node != null;
        if (node != null) {
            CompilationUnit cu = (CompilationUnit)node.getRoot();
            
            startPosition = node.getStartPosition();
            endPosition = node.getStartPosition() + node.getLength() - 1;
            upperLineNumber = cu.getLineNumber(startPosition);
            bottomLineNumber = cu.getLineNumber(endPosition);
            
            extendedStartPosition = cu.getExtendedStartPosition(node);
            extendedEndPosition = cu.getExtendedStartPosition(node) + cu.getExtendedLength(node) - 1;
            extendedUpperLineNumber = cu.getLineNumber(extendedStartPosition);
            extendedBottomLineNumber = cu.getLineNumber(extendedEndPosition);
        }
    }
    
    /**
     * Returns the position that indicates where the code fragment for an AST node begins.
     * @return the value of the position on the source code
     */
    public int getStartPosition() {
        return startPosition;
    }
    
    /**
     * Returns the position that indicates where the code fragment for an AST node ends.
     * @return the value of the position on the source code
     */
    public int getEndPosition() {
        return endPosition;
    }
    
    /**
     * Returns the length of the code fragment for an AST node.
     * @return the length of the code fragment
     */
    public int getCodeLength() {
        return endPosition - startPosition + 1;
    }
    
    /**
     * Returns the starting position of the code fragment for an AST node,
     * including comments and whitespace immediately exist before or after.
     * @return the value of the position on the source code
     */
    public int getExtendedStartPosition() {
        return extendedStartPosition;
    }
    
    /**
     * Returns the ending position of the code fragment for an AST node,
     * including comments and whitespace immediately exist before or after.
     * @return the value of the position on the source code
     */
    public int getExtendedEndPosition() {
        return extendedEndPosition;
    }
    
    /**
     * Returns the length of the code fragment for an AST node,
     * including comments and whitespace immediately exist before or after.
     * @return the length of the code fragment
     */
    public int getExtendedCodeLength() {
        return extendedEndPosition - extendedStartPosition + 1;
    }
    
    /**
     * Returns the upper line number of the position that indicates the code fragment for an AST node begins.
     * @return the value of the position on the source code
     */
    public int getUpperLineNumber() {
        return upperLineNumber;
    }
    
    /**
     * Returns the upper line number of the code fragment for an AST node,
     * including comments and whitespace immediately exist before or after.
     * @return the value of the position on the source code
     */
    public int getExtendedUpperLineNumber() {
        return extendedUpperLineNumber;
    }
    
    /**
     * Returns the bottom line number of the position that indicates the code fragment for an AST node ends.
     * @return the value of the position on the source code
     */
    public int getBottomLineNumber() {
        return bottomLineNumber;
    }
    
    /**
     * Returns the bottom line number of the code fragment for an AST node,
     * including comments and whitespace immediately exist before or after.
     * @return the value of the position on the source code
     */
    public int getExtendedBottomLineNumber() {
        return extendedBottomLineNumber;
    }
    
    /**
     * Obtains the number of lines of code fragment for an AST node.
     * @return the number of lines of the code fragment
     */
    public int getLoc() {
        return bottomLineNumber - upperLineNumber + 1;
    }
    
    /**
     * Obtains the number of lines of code fragment for an AST node,
     * including comments and whitespace immediately exist before or after.
     * @return the number of lines of the code fragment
     */
    public int getExtendedLoc() {
        return extendedBottomLineNumber - extendedUpperLineNumber + 1;
    }
    
    /**
     * Obtains information on the range of an AST node.
     * @return the string representing the information
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("[");
        if (getStartPosition() >= 0) {
            buf.append(getStartPosition());
            buf.append("-");
            buf.append(getEndPosition());
        }
        buf.append("] ");
        return buf.toString();
    }
}
