package view;

import java.sql.ResultSet;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import main.Main;
import model.User;
import util.Connect;
import view.header.LoginRegisNavbar;

public class RegisterScene extends Page implements EventHandler<ActionEvent>{

	private GridPane formPane;
	private FlowPane mainPane;
	private GridPane formLeftPane, formRightPane;
	
	private Label registerLabel, emailLabel, passwordLabel, confirmLabel, ageLabel, genderLabel, nationalityLabel;
	private TextField emailField;
	private PasswordField passwordField, confirmPasswordField;
	private Spinner<Integer> ageSpin;
	private Button registerButton;
	
	private ToggleGroup genderGroup;
	private RadioButton maleButton, femaleButton;
	private GridPane genderPane;
	
	private ComboBox<String> nationalityBox;
	private GridPane nationalityPane;
	
	
	public RegisterScene(User userData) {
		// TODO Auto-generated constructor stub
		super(userData);
	}

	@Override
	public void initialize(User userData) {
		
		Font fontSize = new Font(15);
		
		formPane = new GridPane();
		mainPane = new FlowPane();
		formLeftPane = new GridPane();
		formRightPane = new GridPane();
		
		registerLabel = new Label("Register");
		emailLabel = new Label("Email");
		emailLabel.setFont(fontSize);
		passwordLabel = new Label("Password");
		passwordLabel.setFont(fontSize);
		confirmLabel = new Label("Confirm Password");
		confirmLabel.setFont(fontSize);
		ageLabel = new Label("Age");
		ageLabel.setFont(fontSize);
		genderLabel = new Label("Gender");
		genderLabel.setFont(fontSize);
		nationalityLabel = new Label("Nationality");
		nationalityLabel.setFont(fontSize);
		
		emailField = new TextField();
		passwordField = new PasswordField();
		confirmPasswordField = new PasswordField();
		
		ageSpin = new Spinner<>(1, 100, 1);
		ageSpin.setEditable(true);
		
		registerButton = new Button("Register");
		
		maleButton = new RadioButton("Male");
		femaleButton = new RadioButton("Female");
		maleButton.setUserData("Male");
		femaleButton.setUserData("Female");
		
		genderGroup = new ToggleGroup();
		maleButton.setToggleGroup(genderGroup);
		femaleButton.setToggleGroup(genderGroup);
		genderPane = new GridPane();
		genderPane.add(genderLabel, 0, 0);
		genderPane.add(maleButton, 0, 1);
		genderPane.add(femaleButton, 0, 2);
		genderPane.setVgap(10);
		
		nationalityBox = new ComboBox<>();
		nationalityBox.setMinWidth(120);
		nationalityBox.getItems().addAll("Indonesia", "Foreigner");
		nationalityBox.getSelectionModel().selectFirst();
		
		nationalityPane = new GridPane();
		nationalityPane.add(nationalityLabel, 0, 0);
		nationalityPane.add(nationalityBox, 0, 1);
		nationalityPane.setVgap(10);
		
	}

	@Override
	public void setTopLayout(User userData) {
		// TODO Auto-generated method stub
		setTop(new LoginRegisNavbar());
	}

	@Override
	public void setLayout() {
		// TODO Auto-generated method stub
		registerButton.setMinWidth(120);
		
		formLeftPane.add(emailLabel, 0, 0);
		formLeftPane.add(emailField, 0, 1);
		formLeftPane.add(passwordLabel, 0, 2);
		formLeftPane.add(passwordField, 0, 3);
		formLeftPane.add(confirmLabel, 0, 4);
		formLeftPane.add(confirmPasswordField, 0, 5);
		formLeftPane.add(ageLabel, 0, 6);
		formLeftPane.add(ageSpin, 0, 7);
		formLeftPane.setVgap(10);
		
		formRightPane.setVgap(10);
		formRightPane.add(genderPane, 0, 0);
		formRightPane.add(nationalityPane, 0, 1);
		formRightPane.add(registerButton, 0, 2);
		
		
		formPane.add(formLeftPane, 0, 0);
		formPane.add(formRightPane, 1, 0);
		formPane.setHgap(20);
		formPane.setAlignment(Pos.CENTER);
		
		mainPane.getChildren().add(formPane);
		mainPane.setAlignment(Pos.CENTER);
		setCenter(mainPane);
	}

