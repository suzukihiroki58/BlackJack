package model;

public class Account {
	private String userId;
	private String username;
	private String password;
	private String nickname;

	public Account(String userId, String username, String password, String nickname) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.nickname = nickname;
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

	public String getPassword() {
		return password;
	}

	public String getNickname() {
		return nickname;
	}
}