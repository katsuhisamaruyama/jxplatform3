/*
 *  Copyright 2022-2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.experiment;

import org.jtool.jxplatform.builder.ModelBuilderBatch;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaProject;
import org.jtool.cfg.CCFG;
import java.io.File;
import java.util.List;

/**
 * Test to build PDGs from open-source Java programs.
 * 
 * @author Katsuhisa Maruyama
 */
public class PDGGenTest {
    
    void run(String name) {
        run(name, SrcModelGenTest.EXP_DIR);
    }
    
    void run(String name, String dirPath) {
        String target = dirPath + name;
        
        File dir = new File(target);
        if (!dir.exists()) {
            System.err.println("Not found project " + target);
            return;
        }
        
        ModelBuilderBatch builder = new ModelBuilderBatch(true, true);
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
            count++;
            
            CCFG ccfg = jproject.getCFGStore().generateUnregisteredCCFG(jclass);
            jproject.getPDGStore().generateUnregisteredClDG(jclass, ccfg);
            System.out.println(" - " + jclass.getQualifiedName());
        }
    }
    
    public static void main(String[] args) {
        PDGGenTest tester = new PDGGenTest();
        
        tester.run("ExpForPaper");
        
//        tester.run("ant-1.10.12", SrcModelExp.TEST_TARGET_DIR);
//        tester.run("guava-31.0.1", SrcModelExp.TEST_TARGET_DIR);
//        tester.run("mockito-4.2.0", SrcModelExp.TEST_TARGET_DIR);
        
//        tester.run("ant-1.10.12");
//        tester.run("antlr4-4.9.3");
//        tester.run("apollo-2.0.1");
//        tester.run("arthas-3.6.6");
//        tester.run("commons-bcel-6.5.0");
//        tester.run("commons-cli-1.5.0");
//        tester.run("commons-codec-1.15");
//        tester.run("commons-collections-4.4");
//        tester.run("commons-compress-1.21");
//        tester.run("commons-csv-1.9.0");
//        tester.run("commons-lang-3.12.0");
//        tester.run("easymock-4.3");
//        tester.run("gson-2.8.9");
//        tester.run("guava-31.0.1");
//        tester.run("gwt-core-1.0.0");
//        tester.run("HikariCP-5.0.1");
//        tester.run("httpclient-5.1.2");
//        tester.run("jackson-core-2.14");
//        tester.run("jackson-databind-2.14");
//        tester.run("jackson-dataformat-xml-2.14");
//        tester.run("jenkins-2.373");
//        tester.run("jfreechart-1.5.2");
//        tester.run("jmh-1.35");
//        tester.run("joda-time-2.10.13");
//        tester.run("jsoup-1.14.3");
//        tester.run("junit-4.13.2");
//        tester.run("libgdx-1.11.0");
//        tester.run("maven-3.8.4");
//        tester.run("mockito-4.2.0");
//        tester.run("proguard-7.1.1");
//        tester.run("RxJava-3.1.5");
//        tester.run("worldWindJava-2.2.1");
//        tester.run("xxl-job-2.3.1");
//        tester.run("zookeeper-3.7.0");
    }
}