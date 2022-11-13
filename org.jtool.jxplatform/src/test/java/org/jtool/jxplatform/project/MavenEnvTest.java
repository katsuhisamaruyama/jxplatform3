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

public class MavenEnvTest {
    
    @Test
    public void testGetName1() {
        String name = "guava-31.0.1";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new MavenEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals(name, env.getName());
    }
    
    @Test
    public void testGetBasePath1() {
        String name = "guava-31.0.1";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new MavenEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals(target, env.getBasePath().toString());
    }
    
    @Test
    public void testGetTopPath1() {
        String name = "guava-31.0.1";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new MavenEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals(target, env.getTopPath().toString());
    }
    
    @Test
    public void testGetConfigFile1() {
        String name = "guava-31.0.1";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new MavenEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals(target + "/pom.xml", env.configFile.toString());
    }
    
    @Test
    public void testGetModules1() {
        String name = "guava-31.0.1";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new MavenEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals(5, env.getModules().size());
        assertEquals("guava", env.getModules().get(0));
    }
    
    @Test
    public void testGetSourcePaths1() {
        String name = "guava-31.0.1";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new MavenEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        List<String> paths = TestUtil.asSortedList(env.getSourcePaths());
        assertEquals(1, paths.size());
        assertEquals(target + "/src", paths.get(0));
    }
    
    @Test
    public void testGetBinaryPaths1() {
        String name = "guava-31.0.1";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new MavenEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        List<String> paths = TestUtil.asSortedList(env.getBinaryPaths());
        assertEquals(1, paths.size());
        assertEquals(target + "/bin", paths.get(0));
    }
    
    @Test
    public void testGetClassPaths1() {
        String name = "guava-31.0.1";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new MavenEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        List<String> paths = TestUtil.asSortedList(env.getClassPaths());
        assertEquals(1, paths.size());
        assertEquals(target + "/lib", paths.get(0));
    }
    
    @Test
    public void testGetIncludedSourceFiles1() {
        String name = "guava-31.0.1";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new MavenEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals(0, env.getIncludedSourceFiles().size());
    }
    
    @Test
    public void testGetExcludedSourceFiles1() {
        String name = "guava-31.0.1";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new MavenEnv(name, basePath, basePath);
        assertTrue(env.isApplicable());
        
        assertEquals(0, env.getExcludedSourceFiles().size());
    }
    
    @Test
    public void testGetName2() {
        String name = "guava-31.0.1/guava";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new MavenEnv(name, basePath, basePath.getParent());
        assertTrue(env.isApplicable());
        
        assertEquals(name, env.getName());
    }
    
    @Test
    public void testGetBasePath2() {
        String name = "guava-31.0.1/guava";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new MavenEnv(name, basePath, basePath.getParent());
        assertTrue(env.isApplicable());
        
        assertEquals(target, env.getBasePath().toString());
    }
    
    @Test
    public void testGetTopPath2() {
        String name = "guava-31.0.1/guava";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new MavenEnv(name, basePath, basePath.getParent());
        assertTrue(env.isApplicable());
        
        String cdir = target.replace("/" + name, "/");
        assertEquals(cdir + "guava-31.0.1", env.getTopPath().toString());
    }
    
    @Test
    public void testGetConfigFile2() {
        String name = "guava-31.0.1/guava";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new MavenEnv(name, basePath, basePath.getParent());
        assertTrue(env.isApplicable());
        
        assertEquals(target + "/pom.xml", env.configFile.toString());
    }
    
    @Test
    public void testGetModules2() {
        String name = "guava-31.0.1/guava";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new MavenEnv(name, basePath, basePath.getParent());
        assertTrue(env.isApplicable());
        
        assertEquals(0, env.getModules().size());
    }
    
    @Test
    public void testGetSourcePaths2() {
        String name = "guava-31.0.1/guava";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new MavenEnv(name, basePath, basePath.getParent());
        assertTrue(env.isApplicable());
        
        List<String> paths = TestUtil.asSortedList(env.getSourcePaths());
        assertEquals(2, paths.size());
        assertEquals(target + "/src", paths.get(0));
        assertEquals(target + "/target/generated-sources", paths.get(1));
    }
    
    @Test
    public void testGetBinaryPaths2() {
        String name = "guava-31.0.1/guava";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new MavenEnv(name, basePath, basePath.getParent());
        assertTrue(env.isApplicable());
        
        List<String> paths = TestUtil.asSortedList(env.getBinaryPaths());
        assertEquals(2, paths.size());
        assertEquals(target + "/target/classes", paths.get(0));
        assertEquals(target + "/target/test-classes", paths.get(1));
    }
    
    @Test
    public void testGetClassPaths2() {
        String name = "guava-31.0.1/guava";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new MavenEnv(name, basePath, basePath.getParent());
        assertTrue(env.isApplicable());
        
        List<String> paths = TestUtil.asSortedList(env.getClassPaths());
        assertEquals(2, paths.size());
        assertEquals(target + "/lib", paths.get(0));
        assertEquals(target + "/lib-copied", paths.get(1));
    }
    
    @Test
    public void testGetIncludedSourceFiles2() {
        String name = "guava-31.0.1/guava";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new MavenEnv(name, basePath, basePath.getParent());
        assertTrue(env.isApplicable());
        
        List<String> paths = TestUtil.asSortedList(env.getIncludedSourceFiles());
        assertEquals(1, paths.size());
        assertEquals(target + "/src/com/google/dummy/IncludedDummy.java", paths.get(0));
    }
    
    @Test
    public void testGetExcludedSourceFiles2() {
        String name = "guava-31.0.1/guava";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new MavenEnv(name, basePath, basePath.getParent());
        assertTrue(env.isApplicable());
        
        List<String> paths = TestUtil.asSortedList(env.getExcludedSourceFiles());
        assertEquals(1, paths.size());
        assertEquals(target + "/src/com/google/dummy/ExcludedDummy.java", paths.get(0));
    }
    
    @Test
    public void testGetCompilerSourceVersion() {
        String name = "guava-31.0.1/guava";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new MavenEnv(name, basePath, basePath.getParent());
        assertTrue(env.isApplicable());
        
        assertEquals("1.8", env.getCompilerSourceVersion());
    }
    
    @Test
    public void testGetCompilerTargetVersion() {
        String name = "guava-31.0.1/guava";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = new MavenEnv(name, basePath, basePath.getParent());
        assertTrue(env.isApplicable());
        
        assertEquals("1.8", env.getCompilerTargetVersion());
    }
}
