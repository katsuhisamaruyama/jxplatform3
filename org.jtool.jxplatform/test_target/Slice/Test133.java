class Test133 {

    Test133 x = new Test133();
    Test133 y = new Test133();
    int a = 1;
    
    public void m() {
        int p1 = a;
        int p2 = x.a;
        int p3 = x.getA();
        int p4 = getX().a;
        int p5 = getX().getA();
        int p6 = x.y.getA();
        int p7 = getX().getY().a;
        int p8 = getX().getY().getA();
        int p9 = getX().y.getA();
        int p10 = x.getY().getA();
    }

    public Test133 getX() {
        return x;
    }

    public Test133 getY() {
        return y;
    }

    public int getA() {
        return a;
    }
}
