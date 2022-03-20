
import javax.swing.*;
import java.awt.event.*;

public class Test29 {

    public void m() {

        class MyActionListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
            }
        }

        JButton button = new JButton("Push");
        button.addActionListener(new MyActionListener());
    }
}
