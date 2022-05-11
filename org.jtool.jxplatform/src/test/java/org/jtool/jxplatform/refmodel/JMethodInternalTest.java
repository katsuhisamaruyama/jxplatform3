/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.refmodel;

import org.jtool.jxplatform.util.TestUtil;
import org.jtool.srcmodel.JavaProject;
import java.util.List;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        project = RefModelTestUtil.createProjectFromSourceWithoutLibCache("VideoStore", "/lib/*", "");
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
    public void testInstanceOf1() {
        assertTrue(customerMethod instanceof JMethodInternal);
    }
    
    @Test
    public void testInstanceOf2() {
        assertTrue(addRentalMethod instanceof JMethodInternal);
    }
    
    @Test
    public void testInstanceOf3() {
        assertTrue(statementMethod instanceof JMethodInternal);
    }
    
    @Test
    public void testInstanceOf4() {
        assertTrue(getChargeMethodPrice instanceof JMethodInternal);
    }
    
    @Test
    public void testInstanceOf5() {
        assertTrue(getFrequentRenterPointsMethodPrice instanceof JMethodInternal);
    }
    
    @Test
    public void testInstanceOf6() {
        assertTrue(getChargeMethodNewReleasePrice instanceof JMethodInternal);
    }
    
    @Test
    public void testInstanceOf7() {
        assertTrue(getFrequentRenterPointsMethodNewReleasePrice instanceof JMethodInternal);
    }
    
    @Test
    public void testGetQualifiedName1() {
        assertEquals("org.jtool.videostore.after.Customer#Customer( java.lang.String )",
                customerMethod.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName2() {
        assertEquals("org.jtool.videostore.after.Customer#addRental( org.jtool.videostore.after.Rental )",
                addRentalMethod.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName3() {
        assertEquals("org.jtool.videostore.after.Customer#statement( )",
                statementMethod.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName4() {
        assertEquals("org.jtool.videostore.after.Price#getCharge( int )",
                getChargeMethodPrice.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName5() {
        assertEquals("org.jtool.videostore.after.Price#getFrequentRenterPoints( int )",
                getFrequentRenterPointsMethodPrice.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName6() {
        assertEquals("org.jtool.videostore.after.NewReleasePrice#getCharge( int )",
                getChargeMethodNewReleasePrice.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName7() {
        assertEquals("org.jtool.videostore.after.NewReleasePrice#getFrequentRenterPoints( int )",
                getFrequentRenterPointsMethodNewReleasePrice.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetDeclaringClass1() {
        assertEquals("org.jtool.videostore.after.Customer",
                customerMethod.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testGetDeclaringClass2() {
        assertEquals("org.jtool.videostore.after.Customer",
                addRentalMethod.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testGetDeclaringClass3() {
        assertEquals("org.jtool.videostore.after.Customer",
                statementMethod.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testGetDeclaringClass4() {
        assertEquals("org.jtool.videostore.after.Price",
                getChargeMethodPrice.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testGetDeclaringClass5() {
        assertEquals("org.jtool.videostore.after.Price",
                getFrequentRenterPointsMethodPrice.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testGetDeclaringClass6() {
        assertEquals("org.jtool.videostore.after.NewReleasePrice",
                getChargeMethodNewReleasePrice.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testGetDeclaringClass7() {
        assertEquals("org.jtool.videostore.after.NewReleasePrice",
                getFrequentRenterPointsMethodNewReleasePrice.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testIsInProject1() {
        assertTrue(customerMethod.isInProject());
    }
    
    @Test
    public void testIsInProject2() {
        assertTrue(addRentalMethod.isInProject());
    }
    
    @Test
    public void testIsInProject3() {
        assertTrue(statementMethod.isInProject());
    }
    
    @Test
    public void testIsInProject4() {
        assertTrue(getChargeMethodPrice.isInProject());
    }
    
    @Test
    public void testIsInProject5() {
        assertTrue(getFrequentRenterPointsMethodPrice.isInProject());
    }
    
    @Test
    public void testIsInProject6() {
        assertTrue(getChargeMethodNewReleasePrice.isInProject());
    }
    
    @Test
    public void testIsInProject7() {
        assertTrue(getFrequentRenterPointsMethodNewReleasePrice.isInProject());
    }
    
    @Test
    public void testGetDefFields1() {
        List<String> result = TestUtil.asSortedList(customerMethod.getDefFields().stream()
                .map(o -> o.getQualifiedName()));
        
        assertEquals(1, result.size());
        assertEquals("org.jtool.videostore.after.Customer%name%this.name", result.get(0));
    }
    
    @Test
    public void testGetDefFields2() {
        List<String> result = TestUtil.asSortedList(addRentalMethod.getDefFields().stream()
                .map(o -> o.getQualifiedName()));
        
        assertEquals(2, result.size());
        assertEquals("java.util.ArrayList%size%this.rentals.size", result.get(0));
        assertEquals("org.jtool.videostore.after.Customer%rentals%this.rentals", result.get(1));
    }
    
    @Test
    public void testGetDefFields3() {
        assertEquals(0, statementMethod.getDefFields().size());
    }
    
    @Test
    public void testGetDefFields4() {
        assertEquals(0, getChargeMethodPrice.getDefFields().size());
    }
    
    @Test
    public void testGetDefFields5() {
        assertEquals(0, getFrequentRenterPointsMethodPrice.getDefFields().size());
    }
    
    @Test
    public void testGetDefFields6() {
        assertEquals(0, getChargeMethodNewReleasePrice.getDefFields().size());
    }
    
    @Test
    public void testGetDefFields7() {
        assertEquals(0, getFrequentRenterPointsMethodNewReleasePrice.getDefFields().size());
    }
    
    @Test
    public void testGetUseFields1() {
        List<String> result = TestUtil.asSortedList(customerMethod.getUseFields().stream()
                .map(o -> o.getQualifiedName()));
        
        assertEquals(1, result.size());
        assertEquals("org.jtool.videostore.after.Customer%name%this.name", result.get(0));
    }
    
    @Test
    public void testGetUseFields2() {
        List<String> result = TestUtil.asSortedList(addRentalMethod.getUseFields().stream()
                .map(o -> o.getQualifiedName()));
        
        assertEquals(3, result.size());
        assertEquals("java.util.ArrayList%elementData%this.rentals.elementData", result.get(0));
        assertEquals("java.util.ArrayList%size%this.rentals.size", result.get(1));
        assertEquals("org.jtool.videostore.after.Customer%rentals%this.rentals", result.get(2));
    }
    
    @Test
    public void testGetUseFields3() {
        List<String> result = TestUtil.asSortedList(statementMethod.getUseFields().stream()
                .map(o -> o.getQualifiedName()));
        
        assertEquals(3, result.size());
        assertEquals("java.lang.String%COMPACT_STRINGS%this.COMPACT_STRINGS", result.get(0));
        assertEquals("org.jtool.videostore.after.Customer%name%this.name", result.get(1));
        assertEquals("org.jtool.videostore.after.Customer%rentals%this.rentals", result.get(2));
    }
    
    @Test
    public void testGetUseFields4() {
        assertEquals(0, getChargeMethodPrice.getUseFields().size());
    }
    
    @Test
    public void testGetUseFields5() {
        assertEquals(0, getFrequentRenterPointsMethodPrice.getUseFields().size());
    }
    
    @Test
    public void testGetUseFields6() {
        assertEquals(0, getChargeMethodNewReleasePrice.getUseFields().size());
    }
    
    @Test
    public void testGetUseFields7() {
        assertEquals(0, getFrequentRenterPointsMethodNewReleasePrice.getUseFields().size());
    }
    
    @Test
    public void testGetAccessedMethods1() {
        assertEquals(0, customerMethod.getAccessedMethods().size());
    }
    
    @Test
    public void testGetAccessedMethods2() {
        List<String> result = TestUtil.asSortedList(addRentalMethod.getAccessedMethods().stream()
                .map(o -> o.getQualifiedName()));
        
        assertEquals(1, result.size());
        assertEquals("java.util.List#add( java.lang.Object )", result.get(0));
    }
    
    @Test
    public void testGetAccessedMethods3() {
        List<String> result = TestUtil.asSortedList(statementMethod.getAccessedMethods().stream()
                .map(o -> o.getQualifiedName()));
        
        assertEquals(8, result.size());
        assertEquals("java.lang.String#valueOf( double )", result.get(0));
        assertEquals("java.lang.String#valueOf( int )", result.get(1));
        assertEquals("org.jtool.videostore.after.Customer#getName( )", result.get(2));
        assertEquals("org.jtool.videostore.after.Customer#getTotalCharge( )", result.get(3));
        assertEquals("org.jtool.videostore.after.Customer#getTotalFrequentRenterPoints( )", result.get(4));
        assertEquals("org.jtool.videostore.after.Movie#getTitle( )", result.get(5));
        assertEquals("org.jtool.videostore.after.Rental#getCharge( )", result.get(6));
        assertEquals("org.jtool.videostore.after.Rental#getMovie( )", result.get(7));
    }
    
    @Test
    public void testGetAccessedMethods4() {
        assertEquals(0, getChargeMethodPrice.getAccessedMethods().size());
    }
    
    @Test
    public void testGetAccessedMethods5() {
        assertEquals(0, getFrequentRenterPointsMethodPrice.getAccessedMethods().size());
    }
    
    @Test
    public void testGetAccessedMethods6() {
        assertEquals(0, getChargeMethodNewReleasePrice.getAccessedMethods().size());
    }
    
    @Test
    public void testGetAccessedMethods7() {
        assertEquals(0, getFrequentRenterPointsMethodNewReleasePrice.getAccessedMethods().size());
    }
}
