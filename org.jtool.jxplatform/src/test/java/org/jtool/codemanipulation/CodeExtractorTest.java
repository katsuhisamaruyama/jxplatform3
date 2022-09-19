/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.codemanipulation;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.CodeManipulationTestUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CodeExtractorTest {
    
    private static JavaProject SimpleProject;
    private static JavaProject SliceProject;
    
    @BeforeClass
    public static void setUp() {
        SimpleProject = BuilderTestUtil.createProject("Simple", "", "");
        SliceProject = BuilderTestUtil.createProject("Slice", "", "");
    }
    
    @AfterClass
    public static void tearDown() {
        SimpleProject.getModelBuilder().unbuild();
        SliceProject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testExtractClass() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test01",
                new int[]{ 1 });
        
        String expected = 
                "public class Test01 {\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractInterface() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "I47",
                new int[]{ 1 });
        
        String expected = 
                "interface I47 {\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractEnum() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "PriceCode",
                new int[]{ 1 });
        
        String expected = 
                "enum PriceCode {\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractConstructor1() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test01",
                new int[]{ 1, 2 });
        
        String expected = 
                "public class Test01 {\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractConstructor2() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "P42",
                new int[]{ 2 });
        
        String expected = 
                "class P42 {\n" +
                "    P42() {\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractConstructor3() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "P42",
                new int[]{ 2, 3 });
        
        String expected = 
                "class P42 {\n" +
                "    P42(int x) {\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractConstructor4() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "P42",
                new int[]{ 2, 3, 4 });
        
        String expected = 
                "class P42 {\n" +
                "    P42(int x) {\n" +
                "        this.x = x;\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractMethod() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test01",
                new int[]{ 5 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractInitializer1() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test26",
                new int[]{ 5 });
        
        String expected = 
                "public class Test26 {\n" +
                "    {\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractInitializer2() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test26",
                new int[]{ 6 });
        
        String expected = 
                "public class Test26 {\n" +
                "    {\n" +
                "        int c = 1;\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractEnumConstructor1() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "PriceCode",
                new int[]{ 2 });
        
        String expected = 
                "enum PriceCode {\n" +
                "    ;\n" +
                "    PriceCode() {\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractEnumConstructor2() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "PriceCode",
                new int[]{ 4 });
        
        String expected = 
                "enum PriceCode {\n" +
                "    ;\n" +
                "    PriceCode() {\n" +
                "        priceCode = code;\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractEnumFieldDeclaration() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "PriceCode",
                new int[]{ 28 });
        
        String expected = 
                "enum PriceCode {\n" +
                "    ;\n" +
                "    private int priceCode;\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractEnumConstant1() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "PriceCode",
                new int[]{ 7 });
        
        String expected = 
                "enum PriceCode {\n" +
                "    CHILDRENS(100)\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractEnumConstant2() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "PriceCode",
                new int[]{ 14 });
        
        String expected = 
                "enum PriceCode {\n" +
                "    REGULAR(200)\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractEnumConstant3() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "PriceCode",
                new int[]{ 7, 14 });
        
        String expected = 
                "enum PriceCode {\n" +
                "    CHILDRENS(100), REGULAR(200)\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractEnumConstant4() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "PriceCode",
                new int[]{ 14, 21 });
        
        String expected = 
                "enum PriceCode {\n" +
                "    REGULAR(200), NEW_RELEASE(300)\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractFieldDeclaration1() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test01",
                new int[]{ 33 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public static int ABC = 0;\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractFieldDeclaration2() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test42",
                new int[]{ 26 });
        
        String expected = 
                "public class Test42 {\n" +
                "    private final P42 a = new P42(1);\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractFieldDeclaration3() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test42",
                new int[]{ 28 });
        
        String expected = 
                "public class Test42 {\n" +
                "    private final P42 a = new P42(1);\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractFieldDeclaration4() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test42",
                new int[]{ 26, 28 });
        
        String expected = 
                "public class Test42 {\n" +
                "    private final P42 a = new P42(1);\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractLocalDeclaration1() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test01",
                new int[]{ 5, 6 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        int a;\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractLocalDeclaration2() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test01",
                new int[]{ 6 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        int a;\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractLocalDeclaration3() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test01",
                new int[]{ 8 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        int b = 0;\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractAssignment1() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test01",
                new int[]{ 7 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        a = 0;\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractAssignment2() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test01",
                new int[]{ 6, 7 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        int a;\n" +
                "        a = 0;\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractAssignment3() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test01",
                new int[]{ 6, 7, 8 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        int a;\n" +
                "        a = 0;\n" +
                "        int b = 0;\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractIfStatement1() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test01",
                new int[]{ 10 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        if (a == 0) {\n" +
                "            a++;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractIfStatement2() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test01",
                new int[]{ 11 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        if (a == 0) {\n" +
                "            System.out.println(a);\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractIfStatement3() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test01",
                new int[]{ 12 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        if (a == 0) {\n" +
                "            System.out.println(a);\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractIfStatement4() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test01",
                new int[]{ 13 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        if (a == 0) {\n" +
                "            System.out.println(a);\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractIfStatement5() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test01",
                new int[]{ 15 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        if (a == 0) {\n" +
                "        } else {\n" +
                "            a++;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractIfStatement6() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test01",
                new int[]{ 16 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        if (a == 0) {\n" +
                "        } else {\n" +
                "            a = -b;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractIfStatement7() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test01",
                new int[]{ 15, 16 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        if (a == 0) {\n" +
                "        } else {\n" +
                "            a++;\n" +
                "            a = -b;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractIfStatement8() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test01",
                new int[]{ 17 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        if (a == 0) {\n" +
                "        } else {\n" +
                "            System.out.println(a);\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractIfStatement9() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test07",
                new int[]{ 13 });
        
        String expected = 
                "public class Test07 {\n" +
                "    public void m() {\n" +
                "        if (c == d) {\n" +
                "            if (a < b) {\n" +
                "                b = a;\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractIfStatement10() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test07",
                new int[]{ 16 });
        
        String expected = 
                "public class Test07 {\n" +
                "    public void m() {\n" +
                "        if (c == d) {\n" +
                "            if (a < b) {\n" +
                "            } else if (b < a) {\n" +
                "                a = b;\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractIfStatement11() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test07",
                new int[]{ 22 });
        
        String expected = 
                "public class Test07 {\n" +
                "    public void m() {\n" +
                "        if (c == d) {\n" +
                "            if (a < b) {\n" +
                "            } else if (b < a) {\n" +
                "            } else {\n" +
                "                if (a < b) {\n" +
                "                } else if (b < a) {\n" +
                "                    c = c + e;\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractIfStatement12() {
        String result = CodeManipulationTestUtil.getCode(SliceProject, "Test109",
                new int[]{ 10 });
        
        String expected = 
                "class Test109 {\n" +
                "    public void m() {\n" +
                "        if (x > 10)\n" +
                "            y++;\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractIfStatement13() {
        String result = CodeManipulationTestUtil.getCode(SliceProject, "Test109",
                new int[]{ 11 });
        
        String expected = 
                "class Test109 {\n" +
                "    public void m() {\n" +
                "        if (x > 10)\n" +
                "            ;\n" +
                "        else\n" +
                "            z = x + 2;\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractIfStatement14() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test01",
                new int[]{ 6, 7, 10 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        int a;\n" +
                "        a = 0;\n" +
                "        if (a == 0) {\n" +
                "            a++;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractIfStatement15() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test01",
                new int[]{ 6, 7, 15 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        int a;\n" +
                "        a = 0;\n" +
                "        if (a == 0) {\n" +
                "        } else {\n" +
                "            a++;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractIfStatement16() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test01",
                new int[]{ 6, 7, 10, 15 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        int a;\n" +
                "        a = 0;\n" +
                "        if (a == 0) {\n" +
                "            a++;\n" +
                "        } else {\n" +
                "            a++;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractIfStatement17() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test01",
                new int[]{ 6, 7, 10, 15, 22 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        int a;\n" +
                "        a = 0;\n" +
                "        if (a == 0) {\n" +
                "            a++;\n" +
                "        } else {\n" +
                "            a++;\n" +
                "        }\n" +
                "        System.out.println(a);\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractIfStatement18() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test01",
                new int[]{ 7, 9 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        a = 0;\n" +
                "        if (a == 0) {\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractExpressionStatement() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test01",
                new int[]{ 22 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        System.out.println(a);\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractMethodInvocation1() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test04",
                new int[]{ 9 });
        
        String expected = 
                "public class Test04 {\n" +
                "    public void m() {\n" +
                "        doReturn();\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractMethodInvocation2() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test04",
                new int[]{ 14 });
        
        String expected = 
                "public class Test04 {\n" +
                "    public void m() {\n" +
                "        System.out.println(out);\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractReturnStatement1() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test04",
                new int[]{ 22 });
        
        String expected = 
                "public class Test04 {\n" +
                "    public int doReturn() {\n" +
                "        if (in > 0)\n" +
                "            return in;\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractReturnStatement2() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test04",
                new int[]{ 24 });
        
        String expected = 
                "public class Test04 {\n" +
                "    public int doReturn() {\n" +
                "        return 0;\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractReturnStatement3() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test11",
                new int[]{ 22 });
        
        String expected = 
                "public class Test11 {\n" +
                "    public int doReturn() {\n" +
                "        if (in == 2) {\n" +
                "            return ++in;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractEmptyStatement1() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test02",
                new int[]{ 6 });
        
        String expected = 
                "public class Test02 {\n" +
                "    public void m() {\n" +
                "        int a = 0;\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractEmptyStatement2() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test02",
                new int[]{ 7 });
        
        String expected = 
                "public class Test02 {\n" +
                "    public void m() {\n" +
                "        ;\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractEmptyStatement3() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test02",
                new int[]{ 8 });
        
        String expected = 
                "public class Test02 {\n" +
                "    public void m() {\n" +
                "        int b = 0;\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractWhileStatement1() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test02",
                new int[]{ 9, 11 });
        
        String expected = 
                "public class Test02 {\n" +
                "    public void m() {\n" +
                "        int c = 0;\n" +
                "        while (a < 5 + d) {\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractWhileStatement2() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test02",
                new int[]{ 13 });
        
        String expected = 
                "public class Test02 {\n" +
                "    public void m() {\n" +
                "        while (a < 5 + d) {\n" +
                "            a++;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractWhileStatement3() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test02",
                new int[]{ 12, 13 });
        
        String expected = 
                "public class Test02 {\n" +
                "    public void m() {\n" +
                "        while (a < 5 + d) {\n" +
                "            c = a;\n" +
                "            a++;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractWhileStatement4() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test02",
                new int[]{ 6, 8, 12, 13 });
        
        String expected = 
                "public class Test02 {\n" +
                "    public void m() {\n" +
                "        int a = 0;\n" +
                "        int b = 0;\n" +
                "        while (a < 5 + d) {\n" +
                "            c = a;\n" +
                "            a++;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractWhileStatement5() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test02",
                new int[]{ 6, 8, 12, 13, 27 });
        
        String expected = 
                "public class Test02 {\n" +
                "    public void m() {\n" +
                "        int a = 0;\n" +
                "        int b = 0;\n" +
                "        while (a < 5 + d) {\n" +
                "            c = a;\n" +
                "            a++;\n" +
                "        }\n" +
                "        System.out.println(d);\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractSwitchStatement1() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test03",
                new int[]{ 6, 8 });
        
        String expected = 
                "public class Test03 {\n" +
                "    public void m() {\n" +
                "        int a = 0;\n" +
                "        switch (a) {\n" +
                "            case 0 :\n" +
                "                break;\n" +
                "            case 1 :\n" +
                "                break;\n" +
                "            case 2 :\n" +
                "                break;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractSwitchStatement2() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test03",
                new int[]{ 6, 9 });
        
        String expected = 
                "public class Test03 {\n" +
                "    public void m() {\n" +
                "        int a = 0;\n" +
                "        switch (a) {\n" +
                "            case 0 :\n" +
                "                break;\n" +
                "            case 1 :\n" +
                "                break;\n" +
                "            case 2 :\n" +
                "                break;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractSwitchStatement3() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test03",
                new int[]{ 6, 10 });
        
        String expected = 
                "public class Test03 {\n" +
                "    public void m() {\n" +
                "        int a = 0;\n" +
                "        switch (a) {\n" +
                "            case 0 :\n" +
                "                System.out.println(a);\n" +
                "                break;\n" +
                "            case 1 :\n" +
                "                break;\n" +
                "            case 2 :\n" +
                "                break;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractSwitchStatement4() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test03",
                new int[]{ 6, 10 });
        
        String expected = 
                "public class Test03 {\n" +
                "    public void m() {\n" +
                "        int a = 0;\n" +
                "        switch (a) {\n" +
                "            case 0 :\n" +
                "                System.out.println(a);\n" +
                "                break;\n" +
                "            case 1 :\n" +
                "                break;\n" +
                "            case 2 :\n" +
                "                break;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractIfWhileStatement1() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test05",
                new int[]{ 10 });
        
        String expected = 
                "public class Test05 {\n" +
                "    public void m() {\n" +
                "        if (c < (d = a + b + c)) {\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractIfWhileStatement2() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test05",
                new int[]{ 11 });
        
        String expected = 
                "public class Test05 {\n" +
                "    public void m() {\n" +
                "        if (c < (d = a + b + c)) {\n" +
                "            while (b < 10) {\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    @Test
    public void testExtractIfWhileStatement3() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test05",
                new int[]{ 12 });
        
        String expected = 
                "public class Test05 {\n" +
                "    public void m() {\n" +
                "        if (c < (d = a + b + c)) {\n" +
                "            while (b < 10) {\n" +
                "                b = b + 1;\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractIfWhileStatement4() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test05",
                new int[]{ 13 });
        
        String expected = 
                "public class Test05 {\n" +
                "    public void m() {\n" +
                "        if (c < (d = a + b + c)) {\n" +
                "        } else {\n" +
                "            while ((a++) < 10) {\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractIfWhileStatement5() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test05",
                new int[]{ 14 });
        
        String expected = 
                "public class Test05 {\n" +
                "    public void m() {\n" +
                "        if (c < (d = a + b + c)) {\n" +
                "        } else {\n" +
                "            while ((a++) < 10) {\n" +
                "                System.out.println(a);\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractIfSwitchStatement1() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test06",
                new int[]{ 8 });
        
        String expected = 
                "public class Test06 {\n" +
                "    public void m() {\n" +
                "        if (a == 0) {\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractIfSwitchStatement2() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test06",
                new int[]{ 9 });
        
        String expected = 
                "public class Test06 {\n" +
                "    public void m() {\n" +
                "        if (a == 0) {\n" +
                "            switch (a) {\n" +
                "                case 0 :\n" +
                "                    break;\n" +
                "                case 1 :\n" +
                "                    break;\n" +
                "                case 2 :\n" +
                "                    break;\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractIfSwitchStatement3() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test06",
                new int[]{ 10 });
        
        String expected = 
                "public class Test06 {\n" +
                "    public void m() {\n" +
                "        if (a == 0) {\n" +
                "            switch (a) {\n" +
                "                case 0 :\n" +
                "                    break;\n" +
                "                case 1 :\n" +
                "                    break;\n" +
                "                case 2 :\n" +
                "                    break;\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractIfSwitchStatement4() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test06",
                new int[]{ 11 });
        
        String expected = 
                "public class Test06 {\n" +
                "    public void m() {\n" +
                "        if (a == 0) {\n" +
                "            switch (a) {\n" +
                "                case 0 :\n" +
                "                    b++;\n" +
                "                    break;\n" +
                "                case 1 :\n" +
                "                    break;\n" +
                "                case 2 :\n" +
                "                    break;\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractForStatement1() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test21",
                new int[]{ 7 });
        
        String expected = 
                "public class Test21 {\n" +
                "    final public void m() {\n" +
                "        for (int i = 0; i < 10;) {\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractForStatement2() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test21",
                new int[]{ 8 });
        
        String expected = 
                "public class Test21 {\n" +
                "    final public void m() {\n" +
                "        for (; i < 10;) {\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractForStatement3() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test21",
                new int[]{ 10 });
        
        String expected = 
                "public class Test21 {\n" +
                "    final public void m() {\n" +
                "        for (; i < 10; i++) {\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractForStatement4() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test21",
                new int[]{ 9 });
        
        String expected = 
                "public class Test21 {\n" +
                "    final public void m() {\n" +
                "        for (; i < 10;) {\n" +
                "            a++;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractForStatement5() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test21",
                new int[]{ 16 });
        
        String expected = 
                "public class Test21 {\n" +
                "    public void m2() {\n" +
                "        for (; i < 10; i++) {\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractForStatement6() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test21",
                new int[]{ 22 });
        
        String expected = 
                "public class Test21 {\n" +
                "    public void m3() {\n" +
                "        for (;;) {\n" +
                "            if (i == 10)\n" +
                "                break;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractForStatement7() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test21",
                new int[]{ 25 });
        
        String expected = 
                "public class Test21 {\n" +
                "    public void m3() {\n" +
                "        for (;; i++) {\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractForStatement8() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test21",
                new int[]{ 29 });
        
        String expected = 
                "public class Test21 {\n" +
                "    public void m4() {\n" +
                "        for (int i = 0; i < 10;) {\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    @Test
    public void testExtractDoStatement1() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test22",
                new int[]{ 8 });
        
        String expected = 
                "public class Test22 {\n" +
                "    public void m() {\n" +
                "        do {\n" +
                "        } while (a < 19);\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractDoStatement2() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test22",
                new int[]{ 7 });
        
        String expected = 
                "public class Test22 {\n" +
                "    public void m() {\n" +
                "        do {\n" +
                "            a++;\n" +
                "        } while (a < 19);\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractEnhancedForStatement1() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test50",
                new int[]{ 10 });
        
        String expected = 
                "public class Test50 {\n" +
                "    public void m() {\n" +
                "        for (String str : strings) {\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractEnhancedForStatement2() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test50",
                new int[]{ 11 });
        
        String expected = 
                "public class Test50 {\n" +
                "    public void m() {\n" +
                "        for (String str : strings) {\n" +
                "            int len = str.length();\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractAssertStatement1() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test34",
                new int[]{ 15 });
        
        String expected = 
                "public class Test34 {\n" +
                "    void add() {\n" +
                "        assert x > 0;\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractSynchronizedStatement1() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test34",
                new int[]{ 17 });
        
        String expected = 
                "public class Test34 {\n" +
                "    void add() {\n" +
                "        synchronized (this) {\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testExtractSynchronizedStatement2() {
        String result = CodeManipulationTestUtil.getCode(SimpleProject, "Test34",
                new int[]{ 18 });
        
        String expected = 
                "public class Test34 {\n" +
                "    void add() {\n" +
                "        synchronized (this) {\n" +
                "            v = v + y;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
}
