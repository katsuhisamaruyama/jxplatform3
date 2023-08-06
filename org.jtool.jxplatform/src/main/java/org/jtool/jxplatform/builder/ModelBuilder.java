/*
 *  Copyright 2022-2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder;

import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaFile;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaProject;
import org.jtool.cfg.CCFG;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CallGraph;
import org.jtool.cfg.internal.CallGraphBuilder;
import org.jtool.jxplatform.project.ModelBuilderImpl;
import org.jtool.pdg.ClDG;
import org.jtool.pdg.PDG;
import org.jtool.pdg.SDG;
import org.jtool.pdg.DependencyGraph;
import java.util.Set;

/**
 * A builder that builds models from Java source code.
 * 
 * @author Katsuhisa Maruyama
 */
public abstract class ModelBuilder {
    
    /**
     * A line break symbol, "\n" for macOS and Linux, "\r\n" for Windows
     */
    public static final String br = System.getProperty("line.separator");
    
    /**
     * The implementation module of this model builder.
     */
    protected ModelBuilderImpl builderImpl;
    
    /**
     * Obtains the implementation module of this model builder.
     * @return the implementation module.
     */
    public ModelBuilderImpl getModelBuilderImpl() {
        return builderImpl;
    }
    
    /**
     * Sets the maximum number of a chain when analyzing class defined in source-code.
     * @param analysisChain the maximum number of the chain
     */
    public void setSourcecodeAnalysisChain(int analysisChain) {
        builderImpl.setSourcecodeAnalysisChain(analysisChain);
    }
    
    /**
     * Sets the maximum number of a chain when analyzing byte-code classes.
     * @param analysisChain the maximum number of the chain
     */
    public void setBytecodeAnalysisLevel(int analysisChain) {
        builderImpl.setBytecodeAnalysisChain(analysisChain);
    }
    
    /**
     * Sets whether byte-code analysis is performed.
     * @param bool {@code true} if byte-code analysis is performed, otherwise {@code false}
     */
    public void analyzeBytecode(boolean bool) {
        builderImpl.analyzeBytecode(bool);
    }
    
    /**
     * Tests if byte-code analysis is performed.
     * @return {@code true} if byte-code analysis is performed, otherwise {@code false}
     */
    public boolean analyzeBytecode() {
        return builderImpl.analyzeBytecode();
    }
    
    /**
     * Sets whether byte-code analysis is performed.
     * @param bool {@code true} if byte-code analysis is performed, otherwise {@code false}
     */
    public void useCache(boolean bool) {
        builderImpl.useCache(bool);
    }
    
    /**
     * Tests if byte-code analysis is performed.
     * @return {@code true} if byte-code analysis is performed, otherwise {@code false}
     */
    public boolean useCache() {
        return builderImpl.useCache();
    }
    
    /**
     * Builds a source code model for a target project.
     * @param name the name of the created model
     * @param target the directory storing the target project
     * @param classpath the path where the needed class (or jar) files are located
     * @return the created project data
     */
    public JavaProject build(String name, String target, String classpath) {
        return build(name, target, classpath, (String)null, (String)null);
    }
    
    /**
     * Builds a source code model for a target project.
     * @param name the name of the created model
     * @param target the directory storing the target project
     * @param classpath the path where the needed class (or jar) files are located
     * @param srcpath the path where the source files are located
     * @param binpath the path where the binary files are located
     * @return the created project data
     */
    public JavaProject build(String name, String target, String classpath, String srcpath, String binpath) {
        JavaProject jproject = builderImpl.build(this, name, target, classpath, srcpath, binpath);
        return jproject;
    }
    
    /**
     * Builds a source code model for a target project.
     * @param name the name of the created model
     * @param target the directory storing the target project
     * @param classpath the collection of the paths where the needed class (or jar) files are located
     * @param srcpath the collection of the paths where the source files are located
     * @param binpath the collection of the paths where the binary files are located
     * @return the created project data
     */
    public JavaProject build(String name, String target, String[] classpath, String[] srcpath, String[] binpath) {
        JavaProject jproject = builderImpl.build(this, name, target, classpath, srcpath, binpath);
        return jproject;
    }
    
