/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import java.util.List;
import java.util.Set;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PDGTest {
    
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
    public void testGetNodes_Test103_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.getPDG("Test103#m( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(40, result.size());
    }
    
    @Test
    public void testGetNodes_Test103_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.getPDG("Test103#setA( int )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetNodes_Test103_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.getPDG("Test103#getA( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodes_Test103_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.getPDG("Test103#incA( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodes_Test103_5() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.getPDG("Test103#Test103( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodes_Test103_6() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.getPDG("Test103#a");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodes_Test119_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test119");
        PDG pdg = cldg.getPDG("Test119#m( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(32, result.size());
    }
    
    @Test
    public void testGetNodes_Test119_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test119");
        PDG pdg = cldg.getPDG("Test119#getP( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodes_Test119_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test119");
        PDG pdg = cldg.getPDG("Test119#Test119( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodes_Test119_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test119");
        PDG pdg = cldg.getPDG("Test119#p");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodes_A119_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "A119");
        PDG pdg = cldg.getPDG("A119#x");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodes_A119_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "A119");
        PDG pdg = cldg.getPDG("A119#y");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodes_Test129_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test129");
        PDG pdg = cldg.getPDG("Test129#m( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(71, result.size());
    }
    
    @Test
    public void testGetNodes_Test129_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test129");
        PDG pdg = cldg.getPDG("Test129#s1");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetNodes_Test129_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test129");
        PDG pdg = cldg.getPDG("Test129#s2");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetNodes_P129_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        PDG pdg = cldg.getPDG("P129#get1( java.lang.String )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(7, result.size());
    }
    
    @Test
    public void testGetNodes_P129_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        PDG pdg = cldg.getPDG("P129#set1( java.lang.String java.lang.String )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(8, result.size());
    }
    
    @Test
    public void testGetNodes_P129_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        PDG pdg = cldg.getPDG("P129#map");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetNodes_P129_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        PDG pdg = cldg.getPDG("P129#key");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodes_P129_5() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        PDG pdg = cldg.getPDG("P129#value");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodes_Q134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Q134");
        PDG pdg = cldg.getPDG("Q134#f( int )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetNodes_R134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "R134");
        PDG pdg = cldg.getPDG("R134#f( int )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetNodes_I134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "I134");
        PDG pdg = cldg.getPDG("I134#f( int )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodes_Test139_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test139");
        PDG pdg = cldg.getPDG("Test139#m( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(12, result.size());
    }
    
    @Test
    public void testGetNodes_PriceCode_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDG pdg = cldg.getPDG("PriceCode#PriceCode( int )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetNodes_PriceCode_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDG pdg = cldg.getPDG("PriceCode#getPriceCode( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodes_PriceCode_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDG pdg = cldg.getPDG("PriceCode#priceCode");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodes_PriceCode_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDG pdg = cldg.getPDG("PriceCode#CHILDRENS");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetNodes_PriceCode_5() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDG pdg = cldg.getPDG("PriceCode#REGULAR");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetNodes_PriceCode_6() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDG pdg = cldg.getPDG("PriceCode#NEW_RELEASE");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetNodes_Customer_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        PDG pdg = cldg.getPDG("Customer#Customer( java.lang.String )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetNodes_Customer_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        PDG pdg = cldg.getPDG("Customer#statement( Order )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(16, result.size());
    }
    
    @Test
    public void testGetNodes_Customer_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        PDG pdg = cldg.getPDG("Customer#getAmount( Order )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(11, result.size());
    }
    
    @Test
    public void testGetNodes_Customer_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        PDG pdg = cldg.getPDG("Customer#setDiscount( double )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetNodes_Customer_5() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        PDG pdg = cldg.getPDG("Customer#name");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodes_Customer_6() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        PDG pdg = cldg.getPDG("Customer#discount");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodes_CustomerTest_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "CustomerTest");
        PDG pdg = cldg.getPDG("CustomerTest#CustomerTest( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodes_CustomerTest_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "CustomerTest");
        PDG pdg = cldg.getPDG("CustomerTest#testStatement1( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(38, result.size());
    }
    
    @Test
    public void testGetNodes_CustomerTest_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "CustomerTest");
        PDG pdg = cldg.getPDG("CustomerTest#testStatement2( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(50, result.size());
    }
    
    @Test
    public void testGetNodes_Order_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Order");
        PDG pdg = cldg.getPDG("Order#Order( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodes_Order_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Order");
        PDG pdg = cldg.getPDG("Order#addRental( Rental )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(7, result.size());
    }
    
    @Test
    public void testGetNodes_Order_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Order");
        PDG pdg = cldg.getPDG("Order#getSize( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetNodes_Order_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Order");
        PDG pdg = cldg.getPDG("Order#rentals");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetNodes_Rental_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Rental");
        PDG pdg = cldg.getPDG("Rental#Rental( int int )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetNodes_Rental_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Rental");
        PDG pdg = cldg.getPDG("Rental#getCharge( double )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(8, result.size());
    }
    
    @Test
    public void testGetNodes_Rental_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Rental");
        PDG pdg = cldg.getPDG("Rental#price");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodes_Rental_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Rental");
        PDG pdg = cldg.getPDG("Rental#days");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdges_Test103_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.getPDG("Test103#m( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(67, result.size());
    }
    
    @Test
    public void testGetEdges_Test103_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.getPDG("Test103#setA( int )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetEdges_Test103_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.getPDG("Test103#getA( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetEdges_Test103_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.getPDG("Test103#incA( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdges_Test103_5() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.getPDG("Test103#Test103( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdges_Test103_6() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.getPDG("Test103#a");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdges_Test119_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test119");
        PDG pdg = cldg.getPDG("Test119#m( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(58, result.size());
    }
    
    @Test
    public void testGetEdges_Test119_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test119");
        PDG pdg = cldg.getPDG("Test119#getP( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetEdges_Test119_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test119");
        PDG pdg = cldg.getPDG("Test119#Test119( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdges_Test119_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test119");
        PDG pdg = cldg.getPDG("Test119#p");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdges_A119_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "A119");
        PDG pdg = cldg.getPDG("A119#x");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdges_A119_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "A119");
        PDG pdg = cldg.getPDG("A119#y");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdges_Test129_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test129");
        PDG pdg = cldg.getPDG("Test129#m( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(113, result.size());
    }
    
    @Test
    public void testGetEdges_Test129_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test129");
        PDG pdg = cldg.getPDG("Test129#s1");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetEdges_Test129_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test129");
        PDG pdg = cldg.getPDG("Test129#s2");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetEdges_P129_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        PDG pdg = cldg.getPDG("P129#get1( java.lang.String )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(9, result.size());
    }
    
    @Test
    public void testGetEdges_P129_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        PDG pdg = cldg.getPDG("P129#set1( java.lang.String java.lang.String )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(10, result.size());
    }
    
    @Test
    public void testGetEdges_P129_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        PDG pdg = cldg.getPDG("P129#map");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetEdges_P129_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        PDG pdg = cldg.getPDG("P129#key");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdges_P129_5() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        PDG pdg = cldg.getPDG("P129#value");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdges_Q134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Q134");
        PDG pdg = cldg.getPDG("Q134#f( int )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetEdges_R134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "R134");
        PDG pdg = cldg.getPDG("R134#f( int )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetEdges_I134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "I134");
        PDG pdg = cldg.getPDG("I134#f( int )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdges_Test139_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test139");
        PDG pdg = cldg.getPDG("Test139#m( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(13, result.size());
    }
    
    @Test
    public void testGetEdges_PriceCode_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDG pdg = cldg.getPDG("PriceCode#PriceCode( int )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetEdges_PriceCode_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDG pdg = cldg.getPDG("PriceCode#getPriceCode( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetEdges_PriceCode_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDG pdg = cldg.getPDG("PriceCode#priceCode");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdges_PriceCode_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDG pdg = cldg.getPDG("PriceCode#CHILDRENS");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetEdges_PriceCode_5() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDG pdg = cldg.getPDG("PriceCode#REGULAR");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetEdges_PriceCode_6() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDG pdg = cldg.getPDG("PriceCode#NEW_RELEASE");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetEdges_Customer_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        PDG pdg = cldg.getPDG("Customer#Customer( java.lang.String )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetEdges_Customer_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        PDG pdg = cldg.getPDG("Customer#statement( Order )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(36, result.size());
    }
    
    @Test
    public void testGetEdges_Customer_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        PDG pdg = cldg.getPDG("Customer#getAmount( Order )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(22, result.size());
    }
    
    @Test
    public void testGetEdges_Customer_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        PDG pdg = cldg.getPDG("Customer#setDiscount( double )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetEdges_Customer_5() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        PDG pdg = cldg.getPDG("Customer#name");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdges_Customer_6() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        PDG pdg = cldg.getPDG("Customer#discount");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdges_CustomerTest_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "CustomerTest");
        PDG pdg = cldg.getPDG("CustomerTest#CustomerTest( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdges_CustomerTest_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "CustomerTest");
        PDG pdg = cldg.getPDG("CustomerTest#testStatement1( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(62, result.size());
    }
    
    @Test
    public void testGetEdges_CustomerTest_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "CustomerTest");
        PDG pdg = cldg.getPDG("CustomerTest#testStatement2( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(88, result.size());
    }
    
    @Test
    public void testGetEdges_Order_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Order");
        PDG pdg = cldg.getPDG("Order#Order( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdges_Order_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Order");
        PDG pdg = cldg.getPDG("Order#addRental( Rental )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(8, result.size());
    }
    
    @Test
    public void testGetEdges_Order_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Order");
        PDG pdg = cldg.getPDG("Order#getSize( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(7, result.size());
    }
    
    @Test
    public void testGetEdges_Order_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Order");
        PDG pdg = cldg.getPDG("Order#rentals");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetEdges_Rental_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Rental");
        PDG pdg = cldg.getPDG("Rental#Rental( int int )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(7, result.size());
    }
    
    @Test
    public void testGetEdges_Rental_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Rental");
        PDG pdg = cldg.getPDG("Rental#getCharge( double )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(12, result.size());
    }
    
    @Test
    public void testGetEdges_Rental_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Rental");
        PDG pdg = cldg.getPDG("Rental#price");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdges_Rental_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Rental");
        PDG pdg = cldg.getPDG("Rental#days");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetCDEdges_Test103() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.getPDG("Test103#m( )");
        List<CD> result = pdg.getCDEdges();
        
        assertEquals(39, result.size());
    }
    
    @Test
    public void testGetCDEdges_Test108() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test108");
        PDG pdg = cldg.getPDG("Test108#m( )");
        List<CD> result = pdg.getCDEdges();
        
        assertEquals(15, result.size());
    }
    
    @Test
    public void testGetCDEdges_Test110() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test110");
        PDG pdg = cldg.getPDG("Test110#m( )");
        List<CD> result = pdg.getCDEdges();
        
        assertEquals(11, result.size());
    }
    
    @Test
    public void testGetCDEdges_Test113() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test113");
        PDG pdg = cldg.getPDG("Test113#m( )");
        List<CD> result = pdg.getCDEdges();
        
        assertEquals(15, result.size());
    }
    
    @Test
    public void testGetCDEdges_Test114() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test114");
        PDG pdg = cldg.getPDG("Test114#m( )");
        List<CD> result = pdg.getCDEdges();
        
        assertEquals(15, result.size());
    }
    
    @Test
    public void testGetCDEdges_Test123() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test123");
        PDG pdg = cldg.getPDG("Test123#m( )");
        List<CD> result = pdg.getCDEdges();
        
        assertEquals(23, result.size());
    }
    
    @Test
    public void testGetDDEdges_Test103() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.getPDG("Test103#m( )");
        List<DD> result = pdg.getDDEdges();
        
        assertEquals(28, result.size());
    }
    
    @Test
    public void testGetDDEdges_Test108() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test108");
        PDG pdg = cldg.getPDG("Test108#m( )");
        List<DD> result = pdg.getDDEdges();
        
        assertEquals(8, result.size());
    }
    
    @Test
    public void testGetDDEdges_Test110() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test110");
        PDG pdg = cldg.getPDG("Test110#m( )");
        List<DD> result = pdg.getDDEdges();
        
        assertEquals(7, result.size());
    }
    
    @Test
    public void testGetDDEdges_Test113() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test113");
        PDG pdg = cldg.getPDG("Test113#m( )");
        List<DD> result = pdg.getDDEdges();
        
        assertEquals(10, result.size());
    }
    
    @Test
    public void testGetDDEdges_Test114() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test114");
        PDG pdg = cldg.getPDG("Test114#m( )");
        List<DD> result = pdg.getDDEdges();
        
        assertEquals(10, result.size());
    }
    
    @Test
    public void testGetDDEdges_Test123() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test123");
        PDG pdg = cldg.getPDG("Test123#m( )");
        List<DD> result = pdg.getDDEdges();
        
        assertEquals(10, result.size());
    }
    
    @Test
    public void testGetIncomingEdges_Test103() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.getPDG("Test103#m( )");
        List<Dependence> result = pdg.getIncomingEdges(pdg.getNode(3));
        
        assertEquals(2, result.size());
        assertEquals("0;2", TestUtil.asStr(PDGTestUtil.getIdListOfSrc(pdg, result)));
    }
    
    @Test
    public void testGetIncomingEdges_Test108_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test108");
        PDG pdg = cldg.getPDG("Test108#m( )");
        List<Dependence> result = pdg.getIncomingEdges(pdg.getNode(5));
        
        assertEquals(3, result.size());
        assertEquals("2;4", TestUtil.asStr(PDGTestUtil.getIdListOfSrc(pdg, result)));
    }
    
    @Test
    public void testGetIncomingEdges_Test108_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test108");
        PDG pdg = cldg.getPDG("Test108#m( )");
        List<Dependence> result = pdg.getIncomingEdges(pdg.getNode(6));
        
        assertEquals(5, result.size());
        assertEquals("1;3;4", TestUtil.asStr(PDGTestUtil.getIdListOfSrc(pdg, result)));
    }
    
    @Test
    public void testGetIncomingEdges_Test110() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test110");
        PDG pdg = cldg.getPDG("Test110#m( )");
        List<Dependence> result = pdg.getIncomingEdges(pdg.getNode(4));
        
        assertEquals(4, result.size());
        assertEquals("1;3;4", TestUtil.asStr(PDGTestUtil.getIdListOfSrc(pdg, result)));
    }
    
    @Test
    public void testGetIncomingEdges_Test113_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test113");
        PDG pdg = cldg.getPDG("Test113#m( )");
        List<Dependence> result = pdg.getIncomingEdges(pdg.getNode(4));
        
        assertEquals(3, result.size());
        assertEquals("2;3", TestUtil.asStr(PDGTestUtil.getIdListOfSrc(pdg, result)));
    }
    
    @Test
    public void testGetIncomingEdges_Test113_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test113");
        PDG pdg = cldg.getPDG("Test113#m( )");
        List<Dependence> result = pdg.getIncomingEdges(pdg.getNode(6));
        
        assertEquals(4, result.size());
        assertEquals("1;5;6", TestUtil.asStr(PDGTestUtil.getIdListOfSrc(pdg, result)));
    }
    
    @Test
    public void testGetIncomingEdges_Test114_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test114");
        PDG pdg = cldg.getPDG("Test114#m( )");
        List<Dependence> result = pdg.getIncomingEdges(pdg.getNode(5));
        
        assertEquals(4, result.size());
        assertEquals("1;4;5", TestUtil.asStr(PDGTestUtil.getIdListOfSrc(pdg, result)));
    }
    
    @Test
    public void testGetIncomingEdges_Test114_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test114");
        PDG pdg = cldg.getPDG("Test114#m( )");
        List<Dependence> result = pdg.getIncomingEdges(pdg.getNode(9));
        
        assertEquals(4, result.size());
        assertEquals("0;2;6", TestUtil.asStr(PDGTestUtil.getIdListOfSrc(pdg, result)));
    }
    
    @Test
    public void testGetIncomingCDEdges_Test108_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test108");
        PDG pdg = cldg.getPDG("Test108#m( )");
        List<CD> result = pdg.getIncomingCDEdges(pdg.getNode(5));
        
        assertEquals(2, result.size());
        assertEquals("2;4", TestUtil.asStr(PDGTestUtil.getIdListOfSrc(pdg, result)));
    }
    
    @Test
    public void testGetIncomingCDEdges_Test108_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test108");
        PDG pdg = cldg.getPDG("Test108#m( )");
        List<CD> result = pdg.getIncomingCDEdges(pdg.getNode(6));
        
        assertEquals(3, result.size());
        assertEquals("1;3;4", TestUtil.asStr(PDGTestUtil.getIdListOfSrc(pdg, result)));
    }
    
    @Test
    public void testGetIncomingCDEdges_Test108_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test108");
        PDG pdg = cldg.getPDG("Test108#m( )");
        List<CD> result = pdg.getIncomingCDEdges(pdg.getNode(2));
        
        assertEquals(1, result.size());
        assertEquals("0", TestUtil.asStr(PDGTestUtil.getIdListOfSrc(pdg, result)));
    }
    
    @Test
    public void testGetIncomingCDEdges_Test108_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test108");
        PDG pdg = cldg.getPDG("Test108#m( )");
        List<CD> result = pdg.getIncomingCDEdges(pdg.getNode(3));
        
        assertEquals(1, result.size());
        assertEquals("0", TestUtil.asStr(PDGTestUtil.getIdListOfSrc(pdg, result)));
    }
    
    @Test
    public void testGetIncomingCDEdges_Test110() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test110");
        PDG pdg = cldg.getPDG("Test110#m( )");
        List<CD> result = pdg.getIncomingCDEdges(pdg.getNode(4));
        
        assertEquals(2, result.size());
        assertEquals("1;3", TestUtil.asStr(PDGTestUtil.getIdListOfSrc(pdg, result)));
    }
    
    @Test
    public void testGetIncomingCDEdges_Test113_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test113");
        PDG pdg = cldg.getPDG("Test113#m( )");
        List<CD> result = pdg.getIncomingCDEdges(pdg.getNode(4));
        
        assertEquals(2, result.size());
        assertEquals("2;3", TestUtil.asStr(PDGTestUtil.getIdListOfSrc(pdg, result)));
    }
    
    @Test
    public void testGetIncomingCDEdges_Test113_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test113");
        PDG pdg = cldg.getPDG("Test113#m( )");
        List<CD> result = pdg.getIncomingCDEdges(pdg.getNode(6));
        
        assertEquals(2, result.size());
        assertEquals("1;5", TestUtil.asStr(PDGTestUtil.getIdListOfSrc(pdg, result)));
    }
    
    @Test
    public void testGetIncomingCDEdges_Test114_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test114");
        PDG pdg = cldg.getPDG("Test114#m( )");
        List<Dependence> result = pdg.getIncomingEdges(pdg.getNode(5));
        
        assertEquals(4, result.size());
        assertEquals("1;4;5", TestUtil.asStr(PDGTestUtil.getIdListOfSrc(pdg, result)));
    }
    
    @Test
    public void testGetIncomingCDEdges_Test114_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test114");
        PDG pdg = cldg.getPDG("Test114#m( )");
        List<CD> result = pdg.getIncomingCDEdges(pdg.getNode(9));
        
        assertEquals(2, result.size());
        assertEquals("0;2", TestUtil.asStr(PDGTestUtil.getIdListOfSrc(pdg, result)));
    }
    
    @Test
    public void testGetIncomingDDEdges_Test103() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.getPDG("Test103#m( )");
        List<DD> result = pdg.getIncomingDDEdges(pdg.getNode(3));
        
        assertEquals(1, result.size());
        assertEquals("2", TestUtil.asStr(PDGTestUtil.getIdListOfSrc(pdg, result)));
    }
    
    @Test
    public void testGetIncomingDDEdges_Test108_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test108");
        PDG pdg = cldg.getPDG("Test108#m( )");
        List<DD> result = pdg.getIncomingDDEdges(pdg.getNode(2));
        
        assertEquals(0, result.size());
        assertEquals("", TestUtil.asStr(PDGTestUtil.getIdListOfSrc(pdg, result)));
    }
    
    @Test
    public void testGetIncomingDDEdges_Test108_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test108");
        PDG pdg = cldg.getPDG("Test108#m( )");
        List<DD> result = pdg.getIncomingDDEdges(pdg.getNode(4));
        
        assertEquals(1, result.size());
        assertEquals("1", TestUtil.asStr(PDGTestUtil.getIdListOfSrc(pdg, result)));
    }
    
    @Test
    public void testGetIncomingDDEdges_Test108_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test108");
        PDG pdg = cldg.getPDG("Test108#m( )");
        List<DD> result = pdg.getIncomingDDEdges(pdg.getNode(5));
        
        assertEquals(1, result.size());
        assertEquals("2", TestUtil.asStr(PDGTestUtil.getIdListOfSrc(pdg, result)));
    }
    
    @Test
    public void testGetOutgoingEdges_Test103() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.getPDG("Test103#m( )");
        List<Dependence> result = pdg.getOutgoingEdges(pdg.getNode(2));
        
        assertEquals(2, result.size());
        assertEquals("3;42", TestUtil.asStr(PDGTestUtil.getIdListOfDst(pdg, result)));
    }
    
    @Test
    public void testGetOutgoingEdges_Test108_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test108");
        PDG pdg = cldg.getPDG("Test108#m( )");
        List<Dependence> result = pdg.getOutgoingEdges(pdg.getNode(2));
        
        assertEquals(4, result.size());
        assertEquals("5;8", TestUtil.asStr(PDGTestUtil.getIdListOfDst(pdg, result)));
    }
    
    @Test
    public void testGetOutgoingEdges_Test108_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test108");
        PDG pdg = cldg.getPDG("Test108#m( )");
        List<Dependence> result = pdg.getOutgoingEdges(pdg.getNode(4));
        
        assertEquals(2, result.size());
        assertEquals("5;6", TestUtil.asStr(PDGTestUtil.getIdListOfDst(pdg, result)));
    }
    
    @Test
    public void testGetOutgoingEdges_Test110() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test110");
        PDG pdg = cldg.getPDG("Test110#m( )");
        List<Dependence> result = pdg.getOutgoingEdges(pdg.getNode(3));
        
        assertEquals(1, result.size());
        assertEquals("4", TestUtil.asStr(PDGTestUtil.getIdListOfDst(pdg, result)));
    }
    
    @Test
    public void testGetOutgoingEdges_Test113_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test113");
        PDG pdg = cldg.getPDG("Test113#m( )");
        List<Dependence> result = pdg.getOutgoingEdges(pdg.getNode(3));
        
        assertEquals(2, result.size());
        assertEquals("4;5", TestUtil.asStr(PDGTestUtil.getIdListOfDst(pdg, result)));
    }
    
    @Test
    public void testGetOutgoingEdges_Test113_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test113");
        PDG pdg = cldg.getPDG("Test113#m( )");
        List<Dependence> result = pdg.getOutgoingEdges(pdg.getNode(5));
        
        assertEquals(1, result.size());
        assertEquals("6", TestUtil.asStr(PDGTestUtil.getIdListOfDst(pdg, result)));
    }
    
    @Test
    public void testGetOutgoingEdges_Test114_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test114");
        PDG pdg = cldg.getPDG("Test114#m( )");
        List<Dependence> result = pdg.getOutgoingEdges(pdg.getNode(3));
        
        assertEquals(2, result.size());
        assertEquals("4;6", TestUtil.asStr(PDGTestUtil.getIdListOfDst(pdg, result)));
    }
    
    @Test
    public void testGetOutgoingEdges_Test114_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test114");
        PDG pdg = cldg.getPDG("Test114#m( )");
        List<Dependence> result = pdg.getOutgoingEdges(pdg.getNode(4));
        
        assertEquals(1, result.size());
        assertEquals("5", TestUtil.asStr(PDGTestUtil.getIdListOfDst(pdg, result)));
    }
    
    @Test
    public void testGetOutgoingCDEdges_Test108_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test108");
        PDG pdg = cldg.getPDG("Test108#m( )");
        List<Dependence> result = pdg.getOutgoingEdges(pdg.getNode(4));
        
        assertEquals(2, result.size());
        assertEquals("5;6", TestUtil.asStr(PDGTestUtil.getIdListOfDst(pdg, result)));
    }
    
    @Test
    public void testGetOutgoingCDEdges_Test108_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test108");
        PDG pdg = cldg.getPDG("Test108#m( )");
        List<CD> result = pdg.getOutgoingCDEdges(pdg.getNode(0));
        
        assertEquals(7, result.size());
        assertEquals("1;11;2;3;4;8;9", TestUtil.asStr(PDGTestUtil.getIdListOfDst(pdg, result)));
    }
    
    @Test
    public void testGetOutgoingCDEdges_Test110() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test110");
        PDG pdg = cldg.getPDG("Test110#m( )");
        List<CD> result = pdg.getOutgoingCDEdges(pdg.getNode(3));
        
        assertEquals(1, result.size());
        assertEquals("4", TestUtil.asStr(PDGTestUtil.getIdListOfDst(pdg, result)));
    }
    
    @Test
    public void testGetOutgoingCDEdges_Test113_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test113");
        PDG pdg = cldg.getPDG("Test113#m( )");
        List<CD> result = pdg.getOutgoingCDEdges(pdg.getNode(3));
        
        assertEquals(2, result.size());
        assertEquals("4;5", TestUtil.asStr(PDGTestUtil.getIdListOfDst(pdg, result)));
    }
    
    @Test
    public void testGetOutgoingCDEdges_Test113_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test113");
        PDG pdg = cldg.getPDG("Test113#m( )");
        List<CD> result = pdg.getOutgoingCDEdges(pdg.getNode(5));
        
        assertEquals(1, result.size());
        assertEquals("6", TestUtil.asStr(PDGTestUtil.getIdListOfDst(pdg, result)));
    }
    
    @Test
    public void testGetOutgoingCDEdges_Test114_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test114");
        PDG pdg = cldg.getPDG("Test114#m( )");
        List<CD> result = pdg.getOutgoingCDEdges(pdg.getNode(3));
        
        assertEquals(2, result.size());
        assertEquals("4;6", TestUtil.asStr(PDGTestUtil.getIdListOfDst(pdg, result)));
    }
    
    @Test
    public void testGetOutgoingCDEdges_Test114_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test114");
        PDG pdg = cldg.getPDG("Test114#m( )");
        List<CD> result = pdg.getOutgoingCDEdges(pdg.getNode(4));
        
        assertEquals(1, result.size());
        assertEquals("5", TestUtil.asStr(PDGTestUtil.getIdListOfDst(pdg, result)));
    }
    
    @Test
    public void testGetOutgoingDDEdges_Test103() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.getPDG("Test103#m( )");
        List<DD> result = pdg.getOutgoingDDEdges(pdg.getNode(2));
        
        assertEquals(2, result.size());
        assertEquals("3;42", TestUtil.asStr(PDGTestUtil.getIdListOfDst(pdg, result)));
    }
    
    @Test
    public void testGetOutgoingDDEdges_Test108_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test108");
        PDG pdg = cldg.getPDG("Test108#m( )");
        List<DD> result = pdg.getOutgoingDDEdges(pdg.getNode(1));
        
        assertEquals(2, result.size());
        assertEquals("4;6", TestUtil.asStr(PDGTestUtil.getIdListOfDst(pdg, result)));
    }
    
    @Test
    public void testGetOutgoingDDEdges_Test108_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test108");
        PDG pdg = cldg.getPDG("Test108#m( )");
        List<DD> result = pdg.getOutgoingDDEdges(pdg.getNode(2));
        
        assertEquals(2, result.size());
        assertEquals("5;8", TestUtil.asStr(PDGTestUtil.getIdListOfDst(pdg, result)));
    }
    
    @Test
    public void testGetOutgoingDDEdges_Test108_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test108");
        PDG pdg = cldg.getPDG("Test108#m( )");
        List<DD> result = pdg.getOutgoingDDEdges(pdg.getNode(3));
        
        assertEquals(2, result.size());
        assertEquals("6;9", TestUtil.asStr(PDGTestUtil.getIdListOfDst(pdg, result)));
    }
    
    @Test
    public void testGetOutgoingDDEdges_Test108_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test108");
        PDG pdg = cldg.getPDG("Test108#m( )");
        List<DD> result = pdg.getOutgoingDDEdges(pdg.getNode(9));
        
        assertEquals(0, result.size());
        assertEquals("", TestUtil.asStr(PDGTestUtil.getIdListOfDst(pdg, result)));
    }
    
    @Test
    public void testIsDominated_Test108_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        boolean result = pdg.isDominated(pdg.getNode(0));
        
        assertFalse(result);
    }
    
    @Test
    public void testIsDominated_Test108_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        boolean result = pdg.isDominated(pdg.getNode(2));
        
        assertTrue(result);
    }
    
    @Test
    public void testIsDominated_Test108_3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        boolean result = pdg.isDominated(pdg.getNode(4));
        
        assertTrue(result);
    }
    
    @Test
    public void testIsDominated_Test108_4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        boolean result = pdg.isDominated(pdg.getNode(5));
        
        assertTrue(result);
    }
    
    @Test
    public void testIsDominated_Test108_5() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        boolean result = pdg.isDominated(pdg.getNode(6));
        
        assertTrue(result);
    }
    
    @Test
    public void testIsTrueDominated_Test108_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        boolean result = pdg.isTrueDominated(pdg.getNode(0));
        
        assertFalse(result);
    }
    
    @Test
    public void testIsTrueDominated_Test108_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        boolean result = pdg.isTrueDominated(pdg.getNode(2));
        
        assertTrue(result);
    }
    
    @Test
    public void testIsTrueDominated_Test108_3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        boolean result = pdg.isTrueDominated(pdg.getNode(4));
        
        assertTrue(result);
    }
    
    @Test
    public void testIsTrueDominated_Test108_4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        boolean result = pdg.isTrueDominated(pdg.getNode(5));
        
        assertTrue(result);
    }
    
    @Test
    public void testIsTrueDominated_Test108_5() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        boolean result = pdg.isTrueDominated(pdg.getNode(6));
        
        assertFalse(result);
    }
    
    @Test
    public void testIsFalseDominated_Test108_1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        boolean result = pdg.isTrueDominated(pdg.getNode(0));
        
        assertFalse(result);
    }
    
    @Test
    public void testIsFalseDominated_Test108_2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        boolean result = pdg.isTrueDominated(pdg.getNode(2));
        
        assertTrue(result);
    }
    
    @Test
    public void testIsFalseDominated_Test108_3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        boolean result = pdg.isTrueDominated(pdg.getNode(4));
        
        assertTrue(result);
    }
    
    @Test
    public void testIsFalseDominated_Test108_4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        boolean result = pdg.isTrueDominated(pdg.getNode(5));
        
        assertTrue(result);
    }
    
    @Test
    public void testIsFalseDominated_Test108_5() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        boolean result = pdg.isTrueDominated(pdg.getNode(6));
        
        assertFalse(result);
    }
}
