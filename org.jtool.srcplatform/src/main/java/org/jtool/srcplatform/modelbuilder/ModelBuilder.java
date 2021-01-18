/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.modelbuilder;

import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaFile;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaProject;
import org.jtool.cfg.CCFG;
import org.jtool.cfg.CFG;
import org.jtool.cfg.CallGraph;
import org.jtool.cfg.builder.CallGraphBuilder;
import org.jtool.pdg.ClDG;
import org.jtool.pdg.PDG;
import org.jtool.pdg.SDG;
import org.jtool.srcplatform.project.ModelBuilderImpl;
import org.jtool.srcplatform.util.Logger;
import java.util.Set;

/**
 * A builder that builds models related to Java source code.
 * 
 * @author Katsuhisa Maruyama
 */

public abstract class ModelBuilder {
    
    /**
     * The implementation module of this model builder.
     */
    protected ModelBuilderImpl impl;
    
    /**
     * Sets the maximum number of a chain when analyzing byte-code classes.
     * @param bytecodeAnalysisChain the maximum number of the chain
     */
    public void setBytecodeAnalysisLevel(int bytecodeAnalysisChain) {
        impl.setBytecodeAnalysisChain(bytecodeAnalysisChain);
    }
    
    /**
     * Sets whether byte-code analysis is performed.
     * @param bool {@code true} if byte-code analysis is performed, otherwise {@code false}
     */
    public void analyzeBytecode(boolean bool) {
        impl.analyzeBytecode(bool);
    }
    
    /**
     * Tests if byte-code analysis is performed.
     * @return {@code true} if byte-code analysis is performed, otherwise {@code false}
     */
    public boolean analyzeBytecode() {
        return impl.analyzeBytecode();
    }
    
    /**
     * Sets whether the project cache is used.
     * 
     * The use of a project cache can skip analysis of source code and byte-code within the project,
     * and thus largely reduce the analysis time.
     * 
     * Note that a project cache is never automatically updated although the source code and byte-code
     * has been changed since the cache was created. If the update is needed, you should manually delete
     * the cache file {@code #project.complete.cache} in the directory {@code .srcplatform}.
     * 
     * @param bool {@code true} if the project cache is preferentially used, otherwise {@code false}
     */
    public void useProjectCache(boolean bool) {
        impl.useProjectCache(bool);
    }
    
    /**
     * Tests if the project cache is used.
     * @return {@code true} if the project cache is used, otherwise {@code false}
     */
    public boolean useProjectCache() {
        return impl.useProjectCache();
    }
    
    /**
     * Disposes the created models.
     */
    public void unbuild() {
        impl.unbuild();
    }
    
    /**
     * Creates a copy of the file
     * @param jfile the file to be copied
     * @return the copy
     */
    public JavaFile copyJavaFile(JavaFile jfile) {
        return impl.copyJavaFile(jfile);
    }
    
    /**
     * Create a temporary object corresponding to a file without registering project repository.
     * @param filepath the path of the file
     * @param code the contents of the file
     * @param jproject the project that should contain the file
     * @return the created file
     */
    public JavaFile getUnregisteredJavaFile(String filepath, String code, JavaProject jproject) {
        return impl.getUnregisteredJavaFile(filepath, code, jproject);
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
        return impl.getUnregisteredJavaFile(filepath, code, jproject, charset);
    }
    
    /**
     * Finds the CFG for a class element having a given fully-qualified name.
     * @param jproject the project containing the class element
     * @param fqn the fully-qualified name of the class member ({@code className#memeberName})
     * @return the found CFG, or {@code null} if no CFG is found
     */
    public CFG findCFG(JavaProject jproject, String fqn) {
        return jproject.getCFGStore().findCFG(fqn);
    }
    
    /**
     * Finds the CCFG for a class having a given fully-qualified name.
     * @param jproject the project containing the class
     * @param fqn the fully-qualified name for the class ({@code className})
     * @return the found CFG, or {@code null} if no CFG is found
     */
    public CCFG findCCFG(JavaProject jproject, String fqn) {
        return jproject.getCFGStore().findCCFG(fqn);
    }
    
