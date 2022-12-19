/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.JavaProject;
import org.jtool.cfg.internal.CFGTestUtil;
import org.jtool.jxplatform.builder.BuilderTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import java.util.List;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Tests a class that builds a CFG.
 * 
 * @author Katsuhisa Maruyama
 */
public class CFGNodeTest {
    
    private static JavaProject SimpleProject;
    private static JavaProject SliceProject;
    
    @BeforeClass
    public static void setUp() {
        SimpleProject = BuilderTestUtil.createProject("Simple", "", "");
        SliceProject = BuilderTestUtil.createProject("Slice", "", "");
    }
    
    @AfterClass
    public static void tearDown() {
        SimpleProject.getModelBuilder().unbuild();
        SliceProject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testGetPredecessors1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 4);
        List<String> result = CFGTestUtil.getIdList(cfg, node.getPredecessors());
        
        assertEquals("3", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetPredecessors2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 5);
        List<String> result = CFGTestUtil.getIdList(cfg, node.getPredecessors());
        
        assertEquals("4", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetPredecessors3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 6);
        List<String> result = CFGTestUtil.getIdList(cfg, node.getPredecessors());
        
        assertEquals("4", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetPredecessors4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 7);
        List<String> result = CFGTestUtil.getIdList(cfg, node.getPredecessors());
        
        assertEquals("5;6", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetPredecessors5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test110", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 3);
        List<String> result = CFGTestUtil.getIdList(cfg, node.getPredecessors());
        
        assertEquals("2;4", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetPredecessors6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test110", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 4);
        List<String> result = CFGTestUtil.getIdList(cfg, node.getPredecessors());
        
        assertEquals("3", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetSuccessors1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 4);
        List<String> result = CFGTestUtil.getIdList(cfg, node.getSuccessors());
        
        assertEquals("5;6", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetSuccessors2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 5);
        List<String> result = CFGTestUtil.getIdList(cfg, node.getSuccessors());
        
        assertEquals("7", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetSuccessors3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 6);
        List<String> result = CFGTestUtil.getIdList(cfg, node.getSuccessors());
        
        assertEquals("7", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetSuccessors4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 7);
        List<String> result = CFGTestUtil.getIdList(cfg, node.getSuccessors());
        
        assertEquals("8", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetSuccessors5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test110", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 3);
        List<String> result = CFGTestUtil.getIdList(cfg, node.getSuccessors());
        
        assertEquals("4;5", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetSuccessors6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test110", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 4);
        List<String> result = CFGTestUtil.getIdList(cfg, node.getSuccessors());
        
        assertEquals("3", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetIncomingFlows1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 4);
        List<String> result = CFGTestUtil.getIdListOfSrc(cfg, node.getIncomingFlows());
        
        assertEquals("3", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetIncomingFlows2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 5);
        List<String> result = CFGTestUtil.getIdListOfSrc(cfg, node.getIncomingFlows());
        
        assertEquals("4", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetIncomingFlows3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 6);
        List<String> result = CFGTestUtil.getIdListOfSrc(cfg, node.getIncomingFlows());
        
        assertEquals("4", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetIncomingFlows4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 7);
        List<String> result = CFGTestUtil.getIdListOfSrc(cfg, node.getIncomingFlows());
        
        assertEquals("5;6", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetIncomingFlows5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test110", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 3);
        List<String> result = CFGTestUtil.getIdListOfSrc(cfg, node.getIncomingFlows());
        
        assertEquals("2;4", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetIncomingFlows6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test110", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 4);
        List<String> result = CFGTestUtil.getIdListOfSrc(cfg, node.getIncomingFlows());
        
        assertEquals("3", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetOutgoingFlows1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 4);
        List<String> result = CFGTestUtil.getIdListOfDst(cfg, node.getOutgoingFlows());
        
        assertEquals("5;6", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetOutgoingFlows2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 5);
        List<String> result = CFGTestUtil.getIdListOfDst(cfg, node.getOutgoingFlows());
        
        assertEquals("7", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetOutgoingFlows3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 6);
        List<String> result = CFGTestUtil.getIdListOfDst(cfg, node.getOutgoingFlows());
        
        assertEquals("7", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetOutgoingFlows4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 7);
        List<String> result = CFGTestUtil.getIdListOfDst(cfg, node.getOutgoingFlows());
        
        assertEquals("8", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetOutgoingFlows5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test110", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 3);
        List<String> result = CFGTestUtil.getIdListOfDst(cfg, node.getOutgoingFlows());
        
        assertEquals("4;5", TestUtil.asStr(result));
    }
    
    @Test
    public void testGetOutgoingFlows6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test110", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 4);
        List<String> result = CFGTestUtil.getIdListOfDst(cfg, node.getOutgoingFlows());
        
        assertEquals("3", TestUtil.asStr(result));
    }
    
    @Test
    public void testIsBranch1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 4);
        
        assertTrue(node.isBranch());
    }
    
    @Test
    public void testIsBranch2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 3);
        
        assertFalse(node.isBranch());
    }
    
    @Test
    public void testIsBranch3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test110", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 3);
        
        assertTrue(node.isBranch());
    }
    
