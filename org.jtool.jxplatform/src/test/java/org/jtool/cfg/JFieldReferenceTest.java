/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import static org.jtool.cfg.AllCFGTests.SliceProject;
import org.jtool.cfg.internal.CFGTestUtil;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import java.util.List;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class JFieldReferenceTest {
    
    @BeforeClass
    public static void setUp() {
        SliceProject = BuilderTestUtil.getProject("Slice");
    }
    
    @Test
    public void testGetEnclosingClassName1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("Test103", result.get(0).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingClassName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("Test103", result.get(0).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingClassName3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("Test103", result.get(0).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingClassName4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("Test103", result.get(0).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingClassName5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("Test135", result.get(0).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingClassName6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("Test135", result.get(0).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingClassName7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "n( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("Test132", result.get(0).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingClassName8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("Test139", result.get(0).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingClassName9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "getPriceCode( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("PriceCode", result.get(0).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingClassName10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("S140", result.get(0).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingClassName11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("Test131", result.get(0).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingClassName12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("Test131", result.get(0).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingClassName13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("Test135", result.get(0).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingClassName14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("Test135", result.get(0).getEnclosingClassName());
    }
    
    @Test
    public void testGetEnclosingMethodName1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("m( )", result.get(0).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("m( )", result.get(0).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("getA( )", result.get(0).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("setA( int )", result.get(0).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("m( )", result.get(0).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("m( )", result.get(0).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "n( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("n( )", result.get(0).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("m( )", result.get(0).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "getPriceCode( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("getPriceCode( )", result.get(0).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("m( )", result.get(0).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("", result.get(0).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("", result.get(0).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("", result.get(0).getEnclosingMethodName());
    }
    
    @Test
    public void testGetEnclosingMethodName14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("", result.get(0).getEnclosingMethodName());
    }
    
    @Test
    public void testGetDeclaringClassName1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("Test103", result.get(0).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("Test103", result.get(0).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("Test103", result.get(0).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("Test103", result.get(0).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("Test135", result.get(0).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("S135", result.get(0).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "n( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("P132", result.get(0).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("PriceCode", result.get(0).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "getPriceCode( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("PriceCode", result.get(0).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("Test140", result.get(0).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("Test131", result.get(0).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("Test131", result.get(0).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("Test135", result.get(0).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringClassName14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("Test135", result.get(0).getDeclaringClassName());
    }
    
    @Test
    public void testGetDeclaringMethodName1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("", result.get(0).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("", result.get(0).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("", result.get(0).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("", result.get(0).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("", result.get(0).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("", result.get(0).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "n( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("", result.get(0).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("", result.get(0).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "getPriceCode( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("", result.get(0).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("", result.get(0).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("", result.get(0).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("", result.get(0).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("", result.get(0).getDeclaringMethodName());
    }
    
    @Test
    public void testGetDeclaringMethodName14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("", result.get(0).getDeclaringMethodName());
    }
    
    @Test
    public void testGetName1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("a", result.get(0).getName());
    }
    
    @Test
    public void testGetName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("a", result.get(0).getName());
    }
    
    @Test
    public void testGetName3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("a", result.get(0).getName());
    }
    
    @Test
    public void testGetName4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("a", result.get(0).getName());
    }
    
    @Test
    public void testGetName5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("s1", result.get(0).getName());
    }
    
    @Test
    public void testGetName6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("value", result.get(0).getName());
    }
    
    @Test
    public void testGetName7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "n( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("x", result.get(0).getName());
    }
    
    @Test
    public void testGetName8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("CHILDRENS", result.get(0).getName());
    }
    
    @Test
    public void testGetName9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "getPriceCode( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("priceCode", result.get(0).getName());
    }
    
    @Test
    public void testGetName10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("x", result.get(0).getName());
    }
    
    @Test
    public void testGetName11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("map", result.get(0).getName());
    }
    
    @Test
    public void testGetName12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("map", result.get(0).getName());
    }
    
    @Test
    public void testGetName13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("s1", result.get(0).getName());
    }
    
    @Test
    public void testGetName14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("s1", result.get(0).getName());
    }
    
    @Test
    public void testGetSignature1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("a", result.get(0).getSignature());
    }
    
    @Test
    public void testGetSignature2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("a", result.get(0).getSignature());
    }
    
    @Test
    public void testGetSignature3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("a", result.get(0).getSignature());
    }
    
    @Test
    public void testGetSignature4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("a", result.get(0).getSignature());
    }
    
    @Test
    public void testGetSignature5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("s1", result.get(0).getSignature());
    }
    
    @Test
    public void testGetSignature6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("value", result.get(0).getSignature());
    }
    
    @Test
    public void testGetSignature7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "n( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("x", result.get(0).getSignature());
    }
    
    @Test
    public void testGetSignature8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("CHILDRENS", result.get(0).getSignature());
    }
    
    @Test
    public void testGetSignature9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "getPriceCode( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("priceCode", result.get(0).getSignature());
    }
    
    @Test
    public void testGetSignature10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("x", result.get(0).getSignature());
    }
    
    @Test
    public void testGetSignature11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("map", result.get(0).getSignature());
    }
    
    @Test
    public void testGetSignature12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("map", result.get(0).getSignature());
    }
    
    @Test
    public void testGetSignature13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("s1", result.get(0).getSignature());
    }
    
    @Test
    public void testGetSignature14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("s1", result.get(0).getSignature());
    }
    
    @Test
    public void testGetQualifiedName1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("Test103#a", result.get(0).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("Test103#a", result.get(0).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("Test103#a", result.get(0).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("Test103#a", result.get(0).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("Test135#s1", result.get(0).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("S135#value", result.get(0).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "n( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("P132#x", result.get(0).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("PriceCode#CHILDRENS", result.get(0).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "getPriceCode( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("PriceCode#priceCode", result.get(0).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("Test140#x", result.get(0).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("Test131#map", result.get(0).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("Test131#map", result.get(0).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("Test135#s1", result.get(0).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("Test135#s1", result.get(0).getQualifiedName().fqn());
    }
    
    @Test
    public void testGetReferenceForm1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("this.a", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("this.a", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("this.a", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("this.a", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("this.s1", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("this.s1.value", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "n( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("p$0.x", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("PriceCode.CHILDRENS", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "getPriceCode( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("this.priceCode", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("super.x", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("this.map", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("this.map", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("this.s1", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetReferenceForm14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("this.s1", result.get(0).getReferenceForm());
    }
    
    @Test
    public void testGetReceiverName1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("this", result.get(0).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("this", result.get(0).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("this", result.get(0).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("this", result.get(0).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("this", result.get(0).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("this.s1", result.get(0).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "n( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("p$0", result.get(0).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("PriceCode", result.get(0).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "getPriceCode( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("this", result.get(0).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("super", result.get(0).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("this", result.get(0).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("this", result.get(0).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("this", result.get(0).getReceiverName());
    }
    
    @Test
    public void testGetReceiverName14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("this", result.get(0).getReceiverName());
    }
    
    @Test
    public void testGetType1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("int", result.get(0).getType());
    }
    
    @Test
    public void testGetType2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("int", result.get(0).getType());
    }
    
    @Test
    public void testGetType3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("int", result.get(0).getType());
    }
    
    @Test
    public void testGetType4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("int", result.get(0).getType());
    }
    
    @Test
    public void testGetType5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("S135", result.get(0).getType());
    }
    
    @Test
    public void testGetType6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("java.lang.String", result.get(0).getType());
    }
    
    @Test
    public void testGetType7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "n( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("int", result.get(0).getType());
    }
    
    @Test
    public void testGetType8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("PriceCode", result.get(0).getType());
    }
    
    @Test
    public void testGetType9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "getPriceCode( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("int", result.get(0).getType());
    }
    
    @Test
    public void testGetType10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("int", result.get(0).getType());
    }
    
    @Test
    public void testGetType11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("java.util.HashMap", result.get(0).getType());
    }
    
    @Test
    public void testGetType12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("java.util.HashMap", result.get(0).getType());
    }
    
    @Test
    public void testGetType13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertEquals("S135", result.get(0).getType());
    }
    
    @Test
    public void testGetType14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("S135", result.get(0).getType());
    }
    
    @Test
    public void testGetType15() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test107", "x");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("java.lang.String[][]", result.get(0).getType());
    }
    
    @Test
    public void testGetType16() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test107", "y");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertEquals("java.lang.String[]", result.get(0).getType());
    }
    
    @Test
    public void testIsPrimitiveType1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "n( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "getPriceCode( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isPrimitiveType());
    }
    
    @Test
    public void testIsPrimitiveType14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isPrimitiveType());
    }
    
    @Test
    public void testIsFieldAccess1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "n( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "getPriceCode( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsFieldAccess14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isFieldAccess());
    }
    
    @Test
    public void testIsThisAccess1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "n( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "getPriceCode( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isLocalAccess());
    }
    
    @Test
    public void testIsLocalAccess14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isLocalAccess());
    }
    
    @Test
    public void testIsVariableAccess1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isVariableAccess());
    }
    
    @Test
    public void testIsVariableAccess2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isVariableAccess());
    }
    
    @Test
    public void testIsVariableAccess3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isVariableAccess());
    }
    
    @Test
    public void testIsVariableAccess4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isVariableAccess());
    }
    
    @Test
    public void testIsVariableAccess5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isVariableAccess());
    }
    
    @Test
    public void testIsVariableAccess6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isVariableAccess());
    }
    
    @Test
    public void testIsVariableAccess7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "n( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isVariableAccess());
    }
    
    @Test
    public void testIsVariableAccess8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isVariableAccess());
    }
    
    @Test
    public void testIsVariableAccess9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "getPriceCode( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isVariableAccess());
    }
    
    @Test
    public void testIsVariableAccess10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isVariableAccess());
    }
    
    @Test
    public void testIsVariableAccess11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isVariableAccess());
    }
    
    @Test
    public void testIsVariableAccess12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isVariableAccess());
    }
    
    @Test
    public void testIsVariableAccess13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isVariableAccess());
    }
    
    @Test
    public void testIsVariableAccess14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isVariableAccess());
    }
    
    @Test
    public void testIsMethodCall1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "n( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "getPriceCode( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsMethodCall14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isMethodCall());
    }
    
    @Test
    public void testIsAvailable1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isAvailable());
    }
    
    @Test
    public void testIsAvailable2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isAvailable());
    }
    
    @Test
    public void testIsAvailable3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isAvailable());
    }
    
    @Test
    public void testIsAvailable4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isAvailable());
    }
    
    @Test
    public void testIsAvailable5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isAvailable());
    }
    
    @Test
    public void testIsAvailable6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isAvailable());
    }
    
    @Test
    public void testIsAvailable7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "n( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isAvailable());
    }
    
    @Test
    public void testIsAvailable8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isAvailable());
    }
    
    @Test
    public void testIsAvailable9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "getPriceCode( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isAvailable());
    }
    
    @Test
    public void testIsAvailable10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isAvailable());
    }
    
    @Test
    public void testIsAvailable11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isAvailable());
    }
    
    @Test
    public void testIsAvailable12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isAvailable());
    }
    
    @Test
    public void testIsAvailable13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isAvailable());
    }
    
    @Test
    public void testIsAvailable14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isAvailable());
    }
    
    
    @Test
    public void testIsAvailable15() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test140", "Test140( int )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isAvailable());
    }
    
    @Test
    public void testIsField1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isField());
    }
    
    @Test
    public void testIsField2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isField());
    }
    
    @Test
    public void testIsField3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isField());
    }
    
    @Test
    public void testIsField4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isField());
    }
    
    @Test
    public void testIsField5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isField());
    }
    
    @Test
    public void testIsField6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isField());
    }
    
    @Test
    public void testIsField7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "n( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isField());
    }
    
    @Test
    public void testIsField8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isField());
    }
    
    @Test
    public void testIsField9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "getPriceCode( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isField());
    }
    
    @Test
    public void testIsField10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isField());
    }
    
    @Test
    public void testIsField11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isField());
    }
    
    @Test
    public void testIsField12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isField());
    }
    
    @Test
    public void testIsField13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isField());
    }
    
    @Test
    public void testIsField14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isField());
    }
    
    @Test
    public void testIsEnumConstant1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isEnumConstant());
    }
    
    @Test
    public void testIsEnumConstant2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isEnumConstant());
    }
    
    @Test
    public void testIsEnumConstant3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isEnumConstant());
    }
    
    @Test
    public void testIsEnumConstant4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isEnumConstant());
    }
    
    @Test
    public void testIsEnumConstant5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isEnumConstant());
    }
    
    @Test
    public void testIsEnumConstant6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isEnumConstant());
    }
    
    @Test
    public void testIsEnumConstant7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "n( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isEnumConstant());
    }
    
    @Test
    public void testIsEnumConstant8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isEnumConstant());
    }
    
    @Test
    public void testIsEnumConstant9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "getPriceCode( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isEnumConstant());
    }
    
    @Test
    public void testIsEnumConstant10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isEnumConstant());
    }
    
    @Test
    public void testIsEnumConstant11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isEnumConstant());
    }
    
    @Test
    public void testIsEnumConstant12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isEnumConstant());
    }
    
    @Test
    public void testIsEnumConstant13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isEnumConstant());
    }
    
    @Test
    public void testIsEnumConstant14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isEnumConstant());
    }
    
    @Test
    public void testIsThis1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isThis());
    }
    
    @Test
    public void testIsThis2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isThis());
    }
    
    @Test
    public void testIsThis3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isThis());
    }
    
    @Test
    public void testIsThis4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isThis());
    }
    
    @Test
    public void testIsThis5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isThis());
    }
    
    @Test
    public void testIsThis6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isThis());
    }
    
    @Test
    public void testIsThis7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "n( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isThis());
    }
    
    @Test
    public void testIsThis8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isThis());
    }
    
    @Test
    public void testIsThis9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "getPriceCode( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isThis());
    }
    
    @Test
    public void testIsThis10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isThis());
    }
    
    @Test
    public void testIsThis11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isThis());
    }
    
    @Test
    public void testIsThis12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isThis());
    }
    
    @Test
    public void testIsThis13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertTrue(result.get(0).isThis());
    }
    
    @Test
    public void testIsThis14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isThis());
    }
    
    @Test
    public void testIsSuper1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isSuper());
    }
    
    @Test
    public void testIsSuper2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isSuper());
    }
    
    @Test
    public void testIsSuper3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isSuper());
    }
    
    @Test
    public void testIsSuper4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isSuper());
    }
    
    @Test
    public void testIsSuper5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isSuper());
    }
    
    @Test
    public void testIsSuper6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "m( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isSuper());
    }
    
    @Test
    public void testIsSuper7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test132", "n( )");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isSuper());
    }
    
    @Test
    public void testIsSuper8() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isSuper());
    }
    
    @Test
    public void testIsSuper9() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "getPriceCode( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isSuper());
    }
    
    @Test
    public void testIsSuper10() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S140", "m( )");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertTrue(result.get(0).isSuper());
    }
    
    @Test
    public void testIsSuper11() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isSuper());
    }
    
    @Test
    public void testIsSuper12() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "map");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isSuper());
    }
    
    @Test
    public void testIsSuper13() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getDefFieldReference(cfg);
        
        assertFalse(result.get(0).isSuper());
    }
    
    @Test
    public void testIsSuper14() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test135", "s1");
        List<JFieldReference> result = CFGTestUtil.getUseFieldReference(cfg);
        
        assertFalse(result.get(0).isSuper());
    }
}
