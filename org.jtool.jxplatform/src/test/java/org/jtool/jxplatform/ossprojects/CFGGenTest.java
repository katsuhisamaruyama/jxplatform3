/*
 *  Copyright 2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.ossprojects;

import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.builder.ModelBuilderBatch;
import org.jtool.jxplatform.builder.TimeInfo;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Test to build CFGs from open-source Java programs.
 * 
 * @author Katsuhisa Maruyama
 */
public class CFGGenTest {
    
    static void run(String name) {
        String target = SrcModelGenTest.TEST_TARGET_DIR + name;
        ModelBuilderBatch builder = SrcModelGenTest.getModeBuilder(name, target);
        if (builder == null) {
            return;
        }
        
        List<JavaProject> jprojects = builder.build(name, target);
        jprojects.forEach(jproject -> buildCFGs(builder, jproject));
        
        builder.unbuild();
    }
    
    static void buildCFGs(ModelBuilderBatch builder, JavaProject jproject) {
        int size = jproject.getClasses().size();
        System.out.println();
        System.out.println("** Building CCFGs of " + size + " classes in " + jproject.getName());
        
        ZonedDateTime startTime = TimeInfo.getCurrentTime();
        System.out.println(TimeInfo.getFormatedTime(startTime));
        
        int count = 1;
        for (JavaClass jclass : jproject.getClasses()) {
            System.out.print("(" + count + "/" + size + ")");
            System.out.print(" - " + jclass.getQualifiedName());
            count++;
            
            jproject.getCFGStore().generateUnregisteredCCFG(jclass);
            
            System.out.println(" ... Done ");
        }
        
        ZonedDateTime endTime = TimeInfo.getCurrentTime();
        System.out.println(TimeInfo.getFormatedTime(endTime));
        
        jproject.getCFGStore().cleanup();
    }
    
    public static void main(String[] args) {
        run("ant-1.10.12");
        run("guava-31.0.1");
        run("mockito-4.2.0");
    }
}
