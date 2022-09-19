public class Test31 {
    protected int x;

    Test31() {
    }
    
    Test31(int x) {
        this.x = x;
    }

    public void m() {
        m();
    }
}

class P31 extends Test31 {
    protected int x;

    P31() {
        this(100);
    }

    P31(int x) {
        this.x = x;
    }

    P31(int x, int y) {
        super(x);
        this.x = y;
    }

    public void m() {
        int xx = super.x;
        super.m();
    }

    public void n() {
        P31 p = this;
    }
}
