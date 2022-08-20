/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.JavaField;
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

public class CFGFieldEntryTest {
    
    private static JavaProject SimpleProject;
    private static JavaProject SliceProject;
    private static JavaProject VideoStoreProject;
    
    @BeforeClass
    public static void setUp() {
        SliceProject = BuilderTestUtil.createProject("Slice", "", "");
        VideoStoreProject = BuilderTestUtil.createProject("VideoStore", "/lib/*", "");
        SimpleProject = BuilderTestUtil.createProject("Simple", "", "");
    }
    
    @AfterClass
    public static void tearDown() {
        SliceProject.getModelBuilder().unbuild();
        VideoStoreProject.getModelBuilder().unbuild();
        SimpleProject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testGetQualifiedNameTest101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "p");
        CFGEntry node = cfg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("Test101#p", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNamePriceCode() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "CHILDRENS");
        CFGEntry node = cfg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("PriceCode#CHILDRENS", result.fqn());
    }
    
    @Test
    public void testGetSignartureTest101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "p");
        CFGEntry node = cfg.getEntryNode();
        String result = node.getSignature();
        
        assertEquals("p", result);
    }
    
    @Test
    public void testGetCFGTest101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "p");
        CFGEntry node = cfg.getEntryNode();
        CFG result = node.getCFG();
        
        assertEquals(cfg, result);
    }
    
    @Test
    public void testGetFieldTest101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "p");
        CFGFieldEntry node = (CFGFieldEntry)cfg.getEntryNode();
        JavaField result = node.getJavaField();
        
        assertEquals("Test101#p", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetFieldA127() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "A127", "y");
        CFGFieldEntry node = (CFGFieldEntry)cfg.getEntryNode();
        JavaField result = node.getJavaField();
        
        assertEquals("A127#y", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetFieldS135() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S135", "key");
        CFGFieldEntry node = (CFGFieldEntry)cfg.getEntryNode();
        JavaField result = node.getJavaField();
        
        assertEquals("S135#key", result.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetDeclarationNodeTest101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "p");
        CFGFieldEntry node = (CFGFieldEntry)cfg.getEntryNode();
        CFGStatement result = node.getDeclarationNode();
        
        assertEquals("p=1", result.getASTNode().toString());
    }
    
    @Test
    public void testGetDeclarationNodeA127() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "A127", "y");
        CFGFieldEntry node = (CFGFieldEntry)cfg.getEntryNode();
        CFGStatement result = node.getDeclarationNode();
        
        assertEquals("y=0", result.getASTNode().toString());
    }
    
    @Test
    public void testGetDeclarationNodeS135() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S136", "map");
        CFGFieldEntry node = (CFGFieldEntry)cfg.getEntryNode();
        CFGStatement result = node.getDeclarationNode();
        
        assertEquals("map=new HashMap<>()", result.getASTNode().toString());
    }
    
    @Test
    public void testGetDefField101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "p");
        CFGFieldEntry node = (CFGFieldEntry)cfg.getEntryNode();
        JReference result = node.getDefField();
        
        assertEquals("this.p", result.getReferenceForm());
    }
    
    @Test
    public void testGetDefFieldA127() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "A127", "y");
        CFGFieldEntry node = (CFGFieldEntry)cfg.getEntryNode();
        JReference result = node.getDefField();
        
        assertEquals("this.y", result.getReferenceForm());
    }
    
    @Test
    public void testGetDefFieldS135() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S136", "map");
        CFGFieldEntry node = (CFGFieldEntry)cfg.getEntryNode();
        JReference result = node.getDefField();
        
        assertEquals("this.map", result.getReferenceForm());
    }
    
    @Test
    public void testUseFields101() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "p");
        CFGFieldEntry node = (CFGFieldEntry)cfg.getEntryNode();
        List<JVariableReference> result = node.getUseFields();
        
        assertEquals("this.p", TestUtil.asStrOfReference(result));
    }
    
    @Test
    public void testUseFieldsA127() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "A127", "y");
        CFGFieldEntry node = (CFGFieldEntry)cfg.getEntryNode();
        List<JVariableReference> result = node.getUseFields();
        
        assertEquals("this.y", TestUtil.asStrOfReference(result));
    }
    
    // TODO
    @Test
    public void testUseFieldsS135() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S136", "map");
        CFGFieldEntry node = (CFGFieldEntry)cfg.getEntryNode();
        List<JVariableReference> result = node.getUseFields();
        
        assertEquals("this.map;java.util.HashMap.!HashMap( )", TestUtil.asStrOfReference(result));
    }
}
