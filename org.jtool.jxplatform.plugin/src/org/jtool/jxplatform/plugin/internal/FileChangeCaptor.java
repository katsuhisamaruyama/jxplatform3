/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.plugin.internal;

import org.jtool.jxplatform.plugin.ModelBuilderPluginManager;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

/**
 * An object that captures added, removed, and changed files.
 * 
 * @author Katsuhisa Maruyama
 */
public class FileChangeCaptor implements IResourceChangeListener {
    
    private ModelBuilderPluginManager modelBuilderManager;
    
    public FileChangeCaptor(ModelBuilderPluginManager modelBuilderManager) {
        this.modelBuilderManager = modelBuilderManager;
    }
    
    public void register() {
        ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
    }
    
    public void unregister() {
        ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
    }
    
    @Override
    public void resourceChanged(IResourceChangeEvent event) {
        try {
            if (event.getType() == IResourceChangeEvent.POST_CHANGE) {
                ResourceDeltaVisitor visitor = new ResourceDeltaVisitor();
                event.getDelta().accept(visitor);
            }
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * A visitor that checks resource changes and notifies file changes among them.
     * 
     * @author Katsuhisa Maruyama
     */
    private class ResourceDeltaVisitor implements IResourceDeltaVisitor {
        
        @Override
        public boolean visit(IResourceDelta delta) throws CoreException {
            IResource resource = delta.getResource();
            
            if (resource != null && resource.getType() == IResource.FILE &&
                    resource.getFileExtension().equals(".java")) {
                if (delta.getKind() == IResourceDelta.ADDED) {
                    modelBuilderManager.addFile((IFile)resource);
                } else if (delta.getKind() == IResourceDelta.REMOVED) {
                    modelBuilderManager.removeFile((IFile)resource);
                } else if (delta.getKind() == IResourceDelta.CHANGED) {
                    modelBuilderManager.changeFile((IFile)resource);
                }
            }
            return true;
        }
    }
}
