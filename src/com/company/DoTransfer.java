package com.company;
import java.sql.*;

//import static banking.Main.url;
import static com.company.Main.url;

public class DoTransfer {
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

protected void checkTransfer(String toCardNumber){

}
    protected Boolean cardCheckSum(String cardNumberAI){
        String[] strArray = cardNumberAI.split("");
        int sumNumbers=0;
        int checkN=0;
        Integer[] intArray = new Integer[strArray.length];
        for (int i=0;i< strArray.length;i++){
            intArray[i]=Integer.valueOf(strArray[i]);
        }

        Integer[] intArrayOdd2 = new Integer[strArray.length-1];
        for (int i=0;i< (strArray.length-1);i++){
            if ((i+1)%2!=0){
                intArrayOdd2[i]=2*intArray[i];}
            else {
                intArrayOdd2[i]=intArray[i];
            }
            if (intArrayOdd2[i]>9){
                intArrayOdd2[i]=intArrayOdd2[i]-9;
            }
            sumNumbers=sumNumbers+intArrayOdd2[i];
        }
        checkN=(10-sumNumbers%10);
        if (checkN==intArray[strArray.length-1]){
            return true;
        }
       else{  return false;} //o we only have to generate the last digit, which is a checksum.
    }


    protected Boolean CardExist(String number){
        String sql = "SELECT id, number,pin,balance "
                + "FROM card WHERE number = ? ";
        Boolean cardAndPin=false;
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            pstmt.setString(1,number);

            //
            ResultSet rs  = pstmt.executeQuery();
            //System.out.println("Result query :" + pstmt.executeQuery());
            if (rs.isBeforeFirst()==false){

                cardAndPin=false;
               //cardAndPin=("xxx");
                //return cardAndPin;
            }
            else {
                cardAndPin=true;

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return cardAndPin;
    }
    protected Boolean checkBalance(int balance){
        String sql = "SELECT id, number,pin,balance "
                + "FROM card WHERE balance = ? ";
        Boolean isEnoufh=false;
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            pstmt.setInt(1,balance);


            ResultSet rs  = pstmt.executeQuery();

            if (rs.isBeforeFirst()==false){

                isEnoufh=false;

            }
            else {
                isEnoufh=true;

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return isEnoufh;
    }
}
