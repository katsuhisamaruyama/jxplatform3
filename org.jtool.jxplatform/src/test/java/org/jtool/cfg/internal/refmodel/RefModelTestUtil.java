/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal.refmodel;

import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.srcmodel.JavaProject;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;

public class RefModelTestUtil {
    
    public static JavaProject createProject(String name, String lib, String src) {
        JavaProject project = BuilderTestUtil.createProject(name, lib, src);
        
        BytecodeClassStore bcStore = project.getCFGStore().getBCStore();
        bcStore.loadBytecode();
        
        return project;
    }
    
    public static JavaProject createProjectFromSourceWithoutLibCache(String name, String lib, String src) {
        removeCache(BuilderTestUtil.getTarget(name));
        
        JavaProject project = BuilderTestUtil.createProject(name, lib, src);
        
        BytecodeClassStore bcStore = project.getCFGStore().getBCStore();
        bcStore.loadBytecode();
        
        return project;
    }
    
    public static JavaProject createProjectFromSourceWithLibCache(String name, String lib, String src) {
        removeCache(BuilderTestUtil.getTarget(name));
        createCache(name, lib, src);
        
        JavaProject project = BuilderTestUtil.createProject(name, lib, src);
        
        BytecodeClassStore bcStore = project.getCFGStore().getBCStore();
        bcStore.loadBytecode();
        
        return project;
    }
    
    public static void createCache(String name, String lib, String src) {
        JavaProject project = BuilderTestUtil.createProject(name, lib, src);
        
        BytecodeClassStore bcStore = project.getCFGStore().getBCStore();
        bcStore.loadBytecode();
        
        project.getModelBuilder().unbuild();
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
