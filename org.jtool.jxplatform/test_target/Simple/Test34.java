
public class Test34 {
    private int v = 0;
    
    public void m() {
        add(100);
    }

    void add(int x) {
        assert x > 0;
        int y = x * 2;
        synchronized(this) {
            v = v + y;
        }
    }
}
