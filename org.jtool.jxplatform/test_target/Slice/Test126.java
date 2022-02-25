class Test126 {
    private int p = 0;

    public void m() {
        A126 a = new A126();
        int p = 0;
        a.y = 1;
        A126 a2 = a.add(p);
        a2.y = 2;
        int q = a2.getY();
        int r = a.add(p).getY();
        A126 a3 = new A126();
        a3.setY(3);
        int s = a3.getY();
    }
}

class A126 {
    int y = 0;

    public A126() {
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public A126 add(int x) {
        A126 ret = new A126();
        ret.setY(y + x);
        return ret;
    }
}
