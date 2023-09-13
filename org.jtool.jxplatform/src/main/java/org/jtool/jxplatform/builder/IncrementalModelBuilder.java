/*
 *  Copyright 2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder;

import org.jtool.jxplatform.project.ModelBuilderImpl;
import org.jtool.jxplatform.project.ModelBuilderBatchImpl;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaFile;
import org.jtool.srcmodel.JavaPackage;
import org.jtool.srcmodel.JavaProject;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.time.ZonedDateTime;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * A builder that incrementally builds models from Java source code.
 * 
 * @author Katsuhisa Maruyama
 */
public class IncrementalModelBuilder extends ModelBuilder {
    
    /**
     * A project that this builder is applied to.
     */
    protected List<JavaProject> jprojects;
    
    /**
     * The collection of files whose contents are obsolete.
     */
    protected Set<JavaFile> obsoleteFiles = new HashSet<>();
    
    /**
     * The collection of files that are added.
     */
    protected Set<FilePath> addedFiles = new HashSet<>();
    
    /**
     * The collection of files that are removed.
     */
    protected Set<JavaFile> removedFiles = new HashSet<>();
    
    /**
     * Creates an incremental-mode model builder.
     */
    public IncrementalModelBuilder() {
        this(new ModelBuilderBatchImpl());
    }
    
    /**
     * Creates an incremental-mode model builder.
     * @param builderImpl the implementation module of this model builder
     */
    public IncrementalModelBuilder(ModelBuilderImpl builderImpl) {
        this.builderImpl = builderImpl;
    }
    
    /**
     * Creates an incremental-mode model builder.
     * @param builder a model builder
     * @param jproject a project data
     */
    public IncrementalModelBuilder(ModelBuilderImpl builderImpl, List<JavaProject> jprojects) {
        this.builderImpl = builderImpl;
        this.jprojects = jprojects;
        jprojects.forEach(project -> project.setModelBuilder(this));
    }
    
