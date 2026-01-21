package src;

import javax.swing.*;

public class praktikum2 extends JFrame {

    private JLabel label;
    private JTextField textField;
    private JButton button;
    private JPanel panel;

    public praktikum2() {
        setTitle("Praktikum 2 - Form Nama");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(null);

        label = new JLabel("Nama:");
        label.setBounds(20, 30, 80, 25);

        textField = new JTextField();
        textField.setBounds(100, 30, 200, 25);

        button = new JButton("Simpan");
        button.setBounds(150, 70, 100, 30);

        panel.add(label);
        panel.add(textField);
        panel.add(button);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new praktikum2().setVisible(true);
        });
    }
}