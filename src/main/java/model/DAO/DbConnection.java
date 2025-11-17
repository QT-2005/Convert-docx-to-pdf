package model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/convertdb";
    private static final String USERNAME = "root";   // sửa nếu khác
    private static final String PASSWORD = "bi2005";       // sửa nếu khác

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
