public class Test44 {

    void m1() { 
        P44 a = new P44();
        Q44 b = a.q;
        int x1 = a.q.y;
        int x2 = a.q.getY();
        int x3 = b.y;
        int x4 = b.getY();
    }

    void m2() {
        P44 a = new P44();
        Q44 b = a.getQ();
        int x1 = a.getQ().y;
        int x2 = a.getQ().getY();
        int x3 = b.y;
        int x4 = b.getY();
    }

    void m3() {
        P44 a = new P44();
        Q44 b = a.getQ();
        int x1 = a.getQ().getR().y;
        int x2 = b.y;
    }

    void m4() {
        P44 a = new P44();
        Q44 b = a.getQ().getR();
        int x1 = a.getQ().getR().y;
        int x2 = b.y;
    }
}

class P44 {
    Q44 q = new Q44();

    Q44 getQ() {
        return q;
    }
}

class Q44 {
    int y;
    Q44 r;
    
    int getY() {
        return y;
    }

    Q44 getR() {
        return r;
    }
}
