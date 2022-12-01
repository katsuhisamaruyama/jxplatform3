/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.project;

import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaFile;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.internal.JavaASTVisitor;
import org.jtool.srcmodel.internal.ProjectStore;
import org.jtool.jxplatform.builder.ModelBuilder;
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
public class ModelBuilderImpl {
    
    protected ModelBuilder modelBuilder;
    
    protected boolean analyzeBytecode = true;
    protected boolean useCache = true;
    
    protected int sourcecodeAnalysisChain = 5;
    protected int bytecodeAnalysisChain = 2;
    
    protected Logger logger;
    protected boolean visible = true;
    protected boolean verbose = true;
    
    protected ConsoleProgressMonitor monitor;
    protected static final ConsoleProgressMonitor consoleProgressMonitor = new ConsoleProgressMonitor();
    protected static final ConsoleProgressMonitor nullConsoleProgressMonitor = new NullConsoleProgressMonitor();
    
    protected ModelBuilderImpl(ModelBuilder modelBuiler) {
        this.modelBuilder = modelBuiler;
        this.logger = new Logger();
        this.monitor = getConsoleProgressMonitor();
    }
    
    public Logger getLogger() {
        return logger;
    }
    
    public ConsoleProgressMonitor getConsoleProgressMonitor() {
        return visible ? consoleProgressMonitor : nullConsoleProgressMonitor;
    }
    
    public ModelBuilder getModelBuilder() {
        return modelBuilder;
    }
    
    public void update(JavaProject jproject) {
    }
    
    public void analyzeBytecode(boolean bool) {
        analyzeBytecode = bool;
    }
    
    public boolean analyzeBytecode() {
        return analyzeBytecode;
    }
    
    public void useCache(boolean bool) {
        useCache = bool;
    }
    
    public boolean useCache() {
        return useCache;
    }
    
    public void setSourcecodeAnalysisChain(int bytecodeAnalysisChain) {
        this.sourcecodeAnalysisChain = bytecodeAnalysisChain;
    }
    
    public int getSourcecodeAnalysisChain() {
        return sourcecodeAnalysisChain;
    }
    
    public void setBytecodeAnalysisChain(int bytecodeAnalysisChain) {
        this.bytecodeAnalysisChain = bytecodeAnalysisChain;
    }
    
    public int getBytecodeAnalysisChain() {
        return bytecodeAnalysisChain;
    }
    
    public void printMessage(String message) {
        getConsoleProgressMonitor().printMessage(message);
        logger.recordLog(message);
    }
    
    public void printError(String message) {
        getConsoleProgressMonitor().printError(message);
        logger.recordLog(message);
    }
    
    public void printUnresolvedError(String message) {
        getConsoleProgressMonitor().printError(message);
        logger.recordUnresolvedError(message);
    }
    
    public void printCreationError(String message) {
        getConsoleProgressMonitor().printError(message);
        logger.recordCreationError(message);
    }
    
    public void unbuild() {
        ProjectStore.getInstance().clear();
    }
    
    public void setConsoleVisible(boolean visible) {
        this.visible = visible;
    }
    
    public boolean isConsoleVisible() {
        return visible;
    }
    
    public JavaFile copyJavaFile(JavaFile jfile) {
        return getUnregisteredJavaFile(jfile.getPath(), jfile.getSource(), jfile.getJavaProject(), jfile.getCharset());
    }
    
    public JavaFile getUnregisteredJavaFile(String filepath, String code, JavaProject jproject) {
        return getUnregisteredJavaFile(filepath, code, jproject, JavaCore.getEncoding());
    }
    
    public JavaFile getUnregisteredJavaFile(String filepath, String code, JavaProject jproject, String charset) {
        ASTParser parser = getParser(jproject);
        
        String[] sourcepaths = jproject.getSourcePath();
        parser.setUnitName(filepath);
        parser.setEnvironment(jproject.getClassPath(), sourcepaths, null, true);
        parser.setSource(code.toCharArray());
        
        CompilationUnit cu = (CompilationUnit)parser.createAST(null);
        if (cu != null) {
            JavaFile jfile = new JavaFile(cu, filepath, code, charset, jproject);
            if (getParseErrors(cu).size() != 0) {
                String message = "Incomplete parse: " + filepath;
                monitor.printError(message);
                logger.recordLog(message);
            }
            
            JavaASTVisitor visitor = new JavaASTVisitor(jfile);
            cu.accept(visitor);
            visitor.terminate();
            return jfile;
        }
        return null;
    }
    
    protected ASTParser getParser(JavaProject jproject) {
        String sourceVersion = getCompilerVersion(jproject.getCompilerSourceVersion());
        String targetVersion = getCompilerVersion(jproject.getCompilerTargetVersion());
        
        ASTParser parser = ASTParser.newParser(AST.JLS18);
        Map<String, String> options = JavaCore.getOptions();
        options.put(JavaCore.COMPILER_SOURCE, sourceVersion);
        options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_8);
        options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, targetVersion);
        options.put(JavaCore.COMPILER_DOC_COMMENT_SUPPORT, JavaCore.ENABLED);
        options.put(JavaCore.COMPILER_PB_FORBIDDEN_REFERENCE, JavaCore.IGNORE);
        parser.setCompilerOptions(options);
        
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setResolveBindings(true);
        parser.setStatementsRecovery(true);
        parser.setBindingsRecovery(true);
        return parser;
    }
    
    private String getCompilerVersion(String version) {
        if (version == null) {
            return JavaCore.VERSION_11;
        }
        try {
            double value = Double.valueOf(version);
            if (value <= 1.6) {
                return JavaCore.VERSION_1_6;
            } else if (value <= 1.7) {
                return JavaCore.VERSION_1_7;
            } else if (value <= 1.8) {
                return JavaCore.VERSION_1_8;
            } else if (value <= 9) {
                return JavaCore.VERSION_9;
            } else if (value <= 10) {
                return JavaCore.VERSION_10;
            }
        } catch (NumberFormatException e) {
            /* empty */
        }
        return JavaCore.VERSION_11;
    }
    
   Set<IProblem> getParseErrors(CompilationUnit cu) {
        Set<IProblem> errors = new HashSet<>();
        IProblem[] problems = cu.getProblems();
        if (problems.length > 0) {
            for (IProblem problem : problems) {
                if (problem.isError()) {
                    String message = "Error: " + problem.getMessage();
                    monitor.printError(message);
                    logger.recordLog(message);
                    errors.add(problem);
                }
            }
        }
        return errors;
    }
    
    public Set<JavaClass> getAllClassesForward(JavaClass jclass) {
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
    
    public Set<JavaClass> getAllClassesBackward(JavaClass jclass) {
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
    
    public Set<JavaMethod> getAllMethodsForward(JavaMethod jmethod) {
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
    
    public Set<JavaMethod> getAllMethodsBackward(JavaMethod jmethod) {
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
