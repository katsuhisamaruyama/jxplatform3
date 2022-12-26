/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.QualifiedName;
import org.jtool.cfg.internal.CFGTestUtil;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CFGMethodEntryTest {
    
    private static JavaProject SliceProject;
    
    @BeforeClass
    public static void setUp() {
        SliceProject = BuilderTestUtil.getProject("Slice");
    }
    
    @Test
    public void testGetQualifiedName_Test101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGEntry node = cfg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("Test101#m( )", result.fqn());
    }
    
    @Test
    public void testGetQualifiedName_PriceCode() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "getPriceCode( )");
        CFGEntry node = cfg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("PriceCode#getPriceCode( )", result.fqn());
    }
    
    @Test
    public void testGetSignature_Test101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGEntry node = cfg.getEntryNode();
        String result = node.getSignature();
        
        assertEquals("m( )", result);
    }
    
    @Test
    public void testGetCFG_Test101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGEntry node = cfg.getEntryNode();
        CFG result = node.getCFG();
        
        assertEquals(cfg, result);
    }
    
    @Test
    public void testGetJavaMethod_Test101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        JavaMethod result = node.getJavaMethod();
        
        assertEquals("Test101#m( )", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetJavaMethod_Test120() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m0( int int )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        JavaMethod result = node.getJavaMethod();
        
        assertEquals("Test120#m0( int int )", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetJavaMethod_Test127() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test127", "Test127( )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        JavaMethod result = node.getJavaMethod();
        
        assertEquals("Test127#Test127( )", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetJavaMethod_A127() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "A127", "setY( int )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        JavaMethod result = node.getJavaMethod();
        
        assertEquals("A127#setY( int )", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetFormalIns_Test101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        List<CFGParameter> result = node.getFormalIns();
        
        assertEquals("", TestUtil.asStrOfCFGParameter(result));
    }
    
    @Test
    public void testGetFormalIns_Test120() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m0( int int )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        List<CFGParameter> result = node.getFormalIns();
        
        assertEquals("a$0;b$1", TestUtil.asStrOfCFGParameter(result));
    }
    
    @Test
    public void testGetFormalIns_Test127() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test127", "Test127( )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        List<CFGParameter> result = node.getFormalIns();
        
        assertEquals("", TestUtil.asStrOfCFGParameter(result));
    }
    
    @Test
    public void testGetFormalIns_A127() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "A127", "setY( int )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        List<CFGParameter> result = node.getFormalIns();
        
        assertEquals("y$0", TestUtil.asStrOfCFGParameter(result));
    }
    
    @Test
    public void testGetFormalOut_Test101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        CFGParameter result = node.getFormalOut();
        
        assertEquals("$_", result.getUseVariable().getReferenceForm());
    }
    
    @Test
    public void testGetFormalOut_Test120() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test120", "m0( int int )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        CFGParameter result = node.getFormalOut();
        
        assertEquals("$_", result.getUseVariable().getReferenceForm());
    }
    
    @Test
    public void testGetFormalOut_Test127() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test127", "Test127( )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        CFGParameter result = node.getFormalOut();
        
        assertEquals("$_", result.getUseVariable().getReferenceForm());
    }
    
    @Test
    public void testGetFormalOut_A127() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "A127", "setY( int )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        CFGParameter result = node.getFormalOut();
        
        assertEquals("$_", result.getUseVariable().getReferenceForm());
    }
    
    @Test
    public void testGetExceptionNodes_Test101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        List<CFGException> result = node.getExceptionNodes();
        
        assertEquals("", TestUtil.asStrOfCFGException(result));
    }
    
    @Test
    public void testGetExceptionNodes_Test122() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "n( int )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        List<CFGException> result = node.getExceptionNodes();
        
        assertEquals("java.lang.Exception", TestUtil.asStrOfCFGException(result));
    }
    
    @Test
    public void testGetExceptionNodes_Test123() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test123", "n( int )");
        CFGMethodEntry node = (CFGMethodEntry)cfg.getEntryNode();
        List<CFGException> result = node.getExceptionNodes();
        
        assertEquals("SubException", TestUtil.asStrOfCFGException(result));
    }
}
