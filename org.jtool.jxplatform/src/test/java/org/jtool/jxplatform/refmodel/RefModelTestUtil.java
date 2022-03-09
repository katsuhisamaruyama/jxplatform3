/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.refmodel;

import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.srcmodel.JavaProject;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.jtool.jxplatform.builder.ModelBuilderBatch;

public class RefModelTestUtil {
    
    public static JavaProject createProject(String name, String lib, String src) {
        String target = BuilderTestUtil.getTarget(name);
        
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        JavaProject project = builder.build(target, target, target + lib, target + src, target);
        
        BytecodeClassStore bcStore = project.getCFGStore().getBCStore();
        bcStore.loadBytecode();
        
        return project;
    }
    
    public static JavaProject createProjectFromSourceWithoutLibCache(String name, String lib, String src) {
        String target = BuilderTestUtil.getTarget(name);
        
        removeCache(target);
        
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        JavaProject project = builder.build(target, target, target + lib, target + src, target);
        
        BytecodeClassStore bcStore = project.getCFGStore().getBCStore();
        bcStore.loadBytecode();
        
        return project;
    }
    
    public static JavaProject createProjectFromSourceWithLibCache(String name, String lib, String src) {
        String target = BuilderTestUtil.getTarget(name);
        
        removeCache(target);
        createCache(name, lib, src);
        
        ModelBuilderBatch builder = new ModelBuilderBatch(true);
        JavaProject project = builder.build(target, target, target + lib, target + src, target);
        
        BytecodeClassStore bcStore = project.getCFGStore().getBCStore();
        bcStore.loadBytecode();
        
        return project;
    }
    
    public static JavaProject createProjectFromCache(String name, String lib, String src) {
        String target = BuilderTestUtil.getTarget(name);
        
        removeCache(target);
        createCache(name, lib, src);
        
        ModelBuilderBatch builder = new ModelBuilderBatch(true, true);
        JavaProject project = builder.build(target, target, target + lib, target + src, target);
        
        BytecodeClassStore bcStore = project.getCFGStore().getBCStore();
        bcStore.loadBytecode();
        
        return project;
    }
    
    public static void createCache(String name, String lib, String src) {
        String target = BuilderTestUtil.getTarget(name);
        
        ModelBuilderBatch builder = new ModelBuilderBatch(true, true);
        //builder.setLogVisible(false);
        JavaProject project = builder.build(target, target, target + lib, target + src, target);
        
        BytecodeClassStore bcStore = project.getCFGStore().getBCStore();
        bcStore.loadBytecode();
        
        builder.unbuild();
    }
    
    public static boolean removeCache(String target) {
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
