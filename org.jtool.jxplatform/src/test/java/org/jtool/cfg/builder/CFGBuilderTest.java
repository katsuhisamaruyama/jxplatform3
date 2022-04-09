/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.builder;

import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.CFGTestUtil;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.JavaClass;
import org.jtool.cfg.CCFG;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class CFGBuilderTest {
    
    @Test
    public void testCSclassroomProject() {
        JavaProject jproject = BuilderTestUtil.createProject("CS-classroom", "/lib/*", "/src");
        boolean result = check(jproject);
        assertTrue(result);
        jproject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testDrawToolProject() {
        JavaProject jproject = BuilderTestUtil.createProject("DrawTool", "", "/src");
        boolean result = check(jproject);
        assertTrue(result);
        jproject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testSimpleProject() {
        JavaProject jproject = BuilderTestUtil.createProject("Simple", "", "");
        boolean result = check(jproject);
        assertTrue(result);
        jproject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testSliceProject() {
        JavaProject jproject = BuilderTestUtil.createProject("Slice", "", "");
        boolean result = check(jproject);
        assertTrue(result);
        jproject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testTetrisProject() {
        JavaProject jproject = BuilderTestUtil.createProject("Tetris", "", "");
        boolean result = check(jproject);
        assertTrue(result);
        jproject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testVideoStoreProject() {
        JavaProject jproject = BuilderTestUtil.createProject("VideoStore", "/lib/*", "");
        boolean result = check(jproject);
        assertTrue(result);
        jproject.getModelBuilder().unbuild();
    }
    
    private boolean check(JavaProject jproject) {
        boolean result = true;
        for (JavaClass jclass : jproject.getClasses()) {
            CCFG ccfg = CFGTestUtil.createCCFG(jproject, jclass.getQualifiedName().fqn());
            String actual = CFGTestUtil.getCCFGData(ccfg) + "\n";
            try {
                Path cfgPath = Paths.get(jproject.getPath(), "cfg");
                Path path = cfgPath.resolve(ccfg.getQualifiedName().fqn() + ".cfg");
                
                String expected = Files.readString(path);
                boolean match = actual.equals(expected);
                if (!match) {
                    System.err.println("Incorrect CFG: " + ccfg.getQualifiedName().fqn());
                    result = false;
                }
            } catch (IOException e) {
                System.err.println("Cannot find CFG data: " + ccfg.getQualifiedName().fqn());
                result = false;
            }
        }
        return result;
    }
    
    /**
     * Creates CFG information as expected data when testing.
     */
    public static void main(String[] args) {
        JavaProject CSclassroomProject = BuilderTestUtil.createProject("CS-classroom", "/lib/*", "/src");
        JavaProject DrawToolProject = BuilderTestUtil.createProject("DrawTool", "", "/src");
        JavaProject SimpleProject = BuilderTestUtil.createProject("Simple", "", "");
        JavaProject SliceProject = BuilderTestUtil.createProject("Slice", "", "");
        JavaProject TetrisProject = BuilderTestUtil.createProject("Tetris", "", "");
        JavaProject VideoStoreProject = BuilderTestUtil.createProject("VideoStore", "/lib/*", "");
        
        CFGTestUtil.writeCFGs(CSclassroomProject, false);
        CFGTestUtil.writeCFGs(DrawToolProject, false);
        CFGTestUtil.writeCFGs(SimpleProject, false);
        CFGTestUtil.writeCFGs(SliceProject, false);
        CFGTestUtil.writeCFGs(TetrisProject, false);
        CFGTestUtil.writeCFGs(VideoStoreProject, false);
    }
}
