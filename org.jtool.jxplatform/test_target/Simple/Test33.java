
import java.io.FileInputStream;
import java.io.IOException;

public class Test33 {

    public void m() {
        try (FileInputStream in = new FileInputStream("a.txt")) {
            int c;
            while ((c = in.read()) != -1) ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
