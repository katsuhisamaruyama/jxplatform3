/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal;

import org.jtool.srcmodel.JavaProject;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGMethodCall;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.JVariableReference;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import java.util.List;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExpressionVisitorTest {
    
    private static JavaProject SimpleProject;
    
    @BeforeClass
    public static void setUp() {
        SimpleProject = BuilderTestUtil.getProject("Simple");
    }
    
    @Test
    public void testAssignment1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test01", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> defs = node.getDefVariables();
        List<JVariableReference> uses = node.getUseVariables();
        
        assertEquals("a$0", defs.get(0).getReferenceForm());
        assertTrue(uses.size() == 0);
        
        assertEquals("a=0", defs.get(0).getASTNode().getParent().toString().trim());
    }
    
    @Test
    public void testAssignment2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test01", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 11);
        List<JVariableReference> defs = node.getDefVariables();
        List<JVariableReference> uses = node.getUseVariables();
        
        assertEquals("a$0", defs.get(0).getReferenceForm());
        assertEquals("b$1", uses.get(0).getReferenceForm());
        
        assertEquals("a=-b", defs.get(0).getASTNode().getParent().toString().trim());
        assertEquals("-b", uses.get(0).getASTNode().getParent().toString().trim());
    }
    
    @Test
    public void testAssignment3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test02", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        List<JVariableReference> defs = node.getDefVariables();
        List<JVariableReference> uses = node.getUseVariables();
        
        assertEquals("c$2", defs.get(0).getReferenceForm());
        assertEquals("a$0", uses.get(0).getReferenceForm());
        
        assertEquals("c=a", defs.get(0).getASTNode().getParent().toString().trim());
        assertEquals("c=a", uses.get(0).getASTNode().getParent().toString().trim());
    }
    
    @Test
    public void testVariableDeclaration1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test01", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> defs = node.getDefVariables();
        List<JVariableReference> uses = node.getUseVariables();
        
        assertEquals("a$0", defs.get(0).getReferenceForm());
        assertTrue(uses.size() == 0);
        
        assertEquals("a", defs.get(0).getASTNode().getParent().toString().trim());
    }
    
    @Test
    public void testVariableDeclaration2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test01", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> defs = node.getDefVariables();
        List<JVariableReference> uses = node.getUseVariables();
        
        assertEquals("b$1", defs.get(0).getReferenceForm());
        assertTrue(uses.size() == 0);
        
        assertEquals("b=0", defs.get(0).getASTNode().getParent().toString().trim());
    }
    
    @Test
    public void testVariableDeclaration3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test15", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> defs = node.getDefVariables();
        List<JVariableReference> uses = node.getUseVariables();
        
        assertEquals("i$1", defs.get(0).getReferenceForm());
        assertTrue(uses.size() == 0);
        
        assertEquals("i=0", defs.get(0).getASTNode().getParent().toString().trim());
    }
    
    @Test
    public void testVariableDeclaration4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test36", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 11);
        List<JVariableReference> defs = node.getDefVariables();
        List<JVariableReference> uses = node.getUseVariables();
        
        assertEquals("f$4", defs.get(0).getReferenceForm());
        assertEquals("e$3", uses.get(0).getReferenceForm());
        
        assertEquals("f=e", defs.get(0).getASTNode().getParent().toString().trim());
        assertEquals("f=e", uses.get(0).getASTNode().getParent().toString().trim());
    }
    
    @Test
    public void testVariableDeclaration5() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test38", "x");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> defs = node.getDefVariables();
        
        assertEquals("this.x", defs.get(0).getReferenceForm());
        
        assertEquals("x", defs.get(0).getASTNode().toString().trim());
    }
    
    @Test
    public void testVariableDeclaration6() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test38", "y");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> defs = node.getDefVariables();
        
        assertEquals("this.y", defs.get(0).getReferenceForm());
        
        assertEquals("y", defs.get(0).getASTNode().toString().trim());
    }
    
    @Test
    public void testArrayAccess1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test38", "m( int[] )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> defs = node.getDefVariables();
        List<JVariableReference> uses = node.getUseVariables();
        
        assertEquals("a$0", defs.get(0).getReferenceForm());
        assertEquals("i$1", uses.get(0).getReferenceForm());
        
        assertEquals("a[i]", defs.get(0).getASTNode().getParent().toString().trim());
        assertEquals("a[i]", uses.get(0).getASTNode().getParent().toString().trim());
    }
    
    @Test
    public void testArrayAccess2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test38", "m( int[] )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        List<JVariableReference> uses = node.getUseVariables();
        
        assertEquals("a$0", uses.get(0).getReferenceForm());
        assertEquals("j$2", uses.get(1).getReferenceForm());
        
        assertEquals("a[j]", uses.get(0).getASTNode().getParent().toString().trim());
    }
    
    @Test
    public void testArrayCreation() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test38", "n( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> defs = node.getDefVariables();
        
        assertEquals("str$0", defs.get(0).getReferenceForm());
        
        assertEquals("str", defs.get(0).getASTNode().toString().trim());
    }
    
    @Test
    public void testPrefixExpression1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test01", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 11);
        List<JVariableReference> uses = node.getUseVariables();
        
        assertEquals("b$1", uses.get(0).getReferenceForm());
        
        assertEquals("-b", uses.get(0).getASTNode().getParent().toString().trim());
    }
    
    @Test
    public void testPrefixExpression2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test11", "doReturn( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> defs = node.getDefVariables();
        List<JVariableReference> uses = node.getUseVariables();
        
        assertEquals("in$0", defs.get(1).getReferenceForm());
        assertEquals("in$0", uses.get(0).getReferenceForm());
        
        assertEquals("++in", defs.get(1).getASTNode().getParent().toString().trim());
        assertEquals("++in", uses.get(0).getASTNode().getParent().toString().trim());
    }
    
    @Test
    public void testPostfixExpression1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test11", "doReturn( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        List<JVariableReference> defs = node.getDefVariables();
        List<JVariableReference> uses = node.getUseVariables();
        
        assertEquals("in$0", defs.get(0).getReferenceForm());
        assertEquals("in$0", uses.get(0).getReferenceForm());
        
        assertEquals("in++", defs.get(0).getASTNode().getParent().toString().trim());
        assertEquals("in++", uses.get(0).getASTNode().getParent().toString().trim());
    }
    
    @Test
    public void testPostfixExpression2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test18", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 13);
        List<JVariableReference> defs = node.getDefVariables();
        List<JVariableReference> uses = node.getUseVariables();
        
        assertEquals("i$1", defs.get(0).getReferenceForm());
        assertEquals("i$1", uses.get(0).getReferenceForm());
        
        assertEquals("i++", defs.get(0).getASTNode().getParent().toString().trim());
        assertEquals("i++", uses.get(0).getASTNode().getParent().toString().trim());
    }
    
    @Test
    public void testPostfixExpression3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test38", "m( int[] )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        List<JVariableReference> defs = node.getDefVariables();
        List<JVariableReference> uses = node.getUseVariables();
        
        assertEquals("i$1", defs.get(1).getReferenceForm());
        assertEquals("i$1", uses.get(0).getReferenceForm());
        
        assertEquals("i++", defs.get(1).getASTNode().getParent().toString().trim());
        assertEquals("i++", uses.get(0).getASTNode().getParent().toString().trim());
    }
    
    @Test
    public void testInfixExpression1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test01", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> uses = node.getUseVariables();
        
        assertEquals("a$0", uses.get(0).getReferenceForm());
        
        assertEquals("a == 0", uses.get(0).getASTNode().getParent().toString().trim());
    }
    
    @Test
    public void testInfixExpression2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test05", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> uses = node.getUseVariables();
        
        assertEquals("a$0", uses.get(0).getReferenceForm());
        assertEquals("b$1", uses.get(1).getReferenceForm());
        assertEquals("c$2", uses.get(2).getReferenceForm());
        
        assertEquals("a", uses.get(0).getASTNode().toString().trim());
        assertEquals("b", uses.get(1).getASTNode().toString().trim());
        assertEquals("c", uses.get(2).getASTNode().toString().trim());
    }
    
    @Test
    public void testFieldAccess() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "P31", "P31( int int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 8);
        List<JVariableReference> defs = node.getDefVariables();
        
        assertEquals("this.x", defs.get(0).getReferenceForm());
        
        assertEquals("this.x", defs.get(0).getASTNode().toString().trim());
    }
    
    @Test
    public void testSuperFieldAccess() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "P31", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> uses = node.getUseVariables();
        
        assertEquals("super.x", uses.get(0).getReferenceForm());
        
        assertEquals("super.x", uses.get(0).getASTNode().toString().trim());
    }
    
    @Test
    public void testThisExpression1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "P31", "n( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> uses = node.getUseVariables();
        
        assertEquals("this", uses.get(0).getReferenceForm());
        
        assertEquals("this", uses.get(0).getASTNode().toString().trim());
    }
    
    @Test
    public void testThisExpression2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test34", "add( int )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> uses = node.getUseVariables();
        
        assertEquals("this", uses.get(0).getReferenceForm());
        
        assertEquals("this", uses.get(0).getASTNode().toString().trim());
    }
    
    @Test
    public void testMethodInvocation1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test01", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 8);
        
        assertEquals("java.io.PrintStream#println( int )", node.getQualifiedName().fqn());
        
        assertEquals("System.out.println(a)", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testMethodInvocation2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test13", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        
        assertEquals("Test13#doReturn( int )", node.getQualifiedName().fqn());
        
        assertEquals("doReturn(a)", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testSuperMethodInvocation() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "P31", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        
        assertEquals("Test31#m( )", node.getQualifiedName().fqn());
        
        assertEquals("super.m()", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testConstructorInvocation() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "P31", "P31( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        
        assertEquals("P31#P31( int )", node.getQualifiedName().fqn());
        
        assertEquals("this(100);", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testSuperConstructorInvocation() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "P31", "P31( int int )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 5);
        
        assertEquals("Test31#Test31( int )", node.getQualifiedName().fqn());
        
        assertEquals("super(x);", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testClassInstanceCreation1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test24", "x( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        
        assertEquals("Test24.P24#P24( )", node.getQualifiedName().fqn());
        
        assertEquals("new P24()", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testClassInstanceCreation2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test27", "n( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 4);
        
        assertEquals("java.util.ArrayList#ArrayList( )", node.getQualifiedName().fqn());
        
        assertEquals("new ArrayList<>()", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testClassInstanceCreation3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test27", "list");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 3);
        
        assertEquals("MockArrayList#MockArrayList( )", node.getQualifiedName().fqn());
        
        assertEquals("new MockArrayList()", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testClassInstanceCreation4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test29", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 7);
        
        assertEquals("javax.swing.AbstractButton#addActionListener( java.awt.event.ActionListener )",
                node.getQualifiedName().fqn());
        
        assertEquals("button.addActionListener(new MyActionListener())",
                node.getASTNode().toString().trim());
    }
    
    @Test
    public void testClassInstanceCreation5() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test29", "m( )");
        CFGMethodCall node = (CFGMethodCall)CFGTestUtil.getNode(cfg, 9);
        
        assertEquals("Test29$1MyActionListener#MyActionListener( )", node.getQualifiedName().fqn());
        
        assertEquals("new MyActionListener()", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testEnumConstantDeclaration1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "PriceCode", "REGULAR");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> defs = node.getDefVariables();
        List<JVariableReference> uses = node.getUseVariables();
        
        assertEquals("PriceCode.REGULAR", defs.get(0).getReferenceForm());
        assertEquals("PriceCode.!PriceCode( int )", uses.get(0).getReferenceForm());
        
        assertEquals("REGULAR(200)", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testEnumConstantDeclaration2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Direction", "UP");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> defs = node.getDefVariables();
        List<JVariableReference> uses = node.getUseVariables();
        
        assertEquals("Direction.UP", defs.get(0).getReferenceForm());
        assertTrue(uses.size() == 0);
        
        assertEquals("UP", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testConditionalExpression() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test49", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        List<JVariableReference> defs = node.getDefVariables();
        List<JVariableReference> uses = node.getUseVariables();
        
        assertEquals("z$3", defs.get(0).getReferenceForm());
        assertEquals("x$2", uses.get(0).getReferenceForm());
        assertEquals("a$0", uses.get(1).getReferenceForm());
        assertEquals("b$1", uses.get(2).getReferenceForm());
        
        assertEquals("z=x > 0 ? a : b", node.getASTNode().toString().trim());
    }
    
    @Test
    public void testSimpleName1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test02", "m( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> defs = node.getDefVariables();
        
        assertEquals("d$3", defs.get(0).getReferenceForm());
        
        assertEquals("d", defs.get(0).getASTNode().toString().trim());
    }
    
    @Test
    public void testSimpleName2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "P44", "getQ( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        List<JVariableReference> uses = node.getUseVariables();
        
        assertEquals("this.q", uses.get(0).getReferenceForm());
        
        assertEquals("q", uses.get(0).getASTNode().toString().trim());
    }
    
    @Test
    public void testQualifiedName1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test44", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        List<JVariableReference> uses = node.getUseVariables();
        
        assertEquals("P44.!P44( ).q;a$0;a$0.q", TestUtil.asSortedStrOfReference(uses));
        
        assertEquals("a.q", uses.get(1).getASTNode().toString().trim());
    }
    
    @Test
    public void testQualifiedName2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test44", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        List<JVariableReference> uses = node.getUseVariables();
        
        assertEquals("P44.!P44( ).q;P44.!P44( ).q.y;a$0;a$0.q;a$0.q.y;b$1;b$1.y", TestUtil.asSortedStrOfReference(uses));
        
        assertEquals("a.q.y", uses.get(2).getASTNode().toString().trim());
    }
    
    @Test
    public void testStringLiteral() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 12);
        List<JVariableReference> uses = node.getUseVariables();
        
        assertEquals("$java.lang.String", uses.get(0).getReferenceForm());
        
        assertEquals("\"xyz\"", uses.get(0).getASTNode().toString().trim());
    }
    
    @Test
    public void testTypeLiteral() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test47", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 12);
        List<JVariableReference> uses = node.getUseVariables();
        
        assertEquals("$java.lang.Class", uses.get(0).getReferenceForm());
        
        assertEquals("Test47.class", uses.get(0).getASTNode().toString().trim());
    }
}
