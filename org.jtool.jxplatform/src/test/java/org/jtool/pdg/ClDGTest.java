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

public class ClDGTest {
    
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
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(55, result.size());
    }
    
    @Test
    public void testGetNodesTest119_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test119");
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(48, result.size());
    }
    
    @Test
    public void testGetNodesA119_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "A119");
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(14, result.size());
    }
    
    @Test
    public void testGetNodesTest129_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test129");
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(84, result.size());
    }
    
    @Test
    public void testGetNodesP129_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(42, result.size());
    }
    
    @Test
    public void testGetNodesQ134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Q134");
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(7, result.size());
    }
    
    @Test
    public void testGetNodesR134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "R134");
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(7, result.size());
    }
    
    @Test
    public void testGetNodesI134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "I134");
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetNodesTest139() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test139");
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(15, result.size());
    }
    
    @Test
    public void testGetNodesPriceCode_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(28, result.size());
    }
    
    @Test
    public void testGetNodesCustomer_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(40, result.size());
    }
    
    @Test
    public void testGetNodesCustomerTest_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "CustomerTest");
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(91, result.size());
    }
    
    @Test
    public void testGetNodesOrder_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Order");
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(21, result.size());
    }
    
    @Test
    public void testGetNodesRental_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Rental");
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(19, result.size());
    }
    
    @Test
    public void testGetEdges103_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        List<Dependence> result = cldg.getEdges();
        
        assertEquals(84, result.size());
    }
    
    @Test
    public void testGetEdgesTest119_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test119");
        List<Dependence> result = cldg.getEdges();
        
        assertEquals(81, result.size());
    }
    
    @Test
    public void testGetEdgesA119_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "A119");
        List<Dependence> result = cldg.getEdges();
        
        assertEquals(15, result.size());
    }
    
    @Test
    public void testGetEdgesTest129_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test129");
        List<Dependence> result = cldg.getEdges();
        
        assertEquals(128, result.size());
    }
    
    @Test
    public void testGetEdgesP129_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        List<Dependence> result = cldg.getEdges();
        
        assertEquals(55, result.size());
    }
    
    @Test
    public void testGetEdgesQ134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Q134");
        List<Dependence> result = cldg.getEdges();
        
        assertEquals(8, result.size());
    }
    
    @Test
    public void testGetEdgesR134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "R134");
        List<Dependence> result = cldg.getEdges();
        
        assertEquals(8, result.size());
    }
    
    @Test
    public void testGetEdgesI134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "I134");
        List<Dependence> result = cldg.getEdges();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetEdgesTest139_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test139");
        List<Dependence> result = cldg.getEdges();
        
        assertEquals(16, result.size());
    }
    
    @Test
    public void testGetEdgesPriceCode_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        List<Dependence> result = cldg.getEdges();
        
        assertEquals(32, result.size());
    }
    
    @Test
    public void testGetEdgesCustomer_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        List<Dependence> result = cldg.getEdges();
        
        assertEquals(74, result.size());
    }
    
    @Test
    public void testGetEdgesCustomerTest_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "CustomerTest");
        List<Dependence> result = cldg.getEdges();
        
        assertEquals(154, result.size());
    }
    
    @Test
    public void testGetEdgesOrder_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Order");
        List<Dependence> result = cldg.getEdges();
        
        assertEquals(25, result.size());
    }
    
    @Test
    public void testGetEdgesRental_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Rental");
        List<Dependence> result = cldg.getEdges();
        
        assertEquals(25, result.size());
    }
    
    @Test
    public void testGetInterEdges103_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        List<Dependence> result = cldg.getInterEdges();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetInterEdgesTest119_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test119");
        List<Dependence> result = cldg.getInterEdges();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetInterEdgesA119_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "A119");
        List<Dependence> result = cldg.getInterEdges();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetInterEdgesTest129_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test129");
        List<Dependence> result = cldg.getInterEdges();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetInterEdgesP129_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        List<Dependence> result = cldg.getInterEdges();
        
        assertEquals(8, result.size());
    }
    
    @Test
    public void testGetInterEdgesQ134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Q134");
        List<Dependence> result = cldg.getInterEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetInterEdgesR134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "R134");
        List<Dependence> result = cldg.getInterEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetInterEdgesI134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "I134");
        List<Dependence> result = cldg.getInterEdges();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetInterEdgesTest139_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test139");
        List<Dependence> result = cldg.getInterEdges();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetInterEdgesPriceCode_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        List<Dependence> result = cldg.getInterEdges();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetInterEdgesCustomer_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        List<Dependence> result = cldg.getInterEdges();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetInterEdgesCustomerTest_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "CustomerTest");
        List<Dependence> result = cldg.getInterEdges();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetInterEdgesOrder_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Order");
        List<Dependence> result = cldg.getInterEdges();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetInterEdgesRental_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Rental");
        List<Dependence> result = cldg.getInterEdges();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetPDGsTest103_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        
        assertEquals(6, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsTest103_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        
        assertEquals(6, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsTest119_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test119");
        
        assertEquals(5, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsA119_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "A119");
        
        assertEquals(5, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsA129_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test129");
        
        assertEquals(4, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsP129_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        
        assertEquals(8, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsQ134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Q134");
        
        assertEquals(2, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsR134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "R134");
        
        assertEquals(2, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsI134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "I134");
        
        assertEquals(1, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsTest139_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test139");
        
        assertEquals(2, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsPriceCode_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        
        assertEquals(6, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsCustomer_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        
        assertEquals(6, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsCustomerTest_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "CustomerTest");
        
        assertEquals(3, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsOrder_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Order");
        
        assertEquals(4, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGsRental_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Rental");
        
        assertEquals(4, cldg.getPDGs().size());
    }
    
    @Test
    public void testIsClDG1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        PDG pdg = cldg.findPDG("Test103#a");
        
        assertFalse(pdg.isClDG());
    }
    
    @Test
    public void testIsClDG2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        
        assertTrue(cldg.isClDG());
    }
}
