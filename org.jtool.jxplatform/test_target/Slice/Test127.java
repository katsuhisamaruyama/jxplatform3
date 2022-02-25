class Test127 {
    private int p = 0;

    public void m() {
        A127 a = new A127();
        a.setY(2);
        A127.z = 1;
        int p = a.getY();
        int q = A127.z;
    }
}

class A127 {

    static int z;
    int y = 0;

    public A127() {
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }
}
