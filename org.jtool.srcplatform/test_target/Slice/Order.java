import java.util.List;
import java.util.ArrayList;

class Order {
    List<Rental> rentals = new ArrayList<>();

    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    public int getSize() {
        return rentals.size();
    }
}
