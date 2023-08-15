/*
 *  Copyright 2023
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal;

import org.jtool.jxplatform.builder.ModelBuilder;
import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.JavaClass;
import org.jtool.cfg.CCFG;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.jxplatform.util.FlakyByExternalLib;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.experimental.categories.Category;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

@Category(FlakyByExternalLib.class)
public class CFGBuilderTest {
    
    @Test
    public void testTarget() {
        BuilderTestUtil.clearProject();
        
        JavaProject jproject = BuilderTestUtil.getProject("Tetris");
        boolean result = check(jproject);
        assertTrue(result);
        
        jproject.getModelBuilder().unbuild();
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testCSclassroomProject() {
        BuilderTestUtil.clearProject();
        
        JavaProject jproject = BuilderTestUtil.getProject("CS-classroom");
        boolean result = check(jproject);
        assertTrue(result);
        
        jproject.getModelBuilder().unbuild();
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testDrawToolProject() {
        BuilderTestUtil.clearProject();
        
        JavaProject jproject = BuilderTestUtil.getProject("DrawTool");
        boolean result = check(jproject);
        assertTrue(result);
        
        jproject.getModelBuilder().unbuild();
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testSimpleProject() {
        BuilderTestUtil.clearProject();
        
        JavaProject jproject = BuilderTestUtil.getProject("Simple");
        boolean result = check(jproject);
        assertTrue(result);
        
        jproject.getModelBuilder().unbuild();
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testSliceProject() {
        BuilderTestUtil.clearProject();
        
        JavaProject jproject = BuilderTestUtil.getProject("Slice");
        boolean result = check(jproject);
        assertTrue(result);
        
        jproject.getModelBuilder().unbuild();
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testTetrisProject() {
        BuilderTestUtil.clearProject();
        
        JavaProject jproject = BuilderTestUtil.getProject("Tetris");
        boolean result = check(jproject);
        assertTrue(result);
        
        jproject.getModelBuilder().unbuild();
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testVideoStoreProject() {
        BuilderTestUtil.clearProject();
        
        JavaProject jproject = BuilderTestUtil.getProject("VideoStore");
        boolean result = check(jproject);
        assertTrue(result);
        
        jproject.getModelBuilder().unbuild();
        BuilderTestUtil.clearProject();
    }
    
    private boolean check(JavaProject jproject) {
        boolean result = true;
        for (JavaClass jclass : jproject.getClasses()) {
            CCFG ccfg = CFGTestUtil.createCCFG(jproject, jclass.getQualifiedName().fqn());
            String actual = CFGTestUtil.getCCFGData(ccfg) + ModelBuilder.br;
            try {
                Path cfgPath = Paths.get(jproject.getPath(), "cfg");
                Path path = cfgPath.resolve(ccfg.getQualifiedName().fqn() + ".cfg");
                
                String expected = Files.readString(path);
                boolean match = actual.trim().equals(expected.trim());
                if (!match) {
                    System.err.println("Incorrect CFG: " + ccfg.getQualifiedName().fqn());
                    result = false;
                    Path path0 = cfgPath.resolve(jclass.getQualifiedName().fqn() + ".cfg0");
                    CFGTestUtil.writeCFG(path0, actual);
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
        BuilderTestUtil.clearProject();
        JavaProject CSclassroomProject = BuilderTestUtil.getProject("CS-classroom");
        CFGTestUtil.writeCFGs(CSclassroomProject, false);
        
        BuilderTestUtil.clearProject();
        JavaProject DrawToolProject = BuilderTestUtil.getProject("DrawTool");
        CFGTestUtil.writeCFGs(DrawToolProject, false);
        
        BuilderTestUtil.clearProject();
        JavaProject SimpleProject = BuilderTestUtil.getProject("Simple");
        CFGTestUtil.writeCFGs(SimpleProject, false);
        
        BuilderTestUtil.clearProject();
        JavaProject SliceProject = BuilderTestUtil.getProject("Slice");
        CFGTestUtil.writeCFGs(SliceProject, false);
        
        BuilderTestUtil.clearProject();
        JavaProject TetrisProject = BuilderTestUtil.getProject("Tetris");
        CFGTestUtil.writeCFGs(TetrisProject, false);
        
        BuilderTestUtil.clearProject();
        JavaProject VideoStoreProject = BuilderTestUtil.getProject("VideoStore");
        CFGTestUtil.writeCFGs(VideoStoreProject, false);
    }
}
