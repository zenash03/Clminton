package view;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.Main;
import model.Cart;
import model.TransactionDetail;
import model.TransactionHeader;
import model.User;
import util.Connect;

public class TransactionCardPopUp extends Page implements EventHandler<ActionEvent>{

	private GridPane formPane;
	private FlowPane mainPane;
	
	private FlowPane headerLabelPane;
	private FlowPane listLabelPane;
	private FlowPane cartLabelPane;
	private FlowPane courierLabelPane;
	private FlowPane courierBoxPane;
	private FlowPane useInsuranceBoxPane;
	private FlowPane totalPriceLabelPane;
	private FlowPane checkOutButtonPane;
	
	private Label headerLabel, listLabel, cartLabel, courierLabel, totalPriceLabel;
	private Button checkOutBtn;
	private CheckBox useInsuranceBox;
	private ComboBox<String> courierBox;
	private Font fontsize, fontSizeText, fontSizeCart;
	
	private User userData;
	private ArrayList<Cart> cartData;
	
	private Stage popUpStage;
	
	public TransactionCardPopUp(User userData, ArrayList<Cart> cartData, Stage popUpStage) {
		// TODO Auto-generated constructor stub
		super(userData);
		this.popUpStage = popUpStage;
		this.cartData = cartData;
		setCartList();
	}

	@Override
	public void initialize(User userData) {
		this.userData = userData;

		mainPane = new FlowPane();
		formPane = new GridPane();
		
		headerLabelPane = new FlowPane();
		listLabelPane = new FlowPane();
		cartLabelPane = new FlowPane();
		courierLabelPane = new FlowPane();
		courierBoxPane = new FlowPane();
		useInsuranceBoxPane = new FlowPane();
		totalPriceLabelPane = new FlowPane();
		checkOutButtonPane = new FlowPane();
		
		fontsize = new Font(18);
		fontSizeText = new Font(16);
		fontSizeCart = new Font(14);
		
		headerLabel = new Label("Transaction Card");
		listLabel = new Label("List");
		cartLabel = new Label();
		
		courierLabel = new Label("Courier");
		useInsuranceBox = new CheckBox("Use Insurance");
		totalPriceLabel = new Label("Total Price   : ");
		checkOutBtn = new Button("CheckOut");
		
		courierBox = new ComboBox<>();
		courierBox.getItems().addAll("J&E", "Nanji Express", "Gejok", "JET");
		courierBox.getSelectionModel().selectFirst();
		
		listLabel.setFont(fontSizeText);
		cartLabel.setFont(fontSizeCart);
		courierLabel.setFont(fontSizeText);
		useInsuranceBox.setFont(fontSizeText);
		totalPriceLabel.setFont(fontSizeText);
		checkOutBtn.setFont(fontSizeCart);
		
		checkOutBtn.setPrefWidth(240);
		
		courierBox.setStyle("-fx-font: 16px \"Arial\";");
		
		headerLabel.setTextFill(Color.WHITE);
		
		mainPane.setStyle("-fx-background-color: #8EB0C4;");
		headerLabelPane.setStyle("-fx-background-color: black;");
		
		cartData = new ArrayList<>();
	}

	@Override
	public void setTopLayout(User userData) {
		// TODO Auto-generated method stub
//		setTop(new MainNavbar());
		setTop(headerLabelPane);
	}

	@Override
	public void setLayout() {
		// TODO Auto-generated method stub
		checkOutBtn.setMinWidth(200);
		headerLabel.setFont(fontsize);
	
		
		// masukin ke pane masing"
		headerLabelPane.getChildren().add(headerLabel);
		headerLabelPane.setAlignment(Pos.CENTER);
		
		listLabelPane.getChildren().add(listLabel);
		listLabelPane.setAlignment(Pos.CENTER);
		
		cartLabelPane.getChildren().add(cartLabel);
		cartLabelPane.setAlignment(Pos.CENTER);
		
		courierLabelPane.getChildren().add(courierLabel);
		courierLabelPane.setAlignment(Pos.CENTER);
		
		courierBoxPane.getChildren().add(courierBox);
		courierBoxPane.setAlignment(Pos.CENTER);
		
		useInsuranceBoxPane.getChildren().add(useInsuranceBox);
		useInsuranceBoxPane.setAlignment(Pos.CENTER);
		
		totalPriceLabelPane.getChildren().add(totalPriceLabel);
		totalPriceLabelPane.setAlignment(Pos.CENTER);
		
		checkOutButtonPane.getChildren().add(checkOutBtn);
		checkOutButtonPane.setAlignment(Pos.CENTER);
		
		
		formPane.add(listLabelPane, 0, 0);
		formPane.add(cartLabelPane, 0, 1);
		formPane.add(courierLabelPane, 0, 2);
		formPane.add(courierBoxPane, 0, 3);
		formPane.add(useInsuranceBoxPane, 0, 4);
		formPane.add(totalPriceLabelPane, 0, 5);
		formPane.add(checkOutButtonPane, 0, 6);
		
		
		formPane.setVgap(15);
		formPane.setAlignment(Pos.CENTER);
		
		mainPane.getChildren().add(formPane);
		mainPane.setAlignment(Pos.CENTER);
		setCenter(mainPane);
		
	}

