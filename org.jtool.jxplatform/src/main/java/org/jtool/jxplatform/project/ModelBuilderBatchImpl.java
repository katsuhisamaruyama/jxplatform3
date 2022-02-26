/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.project;

import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaFile;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.builder.JavaASTVisitor;
import org.jtool.srcmodel.builder.ProjectStore;
import org.jtool.jxplatform.builder.ModelBuilder;
import org.jtool.jxplatform.bytecode.BytecodeClassStore;
import org.jtool.jxplatform.bytecode.BytecodeName;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.FileASTRequestor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;
import java.util.Comparator;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.stream.Collectors;

/**
 * A batch processing builder implementation that builds models related to Java source code.
 * 
 * @author Katsuhisa Maruyama
 */

public class ModelBuilderBatchImpl extends ModelBuilderImpl {
    
    public ModelBuilderBatchImpl(ModelBuilder modelBuiler) {
        super(modelBuiler);
    }
    
    @Override
    public boolean isUnderPlugin() {
        return false;
    }
    
    public List<JavaProject> build(String name, String target) {
        ProjectEnv projectEnv = createTopProjectEnv(name, target);
        
        List<ProjectEnv> projectEnvs = getSubProjects(projectEnv);
        if (projectEnvs.size() > 0) {
            return buildMultiTargets(projectEnvs);
        } else {
            return buildSingleTarget(projectEnv);
        }
    }
    
    private List<ProjectEnv> getSubProjects(ProjectEnv projectEnv) {
        List<ProjectEnv> projectEnvs = new ArrayList<>();
        
        List<String> modules = projectEnv.getModules();
        if (modules.size() == 0) {
            if (projectEnv.isProject() &&
                    ModelBuilderBatchImpl.containJavaFile(projectEnv.getSourcePaths())) {
                projectEnvs.add(projectEnv);
            }
            
        } else {
            for (String module : modules) {
                File dir = projectEnv.getBasePath().resolve(module).toFile();
                if (dir.isDirectory()) {
                    String path = dir.getAbsolutePath();
                    String subprojectname = projectEnv.getName() + "#" + module;
                    ProjectEnv env = createProjectEnv(subprojectname, path, projectEnv);
                    
                    if (env.isProject()) {
                        if (ModelBuilderBatchImpl.containJavaFile(env.getSourcePaths())) {
                            projectEnvs.add(env);
                        }
                    } else {
                        projectEnvs.addAll(getSubProjects(env));
                    }
                }
            }
        }
        return projectEnvs;
    }
    
    private ProjectEnv createTopProjectEnv(String name, String target) {
        ProjectEnv env = createProjectEnv(name, target, null);
        try {
            env.setUpTopProject();
        } catch (Exception e) {
            logger.printError("Fail to collect dependent files.");
        }
        return env;
    }
    
    private ProjectEnv createProjectEnv(String name, String target, ProjectEnv parent) {
        String cdir = new File(".").getAbsoluteFile().getParent();
        Path basePath = Paths.get(getFullPath(target, cdir));
        return ProjectEnv.getProjectEnv(name, basePath, parent);
    }
    
    private List<JavaProject> buildMultiTargets(List<ProjectEnv> projectEnvs) {
        List<JavaProject> projects = new ArrayList<>();
        for (ProjectEnv env : projectEnvs) {
            logger.printMessage("Checking sub-project " + env.getName());
            
            JavaProject jproject = buildTarget(env);
            if (jproject != null) {
                projects.add(jproject);
            }
        }
        return projects;
    }
    
    private List<JavaProject> buildSingleTarget(ProjectEnv projectEnv) {
        List<JavaProject> projects = new ArrayList<>();
        logger.printMessage("Checking project " + projectEnv.getName());
        
        JavaProject jproject = buildTarget(projectEnv);
        if (jproject != null) {
            projects.add(jproject);
        }
        return projects;
    }
    
    private JavaProject buildTarget(ProjectEnv projectEnv) {
        try {
            projectEnv.setUpEachProject();
        } catch (Exception e) {
            logger.printError("Fail to collect dependent files.");
            return null;
        }
        
        JavaProject jproject = build(projectEnv);
        return jproject;
    }
    
    private JavaProject build(ProjectEnv projectEnv) {
        String[] classpath = getClassPath(projectEnv.getClassPaths());
        String[] srcpath = getPath(projectEnv.getSourcePaths());
        String[] binpath = getPath(projectEnv.getBinaryPaths());
        JavaProject jproject = createProject(projectEnv.getName(), projectEnv.getBasePath(),
                classpath, srcpath, binpath);
        
        run(jproject, projectEnv.getExcludedSourceFiles());
        
        logger.writeLog();
        return jproject;
    }
    
