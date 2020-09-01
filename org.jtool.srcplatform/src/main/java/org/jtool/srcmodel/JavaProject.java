/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel;

import org.jtool.srcmodel.builder.ProjectStore;
import org.jtool.srcplatform.project.ModelBuilderImpl;
import org.jtool.cfg.builder.CFGStore;
import org.jtool.pdg.builder.PDGStore;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.io.File;

/**
 * An object representing a project that contains source files to be analyzed.
 * 
 * @author Katsuhisa Maruyama
 */
public class JavaProject {
    
    /**
     * The name of this project.
     */
    protected String name;
    
    /**
     * The absolute path string that indicates the root directory of this project relative to the workspace.
     */
    protected String pathInWorkspace;
    
    /**
     * The absolute path string that indicates the root directory of this project in the file system.
     */
    protected String path;
    
    /**
     * The map paths and source files corresponding to the paths.
     */
    protected Map<String, JavaFile> fileStore = new HashMap<>();
    
    /**
     * The map package names and packages corresponding to the names.
     */
    protected Map<String, JavaPackage> packageStore = new HashMap<>();
    
    /**
     * The map the fully-qualified names and classes corresponding to the names.
     */
    protected Map<String, JavaClass> classStore = new HashMap<>();
    
    /**
     * The map the fully-qualified names and outside classes corresponding to the names.
     */
    protected Map<String, JavaClass> externalClasseStore = new HashMap<>();
    
    /**
     * The class paths that store class files in this project.
     */
    protected String[] classPath;
    
    /**
     * The class paths that store source files in this project.
     */
    protected String[] sourcePath;
    
    /**
     * The class paths that store binary files in this project.
     */
    protected String[] binaryPath;
    
    /**
     * The CFG repository that store CFGs for source files in this project.
     */
    protected CFGStore cfgStore;
    
    /**
     * The PDG repository that store PDGs for source files in this project.
     */
    protected PDGStore pdgStore;
    
    /**
     * A model builder that creates Java models in this project.
     */
    private ModelBuilderImpl modelBuilderImpl;
    
    /**
     * Creates a project that stores source files and their related information.
     * @param name the name of this project
     * @param wpath the absolute path that indicates the root directory of this project relative to the workspace
     * @param path the absolute path that indicates the root directory of this project in the file system
     */
    public JavaProject(String name, String wpath, String path) {
        this.name = name;
        this.pathInWorkspace = wpath;
        this.path = path;
        
        cfgStore = new CFGStore();
        pdgStore = new PDGStore(cfgStore);
    }
    
    /**
     * Sets a model builder implementation that creates Java models in this project.
     * @param modelBuilderImp the model builder implementation
     */
    public void setModelBuilderImpl(ModelBuilderImpl modelBuilderImpl) {
        this.modelBuilderImpl = modelBuilderImpl;
    }
    
    /**
     * Return the model builder implementation that creates Java models in this project.
     * @return the model builder
     */
    public ModelBuilderImpl getModelBuilderImpl() {
        return modelBuilderImpl;
    }
    
    /**
     * Tests if this project is under the workspace managed by an Eclipse's plug-in.
     * @return
     */
    public boolean isManagedByPlugin() {
        return modelBuilderImpl.isUnderPlugin();
    }
    
    /**
     * Obtains the collection of all projects.
     * @return the collection of all the projects that were already analyzed
     */
    public static List<JavaProject> getAllProjects() {
        return ProjectStore.getInstance().getProjects();
    }
    
    /**
     * Finds a project of interest
     * @param path the absolute path of the project to be retrieved
     * @return the found project, or {@code null} if there is no project related to the path
     */
    public static JavaProject findProject(String path) {
        return ProjectStore.getInstance().getProject(path);
    }
    
    /**
     * Clears information on this project.
     */
    public void clear() {
        cfgStore.destroy();
        pdgStore.destroy();
    }
    
    /**
     * Returns the name of this project.
     * @return the project name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the path string that indicates the root directory of this project relative to the workspace.
     * @return the absolute path of this project
     */
    public String getPathRelativeToWorkspace() {
        return pathInWorkspace;
    }
    
    /**
     * Returns the path string that indicates the root directory of this project in the file system.
     * @return the absolute path of this project
     */
    public String getPath() {
        return path;
    }
    
    /**
     * Adds a file to this project.
     * This method is not intended to be invoked by clients.
     * @param jfile the file to be added
     */
    public void addFile(JavaFile jfile) {
        if (fileStore.get(jfile.getPath()) == null) {
            fileStore.put(jfile.getPath(), jfile);
        }
    }
    
