package view;

import java.sql.ResultSet;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import model.TransactionDetail;
import model.TransactionHeader;
import model.User;
import util.Connect;
import view.header.AdminNavbar;

public class HistorySceneAdmin extends Page implements EventHandler<ActionEvent>{

	private FlowPane mainPane;
	private GridPane formPane;
	
	private TableView<TransactionHeader> transactionHeaderTable;
	private TableView<TransactionDetail> transactionDetailTable;
	
	private Label historyLabel;
	private Label myTransactionLabel, transactionDetailLabel, totalPriceLabel;
	
	private Font fontSizeTitle;
	
	
	private TableColumn<TransactionHeader, String> transactionIDColumn;
	private TableColumn<TransactionHeader, String> emailColumn;
	private TableColumn<TransactionHeader, String> transactionDateColumn;
	
	private TableColumn<TransactionDetail, String> transactionDetailIDColumn;
	private TableColumn<TransactionDetail, String> productNameColumn;
	private TableColumn<TransactionDetail, Integer> priceColumn;
	private TableColumn<TransactionDetail, Integer> quantityColumn;
	private TableColumn<TransactionDetail, Integer> totalPriceColumn;
	
	private ObservableList<TransactionHeader> transactionHeaderDataView;
	private ArrayList<TransactionHeader> transactionHeaderData;
	private TransactionHeader selectedTransactionHeader = null;
	
	private ObservableList<TransactionDetail> transactionDetailDataView;
	private ArrayList<TransactionDetail> transactionDetailData;
	
	
	private User userData;
	
	public HistorySceneAdmin(User userData) {
		// TODO Auto-generated constructor stub
		super(userData);
		setTransactionHeaderData();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(User userData) {
		this.userData = userData;
		
		// TODO Auto-generated method stub
		mainPane = new FlowPane();
		formPane = new GridPane();
		
		fontSizeTitle = new Font(15);
		
		historyLabel = new Label("My History");
		myTransactionLabel = new Label("My Transaction");
		myTransactionLabel.setFont(fontSizeTitle);
		
		transactionDetailLabel = new Label("Transaction Detail"); // 1
		transactionDetailLabel.setFont(fontSizeTitle);
		
		totalPriceLabel = new Label("Total Price : ");
		totalPriceLabel.setFont(fontSizeTitle);
		
		transactionHeaderTable = new TableView<>();
		transactionIDColumn = new TableColumn<>("ID");
		emailColumn = new TableColumn<>("Email");
		transactionDateColumn = new TableColumn<>("Date");
		
		transactionIDColumn.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("emailUser"));
		transactionDateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
		
		
		transactionIDColumn.setResizable(false);
		emailColumn.setResizable(false);
		transactionDateColumn.setResizable(false);
		
		transactionIDColumn.prefWidthProperty().bind(transactionHeaderTable.widthProperty().multiply(0.325));
		emailColumn.prefWidthProperty().bind(transactionHeaderTable.widthProperty().multiply(0.33));
		transactionDateColumn.prefWidthProperty().bind(transactionHeaderTable.widthProperty().multiply(0.33));
		transactionHeaderTable.setPrefHeight(400);
		transactionHeaderTable.setPrefWidth(400);
		
		transactionHeaderTable.getColumns().setAll(
				transactionIDColumn,
				emailColumn,
				transactionDateColumn
		);
		
		transactionDetailTable = new TableView<>();
		transactionDetailIDColumn = new TableColumn<>("ID");
		productNameColumn = new TableColumn<>("Product");
		priceColumn = new TableColumn<>("Price");
		quantityColumn = new TableColumn<>("Quantity");
		totalPriceColumn = new TableColumn<>("Total Price");
		
		transactionDetailIDColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDetailID"));
		productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
		
		
		transactionDetailIDColumn.setResizable(false);
		productNameColumn.setResizable(false);
		priceColumn.setResizable(false);
		quantityColumn.setResizable(false);
		totalPriceColumn.setResizable(false);
		
		transactionDetailIDColumn.prefWidthProperty().bind(transactionDetailTable.widthProperty().multiply(0.195));		
		productNameColumn.prefWidthProperty().bind(transactionDetailTable.widthProperty().multiply(0.2));	
		priceColumn.prefWidthProperty().bind(transactionDetailTable.widthProperty().multiply(0.2));	
		quantityColumn.prefWidthProperty().bind(transactionDetailTable.widthProperty().multiply(0.2));	
		totalPriceColumn.prefWidthProperty().bind(transactionDetailTable.widthProperty().multiply(0.2));	
		transactionDetailTable.setPrefHeight(400);
		transactionDetailTable.setPrefWidth(500);
		
		transactionDetailTable.getColumns().addAll(
				transactionDetailIDColumn,
				productNameColumn,
				priceColumn,
				quantityColumn,
				totalPriceColumn
		);
		
		transactionHeaderData = new ArrayList<>();
		transactionDetailData = new ArrayList<>();
	}

