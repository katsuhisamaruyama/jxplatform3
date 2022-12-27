/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder;

import org.jtool.srcmodel.JavaProject;
import java.io.File;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ModelBuilderBatchTest {
    
    @Test
    public void testSimple1() {
        String name = "Simple";
        String target = BuilderTestUtil.getTarget(name);
        
        BuilderTestUtil.clearProject();
        ModelBuilderBatch builder = new ModelBuilderBatch(false);
        JavaProject project = builder.build(name, target, target);
        
        assertEquals(55, project.getFiles().size());
        assertEquals(81, project.getClasses().size());
        
        builder.unbuild();
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testSimple2() {
        String name = "Simple";
        String target = BuilderTestUtil.getTarget(name);
        
        BuilderTestUtil.clearProject();
        ModelBuilderBatch builder = new ModelBuilderBatch(false);
        builder.setConsoleVisible(false);
        JavaProject project = builder.build(name, target, target, target, target);
        
        assertEquals(55, project.getFiles().size());
        assertEquals(81, project.getClasses().size());
        
        builder.unbuild();
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testSimple3() {
        String name = "Simple";
        String target = BuilderTestUtil.getTarget(name);
        String[] path = { target };
        
        BuilderTestUtil.clearProject();
        ModelBuilderBatch builder = new ModelBuilderBatch(false);
        JavaProject project = builder.build(name, target, path, path, path);
        
        assertEquals(55, project.getFiles().size());
        assertEquals(81, project.getClasses().size());
        
        builder.unbuild();
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testVideoStore() {
        String name = "VideoStore";
        String target = BuilderTestUtil.getTarget(name);
        String classpath = target + File.separator + "lib" + File.separator + "*";
        
        BuilderTestUtil.clearProject();
        ModelBuilderBatch builder = new ModelBuilderBatch(false);
        JavaProject project = builder.build(target, target, classpath, target, target);
        
        assertEquals(17, project.getFiles().size());
        assertEquals(19, project.getClasses().size());
        
        builder.unbuild();
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testDrawTool() {
        String name = "DrawTool";
        String target = BuilderTestUtil.getTarget(name);
        
        BuilderTestUtil.clearProject();
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        JavaProject project = builder.build(name, target, target, target + File.separator + "src", target);
        
        assertEquals(19, project.getFiles().size());
        assertEquals(35, project.getClasses().size());
        
        builder.unbuild();
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testLambda() {
        String name = "Lambda";
        String target = BuilderTestUtil.getTarget(name);
        
        BuilderTestUtil.clearProject();
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        JavaProject project = builder.build(target, target, target, target, target);
        
        assertEquals(10, project.getFiles().size());
        assertEquals(13, project.getClasses().size());
        
        builder.unbuild();
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testJrb() {
        String name = "jrb-1.0.2";
        String target = BuilderTestUtil.getTarget(name);
        
        BuilderTestUtil.clearProject();
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        JavaProject project = builder.build(target, target, target, target + File.separator + "src", target);
        
        assertEquals(290, project.getFiles().size());
        assertEquals(401, project.getClasses().size());
        
        builder.unbuild();
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testTetris() {
        String name = "Tetris";
        String target = BuilderTestUtil.getTarget(name);
        
        BuilderTestUtil.clearProject();
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        JavaProject project = builder.build(target, target, target, target, target);
        
        assertEquals(12, project.getFiles().size());
        assertEquals(12, project.getClasses().size());
        
        builder.unbuild();
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testCSClassroom() {
        String name = "CS-classroom";
        String target = BuilderTestUtil.getTarget(name);
        String classpath = target + "/lib/*";
        
        BuilderTestUtil.clearProject();
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        JavaProject project = builder.build(target, target, classpath, target + File.separator + "src", target);
        
        assertEquals(38, project.getFiles().size());
        assertEquals(72, project.getClasses().size());
        
        builder.unbuild();
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testSlice() {
        String name = "Slice";
        String target = BuilderTestUtil.getTarget(name);
        
        BuilderTestUtil.clearProject();
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        JavaProject project = builder.build(target, target, target, target, target);
        
        assertEquals(51, project.getFiles().size());
        assertEquals(82, project.getClasses().size());
        
        builder.unbuild();
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testSimple() {
        String name = "Simple";
        String target = BuilderTestUtil.getTarget(name);
        
        BuilderTestUtil.clearProject();
        ModelBuilderBatch builder = new ModelBuilderBatch(false);
        List<JavaProject> projects = builder.build(name, target);
        
        assertEquals(1, projects.size());
        assertEquals(55, projects.get(0).getFiles().size());
        assertEquals(81, projects.get(0).getClasses().size());
        
        builder.unbuild();
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testAnt() {
        String name = "ant-1.10.12";
        String target = BuilderTestUtil.getTarget(name);
        
        BuilderTestUtil.clearProject();
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        List<JavaProject> projects = builder.build(name, target);
        
        assertEquals(1, projects.size());
        assertEquals(1267, projects.get(0).getFiles().size());
        assertEquals(2068, projects.get(0).getClasses().size());
        
        builder.unbuild();
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testGuava() {
        String name = "guava-31.0.1";
        String target = BuilderTestUtil.getTarget(name);
        
        BuilderTestUtil.clearProject();
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        List<JavaProject> projects = builder.build(name, target);
        
        assertEquals(4, projects.size());
        assertEquals(603, projects.get(0).getFiles().size());
        assertEquals(2006, projects.get(0).getClasses().size());
        assertEquals(4, projects.get(1).getFiles().size());
        assertEquals(4, projects.get(1).getClasses().size());
        assertEquals(320, projects.get(2).getFiles().size());
        assertEquals(1015, projects.get(2).getClasses().size());
        assertEquals(489, projects.get(3).getFiles().size());
        assertEquals(3251, projects.get(3).getClasses().size());
        
        builder.unbuild();
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testMockito() {
        String name = "mockito-4.2.0";
        String target = BuilderTestUtil.getTarget(name);
        
        BuilderTestUtil.clearProject();
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        List<JavaProject> projects =  builder.build(name, target);
        
        assertEquals(1, projects.size());
        assertEquals(828, projects.get(0).getFiles().size());
        assertEquals(1865, projects.get(0).getClasses().size());
        
        builder.unbuild();
        BuilderTestUtil.clearProject();
    }
}
