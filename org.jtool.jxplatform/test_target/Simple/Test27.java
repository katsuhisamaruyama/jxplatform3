import java.util.ArrayList;

public class Test27 {

    private ArrayList list = new MockArrayList();

    private int x = init();

    static int init() {
        return 1;
    }

    void n() {
        int sum = 0;
        ArrayList<Integer> numbers = new ArrayList<>();
        for (Integer num : numbers) {
            sum += num;
        }
    }
}

class MockArrayList extends ArrayList {
}    
