/*
 *  Copyright 2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.ossprojects;

import org.jtool.jxplatform.builder.ModelBuilderBatch;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaProject;
import org.jtool.cfg.CCFG;
import java.util.List;

/**
 * Test to build PDGs from open-source Java programs.
 * 
 * @author Katsuhisa Maruyama
 */
public class PDGGenTest {
    
    static void run(String name) {
        String target = SrcModelGenTest.TEST_TARGET_DIR + name;
        ModelBuilderBatch builder = SrcModelGenTest.getModeBuilder(name, target);
        if (builder == null) {
            return;
        }
        
        List<JavaProject> jprojects = builder.build(name, target);
        jprojects.forEach(jproject -> buildPDGs(builder, jproject));
        
        builder.unbuild();
    }
    
    static void buildPDGs(ModelBuilderBatch builder, JavaProject jproject) {
        int size = jproject.getClasses().size();
        System.out.println();
        System.out.println("** Building ClDGs of " + size + " classes in " + jproject.getName());
        
        int count = 1;
        for (JavaClass jclass : jproject.getClasses()) {
            System.out.print("(" + count + "/" + size + ")");
            System.out.print(" - " + jclass.getQualifiedName());
            count++;
            
            CCFG ccfg = jproject.getCFGStore().generateUnregisteredCCFG(jclass);
            jproject.getPDGStore().generateUnregisteredClDG(jclass, ccfg);
            
            System.out.println(" ... Done ");
        }
    }
    
    public static void main(String[] args) {
        run("ant-1.10.12");
        run("guava-31.0.1");
        run("mockito-4.2.0");
    }
}