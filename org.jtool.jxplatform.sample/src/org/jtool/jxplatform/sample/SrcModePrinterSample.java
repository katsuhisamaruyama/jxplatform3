/*
 *  Copyright 2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.sample;

import org.jtool.jxplatform.builder.ModelBuilderBatch;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.JavaFile;
import org.jtool.srcmodel.JavaPackage;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaLocalVar;
import static org.jtool.jxplatform.builder.ModelBuilder.br;
import java.io.File;
import java.util.List;

/**
 * Sample code for building a source code model.
 * 
 * @author Katsuhisa Maruyama
 */
public class SrcModePrinterSample {
    
    final static String SAMPLE_PROJECT_DIR = new File(".").getAbsoluteFile().getParent() + "/sample_project";
    
    public static void main(String[] args) {
        String target = args[0];
        
        int index = target.lastIndexOf(br);
        if (index == -1) {
            index = 0;
        }
        String name = target.substring(index);
        run(name, target);
    }
    
    private static void run(String name, String target) {
        ModelBuilderBatch builder = new ModelBuilderBatch();
        builder.analyzeBytecode(true);
        builder.useCache(true);
        builder.setConsoleVisible(true);
        
        List<JavaProject> jprojects = builder.build(name, target);
        
        for (JavaProject jproject : jprojects) {
            System.out.println(jproject.getName());
            
            List<JavaFile> files = jproject.getFiles();
            files.forEach(f -> System.out.println(f.getName()));
            
            List<JavaClass> classes = jproject.getClasses();
            for (JavaClass jclass : classes) {
                System.out.println(jclass.getQualifiedName().fqn());
                
                JavaPackage jpackage = jclass.getPackage();
                System.out.println(jpackage.getName());
                
                List<JavaMethod> methods = jclass.getMethods();
                for (JavaMethod jmethod : methods) {
                    System.out.println(jmethod.getQualifiedName().fqn());
                    
                    List<JavaLocalVar> locals = jmethod.getLocalVariables();
                    locals.forEach(v -> System.out.println(v.getName()));
                    
                    List<JavaLocalVar> params = jmethod.getParameters();
                    params.forEach(v -> System.out.println(v.getName()));
                }
                
                List<JavaField> fields = jclass.getFields();
                fields.forEach(f -> System.out.println(f.getQualifiedName().fqn()));
            }
        }
        
        builder.unbuild();
    }
}
