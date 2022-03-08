/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.refmodel;

import org.jtool.srcmodel.JavaProject;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.jtool.jxplatform.builder.ModelBuilderBatch;

public class RefModelTestUtil {
    
    public static JavaProject createProject(String target, String classpath) {
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        JavaProject project = builder.build(target, target, classpath, target, target);
        
        BytecodeClassStore bcStore = project.getCFGStore().getBCStore();
        bcStore.loadBytecode();
        
        return project;
    }
    
    public static JavaProject createProjectFromSourceWithoutLibCache(String target, String classpath) {
        removeCache(target);
        
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        JavaProject project = builder.build(target, target, classpath, target, target);
        
        BytecodeClassStore bcStore = project.getCFGStore().getBCStore();
        bcStore.loadBytecode();
        
        return project;
    }
    
    public static JavaProject createProjectFromSourceWithLibCache(String target, String classpath) {
        removeCache(target);
        createCache(target, classpath);
        
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        JavaProject project = builder.build(target, target, classpath, target, target);
        
        BytecodeClassStore bcStore = project.getCFGStore().getBCStore();
        bcStore.loadBytecode();
        
        return project;
    }
    
    public static JavaProject createProjectFromCache(String target, String classpath) {
        removeCache(target);
        createCache(target, classpath);
        
        ModelBuilderBatch builder = new ModelBuilderBatch(true, true);
        JavaProject project = builder.build(target, target, classpath, target, target);
        
        BytecodeClassStore bcStore = project.getCFGStore().getBCStore();
        bcStore.loadBytecode();
        
        return project;
    }
    
    static void createCache(String target, String classpath) {
        ModelBuilderBatch builder = new ModelBuilderBatch(true, true);
        //builder.setLogVisible(false);
        JavaProject project = builder.build(target, target, classpath, target, target);
        
        BytecodeClassStore bcStore = project.getCFGStore().getBCStore();
        bcStore.loadBytecode();
        
        builder.unbuild();
    }
    
    static boolean removeCache(String target) {
        try {
            Path cachepath = Paths.get(target, BytecodeCacheManager.BYTECODE_CACHE_DIR);
            Path gitfilepath = Paths.get(target, BytecodeCacheManager.GIT_IGNORE_FILE);
            FileUtils.deleteDirectory(cachepath.toFile());
            FileUtils.forceDelete(gitfilepath.toFile());
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
