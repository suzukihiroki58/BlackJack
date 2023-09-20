package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.GameRecord;

public class GameRecordsDAO extends BaseDAO {

	public GameRecord calculateGameStats(GameRecord gameRecord) {
		GameRecord stats = new GameRecord();

		int wins = gameRecord.getWins();
		int losses = gameRecord.getLosses();
		int draws = gameRecord.getDraws();
		int totalGames = gameRecord.getTotalGames();
		float winRate;
		if (totalGames != 0) {
			winRate = ((float) wins) / totalGames * 100;
		} else {
			winRate = 0;
		}

		stats.setTotalGames(totalGames);
		stats.setWins(wins);
		stats.setLosses(losses);
		stats.setDraws(draws);
		stats.setWinRate(winRate);

		return stats;
	}

	public void updateGameRecords(GameRecord gameRecord) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = getConnection();

			GameRecord stats = calculateGameStats(gameRecord);

			String upsertQuery = "INSERT INTO game_records (user_id, total_games, wins, losses, draws, win_rate) "
					+ "VALUES (?, ?, ?, ?, ?, ?) "
					+ "ON DUPLICATE KEY UPDATE "
					+ "total_games = total_games + 1, "
					+ "wins = wins + VALUES(wins), "
					+ "losses = losses + VALUES(losses), "
					+ "draws = draws + VALUES(draws), "
					+ "win_rate = (wins / total_games) * 100";

			ps = conn.prepareStatement(upsertQuery);
			ps.setString(1, gameRecord.getUserId());
			ps.setInt(2, stats.getTotalGames());
			ps.setInt(3, stats.getWins());
			ps.setInt(4, stats.getLosses());
			ps.setInt(5, stats.getDraws());
			ps.setFloat(6, stats.getWinRate());

			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, ps, null);
		}
	}

	public GameRecord getUserRecords(String userId) {
		GameRecord record = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "SELECT total_games, wins, losses, draws, win_rate FROM game_records WHERE user_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, userId);

			rs = ps.executeQuery();

			if (rs.next()) {
				record = new GameRecord();
				record.setTotalGames(rs.getInt("total_games"));
				record.setWins(rs.getInt("wins"));
				record.setLosses(rs.getInt("losses"));
				record.setDraws(rs.getInt("draws"));
				record.setWinRate(rs.getInt("win_rate"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, ps, rs);
		}

		return record;
	}

	public List<GameRecord> getAllUserRecords() {
		List<GameRecord> records = new ArrayList<>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			String sql = "SELECT g.user_id, u.USERNAME, g.total_games, g.wins, g.losses, g.draws, g.win_rate " +
					"FROM game_records g " +
					"JOIN USERS u ON g.user_id = u.USER_ID " +
					"ORDER BY g.win_rate DESC";
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				GameRecord record = new GameRecord();
				record.setUserId(rs.getString("user_id"));
				record.setUserName(rs.getString("USERNAME"));
				record.setTotalGames(rs.getInt("total_games"));
				record.setWins(rs.getInt("wins"));
				record.setLosses(rs.getInt("losses"));
				record.setDraws(rs.getInt("draws"));
				record.setWinRate(rs.getInt("win_rate"));
				records.add(record);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, ps, rs);
		}

		return records;
	}

	public void updatePlayerChips(String userId, int chips) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = getConnection();
			String sql = "INSERT INTO player_chips (user_id, chips) VALUES (?, ?) ON DUPLICATE KEY UPDATE chips = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setInt(2, chips);
			ps.setInt(3, chips);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, ps, null);
		}
	}

	public int getPlayerChips(String userId) {
		int chips = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "SELECT chips FROM player_chips WHERE user_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			rs = ps.executeQuery();

			if (rs.next()) {
				chips = rs.getInt("chips");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, ps, rs);
		}
		return chips;
	}

	public List<GameRecord> getAllUserChipRecords() {
		List<GameRecord> records = new ArrayList<>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "SELECT p.user_id, u.USERNAME, p.chips " +
					"FROM player_chips p " +
					"JOIN USERS u ON p.user_id = u.USER_ID " +
					"ORDER BY p.chips DESC";
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				GameRecord record = new GameRecord();
				record.setUserId(rs.getString("user_id"));
				record.setUserName(rs.getString("USERNAME"));
				record.setChips(rs.getInt("chips"));
				records.add(record);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, ps, rs);
		}

		return records;
	}

}
