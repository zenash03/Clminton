package model;

public class TransactionDetail {
	
	private String transactionDetailID;
	private String productID;
	private String productName;
	private Integer productPrice;
	private Integer quantity;
	private Integer totalPrice;
	
	public TransactionDetail(String transactionDetailID, String productID, Integer quantity) {
		super();
		this.transactionDetailID = transactionDetailID;
		this.productID = productID;
		this.quantity = quantity;
	}

	public TransactionDetail(String transactionDetailID, String productID, String productName, Integer productPrice,
			Integer quantity, Integer totalPrice) {
		super();
		this.transactionDetailID = transactionDetailID;
		this.productID = productID;
		this.productName = productName;
		this.productPrice = productPrice;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}

	public String getTransactionDetailID() {
		return transactionDetailID;
	}

	public void setTransactionDetailID(String transactionDetailID) {
		this.transactionDetailID = transactionDetailID;
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

	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
}
