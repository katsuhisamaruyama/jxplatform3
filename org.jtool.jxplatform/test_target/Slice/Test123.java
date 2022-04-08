class Test123 {

    public void m() {
        int a = 2;
        int b = 0;
        try {
            int p = a + 3;
            b = n(a);
            int q = b + 5;
        } catch (Exception e) {
            Exception f = e;
        } finally {
            int r = b + 7;
        }
        int c = b;
    }

    public void m2() {
        try {
            int b = n2(0);
        } catch (Exception e) {
            Exception f = e;
        }
    }

    public int n(int x) throws SubException {
        if (x == 0) {
            throw new SubSubException();
        }
        return 10 / x;
    }

    public int n2(int x) {
        return n3(x);
    }

    public int n3(int x) {
        if (x == 0) {
            throw new SubRuntimeException();
        }
        return 10 / x;
    }
}

class SubException extends Exception {
}

class SubSubException extends SubException {
}

class SubRuntimeException extends RuntimeException {
}
