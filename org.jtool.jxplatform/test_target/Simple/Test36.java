public class Test36 {

    public void m() {
        int a = 0;
        int b;
        try {
            b = n(a);
        } catch (RuntimeException e) {
            Exception f = e;
        } finally {
            b = a;
        }
    }

    public int n(int x) {
        if (x == 0) {
            throw new RuntimeException();
        }
        return 10 / x;
    }
}
