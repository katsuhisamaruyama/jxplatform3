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
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class JFieldInternalTest {
    
    private static JavaProject project;
    private static BytecodeClassStore bcStore;
    
    private static JMethod customerMethodInCustomer;
    private static JMethod addRentalMethodInCustomer;
    private static JMethod statementMethodInCustomer;
    
    private static JMethod getChargeMethodInPrice;
    private static JMethod getFrequentRenterPointsMethodInPrice;
    
    private static JMethod getChargeMethodInNewReleasePrice;
    private static JMethod getFrequentRenterPointsInNewReleasePrice;
    
    @BeforeClass
    public static void setUp() {
        String name = "VideoStore";
        String target = TestUtil.getTarget(name);
        String classpath = target + "/lib/*";
        
        project = RefModelTestUtil.createProjectFromSourceWithoutLibCache(target, classpath);
        bcStore = project.getCFGStore().getBCStore();
        
        JClass customerClass = bcStore.getJClass("org.jtool.videostore.after.Customer");
        customerMethodInCustomer = customerClass.getMethod("Customer( String )");
        addRentalMethodInCustomer = customerClass.getMethod("addRental( org.jtool.videostore.after.Rental)");
        statementMethodInCustomer = customerClass.getMethod("statement( )");
        
        JClass priceClass = bcStore.getJClass("org.jtool.videostore.after.Price");
        getChargeMethodInPrice = priceClass.getMethod("getCharge( int )");
        getFrequentRenterPointsMethodInPrice = priceClass.getMethod("getFrequentRenterPoints( int )");
        
        JClass newReleasePriceClass = bcStore.getJClass("org.jtool.videostore.after.NewReleasePrice");
        getChargeMethodInNewReleasePrice = newReleasePriceClass.getMethod("getCharge( int )");
        getFrequentRenterPointsInNewReleasePrice = newReleasePriceClass.getMethod("getFrequentRenterPoints( int )");
    }
    
    @AfterClass
    public static void tearDown() {
        project.getModelBuilder().unbuild();
    }
    
    @Test
    public void testInstanceOf() {
        assertTrue(customerMethodInCustomer instanceof JMethodInternal);
        
        assertTrue(addRentalMethodInCustomer instanceof JMethodInternal);
        
        assertTrue(statementMethodInCustomer instanceof JMethodInternal);
        
        assertTrue(getChargeMethodInPrice instanceof JMethodInternal);
        
        assertTrue(getFrequentRenterPointsMethodInPrice instanceof JMethodInternal);
        
        assertTrue(getChargeMethodInNewReleasePrice instanceof JMethodInternal);
        
        assertTrue(getFrequentRenterPointsInNewReleasePrice instanceof JMethodInternal);
    }
    
    @Test
    public void testGetQualifiedName() {
        assertEquals("org.jtool.videostore.after.Customer", customerMethodInCustomer.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Rental", addRentalMethodInCustomer.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Movie", statementMethodInCustomer.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Price", getChargeMethodInPrice.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.RegularPrice", getFrequentRenterPointsMethodInPrice.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.NewReleasePrice", getChargeMethodInNewReleasePrice.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.ChildrensPrice", getFrequentRenterPointsInNewReleasePrice.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetDeclaringClass() {
        assertEquals("Customer", customerMethodInCustomer.getDeclaringClass());
        
        assertEquals("Rental", addRentalMethodInCustomer.getDeclaringClass());
        
        assertEquals("Movie", statementMethodInCustomer.getDeclaringClass());
        
        assertEquals("Price", getChargeMethodInPrice.getDeclaringClass());
        
        assertEquals("RegularPrice", getFrequentRenterPointsMethodInPrice.getDeclaringClass());
        
        assertEquals("NewReleasePrice", getChargeMethodInNewReleasePrice.getDeclaringClass());
        
        assertEquals("ChildrensPrice", getFrequentRenterPointsInNewReleasePrice.getDeclaringClass());
    }
    
    @Test
    public void testIsInProject() {
        assertTrue(customerMethodInCustomer.isInProject());
        
        assertTrue(addRentalMethodInCustomer.isInProject());
        
        assertTrue(statementMethodInCustomer.isInProject());
        
        assertTrue(getChargeMethodInPrice.isInProject());
        
        assertTrue(getFrequentRenterPointsMethodInPrice.isInProject());
        
        assertTrue(getChargeMethodInNewReleasePrice.isInProject());
        
        assertTrue(getFrequentRenterPointsInNewReleasePrice.isInProject());
    }
    
    @Test
    public void testGetDefFields() {
        assertEquals("Customer", customerMethodInCustomer.getDefFields().size());
        
        assertEquals("Rental", addRentalMethodInCustomer.getDefFields().size());
        
        assertEquals("Movie", statementMethodInCustomer.getDefFields().size());
        
        assertEquals("Price", getChargeMethodInPrice.getDefFields().size());
        
        assertEquals("RegularPrice", getFrequentRenterPointsMethodInPrice.getDefFields().size());
        
        assertEquals("NewReleasePrice", getChargeMethodInNewReleasePrice.getDefFields().size());
        
        assertEquals("ChildrensPrice", getFrequentRenterPointsInNewReleasePrice.getDefFields().size());
    }
    
    @Test
    public void testGetUseFields() {
        assertEquals("Customer", customerMethodInCustomer.getDeclaringClass());
        
        assertEquals("Rental", addRentalMethodInCustomer.getUseFields().size());
        
        assertEquals("Movie", statementMethodInCustomer.getUseFields().size());
        
        assertEquals("Price", getChargeMethodInPrice.getUseFields().size());
        
        assertEquals("RegularPrice", getFrequentRenterPointsMethodInPrice.getUseFields().size());
        
        assertEquals("NewReleasePrice", getChargeMethodInNewReleasePrice.getUseFields().size());
        
        assertEquals("ChildrensPrice", getFrequentRenterPointsInNewReleasePrice.getUseFields().size());
    }
    
    @Test
    public void tstGetAccessedMethods() {
        assertEquals("Customer", customerMethodInCustomer.getAccessedMethods().size());
        
        assertEquals("Rental", addRentalMethodInCustomer.getAccessedMethods().size());
        
        assertEquals("Movie", statementMethodInCustomer.getAccessedMethods().size());
        
        assertEquals("Price", getChargeMethodInPrice.getAccessedMethods().size());
        
        assertEquals("RegularPrice", getFrequentRenterPointsMethodInPrice.getAccessedMethods().size());
        
        assertEquals("NewReleasePrice", getChargeMethodInNewReleasePrice.getAccessedMethods().size());
        
        assertEquals("ChildrensPrice", getFrequentRenterPointsInNewReleasePrice.getAccessedMethods().size());
    }
}
