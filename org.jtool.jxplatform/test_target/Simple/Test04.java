public class Test04 {

    public void m() {
        int a = 0;
        int out = doReturn(a);
        System.out.println(out);
    }
    
    public int doReturn(int in) {
       in++;
       if (in > 0)
           return in;
       return 0;
    }
}
