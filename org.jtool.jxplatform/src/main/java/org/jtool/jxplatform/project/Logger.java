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
    
    private List<String> logMessages = new ArrayList<>();
    
    private String logfile;
    
    public Logger() {
    }
    
    public void recordLog(String message) {
        logMessages.add(message);
    }
    
    public void recordUnresolvedError(String message) {
        recordLog("**Unresolved : " + message);
    }
    
    public void recordCreationError(String message) {
        recordLog("**Creation Error : " + message);
    }
    
    public void setLogFile(String logfile) {
        this.logfile = logfile;
        File file = new File(logfile);
        if (file.exists()) {
            file.delete();
        }
    }
    
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
