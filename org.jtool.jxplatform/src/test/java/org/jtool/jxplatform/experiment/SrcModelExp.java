/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.experiment;

import org.jtool.jxplatform.builder.ModelBuilderBatch;
import org.jtool.jxplatform.builder.TimeInfo;
import org.jtool.jxplatform.builder.experiment.CommonGenerator;
import java.io.File;
import java.time.ZonedDateTime;

/**
 * Test to build source models from open-source Java programs.
 * 
 * @author Katsuhisa Maruyama
 */
public class SrcModelExp {
    
    /**
     * This directory is available for a test.
     */
    final static String TEST_TARGET_DIR = new File(".").getAbsoluteFile().getParent() + "/test_target/";
    
    /**
     * This directory is available for an experiment.
     * Please change the directory where target projects exist.
     */
    final static String EXP_DIR = "/Users/maru/Work/JxPlatformExpTarget/targetProjects/";
    
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
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        builder.build(name, target);
        ZonedDateTime endTime = TimeInfo.getCurrentTime();
        
        CommonGenerator.printTimeSec(startTime, endTime);
        System.out.println();
        
        builder.unbuild();
    }
    
    public static void main(String[] args) {
        SrcModelExp tester = new SrcModelExp();
        
        tester.run("ExpForPaper");
        
//        tester.run("ant-1.10.12", SrcModelExp.TEST_TARGET_DIR);
//        tester.run("guava-31.0.1", SrcModelExp.TEST_TARGET_DIR);
//        tester.run("mockito-4.2.0", SrcModelExp.TEST_TARGET_DIR);
        
//        tester.run("ant-1.10.12");                      // Ant
//        tester.run("antlr4-4.9.3");                     // Maven
//        tester.run("apollo-2.0.1");                     // Maven
//        tester.run("arthas-3.6.6");                     // Maven
//        tester.run("avro-1.11.0/lang/java");            // Maven
//        tester.run("camel-3.14.0");                     // Maven
//        tester.run("canal-1.1.6");                      // Maven
//        tester.run("checkstyle-9.2");                   // Maven
//        tester.run("commons-bcel-6.5.0");               // Maven
//        tester.run("commons-cli-1.5.0");                // Maven
//        tester.run("commons-codec-1.15");               // Maven
//        tester.run("commons-collections-4.4");          // Maven
//        tester.run("commons-compress-1.21");            // Maven
//        tester.run("commons-csv-1.9.0");                // Maven
//        tester.run("commons-jxpath-1.3");               // Maven
//        tester.run("commons-lang-3.12.0");              // Maven
//        tester.run("commons-math-3.6.1");               // Maven
//        tester.run("dbeaver-22.2.2");                   // Maven
//        tester.run("druid-1.2.13");                     // Maven
//        tester.run("easymock-4.3");                     // Maven
//        tester.run("gson-2.8.9");                       // Maven
//        tester.run("guava-31.0.1");                     // Maven
//        tester.run("gwt-core-1.0.0");                   // Maven
//        tester.run("hadoop-3.3.1");                     // Maven
//        tester.run("HikariCP-5.0.1");                   // Maven
//        tester.run("hsqldb-2.7.0");                     // Simple
//        tester.run("httpclient-5.1.2");                 // Maven
//        tester.run("jackson-core-2.14");                // Maven
//        tester.run("jackson-databind-2.14");            // Maven
//        tester.run("jackson-dataformat-xml-2.14");      // Maven
//        tester.run("jenkins-2.373");                    // Maven
//        tester.run("jfreechart-1.5.2");                 // Maven
//        tester.run("jmh-1.35");                         // Maven
//        tester.run("joda-time-2.10.13");                // Maven
//        tester.run("jsoup-1.14.3");                     // Maven
//        tester.run("junit-4.13.2");                     // Maven
//        tester.run("libgdx-1.11.0");                    // Gradle
//        tester.run("maven-3.8.4");                      // Maven
//        tester.run("mockito-4.2.0");                    // Gradle
//        tester.run("netty-4.1.84");                     // Maven
//        tester.run("pmd-6.42.0");                       // Maven
//        tester.run("proguard-7.1.1");                   // Gradle
//        tester.run("rocketmq-5.0.0");                   // Maven
//        tester.run("RxJava-3.1.5");                     // Gradle
//        tester.run("seata-1.5.2");                      // Maven
//        tester.run("velocity-engine-2.3");              // Maven
//        tester.run("weka-3.8.2/weka");                  // Maven
//        tester.run("worldWindJava-2.2.1");              // Ant
//        tester.run("xxl-job-2.3.1");                    // Maven
//        tester.run("zookeeper-3.7.0");                  // Maven
    }
}
