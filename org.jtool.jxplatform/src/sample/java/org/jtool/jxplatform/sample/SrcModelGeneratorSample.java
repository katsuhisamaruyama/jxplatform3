/*
 *  Copyright 2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.sample;

import org.jtool.jxplatform.builder.ModelBuilderBatch;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.JavaFile;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaField;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Sample code for building a source code model.
 * 
 * @author Katsuhisa Maruyama
 */
public class SrcModelGeneratorSample {
    
    final static String SAMPLE_PROJECT_DIR = new File(".").getAbsoluteFile().getParent() + "/test_target/Slice";
    
    public static void main(String[] args) {
        run("sample", SAMPLE_PROJECT_DIR);
    }
    
    private static void run(String name, String target) {
        ModelBuilderBatch builder = new ModelBuilderBatch();
        builder.analyzeBytecode(true);
        builder.useCache(true);
        builder.setConsoleVisible(true);
        
        List<JavaProject> jprojects = builder.build(name, target);
        
        List<JavaFile> files = jprojects.stream().flatMap(p -> p.getFiles().stream()).collect(Collectors.toList());
        List<JavaClass> classes = jprojects.stream().flatMap(p -> p.getClasses().stream()).collect(Collectors.toList());
        List<JavaMethod> methods = classes.stream().flatMap(p -> p.getMethods().stream()).collect(Collectors.toList());
        List<JavaField> fields = classes.stream().flatMap(p -> p.getFields().stream()).collect(Collectors.toList());
        
        System.out.println("#jprojects = " + jprojects.size());
        System.out.println("#files = " + files.size());
        System.out.println("#classes = " + classes.size());
        System.out.println("#methods = " + methods.size());
        System.out.println("#fields = " + fields.size());
        
        builder.unbuild();
    }
}