    /**
     * Obtains the CFG for a given method.
     * @param jmethod the method of interest
     * @param force {@code true} if the CFG will be forcibly recreated,
     *        or {@code false} if a CFG stored in the repository will be reused
     * @return the created or found CFG, or {@code null} if no CFG is created or found
     */
    public CFG getCFG(JavaMethod jmethod, boolean force) {
        return jmethod.getJavaProject().getCFGStore().getCFG(jmethod, force);
    }
    
    /**
     * Finds the CFG for a given method.
     * @param jmethod the method of interest
     * @return the created or found CFG, or {@code null} if no CFG is created or found
     */
    public CFG getCFG(JavaMethod jmethod) {
        return jmethod.getJavaProject().getCFGStore().getCFG(jmethod, false);
    }
    
    /**
     * Obtains the CFG for a given method.
     * @param jfield the field of interest
     * @param force {@code true} if the CFG will be forcibly recreated,
     *        or {@code false} if a CFG stored in the repository will be reused
     * @return the created or found CFG, or {@code null} if no CFG is created or found
     */
    public CFG getCFG(JavaField jfield, boolean force) {
        return jfield.getJavaProject().getCFGStore().getCFG(jfield, force);
    }
    
    /**
     * Obtains the CFG for a given field.
     * @param jfield the field of interest
     * @return the created or found CFG, or {@code null} if no CFG is created or found
     */
    public CFG getCFG(JavaField jfield) {
        return jfield.getJavaProject().getCFGStore().getCFG(jfield, false);
    }
    
    /**
     * Obtains the CCFG for a given class.
     * @param jclass the class of interest
     * @param force {@code true} if the CCFG will be forcibly recreated,
     *        or {@code false} if a CCFG stored in the repository will be reused
     * @return the created or found CCFG
     */
    public CCFG getCCFG(JavaClass jclass, boolean force) {
        return jclass.getJavaProject().getCFGStore().getCCFG(jclass, force);
    }
    
    /**
     * Obtains the CCFG for a given class.
     * @param jclass the class of interest
     * @return the created or found CCFG
     */
    public CCFG getCCFG(JavaClass jclass) {
        return jclass.getJavaProject().getCFGStore().getCCFG(jclass, false);
    }
    
    /**
     * Obtains the call graph for methods within a project.
     * @param jproject the target project
     * @return the created call graph
     */
    public CallGraph getCallGraph(JavaProject jproject) {
        return CallGraphBuilder.getCallGraph(jproject);
    }
    
    /**
     * Obtains the call graph for methods within a class.
     * @param jclass the class of interest
     * @return the created call graph
     */
    public CallGraph getCallGraph(JavaClass jclass) {
        return CallGraphBuilder.getCallGraph(jclass);
    }
    
    /**
     * Obtains the call graph for method and fields related to a method.
     * @param jmethod the method of interest
     * @return the created call graph
     */
    public CallGraph getCallGraph(JavaMethod jmethod) {
        return CallGraphBuilder.getCallGraph(jmethod);
    }
    
    /**
     * Finds a PDG for a class member having a given fully-qualified name.
     * @param jproject the project containing the class element
     * @param fqn the fully-qualified name of the class member ({@code className#memeberName})
     * @return the found PDG, or {@code null} if no PDG is found
     */
    public PDG findPDG(JavaProject jproject, String fqn) {
        return jproject.getPDGStore().findPDG(fqn);
    }
    
    /**
     * Finds the ClDG for a class having a given fully-qualified name.
     * @param jproject the project containing the class
     * @param fqn the fully-qualified name for the class ({@code className})
     * @return the found ClDG, or {@code null} if no ClDG is found
     */
    public ClDG findClDG(JavaProject jproject, String fqn) {
        return jproject.getPDGStore().findClDG(fqn);
    }
    
