/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.project;

/**
 * A progress monitor that does not display any character.
 * 
 * @author Katsuhisa Maruyama
 */
public class NullConsoleProgressMonitor extends ConsoleProgressMonitor {
    
    public NullConsoleProgressMonitor() {
    }
    
    @Override
    public void begin(int size) {
    }
    
    @Override
    public void done() {
    }
    
    @Override
    public void work(int done) {
    }
}
