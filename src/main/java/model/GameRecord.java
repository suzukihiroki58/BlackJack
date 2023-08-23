package model;

	public class GameRecord {
		private String userId;
		private String username;
	    private int totalGames;
	    private int wins;
	    private int losses;
	    private int draws;
	    private int winRate;

	    public GameRecord() {
	    }
	    
	    public GameRecord(String userId, int totalGames, int wins, int losses, int draws, int winRate) {
	        this.totalGames = totalGames;
	        this.wins = wins;
	        this.losses = losses;
	        this.draws = draws;
	        this.winRate = winRate;
	    }
	    
	    public String getUserId() {
	        return userId;
	    }

	    public void setUserId(String userId) {
	        this.userId = userId;
	    }
	    
	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public int getTotalGames() {
	        return totalGames;
	    }

	    public void setTotalGames(int totalGames) {
	        this.totalGames = totalGames;
	    }

	    public int getWins() {
	        return wins;
	    }

	    public void setWins(int wins) {
	        this.wins = wins;
	    }

	    public int getLosses() {
	        return losses;
	    }

	    public void setLosses(int losses) {
	        this.losses = losses;
	    }

	    public int getDraws() {
	        return draws;
	    }

	    public void setDraws(int draws) {
	        this.draws = draws;
	    }
	    
	    public int getWinrate() {
	    	return winRate;
	    }
	    
	    public void setWinrate(int winRate) {
	    	this.winRate = winRate;
	    }
	}