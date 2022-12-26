/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.JavaProject;
import org.jtool.cfg.internal.CFGTestUtil;
import org.jtool.cfg.internal.CallGraphBuilder;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import java.util.Set;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ControlFlowTest {
    
    private static JavaProject SimpleProject;
    private static JavaProject SliceProject;
    
    @BeforeClass
    public static void setUp() {
        SimpleProject = BuilderTestUtil.getProject("Simple");
        SliceProject = BuilderTestUtil.getProject("Slice");
    }
    
    @Test
    public void testIsTrue1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 0);
        assert node.isMethodEntry();
        ControlFlow result = cfg.getTrueFlowFrom(node);
        
        assertTrue(result.isTrue());
        assertEquals("0->1", CFGTestUtil.asStrOfEdge(cfg, result));
    }
    
    @Test
    public void testIsTrue2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 1);
        assert node.isLocalDeclaration();
        ControlFlow result = cfg.getTrueFlowFrom(node);
        
        assertTrue(result.isTrue());
        assertEquals("1->2", CFGTestUtil.asStrOfEdge(cfg, result));
    }
    
    @Test
    public void testIsTrue3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 4);
        assert node.isIf();
        ControlFlow result = cfg.getTrueFlowFrom(node);
        
        assertTrue(result.isTrue());
        assertEquals("4->5", CFGTestUtil.asStrOfEdge(cfg, result));
    }
    
    @Test
    public void testIsTrue4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 5);
        assert node.isAssignment();
        ControlFlow result = cfg.getTrueFlowFrom(node);
        
        assertTrue(result.isTrue());
        assertEquals("5->7", CFGTestUtil.asStrOfEdge(cfg, result));
    }
    
    @Test
    public void testIsTrue5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 6);
        assert node.isAssignment();
        ControlFlow result = cfg.getTrueFlowFrom(node);
        
        assertTrue(result.isTrue());
        assertEquals("6->7", CFGTestUtil.asStrOfEdge(cfg, result));
    }
    
    @Test
    public void testIsTrue6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test110", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 2);
        assert node.isLocalDeclaration();
        ControlFlow result = cfg.getTrueFlowFrom(node);
        
        assertTrue(result.isTrue());
        assertEquals("2->3", CFGTestUtil.asStrOfEdge(cfg, result));
    }
    
    @Test
    public void testIsTrue7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test110", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 3);
        assert node.isWhile();
        ControlFlow result = cfg.getTrueFlowFrom(node);
        
        assertTrue(result.isTrue());
        assertEquals("3->4", CFGTestUtil.asStrOfEdge(cfg, result));
    }
    
    @Test
    public void testIsFalse1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 4);
        assert node.isIf();
        ControlFlow result = cfg.getFalseFlowFrom(node);
        
        assertTrue(result.isFalse());
        assertEquals("4->6", CFGTestUtil.asStrOfEdge(cfg, result));
    }
    
    @Test
    public void testIsFalse2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test110", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 3);
        assert node.isWhile();
        ControlFlow result = cfg.getFalseFlowFrom(node);
        
        assertTrue(result.isFalse());
        assertEquals("3->5", CFGTestUtil.asStrOfEdge(cfg, result));
    }
    
    @Test
    public void testIsFallThrough1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 1);
        assert node.isReturn();
        ControlFlow result = cfg.getFallThroughFlowFrom(node);
        
        assertTrue(result.isFallThrough());
        assertEquals("1->3", CFGTestUtil.asStrOfEdge(cfg, result));
    }
    
    @Test
    public void testIsFallThrough2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test111", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 7);
        assert node.isBreak();
        ControlFlow result = cfg.getFallThroughFlowFrom(node);
        
        assertTrue(result.isFallThrough());
        assertEquals("7->8", CFGTestUtil.asStrOfEdge(cfg, result));
    }
    
    @Test
    public void testIsFallThrough3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "n( int )");
        CFGNode node = CFGTestUtil.getNode(cfg, 4);
        assert node.isThrow();
        ControlFlow result = cfg.getFallThroughFlowFrom(node);
        
        assertTrue(result.isFallThrough());
        assertEquals("4->8", CFGTestUtil.asStrOfEdge(cfg, result));
    }
    
    @Test
    public void testIsFallThrough4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S135", "get2( java.lang.String )");
        CFGNode node = CFGTestUtil.getNode(cfg, 6);
        assert node.isReturn();
        ControlFlow result = cfg.getFallThroughFlowFrom(node);
        
        assertTrue(result.isFallThrough());
        assertEquals("6->7", CFGTestUtil.asStrOfEdge(cfg, result));
    }
    
    @Test
    public void testIsFallThrough5() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test35", "m1( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 4);
        assert node.isBreak();
        ControlFlow result = cfg.getFallThroughFlowFrom(node);
        
        assertTrue(result.isFallThrough());
        assertEquals("4->5", CFGTestUtil.asStrOfEdge(cfg, result));
    }
    
    @Test
    public void testIsFallThrough6() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test35", "m2( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 5);
        assert node.isContinue();
        ControlFlow result = cfg.getFallThroughFlowFrom(node);
        
        assertTrue(result.isFallThrough());
        assertEquals("5->6", CFGTestUtil.asStrOfEdge(cfg, result));
    }
    
    @Test
    public void testIsCall1() {
        CallGraph graph = CallGraphBuilder.getCallGraph(SliceProject);
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test102", "m( )");
        Set<ControlFlow> result = graph.getCallFlowsFrom(cfg.getEntryNode());
        
        assertTrue(result.stream().allMatch(e -> e.isCall()));
    }
    
    @Test
    public void testIsCall2() {
        CallGraph graph = CallGraphBuilder.getCallGraph(SliceProject);
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        Set<ControlFlow> result = graph.getCallFlowsFrom(cfg.getEntryNode());
        
        assertTrue(result.stream().allMatch(e -> e.isCall()));
    }
    
    @Test
    public void testIsCall3() {
        CallGraph graph = CallGraphBuilder.getCallGraph(SliceProject);
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test119", "m( )");
        Set<ControlFlow> result = graph.getCallFlowsFrom(cfg.getEntryNode());
        
        assertTrue(result.stream().allMatch(e -> e.isCall()));
    }
    
    @Test
    public void testIsCall4() {
        CallGraph graph = CallGraphBuilder.getCallGraph(SliceProject);
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test126", "m( )");
        Set<ControlFlow> result = graph.getCallFlowsFrom(cfg.getEntryNode());
        
        assertTrue(result.stream().allMatch(e -> e.isCall()));
    }
    
    @Test
    public void testIsCall5() {
        CallGraph graph = CallGraphBuilder.getCallGraph(SliceProject);
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test128", "m( )");
        Set<ControlFlow> result = graph.getCallFlowsFrom(cfg.getEntryNode());
        
        assertTrue(result.stream().allMatch(e -> e.isCall()));
    }
    
    @Test
    public void testIsExceptionCatch1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 7);
        assert node.isMethodCall();
        ControlFlow result = cfg.getExceptionCatchFlowFrom(node);
        
        assertTrue(result.isExceptionCatch());
        assertEquals("7->12", CFGTestUtil.asStrOfEdge(cfg, result));
    }
    
    @Test
    public void testIsExceptionCatch2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test123", "m2( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 4);
        assert node.isMethodCall();
        ControlFlow result = cfg.getExceptionCatchFlowFrom(node);
        
        assertTrue(result.isExceptionCatch());
        assertEquals("4->8", CFGTestUtil.asStrOfEdge(cfg, result));
    }
    
    @Test
    public void testIsLoopBack1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test110", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 4);
        ControlFlow result = cfg.getTrueFlowFrom(node);
        
        assertTrue(result.isTrue());
        assertTrue(result.isLoopBack());
        assertEquals("4->3", CFGTestUtil.asStrOfEdge(cfg, result));
    }
    
    @Test
    public void testIsLoopBack2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test115", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 8);
        ControlFlow result = cfg.getTrueFlowFrom(node);
        
        assertTrue(result.isTrue());
        assertTrue(result.isLoopBack());
        assertEquals("8->5", CFGTestUtil.asStrOfEdge(cfg, result));
    }
    
    public void testIsLoopBack3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test22", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 3);
        ControlFlow result = cfg.getTrueFlowFrom(node);
        
        assertTrue(result.isTrue());
        assertTrue(result.isLoopBack());
        assertEquals("3->2", CFGTestUtil.asStrOfEdge(cfg, result));
    }
    
    @Test
    public void testGetLoopBack1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test110", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 4);
        ControlFlow result = cfg.getTrueFlowFrom(node);
        
        assertEquals("3", CFGTestUtil.asStrOfNode(cfg, result.getLoopBack()));
    }
    
    @Test
    public void testGetLoopBack2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test115", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 8);
        ControlFlow result = cfg.getTrueFlowFrom(node);
        
        assertEquals("5", CFGTestUtil.asStrOfNode(cfg, result.getLoopBack()));
    }
    
    @Test
    public void testGetLoopBack3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test22", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 3);
        ControlFlow result = cfg.getTrueFlowFrom(node);
        
        assertEquals("3", CFGTestUtil.asStrOfNode(cfg, result.getLoopBack()));
    }
}
