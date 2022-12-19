/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.srcmodel.internal;

import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.srcmodel.JavaProject;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

public class ProjectStoreTest {
    
    private static JavaProject CSclassroomProject;
    private static JavaProject DrawToolProject;
    private static JavaProject LambdaProject;
    private static JavaProject SimpleProject;
    private static JavaProject SliceProject;
    private static JavaProject TetrisProject;
    private static JavaProject VideoStoreProject;
    
    @BeforeClass
    public static void setUp() {
        CSclassroomProject = BuilderTestUtil.createProject("CS-classroom", "/lib/*", "/src");
        DrawToolProject = BuilderTestUtil.createProject("DrawTool", "", "/src");
        LambdaProject = BuilderTestUtil.createProject("Lambda", "", "");
        SimpleProject = BuilderTestUtil.createProject("Simple", "", "");
        SliceProject = BuilderTestUtil.createProject("Slice", "", "");
        TetrisProject = BuilderTestUtil.createProject("Tetris", "", "");
        VideoStoreProject = BuilderTestUtil.createProject("VideoStore", "/lib/*", "");
    }
    
    @AfterClass
    public static void tearDown() {
        CSclassroomProject.getModelBuilder().unbuild();
        DrawToolProject.getModelBuilder().unbuild();
        LambdaProject.getModelBuilder().unbuild();
        SimpleProject.getModelBuilder().unbuild();
        SliceProject.getModelBuilder().unbuild();
        TetrisProject.getModelBuilder().unbuild();
        VideoStoreProject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testProjectStore() {
        ProjectStore store = ProjectStore.getInstance();
        assertEquals(7, store.getProjects().size());
        
        JavaProject project = store.getProject(BuilderTestUtil.getTarget("DrawTool"));
        assertEquals("DrawTool", project.getName());
    }
    
    @Test
    public void testProjectStoreRemoveAdd() {
        ProjectStore store = ProjectStore.getInstance();
        assertEquals(7, store.getProjects().size());
        
        store.removeProject(BuilderTestUtil.getTarget("DrawTool"));
        
        assertEquals(6, store.getProjects().size());
        assertNull(store.getProject(BuilderTestUtil.getTarget("DrawTool")));
        
        store.addProject(DrawToolProject);
        assertEquals(7, store.getProjects().size());
        assertNotNull(store.getProject(BuilderTestUtil.getTarget("DrawTool")));
    }
    
    @Test
    public void testProjectStoreClearAdd() {
        ProjectStore store = ProjectStore.getInstance();
        assertEquals(7, store.getProjects().size());
        
        store.clear();
        
        assertEquals(0, store.getProjects().size());
        assertNull(store.getProject(BuilderTestUtil.getTarget("DrawTool")));
        
        store.addProject(CSclassroomProject);
        store.addProject(DrawToolProject);
        store.addProject(LambdaProject);
        store.addProject(SimpleProject);
        store.addProject(SliceProject);
        store.addProject(TetrisProject);
        store.addProject(VideoStoreProject);
        
        assertEquals(7, store.getProjects().size());
        assertNotNull(store.getProject(BuilderTestUtil.getTarget("DrawTool")));
    }
}