	@Override
	public void setTopLayout(User userData) {
		// TODO Auto-generated method stub
		setTop(new AdminNavbar(userData));
	}

	@Override
	public void setLayout() {
		// TODO Auto-generated method stub
		formPane.add(myTransactionLabel, 0, 0);
		formPane.add(transactionDetailLabel, 1, 0);
		formPane.add(transactionHeaderTable, 0, 1);
		formPane.add(transactionDetailTable, 1, 1);
		formPane.add(totalPriceLabel, 1, 2);
		formPane.setAlignment(Pos.CENTER);
		formPane.setHgap(20);
		formPane.setVgap(5);
		
		mainPane.getChildren().add(formPane);
		mainPane.setAlignment(Pos.CENTER);
		setCenter(mainPane);
		
	}

	@Override
	public void setAction() {
		// TODO Auto-generated method stub
		transactionHeaderTable.setOnMouseClicked(event -> {
			TransactionHeader th = transactionHeaderTable.getSelectionModel().getSelectedItem();
			selectedTransactionHeader = th;
			setTransactionDetailData();
		});
	}

	@Override
	public void clearForm() {
		// TODO Auto-generated method stub

	}
	
	public String getSceneTitle() {
		return historyLabel.getText();
	}

	private void setTransactionHeaderData() {
		String userId = userData.getUserID();
		String query = String.format("SELECT th.userID, mu.UserEmail, th.TransactionID, th.TransactionDate FROM transactionheader AS th JOIN msuser AS mu ON th.UserID = mu.UserID");
		ResultSet res = Connect.getInstance().execQuery(query);
		
		clearTransactionHeaderTable();
		
		try {
			while (res.next()) {
				String userID = res.getString("userID");
				String emailUser = res.getString("UserEmail");
				String transactionID = res.getString("TransactionID");
				String transactionDate = res.getString("TransactionDate");
				TransactionHeader th = new TransactionHeader(transactionID, userID, emailUser, transactionDate);
				transactionHeaderData.add(th);
				transactionHeaderDataView = FXCollections.observableArrayList(transactionHeaderData);
				transactionHeaderTable.setItems(transactionHeaderDataView);
				transactionHeaderTable.refresh();
			}
		} catch (Exception e) {
			// TODO: handle exception
//			e.printStackTrace();
		}
		
	}
	private void clearTransactionHeaderTable() {
		transactionHeaderData.removeAll(transactionHeaderData);
		transactionHeaderDataView = FXCollections.observableArrayList(transactionHeaderData);
		transactionHeaderTable.getItems().clear();
		transactionHeaderTable.refresh();
	}

	private void setTransactionDetailData(){
		String transactionHeaderID = selectedTransactionHeader.getTransactionID();
		String query = String.format("SELECT td.TransactionID, td.Quantity, mp.ProductID, mp.ProductName, mp.ProductPrice FROM transactiondetail td JOIN msproduct mp ON td.ProductID = mp.ProductID WHERE td.TransactionID = '%s'", transactionHeaderID);
		
		ResultSet res = Connect.getInstance().execQuery(query);
		
		clearTransactionDetailTable();
		try {
			while(res.next()) {				
				String transactionDetailID = res.getString("TransactionID");
				String productID = res.getString("ProductID");
				Integer quantity = res.getInt("Quantity");
				String productName = res.getString("ProductName");
				Integer productPrice = res.getInt("ProductPrice");
				Integer totalPrice = quantity * productPrice;
				
				TransactionDetail td = new TransactionDetail(transactionDetailID, productID, productName, productPrice, quantity, totalPrice);
				transactionDetailData.add(td);
				transactionDetailDataView = FXCollections.observableArrayList(transactionDetailData);
				transactionDetailTable.setItems(transactionDetailDataView);
				transactionDetailTable.refresh();
				calcTotalPrice();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	private void clearTransactionDetailTable() {
		transactionDetailData.removeAll(transactionDetailData);
		transactionDetailDataView = FXCollections.observableArrayList(transactionDetailData);
		transactionDetailTable.getItems().clear();
		transactionDetailTable.refresh();
	}
	
	private void calcTotalPrice() {
		Integer totalPrice = 0;
		for (TransactionDetail transactionDetail : transactionDetailData) {
			totalPrice += transactionDetail.getTotalPrice();
		}
		totalPriceLabel.setText(String.format("%-20s : %d", "Total Price", totalPrice));
	}
	
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}
}
