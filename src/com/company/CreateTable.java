package com.company;

//package com.company;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {




    /**
     * Create a new table in the test database
     *
     */
    protected  void createNewTable(String fileName) {
        // SQLite connection string
        //String url = "jdbc:sqlite:test_new.db";
        String url = "jdbc:sqlite:" + fileName;
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS card (\n"
                + "	id integer ,\n"
                + "	number text NOT NULL,\n"
                + "	pin text NOT NULL,\n"
                + "	balance integer default 0\n"
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

    /**
     * @param args the command line arguments
     */


}

