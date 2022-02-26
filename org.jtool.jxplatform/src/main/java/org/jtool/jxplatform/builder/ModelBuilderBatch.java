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
     * The implementation module of this model builder.
     */
    private ModelBuilderBatchImpl builderImpl;
    
    /**
     * Creates a batch-mode model builder.
     * @param analyzingBytecode {@code true} if byte-code analysis is performed, otherwise {@code false}
     * @param useBytecodeCache {@code true} if the byte-code cache is preferentially used, otherwise {@code false}
     */
    public ModelBuilderBatch(boolean analyzingBytecode, boolean useBytecodeCache) {
        this.builderImpl = new ModelBuilderBatchImpl(this);
        impl = builderImpl;
        
        impl.analyzeBytecode(analyzingBytecode);
        impl.useProjectCache(useBytecodeCache);
    }
    
    /**
     * Creates a batch-mode model builder.
     */
    public ModelBuilderBatch() {
        this(false, false);
    }
    
    /**
     * Creates a batch-mode model builder.
     * @param analyzingBytecode {@code true} if byte-code analysis is performed, otherwise {@code false}
     */
    public ModelBuilderBatch(boolean analyzingBytecode) {
        this(analyzingBytecode, false);
    }
    
    /**
     * Builds a source code model for target projects.
     * @param name the name of the created model
     * @param target the directory storing the target projects
     * @return the collection of created project data
     */
    public List<JavaProject> build(String name, String target) {
        return builderImpl.build(name, target);
    }
    
    /**
     * Builds a source code model for a target project.
     * @param name the name of the created model
     * @param target the directory storing the target project
     * @param classpath the path where the needed class (or jar) files are located
     * @return the created project data
     */
    public JavaProject build(String name, String target, String classpath) {
        return build(name, target, classpath, (String)null, (String)null);
    }
    
    /**
     * Builds a source code model for a target project.
     * @param name the name of the created model
     * @param target the directory storing the target project
     * @param classpath the path where the needed class (or jar) files are located
     * @param srcpath the path where the source files are located
     * @param binpath the path where the binary files are located
     * @return the created project data
     */
    public JavaProject build(String name, String target, String classpath, String srcpath, String binpath) {
        return builderImpl.build(name, target, classpath, srcpath, binpath);
    }
    
    /**
     * Builds a source code model for a target project.
     * @param name the name of the created model
     * @param target the directory storing the target project
     * @param classpath the collection of the paths where the needed class (or jar) files are located
     * @param srcpath the collection of the paths where the source files are located
     * @param binpath the collection of the paths where the binary files are located
     * @return the created project data
     */
    public JavaProject build(String name, String target, String[] classpath, String[] srcpath, String[] binpath) {
        return builderImpl.build(name, target, classpath, srcpath, binpath);
    }
}
