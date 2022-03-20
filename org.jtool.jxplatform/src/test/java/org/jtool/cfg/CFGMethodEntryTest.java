/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.QualifiedName;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.CFGTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CFGMethodEntryTest {
    
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
    public void testGetQualifiedNameTest101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGEntry node = cfg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("Test101#m( )", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNamePriceCode() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "getPriceCode( )");
        CFGEntry node = cfg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("PriceCode#getPriceCode( )", result.fqn());
    }
    
    @Test
    public void testGetSignatureTest101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGEntry node = cfg.getEntryNode();
        String result = node.getSignature();
        
        assertEquals("m( )", result);
    }
    
    @Test
    public void testGetCFGTest101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGEntry node = cfg.getEntryNode();
        CFG result = node.getCFG();
        
        assertEquals(cfg, result);
    }
    
    @Test
    public void testGetJavaMethodTest101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        JavaMethod result = node.getJavaMethod();
        
        assertEquals("Test101#m( )", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetJavaMethodTest120() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m0( int int )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        JavaMethod result = node.getJavaMethod();
        
        assertEquals("Test120#m0( int int )", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetJavaMethodTest127() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test127", "Test127( )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        JavaMethod result = node.getJavaMethod();
        
        assertEquals("Test127#Test127( )", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetJavaMethodA127() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "A127", "setY( int )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        JavaMethod result = node.getJavaMethod();
        
        assertEquals("A127#setY( int )", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetFormalInsTest101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        List<CFGParameter> result = node.getFormalIns();
        
        assertEquals("", TestUtil.asStrOfCFGParameter(result));
    }
    
    @Test
    public void testGetFormalInsTest120() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m0( int int )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        List<CFGParameter> result = node.getFormalIns();
        
        assertEquals("a$0;b$1", TestUtil.asStrOfCFGParameter(result));
    }
    
    @Test
    public void testGetFormalInsTest127() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test127", "Test127( )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        List<CFGParameter> result = node.getFormalIns();
        
        assertEquals("", TestUtil.asStrOfCFGParameter(result));
    }
    
    @Test
    public void testGetFormalInsA127() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "A127", "setY( int )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        List<CFGParameter> result = node.getFormalIns();
        
        assertEquals("y$0", TestUtil.asStrOfCFGParameter(result));
    }
    
    @Test
    public void testGetFormalOutsTest101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        List<CFGParameter> result = node.getFormalOuts();
        
        assertEquals("", TestUtil.asStrOfCFGParameter(result));
    }
    
    @Test
    public void testGetFormalOutsTest120() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m0( int int )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        List<CFGParameter> result = node.getFormalOuts();
        
        assertEquals("", TestUtil.asStrOfCFGParameter(result));
    }
    
    @Test
    public void testGetFormalOutsTest127() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test127", "Test127( )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        List<CFGParameter> result = node.getFormalOuts();
        
        assertEquals("", TestUtil.asStrOfCFGParameter(result));
    }
    
    @Test
    public void testGetFormalOutsA127() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "A127", "setY( int )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        List<CFGParameter> result = node.getFormalOuts();
        
        assertEquals("", TestUtil.asStrOfCFGParameter(result));
    }
    
    @Test
    public void testGetFormalOutForReturnTest101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        CFGParameter result = node.getFormalOutForReturn();
        
        assertEquals("$_", result.getUseVariable().getReferenceForm());
    }
    
    @Test
    public void testGetFormalOutForReturnTest120() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m0( int int )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        CFGParameter result = node.getFormalOutForReturn();
        
        assertEquals("$_", result.getUseVariable().getReferenceForm());
    }
    
    @Test
    public void testGetFormalOutForReturnTest127() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test127", "Test127( )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        CFGParameter result = node.getFormalOutForReturn();
        
        assertNull(result);
    }
    
    @Test
    public void testGetFormalOutForReturnA127() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "A127", "setY( int )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        CFGParameter result = node.getFormalOutForReturn();
        
        assertEquals("$_", result.getUseVariable().getReferenceForm());
    }
    
    @Test
    public void testGetExceptionNodesTest101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        List<CFGException> result = node.getExceptionNodes();
        
        assertEquals("", TestUtil.asStrOfCFGException(result));
    }
    
    @Test
    public void testGetExceptionNodesTest122() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "n( int )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        List<CFGException> result = node.getExceptionNodes();
        
        assertEquals("java.lang.Exception", TestUtil.asStrOfCFGException(result));
    }
    
    @Test
    public void testGetExceptionNodesTest123() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test123", "n( int )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        List<CFGException> result = node.getExceptionNodes();
        
        assertEquals("SubException;SubSubException", TestUtil.asStrOfCFGException(result));
    }
}
