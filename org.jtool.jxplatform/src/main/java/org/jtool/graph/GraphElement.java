/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.graph;

import java.util.Set;
import java.util.HashSet;
import java.util.Collection;

/**
 * An abstract element of a graph.
 * 
 * @author Katsuhisa Maruyama
 */
public abstract class GraphElement {
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof GraphElement) ? equals((GraphElement)obj) : false;
    }
    
    /**
     * Tests if a given graph element is equal to this graph element.
     * @param elem the graph element to be checked
     * @return the {@code true} if the given graph element is equal to this graph element
     */
    public abstract boolean equals(GraphElement elem);
    
    /**
     * Obtains the printed identification number.
     * @param id the identification number
     * @return the printed string of the identification number
     */
    public static String getIdString(long id) {
        String idStr = String.valueOf(id);
        if (id < 10) {
            return "   " + idStr;
        } else if (id < 100) {
            return "  " + idStr;
        } else if (id < 1000) {
            return " " + idStr;
        }
        return idStr;
    }
    
    /**
     * Tests if two graph element collections are equal regarded as a set.
     * @param <E> the type of graph elements
     * @param s1 the first collection of graph elements
     * @param s2 the second collection of graph elements
     * @return {@code true} if the two collections are equal, otherwise {@code false}
     */
    public static <E extends GraphElement> boolean equals(Collection<E> s1, Collection<E> s2) {
        if (s1.size() != s2.size()) {
            return false;
        }
        Set<E> s = difference(s1, s2);
        return s.isEmpty();
    }
    
    /**
     * Obtains a difference set of two graph element collections regarded as a set.
     * @param <E> the type of graph elements
     * @param s1 the first collection of graph elements
     * @param s2 the second collection of graph elements
     * @return the set of graph elements that remain after removing the second one from the first one
     */
    public static <E extends GraphElement> Set<E> difference(Collection<E> s1, Collection<E> s2) {
        Set<E> s = new HashSet<>();
        s.addAll(s1);
        s2.forEach(e -> s.remove(e));
        return s;
    }
    
    /**
     * Obtains a union set of two graph element collections.
     * @param <E> the type of graph elements
     * @param s1 the first collection of graph elements
     * @param s2 the second collection of graph elements
     * @return the set of graph elements that are contained in either the first one or the second one
     */
    public static <E extends GraphElement> Set<E> union(Collection<E> s1, Collection<E> s2) {
        Set<E> s = new HashSet<>();
        s.addAll(s1);
        s.addAll(s2);
        return s;
    }
    
    /**
     * Obtains an intersection set of two graph element collections.
     * @param <E> the type of graph elements
     * @param s1 the first collection of graph elements
     * @param s2 the second collection of graph elements
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
     * Tests if one graph element collection is contained in or equal to the other collection.
     * @param <E> the type of graph elements
     * @param s1 the first collection of graph elements
     * @param s2 the second collection of graph elements
     * @return {@code true} if all graph elements of the first one are contained in the second one,
     * otherwise {@code false}
     */
    public static <E extends GraphElement> boolean subsetEqual(Collection<E> s1, Collection<E> s2) {
        Set<E> s = difference(s1, s2);
        return s.isEmpty();
    }
    
    /**
     * Tests if one graph element collection is contained in and not equal to the other collection.
     * @param <E> the type of graph elements
     * @param s1 the first set of graph elements
     * @param s2 the second set of graph elements
     * @return {@code true} if all graph elements of the first one are contained in the second one
     * and the two collections are not equal, otherwise {@code false}
     */
    public static <E extends GraphElement> boolean subset(Collection<E> s1, Collection<E> s2) {
        return subsetEqual(s1, s2) && s1.size() < s2.size();
    }
}
