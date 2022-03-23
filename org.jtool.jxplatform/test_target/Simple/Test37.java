public class Test37 {

    public void m1() {
        int x = 0;
        int y = 0;
        int z = 0;
        switch (x) {
        case 1:
            y = 10;
            break;
        default:
            x = 10;
        }
        int p = y;
    }

    public void m2() {
        int x = 0;
        int y = 0;
        int z = 0;
        switch (x) {
        default:
            x = 10;
            break;
        case 1:
            y = 10;
            break;
        }
        int p = y;
    }

    public void m3() {
        int x = 0;
        int y = 0;
        int z = 0;
        switch (x) {
        default:
            x = 10;
        case 1:
            y = 10;
            break;
        }
        int p = y;
    }
}
