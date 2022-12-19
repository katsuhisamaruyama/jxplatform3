/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal;

import org.jtool.srcmodel.JavaProject;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGNode;
import org.jtool.cfg.CFGMerge;
import org.jtool.cfg.CFGException;
import org.jtool.cfg.ControlFlow;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import java.util.List;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.CatchClause;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StatementVisitorTest {
    
    private static JavaProject SimpleProject;
    
    @BeforeClass
    public static void setUp() {
        SimpleProject = BuilderTestUtil.createProject("Simple", "", "");
    }
    
    @AfterClass
    public static void tearDown() {
        SimpleProject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testEmptyStatement() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test02", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "EmptyStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof EmptyStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.emptySt);
        
        assertEquals(1, node.getSuccessors().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        
        assertEquals(";", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testExpressionStatement() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test13", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "ExpressionStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof ExpressionStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.assignment);
        
        assertEquals(1, node.getSuccessors().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 6);
        
        assertEquals("doReturn(a);", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testVariableDeclarationStatement() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test01", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "VariableDeclarationFragment");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode().getParent() instanceof VariableDeclarationStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.localDeclaration);
        
        assertEquals(1, node.getSuccessors().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        
        assertEquals("a", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testVariableDeclarationExpression() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test15", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "VariableDeclarationFragment");
        CFGNode node = nodes.get(1);
        assert (node.getASTNode().getParent() instanceof VariableDeclarationExpression);
        
        assertTrue(node.getKind() == CFGNode.Kind.localDeclaration);
        
        assertEquals(1, node.getSuccessors().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        
        assertEquals("i=0", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testConstructorInvocation() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "P31", "P31( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "ConstructorInvocation");
        CFGNode node = nodes.get(2);
        assert (node.getASTNode() instanceof ConstructorInvocation);
        
        assertTrue(node.getKind() == CFGNode.Kind.constructorCall);
        
        assertEquals(1, node.getSuccessors().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(cfg.getTrueSuccessor(node)).getId() - 2);
        
        assertEquals("this(100);", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testSuperConstructorInvocation() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "P31", "P31( int int )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "SuperConstructorInvocation");
        CFGNode node = nodes.get(2);
        assert (node.getASTNode() instanceof SuperConstructorInvocation);
        
        assertTrue(node.getKind() == CFGNode.Kind.constructorCall);
        
        assertEquals(1, node.getSuccessors().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(cfg.getTrueSuccessor(node)).getId() - 2);
        
        assertEquals("super(x);", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testIfStatement1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test01", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "IfStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof IfStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.ifSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 6);
        List<CFGNode> merges = CFGTestUtil.getNodes(cfg, CFGNode.Kind.merge);
        assertEquals(node.getId(), ((CFGMerge)merges.get(0)).getBranch().getId());
        
        assertEquals("if (a == 0) {\n"
                   + "  a++;\n"
                   + "  System.out.println(a);\n"
                   + "}\n"
                   + " else {\n"
                   + "  a++;\n"
                   + "  a=-b;\n"
                   + "  System.out.println(a);\n"
                   + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testIfStatement2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test07", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "IfStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof IfStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.ifSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 17);
        List<CFGNode> merges = CFGTestUtil.getNodes(cfg, CFGNode.Kind.merge);
        assertEquals(node.getId(), ((CFGMerge)merges.get(4)).getBranch().getId());
        
        assertEquals("if (c == d) {\n"
                   + "  if (a < b) {\n"
                   + "    b=a;\n"
                   + "    a=a + 1;\n"
                   + "  }\n"
                   + " else   if (b < a) {\n"
                   + "    a=b;\n"
                   + "    b=b + 1;\n"
                   + "  }\n"
                   + " else {\n"
                   + "    if (a < b) {\n"
                   + "      c=c + 1;\n"
                   + "      d=d - 1;\n"
                   + "    }\n"
                   + " else     if (b < a) {\n"
                   + "      c=c + e;\n"
                   + "      d=d - e;\n"
                   + "    }\n"
                   + "  }\n"
                   + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testIfStatement3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test07", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "IfStatement");
        CFGNode node = nodes.get(1);
        assert (node.getASTNode() instanceof IfStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.ifSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 3);
        List<CFGNode> merges = CFGTestUtil.getNodes(cfg, CFGNode.Kind.merge);
        assertEquals(node.getId(), ((CFGMerge)merges.get(3)).getBranch().getId());
        
        assertEquals("if (a < b) {\n"
                   + "  b=a;\n"
                   + "  a=a + 1;\n"
                   + "}\n"
                   + " else if (b < a) {\n"
                   + "  a=b;\n"
                   + "  b=b + 1;\n"
                   + "}\n"
                   + " else {\n"
                   + "  if (a < b) {\n"
                   + "    c=c + 1;\n"
                   + "    d=d - 1;\n"
                   + "  }\n"
                   + " else   if (b < a) {\n"
                   + "    c=c + e;\n"
                   + "    d=d - e;\n"
                   + "  }\n"
                   + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testIfStatement4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test07", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "IfStatement");
        CFGNode node = nodes.get(2);
        assert (node.getASTNode() instanceof IfStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.ifSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 3);
        List<CFGNode> merges = CFGTestUtil.getNodes(cfg, CFGNode.Kind.merge);
        assertEquals(node.getId(), ((CFGMerge)merges.get(2)).getBranch().getId());
        
        assertEquals("if (b < a) {\n"
                   + "  a=b;\n"
                   + "  b=b + 1;\n"
                   + "}\n"
                   + " else {\n"
                   + "  if (a < b) {\n"
                   + "    c=c + 1;\n"
                   + "    d=d - 1;\n"
                   + "  }\n"
                   + " else   if (b < a) {\n"
                   + "    c=c + e;\n"
                   + "    d=d - e;\n"
                   + "  }\n"
                   + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testIfStatement5() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test07", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "IfStatement");
        CFGNode node = nodes.get(3);
        assert (node.getASTNode() instanceof IfStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.ifSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 3);
        List<CFGNode> merges = CFGTestUtil.getNodes(cfg, CFGNode.Kind.merge);
        assertEquals(node.getId(), ((CFGMerge)merges.get(1)).getBranch().getId());
        
        assertEquals("if (a < b) {\n"
                   + "  c=c + 1;\n"
                   + "  d=d - 1;\n"
                   + "}\n"
                   + " else if (b < a) {\n"
                   + "  c=c + e;\n"
                   + "  d=d - e;\n"
                   + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testIfStatement6() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test07", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "IfStatement");
        CFGNode node = nodes.get(4);
        assert (node.getASTNode() instanceof IfStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.ifSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 3);
        List<CFGNode> merges = CFGTestUtil.getNodes(cfg, CFGNode.Kind.merge);
        assertEquals(node.getId(), ((CFGMerge)merges.get(0)).getBranch().getId());
        
        assertEquals("if (b < a) {\n"
                   + "  c=c + e;\n"
                   + "  d=d - e;\n"
                   + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testWhileStatement1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test05", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "WhileStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof WhileStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.whileSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 7);
        List<ControlFlow> lc = CFGTestUtil.getLCFlow(cfg);
        assertEquals(node.getId(), lc.get(0).getLoopBack().getId());
        
        assertEquals("while (b < 10) {\n"
                   + "  b=b + 1;\n"
                   + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testWhileStatement2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test05", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "WhileStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof WhileStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.whileSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 7);
        List<ControlFlow> lc = CFGTestUtil.getLCFlow(cfg);
        assertEquals(node.getId(), lc.get(0).getLoopBack().getId());
        
        assertEquals("while (b < 10) {\n"
                + "  b=b + 1;\n"
                + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testWhileStatement3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test19", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "WhileStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof WhileStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.whileSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 11);
        List<ControlFlow> lc = CFGTestUtil.getLCFlow(cfg);
        assertEquals(node.getId(), lc.get(2).getLoopBack().getId());
        
        assertEquals("while (i < 10) {\n"
                   + "  while (i < 0)   a=a + i + d;\n"
                   + "  if (i > 0)   a=a + i + d;\n"
                   + "  while (i < 3)   a=a - i + d;\n"
                   + "  a=a + i;\n"
                   + "  i++;\n"
                   + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testWhileStatement4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test19", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "WhileStatement");
        CFGNode node = nodes.get(1);
        assert (node.getASTNode() instanceof WhileStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.whileSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 2);
        List<ControlFlow> lc = CFGTestUtil.getLCFlow(cfg);
        assertEquals(node.getId(), lc.get(0).getLoopBack().getId());
        
        assertEquals("while (i < 0) a=a + i + d;", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testWhileStatement5() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test19", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "WhileStatement");
        CFGNode node = nodes.get(2);
        assert (node.getASTNode() instanceof WhileStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.whileSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 2);
        List<ControlFlow> lc = CFGTestUtil.getLCFlow(cfg);
        assertEquals(node.getId(), lc.get(1).getLoopBack().getId());
        
        assertEquals("while (i < 3) a=a - i + d;", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testWhileStatement6() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test63", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "WhileStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof WhileStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.whileSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 5);
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 7);
        List<ControlFlow> lc = CFGTestUtil.getLCFlow(cfg);
        assertEquals(node.getId(), lc.get(0).getLoopBack().getId());
        
        assertEquals("while (get(a) > 0) {\n"
                   + "  a--;\n"
                   + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testDoStatement() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test22", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "DoStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof DoStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.doSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() + 1);
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 2);
        List<ControlFlow> lc = CFGTestUtil.getLCFlow(cfg);
        assertEquals(node.getId(), lc.get(0).getLoopBack().getId());
        
        assertEquals("do {\n"
                   + "  a++;\n"
                   + "}\n"
                   + " while (a < 19);", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testSwitchStatement1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test03", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "SwitchStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof SwitchStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.switchSt);
        
        assertEquals(1, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        
        assertEquals("switch (a) {\n"
                   + "case 0:  System.out.println(a);\n"
                   + "b=a;\n"
                   + "break;\n"
                   + "case 1:System.out.println(a);\n"
                   + "a++;\n"
                   + "break;\n"
                   + "case 2:System.out.println(a);\n"
                   + "break;\n"
                   + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testSwitchStatement2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test06", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "SwitchStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof SwitchStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.switchSt);
        
        assertEquals(1, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        
        assertEquals("switch (a) {\n"
                   + "case 0:  b++;\n"
                   + "break;\n"
                   + "case 1:b++;\n"
                   + "break;\n"
                   + "case 2:b++;\n"
                   + "break;\n"
                   + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testSwitchStatement3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test06", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "SwitchStatement");
        CFGNode node = nodes.get(1);
        assert (node.getASTNode() instanceof SwitchStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.switchSt);
        
        assertEquals(1, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        
        assertEquals("switch (b) {\n"
                   + "case 0:  a++;\n"
                   + "case 1:a++;\n"
                   + "break;\n"
                   + "case 2:a++;\n"
                   + "break;\n"
                   + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testSwitchStatement4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test37", "m1( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "SwitchStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof SwitchStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.switchSt);
        
        assertEquals(1, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        
        assertEquals("switch (x) {\n"
                    + "case 1:  y=10;\n"
                    + "break;\n"
                    + "default:x=10;\n"
                    + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testSwitchStatement5() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test37", "m2( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "SwitchStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof SwitchStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.switchSt);
        
        assertEquals(1, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        
        assertEquals("switch (x) {\n"
                   + "default:  x=10;\n"
                   + "break;\n"
                   + "case 1:y=10;\n"
                   + "break;\n"
                   + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testSwitchStatement6() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test37", "m3( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "SwitchStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof SwitchStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.switchSt);
        
        assertEquals(1, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        
        assertEquals("switch (x) {\n"
                   + "default:  x=10;\n"
                   + "case 1:y=10;\n"
                   + "break;\n"
                   + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testSwitchSwitchCaseLabel1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test03", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "SwitchCase");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof SwitchCase);
        
        assertTrue(node.getKind() == CFGNode.Kind.switchCase);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 2);
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 7);
        
        assertEquals("case 0:", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testSwitchCaseLabel2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test03", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "SwitchCase");
        CFGNode node = nodes.get(1);
        assert (node.getASTNode() instanceof SwitchCase);
        
        assertTrue(node.getKind() == CFGNode.Kind.switchCase);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 2);
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 7);
        
        assertEquals("case 1:", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testCaseLabel3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test03", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "SwitchCase");
        CFGNode node = nodes.get(2);
        assert (node.getASTNode() instanceof SwitchCase);
        
        assertTrue(node.getKind() == CFGNode.Kind.switchCase);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 2);
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 6);
        
        assertEquals("case 2:", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testSwitchCaseLabel4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test37", "m1( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "SwitchCase");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof SwitchCase);
        
        assertTrue(node.getKind() == CFGNode.Kind.switchCase);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 3);
        
        assertEquals("case 1:", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testSwitchCaseLabel5() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test37", "m1( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "SwitchCase");
        CFGNode node = nodes.get(1);
        assert (node.getASTNode() instanceof SwitchCase);
        
        assertTrue(node.getKind() == CFGNode.Kind.switchDefault);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 2);
        
        assertEquals("default:", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testSwitchCaseLabel6() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test37", "m2( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "SwitchCase");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof SwitchCase);
        
        assertTrue(node.getKind() == CFGNode.Kind.switchDefault);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 3);
        
        assertEquals("default:", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testSwitchCaseLabel7() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test37", "m2( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "SwitchCase");
        CFGNode node = nodes.get(1);
        assert (node.getASTNode() instanceof SwitchCase);
        
        assertTrue(node.getKind() == CFGNode.Kind.switchCase);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 3);
        
        assertEquals("case 1:", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testSwitchCaseLabel8() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test37", "m3( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "SwitchCase");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof SwitchCase);
        
        assertTrue(node.getKind() == CFGNode.Kind.switchDefault);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 2);
        
        assertEquals("default:", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testSwitchCaseLabel9() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test37", "m3( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "SwitchCase");
        CFGNode node = nodes.get(1);
        assert (node.getASTNode() instanceof SwitchCase);
        
        assertTrue(node.getKind() == CFGNode.Kind.switchCase);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 3);
        
        assertEquals("case 1:", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testSwitchBreak1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test03", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "BreakStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof BreakStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.breakSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 14);
        assertEquals(node.getId(), cfg.getFallThroughSuccessor(node).getId() - 1);
        
        assertEquals("break;", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testSwitchBreak2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test37", "m2( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "BreakStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof BreakStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.breakSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 4);
        assertEquals(node.getId(), cfg.getFallThroughSuccessor(node).getId() - 1);
        
        assertEquals("break;", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testForStatement1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test21", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "ForStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof ForStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.forSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 4);
        List<ControlFlow> lc = CFGTestUtil.getLCFlow(cfg);
        assertEquals(node.getId(), lc.get(0).getLoopBack().getId());
        
        CFGNode init = cfg.getNode(node.getId() - 1);
        assertTrue(init.getKind() == CFGNode.Kind.localDeclaration);
        CFGNode update = lc.get(0).getSrcNode();
        assertTrue(update.getKind() == CFGNode.Kind.assignment);
        
        assertEquals("for (int i=0; i < 10; i++) {\n"
                   + "  a++;\n"
                   + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testForStatement2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test21", "m2( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "ForStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof ForStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.forSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 3);
        List<ControlFlow> lc = CFGTestUtil.getLCFlow(cfg);
        assertEquals(node.getId(), lc.get(0).getLoopBack().getId());
        
        CFGNode init = cfg.getNode(node.getId() - 1);
        assertTrue(init.getKind() == CFGNode.Kind.localDeclaration);
        CFGNode update = lc.get(0).getSrcNode();
        assertTrue(update.getKind() == CFGNode.Kind.assignment);
        
        assertEquals("for (; i < 10; i++) {\n"
                   + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testForStatement3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test21", "m3( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "ForStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof ForStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.forSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 6);
        List<ControlFlow> lc = CFGTestUtil.getLCFlow(cfg);
        assertEquals(node.getId(), lc.get(0).getLoopBack().getId());
        
        CFGNode init = cfg.getNode(node.getId() - 1);
        assertTrue(init.getKind() == CFGNode.Kind.localDeclaration);
        CFGNode update = lc.get(0).getSrcNode();
        assertTrue(update.getKind() == CFGNode.Kind.assignment);
        
        assertEquals("for (int i=0; ; i++) {\n"
                   + "  if (i == 10)   break;\n"
                   + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testForStatement4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test21", "m4( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "ForStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof ForStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.forSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId());
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 2);
        List<ControlFlow> lc = CFGTestUtil.getLCFlow(cfg);
        assertEquals(node.getId(), lc.get(0).getLoopBack().getId());
        
        assertEquals("for (int i=0; i < 10; ) {\n"
                   + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testEnhancedForStatement1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test27", "n( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "EnhancedForStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof EnhancedForStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.enhancedForSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 3);
        List<ControlFlow> lc = CFGTestUtil.getLCFlow(cfg);
        assertEquals(node.getId(), lc.get(0).getLoopBack().getId());
        
        assertEquals("for (Integer num : numbers) {\n"
                   + "  sum+=num;\n"
                   + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testEnhancedForStatement2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test50", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "EnhancedForStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof EnhancedForStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.enhancedForSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 2);
        assertEquals(node.getId(), cfg.getFalseSuccessor(node).getId() - 6);
        List<ControlFlow> lc = CFGTestUtil.getLCFlow(cfg);
        assertEquals(node.getId(), lc.get(0).getLoopBack().getId());
        
        assertEquals("for (String str : strings) {\n"
                   + "  int len=str.length();\n"
                   + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testReturnStatementStatement1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test04", "doReturn( int )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "ReturnStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof ReturnStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.returnSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 4);
        assertEquals(node.getId(), cfg.getFallThroughSuccessor(node).getId() - 1);
        
        assertEquals("return in;", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testReturnStatementStatement2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test04", "doReturn( int )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "ReturnStatement");
        CFGNode node = nodes.get(1);
        assert (node.getASTNode() instanceof ReturnStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.returnSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 2);
        assertEquals(node.getId(), cfg.getFallThroughSuccessor(node).getId() - 2);
        
        assertEquals("return 0;", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testReturnStatementStatement3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test11", "doReturn( int )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "ReturnStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof ReturnStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.returnSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 4);
        assertEquals(node.getId(), cfg.getFallThroughSuccessor(node).getId() - 1);
        
        assertEquals("return ++in;", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testReturnStatementStatement4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test11", "doReturn( int )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "ReturnStatement");
        CFGNode node = nodes.get(1);
        assert (node.getASTNode() instanceof ReturnStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.returnSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 2);
        assertEquals(node.getId(), cfg.getFallThroughSuccessor(node).getId() - 2);
        
        assertEquals("return 0;", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testBreakStatement1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test35", "m1( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "BreakStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof BreakStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.breakSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 3);
        assertEquals(node.getId(), cfg.getFallThroughSuccessor(node).getId() - 1);
        
        assertEquals("break;", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testBreakStatement2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test35", "m3( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "BreakStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof BreakStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.breakSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 4);
        assertEquals(node.getId(), cfg.getFallThroughSuccessor(node).getId() - 1);
        
        assertEquals("break LOOP1;", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testContinueStatement1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test35", "m2( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "ContinueStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof ContinueStatement);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() + 3);
        assertEquals(node.getId(), cfg.getFallThroughSuccessor(node).getId() - 1);
        
        assertEquals("continue;", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testContinueStatement2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test35", "m4( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "ContinueStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof ContinueStatement);
        
        cfg.print();
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() + 4);
        assertEquals(node.getId(), cfg.getFallThroughSuccessor(node).getId() - 1);
        
        assertEquals("continue LOOP1;", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testAssertStatement() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test34", "add( int )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "AssertStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof AssertStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.assertSt);
        
        assertEquals(1, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        
        assertEquals("assert x > 0;", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testSynchronizedStatement() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test34", "add( int )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "SynchronizedStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof SynchronizedStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.synchronizedSt);
        
        assertEquals(1, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        
        assertEquals("synchronized (this) {\n"
                   + "  v=v + y;\n"
                   + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testThrowStatement1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test32", "n( int )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "ThrowStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof ThrowStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.throwSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() + 3);
        assertEquals(node.getId(), cfg.getFallThroughSuccessor(node).getId() - 4);
        
        assertEquals("throw new Exception();", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testThrowStatement2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test36", "n( int )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "ThrowStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof ThrowStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.throwSt);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() + 3);
        assertEquals(node.getId(), cfg.getFallThroughSuccessor(node).getId() - 4);
        
        assertEquals("throw new RuntimeException();", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testTryStatement1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test32", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "TryStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof TryStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.trySt);
        
        assertEquals(1, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 2);
        
        assertEquals("try {\n"
                   + "  b=n(a);\n"
                   + "}\n"
                   + " catch (Exception e) {\n"
                   + "  Exception f=e;\n"
                   + "}\n"
                   + " finally {\n"
                   + "  b=a;\n"
                   + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testTryStatement2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test33", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "TryStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof TryStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.trySt);
        
        assertEquals(1, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 2);
        
        assertEquals("try (FileInputStream in=new FileInputStream(\"a.txt\")){\n"
                   + "  int c;\n"
                   + "  while ((c=in.read()) != -1)   ;\n"
                   + "}\n"
                   + " catch (IOException e) {\n"
                   + "  e.printStackTrace();\n"
                   + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testTryStatement3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test36", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "TryStatement");
        CFGNode node = nodes.get(0);
        assert (node.getASTNode() instanceof TryStatement);
        
        assertTrue(node.getKind() == CFGNode.Kind.trySt);
        
        assertEquals(1, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 2);
        
        assertEquals("try {\n"
                   + "  b=n(a);\n"
                   + "}\n"
                   + " catch (RuntimeException e) {\n"
                   + "  Exception f=e;\n"
                   + "}\n"
                   + " finally {\n"
                   + "  b=a;\n"
                   + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testCatchStatement1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test32", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "CatchClause");
        CFGException node = (CFGException)nodes.get(0);
        
        assert (node.getASTNode() instanceof CatchClause);
        
        assertTrue(node.getKind() == CFGNode.Kind.catchClause);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        assertEquals(node.getId(), cfg.getFallThroughSuccessor(node).getId() + 1);
        
        ControlFlow edge = cfg.getFlow(cfg.getNode(node.getId() - 4), node);
        assertTrue(edge.isExceptionCatch());
        
        assertEquals("catch (Exception e) {\n"
                   + "  Exception f=e;\n"
                   + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testCatchStatement2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test33", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "CatchClause");
        CFGException node = (CFGException)nodes.get(0);
        
        assert (node.getASTNode() instanceof CatchClause);
        
        assertTrue(node.getKind() == CFGNode.Kind.catchClause);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 2);
        assertEquals(node.getId(), cfg.getFallThroughSuccessor(node).getId() + 1);
        
        ControlFlow edge = cfg.getFlow(cfg.getNode(node.getId() - 9), node);
        assertTrue(edge.isExceptionCatch());
        
        assertEquals("catch (IOException e) {\n"
                   + "  e.printStackTrace();\n"
                   + "}", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testCatchStatement3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test36", "m( )");
        List<CFGNode> nodes = CFGTestUtil.getNodes(cfg, "CatchClause");
        CFGException node = (CFGException)nodes.get(0);
        
        assert (node.getASTNode() instanceof CatchClause);
        
        assertTrue(node.getKind() == CFGNode.Kind.catchClause);
        
        assertEquals(2, node.getOutgoingEdges().size());
        assertEquals(node.getId(), cfg.getTrueSuccessor(node).getId() - 1);
        assertEquals(node.getId(), cfg.getFallThroughSuccessor(node).getId() + 1);
        
        ControlFlow edge = cfg.getFlow(cfg.getNode(node.getId() - 4), node);
        assertTrue(edge.isExceptionCatch());
        
        assertEquals("catch (RuntimeException e) {\n"
                   + "  Exception f=e;\n"
                   + "}", node.getASTNode().toString().trim());
    }
}
