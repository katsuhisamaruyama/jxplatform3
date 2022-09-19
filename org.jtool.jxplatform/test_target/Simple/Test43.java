public class Test43 {

    void m1() { 
        P43 a = new P43(1);
        P43 b = a;
        P43 c = b;
        int x1 = a.getX();
        int x2 = b.getX();

        P43 d = new P43(2);
        a = d;
        int x3 = a.getX();
        int x4 = b.getX();
        int x5 = d.getX();
    }

    void m2() { 
        P43 a = new P43(1);
        P43 b = a;
        int x1 = a.getX();
        int x2 = b.getX();

        P43 c = new P43(2);
        b = c;
        int x3 = a.getX();
    }
}

class P43 {

    int x;

    P43(int x) {
        this.x = x;
    }

    int getX() {
        return x;
    }
}
