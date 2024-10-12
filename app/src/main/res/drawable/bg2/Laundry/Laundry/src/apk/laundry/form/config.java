/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apk.laundry.form;
import java.sql.*;

/**
 *
 * @author snsv___
 */
public class config {
    public static Connection mysqlconfig;
    public static Connection configDB()throws SQLException{
        try{
            String url="jdbc:mysql://localhost:3306/aplikasi_laundry";
            String user="root";
            String pass="";
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            mysqlconfig=DriverManager.getConnection(url, user, pass);
        } catch (Exception e){
            System.err.println("Koneksi gagal "+e.getMessage());
        } return mysqlconfig;
    }
}

