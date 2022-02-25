/*
 *  Copyright 2021
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.plugin;

import org.jtool.srcplatform.modelbuilder.ModelBuilder;
import org.jtool.srcplatform.plugin.internal.ModelBuilderInteractiveImpl;
import org.jtool.srcmodel.JavaProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

/**
 * A builder for a plug-in that builds models related to Java source code.
 * 
 * @author Katsuhisa Maruyama
 */
public class ModelBuilderInteractive extends ModelBuilder {
    
    /**
     * The implementation module of this model builder.
     */
    private ModelBuilderInteractiveImpl builderImpl;
    
    /**
     * Creates an interactive-mode model builder.
     * @param analyzingBytecode {@code true} if byte-code analysis is performed, otherwise {@code false}
     * @param useBytecodeCache {@code true} if the byte-code cache is preferentially used, otherwise {@code false}
     */
    public ModelBuilderInteractive(boolean analyzingBytecode, boolean useBytecodeCache) {
        this.builderImpl = new ModelBuilderInteractiveImpl(this);
        impl = builderImpl;
        
        impl.analyzeBytecode(analyzingBytecode);
        impl.useProjectCache(useBytecodeCache);
    }
    
    /**
     * Creates an interactive-mode model builder.
     */
    public ModelBuilderInteractive() {
        this(false, false);
    }
    
    /**
     * Creates an interactive-mode model builder.
     * @param analyzingBytecode {@code true} if byte-code analysis is performed, otherwise {@code false}
     */
    public ModelBuilderInteractive(boolean analyzingBytecode) {
        this(analyzingBytecode, false);
    }
    
    /**
     * Starts this builder.
     */
    public void start() {
        builderImpl.start();
    }
    
    /**
     * Stops this builder.
     */
    public void stop() {
        builderImpl.stop();
        unbuild();
    }
    
    /**
     * obtains the Eclipse console for the plug-in.
     * @return
     */
    public SrcPlatConsole getConsole() {
        return builderImpl.getConsole();
    }
    
    /**
     * Builds a source code model for a target file.
     * @param project the target file
     * @return data for the created project containing the target file
     */
    public JavaProject build(IFile file) {
        return builderImpl.build(file);
    }
    
    /**
     * Builds a source code model for a target project.
     * @param project the target project
     * @return the created project data
     */
    public JavaProject build(IProject project) {
        return builderImpl.build(project);
    }
    
    /**
     * Builds a source code model for a target project.
     * @param project the target project
     * @return the created project data
     */
    public JavaProject build(IJavaProject project) {
        return builderImpl.build(project);
    }
}
