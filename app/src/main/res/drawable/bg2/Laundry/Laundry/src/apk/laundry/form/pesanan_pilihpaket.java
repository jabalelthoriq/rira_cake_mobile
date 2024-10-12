package apk.laundry.form;

import java.sql.Connection;
import apk.laundry.form.*;
import apk.laundry.main.Main;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.CardLayout;
import javax.swing.SwingUtilities;
import apk.laundry.form.pesanan_validasi;

public class pesanan_pilihpaket extends javax.swing.JPanel {
    private DefaultTableModel model;  // pindahkan deklarasi ke atas agar dapat diakses di seluruh metode
    private int berat;
    private int jumlah;
    private Object layanan;
    private Object datenext;
    private int subtotal;
    private int harga;

    private int in;
    private int selectedRow;
    private final CardLayout cardLayout;

    /**
     * Creates new form Form_1
     */
    public pesanan_pilihpaket() {
        initComponents();
        this.cardLayout = new CardLayout();
        this.setLayout(cardLayout);
        model = new DefaultTableModel(){  // inisialisasi model tabel di sini
        public boolean isCellEditable(int row, int column) {
        return !(column >= 0 && column <= 7);
        }   };
        model.addColumn("Nama Layanan");
        model.addColumn("Berat(kg)");
        model.addColumn("Jumlah (pcs)");
        model.addColumn("Parfum");
        table1.setModel(model);
    }
    
    private void load_table() {
        String inputText = txt_berat.getText();
        berat = Integer.parseInt(inputText);
        String inputText2 = txt_jumlah.getText();
        jumlah = Integer.parseInt(inputText2);
        if (btn_ecs.isSelected()) {
            layanan = "EXPCLS";
            datenext = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            harga = 8000;
            subtotal = berat * harga;
        } else if (btn_ecl.isSelected()) {
            layanan = "EXPCL";
            datenext = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            harga = 7000;
            subtotal = berat * harga;
        } else if (btn_es.isSelected()) {
            layanan = "EXPSTR";
            datenext = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            harga = 7000;
            subtotal = berat * harga;
        } else if (btn_rcs.isSelected()) {
            layanan = "REGRCLS";
            datenext = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            harga = 5000;
            subtotal = berat * harga;
        } else if (btn_rcl.isSelected()) {
            layanan = "REGRCL";
            datenext = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            harga = 4000;
            subtotal = berat * harga;
        } else if (btn_rs.isSelected()) {
            layanan = "REGRSTR";
            datenext = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            harga = 4000;
            subtotal = berat * harga;
        } else if (btn_selimut.isSelected()) {
            layanan = "SELIMUT";
            datenext = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            harga = 15000;
            subtotal = jumlah * harga;
        } else if (btn_sprei.isSelected()) {
            layanan = "SPREI";
            datenext = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            harga = 10000;
            subtotal = jumlah * harga;
        } else if (btn_sepatu.isSelected()) {
            layanan = "SEPATU";
            datenext = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            harga = 20000;
            subtotal = jumlah * harga;
        } else if (btn_tas.isSelected()) {
            layanan = "TAS";
            datenext = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            harga = 20000;
            subtotal = jumlah * harga;
        } else if (btn_boneka.isSelected()) {
            layanan = "BONEKA";
            datenext = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            harga = 15000;
            subtotal = berat * harga;
        }

    }
    
    private void kosong() {
        txt_berat.setText("");
        txt_jumlah.setText("");
        btn_rcs.setSelected(false);
        btn_rcl.setSelected(false);
        btn_rs.setSelected(false);
        btn_ecs.setSelected(false);
        btn_ecl.setSelected(false);
        btn_es.setSelected(false);
        btn_selimut.setSelected(false);
        btn_sepatu.setSelected(false);
        btn_sprei.setSelected(false);
        btn_tas.setSelected(false);
        btn_boneka.setSelected(false);

    }

