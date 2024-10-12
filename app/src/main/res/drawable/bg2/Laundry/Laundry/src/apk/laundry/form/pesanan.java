package apk.laundry.form;

import apk.laundry.model.Model_Card;
import apk.laundry.model.StatusType;
import apk.laundry.swing.CellStatus;
import apk.laundry.swing.ScrollBar;
import apk.laundry.swing.TableHeader;
import java.awt.Color;
import java.awt.Component;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javaswingdev.message.MessageDialog;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class pesanan extends javax.swing.JPanel {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * Creates new form Form_1
     */
    public pesanan() throws SQLException {
        initComponents();
        scheduler.scheduleAtFixedRate(this::updateStatusAutomatically, 0, 1, TimeUnit.DAYS);
        card3.setData(new Model_Card(new ImageIcon(getClass().getResource("/apk/laundry/icon/flag.png")), "Pesanan Diambil", String.valueOf(diambil()), "jumlah pesanan yang diambil"));
        card1.setData(new Model_Card(new ImageIcon(getClass().getResource("/apk/laundry/icon/wash_by_hand_40px.png")), "Pesanan Proses", String.valueOf(proses()), "jumlah pesanan yang di proses"));
        card2.setData(new Model_Card(new ImageIcon(getClass().getResource("/apk/laundry/icon/shirt_30px.png")), "Pesanan Selesai", String.valueOf(selesai()), "jumlah pesanan sudah selesai"));
        CustomTable customTable = new CustomTable();
        ListSelectionModel selectionModel = customTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = customTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String orderNumber = customTable.getValueAt(selectedRow, 0).toString();
                        String customerName = customTable.getValueAt(selectedRow, 1).toString();
                        String layanan = customTable.getValueAt(selectedRow, 3).toString();
                        String tglpesan = customTable.getValueAt(selectedRow, 5).toString();
                        String tglselesai = customTable.getValueAt(selectedRow, 7).toString();
                        String harga = customTable.getValueAt(selectedRow, 8).toString();
                        String Berat = customTable.getValueAt(selectedRow, 4).toString();
                        String status = customTable.getValueAt(selectedRow, 9).toString();
                        String message = "             No suborder: " + orderNumber
                                + "\n             Nama : " + customerName + "   Layanan : " + layanan
                                + "\n             Berat : " + Berat + "   Harga : " + harga
                                + "\n             Tanggal pemesanan :   " + tglpesan
                                + "\n             Tanggal selesai :          " + tglselesai
                                + "\n             Status : " + status;

                        MessageDialog messageDialog = new MessageDialog((JFrame) SwingUtilities.getWindowAncestor(pesanan.this));
                        messageDialog.showMessage("Detail Pesanan", message);
                        if (messageDialog.getMessageType() == MessageDialog.MessageType.DIAMBIL) {
                            try {
                                String sql = "UPDATE `detail_pesanan` SET `status`= 'Diambil' WHERE detail_pesanan.id_detail = '" + orderNumber + "'";
                                java.sql.Connection conn = config.configDB();
                                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                                pst.executeUpdate();
                            } catch (SQLException ei) {
                                ei.printStackTrace();
                            }refreshPanel();
                            load_table();
                        } else if (messageDialog.getMessageType() == MessageDialog.MessageType.SELESAI) {
                            try {
                                String sql = "UPDATE `detail_pesanan` SET `status`= 'Selesai' WHERE detail_pesanan.id_detail = '" + orderNumber + "'";
                                java.sql.Connection conn = config.configDB();
                                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                                pst.executeUpdate();
                            } catch (SQLException ei) {
                                ei.printStackTrace();
                            }refreshPanel();
                            load_table();
                        } else if (messageDialog.getMessageType() == MessageDialog.MessageType.PROSES) {
                            try {
                                String sql = "UPDATE `detail_pesanan` SET `status`= 'Proses' WHERE detail_pesanan.id_detail = '" + orderNumber + "'";
                                java.sql.Connection conn = config.configDB();
                                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                                pst.executeUpdate();
                            } catch (SQLException ei) {
                                ei.printStackTrace();
                            }refreshPanel();
                            load_table();
                        }
                    }
                }
            }
        });
        spTable.setViewportView(customTable);
        spTable.setVerticalScrollBar(new ScrollBar());
        spTable.getVerticalScrollBar().setBackground(Color.WHITE);
        spTable.getViewport().setBackground(Color.WHITE);
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        spTable.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
        DefaultTableModel model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return !(column >= 0 && column <= 7);
            }
        };

        model.addColumn("No Subpesanan");
        model.addColumn("Nama");
        model.addColumn("Alamat");
        model.addColumn("Layanan");
        model.addColumn("Berat Kg");
        model.addColumn("Tanggal & waktu");
        model.addColumn("Phone");
        model.addColumn("Tanggal Selesai");
        model.addColumn("Harga");
        model.addColumn("Status");
        try {
            String sql = "SELECT detail_pesanan.id_detail,customer.nama_customer,customer.alamat_customer,layanan.nama_layanan,detail_pesanan.berat,pesanan.tgl_pesanan,customer.no_hp,detail_pesanan.tgl_selesai,detail_pesanan.subtotal_harga,detail_pesanan.status FROM pesanan\n"
                    + "JOIN customer ON customer.id_customer = pesanan.id_customer\n"
                    + "JOIN detail_pesanan ON detail_pesanan.no_pesanan = pesanan.no_pesanan\n"
                    + "JOIN layanan ON layanan.id_layanan = detail_pesanan.id_layanan";
            java.sql.Connection conn = (java.sql.Connection) config.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String statusText = getStatusText(res.getString(10));
                StatusType status = StatusType.valueOf(statusText);
                model.addRow(new Object[]{res.getString(1),
                    res.getString(2), res.getString(3), res.getString(4),
                    res.getString(5), res.getString(6), res.getString(7), res.getString(8), res.getString(9), status
                });
            }
            customTable.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
