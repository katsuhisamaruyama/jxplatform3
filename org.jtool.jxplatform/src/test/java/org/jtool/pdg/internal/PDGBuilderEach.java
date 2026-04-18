/*
 *  Copyright 2026
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg.internal;

import org.jtool.srcmodel.JavaProject;
import org.jtool.pdg.PDG;
import org.jtool.jxplatform.builder.BuilderTestUtil;

public class PDGBuilderEach {
    
    public static void testSimpleProject() {
        BuilderTestUtil.clearProject();
        
        JavaProject jproject = BuilderTestUtil.getProject("Simple");
        
        PDG pdg = PDGTestUtil.createPDG(jproject, "Test65", "m( java.lang.String int )");
        pdg.print();
        
        jproject.getModelBuilder().unbuild();
        BuilderTestUtil.clearProject();
    }
    
    public static void main(String[] args) {
        testSimpleProject();
    }
}
