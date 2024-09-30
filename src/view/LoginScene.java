package view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import main.Main;
import model.User;
import util.Connect;
import view.header.LoginRegisNavbar;

public class LoginScene extends Page implements EventHandler<ActionEvent>{

	private GridPane formPane;
	private FlowPane mainPane;
	
	private TextField emailField;
	private PasswordField passwordField;
	private Label loginLabel, emailLabel, passwordLabel;
	private Button loginButton;
	
	private Font fontSizeText;
	
	private User userData;
	
	public LoginScene(User userData) {
		super(userData);
		this.userData = null;
	}

	@Override
	public void initialize(User userData) {
		this.userData = userData;
		
		formPane = new GridPane();
		mainPane = new FlowPane();
		
		fontSizeText = new Font(14);
		
		loginLabel = new Label("Login");
		emailLabel = new Label("Email");
		passwordLabel = new Label("Password");
		
		emailLabel.setFont(fontSizeText);
		passwordLabel.setFont(fontSizeText);
		
		emailField = new TextField();
		passwordField = new PasswordField();
		
		loginButton = new Button("Login");
	}

	@Override
	public void setTopLayout(User userData) {
		// TODO Auto-generated method stub
		setTop(new LoginRegisNavbar());
	}

	@Override
	public void setLayout() {
		// TODO Auto-generated method stub
		loginButton.setMinWidth(100);
		formPane.add(emailLabel, 0, 0);
		formPane.add(emailField, 0, 1);
		formPane.add(passwordLabel, 0, 2);
		formPane.add(passwordField, 0, 3);
		formPane.add(loginButton, 0, 4);
		formPane.setVgap(10);
		formPane.setAlignment(Pos.CENTER);
		
		mainPane.setAlignment(Pos.CENTER);
		mainPane.getChildren().add(formPane);
		setCenter(mainPane);
	}

	@Override
	public void setAction() {
		// TODO Auto-generated method stub
		loginButton.setOnAction(this);
	}

	@Override
	public void clearForm() {
		// TODO Auto-generated method stub
		emailField.setText("");
		passwordField.setText("");
	}
	
	public String getSceneTitle() {
		return loginLabel.getText();
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		Object click = event.getSource();
		try {
			if (click.equals(loginButton)) {
				handleLogin();
			}			
		} catch (Exception e) {
			// TODO: handle exception
			showWarning(e.getMessage());
		}
	}
	
	public void showWarning(String message) {
		Alert alert = new Alert(AlertType.WARNING, message, ButtonType.OK);
		alert.show();
		
	}
	
	public User getFormData() throws Exception {
		String email;
		String password;
		
		email = emailField.getText();
		password = passwordField.getText();
		
		if(email.isEmpty() || password.isEmpty()) {
			 throw new Exception("Email or Password must be filled!");
		}
		return new User(email, password);
	}
	public void handleLogin() throws Exception{
		User user = getFormData();
		
		try {
			if (checkLogin(user.getUserEmail(), user.getUserPassword())) {
				System.out.println("Login Success");
				if(userData.getUserRole().equals("Admin")) {
					ManageProductScene mps = new ManageProductScene(userData);
					Main.changeScene(mps, mps.getSceneTitle());
				} else {
					HomeScene home = new HomeScene(userData);
					Main.changeScene(home, home.getSceneTitle());
					
				}
			} else {
				throw new Exception("Wrong Email or Password");
			}
		} catch (Exception e) {
			showWarning(e.getMessage());
		}
		
	}
	private boolean checkLogin(String email, String password) {
		int row = 0;
		try {
			Connect con = Connect.getInstance();
			String query = "SELECT * FROM msuser WHERE UserEmail= ? AND UserPassword = ?";
			PreparedStatement ps = con.createPreparedStatement(query);
			
			ps.setString(1, email);
			ps.setString(2, password);
			ps.execute();
			ResultSet res = ps.getResultSet();
			
			while (res.next()) {
				String userID = res.getString("UserID");
				String userEmail = res.getString("UserEmail");
				String userPassword = res.getString("UserPassword");
				Integer userAge = res.getInt("UserAge");
				String userGender = res.getString("UserGender");
				String userNationality = res.getString("UserNationality");
				String userRole = res.getString("UserRole");
				
				userData = new User(userID, userEmail, userPassword, userAge, userGender, userNationality, userRole); 
				++row;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (row == 0) {
			return false;
		}
		
		return true;
	}
}
