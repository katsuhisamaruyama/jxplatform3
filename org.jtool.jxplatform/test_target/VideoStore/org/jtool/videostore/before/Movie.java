package org.jtool.videostore.before;

public class Movie {
    
    enum PriceCode {
        CHILDRENS, REGULAR, NEW_RELEASE
    };
    
    private String title;
    
    private PriceCode priceCode;
    
    public Movie(String title, PriceCode priceCode) {
        this.title = title;
        this.priceCode = priceCode;
    }
    
    public String getTitle() {
        return title;
    }
    
    public PriceCode getPriceCode() {
        return priceCode;
    }
    
    public void setPriceCode(PriceCode priceCode) {
        this.priceCode = priceCode;
    }
}
