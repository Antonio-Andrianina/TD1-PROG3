package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private final String url;
    private final String username;
    private final String password;

    public DBConnection() {
        this.url = "jdbc:postgresql://localhost:5432/product_management_db";
        this.username = "product_manager_user";
        this.password = "123456";
    }

    public Connection getDBConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
