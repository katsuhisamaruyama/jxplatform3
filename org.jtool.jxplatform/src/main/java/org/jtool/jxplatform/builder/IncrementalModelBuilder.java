/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder;

import org.jtool.jxplatform.project.ModelBuilderImpl;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaFile;
import org.jtool.srcmodel.JavaPackage;
import org.jtool.srcmodel.JavaProject;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.time.ZonedDateTime;

/**
 * A builder that incrementally builds models from Java source code.
 * 
 * @author Katsuhisa Maruyama
 */
public class IncrementalModelBuilder extends ModelBuilder {
    
    /**
     * A project that this builder is applied to.
     */
    protected JavaProject jproject;
    
    /**
     * The collection of files whose contents are obsolete.
     */
    protected Set<JavaFile> obsoleteFiles = new HashSet<>();
    
    /**
     * The collection of files that are newly added.
     */
    protected Set<Path> newFiles = new HashSet<>();
    
    /**
     * Creates an incremental-mode model builder.
     * @param builderImpl the implementation module of this model builder
     */
    public IncrementalModelBuilder(ModelBuilderImpl builderImpl) {
        this.builderImpl = builderImpl;
    }
    
    /**
     * Builds a source code model for target projects.
     * @param name the name of the created model
     * @param target the directory storing the target projects
     * @return the collection of created project data
     */
    public JavaProject build(String name, String target) {
        obsoleteFiles.clear();
        newFiles.clear();
        
        jproject = super.build(name, target, target);
        jproject.setModelBuilder(this);
        return jproject;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public JavaProject build(String name, String target, String classpath) {
        jproject = super.build(name, target, classpath, (String)null, (String)null);
        return jproject;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public JavaProject build(String name, String target, String classpath, String srcpath, String binpath) {
        jproject = super.build(name, target, classpath, srcpath, binpath);
        return jproject;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public JavaProject build(String name, String target, String[] classpath, String[] srcpath, String[] binpath) {
        jproject = super.build(name, target, classpath, srcpath, binpath);
        return jproject;
    }
    
    /**
     * Returns the project that this builder is applied to.
     * @return project
     */
    public JavaProject getJavaProject() {
        return jproject;
    }
    
    /**
     * Sets the class paths.
     * @param classPath the absolute paths that store class files
     */
    public void setClassPath(String[] classPath) {
        String oldpath = Arrays.asList(classPath).stream().collect(Collectors.joining(";"));
        String newpath = Arrays.asList(jproject.getClassPath()).stream().collect(Collectors.joining(";"));
        if (!oldpath.equals(newpath)) {
            jproject.setClassPath(classPath);
            builderImpl.update(jproject);
        }
    }
    
    /**
     * Sets source paths and binary paths.
     * @param sourcePath the absolute paths that store source files
     * @param binaryPath the absolute paths that store binary files
     */
    public void setSourceBinaryPaths(String[] sourcePath, String[] binaryPath) {
        String oldsrcpath = Arrays.asList(sourcePath).stream().collect(Collectors.joining(";"));
        String newsrcpath = Arrays.asList(jproject.getSourcePath()).stream().collect(Collectors.joining(";"));
        String oldbinpath = Arrays.asList(binaryPath).stream().collect(Collectors.joining(";"));
        String newbinpath = Arrays.asList(jproject.getBinaryPath()).stream().collect(Collectors.joining(";"));
        if (!oldsrcpath.equals(newsrcpath) || !oldbinpath.equals(newbinpath)) {
            jproject.setSourceBinaryPaths(sourcePath, binaryPath);
            builderImpl.update(jproject);
        }
    }
    
    /**
     * Returns files whose contents are obsolete.
     * @return the collection of the obsolete files
     */
    public Set<JavaFile> getObsoleteFiles() {
        return obsoleteFiles;
    }
    
    /**
     * Returns files that are newly added.
     * @return the collection of the new files
     */
    public Set<Path> getNewFiles() {
        return newFiles;
    }
    
    /**
     * Registers a file that was added to the project.
     * @param pathname the path name of the added file
     */
    public void addFile(String pathname) {
        if (ModelBuilderImpl.isCompilableJavaFile(pathname)) {
            File file = new File(pathname);
            if (file.isFile()) {
                addFile(file.toPath());
            }
        }
    }
    
    /**
     * Registers a file that was added to the project.
     * @param path the path of the added file
     */
    void addFile(Path path) {
        String pathname = path.toAbsolutePath().toString();
        if (jproject.getFile(pathname) != null) {
            obsoleteFiles.addAll(collectDependentFiles(pathname));
        }
        newFiles.add(path);
    }
    
    /**
     * Registers files that were added to the project.
     * @param pathnames the collection of path names of the added files
     */
    public void addFiles(Set<String> pathnames) {
        pathnames.forEach(path -> addFile(path));
    }
    
    /**
     * Registers a file that was removed from the project.
     * @param pathname the path name of the removed file
     */
    public void removeFile(String pathname) {
        File file = new File(pathname);
        if (!file.exists() && jproject.getFile(pathname) != null) {
            obsoleteFiles.addAll(collectDependentFiles(pathname));
        }
    }
    
    /**
     * Registers files that were removed from the project.
     * @param pathnames the collection of path names of the removed files
     */
    public void removeFiles(Set<String> pathnames) {
        pathnames.forEach(path -> removeFile(path));
    }
    
    /**
     * Registers a file that was updated in the project.
     * @param pathname the path name of the updated file
     */
    public void updateFile(String pathname) {
        addFile(pathname);
        removeFile(pathname);
    }
    
    /**
     * Registers a file that was updated in the project.
     * @param path the path of the updated file
     */
    void updateFile(Path path) {
        addFile(path);
        removeFile(path.toAbsolutePath().toString());
    }
    
    /**
     * Registers files that were updated in the project.
     * @param pathnames the collection of path names of the updated files
     */
    public void updateFile(Set<String> pathnames) {
        addFiles(pathnames);
        removeFiles(pathnames);
    }
    
    /**
     * Collects files depending on a specific file, whose contents should be removed or updated
     * @param path a removed or updated file
     * @return the collection of the files
     */
    Set<JavaFile> collectDependentFiles(String path) {
        Set<JavaFile> files = new HashSet<>();
        JavaFile jfile = jproject.getFile(path);
        if (jfile == null) {
            return files;
        }
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
        newFiles.clear();
        jproject.getFiles().forEach(jfile -> removeJavaFile(jfile));
        
        List<File> sourceFiles = ModelBuilderImpl.collectAllJavaFiles(jproject.getSourcePath());
        builderImpl.parseFile(jproject, sourceFiles);
        builderImpl.collectInfo(jproject, jproject.getClasses());
    }
    
    /**
     * Builds a source code model based on added and removed files.
     */
    public void incrementalBuild() {
        obsoleteFiles.forEach(jfile -> removeJavaFile(jfile));
        
        List<File> sourceFiles = new ArrayList<>();
        sourceFiles.addAll(obsoleteFiles.stream().map(jfile -> new File(jfile.getPath())).collect(Collectors.toSet()));
        sourceFiles.addAll(newFiles.stream().map(path -> path.toFile()).collect(Collectors.toSet()));
        
        builderImpl.parseFile(jproject, sourceFiles);
        
        List<JavaClass> classes = obsoleteFiles.stream()
                .map(jfile -> jproject.getFile(jfile.getPath()))
                .flatMap(jfile -> jfile.getClasses().stream()).collect(Collectors.toList());
        classes.addAll(newFiles.stream()
                .map(path -> jproject.getFile(path.toAbsolutePath().toString()))
                .flatMap(jfile -> jfile.getClasses().stream()).collect(Collectors.toList()));
        
        builderImpl.collectInfo(jproject, classes);
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
    void collectFilesByFileTime() {
        Set<String> analyzedFiles = jproject.getFiles().stream().map(jfile -> jfile.getPath()).collect(Collectors.toSet());
        
        Set<File> sourceFiles = ModelBuilderImpl.collectAllJavaFileSet(jproject.getSourcePath());
        for (File file : sourceFiles) {
            Path path = file.toPath();
            analyzedFiles.remove(path.toAbsolutePath().toString());
            
            try {
                FileTime fileTimeOnFileSystem = Files.getLastModifiedTime(path);
                JavaFile jfile = jproject.getFile(file.getCanonicalPath());
                
                if (jfile == null) {
                    addFile(path);
                } else {
                    ZonedDateTime jfileTime = jfile.getCreatedTime();
                    ZonedDateTime fileTime = fileTimeOnFileSystem.toInstant().atZone(jfileTime.getZone());
                    if (jfileTime.toInstant().isBefore(fileTime.toInstant())) {
                        updateFile(path);
                    }
                }
            } catch (IOException e) { /* empty */ }
        }
        
        removeFiles(analyzedFiles);
    }
}
