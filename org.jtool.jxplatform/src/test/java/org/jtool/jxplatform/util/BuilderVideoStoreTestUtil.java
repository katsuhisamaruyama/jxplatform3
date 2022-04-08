/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.util;

import org.jtool.srcmodel.JavaClass;
import org.jtool.srcmodel.JavaMethod;
import org.jtool.srcmodel.JavaField;
import org.jtool.srcmodel.JavaProject;

public class BuilderVideoStoreTestUtil {
    
    public static JavaProject VideoStoreProject = BuilderTestUtil.createProject("VideoStore", "/lib/*", "");
    
    public static JavaClass afterCustomer = VideoStoreProject.getClass("org.jtool.videostore.after.Customer");
    
    public static JavaMethod afterCustomer_Customer = afterCustomer.getMethod("Customer( java.lang.String )");
    public static JavaMethod afterCustomer_addRental = afterCustomer.getMethod("addRental( org.jtool.videostore.after.Rental )");
    public static JavaMethod afterCustomer_getName = afterCustomer.getMethod("getName( )");
    public static JavaMethod afterafterCustomer_statement = afterCustomer.getMethod("statement( )");
    public static JavaMethod Customer_getTotalCharge = afterCustomer.getMethod("getTotalCharge( )");
    public static JavaMethod afterCustomer_getTotalFrequentRenterPoints = afterCustomer.getMethod("getTotalFrequentRenterPoints( )");
    
    public static JavaField Customer_name = afterCustomer.getField("name");
    public static JavaField Customer_rentals = afterCustomer.getField("rentals");
    
    public static JavaClass afterRental = VideoStoreProject.getClass("org.jtool.videostore.after.Rental");
    
    public static JavaMethod afterRental_Rental = afterRental.getMethod("Rental( org.jtool.videostore.after.Movie int )");
    public static JavaMethod afterRental_getMovie = afterRental.getMethod("getMovie( )");
    public static JavaMethod afterRental_getDaysRented = afterRental.getMethod("getDaysRented( )");
    public static JavaMethod afterRental_getCharge = afterRental.getMethod("getCharge( )");
    public static JavaMethod afterRental_getFrequentRenterPoints = afterRental.getMethod("getFrequentRenterPoints( )");
    
    public static JavaField afterRental_movie = afterRental.getField("movie");
    public static JavaField afterRental_daysRented = afterRental.getField("daysRented");
    
    public static JavaClass afterMovie = VideoStoreProject.getClass("org.jtool.videostore.after.Movie");
    
    public static JavaMethod afterMovie_Movie = afterRental.getMethod("Movie( java.lang.String org.jtool.videostore.after.PriceCode )");
    public static JavaMethod afterMovie_getTitle = afterRental.getMethod("getTitle( )");
    public static JavaMethod afterMovie_getPriceCode = afterRental.getMethod("getPriceCode( )");
    public static JavaMethod afterMovie_setPriceCode = afterRental.getMethod("setPriceCode( java.lang.String org.jtool.videostore.after.PriceCode )");
    public static JavaMethod afterMovie_getCharge = afterRental.getMethod("getCharge( int )");
    public static JavaMethod afterMovie_getFrequentRenterPoints = afterRental.getMethod("getFrequentRenterPoints( int )");
    
    public static JavaField afterMovie_title = afterRental.getField("title");
    public static JavaField afterMovie_price = afterRental.getField("price");
    
    public static JavaClass afterMoviePriceCode = VideoStoreProject.getClass("org.jtool.videostore.after.Movie.PriceCode");
    
    public static JavaClass afterCHILDRENS = VideoStoreProject.getClass("org.jtool.videostore.after.Movie.PriceCode.CHILDRENS");
    public static JavaClass afterREGULAR = VideoStoreProject.getClass("org.jtool.videostore.after.Movie.PriceCode.REGULAR");
    public static JavaClass afterNEW_RELEASE = VideoStoreProject.getClass("org.jtool.videostore.after.Movie.PriceCode.NEW_RELEASE");
    
    public static JavaClass afterPrice = VideoStoreProject.getClass("org.jtool.videostore.after.Price");
    
    public static JavaMethod afterPrice_Price = afterPrice.getMethod("Price( org.jtool.videostore.after.Movie.PriceCode )");
    public static JavaMethod afterPrice_getPriceCode = afterPrice.getMethod("getPriceCode( )");
    public static JavaMethod afterPrice_getCharge = afterPrice.getMethod("getCharge( int )");
    public static JavaMethod afterPrice_getFrequentRenterPoints = afterPrice.getMethod("getFrequentRenterPoints( int )");
    
    public static JavaClass afterChildrensPrice = VideoStoreProject.getClass("org.jtool.videostore.after.ChildrensPrice");
    public static JavaMethod afterChildrensPrice_ChildrensPrice = afterChildrensPrice.getMethod("ChildrensPrice( org.jtool.videostore.after.Movie.PriceCode )");
    public static JavaMethod afterChildrensPrice_getCharge = afterChildrensPrice.getMethod("getCharge( int )");
    
    public static JavaClass afterRegularPrice = VideoStoreProject.getClass("org.jtool.videostore.after.RegularPrice");
    public static JavaMethod afterRegularPrice_RegularPrice = afterRegularPrice.getMethod("RegularPrice( org.jtool.videostore.after.Movie.PriceCode )");
    public static JavaMethod afterRegularPrice_getCharge = afterRegularPrice.getMethod("getCharge( int )");
    
    public static JavaClass  afterNewReleasePrice = VideoStoreProject.getClass("org.jtool.videostore.after. NewReleasePrice");
    public static JavaMethod afterNewReleasePrice_NewReleasePrice = afterNewReleasePrice.getMethod("NewReleasePrice( org.jtool.videostore.after.Movie.PriceCode )");
    public static JavaMethod afterNewReleasePrice_getCharge = afterNewReleasePrice.getMethod("getCharge( int )");
    public static JavaMethod afterNewReleasePrice_getFrequentRenterPoints = afterNewReleasePrice.getMethod("getFrequentRenterPoints( int )");
    
    public static void unbuild() {
        VideoStoreProject.getModelBuilder().unbuild();
    }
}
