/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.cfg.internal.refmodel;

import org.jtool.jxplatform.builder.BuilderTestUtil;
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
        BuilderTestUtil.clearProject();
        
        project = RefModelTestUtil.createProjectFromSourceWithoutLibCache("VideoStore", "/lib/*", "");
        bcStore = project.getCFGStore().getBCStore();
        
        JClass customerClass = bcStore.getJClass("org.jtool.videostore.after.Customer");
        customerMethod = customerClass.getMethod("Customer( java.lang.String )");
        customerMethod.findDefUseFields("customer$0.!Customer( java.lang.String )", "");
        addRentalMethod = customerClass.getMethod("addRental( org.jtool.videostore.after.Rental )");
        addRentalMethod.findDefUseFields("order$0.!addRental( org.jtool.videostore.after.Rental )", "");
        statementMethod = customerClass.getMethod("statement( )");
        statementMethod.findDefUseFields("customer$0.!statement( )", "");
        
        JClass priceClass = bcStore.getJClass("org.jtool.videostore.after.Price");
        getChargeMethodPrice = priceClass.getMethod("getCharge( int )");
        getChargeMethodPrice.findDefUseFields("price$0.!getCharge( int )", "");
        getFrequentRenterPointsMethodPrice = priceClass.getMethod("getFrequentRenterPoints( int )");
        getFrequentRenterPointsMethodPrice.findDefUseFields("price$0.!getFrequentRenterPoints( int )", "");
        
        JClass newReleasePriceClass = bcStore.getJClass("org.jtool.videostore.after.NewReleasePrice");
        getChargeMethodNewReleasePrice = newReleasePriceClass.getMethod("getCharge( int )");
        getChargeMethodNewReleasePrice.findDefUseFields("movie$0.!getCharge( int )", "");
        getFrequentRenterPointsMethodNewReleasePrice = newReleasePriceClass.getMethod("getFrequentRenterPoints( int )");
        getFrequentRenterPointsMethodNewReleasePrice.findDefUseFields("movie$0.!getFrequentRenterPoints( int )", "");
    }
    
    @AfterClass
    public static void tearDown() {
        project.getModelBuilder().unbuild();
        BuilderTestUtil.clearProject();
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
        assertEquals("java.util.AbstractList%modCount%this.rentals.!java.util.ArrayList.modCount", result.get(0));
        assertEquals("java.util.ArrayList%size%this.rentals.!java.util.ArrayList.size", result.get(1));
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
        assertEquals(0, customerMethod.getUseFields().size());
    }
    
    @Test
    public void testGetUseFields2() {
        List<String> result = TestUtil.asSortedList(addRentalMethod.getUseFields().stream()
                .map(o -> o.getQualifiedName()));
        
        assertEquals(9, result.size());
        assertEquals("java.util.AbstractList%modCount%this.rentals.!java.util.ArrayList.modCount", result.get(0));
        assertEquals("java.util.AbstractList%modCount%this.rentals.!java.util.ArrayList.modCount", result.get(1));
        assertEquals("java.util.ArrayList%elementData%this.rentals.!java.util.ArrayList.elementData", result.get(2));
        assertEquals("java.util.ArrayList%elementData%this.rentals.!java.util.ArrayList.elementData", result.get(3));
        assertEquals("java.util.ArrayList%size%this.rentals.!java.util.ArrayList.size", result.get(4));
        assertEquals("java.util.ArrayList%size%this.rentals.!java.util.ArrayList.size", result.get(5));
        assertEquals("org.jtool.videostore.after.Customer%rentals%this.rentals", result.get(6));
        assertEquals("org.jtool.videostore.after.Rental%daysRented%rental$0.daysRented", result.get(7));
        assertEquals("org.jtool.videostore.after.Rental%movie%rental$0.movie", result.get(8));
    }
    
    @Test
    public void testGetUseFields3() {
        List<String> result = TestUtil.asSortedList(statementMethod.getUseFields().stream()
                .map(o -> o.getQualifiedName()));
        
        assertEquals(35, result.size());
        assertEquals("java.lang.String%COMPACT_STRINGS%this.!java.lang.String.COMPACT_STRINGS", result.get(0));
        assertEquals("java.lang.String%COMPACT_STRINGS%this.!java.lang.String.COMPACT_STRINGS", result.get(1));
        assertEquals("org.jtool.videostore.after.Customer%name%this.name", result.get(2));
        assertEquals("org.jtool.videostore.after.Customer%name%this.name", result.get(3));
        assertEquals("org.jtool.videostore.after.Customer%rentals%this.rentals", result.get(4));
        assertEquals("org.jtool.videostore.after.Customer%rentals%this.rentals", result.get(5));
        assertEquals("org.jtool.videostore.after.Customer%rentals%this.rentals", result.get(6));
        assertEquals("org.jtool.videostore.after.Customer%rentals%this.rentals", result.get(7));
        assertEquals("org.jtool.videostore.after.Customer%rentals%this.rentals", result.get(8));
        assertEquals("org.jtool.videostore.after.Movie%price%each$1.movie.price", result.get(9));
        assertEquals("org.jtool.videostore.after.Movie%price%each$1.movie.price", result.get(10));
        assertEquals("org.jtool.videostore.after.Movie%price%this.each$1.movie.price", result.get(11));
        assertEquals("org.jtool.videostore.after.Movie%price%this.each$1.movie.price", result.get(12));
        assertEquals("org.jtool.videostore.after.Movie%price%this.each$1.movie.price", result.get(13));
        assertEquals("org.jtool.videostore.after.Movie%price%this.each$1.movie.price", result.get(14));
        assertEquals("org.jtool.videostore.after.Movie%price%this.movie.price", result.get(15));
        assertEquals("org.jtool.videostore.after.Movie%price%this.movie.price", result.get(16));
        assertEquals("org.jtool.videostore.after.Movie%title%each$1.!getMovie( ).title", result.get(17));
        assertEquals("org.jtool.videostore.after.Movie%title%each$1.!getMovie( ).title", result.get(18));
        assertEquals("org.jtool.videostore.after.Movie%title%this.movie.title", result.get(19));
        assertEquals("org.jtool.videostore.after.Movie%title%this.movie.title", result.get(20));
        assertEquals("org.jtool.videostore.after.Rental%daysRented%each$1.daysRented", result.get(21));
        assertEquals("org.jtool.videostore.after.Rental%daysRented%each$1.daysRented", result.get(22));
        assertEquals("org.jtool.videostore.after.Rental%daysRented%this.each$1.daysRented", result.get(23));
        assertEquals("org.jtool.videostore.after.Rental%daysRented%this.each$1.daysRented", result.get(24));
        assertEquals("org.jtool.videostore.after.Rental%daysRented%this.each$1.daysRented", result.get(25));
        assertEquals("org.jtool.videostore.after.Rental%daysRented%this.each$1.daysRented", result.get(26));
        assertEquals("org.jtool.videostore.after.Rental%movie%each$1.movie", result.get(27));
        assertEquals("org.jtool.videostore.after.Rental%movie%each$1.movie", result.get(28));
        assertEquals("org.jtool.videostore.after.Rental%movie%each$1.movie", result.get(29));
        assertEquals("org.jtool.videostore.after.Rental%movie%each$1.movie", result.get(30));
        assertEquals("org.jtool.videostore.after.Rental%movie%this.each$1.movie", result.get(31));
        assertEquals("org.jtool.videostore.after.Rental%movie%this.each$1.movie", result.get(32));
        assertEquals("org.jtool.videostore.after.Rental%movie%this.each$1.movie", result.get(33));
        assertEquals("org.jtool.videostore.after.Rental%movie%this.each$1.movie", result.get(34));
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
        assertEquals("java.util.ArrayList#add( java.lang.Object )", result.get(0));
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
