/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.QualifiedName;
import org.jtool.cfg.internal.CFGTestUtil;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CFGFieldEntryTest {
    
    private static JavaProject SliceProject;
    
    @BeforeClass
    public static void setUp() {
        SliceProject = BuilderTestUtil.getProject("Slice");
    }
    
    @Test
    public void testGetQualifiedName_Test101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "p");
        CFGEntry node = cfg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("Test101#p", result.fqn());
    }
    
    @Test
    public void testGetQualifiedName_PriceCode() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "CHILDRENS");
        CFGEntry node = cfg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("PriceCode#CHILDRENS", result.fqn());
    }
    
    @Test
    public void testGetSignarture_Test101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "p");
        CFGEntry node = cfg.getEntryNode();
        String result = node.getSignature();
        
        assertEquals("p", result);
    }
    
    @Test
    public void testGetCFG_Test101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "p");
        CFGEntry node = cfg.getEntryNode();
        CFG result = node.getCFG();
        
        assertEquals(cfg, result);
    }
    
    @Test
    public void testGetField_Test101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "p");
        CFGFieldEntry node = (CFGFieldEntry)cfg.getEntryNode();
        JavaField result = node.getJavaField();
        
        assertEquals("Test101#p", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetField_A127() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "A127", "y");
        CFGFieldEntry node = (CFGFieldEntry)cfg.getEntryNode();
        JavaField result = node.getJavaField();
        
        assertEquals("A127#y", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetField_S135() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S135", "key");
        CFGFieldEntry node = (CFGFieldEntry)cfg.getEntryNode();
        JavaField result = node.getJavaField();
        
        assertEquals("S135#key", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetDeclarationNode_Test101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "p");
        CFGFieldEntry node = (CFGFieldEntry)cfg.getEntryNode();
        CFGStatement result = node.getDeclarationNode();
        
        assertEquals("p=1", result.getASTNode().toString());
    }
    
    @Test
    public void testGetDeclarationNode_A127() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "A127", "y");
        CFGFieldEntry node = (CFGFieldEntry)cfg.getEntryNode();
        CFGStatement result = node.getDeclarationNode();
        
        assertEquals("y=0", result.getASTNode().toString());
    }
    
    @Test
    public void testGetDeclarationNode_S135() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S136", "map");
        CFGFieldEntry node = (CFGFieldEntry)cfg.getEntryNode();
        CFGStatement result = node.getDeclarationNode();
        
        assertEquals("map=new HashMap<>()", result.getASTNode().toString());
    }
    
    @Test
    public void testGetDefField_Test101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "p");
        CFGFieldEntry node = (CFGFieldEntry)cfg.getEntryNode();
        JReference result = node.getDefField();
        
        assertEquals("this.p", result.getReferenceForm());
    }
    
    @Test
    public void testGetDefField_A127() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "A127", "y");
        CFGFieldEntry node = (CFGFieldEntry)cfg.getEntryNode();
        JReference result = node.getDefField();
        
        assertEquals("this.y", result.getReferenceForm());
    }
    
    @Test
    public void testGetDefField_S135() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S136", "map");
        CFGFieldEntry node = (CFGFieldEntry)cfg.getEntryNode();
        JReference result = node.getDefField();
        
        assertEquals("this.map", result.getReferenceForm());
    }
    
    @Test
    public void testUseFields_Test101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "p");
        CFGFieldEntry node = (CFGFieldEntry)cfg.getEntryNode();
        List<JVariableReference> result = node.getUseFields();
        
        assertEquals("this.p", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testUseFields_A127() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "A127", "y");
        CFGFieldEntry node = (CFGFieldEntry)cfg.getEntryNode();
        List<JVariableReference> result = node.getUseFields();
        
        assertEquals("this.y", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testUseFields_S135() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S136", "map");
        CFGFieldEntry node = (CFGFieldEntry)cfg.getEntryNode();
        List<JVariableReference> result = node.getUseFields();
        
        assertEquals("this.map;java.util.HashMap.!HashMap( )", TestUtil.asStrOfReference(result));
    }
}
