/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.graph;

import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class GraphElementTest {
    
    private static Set<ConcreteGraphElement> create(int... args) {
        Set<ConcreteGraphElement> set = new HashSet<>();
        for (int n : args) {
            set.add(new ConcreteGraphElement(n));
        }
        return set;
    }
    
    @Test
    public void testEquals1() {
        Set<ConcreteGraphElement> s1 = create(1, 2, 3);
        Set<ConcreteGraphElement> s2 = create(1, 2, 3);
        boolean result = GraphElement.equals(s1, s2);
        
        assertTrue(result);
    }
    
    @Test
    public void testEquals2() {
        Set<ConcreteGraphElement> s1 = create(1, 2, 3);
        Set<ConcreteGraphElement> s2 = create(1, 3, 4);
        boolean result = GraphElement.equals(s1, s2);
        
        assertFalse(result);
    }
    
    @Test
    public void testEquals3() {
        Set<ConcreteGraphElement> s1 = create();
        Set<ConcreteGraphElement> s2 = create(1, 2, 3);
        boolean result = GraphElement.equals(s1, s2);
        
        assertFalse(result);
    }
    
    @Test
    public void testEquals4() {
        Set<ConcreteGraphElement> s1 = create();
        Set<ConcreteGraphElement> s2 = create(1, 2, 3);
        boolean result = GraphElement.equals(s1, s2);
        
        assertFalse(result);
    }
    
    @Test
    public void testEquals5() {
        Set<ConcreteGraphElement> s1 = create(1, 2, 3);
        Set<ConcreteGraphElement> s2 = create(1, 3, 2);
        boolean result = GraphElement.equals(s1, s2);
        
        assertTrue(result);
    }
    
    @Test
    public void testEquals6() {
        Set<ConcreteGraphElement> s1 = create(1, 2);
        Set<ConcreteGraphElement> s2 = create(1, 3, 2);
        boolean result = GraphElement.equals(s1, s2);
        
        assertFalse(result);
    }
    
    @Test
    public void testEquals7() {
        Set<ConcreteGraphElement> s1 = create();
        Set<ConcreteGraphElement> s2 = create();
        boolean result = GraphElement.equals(s1, s2);
        
        assertTrue(result);
    }
    
    @Test
    public void testDifference1() {
        Set<ConcreteGraphElement> s1 = create(1, 2, 3);
        Set<ConcreteGraphElement> s2 = create(1, 2, 3);
        Set<ConcreteGraphElement> result = GraphElement.difference(s1, s2);
        
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testDifference2() {
        Set<ConcreteGraphElement> s1 = create(1, 2, 3);
        Set<ConcreteGraphElement> s2 = create(1, 2);
        Set<ConcreteGraphElement> result = GraphElement.difference(s1, s2);
        
        assertEquals("3", ConcreteGraphElement.asSortedStr(result));
    }
    
    @Test
    public void testDifference3() {
        Set<ConcreteGraphElement> s1 = create(1, 2, 3);
        Set<ConcreteGraphElement> s2 = create();
        Set<ConcreteGraphElement> result = GraphElement.difference(s1, s2);
        
        assertEquals("1, 2, 3", ConcreteGraphElement.asSortedStr(result));
    }
    
    @Test
    public void testDifference4() {
        Set<ConcreteGraphElement> s1 = create(1, 2, 3);
        Set<ConcreteGraphElement> s2 = create(2);
        Set<ConcreteGraphElement> result = GraphElement.difference(s1, s2);
        
        assertEquals("1, 3", ConcreteGraphElement.asSortedStr(result));
    }
    
    @Test
    public void testDifference5() {
        Set<ConcreteGraphElement> s1 = create(1, 2, 3);
        Set<ConcreteGraphElement> s2 = create(1, 2, 3, 4);
        Set<ConcreteGraphElement> result = GraphElement.difference(s1, s2);
        
        assertEquals("", ConcreteGraphElement.asSortedStr(result));
    }
    
    @Test
    public void testDifference6() {
        Set<ConcreteGraphElement> s1 = create(1, 2, 3);
        Set<ConcreteGraphElement> s2 = create(2, 4, 5);
        Set<ConcreteGraphElement> result = GraphElement.difference(s1, s2);
        
        assertEquals("1, 3", ConcreteGraphElement.asSortedStr(result));
    }
    
    @Test
    public void testDifference7() {
        Set<ConcreteGraphElement> s1 = create();
        Set<ConcreteGraphElement> s2 = create(1, 2);
        Set<ConcreteGraphElement> result = GraphElement.difference(s1, s2);
        
        assertEquals("", ConcreteGraphElement.asSortedStr(result));
    }
    
    @Test
    public void testDifference8() {
        Set<ConcreteGraphElement> s1 = create();
        Set<ConcreteGraphElement> s2 = create();
        Set<ConcreteGraphElement> result = GraphElement.difference(s1, s2);
        
        assertEquals("", ConcreteGraphElement.asSortedStr(result));
    }
    
    @Test
    public void testUnion1() {
        Set<ConcreteGraphElement> s1 = create(1, 2, 3);
        Set<ConcreteGraphElement> s2 = create(4, 5, 6);
        Set<ConcreteGraphElement> result = GraphElement.union(s1, s2);
        
        assertEquals("1, 2, 3, 4, 5, 6", ConcreteGraphElement.asSortedStr(result));
    }
    
    @Test
    public void testUnion2() {
        Set<ConcreteGraphElement> s1 = create(1, 2, 3);
        Set<ConcreteGraphElement> s2 = create(1, 5, 6);
        Set<ConcreteGraphElement> result = GraphElement.union(s1, s2);
        
        assertEquals("1, 2, 3, 5, 6", ConcreteGraphElement.asSortedStr(result));
    }
    
    @Test
    public void testUnion3() {
        Set<ConcreteGraphElement> s1 = create();
        Set<ConcreteGraphElement> s2 = create(4, 5, 6);
        Set<ConcreteGraphElement> result = GraphElement.union(s1, s2);
        
        assertEquals("4, 5, 6", ConcreteGraphElement.asSortedStr(result));
    }
    
    @Test
    public void testUnion4() {
        Set<ConcreteGraphElement> s1 = create(1, 2, 3);
        Set<ConcreteGraphElement> s2 = create();
        Set<ConcreteGraphElement> result = GraphElement.union(s1, s2);
        
        assertEquals("1, 2, 3", ConcreteGraphElement.asSortedStr(result));
    }
    
    @Test
    public void testUnion5() {
        Set<ConcreteGraphElement> s1 = create(1, 2, 3);
        Set<ConcreteGraphElement> s2 = create(1, 3, 2);
        Set<ConcreteGraphElement> result = GraphElement.union(s1, s2);
        
        assertEquals("1, 2, 3", ConcreteGraphElement.asSortedStr(result));
    }
    
    @Test
    public void testUnion6() {
        Set<ConcreteGraphElement> s1 = create();
        Set<ConcreteGraphElement> s2 = create();
        Set<ConcreteGraphElement> result = GraphElement.union(s1, s2);
        
        assertEquals("", ConcreteGraphElement.asSortedStr(result));
    }
    
    @Test
    public void testIntersection1() {
        Set<ConcreteGraphElement> s1 = create(1, 2, 3);
        Set<ConcreteGraphElement> s2 = create(1, 2, 3);
        Set<ConcreteGraphElement> result = GraphElement.intersection(s1, s2);
        
        assertEquals("1, 2, 3", ConcreteGraphElement.asSortedStr(result));
    }
    
    @Test
    public void testIntersection2() {
        Set<ConcreteGraphElement> s1 = create(2, 3);
        Set<ConcreteGraphElement> s2 = create(1, 2, 3);
        Set<ConcreteGraphElement> result = GraphElement.intersection(s1, s2);
        
        assertEquals("2, 3", ConcreteGraphElement.asSortedStr(result));
    }
    
    @Test
    public void testIntersection3() {
        Set<ConcreteGraphElement> s1 = create(1, 2, 3);
        Set<ConcreteGraphElement> s2 = create(3);
        Set<ConcreteGraphElement> result = GraphElement.intersection(s1, s2);
        
        assertEquals("3", ConcreteGraphElement.asSortedStr(result));
    }
    
    @Test
    public void testIntersection4() {
        Set<ConcreteGraphElement> s1 = create(1, 2, 3);
        Set<ConcreteGraphElement> s2 = create();
        Set<ConcreteGraphElement> result = GraphElement.intersection(s1, s2);
        
        assertEquals("", ConcreteGraphElement.asSortedStr(result));
    }
    
    @Test
    public void testIntersection5() {
        Set<ConcreteGraphElement> s1 = create();
        Set<ConcreteGraphElement> s2 = create(1, 2, 3);
        Set<ConcreteGraphElement> result = GraphElement.intersection(s1, s2);
        
        assertEquals("", ConcreteGraphElement.asSortedStr(result));
    }
    
    @Test
    public void testIntersection6() {
        Set<ConcreteGraphElement> s1 = create(1, 2, 3);
        Set<ConcreteGraphElement> s2 = create(4, 5);
        Set<ConcreteGraphElement> result = GraphElement.intersection(s1, s2);
        
        assertEquals("", ConcreteGraphElement.asSortedStr(result));
    }
    
    @Test
    public void testIntersection7() {
        Set<ConcreteGraphElement> s1 = create();
        Set<ConcreteGraphElement> s2 = create();
        Set<ConcreteGraphElement> result = GraphElement.intersection(s1, s2);
        
        assertEquals("", ConcreteGraphElement.asSortedStr(result));
    }
    
    @Test
    public void testSubsetEqual1() {
        Set<ConcreteGraphElement> s1 = create(2, 3);
        Set<ConcreteGraphElement> s2 = create(1, 2, 3);
        boolean result = GraphElement.subsetEqual(s1, s2);
        
        assertTrue(result);
    }
    
    @Test
    public void testSubsetEqual2() {
        Set<ConcreteGraphElement> s1 = create();
        Set<ConcreteGraphElement> s2 = create(1, 2, 3);
        boolean result = GraphElement.subsetEqual(s1, s2);
        
        assertTrue(result);
    }
    
    @Test
    public void testSubsetEqual3() {
        Set<ConcreteGraphElement> s1 = create(1, 2, 3);
        Set<ConcreteGraphElement> s2 = create(2, 3);
        boolean result = GraphElement.subsetEqual(s1, s2);
        
        assertFalse(result);
    }
    
    @Test
    public void testSubsetEqual4() {
        Set<ConcreteGraphElement> s1 = create(1, 2, 3);
        Set<ConcreteGraphElement> s2 = create();
        boolean result = GraphElement.subsetEqual(s1, s2);
        
        assertFalse(result);
    }
    
    @Test
    public void testSubsetEqual5() {
        Set<ConcreteGraphElement> s1 = create(1, 2, 3);
        Set<ConcreteGraphElement> s2 = create(1, 2, 3);
        boolean result = GraphElement.subsetEqual(s1, s2);
        
        assertTrue(result);
    }
    
    @Test
    public void testSubsetEqual6() {
        Set<ConcreteGraphElement> s1 = create();
        Set<ConcreteGraphElement> s2 = create();
        boolean result = GraphElement.subsetEqual(s1, s2);
        
        assertTrue(result);
    }
    
    @Test
    public void testSubset1() {
        Set<ConcreteGraphElement> s1 = create(2, 3);
        Set<ConcreteGraphElement> s2 = create(1, 2, 3);
        boolean result = GraphElement.subset(s1, s2);
        
        assertTrue(result);
    }
    
    @Test
    public void testSubset2() {
        Set<ConcreteGraphElement> s1 = create();
        Set<ConcreteGraphElement> s2 = create(1, 2, 3);
        boolean result = GraphElement.subset(s1, s2);
        
        assertTrue(result);
    }
    
    @Test
    public void testSubset3() {
        Set<ConcreteGraphElement> s1 = create(1, 2, 3);
        Set<ConcreteGraphElement> s2 = create(2, 3);
        boolean result = GraphElement.subset(s1, s2);
        
        assertFalse(result);
    }
    
    @Test
    public void testSubset4() {
        Set<ConcreteGraphElement> s1 = create(1, 2, 3);
        Set<ConcreteGraphElement> s2 = create();
        boolean result = GraphElement.subset(s1, s2);
        
        assertFalse(result);
    }
    
    @Test
    public void testSubset5() {
        Set<ConcreteGraphElement> s1 = create(1, 2, 3);
        Set<ConcreteGraphElement> s2 = create(1, 2, 3);
        boolean result = GraphElement.subset(s1, s2);
        
        assertFalse(result);
    }
    
    @Test
    public void testSubset6() {
        Set<ConcreteGraphElement> s1 = create();
        Set<ConcreteGraphElement> s2 = create();
        boolean result = GraphElement.subset(s1, s2);
        
        assertFalse(result);
    }
}

class ConcreteGraphElement extends GraphElement {
    
    int id = 0;
    
    ConcreteGraphElement(int id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(GraphElement elem) {
        ConcreteGraphElement celem = (ConcreteGraphElement)elem;
        return celem.id == id;
    }
    
    @Override
    public int hashCode() {
        return id;
    }
    
    @Override
    public String toString() {
        return String.valueOf(id);
    }
    
    public static String asSortedStr(Set<ConcreteGraphElement> set) {
        return set.stream().map(ConcreteGraphElement::toString).sorted().collect(Collectors.joining(", "));
    }
}
