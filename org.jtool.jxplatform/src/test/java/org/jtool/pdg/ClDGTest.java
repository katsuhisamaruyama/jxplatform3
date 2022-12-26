/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.srcmodel.JavaProject;
import java.util.List;
import java.util.Set;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ClDGTest {
    
    private static JavaProject SliceProject;
    
    @BeforeClass
    public static void setUp() {
        SliceProject = BuilderTestUtil.getProject("Slice");
    }
    
    @Test
    public void testGetNodes_Test103_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(55, result.size());
    }
    
    @Test
    public void testGetNodes_Test119_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test119");
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(48, result.size());
    }
    
    @Test
    public void testGetNodes_A119_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "A119");
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(14, result.size());
    }
    
    @Test
    public void testGetNodes_Test129_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test129");
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(84, result.size());
    }
    
    @Test
    public void testGetNodes_P129_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(42, result.size());
    }
    
    @Test
    public void testGetNodes_Q134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Q134");
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(7, result.size());
    }
    
    @Test
    public void testGetNodes_R134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "R134");
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(7, result.size());
    }
    
    @Test
    public void testGetNodes_I134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "I134");
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetNodes_Test139() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test139");
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(15, result.size());
    }
    
    @Test
    public void testGetNodes_PriceCode_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(28, result.size());
    }
    
    @Test
    public void testGetNodes_Customer_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(40, result.size());
    }
    
    @Test
    public void testGetNodes_CustomerTest_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "CustomerTest");
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(91, result.size());
    }
    
    @Test
    public void testGetNodes_Order_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Order");
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(21, result.size());
    }
    
    @Test
    public void testGetNodes_Rental_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Rental");
        Set<PDGNode> result = cldg.getNodes();
        
        assertEquals(19, result.size());
    }
    
    @Test
    public void testGetEdges_Test103_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        List<DependencyGraphEdge> result = cldg.getEdges();
        
        assertEquals(84, result.size());
    }
    
    @Test
    public void testGetEdges_Test119_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test119");
        List<DependencyGraphEdge> result = cldg.getEdges();
        
        assertEquals(81, result.size());
    }
    
    @Test
    public void testGetEdges_A119_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "A119");
        List<DependencyGraphEdge> result = cldg.getEdges();
        
        assertEquals(15, result.size());
    }
    
    @Test
    public void testGetEdges_Test129_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test129");
        List<DependencyGraphEdge> result = cldg.getEdges();
        
        assertEquals(128, result.size());
    }
    
    @Test
    public void testGetEdges_P129_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        List<DependencyGraphEdge> result = cldg.getEdges();
        
        assertEquals(55, result.size());
    }
    
    @Test
    public void testGetEdges_Q134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Q134");
        List<DependencyGraphEdge> result = cldg.getEdges();
        
        assertEquals(8, result.size());
    }
    
    @Test
    public void testGetEdges_R134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "R134");
        List<DependencyGraphEdge> result = cldg.getEdges();
        
        assertEquals(8, result.size());
    }
    
    @Test
    public void testGetEdges_I134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "I134");
        List<DependencyGraphEdge> result = cldg.getEdges();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetEdges_Test139_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test139");
        List<DependencyGraphEdge> result = cldg.getEdges();
        
        assertEquals(16, result.size());
    }
    
    @Test
    public void testGetEdges_PriceCode_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        List<DependencyGraphEdge> result = cldg.getEdges();
        
        assertEquals(32, result.size());
    }
    
    @Test
    public void testGetEdges_Customer_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        List<DependencyGraphEdge> result = cldg.getEdges();
        
        assertEquals(74, result.size());
    }
    
    @Test
    public void testGetEdges_CustomerTest_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "CustomerTest");
        List<DependencyGraphEdge> result = cldg.getEdges();
        
        assertEquals(154, result.size());
    }
    
    @Test
    public void testGetEdges_Order_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Order");
        List<DependencyGraphEdge> result = cldg.getEdges();
        
        assertEquals(25, result.size());
    }
    
    @Test
    public void testGetEdges_Rental_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Rental");
        List<DependencyGraphEdge> result = cldg.getEdges();
        
        assertEquals(25, result.size());
    }
    
    @Test
    public void testGetInterEdges_Test103_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        List<InterPDGCD> result = cldg.getInterPDGCDs();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetInterEdges_Test119_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test119");
        List<InterPDGCD> result = cldg.getInterPDGCDs();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetInterEdges_A119_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "A119");
        List<InterPDGCD> result = cldg.getInterPDGCDs();
        
        assertEquals(5, result.size());
    }
    
    @Test
    public void testGetInterEdges_Test129_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test129");
        List<InterPDGCD> result = cldg.getInterPDGCDs();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetInterEdges_P129_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        List<InterPDGCD> result = cldg.getInterPDGCDs();
        
        assertEquals(8, result.size());
    }
    
    @Test
    public void testGetInterEdges_Q134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Q134");
        List<InterPDGCD> result = cldg.getInterPDGCDs();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetInterEdges_R134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "R134");
        List<InterPDGCD> result = cldg.getInterPDGCDs();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetInterEdges_I134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "I134");
        List<InterPDGCD> result = cldg.getInterPDGCDs();
        
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetInterEdges_Test139_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test139");
        List<InterPDGCD> result = cldg.getInterPDGCDs();
        
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetInterEdges_PriceCode_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        List<InterPDGCD> result = cldg.getInterPDGCDs();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetInterEdges_Customer_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        List<InterPDGCD> result = cldg.getInterPDGCDs();
        
        assertEquals(6, result.size());
    }
    
    @Test
    public void testGetInterEdges_CustomerTest_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "CustomerTest");
        List<InterPDGCD> result = cldg.getInterPDGCDs();
        
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetInterEdges_Order_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Order");
        List<InterPDGCD> result = cldg.getInterPDGCDs();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetInterEdges_Rental_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Rental");
        List<InterPDGCD> result = cldg.getInterPDGCDs();
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetPDGs_Test103_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        
        assertEquals(6, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGs_Test103_2() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test103");
        
        assertEquals(6, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGs_Test119_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test119");
        
        assertEquals(5, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGs_A119_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "A119");
        
        assertEquals(5, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGs_A129_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test129");
        
        assertEquals(4, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGs_P129_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "P129");
        
        assertEquals(8, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGs_Q134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Q134");
        
        assertEquals(2, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGs_R134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "R134");
        
        assertEquals(2, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGs_I134_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "I134");
        
        assertEquals(1, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGs_Test139_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Test139");
        
        assertEquals(2, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGs_PriceCode_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "PriceCode");
        
        assertEquals(6, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGs_Customer_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Customer");
        
        assertEquals(6, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGs_CustomerTest_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "CustomerTest");
        
        assertEquals(3, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGs_Order_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Order");
        
        assertEquals(4, cldg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGs_Rental_1() {
        ClDG cldg = PDGTestUtil.createClDG(SliceProject, "Rental");
        
        assertEquals(4, cldg.getPDGs().size());
    }
}
