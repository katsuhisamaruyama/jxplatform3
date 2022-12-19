/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.project;

import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SimpleEnvTest {
    
    @Test
    public void testGetName() {
        String name = "Simple";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new SimpleEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals(name, env.getName());
    }
    
    @Test
    public void testGetBasePath() {
        String name = "Simple";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new SimpleEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals(target, env.getBasePath().toString());
    }
    
    @Test
    public void testGetTopPath() {
        String name = "Simple";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new SimpleEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals(target, env.getTopPath().toString());
    }
    
    @Test
    public void testGetConfigFile() {
        String name = "Simple";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new SimpleEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals(null, env.configFile);
    }
    
    @Test
    public void testGetModules() {
        String name = "Simple";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new SimpleEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals(0, env.getModules().size());
    }
    
    @Test
    public void testGetSourcePaths() {
        String name = "Simple";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new SimpleEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        List<String> paths = TestUtil.asSortedList(env.getSourcePaths());
        assertEquals(2, paths.size());
        assertEquals(target, paths.get(0));
        assertEquals(target + "/src", paths.get(1));
    }
    
    @Test
    public void testGetBinaryPaths() {
        String name = "Simple";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new SimpleEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        List<String> paths = TestUtil.asSortedList(env.getBinaryPaths());
        assertEquals(1, paths.size());
        assertEquals(target + "/bin", paths.get(0));
    }
    
    @Test
    public void testGetClassPaths() {
        String name = "Simple";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new SimpleEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        List<String> paths = TestUtil.asSortedList(env.getClassPaths());
        assertEquals(2, paths.size());
        assertEquals(target + "/lib", paths.get(0));
        assertEquals(target + "/lib-copied", paths.get(1));
    }
    
    @Test
    public void testGetIncludedSourceFiles() {
        String name = "DrawTool";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new EclipseEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals(0, env.getIncludedSourceFiles().size());
    }
    
    @Test
    public void testGetExcludedSourceFiles() {
        String name = "Simple";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new SimpleEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals(0, env.getExcludedSourceFiles().size());
    }
    
    @Test
    public void testGetCompilerSourceVersion() {
        String name = "Simple";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new SimpleEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals("11", env.getCompilerSourceVersion());
    }
    
    @Test
    public void testGetCompilerTargetVersion() {
        String name = "Simple";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new SimpleEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals("11", env.getCompilerTargetVersion());
    }
}
