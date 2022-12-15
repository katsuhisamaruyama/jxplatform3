/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.plugin.internal;

import org.jtool.jxplatform.plugin.Activator;
import org.jtool.jxplatform.plugin.JxConsole;
import org.jtool.jxplatform.project.ConsoleProgressMonitor;

/**
 * A progress monitor that displays information on the console.
 * 
 * @author Katsuhisa Maruyama
 */
public class ConsoleProgressMonitorForPlugin extends ConsoleProgressMonitor {
    
    private JxConsole console;
    
    public ConsoleProgressMonitorForPlugin() {
        this.console = Activator.getPlugin().getConsole();
    }
    
    @Override
    public void done() {
        console.println();
        console.flush();
    }
    
    @Override
    public void display(char ch) {
        console.print(ch);
        console.flush();
    }
    
    @Override
    public void printMessage(String message) {
        console.println(message);
        console.flush();
    }
    
    @Override
    public void printError(String message) {
        console.println(message);
        console.flush();
    }
}
