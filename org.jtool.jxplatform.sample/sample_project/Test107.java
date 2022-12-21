class Test107 {

    private String[][] x;
    private String[] y;
    
    public void m(int[] a) {
        int i = 0;
        a[i++] = 2;
        a[i] = 3;
        int j = i;
        int y = a[0];
    }
}
