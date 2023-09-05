package config;

import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    public static String DB_URL;
    public static String DB_USERNAME;
    public static String DB_PASSWORD;

    static {
        try {
            Properties properties = new Properties();
            try (InputStream input = DatabaseConfig.class.getResourceAsStream("/db.properties")) {
                properties.load(input);
            }
            DB_URL = properties.getProperty("db.url");
            DB_USERNAME = properties.getProperty("db.username");
            DB_PASSWORD = properties.getProperty("db.password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
