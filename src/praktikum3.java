package src;

import javax.swing.*;
import java.awt.event.*;

public class praktikum3 extends JFrame {

    private JButton button;
    private JPanel panel;

    public praktikum3() {
        setTitle("Praktikum 3 - Event Handling");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new JPanel();
        button = new JButton("Klik Saya");

        button.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Tombol diklik!");
        });

        panel.add(button);
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new praktikum3().setVisible(true);
        });
    }
}