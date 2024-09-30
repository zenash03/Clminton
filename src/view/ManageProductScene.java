package view;

import java.sql.ResultSet;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import model.Product;
import model.User;
import util.Connect;
import view.header.AdminNavbar;

public class ManageProductScene extends Page implements EventHandler<ActionEvent> {

	private GridPane formPane;
	private GridPane formLeftPane;
	private GridPane formRightPane;
	private GridPane formComboPane;

	private FlowPane productListPane, productNamePane, addStockPane, deleteProductPane, addStockBtnPane,
			deleteProductBtnPane;

	private Label manageProductLabel;
	private Label productListText;
	private Label productResultText, productNameText;

	private Label productNameLabel, productBrandLabel, productPriceLabel, addStockLabel, deleteProductLabel;
	private TextField productNameField, productPriceField;

	private Button addProductButton, addStockButton, deleteButton;

	private ComboBox<String> productBrandBox;
	private Spinner<Integer> addStockSpinner;

	private TableView<Product> productTable;

	private TableColumn<Product, String> nameColumn;
	private TableColumn<Product, String> brandColumn;
	private TableColumn<Product, Integer> stockColumn;
	private TableColumn<Product, Integer> priceColumn;

	private ObservableList<Product> productDataView;
	private ArrayList<Product> productData;

	private Font fontSizeText, fontSizeTitle;

	private Product selectedProduct = null;
	private User userData;