    /**
     * Finds the SDG for a project.
     * @param jproject the target project
     * @return the most recently created SDG, or {@code null} if a SDG has not been created yet
     */
    public SDG findSDG(JavaProject jproject) {
        return jproject.getPDGStore().findSDG();
    }
    
    /**
     * Obtains the PDG for a given CFG.
     * @param jproject the project containing the target CFG
     * @param cfg the target CFG
     * @param force {@code true} if the PDG will be forcibly recreated,
     *        or {@code false} if a PDG stored in the repository will be reused
     * @return the created or found PDG, or {@code null} if no PDG is created or found
     */
    public PDG getPDG(JavaProject jproject, CFG cfg, boolean force) {
        return jproject.getPDGStore().getPDG(cfg, force);
    }
    
    /**
     * Obtains the PDG for a given CFG.
     * @param jproject the project containing the target CFG
     * @param cfg the target CFG
     * @return the created or found PDG, or {@code null} if no PDG is created or found
     */
    public PDG getPDG(JavaProject jproject, CFG cfg) {
        return jproject.getPDGStore().getPDG(cfg, false);
    }
    
    /**
     * Obtains the PDG for a given method.
     * @param jmethod the method of interest
     * @param force {@code true} if the PDG will be forcibly recreated,
     *        or {@code false} if a PDG stored in the repository will be reused
     * @return the created or found PDG, or {@code null} if no PDG is created or found
     */
    public PDG getPDG(JavaMethod jmethod, boolean force) {
        return jmethod.getJavaProject().getPDGStore().getPDG(jmethod, force);
    }
    
    /**
     * Obtains the PDG for a given method.
     * @param jmethod the method of interest
     * @return the created or found PDG, or {@code null} if no PDG is created or found
     */
    public PDG getPDG(JavaMethod jmethod) {
        return jmethod.getJavaProject().getPDGStore().getPDG(jmethod, false);
    }
    
    /**
     * Obtains the PDG for a given field.
     * @param jfield the field of interest
     * @param force {@code true} if the PDG will be forcibly recreated,
     *        or {@code false} if a PDG stored in the repository will be reused
     * @return the created or found PDG, or {@code null} if no PDG is created or found
     */
    public PDG getPDG(JavaField jfield, boolean force) {
        return jfield.getJavaProject().getPDGStore().getPDG(jfield, force);
    }
    
    /**
     * Obtains the PDG for a given field.
     * @param jfield the field of interest
     * @return the created or found PDG, or {@code null} if no PDG is created or found
     */
    public PDG getPDG(JavaField jfield) {
        return jfield.getJavaProject().getPDGStore().getPDG(jfield, false);
    }
    
    /**
     * Obtains the PDG for a given method.
     * @param jmethod the method of interest
     * @param force {@code true} if the PDG will be forcibly recreated,
     *        or {@code false} if a PDG stored in the repository will be reused
     * @return the created or found PDG within its SDG, or {@code null} if no PDG is created or found
     */
    public PDG getPDGWithinSDG(JavaMethod jmethod, boolean force) {
        return jmethod.getJavaProject().getPDGStore().getPDGWithinSDG(jmethod, force);
    }
    
    /**
     * Obtains the PDG for a given method.
     * @param jmethod the method of interest
     * @return the created or found PDG within its SDG, or {@code null} if no PDG is created or found
     */
    public PDG getPDGWithinSDG(JavaMethod jmethod) {
        return jmethod.getJavaProject().getPDGStore().getPDGWithinSDG(jmethod, false);
    }
    
    /**
     * Obtains the PDG for a given field.
     * @param jfield the field of interest
     * @param force {@code true} if the PDG will be forcibly recreated,
     *        or {@code false} if a PDG stored in the repository will be reused
     * @return the created or found PDG within its SDG, or {@code null} if no PDG is created or found
     */
    public PDG getPDGWithinSDG(JavaField jfield, boolean force) {
        return jfield.getJavaProject().getPDGStore().getPDGWithinSDG(jfield, force);
    }
    
