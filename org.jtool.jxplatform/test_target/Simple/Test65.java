public class Test65 {
    
    public int getPrice(Book book, int x) {
        int y = x + 10;
        return book.getPrice(y);
    }
    
    public int m(String title, int price) {
        int sum = 0;
        for (int i = 1; i < 5; i++) {
            Book book = new Book(title, i * 100);
            if (true) {
                sum += getPrice(book, 0);
            }
        }
        return sum;
    }
}

class Book {
    private String title;
    private int price;
    
    public Book(String title, int price) {
        this.title = title;
        this.price = price;
    }
    
    public int getPrice() {
        return price;
    }
    
    public int getPrice(int p) {
        return price + p;
    }
}
