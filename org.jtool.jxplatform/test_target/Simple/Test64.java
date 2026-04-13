import java.util.ArrayList;
import java.util.List;

class Test64 {

    public void m1(Elem e) {
        e.setX(1);
        e.getX();
    }
    
    public void n() {
        Elem e = new Elem();
        m1(e);
    }
}

class Elem {
    private int x;
    
    public void setX(int x) {
        this.x = x;
    }
    
    public int getX() {
        return x;
    }
}

