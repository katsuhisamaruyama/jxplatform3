/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.project;

import org.jtool.jxplatform.util.TestUtil;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AntEnvTest {
    
    @Test
    public void testGetName() {
        String name = "ant-1.10.12";
        String target = TestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new AntEnv(name, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals(name, env.getName());
    }
    
    @Test
    public void testGetBasePath() {
        String name = "ant-1.10.12";
        String target = TestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new AntEnv(name, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals(target, env.getBasePath().toString());
    }
    
    @Test
    public void testGetConfigFile() {
        String name = "ant-1.10.12";
        String target = TestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new AntEnv(name, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals(target + "/build.xml", env.configFile.toString());
    }
    
    @Test
    public void testGetModules() {
        String name = "ant-1.10.12";
        String target = TestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new AntEnv(name, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals(0, env.getModules().size());
    }
    
    @Test
    public void testGetSourcePaths() {
        String name = "ant-1.10.12";
        String target = TestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new AntEnv(name, basePath);
        assertTrue(env.isApplicable());
        
        List<String> paths = TestUtil.asSortedList(env.getSourcePaths());
        assertEquals(2, paths.size());
        assertEquals(target + "/src/main", paths.get(0));
        assertEquals(target + "/src/tests/junit", paths.get(1));
    }
    
    @Test
    public void testGetBinaryPaths() {
        String name = "ant-1.10.12";
        String target = TestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new AntEnv(name, basePath);
        assertTrue(env.isApplicable());
        
        List<String> paths = TestUtil.asSortedList(env.getBinaryPaths());
        assertEquals(1, paths.size());
        assertEquals(target + "/bin", paths.get(0));
    }
    
    @Test
    public void testGetClassPaths() {
        String name = "ant-1.10.12";
        String target = TestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new AntEnv(name, basePath);
        assertTrue(env.isApplicable());
        
        List<String> paths = TestUtil.asSortedList(env.getClassPaths());
        assertEquals(2, paths.size());
        assertEquals(target + "/build/lib", paths.get(0));
        assertEquals(target + "/lib", paths.get(1));
    }
    
    @Test
    public void testGetIncludedSourceFiles() {
        String name = "ant-1.10.12";
        String target = TestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new AntEnv(name, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals(0, env.getIncludedSourceFiles().size());
    }
    
    @Test
    public void testGetExcludedSourceFiles() {
        String name = "ant-1.10.12";
        String target = TestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new AntEnv(name, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals(0, env.getExcludedSourceFiles().size());
    }
}