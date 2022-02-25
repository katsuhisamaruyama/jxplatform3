/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.exp;

import org.jtool.srcplatform.modelbuilder.ModelBuilderBatch;
import org.jtool.srcplatform.util.TimeInfo;
import java.io.File;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Do experiments to build Java Models.
 * 
 * @author Katsuhisa Maruyama
 */
public class JavaModelBuilderExp {
    
    /**
     * This directory is available in the case of experimental situation.
     */
    private final static String testDirOutside = "/Users/maru/Work/SrcPlatformTestTarget/";
    
    void run(String name) {
        String target = testDirOutside + name + "/";
        
        File dir = new File(target);
        if (!dir.exists()) {
            System.err.println("Not found project " + target);
            return;
        }
        
        ZonedDateTime startTime = TimeInfo.getCurrentTime();
        
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        builder.build(name, target);
        
        ZonedDateTime endTime = TimeInfo.getCurrentTime();
        System.out.println("** Execution time (" + name + ") = " +
                ChronoUnit.MILLIS.between(startTime, endTime) +
                " (" + TimeInfo.getFormatedTime(startTime) + " - " + TimeInfo.getFormatedTime(endTime) + ")");
        
        builder.unbuild();
    }
    
    public static void main(String[] args) {
        JavaModelBuilderExp tester = new JavaModelBuilderExp();
        
        tester.run("ant-1.10.12");                    // Ant
        //tester.run("antlr4-4.9.3");                   // Maven
        //tester.run("avro-1.11.0/lang/java");          // Maven
        //tester.run("camel-3.14.0");                   // Maven
        //tester.run("checkstyle-9.2");                 // Maven
        //tester.run("commons-bcel-6.5.0");             // Maven
        //tester.run("commons-cli-1.5.0");              // Maven
        //tester.run("commons-codec-1.15");             // Maven
        //tester.run("commons-collections-4.4");        // Maven
        //tester.run("commons-compress-1.21");          // Maven
        //tester.run("commons-csv-1.9.0");              // Maven
        //tester.run("commons-jxpath-1.3");             // Maven
        //tester.run("commons-lang-3.12.0");            // Maven
        //tester.run("commons-math-3.6.1");             // Maven
        //tester.run("easymock-4.3");                   // Maven
        //tester.run("gson-2.8.9");                     // Maven
        tester.run("guava-31.0.1");                   // Maven
        //tester.run("gwt-core-1.0.0");                 // Maven
        //tester.run("hadoop-3.3.1");                   // Maven
        //tester.run("hsqldb-2.6.1");                   // Simple
        //tester.run("httpclient-5.1.2");               // Maven
        //tester.run("jackson-core-2.14");              // Maven
        //tester.run("jackson-databind-2.14");          // Maven
        //tester.run("jackson-dataformat-xml-2.14");    // Maven
        //tester.run("jfreechart-1.5.2");               // Maven
        //tester.run("jmh-1.35");                       // Maven
        //tester.run("joda-time-2.10.13");              // Maven
        //tester.run("jsoup-1.14.3");                   // Maven
        //tester.run("junit-4.13.2");                   // Maven
        //tester.run("libgdx-1.10.0");                  // Maven
        //tester.run("maven-3.8.4");                    // Maven
        tester.run("mockito-4.2.0");                  // Gradle
        //tester.run("pmd-6.42.0");                     // Maven
        //tester.run("proguard-7.1.1");                 // Gradle
        //tester.run("spotbugs-4.5.3");                 // Gradle  - Compile errors for com.apple.eawt.Application
        //tester.run("velocity-engine-2.3");            // Maven
        //tester.run("weka-3.8.2/weka");                // Maven
        //tester.run("worldWindJava-2.2.0");            // Ant
        //tester.run("zookeeper-3.7.0");                // Maven
    }
}
