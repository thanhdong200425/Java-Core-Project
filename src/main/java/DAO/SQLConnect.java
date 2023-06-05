package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnect {
    public static Connection getConnection() {
        Connection c = null;
        try {
            String url = "jdbc:mysql://localhost:3306/vleague";
            String user = "root";
                String password = "Dong25042004";
            c = DriverManager.getConnection(url, user, password);
        } catch (Exception s) {
            System.out.println(s.getMessage());
        }
        return c;
    }

    public static void closeConnection(Connection c) {
        if (c != null) {
            try {
                c.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Not exits");
        }
    }
}