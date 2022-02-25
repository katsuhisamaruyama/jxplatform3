/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcplatform.project;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProjectEnvTest {
    
    private final static String testDirInside = new File(".").getAbsoluteFile().getParent() + "/test_target/";
    
    @Test
    public void testSimple() {
        String name = "Simple";
        String target = testDirInside + name + File.separator;
        
        Path basePath = Paths.get(target);
        ProjectEnv env = ProjectEnv.getProjectEnv(name, basePath, null);
        
        assertTrue(env.toString().startsWith("Simple "));
        
        assertEquals(name, env.getName());
        assertEquals(target, env.getBasePath().toString() + File.separator);
        
        assertEquals(null, env.configFile);
        assertEquals(0, env.getModules().size());
        
        List<String> spaths = new ArrayList<>(env.getSourcePaths());
        Collections.sort(spaths);
        assertEquals(target, spaths.get(0) + File.separator);
        assertEquals(target + "src", spaths.get(1));
        
        List<String> bpaths = new ArrayList<>(env.getBinaryPaths());
        Collections.sort(bpaths);
        assertEquals(target + "bin", bpaths.get(0));
        
        List<String> cpaths = new ArrayList<>(env.getClassPaths());
        Collections.sort(cpaths);
        assertEquals(target + "lib", cpaths.get(0));
    }
    
    @Test
    public void testDrawToolUsingEclipse() {
        String name = "DrawTool";
        String target = testDirInside + name + File.separator;
        
        Path basePath = Paths.get(target);
        ProjectEnv env = ProjectEnv.getProjectEnv(name, basePath, null);
        
        assertTrue(env.toString().startsWith("Eclipse "));
        
        assertEquals(name, env.getName());
        assertEquals(target, env.getBasePath().toString() + File.separator);
        
        assertEquals(target + ".classpath", env.configFile.toString());
        assertEquals(0, env.getModules().size());
        
        List<String> spaths = new ArrayList<>(env.getSourcePaths());
        Collections.sort(spaths);
        assertEquals(target + "src", spaths.get(0));
        
        List<String> bpaths = new ArrayList<>(env.getBinaryPaths());
        Collections.sort(bpaths);
        assertEquals(target + "bin", bpaths.get(0));
        
        List<String> cpaths = new ArrayList<>(env.getClassPaths());
        Collections.sort(cpaths);
        assertEquals(target + "lib", cpaths.get(0));
    }
    
    @Test
    public void testAntUsingAnt() {
        String name = "ant-1.10.12";
        String target = testDirInside + name + File.separator;
        
        Path basePath = Paths.get(target);
        ProjectEnv env = ProjectEnv.getProjectEnv(name, basePath, null);
        
        assertTrue(env.toString().startsWith("Ant "));
        
        assertEquals(name, env.getName());
        assertEquals(target, env.getBasePath().toString() + File.separator);
        
        assertEquals(target + "build.xml", env.configFile.toString());
        assertEquals(0, env.getModules().size());
        
        List<String> spaths = new ArrayList<>(env.getSourcePaths());
        Collections.sort(spaths);
        assertEquals(target + "src/main", spaths.get(0));
        assertEquals(target + "src/tests/junit", spaths.get(1));
        
        List<String> bpaths = new ArrayList<>(env.getBinaryPaths());
        Collections.sort(bpaths);
        assertEquals(target + "bin", bpaths.get(0));
        
        List<String> cpaths = new ArrayList<>(env.getClassPaths());
        Collections.sort(cpaths);
        assertEquals(target + "build/lib", cpaths.get(0));
        assertEquals(target + "lib", cpaths.get(1));
    }
    
    @Test
    public void testGuavaUsingMaven() {
        String name = "guava-31.0.1";
        String target = testDirInside + name + File.separator;
        
        Path basePath = Paths.get(target);
        ProjectEnv env = ProjectEnv.getProjectEnv(name, basePath, null);
        
        assertTrue(env.toString().startsWith("Maven "));
        
        assertEquals(name, env.getName());
        assertEquals(target, env.getBasePath().toString() + File.separator);
        
        assertEquals(target + "pom.xml", env.configFile.toString());
        assertEquals(5, env.getModules().size());
        assertEquals("guava", env.getModules().get(0));
        
        Path subBasePath = basePath.resolve(env.getModules().get(0));
        ProjectEnv subEnv = ProjectEnv.getProjectEnv(env.getModules().get(0), subBasePath, env);
        
        List<String> spaths = new ArrayList<>(subEnv.getSourcePaths());
        Collections.sort(spaths);
        assertEquals(target + "guava/src", spaths.get(0));
        assertEquals(target + "guava/target/generated-sources", spaths.get(1));
        
        List<String> bpaths = new ArrayList<>(subEnv.getBinaryPaths());
        Collections.sort(bpaths);
        assertEquals(target + "guava/target/classes", bpaths.get(0));
        assertEquals(target + "guava/target/test-classes", bpaths.get(1));
        
        List<String> cpaths = new ArrayList<>(subEnv.getClassPaths());
        Collections.sort(cpaths);
        assertEquals(target + "guava/lib", cpaths.get(0));
        assertEquals(target + "guava/lib-copied", cpaths.get(1));
    }
    
    @Test
    public void testMockitoUsingGradle() {
        String name = "mockito-4.2.0";
        String target = testDirInside + name + File.separator;
        
        Path basePath = Paths.get(target);
        ProjectEnv env = ProjectEnv.getProjectEnv(name, basePath, null);
        
        assertTrue(env.toString().startsWith("Gradle "));
        
        assertEquals(name, env.getName());
        assertEquals(target, env.getBasePath().toString() + File.separator);
        
        assertEquals(target + "build.gradle", env.configFile.toString());
        assertEquals(0, env.getModules().size());
        
        List<String> spaths = new ArrayList<>(env.getSourcePaths());
        Collections.sort(spaths);
        assertEquals(target + "src/main/java", spaths.get(0));
        assertEquals(target + "src/test/java", spaths.get(1));
        
        List<String> bpaths = new ArrayList<>(env.getBinaryPaths());
        Collections.sort(bpaths);
        assertEquals(target + "bin/main", bpaths.get(0));
        assertEquals(target + "bin/test", bpaths.get(1));
        
        List<String> cpaths = new ArrayList<>(env.getClassPaths());
        Collections.sort(cpaths);
        assertEquals(target + "lib", cpaths.get(0));
        assertEquals(target + "lib-copied", cpaths.get(1));
    }
}
