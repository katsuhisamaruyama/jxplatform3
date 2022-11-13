/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.project;

import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GradleEnvTest {
    
    @Test
    public void testGetName() {
        String name = "mockito-4.2.0";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new GradleEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals(name, env.getName());
    }
    
    @Test
    public void testGetBasePath() {
        String name = "mockito-4.2.0";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new GradleEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals(target, env.getBasePath().toString());
    }
    
    @Test
    public void testGetTopPath() {
        String name = "mockito-4.2.0";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new GradleEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals(target, env.getTopPath().toString());
    }
    
    @Test
    public void testGetConfigFile() {
        String name = "mockito-4.2.0";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new GradleEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals(target + "/build.gradle", env.configFile.toString());
    }
    
    @Test
    public void testGetModules() {
        String name = "mockito-4.2.0";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new GradleEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals(0, env.getModules().size());
    }
    
    @Test
    public void testGetSourcePaths() {
        String name = "mockito-4.2.0";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new GradleEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        List<String> paths = TestUtil.asSortedList(env.getSourcePaths());
        assertEquals(2, paths.size());
        assertEquals(target + "/src/main/java", paths.get(0));
        assertEquals(target + "/src/test/java", paths.get(1));
    }
    
    @Test
    public void testGetBinaryPaths() {
        String name = "mockito-4.2.0";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new GradleEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        List<String> paths = TestUtil.asSortedList(env.getBinaryPaths());
        assertEquals(2, paths.size());
        assertEquals(target + "/bin/main", paths.get(0));
        assertEquals(target + "/bin/test", paths.get(1));
    }
    
    @Test
    public void testGetClassPaths() {
        String name = "mockito-4.2.0";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new GradleEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        List<String> paths = TestUtil.asSortedList(env.getClassPaths());
        assertEquals(2, paths.size());
        assertEquals(target + "/lib", paths.get(0));
        assertEquals(target + "/lib-copied", paths.get(1));
    }
    
    @Test
    public void testGetIncludedSourceFiles() {
        String name = "mockito-4.2.0";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new GradleEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals(0, env.getIncludedSourceFiles().size());
    }
    
    @Test
    public void testGetExcludedSourceFiles() {
        String name = "mockito-4.2.0";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new GradleEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals(0, env.getExcludedSourceFiles().size());
    }
    
    @Test
    public void testGetCompilerSourceVersion() {
        String name = "mockito-4.2.0";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new GradleEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals("1.8", env.getCompilerSourceVersion());
    }
    
    @Test
    public void testGetCompilerTargetVersion() {
        String name = "mockito-4.2.0";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new GradleEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals("1.8", env.getCompilerTargetVersion());
    }
}