    /**
     * Removes a file from this project.
     * This method is not intended to be invoked by clients.
     * @param jfile the file to be removed
     */
    public void removeFile(String path) {
        JavaFile jfile = fileStore.get(path);
        if (jfile != null) {
            fileStore.remove(path);
        }
    }
    
    /**
     * Obtains the source file located at a given absolute path.
     * @param path the absolute path of the file in the file system
     * @return the found source file, {@code null} if no file is found
     */
    public JavaFile getFile(String path) {
        return fileStore.get(path);
    }
    
    /**
     * Obtains source files existing in this project.
     * @return the collection of the existing files
     */
    public List<JavaFile> getFiles() {
        return new ArrayList<>(fileStore.values());
    }
    
    /**
     * Obtains source files existing in this project.
     * @return the sorted collection of the existing files
     */
    public List<JavaFile> getSortedFiles() {
        return sortFiles(getFiles());
    }
    
    /**
     * Adds a package to this project.
     * This method is not intended to be invoked by clients.
     * @param jpackage the package to be added
     */
    protected void addPackage(JavaPackage jpackage) {
        if (packageStore.get(jpackage.getName()) == null) {
            packageStore.put(jpackage.getName(), jpackage);
        }
    }
    
    /**
     * Removes a package from this project.
     * This method is not intended to be invoked by clients.
     * @param jpackage the package to be removed
     */
    public void removePackage(JavaPackage jpackage) {
        packageStore.remove(jpackage.getName());
    }
    
    /**
     * Obtains packages appearing in this project.
     * @return the collection of the existing packages
     */
    public List<JavaPackage> getPackages() {
        return new ArrayList<>(packageStore.values());
    }
    
    /**
     * Obtains packages appearing in this project.
     * @return the sorted collection of the existing packages
     */
    public List<JavaPackage> getSortedPackages() {
        return sortPackages(getPackages());
    }
    
    /**
     * Obtains a package having a given name.
     * @param name the name of the package to be retrieved
     * @return the found package, or {@code null} if no package is found
     */
    public JavaPackage getPackage(String name) {
        if (name != null && name.length() > 0) {
            return packageStore.get(name);
        }
        return null;
    }
    
    /**
     * Adds a class to this project.
     * This method is not intended to be invoked by clients.
     * @param jclass the class to be added
     */
    protected void addClass(JavaClass jclass) {
        if (classStore.get(jclass.getQualifiedName().fqn()) == null) {
            classStore.put(jclass.getQualifiedName().fqn(), jclass);
        }
    }
    
    /**
     * Removes a class from this project.
     * This method is not intended to be invoked by clients.
     * @param jclass the class to be removed
     */
    public void removeClass(JavaClass jclass) {
        classStore.remove(jclass.getQualifiedName().fqn());
    }
    
    /**
     * Obtains classes existing in this project.
     * @return the collection of the existing classes
     */
    public List<JavaClass> getClasses() {
        return new ArrayList<>(classStore.values());
    }
    
    /**
     * Obtains classes existing in this project.
     * @return the sorted collection of the existing classes
     */
    public List<JavaClass> getSortedClasses() {
        return JavaClass.sortClasses(getClasses());
    }
    
    /**
     * Obtains a class having a given fully-qualified name
     * @param fqn the fully-qualified name of the class
     * @return the found class, or {@code null} if no class is found
     */
    public JavaClass getClass(String fqn) {
        if (fqn != null && fqn.length() != 0) {
            return classStore.get(fqn);
        }
        return null;
    }
    
    void addExternalClass(JavaClass jclass) {
        externalClasseStore.put(jclass.getQualifiedName().fqn(), jclass);
    }
    
    /**
     * Obtains a class outside this project, not having source code.
     * @param fqn the fully-qualified name of the class
     * @return the found class, or {@code null} if no class is found
     */
    public JavaClass getExternalClass(String fqn) {
        if (fqn != null && fqn.length() > 0) {
            return externalClasseStore.get(fqn);
        }
        return null;
    }
    
    /**
     * Obtains the classes depending a given class.
     * @param jclass the class of interest
     * @return the collection of the dependent classes
     */
    public Set<JavaClass> collectDependentClasses(JavaClass jclass) {
        Set<JavaClass> classes = new HashSet<>();
        collectDependentClasses(jclass, classes);
        return classes;
    }
    
    private void collectDependentClasses(JavaClass jclass, Set<JavaClass> classes) {
        if (jclass != null && getClass(jclass.getQualifiedName().fqn()) != null) {
            for (JavaClass jc : jclass.getAncestors()) {
                classes.add(jc);
                collectDependentClasses(jc, classes);
            }
            for (JavaClass jc : jclass.getDescendants()) {
                classes.add(jc);
                collectDependentClasses(jc, classes);
            }
            for (JavaClass jc: jclass.getAfferentClassesInProject()) {
                classes.add(jc);
                collectDependentClasses(jc, classes);
            }
        }
    }
    
