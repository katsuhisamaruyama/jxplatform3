/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg.internal;

import org.jtool.jxplatform.builder.ModelBuilder;
import org.jtool.pdg.ClDG;
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
        BuilderTestUtil.clearProject();
        JavaProject CSclassroomProject = BuilderTestUtil.getProject("CS-classroom");
        PDGTestUtil.writePDGs(CSclassroomProject, false);
        
        BuilderTestUtil.clearProject();
        JavaProject DrawToolProject = BuilderTestUtil.getProject("DrawTool");
        PDGTestUtil.writePDGs(DrawToolProject, false);
        
        BuilderTestUtil.clearProject();
        JavaProject SimpleProject = BuilderTestUtil.getProject("Simple");
        PDGTestUtil.writePDGs(SimpleProject, false);
        
        BuilderTestUtil.clearProject();
        JavaProject SliceProject = BuilderTestUtil.getProject("Slice");
        PDGTestUtil.writePDGs(SliceProject, false);
        
        BuilderTestUtil.clearProject();
        JavaProject TetrisProject = BuilderTestUtil.getProject("Tetris");
        PDGTestUtil.writePDGs(TetrisProject, false);
        
        BuilderTestUtil.clearProject();
        JavaProject VideoStoreProject = BuilderTestUtil.getProject("VideoStore");
        PDGTestUtil.writePDGs(VideoStoreProject, false);
    }
}
