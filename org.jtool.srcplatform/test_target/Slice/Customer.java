class Customer {
    private String name = "";
    public double discount = 0;

    public Customer(String name) {
        this.name = name;
    }

    public String statement(Order order) {
        if (order == null) {
            return "No order";
        }

        if (order.getSize() > 1 && discount < 0.2) {
            discount = discount * 2;
        }

        int amount = getAmount(order);

        return name + "'s amount: " + amount;
    }

    public int getAmount(Order order) {
        int amount = 0;
        for (Rental rental : order.rentals) {
            amount += rental.getCharge(discount);
        }
        return amount;
    }

    public void setDiscount(double dis) {
        this.discount = dis;
    }
}
