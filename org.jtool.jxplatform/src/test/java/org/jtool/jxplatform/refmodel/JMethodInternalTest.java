/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.refmodel;

import org.jtool.jxplatform.refmodel.BytecodeClassStore;
import org.jtool.jxplatform.refmodel.JClass;
import org.jtool.jxplatform.refmodel.JMethod;
import org.jtool.jxplatform.refmodel.JMethodInternal;
import org.jtool.jxplatform.util.TestUtil;
import org.jtool.srcmodel.JavaProject;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class JMethodInternalTest {
    
    private static JavaProject project;
    private static BytecodeClassStore bcStore;
    
    private static JMethod customerMethod;
    private static JMethod addRentalMethod;
    private static JMethod statementMethod;
    private static JMethod getChargeMethodPrice;
    private static JMethod getFrequentRenterPointsMethodPrice;
    private static JMethod getChargeMethodNewReleasePrice;
    private static JMethod getFrequentRenterPointsMethodNewReleasePrice;
    
    @BeforeClass
    public static void setUp() {
        String name = "VideoStore";
        String target = TestUtil.getTarget(name);
        String classpath = target + "/lib/*";
        
        project = RefModelTestUtil.createProjectFromSourceWithoutLibCache(target, classpath);
        bcStore = project.getCFGStore().getBCStore();
        
        JClass customerClass = bcStore.getJClass("org.jtool.videostore.after.Customer");
        customerMethod = customerClass.getMethod("Customer( java.lang.String )");
        customerMethod.findDefUseFields();
        addRentalMethod = customerClass.getMethod("addRental( org.jtool.videostore.after.Rental )");
        addRentalMethod.findDefUseFields();
        statementMethod = customerClass.getMethod("statement( )");
        statementMethod.findDefUseFields();
        
        JClass priceClass = bcStore.getJClass("org.jtool.videostore.after.Price");
        getChargeMethodPrice = priceClass.getMethod("getCharge( int )");
        getChargeMethodPrice.findDefUseFields();
        getFrequentRenterPointsMethodPrice = priceClass.getMethod("getFrequentRenterPoints( int )");
        getFrequentRenterPointsMethodPrice.findDefUseFields();
        
        JClass newReleasePriceClass = bcStore.getJClass("org.jtool.videostore.after.NewReleasePrice");
        getChargeMethodNewReleasePrice = newReleasePriceClass.getMethod("getCharge( int )");
        getChargeMethodNewReleasePrice.findDefUseFields();
        getFrequentRenterPointsMethodNewReleasePrice = newReleasePriceClass.getMethod("getFrequentRenterPoints( int )");
        getFrequentRenterPointsMethodNewReleasePrice.findDefUseFields();
    }
    
    @AfterClass
    public static void tearDown() {
        project.getModelBuilder().unbuild();
    }
    
    @Test
    public void testInstanceOf() {
        assertTrue(customerMethod instanceof JMethodInternal);
        
        assertTrue(addRentalMethod instanceof JMethodInternal);
        
        assertTrue(statementMethod instanceof JMethodInternal);
        
        assertTrue(getChargeMethodPrice instanceof JMethodInternal);
        
        assertTrue(getFrequentRenterPointsMethodPrice instanceof JMethodInternal);
        
        assertTrue(getChargeMethodNewReleasePrice instanceof JMethodInternal);
        
        assertTrue(getFrequentRenterPointsMethodNewReleasePrice instanceof JMethodInternal);
    }
    
    @Test
    public void testGetQualifiedName() {
        assertEquals("org.jtool.videostore.after.Customer#Customer( java.lang.String )",
                customerMethod.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Customer#addRental( org.jtool.videostore.after.Rental )",
                addRentalMethod.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Customer#statement( )",
                statementMethod.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Price#getCharge( int )",
                getChargeMethodPrice.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Price#getFrequentRenterPoints( int )",
                getFrequentRenterPointsMethodPrice.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.NewReleasePrice#getCharge( int )",
                getChargeMethodNewReleasePrice.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.NewReleasePrice#getFrequentRenterPoints( int )",
                getFrequentRenterPointsMethodNewReleasePrice.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetDeclaringClass() {
        assertEquals("org.jtool.videostore.after.Customer",
                customerMethod.getDeclaringClass().getClassName());
        
        assertEquals("org.jtool.videostore.after.Customer",
                addRentalMethod.getDeclaringClass().getClassName());
        
        assertEquals("org.jtool.videostore.after.Customer",
                statementMethod.getDeclaringClass().getClassName());
        
        assertEquals("org.jtool.videostore.after.Price",
                getChargeMethodPrice.getDeclaringClass().getClassName());
        
        assertEquals("org.jtool.videostore.after.Price",
                getFrequentRenterPointsMethodPrice.getDeclaringClass().getClassName());
        
        assertEquals("org.jtool.videostore.after.NewReleasePrice",
                getChargeMethodNewReleasePrice.getDeclaringClass().getClassName());
        
        assertEquals("org.jtool.videostore.after.NewReleasePrice",
                getFrequentRenterPointsMethodNewReleasePrice.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testIsInProject() {
        assertTrue(customerMethod.isInProject());
        
        assertTrue(addRentalMethod.isInProject());
        
        assertTrue(statementMethod.isInProject());
        
        assertTrue(getChargeMethodPrice.isInProject());
        
        assertTrue(getFrequentRenterPointsMethodPrice.isInProject());
        
        assertTrue(getChargeMethodNewReleasePrice.isInProject());
        
        assertTrue(getFrequentRenterPointsMethodNewReleasePrice.isInProject());
    }
    
    @Test
    public void testGetDefFields() {
        List<String> cresult = TestUtil.asSortedList(customerMethod.getDefFields().stream().map(o -> o.getQualifiedName()));
        assertEquals(1, cresult.size());
        assertEquals("org.jtool.videostore.after.Customer%name%this.name", cresult.get(0));
        
        assertEquals(0, addRentalMethod.getDefFields().size());
        
        assertEquals(0, statementMethod.getDefFields().size());
        
        assertEquals(0, getChargeMethodPrice.getDefFields().size());
        
        assertEquals(0, getFrequentRenterPointsMethodPrice.getDefFields().size());
        
        assertEquals(0, getChargeMethodNewReleasePrice.getDefFields().size());
        
        assertEquals(0, getFrequentRenterPointsMethodNewReleasePrice.getDefFields().size());
    }
    
    @Test
    public void testGetUseFields() {
        assertEquals(0, customerMethod.getUseFields().size());
        
        List<String> aresult = TestUtil.asSortedList(addRentalMethod.getUseFields().stream().map(o -> o.getQualifiedName()));
        assertEquals(1, aresult.size());
        assertEquals("org.jtool.videostore.after.Customer%rentals%this.rentals", aresult.get(0));
        
        List<String> sresult = TestUtil.asSortedList(statementMethod.getUseFields().stream().map(o -> o.getQualifiedName()));
        assertEquals(4, sresult.size());
        assertEquals("java.lang.String%COMPACT_STRINGS%java.lang.String.COMPACT_STRINGS", sresult.get(0));
        assertEquals("java.lang.String%COMPACT_STRINGS%this.COMPACT_STRINGS", sresult.get(1));
        assertEquals("org.jtool.videostore.after.Customer%name%this.name", sresult.get(2));
        assertEquals("org.jtool.videostore.after.Customer%rentals%this.rentals", sresult.get(3));
        
        assertEquals(0, getChargeMethodPrice.getUseFields().size());
        
        assertEquals(0, getFrequentRenterPointsMethodPrice.getUseFields().size());
        
        assertEquals(0, getChargeMethodNewReleasePrice.getUseFields().size());
        
        assertEquals(0, getFrequentRenterPointsMethodNewReleasePrice.getUseFields().size());
    }
    
    @Test
    public void testGetAccessedMethods() {
        assertEquals(0, customerMethod.getAccessedMethods().size());
        
        List<String> aresult = TestUtil.asSortedList(addRentalMethod.getAccessedMethods().stream().map(o -> o.getQualifiedName()));
        assertEquals(1, aresult.size());
        assertEquals("java.util.List#add( java.lang.Object )", aresult.get(0));
        
        List<String> sresult = TestUtil.asSortedList(statementMethod.getAccessedMethods().stream().map(o -> o.getQualifiedName()));
        assertEquals(8, sresult.size());
        assertEquals("java.lang.String#valueOf( double )", sresult.get(0));
        assertEquals("java.lang.String#valueOf( int )", sresult.get(1));
        assertEquals("org.jtool.videostore.after.Customer#getName( )", sresult.get(2));
        assertEquals("org.jtool.videostore.after.Customer#getTotalCharge( )", sresult.get(3));
        assertEquals("org.jtool.videostore.after.Customer#getTotalFrequentRenterPoints( )", sresult.get(4));
        assertEquals("org.jtool.videostore.after.Movie#getTitle( )", sresult.get(5));
        assertEquals("org.jtool.videostore.after.Rental#getCharge( )", sresult.get(6));
        assertEquals("org.jtool.videostore.after.Rental#getMovie( )", sresult.get(7));
        
        assertEquals(0, getChargeMethodPrice.getAccessedMethods().size());
        
        assertEquals(0, getFrequentRenterPointsMethodPrice.getAccessedMethods().size());
        
        assertEquals(0, getChargeMethodNewReleasePrice.getAccessedMethods().size());
        
        assertEquals(0, getFrequentRenterPointsMethodNewReleasePrice.getAccessedMethods().size());
    }
}
