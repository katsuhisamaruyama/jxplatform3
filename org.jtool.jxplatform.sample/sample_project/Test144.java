import java.util.Map;
import java.util.HashMap;

class Test144 {

    public void m() {
        P144 a = new P144();
        a.put("A", "AAAA");
        String v1 = a.get("A");

        P144 p = new P144();
        p.put("B", "BBBB");
        String v2 = p.get("B");

        T144 t = new T144();
        t.put("C", "CCCC");
        String v3 = t.get("C");
    }
}

class T144 {
    private P144 p = new P144();

    public void put(String key, String value) {
        p.put(key, value);
    }

    public String get(String key) {
        return p.get(key);
    }
}

class P144 {
    private Map<String, String> map = new HashMap<>();
    
    public void put(String key, String value) {
        map.put(key, value);
    }

    public String get(String key) {
        return map.get(key);
    }
}
