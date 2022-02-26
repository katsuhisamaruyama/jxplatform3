/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.project;

/**
 * A progress monitor.
 * 
 * @author Katsuhisa Maruyama
 */
public class ConsoleProgressMonitor {
    
    private int size;
    private int num;
    private int count;
    
    public ConsoleProgressMonitor() {
    }
    
    public void begin(int size) {
        this.size = size;
        this.count = 0;
        this.num = 0;
    }
    
    public void done() {
        System.out.println();
        System.out.flush();
    }
    
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
    
    private void display(char ch) {
        System.out.print(ch);
        System.out.flush();
    }
}
