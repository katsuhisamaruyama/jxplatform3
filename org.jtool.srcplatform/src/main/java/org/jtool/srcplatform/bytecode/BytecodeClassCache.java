/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.bytecode;

import java.util.Map;
import java.util.List;

/**
 * An interface that provides a method that accesses data to be cached.
 * 
 * @author Katsuhisa Maruyama
 */

public interface BytecodeClassCache {
    
    public Map<String, String> getClassCacheData();
    
    public List<Map<String, String>> getMethodCacheData();
}
