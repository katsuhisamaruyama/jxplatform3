/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.util;

import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaProject;

public interface BuilderSliceTestUtil {
    
    public static JavaProject SliceProject = BuilderTestUtil.createProject("Slice", "", "");
    
    public static JavaClass Test101 = SliceProject.getClass("Test101");
    public static JavaMethod Test101m = Test101.getMethod("m( )");
    public static JavaField Test101p = Test101.getField("p");
    
    public static JavaClass Test102 = SliceProject.getClass("Test102");
    public static JavaMethod Test102m = Test102.getMethod("m( )");
    public static JavaMethod Test102inc = Test102.getMethod("inc( int )");
    
    public static JavaClass Test103 = SliceProject.getClass("Test102");
    public static JavaMethod Test103m = Test103.getMethod("m( )");
    public static JavaMethod Test103setA = Test103.getMethod("setA( int )");
    public static JavaMethod Test103getA = Test103.getMethod("getA( )");
    public static JavaMethod Test103incA = Test103.getMethod("incA( )");
    public static JavaField Test103a = Test102.getField("a");
    
    public static JavaClass Test104 = SliceProject.getClass("Test104");
    public static JavaMethod Test104m = Test104.getMethod("m( )");
    
    public static JavaClass Test105 = SliceProject.getClass("Test105");
    public static JavaMethod Test105m = Test105.getMethod("m( )");
    public static JavaMethod Test105setA = Test105.getMethod("setA( int )");
    public static JavaField Test105a = Test105.getField("a");
    
    public static JavaClass Test106 = SliceProject.getClass("Test106");
    public static JavaMethod Test106m = Test106.getMethod("m( )");
    public static JavaMethod Test106setA = Test106.getMethod("setA( int )");
    public static JavaField Test106a = Test106.getField("a");
    
    public static JavaClass Test107 = SliceProject.getClass("Test107");
    public static JavaMethod Test107m = Test107.getMethod("m( int[] )");
    
    public static JavaClass Test108 = SliceProject.getClass("Test108");
    public static JavaMethod Test108m = Test108.getMethod("m( )");
    
    public static JavaClass Test109 = SliceProject.getClass("Test109");
    public static JavaMethod Test109m = Test109.getMethod("m( )");
    
    public static JavaClass Test110 = SliceProject.getClass("Test110");
    public static JavaMethod Test110m = Test110.getMethod("m( )");
    
    public static JavaClass Test111 = SliceProject.getClass("Test111");
    public static JavaMethod Test111m = Test111.getMethod("m( )");
    
    public static JavaClass Test112 = SliceProject.getClass("Test112");
    public static JavaMethod Test112m = Test112.getMethod("m( )");
    
    public static JavaClass Test113 = SliceProject.getClass("Test113");
    public static JavaMethod Test113m = Test113.getMethod("m( )");
    
    public static JavaClass Test114 = SliceProject.getClass("Test114");
    public static JavaMethod Test114m = Test114.getMethod("m( )");
    
    public static JavaClass Test115 = SliceProject.getClass("Test115");
    public static JavaMethod Test115m = Test115.getMethod("m( )");
    
    public static JavaClass Test116 = SliceProject.getClass("Test116");
    public static JavaMethod Test116m = Test116.getMethod("m( )");
    
    public static JavaClass Test117 = SliceProject.getClass("Test117");
    public static JavaMethod Test117m = Test117.getMethod("m( )");
    
    public static JavaClass Test118 = SliceProject.getClass("Test118");
    public static JavaMethod Test118m = Test118.getMethod("m( )");
    public static JavaMethod Test118m0 = Test118.getMethod("m0( int int int )");
    public static JavaMethod Test118m1 = Test118.getMethod("m1( int int int )");
    public static JavaMethod Test118m2 = Test118.getMethod("m2( int int int )");
    public static JavaMethod Test118m3 = Test118.getMethod("m3( int int int )");
    public static JavaMethod Test118m4 = Test118.getMethod("m4( int int int )");
    public static JavaMethod Test118m5 = Test118.getMethod("m5( int int int )");
    public static JavaMethod Test118m6 = Test118.getMethod("m6( int int int )");
    public static JavaMethod Test118m7 = Test118.getMethod("m7( int int int )");
    public static JavaMethod Test118m8 = Test118.getMethod("m8( int int int )");
    public static JavaMethod Test118m80 = Test118.getMethod("m8( )");
    
