/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.plugin;

import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import java.io.IOException;

/**
 * Console in Eclipse to display a message.
 * 
 * @author Katsuhisa Maruyama
 */
public class JxConsole {
    
    /**
     * The name of this console, which is displayed in Eclipse
     */
    private static final String CONSOLE_NAME = "JxConsole";
    
    /**
     * The stream of this console.
     */
    private MessageConsoleStream consoleStream = null;
    
    /**
     * Creates a console that displays a message in Eclipse.
     */
    public JxConsole() {
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
     * Prints a character.
     * @param ch the character to be printed
     */
    public void print(char ch) {
        if (consoleStream != null) {
            consoleStream.print(String.valueOf(ch));
        }
    }
    
    /**
     * Prints a string.
     * @param mesg the string to be printed
     */
    public void print(String mesg) {
        if (consoleStream != null) {
            consoleStream.print(mesg);
        }
    }
    
    /**
     * Prints a string.
     * @param mesg the string to be printed
     */
    public void println(String mesg) {
        if (consoleStream != null) {
            consoleStream.println(mesg);
        }
    }
    
    /**
     * Prints a new line.
     */
    public void println() {
        if (consoleStream != null) {
            consoleStream.println();
        }
    }
    
    /**
     * Flushes the stream.
     */
    public void flush() {
        if (consoleStream != null) {
            try {
                consoleStream.flush();
            } catch (IOException e) { /* empty */ }
        }
    }
}
