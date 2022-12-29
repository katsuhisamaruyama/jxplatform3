/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.project;

import org.jtool.jxplatform.builder.ModelBuilder;
import org.jtool.jxplatform.builder.ConsoleProgressMonitor;
import org.jtool.jxplatform.builder.NullConsoleProgressMonitor;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaFile;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.internal.JavaASTVisitor;
import org.jtool.srcmodel.internal.ProjectStore;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A builder implementation that builds models related to Java source code.
 * 
 * @author Katsuhisa Maruyama
 */
public class ModelBuilderImpl {
    
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
    
    protected ModelBuilderImpl() {
        this.logger = new Logger();
        this.monitor = getConsoleProgressMonitor();
    }
    
    public Logger getLogger() {
        return logger;
    }
    
    public ConsoleProgressMonitor getConsoleProgressMonitor() {
        return visible ? consoleProgressMonitor : nullConsoleProgressMonitor;
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
    
    public List<JavaProject> build(ModelBuilder modelBuilder,
            String name, String target) {
        return new ArrayList<>();
    }
    
    public JavaProject build(ModelBuilder modelBuilder,
            String name, String target, String classpath, String srcpath, String binpath) {
        String cdir = new File(".").getAbsoluteFile().getParent();
        Path basePath = Paths.get(getFullPath(target, cdir));
        return build(modelBuilder, name, basePath, classpath, srcpath, binpath);
    }
    
    public JavaProject build(ModelBuilder modelBuilder,
            String name, Path basePath, String classpath, String srcpath, String binpath) {
        String[] classpaths = getClassPath(classpath);
        String[] srcpaths = getPath(srcpath, basePath, "src");
        String[] binpaths = getPath(srcpath, basePath, "bin");
        return build(modelBuilder, name, basePath, basePath, classpaths, srcpaths, binpaths);
    }
    
    public JavaProject build(ModelBuilder modelBuilder,
            String name, String target, String[] classpath, String[] srcpath, String[] binpath) {
        String cdir = new File(".").getAbsoluteFile().getParent();
        Path basePath = Paths.get(getFullPath(target, cdir));
        return build(modelBuilder, name, basePath, basePath, classpath, srcpath, binpath);
    }
    
    public JavaProject build(ModelBuilder modelBuilder,
            String name, Path basePath, Path topPath, String[] classpath, String[] srcpath, String[] binpath) {
        JavaProject jproject = createProject(modelBuilder, name, basePath, topPath, classpath, srcpath, binpath);
        build(jproject);
        logger.writeLog();
        return jproject;
    }
    
    protected JavaProject createProject(ModelBuilder modelBuilder,
            String name, Path basePath, Path topPath,
            String[] classpath, String[] srcpath, String[] binpath) {
        JavaProject jproject = new JavaProject(name, basePath.toString(), topPath.toString());
        jproject.setModelBuilder(modelBuilder);
        jproject.setClassPath(getClassPath(classpath));
        jproject.setSourceBinaryPaths(srcpath, binpath);
        jproject.getCFGStore().create(jproject);
        
        ProjectStore.getInstance().addProject(jproject);
        return jproject;
    }
    
    protected String[] getPath(Set<String> pathSet) {
        return (String[])pathSet.toArray(new String[pathSet.size()]);
    }
    
    protected String[] getClassPath(Set<String> pathSet) {
        String classPathStr;
        Set<String> libPath = pathSet.stream()
                .map(path -> getLibraryPath(path)).collect(Collectors.toSet());
        classPathStr = String.join(File.pathSeparator, libPath);
        return getClassPath(classPathStr);
    }
    
    protected String getLibraryPath(String path) {
        return path.endsWith(".jar") ? path : path + File.separator + "*";
    }
    
    protected String[] getPath(String path, Path basePath, String dirname) {
        if (path == null) {
            Path src = basePath.resolve(dirname);
            String[] paths = new String[1];
            paths[0] = src.toFile().exists() ? src.toString() : basePath.toString();
            return paths;
        }
        return path.split(File.pathSeparator);
    }
    
    protected String[] getClassPath(String[] classpath) {
        List<String> classpaths = new ArrayList<>();
        for (int i = 0 ; i < classpath.length; i++) {
            classpaths.addAll(Arrays.asList(getClassPath(classpath[i])));
        }
        return classpaths.toArray(new String[classpaths.size()]);
    }
    
    protected String[] getClassPath(String classpath) {
        List<String> classpaths = new ArrayList<>();
        try {
            String cdir = new File(".").getAbsoluteFile().getParent();
            if (classpath != null && classpath.length() != 0) {
                String[] paths = classpath.split(File.pathSeparator);
                if (paths != null) {
                    for (int i = 0; i < paths.length; i++) {
                        String path = getFullPath(paths[i], cdir); 
                        if (path.endsWith(File.separator + "*")) {
                            path = path.substring(0, path.length() - 1);
                            File dir = new File(path);
                            if (dir != null && dir.exists()) {
                                for (File file : dir.listFiles()) {
                                    if (file.getName().endsWith(".jar")) {
                                        classpaths.add(file.getCanonicalPath());
                                    }
                                }
                            }
                        } else {
                            File file = new File(path);
                            if (file != null && file.exists()) {
                                classpaths.add(path);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            return new String[0];
        }
        return classpaths.toArray(new String[classpaths.size()]);
    }
    
    public static String getFullPath(String path, String cdir) {
        if (path == null) {
            return cdir;
        }
        if (path.charAt(0) == File.separatorChar) {
            return path;
        } else {
            return cdir + File.separatorChar + path;
        }
    }
    
    public void build(JavaProject jproject) {
        List<File> sourceFiles = collectAllJavaFiles(jproject.getSourcePath());
        parseFile(jproject, sourceFiles);
        collectInfo(jproject, jproject.getClasses());
    }
    
    public void build(JavaProject jproject, Set<String> excludedSourceFiles) {
        List<File> allFiles = collectAllJavaFiles(jproject.getSourcePath());
        List<File> sourceFiles = allFiles.stream()
                .filter(f -> !excludedSourceFiles.contains(f.getAbsolutePath())).collect(Collectors.toList());
        parseFile(jproject, sourceFiles);
        collectInfo(jproject, jproject.getClasses());
    }
    
    public void parseFile(JavaProject jproject, List<File> sourceFiles) {
        List<FileContent> fileContents = new ArrayList<>();
        for (File file : sourceFiles) {
            try {
                String path = file.getCanonicalPath();
                String source = read(file);
                FileContent fileContent = new FileContent(path, source);
                fileContents.add(fileContent);
            } catch (IOException e) {
                printError("Cannot read file " + file.getPath());
            }
        }
        parse(jproject, fileContents);
    }
    
    private void parse(JavaProject jproject, List<FileContent> fileContents) {
        if (fileContents.size() > 0) {
            String[] paths = new String[fileContents.size()];
            String[] encodings = new String[fileContents.size()];
            Map<String, String> sources = new HashMap<>();
            Map<String, String> charsets = new HashMap<>();
            
            int count = 0;
            for (FileContent fileContent : fileContents) {
                String path = fileContent.getPath();
                String source = fileContent.getSource();
                String charset = StandardCharsets.UTF_8.name();
                paths[count] = path;
                encodings[count] = charset;
                sources.put(path, source);
                charsets.put(path, charset);
                count++;
            }
            
            parse(jproject, paths, encodings, sources, charsets);
        } else {
            printError("Found no Java source files in " + jproject.getPath());
        }
    }
    
    private void parse(JavaProject jproject, String[] paths, String[] encodings,
            Map<String, String> sources, Map<String, String> charsets) {
        final int size = paths.length;
        
        monitor.begin(size);
        FileASTRequestor requestor = new FileASTRequestor() {
            private int count = 0;
            
            @Override
            public void acceptAST(String filepath, CompilationUnit cu) {
                if (getParseErrors(cu).size() == 0) {
                    JavaFile jfile = new JavaFile(cu, filepath, sources.get(filepath), charsets.get(filepath), jproject);
                    JavaASTVisitor visitor = new JavaASTVisitor(jfile);
                    cu.accept(visitor);
                    visitor.terminate();
                } else {
                    printError("Incomplete parse: " + filepath);
                }
                
                monitor.work(1);
                count++;
                logger.recordLog("-Parsed " +
                        filepath.substring(jproject.getPath().length() + 1) + " (" + count + "/" + size + ")");
            }
        };
        
        printMessage("Target = " + jproject.getPath() + " (" + jproject.getName() + ")");
        printMessage("** Ready to parse " + size + " files");
        
        ASTParser parser = getParser(jproject);
        parser.setEnvironment(jproject.getClassPath(), null, null, true);
        parser.createASTs(paths, encodings, new String[]{ }, requestor, null);
        monitor.done();
    }
    
    public void collectInfo(JavaProject jproject, List<JavaClass> classes) {
        printMessage("** Ready to build java models of " + classes.size() + " classes");
        
        monitor.begin(classes.size());
        int count = 0;
        for (JavaClass jclass : classes) {
            jproject.collectInfo(jclass);
            
            monitor.work(1);
            count++;
            logger.recordLog("-Built " + jclass.getQualifiedName() + " (" + count + "/" + classes.size() + ")");
        }
        monitor.done();
    }
    
    protected static boolean containJavaFile(Set<String> paths) {
        return paths.stream().anyMatch(path -> containJavaFile(path));
    }
    
    protected static boolean containJavaFile(String path) {
        if (path == null) {
            return false;
        }
        
        File res = new File(path);
        if (res.isFile()) {
            if (isCompilableJavaFile(path)) {
                return true;
            }
        } else if (res.isDirectory()) {
            for (File r : res.listFiles()) {
                if (containJavaFile(r.getPath())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean isCompilableJavaFile(String path) {
        return path.endsWith(".java") &&
                !path.endsWith(File.separator + "module-info.java") &&
                !path.endsWith(File.separator + "package-info.java") &&
                path.indexOf("archetypes" + File.separator) == -1;
    }
    
    public static List<File> collectAllJavaFiles(String[] paths) {
        Set<File> set = collectAllJavaFileSet(paths);
        return set.stream()
            .sorted(Comparator.comparing(File::getAbsolutePath))
            .collect(Collectors.toList());
    }
    
    static List<File> collectAllJavaFiles(String path) {
        Set<File> set = collectAllJavaFileSet(path);
        return set.stream()
            .sorted(Comparator.comparing(File::getAbsolutePath))
            .collect(Collectors.toList());
    }
    
    public static Set<File> collectAllJavaFileSet(String[] paths) {
        return Arrays.stream(paths)
            .flatMap(path -> collectAllJavaFileSet(path).stream())
            .collect(Collectors.toSet());
    }
    
    protected static Set<File> collectAllJavaFileSet(String path) {
        Set<File> files = new HashSet<>();
        if (path == null) {
            return files;
        }
        
        File res = new File(path);
        if (res.isFile()) {
            if (isCompilableJavaFile(path)) {
                files.add(res);
            }
        } else if (res.isDirectory()) {
            for (File r : res.listFiles()) {
                files.addAll(collectAllJavaFiles(r.getPath()));
            }
        }
        return files;
    }
    
    private String read(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            char[] buf = new char[128];
            int count = 0;
            while ((count = reader.read(buf)) != -1) {
                String data = String.valueOf(buf, 0, count);
                content.append(data);
                buf = new char[1024];
            }
        }
        return content.toString();
    }
    
    public void unbuild() {
        ProjectStore.getInstance().clear();
    }
    
    public void update(JavaProject jproject) {
        ProjectStore.getInstance().removeProject(jproject.getPath());
        build(jproject.getModelBuilder(), 
                jproject.getName(), jproject.getPath(), jproject.getClassPath(),
                jproject.getSourcePath(), jproject.getBinaryPath());
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
        
        ASTParser parser = ASTParser.newParser(AST.JLS19);
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
    
    protected Set<IProblem> getParseErrors(CompilationUnit cu) {
        Set<IProblem> errors = new HashSet<>();
        IProblem[] problems = cu.getProblems();
        if (problems.length > 0) {
            for (IProblem problem : problems) {
                if (problem.isError()) {
                    if (verbose) {
                        String message = "Error: " + problem.getMessage();
                        monitor.printError(message);
                        logger.recordLog(message);
                    }
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
    
    private class FileContent {
        
        private String path;
        private String source;
        
        FileContent(String path, String source) {
            this.path = path;
            this.source = source;
        }
        
        String getPath() {
            return path;
        }
        
        String getSource() {
            return source;
        }
    }
}
