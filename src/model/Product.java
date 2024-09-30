package model;


public class Product {
	
	private String productID;
	private String productName;
	private String productMerk;
	private Integer productPrice;
	private Integer productStock;
	
	
	public Product(String productID, String productName, String productMerk, Integer productPrice,
			Integer productStock) {
		super();
		this.productID = productID;
		this.productName = productName;
		this.productMerk = productMerk;
		this.productPrice = productPrice;
		this.productStock = productStock;
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


	public String getProductMerk() {
		return productMerk;
	}


	public void setProductMerk(String productMerk) {
		this.productMerk = productMerk;
	}


	public Integer getProductPrice() {
		return productPrice;
	}


	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}


	public Integer getProductStock() {
		return productStock;
	}


	public void setProductStock(Integer productStock) {
		this.productStock = productStock;
	}
	
	
	
	
	

}
