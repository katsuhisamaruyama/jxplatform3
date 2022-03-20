/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.util;

import org.jtool.srcmodel.JavaElement;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CCFG;
import org.jtool.pdg.DependenceGraph;
import org.jtool.cfg.JReference;
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
    
    public static String asSortedStrOfTypeBinding(List<ITypeBinding> set) {
        return asSortedStr(set.stream().map(e -> e.getQualifiedName()));
    }
    
    public static String asSortedStrOf(List<? extends JavaElement> list) {
        return asSortedStr(list.stream().map(e -> e.getQualifiedName().fqn()));
    }
    
    public static String asStrOf(List<? extends JavaElement> list) {
        return list.stream().map(e -> e.getQualifiedName().fqn()).collect(Collectors.joining(";"));
    }
    
    public static String asStrOfReference(List<? extends JReference> list) {
        return list.stream().map(e -> e.getReferenceForm()).collect(Collectors.joining(";"));
    }
    
    public static String asStr(List<? extends Object> list) {
        return list.stream().map(Object::toString).collect(Collectors.joining(";"));
    }
    
    public static String asSortedStrOf(Set<? extends JavaElement> set) {
        return asSortedStr(set.stream().map(e -> e.getQualifiedName().fqn()));
    }
    
    public static String asSortedStrOfCFG(Set<? extends CFG> set) {
        return asSortedStr(set.stream().map(e -> e.getQualifiedName().fqn()));
    }
    
    public static String asSortedStrOfCCFG(Set<? extends CCFG> set) {
        return asSortedStr(set.stream().map(e -> e.getQualifiedName().fqn()));
    }
    
    public static String asSortedStrOfPDG(Set<? extends DependenceGraph> set) {
        return asSortedStr(set.stream().map(e -> e.getQualifiedName().fqn()));
    }
    
    public static String asSortedStr(List<String> list) {
        return asSortedStr(list.stream());
    }
    
    public static String asSortedStr(Set<String> set) {
        return asSortedStr(set.stream());
    }
}