	@Override
	public void setAction() {
		// TODO Auto-generated method stub
		checkOutBtn.setOnAction(this);
//		useInsuranceBox.setOnAction(this);
		useInsuranceBox.selectedProperty().addListener((e, a, b) -> {
			if (b) {
				calcTotalPrice(90000);
			} else {
				calcTotalPrice(0);
			}
		});
	}

	@Override
	public void clearForm() {
		// TODO Auto-generated method stub
		courierBox.getSelectionModel().selectFirst();
		
	}
	
	private void setCartList() {
		String cartList = "";
		
		for (Cart cart : cartData) {
			String format = String.format("%s : %d\n", cart.getProductName(), cart.getTotalPrice());
			
			cartList += format;
		}

		cartLabel.setText(cartList);	
		calcTotalPrice(0);
	}
	
	private void calcTotalPrice(Integer insurance) {
		Integer totalPrice = 0;
		for (Cart cart : cartData) {
			totalPrice += cart.getTotalPrice();
		}
		totalPrice += insurance;
		String format = String.format("%s : %d", "Total Price", totalPrice);
		totalPriceLabel.setText(format);
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		try {
			if (event.getSource().equals(checkOutBtn)) {
				Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure want to checkout all the item?", ButtonType.OK, ButtonType.CANCEL);
				Optional<ButtonType> option =  alert.showAndWait();
				if (option.get().equals(ButtonType.OK)) {
					handleCheckOut();
				} else if (option.get().equals(ButtonType.CANCEL)) {
					popUpStage.close();
					CartScene cart = new CartScene(userData);
					Main.changeScene(cart, cart.getSceneTitle());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private void handleCheckOut() {
		TransactionHeader newTh = getTransactionHeader();
		ArrayList<TransactionDetail> newTd = getTransactionDetail(newTh.getTransactionID());
		
		insertNewTh(newTh);
		insertNewTd(newTd);
		deleteCart();
		
		popUpStage.close();
		CartScene cart = new CartScene(userData);
		Main.changeScene(cart, cart.getSceneTitle());
	}
	
	private TransactionHeader getTransactionHeader() {
		String transactionID = generateID();
		String userID = userData.getUserID();
		String emailUser = userData.getUserEmail();
		String transactionDate = getDateNow();
		
		
		Integer deliveryInsurance = (useInsuranceBox.isSelected()) ? 1 : 0;
		String courierType = courierBox.getSelectionModel().getSelectedItem();
		
		TransactionHeader th = new TransactionHeader(transactionID, userID, emailUser, transactionDate, deliveryInsurance, courierType);
		
		return th;
	}
	
	private String getDateNow() {
		LocalDate date = LocalDate.now();
		
		return date.toString();
	}

	private void insertNewTh(TransactionHeader th) {
		String transactionID = th.getTransactionID();
		String userID = th.getUserID();
		String transactionDate = th.getTransactionDate();
		Integer deliveryInsurance = th.getDeliveryInsurance();
		String courierType = th.getCourierType();
		String query = String.format(""
				+ "INSERT INTO transactionheader(TransactionID, UserID, TransactionDate, DeliveryInsurance, CourierType) "
				+ "VALUES ('%s','%s','%s','%d','%s')", transactionID, userID, transactionDate, deliveryInsurance, courierType);
		
		Connect.getInstance().execUpdate(query);
		
	}
	
	private String generateID() {
		String query = String.format("SELECT * FROM `transactionheader` ORDER BY TransactionID DESC LIMIT 1");
		
		ResultSet res = Connect.getInstance().execQuery(query);
		String lastTransactionID = "";
		
		try {
			while (res.next()) {
				lastTransactionID = res.getString("TransactionID");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		Integer idNum = Integer.parseInt(lastTransactionID.substring(2));
		
		
		String template = "TH";
		if (idNum < 9) {
			template += "00" + (idNum + 1);
		}
		else if (idNum < 99) {
			template += "0" + (idNum + 1);
		}
		else {
			template += (idNum + 1);
		}
		return template;
	}
	
	private ArrayList<TransactionDetail> getTransactionDetail(String transactionID) {
		ArrayList<TransactionDetail> tdArr = new ArrayList<>();
		for (Cart cart : cartData) {
			String transactionDetailID = transactionID;
			String productID = cart.getProductID();
			Integer quantity = cart.getProductQuantity();
			TransactionDetail td = new TransactionDetail(transactionDetailID, productID, quantity);
			
			tdArr.add(td);
		}
		
		return tdArr;
	}
	private void insertNewTd(ArrayList<TransactionDetail> newTd) {
		String query = String.format("INSERT INTO transactiondetail(ProductID, TransactionID, Quantity) VALUES ");
		
		for (TransactionDetail transactionDetail : newTd) {
			String transactionID = transactionDetail.getTransactionDetailID();
			String productID = transactionDetail.getProductID();
			Integer quantity = transactionDetail.getQuantity();
			
			String format = "";
			if ((newTd.size()- 1) == newTd.indexOf(transactionDetail)) {
				format = String.format("('%s', '%s', '%d') ", productID, transactionID, quantity);				
			}
			else {
				format = String.format("('%s', '%s', '%d'), ", productID, transactionID, quantity);	
			}
			query += format;
		}
		
		Connect.getInstance().execUpdate(query);
	}
	private void deleteCart() {
		String query = String.format("DELETE FROM carttable WHERE UserID = '%s'", userData.getUserID());
		
		Connect.getInstance().execUpdate(query);
	}
}
