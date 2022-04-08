import java.util.ArrayList;

class Test48 {

    void m1() {
        P48 p = new P48();
        p.s.m1();
        p.ss.m1();
        p.str.length();
    }

    void m2() {
        P48 p = new P48();
        p.t.m1();
        p.t.m2();
        p.t.m3();
        try {
            p.str.wait();
        } catch (InterruptedException e) { }
    }
    
    void m3() {
        P48 p = new P48();
        p.getS().m1();
        p.getSS().m1();
        p.getStr().length();
    }

    void m4() {
        P48 p = new P48();
        p.getT().m1();
        p.getT().m2();
        p.getT().m3();
    }

    void m5() {
        P48 p = new P48();
        S48 s = p.getS();
        s.m1();
        s.m2();
    }

    void m6() {
        S48 s = new S48();
        s.m1();
        s.m2();
        s.toString();
    }

    void m7() {
        T48 t = new T48();
        t.m1();
        t.m2();
        t.m3();
        t.toString();
    }

    void m8() {
        S48 t = new T48();
        t.m1();
        t.m2();
        t.toString();
    }

    void m9(S48 s) {
        s.m1();
    }

    void n9() {
        m9(new S48());
    }    
    
    void m10(S48 s) {
        s.m1();
    }

    void n10() {
        S48 s = new S48();
        m10(s);
    }    

    void m11(S48 s) {
        s.m1();
    }

    void n11(S48 s) {
        m11(s);
    }    

    void l11(S48 s) {
        n11(new S48());
    }    

    void m12(S48 s) {
        s.m1();
    }

    void n12(S48 s) {
        m12(s);
    }    

    void m13(S48 s) {
        s.m1();
        s.m2();
    }

    void n13() {
        m13(new T48());
    }    

    void m14() {
        P48 p = new P48();
        ArrayList<String> l = p.getArrayList();
        l.size();
    }

    void m15() {
        String str = "xyz";
        str.substring(1).length();
        try {
            str.substring(1).wait();
        } catch (InterruptedException e) { }
    }

    void m16(S48 s) {
        s.m1();
        s.m2();
    }
}

class P48 {
    S48 s = new S48();
    T48 t = new T48();
    S48 ss;
    String str = "ABC";

    S48 getS() {
        return s;
    }

    S48 getSS() {
        return ss;
    }

    T48 getT() {
        return t;
    }

    S48 getTT() {
        return t;
    }

    ArrayList<String> getArrayList() {
        return new ArrayList<>();
    }

    String getStr() {
        return str;
    }
}

class S48 {
    void m1() {
    }

    void m2() {
    }
}

class T48 extends S48 {

    void m1() {
    }

    void m3() {
    }
}
