/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.project;

import org.jtool.jxplatform.builder.BuilderTestUtil;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class ProjectEnvTest {
    
    @Test
    public void testSimpleEnv() {
        String name = "Simple";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = ProjectEnv.getProjectEnv(name, basePath, null, null);
        
        assertTrue(env.toString().startsWith("Simple "));
    }
    
    @Test
    public void testEclipseEnv() {
        String name = "DrawTool";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = ProjectEnv.getProjectEnv(name, basePath, null, null);
        
        assertTrue(env.toString().startsWith("Eclipse "));
    }
    
    @Test
    public void testAntUsingAnt() {
        String name = "ant-1.10.12";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = ProjectEnv.getProjectEnv(name, basePath, null, null);
        
        assertTrue(env.toString().startsWith("Ant "));
    }
    
    @Test
    public void testGuavaUsingMaven() {
        String name = "guava-31.0.1";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = ProjectEnv.getProjectEnv(name, basePath, null, null);
        
        assertTrue(env.toString().startsWith("Maven "));
        
        Path subBasePath = basePath.resolve("guava");
        ProjectEnv subEnv = ProjectEnv.getProjectEnv(env.getModules().get(0), subBasePath, env.getBasePath(), env);
        
        assertTrue(subEnv.toString().startsWith("Maven "));
    }
    
    @Test
    public void testMockitoUsingGradle() {
        String name = "mockito-4.2.0";
        String target = BuilderTestUtil.getTarget(name);
        Path basePath = Paths.get(target);
        
        ProjectEnv env = ProjectEnv.getProjectEnv(name, basePath, null, null);
        
        assertTrue(env.toString().startsWith("Gradle "));
    }
}
