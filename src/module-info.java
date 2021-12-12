module bookstore.javafx {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	requires poo;
	
	opens application to javafx.graphics, javafx.fxml;
}
