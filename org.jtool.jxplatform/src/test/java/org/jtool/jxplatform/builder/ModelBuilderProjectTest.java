/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder;

import org.jtool.jxplatform.builder.ModelBuilderBatch;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.pdg.ClDG;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaProject;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Test;
import org.junit.Ignore;

public class ModelBuilderProjectTest {
    
    @Test
    public void testCSClassroom() {
        String name = "CS-classroom";
        String target = BuilderTestUtil.getTarget(name);
        String classpath = target + "/lib/*";
        
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        JavaProject project = builder.build(target, target, classpath, target + "/src", target);
        
        //assertEquals(38, project.getFiles().size());
        //assertEquals(72, project.getClasses().size());
        
        
        JavaClass jclass = project.getClass("Sample6");
        System.out.println(builder.getClDG(jclass));
        
        //JavaClass bclass = project.getExternalClass("java.util.ArrayList");
        //System.out.println(builder.getClDG(bclass));
        
        builder.unbuild();
    }
    
    @Ignore
    @Test
    public void testSimpleAnalyzingBytecode() {
        String name = "Simple";
        String target = BuilderTestUtil.getTarget(name);
        
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        JavaProject project = builder.build(name, target, target, target, target);
        
        
        
        
        //assertEquals(29, project.getFiles().size());
        //assertEquals(33, project.getClasses().size());
        
        builder.unbuild();
    }
    
    @Ignore
    @Test
    public void testSimpleAnalyzingBytecodeUseBytecodeCache() {
        String name = "Simple";
        String target = BuilderTestUtil.getTarget(name);
        
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        builder.setLogVisible(false);
        JavaProject project = builder.build(name, target, target, target, target);
        
        assertEquals(29, project.getFiles().size());
        assertEquals(33, project.getClasses().size());
        
        builder.unbuild();
    }
}
