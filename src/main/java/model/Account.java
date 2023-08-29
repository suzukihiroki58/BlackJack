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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return userName;
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
	
	public String getRole() {
		  return role;
		}

	public void setRole(String role) {
	  this.role = role;
	}
}