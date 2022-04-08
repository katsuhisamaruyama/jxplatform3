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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class JClassInternalTest {
    
    private static JavaProject project;
    private static BytecodeClassStore bcStore;
    
    private static JClass customerClass;
    private static JClass rentalClass;
    private static JClass movieClass;
    private static JClass priceClass;
    private static JClass regularPriceClass;
    private static JClass newReleasePriceClass;
    private static JClass childrensPriceClass;
    
    @BeforeClass
    public static void setUp() {
        project = RefModelTestUtil.createProjectFromSourceWithoutLibCache("VideoStore", "/lib/*", "");
        bcStore = project.getCFGStore().getBCStore();
        
        customerClass = bcStore.getJClass("org.jtool.videostore.after.Customer");
        rentalClass = bcStore.getJClass("org.jtool.videostore.after.Rental");
        movieClass = bcStore.getJClass("org.jtool.videostore.after.Movie");
        priceClass = bcStore.getJClass("org.jtool.videostore.after.Price");
        regularPriceClass = bcStore.getJClass("org.jtool.videostore.after.RegularPrice");
        newReleasePriceClass = bcStore.getJClass("org.jtool.videostore.after.NewReleasePrice");
        childrensPriceClass = bcStore.getJClass("org.jtool.videostore.after.ChildrensPrice");
    }
    
    @AfterClass
    public static void tearDown() {
        project.getModelBuilder().unbuild();
    }
    
    @Test
    public void testInstanceOf1() {
        assertTrue(customerClass instanceof JClassInternal);
    }
    
    @Test
    public void testInstanceOf2() {
        assertTrue(rentalClass instanceof JClassInternal);
    }
    
    @Test
    public void testInstanceOf3() {
        assertTrue(movieClass instanceof JClassInternal);
    }
    
    @Test
    public void testInstanceOf4() {
        assertTrue(priceClass instanceof JClassInternal);
    }
    
    @Test
    public void testInstanceOf5() {
        assertTrue(regularPriceClass instanceof JClassInternal);
    }
    
    @Test
    public void testInstanceOf6() {
        assertTrue(newReleasePriceClass instanceof JClassInternal);
    }
    
    @Test
    public void testInstanceOf7() {
        assertTrue(childrensPriceClass instanceof JClassInternal);
    }
    
    @Test
    public void testGetQualifiedName1() {
        assertEquals("org.jtool.videostore.after.Customer", customerClass.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName2() {
        assertEquals("org.jtool.videostore.after.Rental", rentalClass.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName3() {
        assertEquals("org.jtool.videostore.after.Movie", movieClass.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName4() {
        assertEquals("org.jtool.videostore.after.Price", priceClass.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName5() {
        assertEquals("org.jtool.videostore.after.RegularPrice",
                regularPriceClass.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName6() {
        assertEquals("org.jtool.videostore.after.NewReleasePrice",
                newReleasePriceClass.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetQualifiedName7() {
        assertEquals("org.jtool.videostore.after.ChildrensPrice",
                childrensPriceClass.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetName1() {
        assertEquals("org.jtool.videostore.after.Customer", customerClass.getName());
    }
    
    @Test
    public void testGetName2() {
        assertEquals("org.jtool.videostore.after.Rental", rentalClass.getName());
    }
    
    @Test
    public void testGetName3() {
        assertEquals("org.jtool.videostore.after.Movie", movieClass.getName());
    }
    
    @Test
    public void testGetName4() {
        assertEquals("org.jtool.videostore.after.Price", priceClass.getName());
    }
    
    @Test
    public void testGetName5() {
        assertEquals("org.jtool.videostore.after.RegularPrice", regularPriceClass.getName());
    }
    
    @Test
    public void testGetName6() {
        assertEquals("org.jtool.videostore.after.NewReleasePrice", newReleasePriceClass.getName());
    }
    
    @Test
    public void testGetName7() {
        assertEquals("org.jtool.videostore.after.ChildrensPrice", childrensPriceClass.getName());
    }
    
    @Test
    public void testGetSimpleName1() {
        assertEquals("Customer", customerClass.getSimpleName());
    }
    
    @Test
    public void testGetSimpleName2() {
        assertEquals("Rental", rentalClass.getSimpleName());
    }
    
    @Test
    public void testGetSimpleName3() {
        assertEquals("Movie", movieClass.getSimpleName());
    }
    
    @Test
    public void testGetSimpleName4() {
        assertEquals("Price", priceClass.getSimpleName());
    }
    
    @Test
    public void testGetSimpleName5() {
        assertEquals("RegularPrice", regularPriceClass.getSimpleName());
    }
    
    @Test
    public void testGetSimpleName6() {
        assertEquals("NewReleasePrice", newReleasePriceClass.getSimpleName());
    }
    
    @Test
    public void testGetSimpleName7() {
        assertEquals("ChildrensPrice", childrensPriceClass.getSimpleName());
    }
    
    @Test
    public void testGetMethods1() {
        assertEquals(6, customerClass.getMethods().size());
    }
    
    @Test
    public void testGetMethods2() {
        assertEquals(5, rentalClass.getMethods().size());
    }
    
    @Test
    public void testGetMethods3() {
        assertEquals(6, movieClass.getMethods().size());
    }
    
    @Test
    public void testGetMethods4() {
        assertEquals(4, priceClass.getMethods().size());
    }
    
    @Test
    public void testGetMethods5() {
        assertEquals(2, regularPriceClass.getMethods().size());
    }
    
    @Test
    public void testGetMethods6() {
        assertEquals(3, newReleasePriceClass.getMethods().size());
    }
    
    @Test
    public void testGetMethods7() {
        assertEquals(2, childrensPriceClass.getMethods().size());
    }
    
    @Test
    public void testGetMethod1() {
        assertEquals("org.jtool.videostore.after.Customer#addRental( org.jtool.videostore.after.Rental )",
                customerClass.getMethod("addRental( org.jtool.videostore.after.Rental )")
                .getQualifiedName().fqn());
    }
    
    @Test
    public void testGetMethod2() {
        assertEquals("org.jtool.videostore.after.Rental#getCharge( )",
                rentalClass.getMethod("getCharge( )")
                .getQualifiedName().fqn());
    }
    
    @Test
    public void testGetMethod3() {
        assertEquals("org.jtool.videostore.after.Movie#Movie( java.lang.String org.jtool.videostore.after.Movie.PriceCode )",
                movieClass.getMethod("Movie( java.lang.String org.jtool.videostore.after.Movie.PriceCode )")
                .getQualifiedName().fqn());
    }
    
    @Test
    public void testGetMethod4() {
        assertEquals("org.jtool.videostore.after.Price#getCharge( int )",
                priceClass.getMethod("getCharge( int )")
                .getQualifiedName().fqn());
    }
    
    @Test
    public void testGetMethod5() {
        assertEquals("org.jtool.videostore.after.RegularPrice#getCharge( int )",
                regularPriceClass.getMethod("getCharge( int )")
                .getQualifiedName().fqn());
    }
    
    @Test
    public void testGetMethodReturningNull() {
        assertNull(customerClass.getMethod("addRental( org.jtool.videostore.after.Movie )"));
    }
    
    @Test
    public void testGetFields1() {
        assertEquals(2, customerClass.getFields().size());
    }
    
    @Test
    public void testGetFields2() {
        assertEquals(2, rentalClass.getFields().size());
    }
    
    @Test
    public void testGetFields3() {
        assertEquals(2, movieClass.getFields().size());
    }
    
    @Test
    public void testGetFields4() {
        assertEquals(1, priceClass.getFields().size());
    }
    
    @Test
    public void testGetFields5() {
        assertEquals(0, regularPriceClass.getFields().size());
    }
    
    @Test
    public void testGetFields6() {
        assertEquals(0, newReleasePriceClass.getFields().size());
    }
    
    @Test
    public void testGetFields7() {
        assertEquals(0, childrensPriceClass.getFields().size());
    }
    
    @Test
    public void testGetField1() {
        assertEquals("org.jtool.videostore.after.Customer#rentals",
                customerClass.getField("rentals")
                .getQualifiedName().fqn());
    }
    
    @Test
    public void testGetField2() {
        assertEquals("org.jtool.videostore.after.Rental#daysRented",
                rentalClass.getField("daysRented").getQualifiedName().fqn());
    }
    
    @Test
    public void testGetField3() {
        assertEquals("org.jtool.videostore.after.Movie#title",
                movieClass.getField("title").getQualifiedName().fqn());
    }
    
    @Test
    public void testGetField4() {
        assertEquals("org.jtool.videostore.after.Price#priceCode",
                priceClass.getField("priceCode").getQualifiedName().fqn());
    }
    
    @Test
    public void testGetFieldRetuningNull() {
        assertNull(priceClass.getField("title"));
    }
    
    @Test
    public void testGetSuperClass1() {
        assertEquals("java.lang.Object", customerClass.getSuperClass());
    }
    
    @Test
    public void testGetSuperClass2() {
        assertEquals("java.lang.Object", rentalClass.getSuperClass());
    }
    
    @Test
    public void testGetSuperClass3() {
        assertEquals("java.lang.Object", movieClass.getSuperClass());
    }
    
    @Test
    public void testGetSuperClass4() {
        assertEquals("java.lang.Object", priceClass.getSuperClass());
    }
    
    @Test
    public void testGetSuperClass5() {
        assertEquals("org.jtool.videostore.after.Price", regularPriceClass.getSuperClass());
    }
    
    @Test
    public void testGetSuperClass6() {
        assertEquals("org.jtool.videostore.after.Price", newReleasePriceClass.getSuperClass());
    }
    
    @Test
    public void testGetSuperClass7() {
        assertEquals("org.jtool.videostore.after.Price", childrensPriceClass.getSuperClass());
    }
    
    @Test
    public void testGetSuperInterfaces1() {
        assertEquals(0, customerClass.getSuperInterfaces().size());
    }
    
    @Test
    public void testGetSuperInterfaces2() {
        assertEquals(0, rentalClass.getSuperInterfaces().size());
    }
    
    @Test
    public void testGetSuperInterfaces3() {
        assertEquals(1, movieClass.getSuperInterfaces().size());
        assertEquals("java.io.Serializable", movieClass.getSuperInterfaces().get(0));
    }
    
    @Test
    public void testGetSuperInterfaces4() {
        assertEquals(0, priceClass.getSuperInterfaces().size());
    }
    
    @Test
    public void testGetSuperInterfaces5() {
        assertEquals(0, regularPriceClass.getSuperInterfaces().size());
    }
    
    @Test
    public void testGetSuperInterfaces6() {
        assertEquals(0, newReleasePriceClass.getSuperInterfaces().size());
    }
    
    @Test
    public void testGetSuperInterfaces7() {
        assertEquals(0, childrensPriceClass.getSuperInterfaces().size());
    }
    
    @Test
    public void testGetSuperClassChain1() {
        assertEquals(1, customerClass.getSuperClassChain().size());
        assertEquals("java.lang.Object", customerClass.getSuperClassChain().get(0).getName());
    }
    
    @Test
    public void testGetSuperClassChain2() {
        assertEquals(1, movieClass.getSuperClassChain().size());
        assertEquals("java.lang.Object", movieClass.getSuperClassChain().get(0).getName());
    }
    
    @Test
    public void testGetSuperClassChain3() {
        assertEquals(2, regularPriceClass.getSuperClassChain().size());
        assertEquals("org.jtool.videostore.after.Price", regularPriceClass.getSuperClassChain().get(0).getName());
        assertEquals("java.lang.Object", regularPriceClass.getSuperClassChain().get(1).getName());
    }
    
    @Test
    public void testIsInterface1() {
        assertFalse(customerClass.isInterface());
    }
    
    @Test
    public void testIsInterface2() {
        assertFalse(rentalClass.isInterface());
    }
    
    @Test
    public void testIsInterface3() {
        assertFalse(movieClass.isInterface());
    }
    
    @Test
    public void testIsInterface4() {
        assertFalse(priceClass.isInterface());
    }
    
    @Test
    public void testIsInterface5() {
        assertFalse(regularPriceClass.isInterface());
    }
    
    @Test
    public void testIsInterface6() {
        assertFalse(newReleasePriceClass.isInterface());
    }
    
    @Test
    public void testIsInterface7() {
        assertFalse(childrensPriceClass.isInterface());
    }
    
    @Test
    public void testIsInProject1() {
        assertTrue(customerClass.isInProject());
    }
    
    @Test
    public void testIsInProject2() {
        assertTrue(rentalClass.isInProject());
    }
    
    @Test
    public void testIsInProject3() {
        assertTrue(movieClass.isInProject());
    }
    
    @Test
    public void testIsInProject4() {
        assertTrue(priceClass.isInProject());
    }
    
    @Test
    public void testIsInProject5() {
        assertTrue(regularPriceClass.isInProject());
    }
    
    @Test
    public void testIsInProject6() {
        assertTrue(newReleasePriceClass.isInProject());
    }
    
    @Test
    public void testIsInProject7() {
        assertTrue(childrensPriceClass.isInProject());
    }
    
    @Test
    public void testGetAncestorClasses1() {
        List<String> result = TestUtil.asSortedList(customerClass.getAncestorClasses().stream()
                .map(o -> o.getName()));
        
        assertEquals(1, result.size());
        assertEquals("java.lang.Object", result.get(0));
    }
    
    @Test
    public void testGetAncestorClasses2() {
        List<String> result = TestUtil.asSortedList(movieClass.getAncestorClasses().stream()
                .map(o -> o.getName()));
        
        assertEquals(1, result.size());
        assertEquals("java.lang.Object", result.get(0));
    }
    
    @Test
    public void testGetAncestorClasses3() {
        List<String> result = TestUtil.asSortedList(priceClass.getAncestorClasses().stream()
                .map(o -> o.getName()));
        
        assertEquals(1, result.size());
        assertEquals("java.lang.Object", result.get(0));
    }
    
    @Test
    public void testGetAncestorClasses4() {
        List<String> result = TestUtil.asSortedList(regularPriceClass.getAncestorClasses().stream()
                .map(o -> o.getName()));
        
        assertEquals(2, result.size());
        assertEquals("java.lang.Object", result.get(0));
        assertEquals("org.jtool.videostore.after.Price", result.get(1));
    }
    
    @Test
    public void testGetDescendantClasses1() {
        assertEquals(0, customerClass.getDescendantClasses().size());
    }
    
    @Test
    public void testGetDescendantClasses2() {
        assertEquals(0, movieClass.getDescendantClasses().size());
    }
    
    @Test
    public void testGetDescendantClasses3() {
        List<String> result = TestUtil.asSortedList(priceClass.getDescendantClasses().stream()
                .map(o -> o.getName()));
        
        assertEquals(3, result.size());
        assertEquals("org.jtool.videostore.after.ChildrensPrice", result.get(0));
        assertEquals("org.jtool.videostore.after.NewReleasePrice", result.get(1));
        assertEquals("org.jtool.videostore.after.RegularPrice", result.get(2));
    }
}
