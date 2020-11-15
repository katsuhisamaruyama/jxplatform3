

class Test135 {
    private S135 s1 = new S135();
    private S135 s2 = new S135();

    public void m() {
        int a = 0;
        s1.set2("A", "AAAA");
        s2.set2("B", "BBBB");
        int b = a + 1;
        String v1 = s1.get2("A");
        String v2 = s2.get2("B");
    }
}

class S135 {
    private String value;
    private String key;
    
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
