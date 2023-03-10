/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel.internal;

import static org.jtool.srcmodel.AllSrcTests.SliceProject;
import static org.jtool.srcmodel.AllSrcTests.TetrisProject;
import org.jtool.srcmodel.JavaField;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import java.util.Set;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;

public class FieldInitializerCollectorTest {
    
    @BeforeClass
    public static void setUp() {
        SliceProject = BuilderTestUtil.getProject("Slice");
        TetrisProject = BuilderTestUtil.getProject("Tetris");
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
