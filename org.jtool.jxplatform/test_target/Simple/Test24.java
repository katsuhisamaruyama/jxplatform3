public class Test24 {

    public void x() {
        P24 p = new P24();
        p.m();
        
        P24 q = new Q24();
        q.m();
        ((Q24)q).n();
    }

    protected class P24 {
        void m() {
        }
    }

    final private class Q24 extends P24 {
        void n() {
        }
    }
}
