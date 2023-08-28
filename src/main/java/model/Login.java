package model;

public class Login {

	private String userId;

	private String userName;
	private String password;

	private String role;

	public Login(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public Login(String userId, String userName, String password) {
		this.userId = userId;
		this.userName = userName;
		this.password = password;
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

	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}