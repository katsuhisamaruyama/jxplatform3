/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.util;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestUtil {
    
    private final static String testTargetDir = new File(".").getAbsoluteFile().getParent() + "/test_target/";
    
    public static <T> List<String> asSortedList(Set<T> set) {
        return set.stream().map(o -> o.toString()).sorted().collect(Collectors.toList());
    }
    
    public static <T> List<String> asSortedList(List<T> list) {
        return list.stream().map(o -> o.toString()).sorted().collect(Collectors.toList());
    }
    
    public static <T> List<String> asSortedList(Stream<T> stream) {
        return stream.map(o -> o.toString()).sorted().collect(Collectors.toList());
    }
    
    public static String getTarget(String name) {
        return TestUtil.testTargetDir + name;
    }
}