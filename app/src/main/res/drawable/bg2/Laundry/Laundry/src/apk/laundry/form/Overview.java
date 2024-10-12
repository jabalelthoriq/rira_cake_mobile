package apk.laundry.form;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import raven.chart.ModelChart;
import apk.laundry.model.ModelData;
import apk.laundry.model.Model_Card;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.ImageIcon;

public class Overview extends javax.swing.JPanel {

    /**
     * Creates new form Form_1
     */
    public Overview() throws SQLException {
        initComponents();
        ini.setText(Percentage()+" %");
        lalu.setText(Percentagelalu()+" %");
        card1.setData(new Model_Card(new ImageIcon(getClass().getResource("/apk/laundry/icon/stock.png")), "Transaksi Hari Ini", "Rp. " + formatn(profittdy()), "jumlah pesanan masuk"));
        card2.setData(new Model_Card(new ImageIcon(getClass().getResource("/apk/laundry/icon/flag.png")), "Transaksi Bulan Ini", "Rp. " + formatn(profittm()), "jumlah pesanan selesai"));
        card3.setData(new Model_Card(new ImageIcon(getClass().getResource("/apk/laundry/icon/profit.png")), "Transaksi Tahun ini", "Rp. " + formatn(profity()), "Increased by "));

        chart.setTitle("Chart Tahunan");
        chart.addLegend("Transaksi", Color.decode("#16a6f3"), Color.decode("#3339db"));
        setData("");
        chart1.addLegend("Pengeluaran", new Color(135, 189, 245));
        chart1.addLegend("Profit", new Color(245, 189, 13));
        chart1.addData(new apk.laundry.chart.ModelChart("Bulan ini", new double[]{luar(), pemasukanBersihBulanIni()}));
        chart1.addData(new apk.laundry.chart.ModelChart("Bulan lalu", new double[]{luarlalu(), pemasukanBersihBulanLalu()}));
        chart1.start();
    }

