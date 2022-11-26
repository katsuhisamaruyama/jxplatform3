/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel.internal;

import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.JavaField;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import java.util.Set;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;

public class FieldInitializerCollectorTest {
    
    private static JavaProject SliceProject;
    private static JavaProject TetrisProject;
    
    @BeforeClass
    public static void setUp() {
        SliceProject = BuilderTestUtil.createProject("Slice", "", "");
        TetrisProject = BuilderTestUtil.createProject("Tetris", "", "");
    }
    
    @AfterClass
    public static void tearDown() {
        SliceProject.getModelBuilder().unbuild();
        TetrisProject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testFieldInitializerCollector1() {
        JavaField jf = TetrisProject.getClass("BlueBlock").getField("COLOR");
        FieldInitializerCollector collector = new FieldInitializerCollector(TetrisProject);
        jf.getASTNode().accept(collector);
        Set<JavaField> result = collector.getAccessedFields();
        
        assertEquals(1, result.size());
        assertEquals("java.awt.Color#blue", TestUtil.asSortedStrOf(result));
    }
    
    @Test
    public void testFieldInitializerCollector2() {
        JavaField jf = TetrisProject.getClass("Block").getField("tiles");
        FieldInitializerCollector collector = new FieldInitializerCollector(TetrisProject);
        jf.getASTNode().accept(collector);
        Set<JavaField> result = collector.getAccessedFields();
        
        assertEquals(0, result.size());
    }
    
    @Test
    public void testFieldInitializerCollector3() {
        JavaField jf = SliceProject.getClass("Order").getField("rentals");
        FieldInitializerCollector collector = new FieldInitializerCollector(SliceProject);
        jf.getASTNode().accept(collector);
        Set<JavaField> result = collector.getAccessedFields();
        
        assertEquals(0, result.size());
    }
}