    private String[] getPath(Set<String> pathSet) {
        return (String[])pathSet.toArray(new String[pathSet.size()]);
    }
    
    private String[] getClassPath(Set<String> pathSet) {
        String classPathStr;
        Set<String> libPath = pathSet.stream()
                .map(path -> getLibraryPath(path)).collect(Collectors.toSet());
        classPathStr = String.join(File.pathSeparator, libPath);
        return getClassPath(classPathStr);
    }
    
    private String getLibraryPath(String path) {
        return path.endsWith(".jar") ? path : path + File.separator + "*";
    }
    
    public JavaProject build(String name, Path basePath, String classpath, String srcpath, String binpath) {
        String[] classpaths = getClassPath(classpath);
        String[] srcpaths = getPath(srcpath, basePath, "src");
        String[] binpaths = getPath(srcpath, basePath, "bin");
        return build(name, basePath, classpaths, srcpaths, binpaths);
    }
    
    public JavaProject build(String name, String target, String classpath, String srcpath, String binpath) {
        String cdir = new File(".").getAbsoluteFile().getParent();
        Path basePath = Paths.get(getFullPath(target, cdir));
        return build(name, basePath, classpath, srcpath, binpath);
    }
    
    public JavaProject build(String name, String target, String[] classpath, String[] srcpath, String[] binpath) {
        String cdir = new File(".").getAbsoluteFile().getParent();
        Path basePath = Paths.get(getFullPath(target, cdir));
        return build(name, basePath, classpath, srcpath, binpath);
    }
    
    public JavaProject build(String name, Path basePath, String[] classpath, String[] srcpath, String[] binpath) {
        JavaProject jproject = createProject(name, basePath, classpath, srcpath, binpath);
        
        run(jproject);
        
        logger.writeLog();
        return jproject;
    }
    
    public JavaProject createProject(String name, Path basePath, String[] classpath, String[] srcpath, String[] binpath) {
        JavaProject jproject = new JavaProject(name, basePath.toString(), basePath.toString());
        jproject.setModelBuilderImpl(this);
        jproject.getCFGStore().create(jproject);
        jproject.setClassPath(getClassPath(classpath));
        jproject.setSourceBinaryPaths(srcpath, binpath);
        
        ProjectStore.getInstance().addProject(jproject);
        return jproject;
    }
    
    private String[] getPath(String path, Path basePath, String dirname) {
        if (path == null) {
            Path src = basePath.resolve(dirname);
            String[] paths = new String[1];
            paths[0] = src.toFile().exists() ? src.toString() : basePath.toString();
            return paths;
        }
        return path.split(File.pathSeparator);
    }
    
    private String[] getClassPath(String[] classpath) {
        List<String> classpaths = new ArrayList<>();
        for (int i = 0 ; i < classpath.length; i++) {
            classpaths.addAll(Arrays.asList(getClassPath(classpath[i])));
        }
        return classpaths.toArray(new String[classpaths.size()]);
    }
    
