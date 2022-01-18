package com.company;

//package com.company;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//import static banking.Main.url;
import static com.company.Main.url;

/**
 *
 * @author sqlitetutorial.net
 */
public class InsertTable {

    /**
     * Connect to the test.db database
     *
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string
       // String url = "jdbc:sqlite:test_new.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    protected void insert(int id,String number, String pin,int balance) {


        String sql = "INSERT INTO card(id,number,pin,balance) VALUES(?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, number);
            pstmt.setString(3, pin);
            pstmt.setInt(4, balance);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}





