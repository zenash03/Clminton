package view;

import java.sql.ResultSet;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import main.Main;
import model.Product;
import model.User;
import util.Connect;
import view.header.MainNavbar;

public class HomeScene extends Page implements EventHandler<ActionEvent> {

	private FlowPane mainPane;
	private GridPane formPane;
	private GridPane formRightPane;

	private Label homeLabel;
	private Label headerLabel, nameLabel, brandLabel, priceLabel, totalPriceLabel;
	private Button addCartButton;
	private Spinner<Integer> quantitySpinner;

	private TableView<Product> productTable;
	private TableColumn<Product, String> nameColumn;
	private TableColumn<Product, String> brandColumn;
	private TableColumn<Product, Integer> stockColumn;
	private TableColumn<Product, Integer> priceColumn;

	private ObservableList<Product> productDataView;
	private ArrayList<Product> productData;
	private Product selectedItem = null;

	private Font fontSizeTitle, fontSizeText;

	private User userData;

	public HomeScene(User userData) {
		super(userData);
		setProductData();

	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(User userData) {
		this.userData = userData;
		
		fontSizeTitle = new Font(18);
		fontSizeText = new Font(14);

		mainPane = new FlowPane();
		formPane = new GridPane();
		formRightPane = new GridPane();

		homeLabel = new Label("Home");
		headerLabel = new Label("Product List");
		nameLabel = new Label("Product Name : ");
		brandLabel = new Label("Product Brand: ");
		priceLabel = new Label("Price        : ");
		totalPriceLabel = new Label("Total Price  : ");

		addCartButton = new Button("Add to Cart");

		quantitySpinner = new Spinner<>(0, 1, 0);

		productTable = new TableView<Product>();
		productTable.setPrefWidth(400);

		productData = new ArrayList<>();

		nameColumn = new TableColumn<>("Name");
		brandColumn = new TableColumn<>("Brand");
		stockColumn = new TableColumn<>("Stock");
		priceColumn = new TableColumn<>("Price");

		nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
		brandColumn.setCellValueFactory(new PropertyValueFactory<>("productMerk"));
		stockColumn.setCellValueFactory(new PropertyValueFactory<>("productStock"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));

		nameColumn.prefWidthProperty().bind(productTable.widthProperty().multiply(0.245));
		brandColumn.prefWidthProperty().bind(productTable.widthProperty().multiply(0.25));
		stockColumn.prefWidthProperty().bind(productTable.widthProperty().multiply(0.25));
		priceColumn.prefWidthProperty().bind(productTable.widthProperty().multiply(0.25));

		nameColumn.setResizable(false);
		brandColumn.setResizable(false);
		stockColumn.setResizable(false);
		priceColumn.setResizable(false);

		productTable.getColumns().setAll(nameColumn, brandColumn, stockColumn, priceColumn);

		headerLabel.setFont(fontSizeTitle);
		nameLabel.setFont(fontSizeText);
		brandLabel.setFont(fontSizeText);
		priceLabel.setFont(fontSizeText);
		totalPriceLabel.setFont(fontSizeText);
	}

	@Override
	public void setTopLayout(User userData) {
		// TODO Auto-generated method stub
		setTop(new MainNavbar(userData));
	}

	@Override
	public void setLayout() {
		// TODO Auto-generated method stub
		formRightPane.add(nameLabel, 0, 1);
		formRightPane.add(brandLabel, 0, 2);
		formRightPane.add(priceLabel, 0, 3);
		formRightPane.add(quantitySpinner, 0, 4);
		formRightPane.add(totalPriceLabel, 0, 5);
		formRightPane.add(addCartButton, 0, 6);
		formRightPane.setVgap(25);
		formRightPane.setMinWidth(200);

		formPane.add(headerLabel, 0, 0);
		formPane.add(productTable, 0, 1);
		formPane.add(formRightPane, 1, 1);
		formPane.setHgap(10);
		formPane.setVgap(10);
		formPane.setAlignment(Pos.CENTER);

		mainPane.getChildren().add(formPane);
		mainPane.setAlignment(Pos.CENTER);
		setCenter(mainPane);

	}

	@Override
	public void setAction() {
		// TODO Auto-generated method stub

		productTable.setOnMouseClicked(event -> {
			Product prod = productTable.getSelectionModel().getSelectedItem();
			selectedItem = prod;
			setForm();
		});
		quantitySpinner.setOnMouseClicked(event -> {
			calculateTotalPrice();
//			System.out.println("ADAWd");
		});
		addCartButton.setOnAction(this);
	}

	@Override
	public void clearForm() {
		// TODO Auto-generated method stub
		nameLabel.setText(formLabelFormat("Product Name", ""));
		brandLabel.setText(formLabelFormat("Product Brand", ""));
		priceLabel.setText(formLabelFormat("Price", ""));
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		try {
			if (event.getSource().equals(addCartButton)) {
				addToCart();
//			setProductData();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getSceneTitle() {
		return homeLabel.getText();
	}

	public void setForm() {
		String name = selectedItem.getProductName();
		String brand = selectedItem.getProductMerk();
		Integer price = selectedItem.getProductPrice();
		Integer stock = selectedItem.getProductStock();

		nameLabel.setText(formLabelFormat("Product Name", name));
		brandLabel.setText(formLabelFormat("Product Brand", brand));
		priceLabel.setText(formLabelFormat("Price", Integer.toString(price)));

		SpinnerValueFactory<Integer> newValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, stock, 1);

		quantitySpinner.setValueFactory(newValue);

		Integer totalPrice = price * quantitySpinner.getValue();
		totalPriceLabel.setText(formLabelFormat("Total Price", Integer.toString(totalPrice)));
	}

	public String formLabelFormat(String labelName, String result) {
		return String.format("%s %s %1s", labelName, ":", result);
	}

	public void calculateTotalPrice() {
		Integer price;
		if (selectedItem == null)
			price = 0;
		else
			price = selectedItem.getProductPrice();
		Integer totalPrice = price * quantitySpinner.getValueFactory().getValue();
		totalPriceLabel.setText(formLabelFormat("Total Price", Integer.toString(totalPrice)));
	}

	private void setProductData() {
		String query = "SELECT * FROM msproduct";
		ResultSet res = Connect.getInstance().execQuery(query);

		clearProductTable();

		try {
			while (res.next()) {
				String productID = res.getString("ProductID");
				String productName = res.getString("ProductName");
				String productMerk = res.getString("ProductMerk");
				Integer productPrice = res.getInt("ProductPrice");
				Integer productStock = res.getInt("ProductStock");
				if (productStock >= 1) {
					productData.add(new Product(productID, productName, productMerk, productPrice, productStock));
				}
			}
			productDataView = FXCollections.observableArrayList(productData);
			productTable.setItems(productDataView);
			productTable.refresh();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void clearProductTable() {
		productData.removeAll(productData);
		productDataView = FXCollections.observableArrayList(productData);
		productTable.getItems().clear();
		productTable.refresh();
	}

	private void addToCart() throws Exception {
		// TODO Auto-generated method stub
		Product item = selectedItem;

		try {
			if (item == null) {
				throw new Exception("Please Choose 1 item");
			} else if (checkItemSelected()) {
				updateCart();
				HomeScene home = new HomeScene(userData);
				Main.changeScene(home, home.getSceneTitle());
			} else {
				insertIntoCart();
				HomeScene home = new HomeScene(userData);
				Main.changeScene(home, home.getSceneTitle());
			}
		} catch (Exception e) {
			// TODO: handle exception
			showWarning(e.getMessage());
		}
	}

	private void showWarning(String message) {
		Alert alert = new Alert(AlertType.WARNING, message, ButtonType.OK);
		alert.show();

	}

	private void insertIntoCart() {
		String productID = selectedItem.getProductID();
		Integer quantity = quantitySpinner.getValue();
		String query = String.format("INSERT INTO carttable(UserID, ProductID, Quantity) VALUES ('%s','%s','%d')",
				userData.getUserID(), productID, quantity);

		Connect.getInstance().execUpdate(query);

		updateProductTable();
	}

	private void updateCart() throws Exception {
		String productID = selectedItem.getProductID();
		String userID = userData.getUserID();

		String query = String.format("SELECT * FROM `carttable` WHERE UserID = '%s' AND ProductID = '%s'", userID,
				productID);
		ResultSet res = Connect.getInstance().execQuery(query);

		Integer quantity = 0;

		try {
			while (res.next()) {
				quantity = res.getInt("Quantity");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("Product Not Exist");
		}
		quantity = quantity + quantitySpinner.getValue();

		String updateQuery = String.format(
				"UPDATE carttable SET Quantity='%d' WHERE UserID = '%s' AND ProductID = '%s'", quantity, userData.getUserID(),
				productID);

		Connect.getInstance().execUpdate(updateQuery);

		updateProductTable();
	}

	private boolean checkItemSelected() {
		String productID = selectedItem.getProductID();
		String userID = userData.getUserID();
		String query = String.format("SELECT * FROM `carttable` WHERE UserID = '%s' AND ProductID = '%s'", userID,
				productID);

		ResultSet res = Connect.getInstance().execQuery(query);
		int row = 0;
		try {
			while(res.next()) {
				++row;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(row == 0) return false;
		
		return true;
	}

	private void updateProductTable() {
		String productID = selectedItem.getProductID();
		Integer oldStock = selectedItem.getProductStock();
		Integer newStock = oldStock - quantitySpinner.getValue();
		String query = String.format("UPDATE msproduct SET ProductStock ='%d' WHERE ProductID = '%s'", newStock,
				productID);

		Connect.getInstance().execUpdate(query);
	}
}
