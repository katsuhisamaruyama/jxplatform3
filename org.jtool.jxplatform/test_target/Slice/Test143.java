import java.util.Map;
import java.util.HashMap;

class Test143 {

    private P143 p = new P143();

    public void m() {
        p.set("A", "AAAA");
        String v = p.get("A");
    }
}

class P143 {
    private Map<String, String> map = new HashMap<>();
    
    public void set(String key, String value) {
        map.put(key, value);
    }

    public String get(String key) {
        return map.get(key);
    }
}
