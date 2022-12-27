/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.codemanipulation;

import static org.jtool.codemanipulation.AllCodeManipulationTests.SimpleProject;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.QualifiedName;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.pdg.ClDG;
import org.jtool.pdg.PDG;
import org.jtool.pdg.PDGNode;
import org.jtool.pdg.internal.PDGTestUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.Set;

public class CodeExtractorTest {
    
    private static String getCode(JavaProject jproject, String cname, int[] indices) {
        JavaClass jclass = jproject.getClass(cname);
        ClDG cldg = jproject.getModelBuilder().getClDG(jclass);
        
        Set<PDGNode> nodes = PDGTestUtil.getNodes(cldg, indices);
        CodeExtractor extractor = new CodeExtractor(jclass, nodes);
        String code = extractor.extract();
        return code;
    }
    
    private static String getCode(JavaProject jproject, String cname, String sig, int[] indices) {
        JavaClass jclass = jproject.getClass(cname);
        ClDG cldg = jproject.getModelBuilder().getClDG(jclass);
        QualifiedName qname = new QualifiedName(cname, sig);
        PDG pdg = cldg.getPDG(qname.fqn());
        
        Set<PDGNode> nodes = PDGTestUtil.getNodes(pdg, indices);
        CodeExtractor extractor = new CodeExtractor(jclass, nodes);
        String code = extractor.extract();
        return code;
    }
    
    @BeforeClass
    public static void setUp() {
        SimpleProject = BuilderTestUtil.getProject("Simple");
    }
    
