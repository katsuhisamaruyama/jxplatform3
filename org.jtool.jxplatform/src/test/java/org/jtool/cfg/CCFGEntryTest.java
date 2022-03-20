/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg;

import org.jtool.srcmodel.JavaProject;
import org.jtool.srcmodel.QualifiedName;
import org.jtool.jxplatform.util.BuilderTestUtil;
import org.jtool.jxplatform.util.CFGTestUtil;
import org.jtool.jxplatform.util.TestUtil;
import java.util.Set;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Tests a class that builds a CFG.
 * 
 * @author Katsuhisa Maruyama
 */
public class CCFGEntryTest {
    
    private static JavaProject SimpleProject;
    private static JavaProject SliceProject;
    private static JavaProject VideoStoreProject;
    
    @BeforeClass
    public static void setUp() {
        SliceProject = BuilderTestUtil.createProject("Slice", "", "");
        VideoStoreProject = BuilderTestUtil.createProject("VideoStore", "/lib/*", "");
        SimpleProject = BuilderTestUtil.createProject("Simple", "", "");
    }
    
    @AfterClass
    public static void tearDown() {
        SliceProject.getModelBuilder().unbuild();
        VideoStoreProject.getModelBuilder().unbuild();
        SimpleProject.getModelBuilder().unbuild();
    }
    
    @Test
    public void testGetQualifiedNameTest101() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test101");
        CCFGEntry node = ccfg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("Test101", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNameTest134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test134");
        CCFGEntry node = ccfg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("Test134", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNameP134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "P134");
        CCFGEntry node = ccfg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("P134", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNameQ134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Q134");
        CCFGEntry node = ccfg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("Q134", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNameR134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "R134");
        CCFGEntry node = ccfg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("R134", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNameI134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "I134");
        CCFGEntry node = ccfg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("I134", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNameTest139() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test139");
        CCFGEntry node = ccfg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("Test139", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNamePriceCode() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "PriceCode");
        CCFGEntry node = ccfg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("PriceCode", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNameCustomer() {
        CCFG ccfg = CFGTestUtil.createCCFG(VideoStoreProject, "org.jtool.videostore.after.Customer");
        CCFGEntry node = ccfg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("org.jtool.videostore.after.Customer", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNameRental() {
        CCFG ccfg = CFGTestUtil.createCCFG(VideoStoreProject, "org.jtool.videostore.after.Rental");
        CCFGEntry node = ccfg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("org.jtool.videostore.after.Rental", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNameTest28() {
        CCFG ccfg = CFGTestUtil.createCCFG(SimpleProject, "Test28");
        CCFGEntry node = ccfg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("Test28", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNameTest28$MyActionListener() {
        CCFG ccfg = CFGTestUtil.createCCFG(SimpleProject, "Test28.MyActionListener");
        CCFGEntry node = ccfg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("Test28.MyActionListener", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNameTest29() {
        CCFG ccfg = CFGTestUtil.createCCFG(SimpleProject, "Test29");
        CCFGEntry node = ccfg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("Test29", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNameTest29$1MyActionListener() {
        CCFG ccfg = CFGTestUtil.createCCFG(SimpleProject, "Test29$1MyActionListener");
        CCFGEntry node = ccfg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("Test29$1MyActionListener", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNameTest30() {
        CCFG ccfg = CFGTestUtil.createCCFG(SimpleProject, "Test30");
        CCFGEntry node = ccfg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("Test30", result.fqn());
    }
    
    @Test
    public void testGetQualifiedNameTest30$1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SimpleProject, "Test30$1");
        CCFGEntry node = ccfg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("Test30$1", result.fqn());
    }
    
    @Test
    public void testGetCCFGTest101() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test101");
        CCFGEntry node = ccfg.getEntryNode();
        CCFG result = node.getCCFG();
        
        assertEquals(ccfg, result);
    }

    @Test
    public void testGetCCFGTest134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test134");
        CCFGEntry node = ccfg.getEntryNode();
        CCFG result = node.getCCFG();
        
        assertEquals(ccfg, result);
    }
    
    @Test
    public void testGetCCFGP134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "P134");
        CCFGEntry node = ccfg.getEntryNode();
        CCFG result = node.getCCFG();
        
        assertEquals(ccfg, result);
    }
    
    @Test
    public void testGetCCFGQ134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Q134");
        CCFGEntry node = ccfg.getEntryNode();
        CCFG result = node.getCCFG();
        
        assertEquals(ccfg, result);
    }
    
    @Test
    public void testGetCCFGR134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "R134");
        CCFGEntry node = ccfg.getEntryNode();
        CCFG result = node.getCCFG();
        
        assertEquals(ccfg, result);
    }
    
    @Test
    public void testGetCCFGI134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "I134");
        CCFGEntry node = ccfg.getEntryNode();
        CCFG result = node.getCCFG();
        
        assertEquals(ccfg, result);
    }
    
    @Test
    public void testGetCCFGTest135() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test135");
        CCFGEntry node = ccfg.getEntryNode();
        CCFG result = node.getCCFG();
        
        assertEquals(ccfg, result);
    }
    
    @Test
    public void testGetCCFGS135() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "S135");
        CCFGEntry node = ccfg.getEntryNode();
        CCFG result = node.getCCFG();
        
        assertEquals(ccfg, result);
    }
    
    @Test
    public void testGetCCFGTest139() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test139");
        CCFGEntry node = ccfg.getEntryNode();
        CCFG result = node.getCCFG();
        
        assertEquals(ccfg, result);
    }
    
    @Test
    public void testGetCCFGPriceCode() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "PriceCode");
        CCFGEntry node = ccfg.getEntryNode();
        CCFG result = node.getCCFG();
        
