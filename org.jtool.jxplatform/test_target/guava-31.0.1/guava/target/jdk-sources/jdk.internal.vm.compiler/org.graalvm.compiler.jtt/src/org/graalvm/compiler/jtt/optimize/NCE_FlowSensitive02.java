/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */


package org.graalvm.compiler.jtt.optimize;

import org.junit.Test;

import org.graalvm.compiler.jtt.JTTTest;

/*
 */
public class NCE_FlowSensitive02 extends JTTTest {

    @SuppressWarnings("all")
    public static String test(String arg) {
        if (arg != null) {
            return arg.toString();
        }
        return arg.toString();
    }

    @Test
    public void run0() throws Throwable {
        runTest("test", (Object) null);
    }

    @Test
    public void run1() throws Throwable {
        runTest("test", "x");
    }

    @Test
    public void run2() throws Throwable {
        runTest("test", "yay");
    }

}