    /**
     * Disposes the created models.
     */
    public void unbuild() {
        builderImpl.unbuild();
    }
    
    /**
     * Creates a copy of the file
     * @param jfile the file to be copied
     * @return the copy
     */
    public JavaFile copyJavaFile(JavaFile jfile) {
        assert jfile != null;
        
        return builderImpl.copyJavaFile(jfile);
    }
    
    /**
     * Create a temporary object corresponding to a file without registering project repository.
     * @param filepath the path of the file
     * @param code the contents of the file
     * @param jproject the project that should contain the file
     * @return the created file
     */
    public JavaFile getUnregisteredJavaFile(String filepath, String code, JavaProject jproject) {
        assert jproject != null;
        
        return builderImpl.getUnregisteredJavaFile(filepath, code, jproject);
    }
    
    /**
     * Create a temporary object corresponding to a file without registering project repository.
     * @param filepath the path of the file
     * @param code the contents of the file
     * @param jproject the project that should contain the file
     * @param charset the character set of the file
     * @return the created file
     */
    public JavaFile getUnregisteredJavaFile(String filepath, String code, JavaProject jproject, String charset) {
        assert jproject != null;
        
        return builderImpl.getUnregisteredJavaFile(filepath, code, jproject, charset);
    }
    
    /**
     * Finds a CFG for a class element having a given fully-qualified name.
     * @param jproject the project containing the class element
     * @param fqn the fully-qualified name of the class member ({@code className#memeberName})
     * @return the found CFG, or {@code null} if no CFG is found
     */
    public CFG findCFG(JavaProject jproject, String fqn) {
        assert jproject != null;
        
        return jproject.getCFGStore().findCFG(fqn);
    }
    
    /**
     * Finds a CCFG for a class having a given fully-qualified name.
     * @param jproject the project containing the class
     * @param fqn the fully-qualified name for the class ({@code className})
     * @return the found CFG, or {@code null} if no CFG is found
     */
    public CCFG findCCFG(JavaProject jproject, String fqn) {
        assert jproject != null;
        
        return jproject.getCFGStore().findCCFG(fqn);
    }
    
    /**
     * Obtains a CFG for a given method.
     * @param jmethod the method of interest
     * @param force {@code true} if the CFG will be forcibly recreated,
     *        or {@code false} if a CFG stored in the repository will be reused
     * @return the created or found CFG, or {@code null} if no CFG is created or found
     */
    public CFG getCFG(JavaMethod jmethod, boolean force) {
        assert jmethod != null;
        
        return jmethod.getJavaProject().getCFGStore().getCFG(jmethod, force);
    }
    
    /**
     * Finds a CFG for a given method.
     * @param jmethod the method of interest
     * @return the created or found CFG, or {@code null} if no CFG is created or found
     */
    public CFG getCFG(JavaMethod jmethod) {
        assert jmethod != null;
        
        return jmethod.getJavaProject().getCFGStore().getCFG(jmethod, false);
    }
    
    /**
     * Obtains a CFG for a given method.
     * @param jfield the field of interest
     * @param force {@code true} if the CFG will be forcibly recreated,
     *        or {@code false} if a CFG stored in the repository will be reused
     * @return the created or found CFG, or {@code null} if no CFG is created or found
     */
    public CFG getCFG(JavaField jfield, boolean force) {
        assert jfield != null;
        
        return jfield.getJavaProject().getCFGStore().getCFG(jfield, force);
    }
    
    /**
     * Obtains a CFG for a given field.
     * @param jfield the field of interest
     * @return the created or found CFG, or {@code null} if no CFG is created or found
     */
    public CFG getCFG(JavaField jfield) {
        assert jfield != null;
        
        return jfield.getJavaProject().getCFGStore().getCFG(jfield, false);
    }
    
