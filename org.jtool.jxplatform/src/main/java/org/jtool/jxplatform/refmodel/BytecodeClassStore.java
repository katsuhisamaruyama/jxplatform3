/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.refmodel;

import org.jtool.jxplatform.project.ConsoleProgressMonitor;
import org.jtool.jxplatform.project.ModelBuilderImpl;
import org.jtool.jxplatform.project.NullConsoleProgressMonitor;
import org.jtool.jxplatform.builder.Logger;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaProject;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Enumeration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;

/**
 * An object that stores information on byte-code classes.
 * 
 * @author Katsuhisa Maruyama
 */
public class BytecodeClassStore {
    
    static final String BOOT_VERSION_FILENAME = "#boot.module.version";
    static final String BOOT_CACHE_FILENAME = "#boot.module";
    static final String NO_CACHE_LABEL = "#";
    
    private static final String BootModuleVersion = System.getProperty("java.vm.version");
    
    private JavaProject jproject;
    
    private ClassPool classPool;
    
    private Map<String, BytecodeClass> bytecodeClassMap = new HashMap<>();
    
    private Set<String> cacheNames = new HashSet<>();
    
    private int analysisLevel;
    
    private Map<String, JClass> internalClassMap = new HashMap<>();
    private Map<String, JClass> externalClassMap = new HashMap<>();
    
    public BytecodeClassStore(JavaProject jproject) {
        this.jproject = jproject;
    }
    
    public JavaProject getJavaProject() {
        return jproject;
    }
    
    public void create() {
        ModelBuilderImpl builderImpl = jproject.getModelBuilderImpl();
        JCommon.maxNumberOfChainForSourcecode = builderImpl.getSourcecodeAnalysisChain();
        JCommon.maxNumberOfChainForBytecode = builderImpl.getBytecodeAnalysisChain();
        
        if (builderImpl.analyzeBytecode()) {
            analysisLevel = 1;
        } else {
            analysisLevel = 0;
        }
    }
    
    public void destroy() {
    }
    
    public boolean analyzingBytecode() {
        return jproject.getModelBuilderImpl().analyzeBytecode();
    }
    
    public Set<BytecodeName> getBytecodeNamesToBeLoaded() {
        List<String> classPaths = Arrays.asList(jproject.getClassPath());
        Set<BytecodeName> bytecodeNames = collectBytecodeNames(classPaths);
        if (bytecodeNames.size() > 0) {
            setClassPool(classPaths);
        }
        return bytecodeNames;
    }
    
    private boolean readBootCache() {
        boolean bootModuleVersionOk = BootModuleVersion
                .equals(BytecodeCacheManager.readBootModuleVersion(jproject, BOOT_VERSION_FILENAME));
        boolean readCacheOk = BytecodeCacheManager.readCache(jproject, BOOT_CACHE_FILENAME);
        return bootModuleVersionOk && readCacheOk;
    }
    
    private Set<BytecodeName> collectBytecodeNames(List<String> classPaths) {
        Set<BytecodeName> bytecodeNames = new HashSet<>();
        
        boolean readBootCacheOk = readBootCache();
        if (readBootCacheOk) {
            analysisLevel = 2;
        } else {
            bytecodeNames.addAll(collectClassNamesFromJavaModules());
            cacheNames.add(BOOT_CACHE_FILENAME);
        }
        
        bytecodeNames.addAll(classPaths.stream()
                .flatMap(path -> collectClassNames(path).stream())
                .collect(Collectors.toSet()));
        
        return bytecodeNames;
    }
    
