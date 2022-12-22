/*
 * Copyright (c) 2016, 2019, Oracle and/or its affiliates. All rights reserved.
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
 *
 *
 */

package jdk.internal.access;

import java.net.URLClassLoader;
import java.security.AccessControlContext;

public interface JavaNetURLClassLoaderAccess {
    AccessControlContext getAccessControlContext(URLClassLoader u);;
}
