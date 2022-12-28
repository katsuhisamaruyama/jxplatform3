/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import static org.jtool.cfg.AllCFGTests.SliceProject;
import org.jtool.cfg.internal.CFGTestUtil;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CFGTest {
    
    @BeforeClass
    public static void setUp() {
        SliceProject = BuilderTestUtil.getProject("Slice");
    }
    
    @Test
    public void testGetNodes_Test103_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(41, result.size());
    }
    
    @Test
    public void testGetNodes_Test103_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetNodes_Test103_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetNodes_Test103_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "incA( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetNodes_Test103_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "Test103( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodes_Test103_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "a");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodes_Test119_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(33, result.size());
    }
    
    @Test
    public void testGetNodes_Test119_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "getP( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetNodes_Test119_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "Test119( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodes_Test119_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "p");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodes_A119_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "A119", "x");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodes_A119_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "A119", "y");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodes_Test129_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test129", "m( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(72, result.size());
    }
    
    @Test
    public void testGetNodes_Test129_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test129", "s1");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetNodes_Test129_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test129", "s2");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetNodes_P129_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "P129", "get1( java.lang.String )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(8, result.size());
    }
    
    @Test
    public void testGetNodes_P129_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "P129", "set1( java.lang.String java.lang.String )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(9, result.size());
    }
    
    @Test
    public void testGetNodes_P129_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "P129", "map");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetNodes_P129_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "P129", "key");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodesP129_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "P129", "value");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodes_Q134() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Q134", "f( int )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetNodes_R134() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "R134", "f( int )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetNodes_I134() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "I134", "f( int )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetNodes_Test139() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(13, result.size());
    }
    
    @Test
    public void testGetNodes_PriceCode_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "PriceCode( int )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetNodes_PriceCode_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "getPriceCode( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetNodes_PriceCode_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "priceCode");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodes_PriceCode_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "CHILDRENS");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(7, result.size());
    }
    
    @Test
    public void testGetNodes_PriceCode_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "REGULAR");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(7, result.size());
    }
    
    @Test
    public void testGetNodes_PriceCode_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "NEW_RELEASE");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(7, result.size());
    }
    
    @Test
    public void testGetNodes_Customer_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Customer", "Customer( java.lang.String )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetNodes_Customer_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Customer", "statement( Order )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(19, result.size());
    }
    
    @Test
    public void testGetNodes_Customer_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Customer", "getAmount( Order )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(12, result.size());
    }
    
    @Test
    public void testGetNodes_Customer_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Customer", "setDiscount( double )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetNodes_Customer_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Customer", "name");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodes_Customer_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Customer", "discount");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodes_CustomerTest_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "CustomerTest", "CustomerTest( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodes_CustomerTest_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "CustomerTest", "testStatement1( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(39, result.size());
    }
    
    @Test
    public void testGetNodes_CustomerTest_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "CustomerTest", "testStatement2( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(51, result.size());
    }
    
    @Test
    public void testGetNodes_Order_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Order", "Order( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodes_Order_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Order", "addRental( Rental )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(8, result.size());
    }
    
    @Test
    public void testGetNodes_Order_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Order", "getSize( )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(7, result.size());
    }
    
    @Test
    public void testGetNodes_Order_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Order", "rentals");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetNodes_Rental_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Rental", "Rental( int int )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(7, result.size());
    }
    
    @Test
    public void testGetNodes_Rental_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Rental", "getCharge( double )");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(9, result.size());
    }
    
    @Test
    public void testGetNodes_Rental_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Rental", "price");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetNodes_Rental_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Rental", "days");
        Set<CFGNode> result = cfg.getNodes();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetEdges__Test103_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(40, result.size());
    }
    
    @Test
    public void testGetEdges__Test103_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "setA( int )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetEdges__Test103_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetEdges__Test103_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "incA( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetEdges__Test103_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "Test103( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdges__Test103_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "a");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdges_Test119_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(32, result.size());
    }
    
    @Test
    public void testGetEdges_Test119_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "getP( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetEdges_Test119_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "Test119( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdges_Test119_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "p");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdges_A119_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "A119", "x");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdges_A119_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "A119", "y");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdges_Test129_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test129", "m( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(71, result.size());
    }
    
    @Test
    public void testGetEdges_Test129_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test129", "s1");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetEdges_Test129_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test129", "s2");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetEdges_P129_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "P129", "get1( java.lang.String )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(8, result.size());
    }
    
    @Test
    public void testGetEdges_P129_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "P129", "set1( java.lang.String java.lang.String )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(8, result.size());
    }
    
    @Test
    public void testGetEdges_P129_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "P129", "map");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetEdges_P129_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "P129", "key");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdges_P129_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "P129", "value");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdges_Q134_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Q134", "f( int )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetEdges_R134_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "R134", "f( int )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetEdges_I134_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "I134", "f( int )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetEdges_Test139_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test139", "m( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(12, result.size());
    }
    
    @Test
    public void testGetEdges_PriceCode_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "PriceCode( int )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetEdges_PriceCode_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "getPriceCode( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetEdges_PriceCode_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "priceCode");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdges_PriceCode_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "CHILDRENS");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetEdges_PriceCode_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "REGULAR");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetEdges_PriceCode_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "NEW_RELEASE");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetEdges_Customer_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Customer", "Customer( java.lang.String )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetEdges_Customer_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Customer", "statement( Order )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(22, result.size());
    }
    
    @Test
    public void testGetEdges_Customer_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Customer", "getAmount( Order )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(13, result.size());
    }
    
    @Test
    public void testGetEdges_Customer_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Customer", "setDiscount( double )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetEdges_Customer_5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Customer", "name");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdges_Customer_6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Customer", "discount");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdges_CustomerTest_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "CustomerTest", "CustomerTest( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdges_CustomerTest_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "CustomerTest", "testStatement1( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(38, result.size());
    }
    
    @Test
    public void testGetEdges_CustomerTest_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "CustomerTest", "testStatement2( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(50, result.size());
    }
    
    @Test
    public void testGetEdges_Order_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Order", "Order( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdges_Order_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Order", "addRental( Rental )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(7, result.size());
    }
    
    @Test
    public void testGetEdges_Order_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Order", "getSize( )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(7, result.size());
    }
    
    @Test
    public void testGetEdges_Order_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Order", "rentals");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetEdges_Rental_1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Rental", "Rental( int int )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetEdges_Rental_2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Rental", "getCharge( double )");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(9, result.size());
    }
    
    @Test
    public void testGetEdges_Rental_3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Rental", "price");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetEdges_Rental_4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Rental", "days");
        List<ControlFlow> result = cfg.getEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testIsMethod1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        
        assertTrue(cfg.isMethod());
    }
    
    @Test
    public void testIsMethod2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "Test103( )");
        
        assertTrue(cfg.isMethod());
    }
    
    @Test
    public void testIsMethod3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "I134", "f( int )");
        
        assertTrue(cfg.isMethod());
    }
    
    @Test
    public void testIsMethod4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "PriceCode( int )");
        
        assertTrue(cfg.isMethod());
    }
    
    @Test
    public void testIsMethod5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "getPriceCode( )");
        
        assertTrue(cfg.isMethod());
    }
    
    @Test
    public void testIsMethod6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Customer", "Customer( java.lang.String )");
        
        assertTrue(cfg.isMethod());
    }
    
    @Test
    public void testIsField1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "a");
        
        assertTrue(cfg.isField());
    }
    
    @Test
    public void testIsField2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "CHILDRENS");
        
        assertTrue(cfg.isField());
    }
}
