/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */
 
package org.jtool.jxplatform.plugin.handler;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.plugin.Activator;
import org.jtool.jxplatform.plugin.JxConsole;
import org.jtool.jxplatform.plugin.ModelBuilderPluginManager;
import org.jtool.jxplatform.builder.ModelBuilder;
import org.jtool.srcmodel.JavaClass;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.ui.handlers.HandlerUtil;
import java.util.List;

/**
 * Performs the action that builds CFGs of Java source files within a project.
 * 
 * @author Katsuhisa Maruyama
 */
public class CFGAction extends AbstractHandler {
    
    private JxConsole console;
    
    /**
     * Creates an action handler.
     */
    public CFGAction() {
        console = Activator.getPlugin().getConsole();
    }
    
    /**
     * Executes the action.
     * @param event an event containing information on execution
     * @throws ExecutionException if an exception occurred during execution
     */
    public Object execute(ExecutionEvent event) throws ExecutionException {
        ISelection selection = HandlerUtil.getActiveMenuSelection(event);
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection structured = (IStructuredSelection)selection;
            
            Object elem = structured.getFirstElement();
            if (elem instanceof IJavaProject) {
                ModelBuilderPluginManager manager = Activator.getPlugin().getModelBuilder();
                manager.setConsoleVisible(true);
                JavaProject jproject = manager.build((IJavaProject)elem);
                
                buildCFGs(jproject);
                console.println("Build a Java source code model: " + jproject.getPath());
                console.flush();
            }
        }
        return null;
    }
    
    private void buildCFGs(JavaProject jproject) {
        ModelBuilder builder = jproject.getModelBuilder();
        List<JavaClass> classes = jproject.getClasses();
        int size = classes.size();
        int count = 1;
        console.println();
        console.println("** Building CFGs of " + size + " classes ");
        
        for (JavaClass jclass : classes) {
            console.print("(" + count + "/" + size + ")");
            builder.getCCFG(jclass);
            console.print(" - " + jclass.getQualifiedName() + " - CCFG\n");
            console.flush();
            count++;
        }
    }
}
