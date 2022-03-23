
public class Test35 {

    public void m1() {
        int x = 0;
        while (x < 10) {
            if (x == 5) {
                break;
            }
            x++;
        }
        int y = 0;
    }

    public void m2() {
        int x = 0;
        while (x < 10) {
            if (x == 5) {
                x = x + 2;
                continue;
            }
            x++;
        }
        int y = 0;
    }

    public void m3() {
        LOOP1: for (int x = 0; x < 10; x++) {
            LOOP2: for (int y = 10; y >= 0; y--) {
                if (x == y) {
                    break LOOP1;
                }
            }
        }
        int y = 0;
    }

    public void m4() {
        LOOP1: for (int x = 0; x < 10; x++) {
            LOOP2: for (int y = 10; y >= 0; y--) {
                if (x == y) {
                    continue LOOP1;
                }
            }
        }
        int y = 0;
    }
}
