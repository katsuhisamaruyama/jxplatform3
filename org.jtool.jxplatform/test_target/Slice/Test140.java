
class Test140 {
    protected int x;

    Test140(int x) {
        this.x = x;
    }

    public void m() {
        m();
    }
}

class S140 extends Test140 {
    protected int x;

    S140() {
        this(100);
    }

    S140(int x) {
        super(x);
    }

    public void m() {
        int xx = super.x;
        super.m();
    }
}
