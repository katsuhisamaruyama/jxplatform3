class Test38 {

    private String[] x;
    private int[] y;
    
    void m(int[] a) {
        int i = 0;
        a[i++] = 2;
        a[i] = 3;
        int j = i;
        int y = a[j];
    }

    void n() {
        String[] str = new String[10];
        str[0] = "abc";
    }
}
