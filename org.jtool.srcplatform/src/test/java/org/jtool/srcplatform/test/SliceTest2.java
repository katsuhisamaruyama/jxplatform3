package org.jtool.srcplatform.test;
/*
 *  Copyright 2019
 *  Software Science and Technology Lab.
 *  Department of Computer Science, Ritsumeikan University
 */

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import org.jtool.pdg.ClDG;
import org.jtool.pdg.SDG;
import org.jtool.slice.Slice;
import org.jtool.slice.SliceCriterion;
import org.jtool.slice.SliceExtractor;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcplatform.modelbuilder.ModelBuilderBatch;

import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

/**
 * Tests a class that extracts a slice.
 * 
 * @author Katsuhisa Maruyama
 */
public class SliceTest2 {
    
    private static ModelBuilderBatch builder;
    private static JavaProject jproject;
    
    @BeforeClass
    public static void build() {
        String dir = new File(".").getAbsoluteFile().getParent() + "/test_target/";
        String target = dir +  "Slice/";
        builder = new ModelBuilderBatch(true);
        jproject = builder.build(target, target, target, target, target);
    }
    
    @AfterClass
    public static void unbuild() {
        builder.unbuild();
    }
    
    private Slice slice(JavaClass jclass, int lineNumber, int offset) {
        if (jclass == null) {
            return null;
        }
        
        Set<JavaClass> classes = builder.getAllClassesBackward(jclass);
        SDG sdg = builder.getSDG(classes);
        ClDG cldg = sdg.getClDG(jclass.getQualifiedName().fqn());
        //sdg.print();
        
        String code = jclass.getFile().getCode();
        SliceCriterion criterion = SliceCriterion.find(cldg, code, lineNumber, offset);
        if (criterion != null) {
            return new Slice(criterion);
        }
        return null;
    }
    
    private String getSlicedCode(String fqn, int lineNumber, int offset) {
        JavaClass jclass = jproject.getClass(fqn);
        if (jclass != null) {
            Slice slice = slice(jclass, lineNumber, offset);
            if (slice != null) {
                System.out.println(slice.toString());
                
                Map<String, String> options = new HashMap<String, String>();
                options.put(DefaultCodeFormatterConstants.FORMATTER_TAB_CHAR, JavaCore.SPACE);
                options.put(DefaultCodeFormatterConstants.FORMATTER_INDENTATION_SIZE, "4");
                
                SliceExtractor extractor = new SliceExtractor(builder, slice, jclass);
                String code = extractor.extract(options);
                return code;
            }
        }
        return "Failed";
    }
    
    private String getSlicedCode(String fqn, String sig, int lineNumber, int offset) {
        JavaClass jclass = jproject.getClass(fqn);
        if (jclass != null) {
            JavaMethod jmethod = jclass.getMethod(sig);
            
            Slice slice = slice(jclass, lineNumber, offset); 
            if (slice != null) {
                System.out.println(slice.toString());
                
                SliceExtractor extractor = new SliceExtractor(builder, slice, jmethod);
                String code = extractor.extract();
                return code;
            }
        }
        return "Failed";
    }
    
    @Test
    public void testSlice129_1() {
        String code = getSlicedCode("Test129", 12, 15);
        System.out.println(code);
        String expected = 
                "class Test129 {\n" + 
                "    private S s1 = new S();\n" + 
                "    public void m() {\n" + 
                "        s1.getP().set1(\"A\", \"AAAA\");\n" + 
                "        String v1 = s1.getP().get1(\"A\");\n" + 
                "    }\n" + 
                "}\n";
        assertEquals(expected, code);
    }
    
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        build();
        
        SliceTest2 tester = new SliceTest2();
        
        unbuild();
    }
}