    /**
     * Obtains the PDG for a given field.
     * @param jfield the field of interest
     * @return the created or found PDG within its SDG, or {@code null} if no PDG is created or found
     */
    public PDG getPDGWithinSDG(JavaField jfield) {
        return jfield.getJavaProject().getPDGStore().getPDGWithinSDG(jfield, false);
    }
    
    /**
     * Obtains the ClDG for a given CCFG.
     * @param jproject the project containing the target CCFG
     * @param ccfg the target CCFG
     * @param force {@code true} if the ClDG will be forcibly recreated,
     *        or {@code false} if a ClDG stored in the repository will be reused
     * @return the created or found ClDG, or {@code null} if no ClDG is created or found
     */
    public ClDG getClDG(JavaProject jproject, CCFG ccfg, boolean force) {
        return jproject.getPDGStore().getClDG(ccfg, force);
    }
    
    /**
     * Obtains the ClDG for a given CCFG.
     * @param jproject the project containing the target CCFG
     * @param ccfg the target CCFG
     * @return the created or found ClDG, or {@code null} if no ClDG is created or found
     */
    public ClDG getClDG(JavaProject jproject, CCFG ccfg) {
        return jproject.getPDGStore().getClDG(ccfg, false);
    }
    
    /**
     * Obtains the ClDG for a given class.
     * @param jclass the class of interest
     * @param force {@code true} if the PDG will be forcibly recreated,
     *        or {@code false} if a ClDG stored in the repository will be reused
     * @return the created or found ClDG, or {@code null} if no ClDG is created or found
     */
    public ClDG getClDG(JavaClass jclass, boolean force) {
        return jclass.getJavaProject().getPDGStore().getClDG(jclass, force);
    }
    
    /**
     * Obtains the ClDG for a given class.
     * @param jclass the class of interest
     * @return the created or found ClDG, or {@code null} if no ClDG is created or found
     */
    public ClDG getClDG(JavaClass jclass) {
        return jclass.getJavaProject().getPDGStore().getClDG(jclass, false);
    }
    
    /**
     * Obtains the ClDG for a given class.
     * @param jclass the class of interest
     * @param force {@code true} if the ClDG will be forcibly recreated,
     *        or {@code false} if a ClDG stored in the repository will be reused
     * @return the created or found ClDG within its SDG, or {@code null} if no ClDG is created or found
     */
    public ClDG getClDGWithinSDG(JavaClass jclass, boolean force) {
        return jclass.getJavaProject().getPDGStore().getClDGWithinSDG(jclass, force);
    }
    
    /**
     * Obtains the ClDG for a given class.
     * @param jclass the class of interest
     * @return the created or found ClDG within its SDG, or {@code null} if no ClDG is created or found
     */
    public ClDG getClDGWithinSDG(JavaClass jclass) {
        return jclass.getJavaProject().getPDGStore().getClDGWithinSDG(jclass, false);
    }
    
    /**
     * Obtains the SDG for classes related to a given class.
     * @param jclass the class of interest
     * @param force {@code true} if the SDG will be forcibly recreated,
     *        or {@code false} if a SDG stored in the repository will be reused
     * @return the created or found SDG
     */
    public SDG getSDG(JavaClass jclass, boolean force) {
        return jclass.getJavaProject().getPDGStore().getSDG(jclass, force);
    }
    
    /**
     * Obtains the SDG for classes related to a given class.
     * @param jclass the class of interest
     * @return the created or found SDG
     */
    public SDG getSDG(JavaClass jclass) {
        return jclass.getJavaProject().getPDGStore().getSDG(jclass, false);
    }
    