    public pesanan_pilihpaket(CardLayout cardLayout) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        jLabel3 = new javax.swing.JLabel();
        panel1 = new apk.laundry.component.panel();
        jLabel4 = new javax.swing.JLabel();
        panel4 = new apk.laundry.component.panel();
        btn_rcs = new apk.laundry.model.RadioButtonCustom();
        panel5 = new apk.laundry.component.panel();
        btn_rcl = new apk.laundry.model.RadioButtonCustom();
        panel6 = new apk.laundry.component.panel();
        btn_rs = new apk.laundry.model.RadioButtonCustom();
        panel3 = new apk.laundry.component.panel();
        jLabel9 = new javax.swing.JLabel();
        panel10 = new apk.laundry.component.panel();
        btn_selimut = new apk.laundry.model.RadioButtonCustom();
        panel11 = new apk.laundry.component.panel();
        btn_sprei = new apk.laundry.model.RadioButtonCustom();
        panel12 = new apk.laundry.component.panel();
        btn_sepatu = new apk.laundry.model.RadioButtonCustom();
        panel13 = new apk.laundry.component.panel();
        btn_tas = new apk.laundry.model.RadioButtonCustom();
        panel14 = new apk.laundry.component.panel();
        btn_boneka = new apk.laundry.model.RadioButtonCustom();
        panel2 = new apk.laundry.component.panel();
        jLabel8 = new javax.swing.JLabel();
        panel7 = new apk.laundry.component.panel();
        btn_ecs = new apk.laundry.model.RadioButtonCustom();
        panel8 = new apk.laundry.component.panel();
        btn_ecl = new apk.laundry.model.RadioButtonCustom();
        panel9 = new apk.laundry.component.panel();
        btn_es = new apk.laundry.model.RadioButtonCustom();
        jLabel5 = new javax.swing.JLabel();
        txt_jumlah = new apk.laundry.model.TextField();
        jLabel6 = new javax.swing.JLabel();
        txt_berat = new apk.laundry.model.TextField();
        jLabel10 = new javax.swing.JLabel();
        btn_add = new apk.laundry.model.Button();
        cmb_farfum = new apk.laundry.model.Combobox();
        btn_edit = new apk.laundry.model.Button();
        btn_delete = new apk.laundry.model.Button();
        btn_next = new apk.laundry.model.Button();
        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new apk.laundry.swing.Table();

