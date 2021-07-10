class Rental {
    private int price;
    private int days;

    public Rental(int price, int days) {
        this.price = price;
        this.days = days;
    }

    public int getCharge(double discount) {
        double charge = Math.floor((price * days) * (1 - discount));
        return (int)charge;
    }
}
