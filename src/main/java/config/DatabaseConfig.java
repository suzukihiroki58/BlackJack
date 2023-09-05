package config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
	public static String DB_URL;
	public static String DB_USERNAME;
	public static String DB_PASSWORD;

	static {
		Properties prop = new Properties();
		InputStream input = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("JDBCドライバのロードに失敗しました", e);
		}

		try {
			input = DatabaseConfig.class.getClassLoader().getResourceAsStream("db.properties");
			if (input == null) {
				throw new FileNotFoundException("db.propertiesファイルが見つかりません");
			}
			prop.load(input);
			DB_URL = prop.getProperty("db.url");
			DB_USERNAME = prop.getProperty("db.username");
			DB_PASSWORD = prop.getProperty("db.password");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
