import java.util.ArrayList;
import java.util.List;

class Test137 {
    public void m() {
        P137 s1 = new P137();
        s1.add("AAAA");
        String s = s1.get(0);
    }
}

class S137 {
    private P137 p137 = new P137();
    
    public void add(String elem) {
        p137.add(elem);
    }

    public String get(int index) {
        return p137.get(index);
    }
}

class P137 {
    private List<String> list = new ArrayList<>();
    
    public void add(String elem) {
        list.add(elem);
    }

    public String get(int index) {
        return list.get(index);
    }
}
