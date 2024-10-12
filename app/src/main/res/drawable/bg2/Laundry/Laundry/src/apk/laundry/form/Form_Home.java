package apk.laundry.form;

import apk.laundry.chart.ModelChart;
import apk.laundry.chart.ModelPolarAreaChart;
import apk.laundry.model.Model_Card;
import apk.laundry.model.StatusType;
import apk.laundry.swing.ScrollBar;
import apk.laundry.swing.TableHeader;
import apk.laundry.swing.CellStatus;
import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Form_Home extends javax.swing.JPanel {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public Form_Home() throws SQLException {
        initComponents();
        scheduler.scheduleAtFixedRate(this::updateStatusAutomatically, 0, 1, TimeUnit.DAYS);
        card1.setData(new Model_Card(new ImageIcon(getClass().getResource("/apk/laundry/icon/stock.png")), "Pesanan Masuk", String.valueOf(masuk()), "jumlah pesanan masuk"));
        card2.setData(new Model_Card(new ImageIcon(getClass().getResource("/apk/laundry/icon/flag.png")), "Pesanan Selesai", String.valueOf(selesai()), "jumlah pesanan selesai"));
        card3.setData(new Model_Card(new ImageIcon(getClass().getResource("/apk/laundry/icon/profit.png")), "Pendapatan Hari ini", "Rp. " + formatn(pendapatan()), "Increased by " + Percentage() + "%"));

        //  add row table
        CustomTable customTable = new CustomTable();
        spTable.setViewportView(customTable);
        spTable.setVerticalScrollBar(new ScrollBar());
        spTable.getVerticalScrollBar().setBackground(Color.WHITE);
        spTable.getViewport().setBackground(Color.WHITE);
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        spTable.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nama");
        model.addColumn("Alamat");
        model.addColumn("Layanan");
        model.addColumn("Tanggal & waktu");
        model.addColumn("Phone");
        model.addColumn("Status");
        try {
            String sql = "SELECT\n"
                    + "    customer.nama_customer,\n"
                    + "    customer.alamat_customer,\n"
                    + "    layanan.nama_layanan,\n"
                    + "    pesanan.tgl_pesanan,\n"
                    + "    customer.no_hp,\n"
                    + "    detail_pesanan.status\n"
                    + "FROM\n"
                    + "    pesanan\n"
                    + "JOIN\n"
                    + "    customer ON customer.id_customer = pesanan.id_customer\n"
                    + "JOIN\n"
                    + "    detail_pesanan ON detail_pesanan.no_pesanan = pesanan.no_pesanan\n"
                    + "JOIN\n"
                    + "    layanan ON layanan.id_layanan = detail_pesanan.id_layanan\n"
                    + "WHERE\n"
                    + "    detail_pesanan.status = 'Selesai'\n"
                    + "GROUP BY\n"
                    + "    customer.nama_customer,\n"
                    + "    customer.alamat_customer,\n"
                    + "    layanan.nama_layanan,\n"
                    + "    pesanan.tgl_pesanan,\n"
                    + "    customer.no_hp;";
            java.sql.Connection conn = (java.sql.Connection) config.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String statusText = getStatusText(res.getString(6));
                StatusType status = StatusType.valueOf(statusText);
                model.addRow(new Object[]{res.getString(1),
                    res.getString(2), res.getString(3), res.getString(4),
                    res.getString(5), status
                });
            }
            customTable.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        chart.addLegend("Pemasukan", new Color(135, 189, 245));
        chart.addLegend("Pengeluaran", new Color(189, 135, 245));
        chart.addLegend("Profit", new Color(245, 189, 135));
        chart.addData(new ModelChart("Monday", new double[]{mondayp(), mondaypg(), mondaypro()}));
        chart.addData(new ModelChart("Tuesday", new double[]{tuesdayp(), tuesdaypg(), tuesdaypro()}));
        chart.addData(new ModelChart("Wednesday", new double[]{wednesdayp(), wednesdaypg(), wednesdaypro(),}));
        chart.addData(new ModelChart("Thrusday", new double[]{thrusdayp(), thrusdaypg(), thrusdaypro()}));
        chart.addData(new ModelChart("Friday", new double[]{fridayp(), fridaypg(), fridaypro()}));
        chart.addData(new ModelChart("Saturday", new double[]{saturdayp(), saturdaypg(), saturdaypro()}));
        chart.addData(new ModelChart("Sunday", new double[]{sundayp(), sundaypg(), sundaypro()}));
        chart.start();
        polarAreaChart1.addItem(new ModelPolarAreaChart(new Color(52, 148, 203), "Proses", proses()));
        polarAreaChart1.addItem(new ModelPolarAreaChart(new Color(175, 67, 237), "Selesai", selesaii()));
        polarAreaChart1.addItem(new ModelPolarAreaChart(new Color(87, 218, 137), "Di ambil", diambil()));
        polarAreaChart1.start();
    }

    private BigDecimal Percentage() throws SQLException {
        BigDecimal todayIncome = BigDecimal.ZERO;
        BigDecimal yesterdayIncome = BigDecimal.ZERO;

        // today
        String today = "SELECT SUM(subtotal_harga) as pendapatan\n"
                + "FROM detail_pesanan\n"
                + "JOIN pesanan ON pesanan.no_pesanan = detail_pesanan.no_pesanan\n"
                + " WHERE DATE(pesanan.tgl_pesanan) = CURDATE()";

        // yesterday
        String yesterday = "SELECT SUM(subtotal_harga) as pendapatan\n"
                + "FROM detail_pesanan\n"
                + "JOIN pesanan ON pesanan.no_pesanan = detail_pesanan.no_pesanan\n"
                + " WHERE DATE(pesanan.tgl_pesanan) = CURDATE() - INTERVAL 1 DAY;";

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

    private int masuk() throws SQLException {
        int totalOrders = 0;
        String sql = "SELECT COUNT(id_customer) as total_orders\n"
                + "FROM pesanan\n"
                + "WHERE DATE(pesanan.tgl_pesanan) = CURDATE();";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            totalOrders = rs.getInt("total_orders");
        }
        return totalOrders;
    }

    private int selesai() throws SQLException {
        int total = 0;
        String sql = "SELECT COUNT(status) as status\n"
                + "FROM detail_pesanan\n"
                + "WHERE DATE(detail_pesanan.tgl_selesai) = CURDATE();";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            total = rs.getInt("status");
        }
        return total;
    }

    private int pendapatan() throws SQLException {
        int total = 0;
        String sql = "SELECT SUM(detail_pesanan.subtotal_harga) as pendapatan\n"
                + "FROM detail_pesanan\n"
                + "JOIN pesanan ON pesanan.no_pesanan = detail_pesanan.no_pesanan\n"
                + "WHERE DATE(pesanan.tgl_pesanan) = CURDATE()";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            total = rs.getInt("pendapatan");
        }
        return total;
    }

    private String formatn(int amount) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setCurrencySymbol("Rp. ");
        symbols.setGroupingSeparator(',');

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###", symbols);
        return decimalFormat.format(amount);
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
                    if (i1 == 5) {
                        header.setHorizontalAlignment(JLabel.CENTER);
                    }
                    return header;
                }
            });
            setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable jtable, Object o, boolean selected, boolean bln1, int i, int i1) {
                    if (i1 != 5) {
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

    private int sundayp() throws SQLException {
        int pendapatan = 0;
        String sql = "SELECT SUM(detail_pesanan.subtotal_harga) as pendapatan from detail_pesanan \n"
                + "join pesanan on pesanan.no_pesanan = detail_pesanan.no_pesanan\n"
                + "where DAYOFWEEK(pesanan.tgl_pesanan) = 1 AND WEEK(pesanan.tgl_pesanan)=WEEK(CURDATE())";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            pendapatan = rs.getInt("pendapatan");
        }
        return pendapatan;
    }

    private int sundaypg() throws SQLException {
        int pengeluaran = 0;
        String sql = "SELECT SUM(pengeluaran.biaya) as pengeluaran from pengeluaran\n"
                + "where DAYOFWEEK(pengeluaran.tanggal) = 1 AND WEEK(pengeluaran.tanggal)=WEEK(CURDATE())";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            pengeluaran = rs.getInt("pengeluaran");
        }
        return pengeluaran;
    }

    private int sundaypro() throws SQLException {
        int pendapatan = sundayp();
        int pengeluaran = sundaypg();
        int profit = pendapatan - pengeluaran;
        return profit;
    }

    private int mondayp() throws SQLException {
        int pendapatan = 0;
        String sql = "SELECT SUM(detail_pesanan.subtotal_harga) as pendapatan from detail_pesanan \n"
                + "join pesanan on pesanan.no_pesanan = detail_pesanan.no_pesanan\n"
                + "where DAYOFWEEK(pesanan.tgl_pesanan) = 2 AND WEEK(pesanan.tgl_pesanan)=WEEK(CURDATE())";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            pendapatan = rs.getInt("pendapatan");
        }
        return pendapatan;
    }

    private int mondaypg() throws SQLException {
        int pengeluaran = 0;
        String sql = "SELECT SUM(pengeluaran.biaya) as pengeluaran from pengeluaran\n"
                + "where DAYOFWEEK(pengeluaran.tanggal) = 2 AND WEEK(pengeluaran.tanggal)=WEEK(CURDATE())";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            pengeluaran = rs.getInt("pengeluaran");
        }
        return pengeluaran;
    }

    private int mondaypro() throws SQLException {
        int pendapatan = mondayp();
        int pengeluaran = mondaypg();
        int profit = pendapatan - pengeluaran;
        return profit;
    }

    private int tuesdayp() throws SQLException {
        int pendapatan = 0;
        String sql = "SELECT SUM(detail_pesanan.subtotal_harga) as pendapatan from detail_pesanan \n"
                + "join pesanan on pesanan.no_pesanan = detail_pesanan.no_pesanan\n"
                + "where DAYOFWEEK(pesanan.tgl_pesanan) = 3 AND WEEK(pesanan.tgl_pesanan)=WEEK(CURDATE())";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            pendapatan = rs.getInt("pendapatan");
        }
        return pendapatan;
    }

    private int tuesdaypg() throws SQLException {
        int pengeluaran = 0;
        String sql = "SELECT SUM(pengeluaran.biaya) as pengeluaran from pengeluaran\n"
                + "where DAYOFWEEK(pengeluaran.tanggal) = 3 AND WEEK(pengeluaran.tanggal)=WEEK(CURDATE())";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            pengeluaran = rs.getInt("pengeluaran");
        }
        return pengeluaran;
    }

    private int tuesdaypro() throws SQLException {
        int pendapatan = tuesdayp();
        int pengeluaran = tuesdaypg();
        int profit = pendapatan - pengeluaran;
        return profit;
    }

    private int wednesdayp() throws SQLException {
        int pendapatan = 0;
        String sql = "SELECT SUM(detail_pesanan.subtotal_harga) as pendapatan from detail_pesanan \n"
                + "join pesanan on pesanan.no_pesanan = detail_pesanan.no_pesanan\n"
                + "where DAYOFWEEK(pesanan.tgl_pesanan) = 4 AND WEEK(pesanan.tgl_pesanan)=WEEK(CURDATE())";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            pendapatan = rs.getInt("pendapatan");
        }
        return pendapatan;
    }

    private int wednesdaypg() throws SQLException {
        int pengeluaran = 0;
        String sql = "SELECT SUM(pengeluaran.biaya) as pengeluaran from pengeluaran\n"
                + "where DAYOFWEEK(pengeluaran.tanggal) = 4 AND WEEK(pengeluaran.tanggal)=WEEK(CURDATE())";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            pengeluaran = rs.getInt("pengeluaran");
        }
        return pengeluaran;
    }

    private int wednesdaypro() throws SQLException {
        int pendapatan = wednesdayp();
        int pengeluaran = wednesdaypg();
        int profit = pendapatan - pengeluaran;
        return profit;
    }

    private int thrusdayp() throws SQLException {
        int pendapatan = 0;
        String sql = "SELECT SUM(detail_pesanan.subtotal_harga) as pendapatan from detail_pesanan \n"
                + "join pesanan on pesanan.no_pesanan = detail_pesanan.no_pesanan\n"
                + "where DAYOFWEEK(pesanan.tgl_pesanan) = 5 AND WEEK(pesanan.tgl_pesanan)=WEEK(CURDATE())";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            pendapatan = rs.getInt("pendapatan");
        }
        return pendapatan;
    }

    private int thrusdaypg() throws SQLException {
        int pengeluaran = 0;
        String sql = "SELECT SUM(pengeluaran.biaya) as pengeluaran from pengeluaran\n"
                + "where DAYOFWEEK(pengeluaran.tanggal) = 5 AND WEEK(pengeluaran.tanggal)=WEEK(CURDATE())";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            pengeluaran = rs.getInt("pengeluaran");
        }
        return pengeluaran;
    }

    private int thrusdaypro() throws SQLException {
        int pendapatan = thrusdayp();
        int pengeluaran = thrusdaypg();
        int profit = pendapatan - pengeluaran;
        return profit;
    }

    private int fridayp() throws SQLException {
        int pendapatan = 0;
        String sql = "SELECT SUM(detail_pesanan.subtotal_harga) as pendapatan from detail_pesanan \n"
                + "join pesanan on pesanan.no_pesanan = detail_pesanan.no_pesanan\n"
                + "where DAYOFWEEK(pesanan.tgl_pesanan) = 6 AND WEEK(pesanan.tgl_pesanan)=WEEK(CURDATE())";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            pendapatan = rs.getInt("pendapatan");
        }
        return pendapatan;
    }

    private int fridaypg() throws SQLException {
        int pengeluaran = 0;
        String sql = "SELECT SUM(pengeluaran.biaya) as pengeluaran from pengeluaran\n"
                + "where DAYOFWEEK(pengeluaran.tanggal) = 6 AND WEEK(pengeluaran.tanggal)=WEEK(CURDATE())";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            pengeluaran = rs.getInt("pengeluaran");
        }
        return pengeluaran;
    }

    private int fridaypro() throws SQLException {
        int pendapatan = fridayp();
        int pengeluaran = fridaypg();
        int profit = pendapatan - pengeluaran;
        return profit;
    }

    private int saturdayp() throws SQLException {
        int pendapatan = 0;
        String sql = "SELECT SUM(detail_pesanan.subtotal_harga) as pendapatan from detail_pesanan \n"
                + "join pesanan on pesanan.no_pesanan = detail_pesanan.no_pesanan\n"
                + "where DAYOFWEEK(pesanan.tgl_pesanan) = 7 AND WEEK(pesanan.tgl_pesanan)=WEEK(CURDATE())";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            pendapatan = rs.getInt("pendapatan");
        }
        return pendapatan;
    }

    private int saturdaypg() throws SQLException {
        int pengeluaran = 0;
        String sql = "SELECT SUM(pengeluaran.biaya) as pengeluaran from pengeluaran\n"
                + "where DAYOFWEEK(pengeluaran.tanggal) = 7 AND WEEK(pengeluaran.tanggal)=WEEK(CURDATE())";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            pengeluaran = rs.getInt("pengeluaran");
        }
        return pengeluaran;
    }

    private int saturdaypro() throws SQLException {
        int pendapatan = saturdayp();
        int pengeluaran = saturdaypg();
        int profit = pendapatan - pengeluaran;
        return profit;
    }
    
