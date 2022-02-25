public class Test25 {

    public static void main(String[] args) {
        P25 p = new P25();
        p.x = 1;
        System.out.println(p.x);

        Q25 q = new Q25();
        q.x = 2;
        System.out.println(q.x);

        P25 a = q;
        a.x = 3;
        System.out.println(a.x);
        System.out.println(q.x);
    }
}

class P25 {
    protected int x;
}

class Q25 extends P25 {
}
