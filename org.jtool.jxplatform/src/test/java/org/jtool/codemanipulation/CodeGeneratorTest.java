/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.codemanipulation;

import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.JavaClass;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.PDGTestUtil;
import org.jtool.pdg.ClDG;
import org.jtool.pdg.PDGNode;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.Set;

public class CodeGeneratorTest {
    
    private static JavaProject SimpleProject;
    
    private static String getCode(JavaProject jproject, String cname, int[] indices) {
        JavaClass jclass = jproject.getClass(cname);
        ClDG cldg = jproject.getModelBuilder().getClDG(jclass);
        
        Set<PDGNode> nodes = PDGTestUtil.getNodes(cldg, indices);
        CodeExtractor extractor = new CodeExtractor(jclass, nodes);
        
        CodeGenerator generator = new CodeGenerator();
        String code = generator.generate(jclass.getASTNode(),
                jclass.getFile().getSource(), extractor.getTargetNodes());
        
        return code;
    }
    
    @BeforeClass
    public static void setUp() {
        SimpleProject = BuilderTestUtil.createProject("Simple", "", "");
    }
    
    @AfterClass
    public static void tearDown() {
        SimpleProject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testIndent1() {
        JavaClass jclass = SimpleProject.getClass("Test01");
        
        CodeGenerator codeGenerator = new CodeGenerator();
        String result = codeGenerator.generate(jclass.getASTNode(), jclass.getFile().getSource(), null);
        
        String expected = 
                "public class Test01 {\n" +
                "    public static int ABC = 0;\n" +
                "    public void m() {\n" +
                "        int a;\n" +
                "        a = 0;\n" +
                "        int b = 0;\n" +
                "        if (a == 0) {\n" +
                "            a++;\n" +
                "            System.out.println(a);\n" +
                "        } else {\n" +
                "            a++;\n" +
                "            a = -b;\n" +
                "            System.out.println(a);\n" +
                "        }\n" +
                "        System.out.println(a);\n" +
                "        System.out.println(b);\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testIndent2() {
        JavaClass jclass = SimpleProject.getClass("Test62");
        
        CodeGenerator codeGenerator = new CodeGenerator();
        String result = codeGenerator.generate(jclass.getASTNode(), jclass.getFile().getSource(), null);
        
        String expected = 
                "public class Test62 {\n" +
                "    public static int ABC = 0;\n" +
                "    public void m() {\n" +
                "        int a;\n" +
                "        a = 0;\n" +
                "        int b = 0;\n" +
                "        if (a == 0) {\n" +
                "            a++;\n" +
                "            System.out.println(a);\n" +
                "        } else {\n" +
                "            a++;\n" +
                "            a = -b;\n" +
                "            System.out.println(a);\n" +
                "        }\n" +
                "        System.out.println(a);\n" +
                "        System.out.println(b);\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testComment1() {
        String result = getCode(SimpleProject, "Test61", new int[]{ 1 });
        
        String expected = 
                "/*\n" +
                " * Class Comment\n" +
                " */\n" +
                "public class Test61 {\n" +
                "    private int p;\n" +
                "\n" +
                "    private int q;\n" +
                "    public void m(int x) {\n" +
                "        int a = x + 2;\n" +
                "        int b = x + 2;\n" +
                "        int c = a;\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testComment2() {
        String result = getCode(SimpleProject, "Test61", new int[]{ 5 });
        
        String expected = 
                "/*\n" +
                " * Class Comment\n" +
                " */\n" +
                "public class Test61 {\n" +
                "    private int p;\n" +
                "\n" +
                "    private int q;\n" +
                "    /*\n" +
                "     * Method Comment\n" +
                "     */\n" +
                "    public void m(int x) {\n" +
                "        int a = x + 2;\n" +
                "        int b = x + 2;\n" +
                "        int c = a;\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testComment3() {
        String result = getCode(SimpleProject, "Test61", new int[]{ 13 });
        
        String expected = 
                "/*\n" +
                " * Class Comment\n" +
                " */\n" +
                "public class Test61 {\n" +
                "    /*\n" +
                "     * Field Comment 1\n" +
                "     */\n" +
                "    private int p;\n" +
                "\n" +
                "    private int q;\n" +
                "    public void m(int x) {\n" +
                "        int a = x + 2;\n" +
                "        int b = x + 2;\n" +
                "        int c = a;\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testComment4() {
        String result = getCode(SimpleProject, "Test61", new int[]{ 16 });
        
        String expected = 
                "/*\n" +
                " * Class Comment\n" +
                " */\n" +
                "public class Test61 {\n" +
                "    private int p;\n" +
                "\n" +
                "    private int q; // Field Comment 2\n" +
                "    public void m(int x) {\n" +
                "        int a = x + 2;\n" +
                "        int b = x + 2;\n" +
                "        int c = a;\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testComment5() {
        String result = getCode(SimpleProject, "Test61", new int[]{ 7 });
        
        String expected = 
                "/*\n" +
                " * Class Comment\n" +
                " */\n" +
                "public class Test61 {\n" +
                "    private int p;\n" +
                "\n" +
                "    private int q;\n" +
                "    public void m(int x) {\n" +
                "        // Comment 1\n" +
                "        int a = x + 2; // Comment 6\n" +
                "        int b = x + 2;\n" +
                "        int c = a;\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testComment6() {
        String result = getCode(SimpleProject, "Test61", new int[]{ 8 });
        
        String expected = 
                "/*\n" +
                " * Class Comment\n" +
                " */\n" +
                "public class Test61 {\n" +
                "    private int p;\n" +
                "\n" +
                "    private int q;\n" +
                "    public void m(int x) {\n" +
                "        int a = x + 2;\n" +
                "        // Comment 3\n" +
                "        int b = x + 2;\n" +
                "        int c = a;\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testComment7() {
        String result = getCode(SimpleProject, "Test61", new int[]{ 9 });
        
        String expected = 
                "/*\n" +
                " * Class Comment\n" +
                " */\n" +
                "public class Test61 {\n" +
                "    private int p;\n" +
                "\n" +
                "    private int q;\n" +
                "    public void m(int x) {\n" +
                "        int a = x + 2;\n" +
                "        int b = x + 2;\n" +
                "        /*\n" +
                "         * Comment 4\n" +
                "         */\n" +
                "        int c = a;\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testComment8() {
        String result = getCode(SimpleProject, "Test61", new int[]{ 5, 7 });
        
        String expected = 
                "/*\n" +
                " * Class Comment\n" +
                " */\n" +
                "public class Test61 {\n" +
                "    private int p;\n" +
                "\n" +
                "    private int q;\n" +
                "    /*\n" +
                "     * Method Comment\n" +
                "     */\n" +
                "    public void m(int x) {\n" +
                "        // Comment 1\n" +
                "        int a = x + 2; // Comment 6\n" +
                "        int b = x + 2;\n" +
                "        int c = a;\n" +
                "    }\n" +
                "}\n";
        assertEquals(expected, result);
    }
}
