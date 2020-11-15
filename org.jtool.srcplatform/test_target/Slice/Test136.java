import java.util.HashMap;
import java.util.Map;

class Test136 {
    private S136 s1 = new S136();
    private S136 s2 = new S136();

    public void m() {
        int a = 0;
        s1.set2("A", "AAAA");
        s2.set2("B", "BBBB");
        int b = a + 1;
        String v1 = s1.get2("A");
        String v2 = s2.get2("B");
    }
}

class S136 {
    private Map<String, String> map = new HashMap<>();
    
    public void set2(String key, String value) {
        map.put(key, value);
    }

    public String get2(String key) {
        return map.get(key);
    }
}
