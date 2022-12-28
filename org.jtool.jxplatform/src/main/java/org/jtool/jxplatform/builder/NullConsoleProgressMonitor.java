/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder;

/**
 * A progress monitor that does nothing.
 * 
 * @author Katsuhisa Maruyama
 */
public class NullConsoleProgressMonitor extends ConsoleProgressMonitor {
    
    /**
     * Creates a progress monitor.
     */
    public NullConsoleProgressMonitor() {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void begin(int size) {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void done() {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void work(int done) {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void display(char ch) {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void printMessage(String message) {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void printError(String message) {
    }
}
