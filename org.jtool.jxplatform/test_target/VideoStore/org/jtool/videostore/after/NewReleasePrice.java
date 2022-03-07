package org.jtool.videostore.after;

public class NewReleasePrice extends Price {
    
    public NewReleasePrice(Movie.PriceCode priceCode) {
        super(priceCode);
    }
    
    @Override
    public double getCharge(int daysRented) {
        return daysRented * 3.0;
    }
    
    @Override
    public int getFrequentRenterPoints(int daysRented) {
        if (daysRented > 1)
            return 2;
        else
            return 1;
    }
}
