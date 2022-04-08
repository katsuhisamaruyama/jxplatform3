class Test41 {

    void m() {
        PriceCode c1 = PriceCode.CHILDRENS;
    }
}
    
enum PriceCode {
    CHILDRENS(100),
    REGULAR(200),
    NEW_RELEASE(300);

    PriceCode(int priceCode) {
    }
}
