package model;

public class Account {
	private String userId;
	private String userName;
	private String hashedPassword;
	private String salt;
	private String nickname;
	private String role;
	
	public Account(String userId, String userName, String hashedPassword, String salt, String nickname, String role) {
		this.userId = userId;
		this.userName = userName;
		this.hashedPassword = hashedPassword;
		this.salt = salt;
		this.nickname = nickname;
		this.role = role;
	}
	
	public Account(String userId, String userName, String hashedPassword, String salt, String nickname) {
		this(userId, userName, hashedPassword, salt, nickname, null);
	}

	public Account(String userName, String hashedPassword, String nickname) {
		this(null, userName, hashedPassword, null, nickname, null);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}