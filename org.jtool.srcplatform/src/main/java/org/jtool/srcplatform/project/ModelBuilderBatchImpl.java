/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.project;

import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaFile;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.builder.JavaASTVisitor;
import org.jtool.srcmodel.builder.ProjectStore;
import org.jtool.srcplatform.bytecode.BytecodeClassStore;
import org.jtool.srcplatform.bytecode.BytecodeName;
import org.jtool.srcplatform.modelbuilder.ModelBuilder;
import org.jtool.srcplatform.util.DetectCharset;
import org.jtool.srcplatform.util.Logger;
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
        List<String> subProjects = getSubProjects(new File(target));
        if (subProjects.size() > 0) {
            return buildMultiTargets(name, subProjects);
        } else {
            List<JavaProject> projects = new ArrayList<>();
            JavaProject jproject = buildSingleTarget(name, target);
            projects.add(jproject);
            return projects;
        }
    }
    
    private List<JavaProject> buildMultiTargets(String name, List<String> subProjects) {
        List<JavaProject> projects = new ArrayList<>();
        for (String subproject : subProjects) {
            int index = subproject.lastIndexOf(File.separatorChar);
            String subname = name + "#" + subproject.substring(index + 1);
            System.out.println("Checking sub-project " + subproject);
            JavaProject project = buildSingleTarget(subname, subproject);
            projects.add(project);
        }
        return projects;
    }
    
    private JavaProject buildSingleTarget(String name, String target) {
        String cdir = new File(".").getAbsoluteFile().getParent();
        Path basePath = Paths.get(getFullPath(target, cdir));
        
        ProjectEnv env = ProjectEnv.getProjectEnv(basePath);
        String[] classPath = getClassPath(env.getClassPath());
        String[] sourcePath = getPath(env.getSourcePath());
        String[] binaryPath = getPath(env.getBinaryPath());
        JavaProject jproject = build(name, basePath, classPath, sourcePath, binaryPath);
        return jproject;
    }
    
    private String[] getPath(Set<String> pathSet) {
        return (String[])pathSet.toArray(new String[pathSet.size()]);
    }
    
    private String[] getClassPath(Set<String> pathSet) {
        String classPathStr;
        Set<String> libPath = pathSet.stream()
                .map(path -> getLibrarryPath(path)).collect(Collectors.toSet());
        classPathStr = String.join(File.pathSeparator, libPath);
        return getClassPath(classPathStr);
    }
    
    private String getLibrarryPath(String path) {
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
        JavaProject jproject = new JavaProject(name, basePath.toString(), basePath.toString());
        jproject.setModelBuilderImpl(this);
        jproject.getCFGStore().create(jproject);
        jproject.setClassPath(getClassPath(classpath));
        jproject.setSourceBinaryPaths(srcpath, binpath);
        ProjectStore.getInstance().addProject(jproject);
        
        run(jproject);
        Logger.getInstance().writeLog();
        return jproject;
    }
    
    private List<String> getSubProjects(File dir) {
        List<String> targets = new ArrayList<>();
        for (File file : dir.listFiles()) {
            if (file.isDirectory() && isProject(file)) {
                String path = file.getAbsolutePath();
                if (ModelBuilderBatchImpl.collectAllJavaFiles(path + File.separator + "src").size() > 0) {
                    targets.add(path);
                }
                targets.addAll(getSubProjects(new File(file.getAbsolutePath())));
            }
        }
        return targets;
    }
    
    private boolean isProject(File dir) {
        File[] files = dir.listFiles((file, name)
                -> (name.equals(AntEnv.configName) || name.equals(MavenEnv.configName) || name.endsWith(GradleEnv.configName)));
        return files.length > 0;
    }
    
    public String[] getPath(String path, Path basePath, String dirname) {
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
                                    if (file.getAbsolutePath().endsWith(".jar")) {
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
        build(jproject.getName(), jproject.getPath(), jproject.getClassPath(), jproject.getSourcePath(), jproject.getBinaryPath());
    }
    
    private void run(JavaProject jproject) {
        List<File> sourceFiles = collectAllJavaFiles(jproject.getSourcePath());
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
            System.err.println("Found no Java source files in " + jproject.getPath());
        }
    }
    
    private void parse(JavaProject jproject, String[] paths, String[] encodings, Map<String, String> sources, Map<String, String> charsets) {
        final int size = paths.length;
        ConsoleProgressMonitor pm = new ConsoleProgressMonitor();
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
                    System.err.println("Incomplete parse: " + filepath);
                }
                
                pm.work(1);
                count++;
                Logger.getInstance().recordLog("-Parsed " +
                        filepath.substring(jproject.getPath().length() + 1) + " (" + count + "/" + size + ")");
            }
        };
        
        Logger.getInstance().printMessage("Target = " + jproject.getPath() + " (" + jproject.getName() + ")");
        Logger.getInstance().printMessage("** Ready to parse " + size + " files");
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
        Logger.getInstance().printMessage("** Ready to build java models of " + size + " classes");
        ConsoleProgressMonitor pm = new ConsoleProgressMonitor();
        pm.begin(size);
        int count = 0;
        for (JavaClass jclass : jproject.getClasses()) {
            jproject.collectInfo(jclass);
            
            pm.work(1);
            count++;
            Logger.getInstance().recordLog("-Built " + jclass.getQualifiedName() + " (" + count + "/" + size + ")");
        }
        pm.done();
    }
    
    protected static List<File> collectAllJavaFiles(String[] paths) {
        List<File> files = new ArrayList<>();
        for (String path : paths) {
            files.addAll(collectAllJavaFiles(path));
        }
        return files;
    }
    
    protected static List<File> collectAllJavaFiles(String path) {
        List<File> files = new ArrayList<>();
        if (path == null) {
            return files;
        }
        
        File res = new File(path);
        if (res.isFile()) {
            if (path.endsWith(".java") && !path.endsWith(File.separator + "module-info.java")) {
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
            Logger.getInstance().printMessage("** Ready to build java models of " + names.size() + " bytecode-classes");
            ConsoleProgressMonitor pm = new ConsoleProgressMonitor();
            
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
