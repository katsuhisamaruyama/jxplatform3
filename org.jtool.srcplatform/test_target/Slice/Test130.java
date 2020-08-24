import java.util.HashMap;

class Test130 {
    
    public void m() {
        int p = 1;
        int q = n(2).getX();
        int r = q;
    }
    
    public S130 n(int x) {
        return new S130(x);
    }
}

class S130 {
    private int x;
    
    S130(int x) {
        this.x = x;
    }
    
    int getX() {
        return x;
    }
}
