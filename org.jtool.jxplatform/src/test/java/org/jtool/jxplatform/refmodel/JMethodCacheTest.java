/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.refmodel;

import org.jtool.jxplatform.util.TestUtil;
import org.jtool.srcmodel.JavaProject;
import java.util.List;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class JMethodCacheTest {
    
    private static JavaProject project;
    private static BytecodeClassStore bcStore;
    
    private static JMethod customerMethod;
    private static JMethod addRentalMethod;
    private static JMethod statementMethod;
    
    private static JMethod indexOfMethod;
    private static JMethod expectedMethod;
    
    @BeforeClass
    public static void setUp() {
        project = RefModelTestUtil.createProjectFromCache("VideoStore", "/lib/*", "");
        bcStore = project.getCFGStore().getBCStore();
        
        JClass customerClass = bcStore.getJClass("org.jtool.videostore.after.Customer");
        customerMethod = customerClass.getMethod("Customer( java.lang.String )");
        customerMethod.findDefUseFields();
        addRentalMethod = customerClass.getMethod("addRental( org.jtool.videostore.after.Rental )");
        addRentalMethod.findDefUseFields();
        statementMethod = customerClass.getMethod("statement( )");
        statementMethod.findDefUseFields();
        
        JClass stringClass = bcStore.getJClass("java.lang.String");
        indexOfMethod = stringClass.getMethod("indexOf( byte[] byte int java.lang.String int )");
        indexOfMethod.findDefUseFields();
        
        JClass testClass = bcStore.getJClass("org.junit.Test");
        expectedMethod = testClass.getMethod("expected( )");
        expectedMethod.findDefUseFields();
    }
    
    @AfterClass
    public static void tearDown() {
        project.getModelBuilder().unbuild();
    }
    
    @Test
    public void testInstanceOf() {
        assertTrue(customerMethod instanceof JMethodCache);
        
        assertTrue(addRentalMethod instanceof JMethodCache);
        
        assertTrue(statementMethod instanceof JMethodCache);
        
        assertTrue(indexOfMethod instanceof JMethodCache);
        
        assertTrue(expectedMethod instanceof JMethodCache);
    }
    
    @Test
    public void testGetQualifiedName() {
        assertEquals("org.jtool.videostore.after.Customer#Customer( java.lang.String )",
                customerMethod.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Customer#addRental( org.jtool.videostore.after.Rental )",
                addRentalMethod.getQualifiedName().fqn());
        
        assertEquals("org.jtool.videostore.after.Customer#statement( )",
                statementMethod.getQualifiedName().fqn());
        
        assertEquals("java.lang.String#indexOf( byte[] byte int java.lang.String int )",
                indexOfMethod.getQualifiedName().fqn());
        assertEquals("org.junit.Test#expected( )",
                expectedMethod.getQualifiedName().fqn());
    }
    
    @Test
    public void testGetDeclaringClass() {
        assertEquals("org.jtool.videostore.after.Customer",
                customerMethod.getDeclaringClass().getClassName());
        
        assertEquals("org.jtool.videostore.after.Customer",
                addRentalMethod.getDeclaringClass().getClassName());
        
        assertEquals("org.jtool.videostore.after.Customer",
                statementMethod.getDeclaringClass().getClassName());
        
        assertEquals("java.lang.String",
                indexOfMethod.getDeclaringClass().getClassName());
        
        assertEquals("org.junit.Test",
                expectedMethod.getDeclaringClass().getClassName());
    }
    
    @Test
    public void testIsInProject() {
        assertTrue(customerMethod.isInProject());
        
        assertTrue(addRentalMethod.isInProject());
        
        assertTrue(statementMethod.isInProject());
        
        assertFalse(indexOfMethod.isInProject());
        
        assertFalse(expectedMethod.isInProject());
    }
    
    @Test
    public void testGetDefFields() {
        List<String> cresult = TestUtil.asSortedList(customerMethod.getDefFields().stream().map(o -> o.getQualifiedName()));
        assertEquals(1, cresult.size());
        assertEquals("org.jtool.videostore.after.Customer%name%this.name", cresult.get(0));
        
        assertEquals(0, addRentalMethod.getDefFields().size());
        
        assertEquals(0, statementMethod.getDefFields().size());
        
        assertEquals(0, indexOfMethod.getDefFields().size());
        
        assertEquals(0, expectedMethod.getDefFields().size());
    }
    
    @Test
    public void testGetUseFields() {
        assertEquals(0, customerMethod.getUseFields().size());
        
        List<String> aresult = TestUtil.asSortedList(addRentalMethod.getUseFields().stream().map(o -> o.getQualifiedName()));
        assertEquals(1, aresult.size());
        assertEquals("org.jtool.videostore.after.Customer%rentals%this.rentals", aresult.get(0));
        
        //List<String> sresult = TestUtil.asSortedList(statementMethod.getUseFields().stream().map(o -> o.getQualifiedName()));
        //assertEquals(4, sresult.size());
        //assertEquals("java.lang.String%COMPACT_STRINGS%java.lang.String.COMPACT_STRINGS", sresult.get(0));
        //assertEquals("java.lang.String%COMPACT_STRINGS%this.COMPACT_STRINGS", sresult.get(1));
        //assertEquals("org.jtool.videostore.after.Customer%name%this.name", sresult.get(2));
        //assertEquals("org.jtool.videostore.after.Customer%rentals%this.rentals", sresult.get(3));
        
        List<String> iresult = TestUtil.asSortedList(indexOfMethod.getUseFields().stream().map(o -> o.getQualifiedName()));
        assertEquals(3, iresult.size());
        assertEquals("java.lang.String%COMPACT_STRINGS%java.lang.String.COMPACT_STRINGS", iresult.get(0));
        assertEquals("java.lang.String%coder%java.lang.String.coder", iresult.get(1));
        assertEquals("java.lang.String%value%java.lang.String.value", iresult.get(2));
        
        assertEquals(0, expectedMethod.getUseFields().size());
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
        
        List<String> iresult = TestUtil.asSortedList(indexOfMethod.getAccessedMethods().stream().map(o -> o.getQualifiedName()));
        assertEquals(5, iresult.size());
        assertEquals("java.lang.String#coder( )", iresult.get(0));
        assertEquals("java.lang.String#length( )", iresult.get(1));
        assertEquals("java.lang.StringLatin1#indexOf( byte[] int byte[] int int )", iresult.get(2));
        assertEquals("java.lang.StringUTF16#indexOf( byte[] int byte[] int int )", iresult.get(3));
        assertEquals("java.lang.StringUTF16#indexOfLatin1( byte[] int byte[] int int )", iresult.get(4));
        
        assertEquals(0, expectedMethod.getAccessedMethods().size());
    }
}