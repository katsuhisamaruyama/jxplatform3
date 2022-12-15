/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.plugin.internal;

import org.jtool.jxplatform.plugin.Activator;
import org.jtool.jxplatform.project.ConsoleProgressMonitor;
import org.jtool.jxplatform.project.ModelBuilderImpl;
import org.jtool.jxplatform.project.NullConsoleProgressMonitor;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.JavaFile;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.internal.JavaASTVisitor;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

/**
 * A builder implementation with interactive mode that builds models related to Java source code.
 * 
 * @author Katsuhisa Maruyama
 */
public class ModelBuilderPluginImpl extends ModelBuilderImpl {
    
    private IProject project;
    
    static final ConsoleProgressMonitor consoleProgressMonitor = new ConsoleProgressMonitorForPlugin();
    static final ConsoleProgressMonitor nullConsoleProgressMonitor = new NullConsoleProgressMonitor();
    
    public ModelBuilderPluginImpl(IProject project) {
        assert project != null;
        
        this.project = project;
    }
    
    @Override
    public ConsoleProgressMonitor getConsoleProgressMonitor() {
        return visible ? consoleProgressMonitor : nullConsoleProgressMonitor;
    }
    
    @Override
    public void parseFile(JavaProject jproject, List<File> sourceFiles) {
        Set<ICompilationUnit> units = collectAllCompilationUnits(jproject, sourceFiles);
        
        try {
            ConsoleProgressMonitor monitor = getConsoleProgressMonitor();
            monitor.printMessage("Target = " + jproject.getPath() + " (" + jproject.getName() + ")");
            monitor.printMessage("** Ready to parse " + units.size() + " files");
            
            ASTParser parser = getParser(jproject);
            IWorkbenchWindow workbenchWindow = Activator.getPlugin().getWorkbenchWindow();
            workbenchWindow.run(true, true, new IRunnableWithProgress() {
                
                @Override
                public void run(IProgressMonitor pm) throws InvocationTargetException, InterruptedException {
                    monitor.begin(units.size());
                    pm.beginTask("Parsing files... ", units.size());
                    int count = 1;
                    
                    for (ICompilationUnit icu : units) {
                        pm.subTask(count + "/" + units.size() + " - " + icu.getPath().toString());
                        
                        parse(parser, jproject, icu);
                        
                        if (pm.isCanceled()) {
                            monitor.done();
                            pm.done();
                            throw new InterruptedException();
                        }
                        
                        monitor.work(1);
                        pm.worked(1);
                        count++;
                    }
                    
                    monitor.done();
                    pm.done();
                }
            });
        } catch (InvocationTargetException | InterruptedException e) { /* empty */ }
    }
    
    private Set<ICompilationUnit> collectAllCompilationUnits(JavaProject jproject, List<File> sourceFiles) {
        IWorkspaceRoot workspaceRoot = Activator.getWorkspaceRoot();
        IPath projectPath = project.getProject().getFullPath();
        
        Set<ICompilationUnit> units = new HashSet<>();
        for (File file : sourceFiles) {
            String filename = JavaFile.getRelativePath(file.getAbsolutePath(), jproject.getPath());
            IPath filepath = projectPath.append(filename);
            IFile ifile = workspaceRoot.getFile(filepath);
            ICompilationUnit icu = JavaCore.createCompilationUnitFrom(ifile);
            units.add(icu);
        }
        
        Set<ICompilationUnit> unsavedChanges = new HashSet<>();
        for (ICompilationUnit icu : units) {
            try {
                if (icu.hasUnsavedChanges()) {
                    unsavedChanges.add(icu);
                }
            } catch (JavaModelException e) { /* empty */ }
        }
        
        if (unsavedChanges.size() > 0) {
            Shell shell = Activator.getPlugin().getWorkbenchWindow().getShell();
            boolean saveOk = MessageDialog.openConfirm(shell, "Save files", "Save files containing unsaved changes?");
            if (saveOk) {
                for (ICompilationUnit icu : units) {
                    try {
                        icu.save(null, true);
                    } catch (JavaModelException e) { /* empty */ }
                }
            } else {
                units.clear();
            }
        }
        return units;
    }
    
    private void parse(ASTParser parser, JavaProject jproject, ICompilationUnit icu) {
        parser.setSource(icu);
        
        CompilationUnit cu = (CompilationUnit)parser.createAST(null);
        if (cu != null) {
            String code = getSource(icu);
            if (code != null) {
                if (getParseErrors(cu).size() == 0) {
                    JavaFile jfile = new JavaFile(cu, icu.getPath().toString(), code, JavaCore.getEncoding(), jproject);
                    JavaASTVisitor visitor = new JavaASTVisitor(jfile);
                    cu.accept(visitor);
                    visitor.terminate();
                } else {
                    monitor.printError("Incomplete parse: " + icu.getPath().makeRelative().toString());
                }
            }
        }
    }
    
    private String getSource(ICompilationUnit icu) {
        try {
            return icu.getSource();
        } catch (JavaModelException e) {
            return null;
        }
    }
    
    @Override
    public void collectInfo(JavaProject jproject, List<JavaClass> classes) {
        try {
            ConsoleProgressMonitor monitor = getConsoleProgressMonitor();
            monitor.printMessage("** Ready to build java models of " + classes.size() + " classes");
            
            IWorkbenchWindow workbenchWindow = Activator.getPlugin().getWorkbenchWindow();
            workbenchWindow.run(true, true, new IRunnableWithProgress() {
                
                @Override
                public void run(IProgressMonitor pm) throws InvocationTargetException, InterruptedException {
                    monitor.begin(classes.size());
                    pm.beginTask("Parsing files... ", classes.size());
                    int count = 1;
                    
                    for (JavaClass jclass : classes) {
                        pm.subTask(count + "/" + classes.size() + " - " + jclass.getName());
                        
                        jproject.collectInfo(jclass);
                        
                        if (pm.isCanceled()) {
                            monitor.done();
                            pm.done();
                            throw new InterruptedException();
                        }
                        
                        monitor.work(1);
                        pm.worked(1);
                        count++;
                    }
                    
                    monitor.done();
                    pm.done();
                }
            });
        } catch (InvocationTargetException | InterruptedException e) { /* empty */ }
    }
}
