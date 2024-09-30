package view.header;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.Main;
import view.LoginScene;
import view.RegisterScene;

public class LoginRegisNavbar extends MenuBar implements EventHandler<ActionEvent>{
	private Menu pageMenu;
	private MenuItem pageItemLogin, pageItemRegis;
	
	public LoginRegisNavbar() {
		init();
		setLayout();
		setAction();
	}
	
	public void init() {
		pageMenu = new Menu("Page");
		
		pageItemLogin = new MenuItem("Login");
		pageItemRegis = new MenuItem("Register");
		pageMenu.getItems().add(pageItemLogin);
		pageMenu.getItems().add(pageItemRegis);
		
		getMenus().addAll(pageMenu);
	}
	
	public void setLayout() {
		
	}
	
	public void setAction() {
		pageItemLogin.setOnAction(this);
		pageItemRegis.setOnAction(this);
		
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		if (event.getSource().equals(pageItemLogin)) {
			System.out.println("Clicked");
			LoginScene login = new LoginScene(null);
			Main.changeScene(login, login.getSceneTitle());
		} 
		else if (event.getSource().equals(pageItemRegis)) {
			System.out.println("Regis");
			RegisterScene reg = new RegisterScene(null);
			Main.changeScene(reg, reg.getSceneTitle());
		}
	}

	
}
