public class Test06 {

    public void m() {
        int a = 0;
        int b = 1;

        if (a == 0) {
            switch(a) {
            case 0: b++;
                break;
            case 1: b++;
                break;
            case 2: b++;
                break;
            }
        } else {
            switch(b) {
            case 0: a++;
            case 1: a++;
                break;
            case 2: a++;
                break;
            }
        }

        System.out.println(a);
        System.out.println(b);
    }
}
