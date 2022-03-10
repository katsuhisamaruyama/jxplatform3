package org.jtool.videostore.before;

import java.util.List;
import java.util.ArrayList;

public class Customer {
    
    private String name;
    
    private List<Rental> rentals = new ArrayList<>();
    
    public Customer(String name) {
        assert name != null && name.length() > 0;
        this.name = name;
    }
    
    public void addRental(Rental rental) {
        assert rental != null;
        this.rentals.add(rental);
    }
    
    public String getName() {
        return name;
    }
    
    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        String result = "Rental Record for " + getName() + "\n";
        
        for (Rental each : rentals) {
            double thisAmount = 0;
            
            switch (each.getMovie().getPriceCode()) {
                case REGULAR:
                    thisAmount += 2;
                    if (each.getDaysRented() > 2)
                        thisAmount += (each.getDaysRented() - 2) * 1.5;
                    break;
                case NEW_RELEASE:
                    thisAmount += each.getDaysRented() * 3;
                    break;
                case CHILDRENS:
                    thisAmount += 1.5;
                    if (each.getDaysRented() > 3)
                        thisAmount += (each.getDaysRented() - 3 ) * 1.5;
                    break;
            }
            
            frequentRenterPoints++;
            if (each.getMovie().getPriceCode() == Movie.PriceCode.NEW_RELEASE
                    && each.getDaysRented() > 1) {
                frequentRenterPoints++;
            }
            
            result += "\t" + each.getMovie().getTitle() + "\t" +
                    String.valueOf(thisAmount) + "\n";
            totalAmount += thisAmount;
        }
        
        result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
        result += "You earned " + String.valueOf(frequentRenterPoints) +
            " frequent renter points";
        return result;
    }
}
