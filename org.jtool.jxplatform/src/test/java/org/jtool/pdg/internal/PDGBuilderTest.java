/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg.internal;

import org.jtool.jxplatform.builder.ModelBuilder;
import org.jtool.pdg.ClDG;
import org.jtool.pdg.PDGTestUtil;
import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaProject;
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
public class PDGBuilderTest {
    
    @Test
    public void testCSclassroomProject() {
        BuilderTestUtil.clearProject();
        
        JavaProject jproject = BuilderTestUtil.createProject("CS-classroom", "/lib/*", "/src");
        boolean result = check(jproject);
        assertTrue(result);
        
        jproject.getModelBuilder().unbuild();
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testDrawToolProject() {
        BuilderTestUtil.clearProject();
        
        JavaProject jproject = BuilderTestUtil.createProject("DrawTool", "", "/src");
        boolean result = check(jproject);
        assertTrue(result);
        
        jproject.getModelBuilder().unbuild();
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testSimpleProject() {
        BuilderTestUtil.clearProject();
        
        JavaProject jproject = BuilderTestUtil.createProject("Simple", "", "");
        boolean result = check(jproject);
        assertTrue(result);
        
        jproject.getModelBuilder().unbuild();
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testSliceProject() {
        BuilderTestUtil.clearProject();
        
        JavaProject jproject = BuilderTestUtil.createProject("Slice", "", "");
        boolean result = check(jproject);
        assertTrue(result);
        
        jproject.getModelBuilder().unbuild();
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testTetrisProject() {
        BuilderTestUtil.clearProject();
        
        JavaProject jproject = BuilderTestUtil.createProject("Tetris", "", "");
        boolean result = check(jproject);
        assertTrue(result);
        
        jproject.getModelBuilder().unbuild();
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testVideoStoreProject() {
        BuilderTestUtil.clearProject();
        
        JavaProject jproject = BuilderTestUtil.createProject("VideoStore", "/lib/*", "");
        boolean result = check(jproject);
        assertTrue(result);
        
        jproject.getModelBuilder().unbuild();
        BuilderTestUtil.clearProject();
    }
    
    private boolean check(JavaProject jproject) {
        boolean result = true;
        for (JavaClass jclass : jproject.getClasses()) {
            ClDG cldg = PDGTestUtil.createClDG(jproject, jclass.getQualifiedName().fqn());
            String actual = PDGTestUtil.getClDGData(cldg) + ModelBuilder.br;
            
            try {
                Path pdgPath = Paths.get(jproject.getPath(), "pdg");
                Path path = pdgPath.resolve(cldg.getQualifiedName().fqn() + ".pdg");
                
                String expected = Files.readString(path);
                boolean match = actual.equals(expected);
                if (!match) {
                    System.err.println("Incorrect PDG: " + cldg.getQualifiedName().fqn());
                    result = false;
                    Path path0 = pdgPath.resolve(jclass.getQualifiedName().fqn() + ".pdg0");
                    PDGTestUtil.writePDG(path0, actual);
                }
            } catch (IOException e) {
                System.err.println("Cannot find PDG data: " + cldg.getQualifiedName().fqn());
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
        
        PDGTestUtil.writePDGs(CSclassroomProject, false);
        PDGTestUtil.writePDGs(DrawToolProject, false);
        PDGTestUtil.writePDGs(SimpleProject, false);
        PDGTestUtil.writePDGs(SliceProject, false);
        PDGTestUtil.writePDGs(TetrisProject, false);
        PDGTestUtil.writePDGs(VideoStoreProject, false);
    }
}
