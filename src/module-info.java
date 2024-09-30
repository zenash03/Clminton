module BAD_LAB_Project_CLminton {
	opens main;
	requires javafx.graphics;
	requires javafx.controls;
	requires java.sql;
	
	opens model to javafx.base;
}