    /**
     * Obtains the SDG for classes related to given classes.
     * @param classes the collection of the classes of interest
     * @param force {@code true} if the SDG will be forcibly recreated,
     *        or {@code false} if a SDG stored in the repository will be reused
     * @return the created or found SDG
     */
    public SDG getSDG(Set<JavaClass> classes, boolean force) {
        if (classes.size() > 0) {
            JavaClass jclass = classes.iterator().next();
            return jclass.getJavaProject().getPDGStore().getSDG(classes, force);
        }
        return new SDG();
    }
    
    /**
     * Obtains the SDG for classes related to given classes.
     * @param classes the collection of the classes of interest
     * @return the created or found SDG
     */
    public SDG getSDG(Set<JavaClass> classes) {
        if (classes.size() > 0) {
            JavaClass jclass = classes.iterator().next();
            return jclass.getJavaProject().getPDGStore().getSDG(classes, false);
        }
        return new SDG();
    }
    
    /**
     * Obtains the SDG for classes within a project.
     * @param jproject the target project
     * @param force {@code true} if the SDG will be forcibly recreated,
     *        or {@code false} if a SDG stored in the repository will be reused
     * @return the created or found SDG
     */
    public SDG getSDG(JavaProject jproject, boolean force) {
        return jproject.getPDGStore().getSDG(force);
    }
    
    /**
     * Obtains the SDG for classes within a project.
     * @param jproject the target project
     * @return the created or found SDG
     */
    public SDG getSDG(JavaProject jproject) {
        return jproject.getPDGStore().getSDG(false);
    }
    
    /**
     * Obtains the SDG for a given classes.
     * @param classes the collection of the classes of interest
     * @param force {@code true} if the SDG will be forcibly recreated,
     *        or {@code false} if a SDG stored in the repository will be reused
     * @return the created or found SDG
     */
    public SDG getSDGForClasses(Set<JavaClass> classes, boolean force) {
        if (classes.size() > 0) {
            JavaClass jclass = classes.iterator().next();
            return jclass.getJavaProject().getPDGStore().getSDGForClasses(classes, force);
        }
        return new SDG();
    }
    
    /**
     * Obtains the SDG for a given classes.
     * @param classes the collection of the classes of interest
     * @return the created or found SDG
     */
    public SDG getSDGForClasses(Set<JavaClass> classes) {
        if (classes.size() > 0) {
            JavaClass jclass = classes.iterator().next();
            return jclass.getJavaProject().getPDGStore().getSDGForClasses(classes, false);
        }
        return new SDG();
    }
    
    /**
     * Sets whether the log information is displayed.
     * @param visible {@code true} if the log information is displayed, otherwise {@code false}
     */
    public void setLogVisible(boolean visible) {
        Logger.getInstance().setVisible(visible);
    }
    
    /**
     * Test if the log information is displayed.
     * @return {@code true} if the log information is displayed, otherwise {@code false}
     */
    public boolean isLogVisible() {
        return Logger.getInstance().isVisible();
    }
    
    /**
     * Obtains the classes forward related to a given class.
     * @param jclass the class of interest
     * @return the collection of classes used by the class
     */
    public Set<JavaClass> getAllClassesForward(JavaClass jclass) {
        return impl.getAllClassesForward(jclass);
    }
    
    /**
     * Obtains the classes backward related to a given class.
     * @param jclass the class of interest
     * @return the collection of classes using the class
     */
    public Set<JavaClass> getAllClassesBackward(JavaClass jclass) {
        return impl.getAllClassesBackward(jclass);
    }
    
    /**
     * Obtains the methods forward related to a given class.
     * @param jmethod the method of interest
     * @return the collection of methods called by the method
     */
    public Set<JavaMethod> getAllMethodsForward(JavaMethod jmethod) {
        return impl.getAllMethodsForward(jmethod);
    }
    
    /**
     * Obtains the methods backward related to a given class.
     * @param jmethod the method of interest
     * @return the collection of methods calling the method
     */
    public Set<JavaMethod> getAllMethodsBackward(JavaMethod jmethod) {
        return impl.getAllMethodsBackward(jmethod);
    }
}