    /**
     * Obtains a CCFG for a given class.
     * @param jclass the class of interest
     * @param force {@code true} if the CCFG will be forcibly recreated,
     *        or {@code false} if a CCFG stored in the repository will be reused
     * @return the created or found CCFG
     */
    public CCFG getCCFG(JavaClass jclass, boolean force) {
        assert jclass != null;
        
        return jclass.getJavaProject().getCFGStore().getCCFG(jclass, force);
    }
    
    /**
     * Obtains a CCFG for a given class.
     * @param jclass the class of interest
     * @return the created or found CCFG
     */
    public CCFG getCCFG(JavaClass jclass) {
        assert jclass != null;
        
        return jclass.getJavaProject().getCFGStore().getCCFG(jclass, false);
    }
    
    /**
     * Obtains a call graph for methods within a project.
     * @param jproject the target project
     * @return the created call graph
     */
    public CallGraph getCallGraph(JavaProject jproject) {
        assert jproject != null;
        
        return CallGraphBuilder.getCallGraph(jproject);
    }
    
    /**
     * Finds a PDG for a class member having a given fully-qualified name.
     * @param jproject the project containing the class element
     * @param fqn the fully-qualified name of the class member ({@code className#memeberName})
     * @return the found PDG, or {@code null} if no PDG is found
     */
    public PDG findPDG(JavaProject jproject, String fqn) {
        assert jproject != null;
        
        return jproject.getPDGStore().findPDG(fqn);
    }
    
    /**
     * Finds a ClDG for a class having a given fully-qualified name.
     * @param jproject the project containing the class
     * @param fqn the fully-qualified name for the class ({@code className})
     * @return the found ClDG, or {@code null} if no ClDG is found
     */
    public ClDG findClDG(JavaProject jproject, String fqn) {
        assert jproject != null;
        
        return jproject.getPDGStore().findClDG(fqn);
    }
    
    /**
     * Obtains a PDG for a given CFG for a method or a field.
     * @param jproject the project containing the target CFG
     * @param cfg the target CFG
     * @param force {@code true} if the PDG will be forcibly re-created,
     *        or {@code false} if a PDG stored in the repository will be reused
     * @param whole {@code true} if the PDG will be created with the whole information related to
     *        calls to methods and accesses to fields of outside classes
     * @return the created or found PDG, or {@code null} if no PDG is created or found
     */
    public PDG getPDG(JavaProject jproject, CFG cfg, boolean force, boolean whole) {
        assert jproject != null;
        assert cfg != null;
        
        return jproject.getPDGStore().getPDG(cfg, force, whole);
    }
    
    /**
     * Obtains a PDG for a given CFG for a method or a field from the cache,
     * using the whole information related to calls to methods and accesses to fields of outside classes.
     * @param jproject the project containing the target CFG
     * @param cfg the target CFG
     * @return the created or found PDG, or {@code null} if no PDG is created or found
     */
    public PDG getPDG(JavaProject jproject, CFG cfg) {
        return getPDG(jproject, cfg, false, true);
    }
    
    /**
     * Obtains a PDG for a given method.
     * @param jmethod the method of interest
     * @param force {@code true} if the PDG will be forcibly re-created,
     *        or {@code false} if a PDG stored in the repository will be reused
     * @param whole {@code true} if the PDG will be created with the whole information related to
     *        calls to methods and accesses to fields of outside classes
     * @return the created or found PDG, or {@code null} if no PDG is created or found
     */
    public PDG getPDG(JavaMethod jmethod, boolean force, boolean whole) {
        assert jmethod != null;
        
        return jmethod.getJavaProject().getPDGStore().getPDG(jmethod, force, whole);
    }
    
