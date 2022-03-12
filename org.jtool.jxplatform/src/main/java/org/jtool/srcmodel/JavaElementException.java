/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel;

/**
 * An object that representing the failure of creation of a model element.
 * 
 * @author Katsuhisa Maruyama
 */
public class JavaElementException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    JavaElementException(String message) {
        super(message);
    }
}
