import java.util.Map;
import java.util.HashMap;

class Test129 {
    private S129 s1 = new S129();
    private S129 s2 = new S129();

    public void m() {
        int a = 0;
        s1.getP().set1("A", "AAAA");
        s2.getP().set2("B", "BBBB");
        int b = a + 1;
        String v1 = s1.getP().get1("A");
        S129 s3 = s2;
        String v2 = s3.getP().get2("B");

        T129 t = new T129();
        t.set1("C", "CCCC");
        String v3 = t.get1("C");

        U129 u = new U129();
        u.set1("D", "DDDD");
        String v4 = u.get1("D");
    }
}

class S129 {
    private P129 p = new P129();

    public P129 getP() {
        return p;
    }
}

class T129 {
    private P129 p = new P129();

    public void set1(String key, String value) {
        p.set1(key, value);
    }

    public String get1(String key) {
        return p.get1(key);
    }
}

class U129 {
    private T129 t = new T129();
    
    public void set1(String key, String value) {
        t.set1(key, value);
    }

    public String get1(String key) {
        return t.get1(key);
    }
}

class P129 {
    private Map<String, String> map = new HashMap<>();
    private String key;
    private String value;
    
    public void set1(String key, String value) {
        map.put(key, value);
    }

    public String get1(String key) {
        return map.get(key);
    }

    public void set2(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String get2(String key) {
        if (key.equals(this.key)) {
            return value;
        }
        return null;
    }
}
