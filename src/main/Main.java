package main;
//import view.HistoryScene;
//import view.LoginForm;
//import view.ManageProduct;
//import view.RegisterForm;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import model.User;
import view.CartScene;
import view.HistoryScene;
import view.HomeScene;
import view.LoginScene;
import view.ManageProductScene;
import view.RegisterScene;
import view.TransactionCardPopUp;

public class Main extends Application {
	
	Scene scene;
	
	private static Stage stage;
	
	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		LoginScene firstScene = new LoginScene(null);
		
		this.stage = primaryStage;
//		scene = new Scene(new CartScene(null), 1200, 800);
		scene = new Scene(firstScene, 1200, 800);
		stage.setTitle(firstScene.getSceneTitle());
		stage.setScene(scene);
		stage.show();
		
	}
	public static void changeScene(BorderPane scene, String title) {
		stage.getScene().setRoot(scene);
		stage.setTitle(title);
	}
}
