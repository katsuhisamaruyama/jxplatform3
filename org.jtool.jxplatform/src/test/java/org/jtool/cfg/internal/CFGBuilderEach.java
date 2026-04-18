/*
 *  Copyright 2026
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal;

import org.jtool.srcmodel.JavaProject;
import org.jtool.cfg.CFG;
import org.jtool.jxplatform.builder.BuilderTestUtil;

public class CFGBuilderEach {
    
    public static void testSimpleProject() {
        BuilderTestUtil.clearProject();
        
        JavaProject jproject = BuilderTestUtil.getProject("Simple");
        
        CFG cfg = CFGTestUtil.createCFG(jproject, "Test65", "m( java.lang.String int )");
        cfg.print();
        
        jproject.getModelBuilder().unbuild();
        BuilderTestUtil.clearProject();
    }
    
    public static void main(String[] args) {
        testSimpleProject();
    }
}
