import java.util.ArrayList;
import java.util.List;

class Test138 {
    public void m() {
        P138 s1 = new P138();
        P138 s2 = new P138();
        String a = "A";
        s1.add(a);
        s1.add("B");
        s2.add("C");
        P138 s3 = s1;
        List<String> l1 = s1.getList();
        List<String> l3 = s3.getList();
        List<String> l2 = s2.getList();
    }
}

class P138 {
    private List<String> list = new ArrayList<>();
    
    public void add(String elem) {
        list.add(elem);
    }

    public List<String> getList() {
        return list;
        //return new ArrayList<>(list);
    }
}
