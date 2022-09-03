/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.PDGTestUtil;
import java.util.List;
import java.util.Set;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

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
    public void testGetNodes103_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.findPDG("Test103#m( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(40, result.size());
    }
    
    @Test
    public void testGetNodes103_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.findPDG("Test103#setA( int )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetNodes103_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.findPDG("Test103#getA( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodes103_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.findPDG("Test103#incA( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodes103_5() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.findPDG("Test103#Test103( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodes103_6() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.findPDG("Test103#a");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodesTest119_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test119");
        PDG pdg = cldg.findPDG("Test119#m( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(32, result.size());
    }
    
    @Test
    public void testGetNodesTest119_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test119");
        PDG pdg = cldg.findPDG("Test119#getP( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodesTest119_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test119");
        PDG pdg = cldg.findPDG("Test119#Test119( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodesTest119_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test119");
        PDG pdg = cldg.findPDG("Test119#p");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodesA119_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "A119");
        PDG pdg = cldg.findPDG("A119#x");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodesA119_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "A119");
        PDG pdg = cldg.findPDG("A119#y");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodesTest129_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test129");
        PDG pdg = cldg.findPDG("Test129#m( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(71, result.size());
    }
    
    @Test
    public void testGetNodesTest129_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test129");
        PDG pdg = cldg.findPDG("Test129#s1");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetNodesTest129_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test129");
        PDG pdg = cldg.findPDG("Test129#s2");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetNodesP129_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        PDG pdg = cldg.findPDG("P129#get1( java.lang.String )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(7, result.size());
    }
    
    @Test
    public void testGetNodesP129_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        PDG pdg = cldg.findPDG("P129#set1( java.lang.String java.lang.String )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(8, result.size());
    }
    
    @Test
    public void testGetNodesP129_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        PDG pdg = cldg.findPDG("P129#map");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetNodesP129_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        PDG pdg = cldg.findPDG("P129#key");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodesP129_5() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        PDG pdg = cldg.findPDG("P129#value");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodesQ134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Q134");
        PDG pdg = cldg.findPDG("Q134#f( int )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetNodesR134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "R134");
        PDG pdg = cldg.findPDG("R134#f( int )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetNodesI134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "I134");
        PDG pdg = cldg.findPDG("I134#f( int )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodesTest139_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test139");
        PDG pdg = cldg.findPDG("Test139#m( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(12, result.size());
    }
    
    @Test
    public void testGetNodesPriceCode_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDG pdg = cldg.findPDG("PriceCode#PriceCode( int )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetNodesPriceCode_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDG pdg = cldg.findPDG("PriceCode#getPriceCode( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodesPriceCode_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDG pdg = cldg.findPDG("PriceCode#priceCode");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodesPriceCode_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDG pdg = cldg.findPDG("PriceCode#CHILDRENS");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetNodesPriceCode_5() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDG pdg = cldg.findPDG("PriceCode#REGULAR");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetNodesPriceCode_6() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDG pdg = cldg.findPDG("PriceCode#NEW_RELEASE");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetNodesCustomer_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        PDG pdg = cldg.findPDG("Customer#Customer( java.lang.String )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetNodesCustomer_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        PDG pdg = cldg.findPDG("Customer#statement( Order )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(16, result.size());
    }
    
    @Test
    public void testGetNodesCustomer_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        PDG pdg = cldg.findPDG("Customer#getAmount( Order )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(11, result.size());
    }
    
    @Test
    public void testGetNodesCustomer_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        PDG pdg = cldg.findPDG("Customer#setDiscount( double )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetNodesCustomer_5() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        PDG pdg = cldg.findPDG("Customer#name");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodesCustomer_6() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        PDG pdg = cldg.findPDG("Customer#discount");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodesCustomerTest_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "CustomerTest");
        PDG pdg = cldg.findPDG("CustomerTest#CustomerTest( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodesCustomerTest_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "CustomerTest");
        PDG pdg = cldg.findPDG("CustomerTest#testStatement1( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(38, result.size());
    }
    
    @Test
    public void testGetNodesCustomerTest_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "CustomerTest");
        PDG pdg = cldg.findPDG("CustomerTest#testStatement2( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(50, result.size());
    }
    
    @Test
    public void testGetNodesOrder_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Order");
        PDG pdg = cldg.findPDG("Order#Order( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodesOrder_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Order");
        PDG pdg = cldg.findPDG("Order#addRental( Rental )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(7, result.size());
    }
    
    @Test
    public void testGetNodesOrder_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Order");
        PDG pdg = cldg.findPDG("Order#getSize( )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetNodesOrder_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Order");
        PDG pdg = cldg.findPDG("Order#rentals");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetNodesRental_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Rental");
        PDG pdg = cldg.findPDG("Rental#Rental( int int )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetNodesRental_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Rental");
        PDG pdg = cldg.findPDG("Rental#getCharge( double )");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(8, result.size());
    }
    
    @Test
    public void testGetNodesRental_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Rental");
        PDG pdg = cldg.findPDG("Rental#price");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetNodesRental_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Rental");
        PDG pdg = cldg.findPDG("Rental#days");
        Set<PDGNode> result = pdg.getNodes();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdges103_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.findPDG("Test103#m( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(67, result.size());
    }
    
    @Test
    public void testGetEdges103_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.findPDG("Test103#setA( int )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetEdges103_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.findPDG("Test103#getA( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetEdges103_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.findPDG("Test103#incA( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdges103_5() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.findPDG("Test103#Test103( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdges103_6() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.findPDG("Test103#a");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdgesTest119_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test119");
        PDG pdg = cldg.findPDG("Test119#m( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(58, result.size());
    }
    
    @Test
    public void testGetEdgesTest119_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test119");
        PDG pdg = cldg.findPDG("Test119#getP( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetEdgesTest119_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test119");
        PDG pdg = cldg.findPDG("Test119#Test119( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdgesTest119_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test119");
        PDG pdg = cldg.findPDG("Test119#p");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdgesA119_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "A119");
        PDG pdg = cldg.findPDG("A119#x");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdgesA119_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "A119");
        PDG pdg = cldg.findPDG("A119#y");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdgesTest129_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test129");
        PDG pdg = cldg.findPDG("Test129#m( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(113, result.size());
    }
    
    @Test
    public void testGetEdgesTest129_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test129");
        PDG pdg = cldg.findPDG("Test129#s1");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetEdgesTest129_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test129");
        PDG pdg = cldg.findPDG("Test129#s2");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetEdgesP129_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        PDG pdg = cldg.findPDG("P129#get1( java.lang.String )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(9, result.size());
    }
    
    @Test
    public void testGetEdgesP129_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        PDG pdg = cldg.findPDG("P129#set1( java.lang.String java.lang.String )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(10, result.size());
    }
    
    @Test
    public void testGetEdgesP129_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        PDG pdg = cldg.findPDG("P129#map");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetEdgesP129_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        PDG pdg = cldg.findPDG("P129#key");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdgesP129_5() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        PDG pdg = cldg.findPDG("P129#value");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdgesQ134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Q134");
        PDG pdg = cldg.findPDG("Q134#f( int )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetEdgesR134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "R134");
        PDG pdg = cldg.findPDG("R134#f( int )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetEdgesI134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "I134");
        PDG pdg = cldg.findPDG("I134#f( int )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdgesTest139_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test139");
        PDG pdg = cldg.findPDG("Test139#m( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(13, result.size());
    }
    
    @Test
    public void testGetEdgesPriceCode_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDG pdg = cldg.findPDG("PriceCode#PriceCode( int )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetEdgesPriceCode_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDG pdg = cldg.findPDG("PriceCode#getPriceCode( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetEdgesPriceCode_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDG pdg = cldg.findPDG("PriceCode#priceCode");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdgesPriceCode_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDG pdg = cldg.findPDG("PriceCode#CHILDRENS");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetEdgesPriceCode_5() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDG pdg = cldg.findPDG("PriceCode#REGULAR");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetEdgesPriceCode_6() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDG pdg = cldg.findPDG("PriceCode#NEW_RELEASE");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetEdgesCustomer_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        PDG pdg = cldg.findPDG("Customer#Customer( java.lang.String )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetEdgesCustomer_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        PDG pdg = cldg.findPDG("Customer#statement( Order )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(36, result.size());
    }
    
    @Test
    public void testGetEdgesCustomer_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        PDG pdg = cldg.findPDG("Customer#getAmount( Order )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(22, result.size());
    }
    
    @Test
    public void testGetEdgesCustomer_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        PDG pdg = cldg.findPDG("Customer#setDiscount( double )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetEdgesCustomer_5() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        PDG pdg = cldg.findPDG("Customer#name");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdgesCustomer_6() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        PDG pdg = cldg.findPDG("Customer#discount");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdgesCustomerTest_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "CustomerTest");
        PDG pdg = cldg.findPDG("CustomerTest#CustomerTest( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdgesCustomerTest_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "CustomerTest");
        PDG pdg = cldg.findPDG("CustomerTest#testStatement1( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(62, result.size());
    }
    
    @Test
    public void testGetEdgesCustomerTest_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "CustomerTest");
        PDG pdg = cldg.findPDG("CustomerTest#testStatement2( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(88, result.size());
    }
    
    @Test
    public void testGetEdgesOrder_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Order");
        PDG pdg = cldg.findPDG("Order#Order( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdgesOrder_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Order");
        PDG pdg = cldg.findPDG("Order#addRental( Rental )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(8, result.size());
    }
    
    @Test
    public void testGetEdgesOrder_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Order");
        PDG pdg = cldg.findPDG("Order#getSize( )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(7, result.size());
    }
    
    @Test
    public void testGetEdgesOrder_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Order");
        PDG pdg = cldg.findPDG("Order#rentals");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetEdgesRental_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Rental");
        PDG pdg = cldg.findPDG("Rental#Rental( int int )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(7, result.size());
    }
    
    @Test
    public void testGetEdgesRental_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Rental");
        PDG pdg = cldg.findPDG("Rental#getCharge( double )");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(12, result.size());
    }
    
    @Test
    public void testGetEdgesRental_3() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Rental");
        PDG pdg = cldg.findPDG("Rental#price");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetEdgesRental_4() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Rental");
        PDG pdg = cldg.findPDG("Rental#days");
        List<Dependence> result = pdg.getEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetPDGsTest103_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.findPDG("Test103#m( )");
        
        assertEquals(1, pdg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsTest103_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.findPDG("Test103#Test103( )");
        
        assertEquals(1, pdg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsTest119_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test119");
        PDG pdg = cldg.findPDG("Test119#m( )");
        
        assertEquals(1, pdg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsA119_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "A119");
        PDG pdg = cldg.findPDG("A119#x");
        
        assertEquals(1, pdg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsA129_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test129");
        PDG pdg = cldg.findPDG("Test129#m( )");
        
        assertEquals(1, pdg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsP129_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        PDG pdg = cldg.findPDG("P129#get1( java.lang.String )");
        
        assertEquals(1, pdg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsQ134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Q134");
        PDG pdg = cldg.findPDG("Q134#f( int )");
        
        assertEquals(1, pdg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsR134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "R134");
        PDG pdg = cldg.findPDG("R134#f( int )");
        
        assertEquals(1, pdg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsI134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "I134");
        PDG pdg = cldg.findPDG("I134#f( int )");
        
        assertEquals(1, pdg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsTest139_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test139");
        PDG pdg = cldg.findPDG("Test139#m( )");
        
        assertEquals(1, pdg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsPriceCode_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        PDG pdg = cldg.findPDG("PriceCode#PriceCode( int )");
        
        assertEquals(1, pdg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsCustomer_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        PDG pdg = cldg.findPDG("Customer#Customer( java.lang.String )");
        
        assertEquals(1, pdg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsCustomerTest_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "CustomerTest");
        PDG pdg = cldg.findPDG("CustomerTest#CustomerTest( )");
        
        assertEquals(1, pdg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsOrder_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Order");
        PDG pdg = cldg.findPDG("Order#Order( )");
        
        assertEquals(1, pdg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsRental_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Rental");
        PDG pdg = cldg.findPDG("Rental#Rental( int int )");
        
        assertEquals(1, pdg.getPDGs().size());
    }
    
    @Test
    public void testIsPDG1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.findPDG("Test103#a");
        
        assertTrue(pdg.isPDG());
    }
    
    @Test
    public void testIsPDG2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        
        assertFalse(cldg.isPDG());
    }
}
