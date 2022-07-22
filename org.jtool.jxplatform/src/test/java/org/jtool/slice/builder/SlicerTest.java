/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.slice.builder;

import org.jtool.pdg.ClDG;
import org.jtool.pdg.PDGNode;
import org.jtool.pdg.PDGStatement;
import org.jtool.pdg.SDG;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.JavaClass;
import org.jtool.cfg.JVariableReference;
import org.eclipse.jdt.core.dom.ASTNode;
import java.util.Set;
import java.util.HashSet;
import org.jtool.jxplatform.builder.ModelBuilderBatch;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.PDGTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import org.jtool.jxplatform.util.SliceTestUtil;

import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SlicerTest {
    
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
    public void testSlice101_1() {
        Slicer result = SliceTestUtil.getSliceBuilder(SliceProject, "Test101", 5, 12);
        
        assertEquals(10, result.getNodes().size());
    }
    
    
    
    
    
    @Test
    public void testSlice101_1m() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test101", "m( )", 5, 12);
        
        String expected = 
                "public void m() {\n" + 
                "    int x = 10;\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice101_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test101", 6, 12);
        
        String expected = 
                "class Test101 {\n" + 
                "    public void m() {\n" + 
                "        int x = 10;\n" + 
                "        int y = x + 1;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice101_3() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test101", 7, 12);
        
        String expected = 
                "class Test101 {\n" + 
                "    private int p = 1;\n" + 
                "    public void m() {\n" + 
                "        int x = 10;\n" + 
                "        int z = x + p;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice102_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test102", 7, 12);
        
        String expected = 
                "class Test102 {\n" + 
                "    public void m() {\n" + 
                "        int x = inc(10);\n" + 
                "        int p = x;\n" + 
                "    }\n" + 
                "    public int inc(int n) {\n" + 
                "        return n + 1;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice102_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test102", 8, 12);
        
        String expected = 
                "class Test102 {\n" + 
                "    public void m() {\n" + 
                "        int y = 0;\n" + 
                "        int q = y;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice102_3() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test102", 9, 12);
        
        String expected = 
                "class Test102 {\n" + 
                "    public void m() {\n" + 
                "        int y = 0;\n" + 
                "        int z = inc(y);\n" + 
                "        int r = z;\n" + 
                "    }\n" + 
                "    public int inc(int n) {\n" + 
                "        return n + 1;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice102_4() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test102", 10, 27);
        
        String expected = 
                "class Test102 {\n" + 
                "    public void m() {\n" + 
                "        int y = 0;\n" + 
                "        int z = inc(y);\n" + 
                "    }\n" + 
                "    public int inc(int n) {\n" + 
                "        return n + 1;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice103_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test103", 7, 12);
        
        String expected = 
                "class Test103 {\n" + 
                "    private int a;\n" + 
                "    public void m() {\n" + 
                "        a = 2;\n" + 
                "        int p = a;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice103_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test103", 10, 12);
        
        String expected = 
                "class Test103 {\n" + 
                "    private int a;\n" + 
                "    public void m() {\n" + 
                "        setA(2);\n" + 
                "        int q = a;\n" + 
                "    }\n" + 
                "    private void setA(int a) {\n" + 
                "        this.a = a;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice103_3() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test103", 11, 12);
        
        String expected = 
                "class Test103 {\n" + 
                "    private int a;\n" + 
                "    public void m() {\n" + 
                "        setA(2);\n" + 
                "        int r = getA();\n" + 
                "    }\n" + 
                "    private void setA(int a) {\n" + 
                "        this.a = a;\n" + 
                "    }\n" + 
                "    private int getA() {\n" + 
                "        return a;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice103_4() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test103", 12, 12);
        
        String expected = 
                "class Test103 {\n" + 
                "    private int a;\n" + 
                "    public void m() {\n" + 
                "        setA(2);\n" + 
                "        int s = getA();\n" + 
                "    }\n" + 
                "    private void setA(int a) {\n" + 
                "        this.a = a;\n" + 
                "    }\n" + 
                "    private int getA() {\n" + 
                "        return a;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice103_5() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test103", 15, 12);
        
        String expected = 
                "class Test103 {\n" + 
                "    private int a;\n" + 
                "    public void m() {\n" + 
                "        setA(2);\n" + 
                "        incA();\n" + 
                "        incA();\n" + 
                "        int t = getA();\n" + 
                "    }\n" + 
                "    private void setA(int a) {\n" + 
                "        this.a = a;\n" + 
                "    }\n" + 
                "    private int getA() {\n" + 
                "        return a;\n" + 
                "    }\n" + 
                "    private void incA() {\n" + 
                "        a++;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice104_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test104", 5, 8);
        
        String expected = 
                "class Test104 {\n" + 
                "    public void m() {\n" + 
                "        int x;\n" + 
                "        x = 10;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice104_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test104", 6, 8);
        
        String expected = 
                "class Test104 {\n" + 
                "    public void m() {\n" + 
                "        int y = 1;\n" + 
                "        y = 20;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice104_3() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test104", 7, 8);
        
        String expected = 
                "class Test104 {\n" + 
                "    public void m() {\n" + 
                "        int z;\n" + 
                "        z = 30;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice104_4() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test104", 8, 12);
        
        String expected = 
                "class Test104 {\n" + 
                "    public void m() {\n" + 
                "        int y = 1, z;\n" + 
                "        y = 20;\n" + 
                "        z = 30;\n" + 
                "        int p = y + z;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice104_5() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test104", 9, 8);
        
        String expected = 
                "class Test104 {\n" + 
                "    public void m() {\n" + 
                "        int x, y = 1;\n" + 
                "        x = 10;\n" + 
                "        y = x + 1;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice104_6() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test104", 10, 8);
        
        String expected = 
                "class Test104 {\n" + 
                "    public void m() {\n" + 
                "        int x, z;\n" + 
                "        x = 10;\n" + 
                "        z = x + 2;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice105_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test105", 6, 12);
        
        String expected = 
                "class Test105 {\n" + 
                "    public void m() {\n" + 
                "        int x = setA(1);\n" + 
                "        int y = x;\n" + 
                "    }\n" + 
                "    private int setA(int a) {\n" + 
                "        return a;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice105_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test105", 7, 12);
        
        String expected = 
                "class Test105 {\n" + 
                "    private int a;\n" + 
                "    public void m() {\n" + 
                "        setA(1);\n" + 
                "        int z = a;\n" + 
                "    }\n" + 
                "    private int setA(int a) {\n" + 
                "        this.a = a;\n" + 
                "        return 0;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice106_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test106", 7, 12);
        
        String expected = 
                "class Test106 {\n" + 
                "    public void m() {\n" + 
                "        int x;\n" + 
                "        x = setA(1);\n" + 
                "        int y = x;\n" + 
                "    }\n" + 
                "    private int setA(int a) {\n" + 
                "        return a;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice106_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test106", 8, 12);
        
        String expected = 
                "class Test106 {\n" + 
                "    private int a;\n" + 
                "    public void m() {\n" + 
                "        setA(1);\n" + 
                "        int z = a;\n" + 
                "    }\n" + 
                "    private int setA(int a) {\n" + 
                "        this.a = a;\n" + 
                "        return 0;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice107_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test107", 7, 12);
        //System.out.println(result);
        String expected = 
                "class Test107 {\n" + 
                "    public void m() {\n" + 
                "        int i = 0;\n" + 
                "        a[i++] = 2;\n" + 
                "        int j = i;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice108_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test108", 12, 12);
        //System.out.println(result);
        String expected = 
                "class Test108 {\n" + 
                "    public void m() {\n" + 
                "        int x = 10;\n" + 
                "        int y = 0;;\n" + 
                "        if (x > 10) {\n" + 
                "            y++;\n" + 
                "        } else {\n" + 
                "        }\n" + 
                "        int p = y;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice108_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test108", 13, 12);
        //System.out.println(result);
        String expected = 
                "class Test108 {\n" + 
                "    public void m() {\n" + 
                "        int x = 10;\n" + 
                "        int z = 0;;\n" + 
                "        if (x > 10) {\n" + 
                "        } else {\n" + 
                "            z = x + 2;\n" + 
                "        }\n" + 
                "        int q = z;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice109_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test109", 11, 12);
        //System.out.println(result);
        String expected =
                "class Test109 {\n" + 
                "    public void m() {\n" + 
                "        int x = 10;\n" + 
                "        int y = 0;\n" + 
                "        if (x > 10)\n" + 
                "            y++;\n" + 
                "        int p = y;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice109_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test109", 12, 12);
        //System.out.println(result);
        String expected = 
                "class Test109 {\n" + 
                "    public void m() {\n" + 
                "        int x = 10;\n" + 
                "        int z = 0;\n" + 
                "        if (x > 10)\n" + 
                "            ;\n" + 
                "        else\n" + 
                "            z = x + 2;\n" + 
                "        int q = z;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice110_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test110", 9, 12);
        //System.out.println(result);
        String expected = 
                "class Test110 {\n" + 
                "    public void m() {\n" + 
                "        int x = 0;\n" + 
                "        while (x < 10) {\n" + 
                "            x = x + 1;\n" + 
                "        }\n" + 
                "        int p = x;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice110_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test110", 10, 12);
        //System.out.println(result);
        String expected = 
                "class Test110 {\n" + 
                "    public void m() {\n" + 
                "        int y = 0;\n" + 
                "        int q = y;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice110_3() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test110", 7, 16);
        //System.out.println(result);
        String expected = 
                "class Test110 {\n" + 
                "    public void m() {\n" + 
                "        int x = 0;\n" + 
                "        while (x < 10) {\n" + 
                "            x = x + 1;\n" + 
                "        }\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice111_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test111", 15, 12);
        //System.out.println(result);
        String expected = 
                "class Test111 {\n" + 
                "    public void m() {\n" + 
                "        int x = 0;\n" + 
                "        int y = 0;\n" + 
                "        switch (x) {\n" + 
                "            case 1 :\n" + 
                "                y = 10;\n" + 
                "                break;\n" + 
                "            case 2 :\n" + 
                "                break;\n" + 
                "        }\n" + 
                "        int p = y;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice111_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test111", 16, 12);
        //System.out.println(result);
        String expected = 
                "class Test111 {\n" + 
                "    public void m() {\n" + 
                "        int x = 0;\n" + 
                "        int z = 0;\n" + 
                "        switch (x) {\n" + 
                "            case 1 :\n" + 
                "                break;\n" + 
                "            case 2 :\n" + 
                "                z = 20;\n" + 
                "                break;\n" + 
                "        }\n" + 
                "        int q = z;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice112_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test112", 17, 12);
        //System.out.println(result);
        String expected =
                "class Test112 {\n" + 
                "    public void m() {\n" + 
                "        int x = 0;\n" + 
                "        int y = 0;\n" + 
                "        switch (x) {\n" + 
                "            default :\n" + 
                "            case 1 :\n" + 
                "                y = 10;\n" + 
                "                break;\n" + 
                "            case 2 :\n" + 
                "                break;\n" + 
                "        }\n" + 
                "        int p = y;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice112_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test112", 18, 12);
        //System.out.println(result);
        String expected =
                "class Test112 {\n" + 
                "    public void m() {\n" + 
                "        int x = 0;\n" + 
                "        int z = 0;\n" + 
                "        switch (x) {\n" + 
                "            default :\n" + 
                "            case 1 :\n" + 
                "                break;\n" + 
                "            case 2 :\n" + 
                "                z = 20;\n" + 
                "                break;\n" + 
                "        }\n" + 
                "        int q = z;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice112_3() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test112", 19, 12);
        //System.out.println(result);
        String expected = 
                "class Test112 {\n" + 
                "    public void m() {\n" + 
                "        int x = 0;\n" + 
                "        switch (x) {\n" + 
                "            default :\n" + 
                "                x = 10;\n" + 
                "            case 1 :\n" + 
                "                break;\n" + 
                "            case 2 :\n" + 
                "                break;\n" + 
                "        }\n" + 
                "        int r = x;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice113_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test113", 12, 12);
        //System.out.println(result);
        String expected = 
                "class Test113 {\n" + 
                "    public void m() {\n" + 
                "        int x = 0;\n" + 
                "        if (x == 0) {\n" + 
                "            while (x < 10) {\n" + 
                "                x = x + 1;\n" + 
                "            }\n" + 
                "        }\n" + 
                "        int p = x;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice113_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test113", 13, 12);
        //System.out.println(result);
        String expected = 
                "class Test113 {\n" + 
                "    public void m() {\n" + 
                "        int x = 0;\n" + 
                "        int y = 0;\n" + 
                "        if (x == 0) {\n" + 
                "            y = 10;\n" + 
                "        }\n" + 
                "        int q = y;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice114_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test114", 12, 12);
        //System.out.println(result);
        String expected = 
                "class Test114 {\n" + 
                "    public void m() {\n" + 
                "        int x = 0;\n" + 
                "        if (x == 0)\n" + 
                "            while (x < 10) {\n" + 
                "                x = x + 1;\n" + 
                "            }\n" + 
                "        int p = x;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice114_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test114", 13, 12);
        //System.out.println(result);
        String expected = 
                "class Test114 {\n" + 
                "    public void m() {\n" + 
                "        int x = 0;\n" + 
                "        int y = 0;\n" + 
                "        if (x == 0)\n" + 
                "            ;\n" + 
                "        else\n" + 
                "            y = 10;\n" + 
                "        int q = y;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice115_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test115", 11, 12);
        //System.out.println(result);
        String expected = 
                "class Test115 {\n" + 
                "    public void m() {\n" + 
                "        int[] a = {1, 2, 3, 4, 5};\n" + 
                "        int x = 0;\n" + 
                "        for (int i = 0; i < 5; i++) {\n" + 
                "            x = x + a[i];\n" + 
                "        }\n" + 
                "        int p = x;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice115_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test115", 12, 12);
        //System.out.println(result);
        String expected = 
                "class Test115 {\n" + 
                "    public void m() {\n" + 
                "        int[] a = {1, 2, 3, 4, 5};\n" + 
                "        int y = 1;\n" + 
                "        for (int i = 0; i < 5; i++) {\n" + 
                "            y = y * a[i];\n" + 
                "        }\n" + 
                "        int q = y;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice116_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test116", 15, 12);
        //System.out.println(result);
        String expected = 
                "class Test116 {\n" + 
                "    public void m() {\n" + 
                "        int[] a = {1, 2, 3, 4, 5};\n" + 
                "        int x = 0;\n" + 
                "        for (int i = 0; i < 5; i++) {\n" + 
                "            if (x > 2) {\n" + 
                "                x += a[i];\n" + 
                "            }\n" + 
                "        }\n" + 
                "        int p = x;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice116_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test116", 16, 12);
        //System.out.println(result);
        String expected = 
                "class Test116 {\n" + 
                "    public void m() {\n" + 
                "        int[] a = {1, 2, 3, 4, 5};\n" + 
                "        int y = 1;\n" + 
                "        for (int i = 0; i < 5; i++) {\n" + 
                "            if (y > 3) {\n" + 
                "                y *= a[i];\n" + 
                "            }\n" + 
                "        }\n" + 
                "        int q = y;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice117_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test117", 9, 12);
        //System.out.println(result);
        String expected = 
                "class Test117 {\n" + 
                "    public void m() {\n" + 
                "        int x = 0;\n" + 
                "        for (int i = 0; i < 5; i++, x++) {\n" + 
                "        }\n" + 
                "        int p = x;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice117_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test117", 10, 12);
        //System.out.println(result);
        String expected = 
                "class Test117 {\n" + 
                "    public void m() {\n" + 
                "        int y = 0;\n" + 
                "        for (int i = 0; i < 5; i++) {\n" + 
                "            y++;\n" + 
                "        }\n" + 
                "        int q = y;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice118_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test118", 4, 12);
        //System.out.println(result);
        String expected = 
                "class Test118 {\n" + 
                "    public void m() {\n" + 
                "        int x = m0();\n" + 
                "    }\n" + 
                "    public int m0() {\n" + 
                "        return 1;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice118_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test118", 5, 12);
        //System.out.println(result);
        String expected = 
                "class Test118 {\n" + 
                "    public void m() {\n" + 
                "        int y = m1(1);\n" + 
                "    }\n" + 
                "    public int m1(int a) {\n" + 
                "        return a + 1;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice118_3() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test118", 6, 12);
        //System.out.println(result);
        String expected = 
                "class Test118 {\n" + 
                "    public void m() {\n" + 
                "        int z = m2(2);\n" + 
                "    }\n" + 
                "    public int m2(int b) {\n" + 
                "        return b + 1;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice118_4() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test118", 7, 12);
        //System.out.println(result);
        String expected = 
                "class Test118 {\n" + 
                "    public void m() {\n" + 
                "        int p = m3(3);\n" + 
                "    }\n" + 
                "    public int m3(int c) {\n" + 
                "        return c + 1;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice118_5() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test118", 8, 12);
        //System.out.println(result);
        String expected = 
                "class Test118 {\n" + 
                "    public void m() {\n" + 
                "        int q = m4(1, 2);\n" + 
                "    }\n" + 
                "    public int m4(int a, int b) {\n" + 
                "        return a + b;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice118_6() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test118", 9, 12);
        //System.out.println(result);
        String expected = 
                "class Test118 {\n" + 
                "    public void m() {\n" + 
                "        int r = m5(2, 3);\n" + 
                "    }\n" + 
                "    public int m5(int b, int c) {\n" + 
                "        return b + c;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice118_7() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test118", 10, 12);
        //System.out.println(result);
        String expected = 
                "class Test118 {\n" + 
                "    public void m() {\n" + 
                "        int s = m6(1, 3);\n" + 
                "    }\n" + 
                "    public int m6(int a, int c) {\n" + 
                "        return a + c;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice118_8() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test118", 11, 12);
        //System.out.println(result);
        String expected = 
                "class Test118 {\n" + 
                "    public void m() {\n" + 
                "        int t = m7(1, 2, 3);\n" + 
                "    }\n" + 
                "    public int m7(int a, int b, int c) {\n" + 
                "        return a + b + c;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice118_9() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test118", 12, 12);
        //System.out.println(result);
        String expected = 
                "class Test118 {\n" + 
                "    public void m() {\n" + 
                "        int u = m8(1, 2, 3);\n" + 
                "    }\n" + 
                "    public int m8(int a, int b, int c) {\n" + 
                "        return 1;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice119_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test119", 8, 12);
        //System.out.println(result);
        String expected = 
                "class Test119 {\n" + 
                "    public void m() {\n" + 
                "        A119 a = new A119();\n" + 
                "        a.setX(1);\n" + 
                "        int b = a.getX();\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice119_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test119", 8, 16);
        //System.out.println(result);
        String expected = 
                "class Test119 {\n" + 
                "    public void m() {\n" + 
                "        A119 a = new A119();\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice119_3() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test119", 10, 12); 
        //System.out.println(result);
        String expected = 
                "class Test119 {\n" + 
                "    public void m() {\n" + 
                "        A119 a = new A119();\n" + 
                "        a.setX(2);\n" + 
                "        int c = a.getX();\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice119_4() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test119", 9, 8); 
        //System.out.println(result);
        String expected = 
                "class Test119 {\n" + 
                "    public void m() {\n" + 
                "        A119 a = new A119();\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice119_5() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test119", 11, 12); 
        //System.out.println(result);
        String expected = 
                "class Test119 {\n" + 
                "    private int p;\n" + 
                "    public void m() {\n" + 
                "        p = 10;\n" + 
                "        A119 a = new A119();\n" + 
                "        a.setX(2);\n" + 
                "        int d = a.x + p;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice119_6() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test119", 11, 16); 
        //System.out.println(result);
        String expected = 
                "class Test119 {\n" + 
                "    public void m() {\n" + 
                "        A119 a = new A119();\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice119_7() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test119", 11, 18); 
        //System.out.println(result);
        String expected = 
                "class Test119 {\n" + 
                "    public void m() {\n" + 
                "        A119 a = new A119();\n" + 
                "        a.setX(2);\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice119_8() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test119", 12, 12); 
        //System.out.println(result);
        String expected = 
                "class Test119 {\n" + 
                "    private int p;\n" + 
                "    public void m() {\n" + 
                "        p = 10;\n" + 
                "        int e = getP() + 2;\n" + 
                "    }\n" + 
                "    private int getP() {\n" + 
                "        return p;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice120_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test120", 5, 12);
        //System.out.println(result);
        String expected = 
                "class Test120 {\n" + 
                "    public void m() {\n" + 
                "        int p = m0();\n" + 
                "    }\n" + 
                "    public int m0() {\n" + 
                "        return 1;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice120_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test120", 6, 12);
        //System.out.println(result);
        String expected = 
                "class Test120 {\n" + 
                "    public void m() {\n" + 
                "        A120 a = new A120();\n" + 
                "        int q = m1(a.x);\n" + 
                "    }\n" + 
                "    public int m1(int a) {\n" + 
                "        return a + 1;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice120_3() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test120", 7, 12);
        //System.out.println(result);
        String expected = 
                "class Test120 {\n" + 
                "    public void m() {\n" + 
                "        A120 a = new A120();\n" + 
                "        int r = m2(a.y);\n" + 
                "    }\n" + 
                "    public int m2(int b) {\n" + 
                "        return b + 1;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice120_4() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test120", 8, 12);
        //System.out.println(result);
        String expected = 
                "class Test120 {\n" + 
                "    public void m() {\n" + 
                "        A120 a = new A120();\n" + 
                "        int s = m3(a.x, a.y);\n" + 
                "    }\n" + 
                "    public int m3(int a, int b) {\n" + 
                "        return a + b;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice121_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test121", 10, 12);
        //System.out.println(result);
        String expected = 
                "class Test121 {\n" + 
                "    public int m(int x) {\n" + 
                "        int p = x + 1;\n" +
                "        return 0;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice121_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test121", 11, 12);
        //System.out.println(result);
        String expected = 
                "class Test121 {\n" + 
                "    public int m(int y) {\n" + 
                "        int q = y + 1;\n" +
                "        return 0;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice121_3() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test121", 6, 12);
        //System.out.println(result);
        String expected = 
                "class Test121 {\n" + 
                "    public void m() {\n" + 
                "        int b = 2;\n" +
                "        int c = m(b);\n" + 
                "    }\n" + 
                "    public int m(int y) {\n" + 
                "        int q = y + 1;\n" +
                "        return q;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice122_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test122", 15, 12);
        //System.out.println(result);
        String expected = 
                "class Test122 {\n" + 
                "    public void m() {\n" + 
                "        int a = 2;\n" + 
                "        int b = 0;\n" + 
                "        try {\n" + 
                "            b = n(a);\n" + 
                "        } catch (Exception e) {\n" + 
                "        } finally {\n" +
                "        }\n" + 
                "        int c = b;\n" + 
                "    }\n" + 
                "    public int n(int x) throws Exception {\n" + 
                "        if (x == 0) {\n" + 
                "            throw new Exception();\n" + 
                "        }\n" + 
                "        return 10 / x;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice122_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test122", 9, 16);
        //System.out.println(result);
        String expected = 
                "class Test122 {\n" + 
                "    public void m() {\n" + 
                "        int a = 2;\n" + 
                "        int b = 0;\n" + 
                "        try {\n" + 
                "            b = n(a);\n" + 
                "            int q = b + 5;\n" + 
                "        } catch (Exception e) {\n" + 
                "        } finally {\n" +
                "        }\n" + 
                "    }\n" + 
                "    public int n(int x) throws Exception {\n" + 
                "        if (x == 0) {\n" + 
                "            throw new Exception();\n" + 
                "        }\n" + 
                "        return 10 / x;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice122_3() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test122", 11, 22);
        //System.out.println(result);
        String expected = 
                "class Test122 {\n" + 
                "    public void m() {\n" + 
                "        int a = 2;\n" + 
                "        try {\n" + 
                "            n(a);\n" + 
                "        } catch (Exception e) {\n" + 
                "            Exception f = e;\n" + 
                "        } finally {\n" +
                "        }\n" + 
                "    }\n" + 
                "    public int n(int x) throws Exception {\n" + 
                "        if (x == 0) {\n" + 
                "            throw new Exception();\n" + 
                "        }\n" + 
                "        return 0;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice122_4() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test122", 13, 16);
        //System.out.println(result);
        String expected = 
                "class Test122 {\n" + 
                "    public void m() {\n" + 
                "        int a = 2;\n" + 
                "        int b = 0;\n" + 
                "        try {\n" + 
                "            b = n(a);\n" + 
                "        } catch (Exception e) {\n" + 
                "        } finally {\n" + 
                "            int r = b + 7;\n" + 
                "        }\n" + 
                "    }\n" + 
                "    public int n(int x) throws Exception {\n" + 
                "        if (x == 0) {\n" + 
                "            throw new Exception();\n" + 
                "        }\n" + 
                "        return 10 / x;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice122_5() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test122", 10, 27);
        //System.out.println(result);
        String expected = 
                "class Test122 {\n" + 
                "    public void m() {\n" + 
                "        int a = 2;\n" + 
                "        try {\n" + 
                "            n(a);\n" + 
                "        } catch (Exception e) {\n" + 
                "        } finally {\n" + 
                "        }\n" + 
                "    }\n" + 
                "    public int n(int x) throws Exception {\n" + 
                "        if (x == 0) {\n" + 
                "            throw new Exception();\n" + 
                "        }\n" + 
                "        return 0;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice123_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test123", 15, 12);
        //System.out.println(result);
        String expected = 
                "class Test123 {\n" + 
                "    public void m() {\n" + 
                "        int a = 2;\n" + 
                "        int b = 0;\n" + 
                "        try {\n" + 
                "            b = n(a);\n" + 
                "        } catch (Exception e) {\n" + 
                "        } finally {\n" +
                "        }\n" + 
                "        int c = b;\n" + 
                "    }\n" + 
                "    public int n(int x) throws SubException {\n" + 
                "        if (x == 0) {\n" + 
                "            throw new SubSubException();\n" + 
                "        }\n" + 
                "        return 10 / x;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice123_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test123", 9, 16);
        //System.out.println(result);
        String expected = 
                "class Test123 {\n" + 
                "    public void m() {\n" + 
                "        int a = 2;\n" + 
                "        int b = 0;\n" + 
                "        try {\n" + 
                "            b = n(a);\n" + 
                "            int q = b + 5;\n" + 
                "        } catch (Exception e) {\n" + 
                "        } finally {\n" +
                "        }\n" + 
                "    }\n" + 
                "    public int n(int x) throws SubException {\n" + 
                "        if (x == 0) {\n" + 
                "            throw new SubSubException();\n" + 
                "        }\n" + 
                "        return 10 / x;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice123_3() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test123", 11, 22);
        //System.out.println(result);
        String expected = 
                "class Test123 {\n" + 
                "    public void m() {\n" + 
                "        int a = 2;\n" + 
                "        try {\n" + 
                "            n(a);\n" + 
                "        } catch (Exception e) {\n" + 
                "            Exception f = e;\n" + 
                "        } finally {\n" +
                "        }\n" + 
                "    }\n" + 
                "    public int n(int x) throws SubException {\n" + 
                "        if (x == 0) {\n" + 
                "            throw new SubSubException();\n" + 
                "        }\n" + 
                "        return 0;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice123_4() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test123", 13, 16);
        //System.out.println(result);
        String expected = 
                "class Test123 {\n" + 
                "    public void m() {\n" + 
                "        int a = 2;\n" + 
                "        int b = 0;\n" + 
                "        try {\n" + 
                "            b = n(a);\n" + 
                "        } catch (Exception e) {\n" + 
                "        } finally {\n" + 
                "            int r = b + 7;\n" + 
                "        }\n" + 
                "    }\n" + 
                "    public int n(int x) throws SubException {\n" + 
                "        if (x == 0) {\n" + 
                "            throw new SubSubException();\n" + 
                "        }\n" + 
                "        return 10 / x;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice123_5() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test123", 10, 27);
        //System.out.println(result);
        String expected = 
                "class Test123 {\n" + 
                "    public void m() {\n" + 
                "        int a = 2;\n" + 
                "        try {\n" + 
                "            n(a);\n" + 
                "        } catch (Exception e) {\n" + 
                "        } finally {\n" + 
                "        }\n" + 
                "    }\n" + 
                "    public int n(int x) throws SubException {\n" + 
                "        if (x == 0) {\n" + 
                "            throw new SubSubException();\n" + 
                "        }\n" + 
                "        return 0;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice124_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test124", 7, 13);
        //System.out.println(result);
        String expected = 
                "class Test124 {\n" + 
                "    public void m() {\n" + 
                "        int p = 10;\n" + 
                "        A124 a = new A124(p);\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice124_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test124", 8, 12);
        //System.out.println(result);
        String expected = 
                "class Test124 {\n" + 
                "    public void m() {\n" + 
                "        int p = 10;\n" + 
                "        A124 a = new A124(p);\n" + 
                "        int b = a.getX();\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice124_3() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test124", 9, 12);
        //System.out.println(result);
        String expected = 
                "class Test124 {\n" + 
                "    public void m() {\n" + 
                "        int p = 10;\n" + 
                "        int q = 20;\n" + 
                "        A124 a = new A124(p);\n" + 
                "        int c = a.inc(q);\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice125_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test125", 6, 12);
        //System.out.println(result);
        String expected = 
                "class Test125 {\n" + 
                "    public void m() {\n" + 
                "        int p = 0;\n" + 
                "        int q = inc1(p);\n" + 
                "    }\n" + 
                "    public int inc1(int x) {\n" + 
                "        return x + 1;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice125_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test125", 7, 12);
        //System.out.println(result);
        String expected = 
                "class Test125 {\n" + 
                "    public void m() {\n" + 
                "        int p = 0;\n" + 
                "        int r = inc2(inc1(p));\n" + 
                "    }\n" + 
                "    public int inc1(int x) {\n" + 
                "        return x + 1;\n" + 
                "    }\n" + 
                "    public int inc2(int x) {\n" + 
                "        return x + 2;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice126_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test126", 8, 13);
        //System.out.println(result);
        String expected = 
                "class Test126 {\n" + 
                "    public void m() {\n" + 
                "        A126 a = new A126();\n" + 
                "        int p = 0;\n" + 
                "        a.y = 1;\n" + 
                "        A126 a2 = a.add(p);\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice126_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test126", 10, 12);
        //System.out.println(result);
        String expected = 
                "class Test126 {\n" + 
                "    public void m() {\n" + 
                "        A126 a = new A126();\n" + 
                "        int p = 0;\n" + 
                "        a.y = 1;\n" + 
                "        A126 a2 = a.add(p);\n" + 
                "        a2.y = 2;\n" +
                "        int q = a2.getY();\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice126_3() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test126", 11, 12);
        //System.out.println(result);
        String expected = 
                "class Test126 {\n" + 
                "    public void m() {\n" + 
                "        A126 a = new A126();\n" + 
                "        int p = 0;\n" + 
                "        a.y = 1;\n" + 
                "        int r = a.add(p).getY();\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice127_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test127", 8, 12);
        //System.out.println(result);
        String expected = 
                "class Test127 {\n" + 
                "    public void m() {\n" + 
                "        A127 a = new A127();\n" + 
                "        a.setY(2);\n" + 
                "        int p = a.getY();\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice127_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test127", 9, 12);
        //System.out.println(result);
        String expected = 
                "class Test127 {\n" + 
                "    public void m() {\n" + 
                "        A127.z = 1;\n" + 
                "        int q = A127.z;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice128_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test128", 8, 12);
        //System.out.println(result);
        String expected = 
                "class Test128 {\n" + 
                "    public void m() {\n" + 
                "        A128 a = new A128();\n" + 
                "        int p = 0;\n" + 
                "        a.setY(2);\n" + 
                "        int r = n(a.add(p).getY());\n" + 
                "    }\n" + 
                "    public int n(int y) {\n" + 
                "        return y + 4;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice129_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test129", 13, 15);
        //System.out.println(result);
        String expected = 
                "class Test129 {\n" + 
                "    private S129 s1 = new S129();\n" + 
                "    public void m() {\n" + 
                "        s1.getP().set1(\"A\", \"AAAA\");\n" + 
                "        String v1 = s1.getP().get1(\"A\");\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice129_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test129", 15, 15);
        //System.out.println(result);
        String expected = 
                "class Test129 {\n" + 
                "    private S129 s2 = new S129();\n" + 
                "    public void m() {\n" + 
                "        S129 s3 = s2;\n" + 
                "        String v2 = s3.getP().get2(\"B\");\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice129_3() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test129", 19, 15);
        //System.out.println(result);
        String expected = 
                "class Test129 {\n" + 
                "    public void m() {\n" + 
                "        T129 t = new T129();\n" + 
                "        t.set1(\"C\", \"CCCC\");\n" + 
                "        String v3 = t.get1(\"C\");\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice129_4() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test129", 23, 15);
        //System.out.println(result);
        String expected = 
                "class Test129 {\n" + 
                "    public void m() {\n" + 
                "        U129 u = new U129();\n" + 
                "        u.set1(\"D\", \"DDDD\");\n" + 
                "        String v4 = u.get1(\"D\");\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice130_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test130", 6, 16);
        //System.out.println(result);
        String expected = 
                "class Test130 {\n" + 
                "    public void m() {\n" + 
                "        int q = n(2).getX();\n" + 
                "    }\n" + 
                "    public S130 n(int x) {\n" + 
                "        return new S130(x);\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice131_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test131", 10, 15);
        //System.out.println(result);
        String expected = 
                "class Test131 {\n" + 
                "    private HashMap<String, String> map = new HashMap<>();\n" + 
                "    public void m() {\n" + 
                "        map.put(\"A\", \"AA\");\n" + 
                "        String x = map.get(\"A\");\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice132_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test132", 6, 12);
        //System.out.println(result);
        String expected = 
                "class Test132 {\n" + 
                "    public void m() {\n" + 
                "        int y = n().x;\n" + 
                "    }\n" + 
                "    P132 n() {\n" + 
                "        P132 p = new P132();\n" + 
                "        p.x = 1;\n" + 
                "        return p;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice133_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test133", 8, 12);
        //System.out.println(result);
        String expected = 
                "class Test133 {\n" + 
                "    int a = 1;\n" + 
                "    public void m() {\n" + 
                "        int p1 = a;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice134_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test134", 7, 12);
        //System.out.println(result);
        String expected = 
                "class Test134 {\n" + 
                "    public void m() {\n" + 
                "        int x = 1;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice135_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test135", 12, 15);
        //System.out.println(result);
        String expected = 
                "class Test135 {\n" + 
                "    private S135 s1 = new S135();\n" + 
                "    public void m() {\n" + 
                "        s1.set2(\"A\", \"AAAA\");\n" + 
                "        String v1 = s1.get2(\"A\");\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice135_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test135", 13, 15);
        //System.out.println(result);
        String expected = 
                "class Test135 {\n" + 
                "    private S135 s2 = new S135();\n" + 
                "    public void m() {\n" + 
                "        s2.set2(\"B\", \"BBBB\");\n" + 
                "        String v2 = s2.get2(\"B\");\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice136_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test136", 13, 15);
        //System.out.println(result);
        String expected = 
                "class Test136 {\n" + 
                "    private S136 s1 = new S136();\n" + 
                "    public void m() {\n" + 
                "        s1.set(\"A\", \"AAAA\");\n" + 
                "        String v1 = s1.get(\"A\");\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice136_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test136", 14, 15);
        //System.out.println(result);
        String expected = 
                "class Test136 {\n" + 
                "    private S136 s2 = new S136();\n" + 
                "    public void m() {\n" + 
                "        s2.set(\"B\", \"BBBB\");\n" + 
                "        String v2 = s2.get(\"B\");\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice137_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test137", 8, 15);
        //System.out.println(result);
        String expected = 
                "class Test137 {\n" + 
                "    public void m() {\n" + 
                "        P137 s1 = new P137();\n" + 
                "        s1.add(\"AAAA\");\n" + 
                "        String s = s1.get(0);\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice138_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test138", 12, 18);
        //System.out.println(result);
        String expected = 
                "class Test138 {\n" + 
                "    public void m() {\n" + 
                "        P138 s1 = new P138();\n" + 
                "        String a = \"A\";\n" + 
                "        s1.add(a);\n" + 
                "        s1.add(\"B\");\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testSlice138_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test138", 13, 18);
        //System.out.println(result);
        String expected = 
                "class Test138 {\n" + 
                "    public void m() {\n" + 
                "        P138 s2 = new P138();\n" + 
                "        s2.add(\"C\");\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testTest200_1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test200", 11, 12);
        
        String expected = 
                "/*\n" + 
                "  Class Comment\n" + 
                "*/\n" + 
                "class Test200 {\n" + 
                "    /*\n" + 
                "     * Method Comment\n" + 
                "     */\n" + 
                "    public void m(int x) {\n" + 
                "        // Comment 1\n" + 
                "        int a = x + 2; // Comment 6\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testTest200_2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test200", 15, 12);
        
        String expected = 
                "/*\n" + 
                "  Class Comment\n" + 
                "*/\n" + 
                "class Test200 {\n" + 
                "    /*\n" + 
                "     * Method Comment\n" + 
                "     */\n" + 
                "    public void m(int x) {\n" + 
                "        // Comment 3\n" + 
                "        int b = x + 2;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testTest200_3() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Test200", 19, 12);
        
        String expected = 
                "/*\n" + 
                "  Class Comment\n" + 
                "*/\n" + 
                "class Test200 {\n" + 
                "    /*\n" + 
                "     * Method Comment\n" + 
                "     */\n" + 
                "    public void m(int x) {\n" + 
                "        // Comment 1\n" + 
                "        int a = x + 2; // Comment 6\n" + 
                "        /*\n" + 
                "         * Comment 4\n" + 
                "         */\n" + 
                "        int c = a;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    /*
    @Test
    public void testCustomer1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Customer", 18, 12);
        //System.out.println(result);
        String expected = 
                "class Customer {\n" + 
                "    public double discount = 0;\n" + 
                "    public String statement(Order order) {\n" + 
                "        if (order == null) {\n" + 
                "            return null;\n" + 
                "        }\n" + 
                "        if (order.getSize() > 1 && discount < 0.2) {\n" + 
                "            discount = discount * 2;\n" + 
                "        }\n" + 
                "        int amount = getAmount(order);\n" + 
                "        return null;\n" + 
                "    }\n" + 
                "    public int getAmount(Order order) {\n" + 
                "        int amount = 0;\n" + 
                "        for (Rental rental : order.rentals) {\n" + 
                "            amount += rental.getCharge(discount);\n" + 
                "        }\n" + 
                "        return amount;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testCustomer2() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Customer", 23, 31);
        //System.out.println(result);
        String expected = 
                "class Customer {\n" + 
                "    public int getAmount(Order order) {\n" + 
                "        return 0;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testCustomer3() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Customer", 24, 12);
        //System.out.println(result);
        String expected = 
                "class Customer {\n" + 
                "    public int getAmount() {\n" + 
                "        int amount = 0;\n" + 
                "        return 0;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testCustomer4() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Customer", 18, 31);
        //System.out.println(result);
        String expected = 
                "class Customer {\n" + 
                "    public String statement(Order order) {\n" + 
                "        if (order == null) {\n" + 
                "            return null;\n" + 
                "        }\n" + 
                "        return null;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testCustomer5() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "Customer", 20, 15);
        //System.out.println(result);
        String expected = 
                "class Customer {\n" + 
                "    private String name = \"\";\n" + 
                "    public String statement(Order order) {\n" + 
                "        if (order == null) {\n" + 
                "            return null;\n" + 
                "        }\n" + 
                "        return null;\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testCustomerTest1() {
        String result = SliceTestUtil.getSlicedCode(SliceProject, "CustomerTest", 11, 44);
        //System.out.println(result);
        String expected = 
                "public class CustomerTest {\n" + 
                "    public void testStatement1() {\n" + 
                "        Order order = new Order();\n" + 
                "        Rental r1 = new Rental(200, 2);\n" + 
                "        order.addRental(r1);\n" + 
                "    }\n" + 
                "    public void testStatement2() {\n" + 
                "        Order order = new Order();\n" + 
                "        Rental r1 = new Rental(200, 3);\n" + 
                "        Rental r2 = new Rental(100, 3);\n" + 
                "        order.addRental(r1);\n" + 
                "        order.addRental(r2);\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testCustomerTest2() {
        String result = getSlicedCode("CustomerTest", 23, 44);
        //System.out.println(result);
        String expected = 
                "public class CustomerTest {\n" + 
                "    public void testStatement1() {\n" + 
                "        Order order = new Order();\n" + 
                "        Rental r1 = new Rental(200, 2);\n" + 
                "        order.addRental(r1);\n" + 
                "    }\n" + 
                "    public void testStatement2() {\n" + 
                "        Order order = new Order();\n" + 
                "        Rental r1 = new Rental(200, 3);\n" + 
                "        Rental r2 = new Rental(100, 3);\n" + 
                "        order.addRental(r1);\n" + 
                "        order.addRental(r2);\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, result);
    }
    */
}
