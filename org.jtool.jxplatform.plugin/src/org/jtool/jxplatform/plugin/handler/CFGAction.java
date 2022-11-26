/*
 *  Copyright 2021
 *  Software Science and Technology Lab., Ritsumeikan University
 */
 
package org.jtool.jxplatform.plugin.handler;

import org.jtool.srcmodel.JavaProject;
import org.jtool.cfg.CCFG;
import org.jtool.jxplatform.plugin.Activator;
import org.jtool.jxplatform.plugin.ModelBuilderInteractive;
import org.jtool.jxplatform.plugin.SrcPlatConsole;
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
    
    private SrcPlatConsole console;
    
    /**
     * Creates an action handler.
     */
    public CFGAction() {
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
                console = modelBuilder.getConsole();
                
                JavaProject jproject = modelBuilder.build((IJavaProject)elem);
                CCFG[] ccfgs = buildCFGsForTest(modelBuilder, jproject.getClasses());
            }
        }
        return null;
    }
    
    private CCFG[] buildCFGsForTest(ModelBuilderInteractive builder, List<JavaClass> classes) {
        int size = classes.size();
        CCFG[] ccfgs = new CCFG[size];
        int count = 1;
        console.println();
        console.println("** Building CFGs of " + size + " classes ");
        
        for (JavaClass jclass : classes) {
            console.print("(" + count + "/" + size + ")");
            ccfgs[count - 1] = builder.getCCFG(jclass);
            console.print(" - " + jclass.getQualifiedName() + " - CCFG\n");
            count++;
        }
        return ccfgs;
    }
}
