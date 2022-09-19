public class Test47 {

    void m1() {
        int len = "xyz".length();
        try {
            "xyz".wait();
        } catch (InterruptedException e) { }

        String str = "xyz";
        str.length();
    }

    void m2() {
        String name = Test47.class.getName();
        try {
            Test47.class.wait();
        } catch (InterruptedException e) { }

        Class clazz = Test47.class;
        clazz.getName();
    }

    void m3() {
        I47 p = new P47();
        ((P47)p).set("A");
    }

    void m4() {
        P47 p1 = new P47();
        P47 p2 = new P47("AA");
    }

    void m5() {
        int max = Math.max(1, 2);
    }

    void m6() {
        m1();
    }

    void m7() {
        Direction d = Direction.UP;
        String name1 = d.getName();
        String name2 = d.name();
    }
}

interface I47 {
    public String get();
}

class P47 implements I47 {
    private String value;

    P47() {
    }
    
    P47(String value) {
        this.value = value;
    }

    public void set(String value) {
        this.value = value;
    }

    public String get() {
        return value;
    }
}

enum Direction {
    UP, DOWN;

    String getName() {
        return name();
    }
}
