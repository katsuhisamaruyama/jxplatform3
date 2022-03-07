package org.jtool.videostore.before;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

public class CustomerTest {
    
    private static final Movie RegularMovie = new Movie("RegularMovie", Movie.PriceCode.REGULAR);
    private static final Movie NewReleaseMovie = new Movie("NewReleaseMovie", Movie.PriceCode.NEW_RELEASE);
    private static final Movie ChildrensMovie = new Movie("ChildrensMovie", Movie.PriceCode.CHILDRENS);
    
    private String getHeader(String name) {
        return "Rental Record for " + name + "\n";
    }
    
    private String getEach(String rental, double charge) {
        return "\t" + rental + "\t" + String.valueOf(charge)+ "\n";
    }
    
    private String getTotal(double total, int renterPointsEarned) {
        return "Amount owed is " + String.valueOf(total) + "\n" +
            "You earned " + String.valueOf(renterPointsEarned) + " frequent renter points";
    }
    
    @Test
    public void testName() {
        Customer customer = new Customer("Alice");
        assertEquals("Alice", customer.getName());
    }
    
    @Test(expected = AssertionError.class)
    public void testNullName() {
        @SuppressWarnings("unused")
        Customer customer = new Customer(null);
        fail();
    }
    
    @Test(expected = AssertionError.class)
    public void testEmptyName() {
        Customer customer = new Customer("");
        assertNull(customer.getName());
    }
    
    @Test
    public void testStatementForRentingRegularMovie() {
        Customer customer = new Customer("Alice");
        customer.addRental(new Rental(RegularMovie, 2));
        assertEquals(getHeader("Alice") + getEach("RegularMovie", 2.0)
                + getTotal(2.0, 1), customer.statement());
    }
    
    @Test
    public void testStatementForRentingNewReleaseMovie() {
        Customer customer = new Customer("Alice");
        customer.addRental(new Rental(NewReleaseMovie, 3));
        assertEquals(getHeader("Alice") + getEach("NewReleaseMovie", 9.0)
                + getTotal(9.0, 2), customer.statement());
    }
    
    @Test
    public void testStatementForRentingChildrensMovie() {
        Customer customer = new Customer("Alice");
        customer.addRental(new Rental(ChildrensMovie, 4));
        assertEquals(getHeader("Alice") + getEach("ChildrensMovie", 3.0)
                + getTotal(3.0, 1), customer.statement());
    }
    
    @Test(expected = AssertionError.class)
    public void testStatementZeroDaysRented() {
        Customer customer = new Customer("Alice");
        customer.addRental(new Rental(RegularMovie, 0));
        fail();
    }
    
    @Test(expected = AssertionError.class)
    public void testStatementMinusDaysRented() {
        Customer customer = new Customer("Alice");
        customer.addRental(new Rental(RegularMovie, -1));
        assertEquals(getHeader("Alice"), customer.statement() + "\n");
    }
    
    @Test
    public void testStatementForRentingTwoMovies() {
        Customer customer = new Customer("Bob");
        customer.addRental(new Rental(RegularMovie, 2));
        customer.addRental(new Rental(NewReleaseMovie, 4));
        assertEquals(getHeader("Bob") + getEach("RegularMovie", 2.0)
                + getEach("NewReleaseMovie", 12.0)
                + getTotal(14.0, 3), customer.statement());
    }
    
    @Test(expected = AssertionError.class)
    public void testStatementForRentingTwoMoviesZeroDaysRented() {
        Customer customer = new Customer("Y");
        customer.addRental(new Rental(RegularMovie, 1));
        customer.addRental(new Rental(NewReleaseMovie, 0));
        fail();
    }
    
    @Test
    public void testStatementOfThreeMovies1() {
        Customer customer = new Customer("Carol");
        customer.addRental(new Rental(RegularMovie, 4));
        customer.addRental(new Rental(NewReleaseMovie, 4));
        customer.addRental(new Rental(ChildrensMovie, 4));
        assertEquals(getHeader("Carol")
                + getEach("RegularMovie", 5.0)
                + getEach("NewReleaseMovie", 12.0)
                + getEach("ChildrensMovie", 3.0)
                + getTotal(20.0, 4), customer.statement());
    }
    
    @Test(expected = AssertionError.class)
    public void testStatementRentingThreeMoviesZeroDaysRented() {
        Customer customer = new Customer("Z");
        customer.addRental(new Rental(RegularMovie, 1));
        customer.addRental(new Rental(NewReleaseMovie, 2));
        customer.addRental(new Rental(ChildrensMovie, 0));
        fail();
    }
    
    @Test
    public void testZeroRental() {
        Customer customer = new Customer("Dave");
        assertEquals(getHeader("Dave") + getTotal(0.0, 0), customer.statement());
    }
    
    @Test(expected = AssertionError.class)
    public void testNullRental() {
        Customer customer = new Customer("Dave");
        customer.addRental(new Rental(RegularMovie, 1));
        customer.addRental(null);
        fail();
    }
    
    @Test(expected = AssertionError.class)
    public void testNullMovie() {
        Customer customer = new Customer("Dave");
        customer.addRental(new Rental(new Movie(null, Movie.PriceCode.REGULAR), 2));
        fail();
    }
}