    /**
     * Obtains a PDG for a given method from the cache,
     * using the whole information related to calls to methods and accesses to fields of outside classes.
     * @param jmethod the method of interest
     * @return the created or found PDG, or {@code null} if no PDG is created or found
     */
    public PDG getPDG(JavaMethod jmethod) {
        return getPDG(jmethod, false, true);
    }
    
    /**
     * Obtains a PDG for a given field.
     * @param jfield the field of interest
     * @param force {@code true} if the PDG will be forcibly re-created,
     *        or {@code false} if a PDG stored in the repository will be reused
     * @param whole {@code true} if the PDG will be created with the whole information related to
     *        calls to methods and accesses to fields of outside classes
     * @return the created or found PDG, or {@code null} if no PDG is created or found
     */
    public PDG getPDG(JavaField jfield, boolean force, boolean whole) {
        assert jfield != null;
        
        return jfield.getJavaProject().getPDGStore().getPDG(jfield, force, whole);
    }
    
    /**
     * Obtains a PDG for a given field from the cache,
     * using the whole information related to calls to methods and accesses to fields of outside classes.
     * @param jfield the field of interest
     * @return the created or found PDG, or {@code null} if no PDG is created or found
     */
    public PDG getPDG(JavaField jfield) {
        return getPDG(jfield, false, true);
    }
    
    /**
     * Obtains a ClDG for a given CCFG for a class.
     * @param jproject the project containing the target CCFG
     * @param ccfg the target CCFG
     * @param force {@code true} if the ClDG will be forcibly recreated,
     *        or {@code false} if a ClDG stored in the repository will be reused
     * @param whole {@code true} if a ClDG will be created with the whole information related to
     *        calls to methods and accesses to fields of outside classes
     * @return the created or found ClDG, or {@code null} if no ClDG is created or found
     */
    public ClDG getClDG(JavaProject jproject, CCFG ccfg, boolean force, boolean whole) {
        assert jproject != null;
        assert ccfg != null;
        
        return jproject.getPDGStore().getClDG(ccfg, force, whole);
    }
    
    /**
     * Obtains a ClDG for a given CCFG for a class from the cache,
     * using the whole information related to calls to methods and accesses to fields of outside classes.
     * @param jproject the project containing the target CCFG
     * @param ccfg the target CCFG
     * @return the created or found ClDG, or {@code null} if no ClDG is created or found
     */
    public ClDG getClDG(JavaProject jproject, CCFG ccfg) {
        return getClDG(jproject, ccfg, false, true);
    }
    
    /**
     * Obtains a ClDG for a given class.
     * @param jclass the class of interest
     * @param force {@code true} if the PDG will be forcibly recreated,
     *        or {@code false} if a ClDG stored in the repository will be reused
     * @param whole {@code true} if a ClDG will be created with the whole information related to
     *        calls to methods and accesses to fields of outside classes
     * @return the created or found ClDG, or {@code null} if no ClDG is created or found
     */
    public ClDG getClDG(JavaClass jclass, boolean force, boolean whole) {
        assert jclass != null;
        
        return jclass.getJavaProject().getPDGStore().getClDG(jclass, force, whole);
    }
    
    /**
     * Obtains a ClDG for a given class from the cache,
     * using the whole information related to calls to methods and accesses to fields of outside classes.
     * @param jclass the class of interest
     * @return the created or found ClDG, or {@code null} if no ClDG is created or found
     */
    public ClDG getClDG(JavaClass jclass) {
        return getClDG(jclass, false, true);
    }
    
    /**
     * Obtains an SDG for all classes in a project,
     * using the whole information related to calls to methods and accesses to fields of outside classes.
     * @param jproject the target project
     * @param force {@code true} if an SDG will be forcibly recreated,
     *        or {@code false} if an SDG stored in the repository will be reused
     * @return the created or found SDG
     */
    public SDG getSDG(JavaProject jproject, boolean force) {
        assert jproject != null;
        
        return jproject.getPDGStore().getSDG(force);
    }
    