    @Test
    public void testExtractClass() {
        String result = getCode(SimpleProject, "Test01", new int[]{ 0 });
        
        String expected = 
                "public class Test01 {\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractInterface() {
        String result = getCode(SimpleProject, "I47", new int[]{ 0 });
        
        String expected = 
                "interface I47 {\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractEnum() {
        String result = getCode(SimpleProject, "PriceCode", new int[]{ 0 });
        
        String expected = 
                "enum PriceCode {\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractConstructor1() {
        String result = getCode(SimpleProject, "Test01", "Test01( )", new int[]{ 0 });
        
        String expected = 
                "public class Test01 {\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractConstructor2() {
        String result = getCode(SimpleProject, "P42", "P42( int )", new int[]{ 0 });
        
        String expected = 
                "class P42 {\n" +
                "    P42() {\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractConstructor3() {
        String result = getCode(SimpleProject, "P42", "P42( int )", new int[]{ 0, 1 });
        
        String expected = 
                "class P42 {\n" +
                "    P42(int x) {\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractConstructor4() {
        String result = getCode(SimpleProject, "P42", "P42( int )", new int[]{ 0, 1, 2 });
        
        String expected = 
                "class P42 {\n" +
                "    P42(int x) {\n" +
                "        this.x = x;\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractMethod() {
        String result = getCode(SimpleProject, "Test01", "m( )", new int[]{ 0 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractInitializer1() {
        String result = getCode(SimpleProject, "Test26", ".init( )", new int[]{ 0 });
        
        String expected = 
                "public class Test26 {\n" +
                "    {\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractInitializer2() {
        String result = getCode(SimpleProject, "Test26", ".init( )", new int[]{ 1 });
        
        String expected = 
                "public class Test26 {\n" +
                "    {\n" +
                "        int c = 1;\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractEnumConstructor1() {
        String result = getCode(SimpleProject, "PriceCode", "PriceCode( int )", new int[]{ 0 });
        
        String expected = 
                "enum PriceCode {\n" +
                "    ;\n" +
                "    PriceCode() {\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractEnumConstructor2() {
        String result = getCode(SimpleProject, "PriceCode", "PriceCode( int )", new int[]{ 2 });
        
        String expected = 
                "enum PriceCode {\n" +
                "    ;\n" +
                "    PriceCode() {\n" +
                "        priceCode = code;\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractEnumFieldDeclaration() {
        String result = getCode(SimpleProject, "PriceCode", "priceCode", new int[]{ 0 });
        
        String expected = 
                "enum PriceCode {\n" +
                "    ;\n" +
                "    private int priceCode;\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractEnumConstant1() {
        String result = getCode(SimpleProject, "PriceCode", new int[]{ 6 });
        
        String expected = 
                "enum PriceCode {\n" +
                "    CHILDRENS(100)\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractEnumConstant2() {
        String result = getCode(SimpleProject, "PriceCode", new int[]{ 13 });
        
        String expected = 
                "enum PriceCode {\n" +
                "    REGULAR(200)\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractEnumConstant3() {
        String result = getCode(SimpleProject, "PriceCode", new int[]{ 6, 13 });
        
        String expected = 
                "enum PriceCode {\n" +
                "    CHILDRENS(100), REGULAR(200)\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractEnumConstant4() {
        String result = getCode(SimpleProject, "PriceCode", new int[]{ 13, 20 });
        
        String expected = 
                "enum PriceCode {\n" +
                "    REGULAR(200), NEW_RELEASE(300)\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractFieldDeclaration1() {
        String result = getCode(SimpleProject, "Test01", "ABC", new int[]{ 0 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public static int ABC = 0;\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractFieldDeclaration2() {
        String result = getCode(SimpleProject, "Test42", "a", new int[]{ 0 });
        
        String expected = 
                "public class Test42 {\n" +
                "    private final P42 a = new P42(1);\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractFieldDeclaration3() {
        String result = getCode(SimpleProject, "Test42", "a", new int[]{ 3 });
        
        String expected = 
                "public class Test42 {\n" +
                "    private final P42 a = new P42(1);\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractFieldDeclaration4() {
        String result = getCode(SimpleProject, "Test42", "a", new int[]{ 0, 3 });
        
        String expected = 
                "public class Test42 {\n" +
                "    private final P42 a = new P42(1);\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractLocalDeclaration1() {
        String result = getCode(SimpleProject, "Test01", "m( )", new int[]{ 0, 1 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        int a;\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractLocalDeclaration2() {
        String result = getCode(SimpleProject, "Test01", "m( )", new int[]{ 1 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        int a;\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractLocalDeclaration3() {
        String result = getCode(SimpleProject, "Test01", "m( )", new int[]{ 3 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        int b = 0;\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractAssignment1() {
        String result = getCode(SimpleProject, "Test01", "m( )", new int[]{ 2 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        a = 0;\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractAssignment2() {
        String result = getCode(SimpleProject, "Test01", "m( )", new int[]{ 1, 2 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        int a;\n" +
                "        a = 0;\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractAssignment3() {
        String result = getCode(SimpleProject, "Test01", "m( )", new int[]{ 1, 2, 3 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        int a;\n" +
                "        a = 0;\n" +
                "        int b = 0;\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractIfStatement1() {
        String result = getCode(SimpleProject, "Test01", "m( )", new int[]{ 5 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        if (a == 0) {\n" +
                "            a++;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractIfStatement2() {
        String result = getCode(SimpleProject, "Test01", "m( )", new int[]{ 6 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        if (a == 0) {\n" +
                "            System.out.println(a);\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractIfStatement3() {
        String result = getCode(SimpleProject, "Test01", "m( )", new int[]{ 7 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        if (a == 0) {\n" +
                "            System.out.println(a);\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractIfStatement4() {
        String result = getCode(SimpleProject, "Test01", "m( )", new int[]{ 8 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        if (a == 0) {\n" +
                "            System.out.println(a);\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractIfStatement5() {
        String result = getCode(SimpleProject, "Test01", "m( )", new int[]{ 10 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        if (a == 0) {\n" +
                "        } else {\n" +
                "            a++;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractIfStatement6() {
        String result = getCode(SimpleProject, "Test01", "m( )", new int[]{ 11 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        if (a == 0) {\n" +
                "        } else {\n" +
                "            a = -b;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractIfStatement7() {
        String result = getCode(SimpleProject, "Test01", "m( )", new int[]{ 10, 11 });
        
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
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractIfStatement8() {
        String result = getCode(SimpleProject, "Test01", "m( )", new int[]{ 12 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        if (a == 0) {\n" +
                "        } else {\n" +
                "            System.out.println(a);\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractIfStatement9() {
        String result = getCode(SimpleProject, "Test07", "m( )", new int[]{ 8 });
        
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
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractIfStatement10() {
        String result = getCode(SimpleProject, "Test07", "m( )", new int[]{ 11 });
        
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
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractIfStatement11() {
        String result = getCode(SimpleProject, "Test07", "m( )", new int[]{ 17 });
        
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
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractIfStatement12() {
        String result = getCode(SimpleProject, "Test51", "m( )", new int[]{ 5 });
        
        String expected = 
                "public class Test51 {\n" +
                "    public void m() {\n" +
                "        if (x > 10)\n" +
                "            y++;\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractIfStatement13() {
        String result = getCode(SimpleProject, "Test51", "m( )", new int[]{ 6 });
        
        String expected = 
                "public class Test51 {\n" +
                "    public void m() {\n" +
                "        if (x > 10)\n" +
                "            ;\n" +
                "        else\n" +
                "            z = x + 2;\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractIfStatement14() {
        String result = getCode(SimpleProject, "Test01", "m( )", new int[]{ 1, 2, 5 });
        
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
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractIfStatement15() {
        String result = getCode(SimpleProject, "Test01", "m( )", new int[]{ 1, 2, 10 });
        
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
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractIfStatement16() {
        String result = getCode(SimpleProject, "Test01", "m( )", new int[]{ 1, 2, 5, 10 });
        
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
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractIfStatement17() {
        String result = getCode(SimpleProject, "Test01", "m( )", new int[]{ 1, 2, 5, 10, 17 });
        
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
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractIfStatement18() {
        String result = getCode(SimpleProject, "Test01", "m( )", new int[]{ 2, 4 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        a = 0;\n" +
                "        if (a == 0) {\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractExpressionStatement() {
        String result = getCode(SimpleProject, "Test01", "m( )", new int[]{ 17 });
        
        String expected = 
                "public class Test01 {\n" +
                "    public void m() {\n" +
                "        System.out.println(a);\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractMethodInvocation1() {
        String result = getCode(SimpleProject, "Test04", "m( )", new int[]{ 4 });
        
        String expected = 
                "public class Test04 {\n" +
                "    public void m() {\n" +
                "        doReturn();\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractMethodInvocation2() {
        String result = getCode(SimpleProject, "Test04", "m( )", new int[]{ 10 });
        
        String expected = 
                "public class Test04 {\n" +
                "    public void m() {\n" +
                "        System.out.println(out);\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractReturnStatement1() {
        String result = getCode(SimpleProject, "Test04", "doReturn( int )", new int[]{ 4 });
        
        String expected = 
                "public class Test04 {\n" +
                "    public int doReturn() {\n" +
                "        if (in > 0)\n" +
                "            return in;\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractReturnStatement2() {
        String result = getCode(SimpleProject, "Test04", "doReturn( int )", new int[]{ 6 });
        
        String expected = 
                "public class Test04 {\n" +
                "    public int doReturn() {\n" +
                "        return 0;\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractReturnStatement3() {
        String result = getCode(SimpleProject, "Test11", "doReturn( int )", new int[]{ 4 });
        
        String expected = 
                "public class Test11 {\n" +
                "    public int doReturn() {\n" +
                "        if (in == 2) {\n" +
                "            return ++in;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractEmptyStatement1() {
        String result = getCode(SimpleProject, "Test02", "m( )", new int[]{ 1 });
        
        String expected = 
                "public class Test02 {\n" +
                "    public void m() {\n" +
                "        int a = 0;\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractEmptyStatement2() {
        String result = getCode(SimpleProject, "Test02", "m( )", new int[]{ 2 });
        
        String expected = 
                "public class Test02 {\n" +
                "    public void m() {\n" +
                "        ;\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractEmptyStatement3() {
        String result = getCode(SimpleProject, "Test02", "m( )", new int[]{ 3 });
        
        String expected = 
                "public class Test02 {\n" +
                "    public void m() {\n" +
                "        int b = 0;\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractWhileStatement1() {
        String result = getCode(SimpleProject, "Test02", "m( )", new int[]{ 4, 6 });
        
        String expected = 
                "public class Test02 {\n" +
                "    public void m() {\n" +
                "        int c = 0;\n" +
                "        while (a < 5 + d) {\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractWhileStatement2() {
        String result = getCode(SimpleProject, "Test02", "m( )", new int[]{ 8 });
        
        String expected = 
                "public class Test02 {\n" +
                "    public void m() {\n" +
                "        while (a < 5 + d) {\n" +
                "            a++;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractWhileStatement3() {
        String result = getCode(SimpleProject, "Test02", "m( )", new int[]{ 7, 8 });
        
        String expected = 
                "public class Test02 {\n" +
                "    public void m() {\n" +
                "        while (a < 5 + d) {\n" +
                "            c = a;\n" +
                "            a++;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractWhileStatement4() {
        String result = getCode(SimpleProject, "Test02", "m( )", new int[]{ 1, 3, 7, 8 });
        
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
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractWhileStatement5() {
        String result = getCode(SimpleProject, "Test02", "m( )", new int[]{ 1, 3, 7, 8, 22 });
        
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
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractSwitchStatement1() {
        String result = getCode(SimpleProject, "Test03", "m( )", new int[]{ 1, 3 });
        
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
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractSwitchStatement2() {
        String result = getCode(SimpleProject, "Test03", "m( )", new int[]{ 1, 4 });
        
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
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractSwitchStatement3() {
        String result = getCode(SimpleProject, "Test03", "m( )", new int[]{ 1, 5 });
        
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
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractSwitchStatement4() {
        String result = getCode(SimpleProject, "Test03", "m( )", new int[]{ 1, 6 });
        
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
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractIfWhileStatement1() {
        String result = getCode(SimpleProject, "Test05", "m( )", new int[]{ 5 });
        
        String expected = 
                "public class Test05 {\n" +
                "    public void m() {\n" +
                "        if (c < (d = a + b + c)) {\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractIfWhileStatement2() {
        String result = getCode(SimpleProject, "Test05", "m( )", new int[]{ 6 });
        
        String expected = 
                "public class Test05 {\n" +
                "    public void m() {\n" +
                "        if (c < (d = a + b + c)) {\n" +
                "            while (b < 10) {\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    @Test
    public void testExtractIfWhileStatement3() {
        String result = getCode(SimpleProject, "Test05", "m( )", new int[]{ 7 });
        
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
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractIfWhileStatement4() {
        String result = getCode(SimpleProject, "Test05", "m( )", new int[]{ 8 });
        
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
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractIfWhileStatement5() {
        String result = getCode(SimpleProject, "Test05", "m( )", new int[]{ 9 });
        
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
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractIfSwitchStatement1() {
        String result = getCode(SimpleProject, "Test06", "m( )", new int[]{ 3 });
        
        String expected = 
                "public class Test06 {\n" +
                "    public void m() {\n" +
                "        if (a == 0) {\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractIfSwitchStatement2() {
        String result = getCode(SimpleProject, "Test06", "m( )", new int[]{ 4 });
        
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
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractIfSwitchStatement3() {
        String result = getCode(SimpleProject, "Test06", "m( )", new int[]{ 5 });
        
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
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractIfSwitchStatement4() {
        String result = getCode(SimpleProject, "Test06", "m( )", new int[]{ 6 });
        
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
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractForStatement1() {
        String result = getCode(SimpleProject, "Test21", "m( )", new int[]{ 2 });
        
        String expected = 
                "public class Test21 {\n" +
                "    final public void m() {\n" +
                "        for (int i = 0; i < 10;) {\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractForStatement2() {
        String result = getCode(SimpleProject, "Test21", "m( )", new int[]{ 3 });
        
        String expected = 
                "public class Test21 {\n" +
                "    final public void m() {\n" +
                "        for (; i < 10;) {\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractForStatement3() {
        String result = getCode(SimpleProject, "Test21", "m( )", new int[]{ 5 });
        
        String expected = 
                "public class Test21 {\n" +
                "    final public void m() {\n" +
                "        for (; i < 10; i++) {\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractForStatement4() {
        String result = getCode(SimpleProject, "Test21", "m( )", new int[]{ 4 });
        
        String expected = 
                "public class Test21 {\n" +
                "    final public void m() {\n" +
                "        for (; i < 10;) {\n" +
                "            a++;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractForStatement5() {
        String result = getCode(SimpleProject, "Test21", "m2( )", new int[]{ 3 });
        
        String expected = 
                "public class Test21 {\n" +
                "    public void m2() {\n" +
                "        for (; i < 10; i++) {\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractForStatement6() {
        String result = getCode(SimpleProject, "Test21", "m3( )", new int[]{ 3 });
        
        String expected = 
                "public class Test21 {\n" +
                "    public void m3() {\n" +
                "        for (;;) {\n" +
                "            if (i == 10)\n" +
                "                break;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractForStatement7() {
        String result = getCode(SimpleProject, "Test21", "m3( )", new int[]{ 6 });
        
        String expected = 
                "public class Test21 {\n" +
                "    public void m3() {\n" +
                "        for (;; i++) {\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractForStatement8() {
        String result = getCode(SimpleProject, "Test21", "m4( )", new int[]{ 1 });
        
        String expected = 
                "public class Test21 {\n" +
                "    public void m4() {\n" +
                "        for (int i = 0; i < 10;) {\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    @Test
    public void testExtractDoStatement1() {
        String result = getCode(SimpleProject, "Test22", "m( )", new int[]{ 3 });
        
        String expected = 
                "public class Test22 {\n" +
                "    public void m() {\n" +
                "        do {\n" +
                "        } while (a < 19);\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractDoStatement2() {
        String result = getCode(SimpleProject, "Test22", "m( )", new int[]{ 2 });
        
        String expected = 
                "public class Test22 {\n" +
                "    public void m() {\n" +
                "        do {\n" +
                "            a++;\n" +
                "        } while (a < 19);\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractEnhancedForStatement1() {
        String result = getCode(SimpleProject, "Test50", "m( )", new int[]{ 5 });
        
        String expected = 
                "public class Test50 {\n" +
                "    public void m() {\n" +
                "        for (String str : strings) {\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractEnhancedForStatement2() {
        String result = getCode(SimpleProject, "Test50", "m( )", new int[]{ 6 });
        
        String expected = 
                "public class Test50 {\n" +
                "    public void m() {\n" +
                "        for (String str : strings) {\n" +
                "            int len = str.length();\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractAssertStatement1() {
        String result = getCode(SimpleProject, "Test34", "add( int )", new int[]{ 2 });
        
        String expected = 
                "public class Test34 {\n" +
                "    void add() {\n" +
                "        assert x > 0;\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractSynchronizedStatement1() {
        String result = getCode(SimpleProject, "Test34", "add( int )", new int[]{ 4 });
        
        String expected = 
                "public class Test34 {\n" +
                "    void add() {\n" +
                "        synchronized (this) {\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractSynchronizedStatement2() {
        String result = getCode(SimpleProject, "Test34", "add( int )", new int[]{ 5 });
        
        String expected = 
                "public class Test34 {\n" +
                "    void add() {\n" +
                "        synchronized (this) {\n" +
                "            v = v + y;\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractJavadoc1() {
        String result = getCode(SimpleProject, "Test60", new int[]{ 0 });
        
        String expected = 
                "/**\n" +
                " * Class Javadoc\n" +
                " */\n" +
                "public class Test60 {\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractJavadoc2() {
        String result = getCode(SimpleProject, "Test60", "Test60( int )", new int[]{ 0, 1 });
        
        String expected = 
                "/**\n" +
                " * Class Javadoc\n" +
                " */\n" +
                "public class Test60 {\n" +
                "    /**\n" +
                "     * Constructor Javadoc\n" +
                "     * \n" +
                "     * @param x the parameter\n" +
                "     */\n" +
                "    public Test60(int x) {\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractJavadoc3() {
        String result = getCode(SimpleProject, "Test60", "m( int )", new int[]{ 0 });
        
        String expected = 
                "/**\n" +
                " * Class Javadoc\n" +
                " */\n" +
                "public class Test60 {\n" +
                "    /**\n" +
                "     * Method Javadoc\n" +
                "     * \n" +
                "     * @param x the parameter\n" +
                "     */\n" +
                "    public void m() {\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractJavadoc4() {
        String result = getCode(SimpleProject, "Test60", "m( int )", new int[]{ 2 });
        
        String expected = 
                "/**\n" +
                " * Class Javadoc\n" +
                " */\n" +
                "public class Test60 {\n" +
                "    /**\n" +
                "     * Method Javadoc\n" +
                "     * \n" +
                "     * @param x the parameter\n" +
                "     */\n" +
                "    public void m() {\n" +
                "        a = x + 2;\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractJavadoc5() {
        String result = getCode(SimpleProject, "Test60", "a", new int[]{ 0 });
        
        String expected = 
                "/**\n" +
                " * Class Javadoc\n" +
                " */\n" +
                "public class Test60 {\n" +
                "    /**\n" +
                "     * Field Javadoc\n" +
                "     */\n" +
                "    private int a;\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractEnumJavadoc1() {
        String result = getCode(SimpleProject, "Enum60", new int[]{ 0 });
        
        String expected = 
                "/**\n" +
                " * Enum Javadoc\n" +
                " */\n" +
                "enum Enum60 {\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractEnumJavadoc2() {
        String result = getCode(SimpleProject, "Enum60", "Enum60( int )", new int[]{ 2 });
        
        String expected = 
                "/**\n" +
                " * Enum Javadoc\n" +
                " */\n" +
                "enum Enum60 {\n" +
                "    ;\n" +
                "    /**\n" +
                "     * Enum Constructor Javadoc\n" +
                "     * \n" +
                "     * @param x the parameter\n" +
                "     */\n" +
                "    Enum60() {\n" +
                "        this.x = x;\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractEnumJavadoc3() {
        String result = getCode(SimpleProject, "Enum60", "X", new int[]{ 0 });
        
        String expected = 
                "/**\n" +
                " * Enum Javadoc\n" +
                " */\n" +
                "enum Enum60 {\n" +
                "    /**\n" +
                "     * Field Constant Javadoc\n" +
                "     */\n" +
                "    X(100)\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractEnumJavadoc4() {
        String result = getCode(SimpleProject, "Enum60", "Y", new int[]{ 0 });
        
        String expected = 
                "/**\n" +
                " * Enum Javadoc\n" +
                " */\n" +
                "enum Enum60 {\n" +
                "    /**\n" +
                "     * Enum Constant Javadoc\n" +
                "     */\n" +
                "    Y(100)\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testExtractEnumJavadoc5() {
        String result = getCode(SimpleProject, "Enum60", "x", new int[]{ 0 });
        
        String expected = 
                "/**\n" +
                " * Enum Javadoc\n" +
                " */\n" +
                "enum Enum60 {\n" +
                "    ;\n" +
                "    /**\n" +
                "     * Enum Field Javadoc\n" +
                "     */\n" +
                "    private int x;\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testComment1() {
        String result = getCode(SimpleProject, "Test61", new int[]{ 0 });
        
        String expected = 
                "/*\n" +
                " * Class Comment\n" +
                " */\n" +
                "public class Test61 {\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testComment2() {
        String result = getCode(SimpleProject, "Test61", "m( int )", new int[]{ 0 });
        
        String expected = 
                "/*\n" +
                " * Class Comment\n" +
                " */\n" +
                "public class Test61 {\n" +
                "    /*\n" +
                "     * Method Comment\n" +
                "     */\n" +
                "    public void m() {\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testComment3() {
        String result = getCode(SimpleProject, "Test61", "p", new int[]{ 0 });
        
        String expected = 
                "/*\n" +
                " * Class Comment\n" +
                " */\n" +
                "public class Test61 {\n" +
                "    /*\n" +
                "     * Field Comment 1\n" +
                "     */\n" +
                "    private int p;\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testComment4() {
        String result = getCode(SimpleProject, "Test61", "q", new int[]{ 0 });
        
        String expected = 
                "/*\n" +
                " * Class Comment\n" +
                " */\n" +
                "public class Test61 {\n" +
                "    private int q; // Field Comment 2\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testComment5() {
        String result = getCode(SimpleProject, "Test61", "m( int )", new int[]{ 2 });
        
        String expected = 
                "/*\n" +
                " * Class Comment\n" +
                " */\n" +
                "public class Test61 {\n" +
                "    public void m() {\n" +
                "        // Comment 1\n" +
                "        int a = x + 2; // Comment 6\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testComment6() {
        String result = getCode(SimpleProject, "Test61", "m( int )", new int[]{ 3 });
        
        String expected = 
                "/*\n" +
                " * Class Comment\n" +
                " */\n" +
                "public class Test61 {\n" +
                "    public void m() {\n" +
                "        // Comment 3\n" +
                "        int b = x + 2;\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testComment7() {
        String result = getCode(SimpleProject, "Test61", "m( int )", new int[]{ 4 });
        
        String expected = 
                "/*\n" +
                " * Class Comment\n" +
                " */\n" +
                "public class Test61 {\n" +
                "    public void m() {\n" +
                "        /*\n" +
                "         * Comment 4\n" +
                "         */\n" +
                "        int c = a;\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
    
    @Test
    public void testComment8() {
        String result = getCode(SimpleProject, "Test61", "m( int )", new int[]{ 0, 2 });
        
        String expected = 
                "/*\n" +
                " * Class Comment\n" +
                " */\n" +
                "public class Test61 {\n" +
                "    /*\n" +
                "     * Method Comment\n" +
                "     */\n" +
                "    public void m() {\n" +
                "        // Comment 1\n" +
                "        int a = x + 2; // Comment 6\n" +
                "    }\n" +
                "}\n";
        assertEquals(BuilderTestUtil.getContent(expected), result);
    }
}
