package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.DatabaseConfig;

public class BaseDAO {
	
	protected static String DB_URL;
	protected static String DB_USERNAME;
	protected static String DB_PASSWORD;
	
	static {
		DB_URL = DatabaseConfig.DB_URL;
		DB_USERNAME = DatabaseConfig.DB_USERNAME;
		DB_PASSWORD = DatabaseConfig.DB_PASSWORD;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
	}

    protected void closeResources(Connection conn, PreparedStatement pStmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                handleSQLException(e);
            }
        }
        if (pStmt != null) {
            try {
                pStmt.close();
            } catch (SQLException e) {
                handleSQLException(e);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                handleSQLException(e);
            }
        }
    }

    protected void handleSQLException(SQLException e) {
        e.printStackTrace();
    }
}
