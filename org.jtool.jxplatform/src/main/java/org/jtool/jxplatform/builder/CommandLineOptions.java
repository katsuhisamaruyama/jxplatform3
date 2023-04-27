/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder;

import java.util.Map;
import java.util.HashMap;

/**
 * Collects command-line options.
 * 
 * @author Katsuhisa Maruyama
 */
public class CommandLineOptions {
    
    /**
     * The collected command-line options.
     */
    private Map<String, String> options = new HashMap<>();
    
    /**
     * Creates an object that collects command-line options.
     * @param args the command line parameters
     */
    public CommandLineOptions(String[] args) {
        for (int count = 0; count < args.length; ) {
            if (args[count].charAt(0) == '-') {
                String key = args[count].trim().substring(1);
                count++;
                if (count < args.length) {
                    if (args[count].charAt(0) != '-') {
                        String value = args[count];
                        options.put(key, value.trim());
                        count++;
                    } else {
                        options.put(key, "yes");
                    }
                }
            } else {
                count++;
            }
        }
    }
    
    /**
     * Obtains the string value for the option having a given key.
     * @param key the key for the option
     * @param defaultValue the specified default value
     * @return the string value of the option
     */
    public String get(String key, String defaultValue) {
        key = key.trim().substring(1);
        String value = options.get(key);
        if (value != null) {
            return value;
        } else {
            return defaultValue;
        }
    }
    
    /**
     * Obtains the <code>int</code> value for the option having a given key.
     * @param key the key for the option
     * @param defaultValue the specified default value
     * @return the <code>int</code> value of the option
     */
    public int get(String key, int defaultValue) {
        String value = options.get(key);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }
    
    /**
     * Obtains the <code>long</code> value for the option having a given key.
     * @param key the key for the option
     * @param defaultValue the specified default value
     * @return the <code>long</code> value of the option
     */
    public long get(String key, long defaultValue) {
        String value = options.get(key);
        if (value != null) {
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }
    
    /**
     * Obtains the <code>float</code> for the option having a given key.
     * @param key the key for the option
     * @param defaultValue the specified default value
     * @return the <code>float</code> value of the option
     */
    public float get(String key, float defaultValue) {
        String value = options.get(key);
        if (value != null) {
            try {
                return Float.parseFloat(value);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }
    
    /**
     * Obtains the <code>double</code> for the option having a given key.
     * @param key the key for the option
     * @param defaultValue the specified default value
     * @return the <code>double</code> value of the option
     */
    public double get(String key, double defaultValue) {
        String value = options.get(key);
        if (value != null) {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }
}
