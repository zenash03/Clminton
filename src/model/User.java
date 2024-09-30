package model;

public class User {
	private String userID;
	private String userEmail;
	private String userPassword;
	private Integer userAge;
	private String userGender;
	private String userNationality;
	private String userRole;
	
	public User(String userID, String userEmail, String userPassword, Integer userAge, String userGender,
			String userNationality, String userRole) {
		this.userID = userID;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userAge = userAge;
		this.userGender = userGender;
		this.userNationality = userNationality;
		this.userRole = userRole;
	}
	public User(String userEmail, String userPassword) {
		this.userEmail = userEmail;
		this.userPassword = userPassword;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Integer getUserAge() {
		return userAge;
	}

	public void setUserAge(Integer userAge) {
		this.userAge = userAge;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getUserNationality() {
		return userNationality;
	}

	public void setUserNationality(String userNationality) {
		this.userNationality = userNationality;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
	
}
