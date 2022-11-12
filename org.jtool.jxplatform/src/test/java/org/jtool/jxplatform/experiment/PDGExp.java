/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.experiment;

import org.jtool.jxplatform.builder.ModelBuilderBatch;
import org.jtool.jxplatform.builder.TimeInfo;
import org.jtool.jxplatform.builder.experiment.CommonGenerator;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaProject;
import org.jtool.cfg.CCFG;
import java.io.File;
import java.util.List;
import java.time.ZonedDateTime;

/**
 * Test to build PDGs from open-source Java programs.
 * 
 * @author Katsuhisa Maruyama
 */
public class PDGExp {
    
    void run(String name) {
        run(name, SrcModelExp.EXP_DIR);
    }
    
    void run(String name, String dirPath) {
        String target = dirPath + name;
        
        File dir = new File(target);
        if (!dir.exists()) {
            System.err.println("Not found project " + target);
            return;
        }
        
        ZonedDateTime startTime = TimeInfo.getCurrentTime();
        ModelBuilderBatch builder = new ModelBuilderBatch(true, true);
        List<JavaProject> jprojects = builder.build(name, target);
        jprojects.forEach(jproject -> buildPDGs(builder, jproject));
        ZonedDateTime endTime = TimeInfo.getCurrentTime();
        
        CommonGenerator.printTimeSec(startTime, endTime);
        System.out.println();
        
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
            
            CCFG ccfg = jproject.getCFGStore().generateUnregisteredCCFG(jclass, false);
            jproject.getPDGStore().generateUnregisteredClDG(ccfg);
            System.out.println(" - " + jclass.getQualifiedName());
        }
    }
    
    public static void main(String[] args) {
        PDGExp tester = new PDGExp();
        
        tester.run("ExpForPaper");
        
//        tester.run("ant-1.10.12", SrcModelExp.TEST_TARGET_DIR);
//        tester.run("guava-31.0.1", SrcModelExp.TEST_TARGET_DIR);
//        tester.run("mockito-4.2.0", SrcModelExp.TEST_TARGET_DIR);
        
//        tester.run("ant-1.10.12");
//        tester.run("antlr4-4.9.3/runtime/Java");
//        tester.run("antlr4-4.9.3");
//        tester.run("apollo-2.0.1");
//        tester.run("arthas-3.6.6");
//        tester.run("avro-1.11.0/lang/java");
//        tester.run("camel-3.14.0");
//        tester.run("canal-1.1.6");
//        tester.run("checkstyle-9.2");
//        tester.run("commons-bcel-6.5.0");
//        tester.run("commons-cli-1.5.0");
//        tester.run("commons-codec-1.15");
//        tester.run("commons-collections-4.4");
//        tester.run("commons-compress-1.21");
//        tester.run("commons-csv-1.9.0");
//        tester.run("commons-jxpath-1.3");
//        tester.run("commons-lang-3.12.0");
//        tester.run("commons-math-3.6.1");
//        tester.run("dbeaver-22.2.2");
//        tester.run("druid-1.2.13");
//        tester.run("easymock-4.3");
//        tester.run("gson-2.8.9");
//        tester.run("guava-31.0.1");
//        tester.run("gwt-core-1.0.0");
//        tester.run("hadoop-3.3.1");
//        tester.run("HikariCP-5.0.1");
//        tester.run("hsqldb-2.7.0");
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
//        tester.run("netty-4.1.84");
//        tester.run("pmd-6.42.0");
//        tester.run("proguard-7.1.1");
//        tester.run("rocketmq-5.0.0");
//        tester.run("RxJava-3.1.5");
//        tester.run("seata-1.5.2");
//        tester.run("velocity-engine-2.3");
//        tester.run("weka-3.8.2/weka");
//        tester.run("worldWindJava-2.2.1");
//        tester.run("xxl-job-2.3.1");
//        tester.run("zookeeper-3.7.0");
    }

}