    /**
     * Removes classes from this project.
     * This method is not intended to be invoked by clients.
     * @param classes the classes to be removed
     */
    public void removeClasses(List<JavaClass> classes) {
        for (JavaClass jclass : classes) {
            removeFile(jclass.getFile().getPath());
            removeClass(jclass);
        }
    }
    
    /**
     * Sets the class paths.
     * @param classPath the absolute paths that store class files
     */
    public void setClassPath(String[] classPath) {
        this.classPath = classPath;
    }
    
    /**
     * Sets a source path and a binary path.
     * @param sourcePath the absolute path that stores source files
     * @param binaryPath the absolute path that stores binary files
     */
    public void setSourceBinaryPaths(String sourcePath, String binaryPath) {
        String[] srcPath = new String[1];
        srcPath[0] = sourcePath;
        String[] binPath = new String[1];
        binPath[0] = binaryPath;
        setSourceBinaryPaths(srcPath, binPath);
    }
    
    /**
     * Sets source paths and binary paths.
     * @param sourcePath the absolute paths that store source files
     * @param binaryPath the absolute paths that store binary files
     */
    public void setSourceBinaryPaths(String[] sourcePath, String[] binaryPath) {
        this.sourcePath = sourcePath;
        this.binaryPath = binaryPath;
    }
    
    /**
     * Returns the absolute path where class files (jar or zip archives) are located in the project.
     * The class path will be searched when finding class files are loaded.
     * @return the absolute path of the class files
     */
    public String[] getClassPath() {
        return classPath;
    }
    
    /**
     * Returns the absolute path where source files are located in the project.
     * @return the absolute path of the source files
     */
    public String[] getSourcePath() {
        return sourcePath;
    }
    
    /**
     * Returns the absolute path where binary files are located in the project.
     * @return the absolute path of the binary files
     */
    public String[] getBinaryPath() {
        return binaryPath;
    }
    
    /**
     * Collects additional information on a given class.
     * This method is not intended to be invoked by clients, which will be automatically invoked as needed.
     * @param jclass the class of interest
     */
    public void collectInfo(JavaClass jclass) {
        jclass.collectInfo();
    }
    
    /**
     * Return a repository that stores CFGs for source files in this project.
     * @return the CFG repository
     */
    public CFGStore getCFGStore() {
        return cfgStore;
    }
    
    /**
     * Return a repository that stores PDGs for source files in this project.
     * @return the PDG repository
     */
    public PDGStore getPDGStore() {
        return pdgStore;
    }
    
    /**
     * Makes the directory.
     * @param name the directory name relative to the project path
     * @return {@code true} if the directory are newly made, otherwise {@code false}
     */
    public boolean makeDir(String name) {
        String dirname = path + File.separator + name;
        File dir = new File(dirname);
        if (!dir.isDirectory()) {
            dir.mkdirs();
            return true;
        }
        return false;
    }
    
    /**
     * Removes the directory and files stored in the directory.
     * @param name the directory name relative to the project path
     */
    public void removeDir(String name) {
        String dirname = path + File.separator + name;
        File dir = new File(dirname);
        if (dir.isDirectory()) {
            String[] names = dir.list();
            for (int i = 0; i < names.length; i++) {
                File f = new File(dir.getPath() + File.separator + names[i]);
                f.delete();
            }
        }
        dir.delete();
    }
    
    /**
     * Obtains information on this project.
     * @return the string representing the information
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("\n");
        buf.append("JPROJECT: ");
        buf.append(getName());
        buf.append(" [");
        buf.append(getPath());
        buf.append("]");
        return buf.toString();
    }
    
    /**
     * Sorts the list of source files.
     * @param list the file list
     * @return the sorted file list
     */
    protected static List<JavaFile> sortFiles(List<? extends JavaFile> co) {
        List<JavaFile> jfiles = new ArrayList<>(co);
        Collections.sort(jfiles, new Comparator<>() {
            public int compare(JavaFile jf1, JavaFile jf2) {
                return jf1.getPath().compareTo(jf2.getPath());
            }
        });
        return jfiles;
    }
    
    /**
     * Sorts the list of packages.
     * @param list the package list
     * @return the sorted package list
     */
    protected static List<JavaPackage> sortPackages(List<? extends JavaPackage> collection) {
        return collection
                .stream()
                .sorted((jp1, jp2) -> jp1.getName().compareTo(jp2.getName()))
                .collect(Collectors.toList());
    }
}
