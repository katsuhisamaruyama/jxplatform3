class Test142 {

    private P142 p = new P142();

    public void m() {
        p.set("A", "AAAA");
        String k = p.getKey();
        String v = p.getValue();
    }
}

class P142 {
    private String key;
    private String value;
    
    public void set(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        if (key.equals(this.key)) {
            return value;
        }
        return null;
    }
}
