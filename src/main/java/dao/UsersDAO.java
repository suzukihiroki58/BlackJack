package dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import config.DatabaseConfig;
import model.Account;
import model.UserCredential;

public class UsersDAO extends BaseDAO {

	private String generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

	private String hashPassword(String password, String salt) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(salt.getBytes());
			byte[] bytes = md.digest(password.getBytes());
			generatedPassword = Base64.getEncoder().encodeToString(bytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	public boolean isUserRegisteredSuccessfully(Account account) {
		boolean isSuccess = false;
		Connection conn = null;
		PreparedStatement pStmt = null;

		try {
			conn = getConnection();
			String salt = generateSalt();
			String hashedPassword = hashPassword(account.getHashedPassword(), salt);
			String sql = "INSERT INTO USERS (USERNAME, HASHED_PASSWORD, SALT, NICKNAME) VALUES (?, ?, ?, ?)";
			
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, account.getUserName());
			pStmt.setString(2, hashedPassword);
			pStmt.setString(3, salt);
			pStmt.setString(4, account.getNickname());

			int result = pStmt.executeUpdate();
			if (result == 1) {
				isSuccess = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, pStmt, null);
		}
		return isSuccess;
	}

	public Account findAccountByUserNameAndPassword(UserCredential userCredential) {
		Account account = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USERNAME,
					DatabaseConfig.DB_PASSWORD);
			String sql = "SELECT USER_ID, USERNAME, HASHED_PASSWORD, SALT, NICKNAME, ROLE FROM USERS WHERE USERNAME = ?";
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userCredential.getUserName());

			rs = pStmt.executeQuery();
			if (rs.next()) {
				String userId = rs.getString("USER_ID");
				String userName = rs.getString("USERNAME");
				String storedHash = rs.getString("HASHED_PASSWORD");
				String storedSalt = rs.getString("SALT");
				String nickname = rs.getString("NICKNAME");
				String role = rs.getString("ROLE");

				if (storedHash.equals(hashPassword(userCredential.getPassword(), storedSalt))) {
					account = new Account(userId, userName, storedHash, storedSalt, nickname, role);
					userCredential.setUserId(userId);
					userCredential.setRole(role);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeResources(conn, pStmt, rs);
		}

		return account;
	}

	public void deleteUser(String userId) {
		Connection conn = null;
		PreparedStatement Pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "DELETE FROM users WHERE USER_ID = ?";
			Pstmt = conn.prepareStatement(sql);
			Pstmt.setString(1, userId);
			Pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, Pstmt, null);
		}
	}

	public List<Account> getAllUsers() {
		List<Account> accounts = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			String sql = "SELECT * FROM USERS";
			pStmt = conn.prepareStatement(sql);
			rs = pStmt.executeQuery();

			while (rs.next()) {
				accounts.add(
						new Account(
								rs.getString("USER_ID"),
								rs.getString("USERNAME"),
								rs.getString("HASHED_PASSWORD"),
								rs.getString("SALT"),
								rs.getString("NICKNAME"),
								rs.getString("ROLE")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, pStmt, rs);
		}

		return accounts;
	}

	public boolean isUserNameExists(String userName) {
		boolean exists = false;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "SELECT USERNAME FROM USERS WHERE USERNAME = ?";
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userName);
			rs = pStmt.executeQuery();
			if (rs.next()) {
				exists = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, pStmt, rs);
		}

		return exists;
	}

}
