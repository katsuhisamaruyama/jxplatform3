/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.util;

import org.jtool.srcmodel.JavaElement;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.jdt.core.dom.ITypeBinding;

public class TestUtil {
    
public static <T> List<String> asSortedList(Set<T> set) {
        return set.stream().map(o -> o.toString()).sorted().collect(Collectors.toList());
    }
    
public static <T> List<String> asSortedList(List<T> list) {
        return list.stream().map(o -> o.toString()).sorted().collect(Collectors.toList());
    }
    
public static <T> List<String> asSortedList(Stream<T> stream) {
        return stream.map(o -> o.toString()).sorted().collect(Collectors.toList());
    }
    
    public static <T> String asSortedStr(Stream<String> stream) {
        return stream.sorted().collect(Collectors.joining(";"));
    }
    
    public static String asSortedStrOfTypeBinding(Set<ITypeBinding> set) {
        return asSortedStr(set.stream().map(e -> e.getQualifiedName()));
    }
    
    public static String asSortedStrOf(List<? extends JavaElement> list) {
        return asSortedStr(list.stream().map(e -> e.getQualifiedName().fqn()));
    }
    
    public static String asSortedStrOf(Set<? extends JavaElement> set) {
        return asSortedStr(set.stream().map(e -> e.getQualifiedName().fqn()));
    }
    
    public static String asSortedStr(List<String> list) {
        return asSortedStr(list.stream());
    }
    
    public static String asSortedStr(Set<String> set) {
        return asSortedStr(set.stream());
    }
}
