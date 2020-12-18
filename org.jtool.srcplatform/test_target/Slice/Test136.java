import java.util.HashMap;
import java.util.Map;

class Test136 {
    private S136 s1 = new S136();
    private S136 s2 = new S136();

    public void m() {
        int a = 0;
        s1.set("A", "AAAA");
        s2.set("B", "BBBB");
        int b = a + 1;
        String v1 = s1.get("A");
        String v2 = s2.get("B");
    }
}

class S136 {
    private Map<String, String> map = new HashMap<>();
    
    public void set(String key, String value) {
        map.put(key, value);
    }

    public String get(String key) {
        return map.get(key);
    }
}
