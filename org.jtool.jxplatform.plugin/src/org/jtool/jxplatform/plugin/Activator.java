/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.plugin;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.IWorkbenchPage;
import org.osgi.framework.BundleContext;

/**
 * The activator of the plug-in.
 * 
 * @author Katsuhisa Maruyama
 */
public class Activator extends AbstractUIPlugin {
    
    /**
     * The plug-in identification.
     */
    public static final String PLUGIN_ID = "org.jtool.jxplatform3.plugin";
    
    private JxConsole console;
    
    /**
     * A shared plug-in object.
     */
    private static Activator plugin;
    
    /**
     * An interactive-mode builder.
     */
    private ModelBuilderPluginManager builder;
    
    /**
     * Creates a plug-in runtime object.
     */
    public Activator() {
        super();
        
        console = new JxConsole();
        builder = new ModelBuilderPluginManager();
    }
    
    /**
     * Returns a console that displays a message in Eclipse.
     * @return the console
     */
    public JxConsole getConsole() {
        return console;
    }
    
    /**
     * Performs actions when the plug-in is activated.
     * @param context the bundle context for this plug-in
     * @throws Exception if this plug-in did not start up properly
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
        
        builder.start();
    }
    
    /**
     * Performs actions when when the plug-in is shut down.
     * @param context the bundle context for this plug-in
     * @throws Exception if this this plug-in fails to stop
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        
        builder.stop();
    }
    
    /**
     * Returns the default plug-in instance.
     * @return the default plug-in instance
     */
    public static Activator getPlugin() {
        return plugin;
    }
    
    /**
     * Returns the builder plug-in.
     */
    public ModelBuilderPluginManager getModelBuilder() {
        return builder;
    }
    
    /**
     * Obtains the workbench window that is currently active.
     * @return the workbench window
     */
    public IWorkbenchWindow getWorkbenchWindow() {
        return PlatformUI.getWorkbench().getActiveWorkbenchWindow();
    }
    
    /**
     * Obtains the workbench page that is currently active.
     * @return the workbench page
     */
    public IWorkbenchPage getWorkbenchPage() {
        IWorkbenchWindow window = getWorkbenchWindow();
        return window.getActivePage();
    }
    
    /**
     * Obtains the root of the workspace.
     * @return the workspace root
     */
    public static IWorkspaceRoot getWorkspaceRoot() {
        return ResourcesPlugin.getWorkspace().getRoot();
    }
    
    /**
     * Obtains the path of the workspace.
     * @return the workspace path
     */
    public static String getWorkspacePath() {
        return getWorkspaceRoot().getLocation().toFile().getAbsolutePath().toString();
    }
}
