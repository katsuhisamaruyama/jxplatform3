/*
 *  Copyright 2021
 *  Software Science and Technology Lab., Ritsumeikan University
 */
 
package org.jtool.srcplatform.plugin.handler;

import org.jtool.srcplatform.plugin.Activator;
import org.jtool.srcplatform.plugin.ModelBuilderInteractive;
import org.jtool.srcmodel.JavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Performs the action that builds models of Java source files within a project.
 * 
 * @author Katsuhisa Maruyama
 */
public class BuildAction extends AbstractHandler {
    
    /**
     * Creates an action handler.
     */
    public BuildAction() {
    }
    
    /**
     * Executes the action.
     * @param event an event containing information on execution
     * @throws ExecutionException if an exception occurred during execution
     */
    @SuppressWarnings("unused")
    public Object execute(ExecutionEvent event) throws ExecutionException {
        ISelection selection = HandlerUtil.getActiveMenuSelection(event);
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection structured = (IStructuredSelection)selection;
            
            Object elem = structured.getFirstElement();
            if (elem instanceof IJavaProject) {
                
                ModelBuilderInteractive modelBuilder = Activator.getPlugin().getModelBuilder();
                modelBuilder.setLogVisible(true);
                JavaProject jproject = modelBuilder.build((IJavaProject)elem);
            }
        }
        return null;
    }
}
