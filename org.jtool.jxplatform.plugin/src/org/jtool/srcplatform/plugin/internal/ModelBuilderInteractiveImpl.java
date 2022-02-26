/*
 *  Copyright 2021
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.plugin.internal;

import org.jtool.srcplatform.plugin.Activator;
import org.jtool.srcplatform.plugin.IFileChangeListener;
import org.jtool.srcplatform.plugin.SrcPlatConsole;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.JavaFile;
import org.jtool.srcmodel.JavaPackage;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.builder.JavaASTVisitor;
import org.jtool.srcmodel.builder.ProjectStore;
import org.jtool.jxplatform.builder.ModelBuilder;
import org.jtool.jxplatform.bytecode.BytecodeClassStore;
import org.jtool.jxplatform.bytecode.BytecodeName;
import org.jtool.jxplatform.project.ModelBuilderImpl;
import org.jtool.jxplatform.project.Logger;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * A builder implementation for a plug-in that builds models related to Java source code.
 * 
 * @author Katsuhisa Maruyama
 */
public class ModelBuilderInteractiveImpl extends ModelBuilderImpl {
    
    private IJavaProject javaProject;
    
    private ResourceChangeHandler resourceChangeListener;
    
    private Map<String, ICompilationUnit> compilationUnitMap = new HashMap<>();
    private Set<IFile> dirtyFiles = new HashSet<>();
    
    private SrcPlatConsole console = new SrcPlatConsole();
    
    public ModelBuilderInteractiveImpl(ModelBuilder modelBuiler) {
        super(modelBuiler);
        resourceChangeListener = new ResourceChangeHandler(this);
    }
    
    @Override
    public boolean isUnderPlugin() {
        return true;
    }
    
    public void start() {
        resourceChangeListener.register();
    }
    
    public void stop() {
        resourceChangeListener.unregister();
    }
    
    public SrcPlatConsole getConsole() {
        return console;
    }
    
    public void addFileChangeListener(IFileChangeListener listener) {
        resourceChangeListener.addFileChangeListener(listener);
    }
    
    public void removeFileChangeListener(IFileChangeListener listener) {
        resourceChangeListener.removeFileChangeListener(listener);
    }
    
    void addFile(IFile file) {
        dirtyFiles.add(file);
    }
    
    void removeFile(IFile file) {
        JavaProject jproject = ProjectStore.getInstance().getProject(file.getProject().getFullPath().toString());
        if (jproject == null) {
            return;
        }
        
        Set<JavaFile> files = collectDependentClasses(jproject, file);
        for (JavaFile jfile : files) {
            for (JavaClass jc : jfile.getClasses()) {
                jproject.removeClass(jc);
            }
            jproject.removeFile(jfile.getPath());
        }
        
        for (JavaPackage jpackage : jproject.getPackages()) {
            if (jpackage.getClasses().size() == 0) {
                jproject.removePackage(jpackage);
            }
        }
    }
    
    void changeFile(IFile file) {
        removeFile(file);
        addFile(file);
    }
    
    private Set<JavaFile> collectDependentClasses(JavaProject jproject, IFile file) {
        JavaFile jfile = jproject.getFile(file.getFullPath().toString());
        
        Set<JavaClass> classes = new HashSet<>();
        for (JavaClass jclass : jfile.getClasses()) {
            classes.addAll(jfile.getProject().collectDependentClasses(jclass));
        }
        
        Set<JavaFile> files = new HashSet<>();
        for (JavaClass jclass : classes) {
            files.add(jclass.getFile());
        }
        return files;
    }
    
    private void setPaths(IJavaProject project, JavaProject jproject) {
        jproject.setClassPath(getClassPath(project));
        jproject.setSourceBinaryPaths(getSourcePath(project), getBinaryPath(project));
    }
    
