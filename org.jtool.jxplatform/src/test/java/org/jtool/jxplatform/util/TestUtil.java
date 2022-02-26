/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.util;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;

public class TestUtil {
    
    private final static String testTargetDir = new File(".").getAbsoluteFile().getParent() + "/test_target/";
    
    public static List<String> asSortedList(Set<String> set) {
        List<String> list = new ArrayList<>(set);
        Collections.sort(list);
        return list;
    }
    
    public static String getTarget(String name) {
        return TestUtil.testTargetDir + name;
    }
}