    private String[] getClassPath(String classpath) {
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
    
    @Override
    public void update(JavaProject jproject) {
        ProjectStore.getInstance().removeProject(jproject.getPath());
        build(jproject.getName(), jproject.getPath(), jproject.getClassPath(),
                jproject.getSourcePath(), jproject.getBinaryPath());
    }
    
    private void run(JavaProject jproject) {
        List<File> sourceFiles = collectAllJavaFiles(jproject.getSourcePath());
        run(jproject, sourceFiles);
    }
    
    private void run(JavaProject jproject, Set<String> excludedSourceFiles) {
        List<File> allFiles = collectAllJavaFiles(jproject.getSourcePath());
        List<File> sourceFiles = allFiles.stream()
                .filter(f -> !excludedSourceFiles.contains(f.getAbsolutePath())).collect(Collectors.toList());
        run(jproject, sourceFiles);
    }
    
    private void run(JavaProject jproject, List<File> sourceFiles) {
        if (sourceFiles.size() > 0) {
            String[] paths = new String[sourceFiles.size()];
            String[] encodings = new String[sourceFiles.size()];
            Map<String, String> sources = new HashMap<>();
            Map<String, String> charsets = new HashMap<>();
            
            int count = 0;
            for (File file : sourceFiles) {
                try {
                    String path = file.getCanonicalPath();
                    String source = read(file);
                    String charset = DetectCharset.getCharsetName(source.getBytes());
                    paths[count] = path;
                    encodings[count] = charset;
                    sources.put(path, source);
                    charsets.put(path, charset);
                    count++;
                } catch (IOException e) { /* empty */ }
            }
            
            parse(jproject, paths, encodings, sources, charsets);
            collectInfo(jproject);
        } else {
            logger.printError("Found no Java source files in " + jproject.getPath());
        }
    }
    
    private void parse(JavaProject jproject, String[] paths, String[] encodings, Map<String, String> sources, Map<String, String> charsets) {
        final int size = paths.length;
        
        ConsoleProgressMonitor pm = visible ? new ConsoleProgressMonitor() : new NullConsoleProgressMonitor();
        pm.begin(size);
        FileASTRequestor requestor = new FileASTRequestor() {
            private int count = 0;
            
            public void acceptAST(String filepath, CompilationUnit cu) {
                if (getParseErrors(cu).size() == 0) {
                    JavaFile jfile = new JavaFile(cu, filepath, sources.get(filepath), charsets.get(filepath), jproject);
                    JavaASTVisitor visitor = new JavaASTVisitor(jfile);
                    cu.accept(visitor);
                    visitor.terminate();
                } else {
                    logger.printError("Incomplete parse: " + filepath);
                }
                
                pm.work(1);
                count++;
                logger.recordLog("-Parsed " +
                        filepath.substring(jproject.getPath().length() + 1) + " (" + count + "/" + size + ")");
            }
        };
        
        logger.printMessage("Target = " + jproject.getPath() + " (" + jproject.getName() + ")");
        logger.printMessage("** Ready to parse " + size + " files");
        ASTParser parser = getParser();
        
        parser.setEnvironment(jproject.getClassPath(), null, null, true);
        parser.createASTs(paths, encodings, new String[]{ }, requestor, null);
        pm.done();
    }
    
    @Override
    protected Set<IProblem> getParseErrors(CompilationUnit cu) {
        if (verbose) {
            return super.getParseErrors(cu);
        }
        
        Set<IProblem> errors = new HashSet<>();
        IProblem[] problems = cu.getProblems();
        if (problems.length > 0) {
            for (IProblem problem : problems) {
                if (problem.isError()) {
                    errors.add(problem);
                }
            }
        }
        return errors;
    }
    
    protected void collectInfo(JavaProject jproject) {
        int size = jproject.getClasses().size();
        logger.printMessage("** Ready to build java models of " + size + " classes");
        ConsoleProgressMonitor pm = visible ? new ConsoleProgressMonitor() : new NullConsoleProgressMonitor();
        
        pm.begin(size);
        int count = 0;
        for (JavaClass jclass : jproject.getClasses()) {
            jproject.collectInfo(jclass);
            
            pm.work(1);
            count++;
            logger.recordLog("-Built " + jclass.getQualifiedName() + " (" + count + "/" + size + ")");
        }
        pm.done();
    }
    
    protected static boolean containJavaFile(Set<String> paths) {
        return paths.stream().anyMatch(path -> containJavaFile(path));
    }
    
    private static boolean containJavaFile(String path) {
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
    
    private static boolean isCompilableJavaFile(String path) {
        return path.endsWith(".java") &&
                !path.endsWith(File.separator + "module-info.java") &&
                !path.endsWith(File.separator + "package-info.java") &&
                path.indexOf("archetypes" + File.separator) == -1;
    }
    
    protected static List<File> collectAllJavaFiles(String[] paths) {
        Set<File> set = collectAllJavaFileSet(paths);
        return set.stream()
            .sorted(Comparator.comparing(File::getAbsolutePath))
            .collect(Collectors.toList());
    }
    
    protected static List<File> collectAllJavaFiles(String path) {
        Set<File> set = collectAllJavaFileSet(path);
        return set.stream()
            .sorted(Comparator.comparing(File::getAbsolutePath))
            .collect(Collectors.toList());
    }
    
    private static Set<File> collectAllJavaFileSet(String[] paths) {
        return Arrays.stream(paths)
            .flatMap(path -> collectAllJavaFileSet(path).stream())
            .collect(Collectors.toSet());
    }
    
    private static Set<File> collectAllJavaFileSet(String path) {
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
    
    @Override
    public void loadBytecode(JavaProject jproject) {
        BytecodeClassStore bcStore = jproject.getCFGStore().getBCStore();
        Set<BytecodeName> names = bcStore.getBytecodeNamesToBeLoaded();
        if (names.size() > 0) {
            logger.printMessage("** Ready to build java models of " + names.size() + " bytecode-classes");
            ConsoleProgressMonitor pm = visible ? new ConsoleProgressMonitor() : new NullConsoleProgressMonitor();
            
            pm.begin(names.size());
            for (BytecodeName bytecodeName : names) {
                bcStore.loadBytecode(bytecodeName);
                pm.work(1);
            }
            pm.done();
        }
        
        bcStore.setClassHierarchy();
        bcStore.writeBytecodeCache();
    }
}
