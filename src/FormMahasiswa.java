package src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class FormMahasiswa extends JFrame {

    JTextField txtNIM, txtNama;
    JComboBox<String> cbProdi;
    JTable table;
    DefaultTableModel model;
    JButton btnSimpan, btnUpdate, btnHapus;

    public FormMahasiswa() {
        setTitle("CRUD Mahasiswa");
        setSize(600, 400);
        setLayout(null);

        JLabel lblNIM = new JLabel("NIM");
        lblNIM.setBounds(20, 20, 80, 25);
        add(lblNIM);

        txtNIM = new JTextField();
        txtNIM.setBounds(100, 20, 200, 25);
        add(txtNIM);

        JLabel lblNama = new JLabel("Nama");
        lblNama.setBounds(20, 60, 80, 25);
        add(lblNama);

        txtNama = new JTextField();
        txtNama.setBounds(100, 60, 200, 25);
        add(txtNama);

        JLabel lblProdi = new JLabel("Prodi");
        lblProdi.setBounds(20, 100, 80, 25);
        add(lblProdi);

        cbProdi = new JComboBox<>(new String[] {
                "Informatika", "Sistem Informasi", "Teknik Komputer"
        });
        cbProdi.setBounds(100, 100, 200, 25);
        add(cbProdi);

        btnSimpan = new JButton("Simpan");
        btnSimpan.setBounds(330, 20, 100, 25);
        add(btnSimpan);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(330, 60, 100, 25);
        add(btnUpdate);

        btnHapus = new JButton("Hapus");
        btnHapus.setBounds(330, 100, 100, 25);
        add(btnHapus);

        model = new DefaultTableModel(new String[] { "NIM", "Nama", "Prodi" }, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 140, 540, 200);
        add(sp);

        loadData();

        btnSimpan.addActionListener(e -> simpan());
        btnUpdate.addActionListener(e -> update());
        btnHapus.addActionListener(e -> hapus());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                txtNIM.setText(model.getValueAt(row, 0).toString());
                txtNama.setText(model.getValueAt(row, 1).toString());
                cbProdi.setSelectedItem(model.getValueAt(row, 2).toString());
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    void loadData() {
        model.setRowCount(0);
        try (Connection c = koneksi.getConnection()) {
            ResultSet r = c.createStatement().executeQuery("SELECT * FROM mahasiswa");
            while (r.next()) {
                model.addRow(new Object[] {
                        r.getInt("nim"),
                        r.getString("nama"),
                        r.getString("prodi")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    void simpan() {
        try (Connection c = koneksi.getConnection()) {
            PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO mahasiswa VALUES (?, ?, ?)");
            ps.setInt(1, Integer.parseInt(txtNIM.getText()));
            ps.setString(2, txtNama.getText());
            ps.setString(3, cbProdi.getSelectedItem().toString());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data disimpan");
            clear();
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    void update() {
        try (Connection c = koneksi.getConnection()) {
            PreparedStatement ps = c.prepareStatement(
                    "UPDATE mahasiswa SET nama=?, prodi=? WHERE nim=?");
            ps.setString(1, txtNama.getText());
            ps.setString(2, cbProdi.getSelectedItem().toString());
            ps.setInt(3, Integer.parseInt(txtNIM.getText()));
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data diupdate");
            clear();
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    void hapus() {
        int row = table.getSelectedRow();
        if (row == -1)
            return;

        try (Connection c = koneksi.getConnection()) {
            PreparedStatement ps = c.prepareStatement(
                    "DELETE FROM mahasiswa WHERE nim=?");
            ps.setInt(1, Integer.parseInt(txtNIM.getText()));
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data dihapus");
            clear();
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    void clear() {
        txtNIM.setText("");
        txtNama.setText("");
        cbProdi.setSelectedIndex(0);
    }
}
