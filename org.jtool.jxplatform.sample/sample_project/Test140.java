
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
        int xx = this.x;
    }

    S140(int x) {
        super(x);
        this.x = x;
        int xx = super.x;
    }

    public void m() {
        super.x = 10;
        super.m();
        int xx = super.x;
    }
}
