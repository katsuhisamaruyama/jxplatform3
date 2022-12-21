import java.util.ArrayList;
import java.util.List;

class Test145 {
    List<T145> list = new ArrayList<>();

    public void m() {
        list.add(new T145(1));
        T145 t = list.get(0);
        int a = t.getA();
        int h = t.hashCode();
        List<T145> l = getList(); 
    }

    public List<T145> getList() {
        return list;
    }
}

class T145 {
    private int a;

    T145(int a) {
        this.a = a;
    }
    
    public int getA() {
        return a;
    }

    public int hashCode() {
        return 0;
    }
}
