/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.test;

import org.jtool.cfg.CCFG;
import org.jtool.cfg.CFG;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcplatform.modelbuilder.ModelBuilderBatch;
import org.jtool.srcplatform.util.TimeInfo;

import java.io.File;
import java.util.List;
import org.junit.Test;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Tests a class that builds a CFG.
 * 
 * @author Katsuhisa Maruyama
 */
public class CFGModelBuilderTest {
    
    private final static String testDirInside = new File(".").getAbsoluteFile().getParent() + "/test_target/";
    
    private final static String testDirOutside = "/Users/maru/Work/JxPlatformTestTarget/";
    
    private CCFG[] buildCFGsForTest(ModelBuilderBatch builder, JavaProject jproject) {
        int size = jproject.getClasses().size();
        CCFG[] ccfgs = new CCFG[size];
        System.out.println();
        System.out.println("** Building CFGs of " + size + " classes in " + jproject.getName());
        
        int count = 1;
        for (JavaClass jclass : jproject.getClasses()) {
            System.out.print("(" + count + "/" + size + ")");
            ccfgs[count - 1] = builder.getCCFG(jclass);
            System.out.print(" - " + jclass.getQualifiedName() + " - CCFG\n");
            count++;
        }
        return ccfgs;
    }
    
    private boolean checkDetails(CCFG[] ccfgs) {
        for (CCFG ccfg : ccfgs) {
            StringBuilder buf = new StringBuilder();
            buf.append(ccfg.getQualifiedName());
            buf.append("\n");
            for (CFG cfg : ccfg.getCFGs()) {
                buf.append(cfg.toString());
            }
            System.out.println(buf.toString());
        }
        return true;
    }
    
    @Test
    public void testSimpleWithoutBytecodeAnalysis() {
        String target = testDirInside + "Simple/";
        ModelBuilderBatch builder = new ModelBuilderBatch(false);  // without byte-code analysis
        JavaProject jproject = builder.build(target, target, target, target, target);
        
        CCFG[] ccfgs = buildCFGsForTest(builder, jproject);
        checkDetails(ccfgs);
        builder.unbuild();
    }
    
    @Test
    public void testSimpleWithBytecodeAnalysis() {
        String target = testDirInside + "Simple/";
        ModelBuilderBatch builder = new ModelBuilderBatch(true);  // with byte-code analysis
        builder.useProjectCache(true);                            // using the project cache
        JavaProject jproject = builder.build(target, target, target, target, target);
        
        CCFG[] ccfgs = buildCFGsForTest(builder, jproject);
        checkDetails(ccfgs);
        builder.unbuild();
    }
    
    @Test
    public void testDrawTool() {
        String target = testDirInside + "DrawTool/";
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        JavaProject jproject = builder.build(target, target, target + "/src", target + "/src", target);
        
        buildCFGsForTest(builder, jproject);
        builder.unbuild();
    }
    
    @Test
    public void testLambda() {
        String target = testDirInside + "Lambda/";
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        JavaProject jproject = builder.build(target, target, target, target, target);
        
        buildCFGsForTest(builder, jproject);
        builder.unbuild();
    }
    
    @Test
    public void testJrb() {
        String target = testDirInside + "jrb-1.0.2/";
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        JavaProject jproject = builder.build(target, target, target + "/src", target + "/src", target);
        
        buildCFGsForTest(builder, jproject);
        builder.unbuild();
    }
    
    @Test
    public void testTetris() {
        String target = testDirInside + "Tetris/";
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        JavaProject jproject = builder.build(target, target, target, target, target);
        
        buildCFGsForTest(builder, jproject);
        builder.unbuild();
    }
    
    @Test
    public void testCSClassroom() {
        String target = testDirInside + "CS-classroom/";
        String classpath = target + "lib/*";
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        JavaProject jproject = builder.build(target, target, classpath, target + "/src/", target);
        
        buildCFGsForTest(builder, jproject);
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
        List<JavaProject> jprojects = builder.build(name, target);
        for (JavaProject jproject : jprojects) {
            buildCFGsForTest(builder, jproject);
        }
        ZonedDateTime endTime = TimeInfo.getCurrentTime();
        builder.unbuild();
        
        System.out.println("** Execution time (" + name + ") = " +
                ChronoUnit.MILLIS.between(startTime, endTime) +
                " (" + TimeInfo.getFormatedTime(startTime) + " - " + TimeInfo.getFormatedTime(endTime) + ")");
    }
    
    @SuppressWarnings("unused")
    private void run(String target, String classpath, String[] srcpaths, String binpath) {
        String[] classpaths = new String[1];
        classpaths[0] = classpath;
        String[] binpaths = new String[1];
        binpaths[0] = binpath;
        
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        JavaProject jproject = builder.build(target, target, classpaths, srcpaths, binpaths);
        
        ZonedDateTime startTime = ZonedDateTime.now();
        buildCFGsForTest(builder, jproject);
        builder.unbuild();
        ZonedDateTime endTime = ZonedDateTime.now();
        System.out.println("** Execution time (" + target + ") = " +
                ChronoUnit.MILLIS.between(startTime, endTime) +
                " (" + TimeInfo.getFormatedTime(startTime) + " - " + TimeInfo.getFormatedTime(endTime) + ")");
    }
    
    public static void main(String[] args) {
        CFGModelBuilderTest tester = new CFGModelBuilderTest();
        
        /* The files are stored inside the workspace */
        tester.testSimpleWithoutBytecodeAnalysis();
        tester.testSimpleWithBytecodeAnalysis();
        //tester.testDrawTool();
        //tester.testLambda();
        //tester.testJrb();
        //tester.testTetris(); 
        //tester.testCSClassroom();
        
        /* The files are stored outside the workspace */
        //tester.run("ant-1.9.14");
        //tester.run("ant-1.10.8");
        //tester.run("antlr4-4.8");
        //tester.run("commons-bcel-6.5.0");
        //tester.run("commons-cli-1.4");
        //tester.run("commons-codec-1.14");
        //tester.run("commons-collections-4.4");
        //tester.run("commons-compress-1.20");
        //tester.run("commons-csv-1.8");
        //tester.run("commons-jxpath-1.3");
        //tester.run("commons-lang-3.10");
        //tester.run("commons-math-3.6.1");
        //tester.run("jackson-core-2.12");
        //tester.run("jackson-databind-2.12");
        //tester.run("jfreechart-1.5.0");
        //tester.run("joda-time-2.10.6");
        //tester.run("jsoup-1.13.1");
        //tester.run("junit-4.13");
        //tester.run("mockito-3.3.13");
    }
}
