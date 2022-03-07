package org.jtool.videostore.after;

public abstract class Price {
    
    private Movie.PriceCode priceCode;
    
    public Price(Movie.PriceCode priceCode) {
        this.priceCode = priceCode;
    }
    
    public Movie.PriceCode getPriceCode() {
        return priceCode;
    }
    
    public abstract double getCharge(int daysRented);
    
    public int getFrequentRenterPoints(int daysRented) {
        return 1;
    }
}
