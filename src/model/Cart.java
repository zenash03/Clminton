package model;

public class Cart {

	private String userID;
	private String productID;
	
	private String productName;
	private String productBrand;
	private Integer productPrice;
	private Integer productQuantity;
	private Integer totalPrice;
	
	
	public Cart(String userID, String productID, String productName, String productBrand, Integer productPrice,
			Integer productQuantity, Integer totalPrice) {
		super();
		this.userID = userID;
		this.productID = productID;
		this.productName = productName;
		this.productBrand = productBrand;
		this.productPrice = productPrice;
		this.productQuantity = productQuantity;
		this.totalPrice = totalPrice;
	}


	public String getUserID() {
		return userID;
	}


	public void setUserID(String userID) {
		this.userID = userID;
	}


	public String getProductID() {
		return productID;
	}


	public void setProductID(String productID) {
		this.productID = productID;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public String getProductBrand() {
		return productBrand;
	}


	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}


	public Integer getProductPrice() {
		return productPrice;
	}


	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}


	public Integer getProductQuantity() {
		return productQuantity;
	}


	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}


	public Integer getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
	
}
