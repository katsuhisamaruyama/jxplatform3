/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal;

import org.jtool.srcmodel.JavaProject;

import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CFGStatement;
import org.jtool.cfg.CFGMethodCall;
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
        CFGMethodCall callNode = (CFGMethodCall)CFGTestUtil.getNode(cfg, 7);
        assert callNode.isMethodCall();
        
        CFGStatement node = (CFGStatement)cfg.getTrueSuccessor(callNode);
        assert node.isActualOut();
        List<JVariableReference> def_result = node.getDefVariables();
        List<JVariableReference> use_result = node.getUseVariables();
        
        assertEquals("p$0.value",
                TestUtil.asSortedStrOfReference(def_result));
        assertEquals("",
                TestUtil.asSortedStrOfReference(use_result));
    }
    
    @Test
    public void testFindFieldsForCalledMethod2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test46", "m1( )");
        CFGMethodCall callNode= (CFGMethodCall)CFGTestUtil.getNode(cfg, 12);
        assert callNode.isMethodCall();
        
        CFGStatement node = (CFGStatement)cfg.getTrueSuccessor(callNode);
        assert node.isActualOut();
        List<JVariableReference> def_result = node.getDefVariables();
        List<JVariableReference> use_result = node.getUseVariables();
        
        assertEquals("p$0.!get( )",
                TestUtil.asSortedStrOfReference(def_result));
        assertEquals("P46.!P46( ).value;p$0.value",
                TestUtil.asSortedStrOfReference(use_result));
    }
    
    @Test
    public void testFindFieldsForCalledMethod3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test46", "m2( )");
        CFGMethodCall callNode = (CFGMethodCall)CFGTestUtil.getNode(cfg, 7);
        assert callNode.isMethodCall();
        
        CFGStatement node = (CFGStatement)cfg.getTrueSuccessor(callNode);
        assert node.isActualOut();
        List<JVariableReference> def_result = node.getDefVariables();
        List<JVariableReference> use_result = node.getUseVariables();
        
        assertEquals("q$0.!set( java.lang.String java.lang.String );"
                + "q$0.map.!java.util.HashMap$Node.next;"
                + "q$0.map.!java.util.HashMap$Node.value;"
                + "q$0.map.!java.util.HashMap.modCount;"
                + "q$0.map.!java.util.HashMap.size",
                TestUtil.asSortedStrOfReference(def_result));
        assertEquals("Q46.!Q46( ).map;Q46.!Q46( ).map.!java.util.HashMap$Node.hash;"
                + "Q46.!Q46( ).map.!java.util.HashMap$Node.key;"
                + "Q46.!Q46( ).map.!java.util.HashMap$Node.next;"
                + "Q46.!Q46( ).map.!java.util.HashMap$Node.value;"
                + "Q46.!Q46( ).map.!java.util.HashMap.modCount;"
                + "Q46.!Q46( ).map.!java.util.HashMap.size;"
                + "Q46.!Q46( ).map.!java.util.HashMap.table;"
                + "Q46.!Q46( ).map.!java.util.HashMap.threshold;"
                + "q$0.map;q$0.map.!java.util.HashMap$Node.hash;"
                + "q$0.map.!java.util.HashMap$Node.key;"
                + "q$0.map.!java.util.HashMap$Node.next;"
                + "q$0.map.!java.util.HashMap$Node.value;"
                + "q$0.map.!java.util.HashMap.modCount;"
                + "q$0.map.!java.util.HashMap.size;"
                + "q$0.map.!java.util.HashMap.table;"
                + "q$0.map.!java.util.HashMap.threshold",
                TestUtil.asSortedStrOfReference(use_result));
    }
    
    @Test
    public void testFindFieldsForCalledMethod4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test46", "m2( )");
        CFGMethodCall callNode = (CFGMethodCall)CFGTestUtil.getNode(cfg, 13);
        assert callNode.isMethodCall();
        
        CFGStatement node = (CFGStatement)cfg.getTrueSuccessor(callNode);
        assert node.isActualOut();
        List<JVariableReference> def_result = node.getDefVariables();
        List<JVariableReference> use_result = node.getUseVariables();
        
        assertEquals("q$0.!get( java.lang.String )",
                TestUtil.asSortedStrOfReference(def_result));
        assertEquals("Q46.!Q46( ).map;Q46.!Q46( ).map.!java.util.HashMap$Node.hash;"
                + "Q46.!Q46( ).map.!java.util.HashMap$Node.key;"
                + "Q46.!Q46( ).map.!java.util.HashMap$Node.next;"
                + "Q46.!Q46( ).map.!java.util.HashMap$Node.value;"
                + "Q46.!Q46( ).map.!java.util.HashMap.table;"
                + "q$0.map;q$0.map.!java.util.HashMap$Node.hash;"
                + "q$0.map.!java.util.HashMap$Node.key;"
                + "q$0.map.!java.util.HashMap$Node.next;"
                + "q$0.map.!java.util.HashMap$Node.value;"
                + "q$0.map.!java.util.HashMap.table",
                TestUtil.asSortedStrOfReference(use_result));
    }
    
    @Test
    public void testFindFieldsForCalledMethod5() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test46", "m3( )");
        CFGMethodCall callNode = (CFGMethodCall)CFGTestUtil.getNode(cfg, 7);
        assert callNode.isMethodCall();
        
        CFGStatement node = (CFGStatement)cfg.getTrueSuccessor(callNode);
        assert node.isActualOut();
        List<JVariableReference> def_result = node.getDefVariables();
        List<JVariableReference> use_result = node.getUseVariables();
        
        assertEquals("m$0.!java.util.HashMap$Node.next;"
                + "m$0.!java.util.HashMap$Node.value;"
                + "m$0.!java.util.HashMap.modCount;"
                + "m$0.!java.util.HashMap.size;"
                + "m$0.!put( java.lang.Object java.lang.Object )",
                TestUtil.asSortedStrOfReference(def_result));
        assertEquals("java.util.HashMap.!HashMap( ).!java.util.HashMap$Node.hash;"
                + "java.util.HashMap.!HashMap( ).!java.util.HashMap$Node.key;"
                + "java.util.HashMap.!HashMap( ).!java.util.HashMap$Node.next;"
                + "java.util.HashMap.!HashMap( ).!java.util.HashMap$Node.value;"
                + "java.util.HashMap.!HashMap( ).!java.util.HashMap.modCount;"
                + "java.util.HashMap.!HashMap( ).!java.util.HashMap.size;"
                + "java.util.HashMap.!HashMap( ).!java.util.HashMap.table;"
                + "java.util.HashMap.!HashMap( ).!java.util.HashMap.threshold;"
                + "m$0.!java.util.HashMap$Node.hash;"
                + "m$0.!java.util.HashMap$Node.key;"
                + "m$0.!java.util.HashMap$Node.next;"
                + "m$0.!java.util.HashMap$Node.value;"
                + "m$0.!java.util.HashMap.modCount;"
                + "m$0.!java.util.HashMap.size;"
                + "m$0.!java.util.HashMap.table;"
                + "m$0.!java.util.HashMap.threshold",
                TestUtil.asSortedStrOfReference(use_result));
    }
    
    @Test
    public void testFindFieldsForCalledMethod6() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test46", "m3( )");
        CFGMethodCall callNode = (CFGMethodCall)CFGTestUtil.getNode(cfg, 11);
        assert callNode.isMethodCall();
        
        CFGStatement node = (CFGStatement)cfg.getTrueSuccessor(callNode);
        assert node.isActualOut();
        List<JVariableReference> def_result = node.getDefVariables();
        List<JVariableReference> use_result = node.getUseVariables();
        
        assertEquals("m$0.!get( java.lang.Object )",
                TestUtil.asSortedStrOfReference(def_result));
        assertEquals("java.util.HashMap.!HashMap( ).!java.util.HashMap$Node.hash;"
                + "java.util.HashMap.!HashMap( ).!java.util.HashMap$Node.key;"
                + "java.util.HashMap.!HashMap( ).!java.util.HashMap$Node.next;"
                + "java.util.HashMap.!HashMap( ).!java.util.HashMap$Node.value;"
                + "java.util.HashMap.!HashMap( ).!java.util.HashMap.table;"
                + "m$0.!java.util.HashMap$Node.hash;"
                + "m$0.!java.util.HashMap$Node.key;"
                + "m$0.!java.util.HashMap$Node.next;"
                + "m$0.!java.util.HashMap$Node.value;"
                + "m$0.!java.util.HashMap.table",
                TestUtil.asSortedStrOfReference(use_result));
    }
    
    @Test
    public void testFindFieldsForCalledMethod7() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test46", "m4( )");
        CFGMethodCall callNode = (CFGMethodCall)CFGTestUtil.getNode(cfg, 7);
        assert callNode.isMethodCall();
        
        CFGStatement node = (CFGStatement)cfg.getTrueSuccessor(callNode);
        assert node.isActualOut();
        List<JVariableReference> def_result = node.getDefVariables();
        List<JVariableReference> use_result = node.getUseVariables();
        
        assertEquals("l$0.!add( java.lang.Object );"
                + "l$0.!java.util.ArrayList.modCount;"
                + "l$0.!java.util.ArrayList.size",
                TestUtil.asSortedStrOfReference(def_result));
        assertEquals("java.util.ArrayList.!ArrayList( ).!java.util.ArrayList.elementData;"
                + "java.util.ArrayList.!ArrayList( ).!java.util.ArrayList.modCount;"
                + "java.util.ArrayList.!ArrayList( ).!java.util.ArrayList.size;"
                + "l$0.!java.util.ArrayList.elementData;"
                + "l$0.!java.util.ArrayList.modCount;"
                + "l$0.!java.util.ArrayList.size",
                TestUtil.asSortedStrOfReference(use_result));
    }
    
    @Test
    public void testFindFieldsForCalledMethod8() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test46", "m4( )");
        CFGMethodCall callNode = (CFGMethodCall)CFGTestUtil.getNode(cfg, 11);
        assert callNode.isMethodCall();
        
        CFGStatement node = (CFGStatement)cfg.getTrueSuccessor(callNode);
        assert node.isActualOut();
        List<JVariableReference> def_result = node.getDefVariables();
        List<JVariableReference> use_result = node.getUseVariables();
        
        assertEquals("l$0.!size( )",
                TestUtil.asSortedStrOfReference(def_result));
        assertEquals("java.util.ArrayList.!ArrayList( ).!java.util.ArrayList.size;"
                + "l$0.!java.util.ArrayList.size",
                TestUtil.asSortedStrOfReference(use_result));
    }
    
    @Test
    public void testFindFieldsForEnumConstant() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "PriceCode", "CHILDRENS");
        CFGMethodCall callNode = (CFGMethodCall)CFGTestUtil.getNode(cfg, 2);
        assert callNode.isMethodCall();
        
        CFGStatement node = (CFGStatement)cfg.getTrueSuccessor(callNode);
        assert node.isActualOut();
        List<JVariableReference> def_result = node.getDefVariables();
        List<JVariableReference> use_result = node.getUseVariables();
        
        assertEquals("PriceCode.!PriceCode( int ).priceCode",
                TestUtil.asSortedStrOfReference(def_result));
        assertEquals("",
                TestUtil.asSortedStrOfReference(use_result));
    }
    
    @Test
    public void testFindFieldsForReturn1() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test46", "m1( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 14);
        assert node.isReturn();
        
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("P46.!P46( ).value;p$0;p$0.value",
                TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testFindFieldsForReturn2() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test46", "m2( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 16);
        assert node.isReturn();
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("Q46.!Q46( ).map;"
                + "q$0;"
                + "q$0.map;"
                + "q$0.map.!java.util.HashMap$Node.next;"
                + "q$0.map.!java.util.HashMap$Node.value;"
                + "q$0.map.!java.util.HashMap.modCount;"
                + "q$0.map.!java.util.HashMap.size",
                TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testFindFieldsForReturn3() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test46", "m3( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 13);
        assert node.isReturn();
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("m$0;"
                + "m$0.!java.util.HashMap$Node.next;"
                + "m$0.!java.util.HashMap$Node.value;"
                + "m$0.!java.util.HashMap.modCount;"
                + "m$0.!java.util.HashMap.size",
                TestUtil.asSortedStrOfReference(result));
    }
    
    @Test
    public void testFindFieldsForReturn4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test46", "m4( )");
        CFGStatement node = (CFGStatement)CFGTestUtil.getNode(cfg, 13);
        assert node.isReturn();
        List<JVariableReference> result = node.getUseVariables();
        
        assertEquals("l$0;"
                + "l$0.!java.util.ArrayList.modCount;"
                + "l$0.!java.util.ArrayList.size",
                TestUtil.asSortedStrOfReference(result));
    }
}
