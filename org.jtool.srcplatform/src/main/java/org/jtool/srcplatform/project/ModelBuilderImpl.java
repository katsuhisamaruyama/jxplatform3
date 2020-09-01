/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.project;

import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaFile;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.builder.JavaASTVisitor;
import org.jtool.srcmodel.builder.ProjectStore;
import org.jtool.srcplatform.modelbuilder.ModelBuilder;
import org.jtool.srcplatform.modelbuilder.ModelBuilder.BytecodeAnalysisLevel;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/**
 * A builder implementation that builds models related to Java source code.
 * 
 * @author Katsuhisa Maruyama
 */

public abstract class ModelBuilderImpl {
    
    protected ModelBuilder modelBuilder;
    
    protected boolean analyzeBytecode = false;
    protected boolean useProjectCache = false;
    protected BytecodeAnalysisLevel bytecodeAnalysisLevel = BytecodeAnalysisLevel.SHALLOW1;
    
    protected boolean verbose = true;
    
    protected ModelBuilderImpl(ModelBuilder modelBuiler) {
        this.modelBuilder = modelBuiler;
    }
    
    public ModelBuilder getModelBuilder() {
        return modelBuilder;
    }
    
    public abstract boolean isUnderPlugin();
    
    public abstract void update(JavaProject jproject);
    
    public abstract void loadBytecode(JavaProject jproject);
    
    public void analyzeBytecode(boolean bool) {
        analyzeBytecode = bool;
    }
    
    public boolean analyzeBytecode() {
        return analyzeBytecode;
    }
    
    public void useProjectCache(boolean bool) {
        useProjectCache = bool;
    }
    
    public boolean useProjectCache() {
        return useProjectCache;
    }
    
    public void setBytecodeAnalysisLevel(BytecodeAnalysisLevel level) {
        this.bytecodeAnalysisLevel = level;
    }
    
    public BytecodeAnalysisLevel getBytecodeAnalysisLevel() {
        return bytecodeAnalysisLevel;
    }
    
    public void unbuild() {
        ProjectStore.getInstance().clear();
    }
    
    public JavaFile copyJavaFile(JavaFile jfile) {
        return getUnregisteredJavaFile(jfile.getPath(), jfile.getCode(), jfile.getProject(), jfile.getCharset());
    }
    
    public JavaFile getUnregisteredJavaFile(String filepath, String code, JavaProject jproject) {
        return getUnregisteredJavaFile(filepath, code, jproject, JavaCore.getEncoding());
    }
    
    public JavaFile getUnregisteredJavaFile(String filepath, String code, JavaProject jproject, String charset) {
        ASTParser parser = getParser();
        
        String[] sourcepaths = jproject.getSourcePath();
        parser.setUnitName(filepath);
        parser.setEnvironment(jproject.getClassPath(), sourcepaths, null, true);
        parser.setSource(code.toCharArray());
        
        CompilationUnit cu = (CompilationUnit)parser.createAST(null);
        if (cu != null) {
            JavaFile jfile = new JavaFile(cu, filepath, code, charset, jproject);
            if (getParseErrors(cu).size() != 0) {
                System.err.println("Incomplete parse: " + filepath);
            }
            
            JavaASTVisitor visitor = new JavaASTVisitor(jfile);
            cu.accept(visitor);
            visitor.terminate();
            return jfile;
        }
        return null;
    }
    
    @SuppressWarnings("deprecation")
    protected ASTParser getParser() {
        ASTParser parser = ASTParser.newParser(AST.JLS11);
        Map<String, String> options = JavaCore.getOptions();
        options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_11);
        options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_11);
        options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_11);
        options.put(JavaCore.COMPILER_DOC_COMMENT_SUPPORT, JavaCore.ENABLED);
        parser.setCompilerOptions(options);
        
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setResolveBindings(true);
        parser.setStatementsRecovery(true);
        parser.setBindingsRecovery(true);
        return parser;
    }
    
    protected Set<IProblem> getParseErrors(CompilationUnit cu) {
        Set<IProblem> errors = new HashSet<>();
        IProblem[] problems = cu.getProblems();
        if (problems.length > 0) {
            for (IProblem problem : problems) {
                if (problem.isError()) {
                    System.err.println("Error: " + problem.getMessage());
                    errors.add(problem);
                }
            }
        }
        return errors;
    }
    
    protected Set<JavaClass> getAllClassesForward(JavaClass jclass) {
        Set<JavaClass> classes = new HashSet<>();
        collectAllClassesForward(jclass, classes);
        return classes;
    }
    
    private void collectAllClassesForward(JavaClass jclass, Set<JavaClass> classes) {
        if (classes.contains(jclass)) {
            return;
        }
        classes.add(jclass);
        
        for (JavaClass jc : jclass.getEfferentClassesInProject()) {
            collectAllClassesForward(jc, classes);
        }
    }
    
    protected Set<JavaClass> getAllClassesBackward(JavaClass jclass) {
        Set<JavaClass> classes = new HashSet<JavaClass>();
        collectAllClassesBackward(jclass, classes);
        return classes;
    }
    
    private void collectAllClassesBackward(JavaClass jclass, Set<JavaClass> classes) {
        if (classes.contains(jclass)) {
            return;
        }
        classes.add(jclass);
        
        for (JavaClass jc : jclass.getAfferentClassesInProject()) {
            collectAllClassesBackward(jc, classes);
        }
    }
    
    protected Set<JavaMethod> getAllMethodsForward(JavaMethod jmethod) {
        Set<JavaMethod> methods = new HashSet<>();
        collectAllMethodsForward(jmethod, methods);
        return methods;
    }
    
    private void collectAllMethodsForward(JavaMethod jmethod, Set<JavaMethod> methods) {
        if (methods.contains(jmethod)) {
            return;
        }
        methods.add(jmethod);
        
        for (JavaMethod jm : jmethod.getCalledMethods()) {
            collectAllMethodsForward(jm, methods);
        }
    }
    
    protected Set<JavaMethod> getAllMethodsBackward(JavaMethod jmethod) {
        Set<JavaMethod> methods = new HashSet<>();
        collectAllMethodsBackward(jmethod, methods);
        return methods;
    }
    
    private void collectAllMethodsBackward(JavaMethod jmethod, Set<JavaMethod> methods) {
        if (methods.contains(jmethod)) {
            return;
        }
        methods.add(jmethod);
        
        for (JavaMethod jm : jmethod.getCallingMethods()) {
            collectAllMethodsBackward(jm, methods);
        }
    }
}