    public static JavaClass Test119 = SliceProject.getClass("Test119");
    public static JavaMethod Test119m = Test119.getMethod("m( )");
    public static JavaMethod Test119getP = Test119.getMethod("getP( )");
    public static JavaField Test119p = Test119.getField("p");
    
    public static JavaClass A119 = SliceProject.getClass("A119");
    public static JavaMethod A119setP = A119.getMethod("setX( int ) ");
    public static JavaMethod A119getP = A119.getMethod("getX( )");
    public static JavaField A119x = A119.getField("x");
    public static JavaField A119y = A119.getField("y");
    
    public static JavaClass Test120 = SliceProject.getClass("Test120");
    public static JavaMethod Test120m = Test120.getMethod("m( )");
    public static JavaMethod Test120m0 = Test120.getMethod("m0( int int )");
    public static JavaMethod Test120m1 = Test120.getMethod("m1( int int )");
    public static JavaMethod Test120m2 = Test120.getMethod("m2( int int )");
    public static JavaMethod Test120m3 = Test120.getMethod("m3( int int )");
    
    public static JavaClass A120 = SliceProject.getClass("A120");
    public static JavaField A120x = A120.getField("y");
    public static JavaField A120y = A120.getField("y");
    
    public static JavaClass Test121 = SliceProject.getClass("Test121");
    public static JavaMethod Test121m = Test121.getMethod("m( )");
    public static JavaMethod Test121mm = Test121.getMethod("m( int int )");
    
    public static JavaClass Test122 = SliceProject.getClass("Test122");
    public static JavaMethod Test122m = Test122.getMethod("m( )");
    public static JavaMethod Test112n = Test122.getMethod("n( int )");
    
    public static JavaClass Test123 = SliceProject.getClass("Test123");
    public static JavaMethod Test123m = Test123.getMethod("m( )");
    public static JavaMethod Test123n = Test123.getMethod("n( int )");
    public static JavaClass SubException = SliceProject.getClass("SubException");
    public static JavaClass SubSubException = SliceProject.getClass("SubSubException");
    
    public static JavaClass Test124 = SliceProject.getClass("Test124");
    public static JavaMethod Test124m = Test124.getMethod("m( )");
    public static JavaField Test124p = Test124.getField("p");
    
    public static JavaClass A124 = SliceProject.getClass("A124");
    public static JavaMethod A124A124 = A124.getMethod("A124( int )");
    public static JavaMethod A124getX = A124.getMethod("getX( )");
    public static JavaMethod A124inc = A124.getMethod("inc( int )");
    public static JavaField A124x = A124.getField("x");
    
    public static JavaClass Test125 = SliceProject.getClass("Test125");
    public static JavaMethod Test125m = Test125.getMethod("m( )");
    public static JavaMethod Test125inc1 = Test125.getMethod("inc1( )");
    public static JavaMethod Test125inc2 = Test125.getMethod("inc2( )");
    public static JavaField Test125p = Test125.getField("p");
    
    public static JavaClass Test126 = SliceProject.getClass("Test126");
    public static JavaMethod Test126m = Test126.getMethod("m( )");
    
    public static JavaClass A126 = SliceProject.getClass("A126");
    public static JavaMethod A126A126 = A126.getMethod("A126( )");
    public static JavaMethod A126setY = A126.getMethod("setY( int )");
    public static JavaMethod A126getY = A126.getMethod("getY( )");
    public static JavaMethod A126add = A126.getMethod("add( int )");
    public static JavaField A126y = A126.getField("y");
    
    public static JavaClass Test127 = SliceProject.getClass("Test127");
    public static JavaMethod Test127m = Test127.getMethod("m( )");
    public static JavaField Test127p = Test127.getField("p");
    
    public static JavaClass A127 = SliceProject.getClass("Test127");
    public static JavaMethod A127A127 = A127.getMethod("A127( )");
    public static JavaMethod A127setY = A127.getMethod("setY( int )");
    public static JavaMethod A127getY = A127.getMethod("getY( )");
    public static JavaField Test127z = A127.getField("z");
    public static JavaField Test127y = A127.getField("y");
    
    public static JavaClass Test128 = SliceProject.getClass("Test128");
    public static JavaMethod Test128m = Test128.getMethod("m( )");
    public static JavaMethod Test128n = Test128.getMethod("n( int int )");
    public static JavaField Test128p = Test128.getField("p");
    
