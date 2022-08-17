
class Test42 {

    private P42 a = new P42(1);
    private P42 b = a;

    void m1() { 
        int x1 = a.x;
        int x2 = b.x;
    }

    void m2() { 
        int x1 = a.getX();
        int x2 = b.getX();
    }
}

class P42 {

    int x;

    P42(int x) {
        this.x = x;
    }

    int getX() {
        return x;
    }
}
