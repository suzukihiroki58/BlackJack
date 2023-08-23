package model;

public class Login {

	private String userId; 

	private String username;
	private String password;
	
	private String role;

	public Login(String username, String password) {
		this.username = username;
		this.password = password;
	}

	
	public Login(String userId, String username, String password) {
	    this.userId = userId;
	    this.username = username;
	    this.password = password;
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
	
	public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}