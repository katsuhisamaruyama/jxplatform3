import javax.swing.*;
import java.awt.event.*;

class Test40 {

    public void m1() {
        JButton button = new JButton("Push");
        button.addActionListener(e -> System.out.println(e));
    }

    public void m2() {
        JButton button = new JButton("Push");
        button.addActionListener(e -> e.getModifiers());
    }

    public void m3() {
        JButton button = new JButton("Push");
        button.addActionListener(System.out::println);
    }
}
