package model;

public class TransactionHeader {

	private String transactionID;
	private String userID;
	private String emailUser;
	private String transactionDate;
	private Integer deliveryInsurance;
	private String courierType;
	
	public TransactionHeader(String transactionID, String userID, String emailUser, String transactionDate,
			Integer deliveryInsurance, String courierType) {
		super();
		this.transactionID = transactionID;
		this.userID = userID;
		this.emailUser = emailUser;
		this.transactionDate = transactionDate;
		this.deliveryInsurance = deliveryInsurance;
		this.courierType = courierType;
	}
	
	public TransactionHeader(String transactionID, String userID, String emailUser, String transactionDate) {
		super();
		this.transactionID = transactionID;
		this.userID = userID;
		this.emailUser = emailUser;
		this.transactionDate = transactionDate;
	}


	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getEmailUser() {
		return emailUser;
	}

	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}

	public Integer getDeliveryInsurance() {
		return deliveryInsurance;
	}

	public void setDeliveryInsurance(Integer deliveryInsurance) {
		this.deliveryInsurance = deliveryInsurance;
	}

	public String getCourierType() {
		return courierType;
	}

	public void setCourierType(String courierType) {
		this.courierType = courierType;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	
	
}
