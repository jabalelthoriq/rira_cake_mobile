package apk.laundry.form;

import apk.laundry.model.Model_Card;
import apk.laundry.swing.ScrollBar;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Deo
 */
public class laporan extends javax.swing.JPanel {

    /**
     * Creates new form Form_1
     */
    public laporan() throws SQLException {
        initComponents();
        datatabel();
        jScrollPane2.setVerticalScrollBar(new ScrollBar());
        jScrollPane2.getVerticalScrollBar().setBackground(Color.WHITE);
        jScrollPane2.getViewport().setBackground(Color.WHITE);
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        jScrollPane2.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
        jScrollPane3.setVerticalScrollBar(new ScrollBar());
        jScrollPane3.getVerticalScrollBar().setBackground(Color.WHITE);
        jScrollPane3.getViewport().setBackground(Color.WHITE);
        JPanel q = new JPanel();
        q.setBackground(Color.WHITE);
        jScrollPane3.setCorner(JScrollPane.UPPER_RIGHT_CORNER, q);
        card1.setData(new Model_Card(new ImageIcon(getClass().getResource("/apk/laundry/icon/coin_in_hand_30px.png")), "Total Pemasukan", "Rp. " + formatn(masuk("rp")), "jumlah pesanan masuk Bulan ini " + String.valueOf(masuk(""))));
        card2.setData(new Model_Card(new ImageIcon(getClass().getResource("/apk/laundry/icon/initiate_money_transfer_30px.png")), "Total Pengeluaran", "Rp. " + formatn(keluar("rp")), "jumlah pengeluaran bulan ini " + String.valueOf(keluar(""))));
        card3.setData(new Model_Card(new ImageIcon(getClass().getResource("/apk/laundry/icon/profit.png")), "Total Pemasukan Bersih", "Rp. " + formatn(pemasukanbersih("rp")), "jumlah pesanan masuk Bulan ini " + String.valueOf(pemasukanbersih(""))));
    }

    private int masuk(String query) throws SQLException {
        int totalOrders = 0;
        
        try {
            String datenow = LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY-MM"));
        String sql=(query.equals(""))?"SELECT COUNT(no_pesanan) AS jumlah FROM pesanan WHERE tgl_pesanan BETWEEN \"" + datenow + "-01\" AND \"" + datenow + "-31\";":"SELECT sum(subtotal_harga) AS jumlah FROM detail_pesanan JOIN pesanan ON pesanan.no_pesanan = detail_pesanan.no_pesanan WHERE pesanan.tgl_pesanan BETWEEN \"" + datenow + "-01\" AND \"" + datenow + "-31\";";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            totalOrders = rs.getInt("jumlah");
        }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return totalOrders;
    }
    private int keluar(String query) throws SQLException {
        int totalOrders = 0;
        
        try {
            String datenow = LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY-MM"));
        String sql=(query.equals(""))?"SELECT COUNT(biaya) AS jumlah FROM pengeluaran WHERE tanggal BETWEEN \"" + datenow + "-01\" AND \"" + datenow + "-31\";":"SELECT sum(biaya) AS jumlah FROM pengeluaran  WHERE tanggal BETWEEN \"" + datenow + "-01\" AND \"" + datenow + "-31\";";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            totalOrders = rs.getInt("jumlah");
        }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return totalOrders;
    }

    private int pemasukanbersih(String query) throws SQLException {
        int totalOrders = (query.equals(""))?(masuk("")-keluar("")):(masuk("rp")-keluar("rp"));
        return totalOrders;
    }

    public void datatabel() {
        DefaultTableModel tbl1 = new DefaultTableModel(){
                    public boolean isCellEditable(int row, int column) {
        return !(column >= 0 && column <= 7);
        }   };
        tbl1.addColumn("No");
        tbl1.addColumn("Nama Pengeluaran");
        tbl1.addColumn("Tanggal");
        tbl1.addColumn("Biaya");
        table2.setModel(tbl1);
        try {
            int no = 1;
            String sql = "select pengeluaran.nama_pengeluaran,pengeluaran.tanggal,pengeluaran.biaya from pengeluaran";
            java.sql.Connection conn = (java.sql.Connection) config.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                tbl1.addRow(new Object[]{no++,
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                });
                table2.setModel(tbl1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        DefaultTableModel tbl2 = new DefaultTableModel(){
                    public boolean isCellEditable(int row, int column) {
        return !(column >= 0 && column <= 7);
        }   };
        tbl2.addColumn("No");
        tbl2.addColumn("Nama ");
        tbl2.addColumn("Nama Layanan");
        tbl2.addColumn("Tanggal");
        tbl2.addColumn("Biaya");
        table3.setModel(tbl2);
        try {
            int no = 1;
            String sql = "SELECT customer.nama_customer, layanan.nama_layanan, pesanan.tgl_pesanan, detail_pesanan.subtotal_harga FROM pesanan JOIN customer ON customer.id_customer = pesanan.id_customer JOIN detail_pesanan ON pesanan.no_pesanan = detail_pesanan.no_pesanan, layanan WHERE pesanan.no_pesanan = detail_pesanan.no_pesanan AND layanan.id_layanan = detail_pesanan.id_layanan";
            java.sql.Connection conn = (java.sql.Connection) config.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                tbl2.addRow(new Object[]{no++,
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4)
                });
                table3.setModel(tbl2);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    private String formatn(int amount) {
    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    symbols.setCurrencySymbol("Rp. ");
    symbols.setGroupingSeparator(',');

    DecimalFormat decimalFormat = new DecimalFormat("###,###,###", symbols);
    return decimalFormat.format(amount);
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorder1 = new apk.laundry.swing.PanelBorder();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table2 = new apk.laundry.swing.Table();
        jScrollPane3 = new javax.swing.JScrollPane();
        table3 = new apk.laundry.swing.Table();
        jLabel2 = new javax.swing.JLabel();
        card1 = new apk.laundry.component.Card();
        card2 = new apk.laundry.component.Card();
        card3 = new apk.laundry.component.Card();

        setBackground(new java.awt.Color(242, 242, 242));

        panelBorder1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(127, 127, 127));
        jLabel1.setText("PEMASUKAN");

        table2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(table2);

        table3.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(table3);

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(127, 127, 127));
        jLabel2.setText("PENGELUARAN");

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE))
                .addContainerGap())
        );

        card1.setColor1(new java.awt.Color(22, 166, 243));
        card1.setColor2(new java.awt.Color(31, 150, 214));

        card2.setColor1(new java.awt.Color(186, 123, 247));
        card2.setColor2(new java.awt.Color(167, 94, 236));

        card3.setColor1(new java.awt.Color(241, 208, 62));
        card3.setColor2(new java.awt.Color(211, 184, 61));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panelBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private apk.laundry.component.Card card1;
    private apk.laundry.component.Card card2;
    private apk.laundry.component.Card card3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private apk.laundry.swing.PanelBorder panelBorder1;
    private apk.laundry.swing.Table table2;
    private apk.laundry.swing.Table table3;
    // End of variables declaration//GEN-END:variables

}
