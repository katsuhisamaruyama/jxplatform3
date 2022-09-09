/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.slice;

import org.jtool.pdg.SDG;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.PDGTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SliceCriterionTest {
    
    private static JavaProject SliceProject;
    
    @BeforeClass
    public static void setUp() {
        SliceProject = BuilderTestUtil.createProject("Slice", "", "");
    }
    
    @AfterClass
    public static void tearDown() {
        SliceProject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testFindByClass1() {
        String cname = "Test101";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass, 5, 12);
        
        assertEquals("1", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("x$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByClass2() {
        String cname = "Test102";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass, 6, 20);
        
        assertEquals("10", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("y$1", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByClass3() {
        String cname = "Test103";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass, 19, 8);
        
        assertNull(result);
    }
    
    @Test
    public void testFindByClass4() {
        String cname = "Test103";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass, 19, 13);
        
        assertEquals("2", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("this.a", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByClass5() {
        String cname = "Test104";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass, 4, 22);
        
        assertEquals("3", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("z$2", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByClass6() {
        String cname = "Test105";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass, 10, 25);
        
        assertEquals("1", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("a$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByClass7() {
        String cname = "Test105";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass, 12, 15);
        
        assertEquals("3", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("a$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByClass8() {
        String cname = "Test108";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass, 7, 12);
        
        assertEquals("4", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("x$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByClass9() {
        String cname = "Test108";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass, 8, 12);
        
        assertEquals("5", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("y$1", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByClass10() {
        String cname = "Test110";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass, 6, 15);
        
        assertEquals("3", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("x$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByClass11() {
        String cname = "Test111";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass, 7, 16);
        
        assertEquals("4", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("x$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByClass12() {
        String cname = "Test111";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass, 8, 17);
        
        assertNull(result);
    }
    
    @Test
    public void testFindByClass13() {
        String cname = "Test115";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass, 8, 20);
        
        assertEquals("6", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("a$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByClass14() {
        String cname = "Test115";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass, 8, 22);
        
        assertEquals("6", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("i$3", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByClass15() {
        String cname = "Test119";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass, 8, 16);
        
        assertEquals("12", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("a$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByClass16() {
        String cname = "A119";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass, 27, 15);
        
        assertEquals("1", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("this.x", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByClass17() {
        String cname = "A119";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass, 30, 13);
        
        assertEquals("2", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("this.x", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByClass18() {
        String cname = "Test120";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass, 5, 19);
        
        assertEquals("8", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("a$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByClass19() {
        String cname = "Test120";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass, 5, 21);
        
        assertEquals("8", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("a$0.x", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByClass20() {
        String cname = "Test123";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass, 8, 16);
        
        assertEquals("7", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByClass21() {
        String cname = "Test123";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass, 21, 27);
        
        assertEquals("8", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("e$1", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByClass22() {
        String cname = "Test124";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass, 7, 26);
        
        assertEquals("6", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("p$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByClass23() {
        String cname = "Test125";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass, 7, 26);
        
        assertEquals("13", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("p$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByClass24() {
        String cname = "Test126";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass, 10, 16);
        
        assertEquals("14", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("a2$2", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByClass25() {
        String cname = "Test126";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass, 10, 17);
        
        assertNull(result);
    }
    
    @Test
    public void testFindByMethod1() {
        String cname = "Test101";
        String member = "m( )";
        JavaMethod jmethod = SliceProject.getClass(cname).getMethod(member);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jmethod, 5, 12);
        
        assertEquals("1", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("x$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByMethod2() {
        String cname = "Test102";
        String member = "m( )";
        JavaMethod jmethod = SliceProject.getClass(cname).getMethod(member);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jmethod, 6, 20);
        
        assertEquals("10", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("y$1", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByMethod3() {
        String cname = "Test103";
        String member = "setA( int )";
        JavaMethod jmethod = SliceProject.getClass(cname).getMethod(member);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jmethod, 19, 8);
        
        assertNull(result);
    }
    
    @Test
    public void testFindByMethod4() {
        String cname = "Test103";
        String member = "setA( int )";
        JavaMethod jmethod = SliceProject.getClass(cname).getMethod(member);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jmethod, 19, 13);
        
        assertEquals("2", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("this.a", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByMethod5() {
        String cname = "Test104";
        String member = "m( )";
        JavaMethod jmethod = SliceProject.getClass(cname).getMethod(member);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jmethod, 4, 22);
        
        assertEquals("3", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("z$2", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByMethod6() {
        String cname = "Test105";
        String member = "setA( int )";
        JavaMethod jmethod = SliceProject.getClass(cname).getMethod(member);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jmethod, 10, 25);
        
        assertEquals("1", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("a$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByMethod7() {
        String cname = "Test105";
        String member = "setA( int )";
        JavaMethod jmethod = SliceProject.getClass(cname).getMethod(member);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jmethod, 12, 15);
        
        assertEquals("3", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("a$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByMethod8() {
        String cname = "Test108";
        String member = "m( )";
        JavaMethod jmethod = SliceProject.getClass(cname).getMethod(member);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jmethod, 7, 12);
        
        assertEquals("4", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("x$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByMethod9() {
        String cname = "Test108";
        String member = "m( )";
        JavaMethod jmethod = SliceProject.getClass(cname).getMethod(member);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jmethod, 8, 12);
        
        assertEquals("5", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("y$1", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByMethod10() {
        String cname = "Test110";
        String member = "m( )";
        JavaMethod jmethod = SliceProject.getClass(cname).getMethod(member);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jmethod, 6, 15);
        
        assertEquals("3", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("x$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByMethod11() {
        String cname = "Test111";
        String member = "m( )";
        JavaMethod jmethod = SliceProject.getClass(cname).getMethod(member);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jmethod, 7, 16);
        
        assertEquals("4", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("x$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByMethod12() {
        String cname = "Test111";
        String member = "m( )";
        JavaMethod jmethod = SliceProject.getClass(cname).getMethod(member);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jmethod, 8, 17);
        
        assertNull(result);
    }
    
    @Test
    public void testFindByMethod13() {
        String cname = "Test115";
        String member = "m( )";
        JavaMethod jmethod = SliceProject.getClass(cname).getMethod(member);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jmethod, 8, 20);
        
        assertEquals("6", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("a$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByMethod14() {
        String cname = "Test115";
        String member = "m( )";
        JavaMethod jmethod = SliceProject.getClass(cname).getMethod(member);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jmethod, 8, 22);
        
        assertEquals("6", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("i$3", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByMethod15() {
        String cname = "Test119";
        String member = "m( )";
        JavaMethod jmethod = SliceProject.getClass(cname).getMethod(member);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jmethod, 8, 16);
        
        assertEquals("12", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("a$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByMethod16() {
        String cname = "A119";
        String member = "x";
        JavaField jfield = SliceProject.getClass(cname).getField(member);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jfield, 27, 15);
        
        assertEquals("1", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("this.x", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByMethod17() {
        String cname = "A119";
        String member = "setX( int )";
        JavaMethod jmethod = SliceProject.getClass(cname).getMethod(member);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jmethod, 30, 13);
        
        assertEquals("2", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("this.x", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByMethod18() {
        String cname = "Test120";
        String member = "m( )";
        JavaMethod jmethod = SliceProject.getClass(cname).getMethod(member);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jmethod, 5, 19);
        
        assertEquals("8", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("a$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByMethod19() {
        String cname = "Test120";
        String member = "m( )";
        JavaMethod jmethod = SliceProject.getClass(cname).getMethod(member);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jmethod, 5, 21);
        
        assertEquals("8", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("a$0.x", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByMethod20() {
        String cname = "Test123";
        String member = "m( )";
        JavaMethod jmethod = SliceProject.getClass(cname).getMethod(member);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jmethod, 8, 16);
        
        assertEquals("7", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByMethod21() {
        String cname = "Test123";
        String member = "m2( )";
        JavaMethod jmethod = SliceProject.getClass(cname).getMethod(member);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jmethod, 21, 27);
        
        assertEquals("8", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("e$1", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByMethod22() {
        String cname = "Test124";
        String member = "m( )";
        JavaMethod jmethod = SliceProject.getClass(cname).getMethod(member);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jmethod, 7, 26);
        
        assertEquals("6", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("p$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByMethod23() {
        String cname = "Test125";
        String member = "m( )";
        JavaMethod jmethod = SliceProject.getClass(cname).getMethod(member);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jmethod, 7, 26);
        
        assertEquals("13", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("p$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByMethod24() {
        String cname = "Test126";
        String member = "m( )";
        JavaMethod jmethod = SliceProject.getClass(cname).getMethod(member);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jmethod, 10, 16);
        
        assertEquals("14", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("a2$2", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByMethod25() {
        String cname = "Test126";
        String member = "m( )";
        JavaMethod jmethod = SliceProject.getClass(cname).getMethod(member);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jmethod, 10, 17);
        
        assertNull(result);
    }
    
    @Test
    public void testFindByFile1() {
        String cname = "Test101";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass.getFile(), 5, 12);
        
        assertEquals("1", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("x$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByFile2() {
        String cname = "Test102";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass.getFile(), 6, 20);
        
        assertEquals("10", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("y$1", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByFile3() {
        String cname = "Test103";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass.getFile(), 19, 8);
        
        assertNull(result);
    }
    
    @Test
    public void testFindByFile4() {
        String cname = "Test103";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass.getFile(), 19, 13);
        
        assertEquals("2", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("this.a", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByFile5() {
        String cname = "Test104";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass.getFile(), 4, 22);
        
        assertEquals("3", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("z$2", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByFile6() {
        String cname = "Test105";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass.getFile(), 10, 25);
        
        assertEquals("1", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("a$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByFile7() {
        String cname = "Test105";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass.getFile(), 12, 15);
        
        assertEquals("3", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("a$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByFile8() {
        String cname = "Test108";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass.getFile(), 7, 12);
        
        assertEquals("4", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("x$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByFile9() {
        String cname = "Test108";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass.getFile(), 8, 12);
        
        assertEquals("5", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("y$1", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByFile10() {
        String cname = "Test110";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass.getFile(), 6, 15);
        
        assertEquals("3", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("x$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByFile11() {
        String cname = "Test111";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass.getFile(), 7, 16);
        
        assertEquals("4", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("x$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByFile12() {
        String cname = "Test111";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass.getFile(), 8, 17);
        
        assertNull(result);
    }
    
    @Test
    public void testFindByFile13() {
        String cname = "Test115";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass.getFile(), 8, 20);
        
        assertEquals("6", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("a$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByFile14() {
        String cname = "Test115";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass.getFile(), 8, 22);
        
        assertEquals("6", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("i$3", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByFile15() {
        String cname = "Test119";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass.getFile(), 8, 16);
        
        assertEquals("12", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("a$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByFile16() {
        String cname = "A119";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass.getFile(), 27, 15);
        
        assertEquals("1", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("this.x", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByFile17() {
        String cname = "A119";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass.getFile(), 30, 13);
        
        assertEquals("2", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("this.x", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByFile18() {
        String cname = "Test120";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass.getFile(), 5, 19);
        
        assertEquals("8", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("a$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByFile19() {
        String cname = "Test120";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass.getFile(), 5, 21);
        
        assertEquals("8", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("a$0.x", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByFile20() {
        String cname = "Test123";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass.getFile(), 8, 16);
        
        assertEquals("7", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByFile21() {
        String cname = "Test123";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass.getFile(), 21, 27);
        
        assertEquals("8", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("e$1", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByFile22() {
        String cname = "Test124";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass.getFile(), 7, 26);
        
        assertEquals("6", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("p$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByFile23() {
        String cname = "Test125";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass.getFile(), 7, 26);
        
        assertEquals("13", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("p$0", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByFile24() {
        String cname = "Test126";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass.getFile(), 10, 16);
        
        assertEquals("14", PDGTestUtil.asStrOfNode(sdg, result.getNode()));
        assertEquals("a2$2", TestUtil.asSortedStrOfReference(result.getVariables()));
    }
    
    @Test
    public void testFindByFile25() {
        String cname = "Test126";
        JavaClass jclass = SliceProject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(SliceProject, cname);
        SliceCriterion result = SliceCriterion.find(sdg, jclass.getFile(), 10, 17);
        
        assertNull(result);
    }
}
