/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.util;

import org.jtool.jxplatform.builder.ModelBuilderBatch;
import org.jtool.jxplatform.refmodel.RefModelTestUtil;
import org.jtool.srcmodel.JavaProject;
import java.io.File;

public class BuilderTestUtil {
    
    private final static String testTargetDir = new File(".").getAbsoluteFile().getParent() + "/test_target/";
    
    public static String getTarget(String name) {
        return testTargetDir + name;
    }
    
    public static JavaProject createProject(String name, String lib, String src) {
        String target = getTarget(name);
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        JavaProject project = builder.build(target, target, target + lib, target + src, target);
        return project;
    }
    
    public static boolean removeCache(String target) {
        return RefModelTestUtil.removeCache(target);
    }
}
