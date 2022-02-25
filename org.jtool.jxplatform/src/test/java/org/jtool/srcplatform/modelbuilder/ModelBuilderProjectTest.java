/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.modelbuilder;

import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaProject;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Test;
import org.junit.Ignore;

public class ModelBuilderProjectTest {
    
    private final static String testDirInside = new File(".").getAbsoluteFile().getParent() + "/test_target/";
    
    @Test
    public void testSimple() {
        String name = "Simple";
        String target = testDirInside + name + File.separator;
        
        ModelBuilderBatch builder = new ModelBuilderBatch(false);
        List<JavaProject> projects = builder.build(name, target);
        
        builder.unbuild();
    }
    
    @Test
    public void testDrawToolUsingEclipse() {
        String name = "DrawTool";
        String target = testDirInside + name + File.separator;
        
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        List<JavaProject> projects = builder.build(name, target);
        
        builder.unbuild();
    }
    
    @Test
    public void testAnt() {
        String name = "ant-1.10.12";
        String target = testDirInside + name + File.separator;
        
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        List<JavaProject> projects = builder.build(name, target);
        
        builder.unbuild();
    }
    
    @Test
    public void testGuava() {
        String name = "guava-31.0.1";
        String target = testDirInside + name + File.separator;
        
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        List<JavaProject> projects = builder.build(name, target);
        
        builder.unbuild();
    }
    
    @Test
    public void testMockito() {
        String name = "mockito-4.2.0";
        String target = testDirInside + name + File.separator;
        
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        List<JavaProject> projects =  builder.build(name, target);
        
        
        builder.unbuild();
    }
}