private int proses() throws SQLException {
    int proses = 0;
    String sql = "SELECT COUNT(detail_pesanan.status) as proses\n" +
"            FROM detail_pesanan \n" +
"            JOIN pesanan ON pesanan.no_pesanan = detail_pesanan.no_pesanan\n" +
"            WHERE detail_pesanan.status = 'Proses'";
    java.sql.Connection conn = config.configDB();
    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
    java.sql.ResultSet rs = pst.executeQuery();
    if (rs.next()) {
        proses = rs.getInt("proses");
    }
    return proses;
}
    
    private int selesaii() throws SQLException{
        int selesai = 0;
        String sql = "SELECT COUNT(detail_pesanan.status) as selesai\n" +
"            FROM detail_pesanan \n" +
"            JOIN pesanan ON pesanan.no_pesanan = detail_pesanan.no_pesanan\n" +
"            WHERE WEEK(detail_pesanan.tgl_selesai) = WEEK(CURDATE()) AND detail_pesanan.status = 'Selesai'";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            selesai = rs.getInt("selesai");
        }
        return selesai;
    }
    
    private int diambil() throws SQLException{
        int ambil = 0;
        String sql = "SELECT COUNT(detail_pesanan.status) as ambil\n" +
"            FROM detail_pesanan \n" +
"            JOIN pesanan ON pesanan.no_pesanan = detail_pesanan.no_pesanan\n" +
"            WHERE WEEK(detail_pesanan.tgl_selesai) = WEEK(CURDATE()) AND detail_pesanan.status = 'Diambil'";
        java.sql.Connection conn = config.configDB();
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            ambil = rs.getInt("ambil");
        }
        return ambil;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JLayeredPane();
        card1 = new apk.laundry.component.Card();
        card2 = new apk.laundry.component.Card();
        card3 = new apk.laundry.component.Card();
        panelBorder1 = new apk.laundry.swing.PanelBorder();
        spTable = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        chart = new apk.laundry.chart.Chart();
        jLabel1 = new javax.swing.JLabel();
        polarAreaChart1 = new apk.laundry.chart.PolarAreaChart();

        setBackground(new java.awt.Color(242, 242, 242));
        setPreferredSize(new java.awt.Dimension(1097, 708));

        panel.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        card1.setColor1(new java.awt.Color(22, 166, 243));
        card1.setColor2(new java.awt.Color(31, 150, 214));
        card1.setRequestFocusEnabled(false);
        panel.add(card1);

        card2.setColor1(new java.awt.Color(186, 123, 247));
        card2.setColor2(new java.awt.Color(167, 94, 236));
        panel.add(card2);

        card3.setColor1(new java.awt.Color(241, 208, 62));
        card3.setColor2(new java.awt.Color(211, 184, 61));
        panel.add(card3);

        panelBorder1.setBackground(new java.awt.Color(255, 255, 255));

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

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(127, 127, 127));
        jLabel1.setText("PESANAN SIAP DI AMBIL");

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spTable, javax.swing.GroupLayout.PREFERRED_SIZE, 1020, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, 681, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(polarAreaChart1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                    .addComponent(polarAreaChart1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spTable, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(0, 45, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private apk.laundry.component.Card card1;
    private apk.laundry.component.Card card2;
    private apk.laundry.component.Card card3;
    private apk.laundry.chart.Chart chart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLayeredPane panel;
    private apk.laundry.swing.PanelBorder panelBorder1;
    private apk.laundry.chart.PolarAreaChart polarAreaChart1;
    private javax.swing.JScrollPane spTable;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
