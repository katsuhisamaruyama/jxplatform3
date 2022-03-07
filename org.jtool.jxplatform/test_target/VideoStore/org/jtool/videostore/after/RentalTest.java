package org.jtool.videostore.after;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RentalTest {
    
    private static final Movie RegularMovie = new Movie("RegularMovie", Movie.PriceCode.REGULAR);
    private static final Movie NewReleaseMovie = new Movie("NewReleaseMovie", Movie.PriceCode.NEW_RELEASE);
    private static final Movie ChildrensMovie = new Movie("ChildrensMovie", Movie.PriceCode.CHILDRENS);
    
    @Test
    public void testRegularMovie() {
        Rental rental = new Rental(RegularMovie, 2);
        assertEquals("RegularMovie", rental.getMovie().getTitle());
        assertEquals(Movie.PriceCode.REGULAR, rental.getMovie().getPriceCode());
    }
    
    @Test
    public void testNewReleaseMovie() {
        Rental rental = new Rental(NewReleaseMovie, 1);
        assertEquals("NewReleaseMovie", rental.getMovie().getTitle());
        assertEquals(Movie.PriceCode.NEW_RELEASE, rental.getMovie().getPriceCode());
    }
    
    @Test
    public void testChildrensMovie() {
        Rental rental = new Rental(ChildrensMovie, 4);
        assertEquals("ChildrensMovie", rental.getMovie().getTitle());
        assertEquals(Movie.PriceCode.CHILDRENS, rental.getMovie().getPriceCode());
    }
    
    @Test
    public void testDaysRentedOfRegularMovie() {
        Rental rental = new Rental(RegularMovie, 2);
        assertEquals(2, rental.getDaysRented());
    }
    
    @Test
    public void testDaysRentedOfNewReleaseMovie() {
        Rental rental = new Rental(NewReleaseMovie, 1);
        assertEquals(1, rental.getDaysRented());
    }
    
    @Test
    public void testDaysRentedOfChildrensMovie() {
        Rental rental = new Rental(ChildrensMovie, 4);
        assertEquals(4, rental.getDaysRented());
    }
}
