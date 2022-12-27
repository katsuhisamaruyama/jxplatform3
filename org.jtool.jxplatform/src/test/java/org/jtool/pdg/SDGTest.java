/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.pdg;

import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.pdg.internal.PDGTestUtil;
import org.jtool.srcmodel.JavaProject;
import java.util.List;
import java.util.Set;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SDGTest {
    
    private static JavaProject CSclassroomProject;
    private static JavaProject DrawToolProject;
    private static JavaProject SimpleProject;
    private static JavaProject SliceProject;
    private static JavaProject TetrisProject;
    private static JavaProject VideoStoreProject;
    
    @BeforeClass
    public static void setUp() {
        BuilderTestUtil.clearProject();
        
        CSclassroomProject = BuilderTestUtil.getProject("CS-classroom");
        DrawToolProject = BuilderTestUtil.getProject("DrawTool");
        SimpleProject = BuilderTestUtil.getProject("Simple");
        SliceProject = BuilderTestUtil.getProject("Slice");
        TetrisProject = BuilderTestUtil.getProject("Tetris");
        VideoStoreProject = BuilderTestUtil.getProject("VideoStore");
    }
    
    @AfterClass
    public static void tearDown() {
        BuilderTestUtil.clearProject();
    }
    
    @Test
    public void testGetNodes1() {
        DependencyGraph sdg = PDGTestUtil.createSDG(CSclassroomProject);
        Set<PDGNode> result = sdg.getNodes();
        
        assertEquals(2828, result.size());
    }
    
    @Test
    public void testGetNodes2() {
        DependencyGraph sdg = PDGTestUtil.createSDG(DrawToolProject);
        Set<PDGNode> result = sdg.getNodes();
        
        assertEquals(5510, result.size());
    }
    
    @Test
    public void testGetNodes3() {
        DependencyGraph sdg = PDGTestUtil.createSDG(SimpleProject);
        Set<PDGNode> result = sdg.getNodes();
        
        assertEquals(2122, result.size());
    }
    
    @Test
    public void testGetNodes4() {
        DependencyGraph sdg = PDGTestUtil.createSDG(SliceProject);
        Set<PDGNode> result = sdg.getNodes();
        
        assertEquals(2386, result.size());
    }
    
    @Test
    public void testGetNodes5() {
        DependencyGraph sdg = PDGTestUtil.createSDG(TetrisProject);
        Set<PDGNode> result = sdg.getNodes();
        
        assertEquals(1894, result.size());
    }
    
    @Test
    public void testGetNodes6() {
        DependencyGraph sdg = PDGTestUtil.createSDG(VideoStoreProject);
        Set<PDGNode> result = sdg.getNodes();
        
        assertEquals(2167, result.size());
    }
    
    @Test
    public void testGetEdges1() {
        SDG sdg = PDGTestUtil.createSDG(CSclassroomProject);
        List<DependencyGraphEdge> result = sdg.getEdges();
        
        assertEquals(4872, result.size());
    }
    
    @Test
    public void testGetEdges2() {
        SDG sdg = PDGTestUtil.createSDG(DrawToolProject);
        List<DependencyGraphEdge> result = sdg.getEdges();
        
        assertEquals(18216, result.size());
    }
    
    @Test
    public void testGetEdges3() {
        SDG sdg = PDGTestUtil.createSDG(SimpleProject);
        List<DependencyGraphEdge> result = sdg.getEdges();
        
        assertEquals(4252, result.size());
    }
    
    @Test
    public void testGetEdges4() {
        SDG sdg = PDGTestUtil.createSDG(SliceProject);
        List<DependencyGraphEdge> result = sdg.getEdges();
        
        assertEquals(4794, result.size());
    }
    
    @Test
    public void testGetEdges5() {
        SDG sdg = PDGTestUtil.createSDG(TetrisProject);
        List<DependencyGraphEdge> result = sdg.getEdges();
        
        assertEquals(6241, result.size());
    }
    
    @Test
    public void testGetEdges6() {
        DependencyGraph sdg = PDGTestUtil.createSDG(VideoStoreProject);
        List<DependencyGraphEdge> result = sdg.getEdges();
        
        assertEquals(5458, result.size());
    }
    
    @Test
    public void testGetCDEdges1() {
        SDG sdg = PDGTestUtil.createSDG(CSclassroomProject);
        List<DependencyGraphEdge> result = sdg.getCDEdges();
        
        assertEquals(3134, result.size());
    }
    
    @Test
    public void testGetCDEdges2() {
        SDG sdg = PDGTestUtil.createSDG(DrawToolProject);
        List<DependencyGraphEdge> result = sdg.getCDEdges();
        
        assertEquals(6557, result.size());
    }
    
    @Test
    public void testGetCDEdges3() {
        DependencyGraph sdg = PDGTestUtil.createSDG(SimpleProject);
        List<DependencyGraphEdge> result = sdg.getCDEdges();
        
        assertEquals(2655, result.size());
    }
    
    @Test
    public void testGetCDEdges4() {
        DependencyGraph sdg = PDGTestUtil.createSDG(SliceProject);
        List<DependencyGraphEdge> result = sdg.getCDEdges();
        
        assertEquals(2800, result.size());
    }
    
    @Test
    public void testGetCDEdges5() {
        DependencyGraph sdg = PDGTestUtil.createSDG(TetrisProject);
        List<DependencyGraphEdge> result = sdg.getCDEdges();
        
        assertEquals(2182, result.size());
    }
    
    @Test
    public void testGetCDEdges6() {
        DependencyGraph sdg = PDGTestUtil.createSDG(VideoStoreProject);
        List<DependencyGraphEdge> result = sdg.getCDEdges();
        
        assertEquals(2541, result.size());
    }
    
    @Test
    public void testGetDDEdges1() {
        SDG sdg = PDGTestUtil.createSDG(CSclassroomProject);
        List<DependencyGraphEdge> result = sdg.getDDEdges();
        
        assertEquals(1738, result.size());
    }
    
    @Test
    public void testGetDDEdges2() {
        SDG sdg = PDGTestUtil.createSDG(DrawToolProject);
        List<DependencyGraphEdge> result = sdg.getDDEdges();
        
        assertEquals(11659, result.size());
    }
    
    @Test
    public void testGetDDEdges3() {
        DependencyGraph sdg = PDGTestUtil.createSDG(SimpleProject);
        List<DependencyGraphEdge> result = sdg.getDDEdges();
        
        assertEquals(1597, result.size());
    }
    
    @Test
    public void testGetDDEdges4() {
        DependencyGraph sdg = PDGTestUtil.createSDG(SliceProject);
        List<DependencyGraphEdge> result = sdg.getDDEdges();
        
        assertEquals(1994, result.size());
    }
    
    @Test
    public void testGetDDEdges5() {
        DependencyGraph sdg = PDGTestUtil.createSDG(TetrisProject);
        List<DependencyGraphEdge> result = sdg.getDDEdges();
        
        assertEquals(4059, result.size());
    }
    
    @Test
    public void testGetDDEdges6() {
        DependencyGraph sdg = PDGTestUtil.createSDG(VideoStoreProject);
        List<DependencyGraphEdge> result = sdg.getDDEdges();
        
        assertEquals(2917, result.size());
    }
    
    @Test
    public void testGetInterPDGEdges1() {
        SDG sdg = PDGTestUtil.createSDG(CSclassroomProject);
        List<DependencyGraphEdge> result = sdg.getInterPDGEdges();
        
        assertEquals(696, result.size());
    }
    
    @Test
    public void testGetInterPDGEdges2() {
        SDG sdg = PDGTestUtil.createSDG(DrawToolProject);
        List<DependencyGraphEdge> result = sdg.getInterPDGEdges();
        
        assertEquals(9699, result.size());
    }
    
    @Test
    public void testGetInterPDGEdges3() {
        DependencyGraph sdg = PDGTestUtil.createSDG(SimpleProject);
        List<DependencyGraphEdge> result = sdg.getInterPDGEdges();
        
        assertEquals(832, result.size());
    }
    
    @Test
    public void testGetInterPDGEdges4() {
        DependencyGraph sdg = PDGTestUtil.createSDG(SliceProject);
        List<DependencyGraphEdge> result = sdg.getInterPDGEdges();
        
        assertEquals(1555, result.size());
    }
    
    @Test
    public void testGetInterPDGEdges5() {
        DependencyGraph sdg = PDGTestUtil.createSDG(TetrisProject);
        List<DependencyGraphEdge> result = sdg.getInterPDGEdges();
        
        assertEquals(3205, result.size());
    }
    
    @Test
    public void testGetInterPDGEdges6() {
        DependencyGraph sdg = PDGTestUtil.createSDG(VideoStoreProject);
        List<DependencyGraphEdge> result = sdg.getInterPDGEdges();
        
        assertEquals(2500, result.size());
    }
    
    @Test
    public void testGetPDGs1() {
        DependencyGraph sdg = PDGTestUtil.createSDG(CSclassroomProject);
        
        assertEquals(192, sdg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGs2() {
        DependencyGraph sdg = PDGTestUtil.createSDG(DrawToolProject);
        
        assertEquals(320, sdg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGs3() {
        DependencyGraph sdg = PDGTestUtil.createSDG(SimpleProject);
        
        assertEquals(271, sdg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGs4() {
        DependencyGraph sdg = PDGTestUtil.createSDG(SliceProject);
        
        assertEquals(307, sdg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGs5() {
        DependencyGraph sdg = PDGTestUtil.createSDG(TetrisProject);
        
        assertEquals(120, sdg.getPDGs().size());
    }
    
    @Test
    public void testGetPDGs6() {
        DependencyGraph sdg = PDGTestUtil.createSDG(VideoStoreProject);
        
        assertEquals(140, sdg.getPDGs().size());
    }
    
    @Test
    public void testGetClDGs1() {
        DependencyGraph sdg = PDGTestUtil.createSDG(CSclassroomProject);
        
        assertEquals(72, sdg.getClDGs().size());
    }
    
    @Test
    public void testGetClDGs2() {
        DependencyGraph sdg = PDGTestUtil.createSDG(DrawToolProject);
        
        assertEquals(35, sdg.getClDGs().size());
    }
    
    @Test
    public void testGetClDGs3() {
        DependencyGraph sdg = PDGTestUtil.createSDG(SimpleProject);
        
        assertEquals(81, sdg.getClDGs().size());
    }
    
    @Test
    public void testGetClDGs4() {
        DependencyGraph sdg = PDGTestUtil.createSDG(SliceProject);
        
        assertEquals(82, sdg.getClDGs().size());
    }
    
    @Test
    public void testGetClDGs5() {
        DependencyGraph sdg = PDGTestUtil.createSDG(TetrisProject);
        
        assertEquals(12, sdg.getClDGs().size());
    }
    
    @Test
    public void testGetClDGs6() {
        DependencyGraph sdg = PDGTestUtil.createSDG(VideoStoreProject);
        
        assertEquals(19, sdg.getClDGs().size());
    }
}