    private String[] getClassPath(IJavaProject project) {
        try {
            List<String> classPathList = new ArrayList<>();
            for (IClasspathEntry entry : project.getResolvedClasspath(true)) {
                if (entry.getEntryKind() != IClasspathEntry.CPE_SOURCE) {
                    classPathList.add(entry.getPath().makeAbsolute().toOSString());
                }
            }
            return classPathList.toArray(new String[classPathList.size()]);
        } catch (JavaModelException e) {
            return new String[0];
        }
    }
    
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
    
    public JavaProject build(IFile file) {
        javaProject = JavaCore.create(file.getProject());
        JavaProject jproject = ProjectStore.getInstance().getProject(file.getProject().getFullPath().toString());
        if (jproject == null || jproject.getFiles().size() == 0) {
            if (javaProject != null) {
                buildWhole(javaProject, jproject);
            }
            
        } else {
            if (javaProject != null) {
                jproject = ProjectStore.getInstance().getProject(javaProject.getPath().toString());
                setPaths(javaProject, jproject);
                
                Set<ICompilationUnit> compilationUnits = new HashSet<>();
                compilationUnits.add(JavaCore.createCompilationUnitFrom(file));
                Set<JavaFile> files = collectDependentClasses(jproject, file);
                for (JavaFile jf : files) {
                    ICompilationUnit icu = compilationUnitMap.get(jf.getPath());
                    if (icu != null) {
                        compilationUnits.add(icu);
                        compilationUnitMap.put(icu.getPath().toString(), icu);
                    }
                }
                
                buildJavaModel(compilationUnits, jproject);
            }
        }
        return jproject;
    }
    
    public JavaProject build(IProject project) {
        return build(JavaCore.create(project));
    }
    
    public JavaProject build(IJavaProject project) {
        javaProject = project;
        JavaProject jproject = ProjectStore.getInstance().getProject(project.getPath().toString());
        jproject.setModelBuilderImpl(this);
        if (jproject == null || jproject.getFiles().size() == 0) {
            return buildWhole(javaProject, jproject);
        }
        
        Set<ICompilationUnit> compilationUnits = new HashSet<ICompilationUnit>();
        jproject = ProjectStore.getInstance().getProject(project.getPath().toString());
        setPaths(project, jproject);
        
        for (IFile file : dirtyFiles) {
            compilationUnits.add(JavaCore.createCompilationUnitFrom(file));
            Set<JavaFile> files = collectDependentClasses(jproject, file);
            for (JavaFile jf : files) {
                ICompilationUnit icu = compilationUnitMap.get(jf.getPath());
                if (icu != null) {
                    compilationUnits.add(icu);
                    compilationUnitMap.put(icu.getPath().toString(), icu);
                }
            }
        }
        dirtyFiles.clear();
        
        buildJavaModel(compilationUnits, jproject);
        return jproject;
    }
    
    @Override
    public void update(JavaProject jproject) {
        ProjectStore.getInstance().removeProject(jproject.getPath());
        buildWhole(javaProject, jproject);
    }
    
    private JavaProject buildWhole(IJavaProject project, JavaProject jproject) {
        javaProject = project;
        String name = javaProject.getProject().getName();
        String path = javaProject.getProject().getFullPath().toString();
        String dir = javaProject.getProject().getLocation().toString();
        
        jproject = new JavaProject(name, path, dir);
        jproject.setModelBuilderImpl(this);
        jproject.getCFGStore().create(jproject);
        
        setPaths(javaProject, jproject);
        ProjectStore.getInstance().addProject(jproject);
        
        Set<ICompilationUnit> compilationUnits = collectCompilationUnits(project, jproject);
        buildJavaModel(compilationUnits, jproject);
        return jproject;
    }
    