	@Override
	public void setAction() {
		// TODO Auto-generated method stub
		registerButton.setOnAction(this);
	}

	@Override
	public void clearForm() {
		// TODO Auto-generated method stub
		emailField.setText("");
		passwordField.setText("");
		confirmPasswordField.setText("");
		
		SpinnerValueFactory<Integer> newValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
		ageSpin.setValueFactory(newValue);
		
	}
	
	public String getSceneTitle() {
		return registerLabel.getText();
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		Object click = event.getSource();
		try {
			if (click.equals(registerButton)) {
				handleRegisterForm();
			}
		} catch (Exception e) {
			// TODO: handle exception
			showAlert(e.getMessage());
		}
	}
	
	public void showAlert(String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.show();
		alert.setContentText(message);
		alert.setTitle("ERROR");

	}

	private User getRegisterForm() throws Exception {
		String userEmail = emailField.getText();
		String userPassword = passwordField.getText();
		String userConfirmPassword = confirmPasswordField.getText();
		Integer userAge = ageSpin.getValue();
		Toggle gender = genderGroup.getSelectedToggle();
		String userNationality = nationalityBox.getSelectionModel().getSelectedItem();
		
		if (!userEmail.endsWith("@gmail.com")) {
			throw new Exception("Email must end with '@gmail.com'");
		} 
		else if (userPassword.length() < 6) {
			throw new Exception("Password must contain minimum 6 characters");
		} 
		else if (!userPassword.equals(userConfirmPassword)) {
			throw new Exception("Confirm Password must be the same as Password");
		}
		else if (userAge <= 0) {
			throw new Exception("Age must be greater than 0");
		}
		else if (gender == null) {
			throw new Exception("Gender must be Male or Female");
		}
		else if (!(userNationality.equals("Indonesia") || userNationality.equals("Foreigner"))) {
			throw new Exception("Please select your Nationality!!");
		}
		
		String userGender = gender.getUserData().toString();
		
		String userID = generateID();
		String userRole = "User";
		
		User newUser = new User(userID, userEmail, userPassword, userAge, userGender, userNationality, userRole);
		
		return newUser;
	}
	
	private void handleRegisterForm() throws Exception {
		User user = getRegisterForm();
		if (verifyEmail(user.getUserEmail())) {
			throw new Exception("Email already been registered");
		}
		
		registerNewUser(user);
		
		LoginScene login = new LoginScene(null);
		Main.changeScene(login, login.getSceneTitle());
	}
	
	private String generateID() {
		String query = String.format("SELECT * FROM msuser ORDER BY UserID DESC LIMIT 1");
		
		ResultSet res = Connect.getInstance().execQuery(query);
		
		String lastUserID = "";
		try {
			while(res.next()) {
				lastUserID = res.getString("UserID");
			}
		} catch (Exception e) {
			
		}
		Integer idNum = Integer.parseInt(lastUserID.substring(2));
		
		String template = "UA";
        if (idNum < 9){
            template +=  "00" + (idNum + 1);
        }
        else if (idNum < 99){
        	template +=  "0" + (idNum + 1);
        } else {
        	template += (idNum + 1);
        }
		
		return template;
	}
	
	private boolean verifyEmail(String userEmail) {
		String query = String.format("SELECT * FROM msuser WHERE UserEmail = '%s'", userEmail);
		
		ResultSet res = Connect.getInstance().execQuery(query);
		int row = 0;
		try {
			while(res.next()) {
				++row;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (row == 0) return false;
		
		return true;
	}
	
	private void registerNewUser(User user) {
		String userID = user.getUserID();
		String userEmail = user.getUserEmail();
		String userPassword = user.getUserPassword();
		Integer userAge = user.getUserAge();
		String userGender = user.getUserGender();
		String userNationality = user.getUserNationality();
		String userRole = user.getUserRole();
		String query = String.format(
				"INSERT INTO msuser(UserID, UserEmail, UserPassword, UserAge, UserGender, UserNationality, UserRole) "
				+ "VALUES ('%s','%s','%s','%d','%s','%s','%s')", userID, userEmail, userPassword, userAge, userGender, userNationality, userRole);
		

		Connect.getInstance().execUpdate(query);
	}
}
