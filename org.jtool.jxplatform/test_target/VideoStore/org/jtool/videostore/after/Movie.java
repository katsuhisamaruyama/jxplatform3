package org.jtool.videostore.after;

import java.io.Serializable;

public class Movie implements Serializable {
    
    enum PriceCode {
        CHILDRENS, REGULAR, NEW_RELEASE
    };
    
    private String title;
    
    private Price price;
    
    public Movie(String title, PriceCode priceCode) {
        assert(title != null);
        this.title = title;
        setPriceCode(priceCode);
    }
    
    public String getTitle() {
        return title;
    }
    
    public PriceCode getPriceCode() {
        return price.getPriceCode();
    }
    
    private void setPriceCode(PriceCode priceCode) {
        switch (priceCode) {
            case REGULAR:
                price = new RegularPrice(priceCode);
                break;
            case NEW_RELEASE:
                price = new NewReleasePrice(priceCode);
                break;
            case CHILDRENS:
                price = new ChildrensPrice(priceCode);
                break;
        }
    }
    
    double getCharge(int daysRented) {
        return price.getCharge(daysRented);
    }
    
    int getFrequentRenterPoints(int daysRented) {
        return price.getFrequentRenterPoints(daysRented);
    }
}
