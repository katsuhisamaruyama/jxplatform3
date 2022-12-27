/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel.internal;

import static org.jtool.srcmodel.AllSrcTests.SimpleProject;
import static org.jtool.srcmodel.AllSrcTests.SliceProject;
import static org.jtool.srcmodel.AllSrcTests.TetrisProject;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaLocalVar;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import java.util.Set;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;

public class LocalDeclarationCollectorTest {
    
    @BeforeClass
    public static void setUp() {
        SimpleProject = BuilderTestUtil.getProject("Simple");
        SliceProject = BuilderTestUtil.getProject("Slice");
        TetrisProject = BuilderTestUtil.getProject("Tetris");
    }
    
    @Test
    public void testLocalDeclarationCollector1() {
        JavaMethod jm = TetrisProject.getClass("Tetris").getMethod("keyPressed( java.awt.event.KeyEvent )");
        LocalDeclarationCollector collector = new LocalDeclarationCollector(jm);
        MethodDeclaration node = (MethodDeclaration)jm.getASTNode();
        node.getBody().accept(collector);
        Set<JavaLocalVar> result = collector.getLocalDeclarations();
        
        assertEquals(2, result.size());
        assertEquals("block;keyCode", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testLocalDeclarationCollector2() {
        JavaMethod jm = TetrisProject.getClass("Pit").getMethod("Pit( GameInfo )");
        LocalDeclarationCollector collector = new LocalDeclarationCollector(jm);
        MethodDeclaration node = (MethodDeclaration)jm.getASTNode();
        node.getBody().accept(collector);
        Set<JavaLocalVar> result = collector.getLocalDeclarations();
        
        assertEquals(1, result.size());
        assertEquals("seed", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testLocalDeclarationCollector3() {
        JavaMethod jm = SliceProject.getClass("Customer").getMethod("statement( Order )");
        LocalDeclarationCollector collector = new LocalDeclarationCollector(jm);
        MethodDeclaration node = (MethodDeclaration)jm.getASTNode();
        node.getBody().accept(collector);
        Set<JavaLocalVar> result = collector.getLocalDeclarations();
        
        assertEquals(1, result.size());
        assertEquals("amount", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testLocalDeclarationCollector4() {
        JavaMethod jm = SimpleProject.getClass("Test26").getMethod(".init( )");
        LocalDeclarationCollector collector = new LocalDeclarationCollector(jm);
        Initializer node = (Initializer)jm.getASTNode();
        node.getBody().accept(collector);
        Set<JavaLocalVar> result = collector.getLocalDeclarations();
        
        assertEquals(1, result.size());
        assertEquals("c", TestUtil.asSortedStrOf(result));
    }
}
