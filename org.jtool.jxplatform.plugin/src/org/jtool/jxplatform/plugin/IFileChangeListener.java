/*
 *  Copyright 2021
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.plugin;

import org.eclipse.core.resources.IFile;
import java.util.Set;

/**
 * An interface for receiving events when a file is added, removed, and changed.
 * 
 * @author Katsuhisa Maruyama
 */
public interface IFileChangeListener {
    
    /**
     * Notifies when files are added.
     * @param files the collection of the added files
     */
    public void fileAdded(Set<IFile> files);
    
    /**
     * Notifies when files are removed.
     * @param files the collection of the removed files
     */
    public void fileRemoved(Set<IFile> files);
    
    /**
     * Notifies when files are changed.
     * @param files the collection of the changed files
     */
    public void fileChanged(Set<IFile> files);
}
