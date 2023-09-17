/*
 *  Copyright 2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.plugin;

import org.jtool.jxplatform.plugin.internal.ModelBuilderPluginImpl;
import org.jtool.jxplatform.plugin.internal.FileChangeCaptor;
import org.jtool.jxplatform.plugin.internal.CFGStoreForPlugin;
import org.jtool.jxplatform.builder.IncrementalModelBuilder;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.internal.ProjectStore;
import org.jtool.cfg.internal.CFGStore;
import org.jtool.pdg.internal.PDGStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A builder implementation with interactive mode that builds models related to Java source code.
 * 
 * @author Katsuhisa Maruyama
 */
public class ModelBuilderPluginManager {
    
    /**
     * The collection of builders.
     */
    private Map<String, IncrementalModelBuilder> builders;
    
    /**
     * An object that captures file changes.
     */
    private FileChangeCaptor resourceChangeCaptor;
    
    /**
     * A flag that indicates whether information is displayed on console.
     */
    private boolean visible = true;
    
    /**
     * Creates a builder implementation for a plug-in that incrementally builds models related to Java source code.
     */
    public ModelBuilderPluginManager() {
        builders = new HashMap<>();
        resourceChangeCaptor = new FileChangeCaptor(this);
    }
    
    /**
     * Sets whether information is displayed on console.
     * @param visible {@code true} if the information is displayed, otherwise {@code false}
     */
    public void setConsoleVisible(boolean visible) {
        this.visible = visible;
    }
    
    /**
     * Builds a source model for a project corresponding to a Java project in Eclipse.
     * @param project the project in Eclipse
     * @return the created project
     */
    public JavaProject build(IJavaProject project) {
        CFGStore cfgStore = new CFGStoreForPlugin();
        PDGStore pdgStore = new PDGStore(cfgStore);
        String name = project.getProject().getName();
        String path = project.getProject().getLocation().toOSString();
        
        JavaProject jproject = new JavaProject(name, path, path, path, cfgStore, pdgStore);
        jproject.setSourceBinaryPaths(getSourcePath(project), getBinaryPath(project));
        jproject.setClassPath(getClassPath(project));
        jproject.setCompilerVersions(JavaCore.VERSION_11, JavaCore.VERSION_11);
        jproject.getCFGStore().create(jproject);
        ProjectStore.getInstance().addProject(jproject);
        
        List<JavaProject> jprojects = new ArrayList<>();
        jprojects.add(jproject);
        
        ModelBuilderPluginImpl modelBuilderImpl = new ModelBuilderPluginImpl(project.getProject());
        IncrementalModelBuilder modelBuilder = new IncrementalModelBuilder(modelBuilderImpl, jprojects);
        modelBuilder.setConsoleVisible(visible);
        jproject.setModelBuilder(modelBuilder);
        
        modelBuilderImpl.build(jproject);
        
        builders.put(path, modelBuilder);
        
        return jproject;
    }
    
    /**
     * Re-builds a source code model from all files.
     * @param project the project in Eclipse
     */
    public void rebuild(IJavaProject project) {
        String path = project.getProject().getLocation().toOSString();
        IncrementalModelBuilder incrementalBuilder = builders.get(path);
        incrementalBuilder.getJavaProjects().get(0).setClassPath(getClassPath(project));
        incrementalBuilder.rebuild();
    }
    
    /**
     * Builds a source code model based on added and removed files.
     * @param project the project in Eclipse
     */
    public void incrementalBuild(IJavaProject project) {
        String path = project.getProject().getLocation().toOSString();
        IncrementalModelBuilder incrementalBuilder = builders.get(path);
        
        incrementalBuilder.getJavaProjects().get(0).setClassPath(getClassPath(project));
        incrementalBuilder.incrementalBuild();
    }
    
    /**
     * Obtains the source paths from a Java project in Eclipse.
     * @param project the project in Eclipse
     * @return the array of source paths
     */
    private String[] getSourcePath(IJavaProject project) {
        try {
            IWorkspaceRoot workSpaceRoot = ResourcesPlugin.getWorkspace().getRoot();
            List<String> sourcePathList = new ArrayList<>();
            for (IPackageFragmentRoot root : project.getAllPackageFragmentRoots()) {
                if (root.getKind() == IPackageFragmentRoot.K_SOURCE) {
                    sourcePathList.add(workSpaceRoot.getFolder(root.getPath()).getLocation().toOSString());
                }
            }
            return sourcePathList.toArray(new String[sourcePathList.size()]);
        } catch (JavaModelException e) {
            return new String[0];
        }
    }
    
    /**
     * Obtains the binary paths from a Java project in Eclipse.
     * @param project the project in Eclipse
     * @return the array of binary paths
     */
    private String[] getBinaryPath(IJavaProject project) {
        String path[] = new String[1];
        try {
            IWorkspaceRoot workSpaceRoot = ResourcesPlugin.getWorkspace().getRoot();
            path[0] = workSpaceRoot.getFolder(project.getOutputLocation()).getLocation().toOSString();
            return path;
        } catch (JavaModelException e) {
            return new String[0];
        }
    }
    
    /**
     * Obtains the class paths from a Java project in Eclipse.
     * @param project the project in Eclipse
     * @return the array of class paths
     */
    String[] getClassPath(IJavaProject project) {
        try {
            List<String> classPaths = new ArrayList<>();
            for (IClasspathEntry entry : project.getResolvedClasspath(true)) {
                if (entry.getEntryKind() != IClasspathEntry.CPE_SOURCE) {
                    classPaths.add(entry.getPath().makeAbsolute().toOSString());
                }
            }
            
            try {
                for (IProject proj : project.getProject().getReferencedProjects()) {
                    proj.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, null);
                    IJavaProject iproj = JavaCore.create(proj);
                    classPaths.addAll(Arrays.asList(getBinaryPath(iproj)));
                }
            } catch (CoreException e) { /* empty */ }
            return classPaths.toArray(new String[classPaths.size()]);
        } catch (JavaModelException e) {
            return new String[0];
        }
    }
    
    /**
     * Starts the capturing of file changes.
     */
    void start() {
        resourceChangeCaptor.register();
    }
    
    /**
     * Stops the capturing of file changes and disposes the created models.
     */
    void stop() {
        resourceChangeCaptor.unregister();
        for (IncrementalModelBuilder incrementalBuilder : builders.values()) {
            incrementalBuilder.unbuild();
        }
    }
    
    /**
     * Registers the added file.
     * @param file the added file to be registered
     */
    public void addFile(IFile file) {
    }
    
    /**
     * Registers the removed file.
     * @param file the removed file to be registered
     */
    public void removeFile(IFile file) {
    }
    
    /**
     * Registers the changed file.
     * @param file the change file to be registered
     */
    public void changeFile(IFile file) {
    }
}
