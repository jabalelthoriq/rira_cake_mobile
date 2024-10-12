package apk.laundry.form;
import apk.laundry.form.pesanan_pilihpaket;
import apk.laundry.form.*;
import apk.laundry.swing.ScrollBar;
import apk.laundry.form.pemesanan;
import apk.laundry.swing.Table;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.awt.print.Paper;
import java.awt.print.Printable;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.List;
import java.awt.print.PrinterException;
import java.util.ArrayList;

public class pesanan_validasi extends javax.swing.JPanel {
     private DefaultTableModel model;
     private CardLayout cardLayout;
     Double bHeight=0.0;
     public PageFormat getPageFormat (PrinterJob pj){
         PageFormat pf = pj.defaultPage();
         Paper paper = pf.getPaper();
         
         double bodyHeight = bHeight;
         double headerHeight = 5.0;
         double footerHeight = 5.0;
         double width = cm_to_pp(8);
         double height = cm_to_pp(headerHeight+bodyHeight+footerHeight);
         paper.setSize(width, height);
         paper.setImageableArea(0,10,width,height - cm_to_pp(1));
         pf.setOrientation(PageFormat.PORTRAIT);
         pf.setPaper(paper);
         
         return pf;
   }
     
     public class BillPrintable implements Printable {
        public int print(Graphics graphics, PageFormat pageFormat,int pageIndex) 
        throws PrinterException {    
      
            int result = NO_SUCH_PAGE;    
            if (pageIndex == 0) {                    
        
                Graphics2D g2d = (Graphics2D) graphics;                    
                double width = pageFormat.getImageableWidth();                               
                g2d.translate((int) pageFormat.getImageableX(),(int) pageFormat.getImageableY()); 

         FontMetrics metrics=g2d.getFontMetrics(new Font("Arial",Font.BOLD,7));
        
        try{
            int y=20;
            int yShift = 10;
            int headerRectHeight=15;
            int headerRectHeighta=40;
               
            g2d.setFont(new Font("Monospaced",Font.PLAIN,9));
            g2d.drawString("-------------------------------------",12,y);y+=yShift;
            g2d.drawString("                                     ",10,y);y+=yShift;
            g2d.drawString("         KURNIA FRESH LAUNDRY        ",12,y);y+=yShift;
            g2d.drawString("      No 00000 Address Line One      ",12,y);y+=yShift;
            g2d.drawString("             087881652988            ",12,y);y+=yShift;
            g2d.drawString("-------------------------------------",12,y);y+=headerRectHeight;
            g2d.drawString(" Nama   : "+txt_nama.getText()+"   ",10,y);y+=yShift;
            g2d.drawString(" Alamat : "+txt_alamat.getText()+"    ",10,y);y+=yShift;
            g2d.drawString(" No HP  : "+txt_nohp.getText()+"    ",10,y);y+=yShift;
            g2d.drawString("-------------------------------------",10,y);y+=yShift;
            g2d.drawString(" Layanan   :                         ",10,y);y+=yShift;
            g2d.drawString("                                     ",10,y);y+=yShift;
            g2d.drawString("                                     ",10,y);y+=yShift;
            g2d.drawString("                                     ",10,y);y+=yShift;
            g2d.drawString("                                     ",10,y);y+=yShift;
            g2d.drawString("                                     ",10,y);y+=yShift;
            g2d.drawString("-------------------------------------",10,y);y+=yShift;
            g2d.drawString("                                     ",10,y);y+=yShift;
            g2d.drawString(" Total     : RP."+txt_totalharga.getText()+"   ",10,y);y+=yShift;
            g2d.drawString(" Bayar     : RP."+txt_bayar.getText()+"   ",10,y);y+=yShift;
            g2d.drawString(" Kembalian : RP."+txt_kembali.getText()+"   ",10,y);y+=yShift;
            g2d.drawString("*************************************",10,y);y+=yShift;
            g2d.drawString("                                     ",10,y);y+=yShift;
            g2d.drawString("            TERIMA KASIH             ",10,y);y+=yShift;
            g2d.drawString("                                     ",10,y);y+=yShift;
            g2d.drawString("*************************************",10,y);y+=yShift;
      
 
    }
    catch(Exception e){
    e.printStackTrace();
    }

              result = PAGE_EXISTS;    
          }    
          return result;    
      }
   }
     protected static double cm_to_pp(double cm){
         return toPPI(cm * 0.393600787);
     }
     