    private void setData(String laporan) {
        try {
            chart.resetChart();
            String datenow = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
            List<ModelData> lists = new ArrayList<>();
            DatabaseConnection.getInstance().connectToDatabase();
            String sql = (laporan.equals("keluar")) ? "select DATE_FORMAT(tanggal, '%d') as 'Date', sum(biaya) as biaya from pengeluaran WHERE tanggal BETWEEN \"" + datenow + "-01\" AND \"" + datenow + "-31\" group by DATE_FORMAT(tanggal, '%d%M') order by tanggal DESC limit 31;" : "select DATE_FORMAT(tgl_selesai, '%d') as 'Date', sum(subtotal_harga) as biaya from detail_pesanan WHERE tgl_selesai BETWEEN \"" + datenow + "-01\" AND \"" + datenow + "-31\" group by DATE_FORMAT(tgl_selesai, '%d%M') order by tgl_selesai DESC limit 31;";
            PreparedStatement p = DatabaseConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet res = p.executeQuery();
            while (res.next()) {
                String month = res.getString("Date");
                double amount = res.getDouble("biaya");
//                double cost=res.getDouble("Cost");
//                double profit=res.getDouble("Profit");
                lists.add(new ModelData(month, amount));
            }
            res.close();
            p.close();
            for (int i = lists.size() - 1; i >= 0; i--) {
                ModelData d = lists.get(i);
                chart.addData(new ModelChart(d.getMonth(), new double[]{d.getAmount()}));
            }
            chart.start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private int profittdy() {
        int value = 0;
        try {
            String datenow = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String sql = "SELECT sum(biaya) AS total_pengeluaran FROM pengeluaran WHERE tanggal= '" + datenow + "'";
            java.sql.Connection conn = config.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet res = pst.executeQuery();
            int pengeluaran = 0;
            while (res.next()) {
                pengeluaran = res.getInt("total_pengeluaran");
            }
            String tomorrow = datenow + " 23.59.59";
            datenow = datenow + " 00.00.00";
            String sql1 = "SELECT SUM(detail_pesanan.subtotal_harga) AS total_pemasukan FROM detail_pesanan JOIN pesanan ON pesanan.no_pesanan = detail_pesanan.no_pesanan WHERE pesanan.tgl_pesanan between \"" + datenow + "\" and \"" + tomorrow + "\";";
            java.sql.Connection conn1 = config.configDB();
            java.sql.PreparedStatement pst1 = conn1.prepareStatement(sql1);
            java.sql.ResultSet res1 = pst1.executeQuery();
            int pemasukan = 0;
            while (res1.next()) {
                pemasukan = res1.getInt("total_pemasukan");
            }
            value = (pemasukan - pengeluaran);
        } catch (Exception e) {
            System.out.println("gagal menampilkan transaksi hari ini karena " + e.getMessage());
        }
        return value;
    }

    private int profittm() {
        int value = 0;
        try {
            String monthnow = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
            String endOfMonth = monthnow + "-31 23.59.59";
            monthnow = monthnow + "-01 00.00.00";
            String sql = "SELECT sum(biaya) AS total_pengeluaran FROM pengeluaran WHERE tanggal between \"" + monthnow + "\" and \"" + endOfMonth + "\";";
            java.sql.Connection conn = config.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet res = pst.executeQuery();
            int pengeluaran = 0;
            while (res.next()) {
                pengeluaran = res.getInt("total_pengeluaran");
            }
            String sql1 = "SELECT SUM(detail_pesanan.subtotal_harga) AS total_pemasukan FROM detail_pesanan JOIN pesanan ON pesanan.no_pesanan = detail_pesanan.no_pesanan WHERE pesanan.tgl_pesanan between \"" + monthnow + "\" and \"" + endOfMonth + "\";";
            java.sql.Connection conn1 = config.configDB();
            java.sql.PreparedStatement pst1 = conn1.prepareStatement(sql1);
            java.sql.ResultSet res1 = pst1.executeQuery();
            int pemasukan = 0;
            while (res1.next()) {
                pemasukan = res1.getInt("total_pemasukan");
            }
            value = (pemasukan - pengeluaran);
        } catch (Exception e) {
            System.out.println("gagal menampilkan transaksi hari ini karena " + e.getMessage());
        }
        return value;
    }

    private int profity() {
        int value = 0;
        try {
            String yearnow = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
            String endOfYear = yearnow + "-12-31 23.59.59";
            yearnow = yearnow + "-01-01 00.00.00";
            String sql = "SELECT sum(biaya) AS total_pengeluaran FROM pengeluaran WHERE tanggal between \"" + yearnow + "\" and \"" + endOfYear + "\";";
            java.sql.Connection conn = config.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet res = pst.executeQuery();
            int pengeluaran = 0;
            while (res.next()) {
                pengeluaran = res.getInt("total_pengeluaran");
            }
            String sql1 = "SELECT SUM(detail_pesanan.subtotal_harga) AS total_pemasukan FROM detail_pesanan JOIN pesanan ON pesanan.no_pesanan = detail_pesanan.no_pesanan WHERE pesanan.tgl_pesanan between \"" + yearnow + "\" and \"" + endOfYear + "\";";
            java.sql.Connection conn1 = config.configDB();
            java.sql.PreparedStatement pst1 = conn1.prepareStatement(sql1);
            java.sql.ResultSet res1 = pst1.executeQuery();
            int pemasukan = 0;
            while (res1.next()) {
                pemasukan = res1.getInt("total_pemasukan");
            }
            value = (pemasukan - pengeluaran);
        } catch (Exception e) {
            System.out.println("gagal menampilkan transaksi hari ini karena " + e.getMessage());
        }
        return value;
    }

    private String formatn(int amount) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setCurrencySymbol("Rp. ");
        symbols.setGroupingSeparator(',');

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###", symbols);
        return decimalFormat.format(amount);
    }

    private int luar() throws SQLException {
        int pengeluaran = 0;
        String sql = "SELECT SUM(pengeluaran.biaya) as pengeluaran from pengeluaran\n"
                + "WHERE MONTH(pengeluaran.tanggal) = MONTH(CURDATE()) AND YEAR(pengeluaran.tanggal) = YEAR(CURDATE())";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            pengeluaran = rs.getInt("pengeluaran");
        }
        return pengeluaran;
    }

