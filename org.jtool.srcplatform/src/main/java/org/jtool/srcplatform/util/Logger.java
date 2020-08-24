/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.util;

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
    
    private static Logger instance = new Logger();
    
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
    private boolean visible = true;
    
    /**
     * Returns the singleton object of the logger.
     * @return the logger object
     */
    public static Logger getInstance() {
        return instance;
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
     * @param mesg the message
     */
    public void printProgress(String mesg) {
        if (visible) {
            System.out.print(mesg);
            System.out.flush();
        }
    }
    
    /**
     * Records a simple message.
     * @param mesg the message
     */
    public void printMessage(String mesg) {
        logMessages.add(mesg);
        
        if (visible) {
            System.out.println(mesg);
            System.out.flush();
        }
    }
    
    /**
     * Records a log message.
     * @param mesg the message
     */
    public void printLog(String mesg) {
        logMessages.add(mesg);
    }
    
    /**
     * Records an error message.
     * @param mesg the message
     */
    public void printError(String mesg) {
        logMessages.add(mesg);
        
        if (visible) {
            System.err.println(mesg);
            System.err.flush();
        }
    }
    
    /**
     * Records an error message when the source model contains unresolved factors.
     * @param mesg the message
     */
    public void printUnresolvedError(String mesg) {
        printError("!Unresolved : " + mesg);
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
