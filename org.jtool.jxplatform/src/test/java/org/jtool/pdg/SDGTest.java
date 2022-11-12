/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.PDGTestUtil;
import java.util.List;
import java.util.Set;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class SDGTest {
    
    private static JavaProject CSclassroomProject;
    private static JavaProject DrawToolProject;
    private static JavaProject SimpleProject;
    private static JavaProject SliceProject;
    private static JavaProject TetrisProject;
    private static JavaProject VideoStoreProject;
    
    @BeforeClass
    public static void setUp() {
        CSclassroomProject = BuilderTestUtil.createProject("CS-classroom", "/lib/*", "/src");
        DrawToolProject = BuilderTestUtil.createProject("DrawTool", "", "/src");
        SimpleProject = BuilderTestUtil.createProject("Simple", "", "");
        SliceProject = BuilderTestUtil.createProject("Slice", "", "");
        TetrisProject = BuilderTestUtil.createProject("Tetris", "", "");
        VideoStoreProject = BuilderTestUtil.createProject("VideoStore", "/lib/*", "");
    }
    
    @AfterClass
    public static void tearDown() {
        CSclassroomProject.getModelBuilder().unbuild();
        DrawToolProject.getModelBuilder().unbuild();
        SimpleProject.getModelBuilder().unbuild();
        SliceProject.getModelBuilder().unbuild();
        TetrisProject.getModelBuilder().unbuild();
        VideoStoreProject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testGetNodes1() {
        SDG sdg = PDGTestUtil.createSDG(CSclassroomProject);
        Set<PDGNode> result = sdg.getNodes();
        
        assertEquals(2828, result.size());
    }
    
    @Test
    public void testGetNodes2() {
        SDG sdg = PDGTestUtil.createSDG(DrawToolProject);
        Set<PDGNode> result = sdg.getNodes();
        
        assertEquals(5510, result.size());
    }
    
    @Test
    public void testGetNodes3() {
        SDG sdg = PDGTestUtil.createSDG(SimpleProject);
        Set<PDGNode> result = sdg.getNodes();
        
        assertEquals(2090, result.size());
    }
    
    @Test
    public void testGetNodes4() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject);
        Set<PDGNode> result = sdg.getNodes();
        
        assertEquals(2386, result.size());
    }
    
    @Test
    public void testGetNodes5() {
        SDG sdg = PDGTestUtil.createSDG(TetrisProject);
        Set<PDGNode> result = sdg.getNodes();
        
        assertEquals(1894, result.size());
    }
    
    @Test
    public void testGetNodes6() {
        SDG sdg = PDGTestUtil.createSDG(VideoStoreProject);
        Set<PDGNode> result = sdg.getNodes();
        
        assertEquals(2167, result.size());
    }
    
    @Test
    public void testGetEdges1() {
        SDG sdg = PDGTestUtil.createSDG(CSclassroomProject);
        List<Dependence> result = sdg.getEdges();
        
        assertEquals(5045, result.size());
    }
    
    @Test
    public void testGetEdges2() {
        SDG sdg = PDGTestUtil.createSDG(DrawToolProject);
        List<Dependence> result = sdg.getEdges();
        
        assertEquals(19816, result.size());
    }
    
    @Test
    public void testGetEdges3() {
        SDG sdg = PDGTestUtil.createSDG(SimpleProject);
        List<Dependence> result = sdg.getEdges();
        
        assertEquals(4477, result.size());
    }
    
    @Test
    public void testGetEdges4() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject);
        List<Dependence> result = sdg.getEdges();
        
        assertEquals(5185, result.size());
    }
    
    @Test
    public void testGetEdges5() {
        SDG sdg = PDGTestUtil.createSDG(TetrisProject);
        List<Dependence> result = sdg.getEdges();
        
        assertEquals(6639, result.size());
    }
    
    @Test
    public void testGetEdges6() {
        SDG sdg = PDGTestUtil.createSDG(VideoStoreProject);
        List<Dependence> result = sdg.getEdges();
        
        assertEquals(5754, result.size());
    }
    
    @Test
    public void testGetSInterEdges1() {
        SDG sdg = PDGTestUtil.createSDG(CSclassroomProject);
        List<Dependence> result = sdg.getInterEdges();
        
        assertEquals(697, result.size());
    }
    
    @Test
    public void testGetInterEdges2() {
        SDG sdg = PDGTestUtil.createSDG(DrawToolProject);
        List<Dependence> result = sdg.getInterEdges();
        
        assertEquals(11010, result.size());
    }
    
    @Test
    public void testGetInterEdges3() {
        SDG sdg = PDGTestUtil.createSDG(SimpleProject);
        List<Dependence> result = sdg.getInterEdges();
        
        assertEquals(840, result.size());
    }
    
    @Test
    public void testGetInterEdges4() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject);
        List<Dependence> result = sdg.getInterEdges();
        
        assertEquals(1638, result.size());
    }
    
    @Test
    public void testGetInterEdges5() {
        SDG sdg = PDGTestUtil.createSDG(TetrisProject);
        List<Dependence> result = sdg.getInterEdges();
        
        assertEquals(3545, result.size());
    }
    
    @Test
    public void testGetInterEdges6() {
        SDG sdg = PDGTestUtil.createSDG(VideoStoreProject);
        List<Dependence> result = sdg.getInterEdges();
        
        assertEquals(2656, result.size());
    }
    
    @Test
    public void testGetPDGs1() {
        SDG sdg = PDGTestUtil.createSDG(CSclassroomProject);
        
        assertEquals(192, sdg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGs2() {
        SDG sdg = PDGTestUtil.createSDG(DrawToolProject);
        
        assertEquals(320, sdg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGs3() {
        SDG sdg = PDGTestUtil.createSDG(SimpleProject);
        
        assertEquals(266, sdg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGs4() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject);
        
        assertEquals(307, sdg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGs5() {
        SDG sdg = PDGTestUtil.createSDG(TetrisProject);
        
        assertEquals(120, sdg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGs6() {
        SDG sdg = PDGTestUtil.createSDG(VideoStoreProject);
        
        assertEquals(140, sdg.getPDGs().size());
    }
    
    @Test
    public void testGetClDGs1() {
        SDG sdg = PDGTestUtil.createSDG(CSclassroomProject);
        
        assertEquals(72, sdg.getClDGs().size());
    }
    
    @Test
    public void testGetClDGs2() {
        SDG sdg = PDGTestUtil.createSDG(DrawToolProject);
        
        assertEquals(35, sdg.getClDGs().size());
    }
    
    @Test
    public void testGetClDGs3() {
        SDG sdg = PDGTestUtil.createSDG(SimpleProject);
        
        assertEquals(80, sdg.getClDGs().size());
    }
    
    @Test
    public void testGetClDGs4() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject);
        
        assertEquals(82, sdg.getClDGs().size());
    }
    
    @Test
    public void testGetClDGs5() {
        SDG sdg = PDGTestUtil.createSDG(TetrisProject);
        
        assertEquals(12, sdg.getClDGs().size());
    }
    
    @Test
    public void testGetClDGs6() {
        SDG sdg = PDGTestUtil.createSDG(VideoStoreProject);
        
        assertEquals(19, sdg.getClDGs().size());
    }
    
    @Test
    public void testIsSDG1() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject);
        
        assertTrue(sdg.isSDG());
    }
    
    @Test
    public void testIsSDG2() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject);
        ClDG cldg = sdg.findClDG("Test103");
        
        assertFalse(cldg.isSDG());
    }
    
    @Test
    public void testIsSDG3() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject);
        PDG pdg = sdg.findPDG("Test103#m( )");
        
        assertFalse(pdg.isSDG());
    }
}
