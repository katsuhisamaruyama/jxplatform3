/*
 *  Copyright 2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.ossprojects;

import org.jtool.jxplatform.builder.ModelBuilderBatch;
import java.io.File;

/**
 * Test to build source models from open-source Java programs.
 * @author Katsuhisa Maruyama
 */
public class SrcModelGenTest {
    
    /**
     * This directory is available for a test.
     */
    final static String TEST_TARGET_DIR = new File(".").getAbsoluteFile().getParent() + 
            File.separator + "test_target" + File.separator;
    
    static void run(String name) {
        String target = SrcModelGenTest.TEST_TARGET_DIR + name;
        ModelBuilderBatch builder = getModeBuilder(name, target);
        if (builder == null) {
            return;
        }
        builder.unbuild();
    }
    
    static ModelBuilderBatch getModeBuilder(String name, String target) {
        File dir = new File(target);
        if (!dir.exists()) {
            System.err.println("Not found project " + target);
            return null;
        }
        
        ModelBuilderBatch builder = new ModelBuilderBatch(true, true);
        builder.build(name, target);
        
        return builder;
    }
    
    public static void main(String[] args) {
        run("ant-1.10.12");
        run("guava-31.0.1");
        run("mockito-4.2.0");
    }
}
