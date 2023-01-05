/*
 *  Copyright 2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder;

import org.jtool.cfg.CCFG;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CallGraph;
import org.jtool.pdg.ClDG;
import org.jtool.pdg.DependencyGraph;
import org.jtool.pdg.PDG;
import org.jtool.pdg.SDG;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaFile;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaProject;
import java.io.File;
import java.util.Set;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ModelBuilderTest {
    
    private static JavaProject project;
    
    @BeforeClass
    public static void setUp() {
        BuilderTestUtil.clearProject();
        project = BuilderTestUtil.getProject("Slice");
    }
    
    @AfterClass
    public static void tearDown() {
        project.getModelBuilder().unbuild();
        BuilderTestUtil.clearProject();
    }
    
    private void createCFGs() {
        clearCFGs();
        for (JavaClass jclass : project.getClasses()) {
            project.getCFGStore().getCCFG(jclass, false);
        }
    }
    
    private void clearCFGs() {
        project.getCFGStore().clear();
    }
    
    private void createPDGs() {
        clearPDGs();
        for (JavaClass jclass : project.getClasses()) {
            project.getPDGStore().getClDG(jclass, false, true);
        }
    }
    
    private void clearPDGs() {
        project.getCFGStore().clear();
        project.getPDGStore().clear();
    }
    
    @Test
    public void testGetUnregisteredJavaFile() {
        ModelBuilder builder = project.getModelBuilder();
        String filepath = project.getPath() + File.separator + "Test101.java";
        JavaFile jfile = project.getFile(filepath);
        JavaFile result = builder.getUnregisteredJavaFile(filepath, jfile.getSource(), project);
        
        assertEquals("Test101.java", result.getName());
    }
    
    @Test
    public void testFindCFG_Test101_1() {
        createCFGs();
        
        ModelBuilder builder = project.getModelBuilder();
        CFG result = builder.findCFG(project, "Test101#m( )");
        
        assertEquals(6, result.getNodes().size());
        assertEquals(5, result.getEdges().size());
    }
    
    @Test
    public void testFindCFG_Test101_2() {
        clearCFGs();
        
        ModelBuilder builder = project.getModelBuilder();
        CFG result = builder.findCFG(project, "Test101#m( )");
        
        assertNull(result);
    }
    
    @Test
    public void testFindCFG_Test101_3() {
        createCFGs();
        
        ModelBuilder builder = project.getModelBuilder();
        CFG result = builder.findCFG(project, "Test101#a");
        
        assertEquals(3, result.getNodes().size());
        assertEquals(2, result.getEdges().size());
    }
    
    @Test
    public void testFindCFG_Test101_4() {
        clearCFGs();
        
        ModelBuilder builder = project.getModelBuilder();
        CFG result = builder.findCFG(project, "Test101#a");
        
        assertNull(result);
    }
    
    @Test
    public void testFindCFG_Test135() {
        createCFGs();
        
        ModelBuilder builder = project.getModelBuilder();
        CFG result = builder.findCFG(project, "Test135#m( )");
        
        assertEquals(32, result.getNodes().size());
        assertEquals(31, result.getEdges().size());
    }
    
    @Test
    public void testFindCFG_S135() {
        createCFGs();
        
        ModelBuilder builder = project.getModelBuilder();
        CFG result = builder.findCFG(project, "S135#set2( java.lang.String java.lang.String )");
        
        assertEquals(7, result.getNodes().size());
        assertEquals(6, result.getEdges().size());
    }
    
    @Test
    public void testFindCFG_Test139() {
        createCFGs();
        
        ModelBuilder builder = project.getModelBuilder();
        CFG result = builder.findCFG(project, "Test139#m( )");
        
        assertEquals(13, result.getNodes().size());
        assertEquals(12, result.getEdges().size());
    }
    
    @Test
    public void testFindCCFG_Test101_1() {
        createCFGs();
        
        ModelBuilder builder = project.getModelBuilder();
        CCFG result = builder.findCCFG(project, "Test101");
        
        assertEquals(5, result.getCFGs().size());
        assertEquals("Test101#Test101( );Test101#a;Test101#m( );Test101#p;Test101#q",
                BuilderTestUtil.asStrOfCCFG(result));
    }
    
    @Test
    public void testFindCCFG_Test101_2() {
        clearCFGs();
        
        ModelBuilder builder = project.getModelBuilder();
        CCFG result = builder.findCCFG(project, "Test101");
        
        assertNull(result);
    }
    
    @Test
    public void testFindCCFG_Test135() {
        createCFGs();
        
        ModelBuilder builder = project.getModelBuilder();
        CCFG result = builder.findCCFG(project, "Test135");
        
        assertEquals(4, result.getCFGs().size());
        assertEquals("Test135#Test135( );Test135#m( );Test135#s1;Test135#s2",
                BuilderTestUtil.asStrOfCCFG(result));
    }
    
    @Test
    public void testFindCCFG_S135() {
        createCFGs();
        
        ModelBuilder builder = project.getModelBuilder();
        CCFG result = builder.findCCFG(project, "S135");
        
        assertEquals(5, result.getCFGs().size());
        assertEquals("S135#S135( );S135#get2( java.lang.String );S135#key;"
                + "S135#set2( java.lang.String java.lang.String );S135#value",
                BuilderTestUtil.asStrOfCCFG(result));
    }
    
    @Test
    public void testFindCCFG_Test139() {
        createCFGs();
        
        ModelBuilder builder = project.getModelBuilder();
        CCFG result = builder.findCCFG(project, "Test139");
        
        assertEquals(2, result.getCFGs().size());
        assertEquals("Test139#Test139( );Test139#m( )",
                BuilderTestUtil.asStrOfCCFG(result));
    }
    
    @Test
    public void testGetCFG_Test101_1() {
        JavaClass jclass = project.getClass("Test101");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        CFG result = builder.getCFG(jmethod, true);
        
        assertEquals(6, result.getNodes().size());
        assertEquals(5, result.getEdges().size());
    }
    
    @Test
    public void testGetCFG_Test101_2() {
        JavaClass jclass = project.getClass("Test101");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        CFG result = builder.getCFG(jmethod, false);
        
        assertEquals(6, result.getNodes().size());
        assertEquals(5, result.getEdges().size());
    }
    
    @Test
    public void testGetCFG_Test101_3() {
        JavaClass jclass = project.getClass("Test101");
        JavaField jfield = jclass.getField("a");
        ModelBuilder builder = project.getModelBuilder();
        CFG result = builder.getCFG(jfield, true);
        
        assertEquals(3, result.getNodes().size());
        assertEquals(2, result.getEdges().size());
    }
    
    @Test
    public void testGetCFG_Test101_4() {
        JavaClass jclass = project.getClass("Test101");
        JavaField jfield = jclass.getField("a");
        ModelBuilder builder = project.getModelBuilder();
        CFG result = builder.getCFG(jfield, false);
        
        assertEquals(3, result.getNodes().size());
        assertEquals(2, result.getEdges().size());
    }
    
    @Test
    public void testGetCFG_Test135_1() {
        JavaClass jclass = project.getClass("Test135");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        CFG result = builder.getCFG(jmethod, true);
        
        assertEquals(32, result.getNodes().size());
        assertEquals(31, result.getEdges().size());
    }
    
    @Test
    public void testGetCFG_Test135_2() {
        JavaClass jclass = project.getClass("Test135");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        CFG result = builder.getCFG(jmethod, false);
        
        assertEquals(32, result.getNodes().size());
        assertEquals(31, result.getEdges().size());
    }
    
    @Test
    public void testGetCFG_S135_1() {
        JavaClass jclass = project.getClass("S135");
        JavaMethod jmethod = jclass.getMethod("set2( java.lang.String java.lang.String )");
        ModelBuilder builder = project.getModelBuilder();
        CFG result = builder.getCFG(jmethod, true);
        
        assertEquals(7, result.getNodes().size());
        assertEquals(6, result.getEdges().size());
    }
    
    @Test
    public void testGetCFG_S135_2() {
        JavaClass jclass = project.getClass("S135");
        JavaMethod jmethod = jclass.getMethod("set2( java.lang.String java.lang.String )");
        ModelBuilder builder = project.getModelBuilder();
        CFG result = builder.getCFG(jmethod, false);
        
        assertEquals(7, result.getNodes().size());
        assertEquals(6, result.getEdges().size());
    }
    
    @Test
    public void testGetCFG_Test139_1() {
        JavaClass jclass = project.getClass("Test139");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        CFG result = builder.getCFG(jmethod, true);
        
        assertEquals(13, result.getNodes().size());
        assertEquals(12, result.getEdges().size());
    }
    
    @Test
    public void testGetCFG_Test139_2() {
        JavaClass jclass = project.getClass("Test139");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        CFG result = builder.getCFG(jmethod, false);
        
        assertEquals(13, result.getNodes().size());
        assertEquals(12, result.getEdges().size());
    }
    
    @Test
    public void testGetCCFG_Test101_1() {
        JavaClass jclass = project.getClass("Test101");
        ModelBuilder builder = project.getModelBuilder();
        CCFG result = builder.getCCFG(jclass, true);
        
        assertEquals(5, result.getCFGs().size());
        assertEquals("Test101#Test101( );Test101#a;Test101#m( );Test101#p;Test101#q",
                BuilderTestUtil.asStrOfCCFG(result));
    }
    
    @Test
    public void testGetCCFG_Test101_2() {
        JavaClass jclass = project.getClass("Test101");
        ModelBuilder builder = project.getModelBuilder();
        CCFG result = builder.getCCFG(jclass, false);
        
        assertEquals(5, result.getCFGs().size());
        assertEquals("Test101#Test101( );Test101#a;Test101#m( );Test101#p;Test101#q",
                BuilderTestUtil.asStrOfCCFG(result));
    }
    
    @Test
    public void testGetCCFG_Test135_1() {
        JavaClass jclass = project.getClass("Test135");
        ModelBuilder builder = project.getModelBuilder();
        CCFG result = builder.getCCFG(jclass, true);
        
        assertEquals(4, result.getCFGs().size());
        assertEquals("Test135#Test135( );Test135#m( );Test135#s1;Test135#s2",
                BuilderTestUtil.asStrOfCCFG(result));
    }
    
    @Test
    public void testGetCCFG_Test135_2() {
        JavaClass jclass = project.getClass("Test135");
        ModelBuilder builder = project.getModelBuilder();
        CCFG result = builder.getCCFG(jclass, false);
        
        assertEquals(4, result.getCFGs().size());
        assertEquals("Test135#Test135( );Test135#m( );Test135#s1;Test135#s2",
                BuilderTestUtil.asStrOfCCFG(result));
    }
    
    @Test
    public void testGetCCFG_S135_1() {
        JavaClass jclass = project.getClass("S135");
        ModelBuilder builder = project.getModelBuilder();
        CCFG result = builder.getCCFG(jclass, true);
        
        assertEquals(5, result.getCFGs().size());
        assertEquals("S135#S135( );S135#get2( java.lang.String );S135#key;"
                + "S135#set2( java.lang.String java.lang.String );S135#value",
                BuilderTestUtil.asStrOfCCFG(result));
    }
    
    @Test
    public void testGetCCFG_S135_2() {
        JavaClass jclass = project.getClass("S135");
        ModelBuilder builder = project.getModelBuilder();
        CCFG result = builder.getCCFG(jclass, false);
        
        assertEquals(5, result.getCFGs().size());
        assertEquals("S135#S135( );S135#get2( java.lang.String );S135#key;"
                + "S135#set2( java.lang.String java.lang.String );S135#value",
                BuilderTestUtil.asStrOfCCFG(result));
    }
    
    @Test
    public void testGetCCFG_Test139_1() {
        JavaClass jclass = project.getClass("Test139");
        ModelBuilder builder = project.getModelBuilder();
        CCFG result = builder.getCCFG(jclass, true);
        
        assertEquals(2, result.getCFGs().size());
        assertEquals("Test139#Test139( );Test139#m( )",
                BuilderTestUtil.asStrOfCCFG(result));
    }
    
    @Test
    public void testGetCCFG_Test139_2() {
        JavaClass jclass = project.getClass("Test139");
        ModelBuilder builder = project.getModelBuilder();
        CCFG result = builder.getCCFG(jclass, false);
        
        assertEquals(2, result.getCFGs().size());
        assertEquals("Test139#Test139( );Test139#m( )",
                BuilderTestUtil.asStrOfCCFG(result));
    }
    
    @Test
    public void testGetCallGraph() {
        ModelBuilder builder = project.getModelBuilder();
        CallGraph result = builder.getCallGraph(project);
        
        assertEquals(162, result.getNodes().size());
        assertEquals(155, result.getEdges().size());
    }
    
    @Test
    public void testFindPDG_Test101_1() {
        createPDGs();
        
        ModelBuilder builder = project.getModelBuilder();
        PDG result = builder.findPDG(project, "Test101#m( )");
        
        assertEquals(5, result.getNodes().size());
        assertEquals(8, result.getEdges().size());
    }
    
    @Test
    public void testFindPDG_Test101_2() {
        clearPDGs();
        
        ModelBuilder builder = project.getModelBuilder();
        PDG result = builder.findPDG(project, "Test101#m( )");
        
        assertNull(result);
    }
    
    @Test
    public void testFindPDG_Test101_3() {
        createPDGs();
        
        ModelBuilder builder = project.getModelBuilder();
        PDG result = builder.findPDG(project, "Test101#a");
        
        assertEquals(2, result.getNodes().size());
        assertEquals(1, result.getEdges().size());
    }
    
    @Test
    public void testFindPDG_Test101_4() {
        clearPDGs();
        
        ModelBuilder builder = project.getModelBuilder();
        PDG result = builder.findPDG(project, "Test101#a");
        
        assertNull(result);
    }
    
    @Test
    public void testFindPDG_Test135() {
        createPDGs();
        
        ModelBuilder builder = project.getModelBuilder();
        PDG result = builder.findPDG(project, "Test135#m( )");
        
        assertEquals(31, result.getNodes().size());
        assertEquals(46, result.getEdges().size());
    }
    
    @Test
    public void testFindPDG_S135() {
        createPDGs();
        
        ModelBuilder builder = project.getModelBuilder();
        PDG result = builder.findPDG(project, "S135#set2( java.lang.String java.lang.String )");
        
        assertEquals(6, result.getNodes().size());
        assertEquals(7, result.getEdges().size());
    }
    
    @Test
    public void testFindPDG_Test139() {
        createPDGs();
        
        ModelBuilder builder = project.getModelBuilder();
        PDG result = builder.findPDG(project, "Test139#m( )");
        
        assertEquals(12, result.getNodes().size());
        assertEquals(13, result.getEdges().size());
    }
    
    @Test
    public void testFindClDG_Test101_1() {
        createPDGs();
        
        ModelBuilder builder = project.getModelBuilder();
        ClDG result = builder.findClDG(project, "Test101");
        
        assertEquals(5, result.getPDGs().size());
        assertEquals("Test101#Test101( );Test101#a;Test101#m( );Test101#p;Test101#q",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testFindClDG_Test101_2() {
        clearPDGs();
        
        ModelBuilder builder = project.getModelBuilder();
        ClDG result = builder.findClDG(project, "Test101");
        
        assertNull(result);
    }
    
    @Test
    public void testFindClDG_Test135() {
        createPDGs();
        
        ModelBuilder builder = project.getModelBuilder();
        ClDG result = builder.findClDG(project, "Test135");
        
        assertEquals(4, result.getPDGs().size());
        assertEquals("Test135#Test135( );Test135#m( );Test135#s1;Test135#s2",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testFindClDG_S135() {
        createPDGs();
        
        ModelBuilder builder = project.getModelBuilder();
        ClDG result = builder.findClDG(project, "S135");
        
        assertEquals(5, result.getPDGs().size());
        assertEquals("S135#S135( );S135#get2( java.lang.String );S135#key;"
                + "S135#set2( java.lang.String java.lang.String );S135#value",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testFindClDG_Test139() {
        createPDGs();
        
        ModelBuilder builder = project.getModelBuilder();
        ClDG result = builder.findClDG(project, "Test139");
        
        assertEquals(2, result.getPDGs().size());
        assertEquals("Test139#Test139( );Test139#m( )",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetPDG_Test101_1() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        PDG result = builder.getPDG(jmethod, true, true);
        
        assertEquals(5, result.getNodes().size());
        assertEquals(8, result.getEdges().size());
    }
    
    @Test
    public void testGetPDG_Test101_2() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        PDG result = builder.getPDG(jmethod, false, true);
        
        assertEquals(5, result.getNodes().size());
        assertEquals(8, result.getEdges().size());
    }
    
    @Test
    public void testGetPDG_Test101_3() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        PDG result = builder.getPDG(jmethod, true, false);
        
        assertEquals(5, result.getNodes().size());
        assertEquals(8, result.getEdges().size());
    }
    
    @Test
    public void testGetPDG_Test101_4() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        PDG result = builder.getPDG(jmethod, false, false);
        
        assertEquals(5, result.getNodes().size());
        assertEquals(8, result.getEdges().size());
    }
    
    @Test
    public void testGetPDG_Test101_5() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        JavaField jfield = jclass.getField("a");
        ModelBuilder builder = project.getModelBuilder();
        PDG result = builder.getPDG(jfield, true, true);
        
        assertEquals(2, result.getNodes().size());
        assertEquals(1, result.getEdges().size());
    }
    
    @Test
    public void testGetPDG_Test101_6() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        JavaField jfield = jclass.getField("a");
        ModelBuilder builder = project.getModelBuilder();
        PDG result = builder.getPDG(jfield, false, true);
        
        assertEquals(2, result.getNodes().size());
        assertEquals(1, result.getEdges().size());
    }
    
    @Test
    public void testGetPDG_Test101_7() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        JavaField jfield = jclass.getField("a");
        ModelBuilder builder = project.getModelBuilder();
        PDG result = builder.getPDG(jfield, true, false);
        
        assertEquals(2, result.getNodes().size());
        assertEquals(1, result.getEdges().size());
    }
    
    @Test
    public void testGetPDG_Test101_8() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        JavaField jfield = jclass.getField("a");
        ModelBuilder builder = project.getModelBuilder();
        PDG result = builder.getPDG(jfield, false, false);
        
        assertEquals(2, result.getNodes().size());
        assertEquals(1, result.getEdges().size());
    }
    
    @Test
    public void testGetPDG_Test135_1() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test135");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        PDG result = builder.getPDG(jmethod, true, true);
        
        assertEquals(31, result.getNodes().size());
        assertEquals(46, result.getEdges().size());
    }
    
    @Test
    public void testGetPDG_Test135_2() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test135");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        PDG result = builder.getPDG(jmethod, false, true);
        
        assertEquals(31, result.getNodes().size());
        assertEquals(46, result.getEdges().size());
    }
    
    @Test
    public void testGetPDG_Test135_3() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test135");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        PDG result = builder.getPDG(jmethod, true, false);
        
        assertEquals(31, result.getNodes().size());
        assertEquals(46, result.getEdges().size());
    }
    
    @Test
    public void testGetPDG_Test135_4() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test135");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        PDG result = builder.getPDG(jmethod, false, false);
        
        assertEquals(31, result.getNodes().size());
        assertEquals(46, result.getEdges().size());
    }
    
    @Test
    public void testGetPDG_S135_1() {
        createPDGs();
        
        JavaClass jclass = project.getClass("S135");
        JavaMethod jmethod = jclass.getMethod("set2( java.lang.String java.lang.String )");
        ModelBuilder builder = project.getModelBuilder();
        PDG result = builder.getPDG(jmethod, true, true);
        
        assertEquals(6, result.getNodes().size());
        assertEquals(7, result.getEdges().size());
    }
    
    @Test
    public void testGetPDG_S135_2() {
        createPDGs();
        
        JavaClass jclass = project.getClass("S135");
        JavaMethod jmethod = jclass.getMethod("set2( java.lang.String java.lang.String )");
        ModelBuilder builder = project.getModelBuilder();
        PDG result = builder.getPDG(jmethod, false, true);
        
        assertEquals(6, result.getNodes().size());
        assertEquals(7, result.getEdges().size());
    }
    
    @Test
    public void testGetPDG_S135_3() {
        createPDGs();
        
        JavaClass jclass = project.getClass("S135");
        JavaMethod jmethod = jclass.getMethod("set2( java.lang.String java.lang.String )");
        ModelBuilder builder = project.getModelBuilder();
        PDG result = builder.getPDG(jmethod, true, false);
        
        assertEquals(6, result.getNodes().size());
        assertEquals(7, result.getEdges().size());
    }
    
    @Test
    public void testGetPDG_S135_4() {
        createPDGs();
        
        JavaClass jclass = project.getClass("S135");
        JavaMethod jmethod = jclass.getMethod("set2( java.lang.String java.lang.String )");
        ModelBuilder builder = project.getModelBuilder();
        PDG result = builder.getPDG(jmethod, false, false);
        
        assertEquals(6, result.getNodes().size());
        assertEquals(7, result.getEdges().size());
    }
    
    @Test
    public void testGetPDG_Test139_1() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test139");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        PDG result = builder.getPDG(jmethod, true, false);
        
        assertEquals(12, result.getNodes().size());
        assertEquals(13, result.getEdges().size());
    }
    
    @Test
    public void testGetPDG_Test139_2() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test139");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        PDG result = builder.getPDG(jmethod, false, true);
        
        assertEquals(12, result.getNodes().size());
        assertEquals(13, result.getEdges().size());
    }
    
    @Test
    public void testGetPDG_Test139_3() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test139");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        PDG result = builder.getPDG(jmethod, true, false);
        
        assertEquals(12, result.getNodes().size());
        assertEquals(13, result.getEdges().size());
    }
    
    @Test
    public void testGetPDG_Test139_4() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test139");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        PDG result = builder.getPDG(jmethod, false, false);
        
        assertEquals(12, result.getNodes().size());
        assertEquals(13, result.getEdges().size());
    }
    
    @Test
    public void testGetPDGByCFG_Test101_1() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        CFG cfg = builder.getCFG(jmethod, true);
        PDG result = builder.getPDG(project, cfg, true, true);
        
        assertEquals(5, result.getNodes().size());
        assertEquals(8, result.getEdges().size());
    }
    
    @Test
    public void testGetPDGByProject_Test101_2() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        CFG cfg = builder.getCFG(jmethod, true);
        PDG result = builder.getPDG(project, cfg, false, true);
        
        assertEquals(5, result.getNodes().size());
        assertEquals(8, result.getEdges().size());
    }
    
    @Test
    public void testGetPDGByCFG_Test101_3() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        CFG cfg = builder.getCFG(jmethod, true);
        PDG result = builder.getPDG(project, cfg, true, false);
        
        assertEquals(5, result.getNodes().size());
        assertEquals(8, result.getEdges().size());
    }
    
    @Test
    public void testGetPDGByCFG_Test101_4() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        CFG cfg = builder.getCFG(jmethod, true);
        PDG result = builder.getPDG(project, cfg, false, false);
        
        assertEquals(5, result.getNodes().size());
        assertEquals(8, result.getEdges().size());
    }
    
    @Test
    public void testGetPDGByCFG_Test101_5() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        JavaField jfield = jclass.getField("a");
        ModelBuilder builder = project.getModelBuilder();
        CFG cfg = builder.getCFG(jfield, true);
        PDG result = builder.getPDG(project, cfg, true, true);
        
        assertEquals(2, result.getNodes().size());
        assertEquals(1, result.getEdges().size());
    }
    
    @Test
    public void testGetPDGByCFG_Test101_6() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        JavaField jfield = jclass.getField("a");
        ModelBuilder builder = project.getModelBuilder();
        CFG cfg = builder.getCFG(jfield, true);
        PDG result = builder.getPDG(project, cfg, false, true);
        
        assertEquals(2, result.getNodes().size());
        assertEquals(1, result.getEdges().size());
    }
    
    @Test
    public void testGetPDGByCFG_Test101_7() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        JavaField jfield = jclass.getField("a");
        ModelBuilder builder = project.getModelBuilder();
        CFG cfg = builder.getCFG(jfield, true);
        PDG result = builder.getPDG(project, cfg, true, false);
        
        assertEquals(2, result.getNodes().size());
        assertEquals(1, result.getEdges().size());
    }
    
    @Test
    public void testGetPDGByCFG_Test101_8() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        JavaField jfield = jclass.getField("a");
        ModelBuilder builder = project.getModelBuilder();
        CFG cfg = builder.getCFG(jfield, true);
        PDG result = builder.getPDG(project, cfg, false, false);
        
        assertEquals(2, result.getNodes().size());
        assertEquals(1, result.getEdges().size());
    }
    
    @Test
    public void testGetPDGByCFG_Test135_1() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test135");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        CFG cfg = builder.getCFG(jmethod, true);
        PDG result = builder.getPDG(project, cfg, true, true);
        
        assertEquals(31, result.getNodes().size());
        assertEquals(46, result.getEdges().size());
    }
    
    @Test
    public void testGetPDGByCFG_Test135_2() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test135");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        CFG cfg = builder.getCFG(jmethod, true);
        PDG result = builder.getPDG(project, cfg, false, true);
        
        assertEquals(31, result.getNodes().size());
        assertEquals(46, result.getEdges().size());
    }
    
    @Test
    public void testGetPDGByCFG_Test135_3() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test135");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        CFG cfg = builder.getCFG(jmethod, true);
        PDG result = builder.getPDG(project, cfg, true, false);
        
        assertEquals(31, result.getNodes().size());
        assertEquals(46, result.getEdges().size());
    }
    
    @Test
    public void testGetPDGByCFG_Test135_4() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test135");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        CFG cfg = builder.getCFG(jmethod, true);
        PDG result = builder.getPDG(project, cfg, false, false);
        
        assertEquals(31, result.getNodes().size());
        assertEquals(46, result.getEdges().size());
    }
    
    @Test
    public void testGetPDGByCFG_S135_1() {
        createPDGs();
        
        JavaClass jclass = project.getClass("S135");
        JavaMethod jmethod = jclass.getMethod("set2( java.lang.String java.lang.String )");
        ModelBuilder builder = project.getModelBuilder();
        CFG cfg = builder.getCFG(jmethod, true);
        PDG result = builder.getPDG(project, cfg, true, true);
        
        assertEquals(6, result.getNodes().size());
        assertEquals(7, result.getEdges().size());
    }
    
    @Test
    public void testGetPDGByCFG_S135_2() {
        createPDGs();
        
        JavaClass jclass = project.getClass("S135");
        JavaMethod jmethod = jclass.getMethod("set2( java.lang.String java.lang.String )");
        ModelBuilder builder = project.getModelBuilder();
        CFG cfg = builder.getCFG(jmethod, true);
        PDG result = builder.getPDG(project, cfg, false, true);
        
        assertEquals(6, result.getNodes().size());
        assertEquals(7, result.getEdges().size());
    }
    
    @Test
    public void testGetPDGByCFG_S135_3() {
        createPDGs();
        
        JavaClass jclass = project.getClass("S135");
        JavaMethod jmethod = jclass.getMethod("set2( java.lang.String java.lang.String )");
        ModelBuilder builder = project.getModelBuilder();
        CFG cfg = builder.getCFG(jmethod, true);
        PDG result = builder.getPDG(project, cfg, true, false);
        
        assertEquals(6, result.getNodes().size());
        assertEquals(7, result.getEdges().size());
    }
    
    @Test
    public void testGetPDGByCFG_S135_4() {
        createPDGs();
        
        JavaClass jclass = project.getClass("S135");
        JavaMethod jmethod = jclass.getMethod("set2( java.lang.String java.lang.String )");
        ModelBuilder builder = project.getModelBuilder();
        CFG cfg = builder.getCFG(jmethod, true);
        PDG result = builder.getPDG(project, cfg, false, false);
        
        assertEquals(6, result.getNodes().size());
        assertEquals(7, result.getEdges().size());
    }
    
    @Test
    public void testGetPDGByCFG_Test139_1() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test139");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        CFG cfg = builder.getCFG(jmethod, true);
        PDG result = builder.getPDG(project, cfg, true, false);
        
        assertEquals(12, result.getNodes().size());
        assertEquals(13, result.getEdges().size());
    }
    
    @Test
    public void testGetPDGByCFG_Test139_2() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test139");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        CFG cfg = builder.getCFG(jmethod, true);
        PDG result = builder.getPDG(project, cfg, false, true);
        
        assertEquals(12, result.getNodes().size());
        assertEquals(13, result.getEdges().size());
    }
    
    @Test
    public void testGetPDGByCFG_Test139_3() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test139");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        CFG cfg = builder.getCFG(jmethod, true);
        PDG result = builder.getPDG(project, cfg, true, false);
        
        assertEquals(12, result.getNodes().size());
        assertEquals(13, result.getEdges().size());
    }
    
    @Test
    public void testGetPDGByCFG_Test139_4() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test139");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        CFG cfg = builder.getCFG(jmethod, true);
        PDG result = builder.getPDG(project, cfg, false, false);
        
        assertEquals(12, result.getNodes().size());
        assertEquals(13, result.getEdges().size());
    }
    
    @Test
    public void testGetClDG_Test101_1() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        ModelBuilder builder = project.getModelBuilder();
        ClDG result = builder.getClDG(jclass, true, true);
        
        assertEquals(5, result.getPDGs().size());
        assertEquals("Test101#Test101( );Test101#a;Test101#m( );Test101#p;Test101#q",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDG_Test101_2() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        ModelBuilder builder = project.getModelBuilder();
        ClDG result = builder.getClDG(jclass, false, true);
        
        assertEquals(5, result.getPDGs().size());
        assertEquals("Test101#Test101( );Test101#a;Test101#m( );Test101#p;Test101#q",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDG_Test101_3() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        ModelBuilder builder = project.getModelBuilder();
        ClDG result = builder.getClDG(jclass, true, false);
        
        assertEquals(5, result.getPDGs().size());
        assertEquals("Test101#Test101( );Test101#a;Test101#m( );Test101#p;Test101#q",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDG_Test101_4() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        ModelBuilder builder = project.getModelBuilder();
        ClDG result = builder.getClDG(jclass, false, false);
        
        assertEquals(5, result.getPDGs().size());
        assertEquals("Test101#Test101( );Test101#a;Test101#m( );Test101#p;Test101#q",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDG_Test135_1() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test135");
        ModelBuilder builder = project.getModelBuilder();
        ClDG result = builder.getClDG(jclass, true, true);
        
        assertEquals(4, result.getPDGs().size());
        assertEquals("Test135#Test135( );Test135#m( );Test135#s1;Test135#s2",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDG_Test135_2() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test135");
        ModelBuilder builder = project.getModelBuilder();
        ClDG result = builder.getClDG(jclass, false, true);
        
        assertEquals(4, result.getPDGs().size());
        assertEquals("Test135#Test135( );Test135#m( );Test135#s1;Test135#s2",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDG_Test135_3() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test135");
        ModelBuilder builder = project.getModelBuilder();
        ClDG result = builder.getClDG(jclass, true, false);
        
        assertEquals(4, result.getPDGs().size());
        assertEquals("Test135#Test135( );Test135#m( );Test135#s1;Test135#s2",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDG_Test135_4() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test135");
        ModelBuilder builder = project.getModelBuilder();
        ClDG result = builder.getClDG(jclass, false, false);
        
        assertEquals(4, result.getPDGs().size());
        assertEquals("Test135#Test135( );Test135#m( );Test135#s1;Test135#s2",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDG_S135_1() {
        createPDGs();
        
        JavaClass jclass = project.getClass("S135");
        ModelBuilder builder = project.getModelBuilder();
        ClDG result = builder.getClDG(jclass, true, true);
        
        assertEquals(5, result.getPDGs().size());
        assertEquals("S135#S135( );S135#get2( java.lang.String );S135#key;"
                + "S135#set2( java.lang.String java.lang.String );S135#value",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDG_S135_2() {
        createPDGs();
        
        JavaClass jclass = project.getClass("S135");
        ModelBuilder builder = project.getModelBuilder();
        ClDG result = builder.getClDG(jclass, false, true);
        
        assertEquals(5, result.getPDGs().size());
        assertEquals("S135#S135( );S135#get2( java.lang.String );S135#key;"
                + "S135#set2( java.lang.String java.lang.String );S135#value",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDG_S135_3() {
        createPDGs();
        
        JavaClass jclass = project.getClass("S135");
        ModelBuilder builder = project.getModelBuilder();
        ClDG result = builder.getClDG(jclass, true, false);
        
        assertEquals(5, result.getPDGs().size());
        assertEquals("S135#S135( );S135#get2( java.lang.String );S135#key;"
                + "S135#set2( java.lang.String java.lang.String );S135#value",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDG_S135_4() {
        createPDGs();
        
        JavaClass jclass = project.getClass("S135");
        ModelBuilder builder = project.getModelBuilder();
        ClDG result = builder.getClDG(jclass, false, false);
        
        assertEquals(5, result.getPDGs().size());
        assertEquals("S135#S135( );S135#get2( java.lang.String );S135#key;"
                + "S135#set2( java.lang.String java.lang.String );S135#value",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDG_Test139_1() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test139");
        ModelBuilder builder = project.getModelBuilder();
        ClDG result = builder.getClDG(jclass, true, true);
        
        assertEquals(2, result.getPDGs().size());
        assertEquals("Test139#Test139( );Test139#m( )",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDG_Test139_2() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test139");
        ModelBuilder builder = project.getModelBuilder();
        ClDG result = builder.getClDG(jclass, false, true);
        
        assertEquals(2, result.getPDGs().size());
        assertEquals("Test139#Test139( );Test139#m( )",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDG_Test139_3() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test139");
        ModelBuilder builder = project.getModelBuilder();
        ClDG result = builder.getClDG(jclass, true, false);
        
        assertEquals(2, result.getPDGs().size());
        assertEquals("Test139#Test139( );Test139#m( )",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDG_Test139_4() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test139");
        ModelBuilder builder = project.getModelBuilder();
        ClDG result = builder.getClDG(jclass, false, false);
        
        assertEquals(2, result.getPDGs().size());
        assertEquals("Test139#Test139( );Test139#m( )",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDGByCCFG_Test101_1() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        ModelBuilder builder = project.getModelBuilder();
        CCFG ccfg = builder.getCCFG(jclass, true);
        ClDG result = builder.getClDG(project, ccfg, true, true);
        
        assertEquals(5, result.getPDGs().size());
        assertEquals("Test101#Test101( );Test101#a;Test101#m( );Test101#p;Test101#q",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDGByCCFG_Test101_2() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        ModelBuilder builder = project.getModelBuilder();
        CCFG ccfg = builder.getCCFG(jclass, true);
        ClDG result = builder.getClDG(project, ccfg, false, true);
        
        assertEquals(5, result.getPDGs().size());
        assertEquals("Test101#Test101( );Test101#a;Test101#m( );Test101#p;Test101#q",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDGByCCFG_Test101_3() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        ModelBuilder builder = project.getModelBuilder();
        CCFG ccfg = builder.getCCFG(jclass, true);
        ClDG result = builder.getClDG(project, ccfg, true, false);
        
        assertEquals(5, result.getPDGs().size());
        assertEquals("Test101#Test101( );Test101#a;Test101#m( );Test101#p;Test101#q",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDGByCCFG_Test101_4() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        ModelBuilder builder = project.getModelBuilder();
        CCFG ccfg = builder.getCCFG(jclass, true);
        ClDG result = builder.getClDG(project, ccfg, false, false);
        
        assertEquals(5, result.getPDGs().size());
        assertEquals("Test101#Test101( );Test101#a;Test101#m( );Test101#p;Test101#q",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDGByCCFG_Test135_1() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test135");
        ModelBuilder builder = project.getModelBuilder();
        CCFG ccfg = builder.getCCFG(jclass, true);
        ClDG result = builder.getClDG(project, ccfg, true, true);
        
        assertEquals(4, result.getPDGs().size());
        assertEquals("Test135#Test135( );Test135#m( );Test135#s1;Test135#s2",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDGByCCFG_Test135_2() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test135");
        ModelBuilder builder = project.getModelBuilder();
        CCFG ccfg = builder.getCCFG(jclass, true);
        ClDG result = builder.getClDG(project, ccfg, false, true);
        
        assertEquals(4, result.getPDGs().size());
        assertEquals("Test135#Test135( );Test135#m( );Test135#s1;Test135#s2",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDGByCCFG_Test135_3() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test135");
        ModelBuilder builder = project.getModelBuilder();
        CCFG ccfg = builder.getCCFG(jclass, true);
        ClDG result = builder.getClDG(project, ccfg, true, false);
        
        assertEquals(4, result.getPDGs().size());
        assertEquals("Test135#Test135( );Test135#m( );Test135#s1;Test135#s2",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDGByCCFG_Test135_4() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test135");
        ModelBuilder builder = project.getModelBuilder();
        CCFG ccfg = builder.getCCFG(jclass, true);
        ClDG result = builder.getClDG(project, ccfg, false, false);
        
        assertEquals(4, result.getPDGs().size());
        assertEquals("Test135#Test135( );Test135#m( );Test135#s1;Test135#s2",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDGByCCFG_S135_1() {
        createPDGs();
        
        JavaClass jclass = project.getClass("S135");
        ModelBuilder builder = project.getModelBuilder();
        CCFG ccfg = builder.getCCFG(jclass, true);
        ClDG result = builder.getClDG(project, ccfg, true, true);
        
        assertEquals(5, result.getPDGs().size());
        assertEquals("S135#S135( );S135#get2( java.lang.String );S135#key;"
                + "S135#set2( java.lang.String java.lang.String );S135#value",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDGByCCFG_S135_2() {
        createPDGs();
        
        JavaClass jclass = project.getClass("S135");
        ModelBuilder builder = project.getModelBuilder();
        CCFG ccfg = builder.getCCFG(jclass, true);
        ClDG result = builder.getClDG(project, ccfg, false, true);
        
        assertEquals(5, result.getPDGs().size());
        assertEquals("S135#S135( );S135#get2( java.lang.String );S135#key;"
                + "S135#set2( java.lang.String java.lang.String );S135#value",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDGByCCFG_S135_3() {
        createPDGs();
        
        JavaClass jclass = project.getClass("S135");
        ModelBuilder builder = project.getModelBuilder();
        CCFG ccfg = builder.getCCFG(jclass, true);
        ClDG result = builder.getClDG(project, ccfg, true, false);
        
        assertEquals(5, result.getPDGs().size());
        assertEquals("S135#S135( );S135#get2( java.lang.String );S135#key;"
                + "S135#set2( java.lang.String java.lang.String );S135#value",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDGByCCFG_S135_4() {
        createPDGs();
        
        JavaClass jclass = project.getClass("S135");
        ModelBuilder builder = project.getModelBuilder();
        CCFG ccfg = builder.getCCFG(jclass, true);
        ClDG result = builder.getClDG(project, ccfg, false, false);
        
        assertEquals(5, result.getPDGs().size());
        assertEquals("S135#S135( );S135#get2( java.lang.String );S135#key;"
                + "S135#set2( java.lang.String java.lang.String );S135#value",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDGByCCFG_Test139_1() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test139");
        ModelBuilder builder = project.getModelBuilder();
        CCFG ccfg = builder.getCCFG(jclass, true);
        ClDG result = builder.getClDG(project, ccfg, true, true);
        
        assertEquals(2, result.getPDGs().size());
        assertEquals("Test139#Test139( );Test139#m( )",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDGByCCFG_Test139_2() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test139");
        ModelBuilder builder = project.getModelBuilder();
        CCFG ccfg = builder.getCCFG(jclass, true);
        ClDG result = builder.getClDG(project, ccfg, false, true);
        
        assertEquals(2, result.getPDGs().size());
        assertEquals("Test139#Test139( );Test139#m( )",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDGByCCFG_Test139_3() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test139");
        ModelBuilder builder = project.getModelBuilder();
        CCFG ccfg = builder.getCCFG(jclass, true);
        ClDG result = builder.getClDG(project, ccfg, true, false);
        
        assertEquals(2, result.getPDGs().size());
        assertEquals("Test139#Test139( );Test139#m( )",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetClDGByCCFG_Test139_4() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test139");
        ModelBuilder builder = project.getModelBuilder();
        CCFG ccfg = builder.getCCFG(jclass, true);
        ClDG result = builder.getClDG(project, ccfg, false, false);
        
        assertEquals(2, result.getPDGs().size());
        assertEquals("Test139#Test139( );Test139#m( )",
                BuilderTestUtil.asStrOfClDG(result));
    }
    
    @Test
    public void testGetSDG1() {
        clearPDGs();
        
        ModelBuilder builder = project.getModelBuilder();
        SDG result = builder.getSDG(project, true);
        
        assertEquals(307, result.getPDGs().size());
    }
    
    @Test
    public void testGetSDG2() {
        clearPDGs();
        
        ModelBuilder builder = project.getModelBuilder();
        SDG result = builder.getSDG(project, false);
        
        assertEquals(307, result.getPDGs().size());
    }
    
    @Test
    public void testGetDependencyGraph_Test101_1() {
        clearPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        ModelBuilder builder = project.getModelBuilder();
        DependencyGraph result = builder.getDependencyGraph(jclass, true, true);
        
        assertEquals(5, result.getPDGs().size());
        assertEquals("Test101#Test101( );Test101#a;Test101#m( );Test101#p;Test101#q",
                BuilderTestUtil.asStrOfDependencyGraph(result));
    }
    
    @Test
    public void testGetDependencyGraph_Test101_2() {
        clearPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        ModelBuilder builder = project.getModelBuilder();
        DependencyGraph result = builder.getDependencyGraph(jclass, false, true);
        
        assertEquals(5, result.getPDGs().size());
        assertEquals("Test101#Test101( );Test101#a;Test101#m( );Test101#p;Test101#q",
                BuilderTestUtil.asStrOfDependencyGraph(result));
    }
    
    @Test
    public void testGetDependencyGraph_Test101_3() {
        clearPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        ModelBuilder builder = project.getModelBuilder();
        DependencyGraph result = builder.getDependencyGraph(jclass, true, false);
        
        assertEquals(5, result.getPDGs().size());
        assertEquals("Test101#Test101( );Test101#a;Test101#m( );Test101#p;Test101#q",
                BuilderTestUtil.asStrOfDependencyGraph(result));
    }
    
    @Test
    public void testGetDependencyGraph_Test101_4() {
        clearPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        ModelBuilder builder = project.getModelBuilder();
        DependencyGraph result = builder.getDependencyGraph(jclass, false, true);
        
        assertEquals(5, result.getPDGs().size());
        assertEquals("Test101#Test101( );Test101#a;Test101#m( );Test101#p;Test101#q",
                BuilderTestUtil.asStrOfDependencyGraph(result));
    }
    
    @Test
    public void testGetDependencyGraph_Test135_1() {
        clearPDGs();
        
        JavaClass jclass = project.getClass("Test135");
        ModelBuilder builder = project.getModelBuilder();
        DependencyGraph result = builder.getDependencyGraph(jclass, true, true);
        
        assertEquals(9, result.getPDGs().size());
        assertEquals("S135#S135( );S135#get2( java.lang.String );S135#key;"
                + "S135#set2( java.lang.String java.lang.String );S135#value;"
                + "Test135#Test135( );Test135#m( );Test135#s1;Test135#s2",
                BuilderTestUtil.asStrOfDependencyGraph(result));
    }
    
    @Test
    public void testGetDependencyGraph_Test135_2() {
        clearPDGs();
        
        JavaClass jclass = project.getClass("Test135");
        ModelBuilder builder = project.getModelBuilder();
        DependencyGraph result = builder.getDependencyGraph(jclass, false, true);
        
        assertEquals(9, result.getPDGs().size());
        assertEquals("S135#S135( );S135#get2( java.lang.String );S135#key;"
                + "S135#set2( java.lang.String java.lang.String );S135#value;"
                + "Test135#Test135( );Test135#m( );Test135#s1;Test135#s2",
                BuilderTestUtil.asStrOfDependencyGraph(result));
    }
    
    @Test
    public void testGetDependencyGraph_Test135_3() {
        clearPDGs();
        
        JavaClass jclass = project.getClass("Test135");
        ModelBuilder builder = project.getModelBuilder();
        DependencyGraph result = builder.getDependencyGraph(jclass, true, false);
        
        assertEquals(4, result.getPDGs().size());
        assertEquals("Test135#Test135( );Test135#m( );Test135#s1;Test135#s2",
                BuilderTestUtil.asStrOfDependencyGraph(result));
    }
    
    @Test
    public void testGetDependencyGraph_Test135_4() {
        clearPDGs();
        
        JavaClass jclass = project.getClass("Test135");
        ModelBuilder builder = project.getModelBuilder();
        DependencyGraph result = builder.getDependencyGraph(jclass, false, false);
        
        assertEquals(4, result.getPDGs().size());
        assertEquals("Test135#Test135( );Test135#m( );Test135#s1;Test135#s2",
                BuilderTestUtil.asStrOfDependencyGraph(result));
    }
    
    @Test
    public void testGetDependencyGraph_S135_1() {
        clearPDGs();
        
        JavaClass jclass = project.getClass("S135");
        ModelBuilder builder = project.getModelBuilder();
        DependencyGraph result = builder.getDependencyGraph(jclass, true, true);
        
        assertEquals(5, result.getPDGs().size());
        assertEquals("S135#S135( );S135#get2( java.lang.String );S135#key;"
                + "S135#set2( java.lang.String java.lang.String );S135#value",
                BuilderTestUtil.asStrOfDependencyGraph(result));
    }
    
    @Test
    public void testGetDependencyGraph_S135_2() {
        JavaClass jclass = project.getClass("S135");
        ModelBuilder builder = project.getModelBuilder();
        DependencyGraph result = builder.getDependencyGraph(jclass, false, true);
        
        assertEquals(5, result.getPDGs().size());
        assertEquals("S135#S135( );S135#get2( java.lang.String );S135#key;"
                + "S135#set2( java.lang.String java.lang.String );S135#value",
                BuilderTestUtil.asStrOfDependencyGraph(result));
    }
    
    @Test
    public void testGetDependencyGraph_S135_3() {
        clearPDGs();
        
        JavaClass jclass = project.getClass("S135");
        ModelBuilder builder = project.getModelBuilder();
        DependencyGraph result = builder.getDependencyGraph(jclass, true, false);
        
        assertEquals(5, result.getPDGs().size());
        assertEquals("S135#S135( );S135#get2( java.lang.String );S135#key;"
                + "S135#set2( java.lang.String java.lang.String );S135#value",
                BuilderTestUtil.asStrOfDependencyGraph(result));
    }
    
    @Test
    public void testGetDependencyGraph_S135_4() {
        clearPDGs();
        
        JavaClass jclass = project.getClass("S135");
        ModelBuilder builder = project.getModelBuilder();
        DependencyGraph result = builder.getDependencyGraph(jclass, false, false);
        
        assertEquals(5, result.getPDGs().size());
        assertEquals("S135#S135( );S135#get2( java.lang.String );S135#key;"
                + "S135#set2( java.lang.String java.lang.String );S135#value",
                BuilderTestUtil.asStrOfDependencyGraph(result));
    }
    
    @Test
    public void testGetDependencyGraph_Test139_1() {
        clearPDGs();
        
        JavaClass jclass = project.getClass("Test139");
        ModelBuilder builder = project.getModelBuilder();
        DependencyGraph result = builder.getDependencyGraph(jclass, true, true);
        
        assertEquals(8, result.getPDGs().size());
        assertEquals("PriceCode#CHILDRENS;PriceCode#NEW_RELEASE;PriceCode#PriceCode( int );"
                + "PriceCode#REGULAR;PriceCode#getPriceCode( );PriceCode#priceCode;"
                + "Test139#Test139( );Test139#m( )",
                BuilderTestUtil.asStrOfDependencyGraph(result));
    }
    
    @Test
    public void testGetDependencyGraph_Test139_2() {
        clearPDGs();
        
        JavaClass jclass = project.getClass("Test139");
        ModelBuilder builder = project.getModelBuilder();
        DependencyGraph result = builder.getDependencyGraph(jclass, false, true);
        
        assertEquals(8, result.getPDGs().size());
        assertEquals("PriceCode#CHILDRENS;PriceCode#NEW_RELEASE;PriceCode#PriceCode( int );"
                + "PriceCode#REGULAR;PriceCode#getPriceCode( );PriceCode#priceCode;"
                + "Test139#Test139( );Test139#m( )",
                BuilderTestUtil.asStrOfDependencyGraph(result));
    }
    
    @Test
    public void testGetDependencyGraph_Test139_3() {
        clearPDGs();
        
        JavaClass jclass = project.getClass("Test139");
        ModelBuilder builder = project.getModelBuilder();
        DependencyGraph result = builder.getDependencyGraph(jclass, true, false);
        
        assertEquals(2, result.getPDGs().size());
        assertEquals("Test139#Test139( );Test139#m( )",
                BuilderTestUtil.asStrOfDependencyGraph(result));
    }
    
    @Test
    public void testGetDependencyGraph_Test139_4() {
        clearPDGs();
        
        JavaClass jclass = project.getClass("Test139");
        ModelBuilder builder = project.getModelBuilder();
        DependencyGraph result = builder.getDependencyGraph(jclass, false, false);
        
        assertEquals(2, result.getPDGs().size());
        assertEquals("Test139#Test139( );Test139#m( )",
                BuilderTestUtil.asStrOfDependencyGraph(result));
    }
    
    @Test
    public void testGetAllClassesForward_Test101() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        ModelBuilder builder = project.getModelBuilder();
        Set<JavaClass> result = builder.getAllClassesForward(jclass);
        
        assertEquals(1, result.size());
        assertEquals("Test101",
                BuilderTestUtil.asStrOfClasses(result));
    }
    
    @Test
    public void testGetAllClassesForward_Test135() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test135");
        ModelBuilder builder = project.getModelBuilder();
        Set<JavaClass> result = builder.getAllClassesForward(jclass);
        
        assertEquals(2, result.size());
        assertEquals("S135;Test135",
                BuilderTestUtil.asStrOfClasses(result));
    }
    
    @Test
    public void testGetAllClassesForward_S135() {
        createPDGs();
        
        JavaClass jclass = project.getClass("S135");
        ModelBuilder builder = project.getModelBuilder();
        Set<JavaClass> result = builder.getAllClassesForward(jclass);
        
        assertEquals(1, result.size());
        assertEquals("S135",
                BuilderTestUtil.asStrOfClasses(result));
    }
    
    @Test
    public void testGetAllClassesForward_Test139() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test139");
        ModelBuilder builder = project.getModelBuilder();
        Set<JavaClass> result = builder.getAllClassesForward(jclass);
        
        assertEquals(2, result.size());
        assertEquals("PriceCode;Test139",
                BuilderTestUtil.asStrOfClasses(result));
    }
    
    @Test
    public void testGetAllClassesBackward_Test101() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        ModelBuilder builder = project.getModelBuilder();
        Set<JavaClass> result = builder.getAllClassesBackward(jclass);
        
        assertEquals(1, result.size());
        assertEquals("Test101",
                BuilderTestUtil.asStrOfClasses(result));
    }
    
    @Test
    public void testGetAllClassesBackward_Test135() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test135");
        ModelBuilder builder = project.getModelBuilder();
        Set<JavaClass> result = builder.getAllClassesBackward(jclass);
        
        assertEquals(1, result.size());
        assertEquals("Test135",
                BuilderTestUtil.asStrOfClasses(result));
    }
    
    @Test
    public void testGetAllClassesBackward_S135() {
        createPDGs();
        
        JavaClass jclass = project.getClass("S135");
        ModelBuilder builder = project.getModelBuilder();
        Set<JavaClass> result = builder.getAllClassesBackward(jclass);
        
        assertEquals(2, result.size());
        assertEquals("S135;Test135",
                BuilderTestUtil.asStrOfClasses(result));
    }
    
    @Test
    public void testGetAllClassesBackward_Test139() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test139");
        ModelBuilder builder = project.getModelBuilder();
        Set<JavaClass> result = builder.getAllClassesBackward(jclass);
        
        assertEquals(1, result.size());
        assertEquals("Test139",
                BuilderTestUtil.asStrOfClasses(result));
    }
    
    @Test
    public void testAllMethodsForward_Test101() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        Set<JavaMethod> result = builder.getAllMethodsForward(jmethod);
        
        assertEquals(1, result.size());
        assertEquals("Test101#m( )",
                BuilderTestUtil.asStrOfMethods(result));
    }
    
    @Test
    public void testAllMethodsForward_Test135() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test135");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        Set<JavaMethod> result = builder.getAllMethodsForward(jmethod);
        
        assertEquals(4, result.size());
        assertEquals("S135#get2( java.lang.String );S135#set2( java.lang.String java.lang.String );"
                + "Test135#m( );java.lang.String#equals( java.lang.Object )",
                BuilderTestUtil.asStrOfMethods(result));
    }
    
    @Test
    public void testAllMethodsForward_S135() {
        createPDGs();
        
        JavaClass jclass = project.getClass("S135");
        JavaMethod jmethod = jclass.getMethod("set2( java.lang.String java.lang.String )");
        ModelBuilder builder = project.getModelBuilder();
        Set<JavaMethod> result = builder.getAllMethodsForward(jmethod);
        
        assertEquals(1, result.size());
        assertEquals("S135#set2( java.lang.String java.lang.String )",
                BuilderTestUtil.asStrOfMethods(result));
    }
    
    @Test
    public void testAllMethodsForward_Test139() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test139");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        Set<JavaMethod> result = builder.getAllMethodsForward(jmethod);
        
        assertEquals(3, result.size());
        assertEquals("PriceCode#getPriceCode( );Test139#m( );java.lang.Enum#name( )",
                BuilderTestUtil.asStrOfMethods(result));
    }
    
    @Test
    public void testAllMethodsBackward_Test101() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test101");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        Set<JavaMethod> result = builder.getAllMethodsBackward(jmethod);
        
        assertEquals(1, result.size());
        assertEquals("Test101#m( )",
                BuilderTestUtil.asStrOfMethods(result));
    }
    
    @Test
    public void testAllMethodsBackward_Test135() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test135");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        Set<JavaMethod> result = builder.getAllMethodsBackward(jmethod);
        
        assertEquals(1, result.size());
        assertEquals("Test135#m( )",
                BuilderTestUtil.asStrOfMethods(result));
    }
    
    @Test
    public void testAllMethodsBackward_S135() {
        JavaClass jclass = project.getClass("S135");
        JavaMethod jmethod = jclass.getMethod("set2( java.lang.String java.lang.String )");
        ModelBuilder builder = project.getModelBuilder();
        Set<JavaMethod> result = builder.getAllMethodsBackward(jmethod);
        
        assertEquals(2, result.size());
        assertEquals("S135#set2( java.lang.String java.lang.String );Test135#m( )",
                BuilderTestUtil.asStrOfMethods(result));
    }
    
    @Test
    public void testAllMethodsBackward_Test139() {
        createPDGs();
        
        JavaClass jclass = project.getClass("Test139");
        JavaMethod jmethod = jclass.getMethod("m( )");
        ModelBuilder builder = project.getModelBuilder();
        Set<JavaMethod> result = builder.getAllMethodsBackward(jmethod);
        
        assertEquals(1, result.size());
        assertEquals("Test139#m( )",
                BuilderTestUtil.asStrOfMethods(result));
    }
}
