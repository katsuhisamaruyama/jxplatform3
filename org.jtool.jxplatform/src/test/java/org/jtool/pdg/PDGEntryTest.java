/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.QualifiedName;
import org.jtool.cfg.CFGEntry;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.PDGTestUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Tests a class that builds a CFG.
 * 
 * @author Katsuhisa Maruyama
 */
public class PDGEntryTest {
    
    private static JavaProject SliceProject;
    
    @BeforeClass
    public static void setUp() {
        SliceProject = BuilderTestUtil.createProject("Slice", "", "");
    }
    
    @AfterClass
    public static void tearDown() {
        SliceProject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testGetQualifiedNameTest101_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test101", "m( )");
        PDGEntry node = pdg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("Test101#m( )", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNameTest101_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test101", "p");
        PDGEntry node = pdg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("Test101#p", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNamePriceCode_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "PriceCode", "getPriceCode( )");
        PDGEntry node = pdg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("PriceCode#getPriceCode( )", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNamePriceCode_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "PriceCode", "CHILDRENS");
        PDGEntry node = pdg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("PriceCode#CHILDRENS", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNameTest134() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "I134", "f( int )");
        PDGEntry node = pdg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("I134#f( int )", result.fqn());
    }
    
    @Test
    public void testGetSignartureTest101_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test101", "m( )");
        PDGEntry node = pdg.getEntryNode();
        String result = node.getSignature();
        
        assertEquals("m( )", result);
    }
    
    @Test
    public void testGetSignartureTest101_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test101", "p");
        PDGEntry node = pdg.getEntryNode();
        String result = node.getSignature();
        
        assertEquals("p", result);
    }
    
    @Test
    public void testGetSignarturePriceCode_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "PriceCode", "getPriceCode( )");
        PDGEntry node = pdg.getEntryNode();
        String result = node.getSignature();
        
        assertEquals("getPriceCode( )", result);
    }
    
    @Test
    public void testGetSignarturePriceCode_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "PriceCode", "CHILDRENS");
        PDGEntry node = pdg.getEntryNode();
        String result = node.getSignature();
        
        assertEquals("CHILDRENS", result);
    }
    
    @Test
    public void testGetSignartureTest134() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "I134", "f( int )");
        PDGEntry node = pdg.getEntryNode();
        String result = node.getSignature();
        
        assertEquals("f( int )", result);
    }
    
    @Test
    public void testGetCFGEntryTest101_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test101", "m( )");
        PDGEntry node = pdg.getEntryNode();
        CFGEntry result = node.getCFGEntry();
        
        assertEquals("Test101#m( )", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetCFGEntryTest101_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test101", "p");
        PDGEntry node = pdg.getEntryNode();
        CFGEntry result = node.getCFGEntry();
        
        assertEquals("Test101#p", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetCFGEntryPriceCode_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "PriceCode", "getPriceCode( )");
        PDGEntry node = pdg.getEntryNode();
        CFGEntry result = node.getCFGEntry();
        
        assertEquals("PriceCode#getPriceCode( )", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetCFGEntryPriceCode_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "PriceCode", "CHILDRENS");
        PDGEntry node = pdg.getEntryNode();
        CFGEntry result = node.getCFGEntry();
        
        assertEquals("PriceCode#CHILDRENS", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetCFGEntryTest134() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "I134", "f( int )");
        PDGEntry node = pdg.getEntryNode();
        CFGEntry result = node.getCFGEntry();
        
        assertEquals("I134#f( int )", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetPDGTest101_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test101", "m( )");
        PDGEntry node = pdg.getEntryNode();
        PDG result = node.getPDG();
        
        assertEquals(pdg, result);
    }
    
    @Test
    public void testGetPDGTest101_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test101", "p");
        PDGEntry node = pdg.getEntryNode();
        PDG result = node.getPDG();
        
        assertEquals(pdg, result);
    }
    
    @Test
    public void testGetPDGPriceCode_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "PriceCode", "getPriceCode( )");
        PDGEntry node = pdg.getEntryNode();
        PDG result = node.getPDG();
        
        assertEquals(pdg, result);
    }
    
    @Test
    public void testGetPDGPriceCode_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "PriceCode", "CHILDRENS");
        PDGEntry node = pdg.getEntryNode();
        PDG result = node.getPDG();
        
        assertEquals(pdg, result);
    }
    
    @Test
    public void testGetPDGTest134() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "I134", "f( int )");
        PDGEntry node = pdg.getEntryNode();
        PDG result = node.getPDG();
        
        assertEquals(pdg, result);
    }
}
