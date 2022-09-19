public class Test21 {

    final public void m() {
        int a = 0;
        for (int i = 0; i < 10; i++) {
            a++;
        }
    }

    public void m2() {
        int i = 0;
        for (; i < 10; i++) {
        }
    }

    public void m3() {
        for (int i = 0; ; i++) {
            if (i == 10) break;
        }
    }

    public void m4() {
        for (int i = 0; i < 10; ) {
        }
    }
}
