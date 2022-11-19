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
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;
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
        PDGNode node = pdg.getNode(1);
        CFGNode result = node.getCFGNode();
        
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode cfgnode = CFGTestUtil.getNode(cfg, 1);
        assertEquals(cfgnode, result);
    }
    
    @Test
    public void testGetCFGNode2() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = pdg.getNode(4);
        CFGNode result = node.getCFGNode();
        
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode cfgnode = CFGTestUtil.getNode(cfg, 4);
        assertEquals(cfgnode, result);
    }
    
    @Test
    public void testGetCFGNode3() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = pdg.getNode(5);
        CFGNode result = node.getCFGNode();
        
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode cfgnode = CFGTestUtil.getNode(cfg, 5);
        assertEquals(cfgnode, result);
    }
    
    @Test
    public void testGetCFGNode4() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = pdg.getNode(11);
        CFGNode result = node.getCFGNode();
        
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode cfgnode = CFGTestUtil.getNode(cfg, 11);
        assertEquals(cfgnode, result);
    }
    
    @Test
    public void testGetCFGNode5() {
        PDG pdg = PDGTestUtil.createPDG(SliceProject, "Test108", "m( )");
        PDGNode node = pdg.getNode(7);
        
        assertNull(node);
    }
}
