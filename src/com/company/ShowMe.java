package com.company;

//package com.company;



import java.sql.*;

//import static banking.Main.url;
import static com.company.Main.url;

/**
 *
 * @author sqlitetutorial.net
 */
public class ShowMe {

    /**
     * Connect to the test.db database
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string
        //String url = "jdbc:sqlite:test_new.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    protected void selectAll(){
        String sql = "SELECT id, number,pin,balance FROM card";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("number") + "\t" +
                        rs.getString("pin") + "\t" +
                        rs.getDouble("balance"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Get the warehouse whose capacity greater than a specified capacity
     * @param
     */
    protected String[] getCapacityGreaterThan(String number,String pin){
        String sql = "SELECT id, number,pin,balance "
                + "FROM card WHERE number = ? AND pin = ?";
        String[] cardAndPin=new String[2];
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            pstmt.setString(1,number);
            pstmt.setString(2,pin);
            //
            ResultSet rs  = pstmt.executeQuery();
            //System.out.println("Result query :" + pstmt.executeQuery());
            if (rs.isBeforeFirst()==false){
                //System.out.println("Wrong card number or PIN!");
                cardAndPin[0]=("xxx");
                cardAndPin[1]=("xxx");

            }
            else {
                cardAndPin[0]=rs.getString("number");
                cardAndPin[1]=rs.getString("pin");

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return cardAndPin;
    }
    protected int getBalance(String number){
        String sql = "SELECT balance "
                + "FROM card WHERE number = ? ";
        int bal=0;
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            pstmt.setString(1,number);

            //
            ResultSet rs  = pstmt.executeQuery();
            //System.out.println("Result query :" + pstmt.executeQuery());

                bal=rs.getInt("number");




        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return bal;
    }
}





