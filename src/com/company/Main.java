package com.company;


//package com.company;
//package banking;

import java.sql.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static String url;
    public static void main(String args[]){
        url = "jdbc:sqlite:" + args[1];
        //url = "jdbc:sqlite:newtest.db";
        createNewDatabase();
        createNewTable();
        int countId=0;
        InsertTable appINS =   new InsertTable();
        //menu

        String cardNumberAI="";//generate an Account Identifier
        String cardNumber="";
        String pin="";
        String inputCardNum="";
        String inputPinNum="";
        // String[] strArray = cardNumberAI.split("");
        while (true){
            System.out.println("1. Create an account");
            System.out.println("2. Log into account");
            System.out.println("0. Exit");
            //System.out.println();
            Scanner scanner=new Scanner(System.in);
            int menuInput=scanner.nextInt();

            if (menuInput==1) {
                cardNumberAI = genCardNum(); //генерация generate an Account Identifier+BIN
                cardNumber=cardNumberAI+getCheckSum(cardNumberAI);//генерация номера карты
                pin = genCardPIN();//генерация пин
                /*  //пока убираю проверку на уникальность карты
                 */
                countId++;
                appINS.insert(countId, cardNumber, pin ,0);
                System.out.println("Your card has been created");
                System.out.println("Your card number:");
                System.out.println(Long.valueOf(cardNumber));
                //System.out.println(cardNumber);
                System.out.println("Your card PIN:");
                System.out.println(pin);
                System.out.println();
            }
            else    if (menuInput==2) {
                System.out.println("Enter your card number:");
                inputCardNum = scanner.next();

                System.out.println("Enter your PIN:");
                inputPinNum = scanner.next();
                ShowMe checkLogin = new ShowMe();
                String[] arCheck= new String[2];
                arCheck=checkLogin.getCapacityGreaterThan(inputCardNum,inputPinNum);
                if (arCheck[0].equals(inputCardNum)){

                    if (arCheck[1].equals(inputPinNum)==false) //прооверка соответствия номера карты и номера пин через соотв номер в массиве
                    {
                        System.out.println("Wrong card number or PIN!");

                        System.out.println();
                        //break;
                    }
                    else {
                        System.out.println("You have successfully logged in!");
                        System.out.println();
                        while (true) {
                            System.out.println("1. Balance");
                            System.out.println("2. Add income");
                            System.out.println("3. Do transfer");
                            System.out.println("4. Close account");
                            System.out.println("5. Log out");
                            System.out.println("0. Exit");
                            // Scanner scanner=new Scanner(System.in);
                            int menu2LogInput = scanner.nextInt();
                            if (menu2LogInput==1){

                                System.out.println("Balance: " + getBalance(inputCardNum));

                            }
                            if (menu2LogInput==2){
                                System.out.println("Enter income:");
                                int inputIncome = Integer.valueOf(scanner.next());

                                addBalance(inputIncome,inputCardNum);
                                System.out.println( "Income was added!");
                                //break;
                            }
                            if (menu2LogInput==3){
                                System.out.println("Transfer");
                                System.out.println("Enter card number:");
                                String cardToTransf= scanner.next();
                                if (cardCheckSum(cardToTransf)){
                                    if(CardExist(cardToTransf)){
                                        System.out.println("Enter how much money you want to transfer:");
                                        int moneyToTRansf=Integer.valueOf(scanner.next());
                                        if ((getBalance(inputCardNum)>=moneyToTRansf))
                                        {
                                            addBalance((-moneyToTRansf),inputCardNum);
                                            addBalance(moneyToTRansf,cardToTransf);
                                            System.out.println("Success!");
                                        }
                                        else {System.out.println("Not enough money!");}
                                    }
                                    else{ System.out.println("Such a card does not exist.");}
                                }
                                else {
                                    System.out.println("Probably you made a mistake in the card number. " +
                                            "Please try again!");
                                }
                                //else if  (){}
                                //else if  (){}
                                //break;
                            }
                            if (menu2LogInput==4){
                                removeAccount(inputCardNum);
                                System.out.println("The account has been closed!");
                                //System.out.println();
                                break;
                            }
                            if (menu2LogInput==5){
                                System.out.println("You have successfully logged out!");
                                System.out.println();
                                break;
                            }
                            if (menu2LogInput==0){
                                System.out.println("Bye!");
                                System.out.println();
                                return;
                            }

                        }
                    }

                }
                else {
                    System.out.println("Wrong card number or PIN!");
                    System.out.println();
                    //break;
                }
            }
            else if (menuInput==0){return;}
        }
    }
    static String genCardNum() {

        Random random = new Random();
        int iCard = random.nextInt(1000000000);
        String newCAN = String.valueOf(iCard);//customer account number
        if (String.valueOf(iCard).length() < 9) {
            for (int i = String.valueOf(iCard).length(); i < 9; i++) {
                newCAN = "0" + newCAN;
            }
        }
        //Random randomK = new Random();
        int k = random.nextInt(10);
        String cardNumberAI = "400000" + newCAN ;//+ String.valueOf(k);//BIN+customer account//


        return cardNumberAI;
    }


    static String genCardPIN() {
        Random random = new Random();
        int iPIN = random.nextInt(10000);
        String newPIN = String.valueOf(iPIN);
        if (String.valueOf(iPIN).length() < 4) {
            for (int i = String.valueOf(iPIN).length(); i < 4; i++) {
                newPIN = "0" + newPIN;
            }
        }
        return newPIN;
    }
    static String getCheckSum(String cardNumberAI){
        String[] strArray = cardNumberAI.split("");
        int sumNumbers=0;
        int checkN=0;
        Integer[] intArray = new Integer[strArray.length];
        for (int i=0;i< strArray.length;i++){
            intArray[i]=Integer.valueOf(strArray[i]);
        }

        Integer[] intArrayOdd2 = new Integer[strArray.length];
        for (int i=0;i< strArray.length;i++){
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
        return String.valueOf(checkN); //o we only have to generate the last digit, which is a checksum.
    }
    public static void createNewDatabase() {

        //String url = "jdbc:sqlite:" + fileName;
        //String url = "jdbc:sqlite:test_new.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    protected static void createNewTable() {

        String sql = "CREATE TABLE IF NOT EXISTS card (\n"
                + "	id integer ,\n"
                + "	number text NOT NULL,\n"
                + "	pin text NOT NULL,\n"
                + "	balance integer \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            System.out.println("A new table  has been created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    protected static Connection connect() {
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
    protected static void addBalance(int newBalance,String cardNumber) {
        String sql = "UPDATE card SET balance = balance +  ? "

                + "WHERE number = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, newBalance);
            pstmt.setString(2, cardNumber);

            // update

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    // public void update(int id, String name, double capacity) {
    //        String sql = "UPDATE warehouses SET name = ? , "
    //                + "capacity = ? "
    //                + "WHERE id = ?";
    //
    //        try (Connection conn = this.connect();
    //                PreparedStatement pstmt = conn.prepareStatement(sql)) {
    //
    //            // set the corresponding param
    //            pstmt.setString(1, name);
    //            pstmt.setDouble(2, capacity);
    //            pstmt.setInt(3, id);
    //            // update
    //            pstmt.executeUpdate();
    //        } catch (SQLException e) {
    //            System.out.println(e.getMessage());
    //        }
    //    }
    protected static int getBalance(String cardnumber){
        String sql = "SELECT number, balance "
                + "FROM card WHERE number = ? ";
        int bal=0;
        try (Connection conn = connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            pstmt.setString(1,cardnumber);

            //
            ResultSet rs  = pstmt.executeQuery();
            //System.out.println("Result query :" + pstmt.executeQuery());

            bal=rs.getInt("balance");




        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return bal;
    }
//public void getCapacityGreaterThan(double capacity){
//               String sql = "SELECT id, name, capacity "
//                          + "FROM warehouses WHERE capacity > ?";
//
//        try (Connection conn = this.connect();
//             PreparedStatement pstmt  = conn.prepareStatement(sql)){
//
//            // set the value
//            pstmt.setDouble(1,capacity);
//            //
//            ResultSet rs  = pstmt.executeQuery();
//
//            // loop through the result set
//            while (rs.next()) {
//                System.out.println(rs.getInt("id") +  "\t" +
//                                   rs.getString("name") + "\t" +
//                                   rs.getDouble("capacity"));
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
    protected static Boolean cardCheckSum(String cardNumberAI){
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
    protected static Boolean CardExist(String number){
        String sql = "SELECT id, number,pin,balance "
                + "FROM card WHERE number = ? ";
        Boolean cardAndPin=false;
        try (Connection conn = connect();
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

    public static void removeAccount(String cardNumber) {
        String sql = "DELETE FROM card WHERE number =  ?";


        //DELETE FROM table_name WHERE logical_expression

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param

            pstmt.setString(1, cardNumber);

            // update

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
