/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.CFGTestUtil;
import org.jtool.cfg.builder.CallGraphBuilder;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CallGraphTest {
    
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
    public void testCalleeNodes1() {
        CallGraph graph = CallGraphBuilder.getCallGraph(SliceProject);
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        Set<CFGEntry> result = graph.getCalleeNodes(cfg.getEntryNode());
        
        assertEquals("Test102#inc( int )", CFGTestUtil.asSortedStrOfCFGEntry(result));
    }
    
    @Test
    public void testCalleeNodes2() {
        CallGraph graph = CallGraphBuilder.getCallGraph(SliceProject);
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "inc( int )");
        Set<CFGEntry> result = graph.getCalleeNodes(cfg.getEntryNode());
        
        assertEquals("", CFGTestUtil.asSortedStrOfCFGEntry(result));
    }
    
    @Test
    public void testCalleeNodes3() {
        CallGraph graph = CallGraphBuilder.getCallGraph(SliceProject);
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        Set<CFGEntry> result = graph.getCalleeNodes(cfg.getEntryNode());
        
        assertEquals("Test103#getA( );Test103#incA( );Test103#setA( int )",
                CFGTestUtil.asSortedStrOfCFGEntry(result));
    }
    
    @Test
    public void testCalleeNodes4() {
        CallGraph graph = CallGraphBuilder.getCallGraph(SliceProject);
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        Set<CFGEntry> result = graph.getCalleeNodes(cfg.getEntryNode());
        
        assertEquals("A119#A119( );A119#getX( );A119#setX( int );Test119#getP( )",
                CFGTestUtil.asSortedStrOfCFGEntry(result));
    }
    
    @Test
    public void testCalleeNodes5() {
        CallGraph graph = CallGraphBuilder.getCallGraph(SliceProject);
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        Set<CFGEntry> result = graph.getCalleeNodes(cfg.getEntryNode());
        
        assertEquals("A126#A126( );A126#add( int );A126#getY( );A126#setY( int )",
                CFGTestUtil.asSortedStrOfCFGEntry(result));
    }
    
    @Test
    public void testCalleeNodes6() {
        CallGraph graph = CallGraphBuilder.getCallGraph(SliceProject);
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        Set<CFGEntry> result = graph.getCalleeNodes(cfg.getEntryNode());
        
        assertEquals("A128#A128( );A128#add( int );A128#getY( );A128#setY( int );Test128#n( int int )",
                CFGTestUtil.asSortedStrOfCFGEntry(result));
    }
    
    @Test
    public void testCalleeNodes7() {
        CallGraph graph = CallGraphBuilder.getCallGraph(SliceProject);
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "m( )");
        Set<CFGEntry> result = graph.getCalleeNodes(cfg.getEntryNode());
        
        assertEquals("", CFGTestUtil.asSortedStrOfCFGEntry(result));
    }
    
    @Test
    public void testCallerNodes1() {
        CallGraph graph = CallGraphBuilder.getCallGraph(SliceProject);
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        Set<CFGEntry> result = graph.getCallerNodes(cfg.getEntryNode());
        
        assertEquals("", CFGTestUtil.asSortedStrOfCFGEntry(result));
    }
    
    @Test
    public void testCallerNodes2() {
        CallGraph graph = CallGraphBuilder.getCallGraph(SliceProject);
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "inc( int )");
        Set<CFGEntry> result = graph.getCallerNodes(cfg.getEntryNode());
        
        assertEquals("Test102#m( )", CFGTestUtil.asSortedStrOfCFGEntry(result));
    }
    
    @Test
    public void testCallerNodes3() {
        CallGraph graph = CallGraphBuilder.getCallGraph(SliceProject);
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        Set<CFGEntry> result = graph.getCallerNodes(cfg.getEntryNode());
        
        assertEquals("Test103#m( )", CFGTestUtil.asSortedStrOfCFGEntry(result));
    }
    
    @Test
    public void testCallerNodes4() {
        CallGraph graph = CallGraphBuilder.getCallGraph(SliceProject);
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "A119", "getX( )");
        Set<CFGEntry> result = graph.getCallerNodes(cfg.getEntryNode());
        
        assertEquals("Test119#m( )", CFGTestUtil.asSortedStrOfCFGEntry(result));
    }
    
    @Test
    public void testCallerNodes5() {
        CallGraph graph = CallGraphBuilder.getCallGraph(SliceProject);
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "A126", "setY( int )");
        Set<CFGEntry> result = graph.getCallerNodes(cfg.getEntryNode());
        
        assertEquals("A126#add( int );Test126#m( )", CFGTestUtil.asSortedStrOfCFGEntry(result));
    }
    
    @Test
    public void testGetCallFlowsFrom1() {
        CallGraph graph = CallGraphBuilder.getCallGraph(SliceProject);
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        Set<ControlFlow> result = graph.getCallFlowsFrom(cfg.getEntryNode());
        
        Set<CFGEntry> nodes = result.stream().map(e -> (CFGEntry)e.getDstNode()).collect(Collectors.toSet());
        assertEquals("Test102#inc( int )", CFGTestUtil.asSortedStrOfCFGEntry(nodes));
    }
    
    @Test
    public void testGetCallFlowsFrom2() {
        CallGraph graph = CallGraphBuilder.getCallGraph(SliceProject);
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "inc( int )");
        Set<ControlFlow> result = graph.getCallFlowsFrom(cfg.getEntryNode());
        
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testGetCallFlowsFrom3() {
        CallGraph graph = CallGraphBuilder.getCallGraph(SliceProject);
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        Set<ControlFlow> result = graph.getCallFlowsFrom(cfg.getEntryNode());
        
        Set<CFGEntry> nodes = result.stream().map(e -> (CFGEntry)e.getDstNode()).collect(Collectors.toSet());
        assertEquals("Test103#getA( );Test103#incA( );Test103#setA( int )",
                CFGTestUtil.asSortedStrOfCFGEntry(nodes));
    }
    
    @Test
    public void testGetCallFlowsFrom4() {
        CallGraph graph = CallGraphBuilder.getCallGraph(SliceProject);
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        Set<ControlFlow> result = graph.getCallFlowsFrom(cfg.getEntryNode());
        
        Set<CFGEntry> nodes = result.stream().map(e -> (CFGEntry)e.getDstNode()).collect(Collectors.toSet());
        assertEquals("A119#A119( );A119#getX( );A119#setX( int );Test119#getP( )",
                CFGTestUtil.asSortedStrOfCFGEntry(nodes));
    }
    
    @Test
    public void testGetCallFlowsFrom5() {
        CallGraph graph = CallGraphBuilder.getCallGraph(SliceProject);
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        Set<ControlFlow> result = graph.getCallFlowsFrom(cfg.getEntryNode());
        
        Set<CFGEntry> nodes = result.stream().map(e -> (CFGEntry)e.getDstNode()).collect(Collectors.toSet());
        assertEquals("A126#A126( );A126#add( int );A126#getY( );A126#setY( int )",
                CFGTestUtil.asSortedStrOfCFGEntry(nodes));
    }
    
    @Test
    public void testGetCallFlowsFrom6() {
        CallGraph graph = CallGraphBuilder.getCallGraph(SliceProject);
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        Set<ControlFlow> result = graph.getCallFlowsFrom(cfg.getEntryNode());
        
        Set<CFGEntry> nodes = result.stream().map(e -> (CFGEntry)e.getDstNode()).collect(Collectors.toSet());
        assertEquals("A128#A128( );A128#add( int );A128#getY( );A128#setY( int );Test128#n( int int )",
                CFGTestUtil.asSortedStrOfCFGEntry(nodes));
    }
    
    @Test
    public void testGetCallFlowsFrom7() {
        CallGraph graph = CallGraphBuilder.getCallGraph(SliceProject);
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test131", "m( )");
        Set<CFGEntry> result = graph.getCalleeNodes(cfg.getEntryNode());
        
        assertTrue(result.isEmpty());
    }
}