        assertEquals(ccfg, result);
    }
    
    @Test
    public void testGetCCFGCustomer() {
        CCFG ccfg = CFGTestUtil.createCCFG(VideoStoreProject, "org.jtool.videostore.after.Customer");
        CCFGEntry node = ccfg.getEntryNode();
        CCFG result = node.getCCFG();
        
        assertEquals(ccfg, result);
    }
    
    @Test
    public void testGetCCFGRental() {
        CCFG ccfg = CFGTestUtil.createCCFG(VideoStoreProject, "org.jtool.videostore.after.Rental");
        CCFGEntry node = ccfg.getEntryNode();
        CCFG result = node.getCCFG();
        
        assertEquals(ccfg, result);
    }
    
    @Test
    public void testGetCCFGTest28() {
        CCFG ccfg = CFGTestUtil.createCCFG(SimpleProject, "Test28");
        CCFGEntry node = ccfg.getEntryNode();
        QualifiedName result = node.getQualifiedName();
        
        assertEquals("Test28", result.fqn());
    }
    
    @Test
    public void testGetCCFGTest28$MyActionListener() {
        CCFG ccfg = CFGTestUtil.createCCFG(SimpleProject, "Test28.MyActionListener");
        CCFGEntry node = ccfg.getEntryNode();
        CCFG result = node.getCCFG();
        
        assertEquals(ccfg, result);
    }
    
    @Test
    public void testGetCCFGTest29() {
        CCFG ccfg = CFGTestUtil.createCCFG(SimpleProject, "Test29");
        CCFGEntry node = ccfg.getEntryNode();
        CCFG result = node.getCCFG();
        
        assertEquals(ccfg, result);
    }
    
    @Test
    public void testGetCCFGTest29$1MyActionListener() {
        CCFG ccfg = CFGTestUtil.createCCFG(SimpleProject, "Test29$1MyActionListener");
        CCFGEntry node = ccfg.getEntryNode();
        CCFG result = node.getCCFG();
        
        assertEquals(ccfg, result);
    }
    
    @Test
    public void testGetCCFGTest30() {
        CCFG ccfg = CFGTestUtil.createCCFG(SimpleProject, "Test30");
        CCFGEntry node = ccfg.getEntryNode();
        CCFG result = node.getCCFG();
        
        assertEquals(ccfg, result);
    }
    
    @Test
    public void testGetCCFGTest30$1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SimpleProject, "Test30$1");
        CCFGEntry node = ccfg.getEntryNode();
        CCFG result = node.getCCFG();
        
        assertEquals(ccfg, result);
    }
    
    @Test
    public void testGetMethodsTest101() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test101");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getMethods();
        
        assertEquals("Test101#Test101( );Test101#m( )", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetMethodsTest119() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test119");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getMethods();
        
        assertEquals("Test119#Test119( );Test119#getP( );Test119#m( )", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetMethodsA119() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "A119");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getMethods();
        
        assertEquals("A119#A119( );A119#getX( );A119#setX( int )", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetMethodsTest120() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test120");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getMethods();
        
        assertEquals("Test120#Test120( );"
                   + "Test120#m( );"
                   + "Test120#m0( int int );"
                   + "Test120#m1( int int );"
                   + "Test120#m2( int int );"
                   + "Test120#m3( int int )",
            TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetMethodsA120() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "A120");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getMethods();
        
        assertEquals("A120#A120( )", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetMethodsTest127() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test127");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getMethods();
        
        assertEquals("Test127#Test127( );Test127#m( )", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetMethodsA127() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "A127");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getMethods();
        
        assertEquals("A127#A127( );A127#getY( );A127#setY( int )", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetMethodsTest134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test134");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getMethods();
        
        assertEquals("Test134#Test134( );"
                   + "Test134#a1( );"
                   + "Test134#a2( );"
                   + "Test134#dec( int );"
                   + "Test134#getP( );"
                   + "Test134#getPQ( );"
                   + "Test134#getQ( );"
                   + "Test134#inc( int );"
                   + "Test134#m( );"
                   + "Test134#m2( );"
                   + "Test134#m3( );"
                   + "Test134#n1( P134 int );"
                   + "Test134#n2( P134 int )",
            TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetMethodsP134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "P134");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getMethods();
        
        assertEquals("P134#P134( );P134#f( int )", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetMethodsQ134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Q134");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getMethods();
        
        assertEquals("Q134#Q134( );Q134#f( int )", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetMethodsR134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "R134");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getMethods();
        
        assertEquals("R134#R134( );R134#f( int )", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetMethodsI134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "I134");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getMethods();
        
        assertEquals("I134#f( int )", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetMethodsTest135() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test135");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getMethods();
        
        assertEquals("Test135#Test135( );Test135#m( )", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetMethodsS135() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "S135");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getMethods();
        
        assertEquals("S135#S135( );S135#get2( java.lang.String );S135#set2( java.lang.String java.lang.String )",
                TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetMethodsTest136() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test136");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getMethods();
        
        assertEquals("Test136#Test136( );Test136#m( )", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetMethodsS136() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "S136");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getMethods();
        
        assertEquals("S136#S136( );S136#get( java.lang.String );S136#set( java.lang.String java.lang.String )",
                TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetMethodsTest139() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test139");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getMethods();
        
        assertEquals("Test139#Test139( );Test139#m( )", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetMethodsPriceCode() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "PriceCode");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getMethods();
        
        assertEquals("PriceCode#PriceCode( int );PriceCode#getPriceCode( )", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetMethodsCustomer() {
        CCFG ccfg = CFGTestUtil.createCCFG(VideoStoreProject, "org.jtool.videostore.after.Customer");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getMethods();
        
        assertEquals("org.jtool.videostore.after.Customer#Customer( java.lang.String );"
                   + "org.jtool.videostore.after.Customer#addRental( org.jtool.videostore.after.Rental );"
                   + "org.jtool.videostore.after.Customer#getName( );"
                   + "org.jtool.videostore.after.Customer#getTotalCharge( );"
                   + "org.jtool.videostore.after.Customer#getTotalFrequentRenterPoints( );"
                   + "org.jtool.videostore.after.Customer#statement( )",
            TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetMethodsRental() {
        CCFG ccfg = CFGTestUtil.createCCFG(VideoStoreProject, "org.jtool.videostore.after.Rental");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getMethods();
        
        assertEquals("org.jtool.videostore.after.Rental#Rental( org.jtool.videostore.after.Movie int );"
                   + "org.jtool.videostore.after.Rental#getCharge( );"
                   + "org.jtool.videostore.after.Rental#getDaysRented( );"
                   + "org.jtool.videostore.after.Rental#getFrequentRenterPoints( );"
                   + "org.jtool.videostore.after.Rental#getMovie( )",
            TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetMethodsTest28() {
        CCFG ccfg = CFGTestUtil.createCCFG(SimpleProject, "Test28");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getMethods();
        
        assertEquals("Test28#Test28( );Test28#m( )", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetMethodsTest28$MyActionListener() {
        CCFG ccfg = CFGTestUtil.createCCFG(SimpleProject, "Test28.MyActionListener");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getMethods();
        
        assertEquals("Test28.MyActionListener#MyActionListener( );"
                   + "Test28.MyActionListener#actionPerformed( java.awt.event.ActionEvent )",
            TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetMethodsTest29() {
        CCFG ccfg = CFGTestUtil.createCCFG(SimpleProject, "Test29");
        CCFGEntry node = ccfg.getEntryNode();
            Set<CFG> result = node.getMethods();
        
        assertEquals("Test29#Test29( );Test29#m( )", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetMethodsTest29$1MyActionListener() {
        CCFG ccfg = CFGTestUtil.createCCFG(SimpleProject, "Test29$1MyActionListener");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getMethods();
        
        assertEquals("Test29$1MyActionListener#MyActionListener( );"
                   + "Test29$1MyActionListener#actionPerformed( java.awt.event.ActionEvent )",
            TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetMethodsTest30() {
        CCFG ccfg = CFGTestUtil.createCCFG(SimpleProject, "Test30");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getMethods();
        
        assertEquals("Test30#Test30( );Test30#m( )", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testMethodsTest30$1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SimpleProject, "Test30$1");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getMethods();
        
        assertEquals("Test30$1#actionPerformed( java.awt.event.ActionEvent )",
                TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetFieldsTest101() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test101");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getFields();
        
        assertEquals("Test101#p", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetFieldsTest119() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test119");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getFields();
        
        assertEquals("Test119#p", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetFieldsA119() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "A119");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getFields();
        
        assertEquals("A119#x;A119#y", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetFieldsTest120() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test120");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getFields();
        
        assertEquals("", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetFieldsA120() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "A120");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getFields();
        
        assertEquals("A120#x;A120#y", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetFieldsTest127() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test127");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getFields();
        
        assertEquals("Test127#p", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetFieldsA127() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "A127");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getFields();
        
        assertEquals("A127#y;A127#z", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetFieldsTest134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test134");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getFields();
        
        assertEquals("", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetFieldsP134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "P134");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getFields();
        
        assertEquals("", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetFieldsQ134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Q134");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getFields();
        
        assertEquals("", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetFieldsR134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "R134");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getFields();
        
        assertEquals("", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetFieldsI134() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "I134");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getFields();
        
        assertEquals("", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetFieldsTest135() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test135");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getFields();
        
        assertEquals("Test135#s1;Test135#s2", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetFieldsS135() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "S135");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getFields();
        
        assertEquals("S135#key;S135#value", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetFieldsTest136() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test136");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getFields();
        
        assertEquals("Test136#s1;Test136#s2", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetFieldsS136() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "S136");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getFields();
        
        assertEquals("S136#map", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetFieldsTest139() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test139");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getFields();
        
        assertEquals("", TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetFieldsPriceCode() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "PriceCode");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getFields();
        
        assertEquals("PriceCode#CHILDRENS;"
                   + "PriceCode#NEW_RELEASE;"
                   + "PriceCode#REGULAR;"
                   + "PriceCode#priceCode",
            TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetFieldsCustomer() {
        CCFG ccfg = CFGTestUtil.createCCFG(VideoStoreProject, "org.jtool.videostore.after.Customer");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getFields();
        
        assertEquals("org.jtool.videostore.after.Customer#name;"
                   + "org.jtool.videostore.after.Customer#rentals",
            TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetFieldsRental() {
        CCFG ccfg = CFGTestUtil.createCCFG(VideoStoreProject, "org.jtool.videostore.after.Rental");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CFG> result = node.getFields();
        
        assertEquals("org.jtool.videostore.after.Rental#daysRented;"
                   + "org.jtool.videostore.after.Rental#movie",
            TestUtil.asSortedStrOfCFG(result));
    }
    
    @Test
    public void testGetClassesTest101() {
        CCFG ccfg = CFGTestUtil.createCCFG(SliceProject, "Test101");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CCFG> result = node.getClasses();
        
        assertEquals("", TestUtil.asSortedStrOfCCFG(result));
    }
    
    @Test
    public void testGetClassesTest28() {
        CCFG ccfg = CFGTestUtil.createCCFG(SimpleProject, "Test28");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CCFG> result = node.getClasses();
        
        assertEquals("Test28.MyActionListener", TestUtil.asSortedStrOfCCFG(result));
    }
    
    @Test
    public void testGetClassesTest28$MyActionListener() {
        CCFG ccfg = CFGTestUtil.createCCFG(SimpleProject, "Test28.MyActionListener");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CCFG> result = node.getClasses();
        
        assertEquals("", TestUtil.asSortedStrOfCCFG(result));
    }
    
    @Test
    public void testGetClassesTest29() {
        CCFG ccfg = CFGTestUtil.createCCFG(SimpleProject, "Test29");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CCFG> result = node.getClasses();
        
        assertEquals("Test29$1MyActionListener", TestUtil.asSortedStrOfCCFG(result));
    }
    
    @Test
    public void testGetClassesTest29$1MyActionListener() {
        CCFG ccfg = CFGTestUtil.createCCFG(SimpleProject, "Test29$1MyActionListener");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CCFG> result = node.getClasses();
        
        assertEquals("", TestUtil.asSortedStrOfCCFG(result));
    }
    
    @Test
    public void testGetClassesTest30() {
        CCFG ccfg = CFGTestUtil.createCCFG(SimpleProject, "Test30");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CCFG> result = node.getClasses();
        
        assertEquals("Test30$1", TestUtil.asSortedStrOfCCFG(result));
    }
    
    @Test
    public void testGetClassesTest30$1() {
        CCFG ccfg = CFGTestUtil.createCCFG(SimpleProject, "Test30$1");
        CCFGEntry node = ccfg.getEntryNode();
        Set<CCFG> result = node.getClasses();
        
        assertEquals("", TestUtil.asSortedStrOfCCFG(result));
    }
}
