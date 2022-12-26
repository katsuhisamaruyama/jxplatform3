/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.util;

/**
 * A marker interface indicating that the test results are subject to change due to the structure of external libraries.
 * Test cases with this marker should be executed under the specific environment, macOS 12.6 and Java 11.0.6+8-LTS.
 * 
 * @author Katsuhisa Maruyama
 */
public interface FlakyByExternalLib {
}