    private int luarlalu() throws SQLException {
        int pengeluaran = 0;
        String sql = "SELECT SUM(pengeluaran.biaya) as pengeluaran from pengeluaran\n"
                + "WHERE MONTH(pengeluaran.tanggal) = MONTH(CURDATE() - INTERVAL 1 MONTH) AND YEAR(pengeluaran.tanggal) = YEAR(CURDATE())";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            pengeluaran = rs.getInt("pengeluaran");
        }
        return pengeluaran;
    }

    private int pemasukanBersihBulanIni() throws SQLException {
        int pemasukanBersih = 0;

        String sqlPemasukan = "SELECT SUM(detail_pesanan.subtotal_harga) as pemasukan "
                + "FROM detail_pesanan "
                + "JOIN pesanan ON pesanan.no_pesanan = detail_pesanan.no_pesanan "
                + "WHERE MONTH(pesanan.tgl_pesanan) = MONTH(CURDATE()) AND YEAR(pesanan.tgl_pesanan) = YEAR(CURDATE())";

        // Calculate total expenses for the current month
        String sqlPengeluaran = "SELECT SUM(pengeluaran.biaya) as pengeluaran "
                + "FROM pengeluaran "
                + "WHERE MONTH(pengeluaran.tanggal) = MONTH(CURDATE()) AND YEAR(pengeluaran.tanggal) = YEAR(CURDATE())";

        // Execute queries
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pstPemasukan = conn.prepareStatement(sqlPemasukan);
        java.sql.PreparedStatement pstPengeluaran = conn.prepareStatement(sqlPengeluaran);

        java.sql.ResultSet rsPemasukan = pstPemasukan.executeQuery();
        java.sql.ResultSet rsPengeluaran = pstPengeluaran.executeQuery();
        int pemasukan = 0;
        if (rsPemasukan.next()) {
            pemasukan = rsPemasukan.getInt("pemasukan");
        }
        int pengeluaran = 0;
        if (rsPengeluaran.next()) {
            pengeluaran = rsPengeluaran.getInt("pengeluaran");
        }
        pemasukanBersih = pemasukan - pengeluaran;

        return pemasukanBersih;
    }

    private int pemasukanBersihBulanLalu() throws SQLException {
        int pemasukanBersih = 0;

        // Calculate total revenue for the previous month
        String sqlPemasukan = "SELECT SUM(detail_pesanan.subtotal_harga) as pemasukan "
                + "FROM detail_pesanan "
                + "JOIN pesanan ON pesanan.no_pesanan = detail_pesanan.no_pesanan "
                + "WHERE MONTH(pesanan.tgl_pesanan) = MONTH(CURDATE() - INTERVAL 1 MONTH) AND YEAR(pesanan.tgl_pesanan) = YEAR(CURDATE())";

        // Calculate total expenses for the previous month
        String sqlPengeluaran = "SELECT SUM(pengeluaran.biaya) as pengeluaran "
                + "FROM pengeluaran "
                + "WHERE MONTH(pengeluaran.tanggal) = MONTH(CURDATE() - INTERVAL 1 MONTH) AND YEAR(pengeluaran.tanggal) = YEAR(CURDATE())";

        // Execute queries
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pstPemasukan = conn.prepareStatement(sqlPemasukan);
        java.sql.PreparedStatement pstPengeluaran = conn.prepareStatement(sqlPengeluaran);

        java.sql.ResultSet rsPemasukan = pstPemasukan.executeQuery();
        java.sql.ResultSet rsPengeluaran = pstPengeluaran.executeQuery();

        int pemasukan = 0;
        if (rsPemasukan.next()) {
            pemasukan = rsPemasukan.getInt("pemasukan");
        }

        int pengeluaran = 0;
        if (rsPengeluaran.next()) {
            pengeluaran = rsPengeluaran.getInt("pengeluaran");
        }

        pemasukanBersih = pemasukan - pengeluaran;

        return pemasukanBersih;
    }
        private BigDecimal Percentage() throws SQLException {
        BigDecimal todayIncome = BigDecimal.ZERO;
        BigDecimal yesterdayIncome = BigDecimal.ZERO;

        // today
        String today = "SELECT SUM(subtotal_harga) as pendapatan\n"
                + "FROM detail_pesanan\n"
                + "JOIN pesanan ON pesanan.no_pesanan = detail_pesanan.no_pesanan\n"
                + "WHERE MONTH(pesanan.tgl_pesanan) = MONTH(CURDATE())";

        // yesterday
        String yesterday = "SELECT SUM(subtotal_harga) as pendapatan\n"
                + "FROM detail_pesanan\n"
                + "JOIN pesanan ON pesanan.no_pesanan = detail_pesanan.no_pesanan\n"
                + "WHERE MONTH(pesanan.tgl_pesanan) = MONTH(CURDATE()) - INTERVAL 1 MONTH;";

        try (java.sql.Connection conn = config.configDB()) {

            try (java.sql.PreparedStatement pstToday = conn.prepareStatement(today);
                    java.sql.ResultSet rsToday = pstToday.executeQuery()) {
                if (rsToday.next()) {
                    todayIncome = rsToday.getBigDecimal("pendapatan");
                }
            }

            try (java.sql.PreparedStatement pstYesterday = conn.prepareStatement(yesterday);
                    java.sql.ResultSet rsYesterday = pstYesterday.executeQuery()) {
                if (rsYesterday.next()) {
                    yesterdayIncome = rsYesterday.getBigDecimal("pendapatan");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Calculate the percentage increase
        BigDecimal percentageIncrease = BigDecimal.ZERO;
        if (yesterdayIncome != null && todayIncome != null && !yesterdayIncome.equals(BigDecimal.ZERO)) {
            percentageIncrease = todayIncome.subtract(yesterdayIncome)
                    .divide(yesterdayIncome, 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }
        return percentageIncrease;
    }
        private BigDecimal Percentagelalu() throws SQLException {
        BigDecimal todayIncome = BigDecimal.ZERO;
        BigDecimal yesterdayIncome = BigDecimal.ZERO;

        // today
        String today = "SELECT SUM(subtotal_harga) as pendapatan\n"
                + "FROM detail_pesanan\n"
                + "JOIN pesanan ON pesanan.no_pesanan = detail_pesanan.no_pesanan\n"
                + "WHERE MONTH(pesanan.tgl_pesanan) = MONTH(CURDATE()) - INTERVAL 1 MONTH";

        // yesterday
        String yesterday = "SELECT SUM(subtotal_harga) as pendapatan\n"
                + "FROM detail_pesanan\n"
                + "JOIN pesanan ON pesanan.no_pesanan = detail_pesanan.no_pesanan\n"
                + "WHERE MONTH(pesanan.tgl_pesanan) = MONTH(CURDATE()) - INTERVAL 2 MONTH;";

        try (java.sql.Connection conn = config.configDB()) {

            try (java.sql.PreparedStatement pstToday = conn.prepareStatement(today);
                    java.sql.ResultSet rsToday = pstToday.executeQuery()) {
                if (rsToday.next()) {
                    todayIncome = rsToday.getBigDecimal("pendapatan");
                }
            }

            try (java.sql.PreparedStatement pstYesterday = conn.prepareStatement(yesterday);
                    java.sql.ResultSet rsYesterday = pstYesterday.executeQuery()) {
                if (rsYesterday.next()) {
                    yesterdayIncome = rsYesterday.getBigDecimal("pendapatan");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Calculate the percentage increase
        BigDecimal percentageIncrease = BigDecimal.ZERO;
        if (yesterdayIncome != null && todayIncome != null && !yesterdayIncome.equals(BigDecimal.ZERO)) {
            percentageIncrease = todayIncome.subtract(yesterdayIncome)
                    .divide(yesterdayIncome, 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }
        return percentageIncrease;
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelShadow1 = new raven.panel.PanelShadow();
        chart = new raven.chart.CurveLineChart();
        button1 = new apk.laundry.model.Button();
        button2 = new apk.laundry.model.Button();
        chart1 = new apk.laundry.chart.Chart();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        ini = new javax.swing.JLabel();
        lalu = new javax.swing.JLabel();
        card1 = new apk.laundry.component.Card();
        card2 = new apk.laundry.component.Card();
        card3 = new apk.laundry.component.Card();

        setBackground(new java.awt.Color(242, 242, 242));

        button1.setBackground(new java.awt.Color(22, 166, 243));
        button1.setForeground(new java.awt.Color(255, 255, 255));
        button1.setText("Pemasukan");
        button1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        button2.setBackground(new java.awt.Color(255, 0, 51));
        button2.setForeground(new java.awt.Color(255, 255, 255));
        button2.setText("Pengeluaran");
        button2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Bulanan");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Bulan lalu");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Bulan ini");

        ini.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ini.setForeground(new java.awt.Color(102, 102, 102));
        ini.setText("ini");

        lalu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lalu.setForeground(new java.awt.Color(102, 102, 102));
        lalu.setText("lalu");

        javax.swing.GroupLayout panelShadow1Layout = new javax.swing.GroupLayout(panelShadow1);
        panelShadow1.setLayout(panelShadow1Layout);
        panelShadow1Layout.setHorizontalGroup(
            panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelShadow1Layout.createSequentialGroup()
                .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelShadow1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelShadow1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, 682, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelShadow1Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(ini))
                        .addGap(83, 83, 83)
                        .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lalu)
                            .addComponent(jLabel2))
                        .addGap(68, 68, 68))
                    .addGroup(panelShadow1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(chart1, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );
        panelShadow1Layout.setVerticalGroup(
            panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelShadow1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelShadow1Layout.createSequentialGroup()
                        .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ini)
                            .addComponent(lalu))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addComponent(chart1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelShadow1Layout.createSequentialGroup()
                        .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        card1.setColor1(new java.awt.Color(22, 166, 243));
        card1.setColor2(new java.awt.Color(31, 150, 214));
        card1.setRequestFocusEnabled(false);

        card2.setColor1(new java.awt.Color(186, 123, 247));
        card2.setColor2(new java.awt.Color(167, 94, 236));

        card3.setColor1(new java.awt.Color(241, 208, 62));
        card3.setColor2(new java.awt.Color(211, 184, 61));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelShadow1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(card1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(panelShadow1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        setData("");
    }//GEN-LAST:event_button1ActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        setData("keluar");
    }//GEN-LAST:event_button2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private apk.laundry.model.Button button1;
    private apk.laundry.model.Button button2;
    private apk.laundry.component.Card card1;
    private apk.laundry.component.Card card2;
    private apk.laundry.component.Card card3;
    private raven.chart.CurveLineChart chart;
    private apk.laundry.chart.Chart chart1;
    private javax.swing.JLabel ini;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lalu;
    private raven.panel.PanelShadow panelShadow1;
    // End of variables declaration//GEN-END:variables

    public void initialize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