    /**
     * Builds a source code model for target projects.
     * @param name the name of the created model
     * @param target the directory storing the target project
     * @return the created project data
     */
    public JavaProject build(String name, String target) {
        obsoleteFiles.clear();
        addedFiles.clear();
        removedFiles.clear();
        
        JavaProject jproject = super.build(name, target, target);
        jproject.setModelBuilder(this);
        jprojects.add(jproject);
        return jproject;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public JavaProject build(String name, String target, String classpath) {
        build(name, target, classpath, (String)null, (String)null);
        obsoleteFiles.clear();
        addedFiles.clear();
        removedFiles.clear();
        
        JavaProject jproject = super.build(name, target, classpath, (String)null, (String)null);
        jproject.setModelBuilder(this);
        
        jprojects = new ArrayList<>();
        jprojects.add(jproject);
        return jproject;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public JavaProject build(String name, String target, String classpath, String srcpath, String binpath) {
        obsoleteFiles.clear();
        addedFiles.clear();
        removedFiles.clear();
        
        JavaProject jproject = super.build(name, target, classpath, srcpath, binpath);
        jproject.setModelBuilder(this);
        
        jprojects = new ArrayList<>();
        jprojects.add(jproject);
        return jproject;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public JavaProject build(String name, String target, String[] classpath, String[] srcpath, String[] binpath) {
        obsoleteFiles.clear();
        addedFiles.clear();
        removedFiles.clear();
        
        JavaProject jproject = super.build(name, target, classpath, srcpath, binpath);
        jproject.setModelBuilder(this);
        
        jprojects = new ArrayList<>();
        jprojects.add(jproject);
        return jproject;
    }
    
    /**
     * Returns the project that this builder is applied to.
     * @return the collection of projects
     */
    public List<JavaProject> getJavaProjects() {
        return jprojects;
    }
    
    /**
     * Resets the class paths.
     * @param jproject a project whose class paths will be changed
     * @param classPath the absolute paths that store class files
     */
    public void resetClassPath(JavaProject jproject, String[] classPath) {
        String oldpath = Arrays.asList(jproject.getClassPath()).stream().collect(Collectors.joining(";"));
        String newpath = Arrays.asList(classPath).stream().collect(Collectors.joining(";"));
        if (!oldpath.equals(newpath)) {
            jproject.setClassPath(classPath);
            builderImpl.update(jproject);
        }
    }
    
    /**
     * Resets source paths and binary paths.
     * @param jproject a project whose source and binary paths will be changed
     * @param sourcePath the absolute paths that store source files
     * @param binaryPath the absolute paths that store binary files
     */
    public void resetSourceBinaryPaths(JavaProject jproject, String[] sourcePath, String[] binaryPath) {
        String oldsrcpath = Arrays.asList(jproject.getSourcePath()).stream().collect(Collectors.joining(File.pathSeparator));
        String newsrcpath = Arrays.asList(sourcePath).stream().collect(Collectors.joining(File.pathSeparator));
        String oldbinpath = Arrays.asList(jproject.getBinaryPath()).stream().collect(Collectors.joining(File.pathSeparator));
        String newbinpath = Arrays.asList(binaryPath).stream().collect(Collectors.joining(File.pathSeparator));
        if (!oldsrcpath.equals(newsrcpath) || !oldbinpath.equals(newbinpath)) {
            jproject.setSourceBinaryPaths(sourcePath, binaryPath);
            builderImpl.update(jproject);
        }
    }
    
    /**
     * Returns the names of files whose contents are obsolete.
     * @return the collection of the names of the obsolete files
     */
    public Set<String> getObsoleteFileNames() {
        return obsoleteFiles.stream().map(jf -> jf.getPath()).collect(Collectors.toSet());
    }
    
    /**
     * Returns the names of files that were added.
     * @return the collection of the names of the added files
     */
    public Set<String> getAddedFileNames() {
        return addedFiles.stream().map(fp -> fp.path.toString()).collect(Collectors.toSet());
    }
    
    /**
     * Returns the names of files that were removed.
     * @return the collection of the names of the removed files
     */
    public Set<String> getRemovedFileNames() {
        return removedFiles.stream().map(jf -> jf.getPath()).collect(Collectors.toSet());
    }
    
    /**
     * Registers a file that was added to the project.
     * @param jproject a project that contains the added file
     * @param pathname the path name of the added file
     */
    public void addFile(JavaProject jproject, String pathname) {
        if (ModelBuilderImpl.isCompilableJavaFile(pathname)) {
            pathname = getCanonicalPathName(jproject, pathname);
            if (pathname == null) {
                return;
            }
            
            File file = new File(pathname);
            System.out.println("FILENAME = " + file.isFile());
            if (file.isFile()) {
                JavaFile jfile = jproject.getFile(pathname);
                if (jfile == null) {
                    System.out.println("FILENAME = " + pathname);
                    
                    addedFiles.add(new FilePath(jproject, file.toPath()));
                } else {
                    obsoleteFiles.addAll(collectDependentFiles(jfile));
                }
            }
        }
    }
    
    /**
     * Registers files that were added to the project.
     * @param jproject a project that contains the added files
     * @param pathnames the collection of path names of the added files
     */
    public void addFiles(JavaProject jproject, Set<String> pathnames) {
        pathnames.forEach(pathname -> addFile(jproject, pathname));
    }
    
    /**
     * Registers a file that was removed from the project.
     * @param jproject a project that contains the removed file
     * @param pathname the path name of the removed file
     */
    public void removeFile(JavaProject jproject, String pathname) {
        pathname = getCanonicalPathName(jproject, pathname);
        if (pathname == null) {
            return;
        }
        
        File file = new File(pathname);
        if (!file.exists()) {
            JavaFile jfile = jproject.getFile(pathname);
            if (jfile != null) {
                obsoleteFiles.addAll(collectDependentFiles(jfile));
                obsoleteFiles.remove(jfile);
                removedFiles.add(jfile);
            }
        }
    }
    
    /**
     * Registers files that were removed from the project.
     * @param jproject a project that contains the removed files
     * @param pathnames the collection of path names of the removed files
     */
    public void removeFiles(JavaProject jproject, Set<String> pathnames) {
        pathnames.forEach(pathname -> removeFile(jproject, pathname));
    }
    
    /**
     * Registers a file that was updated in the project.
     * @param jproject a project that contains the updated file
     * @param pathname the path name of the updated file
     */
    public void updateFile(JavaProject jproject, String pathname) {
        pathname = getCanonicalPathName(jproject, pathname);
        if (pathname == null) {
            return;
        }
        
        JavaFile jfile = jproject.getFile(pathname);
        if (jfile != null) {
            obsoleteFiles.addAll(collectDependentFiles(jfile));
        }
    }
    
    /**
     * Registers files that were updated in the project.
     * @param jproject a project that contains the updated files
     * @param pathnames the collection of path names of the updated files
     */
    public void updateFiles(JavaProject jproject, Set<String> pathnames) {
        pathnames.forEach(pathname -> updateFile(jproject, pathname));
    }
    
    /**
     * Obtains the path name of a file in the canonical form.
     * @param jproject a project that contains the file
     * @param pathname the path name of the file
     * @return the canonical formed path name, <code>null</code> if the invalid path.
     */
    protected String getCanonicalPathName(JavaProject jproject, String pathname) {
        if (!pathname.startsWith(File.separator)) {
            pathname = jproject.getPath() + File.separator + pathname;
        }
        File file = new File(pathname);
        try {
            return file.getCanonicalPath();
        } catch (IOException e) {
            return null;
        }
    }
    
    /**
     * Collects files depending on a file that was added, removed or updated
     * @param jfile the added, removed or updated file
     * @return the collection of the depending files
     */
    protected Set<JavaFile> collectDependentFiles(JavaFile jfile) {
        JavaProject jproject = jfile.getJavaProject();
        Set<JavaFile> files = new HashSet<>();
        files.add(jfile);
        
        Set<JavaClass> classes = new HashSet<>();
        for (JavaClass jc : jfile.getClasses()) {
            classes.addAll(jproject.collectClassesDependingOn(jc));
        }
        for (JavaClass jc : classes) {
            files.add(jc.getFile());
        }
        return files;
    }
    
    /**
     * Re-builds a source code model from all files.
     */
    public void rebuild() {
        obsoleteFiles.clear();
        addedFiles.clear();
        removedFiles.clear();
        
        for (JavaProject jproject : jprojects) {
            jproject.getFiles().forEach(jfile -> removeJavaFile(jfile));
            
            List<File> sourceFiles = ModelBuilderImpl.collectAllJavaFiles(jproject.getSourcePath());
            builderImpl.parseFile(jproject, sourceFiles);
            builderImpl.collectInfo(jproject, jproject.getClasses());
        }
    }
    
    /**
     * Builds a source code model based on added and removed files.
     */
    public void incrementalBuild() {
        Multimap<JavaProject, File> map = HashMultimap.create();
        obsoleteFiles.forEach(jfile -> map.put(jfile.getJavaProject(), new File(jfile.getPath())));
        addedFiles.forEach(filepath-> map.put(filepath.jproject, filepath.path.toFile()));
        Map<JavaProject, Collection<File>> fmap = map.asMap();
        
        obsoleteFiles.forEach(jfile -> removeJavaFile(jfile));
        removedFiles.forEach(jfile -> removeJavaFile(jfile));
        
        for (JavaProject jproject : fmap.keySet()) {
            List<File> sourceFiles = new ArrayList<File>(new HashSet<>(fmap.get(jproject)));
            builderImpl.parseFile(jproject, sourceFiles);
        }
        
        for (JavaProject jproject : fmap.keySet()) {
            List<File> sourceFiles = new ArrayList<File>(new HashSet<>(fmap.get(jproject)));
            List<JavaClass> classes = sourceFiles.stream()
                    .map(file -> jproject.getFile(file.getPath()))
                    .filter(jfile -> jfile != null)
                    .flatMap(jfile -> jfile.getClasses().stream()).collect(Collectors.toList());
            builderImpl.collectInfo(jproject, classes);
        }
    }
    
    /**
     * Disposes the created models.
     */
    public void unbuild() {
        builderImpl.unbuild();
    }
    
    /**
     * Removes a file and its contents.
     * @param jfile the file to be removed
     */
    protected void removeJavaFile(JavaFile jfile) {
        JavaProject jproject = jfile.getJavaProject();
        for (JavaClass jc : jfile.getClasses()) {
            jproject.removeClass(jc);
        }
        jproject.removeFile(jfile.getPath());
        
        for (JavaPackage jpackage : jproject.getPackages()) {
            if (jpackage.getClasses().size() == 0) {
                jproject.removePackage(jpackage);
            }
        }
    }
    
    /**
     * Builds a source code model based on the last modified times of files in the project.
     */
    public void buildByFileTime() {
        collectFilesByFileTime();
        incrementalBuild();
    }
    
    /**
     * Collects added and removed files based on the last modified times of files in the project.
     */
    protected void collectFilesByFileTime() {
        for (JavaProject jproject : jprojects) {
            Set<String> addedFiles = new HashSet<>();
            Set<String> removedFiles = jproject.getFiles().stream()
                    .map(jfile -> jfile.getPath()).collect(Collectors.toSet());
            Set<String> updatedFiles = new HashSet<>();
            
            Set<File> sourceFiles = ModelBuilderImpl.collectAllJavaFileSet(jproject.getSourcePath());
            for (File file : sourceFiles) {
                try {
                    String pathname = file.getCanonicalPath();
                    removedFiles.remove(pathname);
                    JavaFile jfile = jproject.getFile(pathname);
                    
                    if (jfile == null) {
                        addedFiles.add(pathname);
                    } else {
                        ZonedDateTime jfileTime = jfile.getCreatedTime();
                        FileTime fileTimeOnFileSystem = Files.getLastModifiedTime(file.toPath());
                        ZonedDateTime fileTime = fileTimeOnFileSystem.toInstant().atZone(jfileTime.getZone());
                        if (jfileTime.toInstant().isBefore(fileTime.toInstant())) {
                            updatedFiles.add(pathname);
                        }
                    }
                } catch (IOException e) { /* empty */ }
            }
            
            removeFiles(jproject, removedFiles);
            addFiles(jproject, addedFiles);
            updateFiles(jproject, updatedFiles);
        }
    }
    
    /**
     * Stores information on a file.
     * 
     * @author Katsuhisa Maruyama
     */
    private class FilePath {
        
        /**
         * The project name for a file.
         */
        JavaProject jproject;
        
        /**
         * The path for a file.
         */
        Path path;
        
        /**
         * Creates an object that stores information on a file.
         * @param jproject the project name for the file
         * @param path the path for the file
         */
        FilePath(JavaProject jproject, Path path) {
            this.jproject = jproject;
            this.path = path;
        }
    }
}
