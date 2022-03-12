
import java.util.ArrayList;

public class Test27 {

    private ArrayList list = new MockArrayList();

    private int x = init();

    static int init() {
        return 1;
    }
}

class MockArrayList extends ArrayList {
}    
