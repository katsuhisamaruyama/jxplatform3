
public class CustomerTest {

    public void testStatement1() {
        Customer customer = new Customer("C1");
        customer.setDiscount(0.1);
        Order order = new Order();
        Rental r1 = new Rental(200, 2);
        order.addRental(r1);
        
        String message = customer.statement(order);
        Order order2 = order;
    }

    public void testStatement2() {
        Customer customer = new Customer("C2");
        customer.setDiscount(0.1);
        Order order = new Order();
        Rental r1 = new Rental(200, 3);
        Rental r2 = new Rental(100, 3);
        order.addRental(r1);
        order.addRental(r2);
        
        String message = customer.statement(order);
    }
}