    private void setClassPool(List<String> classPaths) {
        try {
            classPool = new ClassPool(true);
            for (String path : classPaths) {
                classPool.insertClassPath(path);
            }
            
        } catch (NotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
    
    private Set<BytecodeName> collectClassNamesFromJavaModules() {
        ModuleFinder finder = ModuleFinder.ofSystem();
        Set<BytecodeName> bytecodeNames = new HashSet<>();
        ModuleLayer.boot().modules()
                .stream()
                .map(module -> module.getName())
                .forEach(name -> {
                    Optional<ModuleReference> modref = finder.find(name);
                    modref.ifPresent(ref -> {
                        try {
                            ref.open().list()
                                .filter(n -> n.endsWith(".class"))
                                .map(n -> n.substring(0, n.length() - 6))
                                .map(n -> n.replaceAll(File.separator, "."))
                                .forEach(n -> bytecodeNames.add(new BytecodeName(n, BOOT_CACHE_FILENAME)));
                        } catch (IOException e) { /* empty */ }
                    });
                });
        return bytecodeNames;
    }
    
    private Set<BytecodeName> collectClassNames(String path) {
        Set<BytecodeName> bytecodeNames = new HashSet<>();
        File file = new File(path);
        if (file.isDirectory()) {
            collectClassFiles(bytecodeNames, path, "");
        } else if (file.isFile() && (path.endsWith(".jar") || path.endsWith(".zip"))) {
            collectClassFilesInJar(bytecodeNames, file);
        }
        return bytecodeNames;
    }
    
    private void collectClassFiles(Set<BytecodeName> bytecodeNames, String classPath, String name) {
        File file = new File(classPath + File.separator + name);
        if (file.isDirectory()) {
            String[] names = file.list();
            for (int i = 0; i < names.length; i++) {
                if (name.length() == 0) {
                    collectClassFiles(bytecodeNames, classPath, names[i]);
                } else {
                    collectClassFiles(bytecodeNames, classPath, name + File.separatorChar + names[i]);
                }
            }
            
        } else if (file.isFile() && name.endsWith(".class")) {
            registerClassName(bytecodeNames, name, NO_CACHE_LABEL);
        }
    }
    
    private void collectClassFilesInJar(Set<BytecodeName> bytecodeNames, File file) {
        String cacheName = getCacheName(file);
        if (cacheNames.contains(cacheName)) {
            return;
        }
        
        if (analysisLevel == 2) {
            long cacheTime = BytecodeCacheManager.getLastModifiedTimeJarsCacheFile(jproject, cacheName);
            if (cacheTime >= file.lastModified() && BytecodeCacheManager.canRead(jproject, cacheName)) {
                boolean readCacheOk = BytecodeCacheManager.readCache(jproject, cacheName);
                if (readCacheOk) {
                    return;
                }
            }
        }
        
        cacheNames.add(cacheName);
        
        try (ZipFile zipFile = new ZipFile(file)) {
            for (Enumeration<? extends ZipEntry> entries = zipFile.entries(); entries.hasMoreElements(); ) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();
                if (name.endsWith(".class")) {
                    registerClassName(bytecodeNames, name, cacheName);
                }
            }
        } catch (IOException e) { /* empty */ }
    }
    
    private String getCacheName(File file) {
        String filename = file.getName();
        if (filename.endsWith(".jar")) {
            return filename.substring(0, filename.length() - ".jar".length());
        } else if (filename.endsWith(".zip")) {
            return filename.substring(0, filename.length() - ".zip".length());
        } else {
            return filename;
        }
    }
    
    private void registerClassName(Set<BytecodeName> bytecodeNames, String name, String cacheName) {
        String className = name.substring(0, name.length() - ".class".length());
        className = className.replace(File.separatorChar, '.');
        if (jproject.getClass(className) == null) {
            bytecodeNames.add(new BytecodeName(className, cacheName));
        }
    }
    
    void registerBytecode(BytecodeClassProxy clazz) {
        bytecodeClassMap.put(clazz.getName(), clazz);
    }
    
    public void removeBytecodeCache() {
        BytecodeCacheManager.removeBytecodeCache(jproject);
    }
    
    public void writeBytecodeCache() {
        for (String cacheName : cacheNames) {
            List<BytecodeClass> classes = bytecodeClassMap.values().stream()
                    .filter(bclass -> bclass.getCacheName().equals(cacheName))
                    .collect(Collectors.toList());
            
            BytecodeCacheManager.writeCache(jproject, sortBytecodeClasses(classes), cacheName);
        }
        
        BytecodeCacheManager.writeBootModuleVersion(jproject, BOOT_VERSION_FILENAME, BootModuleVersion);
        BytecodeCacheManager.writeGitIgnore(jproject);
    }
    
    public void setClassHierarchy() {
        analysisLevel = 3;
        
        bytecodeClassMap.values().forEach(bclass -> bclass.findClassHierarchy());
        bytecodeClassMap.values().forEach(bclass -> bclass.setClassHierarchy());
        
        addClassHierarchyForInternalClasses();
    }
    
    private void addClassHierarchyForInternalClasses() {
        for (JavaClass jclass : jproject.getClasses()) {
            if (jclass.getSuperClassName() != null) {
                BytecodeClass superClass = bytecodeClassMap.get(jclass.getSuperClassName());
                if (superClass != null) {
                    superClass.collectAncestors().forEach(ancestor -> ancestor.addDescendant(jclass.getClassName()));
                }
            }
            jclass.getSuperInterfaceNames().stream()
                .map(name -> bytecodeClassMap.get(name))
                .filter(parent -> parent != null)
                .forEach(parent -> parent.collectAncestors()
                        .forEach(ancestor -> ancestor.addDescendant(jclass.getClassName())));
        }
    }
    
    public JClass getJClass(String className) {
        JClass clazz = internalClassMap.get(className);
        if (clazz == null) {
            clazz = registerInternalClass(className);
        }
        if (analysisLevel == 0 || clazz != null) {
            return clazz;
        }
        
        clazz = externalClassMap.get(className);
        if (clazz == null) {
            clazz = registerExternalClass(className);
        }
        return clazz;
    }
    
    public JMethod getJMethod(String className, String signature) {
        JClass clazz = getJClass(className);
        if (clazz == null) {
            return null;
        }
        JMethod method = clazz.getMethod(signature);
        if (method != null) {
            return method;
        }
        
        for (JClass jc : clazz.getSuperClassChain()) {
            JMethod jm = jc.getMethod(signature);
            if (jm != null) {
                return jm;
            }
        }
        return null;
    }
    
    public JField getJField(String className, String signature) {
        JClass clazz = getJClass(className);
        if (clazz == null) {
            return null;
        }
        JField field = clazz.getField(signature);
        if (field != null) {
            return field;
        }
        
        for (JClass jc : clazz.getSuperClassChain()) {
            JField jf = jc.getField(signature);
            if (jf != null) {
                return jf;
            }
        }
        return null;
    }
    
    private JClass registerInternalClass(String className) {
        JavaClass jclass = jproject.getClass(className);
        if (jclass != null) {
            JClass clazz = new JClassInternal(jclass, this);
            internalClassMap.put(clazz.getQualifiedName().fqn(), clazz);
            return clazz;
        }
        return null;
    }
    
    private JClass registerExternalClass(String className) {
        BytecodeClass bclass = getBcClass(className);
        if (bclass != null) {
            JClass clazz;
            if (bclass.isCache()) {
                clazz = new JClassCache(bclass, this);
            } else {
                clazz = new JClassExternal(bclass, this);
            }
            externalClassMap.put(clazz.getQualifiedName().fqn(), clazz);
            return clazz;
        }
        return null;
    }
    
    BytecodeClass getBcClass(String className) {
        if (analysisLevel < 3) {
            loadBytecode();
        }
        return bytecodeClassMap.get(className);
    }
    
    void loadBytecode() {
        Logger logger = jproject.getModelBuilderImpl().getLogger();
        ConsoleProgressMonitor pm = logger.isVisible() ? new ConsoleProgressMonitor() : new NullConsoleProgressMonitor();
        
        buildBytecodeClass(logger, pm);
        setClassHierarchy();
        collectBytecodeClassInfo(logger, pm);
        
        writeBytecodeCache();
    }
    
    private void buildBytecodeClass(Logger logger, ConsoleProgressMonitor pm) {
        Set<BytecodeName> names = getBytecodeNamesToBeLoaded();
        if (names.size() > 0) {
            logger.printMessage("** Ready to build java models of " + names.size() + " bytecode-classes");
            pm.begin(names.size());
            for (BytecodeName bytecodeName : names) {
                try {
                    CtClass ctClass = classPool.get(bytecodeName.className);
                    
                    // javassist does not support multi-release JARs
                    if (!ctClass.getName().startsWith("META-INF.")) {
                        
                        if (ctClass.isInterface() || ctClass.getModifiers() != Modifier.PRIVATE) {
                            BytecodeClassJavassist bclass = new BytecodeClassJavassist(ctClass, bytecodeName.cacheName, this);
                            bytecodeClassMap.put(getCanonicalClassName(ctClass), bclass);
                        }
                    }
                } catch (Exception e) { /* empty */ }
                pm.work(1);
            }
            pm.done();
        }
    }
    
    private void collectBytecodeClassInfo(Logger logger, ConsoleProgressMonitor pm) {
        Collection<BytecodeClass> bclasses = bytecodeClassMap.values();
        logger.printMessage("** Ready to collect information on java models of " + bclasses.size() + " bytecode-classes");
        pm.begin(bclasses.size());
        for (BytecodeClass bclass : bclasses) {
            bclass.collectInfo();
            pm.work(1);
        }
        pm.done();
    }
    
    public JClass findInternalClass(String className) {
        return internalClassMap.get(className);
    }
    
    public JClass findExternalClass(String className) {
        return externalClassMap.get(className);
    }
    
    String getCanonicalClassName(CtClass ctClass) {
        String className = ctClass.getName();
        try {
            CtClass parent = ctClass.getDeclaringClass();
            while (parent != null) {
                String pname = parent.getName();
                if (pname.length() >= className.length()) {
                    break;
                }
                
                String iname = className.substring(pname.length() + 1);
                if (isAnonymousClass(iname)) {
                    className = pname + "$" + iname;
                } else {
                    className = pname + "." + iname;
                }
                parent = parent.getDeclaringClass();
            }
            return className;
        } catch (NotFoundException e) {
            return className;
        }
    }
    
    private boolean isAnonymousClass(String className) {
        return className.matches("\\d+?");
    }
    
    String getMethodSignature(CtMethod cm) {
        return cm.getName() + BytecodeMethodSignature.methodSignatureToString(cm.getSignature(), this);
    }
    
    String getConstructorSignature(CtConstructor cm) {
        String className = getCanonicalSimpleClassName(cm.getDeclaringClass()); 
        int index = className.lastIndexOf(".");
        String name = index != -1 ? className.substring(index + 1) : className;
        return name + BytecodeMethodSignature.methodSignatureToString(cm.getSignature(), this);
    }
    
    private String getCanonicalSimpleClassName(CtClass ctClass) {
        String className = getCanonicalClassName(ctClass);
        if (ctClass.getPackageName() != null) {
            return className.substring(ctClass.getPackageName().length() + 1);
        } else {
            return className;
        }
    }
    
    String getCanonicalClassName(String className) {
        try {
            CtClass ctClass = classPool.get(className);
            return getCanonicalClassName(ctClass);
        } catch (NotFoundException e) {
            return null;
        }
    }
    
    private List<BytecodeClass> sortBytecodeClasses(List<? extends BytecodeClass> co) {
        List<BytecodeClass> classes = new ArrayList<>(co);
        Collections.sort(classes, new Comparator<>() {
            public int compare(BytecodeClass bc1, BytecodeClass bc2) {
                return bc1.getName().compareTo(bc2.getName());
            }
        });
        return classes;
    }
}
