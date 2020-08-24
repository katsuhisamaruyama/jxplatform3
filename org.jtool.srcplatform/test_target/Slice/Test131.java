import java.util.HashMap;

class Test131 {
    
    private HashMap<String, String> map = new HashMap<>();
    
    public void m() {
        map.put("A", "AA");
        int z = 1;
        String x = map.get("A");
    }
}
