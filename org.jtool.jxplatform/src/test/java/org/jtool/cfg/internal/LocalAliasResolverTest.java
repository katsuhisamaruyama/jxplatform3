/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.JVariableReference;
import java.util.List;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;

public class LocalAliasResolverTest {
    
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
    public void testResolveFieldAlias1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test42", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        assert node.isLocalDeclaration();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.a;this.a.x;this.b;this.b.x", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveFieldAlias2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test42", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        assert node.isLocalDeclaration();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.a;this.a.x;this.b;this.b.x", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveFieldAlias3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test42", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 1);
        assert node.isLocalDeclaration();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.a.!getX( )", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveFieldAlias4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test42", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        assert node.isReceiver();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.a", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveFieldAlias5() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test42", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 3);
        assert node.isMethodCall();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.a.x;this.b.x", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveFieldAlias6() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test42", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 4);
        assert node.isActualOut();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.a.x;this.b.x", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveFieldAlias7() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test42", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 5);
        assert node.isLocalDeclaration();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.b.!getX( )", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveFieldAlias8() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test42", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        assert node.isReceiver();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.b", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveFieldAlias9() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test42", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        assert node.isMethodCall();
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.a.x;this.b.x", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveFieldAlias10() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test42", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 8);
        assert node.isActualOut();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("this.a.x;this.b.x", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveLocalVariableAlias1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test43", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 8);
        assert node.isLocalDeclaration();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.!getX( )", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveLocalVariableAlias2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test43", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 9);
        assert node.isReceiver();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveLocalVariableAlias3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test43", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 10);
        assert node.isMethodCall();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("P43.!P43( int ).x;a$0.x;b$1.x;c$2.x", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveLocalVariableAlias4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test43", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 12);
        assert node.isLocalDeclaration();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("b$1.!getX( )", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveLocalVariableAlias5() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test43", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 13);
        assert node.isReceiver();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("b$1", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveLocalVariableAlias6() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test43", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 14);
        assert node.isMethodCall();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("P43.!P43( int ).x;a$0.x;b$1.x;c$2.x", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveLocalVariableAlias7() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test43", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 22);
        assert node.isLocalDeclaration();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.!getX( )", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveLocalVariableAlias8() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test43", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 23);
        assert node.isReceiver();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveLocalVariableAlias9() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test43", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 24);
        assert node.isMethodCall();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("P43.!P43( int ).x;a$0.x;d$5.x", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveLocalVariableAlias10() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test43", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 26);
        assert node.isLocalDeclaration();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("b$1.!getX( )", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveLocalVariableAlias11() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test43", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 27);
        assert node.isReceiver();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("b$1", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveLocalVariableAlias12() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test43", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 28);
        assert node.isMethodCall();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("b$1.x;c$2.x", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveLocalVariableAlias13() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test43", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 30);
        assert node.isLocalDeclaration();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("d$5.!getX( )", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveLocalVariableAlias14() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test43", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 31);
        assert node.isReceiver();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("d$5", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveLocalVariableAlias15() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test43", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 32);
        assert node.isMethodCall();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("P43.!P43( int ).x;a$0.x;d$5.x", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveLocalVariableAlias16() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test43", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        assert node.isLocalDeclaration();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.!getX( )", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveLocalVariableAlias17() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test43", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 8);
        assert node.isReceiver();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveLocalVariableAlias18() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test43", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 9);
        assert node.isMethodCall();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("P43.!P43( int ).x;a$0.x;b$1.x", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveLocalVariableAlias19() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test43", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 11);
        assert node.isLocalDeclaration();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("b$1.!getX( )", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveLocalVariableAlias20() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test43", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 12);
        assert node.isReceiver();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("b$1", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveLocalVariableAlias21() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test43", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 13);
        assert node.isMethodCall();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("P43.!P43( int ).x;a$0.x;b$1.x", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveLocalVariableAlias22() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test43", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 21);
        assert node.isLocalDeclaration();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.!getX( )", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveLocalVariableAlias23() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test43", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 22);
        assert node.isReceiver();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveLocalVariableAlias24() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test43", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 23);
        assert node.isMethodCall();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("P43.!P43( int ).x;a$0.x", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveAliasWithFieldAccess1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test44", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        assert node.isLocalDeclaration();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("P44.!P44( ).q;P44.!P44( ).q.y;a$0;a$0.q;a$0.q.y;b$1;b$1.y",
                TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveAliasWithFieldAccess2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test44", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        assert node.isLocalDeclaration();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.q.!getY( )", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveAliasWithFieldAccess3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test44", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 11);
        assert node.isLocalDeclaration();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.q;a$0.q.r;a$0.q.y;b$1;b$1.r;b$1.y",
                TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveAliasWithFieldAccess4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test44", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 12);
        assert node.isLocalDeclaration();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("b$1.!getY( )", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveAliasWithFieldAccess5() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test44", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 13);
        assert node.isReceiver();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("b$1", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveAliasWithFieldAccess6() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test44", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 14);
        assert node.isMethodCall();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.q.y;b$1.y", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveAliasWithMethodCall1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test44", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 9);
        assert node.isLocalDeclaration();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("P44.!P44( ).!getQ( ).y;a$0.!getQ( );a$0.!getQ( ).y",
                TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveAliasWithMethodCall2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test44", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 10);
        assert node.isReceiver();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveAliasWithMethodCall3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test44", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 11);
        assert node.isMethodCall();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("P44.!P44( ).q;a$0.q;this.q.r;this.q.y",
                TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveAliasWithMethodCall4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test44", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 13);
        assert node.isLocalDeclaration();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.!getQ( ).!getY( )", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveAliasWithMethodCall5() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test44", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 14);
        assert node.isReceiver();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.!getQ( )", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveAliasWithMethodCall6() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test44", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 18);
        assert node.isMethodCall();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("P44.!P44( ).!getQ( ).y;a$0.!getQ( ).y",
                TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveAliasWithMethodCall7() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test44", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 20);
        assert node.isLocalDeclaration();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.!getQ( ).r;a$0.!getQ( ).y;b$1;b$1.r;b$1.y",
                TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveAliasWithMethodCall8() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test44", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 21);
        assert node.isLocalDeclaration();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("b$1.!getY( )", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveAliasWithMethodCall9() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test44", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 22);
        assert node.isReceiver();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("b$1", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveAliasWithMethodCall10() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test44", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 23);
        assert node.isMethodCall();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.!getQ( ).y;b$1.y", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveAliasWithMethodCallChain1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test44", "m3( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 9);
        assert node.isLocalDeclaration();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("P44.!P44( ).!getQ( ).!getR( ).y;a$0.!getQ( ).!getR( );a$0.!getQ( ).!getR( ).y",
                TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveAliasWithMethodCallChain2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test44", "m3( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 10);
        assert node.isReceiver();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.!getQ( )", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveAliasWithMethodCallChain3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test44", "m3( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 12);
        assert node.isMethodCall();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("P44.!P44( ).q;a$0.q;this.q.r;this.q.y",
                TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveAliasWithMethodCallChain4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test44", "m3( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 14);
        assert node.isMethodCall();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("P44.!P44( ).!getQ( ).r;P44.!P44( ).!getQ( ).r.r;"
                + "P44.!P44( ).!getQ( ).r.y;a$0.!getQ( ).r;"
                + "a$0.!getQ( ).r.r;a$0.!getQ( ).r.y",
                TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveAliasWithMethodCallChain5() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test44", "m3( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 16);
        assert node.isLocalDeclaration();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.!getQ( ).r;a$0.!getQ( ).y;b$1;b$1.r;b$1.y",
                TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveAliasWithMethodCallChain6() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test44", "m4( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 12);
        assert node.isLocalDeclaration();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("P44.!P44( ).!getQ( ).!getR( ).y;"
                + "a$0.!getQ( ).!getR( );a$0.!getQ( ).!getR( ).y",
                TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveAliasWithMethodCallChain7() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test44", "m4( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 15);
        assert node.isMethodCall();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("P44.!P44( ).q;a$0.q;this.q.r;this.q.y",
                TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveAliasWithMethodCallChain8() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test44", "m4( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 17);
        assert node.isMethodCall();
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("P44.!P44( ).!getQ( ).r;P44.!P44( ).!getQ( ).r.r;P44.!P44( ).!getQ( ).r.y;"
                + "a$0.!getQ( ).r;a$0.!getQ( ).r.r;a$0.!getQ( ).r.y",
                TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveAliasWithMethodCallChain9() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test44", "m4( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 19);
        assert node.isLocalDeclaration();
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("a$0.!getQ( ).!getR( ).r;a$0.!getQ( ).!getR( ).y;b$1;b$1.r;b$1.y",
                TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveRedefinedAlias1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test45", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        assert node.isLocalDeclaration();
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("P45.!P45( ).q;P45.!P45( ).q.y;a$0;a$0.q;a$0.q.y;b$1;b$1.y",
                TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveRedefinedAlias2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test45", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 12);
        assert node.isLocalDeclaration();
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("P45.!P45( ).q;P45.!P45( ).q.y;Q45.!Q45( ).y;a$0;a$0.q;a$0.q.y;c$3;c$3.y",
                TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveRedefinedAlias3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test45", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 6);
        assert node.isLocalDeclaration();
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("P45.!P45( ).q;P45.!P45( ).q.y;a$0;a$0.q;a$0.q.y;b$1;b$1.y",
                TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testResolveRedefinedAlias4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test45", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 12);
        assert node.isLocalDeclaration();
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("P45.!P45( ).q;P45.!P45( ).q.y;a$0;a$0.q;a$0.q.y;c$3;c$3.q;c$3.q.y",
                TestUtil.asSortedStrOfReference(result));
    }
}
