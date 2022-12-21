class Test120 {

    public void m() {
        A120 a = new A120();
        int p = m0(a.x, a.y);
        int q = m1(a.x, a.y);
        int r = m2(a.x, a.y);
        int s = m3(a.x, a.y);
    }

    public int m0(int a, int b) {
        return 1;
    }

    public int m1(int a, int b) {
        return a + 1;
    }

    public int m2(int a, int b) {
        return b + 1;
    }

    public int m3(int a, int b) {
        return a + b;
    }
}

class A120 {
    int x = 1;
    int y = 2;
}
