/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.builder;

import org.jtool.srcmodel.JavaProject;

import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.CFGTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.JVariableReference;
import java.util.List;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;

public class FieldReferenceResolverTest {
    
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
    public void testFindFieldsForCalledMethod1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test46", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        assert node.isMethodCall();
        List<JVariableReference> def_result = node.getDefVariables();
        List<JVariableReference> use_result = node.getUseVariables();
        
        assertEquals("p$0.value", TestUtil.asSortedStrOfReference(def_result));
        assertEquals("p$0.value", TestUtil.asSortedStrOfReference(use_result));
    }
    
    @Test
    public void testFindFieldsForCalledMethod2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test46", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 12);
        assert node.isMethodCall();
        List<JVariableReference> def_result = node.getDefVariables();
        List<JVariableReference> use_result = node.getUseVariables();
        
        assertEquals("", TestUtil.asSortedStrOfReference(def_result));
        assertEquals("p$0.value", TestUtil.asSortedStrOfReference(use_result));
    }
    
    @Test
    public void testFindFieldsForCalledMethod3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test46", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        assert node.isMethodCall();
        List<JVariableReference> def_result = node.getDefVariables();
        List<JVariableReference> use_result = node.getUseVariables();
        
        assertEquals("q$0;q$0.map", TestUtil.asSortedStrOfReference(def_result));
        assertEquals("q$0;q$0.map", TestUtil.asSortedStrOfReference(use_result));
    }
    
    @Test
    public void testFindFieldsForCalledMethod4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test46", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 13);
        assert node.isMethodCall();
        List<JVariableReference> def_result = node.getDefVariables();
        List<JVariableReference> use_result = node.getUseVariables();
        
        assertEquals("", TestUtil.asSortedStrOfReference(def_result));
        assertEquals("q$0;q$0.map", TestUtil.asSortedStrOfReference(use_result));
    }
    
    @Test
    public void testFindFieldsForCalledMethod5() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test46", "m3( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        assert node.isMethodCall();
        List<JVariableReference> def_result = node.getDefVariables();
        List<JVariableReference> use_result = node.getUseVariables();
        
        assertEquals("m$0;m$0$Node.next;m$0$Node.value;m$0.modCount;m$0.size",
                TestUtil.asSortedStrOfReference(def_result));
        assertEquals("$java.lang.String;m$0;m$0$Node.hash;m$0$Node.key;m$0$Node.next;m$0$Node.value;"
                   + "m$0.modCount;m$0.size;m$0.table;m$0.threshold",
                   TestUtil.asSortedStrOfReference(use_result));
    }
    
    @Test
    public void testFindFieldsForCalledMethod6() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test46", "m3( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 11);
        assert node.isMethodCall();
        List<JVariableReference> def_result = node.getDefVariables();
        List<JVariableReference> use_result = node.getUseVariables();
        
        assertEquals("", TestUtil.asSortedStrOfReference(def_result));
        assertEquals("$java.lang.String;m$0;m$0$Node.hash;m$0$Node.key;m$0$Node.next;m$0$Node.value;m$0.table",
                TestUtil.asSortedStrOfReference(use_result));
    }
    
    @Test
    public void testFindFieldsForCalledMethod7() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test46", "m4( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 7);
        assert node.isMethodCall();
        List<JVariableReference> def_result = node.getDefVariables();
        List<JVariableReference> use_result = node.getUseVariables();
        
        assertEquals("java.util.AbstractList.modCount;l$0;l$0.size",
                TestUtil.asSortedStrOfReference(def_result));
        assertEquals("$java.lang.String;java.util.AbstractList.modCount;l$0;l$0.elementData;l$0.size",
                TestUtil.asSortedStrOfReference(use_result));
    }
    
    @Test
    public void testFindFieldsForCalledMethod8() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test46", "m4( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 11);
        assert node.isMethodCall();
        List<JVariableReference> def_result = node.getDefVariables();
        List<JVariableReference> use_result = node.getUseVariables();
        
        assertEquals("", TestUtil.asSortedStrOfReference(def_result));
        assertEquals("l$0;l$0.size", TestUtil.asSortedStrOfReference(use_result));
    }
    
    @Test
    public void testFindFieldsForEnumConstant() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "PriceCode", "CHILDRENS");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 2);
        assert node.isMethodCall();
        List<JVariableReference> def_result = node.getDefVariables();
        List<JVariableReference> use_result = node.getUseVariables();
        
        assertEquals("PriceCode.priceCode", TestUtil.asSortedStrOfReference(def_result));
        assertEquals("PriceCode.priceCode", TestUtil.asSortedStrOfReference(use_result));
    }
    
    @Test
    public void testFindFieldsForReturn1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test46", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 14);
        assert node.isReturn();
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("p$0", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testFindFieldsForReturn2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test46", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 16);
        assert node.isReturn();
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("q$0", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testFindFieldsForReturn3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test46", "m3( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 13);
        assert node.isReturn();
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("m$0", TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testFindFieldsForReturn4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test46", "m4( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 13);
        assert node.isReturn();
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("l$0", TestUtil.asSortedStrOfReference(result));
    }
}
