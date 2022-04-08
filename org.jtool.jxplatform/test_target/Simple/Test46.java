import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

class Test46 {

    P46 m1() {
        P46 p = new P46();
        p.set("A");
        String r = p.get();
        return p;
    }

    Q46 m2() {
        Q46 q = new Q46();
        q.set("A", "AA");
        String r = q.get("A");
        return q;
    }

    Map<String, String> m3() {
        Map<String, String> m = new HashMap<>();
        m.put("A", "AA");
        String r1 = m.get("A");
        return m;
    }

    List<String> m4() {
        List<String> l = new ArrayList<>();
        l.add("A");
        int s1 = l.size();
        return l;
    }
}

class P46 {
    private String value;

    public void set(String value) {
        this.value = value;
    }

    public String get() {
        return value;
    }
}

class Q46 {
    private Map<String, String> map = new HashMap<>();

    public void set(String key, String value) {
        map.put(key, value);
    }

    public String get(String key) {
        return map.get(key);
    }
}

