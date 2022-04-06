/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.JavaProject;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.CFGTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import java.util.List;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;

public class BasicBlockTest {
    
    private static JavaProject SliceProject;
    private static JavaProject SimpleProject;
    
    @BeforeClass
    public static void setUp() {
        SliceProject = BuilderTestUtil.createProject("Slice", "", "");
        SimpleProject = BuilderTestUtil.createProject("Simple", "", "");
    }
    
    @AfterClass
    public static void tearDown() {
        SliceProject.getModelBuilder().unbuild();
        SimpleProject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testGetLeader1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        List<BasicBlock> result = CFGTestUtil.getBasicBlocks(cfg);
        
        assertEquals(1, result.size());
        
        assertEquals(1, result.get(0).getLeader().getId() - cfg.getId());
    }
    
    @Test
    public void testGetLeader2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        List<BasicBlock> result = CFGTestUtil.getBasicBlocks(cfg);
        
        assertEquals(4, result.size());
        
        assertEquals(1, result.get(0).getLeader().getId() - cfg.getId());
        assertEquals(5, result.get(1).getLeader().getId() - cfg.getId());
        assertEquals(6, result.get(2).getLeader().getId() - cfg.getId());
        assertEquals(7, result.get(3).getLeader().getId() - cfg.getId());
    }
    
    @Test
    public void testGetLeader3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test110", "m( )");
        List<BasicBlock> result = CFGTestUtil.getBasicBlocks(cfg);
        
        assertEquals(3, result.size());
        
        assertEquals(1, result.get(0).getLeader().getId() - cfg.getId());
        assertEquals(4, result.get(1).getLeader().getId() - cfg.getId());
        assertEquals(5, result.get(2).getLeader().getId() - cfg.getId());
    }
    
    @Test
    public void testGetLeader4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test22", "m( )");
        List<BasicBlock> result = CFGTestUtil.getBasicBlocks(cfg);
        
        assertEquals(3, result.size());
        
        assertEquals(1, result.get(0).getLeader().getId() - cfg.getId());
        assertEquals(2, result.get(1).getLeader().getId() - cfg.getId());
        assertEquals(5, result.get(2).getLeader().getId() - cfg.getId());
    }
    
    @Test
    public void testGetLeader5() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test07", "m( )");
        List<BasicBlock> result = CFGTestUtil.getBasicBlocks(cfg);
        
        assertEquals(14, result.size());
        
        assertEquals(1, result.get(0).getLeader().getId() - cfg.getId());
        assertEquals(7, result.get(1).getLeader().getId() - cfg.getId());
        assertEquals(8, result.get(2).getLeader().getId() - cfg.getId());
        assertEquals(10, result.get(3).getLeader().getId() - cfg.getId());
        assertEquals(11, result.get(4).getLeader().getId() - cfg.getId());
        assertEquals(13, result.get(5).getLeader().getId() - cfg.getId());
        assertEquals(14, result.get(6).getLeader().getId() - cfg.getId());
        assertEquals(16, result.get(7).getLeader().getId() - cfg.getId());
        assertEquals(17, result.get(8).getLeader().getId() - cfg.getId());
        assertEquals(19, result.get(9).getLeader().getId() - cfg.getId());
        assertEquals(20, result.get(10).getLeader().getId() - cfg.getId());
        assertEquals(21, result.get(11).getLeader().getId() - cfg.getId());
        assertEquals(22, result.get(12).getLeader().getId() - cfg.getId());
        assertEquals(23, result.get(13).getLeader().getId() - cfg.getId());
    }
    
    @Test
    public void testGetLeader6() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test08", "m( )");
        List<BasicBlock> result = CFGTestUtil.getBasicBlocks(cfg);
        
        assertEquals(8, result.size());
        
        assertEquals(1, result.get(0).getLeader().getId() - cfg.getId());
        assertEquals(6, result.get(1).getLeader().getId() - cfg.getId());
        assertEquals(8, result.get(2).getLeader().getId() - cfg.getId());
        assertEquals(12, result.get(3).getLeader().getId() - cfg.getId());
        assertEquals(13, result.get(4).getLeader().getId() - cfg.getId());
        assertEquals(17, result.get(5).getLeader().getId() - cfg.getId());
        assertEquals(18, result.get(6).getLeader().getId() - cfg.getId());
        assertEquals(24, result.get(7).getLeader().getId() - cfg.getId());
    }
    
    @Test
    public void testGetLeader7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test111", "m( )");
        List<BasicBlock> result = CFGTestUtil.getBasicBlocks(cfg);
        
        assertEquals(5, result.size());
        
        assertEquals(1, result.get(0).getLeader().getId() - cfg.getId());
        assertEquals(6, result.get(1).getLeader().getId() - cfg.getId());
        assertEquals(8, result.get(2).getLeader().getId() - cfg.getId());
        assertEquals(9, result.get(3).getLeader().getId() - cfg.getId());
        assertEquals(11, result.get(4).getLeader().getId() - cfg.getId());
    }
    
    @Test
    public void testGetNodes1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test101", "m( )");
        List<BasicBlock> result = CFGTestUtil.getBasicBlocks(cfg);
        
        assertEquals(1, result.size());
        
        assertEquals("1;2;3;5", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(0).getNodes())));
    }
    
    @Test
    public void testGetNodes2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        List<BasicBlock> result = CFGTestUtil.getBasicBlocks(cfg);
        
        assertEquals(4, result.size());
        
        assertEquals("1;2;3;4", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(0).getNodes())));
        assertEquals("5", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(1).getNodes())));
        assertEquals("6", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(2).getNodes())));
        assertEquals("7;8;9;11", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(3).getNodes())));
    }
    
    @Test
    public void testGetNodes3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test110", "m( )");
        List<BasicBlock> result = CFGTestUtil.getBasicBlocks(cfg);
        
        assertEquals(3, result.size());
        
        assertEquals("1;2;3", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(0).getNodes())));
        assertEquals("4", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(1).getNodes())));
        assertEquals("5;6;8", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(2).getNodes())));
    }
    
    @Test
    public void testGetNodes4() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test22", "m( )");
        List<BasicBlock> result = CFGTestUtil.getBasicBlocks(cfg);
        
        assertEquals(3, result.size());
        
        assertEquals("1", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(0).getNodes())));
        assertEquals("2;3", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(1).getNodes())));
        assertEquals("5;6;7;4;9", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(2).getNodes())));
    }
    
    @Test
    public void testGetNodes5() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test07", "m( )");
        List<BasicBlock> result = CFGTestUtil.getBasicBlocks(cfg);
        
        assertEquals(14, result.size());
        
        assertEquals("1;2;3;4;5;6", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(0).getNodes())));
        assertEquals("7", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(1).getNodes())));
        assertEquals("8;9", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(2).getNodes())));
        assertEquals("10", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(3).getNodes())));
        assertEquals("11;12", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(4).getNodes())));
        assertEquals("13", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(5).getNodes())));
        assertEquals("14;15", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(6).getNodes())));
        assertEquals("16", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(7).getNodes())));
        assertEquals("17;18", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(8).getNodes())));
        assertEquals("19", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(9).getNodes())));
        assertEquals("20", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(10).getNodes())));
        assertEquals("21", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(11).getNodes())));
        assertEquals("22", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(12).getNodes())));
        assertEquals("23;25;26;27;24;29;30;31;28;33", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(13).getNodes())));
    }
    
    @Test
    public void testGetNodes6() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test08", "m( )");
        List<BasicBlock> result = CFGTestUtil.getBasicBlocks(cfg);
        
        assertEquals(8, result.size());
        
        assertEquals("1;2;3;4;5", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(0).getNodes())));
        assertEquals("6;7", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(1).getNodes())));
        assertEquals("8;9;10;11", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(2).getNodes())));
        assertEquals("12", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(3).getNodes())));
        assertEquals("13;14;15;16", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(4).getNodes())));
        assertEquals("17", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(5).getNodes())));
        assertEquals("18;20;21;22;19", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(6).getNodes())));
        assertEquals("24;25;26;23;28;29;30;27;32;33;34;31;36", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(7).getNodes())));
    }
    
    @Test
    public void testGetNodes7() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test111", "m( )");
        List<BasicBlock> result = CFGTestUtil.getBasicBlocks(cfg);
        
        assertEquals(5, result.size());
        
        assertEquals("1;2;3;4;5", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(0).getNodes())));
        assertEquals("6;7", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(1).getNodes())));
        assertEquals("8", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(2).getNodes())));
        assertEquals("9;10", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(3).getNodes())));
        assertEquals("11;12;13;15", TestUtil.asStr(CFGTestUtil.getIdList(cfg, result.get(4).getNodes())));
    }
}