     protected static double toPPI(double inch){
         return inch * 72d;
    }
             
    /**
     * Creates new form Form_1
     */
    public pesanan_validasi() {
        initComponents();
        load_table();
        jScrollPane1.setViewportView(jTable1);
        jScrollPane1.setVerticalScrollBar(new ScrollBar());
        jScrollPane1.getVerticalScrollBar().setBackground(Color.WHITE);
        jScrollPane1.getViewport().setBackground(Color.WHITE);
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        jScrollPane1.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        String datenow = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        txt_tglmasuk.setText(datenow);
        txt_tglmasuk.disable();
        try {
            String sql = "SELECT * FROM customer order by id_customer desc limit 1";
            java.sql.Connection conn = (Connection) config.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);

            while (res.next()) {
                txt_nama.setText(res.getString("nama_customer"));
                txt_alamat.setText(res.getString("alamat_customer"));
                txt_nohp.setText(res.getString("no_hp"));
            }

           
            stm.close();
            res.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        txt_nama.disable();
        txt_alamat.disable();
        txt_nohp.disable();
        try {
            String sql = "SELECT no_pesanan,sum(subtotal_harga) as totalharga FROM detail_pesanan group by no_pesanan order by no_pesanan desc limit 1";
            java.sql.Connection conn = (Connection) config.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);

            while (res.next()) {
                txt_totalharga.setText(res.getString(2));
            }

            // Tidak lupa untuk menutup statement dan result set
            stm.close();
            res.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace(); // Atau tangani eksepsi sesuai kebutuhan Anda
        }
        txt_totalharga.disable();
    }
    public pesanan_validasi(CardLayout cardLayout1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
     private int limit() throws SQLException {
        int total = 0;
        String sql = "SELECT COUNT(detail_pesanan.no_pesanan) as jumlah FROM detail_pesanan\n" +
"JOIN pesanan ON pesanan.no_pesanan = detail_pesanan.no_pesanan\n" +
"GROUP BY pesanan.no_pesanan ORDER BY pesanan.no_pesanan DESC LIMIT 1";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            total = rs.getInt("jumlah");
        }
        return total;
    }
     
    private void load_table(){
        model = new DefaultTableModel(){
                    public boolean isCellEditable(int row, int column) {
        return !(column >= 0 && column <= 7);
        }   }; 
        model.addColumn("Nama Layanan");
        model.addColumn("Parfum");
        model.addColumn("Berat (kg)");
        model.addColumn("Harga");
        model.addColumn("Sub Total");
        model.addColumn("Tgl Selesai");
         try {
            int no = 1;
            String sql = "SELECT layanan.nama_layanan,\n" +
"    detail_pesanan.parfum,\n" +
"    detail_pesanan.berat,\n" +
"    layanan.harga,\n" +
"    detail_pesanan.subtotal_harga,\n" +
"    detail_pesanan.tgl_selesai\n" +
"FROM\n" +
"    detail_pesanan\n" +
"JOIN\n" +
"    pesanan ON pesanan.no_pesanan = detail_pesanan.no_pesanan\n" +
"JOIN\n" +
"    layanan ON detail_pesanan.id_layanan = layanan.id_layanan\n" +
"ORDER BY pesanan.tgl_pesanan DESC limit "+String.valueOf(limit())+"";
            java.sql.Connection conn = (Connection)config.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while(res.next()){
                model.addRow(new Object[] {
                    res.getString(1), res.getString(2), res.getString(3),
                     res.getString(4), res.getString(5), res.getString(6)});
            }
            jTable1.setModel(model);
        } catch (Exception e){
            JOptionPane.showMessageDialog(this,e.getMessage());
        }
    }
    private List<RowData> getAllRowsData(Table jTable1) {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    int rowCount = model.getRowCount();
    List<RowData> rowDataList = new ArrayList<>();

    // Assuming the column indices for the required data
    int namaLayananColumnIndex = 0;
    int beratColumnIndex = 1;
    int jumlahColumnIndex = 2;
    int tanggalSelesaiColumnIndex = 3;

    for (int row = 0; row < rowCount; row++) {
        String namaLayanan = model.getValueAt(row, namaLayananColumnIndex).toString();
        String berat = model.getValueAt(row, beratColumnIndex).toString();
        String jumlah = model.getValueAt(row, jumlahColumnIndex).toString();
        String tanggalSelesai = model.getValueAt(row, tanggalSelesaiColumnIndex).toString();

        RowData rowData = new RowData(namaLayanan, berat, jumlah, tanggalSelesai);
        rowDataList.add(rowData);
    }

    return rowDataList;
}

