public class Test45 {

    void m1() { 
        P45 a = new P45();
        Q45 b = a.q;
        int x1 = a.q.y;

        Q45 c = new Q45();
        a.q = c;
        int x2 = a.q.y;
    }

    void m2() { 
        P45 a = new P45();
        Q45 b = a.q;
        int x1 = a.q.y;

        P45 c = new P45();
        a = c;
        int x2 = a.q.y;
    }
}

class P45 {
    Q45 q = new Q45();
}

class Q45 {
    int y;
}
