package org.jtool.videostore.after;

class RegularPrice extends Price {
    
    public RegularPrice(Movie.PriceCode priceCode) {
        super(priceCode);
    }
    
    @Override
    public double getCharge(int daysRented) {
        double result = 2.0;
        if (daysRented > 2)
            result += (daysRented - 2) * 1.5;
        return result;
    }
}
