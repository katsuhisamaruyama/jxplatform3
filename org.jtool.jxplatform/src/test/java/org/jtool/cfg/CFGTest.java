/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.CFGTestUtil;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CFGTest {
    
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
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test103");
        CFG cfg = ccfg.getCFG("Test103#m( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(41, result.size());
    }
    
    @Test
    public void testGetNodes103_2() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test103");
        CFG cfg = ccfg.getCFG("Test103#setA( int )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetNodes103_3() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test103");
        CFG cfg = ccfg.getCFG("Test103#getA( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetNodes103_4() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test103");
        CFG cfg = ccfg.getCFG("Test103#incA( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetNodes103_5() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test103");
        CFG cfg = ccfg.getCFG("Test103#Test103( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodes103_6() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test103");
        CFG cfg = ccfg.getCFG("Test103#a");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodesTest119_1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test119");
        CFG cfg = ccfg.getCFG("Test119#m( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(33, result.size());
    }
    
    @Test
    public void testGetNodesTest119_2() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test119");
        CFG cfg = ccfg.getCFG("Test119#getP( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetNodesTest119_3() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test119");
        CFG cfg = ccfg.getCFG("Test119#Test119( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodesTest119_4() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test119");
        CFG cfg = ccfg.getCFG("Test119#p");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodesA119_1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "A119");
        CFG cfg = ccfg.getCFG("A119#x");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodesA119_2() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "A119");
        CFG cfg = ccfg.getCFG("A119#y");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodesTest129_1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test129");
        CFG cfg = ccfg.getCFG("Test129#m( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(72, result.size());
    }
    
    @Test
    public void testGetNodesTest129_2() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test129");
        CFG cfg = ccfg.getCFG("Test129#s1");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetNodesTest129_3() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test129");
        CFG cfg = ccfg.getCFG("Test129#s2");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetNodesP129_1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "P129");
        CFG cfg = ccfg.getCFG("P129#get1( java.lang.String )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(8, result.size());
    }
    
    @Test
    public void testGetNodesP129_2() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "P129");
        CFG cfg = ccfg.getCFG("P129#set1( java.lang.String java.lang.String )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(9, result.size());
    }
    
    @Test
    public void testGetNodesP129_3() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "P129");
        CFG cfg = ccfg.getCFG("P129#map");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetNodesP129_4() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "P129");
        CFG cfg = ccfg.getCFG("P129#key");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodesP129_5() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "P129");
        CFG cfg = ccfg.getCFG("P129#value");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodesQ134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Q134");
        CFG cfg = ccfg.getCFG("Q134#f( int )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetNodesR134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "R134");
        CFG cfg = ccfg.getCFG("R134#f( int )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetNodesI134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "I134");
        CFG cfg = ccfg.getCFG("I134#f( int )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetNodesTest139() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test139");
        CFG cfg = ccfg.getCFG("Test139#m( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(13, result.size());
    }
    
    @Test
    public void testGetNodesPriceCode_1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "PriceCode");
        CFG cfg = ccfg.getCFG("PriceCode#PriceCode( int )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetNodesPriceCode_2() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "PriceCode");
        CFG cfg = ccfg.getCFG("PriceCode#getPriceCode( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetNodesPriceCode_3() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "PriceCode");
        CFG cfg = ccfg.getCFG("PriceCode#priceCode");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodesPriceCode_4() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "PriceCode");
        CFG cfg = ccfg.getCFG("PriceCode#CHILDRENS");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(7, result.size());
    }
    
    @Test
    public void testGetNodesPriceCode_5() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "PriceCode");
        CFG cfg = ccfg.getCFG("PriceCode#REGULAR");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(7, result.size());
    }
    
    @Test
    public void testGetNodesPriceCode_6() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "PriceCode");
        CFG cfg = ccfg.getCFG("PriceCode#NEW_RELEASE");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(7, result.size());
    }
    
    @Test
    public void testGetNodesCustomer_1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Customer");
        CFG cfg = ccfg.getCFG("Customer#Customer( java.lang.String )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetNodesCustomer_2() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Customer");
        CFG cfg = ccfg.getCFG("Customer#statement( Order )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(19, result.size());
    }
    
    @Test
    public void testGetNodesCustomer_3() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Customer");
        CFG cfg = ccfg.getCFG("Customer#getAmount( Order )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(12, result.size());
    }
    
    @Test
    public void testGetNodesCustomer_4() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Customer");
        CFG cfg = ccfg.getCFG("Customer#setDiscount( double )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetNodesCustomer_5() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Customer");
        CFG cfg = ccfg.getCFG("Customer#name");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodesCustomer_6() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Customer");
        CFG cfg = ccfg.getCFG("Customer#discount");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodesCustomerTest_1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "CustomerTest");
        CFG cfg = ccfg.getCFG("CustomerTest#CustomerTest( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodesCustomerTest_2() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "CustomerTest");
        CFG cfg = ccfg.getCFG("CustomerTest#testStatement1( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(39, result.size());
    }
    
    @Test
    public void testGetNodesCustomerTest_3() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "CustomerTest");
        CFG cfg = ccfg.getCFG("CustomerTest#testStatement2( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(51, result.size());
    }
    
    @Test
    public void testGetNodesOrder_1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Order");
        CFG cfg = ccfg.getCFG("Order#Order( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodesOrder_2() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Order");
        CFG cfg = ccfg.getCFG("Order#addRental( Rental )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(8, result.size());
    }
    
    @Test
    public void testGetNodesOrder_3() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Order");
        CFG cfg = ccfg.getCFG("Order#getSize( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(7, result.size());
    }
    
    @Test
    public void testGetNodesOrder_4() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Order");
        CFG cfg = ccfg.getCFG("Order#rentals");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetNodesRental_1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Rental");
        CFG cfg = ccfg.getCFG("Rental#Rental( int int )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(7, result.size());
    }
    
    @Test
    public void testGetNodesRental_2() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Rental");
        CFG cfg = ccfg.getCFG("Rental#getCharge( double )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(9, result.size());
    }
    
    @Test
    public void testGetNodesRental_3() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Rental");
        CFG cfg = ccfg.getCFG("Rental#price");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodesRental_4() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Rental");
        CFG cfg = ccfg.getCFG("Rental#days");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetEdges103_1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test103");
        CFG cfg = ccfg.getCFG("Test103#m( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(40, result.size());
    }
    
    @Test
    public void testGetEdges103_2() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test103");
        CFG cfg = ccfg.getCFG("Test103#setA( int )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetEdges103_3() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test103");
        CFG cfg = ccfg.getCFG("Test103#getA( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetEdges103_4() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test103");
        CFG cfg = ccfg.getCFG("Test103#incA( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetEdges103_5() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test103");
        CFG cfg = ccfg.getCFG("Test103#Test103( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdges103_6() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test103");
        CFG cfg = ccfg.getCFG("Test103#a");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdgesTest119_1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test119");
        CFG cfg = ccfg.getCFG("Test119#m( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(32, result.size());
    }
    
    @Test
    public void testGetEdgesTest119_2() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test119");
        CFG cfg = ccfg.getCFG("Test119#getP( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetEdgesTest119_3() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test119");
        CFG cfg = ccfg.getCFG("Test119#Test119( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdgesTest119_4() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test119");
        CFG cfg = ccfg.getCFG("Test119#p");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdgesA119_1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "A119");
        CFG cfg = ccfg.getCFG("A119#x");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdgesA119_2() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "A119");
        CFG cfg = ccfg.getCFG("A119#y");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdgesTest129_1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test129");
        CFG cfg = ccfg.getCFG("Test129#m( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(71, result.size());
    }
    
    @Test
    public void testGetEdgesTest129_2() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test129");
        CFG cfg = ccfg.getCFG("Test129#s1");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetEdgesTest129_3() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test129");
        CFG cfg = ccfg.getCFG("Test129#s2");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetEdgesP129_1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "P129");
        CFG cfg = ccfg.getCFG("P129#get1( java.lang.String )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(8, result.size());
    }
    
    @Test
    public void testGetEdgesP129_2() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "P129");
        CFG cfg = ccfg.getCFG("P129#set1( java.lang.String java.lang.String )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(8, result.size());
    }
    
    @Test
    public void testGetEdgesP129_3() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "P129");
        CFG cfg = ccfg.getCFG("P129#map");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetEdgesP129_4() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "P129");
        CFG cfg = ccfg.getCFG("P129#key");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdgesP129_5() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "P129");
        CFG cfg = ccfg.getCFG("P129#value");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdgesQ134_1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Q134");
        CFG cfg = ccfg.getCFG("Q134#f( int )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetEdgesR134_1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "R134");
        CFG cfg = ccfg.getCFG("R134#f( int )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetEdgesI134_1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "I134");
        CFG cfg = ccfg.getCFG("I134#f( int )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetEdgesTest139_1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test139");
        CFG cfg = ccfg.getCFG("Test139#m( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(12, result.size());
    }
    
    @Test
    public void testGetEdgesPriceCode_1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "PriceCode");
        CFG cfg = ccfg.getCFG("PriceCode#PriceCode( int )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetEdgesPriceCode_2() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "PriceCode");
        CFG cfg = ccfg.getCFG("PriceCode#getPriceCode( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetEdgesPriceCode_3() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "PriceCode");
        CFG cfg = ccfg.getCFG("PriceCode#priceCode");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdgesPriceCode_4() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "PriceCode");
        CFG cfg = ccfg.getCFG("PriceCode#CHILDRENS");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetEdgesPriceCode_5() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "PriceCode");
        CFG cfg = ccfg.getCFG("PriceCode#REGULAR");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetEdgesPriceCode_6() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "PriceCode");
        CFG cfg = ccfg.getCFG("PriceCode#NEW_RELEASE");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetEdgesCustomer_1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Customer");
        CFG cfg = ccfg.getCFG("Customer#Customer( java.lang.String )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetEdgesCustomer_2() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Customer");
        CFG cfg = ccfg.getCFG("Customer#statement( Order )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(22, result.size());
    }
    
    @Test
    public void testGetEdgesCustomer_3() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Customer");
        CFG cfg = ccfg.getCFG("Customer#getAmount( Order )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(13, result.size());
    }
    
    @Test
    public void testGetEdgesCustomer_4() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Customer");
        CFG cfg = ccfg.getCFG("Customer#setDiscount( double )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetEdgesCustomer_5() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Customer");
        CFG cfg = ccfg.getCFG("Customer#name");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdgesCustomer_6() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Customer");
        CFG cfg = ccfg.getCFG("Customer#discount");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdgesCustomerTest_1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "CustomerTest");
        CFG cfg = ccfg.getCFG("CustomerTest#CustomerTest( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdgesCustomerTest_2() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "CustomerTest");
        CFG cfg = ccfg.getCFG("CustomerTest#testStatement1( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(38, result.size());
    }
    
    @Test
    public void testGetEdgesCustomerTest_3() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "CustomerTest");
        CFG cfg = ccfg.getCFG("CustomerTest#testStatement2( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(50, result.size());
    }
    
    @Test
    public void testGetEdgesOrder_1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Order");
        CFG cfg = ccfg.getCFG("Order#Order( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdgesOrder_2() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Order");
        CFG cfg = ccfg.getCFG("Order#addRental( Rental )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(7, result.size());
    }
    
    @Test
    public void testGetEdgesOrder_3() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Order");
        CFG cfg = ccfg.getCFG("Order#getSize( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(7, result.size());
    }
    
    @Test
    public void testGetEdgesOrder_4() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Order");
        CFG cfg = ccfg.getCFG("Order#rentals");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetEdgesRental_1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Rental");
        CFG cfg = ccfg.getCFG("Rental#Rental( int int )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetEdgesRental_2() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Rental");
        CFG cfg = ccfg.getCFG("Rental#getCharge( double )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(9, result.size());
    }
    
    @Test
    public void testGetEdgesRental_3() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Rental");
        CFG cfg = ccfg.getCFG("Rental#price");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdgesRental_4() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Rental");
        CFG cfg = ccfg.getCFG("Rental#days");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testIsMethod1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test103");
        CFG cfg = ccfg.getCFG("Test103#m( )");
        
        assertTrue(cfg.isMethod());
    }
    @Test
    public void testIsMethod2() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test103");
        CFG cfg = ccfg.getCFG("Test103#Test103( )");
        
        assertTrue(cfg.isMethod());
    }
    
    @Test
    public void testIsMethod3() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "I134");
        CFG cfg = ccfg.getCFG("I134#f( int )");
        
        assertTrue(cfg.isMethod());
    }
    
    @Test
    public void testIsMethod4() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "PriceCode");
        CFG cfg = ccfg.getCFG("PriceCode#PriceCode( int )");
        
        assertTrue(cfg.isMethod());
    }
    
    @Test
    public void testIsMethod5() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "PriceCode");
        CFG cfg = ccfg.getCFG("PriceCode#getPriceCode( )");
        
        assertTrue(cfg.isMethod());
    }
    
    @Test
    public void testIsMethod6() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Customer");
        CFG cfg = ccfg.getCFG("Customer#Customer( java.lang.String )");
        
        assertTrue(cfg.isMethod());
    }
    
    @Test
    public void testIsField1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test103");
        CFG cfg = ccfg.getCFG("Test103#a");
        
        assertTrue(cfg.isField());
    }
    
    @Test
    public void testIsField2() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "PriceCode");
        CFG cfg = ccfg.getCFG("PriceCode#CHILDRENS");
        
        assertTrue(cfg.isField());
    }
}
