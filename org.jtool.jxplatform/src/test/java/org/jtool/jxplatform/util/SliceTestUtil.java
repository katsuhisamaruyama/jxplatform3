/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.util;

import org.jtool.slice.Slice;
import org.jtool.slice.SliceCriterion;
import org.jtool.slice.builder.SliceExtractor;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaField;
import org.jtool.pdg.SDG;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import java.util.Map;
import java.util.HashMap;

public class SliceTestUtil {
    
    public static SliceCriterion getSliceCriterion(JavaProject jproject, String cname, int lineNumber, int offset) {
        JavaClass jclass = jproject.getClass(cname);
        SDG sdg = PDGTestUtil.createSDG(jproject, cname);
        
        sdg.print();
        
        return SliceCriterion.find(sdg, jclass, lineNumber, offset);
    }
    
    public static Slice getSlice(JavaProject jproject, String cname, int lineNumber, int offset) {
        SliceCriterion criterion = getSliceCriterion(jproject, cname, lineNumber, offset);
        if (criterion == null) {
            return null;
        }
        return new Slice(criterion);
    }
    
    public static String getSlicedCode(JavaProject jproject, String cname, int lineNumber, int offset) {
        Slice slice = getSlice(jproject, cname, lineNumber, offset);
        if (slice != null) {
            
            slice.print();
            
            Map<String, String> options = new HashMap<String, String>();
            options.put(DefaultCodeFormatterConstants.FORMATTER_TAB_CHAR, JavaCore.SPACE);
            options.put(DefaultCodeFormatterConstants.FORMATTER_INDENTATION_SIZE, "4");
            
            JavaClass jclass = jproject.getClass(cname);
            SliceExtractor extractor = new SliceExtractor(jproject.getModelBuilder(), slice, jclass);
            String code = extractor.extract(options);
            
            System.out.println(code);
            
            return code;
        }
        return "Failed";
    }
    
    public static String getSlicedCodeByMethod(JavaProject jproject, String cname, String sig,
            int lineNumber, int offset) {
        Slice slice = getSlice(jproject, cname, lineNumber, offset);
        if (slice != null) {
            Map<String, String> options = new HashMap<String, String>();
            options.put(DefaultCodeFormatterConstants.FORMATTER_TAB_CHAR, JavaCore.SPACE);
            options.put(DefaultCodeFormatterConstants.FORMATTER_INDENTATION_SIZE, "4");
            
            JavaClass jclass = jproject.getClass(cname);
            JavaMethod jmethod = jclass.getMethod(sig);
            SliceExtractor extractor = new SliceExtractor(jproject.getModelBuilder(), slice, jmethod);
            String code = extractor.extract(options);
            return code;
        }
        return "Failed";
    }
    
    public static String getSlicedCodeByField(JavaProject jproject, String cname, String sig,
            int lineNumber, int offset) {
        Slice slice = getSlice(jproject, cname, lineNumber, offset);
        if (slice != null) {
            Map<String, String> options = new HashMap<String, String>();
            options.put(DefaultCodeFormatterConstants.FORMATTER_TAB_CHAR, JavaCore.SPACE);
            options.put(DefaultCodeFormatterConstants.FORMATTER_INDENTATION_SIZE, "4");
            
            JavaClass jclass = jproject.getClass(cname);
            JavaField jfield = jclass.getField(sig);
            SliceExtractor extractor = new SliceExtractor(jproject.getModelBuilder(), slice, jfield);
            String code = extractor.extract(options);
            return code;
        }
        return "Failed";
    }
}