    public static JavaClass A128 = SliceProject.getClass("A128");
    public static JavaMethod A128A128 = A128.getMethod("A128( )");
    public static JavaMethod A128setY = A128.getMethod("setY( int )");
    public static JavaMethod A128getY = A128.getMethod("getY( )");
    public static JavaMethod A128add = A128.getMethod("add( int )");
    public static JavaField A128z = A128.getField("z");
    public static JavaField A128y = A128.getField("y");
    
    public static JavaClass Test129 = SliceProject.getClass("Test129");
    public static JavaMethod Test129m = Test129.getMethod("m( )");
    public static JavaField Test129s1 = Test129.getField("s1");
    public static JavaField Test129s2= Test129.getField("s2");
    
    public static JavaClass S129 = SliceProject.getClass("S129");
    public static JavaMethod S129getP = S129.getMethod("getP( )");
    public static JavaField S129p = S129.getField("p");
    
    public static JavaClass T129 = SliceProject.getClass("T129");
    public static JavaMethod T129set1 = T129.getMethod("set1( java.lang.String java.lang.String )");
    public static JavaMethod T129get1 = T129.getMethod("get1( java.lang.String )");
    public static JavaField T129p = T129.getField("p");
    
    public static JavaClass U129 = SliceProject.getClass("U129");
    public static JavaMethod U129set1 = U129.getMethod("set1( java.lang.String java.lang.String )");
    public static JavaMethod U129get1 = U129.getMethod("get1( java.lang.String )");
    public static JavaField U129t = U129.getField("t");
    
    public static JavaClass P129 = SliceProject.getClass("P129");
    public static JavaMethod P129set1 = P129.getMethod("set1( java.lang.String java.lang.String )");
    public static JavaMethod P129get1 = P129.getMethod("get1( java.lang.String )");
    public static JavaMethod P129set2 = P129.getMethod("set2( java.lang.String java.lang.String )");
    public static JavaMethod P129get2 = P129.getMethod("get2( java.lang.String )");
    public static JavaField P129map = P129.getField("map");
    public static JavaField P129value = P129.getField("value");
    public static JavaField P129key = P129.getField("key");
    
    
    public static JavaClass Test130 = SliceProject.getClass("Test130");
    public static JavaMethod Test130m = Test130.getMethod("m( )");
    public static JavaMethod Test130n = Test130.getMethod("n( int )");
    
    public static JavaClass S130 = SliceProject.getClass("S130");
    public static JavaMethod S130S130 = S130.getMethod("S130( int )");
    public static JavaMethod S130getX = S130.getMethod("getX( )");
    public static JavaField S130x = S130.getField("x");
    
    public static JavaClass Test131 = SliceProject.getClass("Test131");
    public static JavaMethod Test131m = Test131.getMethod("m( )");
    public static JavaField Test131map = Test131.getField("map");
    
    public static JavaClass Test132 = SliceProject.getClass("Test132");
    public static JavaMethod Test132m = Test132.getMethod("m( )");
    public static JavaMethod Test132n = Test132.getMethod("n( )");
    
    public static JavaClass P132 = SliceProject.getClass("P132");
    public static JavaField P132x = P132.getField("x");
    
    public static JavaClass Test133 = SliceProject.getClass("Test133");
    public static JavaMethod Test133m = Test133.getMethod("m( )");
    public static JavaMethod Test133getX = Test133.getMethod("getX( )");
    public static JavaMethod Test133getY = Test133.getMethod("getY( )");
    public static JavaMethod Test133getA = Test133.getMethod("getA( )");
    public static JavaField Test133x = Test133.getField("x");
    public static JavaField Test133y = Test133.getField("y");
    public static JavaField Test133a = Test133.getField("a");
    
    public static JavaClass Test134 = SliceProject.getClass("Test134");
    public static JavaMethod Test134m = Test134.getMethod("m( )");
    public static JavaMethod Test134a1 = Test134.getMethod("a1( )");
    public static JavaMethod Test134n1 = Test134.getMethod("n1( P134 int )");
    public static JavaMethod Test134a2 = Test134.getMethod("a2( )");
    public static JavaMethod Test134n2 = Test134.getMethod("n2( P134 int )( )");
    public static JavaMethod Test134inc = Test134.getMethod("inc( int )");
    public static JavaMethod Test134dec = Test134.getMethod("dec( int )");
    public static JavaMethod Test134getP = Test134.getMethod("getP( )");
    public static JavaMethod Test134getQ = Test134.getMethod("getQ( )");
    public static JavaMethod Test134getPQ = Test134.getMethod("getPQ( )");
    
