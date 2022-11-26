/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal.refmodel;

import org.jtool.srcmodel.JavaProject;
import java.util.stream.Collectors;
import java.util.List;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import org.apache.commons.io.FileUtils;

/**
 * Manages (plus reads and writes) the cache of byte-code classes.
 * 
 * @author Katsuhisa Maruyama
 */
abstract class BytecodeCacheManager {
    
    protected static final String FORMAT_VERSION = "0.1";
    
    static final String BYTECODE_CACHE_DIR = ".jxplatform";
    
    static final String GIT_IGNORE_FILE = ".gitignore";
    
    protected static final String ProjectElem = "project";
    protected static final String PathAttr = "path";
    protected static final String TimeAttr = "time";
    protected static final String FormatVersionAttr = "version";
    protected static final String JavaVMAttr = "jvm";
    
    protected static final String ClassElem = "class";
    protected static final String MethodElem = "method";
    protected static final String FieldElem = "field";
    
    static final String NameAttr = "name";
    static final String ModifierAttr = "mod";
    static final String isInterfaceAttr = "intf";
    static final String isInProjectAttr = "inproj";
    static final String SuperClassAttr = "sclass";
    static final String SuperInterfaceAttr = "sintf";
    
    static final String SignatureAttr = "sig";
    static final String DefAttr = "def";
    static final String UseAttr = "use";
    static final String CallAttr = "call";
    
    protected String cacheExtension;
    
    protected BytecodeCacheManager(String ext) {
        cacheExtension = ext;
    }
    
    abstract boolean readCache(JavaProject jproject, String rootPath, String cacheName, boolean bootModule);
    
    abstract boolean writeCache(String rootPath, List<? extends BytecodeClassCache>classes, String cacheName);
    
    boolean makeCacheDirectory(String rootPath) {
        return makeDir(rootPath, BYTECODE_CACHE_DIR);
    }
    
    String readBootModuleVersion(String rootPath, String filename) {
        if (!makeDir(rootPath, BYTECODE_CACHE_DIR)) {
            return "0";
        }
        
        Path path = Paths.get(rootPath, BYTECODE_CACHE_DIR, filename);
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return reader.readLine();
        } catch (IOException e) {
            return "0";
        }
    }
    
    boolean makeDir(String rootPath, String name) {
        Path path = Paths.get(rootPath, name);
        File dir = path.toFile();
        if (dir != null) {
            if (!dir.isDirectory()) {
                return dir.mkdirs();
            } else {
                return true;
            }
        }
        return false;
    }
    
    boolean writeBootModuleVersion(String rootPath, String filename, String version) {
        Path path = Paths.get(rootPath, BYTECODE_CACHE_DIR, filename);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(version);
            writer.newLine();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    boolean writeGitIgnore(String rootPath) {
        Path path = Paths.get(rootPath, GIT_IGNORE_FILE);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(BYTECODE_CACHE_DIR);
            writer.newLine();
            writer.write(GIT_IGNORE_FILE);
            writer.newLine();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    List<String> getCacheFiles(String rootPath) {
        Path cachepath = Paths.get(rootPath, BYTECODE_CACHE_DIR);
        return FileUtils.listFiles(cachepath.toFile(), new String[]{ cacheExtension }, false).stream()
                .map(f -> f.getName())
                .filter(n -> !n.startsWith("#"))
                .map(n -> n.replace("." + cacheExtension, ""))
                .collect(Collectors.toList());
    }
    
    boolean removeBytecodeCache(String rootPath) {
        try {
            Path cachepath = Paths.get(rootPath, BYTECODE_CACHE_DIR);
            Path gitfilepath = Paths.get(rootPath, GIT_IGNORE_FILE);
            FileUtils.deleteDirectory(cachepath.toFile());
            FileUtils.forceDelete(gitfilepath.toFile());
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    long getLastModifiedTimeJarsCacheFile(String rootPath, String cacheName) {
        Path path = Paths.get(rootPath, BYTECODE_CACHE_DIR, cacheName + "." + cacheExtension);
        File file = path.toFile();
        if (path.toFile().exists()) {
            return file.lastModified();
        }
        return -1;
    }
    
    boolean canRead(String rootPath, String cacheName) {
        Path path = Paths.get(rootPath, BYTECODE_CACHE_DIR, cacheName + "." + cacheExtension);
        return path.toFile().canRead();
    }
}
