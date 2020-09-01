/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.test;

import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcplatform.modelbuilder.ModelBuilderBatch;
import org.jtool.srcplatform.util.TimeInfo;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.Test;

/**
 * Tests a class that builds a Java Model.
 * 
 * @author Katsuhisa Maruyama
 */
public class JavaModelBuilderTest {
    
    private final static String testDirInside = new File(".").getAbsoluteFile().getParent() + "/test_target/";
    
    private final static String testDirOutside = "/Users/maru/Work/JxPlatformTestTarget/";
    
    @Test
    public void testSimpleWithoutBytecodeAnalysis() {
        String target = testDirInside + "Simple/";
        ModelBuilderBatch builder = new ModelBuilderBatch(false); // without byte-code analysis
        builder.build(target, target, target, target, target);
        builder.unbuild();
    }
    
    @Test
    public void testSimpleWithBytecodeAnalysis() {
        String target = testDirInside + "Simple/";
        ModelBuilderBatch builder = new ModelBuilderBatch(true); // with byte-code analysis
        builder.build(target, target, target, target, target);
        builder.unbuild();
    }
    
    @Test
    public void testDrawTool() {
        String target = testDirInside + "DrawTool/";
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        builder.build(target, target, target, target + "/src", target);
        builder.unbuild();
    }
    
    @Test
    public void testLambda() {
        String target = testDirInside + "Lambda/";
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        builder.build(target, target, target, target, target);
        builder.unbuild();
    }
    
    @Test
    public void testJrb() {
        String target = testDirInside + "jrb-1.0.2/";
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        builder.build(target, target, target, target + "/src", target);
        builder.unbuild();
    }
    
    @Test
    public void testTetris() {
        String target = testDirInside + "Tetris/";
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        builder.build(target, target, target, target, target);
        builder.unbuild();
    }
    
    @Test
    public void testCSClassroom() {
        String target = testDirInside + "CS-classroom/";
        String classpath = target + "lib/*";
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        builder.build(target, target, classpath, target + "/src/", target);
        builder.unbuild();
    }
    
    static void print() {
        String target = testDirInside + "Simple/";
        ModelBuilderBatch builder = new ModelBuilderBatch();
        JavaProject jproject = builder.build(target, target, target, target, target);
        
        for (JavaClass jclass : jproject.getClasses()) {
            System.out.println(jclass.toString());
            for (JavaMethod jmethod : jclass.getMethods()) {
                System.out.println(jmethod.toString());
            }
            for (JavaField jfield : jclass.getFields()) {
                System.out.println(jfield.toString());
            }
        }
        
        builder.unbuild();
    }
    
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
        builder.unbuild();
        
        System.out.println("** Execution time (" + name + ") = " +
                ChronoUnit.MILLIS.between(startTime, endTime) +
                " (" + TimeInfo.getFormatedTime(startTime) + " - " + TimeInfo.getFormatedTime(endTime) + ")");
    }
    
    public static void main(String[] args) {
        JavaModelBuilderTest tester = new JavaModelBuilderTest();
        
        /* The files are stored inside the workspace */
        tester.testSimpleWithoutBytecodeAnalysis();
        tester.testSimpleWithBytecodeAnalysis();
        //tester.testDrawTool();
        //tester.testLambda();
        //tester.testJrb();
        //tester.testTetris();
        //tester.testCSClassroom();
        
        //print();
        
        /* The files are stored outside the workspace */
        //tester.run("ant-1.9.14");                     // Ant
        //tester.run("ant-1.10.8");                     // Ant
        //tester.run("antlr4-4.8");                     // Maven
        //tester.run("commons-bcel-6.5.0");             // Maven
        //tester.run("commons-cli-1.4");                // Maven
        //tester.run("commons-codec-1.14");             // Maven
        //tester.run("commons-collections-4.4");        // Maven
        //tester.run("commons-compress-1.20");          // Maven
        //tester.run("commons-csv-1.8");                // Maven
        //tester.run("commons-jxpath-1.3");             // Maven
        //tester.run("commons-lang-3.10");              // Maven
        //tester.run("commons-math-3.6.1");             // Maven
        //tester.run("jackson-core-2.12");              // Maven
        //tester.run("jackson-databind-2.12");          // Maven
        //tester.run("jfreechart-1.5.0");               // Maven
        //tester.run("joda-time-2.10.6");               // Maven
        //tester.run("jsoup-1.13.1");                   // Maven
        //tester.run("junit-4.13");                     // Maven/Eclipse
        //tester.run("mockito-3.3.13");                 // Gradle
        //tester.run("pmd-6.24.0");                     // Maven
    }
}
