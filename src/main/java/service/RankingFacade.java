package service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import dao.GameRecordsDAO;
import model.GameRecord;

public class RankingFacade {
	public List<GameRecord> fetchAllRankings() {
        GameRecordsDAO dao = new GameRecordsDAO();
        return dao.getAllUserRecords();
    }
	
	public GameRecord fetchUserRecords(String userId) {
        GameRecordsDAO dao = new GameRecordsDAO();
        return dao.getUserRecords(userId);
    }

    public void prepareUserRecordForView(HttpServletRequest request, GameRecord record) {
        request.setAttribute("totalGames", record.getTotalGames());
        request.setAttribute("wins", record.getWins());
        request.setAttribute("losses", record.getLosses());
        request.setAttribute("draws", record.getDraws());
        request.setAttribute("winRate", record.getWinRate());
    }
	
	public void handleUserRecords(HttpServletRequest request, String userId) {
	    GameRecord record = fetchUserRecords(userId);
	    
	    if (record != null) {
	        prepareUserRecordForView(request, record);
	    } else {
	        request.setAttribute("errorMessage", "個人戦績の取得に失敗しました。");
	    }
	}

}