class RowData {
    private String namaLayanan;
    private String berat;
    private String jumlah;
    private String tanggalSelesai;

    public RowData(String namaLayanan, String berat, String jumlah, String tanggalSelesai) {
        this.namaLayanan = namaLayanan;
        this.berat = berat;
        this.jumlah = jumlah;
        this.tanggalSelesai = tanggalSelesai;
    }

    // Add getters if needed
}

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelBorder2 = new apk.laundry.swing.PanelBorder();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new apk.laundry.swing.Table();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txt_totalharga = new apk.laundry.model.TextField();
        txt_bayar = new apk.laundry.model.TextField();
        txt_kembali = new apk.laundry.model.TextField();
        button2 = new apk.laundry.model.Button();
        button3 = new apk.laundry.model.Button();
        button4 = new apk.laundry.model.Button();
        jLabel13 = new javax.swing.JLabel();
        button5 = new apk.laundry.model.Button();
        panel4 = new apk.laundry.component.panel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_nama = new apk.laundry.model.TextField();
        txt_nohp = new apk.laundry.model.TextField();
        textAreaScroll1 = new textarea.TextAreaScroll();
        txt_alamat = new textarea.TextArea();
        jLabel7 = new javax.swing.JLabel();
        txt_tglmasuk = new apk.laundry.model.TextField();
        jLabel8 = new javax.swing.JLabel();
        cmb_mesin = new apk.laundry.model.ComboBoxSuggestion();
        jLabel12 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(242, 242, 242));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1097, 708));

        jPanel1.setBackground(new java.awt.Color(242, 242, 242));
        jPanel1.setPreferredSize(new java.awt.Dimension(1097, 708));

        panelBorder2.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(127, 127, 127));
        jLabel3.setText("PAKET");

        jLabel9.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(153, 153, 153));
        jLabel9.setText("Kembali");

        jLabel10.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(153, 153, 153));
        jLabel10.setText("Total Harga");

        jLabel11.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(153, 153, 153));
        jLabel11.setText("Kembali");

        txt_totalharga.setShadowColor(new java.awt.Color(22, 166, 243));

        txt_bayar.setShadowColor(new java.awt.Color(22, 166, 243));

        txt_kembali.setShadowColor(new java.awt.Color(22, 166, 243));

        button2.setBackground(new java.awt.Color(22, 166, 243));
        button2.setForeground(new java.awt.Color(255, 255, 255));
        button2.setText("Bayar");
        button2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        button3.setBackground(new java.awt.Color(22, 166, 243));
        button3.setForeground(new java.awt.Color(255, 255, 255));
        button3.setText("Cetak Resi");
        button3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3ActionPerformed(evt);
            }
        });

        button4.setBackground(new java.awt.Color(22, 166, 243));
        button4.setForeground(new java.awt.Color(255, 255, 255));
        button4.setText("Kembali");
        button4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button4ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(153, 153, 153));
        jLabel13.setText("Bayar");

        button5.setBackground(new java.awt.Color(22, 166, 243));
        button5.setForeground(new java.awt.Color(255, 255, 255));
        button5.setText("Kembali");
        button5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBorder2Layout = new javax.swing.GroupLayout(panelBorder2);
        panelBorder2.setLayout(panelBorder2Layout);
        panelBorder2Layout.setHorizontalGroup(
            panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder2Layout.createSequentialGroup()
                .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelBorder2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 999, Short.MAX_VALUE)))
                    .addGroup(panelBorder2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(button3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(button4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(button2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(button5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(67, 67, 67)
                        .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelBorder2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                                .addComponent(txt_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelBorder2Layout.createSequentialGroup()
                                .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel13))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_totalharga, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                                    .addComponent(txt_bayar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(21, 21, 21)))
                .addGap(32, 32, 32))
        );
        panelBorder2Layout.setVerticalGroup(
            panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txt_totalharga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(button5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69)
                .addComponent(button4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel4.setColor1(new java.awt.Color(22, 166, 243));
        panel4.setColor2(new java.awt.Color(31, 150, 214));

        jLabel4.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Nama");

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Alamat");

        jLabel6.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("No HP");

        txt_nama.setShadowColor(new java.awt.Color(51, 57, 219));
        txt_nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaActionPerformed(evt);
            }
        });

        txt_nohp.setShadowColor(new java.awt.Color(51, 57, 219));
        txt_nohp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nohpActionPerformed(evt);
            }
        });

        textAreaScroll1.setLabelText("ALAMAT");

        txt_alamat.setColumns(20);
        txt_alamat.setForeground(new java.awt.Color(255, 255, 255));
        txt_alamat.setRows(5);
        textAreaScroll1.setViewportView(txt_alamat);

        jLabel7.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Tanggal");

        txt_tglmasuk.setShadowColor(new java.awt.Color(51, 57, 219));

        jLabel8.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Mesin");

        cmb_mesin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "M1", "M2", "M3", "M4", "M5" }));

        jLabel12.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("PESANAN");

        javax.swing.GroupLayout panel4Layout = new javax.swing.GroupLayout(panel4);
        panel4.setLayout(panel4Layout);
        panel4Layout.setHorizontalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel4Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(29, 29, 29)
                .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textAreaScroll1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nohp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 342, Short.MAX_VALUE)
                .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_tglmasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_mesin, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(475, 475, 475))
        );
        panel4Layout.setVerticalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel4Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(15, 15, 15)
                        .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txt_tglmasuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel4Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel5))
                    .addGroup(panel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cmb_mesin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)))
                    .addGroup(panel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textAreaScroll1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9)
                .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nohp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelBorder2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(panel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelBorder2, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1085, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txt_nohpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nohpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nohpActionPerformed

    private void txt_namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
    String inputText = txt_totalharga.getText();
        int harga = Integer.parseInt(inputText);
        String inputText2 = txt_bayar.getText();
        int bayar = Integer.parseInt(inputText2);
        int kembali = bayar - harga;
        txt_kembali.setText(String.valueOf(kembali));
        txt_kembali.disable();
        try {
            String sql = "insert into pesanan (`mesin`) values ('" + cmb_mesin.getSelectedItem() + "')";

            java.sql.Connection conn = (Connection) config.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_button2ActionPerformed

    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed

         PrinterJob pj = PrinterJob.getPrinterJob();
         pj.setPrintable(new BillPrintable(),getPageFormat(pj));
         try {
             pj.print();
         }
         
//        pemesanan validasi = new pemesanan();
//        removeAll();
//        add("pemesanan", validasi);
//        cardLayout.show(this,"panelPemesanan");
     catch (PrinterException ex){
        ex.printStackTrace();
     }
    }//GEN-LAST:event_button3ActionPerformed

    private void button4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button4ActionPerformed
             
        pemesanan validasi = new pemesanan();
        removeAll();
        add("pemesanan", validasi);
        cardLayout.show(this,"panelPemesanan");
    }//GEN-LAST:event_button4ActionPerformed

    private void button5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button5ActionPerformed
           pemesanan validasi = new pemesanan();
        removeAll();
        add("pemesanan", validasi);
        cardLayout.show(this,"panelPemesanan");
    }//GEN-LAST:event_button5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private apk.laundry.model.Button button2;
    private apk.laundry.model.Button button3;
    private apk.laundry.model.Button button4;
    private apk.laundry.model.Button button5;
    private apk.laundry.model.ComboBoxSuggestion cmb_mesin;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private apk.laundry.swing.Table jTable1;
    private apk.laundry.component.panel panel4;
    private apk.laundry.swing.PanelBorder panelBorder2;
    private textarea.TextAreaScroll textAreaScroll1;
    private textarea.TextArea txt_alamat;
    private apk.laundry.model.TextField txt_bayar;
    private apk.laundry.model.TextField txt_kembali;
    private apk.laundry.model.TextField txt_nama;
    private apk.laundry.model.TextField txt_nohp;
    private apk.laundry.model.TextField txt_tglmasuk;
    private apk.laundry.model.TextField txt_totalharga;
    // End of variables declaration//GEN-END:variables
}
