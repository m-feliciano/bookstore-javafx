module bookstore.javafx {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	requires poo;
	requires java.sql;
	
	opens application to javafx.graphics, javafx.fxml;
}
