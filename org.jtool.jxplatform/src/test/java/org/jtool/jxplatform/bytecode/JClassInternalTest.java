/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.bytecode;

import org.jtool.jxplatform.refmodel.BytecodeClassStore;
import org.jtool.jxplatform.refmodel.JClass;
import org.jtool.jxplatform.refmodel.JClassInternal;
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
        String name = "VideoStore";
        String target = TestUtil.getTarget(name);
        String classpath = target + "/lib/*";
        
        project = BytecodeTestUtil.createProjectFromSourceWithoutLibCache(target, classpath);
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
    public void testInstanceOf() {
        assertTrue(customerClass instanceof JClassInternal);
        
        assertTrue(rentalClass instanceof JClassInternal);
        
        assertTrue(movieClass instanceof JClassInternal);
        
        assertTrue(priceClass instanceof JClassInternal);
        
        assertTrue(regularPriceClass instanceof JClassInternal);
        
        assertTrue(newReleasePriceClass instanceof JClassInternal);
        
        assertTrue(childrensPriceClass instanceof JClassInternal);
    }
    
    @Test
    public void testGetQualifiedName() {
        assertEquals("org.jtool.videostore.after.Customer", customerClass.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Rental", rentalClass.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Movie", movieClass.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Price", priceClass.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.RegularPrice", regularPriceClass.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.NewReleasePrice", newReleasePriceClass.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.ChildrensPrice", childrensPriceClass.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetName() {
        assertEquals("org.jtool.videostore.after.Customer", customerClass.getName());
        
        assertEquals("org.jtool.videostore.after.Rental", rentalClass.getName());
        
        assertEquals("org.jtool.videostore.after.Movie", movieClass.getName());
        
        assertEquals("org.jtool.videostore.after.Price", priceClass.getName());
        
        assertEquals("org.jtool.videostore.after.RegularPrice", regularPriceClass.getName());
        
        assertEquals("org.jtool.videostore.after.NewReleasePrice", newReleasePriceClass.getName());
        
        assertEquals("org.jtool.videostore.after.ChildrensPrice", childrensPriceClass.getName());
    }
    
    @Test
    public void testGetSimpleName() {
        assertEquals("Customer", customerClass.getSimpleName());
        
        assertEquals("Rental", rentalClass.getSimpleName());
        
        assertEquals("Movie", movieClass.getSimpleName());
        
        assertEquals("Price", priceClass.getSimpleName());
        
        assertEquals("RegularPrice", regularPriceClass.getSimpleName());
        
        assertEquals("NewReleasePrice", newReleasePriceClass.getSimpleName());
        
        assertEquals("ChildrensPrice", childrensPriceClass.getSimpleName());
    }
    
    @Test
    public void testGetMethods() {
        assertEquals(6, customerClass.getMethods().size());
        
        assertEquals(5, rentalClass.getMethods().size());
        
        assertEquals(6, movieClass.getMethods().size());
        
        assertEquals(4, priceClass.getMethods().size());
        
        assertEquals(2, regularPriceClass.getMethods().size());
        
        assertEquals(3, newReleasePriceClass.getMethods().size());
        
        assertEquals(2, childrensPriceClass.getMethods().size());
    }
    
    @Test
    public void testGetMethod() {
        assertEquals("org.jtool.videostore.after.Customer#addRental( org.jtool.videostore.after.Rental )",
                customerClass.getMethod("addRental( org.jtool.videostore.after.Rental )")
                .getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Rental#getCharge( )",
                rentalClass.getMethod("getCharge( )")
                .getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Movie#Movie( java.lang.String org.jtool.videostore.after.Movie.PriceCode )",
                movieClass.getMethod("Movie( java.lang.String org.jtool.videostore.after.Movie.PriceCode )")
                .getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Price#getCharge( int )",
                priceClass.getMethod("getCharge( int )")
                .getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.RegularPrice#getCharge( int )",
                regularPriceClass.getMethod("getCharge( int )")
                .getQualifiedName().fqn());
        
    }
    
    @Test
    public void testGetMethodReturningNull() {
        assertNull(customerClass.getMethod("addRental( org.jtool.videostore.after.Movie )"));
    }
    
    @Test
    public void testGetFields() {
        assertEquals(2, customerClass.getFields().size());
        
        assertEquals(2, rentalClass.getFields().size());
        
        assertEquals(2, movieClass.getFields().size());
        
        assertEquals(1, priceClass.getFields().size());
        
        assertEquals(0, regularPriceClass.getFields().size());
        
        assertEquals(0, newReleasePriceClass.getFields().size());
        
        assertEquals(0, childrensPriceClass.getFields().size());
    }
    
    @Test
    public void testGetField() {
        assertEquals("org.jtool.videostore.after.Customer#rentals",
                customerClass.getField("rentals")
                .getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Rental#daysRented",
                rentalClass.getField("daysRented")
                .getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Movie#title",
                movieClass.getField("title")
                .getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Price#priceCode",
                priceClass.getField("priceCode")
                .getQualifiedName().fqn());
    }
    
    @Test
    public void testGetFieldRetuningNull() {
        assertNull(priceClass.getField("title"));
    }
    
    @Test
    public void testGetSuperClass() {
        assertEquals("java.lang.Object", customerClass.getSuperClass());
        
        assertEquals("java.lang.Object", rentalClass.getSuperClass());
        
        assertEquals("java.lang.Object", movieClass.getSuperClass());
        
        assertEquals("java.lang.Object", priceClass.getSuperClass());
        
        assertEquals("org.jtool.videostore.after.Price", regularPriceClass.getSuperClass());
        
        assertEquals("org.jtool.videostore.after.Price", newReleasePriceClass.getSuperClass());
        
        assertEquals("org.jtool.videostore.after.Price", childrensPriceClass.getSuperClass());
    }
    
    @Test
    public void testGetSuperInterfaces() {
        assertEquals(0, customerClass.getSuperInterfaces().size());
        
        assertEquals(0, rentalClass.getSuperInterfaces().size());
        
        assertEquals(1, movieClass.getSuperInterfaces().size());
        assertEquals("java.io.Serializable", movieClass.getSuperInterfaces().get(0));
        
        assertEquals(0, priceClass.getSuperInterfaces().size());
        
        assertEquals(0, regularPriceClass.getSuperInterfaces().size());
        
        assertEquals(0, newReleasePriceClass.getSuperInterfaces().size());
        
        assertEquals(0, childrensPriceClass.getSuperInterfaces().size());
    }
    
    @Test
    public void testGetSuperClassChain() {
        assertEquals(1, customerClass.getSuperClassChain().size());
        assertEquals("java.lang.Object", customerClass.getSuperClassChain().get(0).getName());
        
        assertEquals(1, movieClass.getSuperClassChain().size());
        assertEquals("java.lang.Object", movieClass.getSuperClassChain().get(0).getName());
        
        assertEquals(2, regularPriceClass.getSuperClassChain().size());
        assertEquals("org.jtool.videostore.after.Price", regularPriceClass.getSuperClassChain().get(0).getName());
        assertEquals("java.lang.Object", regularPriceClass.getSuperClassChain().get(1).getName());
    }
    
    @Test
    public void testIsInterface() {
        assertFalse(customerClass.isInterface());
        
        assertFalse(rentalClass.isInterface());
        
        assertFalse(movieClass.isInterface());
        
        assertFalse(priceClass.isInterface());
        
        assertFalse(regularPriceClass.isInterface());
        
        assertFalse(newReleasePriceClass.isInterface());
        
        assertFalse(childrensPriceClass.isInterface());
    }
    
    @Test
    public void testIsInProject() {
        assertTrue(customerClass.isInProject());
        
        assertTrue(rentalClass.isInProject());
        
        assertTrue(movieClass.isInProject());
        
        assertTrue(priceClass.isInProject());
        
        assertTrue(regularPriceClass.isInProject());
        
        assertTrue(newReleasePriceClass.isInProject());
        
        assertTrue(childrensPriceClass.isInProject());
    }
    
    @Test
    public void testGetAncestorClasses() {
        List<String> cresult = TestUtil.asSortedList(customerClass.getAncestorClasses().stream().map(o -> o.getName()));
        assertEquals(1, cresult.size());
        assertEquals("java.lang.Object", cresult.get(0));
        
        List<String> mresult = TestUtil.asSortedList(movieClass.getAncestorClasses().stream().map(o -> o.getName()));
        assertEquals(1, mresult.size());
        assertEquals("java.lang.Object", mresult.get(0));
        
        List<String> presult = TestUtil.asSortedList(priceClass.getAncestorClasses().stream().map(o -> o.getName()));
        assertEquals(1, presult.size());
        assertEquals("java.lang.Object", presult.get(0));
        
        List<String> rresult = TestUtil.asSortedList(regularPriceClass.getAncestorClasses().stream().map(o -> o.getName()));
        assertEquals(2, rresult.size());
        assertEquals("java.lang.Object", rresult.get(0));
        assertEquals("org.jtool.videostore.after.Price", rresult.get(1));
    }
    
    @Test
    public void testGetDescendantClasses() {
        assertEquals(0, customerClass.getDescendantClasses().size());
        
        assertEquals(0, movieClass.getDescendantClasses().size());
        
        List<String> presult = TestUtil.asSortedList(priceClass.getDescendantClasses().stream().map(o -> o.getName()));
        assertEquals(3, presult.size());
        assertEquals("org.jtool.videostore.after.ChildrensPrice", presult.get(0));
        assertEquals("org.jtool.videostore.after.NewReleasePrice", presult.get(1));
        assertEquals("org.jtool.videostore.after.RegularPrice", presult.get(2));
    }
}
