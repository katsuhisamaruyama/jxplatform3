/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.graph;

import java.util.Set;
import java.util.HashSet;

/**
 * An abstract element of a graph.
 * 
 * @author Katsuhsa Maruyama
 */
public abstract class GraphElement {
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GraphElement) {
            return equals((GraphElement)obj);
        }
        return false;
    }
    
    /**
     * Tests if a given graph element is equal to this graph element.
     * @param elem the graph element to be checked
     * @return the {@code true} if the given graph element is equal to this graph element
     */
    public abstract boolean equals(GraphElement elem);
    
    /**
     * Tests if two graph element sets are equal.
     * @param s1 the first set of graph elements
     * @param s2 the second set of graph elements
     * @return {@code true} if the two sets are equal, otherwise {@code false}
     */
    public static <E extends GraphElement> boolean equals(Set<E> s1, Set<E> s2) {
        if (s1.size() != s2.size()) {
            return false;
        }
        Set<E> s = difference(s1, s2);
        return s.isEmpty();
    }
    
    /**
     * Obtains a difference set of two graph element sets.
     * @param s1 the first set of graph elements
     * @param s2 the second set of graph elements
     * @return the collection of graph elements that remain after removing the second one from the first one
     */
    public static <E extends GraphElement> Set<E> difference(Set<E> s1, Set<E> s2) {
        Set<E> s = new HashSet<>();
        s.addAll(s1);
        s2.forEach(e -> s.remove(e));
        return s;
    }
    
    /**
     * Obtains a union set of two graph element sets.
     * @param s1 the first set of graph elements
     * @param s2 the second set of graph elements
     * @return the collection of graph elements that are contained in either the first one or the second one
     */
    public static <E extends GraphElement> Set<E> union(Set<E> s1, Set<E> s2) {
        Set<E> s = new HashSet<>();
        s.addAll(s1);
        s.addAll(s2);
        return s;
    }
    
    /**
     * Obtains an intersection set of two graph element sets.
     * @param s1 the first set of graph elements
     * @param s2 the second set of graph elements
     * @return the collection of graph elements that are contained in both the first one and the second one
     */
    public static <E extends GraphElement> Set<E> intersection(Set<E> s1, Set<E> s2) {
        Set<E> s = new HashSet<>();
        if (s1.size() > s2.size()) {
            for (E e : s2) {
                if (s1.contains(e)) {
                    s.add(e);
                }
            }
        } else {
            for (E e : s1) {
                if (s2.contains(e)) {
                    s.add(e);
                }
            }
        }
        return s;
    }
    
    /**
     * Tests if one graph element set is the subset of or equals to the other set.
     * @param s1 the first set of graph elements
     * @param s2 the second set of graph elements
     * @return {@code true} if all graph elements of the first one are contained in the second one,
     * otherwise {@code false}
     */
    public static <E extends GraphElement> boolean subsetEqual(Set<E> s1, Set<E> s2) {
        Set<E> s = difference(s1, s2);
        return s.isEmpty();
    }
    
    /**
     * Tests if one graph element set is the subset of the other set.
     * @param s1 the first set of graph elements
     * @param s2 the second set of graph elements
     * @return {@code true} if all graph elements of the first one are contained in the second one
     * and the two sets are not equal, otherwise {@code false}
     */
    public static <E extends GraphElement> boolean subset(Set<E> s1, Set<E> s2) {
        return subsetEqual(s1, s2) && s1.size() < s2.size();
    }
}