    public static JavaClass P134 = SliceProject.getClass("P134");
    public static JavaMethod P134f = P134.getMethod("f( int )");
    
    public static JavaClass Q134 = SliceProject.getClass("Q134");
    public static JavaMethod Q134f = Q134.getMethod("f( int )");
    
    public static JavaClass R134 = SliceProject.getClass("r134");
    public static JavaMethod R134f = R134.getMethod("f( int )");
    
    public static JavaClass I134 = SliceProject.getClass("I134");
    public static JavaMethod I134f = I134.getMethod("f( int )");
    
    public static JavaClass Test135 = SliceProject.getClass("Test135");
    public static JavaMethod Test135m = Test135.getMethod("m( )");
    public static JavaField Test135s1 = Test135.getField("s1");
    public static JavaField Test135s2 = Test135.getField("s2");
    
    public static JavaClass S135 = SliceProject.getClass("S135");
    public static JavaMethod S135set2 = S135.getMethod("set2( java.lang.String java.lang.String )");
    public static JavaMethod S135get2 = S135.getMethod("get2( java.lang.String )");
    public static JavaField S135value = S135.getField("value");
    public static JavaField S135key = S135.getField("key");
    
    public static JavaClass Test136 = SliceProject.getClass("Test136");
    public static JavaMethod Test136m =Test136.getMethod("m( )");
    public static JavaField Test136s1 = Test136.getField("s1");
    public static JavaField Test136s2 = Test136.getField("s2");
    
    public static JavaClass S136 = SliceProject.getClass("S136");
    public static JavaMethod S136set2 = S136.getMethod("set( java.lang.String java.lang.String )");
    public static JavaMethod S136get2 = S136.getMethod("get( java.lang.String )");
    public static JavaField S136map = S136.getField("map");
    
    public static JavaClass Test137 = SliceProject.getClass("Test137");
    public static JavaMethod Test137m = Test137.getMethod("m( )");
    
    public static JavaClass S137 = SliceProject.getClass("S137");
    public static JavaMethod S137add = S137.getMethod("add( java.lang.String )");
    public static JavaMethod S137get = S137.getMethod("get( int )");
    public static JavaField S137p = S137.getField("p");
    
    public static JavaClass P137 = SliceProject.getClass("P137");
    public static JavaMethod P137add = P137.getMethod("add( java.lang.String )");
    public static JavaMethod P137get = P137.getMethod("get( int )");
    public static JavaField P137list = P137.getField("list");
    
    public static JavaClass Test138 = SliceProject.getClass("Test138");
    public static JavaMethod Test138m = Test138.getMethod("add( java.lang.String )");
    
    public static JavaClass P138 = SliceProject.getClass("P138");
    public static JavaMethod P138add = P138.getMethod("add( java.lang.String )");
    public static JavaField P138list = P138.getField("list");
    
    public static JavaClass Test200 = SliceProject.getClass("Test200");
    public static JavaMethod Test200m = Test200.getMethod("m( int )");
    
    public static JavaClass Customer = SliceProject.getClass("Customer");
    public static JavaMethod Customer_Customer = Customer.getMethod("Customer( java.lang.String )");
    public static JavaMethod Customer_statement = Customer.getMethod("statement( Order )");
    public static JavaMethod Customer_getAmount = Customer.getMethod("getAmount( Order )");
    public static JavaMethod Customer_setDiscount = Customer.getMethod("setDiscount( double )");
    public static JavaField Customer_name = Customer.getField("name");
    public static JavaField Customer_discount = Customer.getField("discount");
    
    public static JavaClass CustomerTest = SliceProject.getClass("CustomerTest");
    public static JavaMethod CustomerTest_testStatement1 = CustomerTest.getMethod("testStatement1( )");
    public static JavaMethod CustomerTest_testStatement2 = CustomerTest.getMethod("testStatement2( )");
    
    public static JavaClass Rental = SliceProject.getClass("Rental");
    public static JavaMethod Rental_Rental = Rental.getMethod("Rental( int int )");
    public static JavaMethod Rental_getCharge = Rental.getMethod("getCharge( double )");
    public static JavaField Rental_price = Rental.getField("price");
    public static JavaField Rental_days = Rental.getField("days");
    
    public static JavaClass Order = SliceProject.getClass("Order");
    public static JavaMethod Order_addRental = Order.getMethod("addRental( Rental )");
    public static JavaMethod Order_getSize = Order.getMethod("getSize( )");
    public static JavaField Order_rentals = Customer.getField("rentals");
}