    /**
     * Obtains an SDG for all classes in a project from the cache,
     * using the whole information related to calls to methods and accesses to fields of outside classes.
     * @param jproject the target project
     * @return the created or found SDG
     */
    public SDG getSDG(JavaProject jproject) {
        return getSDG(jproject, false);
    }
    
    /**
     * Obtains a dependency graph for classes related to a given class.
     * @param jclass the class of interest
     * @param force {@code true} if the dependency graph will be forcibly recreated,
     *        or {@code false} if a dependency graph stored in the repository will be reused
     * @param whole {@code true} if a dependency graph will be created with the whole information related to
     *        calls to methods and accesses to fields of outside classes
     * @return the created or found dependency graph
     */
    public DependencyGraph getDependencyGraph(JavaClass jclass, boolean force, boolean whole) {
        assert jclass != null;
        
        return jclass.getJavaProject().getPDGStore().getDependencyGraph(jclass, force, whole);
    }
    
    /**
     * Obtains a dependency graph for classes related to a given class from the cache,
     * using the whole information related to calls to methods and accesses to fields of outside classes.
     * @param jclass the class of interest
     * @return the created or found dependency graph
     */
    public DependencyGraph getDependencyGraph(JavaClass jclass) {
        return getDependencyGraph(jclass, false, true);
    }
    
    /**
     * Obtains a dependency graph for classes related to given classes.
     * @param classes the collection of the classes of interest
     * @param force {@code true} if a dependency graph will be forcibly recreated,
     *        or {@code false} if a dependency graph stored in the repository will be reused
     * @param whole {@code true} if a dependency graph will be created with the whole information related to
     *        calls to methods and accesses to fields of outside classes
     * @return the created or found dependency graph
     */
    public DependencyGraph getDependencyGraph(Set<JavaClass> classes, boolean force, boolean whole) {
        if (classes.size() > 0) {
            JavaClass jclass = classes.iterator().next();
            return jclass.getJavaProject().getPDGStore().getDependencyGraph(classes, force, whole);
        }
        return new DependencyGraph("NoClass");
    }
    
    /**
     * Obtains a dependency graph for classes related to given classes from the cache.
     * @param classes the collection of the classes of interest
     * @return the created or found dependency graph
     */
    public DependencyGraph getgetDependencyGraph(Set<JavaClass> classes) {
        return getDependencyGraph(classes, false, true);
    }
    
    /**
     * Sets whether information is displayed on console.
     * @param visible {@code true} if the information is displayed, otherwise {@code false}
     */
    public void setConsoleVisible(boolean visible) {
        builderImpl.setConsoleVisible(visible);
    }
    
    /**
     * Obtains the classes forward related to a given class.
     * @param jclass the class of interest
     * @return the collection of classes used by the class
     */
    public Set<JavaClass> getAllClassesForward(JavaClass jclass) {
        assert jclass != null;
        
        return builderImpl.getAllClassesForward(jclass);
    }
    
    /**
     * Obtains the classes backward related to a given class.
     * @param jclass the class of interest
     * @return the collection of classes using the class
     */
    public Set<JavaClass> getAllClassesBackward(JavaClass jclass) {
        assert jclass != null;
        
        return builderImpl.getAllClassesBackward(jclass);
    }
    
    /**
     * Obtains the methods forward related to a given class.
     * @param jmethod the method of interest
     * @return the collection of methods called by the method
     */
    public Set<JavaMethod> getAllMethodsForward(JavaMethod jmethod) {
        assert jmethod != null;
        
        return builderImpl.getAllMethodsForward(jmethod);
    }
    
    /**
     * Obtains the methods backward related to a given class.
     * @param jmethod the method of interest
     * @return the collection of methods calling the method
     */
    public Set<JavaMethod> getAllMethodsBackward(JavaMethod jmethod) {
        assert jmethod != null;
        
        return builderImpl.getAllMethodsBackward(jmethod);
    }
}
