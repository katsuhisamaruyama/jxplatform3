/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.project.CommandLineOptions;
import org.jtool.jxplatform.project.ModelBuilderBatchImpl;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

/**
 * Builds a Java Model for stand-alone use.
 * 
 * @author Katsuhisa Maruyama
 */
public class JavaModelBuilder {
    
    /**
     * The name of a target project.
     */
    private String projectName;
    
    /**
     * The path of a target project.
     */
    private String projectPath;
    
    /**
     * The file path of a directory that stores source files.
     */
    private String srcpath;
    
    /**
     * The file path of a directory that stores binary files.
     */
    private String binpath;
    
    /**
     * The sequence of file paths and/or jar files of a directory that stores class files.
     */
    private String classpath;
    
    /**
     * The name of a file recording logs.
     */
    private String logfile = "";
    
    /**
     * A flag that indicates whether the project environment is automatically checked.
     */
    private String autoCheckEnv;
    
    /**
     * A builder that builds the source code model.
     */
    private ModelBuilderBatch modelBuilder;
    
    /**
     * Creates a command-line mode model builder.
     * @param args the command-line options
     */
    public JavaModelBuilder(String[] args) {
        try {
            String cdir = new File(".").getAbsoluteFile().getParent();
            CommandLineOptions options = new CommandLineOptions(args);
            String target = removeLastFileSeparator(options.get("-target", "."));
            
            projectName = options.get("-name", getProjectName(target, cdir));
            File dir = new File(ModelBuilderBatchImpl.getFullPath(target, cdir));
            projectPath = dir.getCanonicalPath();
            classpath = getPath(target, options.get("-classpath", target));
            autoCheckEnv = options.get("auto-check-env", "no");
            if (autoCheckEnv.equals("yes")) {
                srcpath = target;
                binpath = target;
            } else {
                srcpath = getPath(target, options.get("-srcpath", target));
                binpath = getPath(target, options.get("-binpath", target));
            }
            
            logfile = options.get("-logfile", "");
        } catch (IOException e) {
            System.err.println("Cannot build a Java model due to the invalid options/settings.");
        }
    }
    
    /**
     * Builds a source code model for target projects.
     * @param name the name of the created model
     * @param target the directory storing the target projects
     */
    public JavaModelBuilder(String name, String target) {
        this(name, target, target, target, target);
    }
    
    /**
     * Builds a source code model for target projects.
     * @param name the name of the created model
     * @param target the directory storing the target projects
     * @param classpath the path where the needed class (or jar) files are located
     */
    public JavaModelBuilder(String name, String target, String classpath) {
        this(name, target, classpath, target, target);
    }
    
    /**
     * Obtains the file path from a specified option.
     * @param target the top directory storing the target project
     * @param option the specified option
     * @return the obtained file path
     */
    private String getPath(String target, String option) {
        if (option == null) {
            return target;
        }
        
        String path[] = option.split(File.pathSeparator);
        for (int i = 0; i < path.length; i++) {
            if (!path[i].startsWith(File.separator)) {
                path[i] = target + File.separator + path[i];
            }
        }
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < path.length; i++) {
            buf.append(File.pathSeparator);
            buf.append(path[i]);
        }
        return buf.substring(1);
    }
    
    /**
     * Creates a command-line mode model builder.
     * @param name the name of the created model
     * @param target the directory storing the target project
     * @param classpath the file path where the needed class (or jar) files are located
     * @param srcpath the file path where the source files are located
     * @param binpath the file path where the binary files are located
     */
    public JavaModelBuilder(String name, String target, String classpath, String srcpath, String binpath) {
        try {
            projectName = replaceFileSeparator(name);
            File dir = new File(target);
            projectPath = dir.getCanonicalPath();
            this.srcpath = srcpath;
            this.binpath = binpath;
            this.classpath = classpath;
            autoCheckEnv = "no";
        } catch (IOException e) {
            System.err.println("Cannot build a Java model due to the invalid options/settings.");
        }
    }
    
    /**
     * Obtains the project name based on the current directory where this is activated.
     * @param target the top directory storing the target project
     * @param cdir the current directory
     * @return the obtained project name
     */
    private String getProjectName(String target, String cdir) {
        String name = removeLastFileSeparator(target);
        if (name.startsWith(cdir)) {
            name = name.substring(cdir.length() + 1);
        }
        int index = name.lastIndexOf(File.separatorChar + "src");
        if (index > 0) {
            name = name.substring(0, index);
        }
        return replaceFileSeparator(name);
    }
    
    /**
     * Returns the file path by removing a directory or file name located at the last position.
     * @param path the original file path
     * @return the resulting file path
     */
    private String removeLastFileSeparator(String path) {
        if (path.charAt(path.length() - 1) == File.separatorChar) {
            return path.substring(0, path.length() - 1);
        }
        return path;
    }
    
    /**
     * Replaces file separator symbol different between OSs with the common symbol ".".
     * @param path the original file path
     * @return the resulting file path
     */
    private String replaceFileSeparator(String path) {
        return path.replace(File.separatorChar, '.');
    }
    
    /**
     * Builds a source code model for the project specified by the command options.
     * @return the collection of created project data
     */
    public List<JavaProject> build() {
        if (!autoCheckEnv.equals("yes")) {
            modelBuilder = new ModelBuilderBatch(true, true);
            if (logfile.length() > 0) {
                modelBuilder.getModelBuilderImpl().getLogger().setLogFile(projectPath + File.separator + logfile);
            }
            
            List<JavaProject> projects = new ArrayList<>();
            projects.add(modelBuilder.build(projectName, projectPath, classpath, srcpath, binpath));
            return projects;
        } else {
            modelBuilder = new ModelBuilderBatch(true, true);
            if (logfile.length() > 0) {
                modelBuilder.getModelBuilderImpl().getLogger().setLogFile(projectPath + File.separator + logfile);
            }
            
            return modelBuilder.build(projectName, projectPath);
        }
    }
    
    /**
     * Disposes the created models.
     */
    public void unbuild() {
        if (modelBuilder != null) {
            modelBuilder.unbuild();
        }
    }
    
    /**
     * Launches this application with options
     * @param args the specified options
     */
    public static void main(String[] args) {
        JavaModelBuilder builder = new JavaModelBuilder(args);
        builder.build();
    }
}
