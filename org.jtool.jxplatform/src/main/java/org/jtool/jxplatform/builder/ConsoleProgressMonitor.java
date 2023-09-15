/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder;

/**
 * A monitor that displays the progress of the task on the console ({@code stdout} and {@code stderr}).
 * 
 * @author Katsuhisa Maruyama
 */
public class ConsoleProgressMonitor {
    
    /**
     * The maximum number of processes in a task to be performed.
     */
    private int size;
    
    /**
     * A counter for displaying the dot symbol.
     */
    private int num;
    
    /**
     * A counter for processes done.
     */
    private int count;
    
    /**
     * Creates a progress monitor.
     */
    public ConsoleProgressMonitor() {
    }
    
    /**
     * Invoked when the first process starts.
     * @param size the maximum number of processes in the task
     */
    public void begin(int size) {
        this.size = size;
        this.count = 0;
        this.num = 0;
    }
    
    /**
     * Invoked when all the processes are done.
     */
    public void done() {
        System.out.println();
        System.out.flush();
    }
    
    /**
     * Invoked when processes are performed.
     * @param done the number of the performed processes
     */
    public void work(int done) {
        count = count + done;
        if (size <= 100) {
            num++;
            display('.');
        } else {
            if (count * 100 >= size * num) {
                num++;
                display('.');
            }
        }
    }
    
    /**
     * Displays one character on the console (both {@code stdout} and {@code stderr}).
     * @param ch a character to be displayed
     */
    public void display(char ch) {
        System.out.print(ch);
        System.out.flush();
    }
    
    /**
     * Displays a message on the console ({@code stdout} only).
     * @param message the message to be displayed
     */
    public void printMessage(String message) {
        System.out.println(message);
        System.out.flush();
    }
    
    /**
     * Displays a message on the console ({@code stderr} only).
     * @param message the message to be displayed
     */
    public void printError(String message) {
        System.err.println(message);
        System.err.flush();
    }
}
