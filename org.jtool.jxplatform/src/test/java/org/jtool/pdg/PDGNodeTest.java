/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.srcmodel.JavaProject;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGNode;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.CFGTestUtil;
import org.jtool.jxplatform.util.PDGTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import java.util.List;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;


public class PDGNodeTest {
    
    private static JavaProject SimpleProject;
    private static JavaProject SliceProject;
    
    @BeforeClass
    public static void setUp() {
        SimpleProject = BuilderTestUtil.createProject("Simple", "", "");
        SliceProject = BuilderTestUtil.createProject("Slice", "", "");
    }
    
    @AfterClass
    public static void tearDown() {
        SimpleProject.getModelBuilder().unbuild();
        SliceProject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testGetCFGNode1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 1);
        CFGNode result = node.getCFGNode();
        
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode cfgnode = CFGTestUtil.getNode(cfg, 1);
        assertEquals(cfgnode, result);
    }
    
    @Test
    public void testGetCFGNode2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 4);
        CFGNode result = node.getCFGNode();
        
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode cfgnode = CFGTestUtil.getNode(cfg, 4);
        assertEquals(cfgnode, result);
    }
    
    @Test
    public void testGetCFGNode3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 5);
        CFGNode result = node.getCFGNode();
        
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode cfgnode = CFGTestUtil.getNode(cfg, 5);
        assertEquals(cfgnode, result);
    }
    
    @Test
    public void testGetCFGNode4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 11);
        CFGNode result = node.getCFGNode();
        
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode cfgnode = CFGTestUtil.getNode(cfg, 11);
        assertEquals(cfgnode, result);
    }
    
    @Test
    public void testGetCFGNode5() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 7);
        
        assertNull(node);
    }
    
    @Test
    public void testGetIncomingDependeceEdges1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 5);
        List<String> result = PDGTestUtil.getIdListOfSrc(pdg, pdg.getIncomingDependenceEdges(node));
        
        assertEquals("2;4", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetIncomingDependeceEdges2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 6);
        List<String> result = PDGTestUtil.getIdListOfSrc(pdg, pdg.getIncomingDependenceEdges(node));
        
        assertEquals("1;3;4", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetIncomingCDEdges1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 2);
        List<String> result = PDGTestUtil.getIdListOfSrc(pdg, pdg.getIncomingCDEdges(node));
        
        assertEquals("0", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetIncomingCDEdges2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 3);
        List<String> result = PDGTestUtil.getIdListOfSrc(pdg, pdg.getIncomingCDEdges(node));
        
        assertEquals("0", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetIncomingCDEdges3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 5);
        List<String> result = PDGTestUtil.getIdListOfSrc(pdg, pdg.getIncomingCDEdges(node));
        
        assertEquals("2;4", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetIncomingCDEdges4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 6);
        List<String> result = PDGTestUtil.getIdListOfSrc(pdg, pdg.getIncomingCDEdges(node));
        
        assertEquals("1;3;4", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetIncomingDDEdges1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 2);
        List<String> result = PDGTestUtil.getIdListOfSrc(pdg, pdg.getIncomingDDEdges(node));
        
        assertEquals("", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetIncomingDDEdges2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 4);
        List<String> result = PDGTestUtil.getIdListOfSrc(pdg, pdg.getIncomingDDEdges(node));
        
        assertEquals("1", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetIncomingDDEdges3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 5);
        List<String> result = PDGTestUtil.getIdListOfSrc(pdg, pdg.getIncomingDDEdges(node));
        
        assertEquals("2", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetOutgoingDependeceEdges1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 2);
        List<String> result = PDGTestUtil.getIdListOfDst(pdg, pdg.getOutgoingDependenceEdges(node));
        
        assertEquals("5;8", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetOutgoingDependeceEdges2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 4);
        List<String> result = PDGTestUtil.getIdListOfDst(pdg, pdg.getOutgoingDependenceEdges(node));
        
        assertEquals("5;6", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetOutgoingCDEdges1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 0);
        List<String> result = PDGTestUtil.getIdListOfDst(pdg, pdg.getOutgoingCDEdges(node));
        
        assertEquals("1;11;2;3;4;8;9", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetOutgoingCDEdges2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 4);
        List<String> result = PDGTestUtil.getIdListOfDst(pdg, pdg.getOutgoingCDEdges(node));
        
        assertEquals("5;6", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetOutgoingDDEdges1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 1);
        List<String> result = PDGTestUtil.getIdListOfDst(pdg, pdg.getOutgoingDDEdges(node));
        
        assertEquals("4;6", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetOutgoingDDEdges2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 2);
        List<String> result = PDGTestUtil.getIdListOfDst(pdg, pdg.getOutgoingDDEdges(node));
        
        assertEquals("5;8", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetOutgoingDDEdges3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 3);
        List<String> result = PDGTestUtil.getIdListOfDst(pdg, pdg.getOutgoingDDEdges(node));
        
        assertEquals("6;9", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetOutgoingDDEdges4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 9);
        List<String> result = PDGTestUtil.getIdListOfDst(pdg, pdg.getOutgoingDDEdges(node));
        
        assertEquals("", TestUtil.asStr(result));
    }
    
    @Test
    public void testIsDominated1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 0);
        
        assertFalse(node.isDominated());
    }
    
    @Test
    public void testIsDominated2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 2);
        
        assertTrue(node.isDominated());
    }
    
    @Test
    public void testIsDominated3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 4);
        
        assertTrue(node.isDominated());
    }
    
    @Test
    public void testIsDominated4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 5);
        
        assertTrue(node.isDominated());
    }
    
    @Test
    public void testIsDominated5() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 6);
        
        assertTrue(node.isDominated());
    }
    
    @Test
    public void testIsTrueDominated1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 0);
        
        assertFalse(node.isTrueDominated());
    }
    
    @Test
    public void testIsTrueDominated2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 2);
        
        assertTrue(node.isTrueDominated());
    }
    
    @Test
    public void testIsTrueDominated3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 4);
        
        assertTrue(node.isTrueDominated());
    }
    
    @Test
    public void testIsTrueDominated4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 5);
        
        assertTrue(node.isTrueDominated());
    }
    
    @Test
    public void testIsTrueDominated5() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 6);
        
        assertFalse(node.isTrueDominated());
    }
    
    @Test
    public void testIsFalseDominated1() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 0);
        
        assertFalse(node.isFalseDominated());
    }
    
    @Test
    public void testIsFalseDominated2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 2);
        
        assertFalse(node.isFalseDominated());
    }
    
    @Test
    public void testIsFalseDominated3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 4);
        
        assertFalse(node.isFalseDominated());
    }
    
    @Test
    public void testIsFalseDominated4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 5);
        
        assertFalse(node.isFalseDominated());
    }
    
    @Test
    public void testIsFalseDominated5() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = PDGTestUtil.getNode(pdg, 6);
        
        assertTrue(node.isFalseDominated());
    }
}
