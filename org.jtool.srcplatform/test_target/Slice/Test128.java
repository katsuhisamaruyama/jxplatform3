class Test128 {
    private int p = 0;

    public void m() {
        A128 a = new A128();
        int p = 0;
        a.setY(2);
        int r = n(0, a.add(p).getY());
    }

    public int n(int x, int y) {
        return y + 4;
    }
}

class A128 {

    static int z;
    int y = 0;

    public A128() {
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public A128 add(int x) {
        A128 ret = new A128();
        ret.setY(y + x);
        return ret;
    }
}
