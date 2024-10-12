package apk.laundry.form;

import apk.laundry.model.Model_Card;
import apk.laundry.swing.ScrollBar;
import apk.laundry.swing.Table;
import java.awt.Color;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class transaksi extends javax.swing.JPanel {
    private int in;
    private int selectedRow;
    
    public transaksi() throws SQLException {
        initComponents();
        clear();
        load_table();
        load_table2();
        jScrollPane2.setVerticalScrollBar(new ScrollBar());
        jScrollPane2.getVerticalScrollBar().setBackground(Color.WHITE);
        jScrollPane2.getViewport().setBackground(Color.WHITE);
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        int[] columnWidths = {2,40,30,20}; // You can adjust these values
        setColumnWidths(table2, columnWidths);
        jScrollPane2.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
        card1.setData(new Model_Card(new ImageIcon(getClass().getResource("/apk/laundry/icon/coin_in_hand_30px.png")), "Total Pemasukan", "Rp. "+formatn(pemasukan()), "jumlah pesanan masuk Bulan ini "+String.valueOf(masuk())));
        card2.setData(new Model_Card(new ImageIcon(getClass().getResource("/apk/laundry/icon/initiate_money_transfer_30px.png")), "Total Pengeluaran","Rp. "+formatn(keluar()), "jumlah pengeluaran bulan ini "+jkeluar()));
        jScrollPane3.setVerticalScrollBar(new ScrollBar());
        jScrollPane3.getVerticalScrollBar().setBackground(Color.WHITE);
        jScrollPane3.getViewport().setBackground(Color.WHITE);
        JPanel q = new JPanel();
        q.setBackground(Color.WHITE);
        jScrollPane3.setCorner(JScrollPane.UPPER_RIGHT_CORNER, q);
        
        String datenow=LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        t.setText(datenow);
        t.disable();
    }
    private void clear(){
        p.setText(null);
        b.setText(null);

}
    
    private void load_table(){
        DefaultTableModel model = new DefaultTableModel(){
                    public boolean isCellEditable(int row, int column) {
        return !(column >= 0 && column <= 7);
        }   };
        model.addColumn("No");
        model.addColumn("Nama");
        model.addColumn("Tanggal");
        model.addColumn("Biaya");
        
        try{
            int no=1;
            String sql = "select nama_pengeluaran,tanggal,biaya FROM pengeluaran";
            java.sql.Connection conn=(java.sql.Connection) config.configDB();
            java.sql.Statement stm=conn.createStatement();
            java.sql.ResultSet res=stm.executeQuery(sql);
            while(res.next()){
                model.addRow(new Object[]{no++ ,res.getString(1),
                    res.getString(2),res.getString(3)
                });
            }
            table2.setModel(model);
        }catch (Exception e) {
             JOptionPane.showMessageDialog(this,e.getMessage());
        }
    }
    private void setColumnWidths(Table table2, int[] columnWidths) {
        TableColumnModel columnModel = table2.getColumnModel();
        for (int i = 0; i < columnWidths.length; i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }
    }
    private void load_table2(){
        DefaultTableModel model = new DefaultTableModel(){
                    public boolean isCellEditable(int row, int column) {
        return !(column >= 0 && column <= 7);
        }   };
        model.addColumn("No");
        model.addColumn("Nama");
        model.addColumn("Layanan");
        model.addColumn("Alamat");
        model.addColumn("Tanggal");
        model.addColumn("Harga");
        
        try{
            int no=1;
            String sql = "SELECT customer.nama_customer,layanan.nama_layanan,customer.alamat_customer,pesanan.tgl_pesanan,detail_pesanan.subtotal_harga\n" +
"FROM pesanan\n" +
"JOIN customer ON customer.id_customer = pesanan.id_customer\n" +
"JOIN detail_pesanan ON detail_pesanan.no_pesanan = pesanan.no_pesanan\n" +
"JOIN layanan ON layanan.id_layanan = detail_pesanan.id_layanan ";
            java.sql.Connection conn=(java.sql.Connection) config.configDB();
            java.sql.Statement stm=conn.createStatement();
            java.sql.ResultSet res=stm.executeQuery(sql);
            while(res.next()){
                model.addRow(new Object[]{no++ ,res.getString(1),
                    res.getString(2),res.getString(3),res.getString(4),res.getString(5)
                });
            }
            table3.setModel(model);
        }catch (Exception e) {
             JOptionPane.showMessageDialog(this,e.getMessage());
        }
    }
        private int pemasukan() throws SQLException {
        int totalOrders = 0;
        String sql = "SELECT SUM(subtotal_harga) as total\n" +
                 "FROM detail_pesanan\n" +
                 "JOIN pesanan ON pesanan.no_pesanan = detail_pesanan.no_pesanan\n"+
                 "WHERE MONTH(detail_pesanan.tgl_selesai) = MONTH(CURDATE())\n" +
                 "AND YEAR(detail_pesanan.tgl_selesai) = YEAR(CURDATE())";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            totalOrders = rs.getInt("total");
        }
        return totalOrders;
    }
    private int masuk() throws SQLException {
        int totalOrders = 0;
        String sql = "SELECT COUNT(id_customer) as total\n" +
                 "FROM pesanan\n" +
                 "WHERE MONTH(pesanan.tgl_pesanan) = MONTH(CURDATE())\n" +
                 "AND YEAR(pesanan.tgl_pesanan) = YEAR(CURDATE())";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            totalOrders = rs.getInt("total");
        }
        return totalOrders;
    }
    private int keluar() throws SQLException {
        int totalOrders = 0;
        String sql = "SELECT SUM(biaya) as total\n" +
                 "FROM pengeluaran\n" +
                 "WHERE MONTH(pengeluaran.tanggal) = MONTH(CURDATE())\n" +
                 "AND YEAR(pengeluaran.tanggal) = YEAR(CURDATE())";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            totalOrders = rs.getInt("total");
        }
        return totalOrders;
    }
    private int jkeluar() throws SQLException {
        int totalOrders = 0;
        String sql = "SELECT COUNT(id_pengeluaran) as total\n" +
                 "FROM pengeluaran\n" +
                 "WHERE MONTH(pengeluaran.tanggal) = MONTH(CURDATE())\n" +
                 "AND YEAR(pengeluaran.tanggal) = YEAR(CURDATE())";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            totalOrders = rs.getInt("total");
        }
        return totalOrders;
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
        jScrollPane2 = new javax.swing.JScrollPane();
        table2 = new apk.laundry.swing.Table();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        table3 = new apk.laundry.swing.Table();
        jLabel5 = new javax.swing.JLabel();
        button2 = new apk.laundry.model.Button();
        button3 = new apk.laundry.model.Button();
        card1 = new apk.laundry.component.Card();
        card2 = new apk.laundry.component.Card();
        panel1 = new apk.laundry.component.panel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        p = new apk.laundry.model.TextField();
        b = new apk.laundry.model.TextField();
        t = new apk.laundry.model.TextField();
        j = new apk.laundry.model.TextField();
        button1 = new apk.laundry.model.Button();
        jLabel7 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(242, 242, 242));

        panelBorder1.setBackground(new java.awt.Color(250, 250, 250));

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
        table2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table2);

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setText("PENGELUARAN");

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

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("PESANAN");

        button2.setBackground(new java.awt.Color(51, 153, 255));
        button2.setForeground(new java.awt.Color(255, 255, 255));
        button2.setText("EDIT");
        button2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        button3.setBackground(new java.awt.Color(51, 153, 255));
        button3.setForeground(new java.awt.Color(255, 255, 255));
        button3.setText("HAPUS");
        button3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelBorder1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        card1.setColor1(new java.awt.Color(241, 208, 62));
        card1.setColor2(new java.awt.Color(211, 184, 61));

        card2.setColor1(new java.awt.Color(186, 123, 247));
        card2.setColor2(new java.awt.Color(167, 94, 236));

        panel1.setColor1(new java.awt.Color(22, 166, 243));
        panel1.setColor2(new java.awt.Color(51, 102, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nama pengeluaran");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Biaya");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Tanggal");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Jumlah");

        p.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pActionPerformed(evt);
            }
        });

        b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bActionPerformed(evt);
            }
        });

        t.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tActionPerformed(evt);
            }
        });

        button1.setBackground(new java.awt.Color(51, 153, 255));
        button1.setForeground(new java.awt.Color(255, 255, 255));
        button1.setText("TAMBAH");
        button1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Input Pengeluaran");

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(j, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                                .addComponent(t, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel1Layout.createSequentialGroup()
                                    .addComponent(b, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(p, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(j, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(t, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 33, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 225, Short.MAX_VALUE)
                    .addComponent(card2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                    .addComponent(card1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bActionPerformed

    private void tActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tActionPerformed

    private void pActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
//    String jumlah = j.getText().trim();
//    if //*(!jumlah.isEmpty())*// {
         try {
            String sql = "INSERT INTO pengeluaran (nama_pengeluaran,tanggal,biaya) VALUES('"+p.getText()+"','"
            +t.getText()+"','"+b.getText()+"')";
//            String sqll = "INSERT INTO inventory (nama_pengeluaran,tanggal,biaya) VALUES('"+p.getText()+"','"
//            +t.getText()+"','"+b.getText()+"')";
            java.sql.Connection conn=config.configDB();
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Pengeluaran Berhasil di tambah");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        load_table();
        clear();
    }//GEN-LAST:event_button1ActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
    if (selectedRow != -1) {
            load_table();      
    } try {
            String sql = "UPDATE `pengeluaran` SET `nama_pengeluaran`='"+p.getText()+"',`tanggal`='"+t.getText()+"',`biaya`='"+b.getText()+"' WHERE id_pengeluaran = '" + in + "'";
            java.sql.Connection conn = config.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Pengeluaran Berhasil di ubah");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "perubahan data gagal " + e.getMessage());
        }
        load_table();
        clear();
    }//GEN-LAST:event_button2ActionPerformed

    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed
    if (selectedRow != -1) {
            load_table();      
    } try {
            String sql = "DELETE FROM pengeluaran WHERE id_pengeluaran = '"+ in +"' ";
            java.sql.Connection conn = config.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Pengeluaran Berhasil di hapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "perubahan data gagal " + e.getMessage());
        }
        load_table();
        clear();
    }//GEN-LAST:event_button3ActionPerformed

    private void table2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table2MouseClicked
        // TODO add your handling code here:
    int rowCount = (table2.getRowCount());
        int baris = table2.rowAtPoint(evt.getPoint());
        selectedRow = table2.getSelectedRow();
        in = 0;
        if (table2.getValueAt(baris, 1) == null) {
            p.setText("");
        } else {
            p.setText(table2.getValueAt(baris, 1).toString());
        }
        if (table2.getValueAt(baris, 3) == null) {
            b.setText("");
        } else {
            b.setText(table2.getValueAt(baris, 3).toString());
        }

        if (selectedRow >= 0 && selectedRow <= rowCount) {
            in = rowCount - selectedRow;
        } else {
        }
        ArrayList<String> idPengeluaranList = new ArrayList<>();
        String id_pengeluaran = null;
        try {
            String sql = "select id_pengeluaran FROM pengeluaran ORDER BY id_pengeluaran DESC LIMIT " + table2.getRowCount() + ";";
            java.sql.Connection conn = (Connection) config.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                id_pengeluaran = res.getString(1);
                idPengeluaranList.add(id_pengeluaran);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(in);
        in = Integer.valueOf(idPengeluaranList.get((in - 1)));
    }//GEN-LAST:event_table2MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private apk.laundry.model.TextField b;
    private apk.laundry.model.Button button1;
    private apk.laundry.model.Button button2;
    private apk.laundry.model.Button button3;
    private apk.laundry.component.Card card1;
    private apk.laundry.component.Card card2;
    private apk.laundry.model.TextField j;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private apk.laundry.model.TextField p;
    private apk.laundry.component.panel panel1;
    private apk.laundry.swing.PanelBorder panelBorder1;
    private apk.laundry.model.TextField t;
    private apk.laundry.swing.Table table2;
    private apk.laundry.swing.Table table3;
    // End of variables declaration//GEN-END:variables

    public void initialize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
