/*
 *  Copyright 2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.sample;

import org.jtool.jxplatform.builder.ModelBuilderBatch;
import org.jtool.jxplatform.builder.IncrementalModelBuilder;
import org.jtool.srcmodel.JavaProject;
import java.util.List;

/**
 * Sample code for incrementally building a source code model.
 * 
 * @author Katsuhisa Maruyama
 */
public class IncrementalModelBuilderSample {
    
    public static void main(String[] args) {
        run("sample", SrcModelGeneratorSample.SAMPLE_PROJECT_DIR);
    }
    
    private static void run(String name, String target) {
        ModelBuilderBatch builder = new ModelBuilderBatch();
        builder.analyzeBytecode(true);
        builder.useCache(true);
        builder.setConsoleVisible(true);
        
        List<JavaProject> projects = builder.build(name, target);
        IncrementalModelBuilder iBuilder = new IncrementalModelBuilder(builder.getModelBuilderImpl(), projects);
        
        // Add, remove, update files
        
        iBuilder.incrementalBuild();
        
        builder.unbuild();
    }
}
