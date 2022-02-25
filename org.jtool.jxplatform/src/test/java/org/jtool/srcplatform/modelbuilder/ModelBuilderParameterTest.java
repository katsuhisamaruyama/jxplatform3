/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.modelbuilder;

import org.jtool.srcmodel.JavaProject;
import java.io.File;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ModelBuilderParameterTest {
    
    private final static String testDirInside = new File(".").getAbsoluteFile().getParent() + "/test_target/";
    
    @Test
    public void testSimple1() {
        String name = "Simple";
        String target = testDirInside + name + File.separator;
        
        ModelBuilderBatch builder = new ModelBuilderBatch(false);
        builder.setLogVisible(true);
        JavaProject project = builder.build(name, target, target);
        
        assertEquals(29, project.getFiles().size());
        assertEquals(33, project.getClasses().size());
        
        builder.unbuild();
    }
    
    @Test
    public void testSimple2() {
        String name = "Simple";
        String target = testDirInside + name + File.separator;
        
        ModelBuilderBatch builder = new ModelBuilderBatch(false);
        builder.setLogVisible(false);
        JavaProject project = builder.build(name, target, target, target, target);
        
        assertEquals(29, project.getFiles().size());
        assertEquals(33, project.getClasses().size());
        
        builder.unbuild();
    }
    
    @Test
    public void testSimple3() {
        String name = "Simple";
        String target = testDirInside + name + File.separator;
        String[] path = { target };
        
        ModelBuilderBatch builder = new ModelBuilderBatch(false);
        builder.setLogVisible(false);
        JavaProject project = builder.build(name, target, path, path, path);
        
        assertEquals(29, project.getFiles().size());
        assertEquals(33, project.getClasses().size());
        
        builder.unbuild();
    }
    
    @Test
    public void testDrawTool() {
        String name = "DrawTool";
        String target = testDirInside + name + File.separator;
        
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        builder.setLogVisible(true);
        JavaProject project = builder.build(name, target, target, target + "/src", target);
        
        assertEquals(19, project.getFiles().size());
        assertEquals(35, project.getClasses().size());
        
        builder.unbuild();
    }
    
    @Test
    public void testLambda() {
        String name = "Lambda";
        String target = testDirInside + name + File.separator;
        
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        builder.setLogVisible(true);
        JavaProject project = builder.build(target, target, target, target, target);
        
        assertEquals(10, project.getFiles().size());
        assertEquals(15, project.getClasses().size());
        
        builder.unbuild();
    }
    
    @Test
    public void testJrb() {
        String name = "jrb-1.0.2";
        String target = testDirInside + name + File.separator;
        
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        builder.setLogVisible(true);
        JavaProject project = builder.build(target, target, target, target + "/src", target);
        
        assertEquals(290, project.getFiles().size());
        assertEquals(401, project.getClasses().size());
        
        builder.unbuild();
    }
    
    @Test
    public void testTetris() {
        String name = "Tetris";
        String target = testDirInside + name + File.separator;
        
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        builder.setLogVisible(true);
        JavaProject project = builder.build(target, target, target, target, target);
        
        assertEquals(12, project.getFiles().size());
        assertEquals(12, project.getClasses().size());
        
        builder.unbuild();
    }
    
    @Test
    public void testCSClassroom() {
        String name = "CS-classroom";
        String target = testDirInside + name + File.separator;
        String classpath = target + "lib/*";
        
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        builder.setLogVisible(true);
        JavaProject project = builder.build(target, target, classpath, target + "/src/", target);
        
        assertEquals(38, project.getFiles().size());
        assertEquals(72, project.getClasses().size());
        
        builder.unbuild();
    }
}
