package apk.laundry.component;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import apk.laundry.event.SearchListener;
import apk.laundry.main.Main;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.util.Date;
import javax.swing.Timer;



public class Header extends javax.swing.JPanel {
    private SearchListener searchListener;
    private Main parentFrame;
    public Header() {
        initComponents();
        setOpaque(false);
        date();
        time();
    }
    
    public void setSearchListener(SearchListener listener) {
        this.searchListener = listener;
    }
    public void date(){
        Date d = new Date();
        SimpleDateFormat s=new SimpleDateFormat("dd-MM-yyyy");
        String dat = s.format(d);
        date.setText(dat);
    }
    public void time(){
        new Timer (0,new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                Date d = new Date();
                SimpleDateFormat s=new SimpleDateFormat("hh-mm-ss");
                String tim=s.format(d);
                time.setText(tim);
            }   
        }).start();
    }
     
   

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        date = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        time = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(688, 40));

        jLabel1.setForeground(new java.awt.Color(22, 166, 243));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/apk/laundry/icon/clock_24px.png"))); // NOI18N

        date.setBackground(new java.awt.Color(187, 187, 187));
        date.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        date.setText("date");

        jLabel4.setForeground(new java.awt.Color(22, 166, 243));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/apk/laundry/icon/Calendar_24px.png"))); // NOI18N

        time.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        time.setText("time");

        jLabel2.setForeground(new java.awt.Color(22, 166, 243));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/apk/laundry/icon/Power Off Button.png"))); // NOI18N
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(date)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(time)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 519, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(date)
                            .addComponent(time))))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        int dialogResult = JOptionPane.showConfirmDialog
        (this, "Apakah anda yakin ingin meninggalkan aplikasi?", "KONFIRMASI", JOptionPane.YES_NO_OPTION);
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (dialogResult == JOptionPane.YES_OPTION){
            JFrame parentframe = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (parentFrame != null) {
            parentFrame.dispose();  // Close the JFrame
            System.exit(0);  // Terminate the application
        }
        }
    }//GEN-LAST:event_jLabel2MouseClicked
    
    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.fillRect(0, 0, 25, getHeight());
        g2.fillRect(getWidth() - 25, getHeight() - 25, getWidth(), getHeight());
        super.paintComponent(grphcs);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel date;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel time;
    // End of variables declaration//GEN-END:variables
}
