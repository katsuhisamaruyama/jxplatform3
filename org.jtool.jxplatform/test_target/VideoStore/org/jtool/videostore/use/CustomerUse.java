package org.jtool.videostore.use;

import org.jtool.videostore.after.Customer;

public class CustomerUse {
    
    public void makeCustomer() {
        Customer customer = new Customer("Alice");
        System.out.println("NAME = " + customer.getName());
    }
}
