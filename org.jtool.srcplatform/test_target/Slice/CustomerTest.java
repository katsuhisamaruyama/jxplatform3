
public class CustomerTest {

    public void testStatement1() {
        Customer customer = new Customer("C1");
        customer.setDiscount(0.1);
        Order order = new Order();
        Rental r1 = new Rental(200, 2);
        order.addRental(r1);

        String message = customer.statement(order);
    }
}