    private Set<ICompilationUnit> collectCompilationUnits(IJavaProject project, JavaProject jproject) {
        Set<ICompilationUnit> compilationUnits = new HashSet<>();
        if (project.getElementName().equals("RemoteSystemsTempFiles")) {
            return compilationUnits;
        }
        
        try {
            IPackageFragment[] packages = project.getPackageFragments();
            for (int i = 0; i < packages.length; i++) {
                ICompilationUnit[] units = packages[i].getCompilationUnits();
                for (int j = 0; j < units.length; j++) {
                    ICompilationUnit icu = units[j];
                    if (icu.hasUnsavedChanges()) {
                        icu.save(null, true);
                    }
                    compilationUnits.add(icu);
                    compilationUnitMap.put(icu.getPath().toString(), icu);
                }
            }
        } catch (JavaModelException e) {
            printError("JavaModelException occurred: " + e.getMessage());
        }
        return compilationUnits;
    }
    
    private boolean buildJavaModel(final Set<ICompilationUnit> cunits, JavaProject jproject) {
        try {
            final ASTParser parser = getParser();
            IWorkbenchWindow workbenchWindow = Activator.getPlugin().getWorkbenchWindow();
            workbenchWindow.run(true, true, new IRunnableWithProgress() {
                
                @Override
                public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                    int size = cunits.size();
                    monitor.beginTask("Parsing files... ", size);
                    int count = 1;
                    for (ICompilationUnit icu : cunits) {
                        monitor.subTask(count + "/" + size + " - " + icu.getPath().toString());
                        try {
                            buildJavaModel(parser, icu, jproject);
                        } catch (NullPointerException e) {
                            printError("* Fatal error occurred. Skip the paser of " + icu.getPath().toString());
                            e.printStackTrace();
                        }
                        if (monitor.isCanceled()) {
                            monitor.done();
                            throw new InterruptedException();
                        }
                        monitor.worked(1);
                        count++;
                    }
                    monitor.done();
                }
            });
            
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            printError("* InvocationTargetException occurred because " + cause);
            return false;
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }
    
    private void buildJavaModel(ASTParser parser, ICompilationUnit icu, JavaProject jproject) {
        parser.setSource(icu);
        
        CompilationUnit cu = (CompilationUnit)parser.createAST(null);
        if (cu != null) {
            String code = getSource(icu);
            if (code != null) {
                JavaFile jfile = new JavaFile(cu, icu.getPath().toString(), code, JavaCore.getEncoding(), jproject);
                if (getParseErrors(cu).size() != 0) {
                    printError("Incomplete parse: " + icu.getPath().makeRelative().toString());
                }
                
                JavaASTVisitor visitor = new JavaASTVisitor(jfile);
                cu.accept(visitor);
                visitor.terminate();
            }
        }
    }
    
    public String getSource(ICompilationUnit icu) {
        try {
            return icu.getSource();
        } catch (JavaModelException e) {
            return null;
        }
    }
    
    private void printError(String mesg) {
        Logger.getInstance().printError(mesg);
        console.println(mesg);
    }
    
    @Override
    public void loadBytecode(JavaProject jproject) {
        BytecodeClassStore bcStore = jproject.getCFGStore().getBCStore();
        
        try {
            IWorkbenchWindow workbenchWindow = Activator.getPlugin().getWorkbenchWindow();
            workbenchWindow.run(true, true, new IRunnableWithProgress() {
                
                @Override
                public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                    Set<BytecodeName> names = bcStore.getBytecodeNamesToBeLoaded();
                    monitor.beginTask("Parsing external classes... ", names.size());
                    int count = 1;
                    for (BytecodeName bytecodeName : names) {
                        monitor.subTask(count + "/" + names.size() + " - " + bytecodeName);
                        try {
                            bcStore.loadBytecode(bytecodeName);
                        } catch (NullPointerException e) {
                            printError("* Fatal error occurred. Skip the paser of " + bytecodeName);
                            e.printStackTrace();
                        }
                        if (monitor.isCanceled()) {
                            monitor.done();
                            throw new InterruptedException();
                        }
                        monitor.worked(1);
                        count++;
                    }
                    monitor.done();
                }
            });
            
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            printError("* InvocationTargetException occurred because " + cause);
        } catch (InterruptedException e) { /* empty */ }
        
        bcStore.setClassHierarchy();
    }
}
