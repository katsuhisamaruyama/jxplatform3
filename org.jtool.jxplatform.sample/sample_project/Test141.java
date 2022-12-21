import java.util.Map;
import java.util.HashMap;

class Test141 {
    private Map<String, String> map1 = new HashMap<>();
    private Map<String, String> map2 = new HashMap<>();

    public void m() {
        map1.put("A", "AAAA");
        map2.put("B", "BBBB");
        String v1 = map1.get("A");
        String v2 = map2.get("B");
    }

    public void m2() {
        map1.put("A", "AAAA");
        map2.put("B", "BBBB");
        String v2 = map2.get("B");
        String v1 = map1.get("A");
    }
}
