/*
 *  Copyright 2021
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.plugin;

import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.MessageConsoleStream;

/**
 * Displays a message on the Eclipse console.
 * 
 * @author Katsuhisa Maruyama
 */
public class SrcPlatConsole {
    
    /**
     * The name of this console.
     */
    private static final String CONSOLE_NAME = "SrcPlatConsole";
    
    /**
     * The stream for this console.
     */
    private MessageConsoleStream consoleStream = null;
    
    /**
     * Creates an object that displays a message on the Eclipse console.
     */
    public SrcPlatConsole() {
        if (ConsolePlugin.getDefault() == null) {
            return;
        }
        
        IConsoleManager consoleManager = ConsolePlugin.getDefault().getConsoleManager();
        IConsole[] consoles = consoleManager.getConsoles();
        MessageConsole console = null;
        for (int i = 0; i < consoles.length; i++) {
            if (CONSOLE_NAME.equals(consoles[i].getName())) {
                console = (MessageConsole)consoles[i];
            }
        }
        if (console == null) {
            console = new MessageConsole(CONSOLE_NAME, null);
        }
        
        consoleManager.addConsoles(new MessageConsole[] { console });
        consoleManager.showConsoleView(console);
        consoleStream = console.newMessageStream();
    }
    
    /**
     * Displays a message without a new-line.
     * @param mesg the message to be displayed
     */
    public void print(String mesg) {
        if (consoleStream != null) {
            consoleStream.print(mesg);
        }
    }
    
    /**
     * Displays a message with a new-line.
     * @param mesg the message to be displayed
     */
    public void println(String mesg) {
        if (consoleStream != null) {
            consoleStream.println(mesg);
        }
    }
    
    /**
     * Displays a new-line.
     */
    public void println() {
        if (consoleStream != null) {
            consoleStream.println();
        }
    }
}