        setBackground(new java.awt.Color(242, 242, 242));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1097, 708));

        panelBorder1.setBackground(new java.awt.Color(242, 242, 242));

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(127, 127, 127));
        jLabel3.setText("PAKET");

        panel1.setColor1(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(40, 58, 118));
        jLabel4.setText("REGULAR");

        panel4.setColor1(new java.awt.Color(22, 166, 243));
        panel4.setColor2(new java.awt.Color(31, 150, 214));

        btn_rcs.setForeground(new java.awt.Color(255, 255, 255));
        btn_rcs.setText("    CUCI SETRIKA");
        btn_rcs.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout panel4Layout = new javax.swing.GroupLayout(panel4);
        panel4.setLayout(panel4Layout);
        panel4Layout.setHorizontalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_rcs, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel4Layout.setVerticalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_rcs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panel5.setColor1(new java.awt.Color(22, 166, 243));
        panel5.setColor2(new java.awt.Color(31, 150, 214));

        btn_rcl.setForeground(new java.awt.Color(255, 255, 255));
        btn_rcl.setText("       CUCI LIPAT");
        btn_rcl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_rcl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_rclActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel5Layout = new javax.swing.GroupLayout(panel5);
        panel5.setLayout(panel5Layout);
        panel5Layout.setHorizontalGroup(
            panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_rcl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel5Layout.setVerticalGroup(
            panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_rcl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panel6.setColor1(new java.awt.Color(22, 166, 243));
        panel6.setColor2(new java.awt.Color(31, 150, 214));

        btn_rs.setForeground(new java.awt.Color(255, 255, 255));
        btn_rs.setText("        SETRIKA");
        btn_rs.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout panel6Layout = new javax.swing.GroupLayout(panel6);
        panel6.setLayout(panel6Layout);
        panel6Layout.setHorizontalGroup(
            panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_rs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel6Layout.setVerticalGroup(
            panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_rs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel3.setColor1(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(40, 58, 118));
        jLabel9.setText("SATUAN");

        panel10.setColor1(new java.awt.Color(22, 166, 243));
        panel10.setColor2(new java.awt.Color(31, 150, 214));

        btn_selimut.setForeground(new java.awt.Color(255, 255, 255));
        btn_selimut.setText("          SELIMUT");
        btn_selimut.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        javax.swing.GroupLayout panel10Layout = new javax.swing.GroupLayout(panel10);
        panel10.setLayout(panel10Layout);
        panel10Layout.setHorizontalGroup(
            panel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_selimut, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel10Layout.setVerticalGroup(
            panel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_selimut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel11.setColor1(new java.awt.Color(22, 166, 243));
        panel11.setColor2(new java.awt.Color(31, 150, 214));

        btn_sprei.setForeground(new java.awt.Color(255, 255, 255));
        btn_sprei.setText("            SPREI");
        btn_sprei.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        javax.swing.GroupLayout panel11Layout = new javax.swing.GroupLayout(panel11);
        panel11.setLayout(panel11Layout);
        panel11Layout.setHorizontalGroup(
            panel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_sprei, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel11Layout.setVerticalGroup(
            panel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_sprei, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel12.setColor1(new java.awt.Color(22, 166, 243));
        panel12.setColor2(new java.awt.Color(31, 150, 214));

        btn_sepatu.setForeground(new java.awt.Color(255, 255, 255));
        btn_sepatu.setText("          SEPATU");
        btn_sepatu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        javax.swing.GroupLayout panel12Layout = new javax.swing.GroupLayout(panel12);
        panel12.setLayout(panel12Layout);
        panel12Layout.setHorizontalGroup(
            panel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_sepatu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel12Layout.setVerticalGroup(
            panel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_sepatu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panel13.setColor1(new java.awt.Color(22, 166, 243));
        panel13.setColor2(new java.awt.Color(31, 150, 214));

        btn_tas.setForeground(new java.awt.Color(255, 255, 255));
        btn_tas.setText("             TAS");
        btn_tas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        javax.swing.GroupLayout panel13Layout = new javax.swing.GroupLayout(panel13);
        panel13.setLayout(panel13Layout);
        panel13Layout.setHorizontalGroup(
            panel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_tas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel13Layout.setVerticalGroup(
            panel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_tas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panel14.setColor1(new java.awt.Color(22, 166, 243));
        panel14.setColor2(new java.awt.Color(31, 150, 214));

        btn_boneka.setForeground(new java.awt.Color(255, 255, 255));
        btn_boneka.setText("         BONEKA");
        btn_boneka.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        javax.swing.GroupLayout panel14Layout = new javax.swing.GroupLayout(panel14);
        panel14.setLayout(panel14Layout);
        panel14Layout.setHorizontalGroup(
            panel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_boneka, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel14Layout.setVerticalGroup(
            panel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_boneka, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel3Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panel3Layout.createSequentialGroup()
                                .addComponent(panel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(panel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(20, 20, 20))
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        panel2.setColor1(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(40, 58, 118));
        jLabel8.setText("EXPRESS");

        panel7.setColor1(new java.awt.Color(22, 166, 243));
        panel7.setColor2(new java.awt.Color(31, 150, 214));

        btn_ecs.setForeground(new java.awt.Color(255, 255, 255));
        btn_ecs.setText("   CUCI SETRIKA");
        btn_ecs.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout panel7Layout = new javax.swing.GroupLayout(panel7);
        panel7.setLayout(panel7Layout);
        panel7Layout.setHorizontalGroup(
            panel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_ecs, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
        );
        panel7Layout.setVerticalGroup(
            panel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_ecs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel8.setColor1(new java.awt.Color(22, 166, 243));
        panel8.setColor2(new java.awt.Color(31, 150, 214));

        btn_ecl.setForeground(new java.awt.Color(255, 255, 255));
        btn_ecl.setText("    CUCI LIPAT");
        btn_ecl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout panel8Layout = new javax.swing.GroupLayout(panel8);
        panel8.setLayout(panel8Layout);
        panel8Layout.setHorizontalGroup(
            panel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_ecl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel8Layout.setVerticalGroup(
            panel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_ecl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel9.setColor1(new java.awt.Color(22, 166, 243));
        panel9.setColor2(new java.awt.Color(31, 150, 214));

        btn_es.setForeground(new java.awt.Color(255, 255, 255));
        btn_es.setText("       SETRIKA");
        btn_es.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout panel9Layout = new javax.swing.GroupLayout(panel9);
        panel9.setLayout(panel9Layout);
        panel9Layout.setHorizontalGroup(
            panel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_es, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel9Layout.setVerticalGroup(
            panel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_es, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("BERAT");

        txt_jumlah.setShadowColor(new java.awt.Color(22, 166, 243));
        txt_jumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jumlahActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("JUMLAH PAKAIAN");

        txt_berat.setShadowColor(new java.awt.Color(22, 166, 243));
        txt_berat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_beratActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Kg");

        btn_add.setBackground(new java.awt.Color(22, 166, 243));
        btn_add.setForeground(new java.awt.Color(255, 255, 255));
        btn_add.setText("TAMBAH");
        btn_add.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        cmb_farfum.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A", "B", "C" }));
        cmb_farfum.setSelectedIndex(-1);
        cmb_farfum.setToolTipText("");
        cmb_farfum.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmb_farfum.setPreferredSize(new java.awt.Dimension(88, 50));
        cmb_farfum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_farfumActionPerformed(evt);
            }
        });

        btn_edit.setBackground(new java.awt.Color(241, 208, 62));
        btn_edit.setForeground(new java.awt.Color(255, 255, 255));
        btn_edit.setText("EDIT");
        btn_edit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });

        btn_delete.setBackground(new java.awt.Color(255, 51, 102));
        btn_delete.setForeground(new java.awt.Color(255, 255, 255));
        btn_delete.setText("HAPUS");
        btn_delete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_next.setBackground(new java.awt.Color(51, 255, 51));
        btn_next.setForeground(new java.awt.Color(255, 255, 255));
        btn_next.setText("LANJUT");
        btn_next.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nextActionPerformed(evt);
            }
        });

        table1.setModel(new javax.swing.table.DefaultTableModel(
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
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table1);

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(471, 471, 471)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder1Layout.createSequentialGroup()
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelBorder1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelBorder1Layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelBorder1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_berat, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)))
                        .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelBorder1Layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                        .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmb_farfum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(36, 36, 36)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btn_next, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(49, 49, 49))
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txt_berat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_farfum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorder1Layout.createSequentialGroup()
                        .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_next, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(panelBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txt_jumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumlahActionPerformed

    private void txt_beratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_beratActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_beratActionPerformed

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
    if (txt_berat.getText().equals("") || txt_jumlah.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Silahkan masukkan berat atau jumlah");
        } else {
            load_table();
            try {
                String sql = "select nama_layanan from layanan where id_layanan = '" + layanan + "'";

                java.sql.Connection conn = (Connection) config.configDB();
                java.sql.Statement stm = conn.createStatement();
                java.sql.ResultSet res = stm.executeQuery(sql);

                while (res.next()) {
                    model.addRow(new Object[]{res.getString(1), txt_berat.getText(), txt_jumlah.getText(), cmb_farfum.getSelectedItem()});
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            String sql = "INSERT INTO detail_pesanan (`no_pesanan`,`id_layanan`,`parfum`,`berat`,`jumlah`,`tgl_selesai`,`subtotal_harga`) VALUES ("
                    + "(SELECT no_pesanan FROM pesanan ORDER BY no_pesanan DESC LIMIT 1),'"
                    + layanan + "','" + cmb_farfum.getSelectedItem() + "','" + txt_berat.getText() + "','" + txt_jumlah.getText()
                    + "','" + datenext + "','"+subtotal+"')";

            java.sql.Connection conn = config.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        kosong();
    }//GEN-LAST:event_btn_addActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
    if (selectedRow != -1) {
            load_table();
            try {
                String sql = "select nama_layanan from layanan where id_layanan = '" + layanan + "'";

                java.sql.Connection conn = (Connection) config.configDB();
                java.sql.Statement stm = conn.createStatement();
                java.sql.ResultSet res = stm.executeQuery(sql);

                while (res.next()) {
                    model.setValueAt(res.getString(1), selectedRow, 0);
                    model.setValueAt(String.valueOf(txt_berat.getText()), selectedRow, 1);
                    model.setValueAt(String.valueOf(txt_jumlah.getText()), selectedRow, 2);
                    model.setValueAt(String.valueOf(cmb_farfum.getSelectedItem()), selectedRow, 3);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            // Tidak ada baris yang dipilih, Anda dapat menampilkan pesan atau melakukan tindakan lainnya
            System.out.println("Tidak ada baris yang dipilih");
        }
        try {
            String sql = "UPDATE detail_pesanan "
                    + "SET id_layanan = '" + layanan + "',parfum = '" + cmb_farfum.getSelectedItem() + "',berat = '" + txt_berat.getText() + "', jumlah = '"
                    + txt_jumlah.getText() + "' WHERE id_detail = '" + in + "';";
            java.sql.Connection conn = config.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "perubahan data gagal " + e.getMessage());
        }
        load_table();
        kosong();
        repaint();
    }//GEN-LAST:event_btn_editActionPerformed

    private void cmb_farfumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_farfumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_farfumActionPerformed

    private void table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table1MouseClicked
    int rowCount = (table1.getRowCount());
        int baris = table1.rowAtPoint(evt.getPoint());
        selectedRow = table1.getSelectedRow();
        in = 0;
        if (table1.getValueAt(baris, 1) == null) {
            txt_berat.setText("");
        } else {
            txt_berat.setText(table1.getValueAt(baris, 1).toString());
        }
        if (table1.getValueAt(baris, 2) == null) {
            txt_jumlah.setText("");
        } else {
            txt_jumlah.setText(table1.getValueAt(baris, 2).toString());
        }

        if (selectedRow >= 0 && selectedRow <= rowCount) {
            in = rowCount - selectedRow;
        } else {
        }
        ArrayList<String> noPesananList = new ArrayList<>();
        String no_pesanan = null;
        try {
            String sql = "select id_detail FROM detail_pesanan ORDER BY no_pesanan DESC LIMIT " + model.getRowCount() + ";";
            java.sql.Connection conn = (Connection) config.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                no_pesanan = res.getString(1);
                noPesananList.add(no_pesanan);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(in);
        in = Integer.valueOf(noPesananList.get((in - 1)));
    }//GEN-LAST:event_table1MouseClicked

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
    if (selectedRow != -1) {
            // Hapus baris yang dipilih dari model tabel
            model.removeRow(selectedRow);
        } else {
            // Tidak ada baris yang dipilih, Anda dapat menampilkan pesan atau melakukan tindakan lainnya
            System.out.println("Tidak ada baris yang dipilih");
        }
        try {

            String sql = "delete from detail_pesanan where id_detail = '" + in + "';";
            java.sql.Connection conn = (Connection) config.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();

            table1.setModel(model);
        } catch (Exception e) {

        }
        kosong();
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nextActionPerformed
   try {
        pesanan_validasi validasi = new pesanan_validasi();
        removeAll();
        add("panelPesananValidasi", validasi);
        cardLayout.show(this,"panelPesananValidasi");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
//        } catch (SQLException ex) {
//           Logger.getLogger(pesanan_pilihpaket.class.getName()).log(Level.SEVERE, null, ex);
    }//GEN-LAST:event_btn_nextActionPerformed

    private void btn_rclActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_rclActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_rclActionPerformed
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private apk.laundry.model.Button btn_add;
    private apk.laundry.model.RadioButtonCustom btn_boneka;
    private apk.laundry.model.Button btn_delete;
    private apk.laundry.model.RadioButtonCustom btn_ecl;
    private apk.laundry.model.RadioButtonCustom btn_ecs;
    private apk.laundry.model.Button btn_edit;
    private apk.laundry.model.RadioButtonCustom btn_es;
    private apk.laundry.model.Button btn_next;
    private apk.laundry.model.RadioButtonCustom btn_rcl;
    private apk.laundry.model.RadioButtonCustom btn_rcs;
    private apk.laundry.model.RadioButtonCustom btn_rs;
    private apk.laundry.model.RadioButtonCustom btn_selimut;
    private apk.laundry.model.RadioButtonCustom btn_sepatu;
    private apk.laundry.model.RadioButtonCustom btn_sprei;
    private apk.laundry.model.RadioButtonCustom btn_tas;
    private apk.laundry.model.Combobox cmb_farfum;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private apk.laundry.component.panel panel1;
    private apk.laundry.component.panel panel10;
    private apk.laundry.component.panel panel11;
    private apk.laundry.component.panel panel12;
    private apk.laundry.component.panel panel13;
    private apk.laundry.component.panel panel14;
    private apk.laundry.component.panel panel2;
    private apk.laundry.component.panel panel3;
    private apk.laundry.component.panel panel4;
    private apk.laundry.component.panel panel5;
    private apk.laundry.component.panel panel6;
    private apk.laundry.component.panel panel7;
    private apk.laundry.component.panel panel8;
    private apk.laundry.component.panel panel9;
    private apk.laundry.swing.PanelBorder panelBorder1;
    private apk.laundry.swing.Table table1;
    private apk.laundry.model.TextField txt_berat;
    private apk.laundry.model.TextField txt_jumlah;
    // End of variables declaration//GEN-END:variables
}
