/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */
 
package org.jtool.jxplatform.plugin.handler;

import org.jtool.jxplatform.plugin.Activator;
import org.jtool.jxplatform.plugin.ModelBuilderPluginManager;
import org.jtool.jxplatform.plugin.JxConsole;
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
     * Executes the action for building source code model.
     * @param event an event containing information on execution
     * @throws ExecutionException if an exception occurred during execution
     */
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        ISelection selection = HandlerUtil.getActiveMenuSelection(event);
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection structured = (IStructuredSelection)selection;
            
            Object elem = structured.getFirstElement();
            if (elem instanceof IJavaProject) {
                ModelBuilderPluginManager manager = Activator.getPlugin().getModelBuilder();
                manager.setConsoleVisible(true);
                JavaProject jproject = manager.build((IJavaProject)elem);
                
                JxConsole console = Activator.getPlugin().getConsole();
                console.println("Build a Java source code model: " + jproject.getPath());
            }
        }
        return null;
    }
}
