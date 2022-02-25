class Test124 {
    private int p;
    
    public void m() {
        int p = 10;
        int q = 20;
        A124 a = new A124(p);
        int b = a.getX();
        int c = a.inc(q);
    }
}

class A124 {
    public int x;

    public A124(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int inc(int y) {
        return x + y;
    }
}
