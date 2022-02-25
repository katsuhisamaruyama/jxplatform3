/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.cfg.CCFG;
import org.jtool.pdg.ClDG;
import org.jtool.pdg.PDG;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcplatform.modelbuilder.ModelBuilderBatch;
import org.jtool.srcplatform.util.TimeInfo;
import java.io.File;
import java.util.List;
import org.junit.Test;
import org.junit.Ignore;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Tests a class that builds a PDG.
 * 
 * @author Katsuhisa Maruyama
 */
public class PDGModelBuilderTest {
    
    private final static String testDirInside = new File(".").getAbsoluteFile().getParent() + "/test_target/";
    
    private final static String testDirOutside = "/Users/maru/Work/SrcPlatformTestTarget/";
    
    
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
        jproject.flushCache();
        return ccfgs;
    }
    
    private ClDG[] buildPDGsForTest(ModelBuilderBatch builder, JavaProject jproject) {
        int size = jproject.getClasses().size();
        ClDG[] cldgs = new ClDG[size];
        System.out.println();
        System.out.println("** Building ClDGs of " + size + " classes in " + jproject.getName());
        
        int count = 1;
        for (JavaClass jclass : jproject.getClasses()) {
            System.out.print("(" + count + "/" + size + ")");
            cldgs[count - 1] = builder.getClDG(jclass);
            System.out.print(" - " + jclass.getQualifiedName() + " - ClDG\n");
            count++;
        }
        return cldgs;
    }
    
    private ClDG buildPDGForTest(ModelBuilderBatch builder, JavaProject jproject, String className) {
        ClDG cldg = null;
        System.out.println();
        System.out.println("** Building ClDG of " + className + " in " + jproject.getName());
        
        for (JavaClass jclass : jproject.getClasses()) {
            if (jclass.getQualifiedName().getClassName().equals(className)) {
                cldg = builder.getClDG(jclass);
                System.out.print(" - " + jclass.getQualifiedName() + " - ClDG\n");
            }
        }
        return cldg;
    }
    
    private boolean checkDetails(ClDG[] ccldgs) {
        for (ClDG cldg : ccldgs) {
            StringBuilder buf = new StringBuilder();
            buf.append(cldg.getQualifiedName());
            buf.append("\n");
            for (PDG pdg : cldg.getPDGs()) {
                buf.append(pdg.toString());
            }
            System.out.println(buf.toString());
        }
        return true;
    }
    
    @Test
    public void testSimple() {
        String target = testDirInside + "Simple/";
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        builder.useProjectCache(true);
        JavaProject jproject = builder.build(target, target, target, target, target);
        
        ClDG[] cldgs = buildPDGsForTest(builder, jproject);
        checkDetails(cldgs);
        builder.unbuild();
    }
    
    @Test
    public void testDrawTool() {
        String target = testDirInside +  "DrawTool/";
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        builder.useProjectCache(true);
        JavaProject jproject = builder.build(target, target, target, target + "/src", target);
        
        buildPDGsForTest(builder, jproject);
        builder.unbuild();
    }
    
    @Test
    public void testLambda() {
        String target = testDirInside +  "Lambda/";
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        builder.useProjectCache(true);
        JavaProject jproject = builder.build(target, target, target, target, target);
        
        buildPDGsForTest(builder, jproject);
        builder.unbuild();
    }
    
    @Ignore
    public void testJrb() {
        String target = testDirInside + "jrb-1.0.2/";
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        builder.useProjectCache(true);
        JavaProject jproject = builder.build(target, target, target + "/src", target + "/src", target);
        
        buildPDGsForTest(builder, jproject);
        builder.unbuild();
    }
    
    @Test
    public void testTetris() {
        String target = testDirInside + "Tetris/";
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        builder.useProjectCache(true);
        JavaProject jproject = builder.build(target, target, target, target, target);
        
        buildPDGsForTest(builder, jproject);
        builder.unbuild();
    }
    
    @Test
    public void testCSClassroom() {
        String target = testDirInside + "CS-classroom/";
        String classpath = target + "lib/*";
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        JavaProject jproject = builder.build(target, target, classpath, target + "/src/", target);
        
        buildPDGsForTest(builder, jproject);
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
        builder.useProjectCache(false);
        List<JavaProject> jprojects = builder.build(name, target);
        
        ZonedDateTime endTime = TimeInfo.getCurrentTime();
        System.out.println("** Execution time (" + name + ") = " +
                ChronoUnit.MILLIS.between(startTime, endTime) +
                " (" + TimeInfo.getFormatedTime(startTime) + " - " + TimeInfo.getFormatedTime(endTime) + ")");
        
        for (JavaProject jproject : jprojects) {
            buildCFGsForTest(builder, jproject);
        }
        
        endTime = TimeInfo.getCurrentTime();
        System.out.println("** Execution time (" + name + ") = " +
                ChronoUnit.MILLIS.between(startTime, endTime) +
                " (" + TimeInfo.getFormatedTime(startTime) + " - " + TimeInfo.getFormatedTime(endTime) + ")");
        
        for (JavaProject jproject : jprojects) {
            buildPDGsForTest(builder, jproject);
        }
        endTime = TimeInfo.getCurrentTime();
        builder.unbuild();
        
        System.out.println("** Execution time (" + name + ") = " +
                ChronoUnit.MILLIS.between(startTime, endTime) +
                " (" + TimeInfo.getFormatedTime(startTime) + " - " + TimeInfo.getFormatedTime(endTime) + ")");
        
        builder.unbuild();
    }
    
    void runForOneClass(String name, String className) {
        String target = testDirOutside + name + "/";
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        builder.useProjectCache(false);
        List<JavaProject> jprojects = builder.build(name, target);
        
        for (JavaProject jproject : jprojects) {
            buildPDGForTest(builder, jproject, className);
        }
        builder.unbuild();
    }
    
    @SuppressWarnings("unused")
    private void run(String target, String classpath, String srcpath, String binpath) {
        String dir = testDirOutside + target + File.separator;
        run0(target, dir + classpath, dir + srcpath, dir + binpath);
    }
    
    private void run0(String target, String classpath, String srcpath, String binpath) {
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        JavaProject jproject = builder.build(target, target, classpath, srcpath, binpath);
        
        buildCFGsForTest(builder, jproject);
        buildPDGsForTest(builder, jproject);
        builder.unbuild();
    }
    
    void initilize() {
    }
    
    public static void main(String[] args) {
        PDGModelBuilderTest tester = new PDGModelBuilderTest();
        tester.initilize();
        
        /* The files are stored inside the workspace */
        //tester.testSimple();
        //tester.testDrawTool();
        //tester.testLambda();
        //tester.testJrb();
        //tester.testTetris(); 
        //tester.testCSClassroom();
        
        /* The files are stored outside the workspace */
        //tester.run("ant-1.10.8");
            //tester.runForOneClass("antlr4-4.8", "org.antlr.v4.unicode.UnicodeData");
        
//tester.run("avro-1.10.2/lang/java/avro");
//tester.run("camel-3.11.2/core/camel-core");
        //tester.run("checkstyle-8.35");
        //tester.run("commons-bcel-6.5.0");
        //tester.run("commons-cli-1.4");
        //tester.run("commons-codec-1.14");
        //tester.run("commons-collections-4.4");
        //tester.run("commons-compress-1.20");
        //tester.run("commons-csv-1.8");
        //tester.run("commons-jxpath-1.3");
        //tester.run("commons-lang-3.10");
        //tester.run("commons-math-3.6.1");
        //tester.run("easymock-4.3");
                                            // tester.run("findbugs-3.0.1");
                                            // tester.run("gson-2.8.7");
//tester.run("guava-30.1/guava");
//tester.run("gwt-core-1.0.0/gwt-core");
//tester.run("hadoop-3.3.1/hadoop-common-projeczt");
                                            // tester.run("hsqldb-2.6.0");
//tester.run("httpclient-5.1/httpclient5");
        //tester.run("jackson-core-2.12");
        //tester.run("jackson-databind-2.12");
///////tester.run("jackson-dataformat-xml-2.12");
        //tester.run("jfreechart-1.5.0");
//tester.run("jmh-1.24/jmh-core");
        //tester.run("joda-time-2.10.6");
        //tester.run("jsoup-1.13.1");
        //tester.run("junit-4.13");
//tester.run("libgdx-1.9.11/gdx");
//tester.run("maven-3.7.0/maven-core");
        //tester.run("mockito-3.3.13");
//tester.run("netty-4.1.68/common");
//tester.run("pmd-6.24.0/pmd-core");
//tester.run("proguard-7.1.1/base");
        //tester.run("snakeyaml-1.30");
//tester.run("spotbugs-4.4.2/spotbugs");
//tester.run("velocity-engine-2.3/velocity-engine-core");
//tester.run("weka-3.8/weka");
        tester.run("worldWindJava-2.2.0");
//tester.run("zookeeper-3.6.3/zookeeper-server");
    }
}