private void load_table() {
            CustomTable customTable = new CustomTable();
        ListSelectionModel selectionModel = customTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = customTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String orderNumber = customTable.getValueAt(selectedRow, 0).toString();
                        String customerName = customTable.getValueAt(selectedRow, 1).toString();
                        String layanan = customTable.getValueAt(selectedRow, 3).toString();
                        String tglpesan = customTable.getValueAt(selectedRow, 5).toString();
                        String tglselesai = customTable.getValueAt(selectedRow, 7).toString();
                        String harga = customTable.getValueAt(selectedRow, 8).toString();
                        String Berat = customTable.getValueAt(selectedRow, 4).toString();
                        String status = customTable.getValueAt(selectedRow, 9).toString();
                        String message = "             No suborder: " + orderNumber
                                + "\n             Nama : " + customerName + "   Layanan : " + layanan
                                + "\n             Berat : " + Berat + "   Harga : " + harga
                                + "\n             Tanggal pemesanan :   " + tglpesan
                                + "\n             Tanggal selesai :          " + tglselesai
                                + "\n             Status : " + status;

                        MessageDialog messageDialog = new MessageDialog((JFrame) SwingUtilities.getWindowAncestor(pesanan.this));
                        messageDialog.showMessage("Detail Pesanan", message);
                        if (messageDialog.getMessageType() == MessageDialog.MessageType.DIAMBIL) {
                            try {
                                String sql = "UPDATE `detail_pesanan` SET `status`= 'Diambil' WHERE detail_pesanan.id_detail = '" + orderNumber + "'";
                                java.sql.Connection conn = config.configDB();
                                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                                pst.executeUpdate();
                            } catch (SQLException ei) {
                                ei.printStackTrace();
                            }refreshPanel();
                            load_table();
                        } else if (messageDialog.getMessageType() == MessageDialog.MessageType.SELESAI) {
                            try {
                                String sql = "UPDATE `detail_pesanan` SET `status`= 'Selesai' WHERE detail_pesanan.id_detail = '" + orderNumber + "'";
                                java.sql.Connection conn = config.configDB();
                                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                                pst.executeUpdate();
                            } catch (SQLException ei) {
                                ei.printStackTrace();
                            }refreshPanel();
                            load_table();
                        } else if (messageDialog.getMessageType() == MessageDialog.MessageType.PROSES) {
                            try {
                                String sql = "UPDATE `detail_pesanan` SET `status`= 'Proses' WHERE detail_pesanan.id_detail = '" + orderNumber + "'";
                                java.sql.Connection conn = config.configDB();
                                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                                pst.executeUpdate();
                            } catch (SQLException ei) {
                                ei.printStackTrace();
                            }refreshPanel();
                            load_table();
                        }
                    }
                }
            }
        });
        spTable.setViewportView(customTable);
        spTable.setVerticalScrollBar(new ScrollBar());
        spTable.getVerticalScrollBar().setBackground(Color.WHITE);
        spTable.getViewport().setBackground(Color.WHITE);
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        spTable.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
        DefaultTableModel model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return !(column >= 0 && column <= 7);
            }
        };

        model.addColumn("No Subpesanan");
        model.addColumn("Nama");
        model.addColumn("Alamat");
        model.addColumn("Layanan");
        model.addColumn("Berat Kg");
        model.addColumn("Tanggal & waktu");
        model.addColumn("Phone");
        model.addColumn("Tanggal Selesai");
        model.addColumn("Harga");
        model.addColumn("Status");
        try {
            String sql = "SELECT detail_pesanan.id_detail,customer.nama_customer,customer.alamat_customer,layanan.nama_layanan,detail_pesanan.berat,pesanan.tgl_pesanan,customer.no_hp,detail_pesanan.tgl_selesai,detail_pesanan.subtotal_harga,detail_pesanan.status FROM pesanan\n"
                    + "JOIN customer ON customer.id_customer = pesanan.id_customer\n"
                    + "JOIN detail_pesanan ON detail_pesanan.no_pesanan = pesanan.no_pesanan\n"
                    + "JOIN layanan ON layanan.id_layanan = detail_pesanan.id_layanan";
            java.sql.Connection conn = (java.sql.Connection) config.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String statusText = getStatusText(res.getString(10));
                StatusType status = StatusType.valueOf(statusText);
                model.addRow(new Object[]{res.getString(1),
                    res.getString(2), res.getString(3), res.getString(4),
                    res.getString(5), res.getString(6), res.getString(7), res.getString(8), res.getString(9), status
                });
            }
            customTable.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
}
  
    

    private int diambil() throws SQLException {
        int totalOrders = 0;
        String sql = "SELECT COUNT(detail_pesanan.status) as status "
                + "FROM detail_pesanan "
                + "WHERE detail_pesanan.status = 'Diambil'";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            totalOrders = rs.getInt("status");
        }
        return totalOrders;
    }

    private int proses() throws SQLException {
        int total = 0;
        String sql = "SELECT COUNT(status) as status\n"
                + "FROM detail_pesanan\n"
                + "WHERE status = 'Proses'";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            total = rs.getInt("status");
        }
        return total;
    }

    private int selesai() throws SQLException {
        int total = 0;
        String sql = "SELECT COUNT(status) as status\n"
                + "FROM detail_pesanan\n"
                + "WHERE status = 'Selesai'";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            total = rs.getInt("status");
        }
        return total;
    }

    public void initialize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class CustomTable extends JTable {

        public CustomTable() {
            setShowHorizontalLines(true);
            setGridColor(new Color(230, 230, 230));
            setRowHeight(40);
            getTableHeader().setReorderingAllowed(false);
            getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
                    TableHeader header = new TableHeader(o + "");
                    if (i1 == 9) {
                        header.setHorizontalAlignment(JLabel.CENTER);
                    }
                    return header;
                }
            });
            setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable jtable, Object o, boolean selected, boolean bln1, int i, int i1) {
                    if (i1 != 9) {
                        Component com = super.getTableCellRendererComponent(jtable, o, selected, bln1, i, i1);
                        com.setBackground(Color.WHITE);
                        setBorder(noFocusBorder);
                        if (selected) {
                            com.setForeground(new Color(15, 89, 140));
                        } else {
                            com.setForeground(new Color(102, 102, 102));
                        }
                        return com;
                    } else {
                        if (o instanceof StatusType) {
                            StatusType type = (StatusType) o;
                            CellStatus cell = new CellStatus(type);
                            return cell;
                        } else if (o instanceof String) {
                            try {
                                // Attempt to convert the string to StatusType
                                StatusType type = StatusType.valueOf((String) o);
                                CellStatus cell = new CellStatus(type);
                                return cell;
                            } catch (IllegalArgumentException e) {
                                // Handle the case where the string is not a valid StatusType
                                return super.getTableCellRendererComponent(jtable, "Invalid Status", selected, bln1, i, i1);
                            }
                        } else {
                            // Handle other cases or return the default component
                            return super.getTableCellRendererComponent(jtable, o, selected, bln1, i, i1);
                        }
                    }
                }
            });
        }
    }

    private static String getStatusText(String status) {

        switch (status) {
            case "Proses":
                return "PROSES";
            case "Selesai":
                return "SELESAI";
            case "Diambil":
                return "DIAMBIL";
            default:
                return status; // Return as-is if not recognized
        }
    }

    public void updateStatusAutomatically() {
        try {
            String sql = "UPDATE detail_pesanan\n"
                    + "SET status = 'Selesai'\n"
                    + "WHERE status = 'Proses' \n"
                    + "AND ((tgl_selesai = CURDATE()) OR (tgl_selesai < CURDATE()));";
            java.sql.Connection conn = config.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

private void refreshPanel() {
    try {
        card3.setData(new Model_Card(new ImageIcon(getClass().getResource("/apk/laundry/icon/flag.png")), "Pesanan Diambil", String.valueOf(diambil()), "jumlah pesanan yang diambil"));
        card1.setData(new Model_Card(new ImageIcon(getClass().getResource("/apk/laundry/icon/wash_by_hand_40px.png")), "Pesanan Proses", String.valueOf(proses()), "jumlah pesanan yang di proses"));
        card2.setData(new Model_Card(new ImageIcon(getClass().getResource("/apk/laundry/icon/shirt_30px.png")), "Pesanan Selesai", String.valueOf(selesai()), "jumlah pesanan sudah selesai"));
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    //    int selectedRow = table.getSelectedRow();
//    
//    if (selectedRow != -1) {
//        String orderNumber = table.getValueAt(selectedRow, 0).toString();
//        String customerName = table.getValueAt(selectedRow, 1).toString();
//        String message = "Order Number: " + orderNumber + "\nCustomer Name: " + customerName;
//
//        MessageDialog messageDialog = new MessageDialog((JFrame) SwingUtilities.getWindowAncestor(this));
//        messageDialog.showMessage("Order Details", message);
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        card1 = new apk.laundry.component.Card();
        card3 = new apk.laundry.component.Card();
        card2 = new apk.laundry.component.Card();
        panel1 = new apk.laundry.component.panel();
        jLabel1 = new javax.swing.JLabel();
        searchText1 = new apk.laundry.swing.SearchText();
        jLabel2 = new javax.swing.JLabel();
        spTable = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        setBackground(new java.awt.Color(242, 242, 242));

        card1.setColor1(new java.awt.Color(22, 166, 243));
        card1.setColor2(new java.awt.Color(31, 150, 214));

        card3.setColor1(new java.awt.Color(241, 208, 62));
        card3.setColor2(new java.awt.Color(211, 184, 61));

        card2.setColor1(new java.awt.Color(186, 123, 247));
        card2.setColor2(new java.awt.Color(167, 94, 236));

        panel1.setColor1(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(127, 127, 127));
        jLabel1.setText("PESANAN");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/apk/laundry/icon/search.png"))); // NOI18N

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spTable.setViewportView(table);

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchText1, javax.swing.GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(21, 21, 21))
            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(spTable, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(20, Short.MAX_VALUE)))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(searchText1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(614, 614, 614))
            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel1Layout.createSequentialGroup()
                    .addGap(42, 42, 42)
                    .addComponent(spTable, javax.swing.GroupLayout.PREFERRED_SIZE, 584, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(21, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(card3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private apk.laundry.component.Card card1;
    private apk.laundry.component.Card card2;
    private apk.laundry.component.Card card3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private apk.laundry.component.panel panel1;
    private apk.laundry.swing.SearchText searchText1;
    private javax.swing.JScrollPane spTable;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
