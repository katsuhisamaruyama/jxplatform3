package org.jtool.videostore.after;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MovieTest {
    
    @Test
    public void testTitleOfRegularMovie() {
        Movie movie = new Movie("RegularMovie", Movie.PriceCode.REGULAR);
        assertEquals("RegularMovie", movie.getTitle());
    }
    
    @Test
    public void testTitleOfNewReleaseMovie() {
        Movie movie = new Movie("NewReleaseMovie", Movie.PriceCode.NEW_RELEASE);
        assertEquals("NewReleaseMovie", movie.getTitle());
    }
    
    @Test
    public void testTitleOfChildrensMovie() {
        Movie movie = new Movie("ChildrensMovie", Movie.PriceCode.CHILDRENS);
        assertEquals("ChildrensMovie", movie.getTitle());
    }
    
    @Test
    public void testPriceCodeOfRegularMovie() {
        Movie movie = new Movie("RegularMovie", Movie.PriceCode.REGULAR);
        assertEquals(Movie.PriceCode.REGULAR, movie.getPriceCode());
    }
    
    @Test
    public void testPriceCodeOfNewReleaseMovie() {
        Movie movie = new Movie("NewReleaseMovie", Movie.PriceCode.NEW_RELEASE);
        assertEquals(Movie.PriceCode.NEW_RELEASE, movie.getPriceCode());
    }
    
    @Test
    public void testPriceCodeOfChildrensMovie() {
        Movie movie = new Movie("ChildrensMovie", Movie.PriceCode.CHILDRENS);
        assertEquals(Movie.PriceCode.CHILDRENS, movie.getPriceCode());
    }
}
