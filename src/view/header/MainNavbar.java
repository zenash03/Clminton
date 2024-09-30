package view.header;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import main.Main;
import model.User;
import view.CartScene;
import view.HistoryScene;
import view.HomeScene;
import view.LoginScene;

public class MainNavbar extends MenuBar implements EventHandler<ActionEvent>{

	private Menu pageMenu;
	private MenuItem pageItemHome;
	private MenuItem pageItemCart;
	private MenuItem pageItemHistory;
	private MenuItem pageItemLogout;
	
	private User userData;
	
	public MainNavbar(User userData) {
		init();
		this.userData = userData;
		setLayout();
		setAction();
	}
	public void init() {
		pageMenu = new Menu("Page");
		pageItemHome = new MenuItem("Home");
		pageItemCart = new MenuItem("Cart");
		pageItemHistory = new MenuItem("History");
		pageItemLogout = new MenuItem("Logout");			
		
		pageMenu.getItems().add(pageItemHome);
		pageMenu.getItems().add(pageItemCart);
		pageMenu.getItems().add(pageItemHistory);
		pageMenu.getItems().add(pageItemLogout);
		
		
		getMenus().addAll(pageMenu);
	}
	
	public void setLayout() {
		
	}
	
	public void setAction() {
		pageItemHome.setOnAction(this);
		pageItemCart.setOnAction(this);
		pageItemHistory.setOnAction(this);
		pageItemLogout.setOnAction(this);
	}
	
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		Object click = event.getSource();
		if (click.equals(pageItemHome)) {
			HomeScene home = new HomeScene(userData);
			Main.changeScene(home, home.getSceneTitle());
		}
		else if (click.equals(pageItemCart)) {
			CartScene cart = new CartScene(userData);
			Main.changeScene(cart, cart.getSceneTitle());
		}
		else if (click.equals(pageItemHistory)) {
			HistoryScene history = new HistoryScene(userData);
			Main.changeScene(history, history.getSceneTitle());
		}
		else if (click.equals(pageItemLogout)) {
			System.out.println("Logout");
			LoginScene login = new LoginScene(null);
			Main.changeScene(login, login.getSceneTitle());
		}
	}
}
