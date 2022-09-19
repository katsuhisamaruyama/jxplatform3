public class Test32 {

    public void m() {
        int a = 0;
        int b;
        try {
            b = n(a);
        } catch (Exception e) {
            Exception f = e;
        } finally {
            b = a;
        }
    }

    public int n(int x) throws Exception {
        if (x == 0) {
            throw new Exception();
        }
        return 10 / x;
    }
}
