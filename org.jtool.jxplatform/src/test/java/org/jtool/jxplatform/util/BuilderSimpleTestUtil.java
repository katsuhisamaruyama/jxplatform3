/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.util;

import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaProject;

public class BuilderSimpleTestUtil {
    
    public static JavaProject SimpleProject = BuilderTestUtil.createProject("Simple", "", "");
    
    public static JavaClass Test01 = SimpleProject.getClass("Test01");
    public static JavaMethod Test01m = Test01.getMethod("m( )");
    public static JavaField Test01ABC = Test01.getField("ABC");
    
    public static JavaClass Test02 = SimpleProject.getClass("Test02");
    public static JavaMethod Test02m = Test02.getMethod("m( )");
    
    public static JavaClass Test03 = SimpleProject.getClass("Test03");
    public static JavaMethod Test03m = Test03.getMethod("m( )");
    
    public static JavaClass Test04 = SimpleProject.getClass("Test04");
    public static JavaMethod Test04m = Test04.getMethod("m( )");
    public static JavaMethod Test04doReturn = Test04.getMethod("doReturn( int )");
    
    public static JavaClass Test05 = SimpleProject.getClass("Test05");
    public static JavaMethod Test05m = Test05.getMethod("m( )");
    
    public static JavaClass Test06 = SimpleProject.getClass("Test06");
    public static JavaMethod Test06m = Test06.getMethod("m( )");
    
    public static JavaClass Test07 = SimpleProject.getClass("Test07");
    public static JavaMethod Test07m = Test07.getMethod("m( )");
    
    public static JavaClass Test08 = SimpleProject.getClass("Test08");
    public static JavaMethod Test08m = Test08.getMethod("m( )");
    
    public static JavaClass Test09 = SimpleProject.getClass("Test09");
    public static JavaMethod Test09m = Test09.getMethod("m( )");
    
    public static JavaClass Test10 = SimpleProject.getClass("Test10");
    public static JavaMethod Test10m = Test10.getMethod("m( )");
    
    public static JavaClass Test11 = SimpleProject.getClass("Test11");
    public static JavaMethod Test11m = Test11.getMethod("m( )");
    public static JavaMethod Test11doReturn = Test11.getMethod("doReturn( int )");
    
    public static JavaClass Test12 = SimpleProject.getClass("Test12");
    public static JavaMethod Test12m = Test12.getMethod("m( )");
    
    public static JavaClass Test13 = SimpleProject.getClass("Test13");
    public static JavaMethod Test13m = Test13.getMethod("m( )");
    public static JavaMethod Test13doReturn = Test13.getMethod("doReturn( int )");
    
    public static JavaClass Test14 = SimpleProject.getClass("Test14");
    public static JavaMethod Test14m = Test14.getMethod("m( )");
    
    public static JavaClass Test15 = SimpleProject.getClass("Test15");
    public static JavaMethod Test15m = Test15.getMethod("m( )");
    
    public static JavaClass Test16 = SimpleProject.getClass("Test16");
    public static JavaMethod Test16m = Test16.getMethod("m( )");
    
    public static JavaClass Test17 = SimpleProject.getClass("Test17");
    public static JavaMethod Test17m = Test17.getMethod("m( )");
    
    public static JavaClass Test18 = SimpleProject.getClass("Test18");
    public static JavaMethod Test18m = Test18.getMethod("m( )");
    
    public static JavaClass Test19 = SimpleProject.getClass("Test19");
    public static JavaMethod Test19m = Test19.getMethod("m( )");
    
    public static JavaClass Test20 = SimpleProject.getClass("Test20");
    public static JavaMethod Test20Test20 = Test20.getMethod("Test20( int )");
    public static JavaMethod Test20get = Test20.getMethod("get( )");
    public static JavaMethod Test20calc = Test20.getMethod("calc( int )");
    public static JavaMethod Test20main = Test20.getMethod("main( java.lang.String[] )");
    public static JavaField Test20priVar = Test20.getField("priVar");
    public static JavaField Test20priVar2 = Test20.getField("priVar2");
    
    public static JavaClass Test21 = SimpleProject.getClass("Test21");
    public static JavaMethod Test21m = Test21.getMethod("m( )");
    
    public static JavaClass Test22 = SimpleProject.getClass("Test22");
    public static JavaMethod Test22m = Test22.getMethod("m( )");
    
    public static JavaClass Test23 = SimpleProject.getClass("Test23");
    public static JavaMethod Test23m = Test23.getMethod("m( )");
    public static JavaMethod Test23inc = Test23.getMethod("inc( int )");
    public static JavaMethod Test23dec = Test23.getMethod("dec( int )");
    
    public static JavaClass Test24 = SimpleProject.getClass("Test24");
    public static JavaMethod Test24x = Test24.getMethod("x( )");
    
    public static JavaClass Test24P24 = SimpleProject.getClass("Test24$P24");
    public static JavaMethod Test24P24m = Test24P24.getMethod("m( )");
    
    public static JavaClass Test24Q24 = SimpleProject.getClass("Test24$Q24");
    public static JavaMethod Test24Q24n = Test24Q24.getMethod("n( )");
    
    public static JavaClass Test25 = SimpleProject.getClass("Test25");
    public static JavaMethod Test25main = Test25.getMethod("main( java.lang.String[] )");
    
    public static JavaClass P25 = SimpleProject.getClass("P25");
    public static JavaField P25x = P25.getField("x");
    
    public static JavaClass Q25 = SimpleProject.getClass("Q25");
    
    public static JavaClass Test26 = SimpleProject.getClass("Test26");
    public static JavaMethod Test26init = Test26.getInitializer();
    public static JavaField Test26xxx = Test26.getField("xxx");
    
    public static JavaClass Test27 = SimpleProject.getClass("Test27");
    public static JavaMethod Test27init = Test27.getMethod("init( )");
    public static JavaField Test27list = Test27.getField("list");
    public static JavaField Test27x = Test27.getField("x");
    
    public static JavaClass MockArrayList = SimpleProject.getClass("MockArrayList"); 
    
    public static void unbuild() {
        SimpleProject.getModelBuilder().unbuild();
    }
}
