import java.util.List;
import java.util.ArrayList;

class Test134 {

    public void m() {
        int x = 1;
        
        //int y1 = inc(x);
        //int y2 = dec(x);
        
        //P134 p = new P134();
        //int z = p.f(x);
        
        //Q134 q1 = new Q134();
        //int z1 = q1.f(x);
        //P134 q2 = new Q134();
        //int z2 = q2.f(x);
        //P134 q3 = q1;
        //int z3 = q3.f(x);
        //P134 q4 = q2;
        //int z4 = q4.f(x);
        
        //R134 r1 = new R134();
        //int e1 = r1.f(x);
        //I134 r2 = new R134();
        //int e2 = r2.f(x);
        //P134 r3 = new R134();
        //int e3 = r3.f(x);
        
        //P134 p2 = getP();
        //int d1 = p2.f(x);
        //Q134 p3 = getQ();
        //int d2 = p3.f(x);
        //P134 p4 = getPQ();
        //int d3 = p4.f(x);
        
        /*
        P134 pp;
        if (x > 1) {
            pp = new Q134();
        } else {
            pp = new R134();
        }
        int dd = pp.f(x);
        */
        
        //ArrayList<String> l1 = new ArrayList<>();
        //l1.add("A");
        
        //List<String> l2 = new ArrayList<>();
        //l2.add("B");
    }
    
    public void a1() {
        //this.n1(new P134(), 1);
    }
    
    public void n1(P134 p, int x) {
        //int z = p.f(x);
    }
    
    public void a2() {
        n2(new Q134(), 1);
    }
    
    public void n2(P134 p, int x) {
        int z = p.f(x);
    }
    
    public int inc(int a) {
        return a + 1;
    }
    
    public int dec(int a) {
        return a - 1;
    }
    
    P134 getP() {
        return new P134();
    }
    
    Q134 getQ() {
        return new Q134();
    }
    
    P134 getPQ() {
        return new Q134();
    }
}

class P134 {
    public int f(int a) {
        return a + 10;
    }
}

class Q134 extends P134 {
    public int f(int a) {
        return a + 20;
    }
}

class R134 extends Q134 implements I134 {
    public int f(int a) {
        return a + 30;
    }
}

interface I134 {
    public int f(int a);
}

