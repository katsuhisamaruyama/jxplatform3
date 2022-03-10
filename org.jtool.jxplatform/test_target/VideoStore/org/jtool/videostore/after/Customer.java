package org.jtool.videostore.after;

import java.util.List;
import java.util.ArrayList;

public class Customer {
    
    String name;
    
    private List<Rental> rentals = new ArrayList<>();
    
    public Customer(String name) {
        this.name = name;
    }
    
    public void addRental(Rental rental) {
        this.rentals.add(rental);
    }
    
    public String getName() {
        return name;
    }
    
    public String statement() {
        String result = "Rental Record for " + getName() + "\n";
        
        for (Rental each : rentals) {
            result += "\t" + each.getMovie().getTitle() + "\t" +
                String.valueOf(each.getCharge()) + "\n";
        }
        
        result += "Amount owed is " + String.valueOf(getTotalCharge()) + "\n";
        result += "You earned " + String.valueOf(getTotalFrequentRenterPoints()) +
            " frequent renter points";
        return result;
    }
    
    private double getTotalCharge() {
        double result = 0;
        for (Rental each : rentals) {
            result += each.getCharge();
        }
        return result;
    }
    
    private int getTotalFrequentRenterPoints() {
        int result = 0;
        for (Rental each : rentals) {
            result += each.getFrequentRenterPoints();
        }
        return result;
    }
}
