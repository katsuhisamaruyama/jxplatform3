
class Test139 {
    public void m() {
        PriceCode c1 = PriceCode.CHILDRENS;
        PriceCode c2 = PriceCode.REGULAR;

        int priceCode = PriceCode.REGULAR.getPriceCode();
        String movie = PriceCode.REGULAR.name();
    }
}
    
enum PriceCode {
    CHILDRENS(100),
    REGULAR(200),
    NEW_RELEASE(300);

    private int priceCode;

    PriceCode(int priceCode) {
        this.priceCode = priceCode;
    }

    int getPriceCode() {
        return priceCode;
    }
}
