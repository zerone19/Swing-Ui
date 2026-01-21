package src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class FormMahasiswa extends JFrame {

    JTextField txtNama;
    JComboBox<String> cbProdi;
    JTable table;
    DefaultTableModel model;

    JButton btnSimpan, btnUpdate, btnHapus;

    public FormMahasiswa() {
        setTitle("CRUD Mahasiswa");
        setSize(600, 400);
        setLayout(null);

        JLabel lblNama = new JLabel("Nama");
        lblNama.setBounds(20, 20, 80, 25);
        add(lblNama);

        txtNama = new JTextField();
        txtNama.setBounds(100, 20, 200, 25);
        add(txtNama);

        JLabel lblProdi = new JLabel("Prodi");
        lblProdi.setBounds(20, 60, 80, 25);
        add(lblProdi);

        cbProdi = new JComboBox<>(new String[] {
                "Informatika", "Sistem Informasi", "Teknik Komputer"
        });
        cbProdi.setBounds(100, 60, 200, 25);
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

        model = new DefaultTableModel();
        model.addColumn("NIM");
        model.addColumn("Nama");
        model.addColumn("Prodi");

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
                txtNama.setText(model.getValueAt(row, 1).toString());
                cbProdi.setSelectedItem(model.getValueAt(row, 2).toString());
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    void loadData() {
        model.setRowCount(0);
        try (Connection c = koneksi.getConnection()) {
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM mahasiswa");
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
            String sql = "INSERT INTO mahasiswa (nama, prodi) VALUES (?, ?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, txtNama.getText());
            ps.setString(2, cbProdi.getSelectedItem().toString());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data disimpan");
            loadData();
            txtNama.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    void update() {
        int row = table.getSelectedRow();
        if (row == -1)
            return;

        int nim = Integer.parseInt(model.getValueAt(row, 0).toString());

        try (Connection c = koneksi.getConnection()) {
            String sql = "UPDATE mahasiswa SET nama=?, prodi=? WHERE nim=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, txtNama.getText());
            ps.setString(2, cbProdi.getSelectedItem().toString());
            ps.setInt(3, nim);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data diupdate");
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    void hapus() {
        int row = table.getSelectedRow();
        if (row == -1)
            return;

        int nim = Integer.parseInt(model.getValueAt(row, 0).toString());

        try (Connection c = koneksi.getConnection()) {
            String sql = "DELETE FROM mahasiswa WHERE nim=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, nim);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data dihapus");
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}
