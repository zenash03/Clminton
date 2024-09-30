package view;

import java.sql.ResultSet;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Cart;
import model.User;
import util.Connect;
import view.header.MainNavbar;

public class CartScene extends Page implements EventHandler<ActionEvent> {

	private FlowPane mainPane;
	private GridPane formPane;
	private GridPane formRightPane;
	private GridPane formTextPane;

	private FlowPane checkOutButtonPane;
	private FlowPane deleteButtonPane;

	private Label cartLabel;
	private Label titleLabel, totalLabel, nameLabel, brandLabel, priceLabel;


	private Label nameTxt, brandTxt, priceTxt, totalTxt;

	private TableView<Cart> cartTable;
	private TableColumn<Cart, String> nameColumn;
	private TableColumn<Cart, String> brandColumn;
	private TableColumn<Cart, Integer> priceColumn;
	private TableColumn<Cart, Integer> quantityColumn;
	private TableColumn<Cart, Integer> totalPriceColumn;

	private Button checkOutButton, deleteButton;

	private ObservableList<Cart> cartDataView;
	private ArrayList<Cart> cartData;
	private Cart selectedItem = null;

	private Font fontSizeTitle, fontSizeText;

	private User userData;

	private Stage popUpStage;

	public CartScene(User userData) {
		super(userData);
		popUpStage = new Stage();
		setCartData();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(User userData) {
		this.userData = userData;

		mainPane = new FlowPane();
		formPane = new GridPane();
		formRightPane = new GridPane();
		formTextPane = new GridPane();

		checkOutButtonPane = new FlowPane();
		deleteButtonPane = new FlowPane();

		fontSizeTitle = new Font(20);
		fontSizeText = new Font(14);

		cartLabel = new Label("Cart");
		titleLabel = new Label("Your Cart List");
		titleLabel.setFont(fontSizeTitle);

		nameLabel = new Label("Name            :");
		brandLabel = new Label("Brand            :");
		priceLabel = new Label("Price              :");
		totalLabel = new Label("Total Price     :");

		nameTxt = new Label();
		brandTxt = new Label();
		priceTxt = new Label();
		totalTxt = new Label();

		nameLabel.setFont(fontSizeText);
		brandLabel.setFont(fontSizeText);
		priceLabel.setFont(fontSizeText);
		totalLabel.setFont(fontSizeText);

		nameTxt.setFont(fontSizeText);
		brandTxt.setFont(fontSizeText);
		priceTxt.setFont(fontSizeText);
		totalTxt.setFont(fontSizeText);

		cartTable = new TableView<>();

		cartData = new ArrayList<Cart>();

		nameColumn = new TableColumn<>("Name");
		brandColumn = new TableColumn<>("Brand");
		priceColumn = new TableColumn<>("Price");
		quantityColumn = new TableColumn<>("Quantity");
		totalPriceColumn = new TableColumn<>("Total");

		nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
		brandColumn.setCellValueFactory(new PropertyValueFactory<>("productBrand"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
		totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

		nameColumn.prefWidthProperty().bind(cartTable.widthProperty().multiply(0.195));
		brandColumn.prefWidthProperty().bind(cartTable.widthProperty().multiply(0.2));
		priceColumn.prefWidthProperty().bind(cartTable.widthProperty().multiply(0.2));
		quantityColumn.prefWidthProperty().bind(cartTable.widthProperty().multiply(0.2));
		totalPriceColumn.prefWidthProperty().bind(cartTable.widthProperty().multiply(0.2));
		cartTable.setPrefWidth(500);

		nameColumn.setResizable(false);
		brandColumn.setResizable(false);
		priceColumn.setResizable(false);
		quantityColumn.setResizable(false);
		totalPriceColumn.setResizable(false);

		cartTable.getColumns().setAll(nameColumn, brandColumn, priceColumn, quantityColumn, totalPriceColumn);

		checkOutButton = new Button("Checkout");
		checkOutButton.setPrefWidth(500);

		deleteButton = new Button("Delete Product");
		deleteButton.setPrefWidth(500);

		checkOutButton.setFont(fontSizeText);
		deleteButton.setFont(fontSizeText);
	}

	@Override
	public void setTopLayout(User userData) {
		setTop(new MainNavbar(userData));
	}

	@Override
	public void setLayout() {

		formRightPane.add(nameLabel, 0, 0);
		formRightPane.add(brandLabel, 0, 1);
		formRightPane.add(priceLabel, 0, 2);
		formRightPane.add(totalLabel, 0, 3);
		formRightPane.setPadding(new Insets(20, 10, 10, 20));
		formRightPane.setVgap(20);

		formTextPane.add(nameTxt, 0, 0);
		formTextPane.add(brandTxt, 0, 1);
		formTextPane.add(priceTxt, 0, 2);
		formTextPane.add(totalTxt, 0, 3);
		formTextPane.setPadding(new Insets(20, 10, 10, 0));
		formTextPane.setVgap(20);
		formTextPane.setMinWidth(100);

		formPane.add(titleLabel, 0, 0);
		formPane.add(cartTable, 0, 1);
		formPane.add(formRightPane, 1, 1);
		formPane.add(formTextPane, 2, 1);

		checkOutButtonPane.getChildren().add(checkOutButton);
		deleteButtonPane.getChildren().add(deleteButton);
		checkOutButtonPane.setAlignment(Pos.CENTER);
		deleteButtonPane.setAlignment(Pos.CENTER);
		formPane.add(checkOutButtonPane, 0, 2, 2, 1);
		formPane.add(deleteButtonPane, 0, 3, 2, 1);
		formPane.setVgap(10);
		formPane.setAlignment(Pos.CENTER);

		mainPane.getChildren().add(formPane);
		mainPane.setAlignment(Pos.CENTER);
		setCenter(mainPane);

	}

	@Override
	public void setAction() {
		cartTable.setOnMouseClicked(EventHandler -> {
			Cart cart = cartTable.getSelectionModel().getSelectedItem();
			selectedItem = cart;
			setForm();
		});

		checkOutButton.setOnAction(this);

		deleteButton.setOnAction(this);
	}

	@Override
	public void clearForm() {
		nameTxt.setText("");
		brandTxt.setText("");
		priceTxt.setText("");
		totalTxt.setText("");

	}

	public String getSceneTitle() {
		return cartLabel.getText();
	}

	@Override
	public void handle(ActionEvent event) {
		Object click = event.getSource();
		try {
			if (click.equals(deleteButton)) {
				handleDelete();
			} else if (click.equals(checkOutButton)) {
				handleCheckOut();
			}

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING, e.getMessage(), ButtonType.OK);
			alert.show();
		}

	}

	public void handleDelete() throws Exception {
		if (selectedItem == null) {
			throw new Exception("Please select product to delete");
		}


		deleteCartItem();
		setCartData();

		clearForm();
		calcTotalPrice();
		selectedItem = null;
	}

	private void handleCheckOut() throws Exception {
		if (cartDataView.isEmpty()) {
			throw new Exception("Please insert item to your cart");
		}

		TransactionCardPopUp tcp = new TransactionCardPopUp(userData, cartData, popUpStage);
		Scene pop = new Scene(tcp, 1200, 800);
		popUpStage.setScene(pop);
		popUpStage.show();
	}

	public void setForm() {
		if (selectedItem != null) {
			String name = selectedItem.getProductName();
			String brand = selectedItem.getProductBrand();
			Integer price = selectedItem.getProductPrice();
			
			nameTxt.setText(name);
			brandTxt.setText(brand);
			priceTxt.setText(Integer.toString(price));
			;
			calcTotalPrice();			
		}
	}

	private void setCartData() {
		String userID = userData.getUserID();
		String query = String.format(
				"SELECT ct.UserID, ct.ProductID, ct.Quantity, mp.ProductName, mp.ProductMerk, mp.ProductPrice FROM carttable ct JOIN msproduct mp ON ct.ProductID = mp.ProductID WHERE UserID = '%s'",
				userID);
		ResultSet res = Connect.getInstance().execQuery(query);

		clearCartTable();

		try {
			while (res.next()) {
				String userIDD = res.getString("UserID");
				String productID = res.getString("ProductID");
				String productName = res.getString("ProductName");
				String productBrand = res.getString("ProductMerk");
				Integer productPrice = res.getInt("ProductPrice");
				Integer productQuantity = res.getInt("Quantity");
				Integer totalPrice = productPrice * productQuantity;

				Cart newData = new Cart(userIDD, productID, productName, productBrand, productPrice, productQuantity,
						totalPrice);
				cartData.add(newData);
			}
			cartDataView = FXCollections.observableArrayList(cartData);
			cartTable.setItems(cartDataView);
			cartTable.refresh();
			calcTotalPrice();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void deleteCartItem() {
		String productID = selectedItem.getProductID();
		String userID = userData.getUserID();
		String query = String.format("DELETE FROM `carttable` WHERE UserID = '%s' AND ProductID = '%s'", userID,
				productID);

		Connect.getInstance().execUpdate(query);

		updateProductStock();
	}

	private void updateProductStock() {
		String productID = selectedItem.getProductID();
		Integer stock = 0;
		String query = String.format("SELECT * FROM msproduct WHERE ProductID = '%s'", productID);

		ResultSet res = Connect.getInstance().execQuery(query);

		try {
			while (res.next()) {
				stock = res.getInt("ProductStock");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		stock = stock + selectedItem.getProductQuantity();

		String updateQuery = String.format("UPDATE `msproduct` SET ProductStock='%s' WHERE ProductID = '%s'", stock,
				productID);

		Connect.getInstance().execUpdate(updateQuery);

	}

	private void clearCartTable() {
		cartData.removeAll(cartData);
		cartDataView = FXCollections.observableArrayList(cartData);
		cartTable.getItems().clear();
		cartTable.refresh();
	}

	private void calcTotalPrice() {
		Integer totalPrice = 0;
		for (Cart cart : cartData) {
			totalPrice += cart.getTotalPrice();
		}
		totalTxt.setText(String.format("%d", totalPrice));
	}

}
