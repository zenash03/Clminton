package view.header;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import main.Main;
import model.User;
import view.HistoryScene;
import view.HistorySceneAdmin;
import view.LoginScene;
import view.ManageProductScene;

public class AdminNavbar extends MenuBar implements EventHandler<ActionEvent>{

	private Menu pageMenu;
	private MenuItem pageItemManageProduct;
	private MenuItem pageItemViewHistory;
	private MenuItem pageItemLogout;
	
	private User userData;
	
	public AdminNavbar(User userData) {
		init();
		this.userData = userData;
		setLayout();
		setAction();
	}
	public void init() {
		pageMenu = new Menu("Admin");
		pageItemManageProduct = new MenuItem("Manage Product");
		pageItemViewHistory = new MenuItem("View History");
		pageItemLogout = new MenuItem("Logout");
		
		pageMenu.getItems().add(pageItemManageProduct);
		pageMenu.getItems().add(pageItemViewHistory);
		pageMenu.getItems().add(pageItemLogout);
		
		getMenus().addAll(pageMenu);
	}
	
	public void setLayout() {
		
	}
	
	public void setAction() {
		pageItemManageProduct.setOnAction(this);
		pageItemViewHistory.setOnAction(this);
		pageItemLogout.setOnAction(this);
	}
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		Object click = event.getSource();
		if(click.equals(pageItemManageProduct)) {
			ManageProductScene manageProduct = new ManageProductScene(userData);
			Main.changeScene(manageProduct, manageProduct.getSceneTitle());
		}
		else if (click.equals(pageItemViewHistory)) {
			HistorySceneAdmin history = new HistorySceneAdmin(userData);
			Main.changeScene(history, history.getSceneTitle());
		}
		else if (click.equals(pageItemLogout)) {
			System.out.println("Logout");
			LoginScene login = new LoginScene(null);
			Main.changeScene(login, login.getSceneTitle());
		}
	}
}
