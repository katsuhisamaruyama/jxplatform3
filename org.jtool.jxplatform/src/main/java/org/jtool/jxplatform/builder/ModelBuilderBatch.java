/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.project.ModelBuilderBatchImpl;
import java.util.List;

/**
 * A batch processing builder that builds models related to Java source code.
 * 
 * @author Katsuhisa Maruyama
 */
public class ModelBuilderBatch extends ModelBuilder {
    
    /**
     * Creates a batch-mode model builder.
     * @param analyzingBytecode {@code true} if byte-code analysis is performed, otherwise {@code false}
     * @param useCache {@code true} if the cache is used, otherwise {@code false}
     */
    public ModelBuilderBatch(boolean analyzingBytecode, boolean useCache) {
        builderImpl = new ModelBuilderBatchImpl();
        
        builderImpl.analyzeBytecode(analyzingBytecode);
        builderImpl.useCache(useCache);
    }
    
    /**
     * Creates a batch-mode model builder.
     * @param analyzingBytecode {@code true} if byte-code analysis is performed, otherwise {@code false}
     */
    public ModelBuilderBatch(boolean analyzingBytecode) {
        this(analyzingBytecode, true);
    }
    
    /**
     * Creates a batch-mode model builder.
     */
    public ModelBuilderBatch() {
        this(true, true);
    }
    
    /**
     * Builds a source code model for target projects.
     * @param name the name of the created model
     * @param target the directory storing the target projects
     * @return the collection of created project data
     */
    public List<JavaProject> build(String name, String target) {
        return builderImpl.build(this, name, target);
    }
}
