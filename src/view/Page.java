package view;

import javafx.scene.layout.BorderPane;
import model.User;

abstract public class Page extends BorderPane {
	
	public Page(User userData) {
		// TODO Auto-generated constructor stub
		initialize(userData);
		setTopLayout(userData);
		setLayout();
		setAction();
		clearForm();
	}
	

	public abstract void initialize(User userData);
	public abstract void setTopLayout(User userData);
	public abstract void setLayout();
	public abstract void setAction();
	public abstract void clearForm();

}