    @Test
    public void testIsBranch4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test111", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 4);
        
        assertFalse(node.isBranch());
    }
    
    @Test
    public void testIsBranch5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test111", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 5);
        
        assertTrue(node.isBranch());
    }
    
    @Test
    public void testIsLoop1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 3);
        
        assertFalse(node.isLoop());
    }
    
    @Test
    public void testIsLoop2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test110", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 3);
        
        assertTrue(node.isLoop());
    }
    
    @Test
    public void testIsLoop3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test115", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 5);
        
        assertTrue(node.isLoop());
    }
    
    @Test
    public void testIsLoop4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test146", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 6);
        
        assertTrue(node.isLoop());
    }
    
    @Test
    public void testIsJoin1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 3);
        
        assertFalse(node.isJoin());
    }
    
    @Test
    public void testIsJoin2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 7);
        
        assertTrue(node.isJoin());
    }
    
    @Test
    public void testIsJoin3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test110", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 3);
        
        assertTrue(node.isJoin());
    }
    
    @Test
    public void testIsJoin4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test111", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 8);
        
        assertTrue(node.isJoin());
    }
    
    @Test
    public void testIsJoin5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test111", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 11);
        
        assertTrue(node.isJoin());
    }
    
    @Test
    public void testIsEntry1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGNode node = cfg.getEntryNode();
        
        assertTrue(node.isEntry());
    }
    
    @Test
    public void testIsEntry2() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test103");
        CFGNode node = ccfg.getEntryNode();
        
        assertTrue(node.isEntry());
    }
    
    @Test
    public void testIsEntry3() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "I134");
        CFGNode node = ccfg.getEntryNode();
        
        assertTrue(node.isEntry());
    }
    
    @Test
    public void testIsEntry4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S130", "S130( int )");
        CFGNode node = cfg.getEntryNode();
        
        assertTrue(node.isEntry());
    }
    
    @Test
    public void testIsEntry5() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "a");
        CFGNode node = cfg.getEntryNode();
        
        assertTrue(node.isEntry());
    }
    
    @Test
    public void testIsEntry6() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "CHILDRENS");
        CFGNode node = cfg.getEntryNode();
        
        assertTrue(node.isEntry());
    }
    
    @Test
    public void testIsClassEntry1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test103");
        CFGNode node = ccfg.getEntryNode();
        
        assertTrue(node.isClassEntry());
    }
    
    @Test
    public void testIsClassEntry2() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "I134");
        CFGNode node = ccfg.getEntryNode();
        
        assertFalse(node.isClassEntry());
    }
    
    @Test
    public void testIsClassEntry3() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "PriceCode");
        CFGNode node = ccfg.getEntryNode();
        
        assertFalse(node.isClassEntry());
    }
    
    @Test
    public void testIsInterfaceEntry1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test103");
        CFGNode node = ccfg.getEntryNode();
        
        assertFalse(node.isInterfaceEntry());
    }
    
    @Test
    public void testIsInterfaceEntry2() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "I134");
        CFGNode node = ccfg.getEntryNode();
        
        assertTrue(node.isInterfaceEntry());
    }
    
    @Test
    public void testIsInterfaceEntry3() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "PriceCode");
        CFGNode node = ccfg.getEntryNode();
        
        assertFalse(node.isInterfaceEntry());
    }
    
    @Test
    public void testIsEnumEntry1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test103");
        CFGNode node = ccfg.getEntryNode();
        
        assertFalse(node.isEnumEntry());
    }
    
    @Test
    public void testIsEnumEntry2() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "I134");
        CFGNode node = ccfg.getEntryNode();
        
        assertFalse(node.isEnumEntry());
    }
    
    @Test
    public void testIsEnumEntry3() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "PriceCode");
        CFGNode node = ccfg.getEntryNode();
        
        assertTrue(node.isEnumEntry());
    }
    
    @Test
    public void testIsMethodEntry1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGNode node = cfg.getEntryNode();
        
        assertTrue(node.isMethodEntry());
    }
    
    @Test
    public void testIsMethodEntry2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S130", "S130( int )");
        CFGNode node = cfg.getEntryNode();
        
        assertFalse(node.isMethodEntry());
    }
    
    @Test
    public void testIsMethodEntry3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "a");
        CFGNode node = cfg.getEntryNode();
        
        assertFalse(node.isMethodEntry());
    }
    
    @Test
    public void testIsMethodEntry4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "CHILDRENS");
        CFGNode node = cfg.getEntryNode();
        
        assertFalse(node.isMethodEntry());
    }
    
    @Test
    public void testIsConstructorEntry1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGNode node = cfg.getEntryNode();
        
        assertFalse(node.isConstructorEntry());
    }
    
    @Test
    public void testIsConstructorEntry2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S130", "S130( int )");
        CFGNode node = cfg.getEntryNode();
        
        assertTrue(node.isConstructorEntry());
    }
    
    @Test
    public void testIsConstructorEntry3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "a");
        CFGNode node = cfg.getEntryNode();
        
        assertFalse(node.isConstructorEntry());
    }
    
    @Test
    public void testIsConstructorEntry4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "CHILDRENS");
        CFGNode node = cfg.getEntryNode();
        
        assertFalse(node.isConstructorEntry());
    }
    
    @Test
    public void testIsFieldEntry1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGNode node = cfg.getEntryNode();
        
        assertFalse(node.isFieldEntry());
    }
    
    @Test
    public void testIsFieldEntry2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S130", "S130( int )");
        CFGNode node = cfg.getEntryNode();
        
        assertFalse(node.isFieldEntry());
    }
    
    @Test
    public void testIsFieldEntry3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "a");
        CFGNode node = cfg.getEntryNode();
        
        assertTrue(node.isFieldEntry());
    }
    
    @Test
    public void testIsFieldEntry4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "CHILDRENS");
        CFGNode node = cfg.getEntryNode();
        
        assertFalse(node.isFieldEntry());
    }
    
    @Test
    public void testIsEnumConstantEntry1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGNode node = cfg.getEntryNode();
        
        assertFalse(node.isEnumConstantEntry());
    }
    
    @Test
    public void testIsEnumConstantdEntry2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "S130", "S130( int )");
        CFGNode node = cfg.getEntryNode();
        
        assertFalse(node.isEnumConstantEntry());
    }
    
    @Test
    public void testIsEnumConstantEntry3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "a");
        CFGNode node = cfg.getEntryNode();
        
        assertFalse(node.isEnumConstantEntry());
    }
    
    @Test
    public void testIsEnumConstantEntry4() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "PriceCode", "CHILDRENS");
        CFGNode node = cfg.getEntryNode();
        
        assertTrue(node.isEnumConstantEntry());
    }
    
    @Test
    public void testIsInitializerEntry() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test26", ".init( )");
        CFGNode node = cfg.getEntryNode();
        
        assertTrue(node.isInitializerEntry());
    }
    
    @Test
    public void testIsExit() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGNode node = cfg.getExitNode();
        
        assertTrue(node.isExit());
    }
    
    @Test
    public void testIsAssignment() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 1);
        
        assertTrue(node.isAssignment());
    }
    
    @Test
    public void testIsMethodCall() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 6);
        
        assertTrue(node.isMethodCall());
    }
    
    @Test
    public void testIsFieldDeclaration() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "a");
        CFGNode node = CFGTestUtil.getNode(cfg, 1);
        
        assertTrue(node.isFieldDeclaration());
    }
    
    @Test
    public void testIsLocalDeclaration() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 3);
        
        assertTrue(node.isLocalDeclaration());
    }
    
    @Test
    public void testIsAssert() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test34", "add( int )");
        CFGNode node = CFGTestUtil.getNode(cfg, 2);
        
        assertTrue(node.isAssert());
    }
    
    @Test
    public void testIsBreak() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test35", "m1( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 4);
        
        assertTrue(node.isBreak());
    }
    
    @Test
    public void testIsContinue() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test35", "m2( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 5);
        
        assertTrue(node.isContinue());
    }
    
    @Test
    public void testIsDo() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test22", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 3);
        
        assertTrue(node.isDo());
    }
    
    @Test
    public void testIsFor() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test115", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 5);
        
        assertTrue(node.isFor());
    }
    
    @Test
    public void testIsIf() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 4);
        
        assertTrue(node.isIf());
    }
    
    @Test
    public void testIsReturn() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test103", "getA( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 1);
        
        assertTrue(node.isReturn());
    }
    
    @Test
    public void testIsSwitchCase() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test111", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 5);
        
        assertTrue(node.isSwitchCase());
    }
    
    @Test
    public void testIsSwitchDefault() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test112", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 5);
        
        assertTrue(node.isSwitchDefault());
    }
    
    @Test
    public void testIsWhile() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test110", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 3);
        
        assertTrue(node.isWhile());
    }
    
    @Test
    public void testIsSwitch() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test111", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 4);
        
        assertTrue(node.isSwitch());
    }
    
    @Test
    public void testIsSynchronized() {
        CFG cfg = CFGTestUtil.createCFG(SimpleProject, "Test34", "add( int )");
        CFGNode node = CFGTestUtil.getNode(cfg, 4);
        
        assertTrue(node.isSynchronized());
    }
    
    @Test
    public void testIsThrow() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "n( int )");
        CFGNode node = CFGTestUtil.getNode(cfg, 4);
        
        assertTrue(node.isThrow());
    }
    
    @Test
    public void testIsTry() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 3);
        
        assertTrue(node.isTry());
    }
    
    @Test
    public void testIsCatchClause() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 12);
        
        assertTrue(node.isCatchClause());
    }
    
    @Test
    public void testIsFinallyClause() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 14);
        
        assertTrue(node.isFinallyClause());
    }
    
    @Test
    public void testIsThrowClause() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "n( int )");
        CFGNode node = CFGTestUtil.getNode(cfg, 1);
        
        assertTrue(node.isThrowClause());
    }
    
    @Test
    public void testIsParameter() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "n( int )");
        CFGNode node = CFGTestUtil.getNode(cfg, 2);
        
        assertTrue(node.isParameter());
    }
    
    @Test
    public void testIsFormal() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "n( int )");
        CFGNode node = CFGTestUtil.getNode(cfg, 2);
        
        assertTrue(node.isFormal());
    }
    
    @Test
    public void testIsFormalIn() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "n( int )");
        CFGNode node = CFGTestUtil.getNode(cfg, 2);
        
        assertTrue(node.isFormalIn());
    }
    
    @Test
    public void testIsFormalOut() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "n( int )");
        CFGNode node = CFGTestUtil.getNode(cfg, 11);
        
        assertTrue(node.isFormalOut());
    }
    
    @Test
    public void testIsActual() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 8);
        
        assertTrue(node.isActual());
    }
    
    @Test
    public void testIsActualIn() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 8);
        
        assertTrue(node.isActualIn());
    }
    
    @Test
    public void testIsActualOut() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 9);
        
        assertTrue(node.isActualOut());
    }
    
    @Test
    public void testIsReceiver() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 6);
        
        assertTrue(node.isReceiver());
    }
    
    @Test
    public void testIsStatementNotParameter1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 5);
        
        assertTrue(node.isStatementNotParameter());
    }
    
    @Test
    public void testIsStatementNotParameter2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 9);
        
        assertFalse(node.isStatementNotParameter());
    }
    
    @Test
    public void testIsStatement1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 5);
        
        assertTrue(node.isStatement());
    }
    
    @Test
    public void testIsStatement2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 9);
        
        assertTrue(node.isStatement());
    }
    
    @Test
    public void testIsMerge() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test122", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 11);
        
        assertTrue(node.isMerge());
    }
    
    @Test
    public void testIsNextToBranch1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 4);
        
        assertFalse(node.isNextToBranch());
    }
    
    @Test
    public void testIsNextToBranch2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 5);
        
        assertTrue(node.isNextToBranch());
    }
    
    @Test
    public void testIsNextToBranch3() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 6);
        
        assertTrue(node.isNextToBranch());
    }
    
    @Test
    public void testHasDefVariable1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 1);
        
        assertTrue(node.hasDefVariable());
    }
    
    @Test
    public void testHasDefVariable2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 4);
        
        assertFalse(node.hasDefVariable());
    }
    
    @Test
    public void testHasUseVariable1() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 1);
        
        assertFalse(node.hasUseVariable());
    }
    
    @Test
    public void testHasUseVariable2() {
        CFG cfg = CFGTestUtil.createCFG(SliceProject, "Test108", "m( )");
        CFGNode node = CFGTestUtil.getNode(cfg, 4);
        
        assertTrue(node.hasUseVariable());
    }
}
