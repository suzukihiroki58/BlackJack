package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Account;
import model.GameRecord;
import model.Login;

public class AccountsDAO {

	private static final String DB_URL = "jdbc:mysql://localhost/BlackJack";
	private static final String DB_USERNAME = "1";
	private static final String DB_PASSWORD = "1234";

	public boolean registerUser(String userName, String password, String nickname) {
		boolean isSuccess = false;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		String sql = "INSERT INTO USERS (USERNAME, PASSWORD, NICKNAME) VALUES (?, ?, ?)";

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
				PreparedStatement pStmt = conn.prepareStatement(sql)) {

			pStmt.setString(1, userName);
			pStmt.setString(2, password);
			pStmt.setString(3, nickname);

			int result = pStmt.executeUpdate();

			if (result == 1) {
				isSuccess = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return isSuccess;
	}

	public Account findByLogin(Login login) {
		Account account = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			String sql = "SELECT USER_ID, USERNAME, PASSWORD, NICKNAME, ROLE FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, login.getUserName());
			pStmt.setString(2, login.getPassword());

			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {
				String userId = rs.getString("USER_ID");
				String userName = rs.getString("USERNAME");
				String password = rs.getString("PASSWORD");
				String nickname = rs.getString("NICKNAME");
				String role = rs.getString("ROLE");

				login.setUserId(userId);
				login.setRole(role);

				account = new Account(userId, userName, password, nickname);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return account;
	}

	public void updateGameRecords(int userId, boolean win, boolean lose, boolean draw) {
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

			String query = "SELECT * FROM game_records WHERE user_id = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int totalGames = rs.getInt("total_games") + 1;
				int wins = rs.getInt("wins");
				int losses = rs.getInt("losses");
				int draws = rs.getInt("draws");

				if (win)
					wins++;
				if (lose)
					losses++;
				if (draw)
					draws++;

				float winRate = ((float) wins) / totalGames * 100;

				String updateQuery = "UPDATE game_records SET total_games=?, wins=?, losses=?, draws=?, win_rate=? WHERE user_id=?";
				PreparedStatement updatePs = conn.prepareStatement(updateQuery);
				updatePs.setInt(1, totalGames);
				updatePs.setInt(2, wins);
				updatePs.setInt(3, losses);
				updatePs.setInt(4, draws);
				updatePs.setFloat(5, winRate);
				updatePs.setInt(6, userId);
				updatePs.executeUpdate();
			} else {

				int totalGames = 1;
				int wins = win ? 1 : 0;
				int losses = lose ? 1 : 0;
				int draws = draw ? 1 : 0;
				float winRate = ((float) wins) / totalGames * 100;

				String insertQuery = "INSERT INTO game_records (user_id, total_games, wins, losses, draws, win_rate) VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement insertPs = conn.prepareStatement(insertQuery);
				insertPs.setInt(1, userId);
				insertPs.setInt(2, totalGames);
				insertPs.setInt(3, wins);
				insertPs.setInt(4, losses);
				insertPs.setInt(5, draws);
				insertPs.setFloat(6, winRate);
				insertPs.executeUpdate();
				insertPs.close();
			}

			rs.close();
			ps.close();
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
						new Account(rs.getString("USER_ID"), rs.getString("USERNAME"), "", rs.getString("NICKNAME")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return accounts;
	}

}
