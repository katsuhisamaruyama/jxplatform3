/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.project;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Records logs.
 * 
 * @author Katsuhisa Maruyama
 */
public class Logger {
    
    /**
     * Recorded log messages.
     */
    private List<String> logMessages = new ArrayList<>();
    
    /**
     * The name of the file that stores the log information.
     */
    private String logfile;
    
    /**
     * A flag that indicates whether the log information is displayed.
     */
    private boolean visible;
    
    /**
     * Creates an object for logging.
     * @param visible {@code true} if the log information is displayed, otherwise {@code false}
     */
    public Logger(boolean visible) {
        this.visible = visible;
    }
    
    /**
     * Sets whether the log information is displayed.
     * @param visible {@code true} if the log information is displayed, otherwise {@code false}
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    /**
     * Tests if the log information is displayed.
     * @return {@code true} if the log information is displayed, otherwise {@code false}
     */
    public boolean isVisible() {
        return visible;
    }
    
    /**
     * Records a progress message.
     * @param message the message
     */
    public void printProgress(String message) {
        if (visible) {
            System.out.print(message);
            System.out.flush();
        }
    }
    
    /**
     * Records a simple message.
     * @param mesg the message
     */
    public void printMessage(String message) {
        logMessages.add(message);
        
        if (visible) {
            System.out.println(message);
            System.out.flush();
        }
    }
    
    /**
     * Records a log message.
     * @param message the message
     */
    public void recordLog(String message) {
        logMessages.add(message);
    }
    
    /**
     * Records an error message.
     * @param message the message
     */
    public void printError(String message) {
        logMessages.add(message);
        
        if (visible) {
            System.err.println(message);
            System.err.flush();
        }
    }
    
    /**
     * Records an error message when the source model contains unresolved factors.
     * @param message the message
     */
    public void printUnresolvedError(String message) {
        printError("**Unresolved : " + message);
    }
    
    /**
     * Records an error message when the source model contains unresolved factors.
     * @param message the message
     */
    public void printCreationError(String message) {
        printError("**Creation Error : " + message);
    }
    
    /**
     * Sets the file that stores the log information.
     * @param logfile the name of the file
     */
    public void setLogFile(String logfile) {
        this.logfile = logfile;
        File file = new File(logfile);
        if (file.exists()) {
            file.delete();
        }
    }
    
    /**
     * Writes the log information into the file.
     */
    public void writeLog() {
        if (logfile == null || logfile.length() == 0) {
            return;
        }
        
        StringBuilder buf = new StringBuilder();
        for (String mesg : logMessages) {
            buf.append(mesg);
            buf.append("\n");
        }
        
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(logfile), true));
            writer.append(buf.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.flush();
        }
        logMessages.clear();
    }
}
