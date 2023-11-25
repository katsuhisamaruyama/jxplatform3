/*
 *  Copyright 2022-2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder;

import org.jtool.jxplatform.project.ModelBuilderBatchImpl;

/**
 * A batch processing builder that builds models from Java source code.
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
}
