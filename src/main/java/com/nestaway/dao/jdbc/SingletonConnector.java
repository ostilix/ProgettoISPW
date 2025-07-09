package com.nestaway.dao.jdbc;

import com.nestaway.exception.dao.ConnectionException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SingletonConnector {
    private static Connection connection = null;
    private static final String CONNECTION_SETTINGS = "src/main/resources/properties/db.properties";

    private SingletonConnector() {}

    public static synchronized Connection getConnection() throws ConnectionException {
        if (connection == null) {
            try (InputStream input = new FileInputStream(CONNECTION_SETTINGS)) {
                Properties properties = new Properties();
                properties.load(input);

                String connectionUrl = properties.getProperty("CONNECTION_URL");
                String user = properties.getProperty("USER");
                String pass = properties.getProperty("PASS");

                connection = DriverManager.getConnection(connectionUrl, user, pass);
            } catch (IOException | SQLException e) {
                throw new ConnectionException("Error in getConnection: " + e.getMessage(), e);
            }
        }
        return connection;
    }

}