	public ManageProductScene(User userData) {
		super(userData);
		setProductData();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(User userData) {
		this.userData = userData;

		fontSizeText = new Font(15);
		fontSizeTitle = new Font(20);
		formPane = new GridPane();
		formLeftPane = new GridPane();
		formRightPane = new GridPane();
		formComboPane = new GridPane();
		productNamePane = new FlowPane();
		addStockPane = new FlowPane();
		deleteProductPane = new FlowPane();
		productListPane = new FlowPane();
		addStockBtnPane = new FlowPane();
		deleteProductBtnPane = new FlowPane();

		manageProductLabel = new Label("Manage Product");
		productListText = new Label("Product List");
		productNameText = new Label("Name		: ");
		productResultText = new Label("");
		productNameLabel = new Label("Product Name");
		productBrandLabel = new Label("Product Brand");
		productPriceLabel = new Label("Product Price");
		addStockLabel = new Label("Add Stock");
		deleteProductLabel = new Label("Delete Product");

		productNameField = new TextField();
		productPriceField = new TextField();

		addProductButton = new Button("Add Product");
		addStockButton = new Button("Add Stock");
		deleteButton = new Button("Delete");

		productBrandBox = new ComboBox<>();
		productBrandBox.setMinWidth(120);
		productBrandBox.getItems().addAll("Yonex", "Li-Ning", "Victor");
		productBrandBox.getSelectionModel().selectFirst();

		addStockSpinner = new Spinner<>(1, 10, 1);

		productTable = new TableView<Product>();

		productTable.setMaxSize(400, 600);

		productData = new ArrayList<>();
		productDataView = FXCollections.observableArrayList(productData);
		productTable.setItems(productDataView);

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

//		productTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		productTable.getColumns().setAll(nameColumn, brandColumn, stockColumn, priceColumn);

		productListText.setFont(fontSizeTitle);
		productResultText.setFont(fontSizeTitle);
		productNameText.setFont(fontSizeTitle);
		addStockLabel.setFont(fontSizeText);
		deleteProductLabel.setFont(fontSizeText);
		productBrandLabel.setFont(fontSizeText);
		productNameLabel.setFont(fontSizeText);
		productPriceLabel.setFont(fontSizeText);
	}

	@Override
	public void setTopLayout(User userData) {
		// TODO Auto-generated method stub
		setTop(new AdminNavbar(userData));
	}

	@Override
	public void setLayout() {
		// TODO Auto-generated method stub

		// Left Layout
		productListPane.getChildren().add(productListText);
		productListPane.setAlignment(Pos.CENTER);
		formLeftPane.add(productListPane, 0, 0);
		formLeftPane.add(productTable, 0, 1);
		formLeftPane.setVgap(15);

		// Right Layout
		formRightPane.add(productNameLabel, 0, 1);
		formRightPane.add(productNameField, 0, 2);
		formRightPane.add(productBrandLabel, 0, 3);
		formRightPane.add(productBrandBox, 0, 4);
		formRightPane.add(productPriceLabel, 0, 5);
		formRightPane.add(productPriceField, 0, 6);
		formRightPane.add(addProductButton, 0, 7);
		formRightPane.setVgap(15);
		formRightPane.setPadding(new Insets(30, 0, 0, 0));

		// combo Layout
		addStockPane.getChildren().add(addStockLabel);
		addStockPane.setAlignment(Pos.CENTER);
		addStockPane.setPrefWidth(100);
		formComboPane.add(addStockPane, 0, 1);

		deleteProductPane.getChildren().add(deleteProductLabel);
		deleteProductPane.setAlignment(Pos.CENTER);
		deleteProductPane.setPrefWidth(120);
		formComboPane.add(deleteProductPane, 1, 1);

		formComboPane.add(addStockSpinner, 0, 2);

		addStockButton.setPrefWidth(100);
		addStockBtnPane.getChildren().add(addStockButton);
		addStockBtnPane.setAlignment(Pos.CENTER);
		addStockBtnPane.setPrefWidth(120);
		formComboPane.add(addStockBtnPane, 0, 3);

		deleteButton.setPrefWidth(100);
		deleteProductBtnPane.getChildren().add(deleteButton);
		deleteProductBtnPane.setAlignment(Pos.CENTER);
		deleteProductBtnPane.setPrefWidth(100);
		formComboPane.add(deleteProductBtnPane, 1, 3);
		formComboPane.setAlignment(Pos.CENTER);
		formComboPane.setVgap(15);
		formComboPane.setHgap(20);

		// form Pane Layout
		formPane.add(formLeftPane, 0, 0);
		formPane.add(formRightPane, 1, 0);

		productNamePane.getChildren().add(productNameText);
		productNamePane.getChildren().add(productResultText);
		productNamePane.setAlignment(Pos.CENTER);
		productNamePane.setOrientation(Orientation.HORIZONTAL);

		formPane.add(productNamePane, 0, 1, 2, 1);

		formPane.add(formComboPane, 0, 2, 2, 1);
		formPane.setVgap(5);
		formPane.setHgap(15);

		formPane.setAlignment(Pos.CENTER);
		setCenter(formPane);
	}

	@Override
	public void setAction() {
		// TODO Auto-generated method stub
		productTable.setOnMouseClicked(e -> {
			selectedProduct = productTable.getSelectionModel().getSelectedItem();

			if (selectedProduct == null)
				return;

			productResultText.setText(selectedProduct.getProductName());
		});

		addStockButton.setOnAction(this);
		deleteButton.setOnAction(this);
		addProductButton.setOnAction(this);
	}

	@Override
	public void clearForm() {
		// TODO Auto-generated method stub
		productNameField.setText("");
		productBrandBox.getSelectionModel().selectFirst();
		productPriceField.setText("");
		productResultText.setText("");
		SpinnerValueFactory<Integer> newValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
		addStockSpinner.setValueFactory(newValue);
	}

	public String getSceneTitle() {
		return manageProductLabel.getText();
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		try {
			if (event.getSource().equals(addStockButton)) {
				handleAddStock();
			} else if (event.getSource().equals(deleteButton)) {
				handleDelete();
			} else if (event.getSource().equals(addProductButton)) {
				handleAddProduct();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void handleAddStock() throws Exception {
		if (selectedProduct == null) {
			throw new Exception("Select a product");
		}
		updateProduct(selectedProduct);
		setProductData();
		clearForm();
		selectedProduct = null;
	}

	private void handleDelete() throws Exception {
		if (selectedProduct == null) {
			throw new Exception("Select a product");
		}
		deleteProduct(selectedProduct);
		setProductData();
		clearForm();
		selectedProduct = null;
	}

	private void handleAddProduct() throws Exception {
		Product newDataProduct = getFormProduct();
		insertIntoProduct(newDataProduct);
		setProductData();
		clearForm();
	}

	private Product getFormProduct() throws Exception {
		String ProductName, ProductBrand, tempPrice;
		int ProductPrice;

		ProductName = productNameField.getText();
		ProductBrand = productBrandBox.getSelectionModel().getSelectedItem();
//		int aprice = productPriceField.getText();
		tempPrice = productPriceField.getText();
		if (!tempPrice.matches("[0-9]+")) {
			throw new Exception("Price must be number");
		}
		ProductPrice = Integer.parseInt(tempPrice);

		if (ProductName.isEmpty())
			throw new Exception("Name must not empty");
		else if (ProductPrice <= 0)
			throw new Exception("Input Price higher than 0");

		String idProduct = "";

		return new Product(idProduct, ProductName, ProductBrand, ProductPrice, 1);
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

				productData.add(new Product(productID, productName, productMerk, productPrice, productStock));

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

	private void insertIntoProduct(Product product) {
		String productID = generateID();
		String productName = product.getProductName();
		String productmerk = product.getProductMerk();
		Integer productPrice = product.getProductPrice();
		Integer stock = product.getProductStock();
		String query = String.format(
				"INSERT INTO `msproduct`(`ProductID`, `ProductName`, `ProductMerk`, `ProductPrice`, `ProductStock`) "
						+ "VALUES ('%s','%s','%s','%s','%s')",
				productID, productName, productmerk, productPrice, stock);
		Connect.getInstance().execUpdate(query);

	}

	private void updateProduct(Product product) {
		String productID = product.getProductID();
		Integer stock = product.getProductStock() + addStockSpinner.getValue();
		String query = String.format("UPDATE `msproduct` SET `ProductStock`='%s' WHERE ProductID = '%s'", stock,
				productID);

		Connect.getInstance().execUpdate(query);

	}

	private void deleteProduct(Product product) {
		String productID = product.getProductID();
		String query = String.format("DELETE FROM `msproduct` WHERE ProductID = '%s'", productID);

		Connect.getInstance().execUpdate(query);

	}

	private String generateID() {
		String query = String.format("SELECT * FROM `msproduct` ORDER BY ProductID DESC LIMIT 1");

		ResultSet res = Connect.getInstance().execQuery(query);
		String lastProductID = "";

		try {
			while (res.next()) {
				lastProductID = res.getString("ProductID");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		Integer idNum = Integer.parseInt(lastProductID.substring(2));

		String template = "PD";
		if (idNum < 9) {
			template += "00" + (idNum + 1);
		} else if (idNum < 99) {
			template += "0" + (idNum + 1);
		} else {
			template += (idNum + 1);
		}
		return template;
	}

}
