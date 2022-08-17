import java.util.ArrayList;
import java.util.List;

class Test146 {

    public void m() {
        int total = 0;
        List<String> strings = new ArrayList<>();
        for (String str : strings) {
            total += str.length();
        }
        int result = total;
    }
}
