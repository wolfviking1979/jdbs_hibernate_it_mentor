package jm.task.core.jdbc.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class UtilJdbc {
    public static Properties properties = new Properties();

    static {
        loadDriver();
        loadProperties();
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    private static final String USERNAME = "db.username";
    private static final String PASSWORD = "db.password";
    private static final String URL = "db.url";

    public static void loadProperties() {
        try( var inputStream = UtilJdbc.class
                .getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private UtilJdbc() {
    }

    public static Connection openConnection() {
        try {
            return DriverManager.getConnection(
                    getProperty(URL),
                    getProperty(USERNAME),
                    getProperty(PASSWORD)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
