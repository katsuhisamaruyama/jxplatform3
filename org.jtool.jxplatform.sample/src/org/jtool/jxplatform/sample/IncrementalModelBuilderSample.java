/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.sample;

import org.jtool.jxplatform.builder.IncrementalModelBuilder;
import org.jtool.jxplatform.project.ModelBuilderBatchImpl;
import org.jtool.jxplatform.project.ModelBuilderImpl;
import java.io.File;

/**
 * Sample code for incrementally building a source code model.
 * 
 * @author Katsuhisa Maruyama
 */
public class IncrementalModelBuilderSample {
    
    public static void main(String[] args) {
        IncrementalModelBuilderSample generator = new IncrementalModelBuilderSample();
        generator.run(SrcModelGeneratorSample.SAMPLE_PROJECT_DIR);
    }
    
    private void run(String path) {
        ModelBuilderImpl builderImpl = new ModelBuilderBatchImpl();
        builderImpl.analyzeBytecode(true);
        builderImpl.useCache(true);
        builderImpl.setConsoleVisible(true);
        IncrementalModelBuilder builder = new IncrementalModelBuilder(builderImpl);
        
        String name = path;
        String classpath = path;
        builder.build(name, path, classpath);
        
        builder.addFile(path + File.separator + "Added.java");
        
        builder.incrementalBuild();
        
        builder.removeFile(path + File.separator + "Deleled.java");
        builder.updateFile(path + File.separator + "Updated.java");
        
        builder.incrementalBuild();
        
        builder.unbuild();
    }
}
