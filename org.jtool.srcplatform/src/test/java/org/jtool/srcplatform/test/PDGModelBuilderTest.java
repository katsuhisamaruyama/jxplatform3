/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.test;

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
        jproject.flushCache();
        return ccfgs;
    }
    
    private ClDG[] buildPDGsForTest(ModelBuilderBatch builder, JavaProject jproject) {
        buildCFGsForTest(builder, jproject);
        
        int size = jproject.getClasses().size();
        ClDG[] cldgs = new ClDG[size];
        System.out.println();
        System.out.println("** Building ClDGs of " + size + " classes in " + jproject.getName());
        
        int count = 1;
        for (JavaClass jclass : jproject.getClasses()) {
            System.out.print("(" + count + "/" + size + ")");
            cldgs[count - 1] = builder.getClDG(jclass);
            System.out.print(" - " + jclass.getQualifiedName() + " - CCFG\n");
            count++;
        }
        return cldgs;
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
        builder.useProjectCache(true);
        
        List<JavaProject> jprojects = builder.build(name, target);
        for (JavaProject jproject : jprojects) {
            buildPDGsForTest(builder, jproject);
        }
        ZonedDateTime endTime = TimeInfo.getCurrentTime();
        builder.unbuild();
        
        System.out.println("** Execution time (" + name + ") = " +
                ChronoUnit.MILLIS.between(startTime, endTime) +
                " (" + TimeInfo.getFormatedTime(startTime) + " - " + TimeInfo.getFormatedTime(endTime) + ")");
    }
    
    @SuppressWarnings("unused")
    private void run(String target, String classpath, String srcpath, String binpath) {
        String dir = testDirOutside + target + File.separator;
        run0(target, dir + classpath, dir + srcpath, dir + binpath);
    }
    
    private void run0(String target, String classpath, String srcpath, String binpath) {
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        JavaProject jproject = builder.build(target, target, classpath, srcpath, binpath);
        
        ZonedDateTime startTime = ZonedDateTime.now();
        buildPDGsForTest(builder, jproject);
        builder.unbuild();
        ZonedDateTime endTime = ZonedDateTime.now();
        System.out.println("** Execution time (" + target + ") = " +
                ChronoUnit.MILLIS.between(startTime, endTime) +
                " (" + TimeInfo.getFormatedTime(startTime) + " - " + TimeInfo.getFormatedTime(endTime) + ")");
    }
    
    public static void main(String[] args) {
        PDGModelBuilderTest tester = new PDGModelBuilderTest();
        
        /* The files are stored inside the workspace */
        tester.testSimple();
        //tester.testDrawTool();
        //tester.testLambda();
        //tester.testJrb();      Not feasible test because this takes long time
        //tester.testTetris(); 
        //tester.testCSClassroom();
    }
}
