
import javax.swing.*;
import java.awt.event.*;

public class Test28 {

    public void m() {

        JButton button = new JButton("Push");
        button.addActionListener(new MyActionListener());
    }

    private class MyActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        }
    }
}
