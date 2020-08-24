/*
 *  Copyright 2020
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.bytecode;

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
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Enumeration;
import java.util.Arrays;
import java.util.Collection;
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
    
    static final String VM_VERSION_FILENAME = "#vmversion";
    static final String BOOT_CACHE_FILENAME = "#boot.module";
    static final String PROJECT_CACHE_FILENAME = "#project.complete";
    static final String NO_CACHE_LABEL = "#";
    
    private static final String JavaVMVersion = System.getProperty("java.vm.version");
    
    private JavaProject jproject;
    
    private ClassPool classPool;
    
    private Map<String, BytecodeClass> bytecodeClassMap = new HashMap<>();
    
    private Set<String> cacheNames = new HashSet<>();
    
    private int analysisLevel = 0;
    
    private Map<String, JClass> internalClassMap = new HashMap<>();
    private Map<String, JClass> externalClassMap = new HashMap<>();
    
    public BytecodeClassStore(JavaProject jproject) {
        this.jproject = jproject;
        
        if (jproject.getModelBuilderImpl().isAnalyzingBytecode()) {
            analysisLevel = 1;
            if (jproject.getModelBuilderImpl().useBytecodeCache()) {
                loadProjectCache();
                analysisLevel = 2;
            }
        }
    }
    
    public void destroy() {
        if (analysisLevel > 1) {
            writeProjectCache(externalClassMap.values());
        }
    }
    
    private void loadProjectCache() {
        bytecodeClassMap.clear();
        BytecodeCacheManager.readCache(jproject, PROJECT_CACHE_FILENAME);
    }
    
    private void writeProjectCache(Collection<JClass> classes) {
        bytecodeClassMap.clear();
        BytecodeCacheManager.writeCache(jproject, new HashSet<BytecodeClassCache>(classes), PROJECT_CACHE_FILENAME);
    }
    
    public JavaProject getJavaProject() {
        return jproject;
    }
    
    public Set<BytecodeName> getBytecodeNamesToBeLoaded() {
        List<String> classPaths = Arrays.asList(jproject.getClassPath());
        Set<BytecodeName> bytecodeNames = collectBytecodeNames(classPaths);
        if (bytecodeNames.size() > 0) {
            setClassPool(classPaths);
        }
        return bytecodeNames;
    }
    
    private Set<BytecodeName> collectBytecodeNames(List<String> classPaths) {
        Set<BytecodeName> bytecodeNames = new HashSet<>();
        
        if (!JavaVMVersion.equals(BytecodeCacheManager.getVMVersion(jproject, VM_VERSION_FILENAME))) {
            bytecodeNames.addAll(collectClassNamesFromJavaModules());
            cacheNames.add(BOOT_CACHE_FILENAME);
        }
        
        bytecodeNames.addAll(classPaths
                .stream()
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
            name = name.substring(0, name.length() - ".class".length());
            name = name.replace(File.separatorChar, '.');
            registerClassName(bytecodeNames, name, NO_CACHE_LABEL);
        }
    }
    
    private void collectClassFilesInJar(Set<BytecodeName> bytecodeNames, File file) {
        String cacheName = getCacheName(file);
        if (cacheNames.contains(cacheName)) {
            return;
        }
        cacheNames.add(cacheName);
        
        long cacheTime = BytecodeCacheManager.getLastModifiedTimeJarsCacheFile(jproject, cacheName);
        if (cacheTime >= file.lastModified()) {
            return;
        }
        
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
        bytecodeNames.add(new BytecodeName(className, cacheName));
    }
    
    public void loadBytecode(BytecodeName byteCodeName) {
        try {
            CtClass ctClass = classPool.get(byteCodeName.className);
            
            // javassist does not support multi-release JARs
            if (!ctClass.getName().startsWith("META-INF.")) {
                
                if (ctClass.isInterface() || ctClass.getModifiers() != Modifier.PRIVATE) {
                    BytecodeClassJavassist clazz = new BytecodeClassJavassist(ctClass, byteCodeName.cacheName, this);
                    bytecodeClassMap.put(getCanonicalClassName(ctClass), clazz);
                }
            }
        } catch (NotFoundException e) { /* empty */ }
    }
    
    public void setClassHierarchy() {
        bytecodeClassMap.values().forEach(bclass -> bclass.findChierarchy());
        bytecodeClassMap.values().forEach(bclass -> bclass.setClassHierarchy());
        
        for (JavaClass jclass : jproject.getClasses()) {
            BytecodeClass superClass = bytecodeClassMap.get(jclass.getSuperClassName());
            if (superClass != null) {
                superClass.collectAncestors().forEach(ancestor -> ancestor.addDescendant(jclass.getClassName()));
            }
            jclass.getSuperInterfaceNames().stream()
                .map(name -> bytecodeClassMap.get(name))
                .filter(parent -> parent != null)
                .forEach(parent -> parent.collectAncestors()
                        .forEach(ancestor -> ancestor.addDescendant(jclass.getClassName())));
        
        }
        
        bytecodeClassMap.values().forEach(bclass -> bclass.clearClassHierarchyCache());
        analysisLevel = 3;
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
        
        for (JClass jc: clazz.getSuperClassChain()) {
            JMethod jm = jc.getMethod(signature);
            if (jm != null) {
                return jm;
            }
        }
        return null;
    }
    
    public JField getJField(String className, String signature) {
        JClass clazz = getJClass(className);
        if (clazz == null || !clazz.isInProject()) {
            return null;
        }
        
        return clazz.getField(signature);
    }
    
    private JClass registerInternalClass(String className) {
        JavaClass jclass = jproject.getClass(className);
        if (jclass != null) {
            JClassInternal clazz = new JClassInternal(jclass, this);
            internalClassMap.put(clazz.getQualifiedName().fqn(), clazz);
            return clazz;
        }
        return null;
    }
    
    private JClass registerExternalClass(String className) {
        BytecodeClass bclass = getBcClass(className);
        if (bclass != null) {
            JClassExternal clazz = new JClassExternal(bclass, this);
            externalClassMap.put(clazz.getQualifiedName().fqn(), clazz);
            return clazz;
        }
        return null;
    }
    
    BytecodeClass getBcClass(String className) {
        if (analysisLevel < 3) {
            jproject.getModelBuilderImpl().loadBytecode(jproject);
        }
        return bytecodeClassMap.get(className);
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
        return className + BytecodeMethodSignature.methodSignatureToString(cm.getSignature(), this);
    }
    
    private String getCanonicalSimpleClassName(CtClass ctClass) {
        String className = getCanonicalClassName(ctClass);
        return className.substring(ctClass.getPackageName().length() + 1);
    }
    
    String getCanonicalClassName(String className) {
        try {
            CtClass ctClass = classPool.get(className);
            return getCanonicalClassName(ctClass);
        } catch (NotFoundException e) {
            return null;
        }
    }
}
