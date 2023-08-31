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

import model.Account;
import model.GameRecord;
import model.UserCredential;

public class AccountsDAO {

	private static final String DB_URL = "jdbc:mysql://localhost/BlackJack";
	private static final String DB_USERNAME = "1";
	private static final String DB_PASSWORD = "1234";

	public int totalGames;
	public int wins;
	public int losses;
	public int draws;
	public float winRate;

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

	public boolean isUserRegisteredSuccessfully(String userName, String password, String nickname) {
		boolean isSuccess = false;

		String salt = generateSalt();
		String hashedPassword = hashPassword(password, salt);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		String sql = "INSERT INTO USERS (USERNAME, HASHED_PASSWORD, SALT, NICKNAME) VALUES (?, ?, ?, ?)";

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
				PreparedStatement pStmt = conn.prepareStatement(sql)) {

			pStmt.setString(1, userName);
			pStmt.setString(2, hashedPassword);
			pStmt.setString(3, salt);
			pStmt.setString(4, nickname);

			int result = pStmt.executeUpdate();

			if (result == 1) {
				isSuccess = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return isSuccess;
	}

	public Account findAccountByUserNameAndPassword(UserCredential userCredential) {
		Account account = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			String sql = "SELECT USER_ID, USERNAME, HASHED_PASSWORD, SALT, NICKNAME, ROLE FROM USERS WHERE USERNAME = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userCredential.getUserName());

			ResultSet rs = pStmt.executeQuery();

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
		}

		return account;
	}

	public AccountsDAO calculateGameStats(boolean win, boolean lose, boolean draw) {
		AccountsDAO stats = new AccountsDAO();

		stats.wins = win ? 1 : 0;
		stats.losses = lose ? 1 : 0;
		stats.draws = draw ? 1 : 0;
		stats.totalGames = 1;
		stats.winRate = ((float) stats.wins) / stats.totalGames * 100;

		return stats;
	}

	public void updateGameRecords(int userId, boolean win, boolean lose, boolean draw) {
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

			AccountsDAO stats = calculateGameStats(win, lose, draw);

			String upsertQuery = "INSERT INTO game_records (user_id, total_games, wins, losses, draws, win_rate) VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE total_games = total_games + 1, wins = wins + VALUES(wins), losses = losses + VALUES(losses), draws = draws + VALUES(draws), win_rate = (wins / total_games) * 100";

			PreparedStatement upsertPs = conn.prepareStatement(upsertQuery);
			upsertPs.setInt(1, userId);
			upsertPs.setInt(2, stats.totalGames);
			upsertPs.setInt(3, stats.wins);
			upsertPs.setInt(4, stats.losses);
			upsertPs.setInt(5, stats.draws);
			upsertPs.setFloat(6, stats.winRate);

			upsertPs.executeUpdate();
			upsertPs.close();

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteUser(String userId) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

			String sql = "DELETE FROM users WHERE USER_ID = ?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, userId);

			statement.executeUpdate();
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	public GameRecord getUserRecords(String userId) {
		GameRecord record = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			String sql = "SELECT total_games, wins, losses, draws, win_rate FROM game_records WHERE user_id = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);

			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {
				record = new GameRecord();
				record.setTotalGames(rs.getInt("total_games"));
				record.setWins(rs.getInt("wins"));
				record.setLosses(rs.getInt("losses"));
				record.setDraws(rs.getInt("draws"));
				record.setWinrate(rs.getInt("win_rate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return record;
	}

	public void deleteGameRecords(String userId) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

			String sql = "DELETE FROM game_records WHERE user_id = ?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, userId);

			statement.executeUpdate();
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	public List<GameRecord> getAllUserRecords() {
		List<GameRecord> records = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			String sql = "SELECT g.user_id, u.USERNAME, g.total_games, g.wins, g.losses, g.draws, g.win_rate " +
					"FROM game_records g " +
					"JOIN USERS u ON g.user_id = u.USER_ID " +
					"ORDER BY g.win_rate DESC";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				GameRecord record = new GameRecord();
				record.setUserId(rs.getString("user_id"));
				record.setUserName(rs.getString("USERNAME"));
				record.setTotalGames(rs.getInt("total_games"));
				record.setWins(rs.getInt("wins"));
				record.setLosses(rs.getInt("losses"));
				record.setDraws(rs.getInt("draws"));
				record.setWinrate(rs.getInt("win_rate"));
				records.add(record);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return records;
	}

	public List<Account> getAllUsers() {
		List<Account> accounts = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			String sql = "SELECT USER_ID, USERNAME, NICKNAME, ROLE FROM USERS";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			ResultSet rs = pStmt.executeQuery();

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
		}

		return accounts;
	}

	public boolean isUserNameExists(String userName) {
		boolean exists = false;
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			String sql = "SELECT USERNAME FROM USERS WHERE USERNAME = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userName);

			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				exists = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exists;
	}

}
