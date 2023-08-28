package model;

public class Account {
	private String userId;
	private String userName;
	private String password;
	private String nickname;

	public Account(String userId, String userName, String password, String nickname) {
		this.userId = userId;
		this.userName = userName;
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
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getNickname() {
		return nickname;
	}
}